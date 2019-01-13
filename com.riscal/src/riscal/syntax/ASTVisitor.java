// ---------------------------------------------------------------------------
// ASTVisitor.java
// Visitors to abstract syntax trees
// $Id: ASTVisitor.java,v 1.59 2018/06/14 13:22:44 schreine Exp schreine $
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
package riscal.syntax;

import riscal.syntax.AST.*;
import riscal.syntax.AST.Selector.*;
import riscal.syntax.AST.Command.*;
import riscal.syntax.AST.Declaration.*;
import riscal.syntax.AST.Expression.*;
import riscal.syntax.AST.LoopSpec.*;
import riscal.syntax.AST.PatternCommand.*;
import riscal.syntax.AST.PatternExpression.*;
import riscal.syntax.AST.QuantifiedVariableCore.*;
import riscal.syntax.AST.RecursiveIdentifier.*;
import riscal.syntax.AST.FunctionSpec.*;
import riscal.syntax.AST.Type.*;

// a visitor for abstract syntax trees
public interface ASTVisitor<T>
{
  // Selector
  public T visit(MapSelector ast);
  public T visit(RecordSelector ast);
  public T visit(TupleSelector ast);
  
  // Binder
  public T visit(Binder ast);
  
  // Command
  public T visit(AssertCommand ast);
  public T visit(AssignmentCommand ast);
  public T visit(ChooseCommand ast);
  public T visit(ChooseElseCommand ast);
  public T visit(ChooseDoCommand ast);
  public T visit(CommandSequence ast);
  public T visit(DoWhileCommand ast);
  public T visit(EmptyCommand ast);
  public T visit(ExpCommand exp);
  public T visit(ForCommand ast);
  public T visit(ForInCommand ast);
  public T visit(IfThenCommand ast);
  public T visit(IfThenElseCommand ast);
  public T visit(MatchCommand ast);
  public T visit(PrintCommand ast);
  public T visit(CheckCommand ast);
  public T visit(ValCommand ast);
  public T visit(VarCommand ast);
  public T visit(WhileCommand ast);
  
  // Declaration
  public T visit(FunctionDeclaration ast);
  public T visit(FunctionDefinition ast);
  public T visit(PredicateDeclaration ast);
  public T visit(PredicateDefinition ast);
  public T visit(TheoremParamDefinition ast);
  public T visit(ProcedureDeclaration ast);
  public T visit(ProcedureDefinition ast);
  public T visit(RecTypeDefinition ast);
  public T visit(EnumTypeDefinition ast);
  public T visit(TheoremDefinition ast);
  public T visit(PredicateValueDefinition ast);
  public T visit(TypeDefinition ast);
  public T visit(ValueDeclaration ast);
  public T visit(ValueDefinition ast);
  
