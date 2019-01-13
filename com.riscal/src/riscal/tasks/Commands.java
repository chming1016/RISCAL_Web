// ---------------------------------------------------------------------------
// Commands.java
// Generate verification conditions for commands.
// $Id: Commands.java,v 1.34 2018/06/07 10:40:24 schreine Exp $
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
import java.util.function.*;

import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.syntax.AST.Command.*;
import riscal.syntax.AST.Expression.*;
import riscal.syntax.AST.Selector.MapSelector;
import riscal.syntax.AST.Selector.RecordSelector;
import riscal.syntax.AST.Selector.TupleSelector;
import riscal.types.*;
import riscal.types.Environment.Symbol.*;
import riscal.tasks.Validation.Condition;
import riscal.tasks.Validation.Condition.Kind;

public class Commands 
{
  /****************************************************************************
   * Generate verification conditions for procedure.
   * @param info the information about the procedure.
   * @param callers the recursion map
   * @return the verification conditions.
   ***************************************************************************/
  public static List<Condition> process(OpInfo info, Recursion.CallMap callers)
  {
    List<Condition> post = new ArrayList<Condition>();
    post.addAll(Preconditions.generate(info.bodyexp, info.symbol, callers));
    List<Binder> binders = new ArrayList<Binder>();
    binders.add(new Binder(new Identifier(TypeChecker.resultName), info.bodyexp));
    for (FunctionSpec post0 : info.post)
    {
      FunctionSpec.EnsuresSpec post1 = (FunctionSpec.EnsuresSpec)post0;
      Expression postFormula = post1.exp;
      Expression exp = new LetExp(binders, postFormula);
      Condition cond = new Condition(Kind.ResultCorrect, postFormula, exp);
      cond.addPosition(info.bodyexp.getPosition());
      post.add(cond);
    }
    Commands processor = new Commands(info, callers);
    return processor.process(info.commands, post);
  }
  
  // the context information
  private OpInfo info;
  private Recursion.CallMap callers;
  private Commands(OpInfo info, Recursion.CallMap callers)
  {
    this.info = info;
    this.callers = callers;
  }
  
  /****************************************************************************
   * Generate verification conditions for sequence of commands.
   * @param commands the command sequence.
   * @param post the postconditions to be established by the sequence.
   * @return the verification conditions.
   ***************************************************************************/
  private List<Condition> process(Command[] commands, List<Condition> post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(def(commands));
    for (Condition p : post)
      result.addAll(pre(commands, p));
    return result;
  }
  
  /****************************************************************************
   * Generate definedness conditions for sequence of commands.
   * @param commands the command sequence.
   * @return the definedness conditions.
   ***************************************************************************/
  private List<Condition> def(Command[] commands)
  {
    List<Condition> result = new ArrayList<Condition>();
    int n = commands.length;
    for (int i = n-1; i >= 0; i--)
    {
      Command c = commands[i];
      result = pre(c, result);
      result.addAll(0, def(c));
    }
    return result;
  }
  
  /****************************************************************************
   * Generate preconditions for sequence of commands.
   * @param commands the command sequence.
   * @param post the postcondition to be established by the sequence.
   * @return the preconditions necessary to establish the postcondition.
   ***************************************************************************/
  private List<Condition> pre(Command[] commands, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(post);
    int n = commands.length;
    for (int i = n-1; i >= 0; i--)
    {
      Command c = commands[i];
      result = pre(c, result);
    }
    return result;
  }
      
  /****************************************************************************
   * Generate preconditions for a command.
   * @param command the command.
   * @param post the list of postconditions to be established by the command.
   * @return the preconditions sufficient to establish the postconditions.
   ***************************************************************************/
  private List<Condition> pre(Command command, List<Condition> post)
  {
    List<Condition> result = new ArrayList<Condition>();
    for (Condition p : post)
      result.addAll(pre(command, p));
    return result;
  }
  
  /****************************************************************************
   * Generate definedness conditions for a command.
   * @param command the command.
   * @param result the list to which the conditions are added.
   ***************************************************************************/
  private List<Condition> def(Command command)
  {
    if (command instanceof AssertCommand)
      return def((AssertCommand)command);
    else if (command instanceof AssignmentCommand)
      return def((AssignmentCommand)command);   
    else if (command instanceof ChooseCommand)
      return def((ChooseCommand)command); 
    else if (command instanceof ChooseElseCommand)
      return def((ChooseElseCommand)command); 
    else if (command instanceof ChooseDoCommand)
      return def((ChooseDoCommand)command); 
    else if (command instanceof CommandSequence)
      return def((CommandSequence)command); 
    else if (command instanceof DoWhileCommand)
      return def((DoWhileCommand)command);
    else if (command instanceof EmptyCommand)
      return def((EmptyCommand)command); 
    else if (command instanceof ExpCommand)
      return def((ExpCommand)command); 
    else if (command instanceof ForCommand)
      return def((ForCommand)command); 
    else if (command instanceof ForInCommand)
      return def((ForInCommand)command); 
    else if (command instanceof IfThenCommand)
      return def((IfThenCommand)command); 
    else if (command instanceof IfThenElseCommand)
      return def((IfThenElseCommand)command); 
    else if (command instanceof MatchCommand)
      return def((MatchCommand)command); 
    else if (command instanceof PrintCommand)
      return def((PrintCommand)command); 
    else if (command instanceof CheckCommand)
      return def((CheckCommand)command); 
    else if (command instanceof ValCommand)
      return def((ValCommand)command);
    else if (command instanceof VarCommand)
      return def((VarCommand)command);
    else if (command instanceof WhileCommand)
      return def((WhileCommand)command); 
    return null;
  }
  
