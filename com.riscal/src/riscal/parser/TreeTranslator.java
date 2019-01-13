// ---------------------------------------------------------------------------
// ASTVisitor.java
// Translation from ANTLR 4 parse trees to abstract syntax trees.
// $Id: TreeTranslator.java,v 1.52 2018/06/14 13:22:44 schreine Exp schreine $
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
package riscal.parser;

import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;

import riscal.syntax.AST;
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

public class TreeTranslator extends RISCALBaseVisitor<Object>
{
  private final AST.Source source;
  private final TokenStream tokens;
  public TreeTranslator(AST.Source source, TokenStream tokens)
  {
    this.source = source;
    this.tokens = tokens;
  }
  
  private AST.SourcePosition getPosition(ParserRuleContext context)
  {
    Interval interval = context.getSourceInterval();
    if (interval.a == -1) interval.a = 0;
    if (interval.b == -1) interval.b = 0;
    Token from = tokens.get(interval.a);
    Token to = tokens.get(interval.b);
    int lineFrom = from.getLine();
    int charFrom = from.getCharPositionInLine();
    int lineTo = to.getLine();
    int charTo = to.getCharPositionInLine();
    return new AST.SourcePosition(source, lineFrom-1, charFrom, lineTo-1, charTo);
  }
  
  public AST visit(ParserRuleContext c)
  {
    AST ast = (AST)super.visit(c);
    ast.setPosition(getPosition(c));
    return ast;
  }
  
  // Specification
  public Specification visitSpecification(RISCALParser.SpecificationContext c)
  {
    List<Declaration> declarations = new ArrayList<Declaration>(c.declaration().size());
    for (RISCALParser.DeclarationContext d : c.declaration())
    {
      declarations.add((Declaration)visit(d));
    }
    return new Specification(declarations);
  }
  
  // Declaration
  public ValueDeclaration visitValueDeclaration(RISCALParser.ValueDeclarationContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    return new ValueDeclaration(ident);
  }
  public FunctionDeclaration visitFunctionDeclaration(RISCALParser.FunctionDeclarationContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    Type type = (Type)visit(c.type());
    return new FunctionDeclaration(multiple, ident, param, type);
  }
  public PredicateDeclaration visitPredicateDeclaration(RISCALParser.PredicateDeclarationContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    return new PredicateDeclaration(multiple, ident, param);
  }
  public ProcedureDeclaration visitProcedureDeclaration(RISCALParser.ProcedureDeclarationContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    Type type = (Type)visit(c.type());
    return new ProcedureDeclaration(multiple, ident, param, type);
  }
  public TypeDefinition visitTypeDefinition(RISCALParser.TypeDefinitionContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Type type = (Type)visit(c.type());
    Expression exp = c.exp() == null ? null : (Expression)visit(c.exp());
    return new TypeDefinition(ident, type, exp);
  }
  public RecTypeDefinition visitRecTypeDefinition(RISCALParser.RecTypeDefinitionContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    List<RecursiveTypeItem> ritems = 
        new ArrayList<RecursiveTypeItem>(c.ritem().size());
    for (RISCALParser.RitemContext r : c.ritem())
      ritems.add((RecursiveTypeItem)visit(r));
    return new RecTypeDefinition(exp, ritems);
  }
  public EnumTypeDefinition visitEnumTypeDefinition(RISCALParser.EnumTypeDefinitionContext c)
  {
    RecursiveTypeItem ritem = (RecursiveTypeItem)visit(c.ritem());
    return new EnumTypeDefinition(ritem);
  }
  public ValueDefinition visitValueDefinition(RISCALParser.ValueDefinitionContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Type type = c.type() == null ? null : (Type)visit(c.type());
    Expression exp = (Expression)visit(c.exp());
    return new ValueDefinition(ident, type, exp);
  }
  public FunctionDefinition visitFunctionDefinition(RISCALParser.FunctionDefinitionContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    Type type = (Type)visit(c.type());
    List<FunctionSpec> spec = new ArrayList<FunctionSpec>(c.funspec().size());
    for (RISCALParser.FunspecContext s : c.funspec()) spec.add((FunctionSpec)(visit(s)));
    Expression exp = (Expression)visit(c.exp());
    return new FunctionDefinition(multiple, ident, param, type, spec, exp);
  }
  public PredicateDefinition visitPredicateDefinition(RISCALParser.PredicateDefinitionContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    List<FunctionSpec> spec = new ArrayList<FunctionSpec>(c.funspec().size());
    for (RISCALParser.FunspecContext s : c.funspec()) spec.add((FunctionSpec)(visit(s)));
    Expression exp = (Expression)visit(c.exp());
    return new PredicateDefinition(multiple, ident, param, spec, exp);
  }
  public TheoremParamDefinition visitTheoremParamDefinition(RISCALParser.TheoremParamDefinitionContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    List<FunctionSpec> spec = new ArrayList<FunctionSpec>(c.funspec().size());
    for (RISCALParser.FunspecContext s : c.funspec()) spec.add((FunctionSpec)(visit(s)));
    Expression exp = (Expression)visit(c.exp());
    return new TheoremParamDefinition(multiple, ident, param, spec, exp);
  }
  public ProcedureDefinition visitProcedureDefinition(RISCALParser.ProcedureDefinitionContext c)
  {
    Multiple multiple = (Multiple)visit(c.multiple());
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    Type type = (Type)visit(c.type());
    List<FunctionSpec> spec = new ArrayList<FunctionSpec>(c.funspec().size());
    for (RISCALParser.FunspecContext s : c.funspec()) spec.add((FunctionSpec)(visit(s)));
    List<Command> commands = new ArrayList<Command>(c.command().size());
    for (RISCALParser.CommandContext s : c.command()) commands.add((Command)(visit(s)));
    Expression exp = c.exp() == null ? null : (Expression)visit(c.exp());
    return new ProcedureDefinition(multiple, ident, param, type, spec, commands, exp);
  }
  public TheoremDefinition visitTheoremDefinition(RISCALParser.TheoremDefinitionContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = (Expression)visit(c.exp());
    return new TheoremDefinition(ident, exp);
  }
  public PredicateValueDefinition visitPredicateValueDefinition(RISCALParser.PredicateValueDefinitionContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = (Expression)visit(c.exp());
    return new PredicateValueDefinition(ident, exp);
  }
  