  // Expression
  public T visit(AndExp ast);
  public T visit(ApplicationExp ast);
  public T visit(ArrayBuilderExp ast);
  public T visit(MapBuilderExp ast);
  public T visit(MapSelectionExp ast);
  public T visit(MapUpdateExp ast);
  public T visit(AssertExp ast);
  public T visit(BigUnionExp ast);
  public T visit(BigIntersectExp ast);
  public T visit(ChooseExp ast);
  public T visit(ChooseInElseExp ast);
  public T visit(ChooseInExp ast);
  public T visit(DividesExp ast);
  public T visit(EmptySetExp ast);
  public T visit(EnumeratedSetExp ast);
  public T visit(CartesianExp ast);
  public T visit(EqualsExp ast);
  public T visit(EquivExp ast);
  public T visit(ExistsExp ast);
  public T visit(FalseExp ast);
  public T visit(ForallExp ast);
  public T visit(GreaterEqualExp ast);
  public T visit(GreaterExp ast);
  public T visit(IdentifierExp ast);
  public T visit(IfThenElseExp ast);
  public T visit(ImpliesExp ast);
  public T visit(InSetExp ast);
  public T visit(IntersectExp ast);
  public T visit(IntervalExp ast);
  public T visit(MatchExp ast);
  public T visit(NumberLiteralExp ast);
  public T visit(LessEqualExp ast);
  public T visit(LessExp ast);
  public T visit(LetExp ast);
  public T visit(LetParExp ast);
  public T visit(MinusExp ast);
  public T visit(NegationExp ast);
  public T visit(FactorialExp ast);
  public T visit(NotEqualsExp ast);
  public T visit(NotExp ast);
  public T visit(NumberExp ast);
  public T visit(OrExp ast);
  public T visit(PlusExp ast);
  public T visit(PlusExpMult ast);
  public T visit(PowerExp ast);
  public T visit(PowersetExp ast);
  public T visit(Powerset1Exp ast);
  public T visit(Powerset2Exp ast);
  public T visit(PrintExp ast);
  public T visit(PrintInExp ast);
  public T visit(CheckExp ast);
  public T visit(RecApplicationExp ast);
  public T visit(RecIdentifierExp ast);
  public T visit(RecordExp ast);
  public T visit(RecordSelectionExp ast);
  public T visit(RecordUpdateExp ast);
  public T visit(RemainderExp ast);
  public T visit(SetBuilderExp ast);
  public T visit(SumExp ast);
  public T visit(ProductExp ast);
  public T visit(MinExp ast);
  public T visit(MaxExp ast);
  public T visit(SetSizeExp ast);
  public T visit(SubsetExp ast);
  public T visit(TimesExp ast);
  public T visit(TimesExpMult ast);
  public T visit(TrueExp ast);
  public T visit(TupleExp ast);
  public T visit(TupleSelectionExp ast);
  public T visit(TupleUpdateExp ast);
  public T visit(UnionExp ast);
  public T visit(UnitExp ast);
  public T visit(WithoutExp ast);
  
  // ExpressionIdentifier
  public T visit(ExpressionIdentifier ast);
  
  // Multiple
  public T visit(Multiple ast);
  
  // Identifier
  public T visit(Identifier ast);
  
  // Decimal
  public T visit(Decimal ast);
  
  // LoopSpec
  public T visit(DecreasesLoopSpec ast);
  public T visit(InvariantLoopSpec ast);
  
  // Parameter
  public T visit(Parameter ast);
  
  // PatternCommand
  public T visit(ApplicationPatternCommand ast);
  public T visit(DefaultPatternCommand ast);
  public T visit(IdentifierPatternCommand ast);
  
  // PatternExpression
  public T visit(ApplicationPatternExp ast);
  public T visit(DefaultPatternExp ast);
  public T visit(IdentifierPatternExp ast);
  
  // QuantifiedVariable
  public T visit(QuantifiedVariable ast);
  
  // QuantifiedVariableCore
  public T visit(IdentifierSetQuantifiedVar ast);
  public T visit(IdentifierTypeQuantifiedVar ast);
  
  // RecursiveTypeItem
  public T visit(RecursiveTypeItem ast);
  
  // RecursiveIdentifier
  public T visit(RecApplication ast);
  public T visit(RecIdentifier ast);
 
  // Spec
  public T visit(DecreasesSpec ast);
  public T visit(EnsuresSpec ast);
  public T visit(RequiresSpec ast);
  public T visit(ContractSpec ast);
  
  // Specification
  public T visit(Specification ast);
  
  // Type
  public T visit(ArrayType ast);
  public T visit(BoolType ast);
  public T visit(IdentifierType ast);
  public T visit(IntType ast);
  public T visit(MapType ast);
  public T visit(NatType ast);
  public T visit(RecordType ast);
  public T visit(RecursiveType ast);
  public T visit(SetType ast);
  public T visit(SubType ast);
  public T visit(TupleType ast);
  public T visit(UnitType ast);
   