  /****************************************************************************
   * Compute sufficient precondition of command with respect to postcondition.
   * @param command the command.
   * @param post the postcondition.
   * @return the precondition (sufficient but as weak as possible)
   ***************************************************************************/
  private List<Condition> pre(Command command, Condition post)
  {
    if (command instanceof AssertCommand)
      return pre((AssertCommand)command, post);
    else if (command instanceof AssignmentCommand)
      return pre((AssignmentCommand)command, post);   
    else if (command instanceof ChooseCommand)
      return pre((ChooseCommand)command, post); 
    else if (command instanceof ChooseElseCommand)
      return pre((ChooseElseCommand)command, post); 
    else if (command instanceof ChooseDoCommand)
      return pre((ChooseDoCommand)command, post); 
    else if (command instanceof CommandSequence)
      return pre((CommandSequence)command, post); 
    else if (command instanceof DoWhileCommand)
      return pre((DoWhileCommand)command, post);
    else if (command instanceof EmptyCommand)
      return pre((EmptyCommand)command, post); 
    else if (command instanceof ExpCommand)
      return pre((ExpCommand)command, post); 
    else if (command instanceof ForCommand)
      return pre((ForCommand)command, post); 
    else if (command instanceof ForInCommand)
      return pre((ForInCommand)command, post); 
    else if (command instanceof IfThenCommand)
      return pre((IfThenCommand)command, post); 
    else if (command instanceof IfThenElseCommand)
      return pre((IfThenElseCommand)command, post); 
    else if (command instanceof MatchCommand)
      return pre((MatchCommand)command, post); 
    else if (command instanceof PrintCommand)
      return pre((PrintCommand)command, post); 
    else if (command instanceof CheckCommand)
      return pre((CheckCommand)command, post); 
    else if (command instanceof ValCommand)
      return pre((ValCommand)command, post);
    else if (command instanceof VarCommand)
      return pre((VarCommand)command, post);
    else if (command instanceof WhileCommand)
      return pre((WhileCommand)command, post); 
    else
      return null;
  }
  