  // Spec
  public RequiresSpec visitRequiresSpec(RISCALParser.RequiresSpecContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new RequiresSpec(exp);
  }
  public EnsuresSpec visitEnsuresSpec(RISCALParser.EnsuresSpecContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new EnsuresSpec(exp);
  }
  public DecreasesSpec visitDecreasesSpec(RISCALParser.DecreasesSpecContext c)
  {
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new DecreasesSpec(exps);
  }
  public ContractSpec visitContractSpec(RISCALParser.ContractSpecContext c)
  {
    return new ContractSpec();
  }
  
  // Command and SemicolonCommand
  public Command visitSemicolonCommand(RISCALParser.SemicolonCommandContext c)
  {
    return (Command)visit(c.scommand());
  }
  public EmptyCommand visitEmptyCommand(RISCALParser.EmptyCommandContext c)
  {
    return new EmptyCommand();
  }
  public AssignmentCommand visitAssignmentCommand(RISCALParser.AssignmentCommandContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    List<RISCALParser.SelContext> sels0 = c.sel();
    List<Selector> sels1 = new ArrayList<Selector>(sels0.size());
    for (RISCALParser.SelContext sel : sels0) sels1.add((Selector)visit(sel));
    Expression exp = (Expression)visit(c.exp());
    return new AssignmentCommand(ident, sels1, exp);
  }
  public ChooseCommand visitChooseCommand(RISCALParser.ChooseCommandContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    return new ChooseCommand(qvar);
  }
  public ChooseElseCommand visitChooseElseCommand(RISCALParser.ChooseElseCommandContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Command command1 = (Command)visit(c.command(0));
    Command command2 = (Command)visit(c.command(1));
    return new ChooseElseCommand(qvar, command1, command2);
  }
  public ChooseDoCommand visitChooseDoCommand(RISCALParser.ChooseDoCommandContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    List<LoopSpec> loopspec = new ArrayList<LoopSpec>(c.loopspec().size());
    for (RISCALParser.LoopspecContext s : c.loopspec()) loopspec.add((LoopSpec)visit(s));
    Command command = (Command)visit(c.command());
    return new ChooseDoCommand(qvar, loopspec, command);
  }
  public IfThenCommand visitIfThenCommand(RISCALParser.IfThenCommandContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    Command command = (Command)visit(c.command());
    return new IfThenCommand(exp, command);
  }
  public IfThenElseCommand visitIfThenElseCommand(RISCALParser.IfThenElseCommandContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    Command command1 = (Command)visit(c.command(0));
    Command command2 = (Command)visit(c.command(1));
    return new IfThenElseCommand(exp, command1, command2);
  }
  public MatchCommand visitMatchCommand(RISCALParser.MatchCommandContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    List<PatternCommand> pcommand = new ArrayList<PatternCommand>(c.pcommand().size());
    for (RISCALParser.PcommandContext p : c.pcommand()) pcommand.add((PatternCommand)visit(p));
    return new MatchCommand(exp, pcommand);
  }
  public WhileCommand visitWhileCommand(RISCALParser.WhileCommandContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    List<LoopSpec> loopspec = new ArrayList<LoopSpec>(c.loopspec().size());
    for (RISCALParser.LoopspecContext s : c.loopspec()) loopspec.add((LoopSpec)visit(s));
    Command command = (Command)visit(c.command());
    return new WhileCommand(exp, loopspec, command);
  }
  public DoWhileCommand visitDoWhileCommand(RISCALParser.DoWhileCommandContext c)
  {
    Command command = (Command)visit(c.command());
    Expression exp = (Expression)visit(c.exp());
    List<LoopSpec> loopspec = new ArrayList<LoopSpec>(c.loopspec().size());
    for (RISCALParser.LoopspecContext s : c.loopspec()) loopspec.add((LoopSpec)visit(s));
    return new DoWhileCommand(command, exp, loopspec);
  }
  public ExpCommand visitExpCommand(RISCALParser.ExpCommandContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new ExpCommand(exp);
  }
  public ForCommand visitForCommand(RISCALParser.ForCommandContext c)
  {
    Command command1 = (Command)visit(c.scommand(0));
    Expression exp = (Expression)visit(c.exp());
    Command command2 = (Command)visit(c.scommand(1));
    List<LoopSpec> loopspec = new ArrayList<LoopSpec>(c.loopspec().size());
    for (RISCALParser.LoopspecContext s : c.loopspec()) loopspec.add((LoopSpec)visit(s));
    Command command3 = (Command)visit(c.command());
    return new ForCommand(command1, exp, command2, loopspec, command3);
  }
  public ForInCommand visitForInCommand(RISCALParser.ForInCommandContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    List<LoopSpec> loopspec = new ArrayList<LoopSpec>(c.loopspec().size());
    for (RISCALParser.LoopspecContext s : c.loopspec()) loopspec.add((LoopSpec)visit(s));
    Command command = (Command)visit(c.command());
    return new ForInCommand(qvar, loopspec, command);
  }
  public VarCommand visitVarCommand(RISCALParser.VarCommandContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Type type = (Type)visit(c.type());
    Expression exp = c.exp() == null ? null : (Expression)visit(c.exp());
    return new VarCommand(ident, type, exp);
  }
  public ValCommand visitValCommand(RISCALParser.ValCommandContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Type type = c.type() == null ? null : (Type)visit(c.type());
    Expression exp = (Expression)visit(c.exp());
    return new ValCommand(ident, type, exp);
  }
  public AssertCommand visitAssertCommand(RISCALParser.AssertCommandContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new AssertCommand(exp);
  }
  public PrintCommand visitPrintCommand(RISCALParser.PrintCommandContext c)
  {
    String string = c.STRING() == null ? null : c.STRING().getText();
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new PrintCommand(string, exps);
  }
  public PrintCommand visitPrint2Command(RISCALParser.Print2CommandContext c)
  {
    String string = c.STRING() == null ? null : c.STRING().getText();
    List<Expression> exps = new ArrayList<Expression>(0);
    return new PrintCommand(string, exps);
  }
  public CheckCommand visitCheckCommand(RISCALParser.CheckCommandContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = c.exp() == null ? null : (Expression)visit(c.exp());
    return new CheckCommand(ident, exp);
  }
  public CommandSequence visitCommandSequence(RISCALParser.CommandSequenceContext c)
  {
    List<RISCALParser.CommandContext> cs0 = c.command();
    List<Command> cs1 = new ArrayList<Command>(cs0.size());
    for (RISCALParser.CommandContext com : cs0) cs1.add((Command)visit(com));
    return new CommandSequence(cs1);
  }
  
