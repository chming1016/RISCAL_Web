// ---------------------------------------------------------------------------
// Choices.java
// Replace choices from expressions (as far as possible)
// $Id: Choices.java,v 1.14 2018/05/03 10:52:13 schreine Exp $
//
// Author: Wolfgang Schreiner <Wolfgang.Schreiner@risc.jku.at>
// Copyright (C) 2018-, Research Institute for Symbolic Computation (RISC)
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
package riscal.tasks;

import java.util.*;

import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.syntax.AST.Expression.*;

public class Choices extends ASTCloner
{
  // can remove choices that do not occur in the bodies of quantified terms:
  // - q(choose x with p1(x), choose x with p2(x))
  //   -> forall x with p1(x). 
  //        forall x0 with let x=x0 in p2(x). q(x, let x=x0 in p2(x))
  //
  // cannot remove choices that do occur in the bodies of quantified terms:
  // - max x. (choose y with p1(x,y)) + (choose y with p2(x,y)) 
  //   -> max x. choose y with p1(x,y) in 
  //               choose y0 with let y=y0 in p2(x,y0) in 
  //                 y + (let y=y0 in y)
  
  /***************************************************************************
   * Remove choices in a theorem as far as possible.
   * @param exp the theorem formula
   * @param vars the list of variables that are currently visible
   * @return the the formula with a lot of choices removed.
   **************************************************************************/
  public static Expression minimizeTheorem(Expression exp, List<String> vars)
  {
    Choices choices = new Choices(true, vars);
    Expression exp0 = (Expression)exp.accept(choices);
    return choices.closeForall(exp0);
  }
  
  /***************************************************************************
   * Close a theorem formula by adding all pending quantified variables
   * @param exp the formula
   * @return the closed formula
   ***************************************************************************/
  private Expression closeForall(Expression exp)
  {
    List<QuantifiedVariableCore> cores = new ArrayList<QuantifiedVariableCore>();
    for (QuantifiedVariable v : vars)
    {
      cores.addAll(Arrays.asList(v.qvcore));
      if (v.exp != null) 
        exp = new ForallExp(new QuantifiedVariable(cores, v.exp), exp);
    }
    if (!cores.isEmpty()) 
      exp = new ForallExp(new QuantifiedVariable(cores, null), exp);
    return exp;
  }

  /***************************************************************************
   * Remove choices in the term as far as possible.
   * @param exp the term
   * @param vars the list of variables that are currently visible
   * @return the the term with a lot of choices removed.
   **************************************************************************/
  public static Expression minimizeTerm(Expression exp, List<String> vars)
  {
    Choices choices = new Choices(false, vars);
    Expression exp0 = (Expression)exp.accept(choices);
    return choices.closeChoose(exp0);
  }
  
  /***************************************************************************
   * Close a term expression by adding all pending quantified variables
   * @param exp the expression
   * @return the closed expression
   ***************************************************************************/
  private Expression closeChoose(Expression exp)
  {
    List<QuantifiedVariableCore> cores = new ArrayList<QuantifiedVariableCore>();
    for (QuantifiedVariable v : vars)
    {
      cores.addAll(Arrays.asList(v.qvcore));
      if (v.exp != null) 
        exp = new ChooseInExp(new QuantifiedVariable(cores, v.exp), exp);
    }
    if (!cores.isEmpty()) 
      exp = new ChooseInExp(new QuantifiedVariable(cores, null), exp);
    return exp;
  }
  
  /***************************************************************************
   * Remove choices in the expression as far as possible.
   * @param exp the expression.
   * @param vars the list of variables that are currently visible
   * @return the expression with a lot of choices removed.
   **************************************************************************/
  private Expression minimize(Expression exp, List<String> vars)
  {
    if (theorem && isFormula(exp)) return minimizeTheorem(exp, vars);
    return minimizeTerm(exp, vars);
  }
  
  /***************************************************************************
   * Remove choices in the expression as far as possible.
   * @param exp the expression
   * @return the expression with a lot of choices removed.
   **************************************************************************/
  private Expression minimize(Expression exp)
  {
    return minimize(exp, visible);
  }
  
