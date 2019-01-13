// ---------------------------------------------------------------------------
// Expressions.java
// The denotational semantics of expressions.
// $Id: Expressions.java,v 1.54 2018/06/14 13:22:44 schreine Exp schreine $
//
// Author: Wolfgang Schreiner <Wolfgang.Schreiner@risc.jku.at>
// Copyright (C) 2016-, Research Institute for Symbolic Computation (RISC)
// Johannes Kepler University, Linz, Austria, http://www.risc.jku.at
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
// ----------------------------------------------------------------------------
package riscal.semantics;

import riscal.*;
import riscal.syntax.*;
import riscal.syntax.AST.Type.IntType;
import riscal.types.*;
import riscal.types.Environment.Symbol;
import riscal.types.Environment.Symbol.*;
import riscal.semantics.Types.*;
import riscal.util.*;

import java.util.*;
import java.util.function.*;

public final class Expressions
{
  // the current translator
  private final Translator translator;
  
  /***************************************************************************
   * Create expression translator.
   * @param translator the overall translator.
   **************************************************************************/
  public Expressions(Translator translator)
  {
    this.translator = translator;
  }
  
  // -------------------------------------------------------------------------
  // 
  // Expressions
  //
  // -------------------------------------------------------------------------
  
  public ExpSem AndExp(ExpSem e1, ExpSem e2)
  {
    return ifThenElse(e1, e2, (ExpSem.Single)(Context c)->new Value.Bool(false));
  }
  public ExpSem ApplicationExp(AST.Expression.ApplicationExp exp,
    AST.Identifier ident, ExpSem[] es, AST.Expression[] exps)
  {
    FunctionSymbol symbol = (FunctionSymbol)ident.getSymbol();
    FunSem fun = symbol.getValue();
    if (fun == null)
    {
      // recursive function is not yet defined at translation time 
      // must be looked up at application time
      if (!(translator.nondeterministic && symbol.multiple))
        fun = (FunSem.Single)(Argument a)->
      {
        FunSem fun0 = symbol.getValue();
        if (fun0 == null)
          translator.runtimeError(exp, "function is declared but not defined");
        if (!(fun0 instanceof FunSem.Single))
          translator.runtimeError(exp, "nondeterministic recursive function must be declared as \"multiple\"");
        FunSem.Single fun1 = (FunSem.Single)fun0;
        return fun1.apply(a);
      };
      else
        fun = (FunSem.Multiple)(Argument a)->
      {
        FunSem fun0 = symbol.getValue();
        if (fun0 == null)
          translator.runtimeError(exp, "function is declared but not defined");
        if (!(fun0 instanceof FunSem.Multiple))
          translator.runtimeError(exp, "deterministic function must not be declared as \"multiple\"");
        FunSem.Multiple fun1 = (FunSem.Multiple)fun0;
        return fun1.apply(a);
      };
    }
    int n = exps.length;
    if (fun instanceof FunSem.Single)
    {
      FunSem.Single fun0 = (FunSem.Single)fun;
      FunSem.Single fun1 = (Argument a)->
      {

        for (int i=0; i<n; i++)
        {
          translator.checkConstraints(exps[i], a.values[i], exps[i].getType(), symbol.types[i]);
        }
        Trace.application(exp);
        return fun0.apply(a);
      };
      return apply(es, fun1);
    }
    else
    {
      FunSem.Multiple fun0 = (FunSem.Multiple)fun;
      FunSem.Multiple fun1 = (Argument a)->
      {

        for (int i=0; i<n; i++)
        {
          translator.checkConstraints(exps[i], a.values[i], exps[i].getType(), symbol.types[i]);
        }
        return fun0.apply(a);
      };
      return applyMultiple(es, fun1);
    }
  }
  public ExpSem ArrayBuilderExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int size = (Value.Int)v1;
      int n = size.getValue();
      Value.Array array = new Value.Array(n);
      for (int i=0; i<n; i++) array.set(i, v2);;
      return array;
    });
  }
  public ExpSem AssertExp(AST.Expression.AssertExp exp,
    ExpSem exp1, ExpSem exp2)
  {  
    return ifThenElse(exp1, exp2, (ExpSem.Single)(Context c)-> 
    {
      translator.runtimeError(exp, "assertion failed");
      return null;
    });
  }

  public ExpSem BigIntersectExp(ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Set set = (Value.Set)v;
      Value.Set result = new Value.Set();
      boolean first = true;
      for (Value elem : set)
      {
        Value.Set set0 = (Value.Set)elem;
        if (first)
        {
          result.addAll(set0);
          first = false;
        }
        else
          result.retainAll(set0);
      }
      return result;
    });
  }
  public ExpSem BigUnionExp(ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Set set = (Value.Set)v;
      Value.Set result = new Value.Set();
      for (Value elem : set)
      {
        Value.Set set0 = (Value.Set)elem;
        result.addAll(set0);
      }
      return result;
    });
  }
  public ExpSem ChooseExp(AST.Expression.ChooseExp exp, 
    ValueSymbol[] symbols, QvarSem qvar)
  {
    if (symbols.length == 1)
      return choose(exp, qvar, null, (FunExpSem.Single)(Value[] v)->(Context c)->v[0]);
    else
      return choose(exp, qvar, null, (FunExpSem.Single)(Value[] v)->(Context c)->new Value.Array(v));      
  }
  public ExpSem ChooseInElseExp(AST.Expression.ChooseInElseExp exp,
    ValueSymbol[] symbols, QvarSem qvar, ExpSem e1, ExpSem e2)
  {
    return choose(exp, qvar, e2, bind(symbols, e1));
  }
  public ExpSem ChooseInExp(AST.Expression.ChooseInExp exp,
    ValueSymbol[] symbols, QvarSem qvar, ExpSem e)
  {
    return choose(exp, qvar, null, bind(symbols, e));
  }
  public ExpSem DividesExp(AST.Expression.DividesExp exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      if (i1.getValue() == Integer.MIN_VALUE && i2.getValue() == -1)
        throw new ArithmeticException("integer overflow by computation of " +
         i1.getValue() + "/" + i2.getValue());
      try
      {
        return new Value.Int(i1.getValue()/i2.getValue());
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem EmptySetExp()
  {
    Value value = new Value.Set();
    return (ExpSem.Single)(Context c)-> value;
  }
  public ExpSem EnumeratedSetExp(ExpSem[] es)
  {
    return apply(es, (Argument a)->
    {
      Value.Set result = new Value.Set();
      for (Value v : a.values) result.add(v);
      return result;
    });
  }
  public ExpSem CartesianExp(ExpSem[] es)
  {
    return apply(es, (Argument a)->
    {
      int n = a.values.length;
      @SuppressWarnings("unchecked")
      List<Value>[] set = new List[n];
      for (int i=0; i<n; i++)
      {
        set[i] = new LinkedList<Value>((Value.Set)a.values[i]);
      }
      List<Value> tuples = Values.getTupleList(set);
      return new Value.Set(tuples);
    });
  }
  public ExpSem EqualsExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      return new Value.Bool(v1.equals(v2));
    });
  }
  public ExpSem EquivExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Bool b1 = (Value.Bool)v1;
      Value.Bool b2 = (Value.Bool)v2;
      return new Value.Bool(b1.getValue() == b2.getValue());
    });
  }
  public ExpSem ExistsExp(ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    return quantifiedSearch(qvar, bind(symbols, exp), true);
  }
  public ExpSem FalseExp()
  {
    Value value = new Value.Bool(false);
    return (ExpSem.Single)(Context c)-> value;
  }
  public ExpSem ForallExp(ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    return quantifiedSearch(qvar, bind(symbols, exp), false);
  }
  public ExpSem GreaterEqualExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      return new Value.Bool(i1.getValue() >= i2.getValue());
    });
  }
  public ExpSem GreaterExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      return new Value.Bool(i1.getValue() > i2.getValue());
    });
  }
  public ExpSem IdentifierExp(ValueSymbol symbol, AST.Expression exp)
  {
    if (symbol.kind == ValueSymbol.Kind.global)
    {
      // global value must be already known
      Seq<Value> values = symbol.getValues();
      if (values == null)
      {
        Value value = symbol.getValue();
        return (ExpSem.Single)(Context c)->value;
      }
      else
      {
        return (ExpSem.Multiple)(Context c)->values;
      }
    }
    else
    {
      int slot = Translator.toSlot(symbol);
      return (ExpSem.Single)(Context c)->
      {
        Value result = c.get(slot);
        if (result == null)
          translator.runtimeError(exp, "uninitialized variable");
        return result;
      };
    }
  }
  public ExpSem IfThenElseExp(ExpSem e1, ExpSem e2, ExpSem e3)
  {
    return ifThenElse(e1, e2, e3);
  }
  public ExpSem ImpliesExp(ExpSem e1, ExpSem e2)
  {
    return ifThenElse(e1, e2, (ExpSem.Single)(Context c)->new Value.Bool(true));
  }
  public ExpSem InSetExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Set s = (Value.Set)v2;
      return new Value.Bool(s.contains(v1));
    });
  }
  public ExpSem IntersectExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Set s1 = (Value.Set)v1;
      Value.Set s2 = (Value.Set)v2;
      Value.Set result = new Value.Set(s1);
      result.retainAll(s2);
      return result;
    });
  }
  public ExpSem IntervalExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      Value.Set result = new Value.Set();
      for (int i=i1.getValue(); i <= i2.getValue(); i++)
        result.add(new Value.Int(i));
      return result;
    });
  }
  public ExpSem LessEqualExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      return new Value.Bool(i1.getValue() <= i2.getValue());
    });
  }
  public ExpSem LessExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      return new Value.Bool(i1.getValue() < i2.getValue());
    });
  }
  public ExpSem LetExp(BinderSem[] bs, ExpSem e)
  {
    int n = bs.length;
    int i = n-1;
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      while (i >= 0 && (bs[i] instanceof BinderSem.Single))
      {
        BinderSem.Single b0 = (BinderSem.Single)bs[i];
        final ExpSem.Single f0 = e0;
        e0 = (ExpSem.Single)(Context c)-> f0.apply(b0.apply(c));
        i--;
      }
      if (i < 0) return e0;
      e = e0;
    }
    ExpSem.Multiple e1 = e.toMultiple();
    while (i >= 0)
    {
      BinderSem.Multiple b1 = bs[i].toMultiple();
      final ExpSem.Multiple f1 = e1;
      e1 = (ExpSem.Multiple)(Context c)-> b1.apply(c).applyJoin(f1);
      i--;
    }
    return e1; 
  }
  public ExpSem LetParExp(BinderSem b, ExpSem e)
  {
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      if (b instanceof BinderSem.Single)
      {
        BinderSem.Single b0 = (BinderSem.Single)b;
        return (ExpSem.Single)(Context c)->
        {
          Context c0 = b0.apply(c);
          return e0.apply(c0);
        };
      }
      else
      {
        BinderSem.Multiple b0 = (BinderSem.Multiple)b;
        return (ExpSem.Multiple)(Context c)->
        {
          Seq<Context> cs = b0.apply(c);
          return cs.apply((Context c0)->e0.apply(c0));
        };
      }
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      if (b instanceof BinderSem.Single)
      {
        BinderSem.Single b0 = (BinderSem.Single)b;
        return (ExpSem.Multiple)(Context c)->
        {
          Context c0 = b0.apply(c);
          return e0.apply(c0);
        };
      }
      else
      {
        BinderSem.Multiple b0 = (BinderSem.Multiple)b;
        return (ExpSem.Multiple)(Context c)->
        {
          Seq<Context> cs = b0.apply(c);
          return cs.applyJoin((Context c0)->e0.apply(c0));
        };
      }
    }
  }
  public ExpSem MapBuilderExp(AST.Type.MapType type, ExpSem e)
  {
    if (Types.isArray(type))
    {
      IntType type1 = (IntType)type.type1;
      ExpSem.Single size = (Context c)->
      {
        int s = translator.toInt(type, type1.ivalue2);
        if (s == Integer.MAX_VALUE)
          translator.runtimeError(type, "size of type is too large");
        return new Value.Int(s+1);
      };
      return ArrayBuilderExp(size, e);
    }
    try
    {
      return apply(e, (Value v)->
      {
        Seq<Value> keys = Values.getValueSeq(type.type1); 
        Value.Map map = new Value.Map();
        while (true)
        {
          Seq.Next<Value> next = keys.get();
          if (next == null) break;
          map.put(next.head, v);
          keys = next.tail;
        }
        return map;
      });
    }
    catch(Exception ex)
    {
      translator.runtimeError(type, ex.getMessage());
      return null;
    }
  }
  public ExpSem MapSelectionExp(AST exp, AST.Type.MapType type, ExpSem e1, ExpSem e2)
  {
    if (Types.isArray(type))
      return apply(e1, e2, (Value v1, Value v2)->
      {
        Value.Array array = (Value.Array)v1;
        Value.Int index = (Value.Int)v2;
        int index0 = index.getValue();
        if (index0 < 0 || index0 >= array.size())
          translator.runtimeError(exp, "array index " + index0 + " out of bounds");
        return array.get(index.getValue());
      }); 
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Map map = (Value.Map)v1;
      if (map.get(v2) == null)
        translator.runtimeError(exp, "map key " + v2 + " out of domain"); 
      return map.get(v2);
    });
  }
  public ExpSem MapUpdateExp(AST exp, 
    AST.Type.MapType type, AST.Type etype,
    ExpSem e1, ExpSem e2, ExpSem e3)
  {
    AST.Type ctype = type.type2;
    if (Types.isArray(type))
    {
      if (etype == null || TypeChecker.matchesStrong(etype, ctype))
        return apply(e1, e2, e3, (Value v1, Value v2, Value v3)->
        {
          Value.Array array = (Value.Array)v1;
          Value.Int index = (Value.Int)v2;
          Value.Array array0 = new Value.Array(array);
          int index0 = index.getValue();
          if (index0 < 0 || index0 >= array0.size())
            translator.runtimeError(exp, "array index " + index0 + " out of bounds");
          array0.set(index0, v3);
          return array0;
        }); 
      else
        return apply(e1, e2, e3, (Value v1, Value v2, Value v3)->
        {
          translator.checkConstraints(exp, v3, etype, ctype);
          Value.Array array = (Value.Array)v1;
          Value.Int index = (Value.Int)v2;
          Value.Array array0 = new Value.Array(array);
          int index0 = index.getValue();
          if (index0 < 0 || index0 >= array0.size())
            translator.runtimeError(exp, "array index " + index0 + " out of bounds");
          array0.set(index0, v3);
          return array0;
        }); 
    }    
    else
    {
      if (etype == null || TypeChecker.matchesStrong(etype, ctype))
        return apply(e1, e2, e3, (Value v1, Value v2, Value v3)->
        {
          Value.Map map = (Value.Map)v1;
          if (map.get(v2) == null)
            translator.runtimeError(exp, "map key " + v2 + " out of domain"); 
          Value.Map map0 = new Value.Map(map);
          map0.put(v2, v3);
          return map0;
        });
      else
        return apply(e1, e2, e3, (Value v1, Value v2, Value v3)->
        {
          translator.checkConstraints(exp, v3, etype, ctype);
          Value.Map map = (Value.Map)v1;
          if (map.get(v2) == null)
            translator.runtimeError(exp, "map key " + v2 + " out of domain");          
          Value.Map map0 = new Value.Map(map);
          map0.put(v2, v3);
          return map0;
        });   
    }
  }  
  public ExpSem MatchExp(TypeSymbol tsymbol, Symbol[] symbols, 
    ExpSem e, FunExpSem[] pes)
  {
    Integer[] options = Arrays.stream(symbols)
        .map((Symbol symbol)->
          symbol == null ? -1 : Translator.getOption(tsymbol, symbol))
        .toArray(Integer[]::new);
    if (e instanceof ExpSem.Single &&
        Arrays.stream(pes).allMatch((FunExpSem pe)->pe instanceof FunExpSem.Single))
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (ExpSem.Single)(Context c)->
      {
        Value.Array v0 = (Value.Array)e0.apply(c);
        int index = Translator.getIndex(options, v0.getOption());
        FunExpSem.Single pe = (FunExpSem.Single)pes[index];
        ExpSem.Single pe0 = pe.apply(v0.get());
        return pe0.apply(c);
      };
    }
    else
    {
      ExpSem.Multiple e0 = e.toMultiple();
      FunExpSem.Multiple[] pes0 = 
          Arrays.stream(pes).map((FunExpSem s)->s.toMultiple())
          .toArray(FunExpSem.Multiple[]::new);
      return (ExpSem.Multiple)(Context c)->e0.apply(c).applyJoin((Value v)->
      {
        Value.Array v0 = (Value.Array)v;
        int index = Translator.getIndex(options, v0.getOption());
        FunExpSem.Multiple pe = pes0[index];
        ExpSem.Multiple pe0 = pe.apply(v0.get());
        return pe0.apply(c);
      });
    }
  }
  public ExpSem MinusExp(AST.Expression.MinusExp exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        return new Value.Int(Math.subtractExact(i1.getValue(), i2.getValue()));
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem NegationExp(AST.Expression.NegationExp exp, ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Int i = (Value.Int)v;
      try
      {
        return new Value.Int(Math.subtractExact(0, i.getValue()));
      }
      catch(ArithmeticException e0)
      {
        translator.runtimeError(exp, e0.getMessage());
        return null;
      }
    });
  }
  public ExpSem FactorialExp(AST.Expression.FactorialExp exp, ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Int i = (Value.Int)v;
      try
      {
        long result = Arith.multiProduct(1, i.getValue());
        if (result > Integer.MAX_VALUE)
          throw new ArithmeticException("too large result " + result);
        return new Value.Int((int)result);
      }
      catch(ArithmeticException e0)
      {
        translator.runtimeError(exp, e0.getMessage());
        return null;
      }
    });
  }
  public ExpSem NotEqualsExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      return new Value.Bool(!v1.equals(v2));
    });
  }
  public ExpSem NotExp(ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Bool b = (Value.Bool)v;
      return new Value.Bool(!b.getValue());
    });
  }
  public ExpSem NumberExp(ValueSymbol[] symbols, QvarSem qvar)
  {
    Value zero = new Value.Int(0);
    return quantifiedCombine(qvar, 
        (FunExpSem.Single)(Value[]v)->(Context c)->zero, ()->zero,
        (Value r, Value v)->
    {
      Value.Int r0 = (Value.Int)r;
      return new Value.Int(1+r0.getValue());
    });
  }
  public ExpSem NumberLiteralExp(AST.Decimal d)
  {
    Value value = new Value.Int(d.getInteger());
    return (ExpSem.Single)(Context c)->value;
  }
  public ExpSem OrExp(ExpSem e1, ExpSem e2)
  {
    {
      return ifThenElse(e1, (ExpSem.Single)(Context c)->new Value.Bool(true), e2);
    }
  }
  public ExpSem PlusExp(AST.Expression.PlusExp exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        return new Value.Int(Math.addExact(i1.getValue(), i2.getValue()));
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem PlusExpMult(AST.Expression.PlusExpMult exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        long result = Arith.multiSum(i1.getValue(), i2.getValue());
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
          throw new ArithmeticException("too large/small result " + result);
        return new Value.Int((int)result);
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem PowerExp(AST.Expression.PowerExp exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        if (i1.getValue() < 0)
          throw new ArithmeticException("negative base " + i1.getValue());
        if (i2.getValue() < 0)
          throw new ArithmeticException("negative exponent " + i2.getValue());
        long result = Arith.power(i1.getValue(), i2.getValue());
        if (result > Integer.MAX_VALUE)
          throw new ArithmeticException("too large result " + result);
        return new Value.Int((int)result);
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem PowersetExp(ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Set s = (Value.Set)v;
      return new Value.Set(Values.getSetList(s));
    });
  }
  public ExpSem Powerset1Exp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Set s = (Value.Set)v1;
      Value.Int i = (Value.Int)v2;
      return new Value.Set(Values.getSetList(s, i.getValue(), i.getValue()));
    });
  }
  public ExpSem Powerset2Exp(ExpSem e1, ExpSem e2, ExpSem e3)
  {
    return apply(e1, e2, e3, (Value v1, Value v2, Value v3)->
    {
      Value.Set s = (Value.Set)v1;
      Value.Int i = (Value.Int)v2;
      Value.Int j = (Value.Int)v3;
      return new Value.Set(Values.getSetList(s, i.getValue(), j.getValue()));
    });
  }
  public ExpSem PrintExp(String string, ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value[] vs = new Value[] { v };
      print(string, vs, 1);
      return v;
    });
  }
  public ExpSem PrintInExp(String string, ExpSem[] es, ExpSem e)
  {
    int n = es.length;
    ExpSem[] es0 = new ExpSem[n+1];
    for (int i=0; i<n; i++) es0[i] = es[i];
    es0[n] = e;
    return apply(es0, (Argument a)->
    {
      print(string, a.values, n);
      return a.values[n];
    });
  }
  public ExpSem CheckExp(FunctionSymbol fun, ExpSem e)
  {
    if (e == null)
    {
      return (ExpSem.Single)(Context c)->new Value.Bool(Main.execute(fun));
    }
    return apply(e, (Value v)->
    {
      Value.Set set = (Value.Set)v;
      Seq<Value> values = Seq.list(new ArrayList<Value>(set));
      return new Value.Bool(Main.execute(fun, values, set.size()));
    });
  }
  public ExpSem RecApplicationExp(TypeSymbol tsymbol, FunctionSymbol symbol, 
    ExpSem[] es)
  {
    int option = Translator.getOption(tsymbol, symbol);
    return apply(es, (Argument a)-> new Value.Array(option, a.values));
  }
  public ExpSem RecIdentifierExp(TypeSymbol tsymbol, ValueSymbol symbol)
  {
    int option = Translator.getOption(tsymbol, symbol);
    Value value = new Value.Array(option, new Value[]{ });
    return (ExpSem.Single)(Context c)->value;
  }
  public ExpSem RecordExp(ExpSem[] es)
  {
    return apply(es, (Argument a) -> new Value.Array(a.values));
  }
  public ExpSem RecordSelectionExp(AST.Type.RecordType type, 
    ExpSem e, ValueSymbol s)
  {
    final int index = Translator.getIndex(type, s);
    return apply(e, (Value v)->
    {
      Value.Array array = (Value.Array)v;
      return array.get(index);
    });
  }
  public ExpSem RecordUpdateExp(AST exp,
    AST.Type.RecordType type, AST.Type etype,
    ExpSem e1, ValueSymbol s, ExpSem e2)
  {
    final int index = Translator.getIndex(type, s);
    ValueSymbol symbol = (ValueSymbol)type.symbols[index].ident.getSymbol();
    AST.Type ctype = symbol.type;
    if (etype == null || TypeChecker.matchesStrong(etype, ctype))
      return apply(e1, e2, (Value v1, Value v2)->
      {
        Value.Array array = (Value.Array)v1;
        Value.Array array0 = new Value.Array(array);
        array0.set(index, v2);
        return array0;
      }); 
    else
      return apply(e1, e2, (Value v1, Value v2)->
      {
        translator.checkConstraints(exp, v2, etype, ctype);
        Value.Array array = (Value.Array)v1;
        Value.Array array0 = new Value.Array(array);
        array0.set(index, v2);
        return array0;
      });      
  }
  public ExpSem RemainderExp(AST.Expression.RemainderExp exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        return new Value.Int(i1.getValue()%i2.getValue());
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem SetBuilderExp(ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    return quantifiedCombine(qvar, bind(symbols, exp), ()->new Value.Set(),
        (Value r, Value v)->
    {
      // intermediate set is destructively updated
      Value.Set r0 = (Value.Set)r;
      r0.add(v);
      return r0;
    });
  }
  public ExpSem SumExp(AST.Expression.SumExp exp0,
    ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    return quantifiedCombine(qvar, bind(symbols, exp), ()->new Value.Int(0),
        (Value r, Value v)->
    {
      Value.Int r0 = (Value.Int)r;
      Value.Int v0 = (Value.Int)v;
      try
      {
        return new Value.Int(Math.addExact(r0.getValue(), v0.getValue()));
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp0, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem ProductExp(AST.Expression.ProductExp exp0,
    ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    return quantifiedCombine(qvar, bind(symbols, exp), ()->new Value.Int(1),
        (Value r, Value v)->
    {
      Value.Int r0 = (Value.Int)r;
      Value.Int v0 = (Value.Int)v;
      try
      {
        return new Value.Int(Math.multiplyExact(r0.getValue(), v0.getValue()));
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp0, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem MinExp(AST.Expression.MinExp exp0,
    ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    ExpSem e = quantifiedCombine(qvar, bind(symbols, exp), ()->null,
        (Value r, Value v)->
    {
      if (r == null) return v;
      Value.Int r0 = (Value.Int)r;
      Value.Int v0 = (Value.Int)v;
      if (r0.getValue() <= v0.getValue()) 
        return r0;
      else
        return v0;
    });
    return apply(e, (Value v)->
    {
      if (v == null)
        translator.runtimeError(exp0, "quantification range is empty");
      return v;
    });
  }
  public ExpSem MaxExp(AST.Expression.MaxExp exp0,
    ValueSymbol[] symbols, QvarSem qvar, ExpSem exp)
  {
    ExpSem e = quantifiedCombine(qvar, bind(symbols, exp), ()->null,
        (Value r, Value v)->
    {
      if (r == null) return v;
      Value.Int r0 = (Value.Int)r;
      Value.Int v0 = (Value.Int)v;
      if (r0.getValue() >= v0.getValue()) 
        return r0;
      else
        return v0;
    });
    return apply(e, (Value v)->
    {
      if (v == null)
        translator.runtimeError(exp0, "quantification range is empty");
      return v;
    });
  }
  public ExpSem SetSizeExp(ExpSem e)
  {
    return apply(e, (Value v)->
    {
      Value.Set s = (Value.Set)v;
      return new Value.Int(s.size());
    });
  }
  public ExpSem SubsetExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Set s1 = (Value.Set)v1;
      Value.Set s2 = (Value.Set)v2;
      return new Value.Bool(s2.containsAll(s1));
    });
  }
  public ExpSem TimesExp(AST.Expression.TimesExp exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        return new Value.Int(Math.multiplyExact(i1.getValue(), i2.getValue()));
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem TimesExpMult(AST.Expression.TimesExpMult exp, ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Int i1 = (Value.Int)v1;
      Value.Int i2 = (Value.Int)v2;
      try
      {
        long result = Arith.multiProduct(i1.getValue(), i2.getValue());
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
          throw new ArithmeticException("too large/small result " + result);
        return new Value.Int((int)result);
      }
      catch(ArithmeticException e)
      {
        translator.runtimeError(exp, e.getMessage());
        return null;
      }
    });
  }
  public ExpSem TrueExp()
  {
    Value value = new Value.Bool(true);
    return (ExpSem.Single)(Context c)-> value;
  }
  public ExpSem TupleExp(ExpSem[] es)
  {
    return apply(es, (Argument a) -> new Value.Array(a.values));
  }
  public ExpSem TupleSelectionExp(ExpSem e, int index)
  {
    return apply(e, (Value v)->
    {
      Value.Array array = (Value.Array)v;
      return array.get(index-1);
    });
  }
  public ExpSem TupleUpdateExp(AST exp, 
    AST.Type.TupleType type, AST.Type etype,
    ExpSem e1, int index, ExpSem e2)
  {
    AST.Type ctype = type.types[index-1];
    if (etype == null || TypeChecker.matchesStrong(etype, ctype))
      return apply(e1, e2, (Value v1, Value v2)->
      {
        Value.Array array = (Value.Array)v1;
        Value.Array array0 = new Value.Array(array);
        array0.set(index-1, v2);
        return array0;
      }); 
    else
      return apply(e1, e2, (Value v1, Value v2)->
      {
        translator.checkConstraints(exp, v2, etype, ctype);
        Value.Array array = (Value.Array)v1;
        Value.Array array0 = new Value.Array(array);
        array0.set(index-1, v2);
        return array0;
      }); 
  }
  public ExpSem UnionExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Set s1 = (Value.Set)v1;
      Value.Set s2 = (Value.Set)v2;
      Value.Set result = new Value.Set(s1);
      result.addAll(s2);
      return result;
    });
  }
  public ExpSem UnitExp()
  {
    Value value = new Value.Unit();
    return (ExpSem.Single)(Context c)-> value;
  }
  public ExpSem WithoutExp(ExpSem e1, ExpSem e2)
  {
    return apply(e1, e2, (Value v1, Value v2)->
    {
      Value.Set s1 = (Value.Set)v1;
      Value.Set s2 = (Value.Set)v2;
      Value.Set result = new Value.Set(s1);
      result.removeAll(s2);
      return result;
    });
  }
  
  // -------------------------------------------------------------------------
  // 
  // Binders
  //
  // -------------------------------------------------------------------------
  
  public BinderSem Binder(ValueSymbol symbol, ExpSem e, AST.Expression exp)
  {
    int slot = Translator.toSlot(symbol);
    String name = Translator.toName(symbol);
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (BinderSem.Single)(Context c)->
      {
        Value v = e0.apply(c);
        return c.set(slot, v, name);
      };
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      return (BinderSem.Multiple)(Context c)->
      {
        return e0.apply(c).apply((Value v)->
          c.set(slot, v, name));
      };
    }
  }
  
  public BinderSem Binder(ValueSymbol[] symbol, ExpSem[] e, AST.Expression[] exp)
  {
    int n = symbol.length;
    int[] slot = Translator.toSlots(symbol);
    String[] names = Translator.toNames(symbol);
    boolean single = true;
    for (ExpSem e0 : e)
    {
      if (e0 instanceof ExpSem.Multiple) { single = false; break; }
    }
    if (single)
    {
      return (BinderSem.Single)(Context c)->
      {
        Context c0 = new Context(c);
        for (int i=0; i<n; i++)
        {
          ExpSem.Single e0 = (ExpSem.Single)e[i];
          c0 = c0.set(slot[i], e0.apply(c), names[i]);
        }
        return c0;
      };
    }
    else
    {
      return (BinderSem.Multiple)(Context c)->
      {
        @SuppressWarnings("unchecked")
        Seq<Value>[] seq = new Seq[n];
        for (int i=0; i<n; i++)
        {
          ExpSem.Multiple e0 = e[i].toMultiple();
          seq[i] = e0.apply(c);
        }
        return Types.getCombinations(seq).apply((Value[] v)->
        {
          Context c0 = new Context(c);
          for (int i=0; i<n; i++)
            c0 = c0.set(slot[i], v[i], names[i]);
          return c;
        });
      };
    }
  }
  
  // -------------------------------------------------------------------------
  // 
  // Pattern expressions
  //
  // -------------------------------------------------------------------------
  
  public FunExpSem DefaultPatternExp(ExpSem e)
  {
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (FunExpSem.Single)(Value[] v) -> e0;
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      return (FunExpSem.Multiple)(Value[] v) -> e0;
    }
  }
  public FunExpSem IdentifierPatternExp(ExpSem e)
  {
    return DefaultPatternExp(e);
  }
  public FunExpSem ApplicationPatternExp(ValueSymbol[] params, ExpSem e)
  {
    return bind(params, e);
  }
  
  // -------------------------------------------------------------------------
  // 
  // Quantified variables
  //
  // -------------------------------------------------------------------------
  
  public QvarSem QuantifiedVariable(ValueSymbol[] symbols, QvarSem q, ExpSem e)
  {
    FunExpSem e0 = bind(symbols, e);
    if (q instanceof QvarSem.Single && e0 instanceof FunExpSem.Single)
    {
      QvarSem.Single q0 = (QvarSem.Single)q;
      FunExpSem.Single e1 = (FunExpSem.Single)e0;
      return (QvarSem.Single)(Context c)->
        q0.apply(c).filter((Value[] v)->
        {
          Value.Bool b = (Value.Bool)e1.apply(v).apply(c);
          return b.getValue();
        });
    }
    else
    {
      QvarSem.Multiple q0 = q.toMultiple();
      FunExpSem.Multiple e1 = e0.toMultiple();
      return (QvarSem.Multiple)(Context c)-> 
      q0.apply(c).apply((Seq<Value[]> values)->
      values.filter((Value[]v)-> 
      {
        Seq<Value> bs = e1.apply(v).apply(c);
        while (true)
        {
          Seq.Next<Value> next = bs.get();
          if (next == null) return false;
          Value.Bool b = (Value.Bool)next.head;
          if (b.getValue()) return true;
          bs = next.tail;
        }
      }));
    }
  }
  
  public QvarSem QuantifiedVariableCore(ValueSymbol[] symbols, QvarCoreSem[] q)
  {
    int[] slots = Translator.toSlots(symbols);
    String[] names = Translator.toNames(symbols);
    QvarSem q0 = 
        (QvarSem.Single)(Context c)->Seq.cons(new Value[]{}, Seq.empty());  
    for (int i=q.length-1; i>=0; i--)
    {
      int slot = slots[i];
      String name = names[i];
      int n = q.length-i;
      if (q0 instanceof QvarSem.Single && q[i] instanceof QvarCoreSem.Single)
      {
        QvarSem.Single q1 = (QvarSem.Single)q0;
        QvarCoreSem.Single q2 = (QvarCoreSem.Single)q[i];
        q0 = (QvarSem.Single)(Context c)->
        q2.apply(c).applyJoin((Value v)->
        {
          Context c0 = c.set(slot, v, name);
          return q1.apply(c0).apply((Value[] v0)->
          {
            Value[] v1 = new Value[n];
            v1[0] = v;
            for (int j=1; j<n; j++) v1[j] = v0[j-1];
            return v1;
          });
        });
      }
      else
      {
        QvarSem.Multiple q1 = q0.toMultiple();
        QvarCoreSem.Multiple q2 = q[i].toMultiple();
        q0 = (QvarSem.Multiple)(Context c)->
        q2.apply(c).applyJoin((Seq<Value> vs)->
        vs.applyJoin((Value v)->
        {
          Context c0 = c.set(slot, v, name);
          return q1.apply(c0).apply((Seq<Value[]> vs0)->
          vs0.apply((Value[] v0)->
          {
            Value[] v1 = new Value[n];
            v1[0] = v;
            for (int j=1; j<n; j++) v1[j] = v0[j-1];
            return v1;
          }));
        }));
      }
    } 
    return q0;
  }
  
  public QvarCoreSem IdentifierSetQuantifiedVar(ExpSem e)
  {
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (QvarCoreSem.Single)(Context c)->
      {
        Value.Set set = (Value.Set)e0.apply(c);
        return Seq.list(new ArrayList<Value>(set));
      };
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      return (QvarCoreSem.Multiple)(Context c)->e0.apply(c).apply((Value v)->
      {
        Value.Set set = (Value.Set)v;
        return Seq.list(new ArrayList<Value>(set));
      });
    }
  }
  
  public QvarCoreSem IdentifierTypeQuantifiedVar(
    AST.QuantifiedVariableCore.IdentifierTypeQuantifiedVar qvar,
    AST.Type type)
  {
    return (QvarCoreSem.Single)(Context c)->
    {
      try
      {
        return Values.getValueSeq(type);
      }
      catch(Exception e)
      {
        translator.runtimeError(qvar, e.getMessage());
        return null;
      }
    };
  }
      
  // -------------------------------------------------------------------------
  // 
  // Application of function to expression semantics
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Apply a function to the semantics of an expression 
   * @param exp the expression
   * @param fun the function
   * @return all fun(v) where v is a value of exp
   **************************************************************************/
  private static ExpSem apply(ExpSem exp, Function<Value,Value> fun)
  {
    if (exp instanceof ExpSem.Single)
    {
      ExpSem.Single e = (ExpSem.Single)exp;
      return (ExpSem.Single)(Context c)->fun.apply(e.apply(c));
    }
    else
    {
      ExpSem.Multiple e = (ExpSem.Multiple)exp;
      return (ExpSem.Multiple)(Context c)-> e.apply(c).apply(fun);
    }
  }
  
  /***************************************************************************
   * Apply a function to the semantics of two expressions.
   * @param exp1 the first expression
   * @param exp2 the second one
   * @param fun the function
   * @return all fun(v1,v2) where v1 and v2 are values of exp1 and exp2
   **************************************************************************/
  private static ExpSem apply(ExpSem exp1, ExpSem exp2,
    BiFunction<Value,Value,Value> fun)
  {
    if (exp1 instanceof ExpSem.Single)
    {
      ExpSem.Single e1 = (ExpSem.Single)exp1;
      if (exp2 instanceof ExpSem.Single)
      {
        ExpSem.Single e2 = (ExpSem.Single)exp2;
        return (ExpSem.Single)(Context c)->
        {
          Value v1 = e1.apply(c);
          Value v2 = e2.apply(c);
          return fun.apply(v1, v2);
        };
      }
      else
      {
        ExpSem.Multiple e2 = (ExpSem.Multiple)exp2;
        return (ExpSem.Multiple)(Context c)->
        {
          Value v1 = e1.apply(c);
          Seq<Value> s2 = e2.apply(c);
          return s2.apply((Value v2)-> fun.apply(v1, v2));
        };
      }
    }
    else
    {
      ExpSem.Multiple e1 = (ExpSem.Multiple)exp1;
      if (exp2 instanceof ExpSem.Single)
      {
        ExpSem.Single e2 = (ExpSem.Single)exp2;
        return (ExpSem.Multiple)(Context c)->
        {
          Seq<Value> s1 = e1.apply(c);
          Value v2 = e2.apply(c);
          return s1.apply((Value v1)-> fun.apply(v1, v2));
        };
      }
      else
      {
        ExpSem.Multiple e2 = (ExpSem.Multiple)exp2;
        return (ExpSem.Multiple)(Context c)->
        {
          Seq<Value> s1 = e1.apply(c);
          Seq<Value> s2 = e2.apply(c);
          return s1.applyJoin((Value v1)->
            s2.apply((Value v2)-> fun.apply(v1, v2)));
        };
      }
    }
  }

  // not a standard functional interface
  private interface TriFunction<T1,T2,T3,R>
  {
    public R apply(T1 v1, T2 v2, T3 v3);
  }
  
  /***************************************************************************
   * Apply a function to the semantics of two expressions.
   * @param exp1 the first expression
   * @param exp2 the second one
   * @param exp3 the third one
   * @param fun the function
   * @return all fun(v1,v2,v3) where v1,v2,v3 are values of exp1,exp2,expr
   **************************************************************************/
  private static ExpSem apply(ExpSem exp1, ExpSem exp2, ExpSem exp3,
    TriFunction<Value,Value,Value,Value> fun)
  {
    if (exp1 instanceof ExpSem.Single &&
        exp2 instanceof ExpSem.Single &&
        exp3 instanceof ExpSem.Single)
    {
      ExpSem.Single e1 = (ExpSem.Single)exp1;
      ExpSem.Single e2 = (ExpSem.Single)exp2;
      ExpSem.Single e3 = (ExpSem.Single)exp3;
      return (ExpSem.Single)(Context c)->
      {
        Value v1 = e1.apply(c);
        Value v2 = e2.apply(c);
        Value v3 = e3.apply(c);
        return fun.apply(v1, v2, v3);
      };
    }
    else
    {
      ExpSem.Multiple e1 = exp1.toMultiple();
      ExpSem.Multiple e2 = exp2.toMultiple();
      ExpSem.Multiple e3 = exp3.toMultiple();
      return (ExpSem.Multiple)(Context c)->
      {
        Seq<Value> s1 = e1.apply(c);
        Seq<Value> s2 = e2.apply(c);
        Seq<Value> s3 = e3.apply(c);
        return 
            s1.applyJoin((Value v1)->
            s2.applyJoin((Value v2)->
            s3.apply((Value v3)->fun.apply(v1,v2,v3))));
      };
    }
  }
  
  /****************************************************************************
   * Create stream of all combine(v1,..,vn) where (v1,..,vn) is a combination
   * of values of the given expressions expressions.
   * @param es the expressions
   * @param combine the combination functions
   * @return the corresponding expression semantics (potentially deterministic.
   ***************************************************************************/
  public static ExpSem apply(ExpSem[] es, Function<Argument,Value> combine)
  {
    if (Arrays.stream(es).allMatch((ExpSem e)-> (e instanceof ExpSem.Single)))
      return (ExpSem.Single)(Context c)->
    {
      int n = es.length;
      Value[] values = new Value[n];
      for (int i=0; i<n; i++) 
      {
        ExpSem.Single e0 = (ExpSem.Single)es[i];
        values[i] = e0.apply(c);
      }
      return combine.apply(new Argument(values, c.getMeasures()));
    };
    return applyCore(es, combine);
  }
  
  /****************************************************************************
   * Create stream of all combine(v1,..,vn) where (v1,..,vn) is a combination
   * of values of the given expressions.
   * @param es the expressions
   * @param combine the combination functions
   * @return the corresponding expression semantics (non-deterministic).
   ***************************************************************************/
  private static ExpSem.Multiple applyCore(ExpSem[] es, Function<Argument,Value> combine)
  {  
    return (ExpSem.Multiple)(Context c)->
    {
      int n = es.length;
      @SuppressWarnings("unchecked")
      Seq<Value>[] seq = new Seq[n];
      for (int i=0; i<n; i++)
      {
        ExpSem.Multiple e = es[i].toMultiple();
        seq[i] = e.apply(c);
      }
      MeasureMap m = c.getMeasures();
      return Types.getCombinations(seq).apply((Value[] v)->combine.apply(new Argument(v, m)));
    };
  }

  /****************************************************************************
   * Create stream of all combine(v1,..,vn) where (v1,..,vn) is a combination
   * of values of the given expressions.
   * @param es the expressions
   * @param combine the combination functions
   * @return the corresponding expression semantics (potentially deterministic)
   ***************************************************************************/
  public static ExpSem applyMultiple(ExpSem[] es, 
    Function<Argument,Seq<Value>> combine)
  {
    if (Arrays.stream(es).allMatch((ExpSem e)-> (e instanceof ExpSem.Single)))
      return (ExpSem.Multiple)(Context c)->
    {
      int n = es.length;
      Value[] values = new Value[n];
      for (int i=0; i<n; i++) 
      {
        ExpSem.Single e0 = (ExpSem.Single)es[i];
        values[i] = e0.apply(c);
      }
      return combine.apply(new Argument(values, c.getMeasures()));
    };
    return applyCoreMultiple(es, combine);
  }
  
  /****************************************************************************
   * Create stream of all combine(v1,..,vn) where (v1,..,vn) is a combination
   * of values of the given expressions.
   * @param es the expressions
   * @param combine the combination functions
   * @return the corresponding expression semantics (non-deterministic).
   ***************************************************************************/
  private static ExpSem.Multiple applyCoreMultiple(ExpSem[] es, 
    Function<Argument,Seq<Value>> combine)
  {  
    return (ExpSem.Multiple)(Context c)->
    {
      int n = es.length;
      @SuppressWarnings("unchecked")
      Seq<Value>[] seq = new Seq[n];
      for (int i=0; i<n; i++)
      {
        ExpSem.Multiple e = es[i].toMultiple();
        seq[i] = e.apply(c);
      }
      MeasureMap m = c.getMeasures();
      return Types.getCombinations(seq).applyJoin((Value[] v)->combine.apply(new Argument(v, m)));
    };
  }
  
  // -------------------------------------------------------------------------
  // 
  // Special expression combinations
  //
  // -------------------------------------------------------------------------  
  
  /***************************************************************************
   * Create semantics of conditional choice of two results.
   * @param ifExp the condition
   * @param thenExp the choice, if the condition is true.
   * @param elseExp the choice, if the condition is false.
   * @return the conditional semantics.
   **************************************************************************/
  private static ExpSem ifThenElse(ExpSem ifExp, ExpSem thenExp, ExpSem elseExp)
  {
    if (ifExp instanceof ExpSem.Single)
    {
      ExpSem.Single ifExp0 = (ExpSem.Single)ifExp;
      if (thenExp instanceof ExpSem.Single && elseExp instanceof ExpSem.Single)
      {
        ExpSem.Single thenExp0 = (ExpSem.Single)thenExp;
        ExpSem.Single elseExp0 = (ExpSem.Single)elseExp;
        return (ExpSem.Single)(Context c)->
        {
          Value.Bool b = (Value.Bool)ifExp0.apply(c);
          if (b.getValue())
            return thenExp0.apply(c);
          else
            return elseExp0.apply(c);
        };
      }
      else
      {
        ExpSem.Multiple thenExp0 = thenExp.toMultiple();
        ExpSem.Multiple elseExp0 = elseExp.toMultiple();
        return (ExpSem.Multiple)(Context c)->
        {
          Value.Bool b = (Value.Bool)ifExp0.apply(c);
          if (b.getValue())
            return thenExp0.apply(c);
          else
            return elseExp0.apply(c);
        };
      }
    }
    else
    {
      ExpSem.Multiple ifExp0 = (ExpSem.Multiple)ifExp;
      ExpSem.Multiple thenExp0 = thenExp.toMultiple();
      ExpSem.Multiple elseExp0 = elseExp.toMultiple();
      return (ExpSem.Multiple)(Context c)->
      {
        Seq<Value> cond = ifExp0.apply(c);
        return cond.applyJoin((Value v)->
        {
          Value.Bool b = (Value.Bool)v;
          return b.getValue() ? 
              thenExp0.apply(c) : 
              elseExp0.apply(c);
        });
      };
    }
  }
  
  /***************************************************************************
   * Create a function semantics that accepts values for the given
   * symbols and returns the value of the expression in the current
   * context where the symbols receive the given values.
   * @param symbols the symbols
   * @param e the expression
   * @return the function semantics
   **************************************************************************/
  private static FunExpSem bind(ValueSymbol[] symbols, ExpSem e)
  {
    int[] slots = Translator.toSlots(symbols);
    String[] names = Translator.toNames(symbols);
    if (e instanceof ExpSem.Single) 
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (FunExpSem.Single)(Value[] v)-> (ExpSem.Single)(Context c)->
        {
          Context c0 = c.set(slots, v, names);
          return e0.apply(c0);
        };
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      return (FunExpSem.Multiple)(Value[] v)-> (ExpSem.Multiple)(Context c)->
        {
          Context c0 = c.set(slots, v, names);
          return e0.apply(c0);
        };
    }
  }
  
  /****************************************************************************
   * Determine the semantics of an expression from the choice of a variable.
   * @param exp the expression that makes the choice.
   * @param qvar the quantified variables to be chosen from.
   * @param none its application to the original context
   *             determines the result, if no choice is possible.
   *             (if null, the execution is aborted)
   * @param some its application to the context updated by the choice
   *             determines the result.
   * @return the resulting semantics
   ***************************************************************************/
  private ExpSem choose(AST.Expression exp, QvarSem qvar, ExpSem none, FunExpSem some)
  {
    if (qvar instanceof QvarSem.Single &&
        (none == null || none instanceof ExpSem.Single) &&
        some instanceof FunExpSem.Single)
    {
      QvarSem.Single qvar0 = (QvarSem.Single)qvar;
      ExpSem.Single none0 = (ExpSem.Single)none;
      FunExpSem.Single some0 = (FunExpSem.Single)some;
      if (translator.nondeterministic)
        return (ExpSem.Multiple)(Context c)-> 
      {
        Seq<Value[]> v = qvar0.apply(c);
        // if (v.get() == null)
        // {
        //   if (none0 == null)
        //    return Seq.empty();
        //   else
        //    return Seq.cons(none0.apply(c), Seq.empty());
        // }
        // return v.apply((Value[] v1)->some0.apply(v1).apply(c));
        if (none0 == null)
          return v.apply((Value[] v1)->some0.apply(v1).apply(c));
        else
          return Seq.append(v.apply((Value[] v1)->some0.apply(v1).apply(c)),
              Seq.supplier(()->new Seq.Next<Value>(none0.apply(c), Seq.empty())));
      };
      else
        return (ExpSem.Single)(Context c)-> 
      {
        Main.performChoice();
        Seq<Value[]> v = qvar0.apply(c);
        Seq.Next<Value[]> next = v.get();
        if (next == null) 
        {
          if (none0 == null)
          {
            translator.runtimeError(exp, "no choice possible");
            return null;
          }
          else
            return none0.apply(c);
        }
        else
          return some0.apply(next.head).apply(c);
      };
    }
    else
    {
      QvarSem.Multiple qvar0 = qvar.toMultiple();
      FunExpSem.Multiple some0 = some.toMultiple();
      ExpSem.Multiple none0 = none == null ? null : none.toMultiple();
      return (ExpSem.Multiple)(Context c)->
      {
        Seq<Seq<Value[]>> result = qvar0.apply(c);
        return result.applyJoin((Seq<Value[]> v)->
        {
          if (v.get() == null) 
          {
            if (none0 == null)
              return Seq.empty();
            else
              return none0.apply(c);
          }
          return v.applyJoin((Value[] v1)->some0.apply(v1).apply(c));
        });
      };
    }
  }
  
  /****************************************************************************
   * Search for an instance of exp that has the given value
   * @param value the value that the expression shall have
   * @param qvar the quantified variables
   * @param body the body expression
   * @return value, if some instance has it, otherwise its negation
   ***************************************************************************/
  private static ExpSem quantifiedSearch(QvarSem qvar, 
    FunExpSem body, boolean value)
  {
    
    if (qvar instanceof QvarSem.Single && body instanceof FunExpSem.Single)
    {
      QvarSem.Single qvar0 = (QvarSem.Single)qvar;
      FunExpSem.Single body0 = (FunExpSem.Single)body;
      return (ExpSem.Single)(Context c)->
      {
        Seq<Value[]> vs = qvar0.apply(c);
        while (true)
        {
          Seq.Next<Value[]> next = vs.get();
          if (next == null) break;
          vs = next.tail;
          Value[] v  = next.head;
          Value.Bool b = (Value.Bool)body0.apply(v).apply(c);
          if (b.getValue() == value) return b;
        }
        return new Value.Bool(!value);
      };
    }
    else
    {
      QvarSem.Multiple qvar0 = qvar.toMultiple();
      FunExpSem.Multiple body0 = body.toMultiple();
      return (ExpSem.Multiple)(Context c)->
      {
        Seq<Seq<Value[]>> seq = qvar0.apply(c);
        return seq.applyJoin((Seq<Value[]> vs)-> 
        {
          boolean some = false;
          while (true)
          {
            Seq.Next<Value[]> next = vs.get();
            if (next == null) break;
            vs = next.tail;
            Seq<Value> values = body0.apply(next.head).apply(c);
            boolean all = true;
            while (true)
            {
              Seq.Next<Value> next0 = values.get();
              if (next0 == null) break;
              values = next0.tail;
              Value.Bool head = (Value.Bool)next0.head;
              if (head.getValue() == value)
                some = true;
              else
                all = false;
            }
            if (all) return Seq.cons(new Value.Bool(value), Seq.empty());
          }
          Seq<Value> notall = Seq.cons(new Value.Bool(!value), Seq.empty());
          if (some)
            return Seq.cons(new Value.Bool(value), notall);
          else
            return notall;
        });
      };
    }
  }
  
  /****************************************************************************
   * Combine values derived from quantification.
   * @param qvar the quantified variables
   * @param body the body expression
   * @param identity the base value
   * @param combine the combination function
   * @return the semantics.
   ***************************************************************************/
  private static ExpSem quantifiedCombine(QvarSem qvar, 
    FunExpSem body, Supplier<Value> identity, BiFunction<Value,Value,Value> combine)
  {
    if (qvar instanceof QvarSem.Single && body instanceof FunExpSem.Single)
    {
      QvarSem.Single qvar0 = (QvarSem.Single)qvar;
      FunExpSem.Single body0 = (FunExpSem.Single)body;
      return (ExpSem.Single)(Context c)->
      {
        Seq<Value[]> vs = qvar0.apply(c);
        Value result = identity.get();
        while (true)
        {
          Seq.Next<Value[]> next = vs.get();
          if (next == null) return result;
          vs = next.tail;
          Value[] v = next.head;
          Value r = body0.apply(v).apply(c);
          result = combine.apply(result, r);
        }
      };
    }
    else
    {
      QvarSem.Multiple qvar0 = qvar.toMultiple();
      FunExpSem.Multiple body0 = body.toMultiple();
      return (ExpSem.Multiple)(Context c)->
      {
        Seq<Seq<Value[]>> seq = qvar0.apply(c);
        return seq.applyJoin((Seq<Value[]> vs)-> 
        {
          List<ExpSem> es = new ArrayList<ExpSem>();
          while (true)
          {
            Seq.Next<Value[]> next = vs.get();
            if (next == null) break;
            vs = next.tail;
            Value[] v = next.head;
            es.add(body0.apply(v));
          }
          ExpSem[] es0 = es.toArray(new ExpSem[es.size()]);
          ExpSem.Multiple all = applyCore(es0, (Argument a)-> 
          {
            Value result = identity.get();
            for (Value r : a.values)
              result = combine.apply(result, r);
            return result;
          });
          return all.apply(c);
        });
      };
    }
  }
  
  // -------------------------------------------------------------------------
  //
  // Other auxiliaries
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Print values according to format string.
   * @param string the format string.
   * @param vs the values.
   * @param n the number of values to be printed.
   **************************************************************************/
  private void print(String string, Value[] vs, int n)
  {
    // if (Main.getSilent()) return v;
    if (string == null)
    {
      for (int i=0; i<n; i++)
      {
        translator.writer.print(vs[i]);
        if (i+1 < n) translator.writer.print(",");
      }
      translator.writer.println();
      return;
    }
    // splits "abc{1}def{2}geh" into ["abc","{1}", "def", "{2}", "geh"] 
    String regex1 = "\\{\\d{1,3}\\}";
    String regex2 = "(?<=(" + regex1 + "))|(?=(" + regex1 + "))";
    String[] tokens = string.substring(1, string.length()-1).split(regex2);
    for (String token : tokens)
    {
      if (!token.startsWith("{")) 
      {
        token = token.replaceAll("\\{", "{");
        token = token.replaceAll("\\}", "}");
        token = token.replaceAll("\\n", "\n");
        translator.writer.print(token);
        continue;
      }
      String literal = token.substring(1, token.length()-1);
      try
      {
        int index = Integer.parseInt(literal);
        if (index < 1 || index > n)
          throw new RuntimeException("invalid value index " + index);
        translator.writer.print(vs[index-1]);
      }
      catch(NumberFormatException ex) { }
    }
    translator.writer.println();
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------