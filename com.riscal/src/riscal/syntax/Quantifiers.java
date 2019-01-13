// ---------------------------------------------------------------------------
// Quantifiers.java
// Remove quantified variables where not needed
// $Id: Quantifiers.java,v 1.3 2018/05/24 13:35:56 schreine Exp $
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
package riscal.syntax;

import java.util.*;

import riscal.syntax.AST.*;
import riscal.syntax.AST.Expression.*;

public class Quantifiers extends ASTCloner
{
  /**************************************************************************
   * Remove unneeded quantified variables from an expression.
   * @param exp the expression
   * @return the processed expression
   *************************************************************************/
  public static Expression minimize(Expression exp)
  {
    ASTCloner cloner = new Quantifiers();
    return (Expression)exp.accept(cloner);
  }
  
  // ------------------------------------------------------------------------
  // 
  // auxiliaries
  // 
  // ------------------------------------------------------------------------

  /***************************************************************************
   * Remove quantified variables.
   * @param qvar the list of quantified variables.
   * @param exp the body expression.
   * @return a possibly shorter list (null, if no variable remains)
   **************************************************************************/
  private QuantifiedVariable minimize(QuantifiedVariable qvar, Expression exp)
  {
    Set<String> free = FreeVariables.compute(exp);
    Expression qexp = qvar.exp == null ? null : minimize(qvar.exp);
    if (qexp != null) free.addAll(FreeVariables.compute(qexp));
    List<QuantifiedVariableCore> qvc = new LinkedList<QuantifiedVariableCore>();
    int n = qvar.qvcore.length;
    for (int i = n-1; i >= 0; i--)
    {
      QuantifiedVariableCore qvc0 = minimize(qvar.qvcore[i]);
      String name = qvc0.ident.string;
      // must not remove "x in S" condition (might be false)
      if (free.contains(name) || 
          qvc0 instanceof QuantifiedVariableCore.IdentifierSetQuantifiedVar) 
      { 
        qvc.add(0, qvc0);
        free.remove(name);
        free.addAll(FreeVariables.compute(qvc0));
      }
    }
    if (qvc.isEmpty()) return null;
    return new QuantifiedVariable(qvc, qexp);
  }
  
  /***************************************************************************
   * Minimize quantified variable core.
   * @param qvc the quantified variable core.
   * @return the minimized version of the core.
   **************************************************************************/
  private QuantifiedVariableCore minimize(QuantifiedVariableCore qvc)
  {
    if (qvc instanceof QuantifiedVariableCore.IdentifierTypeQuantifiedVar)
    {
      QuantifiedVariableCore.IdentifierTypeQuantifiedVar qvc0 =
          (QuantifiedVariableCore.IdentifierTypeQuantifiedVar)qvc;
      return new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(qvc0.ident,
          minimize(qvc0.type));
    }
    else if (qvc instanceof QuantifiedVariableCore.IdentifierSetQuantifiedVar)
    {
      QuantifiedVariableCore.IdentifierSetQuantifiedVar qvc0 =
          (QuantifiedVariableCore.IdentifierSetQuantifiedVar)qvc;
      return new QuantifiedVariableCore.IdentifierSetQuantifiedVar(qvc0.ident,
          minimize(qvc0.exp));
    }
    return null;
  }
  
  /**************************************************************************
   * Remove unneeded quantified variables from a type.
   * @param type the type.
   * @return the processed type.
   *************************************************************************/
  public static Type minimize(Type type)
  {
    ASTCloner cloner = new Quantifiers();
    return (Type)type.accept(cloner);
  }
  
  // ------------------------------------------------------------------------
  // 
  // the visitor methods (removing quantified variables by their traversal)
  //
  // ChooseExp and SetBuilderExp: cannot be minimized because result
  // value depends on values of quantified variables
  //
  // MatchExp: we ignore it, since there is no minimization possible
  
  // NumberExp ProductExp, SumExp: we ignore them, because 
  // their handling requires some work which does not really pay off
  // 
  // ------------------------------------------------------------------------
  
  public Expression visit(ChooseInElseExp exp)
  {
    Expression exp1 = minimize(exp.exp1);
    Expression exp2 = minimize(exp.exp2);
    QuantifiedVariable qvar = minimize(exp.qvar, exp1);
    if (qvar == null) return exp1;
    return new ChooseInElseExp(qvar, exp1, exp2);
  }
  
  public Expression visit(ChooseInExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    QuantifiedVariable qvar = minimize(exp.qvar, exp0);
    if (qvar == null) return exp0;
    return new ChooseInExp(qvar, exp0);
  }
  
  public Expression visit(ExistsExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    QuantifiedVariable qvar = minimize(exp.qvar, exp0);
    if (qvar == null) return exp0;
    return new ExistsExp(qvar, exp0);
  }
  
  public Expression visit(ForallExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    QuantifiedVariable qvar = minimize(exp.qvar, exp0);
    if (qvar == null) return exp0;
    return new ForallExp(qvar, exp0);
  }
  
  public Expression visit(LetExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    Set<String> free = FreeVariables.compute(exp0);
    List<Binder> binders = new LinkedList<Binder>();
    int n = exp.binder.length;
    for (int i = n-1; i >= 0; i--)
    {
      Binder binder = exp.binder[i];
      String name = binder.ident.string;
      if (free.contains(name)) 
      { 
        Binder binder0 = new Binder(binder.ident, minimize(binder.exp));
        binders.add(0, binder0);
        free.remove(name);
        free.addAll(FreeVariables.compute(binder0.exp));
      }
    }
    if (binders.isEmpty()) return exp0;
    return new LetExp(binders, exp0);
  }
  
  public Expression visit(LetParExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    Set<String> free = FreeVariables.compute(exp0);
    List<Binder> binders = new ArrayList<Binder>();
    for (Binder binder : exp.binder)
    {
      String name = binder.ident.string;
      if (free.contains(name)) 
      {
        Binder binder0 = new Binder(binder.ident, minimize(binder.exp));
        binders.add(binder0);
      }
    }
    if (binders.isEmpty()) return exp0;
    return new LetParExp(binders, exp0);
  }
  
  public Expression visit(MaxExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    QuantifiedVariable qvar = minimize(exp.qvar, exp0);
    if (qvar == null) return exp0;
    return new MaxExp(qvar, exp0);
  }
  
  public Expression visit(MinExp exp)
  {
    Expression exp0 = minimize(exp.exp);
    QuantifiedVariable qvar = minimize(exp.qvar, exp0);
    if (qvar == null) return exp0;
    return new MinExp(qvar, exp0);
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------