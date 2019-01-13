// ---------------------------------------------------------------------------
// Commands.java
// The denotational semantics of commands.
// $Id: Commands.java,v 1.42 2018/06/06 15:06:21 schreine Exp $
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

import java.util.*;
import java.util.function.*;

import riscal.semantics.Types.*;
import riscal.syntax.AST;
import riscal.types.TypeChecker;
import riscal.types.Environment.Symbol;
import riscal.types.Environment.Symbol.FunctionSymbol;
import riscal.types.Environment.Symbol.TypeSymbol;
import riscal.types.Environment.Symbol.ValueSymbol;
import riscal.util.*;
import riscal.*;

public final class Commands
{
  // the current translator
  private final Translator translator;
  
  /***************************************************************************
   * Create commands translator.
   * @param translator the overall translator.
   **************************************************************************/
  public Commands(Translator translator)
  {
    this.translator = translator;
  }
  
  // -------------------------------------------------------------------------
  //
  // Commands
  //
  // -------------------------------------------------------------------------
  
  public ComSem AssertCommand(AST.Command.AssertCommand com, ExpSem exp)
  {
    return ifThenElse(com.exp, exp, 
        (ComSem.Single)(Context c)->c, 
        (ComSem.Single)(Context c)-> 
        {
          translator.runtimeError(com, "assertion failed");
          return null;
        });
  }
  public ComSem AssignmentCommand(AST.Command command,
    ValueSymbol symbol, ExpSem e, AST.Type type, boolean check)
  {
    int slot = Translator.toSlot(symbol);
    String name = Translator.toName(symbol);
    if (check && TypeChecker.matchesStrong(type, symbol.type))
    {
      return apply(e, (SingleFunComSem.Single)(Value v)->(Context c)->
      {
        Context c0 = c.set(slot, v, name);
        Trace.assignment(c0, command, v);
        return c0;
      });
    }
    else
    {
      return apply(e, (SingleFunComSem.Single)(Value v)->(Context c)->
      {
        translator.checkConstraints(command, v, type, symbol.type);
        Context c0 = c.set(slot, v, name);
        Trace.assignment(c0, command, v);
        return c0;
      });
    }
  }
  public ComSem ChooseCommand(AST.Command.ChooseCommand com, 
    ValueSymbol[] symbols, QvarSem qvar)
  {
    return choose(com, qvar, null, update(com, symbols));
  }
  public ComSem ChooseElseCommand(AST.Command.ChooseElseCommand com,
    ValueSymbol[] symbols, QvarSem qvar,
    ComSem com1, ComSem com2)
  {
    return choose(com, qvar, com2, bind(com, symbols, com1));
  }
  public ComSem ChooseDoCommand(AST.Command.ChooseDoCommand command,
    ValueSymbol[] symbols,
    QvarSem qv, ComSem com, LoopSpecSem[] spec)
  { 
    int[] slots = Translator.toSlots(symbols);
    String[] names = Translator.toNames(symbols);
    List<ContextRelation> invariants = getInvariants(spec);
    List<ContextFunction> variants = getVariants(spec);
    List<AST> cinvariants = getInvariants(command.spec);
    List<AST> cvariants = getVariants(command.spec);
    int allSlot = Translator.toSlot(command.getAllSet());
    if (!translator.nondeterministic)
    {
      QvarSem.Single qv0 = (QvarSem.Single)qv;
      ComSem.Single com0 = (ComSem.Single)com;
      return (ComSem.Single)(Context c)->
      {
        Context oldc = new Context(c);
        List<List<Value[]>> measures = 
            translator.initMeasures(c, variants, cvariants);
        Seq<Value[]> values = qv0.apply(c);
        Value.Set allSet = new Value.Set();
        c.set(allSlot, allSet, TypeChecker.chooseSetName);
        while (true)
        {
          Main.checkStopped();
          Seq.Next<Value[]> next = values.get();
          Main.performChoice();
          if (next == null) break;
          values = next.tail;
          Value[] v = next.head;
          c.set(slots, v, names);
          Trace.choice(c, command, v);
          checkInvariant(invariants, c, oldc, cinvariants);
          c = com0.apply(c);
          measures = translator.checkMeasures(measures, c, variants, cvariants);
          if (v.length == 1)
            allSet.add(v[0]);
          else
            allSet.add(new Value.Array(v));
          c.set(allSlot, allSet, TypeChecker.chooseSetName);
          // determine new list of candidates
          values = qv0.apply(c);
        }
        checkInvariant(invariants, c, oldc, cinvariants);
        return c;
      };
    }
    else
    {
      QvarSem.Multiple qv0 = qv.toMultiple();
      ComSem.Multiple com0 = com.toMultiple();
      return (ComSem.Multiple)(Context c)->
      {
        List<List<Value[]>> measures = 
            translator.initMeasures(c, variants, cvariants);
        Context oldc = c;
        // duplicate context to avoid interference among non-deterministic branches
        return qv0.apply(c).applyJoin((Seq<Value[]> values)->
        {
          Context c0 = new Context(oldc);
          c0.set(allSlot, new Value.Set(), TypeChecker.chooseSetName);
          return cloop(qv0, c0, values, com0, slots, names, allSlot, measures, oldc, 
              invariants, variants, cinvariants, cvariants);
        });
      };
    }
  }
  public ComSem CommandSequence(ComSem[] coms)
  {
    int n = coms.length;
    if (n == 0) return (ComSem.Single)(Context c)->c;
    int i = n-2;
    ComSem com = coms[n-1];
    if (com instanceof ComSem.Single)
    {
      ComSem.Single com0 = (ComSem.Single)com;
      while (i >= 0 && (coms[i] instanceof ComSem.Single))
      {
        ComSem.Single com1 = (ComSem.Single)coms[i];
        final ComSem.Single com2 = com0;
        com0 = (ComSem.Single)(Context c)-> com2.apply(com1.apply(c));
        i--;
      }
      if (i < 0) return com0;
      com = com0;
    }
    // execute every non-deterministic branch in duplicate context "new Context(c)"
    // to avoid interference of context updates
    ComSem.Multiple com1 = com.toMultiple();
    while (i >= 0)
    {
      ComSem com2 = coms[i];
      if (com2 instanceof ComSem.Single)
      {
        ComSem.Single com3 = (ComSem.Single)com2;
        final ComSem.Multiple com4 = com1;
        com1 = (ComSem.Multiple)(Context c)-> com4.apply(com3.apply(new Context(c)));
      }
      else
      {
        ComSem.Multiple com3 = 
            (Context c)->((ComSem.Multiple)com2).apply(new Context(c));
        final ComSem.Multiple com4 = com1;
        com1 = (ComSem.Multiple)(Context c)-> com3.apply(c).applyJoin(com4);
      }
      i--;
    }
    return com1;
  }
  public ComSem EmptyCommand()
  {
    return (ComSem.Single)(Context c)->c;
  }
  public ComSem ExpCommand(ExpSem e)
  {
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (ComSem.Single)(Context c)->
      {
        e0.apply(c);
        return c;
      };
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      return apply(e0, (SingleFunComSem.Single)(Value v)->(Context c)->c);
    }
  }
  public ComSem ForInCommand(AST.Command.ForInCommand command,
    ValueSymbol[] symbols,
    QvarSem qv, ComSem com, LoopSpecSem[] spec)
  {
    int[] slots = Translator.toSlots(symbols);
    String[] names = Translator.toNames(symbols);
    List<ContextRelation> invariants = getInvariants(spec);
    List<ContextFunction> variants = getVariants(spec);
    List<AST> cinvariants = getInvariants(command.spec);
    List<AST> cvariants = getVariants(command.spec);
    int allSlot = Translator.toSlot(command.getAllSet());
    if (!translator.nondeterministic)
    {
      QvarSem.Single qv0 = (QvarSem.Single)qv;
      ComSem.Single com0 = (ComSem.Single)com;
      return (ComSem.Single)(Context c)->
      {
        Context oldc = new Context(c);
        List<List<Value[]>> measures = 
            translator.initMeasures(c, variants, cvariants);
        Seq<Value[]> values = qv0.apply(c);
        Value.Set allSet = new Value.Set();
        c.set(allSlot, allSet, TypeChecker.forSetName);
        while (true)
        {
          Main.checkStopped();
          Seq.Next<Value[]> next = values.get();
          if (next == null) break;
          values = next.tail;
          Value[] v = next.head;
          c.set(slots, v, names);
          checkInvariant(invariants, c, oldc, cinvariants);
          c = com0.apply(c);
          measures = translator.checkMeasures(measures, c, variants, cvariants);
          if (v.length == 1)
            allSet.add(v[0]);
          else
            allSet.add(new Value.Array(v));
          c.set(allSlot, allSet, TypeChecker.forSetName);
        }
        checkInvariant(invariants, c, oldc, cinvariants);
        return c;
      };
    }
    else
    {
      QvarSem.Multiple qv0 = qv.toMultiple();
      ComSem.Multiple com0 = com.toMultiple();
      return (ComSem.Multiple)(Context c)->
      {
        List<List<Value[]>> measures = 
            translator.initMeasures(c, variants, cvariants);
        Context oldc = c;
        // duplicate context to avoid interference among non-deterministic branches
        return qv0.apply(c).applyJoin((Seq<Value[]> values)->
        {
          Context c0 = new Context(oldc);
          c0.set(allSlot, new Value.Set(), TypeChecker.forSetName);
          List<Value[]> values0 = values.list();
          return floop(c0, values0, com0, slots, names, allSlot, measures, oldc, 
              invariants, variants, cinvariants, cvariants);
        });
      };
    }
  }
  public ComSem IfThenCommand(AST.Expression e, ExpSem exp, ComSem com)
  {
    return ifThenElse(e, exp, com, (ComSem.Single)(Context c)->c);
  }
  public ComSem IfThenElseCommand(AST.Expression e, ExpSem exp, ComSem com1, ComSem com2)
  {
    return ifThenElse(e, exp, com1, com2);
  }
  public ComSem MatchCommand(AST.Command.MatchCommand command, TypeSymbol tsymbol,
    Symbol[] symbols, ExpSem e, FunComSem[] pcs)
  {
    Integer[] options = Arrays.stream(symbols)
        .map((Symbol symbol)->
          symbol == null ? -1 : Translator.getOption(tsymbol, symbol))
        .toArray(Integer[]::new);
    if (e instanceof ExpSem.Single &&
        Arrays.stream(pcs).allMatch((FunComSem pc)->pc instanceof FunComSem.Single))
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      return (ComSem.Single)(Context c)->
      {
        Value.Array v0 = (Value.Array)e0.apply(c);
        int index = Translator.getIndex(options, v0.getOption());
        FunComSem.Single pc = (FunComSem.Single)pcs[index];
        Value[] values = v0.get();
        ComSem.Single pc0 = pc.apply(values);
        Trace.matchtest(c, command, index, values);
        return pc0.apply(c);
      };
    }
    else
    {
      ExpSem.Multiple e0 = e.toMultiple();
      FunComSem.Multiple[] pcs0 = 
          Arrays.stream(pcs).map((FunComSem s)->s.toMultiple())
          .toArray(FunComSem.Multiple[]::new);
      return (ComSem.Multiple)(Context c)->e0.apply(c).applyJoin((Value v)->
      {
        Value.Array v0 = (Value.Array)v;
        int index = Translator.getIndex(options, v0.getOption());
        FunComSem.Multiple pc = pcs0[index];
        ComSem.Multiple pc0 = pc.apply(v0.get());
        return pc0.apply(c);
      });
    }
  }
  public ComSem PrintCommand(String string, ExpSem[] es)
  {
    return apply(es, (Value[] vs, Context c)->
    {
      // if (Main.getSilent()) return c;
      if (string == null)
      {
        int n = vs.length;
        for (int i=0; i<n; i++)
        {
          translator.writer.print(vs[i]);
          if (i+1 < n) translator.writer.print(",");
        }
        translator.writer.println();
        return c;
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
          if (index < 1 || index > vs.length)
            throw new RuntimeException("invalid value index " + index);
          translator.writer.print(vs[index-1]);
        }
        catch(NumberFormatException e) { }
      }
      translator.writer.println();
      return c;
    }
    );
  }
  public ComSem CheckCommand(AST.Command.CheckCommand command, 
    FunctionSymbol fun, ExpSem e)
  {
    if (e == null)
    {
      return (ComSem.Single)(Context c)->
      {
        boolean result = Main.execute(fun);
        if (!result)
        {
          translator.runtimeError(command, "ERROR in checking");
          return null;
        }
        return c;
      };
    }
    return apply(e, (SingleFunComSem.Single)(Value v)->(Context c)->
    {
      Value.Set set = (Value.Set)v;
      Seq<Value> values = Seq.list(new ArrayList<Value>(set));
      boolean result = Main.execute(fun, values, set.size());
      if (!result)
      {
        translator.runtimeError(command, "ERROR in checking");
        return null;
      }
      return c;
    });
  }
  public ComSem WhileCommand(AST.Command.WhileCommand command, 
    ExpSem e, ComSem com, LoopSpecSem[] spec)
  {
    List<ContextRelation> invariants = getInvariants(spec);
    List<ContextFunction> variants = getVariants(spec);
    List<AST> cinvariants = getInvariants(command.spec);
    List<AST> cvariants = getVariants(command.spec);
    if (e instanceof ExpSem.Single && com instanceof ComSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      ComSem.Single com0 = (ComSem.Single)com;
      return (ComSem.Single)(Context c)->
      {
        Context oldc = new Context(c);
        List<List<Value[]>> measures = translator.initMeasures(c, variants, cvariants);
        while (true)
        {
          Main.checkStopped();
          checkInvariant(invariants, c, oldc, cinvariants);
          Value.Bool b = (Value.Bool)e0.apply(c);
          boolean value = b.getValue();
          Trace.booltest(c, command.exp, value);
          if (!value) return c;
          c = com0.apply(c);
          measures = translator.checkMeasures(measures, c, variants, cvariants);
        }
      };
    }
    else
    {
      ExpSem.Multiple e0 = e.toMultiple();
      ComSem.Multiple com0 = com.toMultiple();
      return (ComSem.Multiple)(Context c)->
      {
        List<List<Value[]>> measures = translator.initMeasures(c, variants, cvariants);
        Context oldc = new Context(c);
        return wloop(c, e0, com0, measures, oldc, invariants, variants, cinvariants, cvariants);
      };
    }
  }

  // -------------------------------------------------------------------------
  // 
  // Loop handling
  //
  // -------------------------------------------------------------------------
  
  /****************************************************************************
   * Get all variants from specifications.
   * @param spec the specifications.
   * @return the variants.
   ***************************************************************************/
  public static List<AST> getInvariants(AST.LoopSpec[] spec)
  {
    List<AST> result = new ArrayList<AST>();
    for (AST.LoopSpec s : spec)
    {
      if (s instanceof AST.LoopSpec.InvariantLoopSpec) 
        result.add(s);
    }
    return result;
  }
  
  /****************************************************************************
   * Get all variants from specifications.
   * @param spec the specifications.
   * @return the variants.
   ***************************************************************************/
  public static List<ContextRelation> getInvariants(LoopSpecSem[] spec)
  {
    List<ContextRelation> result = new ArrayList<ContextRelation>();
    for (LoopSpecSem s : spec)
    {
      if (s instanceof LoopSpecSem.Invariant) 
        result.add((ContextRelation)s);
    }
    return result;
  }
  
  /****************************************************************************
   * Get all variants from specifications.
   * @param spec the specifications.
   * @return the variants.
   ***************************************************************************/
  public static List<AST> getVariants(AST.LoopSpec[] spec)
  {
    List<AST> result = new ArrayList<AST>();
    for (AST.LoopSpec s : spec)
    {
      if (s instanceof AST.LoopSpec.DecreasesLoopSpec) 
        result.add(s);
    }
    return result;
  }
  
  /****************************************************************************
   * Get all variants from specifications.
   * @param spec the specifications.
   * @return the variants.
   ***************************************************************************/
  public static List<ContextFunction> getVariants(LoopSpecSem[] spec)
  {
    List<ContextFunction> result = new ArrayList<ContextFunction>();
    for (LoopSpecSem s : spec)
    {
      if (s instanceof LoopSpecSem.Variant) 
        result.add((ContextFunction)s);
    }
    return result;
  }
  
  
  /****************************************************************************
   * Check invariants with respect to current context and old context.
   * @param invariants the invariants.
   * @param c the current context.
   * @param oldc the old context.
   * @param cinvariants the syntax of the invariants.
   ***************************************************************************/
  private void checkInvariant(List<ContextRelation> invariants, 
    Context c, Context oldc, List<AST> cinvariants)
  {
    int i = 0;
    for (ContextRelation s : invariants)
    {
      if (s instanceof ContextRelation.Single)
      {
        ContextRelation.Single s0 = (ContextRelation.Single)s;
        Value.Bool b = (Value.Bool)s0.apply(c, oldc);
        if (!b.getValue())
          translator.runtimeError(cinvariants.get(i), "invariant is violated");
      }
      else
      {
        ContextRelation.Multiple s0 = (ContextRelation.Multiple)s;
        Seq<Value.Bool> bs = s0.apply(c, oldc);
        while (true)
        {
          Seq.Next<Value.Bool> next = bs.get();
          if (next == null) break;
          Value.Bool b = next.head;
          if (!b.getValue())
            translator.runtimeError(cinvariants.get(i), "invariant is violated");
          bs = next.tail;
        }
      }
      i++;
    }
  }
  
  /***************************************************************************
   * Get contexts arising from non-deterministic while loop execution.
   * @param c the initial context.
   * @param exp the loop condition.
   * @param com the loop body.
   * @param measures the list of measures.
   * @param oldc the old context.
   * @param invariants the invariants
   * @param variants the variants
   * @param cinvariants the syntax of the invariants
   * @param cvariants the syntax of the variants
   * @return the sequence of contexts after the termination of the loop.
   ***************************************************************************/
  private Seq<Context> wloop(Context c, ExpSem.Multiple exp, ComSem.Multiple com,
    List<List<Value[]>> measures, Context oldc, 
    List<ContextRelation> invariants, List<ContextFunction> variants, 
    List<AST> cinvariants, List<AST> cvariants)
  {
    return exp.apply(c).applyJoin((Value b0)->
    {
      Main.checkStopped();
      checkInvariant(invariants, c, oldc, cinvariants);
      Value.Bool b = (Value.Bool)b0;
      if (!b.getValue()) return Seq.cons(c, Seq.empty());
      // duplicate context for non-interference across non-deterministic branches
      return com.apply(new Context(c)).applyJoin((Context c0)->
      {
        List<List<Value[]>> nmeasures = translator.checkMeasures(measures, c0, variants, cvariants);
        return wloop(c0, exp, com, nmeasures, oldc, invariants, variants, cinvariants, cvariants);
      });
    });
  }
  
  /***************************************************************************
   * Get contexts arising from non-deterministic for loop execution.
   * @param c the initial context.
   * @param values a list of values for the loop variable
   * @param com the loop body.
   * @param slots the variable slots.
   * @param names the names of the variables
   * @param allSlot the slot for the "all values" set.
   * @param measures the list of measures.
   * @param oldc the old context.
   * @param invariants the specification invariants,
   * @param variants the specification variants.
   * @param cinvariants the syntax of the invariants.
   * @param cvariants the syntax of the variants.
   * @return the sequence of contexts after the termination of the loop.
   ***************************************************************************/  
  private Seq<Context> floop(Context c, List<Value[]> values, 
    ComSem.Multiple com, int[] slots, String[] names, int allSlot,
    List<List<Value[]>> measures, Context oldc, 
    List<ContextRelation> invariants, List<ContextFunction> variants, 
    List<AST> cinvariants, List<AST> cvariants)
  {
    Main.checkStopped();
    if (values.size() == 0)
    {
      checkInvariant(invariants, c, oldc, cinvariants);
      return Seq.cons(c, Seq.empty());
    }
    return Seq.list(values).applyJoin((Value[] v)->
    {
      List<Value[]> values0 = new ArrayList<Value[]>(values.size()-1);
      for (Value[] v0 : values)
      {
        if (v0 != v) values0.add(v0);
      }
      c.set(slots, v, names);
      checkInvariant(invariants, c, oldc, cinvariants);
      return com.apply(c).applyJoin((Context c0)->
      {
       Context c1 = new Context(c0);
       List<List<Value[]>> nmeasures = 
           translator.checkMeasures(measures, c1, variants, cvariants);
       Value.Set allSet = new Value.Set((Value.Set)c1.get(allSlot));
       if (v.length == 1)
         allSet.add(v[0]);
       else
         allSet.add(new Value.Array(v));
       c1.set(allSlot, allSet, TypeChecker.forSetName);
       return floop(c1, values0, com, slots, names, allSlot, nmeasures, oldc, 
          invariants, variants, cinvariants, cvariants);
      });
    });
  }
  
  /***************************************************************************
   * Get contexts arising from non-deterministic loop execution.
   * @param qv the quantified variables
   * @param c the initial context.
   * @param values a list of values for the loop variable
   * @param com the loop body.
   * @param slots the variable slots.
   * @param names the names of the variables.
   * @param allSlot the slot for the "all values" set.
   * @param measures the list of measures.
   * @param oldc the old context.
   * @param invariants the specification invariants,
   * @param variants the specification variants.
   * @param cinvariants the syntax of the invariants.
   * @param cvariants the syntax of the variants.
   * @return the sequence of contexts after the termination of the loop.
   ***************************************************************************/  
  private Seq<Context> cloop(QvarSem.Multiple qv,
    Context c, Seq<Value[]> values, 
    ComSem.Multiple com, int[] slots, String[] names, int allSlot,
    List<List<Value[]>> measures, Context oldc, 
    List<ContextRelation> invariants, List<ContextFunction> variants, 
    List<AST> cinvariants, List<AST> cvariants)
  {
    Main.checkStopped();
    Seq.Next<Value[]> next = values.get();
    if (next == null)
    {
      checkInvariant(invariants, c, oldc, cinvariants);
      return Seq.cons(c, Seq.empty());
    }
    return values.applyJoin((Value[] v)->
    {
      c.set(slots, v, names);
      checkInvariant(invariants, c, oldc, cinvariants);
      return com.apply(c).applyJoin((Context c0)->
      qv.apply(c0).applyJoin((Seq<Value[]> values0)->
      {
        Context c1 = new Context(c0);
        List<List<Value[]>> nmeasures = 
            translator.checkMeasures(measures, c0, variants, cvariants);
        Value.Set allSet = new Value.Set((Value.Set)c1.get(allSlot));
        if (v.length == 1)
          allSet.add(v[0]);
        else
          allSet.add(new Value.Array(v));
        c1.set(allSlot, allSet, TypeChecker.chooseSetName);
        return 
            cloop(qv, c1, values0, com, slots, names, allSlot, nmeasures, oldc, 
                invariants, variants, cinvariants, cvariants);
      }));
    });
  }
 
  // -------------------------------------------------------------------------
  // 
  // Pattern commands
  //
  // -------------------------------------------------------------------------
  
  public FunComSem DefaultPatternCommand(ComSem com)
  {
    if (com instanceof ComSem.Single)
    {
      ComSem.Single com0 = (ComSem.Single)com;
      return (FunComSem.Single)(Value[] v) -> com0;
    }
    else
    {
      ComSem.Multiple com0 = (ComSem.Multiple)com;
      return (FunComSem.Multiple)(Value[] v) -> com0;
    }
  }
  public FunComSem IdentifierPatternCommand(ComSem com)
  {
    return DefaultPatternCommand(com);
  }
  public FunComSem ApplicationPatternCommand(AST command, ValueSymbol[] params, ComSem com)
  {
    return bind(command, params, com);
  }
  
  // -------------------------------------------------------------------------
  // 
  // Special command combinations
  //
  // -------------------------------------------------------------------------  
  
  /***************************************************************************
   * Apply a function to the semantics of an expression 
   * @param exp the expression
   * @param fun the function
   * @return all fun(v) where v is a value of exp
   **************************************************************************/
  private static ComSem apply(ExpSem exp, SingleFunComSem fun)
  {
    if (exp instanceof ExpSem.Single)
    {
      ExpSem.Single e = (ExpSem.Single)exp;
      if (fun instanceof SingleFunComSem.Single)
      {
        SingleFunComSem.Single f = (SingleFunComSem.Single)fun;
        return (ComSem.Single)(Context c)->f.apply(e.apply(c)).apply(c);
      }
      else
      {
        SingleFunComSem.Multiple f = (SingleFunComSem.Multiple)fun;
        return (ComSem.Multiple)(Context c)->f.apply(e.apply(c)).apply(c);
      }
    }
    else
    {
      ExpSem.Multiple e = (ExpSem.Multiple)exp;
      if (fun instanceof SingleFunComSem.Single)
      {
        SingleFunComSem.Single f = (SingleFunComSem.Single)fun;
        return (ComSem.Multiple)(Context c)->
          e.apply(c).apply((Value v)->f.apply(v).apply(c));
      }
      else
      {
        SingleFunComSem.Multiple f = (SingleFunComSem.Multiple)fun;
        return (ComSem.Multiple)(Context c)->
          e.apply(c).applyJoin((Value v)->f.apply(v).apply(c));
      }
    }
  }
  
  /***************************************************************************
   * Create semantics of conditional choice of two results.
   * @param exp the condition
   * @param ifExp the semantics of the condition
   * @param thenCom the choice, if the condition is true.
   * @param elseCom the choice, if the condition is false.
   * @return the conditional semantics.
   **************************************************************************/
  private static ComSem ifThenElse(AST.Expression exp, 
    ExpSem ifExp, ComSem thenCom, ComSem elseCom)
  {
    if (ifExp instanceof ExpSem.Single)
    {
      ExpSem.Single ifExp0 = (ExpSem.Single)ifExp;
      if (thenCom instanceof ComSem.Single && elseCom instanceof ComSem.Single)
      {
        ComSem.Single thenCom0 = (ComSem.Single)thenCom;
        ComSem.Single elseCom0 = (ComSem.Single)elseCom;
        return (ComSem.Single)(Context c)->
        {
          Value.Bool b = (Value.Bool)ifExp0.apply(c);
          if (b.getValue())
          {
            Trace.booltest(c, exp, true);
            return thenCom0.apply(c);
          }
          else
          {
            Trace.booltest(c, exp, false);
            return elseCom0.apply(c);
          }
        };
      }
      else
      {
        ComSem.Multiple thenCom0 = thenCom.toMultiple();
        ComSem.Multiple elseCom0 = elseCom.toMultiple();
        return (ComSem.Multiple)(Context c)->
        {
          Value.Bool b = (Value.Bool)ifExp0.apply(c);
          if (b.getValue())
            return thenCom0.apply(c);
          else
            return elseCom0.apply(c);
        };
      }
    }
    else
    {
      ExpSem.Multiple ifExp0 = (ExpSem.Multiple)ifExp;
      ComSem.Multiple thenCom0 = thenCom.toMultiple();
      ComSem.Multiple elseCom0 = elseCom.toMultiple();
      return (ComSem.Multiple)(Context c)->
      {
        Seq<Value> cond = ifExp0.apply(c);
        return cond.applyJoin((Value v)->
        {
          Value.Bool b = (Value.Bool)v;
          return b.getValue() ? 
              thenCom0.apply(c) : 
              elseCom0.apply(c);
        });
      };
    }
  }
  
  /***************************************************************************
   * Create a function semantics that accepts values for the given
   * symbols and returns the command that updates in the current context
   * the symbols with the values.
   * @param command the syntactic command 
   * @param symbols the symbols
   * @return the function semantics
   **************************************************************************/
  private static FunComSem update(AST.Command command, ValueSymbol[] symbols)
  {
    int[] slots = Translator.toSlots(symbols);
    String[] names = Translator.toNames(symbols);
    return (FunComSem.Single)(Value[] v)-> (ComSem.Single)(Context c)->
    {
      Context c0 = c.set(slots, v, names);
      Trace.choice(c0, command, v);
      return c0;
    };
  }
  
  /***************************************************************************
   * Create a function semantics that accepts values for the given
   * symbols and returns the value of the command in the current
   * context where the symbols receive the given values.
   * @param command the syntactic command
   * @param symbols the symbols
   * @param com the command
   * @return the function semantics
   **************************************************************************/
  private static FunComSem bind(AST command, ValueSymbol[] symbols, ComSem com)
  {
    int[] slots = Translator.toSlots(symbols);
    String[] names = Translator.toNames(symbols);
    if (com instanceof ComSem.Single) 
    {
      ComSem.Single com0 = (ComSem.Single)com;
      return (FunComSem.Single)(Value[] v)-> (ComSem.Single)(Context c)->
        {
          Context c0 = c.set(slots, v, names);
          Trace.choice(c0, command, v);
          return com0.apply(c0);
        };
    }
    else
    {
      ComSem.Multiple com0 = (ComSem.Multiple)com;
      return (FunComSem.Multiple)(Value[] v)-> (ComSem.Multiple)(Context c)->
        {
          Context c0 = c.set(slots, v, names);
          return com0.apply(c0);
        };
    }
  }
  
  /****************************************************************************
   * Determine the semantics of a command from the choice of a variable.
   * @param com the command that makes the choice.
   * @param qvar the quantified variables to be chosen from.
   * @param none its application to the original context
   *             determines the result, if no choice is possible
   *             (if null, an exception is raised)
   * @param some its application to the context updated by the choice
   *             determines the result.
   * @return the resulting semantics
   ***************************************************************************/
  private ComSem choose(AST.Command com, QvarSem qvar, ComSem none, FunComSem some)
  {
    if (qvar instanceof QvarSem.Single &&
        (none == null || none instanceof ComSem.Single) &&
        some instanceof FunComSem.Single)
    {
      QvarSem.Single qvar0 = (QvarSem.Single)qvar;
      ComSem.Single none0 = (ComSem.Single)none;
      FunComSem.Single some0 = (FunComSem.Single)some;
      if (translator.nondeterministic)
        return (ComSem.Multiple)(Context c)-> 
      {
        Seq<Value[]> v = qvar0.apply(c);
        // if (v.get() == null) 
        // {
        //  if (none0 == null)
        //    return Seq.empty();
        //  else
        //    return Seq.cons(none0.apply(c), Seq.empty());
        // }
        // return v.apply((Value[] v1)->some0.apply(v1).apply(c));
        if (none0 == null)
          return v.apply((Value[] v1)-> some0.apply(v1).apply(c));
        else
          return Seq.append(v.apply((Value[] v1)-> some0.apply(v1).apply(c)),
              Seq.supplier(()->new Seq.Next<Context>(none0.apply(c), Seq.empty())));
      };
      else
        return (ComSem.Single)(Context c)-> 
      {
        Main.performChoice();
        Seq<Value[]> v = qvar0.apply(c);
        Seq.Next<Value[]> next = v.get();
        if (next == null) 
        {
          if (none0 == null)
          {
            translator.runtimeError(com, "no choice possible");
            return null;
          }
          else
          {
            // Trace.choice(c, com, null);
            return none0.apply(c);
          }
        }
        else
        {
          // Trace.choice(c, com, next.head);
          return some0.apply(next.head).apply(c);
        }
      };
    }
    else
    {
      QvarSem.Multiple qvar0 = qvar.toMultiple();
      FunComSem.Multiple some0 = some.toMultiple();
      ComSem.Multiple none0 = none == null ? null : none.toMultiple();
      return (ComSem.Multiple)(Context c)->
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
  
  /***************************************************************************
   * Apply function to each combination of values of the given expressions.
   * @param es the expressions
   * @param fun the function.
   **************************************************************************/
  private static ComSem apply(ExpSem[] es, BiFunction<Value[],Context,Context> fun)
  {
    if (Arrays.stream(es).allMatch((ExpSem e)-> (e instanceof ExpSem.Single)))
      return (ComSem.Single)(Context c)->
    {
      int n = es.length;
      Value[] values = new Value[n];
      for (int i=0; i<n; i++) 
      {
        ExpSem.Single e0 = (ExpSem.Single)es[i];
        values[i] = e0.apply(c);
      }
      return fun.apply(values,c);
    };
    else
    {
      return (ComSem.Multiple)(Context c)->
      {
        int n = es.length;
        @SuppressWarnings("unchecked")
        Seq<Value>[] seq = new Seq[n];
        for (int i=0; i<n; i++)
        {
          ExpSem.Multiple e = es[i].toMultiple();
          seq[i] = e.apply(c);
        }
        return Types.getCombinations(seq).apply((Value[] v)->fun.apply(v, c));
      };
    }
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------