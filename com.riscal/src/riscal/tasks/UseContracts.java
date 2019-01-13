// ---------------------------------------------------------------------------
// UseContracts.java
// Replace calls of modular functions by contracts.
// $Id: UseContracts.java,v 1.10 2018/06/07 10:55:55 schreine Exp $
//
// Author: Wolfgang Schreiner <Wolfgang.Schreiner@risc.jku.at>
// Copyright (C) 2017-, Research Institute for Symbolic Computation (RISC)
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

import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.types.*;
import riscal.types.Environment.Symbol.*;

import java.util.*;

public class UseContracts extends ASTCloner
{
  /***************************************************************************
   * Replace calls of modular functions in expression by contracts.
   * @param exp the expression to be transformed.
   * @param fun the function in which this expression occurs
   * @param callers the caller map.
   * @return the transformed expression.
   **************************************************************************/
  public static Expression execute(Expression exp, 
    FunctionSymbol fun, Recursion.CallMap callers)
  {
    Expression exp0 = (Expression)exp.accept(new UseContracts(fun, callers));
    exp0.setPosition(exp.getPosition());
    exp0.setType(exp.getType());
    return exp0;
  }
  
  private FunctionSymbol current;        // the current function
  private Recursion.CallMap callers; // the call map of the current specification
  
  private UseContracts(FunctionSymbol current, Recursion.CallMap callers)
  {
    this.current = current;
    this.callers = callers;
  }
  
  public Expression visit(Expression.ApplicationExp exp)
  {
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    return transform(exp.ident, exps0);
  }
  
  /***************************************************************************
   * Transform function application.
   * @param fident the identifier of the function.
   * @param args the arguments of the function.
   * @return the transformed expression.
   **************************************************************************/
  public Expression transform(Identifier fident, List<Expression> args)
  {
    FunctionSymbol fun = (FunctionSymbol)fident.getSymbol();
    Declaration def = fun.getDefinition();

    // replace application of operation by its contract if
    // - the operation is a procedure OR
    // - the operation is marked as "opaque" OR
    // - the current function is called recursively by this operation
    boolean doit = 
       def instanceof Declaration.ProcedureDefinition || 
       callers.calls(fun, current);
    if (!doit)
    {
      FunctionSpec[] spec = OpInfo.getSpec(def);
      for (FunctionSpec s : spec)
      {
        doit = s instanceof FunctionSpec.ContractSpec;
        if (doit) break;
      }
    }
    if (!doit) return new Expression.ApplicationExp(fident, args);
    
    // replace application by choice of value satisfying the operation's postcondition
    OpInfo info = OpInfo.getInfo(def);
    Expression postFormula = info.postFormula;
    if (postFormula == null) postFormula = new Expression.TrueExp();
    int n = info.param.length;
    List<Binder> binders = new ArrayList<Binder>(n);
    for (int i=0; i<n; i++)
    {
      Parameter p = info.param[i];
      Binder b = new Binder(new Identifier(p.ident.string), args.get(i));
      binders.add(b);
    }
    Identifier ident = new Identifier(TypeChecker.resultName);
    List<QuantifiedVariableCore> qv0 = new ArrayList<QuantifiedVariableCore>();
    qv0.add(new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(ident, info.type));
    QuantifiedVariable qvar = new QuantifiedVariable(qv0, postFormula);
    Expression choice = new Expression.ChooseExp(qvar);
    if (n == 0) return choice;
    return new Expression.LetParExp(binders, choice);
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------