  /***************************************************************************
   * Determine whether an expression is a formula.
   * @param exp the expression.
   * @return true if the expression denotes a formula.
   **************************************************************************/
  private static boolean isFormula(Expression exp)
  {
    Type type = exp.getType();
    while (type instanceof Type.SubType)
    {
      Type.SubType type0 = (Type.SubType)type;
      type = type0.type;
    }
    return type instanceof Type.BoolType;
  }
  
  // are we in a theorem context?
  private boolean theorem;
  
  // the list of chosen variables
  private List<QuantifiedVariable> vars = new ArrayList<QuantifiedVariable>();
  
  // the list of currently visible variable names;
  private List<String> visible;
  
  /***************************************************************************
   * Create object for minimizing choices.
   * @param theorem true if we are in a theorem context
   * @param visible the names of the currently visible variables.
   **************************************************************************/
  private Choices(boolean theorem, List<String> visible)
  {
    this.theorem = theorem;
    this.visible = new ArrayList<String>();
    this.visible.addAll(visible);
  }
  
  // we have to consider all quantified expressions
  // - choices: ChooseExp, ChooseInExp, ChooseInElseExp
  // - formulas: ForallExp, ExistsExp
  // - terms: MinExp, MaxExp, NumberExp, SumExp, ProductExp, SetBuilderExp
  // - binders: LetExp, LetParExp
  // - patterns: MatchExp
  // and also the expressions from which the evaluation of a choice
  // must not escape:
  // - AndExp, OrExp, ImpliesExp, IfThenElseExp
  
  /**************************************************************************
   * Visit an expression.
   * @param exp the expression
   * @return a version of exp with choices minimized
   **************************************************************************/
  
  public Expression visit(ChooseExp exp)
  {
    return replace(exp.qvar, null);
  }
  
  public Expression visit(ChooseInExp exp)
  {
    return replace(exp.qvar, exp.exp);
  }
  
  public Expression visit(ChooseInElseExp exp)
  {
    // do not let the evaluation of the subexpressions
    // escape the context of the quantified variable
    QuantifiedVariable qvar = minimize(exp.qvar);
    List<String> visible0 = new ArrayList<String>(visible);
    for (QuantifiedVariableCore c : exp.qvar.qvcore)
      visible0.add(c.ident.string);
    Expression exp1 = minimize(exp.exp1, visible0);
    Expression exp2 = minimize(exp.exp2);
    return new ChooseInElseExp(qvar, exp1, exp2);
  }
 
  public Expression visit(ForallExp exp)
  {
    return replace(exp.qvar, exp.exp);
  }
  
  public Expression visit(ExistsExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new ExistsExp(qvar, exp.exp);
  }  
  
  public Expression visit(AndExp exp)
  {
    Expression exp1 = process(exp.exp1);
    Expression exp2 = minimize(exp.exp2);
    return new AndExp(exp1, exp2);
  }
  
  public Expression visit(OrExp exp)
  {
    Expression exp1 = process(exp.exp1);
    Expression exp2 = minimize(exp.exp2);
    return new OrExp(exp1, exp2);
  }
  
  public Expression visit(ImpliesExp exp)
  {
    Expression exp1 = process(exp.exp1);
    Expression exp2 = minimize(exp.exp2);
    return new ImpliesExp(exp1, exp2);
  }
  
  public Expression visit(IfThenElseExp exp)
  {
    Expression exp1 = process(exp.exp1);
    Expression exp2 = minimize(exp.exp2);
    Expression exp3 = minimize(exp.exp3);
    return new IfThenElseExp(exp1, exp2, exp3);
  }
  
  public Expression visit(MinExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new MinExp(qvar, exp.exp);
  } 
  
  public Expression visit(MaxExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new MaxExp(qvar, exp.exp);
  } 
  
  public Expression visit(SumExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new SumExp(qvar, exp.exp);
  } 
  
  public Expression visit(ProductExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new ProductExp(qvar, exp.exp);
  } 
  
  public Expression visit(NumberExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new NumberExp(qvar);
  } 
  
