// ---------------------------------------------------------------------------
// Type Checker.java
// The type checker.
// $Id: TypeChecker.java,v 1.98 2018/06/14 13:22:44 schreine Exp schreine $
//
// Author: Wolfgang Schreiner <Wolfgang.Schreiner@risc.jku.at>
// Copyright (C) 2016-, Research Institute for Symbolic Computation (RISC)
// Johannes Kepler University, Linz, Austria, http://www.risc.jku.at
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General private License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// aint with this program.  If not, see <http://www.gnu.org/licenses/>.
// ----------------------------------------------------------------------------
package riscal.types;

import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.syntax.AST.Selector.*;
import riscal.syntax.AST.Command.*;
import riscal.syntax.AST.Declaration.*;
import riscal.syntax.AST.Expression.*;
import riscal.syntax.AST.LoopSpec.*;
import riscal.syntax.AST.RecursiveIdentifier.*;
import riscal.syntax.AST.FunctionSpec.*;
import riscal.syntax.AST.Type.*;
import riscal.syntax.AST.QuantifiedVariableCore.*;
import riscal.syntax.AST.PatternCommand.*;
import riscal.syntax.AST.PatternExpression.*;

import riscal.types.Environment.Symbol.*;
import riscal.Main;
import riscal.semantics.Values;
import riscal.semantics.Types.*;
import riscal.util.*;

import java.util.*;

public class TypeChecker 
{
  // the environment used by the type checker
  private final Environment env;
  
  // the writer to be used for printing error messages
  private final Main.ErrorWriter error;
  
  // the generator of validation tasks
  // private final Validation validation;
  
  // the number of slots currently occupied in the current local frame
  private int slotNumber;
  
  // the maximum number of slots occupied in the current local frame
  private int[] slotMaximum; // shared across duplicated frames
  
  // name of result variable, visited elements of for loop, and
  // parameter of subtype predicate
  public final static String resultName = "result";
  public final static String forSetName = "forSet";
  public final static String chooseSetName = "chooseSet";
  public final static String paramName  = "value";
  
  // prefix for "old" version of names
  private final static String oldPrefix = "old_";
  
  /***************************************************************************
   * A type checker for the given environment
   * @param env the environment to be used for checking the phrase.
   * @param writer the writer for printing error messages
   **************************************************************************/
  public TypeChecker(Environment env, Main.ErrorWriter writer)
  {
    this.env = env;
    this.error = writer;
    // this.validation = new Validation(this, translator);
    this.slotNumber = 0;
    this.slotMaximum = new int[] { 0 };
  }
  
  /***************************************************************************
   * Create new type checker with copy of environment of given checker
   * (but using the same error writer).
   * @param checker the checker whose environment is to be copied
   **************************************************************************/
  private TypeChecker(TypeChecker checker)
  {
    // duplicate environment but just keep the error writer
    env = new Environment(checker.env);
    error = checker.error; 
    // validation = checker.validation;
    this.slotNumber = checker.slotNumber;
    this.slotMaximum = checker.slotMaximum;
  }
  
  /****************************************************************************
   * indicate whether checking yielded errors.
   * @return true if checking yielded errors
   ***************************************************************************/
  public boolean hasErrors() { return error.getErrors() > 0; }
  
  /****************************************************************************
   * get slot number for new local value/variable
   * @return the slot number
   ***************************************************************************/
  private int getSlot()
  {
    int slot = slotNumber;
    slotNumber++;
    if (slotNumber > slotMaximum[0]) slotMaximum[0] = slotNumber;
    return slot;
  }
  
  /****************************************************************************
   * get maximum number of slots used in current frame (and nested frames)
   * @return the maximum
   ***************************************************************************/
  private int getSlotMaximum()
  {
    return slotMaximum[0];
  }
  
  // the last declaration that was processed
  private static AST lastDecl = null;
  
  /***************************************************************************
   * Throw a type error.
   * @param tree the phrase causing the error
   * @param msg the message reported by the error
   **************************************************************************/
  public static void typeError(AST tree, String msg)
  {
    if (tree == null || tree.getPosition() == null) tree = lastDecl;
    throw new TypeError(tree, msg);
  }
  
  /***************************************************************************
   * The exception raised by the type checker on an error.
   **************************************************************************/
  public static final class TypeError extends RuntimeException
  {
    private static final long serialVersionUID = 28091967L;
    public final AST tree;
    
    /*************************************************************************
     * Create a type error with the denoted message originating from 
     * type-checking the denoted phrase.
     * @param tree the abstract syntax tree of the phrase
     * @param msg the message
     ************************************************************************/
    public TypeError(AST tree, String msg) 
    { 
      super(msg); 
      this.tree = tree;
    }
  }
  
  // -------------------------------------------------------------------------
  //
  // External Declaration
  //
  // -------------------------------------------------------------------------
  public void process(ValueDeclaration decl, int value)
  {
    Type type = intType(value, value);
    ValueSymbol symbol = env.putValue(decl.ident, type, ValueSymbol.ValueSymbol.Kind.global);
    symbol.setValue(new Value.Int(value));
  }
  