  // a base class for implementing visitors
  public static class Base<T> implements ASTVisitor<T>
  { 
    public T visit(MapSelector sel)
    {
      sel.exp.accept(this);
      return null;
    }
    public T visit(TupleSelector sel)
    {
      sel.index.accept(this);
      return null;
    }
    public T visit(RecordSelector sel)
    {
      sel.ident.accept(this);
      return null;
    }
    
    // Binder
    public T visit(Binder binder)
    {
      binder.ident.accept(this);
      binder.exp.accept(this);
      return null;
    }

    // Command
    public T visit(AssertCommand command)
    {
      command.exp.accept(this);
      return null;
    }
    public T visit(AssignmentCommand command)
    {
      command.ident.accept(this);
      for (Selector sel : command.sels) sel.accept(this);
      command.exp.accept(this);
      return null;
    }     
    public T visit(ChooseCommand command)
    {
      command.qvar.accept(this);
      return null;
    }
    public T visit(ChooseElseCommand command)
    {
      command.qvar.accept(this);
      command.command1.accept(this);
      command.command2.accept(this);
      return null;
    }
    public T visit(ChooseDoCommand command)
    {
      command.qvar.accept(this);
      for (LoopSpec s : command.spec) s.accept(this);
      command.command.accept(this);
      return null;
    }
    public T visit(CommandSequence command)
    {
      for (Command c : command.commands) c.accept(this);
      return null;
    }
    public T visit(DoWhileCommand command)
    {
      command.command.accept(this);
      command.exp.accept(this);
      for (LoopSpec s : command.spec) s.accept(this);
      return null;
    }
    public T visit(EmptyCommand command)
    {
      return null;
    }
    public T visit(ExpCommand command)
    {
      command.exp.accept(this);
      return null;
    }
    public T visit(ForCommand command)
    {
      command.command1.accept(this);
      command.exp.accept(this);
      command.command2.accept(this);
      for (LoopSpec s : command.spec) s.accept(this);
      command.command3.accept(this);
      return null;
    }
    public T visit(ForInCommand command)
    {
      command.qvar.accept(this);
      for (LoopSpec s : command.spec) s.accept(this);
      command.command.accept(this);
      return null;
    }
    public T visit(IfThenCommand command)
    {
      command.exp.accept(this);
      command.command.accept(this);
      return null;
    }
    public T visit(IfThenElseCommand command)
    {
      command.exp.accept(this);
      command.command1.accept(this);
      command.command2.accept(this);
      return null;
    }
    public T visit(MatchCommand command)
    {
      command.exp.accept(this);
      for (PatternCommand p : command.pcommand) p.accept(this);
      return null;
    }  
    public T visit(PrintCommand command)
    {
      for (Expression e : command.exps) e.accept(this);
      return null;
    }
    public T visit(CheckCommand command)
    {
      command.ident.accept(this);
      if (command.exp != null) command.exp.accept(this);
      return null;
    }
    public T visit(ValCommand command)
    {
      command.ident.accept(this);
      if (command.type != null) command.type.accept(this);
      command.exp.accept(this);
      return null;
    }
    public T visit(VarCommand command)
    {
      command.ident.accept(this);
      command.type.accept(this);
      if (command.exp != null) command.exp.accept(this);
      return null;
    }
    public T visit(WhileCommand command)
    {
      command.exp.accept(this);
      for (LoopSpec s : command.spec) s.accept(this);
      command.command.accept(this);
      return null;
    }
    