  // LoopSpec
  public InvariantLoopSpec visitInvariantLoopSpec(RISCALParser.InvariantLoopSpecContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new InvariantLoopSpec(exp);
  }
  public DecreasesLoopSpec visitDecreasesLoopSpec(RISCALParser.DecreasesLoopSpecContext c)
  {
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new DecreasesLoopSpec(exps);
  }
  
  // Expression
  public UnitExp visitUnitExp(RISCALParser.UnitExpContext c)
  {
    return new UnitExp();
  }
  public TrueExp visitTrueExp(RISCALParser.TrueExpContext c)
  {
    return new TrueExp();
  }
  public FalseExp visitFalseExp(RISCALParser.FalseExpContext c)
  {
    return new FalseExp();
  }
  public NumberLiteralExp visitIntLiteralExp(RISCALParser.IntLiteralExpContext c)
  {
    Decimal decimal = (Decimal)visit(c.decimal());
    return new NumberLiteralExp(decimal);
  }
  public EmptySetExp visitEmptySetExp(RISCALParser.EmptySetExpContext c)
  {
    Type type = (Type)visit(c.type());
    return new EmptySetExp(type);
  }
  public RecIdentifierExp visitRecIdentifierExp(RISCALParser.RecIdentifierExpContext c)
  {
    Identifier ident1 = (Identifier)visit(c.ident(0));
    Identifier ident2 = (Identifier)visit(c.ident(1));
    return new RecIdentifierExp(ident1, ident2);
  }
  public RecApplicationExp visitRecApplicationExp(RISCALParser.RecApplicationExpContext c)
  {
    Identifier ident1 = (Identifier)visit(c.ident(0));
    Identifier ident2 = (Identifier)visit(c.ident(1));
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new RecApplicationExp(ident1, ident2, exps);
  }
  public IdentifierExp visitIdentifierExp(RISCALParser.IdentifierExpContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    return new IdentifierExp(ident);
  }
  public ApplicationExp visitApplicationExp(RISCALParser.ApplicationExpContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new ApplicationExp(ident, exps);
  }
  public PowersetExp visitPowerSetExp(RISCALParser.PowerSetExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new PowersetExp(exp);
  }
  public Powerset1Exp visitPowerSet1Exp(RISCALParser.PowerSet1ExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new Powerset1Exp(exp1,exp2);
  }
  public Powerset2Exp visitPowerSet2Exp(RISCALParser.PowerSet2ExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    Expression exp3 = (Expression)visit(c.exp(2));
    return new Powerset2Exp(exp1,exp2,exp3);
  }
  public MapSelectionExp visitArraySelectionExp(RISCALParser.ArraySelectionExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new MapSelectionExp(exp1, exp2);
  }
  public TupleSelectionExp visitTupleSelectionExp(RISCALParser.TupleSelectionExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    Decimal decimal = (Decimal)visit(c.decimal());
    return new TupleSelectionExp(exp, decimal);
  }
  public RecordSelectionExp visitRecordSelectionExp(RISCALParser.RecordSelectionExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    Identifier ident = (Identifier)visit(c.ident());
    return new RecordSelectionExp(exp, ident);
  }
  public NegationExp visitNegationExp(RISCALParser.NegationExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new NegationExp(exp);
  }
  public FactorialExp visitFactorialExp(RISCALParser.FactorialExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new FactorialExp(exp);
  }
  public BigUnionExp visitBigUnionExp(RISCALParser.BigUnionExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new BigUnionExp(exp);
  }
  public BigIntersectExp visitBigIntersectExp(RISCALParser.BigIntersectExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new BigIntersectExp(exp);
  }
  public PowerExp visitPowerExp(RISCALParser.PowerExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new PowerExp(exp1, exp2);
  }
  public PrintExp visitPrintExp(RISCALParser.PrintExpContext c)
  {
    String string = c.STRING() == null ? null : c.STRING().getText();
    Expression exp = (Expression)visit(c.exp());
    return new PrintExp(string, exp);
  }
  public PrintInExp visitPrintInExp(RISCALParser.PrintInExpContext c)
  {
    String string = c.STRING() == null ? null : c.STRING().getText();
    int n = c.exp().size();
    List<Expression> exps = new ArrayList<Expression>(n-1);
    for (int i=0; i<n-1; i++) exps.add((Expression)visit(c.exp(i)));
    Expression exp = (Expression)visit(c.exp(n-1));
    return new PrintInExp(string, exps, exp);
  }
  public CheckExp visitCheckExp(RISCALParser.CheckExpContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = c.exp() == null ? null : (Expression)visit(c.exp());
    return new CheckExp(ident, exp);
  }
  public TimesExp visitTimesExp(RISCALParser.TimesExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new TimesExp(exp1, exp2);
  }
  public TimesExpMult visitTimesExpMult(RISCALParser.TimesExpMultContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new TimesExpMult(exp1, exp2);
  }
  public DividesExp visitDividesExp(RISCALParser.DividesExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new DividesExp(exp1, exp2);
  }
  public RemainderExp visitRemainderExp(RISCALParser.RemainderExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new RemainderExp(exp1, exp2);
  }
  public PlusExp visitPlusExp(RISCALParser.PlusExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new PlusExp(exp1, exp2);
  }
  public PlusExpMult visitPlusExpMult(RISCALParser.PlusExpMultContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new PlusExpMult(exp1, exp2);
  }
  public MinusExp visitMinusExp(RISCALParser.MinusExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new MinusExp(exp1, exp2);
  }
  public EnumeratedSetExp visitEnumeratedSetExp(RISCALParser.EnumeratedSetExpContext c)
  {
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new EnumeratedSetExp(exps);
  }
  public CartesianExp visitCartesianExp(RISCALParser.CartesianExpContext c)
  {
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new CartesianExp(exps);
  }
  public IntervalExp visitIntervalExp(RISCALParser.IntervalExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new IntervalExp(exp1, exp2);
  }
  public TupleExp visitTupleExp(RISCALParser.TupleExpContext c)
  {
    List<Expression> exps = new ArrayList<Expression>(c.exp().size());
    for (RISCALParser.ExpContext e : c.exp()) exps.add((Expression)visit(e));
    return new TupleExp(exps);
  }
  public RecordExp visitRecordExp(RISCALParser.RecordExpContext c)
  {
    List<ExpressionIdentifier> eidents = 
        new ArrayList<ExpressionIdentifier>(c.eident().size());
    for (RISCALParser.EidentContext e : c.eident())
      eidents.add((ExpressionIdentifier)visit(e));
    return new RecordExp(eidents);
  }
  public SetSizeExp visitSetSizeExp(RISCALParser.SetSizeExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new SetSizeExp(exp);
  }
  public UnionExp visitUnionExp(RISCALParser.UnionExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new UnionExp(exp1, exp2);
  }
  public IntersectExp visitIntersectExp(RISCALParser.IntersectExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new IntersectExp(exp1, exp2);
  }
  public WithoutExp visitWithoutExp(RISCALParser.WithoutExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new WithoutExp(exp1, exp2);
  }
  public NumberExp visitNumberExp(RISCALParser.NumberExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    return new NumberExp(qvar);
  }
  public SetBuilderExp visitSetBuilderExp(RISCALParser.SetBuilderExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    return new SetBuilderExp(exp, qvar);
  }
  public SumExp visitSumExp(RISCALParser.SumExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new SumExp(qvar, exp);
  }
  public ProductExp visitProductExp(RISCALParser.ProductExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new ProductExp(qvar, exp);
  }
  public MinExp visitMinExp(RISCALParser.MinExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new MinExp(qvar, exp);
  }
  public MaxExp visitMaxExp(RISCALParser.MaxExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new MaxExp(qvar, exp);
  }
  public ArrayBuilderExp visitArrayBuilderExp(RISCALParser.ArrayBuilderExpContext c)
  {
    Type type = (Type)visit(c.type());
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new ArrayBuilderExp(type, exp1, exp2);
  }
  public MapBuilderExp visitMapBuilderExp(RISCALParser.MapBuilderExpContext c)
  {
    Type type1 = (Type)visit(c.type(0));
    Type type2 = (Type)visit(c.type(1));
    Expression exp = (Expression)visit(c.exp());
    return new MapBuilderExp(type1, type2, exp);
  }
  public ChooseExp visitChooseExp(RISCALParser.ChooseExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    return new ChooseExp(qvar);
  }
  public MapUpdateExp visitArrayUpdateExp(RISCALParser.ArrayUpdateExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    Expression exp3 = (Expression)visit(c.exp(2));
    return new MapUpdateExp(exp1, exp2, exp3);
  }
  public TupleUpdateExp visitTupleUpdateExp(RISCALParser.TupleUpdateExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Decimal decimal = (Decimal)visit(c.decimal());
    Expression exp2 = (Expression)visit(c.exp(1));
    return new TupleUpdateExp(exp1, decimal, exp2);
  }
  public RecordUpdateExp visitRecordUpdateExp(RISCALParser.RecordUpdateExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp2 = (Expression)visit(c.exp(1));
    return new RecordUpdateExp(exp1, ident, exp2);
  }
  public EqualsExp visitEqualsExp(RISCALParser.EqualsExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new EqualsExp(exp1, exp2);
  }
  public NotEqualsExp visitNotEqualsExp(RISCALParser.NotEqualsExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new NotEqualsExp(exp1, exp2);
  }
  public LessExp visitLessExp(RISCALParser.LessExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new LessExp(exp1, exp2);
  }
  public LessEqualExp visitLessEqualExp(RISCALParser.LessEqualExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new LessEqualExp(exp1, exp2);
  }
  public GreaterExp visitGreaterExp(RISCALParser.GreaterExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new GreaterExp(exp1, exp2);
  }
  public GreaterEqualExp visitGreaterEqualExp(RISCALParser.GreaterEqualExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new GreaterEqualExp(exp1, exp2);
  }
  public InSetExp visitInSetExp(RISCALParser.InSetExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new InSetExp(exp1, exp2);
  }
  public SubsetExp visitSubsetExp(RISCALParser.SubsetExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new SubsetExp(exp1, exp2);
  }
  public NotExp visitNotExp(RISCALParser.NotExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new NotExp(exp);
  }
  public AndExp visitAndExp(RISCALParser.AndExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new AndExp(exp1, exp2);
  }
  public OrExp visitOrExp(RISCALParser.OrExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new OrExp(exp1, exp2);
  }
  public ImpliesExp visitImpliesExp(RISCALParser.ImpliesExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new ImpliesExp(exp1, exp2);
  }
  public EquivExp visitEquivExp(RISCALParser.EquivExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new EquivExp(exp1, exp2);
  }
  public IfThenElseExp visitIfThenElseExp(RISCALParser.IfThenElseExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    Expression exp3 = (Expression)visit(c.exp(2));
    return new IfThenElseExp(exp1, exp2, exp3);
  }
  public MatchExp visitMatchExp(RISCALParser.MatchExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    List<PatternExpression> pexp = new ArrayList<PatternExpression>(c.pexp().size());
    for (RISCALParser.PexpContext p : c.pexp()) pexp.add((PatternExpression)visit(p));
    return new MatchExp(exp, pexp);
  }
  public ForallExp visitForallExp(RISCALParser.ForallExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new ForallExp(qvar, exp);
  }
  public ExistsExp visitExistsExp(RISCALParser.ExistsExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new ExistsExp(qvar, exp);
  }
  public LetExp visitLetExp(RISCALParser.LetExpContext c)
  {
    List<Binder> binder = new ArrayList<Binder>(c.binder().size());
    for (RISCALParser.BinderContext b : c.binder()) binder.add((Binder)visit(b));
    Expression exp = (Expression)visit(c.exp());
    return new LetExp(binder, exp);
  }
  public LetParExp visitLetParExp(RISCALParser.LetParExpContext c)
  {
    List<Binder> binder = new ArrayList<Binder>(c.binder().size());
    for (RISCALParser.BinderContext b : c.binder()) binder.add((Binder)visit(b));
    Expression exp = (Expression)visit(c.exp());
    return new LetParExp(binder, exp);
  }
  public ChooseInExp visitChooseInExp(RISCALParser.ChooseInExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp = (Expression)visit(c.exp());
    return new ChooseInExp(qvar, exp);
  }
  public ChooseInElseExp visitChooseInElseExp(RISCALParser.ChooseInElseExpContext c)
  {
    QuantifiedVariable qvar = (QuantifiedVariable)visit(c.qvar());
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new ChooseInElseExp(qvar, exp1, exp2);
  }
  public AssertExp visitAssertExp(RISCALParser.AssertExpContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new AssertExp(exp1, exp2);
  }
  public Expression visitParenthesizedExp(RISCALParser.ParenthesizedExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return exp;
  }
  
