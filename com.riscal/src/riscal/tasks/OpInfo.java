// ---------------------------------------------------------------------------
// OpInfo.java
// The relevant information about a parameterized operation.
// $Id: OpInfo.java,v 1.7 2018/05/23 15:31:16 schreine Exp $
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

import java.util.ArrayList;
import java.util.List;

import riscal.syntax.AST.Declaration;
import riscal.syntax.AST.Expression;
import riscal.syntax.AST.FunctionSpec;
import riscal.syntax.AST.Identifier;
import riscal.syntax.AST.Parameter;
import riscal.syntax.AST.Type;
import riscal.syntax.AST.Command;
import riscal.syntax.AST.Declaration.FunctionDefinition;
import riscal.syntax.AST.Declaration.PredicateDefinition;
import riscal.syntax.AST.Declaration.ProcedureDefinition;
import riscal.syntax.AST.Declaration.TheoremParamDefinition;
import riscal.syntax.AST.Expression.AndExp;
import riscal.syntax.AST.FunctionSpec.*;
import riscal.types.Environment.Symbol.*;

public class OpInfo
{
  public final FunctionSymbol symbol;
  public final Parameter[] param;
  public final Type type;
  public final FunctionSpec[] spec;
  public final Expression bodyexp;
  public final Command[] commands;     // may be null
  public final List<FunctionSpec> pre;
  public final Expression preFormula;  // may be null
  public final List<FunctionSpec> post;
  public final Expression postFormula; // may be null
  public final List<FunctionSpec> dec;
  public final Expression[] decTerms;  // may be null
  
  /***************************************************************************
   * Create information about operation
   * @param ident its name
   * @param param its parameter list
   * @param type its result type
   * @param spec its specification list
   * @param bodyexp its body expression
   * @param commands its commands (may be null)
   **************************************************************************/
  private OpInfo(Identifier ident, Parameter[] param, Type type, 
    FunctionSpec[] spec, Expression bodyexp, Command[] commands)
  {
    this.symbol = (FunctionSymbol)ident.getSymbol();
    this.param = param;
    this.type = type;
    this.spec = spec;
    this.bodyexp = bodyexp;
    this.commands = commands;
    pre = new ArrayList<FunctionSpec>();
    post = new ArrayList<FunctionSpec>();
    dec = new ArrayList<FunctionSpec>();
    Expression pre0 = null;
    Expression post0 = null;
    Expression[] decTerms0 = null;
    for (FunctionSpec s : spec)
    {
      if (s instanceof RequiresSpec)
      {
        RequiresSpec s0 = (RequiresSpec)s;
        pre.add(s0);
        if (pre0 == null)
          pre0 = s0.exp;
        else
          pre0 = new AndExp(pre0, s0.exp);
      }
      else if (s instanceof EnsuresSpec)
      {
        EnsuresSpec s0 = (EnsuresSpec)s;
        post.add(s0);
        if (post0 == null)
          post0 = s0.exp;
        else
          post0 = new AndExp(post0, s0.exp);
      }
      else if (s instanceof DecreasesSpec)
      {
        DecreasesSpec s0 = (DecreasesSpec)s;
        dec.add(s0);
        if (decTerms0 == null) decTerms0 = s0.exps;
      }
    }
    preFormula = pre0;
    postFormula = post0;
    decTerms = decTerms0;
  }
  
  /*************************************************************************
   * Get the relevant information about a parameterized operation.
   * @param decl the declaration of the operation.
   ********************************** **************************************/
  public static OpInfo getInfo(Declaration decl)
  {
    if (decl instanceof FunctionDefinition)
    {
      FunctionDefinition decl0 = (FunctionDefinition)decl;
      return new OpInfo(decl0.ident, decl0.param, decl0.type, decl0.spec, decl0.exp, null);
    }
    if (decl instanceof PredicateDefinition)
    {
      PredicateDefinition decl0 = (PredicateDefinition)decl;
      return new OpInfo(decl0.ident, decl0.param, new Type.BoolType(), decl0.spec, decl0.exp, null);
    }
    if (decl instanceof ProcedureDefinition)
    {
      ProcedureDefinition decl0 = (ProcedureDefinition)decl;
      Expression exp = decl0.exp;
      if (exp == null) 
      {
        exp = new Expression.UnitExp();
        exp.setType(new Type.UnitType());
      }
      return new OpInfo(decl0.ident, decl0.param, decl0.type, decl0.spec, exp, decl0.commands);
    }
    if (decl instanceof TheoremParamDefinition)
    {
      TheoremParamDefinition decl0 = (TheoremParamDefinition)decl;
      return new OpInfo(decl0.ident, decl0.param, new Type.BoolType(), decl0.spec, decl0.exp, null);
    }
    return null;
  }
  
  /*************************************************************************
   * Get the specification of a parameterized operation.
   * @param decl the declaration of the operation.
   ********************************** **************************************/
  public static FunctionSpec[] getSpec(Declaration decl)
  {
    if (decl instanceof FunctionDefinition)
    {
      FunctionDefinition decl0 = (FunctionDefinition)decl;
      return decl0.spec;
    }
    if (decl instanceof PredicateDefinition)
    {
      PredicateDefinition decl0 = (PredicateDefinition)decl;
      return decl0.spec;
    }
    if (decl instanceof ProcedureDefinition)
    {
      ProcedureDefinition decl0 = (ProcedureDefinition)decl;
      return decl0.spec;
    }
    if (decl instanceof TheoremParamDefinition)
    {
      TheoremParamDefinition decl0 = (TheoremParamDefinition)decl;
      return decl0.spec;
    }
    return null;
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------