  /****************************************************************************
   * AssertCommand
   ***************************************************************************/
  private List<Condition> def(AssertCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(new Condition(Kind.Assertion, command.exp, command.exp));
    return result;
  }
  private List<Condition> pre(AssertCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(addPosition(post, command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * AssignmentCommand
   ***************************************************************************/
  private List<Condition> def(AssignmentCommand command)
  {
    Expression exp0 = process(command, false);
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(exp0));
    ValueSymbol symbol = (ValueSymbol)command.ident.getSymbol();
    // for-loop identifier may have been generated by syntactic transformation
    // which must not yield a constraint
    if (symbol != null)
    {
      Expression exp1 = Validation.getConstraint(symbol.type, exp0, exp0.getType());
      if (exp1 != null)
        result.add(new Condition(Condition.Kind.Assignment, command, exp1));
    }
    return result;
  }
  private List<Condition> pre(AssignmentCommand command, Condition post)
  {
    Expression exp0 = process(command, true);
    List<Binder> binders = new ArrayList<Binder>();
    binders.add(new Binder(command.ident, exp0));
    Expression exp = new LetExp(binders, post.exp);
    List<Condition> result = new ArrayList<Condition>();
    result.add(addPosition(post.clone(exp), command.getPosition()));
    return result;
  }
  
  /***************************************************************************
   * Determine the expression assigned to the basic variable of assignment.
   * @param command the assignment.
   * @param contracts true if contract replacement is to take place.
   * @return the expression.
   *************************************************************************/
  private Expression process(AssignmentCommand command, boolean contracts)
  {
    Expression exp0 = contracts ? contracts(command.exp) : command.exp;
    if (command.sels.length == 0) return exp0;
    IdentifierExp ref = new IdentifierExp(command.ident);
    ValueSymbol symbol = (ValueSymbol)command.ident.getSymbol();
    ref.setType(symbol.type);
    return process(command.sels, 0, ref, exp0);
  }
  
  /***************************************************************************
   * Translate selectors starting at a particular index.
   * @param sels the selectors
   * @param i the current index
   * @param ref the expression to which selector i is applied
   * @param exp the expression assigned to the last selector
   * @return the expression represented by the selectors
   **************************************************************************/
  private Expression process(Selector[] sels, int i, 
    Expression ref, Expression exp)
  {
    int n = sels.length;
    if (i == n) return exp;
    Type type = ref.getType();
    Selector sel = sels[i];
    if (sel instanceof MapSelector)
    {
      MapSelector sel0 = (MapSelector)sel;
      Expression exp0 = contracts(sel0.exp);
      Expression ref0 = new MapSelectionExp(ref, exp0);
      Type type0 = ((Type.MapType)type).type2;
      ref0.setType(type0);
      ref0.setPosition(sel0.getPosition());
      Expression exp1 = process(sels, i+1, ref0, exp);
      Expression result = new MapUpdateExp(ref, exp0, exp1);
      result.setPosition(exp.getPosition());
      result.setType(type);
      return result;
    }
    if (sel instanceof RecordSelector)
    {
      RecordSelector sel0 = (RecordSelector)sel;
      Identifier ident = sel0.ident;
      Expression ref0 = new RecordSelectionExp(ref, ident);
      ref0.setPosition(sel0.getPosition());
      Expression exp1 = process(sels, i+1, ref0, exp);
      Expression result = new RecordUpdateExp(ref, ident, exp1);
      result.setPosition(exp.getPosition());
      Type type0 = null;
      for (ValueSymbol symbol : ((Type.RecordType)type).symbols)
      {
        if (ident.string.equals(symbol.ident.string))
        {
          type0 = symbol.type;
          break;
        }
      }
      ref0.setType(type0);
      result.setType(type);
      return result;
    }
    if (sel instanceof TupleSelector)
    {
      TupleSelector sel0 = (TupleSelector)sel;
      Decimal index = sel0.index;
      Expression ref0 = new TupleSelectionExp(ref, index);
      ref0.setPosition(sel0.getPosition());
      Expression exp1 = process(sels, i+1, ref0, exp);
      Expression result = new TupleUpdateExp(ref, index, exp1);
      result.setPosition(exp.getPosition());
      Type type0 = ((Type.TupleType)type).types[index.getInteger()-1];
      ref0.setType(type0);
      result.setType(type);
      return result;
    }
    return null;
  }
  
  /****************************************************************************
   * ChooseCommand
   ***************************************************************************/
  private List<Condition> def(ChooseCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.qvar));
    Expression exp = new ExistsExp(command.qvar, new TrueExp());
    result.addAll(preconditions(exp));
    result.add(new Condition(Condition.Kind.Choice, command.qvar, exp));
    return result;
  }
  private List<Condition> pre(ChooseCommand command, Condition post)
  {
    Expression exp = new ForallExp(contracts(command.qvar), post.exp);
    List<Condition> result = new ArrayList<Condition>();
    result.add(addPosition(post.clone(exp), command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * ChooseElseCommand
   ***************************************************************************/
  private List<Condition> def(ChooseElseCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.qvar));
    List<Condition> result1 = def(command.command1);
    List<Condition> result2 = def(command.command2);
    clone(command, result, result1, result2);
    return result;
  }
  private List<Condition> pre(ChooseElseCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    List<Condition> result1 = pre(command.command1, post);
    List<Condition> result2 = pre(command.command2, post);
    clone(command, result, result1, result2);
    return result;
  }
  private void clone(ChooseElseCommand command, List<Condition> result,
    List<Condition> result1, List<Condition> result2)
  {
    QuantifiedVariable qvar0 = contracts(command.qvar);
    SourcePosition pos = qvar0.getPosition();
    clone(result, result1,
        (Expression e)-> new ImpliesExp(
            new ExistsExp(qvar0, new TrueExp()),
            new ForallExp(qvar0, e)), pos);
    clone(result, result2,
        (Expression e)-> new ImpliesExp(
            new NotExp(new ExistsExp(qvar0, new TrueExp())),
            e), pos);
  }
  
  /****************************************************************************
   * ChooseDoCommand
   ***************************************************************************/
  private List<Condition> def(ChooseDoCommand command)
  {
    Command command0 = replace(command);
    return def(command0);
  }
  private List<Condition> pre(ChooseDoCommand command, Condition post)
  {
    Command command0 = replace(command);
    return pre(command0, post);
  }
  private Command replace(ChooseDoCommand command)
  {
    // the choice variable and the choice set
    QuantifiedVariable qvar0 = command.qvar;
    QuantifiedVariableCore[] qvcore = qvar0.qvcore;
    Expression chooseSet = AST.reference(TypeChecker.chooseSetName);
    
    // reference to element and its type
    Expression element;
    Type elementType;
    if (qvcore.length == 1)
    {
      element = AST.reference(qvcore[0].ident.string);
      ValueSymbol symbol = (ValueSymbol)qvcore[0].ident.getSymbol();
      elementType = symbol.type;
    }
    else
    {
      List<Expression> ids = new ArrayList<Expression>();
      List<Type> types = new ArrayList<Type>();
      for (QuantifiedVariableCore qv : qvcore)
      {
        ids.add(AST.reference(qv.ident.string));
        ValueSymbol symbol = (ValueSymbol)qvcore[0].ident.getSymbol();
        types.add(symbol.type);
      }
      element = new TupleExp(ids);
      elementType = new Type.TupleType(types);
      elementType.setCanonical();
    }
    Type setType = new Type.SetType(elementType);
    setType.setCanonical();
    
    // building the loop
    List<Command> body = new ArrayList<Command>();
    body.add(new ChooseCommand(command.qvar));
    body.add(command.command);
    List<Expression> exps = new ArrayList<Expression>();
    exps.add(element);
    body.add(
        new AssignmentCommand(new Identifier(TypeChecker.chooseSetName),
        new ArrayList<Selector>(), 
        new UnionExp(chooseSet, new EnumeratedSetExp(exps))));
    Expression exp = new ExistsExp(contracts(command.qvar), new TrueExp());
    exp.setPosition(command.qvar.getPosition());
    WhileCommand wcommand = new WhileCommand(exp, Arrays.asList(command.spec), 
        annotate(command, new CommandSequence(body)));
    wcommand.addAll(command.getVars(), command.getOldVars());
    
    // building the command sequence
    List<Command> block = new ArrayList<Command>();
    block.add(
        new VarCommand(new Identifier(TypeChecker.chooseSetName),
            setType,
            new EmptySetExp(elementType)));
    block.add(annotate(command, wcommand));
    return annotate(command, new CommandSequence(block));
  }
  
  /****************************************************************************
   * CommandSequence
   ***************************************************************************/
  private List<Condition> def(CommandSequence command)
  {
    return def(command.commands);
  }
  private List<Condition> pre(CommandSequence command, Condition post)
  {
    return pre(command.commands, post);
  }
  
  /****************************************************************************
   * DoWhileCommand
   ***************************************************************************/
  private List<Condition> def(DoWhileCommand command)
  {
    Command command0 = replace(command);
    return def(command0);
  }
  private List<Condition> pre(DoWhileCommand command, Condition post)
  {
    Command command0 = replace(command);
    return pre(command0, post);
  }
  private Command replace(DoWhileCommand command)
  {
    List<Command> commands = new ArrayList<Command>();
    commands.add(command.command);
    WhileCommand wcommand = 
        new WhileCommand(contracts(command.exp), Arrays.asList(command.spec), command.command);
    wcommand.addAll(command.getVars(), command.getOldVars());
    commands.add(annotate(command, wcommand));
    return annotate(command, new CommandSequence(commands));
  }
  
  /****************************************************************************
   * EmptyCommand
   ***************************************************************************/
  private List<Condition> def(EmptyCommand command)
  {
    return new ArrayList<Condition>();
  }
  private List<Condition> pre(EmptyCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(post);
    return result;
  }
  
  /****************************************************************************
   * ExpCommand
   ***************************************************************************/
  private List<Condition> def(ExpCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.exp));
    return result;
  }
  private List<Condition> pre(ExpCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(addPosition(post, command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * ForCommand
   ***************************************************************************/
  private List<Condition> def(ForCommand command)
  {
    Command command0 = replace(command);
    return def(command0);
  }
  private List<Condition> pre(ForCommand command, Condition post)
  {
    Command command0 = replace(command);
    return pre(command0, post);
  }
  private Command replace(ForCommand command)
  {
    List<Command> commands0 = new ArrayList<Command>();
    commands0.add(command.command3);
    commands0.add(command.command2);
    List<Command> commands1 = new ArrayList<Command>();
    commands1.add(command.command1);
    WhileCommand wcommand = new WhileCommand(contracts(command.exp), Arrays.asList(command.spec), 
        annotate(command, new CommandSequence(commands0)));
    commands1.add(annotate(command, wcommand));
    wcommand.addAll(command.getVars(), command.getOldVars());
    /*
    if (command.command1 instanceof VarCommand)
    {
      VarCommand command1 = (VarCommand)command.command1;
      Identifier ident = command1.ident;
      ValueSymbol symbol = (ValueSymbol)ident.getSymbol();
      Identifier oident = TypeChecker.oldIdentifier(ident);
      ValueSymbol osymbol = new ValueSymbol(oident, symbol.type, symbol.kind);
      osymbol.setSyntacticType(symbol.getSyntacticType());
      wcommand.add(symbol, osymbol);
    }
    */
    return annotate(command, new CommandSequence(commands1));
  }
  
  /****************************************************************************
   * ForInCommand
   ***************************************************************************/
  private List<Condition> def(ForInCommand command)
  {
    Command command0 = replace(command);
    return def(command0);
  }
  private List<Condition> pre(ForInCommand command, Condition post)
  {
    Command command0 = replace(command);
    return pre(command0, post);
  }
  private Command replace(ForInCommand command)
  {
    QuantifiedVariable qvar0 = contracts(command.qvar);
    QuantifiedVariableCore[] qvcore = qvar0.qvcore;
    Expression forSet = AST.reference(TypeChecker.forSetName);

    // reference to element and its type
    Expression element;
    Type elementType;
    if (qvcore.length == 1)
    {
      element = AST.reference(qvcore[0].ident.string);
      ValueSymbol symbol = (ValueSymbol)qvcore[0].ident.getSymbol();
      elementType = symbol.type;
    }
    else
    {
      List<Expression> ids = new ArrayList<Expression>();
      List<Type> types = new ArrayList<Type>();
      for (QuantifiedVariableCore qv : qvcore)
      {
        ids.add(AST.reference(qv.ident.string));
        ValueSymbol symbol = (ValueSymbol)qvcore[0].ident.getSymbol();
        types.add(symbol.type);
      }
      element = new TupleExp(ids);
      elementType = new Type.TupleType(types);
      elementType.setCanonical();
    }
    Type setType = new Type.SetType(elementType);
    setType.setCanonical();
    
    // body: { C; forSet = forSet U { elem }; _choiceSetSize = _choiceSetSize+1; }
    List<Command> commands0 = new ArrayList<Command>();
    commands0.add(command.command);
    List<Expression> exps = new ArrayList<Expression>();
    exps.add(element);
    commands0.add(
        new AssignmentCommand(new Identifier(TypeChecker.forSetName),
        new ArrayList<Selector>(), 
        new UnionExp(forSet, new EnumeratedSetExp(exps))));
    String choiceSetSizeName = "_choiceSetSize";
    commands0.add(
        new AssignmentCommand(new Identifier(choiceSetSizeName), 
            new ArrayList<Selector>(), 
            new MinusExp(AST.reference(choiceSetSizeName), AST.number(1))));
    
    // qv:   x in forSet / _x in forSet 
    // pred: p(x)        / letpar x0 = _x.0, x1 = _x.1 in p(x0, x1)
    List<QuantifiedVariableCore> qv = new ArrayList<QuantifiedVariableCore>();
    Expression pred = null;
    if (qvcore.length == 1)
    {
      qv.add(new QuantifiedVariableCore.IdentifierSetQuantifiedVar(qvcore[0].ident,
          AST.reference(TypeChecker.forSetName)));
      if (qvar0.exp == null)
        pred = null;
      else
        pred = qvar0.exp;
    }
    else
    {
      String forSetElemName = "_x";
      qv.add(new QuantifiedVariableCore.IdentifierSetQuantifiedVar(new Identifier(forSetElemName),
          AST.reference(TypeChecker.forSetName)));
      List<Binder> binders = new ArrayList<Binder>();
      int i = 0;
      for (QuantifiedVariableCore qv0 : qvcore)
      {
        binders.add(new Binder(qv0.ident, 
            new TupleSelectionExp(AST.reference(forSetElemName), AST.decimal(i))));
        i++;
      }
      if (qvar0.exp == null)
        pred = null;
      else
        pred = new LetParExp(binders, qvar0.exp);
    }
    
    // invariant and termination term
    List<LoopSpec> spec = new ArrayList<LoopSpec>(Arrays.asList(command.spec));
    List<Expression> terms = new ArrayList<Expression>();
    terms.add(AST.reference(choiceSetSizeName));
    spec.add(0, new LoopSpec.DecreasesLoopSpec(terms));
    if (pred != null)
      spec.add(0, new LoopSpec.InvariantLoopSpec(
          new ForallExp(new QuantifiedVariable(qv, null), pred)));
    
    // block { 
    //   var forSet: Set(Elem) = [Elem]{};
    //   var _choiceSetSizeName: Nat[2^ElemSize] = { elem | .... }
    //   choose ... do specs { body }
    List<Command> commands1 = new ArrayList<Command>();
    commands1.add(
            new VarCommand(new Identifier(TypeChecker.forSetName),
                setType,
                new EmptySetExp(elementType)));
    commands1.add(    
            new VarCommand(new Identifier(choiceSetSizeName), 
                new Type.NatType(new PowerExp(
                    new Expression.NumberLiteralExp(AST.decimal(2)), 
                    new Expression.NumberLiteralExp(AST.decimal(elementType.getSize())))),
                new SetSizeExp(new SetBuilderExp(element, qvar0))));
    QuantifiedVariable qvar1 = 
        new QuantifiedVariable(Arrays.asList(qvcore), 
            AST.and(qvar0.exp, 
                new NotExp(new Expression.InSetExp(element, forSet))));
    qvar1.setPosition(command.qvar.getPosition());
    ChooseDoCommand cdcommand =  
        new ChooseDoCommand(qvar1, 
            spec, annotate(command, new CommandSequence(commands0)));
    cdcommand.addAll(command.getVars(), command.getOldVars());
    commands1.add(annotate(command, cdcommand));
    return annotate(command, new CommandSequence(commands1));
  }
  
  /****************************************************************************
   * IfThenCommand
   ***************************************************************************/
  private List<Condition> def(IfThenCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.exp));
    List<Condition> result1 = def(command.command);
    clone(command, result, result1);
    return result;
  }
  private List<Condition> pre(IfThenCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    List<Condition> result1 = pre(command.command, post);
    clone(command, result, result1);
    result.add(addPosition(
        post.clone(new ImpliesExp(new NotExp(contracts(command.exp)), post.exp)), 
        command.exp.getPosition()));
    return result;
  }
  private void clone(IfThenCommand command, List<Condition> result,
    List<Condition> result1)
  {
    SourcePosition pos = command.exp.getPosition();
    clone(result, result1,
        (Expression e)-> new ImpliesExp(command.exp, e), pos);
  }
  
  /****************************************************************************
   * IfThenElseCommand
   ***************************************************************************/
  private List<Condition> def(IfThenElseCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.exp));
    List<Condition> result1 = def(command.command1);
    List<Condition> result2 = def(command.command2);
    clone(command, result, result1, result2);
    return result;
  }
  private List<Condition> pre(IfThenElseCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    List<Condition> result1 = pre(command.command1, post);
    List<Condition> result2 = pre(command.command2, post);
    clone(command, result, result1, result2);
    return result;
  }
  private void clone(IfThenElseCommand command, List<Condition> result,
    List<Condition> result1, List<Condition> result2)
  {
    SourcePosition pos = command.exp.getPosition();
    Expression exp0 = contracts(command.exp);
    clone(result, result1,
        (Expression e)-> new ImpliesExp(exp0, e), pos);
    clone(result, result2,
        (Expression e)-> new ImpliesExp(new NotExp(exp0), e), pos);
  }
  
  /****************************************************************************
   * MatchCommand
   ***************************************************************************/
  private List<Condition> def(MatchCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    Expression exp0 = contracts(command.exp);
    for (PatternCommand p : command.pcommand)
    {
      List<Condition> result0 = def(p.command);
      clone(result, result0, command, p, exp0);
    }
    return result;
  }
  private List<Condition> pre(MatchCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    Expression exp0 = contracts(command.exp);
    for (PatternCommand p : command.pcommand)
    {
      List<Condition> result0 = pre(p.command, post);
      clone(result, result0, command, p, exp0);
    }
    return result;
  }
  private void clone(List<Condition> result, List<Condition> cond, 
    MatchCommand c, PatternCommand p, Expression exp0)
  {
    SourcePosition pos = c.exp.getPosition();
    clone(result, cond,
        (Expression e)-> 
        {
          List<PatternExpression> ps = new ArrayList<PatternExpression>();
          for (PatternCommand p0 : c.pcommand)
          {
            Expression e0 = p0 == p ? e : new TrueExp();
            if (p0 instanceof PatternCommand.DefaultPatternCommand)
            {
              ps.add(new PatternExpression.DefaultPatternExp(e0));
            }
            else if (p0 instanceof PatternCommand.IdentifierPatternCommand)
            {
              PatternCommand.IdentifierPatternCommand p1 =
                  (PatternCommand.IdentifierPatternCommand)p0;
              ps.add(new PatternExpression.IdentifierPatternExp(p1.ident, e0));
            }
            else if (p0 instanceof PatternCommand.ApplicationPatternCommand)
            {
              PatternCommand.ApplicationPatternCommand p1 =
                  (PatternCommand.ApplicationPatternCommand)p0;
              ps.add(new PatternExpression.ApplicationPatternExp(p1.ident, 
                  Arrays.asList(p1.params), e0));
            }    
          }
          return new MatchExp(exp0, ps);
        }, pos);
  }
  
  /****************************************************************************
   * PrintCommand
   ***************************************************************************/
  private List<Condition> def(PrintCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    for (Expression e : command.exps)
      result.addAll(preconditions(e));
    return result;
  }
  private List<Condition> pre(PrintCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(addPosition(post, command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * CheckCommand
   ***************************************************************************/
  private List<Condition> def(CheckCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.exp));
    return result;
  }
  private List<Condition> pre(CheckCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.add(addPosition(post, command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * ValCommand
   ***************************************************************************/
  private List<Condition> def(ValCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    result.addAll(preconditions(command.exp));
    return result;
  }
  private List<Condition> pre(ValCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    List<Binder> binders = new ArrayList<Binder>();
    binders.add(new Binder(command.ident, contracts(command.exp)));
    Expression exp = new LetExp(binders, post.exp);
    result.add(addPosition(post.clone(exp), command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * VarCommand
   ***************************************************************************/
  private List<Condition> def(VarCommand command)
  {
    List<Condition> result = new ArrayList<Condition>();
    if (command.exp != null) result.addAll(preconditions(command.exp));
    return result;
  }
  private List<Condition> pre(VarCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    Expression exp;
    if (command.exp == null)
    {
      List<QuantifiedVariableCore> qvcore = 
          new ArrayList<QuantifiedVariableCore>();
      qvcore.add(new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(
          command.ident, command.type));
      QuantifiedVariable qvar = new QuantifiedVariable(qvcore, null);
      exp = new ForallExp(qvar, post.exp);
    }
    else
    {
      List<Binder> binders = new ArrayList<Binder>();
      binders.add(new Binder(command.ident, contracts(command.exp)));
      exp = new LetExp(binders, post.exp);
    }
    result.add(addPosition(post.clone(exp), command.getPosition()));
    return result;
  }
  
  /****************************************************************************
   * WhileCommand
   ***************************************************************************/
  private List<Condition> def(WhileCommand command)
  {
    // the list of well-definedness conditions to be computed
    List<Condition> result = new ArrayList<Condition>();

    // source code position to be displayed (only loop head)
    SourcePosition pos = command.getPosition();
    if (pos.lineFrom != pos.lineTo)
    {
      int to = pos.source.lines.get(pos.lineFrom).length();
      pos = new SourcePosition(pos.source, pos.lineFrom, pos.charFrom, 
          pos.lineFrom, to);
    }
    
    // list of variables and old variables
    List<ValueSymbol> vars = command.getVars();
    List<ValueSymbol> ovars = command.getOldVars();
    
    // the list of variables to be quantified
    List<QuantifiedVariableCore> qvcore = quantifiedVariables(command);
    
    // invariants and single invariant formula (may be "true") 
    List<Expression> invariants = getInvariants(command.spec);
    Expression invariant = getInvariant(command.spec);
    
    // preconditions in invariant hold initially
    List<Condition> invpre = preconditions(invariant);
    clone(result, invpre, (Expression e) -> bind(ovars, vars, e), null);
    
    // preconditions in measures hold
    List<Expression[]> measures = getMeasures(command.spec);
    for (Expression[] measures0 : measures)
    {
      for (Expression m : measures0)
      {
        clone(result, preconditions(m),
            (Expression e)-> bind(ovars, vars,
                close(qvcore, 
                    new ImpliesExp(invariant, e))), null);
      }
      
    }
    
    // preconditions in loop expression hold
    clone(result, preconditions(command.exp),
        (Expression e)-> bind(ovars, vars,
            close(qvcore, 
                new ImpliesExp(invariant, e))), null);

    // preconditions in invariant are preserved
    Expression cexp = contracts(command.exp);
    for (Condition i : invpre)
    {
      clone(result, pre(command.command, i), 
          (Expression e) -> bind(ovars, vars,
              close(qvcore, 
                  new ImpliesExp(new AndExp(invariant, cexp), e))),
          command.exp.getPosition());
    }
    
    // invariants holds initially
    for (Expression inv : invariants)
    {
      Expression exp1 = bind(ovars, vars, inv);
      result.add(new Condition(Condition.Kind.InvariantInitial, inv, exp1));
    }
     
    // termination term is non-negative
    for (Expression[] measure : measures)
    {
      Expression exp = null;
      for (Expression m : measure) 
        exp = AST.and(exp, new GreaterEqualExp(m, AST.number(0)));
      if (exp == null) continue;
      exp = close(qvcore, new ImpliesExp(invariant, exp));
      result.add(new Condition(Condition.Kind.LoopMeasureNonNegative, measure[0], 
          bind(ovars, vars, exp)));
    }
    
    // invariants are preserved
    for (Expression inv : invariants)
    {
      Condition inv0 =
          new Condition(Condition.Kind.InvariantPreserved, inv, inv);
      clone(result, pre(command.command, inv0),
          (Expression e) -> bind(ovars, vars,
              close(qvcore, 
                  new ImpliesExp(new AndExp(invariant, cexp), e))),
          command.exp.getPosition());
    }

    // termination term is decreased
    String measureid = "_measure";
    for (Expression[] measure : measures)
    {
      Expression eq = null;
      Expression exp = null;
      List<Binder> binders = new ArrayList<Binder>();
      int n = measure.length;
      for (int i=0; i<n; i++)
      {
        Expression m = measure[i];
        if (eq == null)
        {
          exp = new LessExp(m, AST.reference(measureid));
          eq = new EqualsExp(m, AST.reference(measureid));
          binders.add(new Binder(new Identifier(measureid), m));
          continue;
        }
        String measureid0 = measureid + i;
        exp = new OrExp(exp, new AndExp(eq, new LessExp(m, AST.reference(measureid0))));     
        eq = new AndExp(eq, new EqualsExp(m, AST.reference(measureid0)));
        binders.add(new Binder(new Identifier(measureid0), m));
      }
      Condition nneg =
          new Condition(Condition.Kind.LoopMeasureDecreased, measure[0], exp);
      clone(result, pre(command.command, nneg),
          (Expression e) -> bind(ovars, vars,
            close(qvcore, 
                new ImpliesExp(new AndExp(invariant, cexp), 
                    new LetParExp(binders, e)))),
          command.exp.getPosition());
    }

    // preconditions in body hold
    clone(result, def(command.command),
        (Expression e)-> bind(ovars, vars,
            close(qvcore, 
                new ImpliesExp(new AndExp(invariant, cexp), e))),
        command.exp.getPosition());

    // the result
    return result;
  }
  private List<Condition> pre(WhileCommand command, Condition post)
  {
    List<Condition> result = new ArrayList<Condition>();
    Expression cexp = contracts(command.exp);
    Expression invariant = getInvariant(command.spec);
    Expression exp = new ImpliesExp(
        new AndExp(invariant, new NotExp(cexp)), post.exp);
    List<QuantifiedVariableCore> qvcore = quantifiedVariables(command);
    if (qvcore != null)
      exp = new ForallExp(new QuantifiedVariable(qvcore, null), exp);
    exp = bind(command.getOldVars(), command.getVars(), exp);
    result.add(addPosition(post.clone(exp), cexp.getPosition()));
    return result;
  }
  
  //--------------------------------------------------------------------------
  //
  // Auxiliaries
  //
  // -------------------------------------------------------------------------
  
  /****************************************************************************
   * Generate preconditions for the evaluation of expression
   * @param exp the expression
   * @return the preconditions
   ***************************************************************************/
  private List<Condition> preconditions(Expression exp)
  {
    return Preconditions.generate(exp, info.symbol, callers);
  }
  
  /****************************************************************************
   * Generate preconditions for the evaluation of quantified variable
   * @param qvar the quantified variable
   * @return the preconditions
   ***************************************************************************/
  private List<Condition> preconditions(QuantifiedVariable qvar)
  {
    return Preconditions.generate(qvar, info.symbol, callers);
  }
  
  /***************************************************************************
   * Process expression replacing function calls by contracts.
   * @param exp the expression.
   * @return the processed expression.
   **************************************************************************/
  private Expression contracts(Expression exp)
  {
    return UseContracts.execute(exp, info.symbol, callers);
  }
  
  /***************************************************************************
   * Process quantified variable replacing function calls by contracts.
   * @param qvar the quantified variable.
   * @return the processed quantified variable.
   **************************************************************************/
  private QuantifiedVariable contracts(QuantifiedVariable qvar)
  {
    Expression exp0 = qvar.exp == null ? null : contracts(qvar.exp);
    List<QuantifiedVariableCore> qvcs = new ArrayList<QuantifiedVariableCore>();
    for (QuantifiedVariableCore qvc : qvar.qvcore)
    {
      if (qvc instanceof QuantifiedVariableCore.IdentifierSetQuantifiedVar)
      {
        QuantifiedVariableCore.IdentifierSetQuantifiedVar qvc0 =
            (QuantifiedVariableCore.IdentifierSetQuantifiedVar)qvc;
        QuantifiedVariableCore.IdentifierSetQuantifiedVar qvc1 =
            new QuantifiedVariableCore.IdentifierSetQuantifiedVar(
                qvc0.ident, contracts(qvc0.exp));
        qvc1.setPosition(qvc0.getPosition());
        qvcs.add(qvc1);
      }
      else
        qvcs.add(qvc);
    }
   QuantifiedVariable qvar0 = new QuantifiedVariable(qvcs, exp0);
   qvar0.setPosition(qvar.getPosition());
   return qvar0;
  }
  
  /***************************************************************************
   * Clone conditions.
   * @param result the list to which to add the clones.
   * @param cond a list of conditions to be cloned.
   * @param op the operation transforming the condition expressions.
   * @param pos the position to be added to the clone (may be null)
   **************************************************************************/
  private static void clone(List<Condition> result, List<Condition> cond, 
    UnaryOperator<Expression> op, SourcePosition pos)
  {
    for (Condition c : cond)
    {
      result.add(addPosition(c.clone(op.apply(c.exp)), pos));
    }
  } 
  
  /****************************************************************************
   * Get invariant formulas from loop specification
   * @param spec the loop specification
   * @return the invariant formulas
   ***************************************************************************/
  private static List<Expression> getInvariants(LoopSpec[] spec)
  {
    List<Expression> result = new ArrayList<Expression>();
    for (LoopSpec s : spec)
    {
      if (!(s instanceof LoopSpec.InvariantLoopSpec)) continue;
      LoopSpec.InvariantLoopSpec s0 = (LoopSpec.InvariantLoopSpec)s;
      result.add(s0.exp);
    }
    return result;
  }
  
  /****************************************************************************
   * Get invariant formula from loop specification
   * @param spec the loop specification
   * @return the invariant formula (may be "true")
   ***************************************************************************/
  private static Expression getInvariant(LoopSpec[] spec)
  {
    Expression result = null;
    for (LoopSpec s : spec)
    {
      if (!(s instanceof LoopSpec.InvariantLoopSpec)) continue;
      LoopSpec.InvariantLoopSpec s0 = (LoopSpec.InvariantLoopSpec)s;
      result = AST.and(result, s0.exp);
    }
    if (result == null) result = new TrueExp();
    return result;
  }
  
  /****************************************************************************
   * Get termination measures from loop specification
   * @param spec the loop specification
   * @return the termination measures
   ***************************************************************************/
  private static List<Expression[]> getMeasures(LoopSpec[] spec)
  {
    List<Expression[]> result = new ArrayList<Expression[]>();
    for (LoopSpec s : spec)
    {
      if (!(s instanceof LoopSpec.DecreasesLoopSpec)) continue;
      LoopSpec.DecreasesLoopSpec s0 = (LoopSpec.DecreasesLoopSpec)s;
      result.add(s0.exps);
    }
    if (result.isEmpty())
    {
      // add invalid measure -1 such that termination proof fails
      result.add(new Expression[] { new NegationExp(new NumberLiteralExp(AST.decimal(1)))});
    }
    return result;
  }

  /****************************************************************************
   * Get quantified variables for loop.
   * @param command the loop command.
   * @return the variables
   ***************************************************************************/
  private static List<QuantifiedVariableCore>
  quantifiedVariables(LoopCommand command)
  {
    List<ValueSymbol> vars = command.getVars();
    List<QuantifiedVariableCore> qvcore = new ArrayList<QuantifiedVariableCore>();
    for (ValueSymbol var : vars)
      qvcore.add(new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(
          var.ident, var.getSyntacticType()));
    return qvcore;
  }
  
  /***************************************************************************
   * Close expression by quantified variables
   * @param qvcore the list of quantified variables (may be null)
   * @param exp the expression
   * @return the closed expression
   **************************************************************************/
  private static Expression close(List<QuantifiedVariableCore> qvcore, Expression exp)
  {
    if (qvcore.isEmpty()) return exp;
    return new ForallExp(new QuantifiedVariable(qvcore, null), exp);
  }
  
  /***************************************************************************
   * Close expression by binding quantified variables to values
   * @param vars the list of variables to be bound
   * @param values a list of variables denoting the values
   * @param exp the expression
   * @return the closed expression
   **************************************************************************/
  private static Expression bind(List<ValueSymbol> vars, 
    List<ValueSymbol> values, Expression exp)
  {
    if (vars.isEmpty()) return exp;
    List<Binder> binders = new ArrayList<Binder>();
    int n = vars.size();
    for (int i=0; i<n; i++)
      binders.add(new Binder(vars.get(i).ident, 
          new IdentifierExp(values.get(i).ident)));
    return new LetParExp(binders, exp);
  }
  
  /***************************************************************************
   * Annotate new command with information from original command.
   * @param command the original command
   * @param command0 the new command
   * @return the new command command0
   **************************************************************************/
  private static Command annotate(Command command, Command command0)
  {
    command0.setPosition(command.getPosition());
    return command0;
  }
  
  /***************************************************************************
   * Add position to condition.
   * @param cond the condition.
   * @param pos the position (may be null).
   * @return the condition annotated with the position.
   ***************************************************************************/
  private static Condition addPosition(Condition cond, SourcePosition pos)
  {
    if (pos != null) cond.addPosition(pos);
    return cond;
  }
  
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------