  // Type
  public UnitType visitUnitType(RISCALParser.UnitTypeContext c)
  {
    return new UnitType();
  }
  public BoolType visitBoolType(RISCALParser.BoolTypeContext c)
  {
    return new BoolType();
  }
  public IntType visitIntType(RISCALParser.IntTypeContext c)
  {
    Expression exp1 = (Expression)visit(c.exp(0));
    Expression exp2 = (Expression)visit(c.exp(1));
    return new IntType(exp1, exp2);
  }
  public MapType visitMapType(RISCALParser.MapTypeContext c)
  {
    Type type1 = (Type)visit(c.type(0));
    Type type2 = (Type)visit(c.type(1));
    return new MapType(type1, type2);
  }
  public TupleType visitTupleType(RISCALParser.TupleTypeContext c)
  {
    List<Type> types = new ArrayList<Type>(c.type().size());
    for (RISCALParser.TypeContext t : c.type()) types.add((Type)visit(t));
    return new TupleType(types);
  }
  public RecordType visitRecordType(RISCALParser.RecordTypeContext c)
  {
    List<Parameter> param = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) param.add((Parameter)visit(p));
    return new RecordType(param);
  }
  public NatType visitNatType(RISCALParser.NatTypeContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new NatType(exp);
  }
  public SetType visitSetType(RISCALParser.SetTypeContext c)
  {
    Type type = (Type)visit(c.type());
    return new SetType(type);
  }
  public ArrayType visitArrayType(RISCALParser.ArrayTypeContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    Type type = (Type)visit(c.type());
    return new ArrayType(exp, type);
  }
  public IdentifierType visitIdentifierType(RISCALParser.IdentifierTypeContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    return new IdentifierType(ident);
  }
  
  // QuantifiedVariable
  public QuantifiedVariable visitQuantifiedVariable(RISCALParser.QuantifiedVariableContext c)
  {
    List<QuantifiedVariableCore> qvcore = 
        new ArrayList<QuantifiedVariableCore>(c.qvcore().size());
    for (RISCALParser.QvcoreContext q : c.qvcore())
      qvcore.add((QuantifiedVariableCore)visit(q));
    Expression exp = c.exp() == null ? null : (Expression)visit(c.exp());
    return new QuantifiedVariable(qvcore, exp);
  }
  
  // QuantifiedVariableCore
  public IdentifierTypeQuantifiedVar 
  visitIdentifierTypeQuantifiedVar(RISCALParser.IdentifierTypeQuantifiedVarContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Type type = (Type)visit(c.type());
    return new IdentifierTypeQuantifiedVar(ident, type);
  }
  public IdentifierSetQuantifiedVar 
  visitIdentifierSetQuantifiedVar(RISCALParser.IdentifierSetQuantifiedVarContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = (Expression)visit(c.exp());
    return new IdentifierSetQuantifiedVar(ident, exp);
  }
  
  // Binder
  public Binder visitBinder(RISCALParser.BinderContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = (Expression)visit(c.exp());
    return new Binder(ident, exp);
  }
  
  // PatternCommand
  public IdentifierPatternCommand 
  visitIdentifierPatternCommand(RISCALParser.IdentifierPatternCommandContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Command command = (Command)visit(c.command());
    return new IdentifierPatternCommand(ident, command);
  }
  public ApplicationPatternCommand 
  visitApplicationPatternCommand(RISCALParser.ApplicationPatternCommandContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> params = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) params.add((Parameter)visit(p));
    Command command = (Command)visit(c.command());
    return new ApplicationPatternCommand(ident, params, command);
  }
  public DefaultPatternCommand 
  visitDefaultPatternCommand(RISCALParser.DefaultPatternCommandContext c)
  {
    Command command = (Command)visit(c.command());
    return new DefaultPatternCommand(command);
  }
  
  // PatternExpression
  public IdentifierPatternExp
  visitIdentifierPatternExp(RISCALParser.IdentifierPatternExpContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = (Expression)visit(c.exp());
    return new IdentifierPatternExp(ident, exp);
  }
  public ApplicationPatternExp
  visitApplicationPatternExp(RISCALParser.ApplicationPatternExpContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    List<Parameter> params = new ArrayList<Parameter>(c.param().size());
    for (RISCALParser.ParamContext p : c.param()) params.add((Parameter)visit(p));
    Expression exp = (Expression)visit(c.exp());
    return new ApplicationPatternExp(ident, params, exp);
  }
  public DefaultPatternExp
  visitDefaultPatternExp(RISCALParser.DefaultPatternExpContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new DefaultPatternExp(exp);
  }
  
  // Parameter
  public Parameter visitParam(RISCALParser.ParamContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Type type = (Type)visit(c.type());
    return new Parameter(ident, type);
  }
  
  // RecursiveTypeItem
  public RecursiveTypeItem visitRitem(RISCALParser.RitemContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    List<RecursiveIdentifier> ridents = 
        new ArrayList<RecursiveIdentifier>(c.rident().size());
    for (RISCALParser.RidentContext r : c.rident())
      ridents.add((RecursiveIdentifier)visit(r));
    return new RecursiveTypeItem(ident, ridents);
  }
  
  // ExpressionIdentifier
  public ExpressionIdentifier visitEident(RISCALParser.EidentContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    Expression exp = (Expression)visit(c.exp());
    return new ExpressionIdentifier(ident, exp);
  }
  
  // RecursiveIdentifier
  public RecIdentifier visitRecIdentifier(RISCALParser.RecIdentifierContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    return new RecIdentifier(ident);
  }
  public RecApplication visitRecApplication(RISCALParser.RecApplicationContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    List<Type> types = new ArrayList<Type>(c.type().size());
    for (RISCALParser.TypeContext t : c.type()) types.add((Type)visit(t));
    return new RecApplication(ident, types);
  }
  
  // Selector
  public MapSelector visitMapSelector(RISCALParser.MapSelectorContext c)
  {
    Expression exp = (Expression)visit(c.exp());
    return new MapSelector(exp);
  }
  public TupleSelector visitTupleSelector(RISCALParser.TupleSelectorContext c)
  {
    Decimal decimal = (Decimal)visit(c.decimal());
    return new TupleSelector(decimal);
  }
  public RecordSelector visitRecordSelector(RISCALParser.RecordSelectorContext c)
  {
    Identifier ident = (Identifier)visit(c.ident());
    return new RecordSelector(ident);
  }
  
  // Multiple
  public Multiple visitIsMultiple(RISCALParser.IsMultipleContext c)
  {
    return new Multiple(true);
  }
  public Multiple visitIsNotMultiple(RISCALParser.IsNotMultipleContext c)
  {
    return new Multiple(false);
  }
  
  // Identifier
  public Identifier visitIdent(RISCALParser.IdentContext c)
  {
    String string = c.getText();
    return new Identifier(string);
  }
  
  // Decimal
  public Decimal visitDecimal(RISCALParser.DecimalContext c)
  {
    String string = c.getText();
    return new Decimal(string);
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------