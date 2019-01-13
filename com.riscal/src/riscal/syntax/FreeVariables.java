// ---------------------------------------------------------------------------
// FreeVariables.java
// Determine the free variables of an expression.
// $Id: FreeVariables.java,v 1.3 2018/05/24 13:26:08 schreine Exp $
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

public class FreeVariables extends ASTVisitor.Base<Void>
{
  /***************************************************************************
   * Compute the free variables of an expression.
   * @param exp the expression.
   * @return its free variables (including constants).
   **************************************************************************/
  public static Set<String> compute(Expression exp)
  {
    FreeVariables visitor = new FreeVariables();
    exp.accept(visitor);
    return visitor.free;
  }
    
  /***************************************************************************
   * Compute the free variables of a quantified variable core
   * @param qvar the quantified variable core.
   * @return its free variables (including constants).
   **************************************************************************/
  public static Set<String> compute(QuantifiedVariableCore qvc)
  {
    FreeVariables visitor = new FreeVariables();
    qvc.accept(visitor);
    return visitor.free;
  }
  
  // the set of free variables computed
  private Set<String> free = new TreeSet<String>();
  
  // --------------------------------------------------------------------------
  //
  // auxiliaries
  //
  // --------------------------------------------------------------------------
  
  /***************************************************************************
   * Add free variables of quantifier
   * @param qvar the quantified variable
   * @param exp the body expression (possibly null)
   **************************************************************************/
  private void addFree(QuantifiedVariable qvar, Expression exp)
  {
    Set<String> free0 = exp == null ? new TreeSet<String>() : compute(exp);
    if (qvar.exp != null) free0.addAll(compute(qvar.exp));
    int n = qvar.qvcore.length;
    for (int i = n-1; i >= 0; i--)
    {
      QuantifiedVariableCore qvc = qvar.qvcore[i];
      free0.remove(qvc.ident.string);
      free0.addAll(compute(qvc));
    }
    free.addAll(free0);
  }
  
  /***************************************************************************
   * Compute the free variables of a type
   * @param type the type
   * @return its free variables (including constants).
   **************************************************************************/
  private static Set<String> compute(Type type)
  {
    FreeVariables visitor = new FreeVariables();
    type.accept(visitor);
    return visitor.free;
  }
  
  // --------------------------------------------------------------------------
  //
  // the visitor methods (adding free variables by their traversal)
  //
  // --------------------------------------------------------------------------
  
  public Void visit(IdentifierExp exp)
  {
    free.add(exp.ident.string);
    return null;
  }
  
  public Void visit(ChooseExp exp)
  {
    addFree(exp.qvar, null);
    return null;
  }
  
  public Void visit(ChooseInElseExp exp)
  {
    addFree(exp.qvar, exp.exp1);
    exp.exp2.accept(this);
    return null;
  }
  
  public Void visit(ChooseInExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(ExistsExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(ForallExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(LetExp exp)
  {
    Set<String> free0 = compute(exp.exp);
    int n = exp.binder.length;
    for (int i = n-1; i >= 0; i--)
    {
      Binder binder = exp.binder[i];
      free0.remove(binder.ident.string);
      free0.addAll(compute(binder.exp));
    }
    free.addAll(free0);
    return null;
  }
  
  public Void visit(LetParExp exp)
  {
    Set<String> free0 = compute(exp.exp);
    for (Binder binder : exp.binder)
      free0.remove(binder.ident.string);
    for (Binder binder : exp.binder)
      free0.addAll(compute(binder.exp));
    free.addAll(free0);
    return null;
  }
  
  public Void visit(MatchExp exp)
  {
    exp.exp.accept(this);
    for (PatternExpression pexp : exp.pexp)
    {
      Set<String> pfree = compute(pexp.exp);
      if (pexp instanceof PatternExpression.ApplicationPatternExp)
      {
        PatternExpression.ApplicationPatternExp pexp0 =
            (PatternExpression.ApplicationPatternExp)pexp;
        for (Parameter p : pexp0.params)
        {
          pfree.remove(p.ident.string);
          pfree.addAll(compute(p.type));
        }
      }
      free.addAll(pfree);
    }
    return null;
  }
  
  public Void visit(MaxExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(MinExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(NumberExp exp)
  {
    addFree(exp.qvar, null);
    return null;
  }
  
  public Void visit(ProductExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(SetBuilderExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
  
  public Void visit(SumExp exp)
  {
    addFree(exp.qvar, exp.exp);
    return null;
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------