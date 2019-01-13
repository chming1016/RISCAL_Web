// ---------------------------------------------------------------------------
// Translator.java
// The translator to the denotational semantics.
// $Id: Translator.java,v 1.77 2018/06/14 13:22:44 schreine Exp schreine $
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

import riscal.syntax.AST;
import riscal.syntax.AST.*;
import riscal.syntax.AST.Command.*;
import riscal.syntax.AST.Declaration.*;
import riscal.syntax.AST.Expression.*;
import riscal.syntax.AST.LoopSpec.*;
import riscal.syntax.AST.FunctionSpec.*;
import riscal.syntax.AST.PatternExpression.*;
import riscal.syntax.AST.PatternCommand.*;
import riscal.syntax.AST.Type.*;
import riscal.syntax.AST.QuantifiedVariableCore.*;
import riscal.syntax.AST.Selector.*;
import riscal.types.Environment.Symbol;
import riscal.types.Environment.Symbol.*;
import riscal.semantics.Types.*;
import riscal.util.*;

import java.io.*;
import java.util.*;

public final class Translator
{
  // nondeterministic execution is supported
  public final boolean nondeterministic;
 
  // the writer to be used for printing error messages
  public final PrintWriter writer;
  
  // the various phrase translators
  private final Commands ctranslator;
  private final Expressions etranslator;
  private final Declarations dtranslator;
  
  /***************************************************************************
   * A translator of syntactic phrases to semantic objects.
   * @param writer the writer for printing error messages
   * @param nondeterministic true if nondeterministic execution is supported
   **************************************************************************/
  public Translator(PrintWriter writer, boolean nondeterministic)
  {
    this.writer = writer;
    this.nondeterministic = nondeterministic;
    this.dtranslator = new Declarations(this);
    this.ctranslator = new Commands(this);
    this.etranslator = new Expressions(this);
  }
  
  /***************************************************************************
   * The exception raised by the translator on an error.
   **************************************************************************/
  public static final class TranslationError extends RuntimeException
  {
    private static final long serialVersionUID = 28091967L;
    public final AST tree;
    
    /*************************************************************************
     * Create a translator error with the denoted message originating from 
     * translating the denoted phrase.
     * @param tree the abstract syntax tree of the phrase
     * @param msg the message
     ************************************************************************/
    public TranslationError(AST tree, String msg) 
    { 
      super(msg); 
      this.tree = tree;
    }
  }
  
  /***************************************************************************
   * Throw a translation error.
   * @param tree the phrase causing the error
   * @param msg the message reported by the error
   **************************************************************************/
  public void translationError(AST tree, String msg)
  {
    throw new TranslationError(tree, msg);
  }
  
  /***************************************************************************
   * The exception raised by the runtime system on an error.
   **************************************************************************/
  public static final class RuntimeError extends RuntimeException
  {
    private static final long serialVersionUID = 28091967L;
    public final AST tree;
    
    /*************************************************************************
     * Create a runtime error with the denoted message originating from 
     * evaluating the denoted phrase.
     * @param tree the abstract syntax tree of the phrase
     * @param msg the message
     ************************************************************************/
    public RuntimeError(AST tree, String msg) 
    { 
      super(msg); 
      this.tree = tree;
    }
  }
  
  /***************************************************************************
   * Throw a runtime error.
   * @param tree the phrase causing the error
   * @param msg the message reported by the error
   **************************************************************************/
  public void runtimeError(AST tree, String msg)
  {
    throw new RuntimeError(tree, msg);
  }
  