    // Declaration
    public T visit(FunctionDeclaration decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      decl.type.accept(this);
      return null;
    }
    public T visit(FunctionDefinition decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      decl.type.accept(this);
      for (FunctionSpec s : decl.spec) s.accept(this);  
      decl.exp.accept(this);
      return null;
    }
    public T visit(PredicateDeclaration decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      return null;
    }
    public T visit(PredicateDefinition decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      for (FunctionSpec s : decl.spec) s.accept(this);    
      decl.exp.accept(this);
      return null;
    }
    public T visit(TheoremParamDefinition decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      for (FunctionSpec s : decl.spec) s.accept(this);    
      decl.exp.accept(this);
      return null;
    }
    public T visit(ProcedureDeclaration decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      decl.type.accept(this);
      return null;
    }
    public T visit(ProcedureDefinition decl)
    {
      decl.ident.accept(this);
      for (Parameter p : decl.param) p.accept(this);
      decl.type.accept(this);
      for (FunctionSpec s : decl.spec) s.accept(this);  
      for (Command c : decl.commands) c.accept(this);
      if (decl.exp != null) decl.exp.accept(this);
      return null;
    }
    public T visit(RecTypeDefinition decl)
    {
      decl.exp.accept(this);
      for (RecursiveTypeItem ritem : decl.ritems) ritem.accept(this);
      return null;
    }
    public T visit(EnumTypeDefinition decl)
    {
      decl.ritem.accept(this);
      return null;
    }
    public T visit(TheoremDefinition decl)
    {
      decl.ident.accept(this);
      decl.exp.accept(this);
      return null;
    }
    public T visit(PredicateValueDefinition decl)
    {
      decl.ident.accept(this);
      decl.exp.accept(this);
      return null;
    }
    public T visit(TypeDefinition decl)
    {
      decl.ident.accept(this);
      decl.type.accept(this);
      if (decl.exp != null) decl.exp.accept(this);
      return null;
    }
    public T visit(ValueDeclaration decl)
    {
      decl.ident.accept(this);
      return null;
    }
    public T visit(ValueDefinition decl)
    {
      decl.ident.accept(this);
      if (decl.type != null) decl.type.accept(this);
      decl.exp.accept(this);
      return null;
    }