  public Expression visit(SetBuilderExp exp)
  {
    QuantifiedVariable qvar = minimize(exp.qvar);
    return new SetBuilderExp(exp.exp, qvar);
  } 
  
  public Expression visit(LetExp exp)
  {
    List<Binder> binders = new ArrayList<Binder>();
    List<String> visible0 = new ArrayList<String>(visible);
    for (Binder b : exp.binder)
    {
      binders.add(new Binder(b.ident, minimizeTerm(b.exp, visible0)));
      visible0.add(b.ident.string);
    }
    Expression exp0 = minimize(exp.exp);
    return new LetExp(binders, exp0);
  }
  
  public Expression visit(LetParExp exp)
  {
    List<Binder> binders = new ArrayList<Binder>();
    for (Binder b : exp.binder)
      binders.add(new Binder(b.ident, minimizeTerm(b.exp, visible)));
    Expression exp0 = minimize(exp.exp);
    return new LetParExp(binders, exp0);
  }
  
  public Expression visit(MatchExp exp)
  {
    Expression exp0 = process(exp.exp);
    List<PatternExpression> patterns = new ArrayList<PatternExpression>();
    for (PatternExpression pexp : exp.pexp)
      patterns.add(minimize(pexp));
    return new MatchExp(exp0, patterns);
  }
    
  // --------------------------------------------------------------------------
  //
  // Auxiliaries
  //
  // --------------------------------------------------------------------------
  
  /***************************************************************************
   * Minimize quantified variables.
   * @param qvar the quantified variables.
   * @return the minimized variables (names unchanged).
   **************************************************************************/
  private QuantifiedVariable minimize(QuantifiedVariable qvar)
  {
    if (qvar.exp == null) return qvar;
    List<String> visible0 = new ArrayList<String>(visible);
    for (QuantifiedVariableCore c : qvar.qvcore)
      visible0.add(c.ident.string);
    Expression exp0 = minimizeTheorem(qvar.exp, visible0);
    return new QuantifiedVariable(Arrays.asList(qvar.qvcore), exp0);
  }
  
  /***************************************************************************
   * Minimize pattern expression
   * @param pexp the pattern expression.
   * @return the minimized version of the expression.
   **************************************************************************/
  private PatternExpression minimize(PatternExpression pexp)
  {
    if (pexp instanceof PatternExpression.DefaultPatternExp)
    {
      PatternExpression.DefaultPatternExp pexp0 =
          (PatternExpression.DefaultPatternExp)pexp;
      return new PatternExpression.DefaultPatternExp(minimize(pexp0.exp));
    }
    if (pexp instanceof PatternExpression.IdentifierPatternExp)
    {
      PatternExpression.IdentifierPatternExp pexp0 =
          (PatternExpression.IdentifierPatternExp)pexp;
      return new PatternExpression.IdentifierPatternExp(pexp0.ident,
          minimize(pexp0.exp));
    }
    if (pexp instanceof PatternExpression.ApplicationPatternExp)
    {
      PatternExpression.ApplicationPatternExp pexp0 =
          (PatternExpression.ApplicationPatternExp)pexp;
      List<Parameter> params = new ArrayList<Parameter>();
      List<String> visible0 = new ArrayList<String>();
      for (Parameter p : pexp0.params)
      {
        params.add(new Parameter(p.ident, p.type));
        visible0.add(p.ident.string);
      }
      return new PatternExpression.ApplicationPatternExp(pexp0.ident,
         params, minimize(pexp0.exp, visible0));
    }
    return null;
  }
  
