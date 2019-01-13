// ---------------------------------------------------------------------------
// ASTVisitor.java
// A base class for syntax tree cloning.
// $Id: ASTCloner.java,v 1.46 2018/06/14 13:22:44 schreine Exp schreine $
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

import java.util.*;

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

public class ASTCloner extends ASTVisitor.Base<AST>
{
  // Selector
  public Selector visit(MapSelector sel)
  {
    Expression exp0 = (Expression)sel.exp.accept(this);
    return new MapSelector(exp0);
  }
  public Selector visit(TupleSelector sel)
  {
    Decimal index0 = (Decimal)sel.index.accept(this);
    return new TupleSelector(index0);
  }
  public Selector visit(RecordSelector sel)
  {
    Identifier ident0 = (Identifier)sel.ident.accept(this);
    return new RecordSelector(ident0);
  }

  // Binder
  public Binder visit(Binder binder)
  {
    Identifier ident0 = (Identifier)binder.ident.accept(this);
    Expression exp0 = (Expression)binder.exp.accept(this);
    return new Binder(ident0, exp0);
  } 
  
  // Command
  public Command visit(AssertCommand command)
  {
    Expression exp0 = (Expression)command.exp.accept(this);
    return new AssertCommand(exp0);
  }
  public Command visit(AssignmentCommand command)
  {
    Identifier ident0 = (Identifier)command.ident.accept(this);
    List<Selector> sels0 = new ArrayList<Selector>(command.sels.length);
    for (Selector s : command.sels) sels0.add((Selector)s.accept(this));
    Expression exp0 = (Expression)command.exp.accept(this);
    return new AssignmentCommand(ident0, sels0, exp0);
  }     
  public Command visit(ChooseCommand command)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)command.qvar.accept(this);
    return new ChooseCommand(qvar0);
  }
  public Command visit(ChooseElseCommand command)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)command.qvar.accept(this);
    Command command10 = (Command)command.command1.accept(this);
    Command command20 = (Command)command.command2.accept(this);
    return new ChooseElseCommand(qvar0, command10, command20);
  }
  public Command visit(ChooseDoCommand command)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)command.qvar.accept(this);
    List<LoopSpec> spec0 = new ArrayList<LoopSpec>(command.spec.length);
    for (LoopSpec s : command.spec) spec0.add((LoopSpec)s.accept(this));
    Command command0 = (Command)command.command.accept(this);
    return new ChooseDoCommand(qvar0, spec0, command0);
  }
  public Command visit(CommandSequence command)
  {
    List<Command> commands0 = new ArrayList<Command>(command.commands.length);
    for (Command c : command.commands) commands0.add((Command)c.accept(this));
    return new CommandSequence(commands0);
  }
  public Command visit(DoWhileCommand command)
  {
    Command command0 = (Command)command.command.accept(this);
    Expression exp0 = (Expression)command.exp.accept(this);
    List<LoopSpec> spec0 = new ArrayList<LoopSpec>(command.spec.length);
    for (LoopSpec s : command.spec) spec0.add((LoopSpec)s.accept(this));
    return new DoWhileCommand(command0, exp0, spec0);
  }
  public Command visit(EmptyCommand command)
  {
    return new EmptyCommand();
  }
  public Command visit(ExpCommand command)
  {
    Expression exp0 = (Expression)command.exp.accept(this);
    return new ExpCommand(exp0);
  }
  public Command visit(ForCommand command)
  {
    Command command10 = (Command)command.command1.accept(this);
    Expression exp0 = (Expression)command.exp.accept(this);
    Command command20 = (Command)command.command2.accept(this);
    List<LoopSpec> spec0 = new ArrayList<LoopSpec>(command.spec.length);
    for (LoopSpec s : command.spec) spec0.add((LoopSpec)s.accept(this));
    Command command30 = (Command)command.command3.accept(this);
    return new ForCommand(command10, exp0, command20, spec0, command30);
  }
  public Command visit(ForInCommand command)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)command.qvar.accept(this);
    List<LoopSpec> spec0 = new ArrayList<LoopSpec>(command.spec.length);
    for (LoopSpec s : command.spec) spec0.add((LoopSpec)s.accept(this));
    Command command0 = (Command)command.command.accept(this);
    return new ForInCommand(qvar0, spec0, command0);
  }
  public Command visit(IfThenCommand command)
  {
    Expression exp0 = (Expression)command.exp.accept(this);
    Command command0 = (Command)command.command.accept(this);
    return new IfThenCommand(exp0, command0);
  }
  public Command visit(IfThenElseCommand command)
  {
    Expression exp0 = (Expression)command.exp.accept(this);
    Command command10 = (Command)command.command1.accept(this);
    Command command20 = (Command)command.command2.accept(this);
    return new IfThenElseCommand(exp0, command10, command20);
  }
  public Command visit(MatchCommand command)
  {
    Expression exp0 = (Expression)command.exp.accept(this);
    List<PatternCommand> pcommand0 = new ArrayList<PatternCommand>(command.pcommand.length);
    for (PatternCommand p : command.pcommand) pcommand0.add((PatternCommand)p.accept(this));
    return new MatchCommand(exp0, pcommand0);
  }  
  public Command visit(PrintCommand command)
  {
    List<Expression> exps0 = new ArrayList<Expression>(command.exps.length);
    for (Expression e : command.exps) exps0.add((Expression)e.accept(this));
    return new PrintCommand(command.string, exps0);
  }
  public Command visit(CheckCommand command)
  {
    Identifier ident0 = (Identifier)command.ident.accept(this);
    Expression exp0 = command.exp == null ? null : (Expression)command.exp.accept(this);
    return new CheckCommand(ident0, exp0);
  }
  public Command visit(ValCommand command)
  {
    Identifier ident0 = (Identifier)command.ident.accept(this);
    Type type0 = command.type == null ? null : (Type)command.type.accept(this);
    Expression exp0 = (Expression)command.exp.accept(this);
    return new ValCommand(ident0, type0, exp0);
  }
  public Command visit(VarCommand command)
  {
    Identifier ident0 = (Identifier)command.ident.accept(this);
    Type type0 = (Type)command.type.accept(this);
    Expression exp0 = 
        command.exp == null ? null : (Expression)command.exp.accept(this);
    return new VarCommand(ident0, type0, exp0);
  }
  public Command visit(WhileCommand command)
  {
    Expression exp0 = (Expression)command.exp.accept(this);
    List<LoopSpec> spec0 = new ArrayList<LoopSpec>(command.spec.length);
    for (LoopSpec s : command.spec) spec0.add((LoopSpec)s.accept(this));
    Command command0 = (Command)command.command.accept(this);
    return new WhileCommand(exp0, spec0, command0);
  }
  
  // Declaration
  public Declaration visit(FunctionDeclaration decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    Type type0 = (Type)decl.type.accept(this);
    return new FunctionDeclaration(decl.multiple, ident0, param0, type0);
  }
  public Declaration visit(FunctionDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    Type type0 = (Type)decl.type.accept(this);
    List<FunctionSpec> spec0 = new ArrayList<FunctionSpec>(decl.spec.length);
    for (FunctionSpec s : decl.spec) spec0.add((FunctionSpec)s.accept(this));   
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new FunctionDefinition(decl.multiple, ident0, param0, type0, spec0, exp0);
  }
  public Declaration visit(PredicateDeclaration decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    return new PredicateDeclaration(decl.multiple, ident0, param0);
  }
  public Declaration visit(PredicateDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    List<FunctionSpec> spec0 = new ArrayList<FunctionSpec>(decl.spec.length);
    for (FunctionSpec s : decl.spec) spec0.add((FunctionSpec)s.accept(this)); 
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new PredicateDefinition(decl.multiple, ident0, param0, spec0, exp0);
  }
  public Declaration visit(TheoremParamDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    List<FunctionSpec> spec0 = new ArrayList<FunctionSpec>(decl.spec.length);
    for (FunctionSpec s : decl.spec) spec0.add((FunctionSpec)s.accept(this)); 
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new TheoremParamDefinition(decl.multiple, ident0, param0, spec0, exp0);
  }
  public Declaration visit(ProcedureDeclaration decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    Type type0 = (Type)decl.type.accept(this);
    return new ProcedureDeclaration(decl.multiple, ident0, param0, type0);
  }
  public Declaration visit(ProcedureDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    List<Parameter> param0 = new ArrayList<Parameter>(decl.param.length);
    for (Parameter p : decl.param) param0.add((Parameter)p.accept(this));
    Type type0 = (Type)decl.type.accept(this);
    List<FunctionSpec> spec0 = new ArrayList<FunctionSpec>(decl.spec.length);
    for (FunctionSpec s : decl.spec) spec0.add((FunctionSpec)s.accept(this));  
    List<Command> commands0 = new ArrayList<Command>(decl.commands.length);
    for (Command c : decl.commands) commands0.add((Command)c.accept(this));
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new ProcedureDefinition(decl.multiple, ident0, param0, type0, spec0, commands0, exp0);
  }
  public Declaration visit(RecTypeDefinition decl)
  {
    Expression exp0 = (Expression)decl.exp.accept(this);
    List<RecursiveTypeItem> ritems0 = 
        new ArrayList<RecursiveTypeItem>(decl.ritems.length);
    for (RecursiveTypeItem ritem : decl.ritems)
      ritems0.add((RecursiveTypeItem)ritem.accept(this));
    return new RecTypeDefinition(exp0, ritems0);
  }
  public Declaration visit(TheoremDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new TheoremDefinition(ident0, exp0);
  }
  public Declaration visit(PredicateValueDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new PredicateValueDefinition(ident0, exp0);
  }
  public Declaration visit(TypeDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    Type type0 = (Type)decl.type.accept(this);
    Expression exp0 = decl.exp == null ? null : (Expression)decl.exp.accept(this);
    return new TypeDefinition(ident0, type0, exp0);
  }
  public Declaration visit(ValueDeclaration decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    return new ValueDeclaration(ident0);
  }
  public Declaration visit(ValueDefinition decl)
  {
    Identifier ident0 = (Identifier)decl.ident.accept(this);
    Type type0 = decl.type == null ? null : (Type)decl.type.accept(this);
    Expression exp0 = (Expression)decl.exp.accept(this);
    return new ValueDefinition(ident0, type0, exp0);
  }
  
  // Expression
  public Expression visit(AndExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new AndExp(exp10, exp20);
  }
  public Expression visit(ApplicationExp exp)
  {
    Identifier ident0 = (Identifier)exp.ident.accept(this);
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    return new ApplicationExp(ident0, exps0);
  }
  public Expression visit(ArrayBuilderExp exp)
  {
    Type type0 = (Type)exp.type.accept(this);
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new ArrayBuilderExp(type0, exp10, exp20);
  }
  public Expression visit(AssertExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new AssertExp(exp10, exp20);
  }
  public Expression visit(BigUnionExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new BigUnionExp(exp0);
  }
  public Expression visit(BigIntersectExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new BigIntersectExp(exp0);
  }
  public Expression visit(ChooseExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    return new ChooseExp(qvar0);
  }
  public Expression visit(ChooseInElseExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new ChooseInElseExp(qvar0, exp10, exp20);
  }
  public Expression visit(ChooseInExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new ChooseInExp(qvar0, exp0);
  }    
  public Expression visit(DividesExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new DividesExp(exp10, exp20);
  }
  public Expression visit(EmptySetExp exp)
  {
    Type type0 = (Type)exp.type.accept(this);
    return new EmptySetExp(type0);
  }
  public Expression visit(EnumeratedSetExp exp)
  {
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    return new EnumeratedSetExp(exps0);
  }
  public Expression visit(CartesianExp exp)
  {
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    return new CartesianExp(exps0);
  }
  public Expression visit(EqualsExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new EqualsExp(exp10, exp20);
  }
  public Expression visit(EquivExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new EquivExp(exp10, exp20);
  }
  public Expression visit(ExistsExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new ExistsExp(qvar0, exp0);
  }
  public Expression visit(FalseExp exp)
  {
    return new FalseExp();
  }
  public Expression visit(ForallExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new ForallExp(qvar0, exp0);
  }
  public Expression visit(GreaterEqualExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new GreaterEqualExp(exp10, exp20);
  }
  public Expression visit(GreaterExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new GreaterExp(exp10, exp20);
  }
  public Expression visit(IdentifierExp exp)
  {
    Identifier ident0 = (Identifier)exp.ident.accept(this);
    return new IdentifierExp(ident0);
  }
  public Expression visit(IfThenElseExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    Expression exp30 = (Expression)exp.exp3.accept(this);
    return new IfThenElseExp(exp10, exp20, exp30);
  }
  public Expression visit(ImpliesExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new ImpliesExp(exp10, exp20);
  }    
  public Expression visit(InSetExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new InSetExp(exp10, exp20);
  }
  public Expression visit(IntersectExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new IntersectExp(exp10, exp20);
  }
  public Expression visit(IntervalExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new IntervalExp(exp10, exp20);
  }
  public Expression visit(NumberLiteralExp exp)
  {
    return new NumberLiteralExp(exp.literal);
  }
  public Expression visit(LessEqualExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new LessEqualExp(exp10, exp20);
  }
  public Expression visit(LessExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new LessExp(exp10, exp20);
  }
  public Expression visit(LetExp exp)
  {
    List<Binder> binder0 = new ArrayList<Binder>(exp.binder.length);
    for (Binder b : exp.binder) binder0.add((Binder)b.accept(this));
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new LetExp(binder0, exp0);
  }
  public Expression visit(LetParExp exp)
  {
    List<Binder> binder0 = new ArrayList<Binder>(exp.binder.length);
    for (Binder b : exp.binder) binder0.add((Binder)b.accept(this));
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new LetParExp(binder0, exp0);
  }
  public Expression visit(MapBuilderExp exp)
  {
    Type type10 = (Type)exp.type1.accept(this);
    Type type20 = (Type)exp.type2.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new MapBuilderExp(type10, type20, exp0);
  }
  public Expression visit(MapSelectionExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new MapSelectionExp(exp10, exp20);
  }
  public Expression visit(MapUpdateExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    Expression exp30 = (Expression)exp.exp3.accept(this);
    return new MapUpdateExp(exp10, exp20, exp30);
  }
  public Expression visit(MatchExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    List<PatternExpression> pexp0 = new ArrayList<PatternExpression>(exp.pexp.length);
    for (PatternExpression p : exp.pexp) pexp0.add((PatternExpression)p.accept(this));
    return new MatchExp(exp0, pexp0);
  }
  public Expression visit(MinusExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new MinusExp(exp10, exp20);
  }
  public Expression visit(NegationExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new NegationExp(exp0);
  }
  public Expression visit(FactorialExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new FactorialExp(exp0);
  }
  public Expression visit(NotEqualsExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new NotEqualsExp(exp10, exp20);
  }
  public Expression visit(NotExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new NotExp(exp0);
  }
  public Expression visit(NumberExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    return new NumberExp(qvar0);
  }
  public Expression visit(OrExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new OrExp(exp10, exp20);
  }
  public Expression visit(PlusExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new PlusExp(exp10, exp20);
  }
  public Expression visit(PlusExpMult exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new PlusExpMult(exp10, exp20);
  }
  public Expression visit(PowerExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new PowerExp(exp10, exp20);
  }
  public Expression visit(PowersetExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new PowersetExp(exp0);
  } 
  public Expression visit(Powerset1Exp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new Powerset1Exp(exp10, exp20);
  } 
  public Expression visit(Powerset2Exp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    Expression exp30 = (Expression)exp.exp3.accept(this);
    return new Powerset2Exp(exp10, exp20, exp30);
  } 
  public Expression visit(PrintExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new PrintExp(exp.string, exp0);
  }
  public Expression visit(PrintInExp exp)
  {
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new PrintInExp(exp.string, exps0, exp0);
  }
  public Expression visit(CheckExp command)
  {
    Identifier ident0 = (Identifier)command.ident.accept(this);
    Expression exp0 = command.exp == null ? null : (Expression)command.exp.accept(this);
    return new CheckExp(ident0, exp0);
  }
  public Expression visit(RecApplicationExp exp)
  {
    Identifier ident10 = (Identifier)exp.ident1.accept(this);
    Identifier ident20 = (Identifier)exp.ident2.accept(this);
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    return new RecApplicationExp(ident10, ident20, exps0);
  }
  public Expression visit(RecIdentifierExp exp)
  {
    Identifier ident10 = (Identifier)exp.ident1.accept(this);
    Identifier ident20 = (Identifier)exp.ident2.accept(this);
    return new RecIdentifierExp(ident10, ident20);
  }
  public Expression visit(RecordExp exp)
  {
    List<ExpressionIdentifier> eidents0 = 
        new ArrayList<ExpressionIdentifier>(exp.eidents.length);
    for (ExpressionIdentifier eident : exp.eidents)
      eidents0.add((ExpressionIdentifier)eident.accept(this));
    return new RecordExp(eidents0);
  }
  public Expression visit(RecordSelectionExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    Identifier ident0 = (Identifier)exp.ident.accept(this);
    return new RecordSelectionExp(exp0, ident0);
  }
  public Expression visit(RecordUpdateExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Identifier ident0 = (Identifier)exp.ident.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new RecordUpdateExp(exp10, ident0, exp20);
  }
  public Expression visit(RemainderExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new RemainderExp(exp10, exp20);
  }
  public Expression visit(SetBuilderExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    return new SetBuilderExp(exp0, qvar0);
  }
  public Expression visit(SumExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new SumExp(qvar0, exp0);
  }
  public Expression visit(ProductExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new ProductExp(qvar0, exp0);
  }
  public Expression visit(MinExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new MinExp(qvar0, exp0);
  }
  public Expression visit(MaxExp exp)
  {
    QuantifiedVariable qvar0 = (QuantifiedVariable)exp.qvar.accept(this);
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new MaxExp(qvar0, exp0);
  }
  public Expression visit(SetSizeExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    return new SetSizeExp(exp0);
  }
  public Expression visit(SubsetExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new SubsetExp(exp10, exp20);
  }      
  public Expression visit(TimesExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new TimesExp(exp10, exp20);
  }
  public Expression visit(TimesExpMult exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new TimesExpMult(exp10, exp20);
  }
  public Expression visit(TrueExp exp)
  {
    return new TrueExp();
  }
  public Expression visit(TupleExp exp)
  {
    List<Expression> exps0 = new ArrayList<Expression>(exp.exps.length);
    for (Expression e : exp.exps) exps0.add((Expression)e.accept(this));
    return new TupleExp(exps0);
  }
  public Expression visit(TupleSelectionExp exp)
  {
    Expression exp0 = (Expression)exp.exp.accept(this);
    Decimal index0 = (Decimal)exp.index.accept(this);
    return new TupleSelectionExp(exp0, index0);
  }
  public Expression visit(TupleUpdateExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Decimal index0 = (Decimal)exp.index.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new TupleUpdateExp(exp10, index0, exp20);
  }
  public Expression visit(UnionExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new UnionExp(exp10, exp20);
  }
  public Expression visit(UnitExp exp)
  {
    return new UnitExp();
  }
  public Expression visit(WithoutExp exp)
  {
    Expression exp10 = (Expression)exp.exp1.accept(this);
    Expression exp20 = (Expression)exp.exp2.accept(this);
    return new WithoutExp(exp10, exp20);
  }
  
  // ExpressionIdentifier
  public ExpressionIdentifier visit(ExpressionIdentifier eident)
  {
    Identifier ident0 = (Identifier)eident.ident.accept(this);
    Expression exp0 = (Expression)eident.exp.accept(this);
    return new ExpressionIdentifier(ident0, exp0);
  } 
  
  // Identifier
  public Identifier visit(Identifier ident)
  {
    return new Identifier(ident.string);
  }
  
  // Int
  public Decimal visit(Decimal v)
  {
    return new Decimal(v.value);
  }
  
  // LoopSpec
  public LoopSpec visit(DecreasesLoopSpec lspec)
  {
    List<Expression> exps0 = new ArrayList<Expression>(lspec.exps.length);
    for (Expression e : lspec.exps) exps0.add((Expression)e.accept(this));
    return new DecreasesLoopSpec(exps0); 
  }
  public LoopSpec visit(InvariantLoopSpec lspec)
  {
    Expression exp0 = (Expression)lspec.exp.accept(this);
    return new InvariantLoopSpec(exp0);
  }
  
  // Parameter
  public Parameter visit(Parameter param)
  {
    Identifier ident0 = (Identifier)param.ident.accept(this);
    Type type0 = (Type)param.type.accept(this);
    return new Parameter(ident0, type0); 
  }
  
  // PatternCommand
  public PatternCommand visit(ApplicationPatternCommand pcommand)
  {
    Identifier ident0 = (Identifier)pcommand.ident.accept(this);
    List<Parameter> params0 = new ArrayList<Parameter>(pcommand.params.length);
    for (Parameter p : pcommand.params) params0.add((Parameter)p.accept(this));
    Command command0 = (Command)pcommand.command.accept(this);
    return new ApplicationPatternCommand(ident0, params0, command0);
  }
  public PatternCommand visit(DefaultPatternCommand pcommand)
  {
    Command command0 = (Command)pcommand.command.accept(this);
    return new DefaultPatternCommand(command0);
  }
  public PatternCommand visit(IdentifierPatternCommand pcommand)
  {
    Identifier ident0 = (Identifier)pcommand.ident.accept(this);
    Command command0 = (Command)pcommand.command.accept(this);
    return new IdentifierPatternCommand(ident0, command0);
  }
  
  // PatternExpression
  public PatternExpression visit(ApplicationPatternExp pexp)
  {
    Identifier ident0 = (Identifier)pexp.ident.accept(this);
    List<Parameter> params0 = new ArrayList<Parameter>(pexp.params.length);
    for (Parameter p : pexp.params) params0.add((Parameter)p.accept(this));
    Expression exp0 = (Expression)pexp.exp.accept(this);
    return new ApplicationPatternExp(ident0, params0, exp0);
  }
  public PatternExpression visit(DefaultPatternExp pexp)
  {
    Expression exp0 = (Expression)pexp.exp.accept(this);
    return new DefaultPatternExp(exp0);
  }
  public PatternExpression visit(IdentifierPatternExp pexp)
  {
    Identifier ident0 = (Identifier)pexp.ident.accept(this);
    Expression exp0 = (Expression)pexp.exp.accept(this);
    return new IdentifierPatternExp(ident0, exp0);
  }
  
  // QuantifiedVariable
  public QuantifiedVariable visit(QuantifiedVariable qvar)
  {
    List<QuantifiedVariableCore> qvcore0 = new
        ArrayList<QuantifiedVariableCore>(qvar.qvcore.length);
    for (QuantifiedVariableCore q : qvar.qvcore)
        qvcore0.add((QuantifiedVariableCore)q.accept(this));
    Expression exp0 = 
        qvar.exp == null ? null : (Expression)qvar.exp.accept(this);
    return new QuantifiedVariable(qvcore0, exp0);
  }
  
  // QuantifiedVariableCore
  public QuantifiedVariableCore visit(IdentifierSetQuantifiedVar qvcore)
  {
    Identifier ident0 = (Identifier)qvcore.ident.accept(this);
    Expression exp0 = (Expression)qvcore.exp.accept(this);
    return new IdentifierSetQuantifiedVar(ident0, exp0);
  }
  public QuantifiedVariableCore visit(IdentifierTypeQuantifiedVar qvcore)
  {
    Identifier ident0 = (Identifier)qvcore.ident.accept(this);
    Type type0 = (Type)qvcore.type.accept(this);
    return new IdentifierTypeQuantifiedVar(ident0, type0);
  }
  
  // RecursiveIdentifier
  public RecursiveIdentifier visit(RecApplication rident)
  {
    Identifier ident0 = (Identifier)rident.ident.accept(this);
    List<Type> types0 = new ArrayList<Type>(rident.types.length);
    for (Type t : rident.types) types0.add((Type)t.accept(this));
    return new RecApplication(ident0, types0);
  }
  public RecursiveIdentifier visit(RecIdentifier rident)
  {
    Identifier ident0 = (Identifier)rident.ident.accept(this);
    return new RecIdentifier(ident0);
  }
  
  // RecursiveTypeItem
  public RecursiveTypeItem visit(RecursiveTypeItem ritem)
  {
    Identifier ident0 = (Identifier)ritem.ident.accept(this);
    List<RecursiveIdentifier> ridents0 = 
        new ArrayList<RecursiveIdentifier>(ritem.ridents.length);
    for (RecursiveIdentifier rident : ritem.ridents)
      ridents0.add((RecursiveIdentifier)rident.accept(this));
    return new RecursiveTypeItem(ident0, ridents0);
  }
  
  // FunctionSpec
  public FunctionSpec visit(DecreasesSpec spec)
  {
    List<Expression> exps0 = new ArrayList<Expression>(spec.exps.length);
    for (Expression e : spec.exps) exps0.add((Expression)e.accept(this));
    return new DecreasesSpec(exps0);       
  }
  public FunctionSpec visit(EnsuresSpec spec)
  {
    Expression exp0 = (Expression)spec.exp.accept(this);
    return new EnsuresSpec(exp0); 
  }
  public FunctionSpec visit(RequiresSpec spec)
  {
    Expression exp0 = (Expression)spec.exp.accept(this);
    return new RequiresSpec(exp0);      
  }
  public FunctionSpec visit(ContractSpec spec)
  {
    return new ContractSpec();      
  }
  
  // Specification
  public Specification visit(Specification spec)
  {
    List<Declaration> declarations = new ArrayList<Declaration>(spec.declarations.length);
    for (Declaration d : spec.declarations)
      declarations.add((Declaration)d.accept(this));
    return new Specification(declarations);
  }
  
  // Type (do not clone canonical types)
  public Type visit(ArrayType type)
  {
    if (type.isCanonical()) return type;
    Expression exp0 = (Expression)type.exp.accept(this);
    Type type0 = (Type)type.type.accept(this);
    return new ArrayType(exp0, type0);
  }
  public Type visit(BoolType type)
  {
    if (type.isCanonical()) return type;
    return new BoolType();
  }
  public Type visit(IdentifierType type)
  {
    if (type.isCanonical()) return type;
    Identifier ident0 = (Identifier)type.ident.accept(this);
    return new IdentifierType(ident0);
  }
  public Type visit(IntType type)
  {
    if (type.isCanonical()) return type;
    Expression exp10 = (Expression)type.exp1.accept(this);
    Expression exp20 = (Expression)type.exp2.accept(this);
    return new IntType(exp10, exp20);
  }
  public Type visit(MapType type)
  {
    if (type.isCanonical()) return type;
    Type type10 = (Type)type.type1.accept(this);
    Type type20 = (Type)type.type2.accept(this);
    return new MapType(type10, type20);
  }     
  public Type visit(NatType type)
  {
    if (type.isCanonical()) return type;
    Expression exp0 = (Expression)type.exp.accept(this);
    return new NatType(exp0);
  }
  public Type visit(RecordType type)
  {
    if (type.isCanonical()) return type;
    List<Parameter> param0 = new ArrayList<Parameter>(type.param.length);
    for (Parameter p : type.param) param0.add((Parameter)p.accept(this));
    return new RecordType(param0);
  }
  public Type visit(RecursiveType type)
  {
    if (type.isCanonical()) return type;
    Expression exp0 = (Expression)type.exp.accept(this);
    Identifier ident0 = (Identifier)type.ident.accept(this);
    List<RecursiveTypeItem> ritems0 = 
        new ArrayList<RecursiveTypeItem>(type.ritems.length);
    for (RecursiveTypeItem ritem : type.ritems)
      ritems0.add((RecursiveTypeItem)ritem.accept(this));
    return new RecursiveType(exp0, ident0, ritems0);
  }
  public Type visit(SetType type)
  {
    if (type.isCanonical()) return type;
    Type type0 = (Type)type.type.accept(this);
    return new SetType(type0);
  }    
  public Type visit(SubType type)
  {
    if (type.isCanonical()) return type;
    Identifier ident0 = (Identifier)type.ident.accept(this);
    Type type0 = (Type)type.type.accept(this);
    return new SubType(ident0, type0, type.pred); // do not clone predicate symbol
  } 
  public Type visit(TupleType type)
  {
    if (type.isCanonical()) return type;
    List<Type> types0 = new ArrayList<Type>(type.types.length);
    for (Type t : type.types) types0.add((Type)t.accept(this));
    return new TupleType(types0);
  }   
  public Type visit(UnitType type)
  {
    if (type.isCanonical()) return type;
    return new UnitType();
  } 
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------