    // Expression
    public T visit(AndExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(ApplicationExp exp)
    {
      exp.ident.accept(this);
      for (Expression e : exp.exps) e.accept(this);
      return null;
    }
    public T visit(ArrayBuilderExp exp)
    {
      exp.type.accept(this);
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(MapBuilderExp exp)
    {
      exp.type1.accept(this);
      exp.type2.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(MapSelectionExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(MapUpdateExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      exp.exp3.accept(this);
      return null;
    }
    public T visit(AssertExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(BigUnionExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(BigIntersectExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(ChooseExp exp)
    {
      exp.qvar.accept(this);
      return null;
    }
    public T visit(ChooseInElseExp exp)
    {
      exp.qvar.accept(this);
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(ChooseInExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }    
    public T visit(DividesExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(EmptySetExp exp)
    {
      exp.type.accept(this);
      return null;
    }
    public T visit(EnumeratedSetExp exp)
    {
      for (Expression e : exp.exps) e.accept(this);
      return null;
    }
    public T visit(CartesianExp exp)
    {
      for (Expression e : exp.exps) e.accept(this);
      return null;
    }
    public T visit(EqualsExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(EquivExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(ExistsExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(FalseExp exp)
    {
      return null;
    }
    public T visit(ForallExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(GreaterEqualExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(GreaterExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(IdentifierExp exp)
    {
      return null;
    }
    public T visit(IfThenElseExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      exp.exp3.accept(this);
      return null;
    }
    public T visit(ImpliesExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }    
    public T visit(InSetExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(IntersectExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(IntervalExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(NumberLiteralExp exp)
    {
      return null;
    }
    public T visit(LessEqualExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(LessExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(LetExp exp)
    {
      for (Binder b : exp.binder) b.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(LetParExp exp)
    {
      for (Binder b : exp.binder) b.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(MatchExp exp)
    {
      exp.exp.accept(this);
      for (PatternExpression p : exp.pexp) p.accept(this);
      return null;
    } 
    public T visit(MinusExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(NegationExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(FactorialExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(NotEqualsExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(NotExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(NumberExp exp)
    {
      exp.qvar.accept(this);
      return null;
    }
    public T visit(OrExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(PlusExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(PlusExpMult exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(PowerExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    } 
    public T visit(PowersetExp exp)
    {
      exp.exp.accept(this);
      return null;
    } 
    public T visit(Powerset1Exp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    } 
    public T visit(Powerset2Exp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      exp.exp3.accept(this);
      return null;
    } 
    public T visit(PrintExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(PrintInExp exp)
    {
      for (Expression e : exp.exps) e.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(CheckExp exp)
    {
      exp.ident.accept(this);
      if (exp.exp != null) exp.exp.accept(this);
      return null;
    }
    public T visit(RecApplicationExp exp)
    {
      exp.ident1.accept(this);
      exp.ident2.accept(this);
      for (Expression e : exp.exps) e.accept(this);
      return null;
    }
    public T visit(RecIdentifierExp exp)
    {
      exp.ident1.accept(this);
      exp.ident2.accept(this);
      return null;
    }
    public T visit(RecordExp exp)
    {
      for (ExpressionIdentifier eident : exp.eidents) eident.accept(this);
      return null;
    }
    public T visit(RecordSelectionExp exp)
    {
      exp.exp.accept(this);
      exp.ident.accept(this);
      return null;
    }
    public T visit(RecordUpdateExp exp)
    {
      exp.exp1.accept(this);
      exp.ident.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(RemainderExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(SetBuilderExp exp)
    {
      exp.exp.accept(this);
      exp.qvar.accept(this);
      return null;
    }
    public T visit(SumExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(ProductExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(MinExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(MaxExp exp)
    {
      exp.qvar.accept(this);
      exp.exp.accept(this);
      return null;
    }
    public T visit(SetSizeExp exp)
    {
      exp.exp.accept(this);
      return null;
    }
    public T visit(SubsetExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }      
    public T visit(TimesExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(TimesExpMult exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(TrueExp exp)
    {
      return null;
    }
    public T visit(TupleExp exp)
    {
      for (Expression e : exp.exps) e.accept(this);
      return null;
    }
    public T visit(TupleSelectionExp exp)
    {
      exp.exp.accept(this);
      exp.index.accept(this);
      return null;
    }
    public T visit(TupleUpdateExp exp)
    {
      exp.exp1.accept(this);
      exp.index.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(UnionExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }
    public T visit(UnitExp exp)
    {
      return null;
    }
    public T visit(WithoutExp exp)
    {
      exp.exp1.accept(this);
      exp.exp2.accept(this);
      return null;
    }

    // ExpressionIdentifier
    public T visit(ExpressionIdentifier eident)
    {
      eident.ident.accept(this);
      eident.exp.accept(this);
      return null;
    }      

    // Identifier
    public T visit(Identifier ident)
    {
      return null;
    }
    
    // Int
    public T visit(Decimal v)
    {
      return null;
    }
    
    // LoopSpec
    public T visit(DecreasesLoopSpec lspec)
    {
      for (Expression e : lspec.exps) e.accept(this);
      return null;       
    }
    public T visit(InvariantLoopSpec lspec)
    {
      lspec.exp.accept(this);
      return null;       
    }

    // Multiple
    public T visit(Multiple multiple)
    {
      multiple.accept(this);
      return null;       
    }
    
    // Parameter
    public T visit(Parameter param)
    {
      param.ident.accept(this);
      param.type.accept(this);
      return null;       
    }
    
    // PatternCommand
    public T visit(ApplicationPatternCommand pcommand)
    {
      pcommand.ident.accept(this);
      for (Parameter p : pcommand.params) p.accept(this);
      pcommand.command.accept(this);
      return null;
    }
    public T visit(DefaultPatternCommand pcommand)
    {
      pcommand.command.accept(this);
      return null;
    }
    public T visit(IdentifierPatternCommand pcommand)
    {
      pcommand.ident.accept(this);
      pcommand.command.accept(this);
      return null;
    }

    // PatternExpression
    public T visit(ApplicationPatternExp pexp)
    {
      pexp.ident.accept(this);
      for (Parameter p : pexp.params) p.accept(this);
      pexp.exp.accept(this);
      return null;
    }
    public T visit(DefaultPatternExp pexp)
    {
      pexp.exp.accept(this);
      return null;
    }
    public T visit(IdentifierPatternExp pexp)
    {
      pexp.ident.accept(this);
      pexp.exp.accept(this);
      return null;
    }
    
    // QuantifiedVariable
    public T visit(QuantifiedVariable qvar)
    {
      for (QuantifiedVariableCore q : qvar.qvcore) q.accept(this);
      if (qvar.exp != null) qvar.exp.accept(this);
      return null;
    }
    
    // QuantifiedVariableCore
    public T visit(IdentifierSetQuantifiedVar qvcore)
    {
      qvcore.ident.accept(this);
      qvcore.exp.accept(this);
      return null;       
    }
    public T visit(IdentifierTypeQuantifiedVar qvcore)
    {
      qvcore.ident.accept(this);
      qvcore.type.accept(this);
      return null;       
    }

    // RecursiveIdentifier
    public T visit(RecApplication rident)
    {
      rident.ident.accept(this);
      for (Type type : rident.types) type.accept(this);
      return null;
    }
    public T visit(RecIdentifier rident)
    {
      rident.ident.accept(this);
      return null;
    }
    
    // RecursiveTypeItem
    public T visit(RecursiveTypeItem ritem)
    {
      ritem.ident.accept(this);
      for (RecursiveIdentifier rident : ritem.ridents) rident.accept(this);
      return null;
    }
    
    // Spec
    public T visit(DecreasesSpec spec)
    {
      for (Expression e : spec.exps) e.accept(this);
      return null;       
    }
    public T visit(EnsuresSpec spec)
    {
      spec.exp.accept(this);
      return null;       
    }
    public T visit(RequiresSpec spec)
    {
      spec.exp.accept(this);
      return null;       
    }
    public T visit(ContractSpec spec)
    {
      return null;       
    }
    
    // Specification
    public T visit(Specification spec)
    {
      for (Declaration d : spec.declarations) 
        d.accept(this);
      return null;
    }
    
    // Type (do not visit canonical types)
    public T visit(ArrayType type)
    {
      if (type.isCanonical()) return null;
      type.exp.accept(this);
      type.type.accept(this);
      return null;
    }
    public T visit(BoolType ast)
    {
      return null;
    }
    public T visit(IdentifierType type)
    {
      if (type.isCanonical()) return null;
      type.ident.accept(this);
      return null;
    }
    public T visit(IntType type)
    {
      if (type.isCanonical()) return null;
      type.exp1.accept(this);
      type.exp2.accept(this);
      return null;
    }
    public T visit(MapType type)
    {
      if (type.isCanonical()) return null;
      type.type1.accept(this);
      type.type2.accept(this);
      return null;
    }     
    public T visit(NatType type)
    {
      if (type.isCanonical()) return null;
      type.exp.accept(this);
      return null;
    }
    public T visit(RecordType type)
    {
      if (type.isCanonical()) return null;
      for (Parameter p : type.param) p.accept(this);
      return null;
    }
    public T visit(RecursiveType type)
    {
      if (type.isCanonical()) return null;
      type.exp.accept(this);
      type.ident.accept(this);
      for (RecursiveTypeItem ritem : type.ritems) ritem.accept(this);
      return null;
    }
    public T visit(SetType type)
    {
      if (type.isCanonical()) return null;
      type.type.accept(this);
      return null;
    }    
    public T visit(SubType type)
    {
      if (type.isCanonical()) return null;
      type.ident.accept(this);
      type.type.accept(this);
      // do not visit predicate symbol
      return null;
    } 
    public T visit(TupleType type)
    {
      if (type.isCanonical()) return null;
      for (Type t : type.types) t.accept(this);
      return null;
    }   
    public T visit(UnitType type)
    {
      return null;
    }
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------