  // -------------------------------------------------------------------------
  //
  // Declaration
  //
  // -------------------------------------------------------------------------
  public void process(Declaration decl)
  {
    lastDecl = decl;
    try
    {
      if (decl instanceof ValueDeclaration) 
        process((ValueDeclaration)decl);
      else if (decl instanceof FunctionDeclaration) 
        process((FunctionDeclaration)decl);
      else if (decl instanceof FunctionDefinition) 
        process((FunctionDefinition)decl);
      else if (decl instanceof PredicateDeclaration) 
        process((PredicateDeclaration)decl);
      else if (decl instanceof PredicateDefinition) 
        process((PredicateDefinition)decl);
      else if (decl instanceof TheoremParamDefinition) 
        process((TheoremParamDefinition)decl);
      else if (decl instanceof ProcedureDeclaration) 
        process((ProcedureDeclaration)decl);
      else if (decl instanceof ProcedureDefinition) 
        process((ProcedureDefinition)decl);
      else if (decl instanceof RecTypeDefinition) 
        process((RecTypeDefinition)decl);
      else if (decl instanceof EnumTypeDefinition) 
        process((EnumTypeDefinition)decl);
      else if (decl instanceof TheoremDefinition) 
        process((TheoremDefinition)decl);
      else if (decl instanceof PredicateValueDefinition) 
        process((PredicateValueDefinition)decl);
      else if (decl instanceof TypeDefinition) 
        process((TypeDefinition)decl);
      else if (decl instanceof ValueDefinition) 
        process((ValueDefinition)decl);
      else
        typeError(decl, "unknown kind of declaration");
    }
    catch(TypeError e)
    {
      error.reportError(e);
    }
  }
  private void process(FunctionDeclaration decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = process(decl.type);
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(ident, ptypes);
    if (fun != null)
      typeError(ident,
          "function already declared at " + fun.ident.getPosition());
    env.putFunction(decl.multiple.value, decl.ident, psymbols, type);
  }
  private void process(FunctionDefinition decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = process(decl.type);
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(decl.ident, ptypes);
    if (fun == null)
    {
      fun = env.putFunction(decl.multiple.value, ident, psymbols, type);
      checker.env.putFunctionSymbol(fun);
    }
    else if (fun.isDefined())
      typeError(ident,
          "function already declared at " + fun.ident.getPosition());
    fun.makeDefined(decl);
    int rslot = checker.getSlot();
    TypeChecker checker0 = new TypeChecker(checker);
    ValueSymbol symbol = checker0.env.putValue(new Identifier(resultName), 
        fun.type, ValueSymbol.Kind.value);
    symbol.setSlot(rslot);
    fun.setResult(symbol);
    for (FunctionSpec s : decl.spec) process(s, fun, checker, checker0);
    Type etype = checker.process(decl.exp);
    if (!matchesWeak(etype, type))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + type);
    fun.setSlots(getSlotMaximum());
  }
  private void process(PredicateDeclaration decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = processType(new BoolType());
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(ident, ptypes);
    if (fun != null)
      typeError(ident,
          "predicate already declared at " + fun.ident.getPosition());
    env.putFunction(decl.multiple.value, decl.ident, psymbols, type);
  }
  private void process(PredicateDefinition decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = processType(new BoolType());
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(ident, ptypes);
    if (fun == null)
    {
      fun = env.putFunction(decl.multiple.value, decl.ident, psymbols, type);
      checker.env.putFunctionSymbol(fun);
    }
    else if (fun.isDefined())
      typeError(ident,
          "function already defined at " + fun.ident.getPosition());
    fun.makeDefined(decl);
    int rslot = checker.getSlot();
    TypeChecker checker0 = new TypeChecker(checker);
    ValueSymbol symbol = checker0.env.putValue(new Identifier(resultName), 
        fun.type, ValueSymbol.Kind.value);
    symbol.setSlot(rslot);
    fun.setResult(symbol);
    for (FunctionSpec s : decl.spec) process(s, fun, checker, checker0);
    Type etype = checker.process(decl.exp);
    if (!matchesWeak(etype, type))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + type);
    fun.setSlots(getSlotMaximum());
  }
  private void process(TheoremParamDefinition decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = processType(new BoolType());
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(ident, ptypes);
    if (fun == null)
    {
      fun = env.putFunction(decl.multiple.value, decl.ident, psymbols, type);
      checker.env.putFunctionSymbol(fun);
    }
    else if (fun.isDefined())
      typeError(ident,
          "function already defined at " + fun.ident.getPosition());
    fun.makeDefined(decl);
    int rslot = checker.getSlot();
    TypeChecker checker0 = new TypeChecker(checker);
    ValueSymbol symbol = checker0.env.putValue(new Identifier(resultName), 
        fun.type, ValueSymbol.Kind.value);
    symbol.setSlot(rslot);
    fun.setResult(symbol);
    for (FunctionSpec s : decl.spec) process(s, fun, checker, checker0);
    Type etype = checker.process(decl.exp);
    if (!matchesWeak(etype, type))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + type);
    fun.setSlots(getSlotMaximum());
  }
  private void process(ProcedureDeclaration decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = process(decl.type);
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(ident, ptypes);
    if (fun != null)
      typeError(ident,
          "procedure already declared at " + fun.ident.getPosition());
    fun = env.putFunction(decl.multiple.value, decl.ident, psymbols, type);
  }
  private void process(ProcedureDefinition decl)
  {
    Identifier ident = process(decl.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] psymbols = checker.process(decl.param);
    Type type = process(decl.type);
    int pnumber = psymbols.length;
    Type[] ptypes = new Type[pnumber];
    for (int i=0; i<pnumber; i++) ptypes[i] = psymbols[i].type;
    FunctionSymbol fun = getFunction(decl.ident, ptypes);
    if (fun == null)
    {
      fun = env.putFunction(decl.multiple.value, ident, psymbols, type);
      checker.env.putFunctionSymbol(fun);
    }
    else if (fun.isDefined())
      typeError(ident,
          "procedure already defined at " + fun.ident.getPosition().toShortString());
    fun.makeDefined(decl);
    int rslot = checker.getSlot();
    TypeChecker checker0 = new TypeChecker(checker);
    ValueSymbol symbol = checker0.env.putValue(new Identifier(resultName), 
        fun.type, ValueSymbol.Kind.value);
    symbol.setSlot(rslot);
    fun.setResult(symbol);
    for (FunctionSpec s : decl.spec) process(s, fun, checker, checker0);
    for (Command c : decl.commands) checker.process(c);
    Type etype = decl.exp == null ? new UnitType() : checker.process(decl.exp);
    if (!matchesWeak(etype, type))
    {
      if (decl.exp == null)
        typeError(decl,
            "procedure must return a value of type " + type);
      else
        typeError(decl.exp,
            "expression has type " + etype + " but must have type " + type);
    }
    fun.setSlots(getSlotMaximum());
  }
  private void process(RecTypeDefinition decl)
  {
    long ivalue = evalInt(decl.exp);
    if (ivalue < 0)
      typeError(decl.exp, "recursion depth " + ivalue + " must not be negative");
    process(decl.ritems, (int)ivalue);
  }
  private void process(EnumTypeDefinition decl)
  {
    RecursiveTypeItem[] ritems = new RecursiveTypeItem[]{ decl.ritem };
    process(ritems, 0);
  }
  private void process(TheoremDefinition decl)
  {
    Identifier ident = process(decl.ident);
    Type etype = process(decl.exp);
    Type type = processType(new BoolType());
    if (!matchesWeak(etype, type))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + type);
    ValueSymbol value = env.getValue(ident);
    if (value != null)
    {
      typeError(ident,
          "theorem already defined at " + value.ident.getPosition().toShortString());
    }
    value = env.putValue(decl.ident, type, ValueSymbol.Kind.global);
    value.setSlots(getSlotMaximum());
  }
  private void process(PredicateValueDefinition decl)
  {
    Identifier ident = process(decl.ident);
    Type etype = process(decl.exp);
    Type type = processType(new BoolType());
    if (!matchesWeak(etype, type))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + type);
    ValueSymbol value = env.getValue(ident);
    if (value != null)
    {
      typeError(ident,
          "predicate already defined at " + value.ident.getPosition().toShortString());
    }
    value = env.putValue(decl.ident, type, ValueSymbol.Kind.global);
    value.setSlots(getSlotMaximum());
  }
  private void process(TypeDefinition decl)
  {
    Identifier ident = process(decl.ident);
    Type type = process(decl.type);
    TypeSymbol tsymbol = env.getType(ident);
    if (tsymbol != null)
    {
      typeError(ident,
          "type already defined at " + tsymbol.ident.getPosition().toShortString());
    }
    if (decl.exp == null)
    {
      env.putType(ident, type);
      return;
    }
    Identifier pident = process(new Identifier("_pred"));
    TypeChecker checker = new TypeChecker(this);
    Parameter[] params = 
        new Parameter[] { new Parameter(new Identifier(paramName), decl.type) };   
    ValueSymbol[] psymbols = checker.process(params);
    Type btype = processType(new BoolType());
    FunctionSymbol pred = new FunctionSymbol(false, pident, psymbols, btype);
    pred.makeDefined(decl);
    int rslot = checker.getSlot();
    TypeChecker checker0 = new TypeChecker(checker);
    ValueSymbol symbol = checker0.env.putValue(new Identifier(resultName), 
        pred.type, ValueSymbol.Kind.value);
    symbol.setSlot(rslot);
    pred.setResult(symbol);
    Type etype = checker.process(decl.exp);
    if (!matchesWeak(etype, btype))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + btype);
    pred.setSlots(getSlotMaximum());
    Type type0 = new SubType(ident, type, pred);
    env.putType(ident, type0);
  }
  private void process(ValueDefinition decl)
  {
    Identifier ident = process(decl.ident);
    Type etype = process(decl.exp);
    Type type = decl.type == null ? etype : process(decl.type);
    if (!matchesWeak(etype, type))
      typeError(decl.exp,
          "expression has type " + etype + " but must have type " + type);
    ValueSymbol value = env.getValue(ident);
    if (value != null)
    {
      typeError(ident,
          "value already declared at " + 
           value.ident.getPosition().toShortString());
    }
    value = env.putValue(ident, type, ValueSymbol.Kind.global);
    value.setSlots(getSlotMaximum());
  }  
  
  // -------------------------------------------------------------------------
  //
  // Spec
  //
  // -------------------------------------------------------------------------
  private static void process(FunctionSpec spec, FunctionSymbol fun,
    TypeChecker checker, TypeChecker checker0)
  {
    if (spec instanceof DecreasesSpec) 
      checker.process((DecreasesSpec)spec, fun);
    else if (spec instanceof EnsuresSpec) 
      checker0.process((EnsuresSpec)spec, fun);
    else if (spec instanceof RequiresSpec) 
      checker.process((RequiresSpec)spec, fun);
    else if (spec instanceof ContractSpec)
      checker.process((ContractSpec)spec, fun);
    else
      typeError(spec, "unknown kind of function specification");
  }
  private void process(DecreasesSpec spec, FunctionSymbol fun)
  {
    for (Expression e : spec.exps)
      processInt(e);
  }
  private void process(EnsuresSpec spec, FunctionSymbol fun)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.processBool(spec.exp);
  }
  private void process(RequiresSpec spec, FunctionSymbol fun)
  {
    processBool(spec.exp);
  }
  private void process(ContractSpec spec, FunctionSymbol fun)
  {
    // no error possibility
  }
  
  // -------------------------------------------------------------------------
  //
  // Command
  //
  // -------------------------------------------------------------------------
  private void process(Command command)
  {
    if (command instanceof AssertCommand)
      process((AssertCommand)command);
    else if (command instanceof AssignmentCommand)
      process((AssignmentCommand)command);   
    else if (command instanceof ChooseCommand)
      process((ChooseCommand)command); 
    else if (command instanceof ChooseElseCommand)
      process((ChooseElseCommand)command); 
    else if (command instanceof ChooseDoCommand)
      process((ChooseDoCommand)command); 
    else if (command instanceof CommandSequence)
      process((CommandSequence)command); 
    else if (command instanceof DoWhileCommand)
      process((DoWhileCommand)command);
    else if (command instanceof EmptyCommand)
      process((EmptyCommand)command); 
    else if (command instanceof ExpCommand)
      process((ExpCommand)command); 
    else if (command instanceof ForCommand)
      process((ForCommand)command); 
    else if (command instanceof ForInCommand)
      process((ForInCommand)command); 
    else if (command instanceof IfThenCommand)
      process((IfThenCommand)command); 
    else if (command instanceof IfThenElseCommand)
      process((IfThenElseCommand)command); 
    else if (command instanceof MatchCommand)
      process((MatchCommand)command); 
    else if (command instanceof PrintCommand)
      process((PrintCommand)command); 
    else if (command instanceof CheckCommand)
      process((CheckCommand)command); 
    else if (command instanceof ValCommand)
      process((ValCommand)command);
    else if (command instanceof VarCommand)
      process((VarCommand)command);
    else if (command instanceof WhileCommand)
      process((WhileCommand)command); 
    else
      typeError(command, "unknown kind of command");
  }
  private void process(AssertCommand command)
  {
    processBool(command.exp);
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(AssignmentCommand command)
  {
    Identifier ident = command.ident;
    ValueSymbol symbol = env.getValue(ident);
    if (symbol == null)
      typeError(ident, "unknown variable identifier");
    if (symbol.kind != ValueSymbol.Kind.variable)
      typeError(ident, "must be a variable but is a value");
    Type type0 = process(symbol.type, command.sels);
    Type etype = process(command.exp);
    if (!matchesWeak(etype, type0))
      typeError(command.exp, "expression has type " + etype + 
          " but must have type " + type0);
    Set<ValueSymbol> frame = new LinkedHashSet<ValueSymbol>();
    frame.add(symbol);
    command.setFrame(frame);
  }
  private void process(ChooseCommand command)
  {    
    process(command.qvar);
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(ChooseElseCommand command)
  {    
    TypeChecker checker1 = new TypeChecker(this);
    checker1.process(command.qvar);
    checker1.process(command.command1);
    TypeChecker checker2 = new TypeChecker(this);
    checker2.process(command.command2);
    Set<ValueSymbol> frame = new LinkedHashSet<ValueSymbol>();
    frame.addAll(command.command1.getFrame());
    frame.addAll(command.command2.getFrame());
    command.setFrame(frame);
  }
  private void process(ChooseDoCommand command)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(command.qvar);
    int n = symbols.length;
    Type ttype;
    if (n == 1)
    {
      ttype = symbols[0].type;
    }
    else
    {
      Type[] t = new Type[n];
      for (int i=0; i<n; i++) t[i] = symbols[i].type;
      ttype = processType(new TupleType(t));
    }
    Type type = processType(new SetType(ttype));
    Identifier ident = process(new Identifier(chooseSetName));
    ValueSymbol allSet = checker.env.putValue(ident, type, ValueSymbol.Kind.value);
    allSet.setSlot(checker.getSlot());
    command.setAllSet(allSet);
    checker.processLoopCommand(command);
    TypeChecker checker2 = new TypeChecker(checker);
    checker2.process(command.command);
    command.setFrame(new LinkedHashSet<ValueSymbol>(command.command.getFrame()));
  }
  private void process(CommandSequence command)
  {    
    TypeChecker checker = new TypeChecker(this);
    Set<ValueSymbol> frame = new LinkedHashSet<ValueSymbol>();
    for (Command c : command.commands)
    {
      checker.process(c);
      frame.addAll(c.getFrame());
    }
    for (Command c : command.commands)
    {
      if (c instanceof VarCommand)
      {
        VarCommand c0 = (VarCommand)c;
        frame.remove((ValueSymbol)c0.ident.getSymbol());
      }
    }
    command.setFrame(frame);
  }
  private void process(DoWhileCommand command)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(command.command);
    processBool(command.exp);
    processLoopCommand(command);
    command.setFrame(new LinkedHashSet<ValueSymbol>(command.command.getFrame()));
  }
  private void process(EmptyCommand command)
  {
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(ExpCommand command)
  {
    process(command.exp);
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(ForCommand command)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(command.command1);
    checker.processLoopCommand(command);
    checker.processBool(command.exp);
    TypeChecker checker2 = new TypeChecker(checker);
    checker2.process(command.command3);
    checker.process(command.command2);
    Set<ValueSymbol> frame = new LinkedHashSet<ValueSymbol>();
    frame.addAll(command.command1.getFrame());
    frame.addAll(command.command2.getFrame());
    frame.addAll(command.command3.getFrame());
    if (command.command1 instanceof VarCommand)
    {
      VarCommand c = (VarCommand)command.command1;
      frame.remove((ValueSymbol)c.ident.getSymbol());
    }
    command.setFrame(frame);
  }
  private void process(ForInCommand command)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(command.qvar);
    int n = symbols.length;
    Type ttype;
    if (n == 1)
    {
      ttype = symbols[0].type;
    }
    else
    {
      Type[] t = new Type[n];
      for (int i=0; i<n; i++) t[i] = symbols[i].type;
      ttype = processType(new TupleType(t));
    }
    Type type = processType(new SetType(ttype));
    Identifier ident = process(new Identifier(forSetName));
    ValueSymbol allSet = checker.env.putValue(ident, type, ValueSymbol.Kind.value);
    allSet.setSlot(checker.getSlot());
    command.setAllSet(allSet);
    checker.processLoopCommand(command);
    TypeChecker checker2 = new TypeChecker(checker);
    checker2.process(command.command);
    command.setFrame(new LinkedHashSet<ValueSymbol>(command.command.getFrame()));
  }
  private void process(IfThenCommand command)
  {
    processBool(command.exp);
    TypeChecker checker = new TypeChecker(this);
    checker.process(command.command);
    command.setFrame(new LinkedHashSet<ValueSymbol>(command.command.getFrame()));
  }
  private void process(IfThenElseCommand command)
  {
    processBool(command.exp);
    TypeChecker checker1 = new TypeChecker(this);
    checker1.process(command.command1);
    TypeChecker checker2 = new TypeChecker(this);
    checker2.process(command.command2);
    Set<ValueSymbol> frame = new LinkedHashSet<ValueSymbol>();
    frame.addAll(command.command1.getFrame());
    frame.addAll(command.command2.getFrame());
    command.setFrame(frame);
  }
  private void process(MatchCommand command)
  {
    RecursiveType type = processRecursive(command.exp);
    Set<ValueSymbol> frame = new LinkedHashSet<ValueSymbol>();
    for (PatternCommand pc : command.pcommand)
      process(pc, type, frame);
    command.setFrame(frame);
  }
  private void process(PrintCommand command)
  {
    for (Expression e : command.exps)
      process(e);
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(CheckCommand command)
  {
    Identifier ident = process(command.ident);
    if (command.exp == null)
    {
      List<FunctionSymbol> fun = env.getFunctions(ident);
      if (fun == null)
        typeError(command.ident, "unknown function " + command.ident);
      if (fun.size() != 1)
        typeError(ident, "multiple functions named " + command.ident);
      ident.setSymbol(fun.get(0));
    }
    else
    {
      SetType type = processSet(command.exp);
      List<Type> types = new ArrayList<Type>();
      Type type0 = type.type;
      if (type0 instanceof TupleType)
      {
        TupleType type1 = (TupleType)type0;
        for (Type t : type1.types)
          types.add(t);
      }
      else
        types.add(type0);
      Type[] types0 = types.toArray(new Type[1]);
      FunctionSymbol fun = getFunction(ident, types0);
      if (fun == null)
        typeError(ident, "unknown function with argument types <" +
            AST.toString(types0, ", ") + ">");
      command.setFrame(new LinkedHashSet<ValueSymbol>());
    }
  }
  private void process(ValCommand command)
  {
    Identifier ident = process(command.ident);
    ValueSymbol value = env.getValue(ident);
    if (value != null && (value.kind == ValueSymbol.Kind.value || value.kind == ValueSymbol.Kind.variable))
    {
      SourcePosition pos = value.ident.getPosition();
      if (pos == null)
        typeError(ident, "reserved name must not be used for value");
      else
        typeError(ident, "value already defined at " + pos.toShortString());
    }
    Type type = null;
    if (command.type != null) type = process(command.type);
    Type etype = process(command.exp);
    if (type == null)
      type = etype;
    else if (!matchesWeak(etype, type))
      typeError(command.exp, "expression has type " + etype + 
          " but must have type " + type);
    ValueSymbol symbol = env.putValue(ident, type, ValueSymbol.Kind.value);
    symbol.setSlot(getSlot());
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(VarCommand command)
  {
    Identifier ident = process(command.ident);
    ValueSymbol value = env.getValue(ident);
    if (value != null && (value.kind == ValueSymbol.Kind.value || value.kind == ValueSymbol.Kind.variable))
    {
      SourcePosition pos = value.ident.getPosition();
      if (pos == null)
        typeError(ident, "reserved name must not be used for variable");
      else
        typeError(ident, "variable already defined at " + pos.toShortString());
    }
    Type type = process(command.type);
    if (command.exp != null) 
    {
      Type etype = process(command.exp);
      if (etype != null && !matchesWeak(etype, type))
        typeError(command, "expression has type " + etype + 
            " but should have type " + type);
    }
    ValueSymbol symbol = env.putValue(ident, type, ValueSymbol.Kind.variable);
    symbol.setSlot(getSlot());
    symbol.setSyntacticType(command.type);
    command.setFrame(new LinkedHashSet<ValueSymbol>());
  }
  private void process(WhileCommand command)
  {
    processBool(command.exp);
    processLoopCommand(command);
    TypeChecker checker = new TypeChecker(this);
    checker.process(command.command);
    command.setFrame(new LinkedHashSet<ValueSymbol>(command.command.getFrame()));
  }
  
  /****************************************************************************
   * Process the annotations of a loop command.
   * @param command the loop command.
   ***************************************************************************/
  private void processLoopCommand(LoopCommand command)
  {
    TypeChecker checker = new TypeChecker(this);
    for (ValueSymbol v : env.getValues())
    {
      if (v.kind != ValueSymbol.Kind.variable) continue;
      Identifier ident = process(oldIdentifier(v.ident));
      ValueSymbol symbol = checker.env.putValue(ident, v.type, ValueSymbol.Kind.value);
      symbol.setSlot(checker.getSlot());
      symbol.setSyntacticType(v.getSyntacticType());
      command.add(v, symbol);
    }
    for (LoopSpec s : command.spec)
      process(s, checker);
  }
  
  // -------------------------------------------------------------------------
  //
  // LoopSpec
  //
  // -------------------------------------------------------------------------
  private void process(LoopSpec spec, TypeChecker checker)
  {
    if (spec instanceof DecreasesLoopSpec) 
      process((DecreasesLoopSpec)spec);
    else if (spec instanceof InvariantLoopSpec) 
      process((InvariantLoopSpec)spec, checker);
    else
      typeError(spec, "unknown kind of loop specification");
  }
  private void process(DecreasesLoopSpec spec)
  {
    for (Expression e : spec.exps)
      processInt(e);
  }
  private void process(InvariantLoopSpec spec, TypeChecker checker)
  {
    checker.processBool(spec.exp);
  }
  
  // -------------------------------------------------------------------------
  //
  // Expression
  //
  // -------------------------------------------------------------------------
  private Type process(Expression exp)
  {
    // the expression is annotated with its canonical type
    // after stripping of the subtypes
    Type type = null;
    if (exp instanceof AndExp)
      type = process((AndExp)exp);
    else if (exp instanceof ApplicationExp)
      type = process((ApplicationExp)exp);
    else if (exp instanceof ArrayBuilderExp)
      type = process((ArrayBuilderExp)exp);
    else if (exp instanceof MapBuilderExp)
      type = process((MapBuilderExp)exp);
    else if (exp instanceof MapSelectionExp)
      type = process((MapSelectionExp)exp);
    else if (exp instanceof MapUpdateExp)
      type = process((MapUpdateExp)exp);
    else if (exp instanceof AssertExp)
      type = process((AssertExp)exp);
    else if (exp instanceof BigIntersectExp)
      type = process((BigIntersectExp)exp);
    else if (exp instanceof BigUnionExp)
      type = process((BigUnionExp)exp);
    else if (exp instanceof ChooseExp)
      type = process((ChooseExp)exp);
    else if (exp instanceof ChooseInElseExp)
      type = process((ChooseInElseExp)exp);
    else if (exp instanceof ChooseInExp)
      type = process((ChooseInExp)exp);
    else if (exp instanceof DividesExp)
      type = process((DividesExp)exp);
    else if (exp instanceof EmptySetExp)
      type = process((EmptySetExp)exp);
    else if (exp instanceof EnumeratedSetExp)
      type = process((EnumeratedSetExp)exp);
    else if (exp instanceof CartesianExp)
      type = process((CartesianExp)exp);
    else if (exp instanceof EqualsExp)
      type = process((EqualsExp)exp);
    else if (exp instanceof EquivExp)
      type = process((EquivExp)exp);
    else if (exp instanceof ExistsExp)
      type = process((ExistsExp)exp);
    else if (exp instanceof FalseExp)
      type = process((FalseExp)exp);
    else if (exp instanceof ForallExp)
      type = process((ForallExp)exp);
    else if (exp instanceof GreaterEqualExp)
      type = process((GreaterEqualExp)exp);
    else if (exp instanceof GreaterExp)
      type = process((GreaterExp)exp);
    else if (exp instanceof IdentifierExp)
      type = process((IdentifierExp)exp);
    else if (exp instanceof IfThenElseExp)
      type = process((IfThenElseExp)exp);
    else if (exp instanceof ImpliesExp)
      type = process((ImpliesExp)exp);
    else if (exp instanceof InSetExp)
      type = process((InSetExp)exp);
    else if (exp instanceof IntersectExp)
      type = process((IntersectExp)exp);
    else if (exp instanceof IntervalExp)
      type = process((IntervalExp)exp);
    else if (exp instanceof LessEqualExp)
      type = process((LessEqualExp)exp);
    else if (exp instanceof LessExp)
      type = process((LessExp)exp);
    else if (exp instanceof LetExp)
      type = process((LetExp)exp);
    else if (exp instanceof LetParExp)
      type = process((LetParExp)exp);
    else if (exp instanceof MatchExp)
      type = process((MatchExp)exp);
    else if (exp instanceof MinusExp)
      type = process((MinusExp)exp);
    else if (exp instanceof NegationExp)
      type = process((NegationExp)exp);
    else if (exp instanceof FactorialExp)
      type = process((FactorialExp)exp);
    else if (exp instanceof NotEqualsExp)
      type = process((NotEqualsExp)exp);
    else if (exp instanceof NotExp)
      type = process((NotExp)exp);
    else if (exp instanceof NumberExp)
      type = process((NumberExp)exp);
    else if (exp instanceof NumberLiteralExp)
      type = process((NumberLiteralExp)exp);
    else if (exp instanceof OrExp)
      type = process((OrExp)exp);
    else if (exp instanceof PlusExp)
      type = process((PlusExp)exp);
    else if (exp instanceof PlusExpMult)
      type = process((PlusExpMult)exp);
    else if (exp instanceof PowerExp)
      type = process((PowerExp)exp);
    else if (exp instanceof PowersetExp)
      type = process((PowersetExp)exp);
    else if (exp instanceof Powerset1Exp)
      type = process((Powerset1Exp)exp);
    else if (exp instanceof Powerset2Exp)
      type = process((Powerset2Exp)exp);
    else if (exp instanceof PrintExp)
      type = process((PrintExp)exp);
    else if (exp instanceof PrintInExp)
      type = process((PrintInExp)exp);
    else if (exp instanceof CheckExp)
      type = process((CheckExp)exp);
    else if (exp instanceof RecApplicationExp)
      type = process((RecApplicationExp)exp);
    else if (exp instanceof RecIdentifierExp)
      type = process((RecIdentifierExp)exp);
    else if (exp instanceof RecordExp)
      type = process((RecordExp)exp);
    else if (exp instanceof RecordSelectionExp)
      type = process((RecordSelectionExp)exp);
    else if (exp instanceof RecordUpdateExp)
      type = process((RecordUpdateExp)exp);
    else if (exp instanceof RemainderExp)
      type = process((RemainderExp)exp);
    else if (exp instanceof SetBuilderExp)
      type = process((SetBuilderExp)exp);
    else if (exp instanceof SumExp)
      type = process((SumExp)exp);
    else if (exp instanceof ProductExp)
      type = process((ProductExp)exp);
    else if (exp instanceof MinExp)
      type = process((MinExp)exp);
    else if (exp instanceof MaxExp)
      type = process((MaxExp)exp);
    else if (exp instanceof SetSizeExp)
      type = process((SetSizeExp)exp);
    else if (exp instanceof SubsetExp)
      type = process((SubsetExp)exp);
    else if (exp instanceof TimesExp)
      type = process((TimesExp)exp);
    else if (exp instanceof TimesExpMult)
      type = process((TimesExpMult)exp);
    else if (exp instanceof TrueExp)
      type = process((TrueExp)exp);
    else if (exp instanceof TupleExp)
      type = process((TupleExp)exp);
    else if (exp instanceof TupleSelectionExp)
      type = process((TupleSelectionExp)exp);
    else if (exp instanceof TupleUpdateExp)
      type = process((TupleUpdateExp)exp);
    else if (exp instanceof UnionExp)
      type = process((UnionExp)exp);
    else if (exp instanceof UnitExp)
      type = process((UnitExp)exp);
    else if (exp instanceof WithoutExp)
      type = process((WithoutExp)exp);
    else
      typeError(exp, "unknown kind of expression");
    type = getBaseType(type);
    exp.setType(type);
    return type;
  }
  private BoolType process(AndExp exp)
  {
    processBool(exp.exp1);
    return processBool(exp.exp2);
  }
  private Type process(ApplicationExp exp)
  {
    Identifier ident = process(exp.ident);
    List<Type> types = new ArrayList<Type>();
    for (Expression e : exp.exps)
      types.add(process(e));
    Type[] types0 = types.toArray(new Type[types.size()]);
    FunctionSymbol fun = getFunction(ident, types0);
    if (fun == null)
      typeError(ident, "unknown function " + ident.string +"(" +
          AST.toString(types0, ", ") + ")");
    return fun.type;
  }
  private MapType process(ArrayBuilderExp exp)
  {
    long ivalue = evalInt(exp.exp1);
    if (ivalue < 0)
      typeError(exp.exp1, "array length " + ivalue + " must not be negative");
    Type itype = intType(0, ivalue-1);
    Type type = process(exp.type);
    Type etype = process(exp.exp2);
    if (!matchesWeak(etype, type))
      typeError(exp.exp2, "expression must have type " + type +
          " but has type " + etype);
    return (MapType)processType(new MapType(itype, type));
  }
  private MapType process(MapBuilderExp exp)
  {
    Type type1 = process(exp.type1);
    Type type2 = process(exp.type2);
    Type etype = process(exp.exp);
    if (!matchesWeak(etype, type2))
      typeError(exp.exp, "expression must have type " + type2 +
          " but has type " + etype);
    return (MapType)processType(new MapType(type1, type2));
  }
  private Type process(MapSelectionExp exp)
  {
    MapType type0 = processMap(exp.exp1);
    Type type1 = process(exp.exp2);
    if (!matchesWeak(type1, type0.type1))
      typeError(exp.exp2, "expression must have type " + type0.type1 + 
          " but has type " + type1);
    return type0.type2;
  }
  private MapType process(MapUpdateExp exp)
  {
    MapType type0 = processMap(exp.exp1);
    Type type1 = process(exp.exp2);
    if (!matchesWeak(type1, type0.type1))
      typeError(exp.exp2, "expression must have type " + type0.type1 + 
          " but has type " + type1);
    Type type3 = process(exp.exp3);
    if (!matchesWeak(type3, type0.type2))
      typeError(exp.exp3, "expression must have type " + type0.type2 +
          " but has type " + type3);
    return type0;
  }
  private Type process(AssertExp exp)
  {
    processBool(exp.exp1);
    return process(exp.exp2);
  }
  private SetType process(BigIntersectExp exp)
  {
    SetType type = processSetOfSet(exp.exp);
    return (SetType)type.type;
  }
  private SetType process(BigUnionExp exp)
  {
    SetType type = processSetOfSet(exp.exp);
    return (SetType)type.type;
  }
  private Type process(ChooseExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbol = checker.process(exp.qvar);
    if (symbol.length == 1) return symbol[0].type;
    List<Type> types = new ArrayList<Type>(symbol.length);
    for (ValueSymbol s : symbol) types.add(s.type);
    return (TupleType)processType(new TupleType(types));
  }
  private Type process(ChooseInElseExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    Type type1 = checker.process(exp.exp1);
    Type type2 = process(exp.exp2);
    Type type3 = union(type1, type2);
    if (type3 == null)
      typeError(exp, "expression " + exp.exp1 + " has type " + type1 +
          " but expression " + exp.exp2 + " has different type " + type2);
    return type3;
  }
  private Type process(ChooseInExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    return checker.process(exp.exp);
  }
  private IntType process(DividesExp exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    if (b1 == 0) b1 = 1L;
    if (b2 == 0) b2 = 1L;
    return intType(a1/b2, a2/b1);
  }
  private SetType process(EmptySetExp exp)
  {
    Type type = process(exp.type);
    return (SetType)processType(new SetType(type));
  }
  private SetType process(EnumeratedSetExp exp)
  {
    // grammar ensures that there is at least one expression
    Type type = null;
    for (Expression e : exp.exps)
    {
      Type etype = process(e);
      if (type == null)
        type = etype;
      else if (!matchesWeak(etype, type))
        typeError(e, "expression must have type " + type + 
            " but has type " + etype);
      else
        type = combine(type, etype, true);
    }
    return (SetType)processType(new SetType(type));
  }
  private SetType process(CartesianExp exp)
  {
    // grammar ensures that there is at least one expression
    int n = exp.exps.length;
    Type[] types = new Type[n];
    for (int i = 0; i<n; i++)
    {
      SetType type = processSet(exp.exps[i]);
      types[i] = type.type;       
    }
    Type ttype = processType(new TupleType(types));
    return (SetType)processType(new SetType(ttype));
  }
  private BoolType process(EqualsExp exp)
  {
    Type type1 = process(exp.exp1);
    Type type2 = process(exp.exp2);
    Type type = union(type1, type2);
    if (type == null)
      typeError(exp.exp2, "expression must have type " + type1 +
          " but has type " + type2);
    return (BoolType)processType(new BoolType());
  }
  private BoolType process(EquivExp exp)
  {
    processBool(exp.exp1);
    return processBool(exp.exp2);
  }
  private BoolType process(ExistsExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    return checker.processBool(exp.exp);
  }
  private BoolType process(FalseExp exp)
  {
    return (BoolType)processType(new BoolType());
  }
  private BoolType process(ForallExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    return checker.processBool(exp.exp);
  }
  private BoolType process(GreaterEqualExp exp)
  {
    processInt(exp.exp1);
    processInt(exp.exp2);
    return (BoolType)processType(new BoolType());
  }
  private BoolType process(GreaterExp exp)
  {
    processInt(exp.exp1);
    processInt(exp.exp2);
    return (BoolType)processType(new BoolType());
  }
  private Type process(IdentifierExp exp)
  {
    Identifier ident = process(exp.ident);
    ValueSymbol symbol = env.getValue(ident);
    if (symbol == null)
      typeError(exp, "unknown identifier");
    return symbol.type;
  }
  private Type process(IfThenElseExp exp)
  {
    processBool(exp.exp1);
    Type type1 = process(exp.exp2);
    Type type2 = process(exp.exp3);
    Type type3 = union(type1, type2);
    if (type3 == null)
      typeError(exp.exp3, "expression must have type " + type1 +
          " but has type " + type2);
    return type3;
  }
  private BoolType process(ImpliesExp exp)
  {
    processBool(exp.exp1);
    return processBool(exp.exp2);
  }
  private BoolType process(InSetExp exp)
  {
    Type type1 = process(exp.exp1);
    SetType type2 = processSet(exp.exp2);
    if (!matchesWeak(type1, type2.type))
      typeError(exp.exp1, "expression must have type " + type2.type +
          " but has type " + type1);
    return (BoolType)processType(new BoolType());
  }
  private SetType process(IntersectExp exp)
  {
    SetType type1 = processSet(exp.exp1);
    SetType type2 = processSet(exp.exp2);
    SetType type3 = (SetType)intersect(type1, type2);
    if (type3 == null)
      typeError(exp.exp2, "expression must have type " + type1 +
          " but has type " + type2);
    return type3;
  }
  private SetType process(IntervalExp exp)
  {
    Long int1 = tryEvalInt(exp.exp1);
    if (int1 == null) int1 = Long.MIN_VALUE;
    Long int2 = tryEvalInt(exp.exp2);
    if (int2 == null) int2 = Long.MAX_VALUE;
    return (SetType)processType(new SetType(intType(int1, int2)));
  }
  private BoolType process(LessEqualExp exp)
  {
    processInt(exp.exp1);
    processInt(exp.exp2);
    return (BoolType)processType(new BoolType());
  }
  private BoolType process(LessExp exp)
  {
    processInt(exp.exp1);
    processInt(exp.exp2);
    return (BoolType)processType(new BoolType());
  }
  private Type process(LetExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    for (Binder b: exp.binder)
      checker.process(b);
    return checker.process(exp.exp);
  }
  private Type process(LetParExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.binder);
    return checker.process(exp.exp);
  }
  private Type process(MatchExp exp)
  {
    // grammar ensures that there is at least one pattern
    RecursiveType rtype = processRecursive(exp.exp);
    Type etype = null;
    for (PatternExpression p : exp.pexp)
    {
      Type etype0 = process(p, rtype);
      if (etype == null)
        etype = etype0;
      else 
      {
        Type etype1 = union(etype, etype0);
        if (etype1 == null)
          typeError(p.exp, "expression must have type " + etype + 
              " but has type " + etype0);
        etype = etype1;
      }
    }
    return etype; 
  }
  private IntType process(MinusExp exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      long c1 = Math.subtractExact(a1, b2);
      long c2 = Math.subtractExact(a2, b1);
      return intType(c1, c2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    } 
  }
  private IntType process(NegationExp exp)
  {
    IntType type = processInt(exp.exp);
    long a1 = type.ivalue1;
    long a2 = type.ivalue2;
    try
    {
      long b1 = Math.subtractExact(0,a2);
      long b2 = Math.subtractExact(0,a1);
      return intType(b1, b2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    } 
  }
  private IntType process(FactorialExp exp)
  {
    IntType type = processInt(exp.exp);
    long a1 = type.ivalue1;
    long a2 = type.ivalue2;
    try
    {
      long b1 = Arith.multiProduct(1,a1);
      long b2 = Arith.multiProduct(1,a2);
      return intType(b1, b2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    } 
  }
  private BoolType process(NotEqualsExp exp)
  {
    Type type1 = process(exp.exp1);
    Type type2 = process(exp.exp2);
    Type type = union(type1, type2);
    if (type == null)
      typeError(exp.exp2, "expression must have type " + type1 +
          " but has type " + type2);
    return (BoolType)processType(new BoolType());
  }
  private BoolType process(NotExp exp)
  {
    return processBool(exp.exp);
  }
  private IntType process(NumberExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] vars = checker.process(exp.qvar);
    long n = varSize(vars);
    return intType(0, n);
  }
  private IntType process(NumberLiteralExp exp)
  {
    String string = exp.literal.value;
    try
    {
      int value = Integer.parseInt(string);
      exp.literal.setInteger(value);
      return intType(value, value);
    }
    catch (NumberFormatException e)
    {
      typeError(exp, "number is too large");
      return null;
    }
  }
  private BoolType process(OrExp exp)
  {
    processBool(exp.exp1);
    return processBool(exp.exp2);
  }
  private IntType process(PlusExp exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      long c1 = Math.addExact(a1, b1);
      long c2 = Math.addExact(a2, b2);
      return intType(c1, c2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    } 
  }
  private IntType process(PlusExpMult exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      long c1 = Arith.multiSum(a2,b1);
      long c2 = Arith.multiSum(a1,b2);
      return intType(c1,c2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    } 
  }
  private IntType process(PowerExp exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      if (a1 < 0) a1 = 0L;
      if (a2 < 0) a2 = 0L;
      if (b1 < 0) b1 = 0L;
      if (b2 < 0) b2 = 0L;
      long c1 = Arith.power(a1, b1);
      long c2 = Arith.power(a2, b2);
      return intType(c1, c2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    }
  }
  private SetType process(PowersetExp exp)
  {
    SetType type = processSet(exp.exp);
    return (SetType)processType(new SetType(type));
  }
  private SetType process(Powerset1Exp exp)
  {
    SetType type = processSet(exp.exp1);
    processInt(exp.exp2);
    return (SetType)processType(new SetType(type));
  }
  private SetType process(Powerset2Exp exp)
  {
    SetType type = processSet(exp.exp1);
    processInt(exp.exp2);
    processInt(exp.exp3);
    return (SetType)processType(new SetType(type));
  }
  private Type process(PrintExp exp)
  {
    return process(exp.exp);
  }
  private Type process(PrintInExp exp)
  {
    for (Expression e : exp.exps) process(e);
    return process(exp.exp);
  }
  private BoolType process(CheckExp exp)
  {
    Identifier ident = process(exp.ident);
    if (exp.exp == null)
    {
      List<FunctionSymbol> fun = env.getFunctions(ident);
      if (fun == null)
        typeError(exp.ident, "unknown function " + exp.ident);
      if (fun.size() != 1)
        typeError(ident, "multiple functions named " + exp.ident);
      ident.setSymbol(fun.get(0));
    }
    else
    {
      SetType type = processSet(exp.exp);
      List<Type> types = new ArrayList<Type>();
      Type type0 = type.type;
      if (type0 instanceof TupleType)
      {
        TupleType type1 = (TupleType)type0;
        for (Type t : type1.types)
          types.add(t);
      }
      else
        types.add(type0);
      Type[] types0 = types.toArray(new Type[1]);
      FunctionSymbol fun = getFunction(ident, types0);
      if (fun == null)
        typeError(ident, "unknown function with argument types <" +
            AST.toString(types0, ", ") + ">");
    }
    return (BoolType)processType(new BoolType());
  }
  private RecursiveType process(RecApplicationExp exp)
  {
    Identifier ident1 = process(exp.ident1);
    Identifier ident2 = process(exp.ident2);
    RecursiveTypeItem ritem = getItem(ident1);
    Type[] etypes = new Type[exp.exps.length];
    for (int i=0; i<exp.exps.length; i++)
      etypes[i] = process(exp.exps[i]);
    FunctionSymbol fun = getFunction(ritem, ident2, etypes);
    if (fun == null)
      typeError(ident2, "no constructor for argument types " + 
          AST.toString(etypes, ", "));
    RecursiveType type = (RecursiveType)fun.type;
    // enforce runtime type checking
    return new RecursiveType(Long.MAX_VALUE, type.ritem, Arrays.asList(type.symbols));
  }
  private RecursiveType process(RecIdentifierExp exp)
  {
    Identifier ident1 = process(exp.ident1);
    Identifier ident2 = process(exp.ident2);
    RecursiveTypeItem ritem = getItem(ident1);
    ValueSymbol value = getValue(ritem, ident2);
    if (value == null)
      typeError(ident2, "no value constructor");
    return (RecursiveType)value.type;
  }
  private RecordType process(RecordExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    Parameter[] params = checker.process(exp.eidents);
    List<Parameter> params0 = new ArrayList<Parameter>(params.length);
    for (Parameter p : params) params0.add(p);
    Type type = new RecordType(params0);
    return (RecordType)process(type);
  }
  private Type process(RecordSelectionExp exp)
  {
    Identifier ident = process(exp.ident);
    Type type = process(exp.exp);
    if (!(type instanceof RecordType))
      typeError(exp, "must have type Record but has type " + type);
    RecordType type0 = (RecordType)type;
    ValueSymbol value = getSelector(type0, ident);
    if (value == null)
      typeError(ident, "no selector in type " + type0);
    return value.type;
  }
  private Type process(RecordUpdateExp exp)
  {
    Type type = process(exp.exp1);
    if (!(type instanceof RecordType))
      typeError(exp, "must have type Record but has type " + type);
    RecordType type0 = (RecordType)type;
    Identifier ident = process(exp.ident);
    ValueSymbol value = getSelector(type0, ident);
    if (value == null)
      typeError(ident, "no selector in type " + type0);
    Type type1 = value.type;
    Type etype = process(exp.exp2);
    if (!matchesWeak(etype, type1))
      typeError(exp, "must have type " + type1 + " but has type " + etype);
    return type1;
  }
  private IntType process(RemainderExp exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      long c = Math.subtractExact(Math.max(Math.abs(b1), Math.abs(b2)),1);
      if (a1 >= 0) 
        return intType(0,c);
      if (a2 <= 0)
        return intType(Math.negateExact(c),0);
      return intType(Math.negateExact(c),c);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    }
  }
  private SetType process(SetBuilderExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    Type type = checker.process(exp.exp);
    return (SetType)processType(new SetType(type));
  }
  private IntType process(SumExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(exp.qvar);
    IntType type = checker.processInt(exp.exp);
    long number = checker.combinationNumber(exp.qvar, symbols);
    try
    {
      long min = exp.qvar.exp == null ? 
          Math.multiplyExact(number, type.ivalue1) : 0;
      long max = Math.multiplyExact(number, type.ivalue2);
      return intType(min,max);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    }
  }
  private IntType process(ProductExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(exp.qvar);
    IntType type = checker.processInt(exp.exp);
    long number = checker.combinationNumber(exp.qvar, symbols);
    try
    {
      long min = exp.qvar.exp == null ? 
          Arith.power(type.ivalue1, number) : 1;
      long max = Arith.power(type.ivalue2, number);
      return intType(min,max);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    }
  }
  private IntType process(MinExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    return checker.processInt(exp.exp);
  }
  private IntType process(MaxExp exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(exp.qvar);
    return checker.processInt(exp.exp);
  }
  private IntType process(SetSizeExp exp)
  {
    Type type = processSet(exp.exp);
    long n = type.getSize();
    return intType(0, n);
  }
  private BoolType process(SubsetExp exp)
  {
    SetType type1 = processSet(exp.exp1);
    SetType type2 = processSet(exp.exp2);
    if (!matchesWeak(type1, type2))
      typeError(exp.exp1, "must have type " + type2 + " but has type " + type1);
    return (BoolType)processType(new BoolType());
  }
  private IntType process(TimesExp exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      long c1 = Math.multiplyExact(a1, b1);
      long c2 = Math.multiplyExact(a2, b2);
      return intType(c1, c2);
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    }
  }
  private IntType process(TimesExpMult exp)
  {
    IntType type1 = processInt(exp.exp1);
    IntType type2 = processInt(exp.exp2);
    long a1 = type1.ivalue1;
    long a2 = type1.ivalue2;
    long b1 = type2.ivalue1;
    long b2 = type2.ivalue2;
    try
    {
      if (a1 >= 0 && b1 >= 0)
      {
        long c1 = Arith.multiProduct(a2,b1);
        long c2 = Arith.multiProduct(a1,b2);
        return intType(c1,c2);
      }
      else
      {
        // too lazy to consider all cases in detail
        if (a1 == Integer.MIN_VALUE || a2 == Integer.MIN_VALUE ||
            b1 == Integer.MIN_VALUE || b2 == Integer.MIN_VALUE)
          typeError(exp, "type range of integer result cannot be computed");
        long a10 = a1 >= 0 ? a1 : -a1;
        long a20 = a2 >= 0 ? a2 : -a2;
        long b10 = b1 >= 0 ? b1 : -b1;
        long b20 = b2 >= 0 ? b2 : -b2;
        long n = Arith.multiProduct(1, Math.max(Math.max(a10,a20), Math.max(b10,b20)));
        return intType(-n,n);
      }
    }
    catch(ArithmeticException e)
    {
      typeError(exp, "type range of integer result is too large");
      return null;
    }
  }
  private BoolType process(TrueExp exp)
  {
    return (BoolType)processType(new BoolType());
  }
  private TupleType process(TupleExp exp)
  {
    List<Type> types = new ArrayList<Type>(exp.exps.length);
    for (Expression e : exp.exps)
    {
      Type type = process(e);
      types.add(type);
    }
    return (TupleType)processType(new TupleType(types));
  }
  private Type process(TupleSelectionExp exp)
  {
    TupleType type = processTuple(exp.exp);
    return getType(type, exp.index);
  }
  private TupleType process(TupleUpdateExp exp)
  {
    TupleType type = processTuple(exp.exp1);
    Type type0 = getType(type, exp.index);
    Type etype = process(exp.exp2);
    if (!matchesWeak(etype, type0))
      typeError(exp.exp2, "expression must have type " + type0 +
          " but has type " + etype);
    return type;
  }
  private SetType process(UnionExp exp)
  {
    SetType type1 = processSet(exp.exp1);
    SetType type2 = processSet(exp.exp2);
    SetType type3 = (SetType)union(type1, type2);
    if (type3 == null)
      typeError(exp.exp2, "expression must have type " + type1 +
          " but has type " + type2);
    return type3;
  }
  private UnitType process(UnitExp exp)
  {
    return new UnitType();
  }
  private SetType process(WithoutExp exp)
  {
    SetType type1 = processSet(exp.exp1);
    SetType type2 = processSet(exp.exp2);
    if (!matchesWeak(type2, type1))
      typeError(exp.exp2, "expression must have type " + type1 +
          " but has type " + type2);
    return type1;
  }
  
  // -------------------------------------------------------------------------
  //
  // Type
  //
  // -------------------------------------------------------------------------
  private Type processType(Type type)
  {
    // make type canonical
    type.setCanonical();
    return type;
  }
  private Type process(Type type) 
  {
    if (type.isCanonical()) return type;
    Type type0 = null;
    if (type instanceof ArrayType)
      type0 = process((ArrayType)type);
    else if (type instanceof BoolType)
      type0 = process((BoolType)type);
    else if (type instanceof IdentifierType)
      type0 = process((IdentifierType)type);
    else if (type instanceof IntType)
      type0 = process((IntType)type);
    else if (type instanceof MapType)
      type0 = process((MapType)type);
    else if (type instanceof NatType)
      type0 = process((NatType)type);
    else if (type instanceof RecordType)
      type0 = process((RecordType)type);
    else if (type instanceof RecursiveType)
      type0 = process((RecursiveType)type);
    else if (type instanceof SetType)
      type0 = process((SetType)type);
    else if (type instanceof SubType)
      type0 = process((SubType)type);
    else if (type instanceof TupleType)
      type0 = process((TupleType)type);
    else if (type instanceof UnitType)
      type0 = process((UnitType)type);
    else
      typeError(type, "unknown kind of type");
    return processType(type0);
  }
  private MapType process(ArrayType type)
  {
    long ivalue = evalInt(type.exp);
    if (ivalue < 0)
      typeError(type.exp, "array length " + ivalue + " must not be negative");
    Type type0 = process(type.type);
    Type type1 = intType(0, ivalue-1);
    return new MapType(type1, type0);
  }
  private BoolType process(BoolType type)
  {
    return type;
  }
  private Type process(IdentifierType type)
  {
    Identifier ident = process(type.ident);
    TypeSymbol tsymbol = env.getType(ident);
    if (tsymbol == null)
      typeError(type, "unknown type");
    return tsymbol.getType();
  }
  private IntType process(IntType type)
  {
    long ivalue1 = evalInt(type.exp1);
    long ivalue2 = evalInt(type.exp2);
    if (ivalue1 > ivalue2)
      typeError(type, "lower bound " + ivalue1 + 
          " must be less than equal the upper bound " + ivalue2);
    return new IntType(ivalue1, ivalue2);
  }
  private MapType process(MapType type)
  {
    Type type10 = process(type.type1);
    Type type20 = process(type.type2);
    return new MapType(type10, type20);
  }
  private IntType process(NatType type)
  {
    long ivalue = evalInt(type.exp);
    if (ivalue < 0)
      typeError(type, "bound " + ivalue + " must be greater equal 0 ");
    return new IntType(0, ivalue);
  }
  private RecordType process(RecordType type)
  {
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(type.param, false);
    List<ValueSymbol> symbols0 = new ArrayList<ValueSymbol>(symbols.length);
    for (ValueSymbol s : symbols) symbols0.add(s);  
    return new RecordType(symbols0, true);
  }
  private RecursiveType process(RecursiveType type)
  {
    Identifier ident = process(type.ident);
    long ivalue = evalInt(type.exp);
    if (ivalue < 0)
      typeError(type.exp, "recursion depth " + ivalue + " must not be negative");
    TypeChecker checker = new TypeChecker(this);
    List<TypeSymbol> symbols = checker.process(type.ritems, (int)ivalue);
    RecursiveTypeItem ritem = null;
    for (TypeSymbol s : symbols)
    {
      RecursiveType t = (RecursiveType)s.getType();
      if (equals(ident, t.ritem.ident))
      {
        ritem = t.ritem;
        break;
      }
    }
    if (ritem == null)
      typeError(ident, "type not defined in " + type);
    ident.setSymbol(ritem.ident.getSymbol());
    return new RecursiveType((int)ivalue, ritem, symbols);
  }
  private SetType process(SetType type)
  {
    Type type0 = process(type.type);
    return new SetType(type0);
  }
  private TupleType process(TupleType type)
  {
    List<Type> types = new ArrayList<Type>(type.types.length);
    for (Type t : type.types)
      types.add(process(t));
    return new TupleType(types);
  }
  private SubType process(SubType type)
  {
    Type type0 = process(type.type);
    return new SubType(type.ident, type0, type.pred);
  }
  private UnitType process(UnitType type)
  {
    return type;
  }
  
  // -------------------------------------------------------------------------
  //
  // QuantifiedVariable
  //
  // -------------------------------------------------------------------------
  private ValueSymbol[] process(QuantifiedVariable qvar)
  {
    List<ValueSymbol> symbols = new ArrayList<ValueSymbol>();
    for (QuantifiedVariableCore qvcore : qvar.qvcore)
    {
      ValueSymbol symbol = process(qvcore);
      symbols.add(symbol);
    }
    if (qvar.exp != null) processBool(qvar.exp);
    ValueSymbol[] result = symbols.toArray(new ValueSymbol[symbols.size()]);
    return result;
  }
  
  // -------------------------------------------------------------------------
  //
  // QuantifiedVariableCore
  //
  // -------------------------------------------------------------------------
  private ValueSymbol process(QuantifiedVariableCore qvcore)
  {
    if (qvcore instanceof IdentifierTypeQuantifiedVar)
      return process((IdentifierTypeQuantifiedVar)qvcore);
    else if (qvcore instanceof IdentifierSetQuantifiedVar)
      return process((IdentifierSetQuantifiedVar)qvcore);
    else
      typeError(qvcore, "unknown kind of quantified variable");
    return null;
  }
  private ValueSymbol process(IdentifierTypeQuantifiedVar qvcore)
  {
    Identifier ident = process(qvcore.ident);
    Type type = process(qvcore.type);
    ValueSymbol symbol = env.putValue(ident, type, ValueSymbol.Kind.value);
    symbol.setSlot(getSlot());
    return symbol;
  }
  private ValueSymbol process(IdentifierSetQuantifiedVar qvcore)
  {
    Identifier ident = process(qvcore.ident);
    Type type = processSet(qvcore.exp);
    SetType type0 = (SetType)type;
    ValueSymbol symbol = env.putValue(ident, type0.type, ValueSymbol.Kind.value);
    symbol.setSlot(getSlot());
    return symbol;
  }
  
  // -------------------------------------------------------------------------
  //
  // Binder
  //
  // -------------------------------------------------------------------------
  private void process(Binder binder)
  {
    Identifier ident = process(binder.ident);
    Type type = process(binder.exp);
    ValueSymbol symbol = env.putValue(ident, type, ValueSymbol.Kind.value);
    symbol.setSlot(getSlot());
  }
  private void process(Binder[] binder)
  {
    int n = binder.length;
    Type[] type = new Type[n];
    for (int i=0; i<n; i++)
    {
      for (int j=0; j<i; j++)
      {
        if (binder[i].ident.string.equals(binder[j].ident.string))
        {
          typeError(binder[i], 
              "name already used at binding " + (j+1));
        }
      }
      type[i] = process(binder[i].exp);
    }
    for (int i=0; i<n; i++)
    {
      Identifier ident = process(binder[i].ident);
      ValueSymbol symbol = env.putValue(ident, type[i], ValueSymbol.Kind.value);
      symbol.setSlot(getSlot());
    }
  }
  
  // -------------------------------------------------------------------------
  //
  // PatternCommand
  //
  // -------------------------------------------------------------------------
  private void process(PatternCommand pcommand, RecursiveType type,
    Set<ValueSymbol> frame)
  {
    if (pcommand instanceof IdentifierPatternCommand)
      process((IdentifierPatternCommand)pcommand, type, frame);
    else if (pcommand instanceof ApplicationPatternCommand)
      process((ApplicationPatternCommand)pcommand, type, frame);
    else if (pcommand instanceof DefaultPatternCommand)
      process((DefaultPatternCommand)pcommand, type, frame);
    else
      typeError(pcommand, "unknown kind of pattern command");
  }
  private void process(IdentifierPatternCommand pcommand, RecursiveType type,
    Set<ValueSymbol> frame)
  {
    Identifier ident = process(pcommand.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol symbol = checker.getValue(type.ritem, ident);
    if (symbol == null)
      typeError(ident, "unknown value constructor in type " + type);
    checker.process(pcommand.command);
    frame.addAll(pcommand.command.getFrame());
  }
  private void process(ApplicationPatternCommand pcommand, RecursiveType type,
    Set<ValueSymbol> frame)
  {
    Identifier ident = process(pcommand.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(pcommand.params);
    int n = symbols.length;
    Type[] types = new Type[n];
    for (int i=0; i<n; i++) types[i] = symbols[i].type;
    FunctionSymbol symbol = getFunction(type.ritem, ident, types);
    if (symbol == null)
      typeError(ident, "unknown value constructor in type " + type);
    checker.process(pcommand.command);
    frame.addAll(pcommand.command.getFrame());
  }
  private void process(DefaultPatternCommand pcommand, RecursiveType type,
    Set<ValueSymbol> frame)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(pcommand.command);
    frame.addAll(pcommand.command.getFrame());
  }
  
  // -------------------------------------------------------------------------
  //
  // PatternExpression
  //
  // -------------------------------------------------------------------------
  private Type process(PatternExpression pexp, RecursiveType type)
  {
    if (pexp instanceof IdentifierPatternExp)
      return process((IdentifierPatternExp)pexp, type);
    else if (pexp instanceof ApplicationPatternExp)
      return process((ApplicationPatternExp)pexp, type);
    else if (pexp instanceof DefaultPatternExp)
      return process((DefaultPatternExp)pexp, type);
    else
      typeError(pexp, "unknown kind of pattern command");
    return null;
  }
  private Type process(IdentifierPatternExp pexp, RecursiveType type)
  {
    Identifier ident = process(pexp.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol symbol = checker.getValue(type.ritem, ident);
    if (symbol == null)
      typeError(ident, "unknown value constructor in type " + type);
    return checker.process(pexp.exp);
  }
  private Type process(ApplicationPatternExp pexp, RecursiveType type)
  {
    Identifier ident = process(pexp.ident);
    TypeChecker checker = new TypeChecker(this);
    ValueSymbol[] symbols = checker.process(pexp.params);
    int n = symbols.length;
    Type[] types = new Type[n];
    for (int i=0; i<n; i++) types[i] = symbols[i].type;
    FunctionSymbol symbol = getFunction(type.ritem, ident, types);
    if (symbol == null)
      typeError(ident, "unknown value constructor in type " + type);
    return checker.process(pexp.exp);
  }
  private Type process(DefaultPatternExp pexp, RecursiveType type)
  {
    TypeChecker checker = new TypeChecker(this);
    return checker.process(pexp.exp);
  }
  
  // -------------------------------------------------------------------------
  //
  // Parameter
  //
  // -------------------------------------------------------------------------
  private ValueSymbol[] process(Parameter[] param)
  {
    return process(param, true);
  }
  private ValueSymbol[] process(Parameter[] param, boolean slot)
  {
    int n = param.length;
    ValueSymbol[] symbols = new ValueSymbol[n];
    for (int i=0; i<n; i++) 
    {
      for (int j=0; j<i; j++)
      {
        if (param[i].ident.string.equals(param[j].ident.string))
        {
          typeError(param[i], 
              "name already used at position " + (j+1) + " of sequence");
        }
      }
      symbols[i] = process(param[i], slot);
    }
    return symbols;
  }
  private ValueSymbol process(Parameter param, boolean slot)
  {
    Identifier ident = process(param.ident);
    Type type = process(param.type);
    ValueSymbol symbol = env.putValue(ident, type, ValueSymbol.Kind.value);
    if (slot) symbol.setSlot(getSlot());
    return symbol;
  }
  
  // -------------------------------------------------------------------------
  //
  // ExpressionIdentifier
  //
  // -------------------------------------------------------------------------
  private Parameter[] process(ExpressionIdentifier[] eidents)
  {
    int n = eidents.length;
    Parameter[] params = new Parameter[n];
    for (int i=0; i<n; i++)
      params[i] = process(eidents[i]);
    return params;
  }
  private Parameter process(ExpressionIdentifier eident)
  {
    Identifier ident = process(eident.ident);
    Type type = process(eident.exp);
    Parameter param = new Parameter(ident, type);
    env.putValue(ident, type, ValueSymbol.Kind.value);
    return param;
  }
  
  // -------------------------------------------------------------------------
  //
  // RecursiveIdentifier
  //
  // -------------------------------------------------------------------------
  private void process(RecursiveIdentifier rident, Type type)
  {
    if (rident instanceof RecIdentifier)
      process((RecIdentifier)rident, type);
    else if (rident instanceof RecApplication)
      process((RecApplication)rident, type);
    else 
      typeError(rident, "unknown kind of recursive identifier");
  }
  private void process(RecIdentifier rident, Type type)
  {
    Identifier ident = process(rident.ident);
    env.putValue(ident, type, ValueSymbol.Kind.global); 
  }
  private void process(RecApplication rident, Type type)
  {
    Identifier ident = process(rident.ident);
    int n = rident.types.length;
    ValueSymbol[] params = new ValueSymbol[n];
    for (int i=0; i<n; i++)
      params[i] = new ValueSymbol(new AST.Identifier("dummy"), 
          process(rident.types[i]), ValueSymbol.Kind.value);
    env.putFunction(false, ident, params, type);
  }
  
  // -------------------------------------------------------------------------
  //
  // RecursiveTypeItem
  //
  // -------------------------------------------------------------------------
  private List<TypeSymbol> process(RecursiveTypeItem[] ritems, long depth)
  {
    // create dummy symbols with null type, populate environment
    List<TypeSymbol> symbols = new ArrayList<TypeSymbol>(ritems.length);
    for (RecursiveTypeItem ritem : ritems)
    {
      Identifier ident = process(ritem.ident);
      TypeSymbol symbol = env.putType(ident, null);
      symbols.add(symbol);
    }
    
    // fill type symbol by setting its type to recursive type 
    for (RecursiveTypeItem ritem : ritems)
    {
      Identifier ident = ritem.ident;
      TypeSymbol symbol = (TypeSymbol)ident.getSymbol();
      RecursiveType type = new RecursiveType(depth, ritem, symbols);
      symbol.setType(type);
    }
    
    // process type items in environment with dummy symbols for all types
    for (RecursiveTypeItem ritem : ritems)
      process(ritem, depth, symbols);
    
    // compute sizes of all types
    for (TypeSymbol symbol : symbols)
    {
      // processing one type annotates the others as well
      RecursiveType type0 = (RecursiveType)symbol.getType();
      recursiveSize(type0);
      break; 
    }
    return symbols;
  }
  private void process(RecursiveTypeItem ritem, long depth, List<TypeSymbol> symbols)
  {    
    // type constructors are added to local environment
    TypeChecker checker = new TypeChecker(this);
    Identifier ident = ritem.ident;
    TypeSymbol symbol = (TypeSymbol)ident.getSymbol();
    for (RecursiveIdentifier rident : ritem.ridents)
      checker.process(rident, symbol.getType());
  }
  
  // -------------------------------------------------------------------------
  //
  // Selector
  //
  // -------------------------------------------------------------------------
  private Type process(Type type, Selector[] sels)
  {
    for (Selector sel : sels)
    {
      if (sel instanceof MapSelector)
        type = process(type, (MapSelector)sel); 
      else if (sel instanceof TupleSelector)
        type = process(type, (TupleSelector)sel); 
      else if (sel instanceof RecordSelector)
        type = process(type, (RecordSelector)sel); 
      else
        typeError(sel, "unknown kind of selector");
    }
    return type;
  }
  private Type process(Type type, MapSelector sel)
  {
    if (!(type instanceof MapType))
      typeError(sel, "variable must have type Array/Map but has type " + type);
    MapType type0 = (MapType)type;
    Type type1 = process(sel.exp);
    if (!matchesWeak(type1, type0.type1))
      typeError(sel.exp, "expression must have type " + type0.type1 + 
          " but has type " + type1);
    return type0.type2;
  }
  private Type process(Type type, TupleSelector sel)
  {
    if (!(type instanceof TupleType))
      typeError(sel, "variable must have type Tuple but has type " + type);
    TupleType type0 = (TupleType)type;
    long index = process(sel.index);
    if (index < 1 || index > type0.types.length)
      typeError(sel.index, "must be from 1 to " + type0.types.length + " for type " + type0);
    return type0.types[(int)index-1];
  }
  private Type process(Type type, RecordSelector sel)
  {
    if (!(type instanceof RecordType))
      typeError(sel, "variable must have type Record but has type " + type);
    RecordType type0 = (RecordType)type;
    Identifier ident = process(sel.ident);
    ValueSymbol symbol0 = null;
    for (ValueSymbol s : type0.symbols)
    {
      if (equals(ident, s.ident)) { symbol0 = s; break; }
    }
    if (symbol0 == null)
      typeError(ident, "does not denote a selector in type " + type0);
    ident.setSymbol(symbol0);
    return symbol0.type;
  }
  
  // -------------------------------------------------------------------------
  //
  // Identifier
  //
  // -------------------------------------------------------------------------
  private Identifier process(Identifier ident)
  {
    return ident;
  }
  
  // -------------------------------------------------------------------------
  //
  // Decimal
  //
  // -------------------------------------------------------------------------
  private long process(Decimal decimal)
  {
    try
    {
      int ivalue = Integer.parseInt(decimal.value);
      decimal.setInteger(ivalue);
      return ivalue;
    }
    catch(NumberFormatException e) 
    {
      typeError(decimal, "does not denote an integer literal");
    } 
    return -1;
  }
  
  // -------------------------------------------------------------------------
  //
  // Identifier handling
  //
  // -------------------------------------------------------------------------
  private final static boolean equals(Identifier ident1, Identifier ident2)
  {
    return ident1.string.equals(ident2.string);
  }
  
  // -------------------------------------------------------------------------
  //
  // Expression evaluation
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Process and evaluate expression to an integer
   * @param exp the expression
   * @return the integer value of the expression (raises an exception, if none)
   **************************************************************************/
  private long evalInt(Expression exp)
  {
    Long value = tryEvalInt(exp);
    if (value == null)
      typeError(exp, "does not evaluate to integer");
    return value;
  }
  
  /***************************************************************************
   * Process and evaluate expression to an integer
   * @param exp the expression
   * @return the integer value of the expression (null, if none)
   **************************************************************************/
  private Long tryEvalInt(Expression exp)
  {
    // check type
    Type type = process(exp);
    if (!(type instanceof IntType))
      typeError(exp, "is not of type Int");
    IntType type0 = (IntType)type;
    
    // the type denotes its value
    if (type0.ivalue1.equals(type0.ivalue2)) return type0.ivalue1;
    
    // for the moment, only identifiers are handled
    if (exp instanceof IdentifierExp)
    {
      IdentifierExp exp0 = (IdentifierExp)exp;
      ValueSymbol symbol = env.getValue(exp0.ident);
      Value value = symbol.getValue();
      if (value instanceof Value.Int) 
        return Long.valueOf(((Value.Int)value).getValue());
    }
    return null;
  }
  
  // -------------------------------------------------------------------------
  //
  // Type checking expressions with expectation on result type.
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Get non-subtype basetype.
   * @param type a type.
   * @return the type after stripping of the subtypes.
   **************************************************************************/
  private Type getBaseType(Type type)
  {
    while (type instanceof SubType)
    {
      SubType type0 = (SubType)type;
      type = type0.type;
    }
    return type;
  }
  
  /***************************************************************************
   * Process expression with Integer type.
   * @param e the expression.
   * @return the Integer type (raises an exception, if none)
   **************************************************************************/
  private IntType processInt(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof IntType))
      typeError(e, "expression must have type Int but has type " + type);
    return (IntType)type;
  }
  
  /***************************************************************************
   * Process expression with boolean type.
   * @param e the expression.
   * @return the boolean type (raises an exception, if none)
   **************************************************************************/
  private BoolType processBool(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof BoolType))
      typeError(e, "expression must have type Bool but has type " + type);
    return (BoolType)type;
  }
  
  /***************************************************************************
   * Process expression with recursive type.
   * @param e the expression.
   * @return the recursive type (raises an exception, if none)
   **************************************************************************/
  private RecursiveType processRecursive(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof RecursiveType))
      typeError(e, "expression must have type Recursive but has type " + type);
    return (RecursiveType)type;
  }
  
  /***************************************************************************
   * Process expression with map type.
   * @param e the expression.
   * @return the map type (raises an exception, if none)
   **************************************************************************/
  private MapType processMap(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof MapType))
      typeError(e, "expression must have type Array/Map but has type " + type);
    return (MapType)type;
  }
  
  /***************************************************************************
   * Process expression with set type.
   * @param e the expression.
   * @return the set type (raises an exception, if none)
   **************************************************************************/
  private SetType processSet(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof SetType))
      typeError(e, "expression must have type Set but has type " + type);
    return (SetType)type;
  }
  
  /***************************************************************************
   * Process expression with set of set type.
   * @param e the expression.
   * @return the set of set type (raises an exception, if none)
   **************************************************************************/
  private SetType processSetOfSet(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof SetType))
      typeError(e, "expression must have type Set but has type " + type);
    Type type0 = ((SetType)type).type;
    if (!(type0 instanceof SetType))
      typeError(e, "expression must have type Set(Set) but has type " + type);
    return (SetType)type;
  }
  
  /***************************************************************************
   * Process expression with tuple type.
   * @param e the expression.
   * @return the array type (raises an exception, if none)
   **************************************************************************/
  private TupleType processTuple(Expression e)
  {
    Type type = process(e);
    if (!(type instanceof TupleType))
      typeError(e, "expression must have type Tuple but has type " + type);
    return (TupleType)type;
  }
  
  // -------------------------------------------------------------------------
  //
  // Type handling
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Create processed version of integer type
   * @param from its lower bound
   * @param to its upper bound
   * @return the processed version of the integer type.
   **************************************************************************/
  private IntType intType(long from, long to)
  {
    return (IntType)processType(new IntType(from, to));
  }
  
  /***************************************************************************
   * Return the combination (least upper bound or greatest lower bound)
   * of two canonical types.
   * @param type1 the first type.
   * @param type2 the second type.
   * @param union true if lub is requested (otherwise glb).
   * @return the combination (null, if none)
   **************************************************************************/
  private Type combine(Type type1, Type type2, boolean union)
  {
    // ignore subtypes
    if (type1 instanceof SubType)
    {
      SubType type0 = (SubType)type1;
      return combine(type0.type, type2, union);
    }
    if (type2 instanceof SubType)
    {
      SubType type0 = (SubType)type2;
      return combine(type1, type0.type, union);
    }  
    // recursive types are only captured by identity
    if (matchesStrong(type1, type2)) return union ? type2 : type1;
    if (matchesStrong(type2, type1)) return union ? type1 : type2;
    if (type1 instanceof MapType)
    {
      if (!(type2 instanceof MapType)) return null;
      MapType type10 = (MapType)type1;
      MapType type20 = (MapType)type2;
      Type domain = combine(type10.type1, type20.type1, union);
      if (domain == null) return null;
      Type range = combine(type10.type2, type20.type2, union);
      if (range == null) return null;
      return processType(new MapType(domain, range));
    }
    if (type1 instanceof TupleType)
    {
      if (!(type2 instanceof TupleType)) return null;
      TupleType type10 = (TupleType)type1;
      TupleType type20 = (TupleType)type2;
      int n = type10.types.length;
      if (n != type20.types.length) return null;
      List<Type> types = new ArrayList<Type>(n);
      for (int i=0; i<n; i++)
      {
        Type type = combine(type10.types[i], type20.types[i], union);
        if (type == null) return null;
        types.add(type);
      }
      return processType(new TupleType(types));
    }
    if (type1 instanceof SetType)
    {
      if (!(type2 instanceof SetType)) return null;
      SetType type10 = (SetType)type1;
      SetType type20 = (SetType)type2;
      Type type = combine(type10.type, type20.type, union);
      if (type == null) return null;
      return processType(new SetType(type));
    }
    if (type1 instanceof IntType)
    {
      if (!(type2 instanceof IntType)) return null;
      IntType type10 = (IntType)type1;
      IntType type20 = (IntType)type2;
      long ivalue1 = Math.min(type10.ivalue1, type20.ivalue1);
      long ivalue2 = Math.max(type10.ivalue2, type20.ivalue2);
      return intType(ivalue1, ivalue2);
    }
    if (type1 instanceof RecordType)
    {
      if (!(type2 instanceof RecordType)) return null;
      RecordType type10 = (RecordType)type1;
      RecordType type20 = (RecordType)type2;
      int n = type10.symbols.length;
      if (n != type20.symbols.length) return null;
      List<ValueSymbol> symbols = new ArrayList<ValueSymbol>(n);
      for (int i=0; i<n; i++)
      {
        ValueSymbol symbol1 = type10.symbols[i];
        ValueSymbol symbol2 = type20.symbols[i];
        if (!equals(symbol1.ident, symbol2.ident)) return null;
        Type type = combine(symbol1.type, symbol2.type, union);
        if (type == null) return null;
        Identifier ident = new Identifier(symbol1.ident.string);
        ValueSymbol symbol = new ValueSymbol(ident, type, ValueSymbol.Kind.value);
        symbols.add(symbol);
      }
      return processType(new RecordType(symbols, true));
    }
    return null;
  }
  private Type union(Type type1, Type type2)
  { return combine(type1, type2, true); }
  private Type intersect(Type type1, Type type2)
  { return combine(type1, type2, false); }
  
  /***************************************************************************
   * Return true if one canonical type matches another type
   * @param etype the potentially more special type
   * @param type the potentially more general type
   * @param strong true if size arithmetic information is to be considered in matching
   * @return true iff etype "matches" (is subset of) type
   **************************************************************************/
  private static boolean matches(Type etype, Type type, boolean strong)
  {
    if (etype == type) return true;
    if (etype instanceof SubType)
    {
      // strip subtypes from expression type
      SubType etype0 = (SubType)etype;
      return matches(etype0.type, type, strong);
    }
    if ((!strong) && (type instanceof SubType))
    {
      // only strip subtypes from declaration type, if not strong matching
      SubType type0 = (SubType)type;
      return matches(etype, type0.type, strong);
    }
    if (etype instanceof UnitType) return type instanceof UnitType;
    if (etype instanceof BoolType) return type instanceof BoolType;
    if (etype instanceof MapType)
    {
      if (!(type instanceof MapType)) return false;
      MapType etype0 = (MapType)etype;
      MapType type0  = (MapType)type;
      // covariant range matching but contravariant domain matching
      return matches(etype0.type2, type0.type2, strong)
          && matches(type0.type1, etype0.type1, strong);
    }
    if (etype instanceof TupleType)
    {
      if (!(type instanceof TupleType)) return false;
      TupleType etype0 = (TupleType)etype;
      TupleType type0  = (TupleType)type;
      int n = etype0.types.length;
      if (n != type0.types.length) return false;
      for (int i=0; i<n; i++)
      {
        if (!matches(etype0.types[i], type0.types[i], strong)) 
          return false;
      }
      return true;
    }
    if (etype instanceof SetType)
    {
      if (!(type instanceof SetType)) return false;
      SetType etype0 = (SetType)etype;
      SetType type0 = (SetType)type;
      return matches(etype0.type, type0.type, strong);
    }
    if (etype instanceof IntType)
    {
      if (!(type instanceof IntType)) return false;
      if (!strong) return true;
      IntType etype0 = (IntType)etype;
      IntType type0 = (IntType)type;
      return etype0.ivalue1 >= type0.ivalue1 
          && etype0.ivalue2 <= type0.ivalue2;
    }
    if (etype instanceof RecordType)
    {
      if (!(type instanceof RecordType)) return false;
      RecordType etype0 = (RecordType)etype;
      RecordType type0 = (RecordType)type;
      int n = etype0.symbols.length;
      if (n != type0.symbols.length) return false;
      for (int i=0; i<n; i++)
      {
        ValueSymbol symbol1 = etype0.symbols[i];
        ValueSymbol symbol2 = type0.symbols[i];
        if (!equals(symbol1.ident, symbol2.ident)) return false;
        if (!matches(symbol1.type, symbol2.type, strong)) return false;
      }
      return true;
    }
    if (etype instanceof RecursiveType)
    {
      if (!(type instanceof RecursiveType)) return false;
      RecursiveType etype0 = (RecursiveType)etype;
      RecursiveType type0 = (RecursiveType)type;
      if (etype0.ritem.ident.getSymbol() != type0.ritem.ident.getSymbol()) return false;
      return !strong || etype0.ivalue <= type0.ivalue;
    }
    return false;
  }
  public static boolean matchesWeak(Type etype, Type type)
  { return matches(etype, type, false); }
  public static boolean matchesStrong(Type etype, Type type)
  { return matches(etype, type, true); }
  
  /***************************************************************************
   * Return true if one typ sequence matches another type sequence
   * @param etypes the potentially more special types
   * @param types the potentially more general types
   * @param strong true if size arithmetic information is to be considered in matching
   * @return true iff etypes equals or is more special than types
   **************************************************************************/
  private boolean matches(Type[] etypes, Type[] types, boolean strong)
  {
    int n = etypes.length;
    if (n != types.length) return false;
    for (int i=0; i<n; i++)
    {
      if (!matches(etypes[i], types[i], strong)) return false;
    }
    return true;
  }
  public boolean matchesWeak(Type[] etypes, Type[] types)
  { return matches(etypes, types, false); }
  public boolean matchesStrong(Type[] etypes, Type[] types)
  { return matches(etypes, types, true); }
  
  // --------------------------------------------------------------------------
  //
  // Alternative entry points
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Type check expression in environment set up by parameters.
   * @param param the parameters.
   * @param exp the expression
   * @return its type.
   **************************************************************************/
  public Type process(Parameter[] param, Expression exp)
  {
    TypeChecker checker = new TypeChecker(this);
    checker.process(param);
    return checker.process(exp); 
  }
  
  // --------------------------------------------------------------------------
  //
  // Auxiliaries
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Returns the function associated to the given identifier with the given
   * argument types (sets the symbol of the identifier as a side effect)
   * @param ident the identifier
   * @param types the argument types (must have canonical versions)
   * @return the associated function (null, if none)
   ***************************************************************************/
  private FunctionSymbol getFunction(AST.Identifier ident, AST.Type[] types)
  {
    List<FunctionSymbol> symbols = env.getFunctions(ident);
    if (symbols == null) return null;
    for (FunctionSymbol s : symbols)
    {
      if (matchesWeak(types, s.types)) 
      {
        ident.setSymbol(s);
        return s;
      }
    }
    return null;
  }
  
  /****************************************************************************
   * Get "old" version of identifier.
   * @param ident the identifier.
   * @return its "old" version
   ***************************************************************************/
  public static Identifier oldIdentifier(Identifier ident)
  {
    return new Identifier(oldPrefix + ident.string);
  }
  
  /****************************************************************************
   * Get number of combinations of values denoted by quantified variables.
   * @param var the quantified variables
   * @param symbols the symbols of the variables.
   * @return the number of combination of values for the variables.
   ***************************************************************************/
  private long combinationNumber(QuantifiedVariable var, ValueSymbol[] symbols)
  {
    try
    {
      long result = 1;
      for (ValueSymbol s : symbols)
      {
        result = Math.multiplyExact(result, s.type.getSize());
      }
      return result;
    }
    catch(ArithmeticException e)
    {
      typeError(var, "number of variable values is too large");
      return -1;
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Recursive Types
  //
  // --------------------------------------------------------------------------
  
  /***************************************************************************
   * Get denoted item from name of recursive type
   * @param Ident the name of the type
   * @return the item (raises an exception, if none)
   ***************************************************************************/
  private RecursiveTypeItem getItem(Identifier ident)
  {
    TypeSymbol tsymbol = env.getType(ident);
    if (tsymbol == null)
      typeError(ident, "unknown type");
    Type type = tsymbol.getType();
    if (!(type instanceof RecursiveType))
      typeError(ident, "must be type Recursive but is type " + type);
    RecursiveType type0 = (RecursiveType)type;
    return getItem(type0, ident); // can't be null
  }
  
  /***************************************************************************
   * Get denoted item from recursive type
   * @param type the recursive type
   * @param ident the name of the item
   * @return the item (null, if none)
   ***************************************************************************/
  private RecursiveTypeItem getItem(RecursiveType type, AST.Identifier ident)
  {
    for (TypeSymbol symbol : type.symbols)
    {
      if (equals(ident, symbol.ident)) 
      {
        RecursiveType t = (RecursiveType)symbol.getType();
        return t.ritem;
      }
    }
    return null;
  }
  
  /***************************************************************************
   * Get constructor for application denoted by name and argument types.
   * @param ritem the item for the recursive type.
   * @param ident the name of the function.
   * @param etypes the types of the arguments.
   * @return the constructor (null, if none)
   ***************************************************************************/
  private FunctionSymbol getFunction(RecursiveTypeItem ritem, 
    Identifier ident, Type[] etypes)
  {
    for (RecursiveIdentifier rident : ritem.ridents)
    {      
      if (!equals(ident, rident.ident)) continue;
      if (!(rident instanceof RecApplication)) continue;
      RecApplication rident0 = (RecApplication)rident; 
      FunctionSymbol fun = (FunctionSymbol)rident0.ident.getSymbol();
      if (matchesWeak(etypes, fun.types)) 
      {
        ident.setSymbol(fun);
        return fun;
      }
    }
    return null;
  }
  
  /***************************************************************************
   * Get constructor for denoted name without arguments.
   * @param ritem the item for the recursive type.
   * @param ident the name of the function.
   * @return the constructor (null, if none)
   ***************************************************************************/
  private ValueSymbol getValue(RecursiveTypeItem ritem, Identifier ident)
  {
    for (RecursiveIdentifier rident : ritem.ridents)
    {      
      if (!equals(ident, rident.ident)) continue;
      if (!(rident instanceof RecIdentifier)) continue;
      ValueSymbol value = (ValueSymbol)rident.ident.getSymbol();
      ident.setSymbol(value);
      return value;
    }
    return null;
  }
  
  // --------------------------------------------------------------------------
  //
  // Record Types
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Get value of record type denoted by identifier.
   * @param type the record type.
   * @param ident the identifier.
   * @return the value (null, if none).
   ***************************************************************************/
  private ValueSymbol getSelector(RecordType type, Identifier ident)
  {
    for (ValueSymbol s : type.symbols)
    {
      Identifier sident = s.ident;
      if (equals(ident, sident))
      {
        ident.setSymbol(s);
        return s;
      }
    }
    return null;
  }
  
  // --------------------------------------------------------------------------
  //
  // Tuple Types
  //
  // --------------------------------------------------------------------------
  
  /****************************************************************************
   * Get component type of tuple type denoted by index.
   * @param type the tuple type.
   * @param index the index.
   * @return the component type (raises an exception, if none)
   ***************************************************************************/
  private Type getType(TupleType type, Decimal index)
  {
    long i = process(index);
    if (i == 0)
      typeError(index, "index must be positive");
    if (i > type.types.length)
      typeError(index, "index must be at most " + type.types.length +
          " for type " + type);
    return type.types[(int)i-1];
  }
  
  // -------------------------------------------------------------------------
  //
  // Type size computation
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Compute the power of two values
   * @param a the base
   * @param b the exponent
   * @return the power (INFINITY on overflow)
   **************************************************************************/
  public static long powerSize(long a, long b)
  {
    if (a == Type.INFINITY || b == Type.INFINITY) return Type.INFINITY;
    try
    {
      return Arith.power(a, b);
    }
    catch(ArithmeticException e)
    { 
      return Type.INFINITY;
    }
  }
  
  /***************************************************************************
   * Get number of elements in interval [ivalue1,ivalue2]
   * @param ivalue1 the lower bound of the interval (inclusive)
   * @param ivalue2 the upper bound of the interval (exclusive)
   * @return the number of elements (Type.INFINITY on overflow)
   **************************************************************************/
  public static long intSize(long ivalue1, long ivalue2)
  {
    if (ivalue1 == Type.INFINITY || ivalue2 == Type.INFINITY) return Type.INFINITY;
    try
    {
      long result = Math.subtractExact(ivalue2, ivalue1);
      result = Math.addExact(result, 1);
      return Math.max(result, 0);
    }
    catch(ArithmeticException e) { return Type.INFINITY; }
  }
  
  /***************************************************************************
   * Get number of elements of tuple type
   * @param types the canonical component types of the tuple type
   * @return the number of elements (Type.INFINITY on overflow)
   **************************************************************************/
  public static final long tupleSize(Type[] types)
  {
    try
    {
      long result = 1;
      for (Type type : types)
        result = Math.multiplyExact(result, type.getSize());
      return result;
    }
    catch(ArithmeticException e) { return Type.INFINITY; }
  }
  
  /***************************************************************************
   * Get number of elements of record type
   * @param symbols the symbols with the canonical types of the record components
   * @return the number of elements (Type.INFINITY on overflow)
   **************************************************************************/
  public static final long recordSize(ValueSymbol[] symbols)
  {
    try
    {
      long result = 1;
      for (ValueSymbol symbol : symbols)
        result = Math.multiplyExact(result, symbol.type.getSize());
      return result;
    }
    catch(ArithmeticException e) { return Type.INFINITY; }
  }
  
  /***************************************************************************
   * Get number of elements of subtype
   * @param type the base type
   * @param fun the subtype predicate
   * @return the number of elements (Type.INFINITY on overflow)
   **************************************************************************/
  public static final long subSize(Type type, FunctionSymbol pred)
  {
    Seq<Value> values = Values.getValueSeq(type);
    FunSem.Single fun = (FunSem.Single)pred.getValue();
    long size = 0;
    while (true)
    {
      Seq.Next<Value> next = values.get();
      if (next == null) break;
      Value.Bool result = (Value.Bool)fun.apply(new Argument(new Value[] { next.head }));
      if (result.getValue()) size++;
      values = next.tail;
    }
    return size;
  }
  
  /***************************************************************************
   * Get number of elements for recursive item.
   * @param ridents the identifiers of the item
   * @return the number of elements (Type.INFINITY on overflow)
   **************************************************************************/
  private static final long recIdentSize(RecursiveIdentifier[] ridents)
  {
    try
    {
      long result = 0;
      for (RecursiveIdentifier rident : ridents)
      {
        if (rident instanceof RecIdentifier)
        {
          result = Math.addExact(result, 1);
        }
        else if (rident instanceof RecApplication)
        {
          RecApplication rident0 = (RecApplication)rident;
          FunctionSymbol fsymbol = (FunctionSymbol)rident0.ident.getSymbol();
          long r = 1;
          for (Type t : fsymbol.types)
          {
            r = Math.multiplyExact(r, t.getSize());
          }
          result = Math.addExact(result, r);
        }
        else typeError(rident, "unknown recursive identifier");
        
      }
      return result;
    }
    catch(ArithmeticException e) { return Type.INFINITY; }
  }
  
  /***************************************************************************
   * Set number of elements of recursive type
   * @param type the recursive types annotated with its size
   **************************************************************************/
  public static final void recursiveSize(RecursiveType type)
  {
    long depth = type.ivalue;
    TypeSymbol[] symbols = type.symbols;
    int n = symbols.length;
    long[] size = new long[n];
    for (int j=0; j<n; j++) size[j] = 0;
    int i = 0;
    while (true)
    {
      for (int j=0; j<n; j++)
      {
        TypeSymbol s = symbols[j];
        Type t = s.getType();
        long r = size[j];
        t.setSize(r);
      }
      if (i == depth+1) return;
      i++;
      for (int j=0; j<n; j++) 
      {
        RecursiveType t = (RecursiveType)symbols[j].getType();
        size[j] = recIdentSize(t.ritem.ridents);
      }
    }
  }
  
  /****************************************************************************
   * Get number of combination of variable values.
   * @param vars the variables.
   * @return the number of combinations (Type.INFINITY on overflow)
   ***************************************************************************/
  private static final long varSize(ValueSymbol[] vars)
  {
    try
    {
      long number = 1;
      for (ValueSymbol v : vars)
      {
        long n = v.type.getSize();
        number = Math.multiplyExact(number, n);
      }
      return number;
    }
    catch(ArithmeticException e) { return Type.INFINITY; }
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------