  /****************************************************************************
   * Replace quantified variable in which expression is evaluated.
   * @param qvar the quantified variable.
   * @param the expression (may be null indicating a reference to the variables)
   * @return the replaced expression (may be the reference to the variables)
   ***************************************************************************/
  private Expression replace(QuantifiedVariable qvar, Expression exp)
  {
    int n = qvar.qvcore.length;
    List<QuantifiedVariableCore> core0 = new ArrayList<QuantifiedVariableCore>();
    for (int i=0; i<n; i++)
    {
      Expression qvarexp = i < n-1 ? null : qvar.exp;
      QuantifiedVariableCore c = qvar.qvcore[i];
      QuantifiedVariableCore c0 = replace(c, qvarexp, qvar.qvcore, core0);
      core0.add(c0);
    }
    if (exp != null) 
      return process(exp, qvar.qvcore, core0);
    if (core0.size() == 1) 
      return AST.reference(core0.get(0).ident.string);
    List<Expression> refs = new ArrayList<Expression>();
    for (QuantifiedVariableCore c0 : core0)
      refs.add(AST.reference(c0.ident.string));
    return new TupleExp(refs);    
  }
  
  /****************************************************************************
   * Replace variable core
   * @param c the core to be replaced
   * @param exp the condition attached to the core (may be null)
   * @param core  all cores
   * @param core0 the replacements performed so far
   * @return the replaced version of core
   ***************************************************************************/
  private QuantifiedVariableCore replace(QuantifiedVariableCore c,
    Expression exp,
    QuantifiedVariableCore[] core, List<QuantifiedVariableCore> core0)
  {
    List<QuantifiedVariableCore> result = 
        new ArrayList<QuantifiedVariableCore>();
    
    // determine replacement string, if necessary
    String string = freshVariable(c.ident.string);
    if (string.equals(c.ident.string)) 
    {
      result.add(c);
      vars.add(new QuantifiedVariable(result, exp));
      return c;
    }
    
    // perform the replacement
    if (c instanceof QuantifiedVariableCore.IdentifierTypeQuantifiedVar)
    {
      QuantifiedVariableCore.IdentifierTypeQuantifiedVar c0 =
          (QuantifiedVariableCore.IdentifierTypeQuantifiedVar)c;
      result.add(new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(
          new Identifier(string), c0.type));
    }
    else if (c instanceof QuantifiedVariableCore.IdentifierSetQuantifiedVar)
    {
      QuantifiedVariableCore.IdentifierSetQuantifiedVar c0 =
          (QuantifiedVariableCore.IdentifierSetQuantifiedVar)c;
      Expression exp0 = process(c0.exp, core, core0);
      result.add(new QuantifiedVariableCore.IdentifierSetQuantifiedVar(
          new Identifier(string), exp0));
    }
    Expression exp0 = exp == null ? null : process(exp, core, core0);
    vars.add(new QuantifiedVariable(result, exp0));
    return result.get(0);
  }
  
  /****************************************************************************
   * Process expression after replacement of quantified variables.
   * @param exp the expression.
   * @param core the original quantified variables.
   * @param core0 the new quantified variables.
   * @return the processed expression with appropriate renaming
   ***************************************************************************/
  private Expression process(Expression exp,
    QuantifiedVariableCore[] core, List<QuantifiedVariableCore> core0)
  {
    Expression exp0 = process(exp);
    List<Binder> binder = new ArrayList<Binder>();
    int n = core.length;
    for (int i=0; i<n; i++)
    {
      Identifier ident = core[i].ident;
      Identifier ident0 = core0.get(i).ident;
      if (!ident.string.equals(ident0.string))
        binder.add(new Binder(new Identifier(ident.string), AST.reference(ident0.string)));
    }
    if (binder.size() > 0) exp0 = new LetParExp(binder, exp0);
    return exp0;
  }
  
  /****************************************************************************
   * Process expression in current context.
   * @param exp the expression.
   * @return its processed form.
   ***************************************************************************/
  private Expression process(Expression exp)
  {
    return (Expression)exp.accept(this);
  }
  
  /****************************************************************************
   * Get the name of a variable that does not occur among the visible variables
   * @param name a suggestion for the name of the variable.
   * @return a name based on the suggestion which is not yet visible
   * also adds the name to the list of visible variables
   ***************************************************************************/
  private String freshVariable(String name)
  {
    String name0 = name;
    int i = 0;
    while (true)
    {
      if (!visible.contains(name0)) 
      {
        visible.add(name0);
        return name0;
      }
      name0 = "_" + name + i;
      i++;
    }
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------