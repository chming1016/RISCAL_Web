// ---------------------------------------------------------------------------
// Declarations.java
// The denotational semantics of declarations
// $Id: Declarations.java,v 1.20 2018/03/23 20:28:49 schreine Exp $
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

import riscal.types.Environment.Symbol.*;
import riscal.util.Seq;

import java.util.ArrayList;
import java.util.List;

import riscal.Main;
import riscal.semantics.Types.*;
import riscal.syntax.AST;

public class Declarations
{
  // the current translator
  private final Translator translator;
  
  // -------------------------------------------------------------------------
  //
  // Declarations
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Create declarations translator.
   * @param translator the overall translator.
   **************************************************************************/
  public Declarations(Translator translator)
  {
    this.translator = translator;
  }
  
  public void ValueDefinition(AST.Declaration.ValueDefinition decl, 
    ValueSymbol symbol, ExpSem e)
  {
    int slots = symbol.getSlots();
    Context c = new Context(slots);
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      Value value = e0.apply(c);
      translator.checkConstraints(decl, value, decl.exp.getType(), symbol.type);
      symbol.setValue(value);
    }
    else
    {
      // in a non-deterministic expression, we use the first value 
      // for static type-checking
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      Seq<Value> values = e0.apply(c).apply((Value value)->
      {
        translator.checkConstraints(decl, value, decl.exp.getType(), symbol.type);
        return value;
      });
      symbol.setValues(values);
      Seq.Next<Value> next = values.get();
      if (next != null) symbol.setValue(next.head);
    }
  }
  
  public void TheoremDefinition(AST.Declaration.TheoremDefinition decl,
    ValueSymbol symbol, ExpSem exp)
  {
    int slots = symbol.getSlots();
    Context c = new Context(slots);
    if (exp instanceof ExpSem.Single)
    {
      ExpSem.Single exp0 = (ExpSem.Single)exp;
      Value.Bool value = (Value.Bool)exp0.apply(c);
      if (!value.getValue())
        translator.translationError(decl, "theorem is not true");
      symbol.setValue(value);
    }
    else
    {
      // in a non-deterministic expression, all values must be true
      ExpSem.Multiple exp0 = (ExpSem.Multiple)exp;
      Seq<Value> values = exp0.apply(c);
      symbol.setValues(values);
      Seq.Next<Value> next = values.get();
      if (next == null) 
        translator.translationError(decl, "theorem has no value");
      do
      {
        Value.Bool value = (Value.Bool)next.head;
        if (!value.getValue())
          translator.translationError(decl, "theorem is not always true");
        values = next.tail;
        next = values.get();
      }
      while (next != null);
      symbol.setValue(new Value.Bool(true));
    }
  }
  
  public void PredicateValueDefinition(AST.Declaration.PredicateValueDefinition decl,
    ValueSymbol symbol, ExpSem exp)
  {
    int slots = symbol.getSlots();
    Context c = new Context(slots);
    if (exp instanceof ExpSem.Single)
    {
      ExpSem.Single exp0 = (ExpSem.Single)exp;
      Value.Bool value = (Value.Bool)exp0.apply(c);
      symbol.setValue(value);
    }
    else
    {
      // in a non-deterministic expression, we use the first value 
      // for static type-checking
      ExpSem.Multiple exp0 = (ExpSem.Multiple)exp;
      Seq<Value> values = exp0.apply(c);
      symbol.setValues(values);
      Seq.Next<Value> next = values.get();
      if (next != null) symbol.setValue(next.head);
    }
  }
  
  public void FunctionDefinition(FunctionSymbol fun, ValueSymbol[] params,
    ExpSem e, AST.Expression exp, FunSpecSem[] spec,
    AST.Declaration.FunctionSpec[] dspec, boolean theorem)
  {
    List<ContextCondition> pre = new ArrayList<ContextCondition>();
    List<ContextCondition> post = new ArrayList<ContextCondition>();
    List<ContextFunction> variants = new ArrayList<ContextFunction>();
    List<AST> cpre = new ArrayList<AST>();
    List<AST> cpost = new ArrayList<AST>();
    List<AST> cvariants = new ArrayList<AST>();
    getPreconditions(dspec, spec, cpre, pre);
    getPostconditions(dspec, spec, cpost, post);
    getVariants(dspec, spec, cvariants, variants);
    int slots = fun.getSlots();
    int[] p = Translator.toSlots(params);
    String[] names = Translator.toNames(params);
    if (e instanceof ExpSem.Single)
    {
      ExpSem.Single e0 = (ExpSem.Single)e;
      fun.setValue((FunSem.Single)(Argument a)->
      {
        Main.checkStopped();
        Context c = new Context(slots, p, a, names);
        checkPrecondition(pre, c, cpre);
        checkMeasures(fun, c, variants, cvariants);
        Trace.arguments(c, params, a.values);
        Value result = e0.apply(c);
        Trace.result(c, exp, result);
        translator.checkConstraints(exp, result, exp.getType(), fun.type);
        checkPostcondition(fun, post, c, result, cpost);
        if (theorem) checkTheorem(fun, result);
        return result;
      });
      fun.setPreconditions(pre);
    }
    else
    {
      ExpSem.Multiple e0 = (ExpSem.Multiple)e;
      fun.setValue((FunSem.Multiple)(Argument a)->
      {
        Main.checkStopped();
        Context c = new Context(slots, p, a, names);
        checkPrecondition(pre, c, cpre);
        checkMeasures(fun, c, variants, cvariants);
        Seq<Value> results = e0.apply(c).apply((Value result)->
        {
          checkPostcondition(fun, post, c, result, cpost);
          if (theorem) checkTheorem(fun, result);
          return result;
        });
        return results;
      });
      fun.setPreconditions(pre);
    }
  }
  
  public void ProcedureDefinition(FunctionSymbol fun, ValueSymbol[] params,
    ComSem com, ExpSem e, AST.Expression exp, FunSpecSem[] spec,
    AST.Declaration.FunctionSpec[] dspec)
  {
    List<ContextCondition> pre = new ArrayList<ContextCondition>();
    List<ContextCondition> post = new ArrayList<ContextCondition>();
    List<ContextFunction> variants = new ArrayList<ContextFunction>();
    List<AST> cpre = new ArrayList<AST>();
    List<AST> cpost = new ArrayList<AST>();
    List<AST> cvariants = new ArrayList<AST>();
    getPreconditions(dspec, spec, cpre, pre);
    getPostconditions(dspec, spec, cpost, post);
    getVariants(dspec, spec, cvariants, variants);
    int slots = fun.getSlots();
    int[] p = Translator.toSlots(params);
    String[] names = Translator.toNames(params);
    if (com instanceof ComSem.Single && e instanceof ExpSem.Single)
    {
      ComSem.Single com0 = (ComSem.Single)com;
      ExpSem.Single e0 = (ExpSem.Single)e;
      fun.setValue((FunSem.Single)(Argument a)->
      {
        Main.checkStopped();
        Context c = new Context(slots, p, a, names);
        checkPrecondition(pre, c, cpre);
        checkMeasures(fun, c, variants, cvariants);
        Trace.arguments(c, params, a.values);
        Context c0 = com0.apply(c);
        Value result = e0.apply(c0);
        Trace.result(c0, exp, result);
        if (exp != null) 
          translator.checkConstraints(exp, result, exp.getType(), fun.type);
        checkPostcondition(fun, post, c, result, cpost);
        return result;
      });
      fun.setPreconditions(pre);
    }
    else
    {
      ComSem.Multiple com0 = com.toMultiple();
      ExpSem.Multiple e0 = e.toMultiple();
      fun.setValue((FunSem.Multiple)(Argument a)->
      {
        Main.checkStopped();
        Context c = new Context(slots, p, a, names);
        checkPrecondition(pre, c, cpre);
        checkMeasures(fun, c, variants, cvariants);
        Seq<Value> results = com0.apply(c).applyJoin((Context c0)->
        e0.apply(c0).apply((Value result)->
        {
          if (exp != null)
            translator.checkConstraints(exp, result, exp.getType(), fun.type);
          checkPostcondition(fun, post, c, result, cpost);
          return result;
        }));
        return results;
      });
      fun.setPreconditions(pre);
    }
  }
  
  // -------------------------------------------------------------------------
  //
  // Function Specifications
  //
  // -------------------------------------------------------------------------
  
  /****************************************************************************
   * Check measures list by new values of variants and trigger a runtime
   * exception if some variant is not decreased or becomes negative.
   * @param fun the current function.
   * @param c the current context.
   * @param variants the semantics of the variants.
   * @param cvariants the syntax of the variants.
   ***************************************************************************/
  public void checkMeasures(FunctionSymbol fun, Context c, 
    List<ContextFunction> variants, List<AST>  cvariants)
  {
    MeasureMap measures = c.getMeasures();
    if (measures == null) measures = new MeasureMap();
    List<List<Value[]>> omeasures = measures.get(fun);
    List<List<Value[]>> nmeasures = 
        omeasures == null ?
            translator.initMeasures(c, variants, cvariants)
          : translator.checkMeasures(omeasures, c, variants, cvariants);
    MeasureMap result = new MeasureMap(measures);
    result.put(fun, nmeasures);
    c.setMeasures(result);
  }
 
  /****************************************************************************
   * Get all preconditions from specifications.
   * @param spec the specifications.
   * @param sem the corresponding semantics.
   * @param pspec the vector to receive the specifications.
   * @param psem the vector to receive the semantics.
   ***************************************************************************/
  private static void getPreconditions(
    AST.FunctionSpec[] spec, FunSpecSem[] sem,
    List<AST> pspec, List<ContextCondition> psem)
  {
    int n = spec.length;
    for (int i=0; i<n; i++)
    {
      AST.FunctionSpec s = spec[i];
      if (s instanceof AST.FunctionSpec.RequiresSpec) 
      {
        pspec.add(s);
        psem.add((ContextCondition)(sem[i]));
      }
    }
  }

  /****************************************************************************
   * Get all postconditions from specifications.
   * @param spec the specifications.
   * @param sem the corresponding semantics.
   * @param pspec the vector to receive the specifications.
   * @param psem the vector to receive the semantics.
   ***************************************************************************/
  private static void getPostconditions(
    AST.FunctionSpec[] spec, FunSpecSem[] sem,
    List<AST> pspec, List<ContextCondition> psem)
  {
    int n = spec.length;
    for (int i=0; i<n; i++)
    {
      AST.FunctionSpec s = spec[i];
      if (s instanceof AST.FunctionSpec.EnsuresSpec) 
      {
        pspec.add(s);
        psem.add((ContextCondition)(sem[i]));
      }
    }
  }
  
  /****************************************************************************
   * Get all variants from specifications.
   * @param spec the specifications.
   * @param sem the corresponding semantics.
   * @param pspec the vector to receive the specifications.
   * @param psem the vector to receive the semantics.
   ***************************************************************************/
  private static void getVariants(
    AST.FunctionSpec[] spec, FunSpecSem[] sem,
    List<AST> pspec, List<ContextFunction> psem)
  {
    int n = spec.length;
    for (int i=0; i<n; i++)
    {
      AST.FunctionSpec s = spec[i];
      if (s instanceof AST.FunctionSpec.DecreasesSpec) 
      {
        pspec.add(s);
        psem.add((ContextFunction)(sem[i]));
      }
    }
  }
  
  /***************************************************************************
   * Check preconditions.
   * @param pre the semantics of the preconditions.
   * @param c the context when the function is called.
   * @param cpre the syntax of the preconditions.
   **************************************************************************/
  private void checkPrecondition(List<ContextCondition> pre, Context c, 
    List<AST> cpre)
  {
    int i = 0;
    for (ContextCondition s : pre)
    {
      if (s instanceof ContextCondition.Single)
      {
        ContextCondition.Single s0 = (ContextCondition.Single)s;
        Value.Bool b = s0.apply(c);
        if (!b.getValue())
          translator.runtimeError(cpre.get(i), "precondition is violated");
      }
      else
      {
        ContextCondition.Multiple s0 = (ContextCondition.Multiple)s;
        Seq<Value.Bool> bs = s0.apply(c);
        while (true)
        {
          Seq.Next<Value.Bool> next = bs.get();
          if (next == null) break;
          Value.Bool b = next.head;
          if (!b.getValue())
            translator.runtimeError(cpre.get(i), "precondition is violated");
          bs = next.tail;
        }
      }
      i++;
    }
  }
   
  /***************************************************************************
   * Check postconditions.
   * @param fun the symbol of the function.
   * @param post the semantics of the postconditions.
   * @param c the context when the function was called.
   * @param result the result value of the function.
   * @param cpost the syntax of the postconditions.
   **************************************************************************/
  private void checkPostcondition(FunctionSymbol fun, 
    List<ContextCondition> post, Context c, Value result,
    List<AST> cpost)
  {
    ValueSymbol symbol = fun.getResult();
    c.set(symbol.getSlot(), result, symbol.ident.string);
    int i = 0;
    for (ContextCondition s : post)
    {
      if (s instanceof ContextCondition.Single)
      {
        ContextCondition.Single s0 = (ContextCondition.Single)s;
        Value.Bool b = s0.apply(c);
        if (!b.getValue())
          translator.runtimeError(cpost.get(i), 
              "postcondition is violated by result " + result);
      }
      else
      {
        ContextCondition.Multiple s0 = (ContextCondition.Multiple)s;
        Seq<Value.Bool> bs = s0.apply(c);
        while (true)
        {
          Seq.Next<Value.Bool> next = bs.get();
          if (next == null) break;
          Value.Bool b = next.head;
          if (!b.getValue())
            translator.runtimeError(cpost.get(i), 
                "postcondition is violated by result " + result);
          bs = next.tail;
        }
      }
      i++;
    }
  }
  
  /****************************************************************************
   * Check whether result of function is "true"
   * @param fun the function
   * @param result its result
   ***************************************************************************/
  private void checkTheorem(FunctionSymbol fun, Value result)
  {
    Value.Bool result0 = (Value.Bool)result;
    if (result0.getValue()) return;
    translator.runtimeError(fun.ident, "theorem is not true");
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------