  // --------------------------------------------------------------------------
  //
  // Declarations
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Process a declaration and annotate it with its semantics.
   * @param decl process the declaration.
   ***************************************************************************/
  public void process(Declaration decl)
  {
    // only the following definitions need to be processed
    if (decl instanceof FunctionDefinition)
    {
      FunctionDefinition decl0 = (FunctionDefinition)decl;
      FunctionSymbol symbol = (FunctionSymbol)decl0.ident.getSymbol();
      ValueSymbol[] params = Arrays.stream(decl0.param)
          .map((Parameter p)->(ValueSymbol)p.ident.getSymbol())
          .toArray(ValueSymbol[]::new);
      ExpSem exp = process(decl0.exp);
      FunSpecSem[] spec = process(decl0.spec);
      dtranslator.FunctionDefinition(symbol, params, exp, decl0.exp, spec, decl0.spec, false);
    }
    else if (decl instanceof PredicateDefinition)
    {
      PredicateDefinition decl0 = (PredicateDefinition)decl;
      FunctionSymbol symbol = (FunctionSymbol)decl0.ident.getSymbol();
      ValueSymbol[] params = Arrays.stream(decl0.param)
          .map((Parameter p)->(ValueSymbol)p.ident.getSymbol())
          .toArray(ValueSymbol[]::new);
      ExpSem exp = process(decl0.exp);
      FunSpecSem[] spec = process(decl0.spec);
      dtranslator.FunctionDefinition(symbol, params, exp, decl0.exp, spec, decl0.spec, false);
    }
    else if (decl instanceof TheoremParamDefinition)
    {
      TheoremParamDefinition decl0 = (TheoremParamDefinition)decl;
      FunctionSymbol symbol = (FunctionSymbol)decl0.ident.getSymbol();
      ValueSymbol[] params = Arrays.stream(decl0.param)
          .map((Parameter p)->(ValueSymbol)p.ident.getSymbol())
          .toArray(ValueSymbol[]::new);
      ExpSem exp = process(decl0.exp);
      FunSpecSem[] spec = process(decl0.spec);
      dtranslator.FunctionDefinition(symbol, params, exp, decl0.exp, spec, decl0.spec, true);
    }
    else if (decl instanceof ProcedureDefinition)
    {
      ProcedureDefinition decl0 = (ProcedureDefinition)decl;
      FunctionSymbol symbol = (FunctionSymbol)decl0.ident.getSymbol();
      ValueSymbol[] params = Arrays.stream(decl0.param)
          .map((Parameter p)->(ValueSymbol)p.ident.getSymbol())
          .toArray(ValueSymbol[]::new);
      ComSem[] coms = process(decl0.commands);
      ExpSem exp = decl0.exp == null ?
          (ExpSem.Single)(Context c)->new Value.Unit() :
          process(decl0.exp);
      FunSpecSem[] spec = process(decl0.spec);
      dtranslator.ProcedureDefinition(symbol, params, 
          ctranslator.CommandSequence(coms), exp, decl0.exp, spec, decl0.spec);
    }
    else if (decl instanceof TheoremDefinition)
    {
      TheoremDefinition decl0 = (TheoremDefinition)decl;
      ValueSymbol symbol = (ValueSymbol)decl0.ident.getSymbol();
      ExpSem exp = process(decl0.exp);
      dtranslator.TheoremDefinition(decl0, symbol, exp);
    }
    else if (decl instanceof PredicateValueDefinition)
    {
      PredicateValueDefinition decl0 = (PredicateValueDefinition)decl;
      ValueSymbol symbol = (ValueSymbol)decl0.ident.getSymbol();
      ExpSem exp = process(decl0.exp);
      dtranslator.PredicateValueDefinition(decl0, symbol, exp);
    }
    else if (decl instanceof ValueDefinition)
    {
      ValueDefinition decl0 = (ValueDefinition)decl;
      ValueSymbol symbol = (ValueSymbol)decl0.ident.getSymbol();
      ExpSem exp = process(decl0.exp);
      dtranslator.ValueDefinition(decl0, symbol, exp);
    }
    else if (decl instanceof TypeDefinition)
    {
      TypeDefinition decl0 = (TypeDefinition)decl;
      if (decl0.exp == null) return;
      TypeSymbol symbol = (TypeSymbol)decl0.ident.getSymbol();
      Type.SubType stype = (Type.SubType)symbol.getType();
      FunctionSymbol pred = (FunctionSymbol)stype.pred;
      ValueSymbol[] params = 
          new ValueSymbol[] { (ValueSymbol)pred.params[0].ident.getSymbol() } ;
      ExpSem exp = process(decl0.exp);
      if (!(exp instanceof ExpSem.Single))
        runtimeError(decl0.exp, "subtype predicate must be deterministic");
      dtranslator.FunctionDefinition(pred, params, exp, decl0.exp, 
          new FunSpecSem[] {}, new FunctionSpec[] {}, false);
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Function Specifications
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Process a function specification and return its semantics.
   * @param spec the specification
   * @return its semantics
   ***************************************************************************/
  private FunSpecSem process(FunctionSpec spec)
  {
    if (spec instanceof RequiresSpec)
    {
      RequiresSpec spec0 = (RequiresSpec)spec;
      return getContextCondition(process(spec0.exp));
    }
    if (spec instanceof EnsuresSpec)
    {
      EnsuresSpec spec0 = (EnsuresSpec)spec;
      return getContextCondition(process(spec0.exp));
    }
    if (spec instanceof DecreasesSpec)
    {
      DecreasesSpec spec0 = (DecreasesSpec)spec;
      return getContextFunction(process(spec0.exps));
    }
    if (spec instanceof ContractSpec)
    {
      // no semantics, not used
      return null;
    }
    return null;
  }  
  private FunSpecSem[] process(FunctionSpec[] spec)
  {
    return Arrays.stream(spec).map(this::process).toArray(FunSpecSem[]::new);
  }
  
  // --------------------------------------------------------------------------
  //
  // Commands
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Process a command and return its semantics.
   * @param exp the command
   * @return its semantics
   ***************************************************************************/
  private ComSem process(Command command)
  {
    if (command instanceof AssertCommand)
    {
      AssertCommand command0 = (AssertCommand)command;
      return ctranslator.AssertCommand(command0, process(command0.exp));
    }
    if (command instanceof AssignmentCommand)
    {
      AssignmentCommand command0 = (AssignmentCommand)command;
      ValueSymbol symbol = (ValueSymbol)command0.ident.getSymbol();
      if (command0.sels.length == 0)
      {
        return ctranslator.AssignmentCommand(command0, 
            symbol, process(command0.exp), command0.exp.getType(), true);
      }
      else
      {
        ExpSem exp = process(command0, symbol, command0.sels, command0.exp);
        Type type = getType(symbol.type, command0.sels, command0.exp.getType());
        return ctranslator.AssignmentCommand(command0, symbol, exp, 
            type, false);
      }
    }
    if (command instanceof ChooseCommand)
    {
      ChooseCommand command0 = (ChooseCommand)command;
      ValueSymbol[] symbols = getSymbols(command0.qvar);
      return ctranslator.ChooseCommand(command0, symbols, process(command0.qvar));
    }
    if (command instanceof ChooseElseCommand)
    {
      ChooseElseCommand command0 = (ChooseElseCommand)command;
      ValueSymbol[] symbols = getSymbols(command0.qvar);
      return ctranslator.ChooseElseCommand(command0,
          symbols, process(command0.qvar), 
          process(command0.command1), process(command0.command2));
    }
    if (command instanceof ChooseDoCommand)
    {
      ChooseDoCommand command0 = (ChooseDoCommand)command;
      ValueSymbol[] symbols = getSymbols(command0.qvar);
      return ctranslator.ChooseDoCommand(command0, symbols,
          process(command0.qvar), process(command0.command), 
          process(command0.spec, command0.getVars(), command0.getOldVars()));
    }
    if (command instanceof CommandSequence)
    {
      CommandSequence command0 = (CommandSequence)command;
      return ctranslator.CommandSequence(process(command0.commands));
    }
    if (command instanceof DoWhileCommand)
    {
      DoWhileCommand command0 = (DoWhileCommand)command;
      List<Command> commands = new ArrayList<Command>(2);
      commands.add(command0.command);
      WhileCommand wcommand = 
          new WhileCommand(command0.exp, Arrays.asList(command0.spec), command0.command);
      wcommand.addAll(command0.getVars(), command0.getOldVars());
      commands.add(wcommand);
      return process(new CommandSequence(commands));
    }
    if (command instanceof EmptyCommand)
    {
      return ctranslator.EmptyCommand();
    }
    if (command instanceof ForCommand)
    {
      ForCommand command0 = (ForCommand)command;
      List<Command> body = new ArrayList<Command>(2);
      body.add(command0.command3);
      body.add(command0.command2);
      List<Command> commands = new ArrayList<Command>(2);
      commands.add(command0.command1);
      WhileCommand wcommand = new WhileCommand(command0.exp, 
          Arrays.asList(command0.spec), new CommandSequence(body));
      wcommand.addAll(command0.getVars(), command0.getOldVars());
      commands.add(wcommand);
      return process(new CommandSequence(commands));
    }
    if (command instanceof ExpCommand)
    {
      ExpCommand command0 = (ExpCommand)command;
      return ctranslator.ExpCommand(process(command0.exp));
    }
    if (command instanceof ForInCommand)
    {
      ForInCommand command0 = (ForInCommand)command;
      ValueSymbol[] symbols = getSymbols(command0.qvar);
      return ctranslator.ForInCommand(command0, symbols,
          process(command0.qvar), process(command0.command), 
          process(command0.spec, command0.getVars(), command0.getOldVars()));
    }
    if (command instanceof IfThenCommand)
    {
      IfThenCommand command0 = (IfThenCommand)command;
      return ctranslator.IfThenCommand(command0.exp, process(command0.exp), 
          process(command0.command));
    }
    if (command instanceof IfThenElseCommand)
    {
      IfThenElseCommand command0 = (IfThenElseCommand)command;
      return ctranslator.IfThenElseCommand(command0.exp, process(command0.exp), 
          process(command0.command1), process(command0.command2));
    }
    if (command instanceof MatchCommand)
    {
      MatchCommand command0 = (MatchCommand)command;
      RecursiveType type = (RecursiveType)command0.exp.getType();
      Symbol[] symbols = Arrays.stream(command0.pcommand)
          .map((PatternCommand pcom)->
              pcom instanceof DefaultPatternCommand ? null :
                pcom instanceof IdentifierPatternCommand ? 
                    ((IdentifierPatternCommand)pcom).ident.getSymbol() :
                      ((ApplicationPatternCommand)pcom).ident.getSymbol())
          .toArray(Symbol[]::new);
      return ctranslator.MatchCommand(command0,
          (TypeSymbol)type.ritem.ident.getSymbol(), 
          symbols, process(command0.exp), process(command0.pcommand));
    }
    if (command instanceof PrintCommand)
    {
      PrintCommand command0 = (PrintCommand)command;
      return ctranslator.PrintCommand(command0.string, process(command0.exps));
    }
    if (command instanceof CheckCommand)
    {
      CheckCommand command0 = (CheckCommand)command;
      return ctranslator.CheckCommand(command0,
          (FunctionSymbol)command0.ident.getSymbol(), 
          command0.exp == null ? null : process(command0.exp));
    }
    if (command instanceof ValCommand)
    {
      ValCommand command0 = (ValCommand)command;
      ValueSymbol symbol = (ValueSymbol)command0.ident.getSymbol();
      return ctranslator.AssignmentCommand(command0, 
          symbol, process(command0.exp), command0.exp.getType(), true);
    }
    if (command instanceof VarCommand)
    {
      VarCommand command0 = (VarCommand)command;
      ValueSymbol symbol = (ValueSymbol)command0.ident.getSymbol();
      if (command0.exp == null)
        return (ComSem.Single)(Context c)->c;
      else
        return ctranslator.AssignmentCommand(command0, 
            symbol, process(command0.exp), command0.exp.getType(), true);
    }
    if (command instanceof WhileCommand)
    {
      WhileCommand command0 = (WhileCommand)command;
      return ctranslator.WhileCommand(command0,
          process(command0.exp), process(command0.command), 
          process(command0.spec, command0.getVars(), command0.getOldVars()));
    }
    return null;
  }
  private ComSem[] process(Command[] commands)
  {
    return Arrays.stream(commands).map(this::process).toArray(ComSem[]::new);
  }
  
  /****************************************************************************
   * Process a pattern command and return its semantics.
   * @param pcommand the pattern command
   * @return its semantics
   ***************************************************************************/
  private FunComSem process(PatternCommand pcommand)
  {
    if (pcommand instanceof DefaultPatternCommand)
    {
      DefaultPatternCommand pcommand0 = (DefaultPatternCommand)pcommand;
      return ctranslator.DefaultPatternCommand(process(pcommand0.command));
    }
    if (pcommand instanceof IdentifierPatternCommand)
    {
      IdentifierPatternCommand pcommand0 = (IdentifierPatternCommand)pcommand;
      return ctranslator.IdentifierPatternCommand(process(pcommand0.command));
    }
    if (pcommand instanceof ApplicationPatternCommand)
    {
      ApplicationPatternCommand pcommand0 = (ApplicationPatternCommand)pcommand;
      ValueSymbol[] psymbols =
          Arrays.stream(pcommand0.params)
          .map((Parameter p)->(ValueSymbol)p.ident.getSymbol())
          .toArray(ValueSymbol[]::new);
      return ctranslator.ApplicationPatternCommand(pcommand0, psymbols, process(pcommand0.command));
    }
    return null;
  }
  private FunComSem[] process(PatternCommand[] pcommands)
  {
    return Arrays.stream(pcommands).map(this::process).toArray(FunComSem[]::new);
  }
  
  // --------------------------------------------------------------------------
  //
  // Loop Specifications
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Process a loop specification and return its semantics.
   * @param spec the specification
   * @param vars the list of variables visible in spec
   * @param oldvars the list of "old" variables visible in spec
   * @return its semantics
   ***************************************************************************/
  private LoopSpecSem process(LoopSpec spec, 
    List<ValueSymbol> vars, List<ValueSymbol> oldvars)
  {
    if (spec instanceof DecreasesLoopSpec)
    {
      DecreasesLoopSpec spec0 = (DecreasesLoopSpec)spec;
      return getContextFunction(process(spec0.exps));
    }
    if (spec instanceof InvariantLoopSpec)
    {
      InvariantLoopSpec spec0 = (InvariantLoopSpec)spec;
      return getContextRelation(process(spec0.exp), 
          vars.toArray(new ValueSymbol[vars.size()]), 
          oldvars.toArray(new ValueSymbol[oldvars.size()]));
    }
    return null;
  }
  private LoopSpecSem[] process(LoopSpec[] spec,
    List<ValueSymbol> vars, List<ValueSymbol> oldvars)
  {
    return Arrays.stream(spec)
        .map((LoopSpec spec0)->process(spec0, vars, oldvars))
        .toArray(LoopSpecSem[]::new);
  }
  
  // --------------------------------------------------------------------------
  //
  // Expressions
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Process an expression and return its semantics.
   * @param exp the expression
   * @return its semantics
   ***************************************************************************/
  private ExpSem process(Expression exp)
  {
    if (exp instanceof AndExp)
    {
      AndExp exp0 = (AndExp)exp;
      return etranslator.AndExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof ApplicationExp)
    {
      ApplicationExp exp0 = (ApplicationExp)exp;
      return etranslator.ApplicationExp(exp0, exp0.ident, process(exp0.exps), exp0.exps);
    }
    if (exp instanceof ArrayBuilderExp)
    {
      ArrayBuilderExp exp0 = (ArrayBuilderExp)exp;
      return etranslator.ArrayBuilderExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof AssertExp)
    {
      AssertExp exp0 = (AssertExp)exp;
      return etranslator.AssertExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof BigIntersectExp)
    {
      BigIntersectExp exp0 = (BigIntersectExp)exp;
      return etranslator.BigIntersectExp(process(exp0.exp));
    }
    if (exp instanceof BigUnionExp)
    {
      BigUnionExp exp0 = (BigUnionExp)exp;
      return etranslator.BigUnionExp(process(exp0.exp));
    }
    if (exp instanceof ChooseExp)
    {
      ChooseExp exp0 = (ChooseExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.ChooseExp(exp0, symbols, process(exp0.qvar));
    }
    if (exp instanceof ChooseInElseExp)
    {
      ChooseInElseExp exp0 = (ChooseInElseExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.ChooseInElseExp(exp0, symbols, process(exp0.qvar), 
          process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof ChooseInExp)
    {
      ChooseInExp exp0 = (ChooseInExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.ChooseInExp(exp0,
          symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof DividesExp)
    {
      DividesExp exp0 = (DividesExp)exp;
      return etranslator.DividesExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof EmptySetExp)
      return etranslator.EmptySetExp();
    if (exp instanceof EnumeratedSetExp)
    {
      EnumeratedSetExp exp0 = (EnumeratedSetExp)exp;
      return etranslator.EnumeratedSetExp(process(exp0.exps));
    }
    if (exp instanceof CartesianExp)
    {
      CartesianExp exp0 = (CartesianExp)exp;
      return etranslator.CartesianExp(process(exp0.exps));
    }
    if (exp instanceof EqualsExp)
    {
      EqualsExp exp0 = (EqualsExp)exp;
      return etranslator.EqualsExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof EquivExp)
    {
      EquivExp exp0 = (EquivExp)exp;
      return etranslator.EquivExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof ExistsExp)
    {
      ExistsExp exp0 = (ExistsExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.ExistsExp(symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof FalseExp)
      return etranslator.FalseExp();
    if (exp instanceof ForallExp)
    {
      ForallExp exp0 = (ForallExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.ForallExp(symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof GreaterEqualExp)
    {
      GreaterEqualExp exp0 = (GreaterEqualExp)exp;
      return etranslator.GreaterEqualExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof GreaterExp)
    {
      GreaterExp exp0 = (GreaterExp)exp;
      return etranslator.GreaterExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof IdentifierExp)
    {
      IdentifierExp exp0 = (IdentifierExp)exp;
      ValueSymbol symbol = (ValueSymbol)exp0.ident.getSymbol();
      return etranslator.IdentifierExp(symbol, exp0);
    }
    if (exp instanceof IfThenElseExp)
    {
      IfThenElseExp exp0 = (IfThenElseExp)exp;
      return etranslator.IfThenElseExp(process(exp0.exp1), 
          process(exp0.exp2), process(exp0.exp3));
    }
    if (exp instanceof ImpliesExp)
    {
      ImpliesExp exp0 = (ImpliesExp)exp;
      return etranslator.ImpliesExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof InSetExp)
    {
      InSetExp exp0 = (InSetExp)exp;
      return etranslator.InSetExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof IntersectExp)
    {
      IntersectExp exp0 = (IntersectExp)exp;
      return etranslator.IntersectExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof IntervalExp)
    {
      IntervalExp exp0 = (IntervalExp)exp;
      return etranslator.IntervalExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof LessEqualExp)
    {
      LessEqualExp exp0 = (LessEqualExp)exp;
      return etranslator.LessEqualExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof LessExp)
    {
      LessExp exp0 = (LessExp)exp;
      return etranslator.LessExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof LetExp)
    {
      LetExp exp0 = (LetExp)exp;
      return etranslator.LetExp(process(exp0.binder), process(exp0.exp));
    }
    if (exp instanceof LetParExp)
    {
      LetParExp exp0 = (LetParExp)exp;
      return etranslator.LetParExp(processPar(exp0.binder), process(exp0.exp));
    }
    if (exp instanceof MapBuilderExp)
    {
      MapBuilderExp exp0 = (MapBuilderExp)exp;
      return etranslator.MapBuilderExp((Type.MapType)exp0.getType(), process(exp0.exp));
    }
    if (exp instanceof MapSelectionExp)
    {
      MapSelectionExp exp0 = (MapSelectionExp)exp;
      return etranslator.MapSelectionExp(exp0, (Type.MapType)exp0.exp1.getType(),
          process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof MapUpdateExp)
    {
      MapUpdateExp exp0 = (MapUpdateExp)exp;
      return etranslator.MapUpdateExp(exp0, 
          (Type.MapType)exp0.getType(), exp0.exp3.getType(),
          process(exp0.exp1), process(exp0.exp2), process(exp0.exp3));
    }
    if (exp instanceof MatchExp)
    {
      MatchExp exp0 = (MatchExp)exp;
      RecursiveType type = (RecursiveType)exp0.exp.getType();
      Symbol[] symbols = Arrays.stream(exp0.pexp)
          .map((PatternExpression pexp)->
              pexp instanceof DefaultPatternExp ? null :
                pexp instanceof IdentifierPatternExp ? 
                    ((IdentifierPatternExp)pexp).ident.getSymbol() :
                      ((ApplicationPatternExp)pexp).ident.getSymbol())
          .toArray(Symbol[]::new);
      return etranslator.MatchExp((TypeSymbol)type.ritem.ident.getSymbol(), 
          symbols, process(exp0.exp), process(exp0.pexp));
    }
    if (exp instanceof MinusExp)
    {
      MinusExp exp0 = (MinusExp)exp;
      return etranslator.MinusExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof NegationExp)
    {
      NegationExp exp0 = (NegationExp)exp;
      return etranslator.NegationExp(exp0, process(exp0.exp));
    }
    if (exp instanceof FactorialExp)
    {
      FactorialExp exp0 = (FactorialExp)exp;
      return etranslator.FactorialExp(exp0, process(exp0.exp));
    }
    if (exp instanceof NotEqualsExp)
    {
      NotEqualsExp exp0 = (NotEqualsExp)exp;
      return etranslator.NotEqualsExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof NotExp)
    {
      NotExp exp0 = (NotExp)exp;
      return etranslator.NotExp(process(exp0.exp));
    }
    if (exp instanceof NumberExp)
    {
      NumberExp exp0 = (NumberExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.NumberExp(symbols, process(exp0.qvar));
    }
    if (exp instanceof NumberLiteralExp)
    {
      NumberLiteralExp exp0 = (NumberLiteralExp)exp;
      return etranslator.NumberLiteralExp(exp0.literal);
    }
    if (exp instanceof OrExp)
    {
      OrExp exp0 = (OrExp)exp;
      return etranslator.OrExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof PlusExp)
    {
      PlusExp exp0 = (PlusExp)exp;
      return etranslator.PlusExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof PlusExpMult)
    {
      PlusExpMult exp0 = (PlusExpMult)exp;
      return etranslator.PlusExpMult(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof PowerExp)
    {
      PowerExp exp0 = (PowerExp)exp;
      return etranslator.PowerExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof PowersetExp)
    {
      PowersetExp exp0 = (PowersetExp)exp;
      return etranslator.PowersetExp(process(exp0.exp));
    }
    if (exp instanceof Powerset1Exp)
    {
      Powerset1Exp exp0 = (Powerset1Exp)exp;
      return etranslator.Powerset1Exp(process(exp0.exp1),process(exp0.exp2));
    }
    if (exp instanceof Powerset2Exp)
    {
      Powerset2Exp exp0 = (Powerset2Exp)exp;
      return etranslator.Powerset2Exp(process(exp0.exp1),process(exp0.exp2),
          process(exp0.exp3));
    }
    if (exp instanceof PrintExp)
    {
      PrintExp exp0 = (PrintExp)exp;
      return etranslator.PrintExp(exp0.string, process(exp0.exp));
    }
    if (exp instanceof PrintInExp)
    {
      PrintInExp exp0 = (PrintInExp)exp;
      return etranslator.PrintInExp(exp0.string, process(exp0.exps),
          process(exp0.exp));
    }
    if (exp instanceof CheckExp)
    {
      CheckExp exp0 = (CheckExp)exp;
      return etranslator.CheckExp((FunctionSymbol)exp0.ident.getSymbol(), 
          exp0.exp == null ? null : process(exp0.exp));
    }
    if (exp instanceof RecApplicationExp)
    {
      RecApplicationExp exp0 = (RecApplicationExp)exp;
      return etranslator.RecApplicationExp(
          (TypeSymbol)exp0.ident1.getSymbol(), 
          (FunctionSymbol)exp0.ident2.getSymbol(),
          process(exp0.exps));
    }
    if (exp instanceof RecIdentifierExp)
    {
      RecIdentifierExp exp0 = (RecIdentifierExp)exp;
      return etranslator.RecIdentifierExp(
          (TypeSymbol)exp0.ident1.getSymbol(), 
          (ValueSymbol)exp0.ident2.getSymbol());
    }
    if (exp instanceof RecordExp)
    {
      RecordExp exp0 = (RecordExp)exp;
      return etranslator.RecordExp(process(exp0.eidents));
    }
    if (exp instanceof RecordSelectionExp)
    {
      RecordSelectionExp exp0 = (RecordSelectionExp)exp;
      return etranslator.RecordSelectionExp((Type.RecordType)exp0.exp.getType(),
          process(exp0.exp), (ValueSymbol)exp0.ident.getSymbol());
    }
    if (exp instanceof RecordUpdateExp)
    {
      RecordUpdateExp exp0 = (RecordUpdateExp)exp;
      return etranslator.RecordUpdateExp(exp0,
          (Type.RecordType)exp0.exp1.getType(), exp0.exp2.getType(),
          process(exp0.exp1), (ValueSymbol)exp0.ident.getSymbol(), process(exp0.exp2));
    }
    if (exp instanceof RemainderExp)
    {
      RemainderExp exp0 = (RemainderExp)exp;
      return etranslator.RemainderExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof SetBuilderExp)
    {
      SetBuilderExp exp0 = (SetBuilderExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.SetBuilderExp(symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof SumExp)
    {
      SumExp exp0 = (SumExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.SumExp(exp0, symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof ProductExp)
    {
      ProductExp exp0 = (ProductExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.ProductExp(exp0, symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof MinExp)
    {
      MinExp exp0 = (MinExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.MinExp(exp0, symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof MaxExp)
    {
      MaxExp exp0 = (MaxExp)exp;
      ValueSymbol[] symbols = getSymbols(exp0.qvar);
      return etranslator.MaxExp(exp0, symbols, process(exp0.qvar), process(exp0.exp));
    }
    if (exp instanceof SetSizeExp)
    {
      SetSizeExp exp0 = (SetSizeExp)exp;
      return etranslator.SetSizeExp(process(exp0.exp));
    }
    if (exp instanceof SubsetExp)
    {
      SubsetExp exp0 = (SubsetExp)exp;
      return etranslator.SubsetExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof TimesExp)
    {
      TimesExp exp0 = (TimesExp)exp;
      return etranslator.TimesExp(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof TimesExpMult)
    {
      TimesExpMult exp0 = (TimesExpMult)exp;
      return etranslator.TimesExpMult(exp0, process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof TrueExp)
      return etranslator.TrueExp();
    if (exp instanceof TupleExp)
    {
      TupleExp exp0 = (TupleExp)exp;
      return etranslator.TupleExp(process(exp0.exps));
    }
    if (exp instanceof TupleSelectionExp)
    {
      TupleSelectionExp exp0 = (TupleSelectionExp)exp;
      return etranslator.TupleSelectionExp(process(exp0.exp), exp0.index.getInteger());
    }
    if (exp instanceof TupleUpdateExp)
    {
      TupleUpdateExp exp0 = (TupleUpdateExp)exp;
      return etranslator.TupleUpdateExp(exp0,
          (Type.TupleType)exp0.getType(), exp0.exp2.getType(),
          process(exp0.exp1), exp0.index.getInteger(), process(exp0.exp2));
    }
    if (exp instanceof UnionExp)
    {
      UnionExp exp0 = (UnionExp)exp;
      return etranslator.UnionExp(process(exp0.exp1), process(exp0.exp2));
    }
    if (exp instanceof UnitExp)
      return etranslator.UnitExp();
    if (exp instanceof WithoutExp)
    {
      WithoutExp exp0 = (WithoutExp)exp;
      return etranslator.WithoutExp(process(exp0.exp1), process(exp0.exp2));
    }
    return null;
  }
  private ExpSem[] process(Expression[] exps)
  {
    return Arrays.stream(exps).map(this::process).toArray(ExpSem[]::new);
  }
    
  /****************************************************************************
   * Process a binder and return its semantics.
   * @param binder the binder
   * @return its semantics
   ***************************************************************************/
  private BinderSem process(Binder binder)
  {
    ValueSymbol symbol = (ValueSymbol)binder.ident.getSymbol();
    return etranslator.Binder(symbol, process(binder.exp), binder.exp);
  }
  private BinderSem[] process(Binder[] binders)
  {
    return Arrays.stream(binders).map(this::process).toArray(BinderSem[]::new);
  }
  private BinderSem processPar(Binder[] binders)
  {
    int n = binders.length;
    ValueSymbol[] symbols = new ValueSymbol[n];
    Expression[] exps = new Expression[n];
    ExpSem[] es = new ExpSem[n];
    for (int i=0; i<n; i++)
    {
      symbols[i] = (ValueSymbol)binders[i].ident.getSymbol();
      exps[i] = binders[i].exp;
      es[i] = process(exps[i]);
    }
    return etranslator.Binder(symbols, es, exps);
  }
  
  /****************************************************************************
   * Process a pattern expression and return its semantics.
   * @param pexp the pattern expression
   * @return its semantics
   ***************************************************************************/
  private FunExpSem process(PatternExpression pexp)
  {
    if (pexp instanceof DefaultPatternExp)
    {
      DefaultPatternExp pexp0 = (DefaultPatternExp)pexp;
      return etranslator.DefaultPatternExp(process(pexp0.exp));
    }
    if (pexp instanceof IdentifierPatternExp)
    {
      IdentifierPatternExp pexp0 = (IdentifierPatternExp)pexp;
      return etranslator.IdentifierPatternExp(process(pexp0.exp));
    }
    if (pexp instanceof ApplicationPatternExp)
    {
      ApplicationPatternExp pexp0 = (ApplicationPatternExp)pexp;
      ValueSymbol[] psymbols =
          Arrays.stream(pexp0.params)
          .map((Parameter p)->(ValueSymbol)p.ident.getSymbol())
          .toArray(ValueSymbol[]::new);
      return etranslator.ApplicationPatternExp(psymbols, process(pexp0.exp));
    }
    return null;
  }
  private FunExpSem[] process(PatternExpression[] pexps)
  {
    return Arrays.stream(pexps).map(this::process).toArray(FunExpSem[]::new);
  }
  
  /****************************************************************************
   * Process an expression identifier and return its semantics.
   * @param binder the binder
   * @return its semantics
   ***************************************************************************/
  private ExpSem process(ExpressionIdentifier eident)
  {
    return process(eident.exp);
  }
  private ExpSem[] process(ExpressionIdentifier[] eidents)
  {
    return Arrays.stream(eidents).map(this::process).toArray(ExpSem[]::new);
  }
  
  /***************************************************************************
   * Get symbols of quantified variables.
   * @param qvar the quantified variables.
   * @return the symbols.
   **************************************************************************/
  private ValueSymbol[] getSymbols(QuantifiedVariable qvar)
  {
    return Arrays.stream(qvar.qvcore)
        .map((QuantifiedVariableCore qvcore)->(ValueSymbol)qvcore.ident.getSymbol())
        .toArray(ValueSymbol[]::new);
  }
  
  /****************************************************************************
   * Process a quantified variable and return its semantics.
   * @param qvar the quantified variable
   ***************************************************************************/
  private QvarSem process(QuantifiedVariable qvar)
  {
    ValueSymbol[] symbols = getSymbols(qvar);
    ExpSem e = qvar.exp == null ?
        (ExpSem.Single)(Context c)->new Value.Bool(true) :
        process(qvar.exp);
    return etranslator.QuantifiedVariable(symbols, 
        process(symbols, qvar.qvcore), e);
  }
  private QvarSem process(ValueSymbol[] symbols, QuantifiedVariableCore[] qvar)
  {
    QvarCoreSem[] q = Arrays.stream(qvar)
        .map(this::process).toArray(QvarCoreSem[]::new);
    return etranslator.QuantifiedVariableCore(symbols, q);
  }
  private QvarCoreSem process(QuantifiedVariableCore qvar)
  {
    if (qvar instanceof IdentifierSetQuantifiedVar)
    {
      IdentifierSetQuantifiedVar qvar0 = (IdentifierSetQuantifiedVar)qvar;
      return etranslator.IdentifierSetQuantifiedVar(process(qvar0.exp));
    }
    if (qvar instanceof IdentifierTypeQuantifiedVar)
    {
      IdentifierTypeQuantifiedVar qvar0 = (IdentifierTypeQuantifiedVar)qvar;
      ValueSymbol symbol = (ValueSymbol)qvar0.ident.getSymbol();
      return etranslator.IdentifierTypeQuantifiedVar(qvar0, symbol.type);
    }
    return null;
  }

  // -------------------------------------------------------------------------
  //
  // Selector
  //
  // -------------------------------------------------------------------------
 
  /***************************************************************************
   * Replace in compound type selected subtype by given base type
   * @param type the compound type.
   * @param sels the selectors.
   * @param base the base type.
   * @return the replacement type.
   **************************************************************************/
  private Type getType(Type type, Selector[] sels, Type base)
  {
    return getType(0, type, sels, base);
  }
  
  /***************************************************************************
   * Replace in compound type selected subtype by given base type
   * @param i the index of the first selector
   * @param type the compound type.
   * @param sels the selectors.
   * @param base the base type.
   * @return the replacement type.
   **************************************************************************/
  private Type getType(int i, Type type, Selector[] sels, Type base)
  {
    int n = sels.length;
    if (i == n) return base;
    Selector sel = sels[i];
    if (sel instanceof MapSelector)
    {
      MapType type0 = (MapType)type;
      return new MapType(type0.type1, getType(i+1, type0.type2, sels, base));
    }
    if (sel instanceof RecordSelector)
    {
      RecordSelector sel0 = (RecordSelector)sel;
      RecordType type0 = (RecordType)type;
      ValueSymbol symbol = (ValueSymbol)sel0.ident.getSymbol();
      List<ValueSymbol> symbols = new ArrayList<ValueSymbol>(type0.symbols.length);
      for (ValueSymbol s : type0.symbols)
      {
        Type t = s.type;
        Type t0 = s != symbol ? t : getType(i+1, t, sels, base);
        symbols.add(new ValueSymbol(s.ident, t0, s.kind));
      }
      return new RecordType(symbols, true);
    }
    if (sel instanceof TupleSelector)
    {
      TupleSelector sel0 = (TupleSelector)sel;
      TupleType type0 = (TupleType)type;
      int index = sel0.index.getInteger();
      List<Type> types = new ArrayList<Type>(type0.types.length);
      int k=1;
      for (Type t : type0.types)
      {
        Type t0 = k != index ? t : getType(i+1, t, sels, base);
        types.add(t0);
        k++;
      }
      return new TupleType(types);
    }
    return null;
  }
  
  /***************************************************************************
   * Process an assignment with selectors and return the semantics of the 
   * expression assigned to the basic variable.
   * @param com the command itself.
   * @param symbol the symbol of the basic variable.
   * @param sels the selectors (length > 0).
   * @param exp the expression to be assigned to the selected component.
   * @return the semantics of the expression assigned to the basic variable.
   **************************************************************************/
  private ExpSem process(AST.Command.AssignmentCommand com, 
    ValueSymbol symbol, Selector[] sels, Expression exp)
  {
    IdentifierExp ref = new IdentifierExp(symbol.ident);
    ref.setPosition(symbol.ident.getPosition());
    ref.setType(symbol.type);
    return process(com, 0, sels, symbol.type, process(ref), 
        process(exp), exp.getType());
  }
  
  /***************************************************************************
   * Process selectors starting at a particular index.
   * @param com the command from which the selectors arose.
   * @param i the current index, 0 <= i <= sels.length
   * @param sels the selectors
   * @param type the type of ref
   * @param ref the expression to which selector i is applied
   * @param exp the expression assigned to the last selector.
   * @param etype the type of exp
   * @return the semantics of the expression represented by the selectors.
   ***************************************************************************/
  private ExpSem process(AST.Command.AssignmentCommand com, 
    int i, Selector[] sels, Type type, ExpSem ref, ExpSem exp, Type etype)
  {
    int n = sels.length;
    if (i == n) return exp;
    Selector sel = sels[i];
    // only last update is runtime type-checked
    Type etype0 = (i == n-1) ? etype : null;
    if (sel instanceof MapSelector)
    {
      MapSelector sel0 = (MapSelector)sel;
      MapType type0 = (MapType)type;
      ExpSem exp0 = process(sel0.exp);
      ExpSem ref0 = etranslator.MapSelectionExp(sel0.exp, type0, ref, exp0);
      ExpSem exp1 = process(com, i+1, sels, type0.type2, ref0, exp, etype);
      return etranslator.MapUpdateExp(com, type0, etype0, ref, exp0, exp1);
    }
    if (sel instanceof RecordSelector)
    {
      RecordSelector sel0 = (RecordSelector)sel;
      RecordType type0 = (RecordType)type;
      ValueSymbol symbol = (ValueSymbol)sel0.ident.getSymbol();
      ExpSem ref0 = etranslator.RecordSelectionExp(type0, ref, symbol);
      ExpSem exp1 = process(com, i+1, sels, symbol.type, ref0, exp, etype);
      return etranslator.RecordUpdateExp(com, type0, etype0, ref, symbol, exp1);
    }
    if (sel instanceof TupleSelector)
    {
      TupleSelector sel0 = (TupleSelector)sel;
      TupleType type0 = (TupleType)type;
      int index = sel0.index.getInteger();
      ExpSem ref0 = etranslator.TupleSelectionExp(ref, index);
      ExpSem exp1 = process(com, i+1, sels, type0.types[index-1], ref0, exp, etype);
      return etranslator.TupleUpdateExp(com, type0, etype0, ref, index, exp1);
    }
    return null;
  }

  // --------------------------------------------------------------------------
  //
  // Specification handling
  //
  // --------------------------------------------------------------------------
      
  /****************************************************************************
   * Convert expression to context condition.
   * @param exp the expression.
   * @return the context condition.
   ***************************************************************************/
  private ContextCondition getContextCondition(ExpSem exp)
  {
    if (exp instanceof ExpSem.Single)
    {
      ExpSem.Single exp0 = (ExpSem.Single)exp; 
      return (ContextCondition.Single)(Context c)->
      (Value.Bool)exp0.apply(c);
    }
    else
    {
      ExpSem.Multiple exp0 = (ExpSem.Multiple)exp; 
      return (ContextCondition.Multiple)(Context c)->
      exp0.apply(c).apply((Value v)->(Value.Bool)v);
    }
  }
  
  /****************************************************************************
   * Convert expression to context relation.
   * @param exp the expression
   * @param vars the symbols of the current variables in the context.
   * @param oldvars the symbols of the old variables in the context.
   * @return the context relation.
   ***************************************************************************/
  private ContextRelation getContextRelation(ExpSem exp,
    ValueSymbol[] vars, ValueSymbol[] oldvars)
  {
    int[] slots = toSlots(vars);
    int[] oslots = toSlots(oldvars);
    String[] onames = toNames(oldvars);
    int n = slots.length;
    if (exp instanceof ExpSem.Single)
    {
      ExpSem.Single exp0 = (ExpSem.Single)exp;
      return (ContextRelation.Single)(Context c, Context cold)->
      {
        for (int i=0; i<n; i++)
        {
          Value value = cold.get(slots[i]);
          c.set(oslots[i], value, onames[i]);
        }
        return (Value.Bool)exp0.apply(c);
      };
    }
    else
    {
      ExpSem.Multiple exp0 = (ExpSem.Multiple)exp;
      return (ContextRelation.Multiple)(Context c, Context cold)->
      {
        for (int i=0; i<n; i++)
        {
          Value value = cold.get(slots[i]);
          c.set(oslots[i], value, onames[i]);
        }
        return exp0.apply(c).apply((Value v)->(Value.Bool)v);
      };
    }
  }
  
  /****************************************************************************
   * Convert expressions to context function.
   * @param exps the expressions.
   * @return the context function.
   ***************************************************************************/
  private ContextFunction getContextFunction(ExpSem[] exps)
  {
    int n = exps.length;
    if (Arrays.stream(exps).allMatch((ExpSem e)->e instanceof ExpSem.Single))
    {
      ExpSem.Single[] exps0 = new ExpSem.Single[n];
      for (int i=0; i<n; i++) exps0[i] = (ExpSem.Single)exps[i];
      return (ContextFunction.Single)(Context c)->
      {
        Value.Int[] m = new Value.Int[n];
        for (int i=0; i<n; i++) m[i] = (Value.Int)exps0[i].apply(c);
        return m;
      };
    }
    else
    {
      ExpSem.Multiple[] exps0 = new ExpSem.Multiple[n];
      for (int i=0; i<n; i++) exps0[i] = exps[i].toMultiple();
      return (ContextFunction.Multiple)(Context c)->
      {
        @SuppressWarnings("unchecked")
        Seq<Value>[] values = new Seq[n];
        for (int i=0; i<n;i++) values[i] = exps0[i].apply(c);
        return Types.getCombinations(values);
      };
    }
  }
  
  /****************************************************************************
   * Create list of initial values of variants.
   * @param c the current context.
   * @param variants the semantics of the variants.
   * @param cvariants the syntax of the variants.
   * @return a list with one entry per variant; each entry is a list of
   * value vectors arising from the potentially non-deterministic variants.
   ***************************************************************************/
  public List<List<Value[]>> initMeasures(Context c, 
    List<ContextFunction> variants, List<AST> cvariants)
  {
    List<List<Value[]>> measures = new ArrayList<List<Value[]>>();
    int i = 0;
    for (ContextFunction variant : variants)
    {
      List<Value[]> ms = evalVariant(c, variant, cvariants.get(i));
      measures.add(ms);
      i++;
    }
    return measures;
  }
  
  /****************************************************************************
   * Check measures list by new values of variants and trigger a runtime
   * exception if some variant is not decreased or becomes negative.
   * @param fun the current function
   * @param measures the old measures list.
   * @param c the current context.
   * @param variants the semantics of the variants.
   * @param cvariants the syntax of the variants.
   * @return the new measures list
   ***************************************************************************/
  public List<List<Value[]>> checkMeasures(List<List<Value[]>> measures, 
    Context c, List<ContextFunction> variants, List<AST>  cvariants)
  {
    int i = 0;
    List<List<Value[]>> nmeasures = new ArrayList<List<Value[]>>(measures.size());
    for (ContextFunction variant : variants)
    {
      ContextFunction variant0 = (ContextFunction)variant;
      List<Value[]> oldms = measures.get(i);
      List<Value[]> newms = evalVariant(c, variant0, cvariants.get(i));
      for (Value[] newm : newms)
      {
        for (Value[] oldm : oldms)
        {
          int n = oldm.length;
          boolean less = false;
          for (int j=0; j<n; j++)
          {
            int om = ((Value.Int)oldm[j]).getValue();
            int nm = ((Value.Int)newm[j]).getValue();
            if (nm < om) { less = true ; break; };
            if (nm > om) break;
          }
          if (!less)
            runtimeError(cvariants.get(i), "variant value " + toString(newm) + 
                " is not less than old value " + toString(oldm));
        }
      }
      nmeasures.add(newms);
      i++;
    }
    return nmeasures;
  }
  
  /****************************************************************************
   * Get values of (potentially non-deterministic) variant.
   * @param c the current context.
   * @param variant the semantics of the variant.
   * @param cvariant the syntax of the variants.
   * @return the list of value vectors.
   ***************************************************************************/
  private List<Value[]> evalVariant(Context c, 
    ContextFunction variant, AST cvariant)
  {
    List<Value[]> ms = new ArrayList<Value[]>(1);
    if (variant instanceof ContextFunction.Single)
    {
      ContextFunction.Single variant0 = (ContextFunction.Single)variant;
      Value[] vs = variant0.apply(c);
      for (Value v : vs)
      {
        Value.Int v0 = (Value.Int)v;
        if (v0.getValue() < 0) 
          runtimeError(cvariant, "negative variant value " + toString(vs));
      }
      ms.add(vs);
    }
    else
    {
      ContextFunction.Multiple variant0 = (ContextFunction.Multiple)variant;
      Seq<Value[]> vs = variant0.apply(c);
      while (true)
      {
        Seq.Next<Value[]> vs0 = vs.get();
        if (vs0 == null) break;
        for (Value v : vs0.head)
        {
          Value.Int v0 = (Value.Int)v;
          if (v0.getValue() < 0) 
            runtimeError(cvariant, "negative variant value " + toString(vs0.head));
        }
        ms.add(vs0.head);
        vs = vs0.tail;
      }
    }
    return ms;
  }
  
  /****************************************************************************
   * Convert list of values to string
   * @param is the list
   * @return the string
   ***************************************************************************/
  private static String toString(Value[] vs)
  {
    StringBuffer b = new StringBuffer();
    int n = vs.length;
    for (int i=0; i<n; i++)
    {
      b.append(vs[i]);
      if (i+1 < n) b.append(",");
    }
    return b.toString();
  }
  
  // -------------------------------------------------------------------------
  //
  // Auxiliaries
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Convert long to int.
   * @param tree a syntactic phrase where this conversion is required.
   * @param value the value to be converted.
   * @return the converted value (raises an exception, if not possible).
   **************************************************************************/
  public int toInt(AST tree, long value)
  {
    if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE)
      runtimeError(tree, "value is too large");
    return (int)value;
  }
      
  /***************************************************************************
   * Convert value symbols to slots.
   * @param symbols the symbols
   * @return the slots
   **************************************************************************/
  public static int[] toSlots(ValueSymbol[] symbols)
  { return Arrays.stream(symbols).mapToInt((ValueSymbol s)->s.getSlot()).toArray(); }

  /***************************************************************************
   * Convert value symbol to slot.
   * @param symbols the symbol
   * @return the slot
   **************************************************************************/
  public static int toSlot(ValueSymbol symbol) 
  { return symbol.getSlot(); }

  /***************************************************************************
   * Convert value symbols to names.
   * @param symbols the symbols
   * @return the names
   **************************************************************************/
  public static String[] toNames(ValueSymbol[] symbols)
  { return Arrays.stream(symbols).map((ValueSymbol s)->s.ident.string).toArray(String[]::new); }

  /***************************************************************************
   * Convert value symbol to name.
   * @param symbols the symbol
   * @return the name
   **************************************************************************/
  public static String toName(ValueSymbol symbol) 
  { return symbol.ident.string; }
  
  /***************************************************************************
   * Get index of recursive type component.
   * @param tsymbol the symbol of the recursive type.
   * @param symbol the symbol of the component.
   * @return the index.
   **************************************************************************/
  public static int getOption(TypeSymbol tsymbol, Symbol symbol)
  {
    AST.Type.RecursiveType type = (AST.Type.RecursiveType)tsymbol.getType();
    AST.RecursiveIdentifier[] ridents = type.ritem.ridents;
    int n = ridents.length;
    for (int i = 0; i < n; i ++)
    {
      Symbol symbol0 = ridents[i].ident.getSymbol();
      if (symbol0 == symbol) return i; 
    }
    return -1;
  }
  
  /***************************************************************************
   * Get index of record type component.
   * @param type the record type.
   * @param symbol the symbol of the component.
   * @return the index.
   **************************************************************************/
  public static int getIndex(AST.Type.RecordType type, ValueSymbol symbol)
  {
    ValueSymbol[] symbols = type.symbols;
    int n = symbols.length;
    for (int i = 0; i < n; i ++)
    {
      if (symbols[i] == symbol) return i; 
    }
    return -1;
  }
  
  /****************************************************************************
   * Get index of selected option
   * @param options the array of option values (-1: default)
   * @param option the option
   * @return the index of the option in the array (exception, if none)
   ***************************************************************************/
  public static int getIndex(Integer[] options, int option)
  {
    int n = options.length;
    for (int i=0; i<n; i++)
    {
      int o = options[i];
      if (o == -1 || o == option) return i;
    }
    throw new RuntimeException("unmatched option " + (option+1));
  }
  
  /***************************************************************************
   * Check whether a value of a given type satisfies the constraints
   * of a weakly matching type.
   * @param ast the construct where the matching occurs
   * @param value the value
   * @param vtype the type of the value, weakly matching stype.
   * @param stype the static type whose constraints are to be satisfied.
   * @return true if the value satisfies the constraints of the type.
   **************************************************************************/
  public void checkConstraints(AST ast, Value value, Type vtype, Type stype)
  {
    boolean okay = Values.checkSize(value, vtype, stype);
    if (!okay) runtimeError(ast, "size constraint of type " + stype + 
        " violated by value " + value);
    if (!(stype instanceof SubType)) return;
    SubType stype0 = (SubType)stype;  
    FunSem.Single fun = (FunSem.Single)stype0.pred.getValue();
    Value.Bool result = (Value.Bool)fun.apply(new Argument(new Value[] { value }));
    if (!result.getValue())
      runtimeError(ast, "constraint of subtype " + stype + 
          " violated by value " + value);
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------