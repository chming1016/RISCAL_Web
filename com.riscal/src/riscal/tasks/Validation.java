// ---------------------------------------------------------------------------
// Validation.java
// Generate tasks to validate the specification of an operation.
// $Id: Validation.java,v 1.44 2018/06/06 15:44:30 schreine Exp $
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
import riscal.syntax.AST.Declaration.*;
import riscal.syntax.AST.Expression.*;
import riscal.syntax.AST.QuantifiedVariableCore.*;
import riscal.syntax.AST.Type.BoolType;
import riscal.syntax.AST.Type.IntType;
import riscal.syntax.AST.Type.MapType;
import riscal.syntax.AST.Type.RecordType;
import riscal.syntax.AST.Type.RecursiveType;
import riscal.syntax.AST.Type.SetType;
import riscal.syntax.AST.Type.SubType;
import riscal.syntax.AST.Type.TupleType;
import riscal.syntax.AST.Type.UnitType;
import riscal.types.*;
import riscal.types.Environment.Symbol.*;
import riscal.Main;
import riscal.semantics.Translator;

import java.util.*;

public class Validation
{
  private Specification spec;    // the specification being processed
  private TypeChecker checker;   // the type checker for processing new formulas
  private Translator translator; // the translator to executable representations
  private ASTCloner cloner;      // the cloner of syntax phrases
  private Recursion.CallMap callers; // the call map of the specifcation

  // the name given to a function measure (possible suffixed with 1..(n-1)
  public final static String funMeasureName = "_fmeasure";
  
  /***************************************************************************
   * Add all validation tasks to specification.
   * @param spec the specification
   * @param checker the checker used for processing new formulas.
   * @param translator the translator to executable representations.
   **************************************************************************/
  public static void addTasks(Specification spec,
    TypeChecker checker, Translator translator)
  {
    Validation validation = new Validation(spec, checker, translator);
    validation.addTasks();
  }

  /***************************************************************************
   * Create validation task generator.
   * @param spec the specification
   * @param checker the checker used for processing new formulas.
   * @param translator the translator to executable representations.
   *************************************************************************/
  private Validation(Specification spec,
    TypeChecker checker, Translator translator)
  {
    this.spec = spec;
    this.checker = checker;
    this.translator = translator;
    this.cloner = new ASTCloner();
    this.callers = Recursion.analyze(spec);
  }

  /****************************************************************************
   * A condition giving rise to a task.
   ***************************************************************************/
  public static final class Condition
  {
    // the kind of condition
    // - the precondition generated from a type constraint
    // - the precondition generated from a function/operation application
    // - the precondition generated from a selection
    // - the precondition generated from an update value
    // - the precondition generated from an assertion
    // - the precondition generated from a choice
    // - the precondition generated from an assignment
    // - the condition that a loop invariant is initially satisfied
    // - the condition that a loop invariant is maintained
    // - the condition that a loop termination measure is/remains non-negative
    // - the condition that a loop/function termination measure is decreased
    // - the condition that the result is correct
    public static enum Kind { Constraint, Application, Select, Update, Assertion,
      Choice, Assignment, InvariantInitial, InvariantPreserved,
      LoopMeasureNonNegative, LoopMeasureDecreased, FunctionMeasureDecreased,
      ResultCorrect
      };
    
    public final Kind kind;      // the kind of condition
    public final AST phrase;     // the phrase giving rise to the condition
    public final Expression exp; // the condition expression
    public final SourcePosition pos; // the source code position
    public final List<SourcePosition> path; // a path of positions
    
    // a new condition with the given condition expression
    public Condition(Kind kind, AST phrase, Expression exp)
    {
      this(kind, phrase, exp, phrase.getPosition(), new ArrayList<SourcePosition>());
    }
    
    // a new condition with the stated source code position
    public Condition(Kind kind, AST phrase, Expression exp, SourcePosition pos,
      List<SourcePosition>path)
    {
      this.kind = kind;
      this.phrase = phrase;
      this.exp = exp;
      this.pos = pos;
      this.path = new ArrayList<SourcePosition>(path);
    }
    
    // a copy of this condition with a new condition expression
    public Condition clone(Expression exp)
    {
      return new Condition(kind, phrase, exp, pos, path);
    }
    
    // add position to path
    public void addPosition(SourcePosition pos)
    {
      path.add(pos);
    }
  }
  
  // the list of value names processed so far
  private List<String> valnames = new ArrayList<String>();
  
  /***************************************************************************
   * Add all validation tasks to specification.
   **************************************************************************/
  private void addTasks()
  {
    int i = 0;
    for (Declaration d : spec.declarations)
    {
      FunctionSymbol fun = null;
      ValueSymbol val = null;
      if (d instanceof FunctionDefinition)
        fun = (FunctionSymbol)((FunctionDefinition) d).ident.getSymbol();
      else if (d instanceof PredicateDefinition)
        fun = (FunctionSymbol)((PredicateDefinition) d).ident.getSymbol();
      else if (d instanceof ProcedureDefinition)
        fun = (FunctionSymbol)((ProcedureDefinition) d).ident.getSymbol();
      else if (d instanceof TheoremParamDefinition)
        fun = (FunctionSymbol)((TheoremParamDefinition) d).ident.getSymbol();
      else if (d instanceof ValueDeclaration)
        val = (ValueSymbol)((ValueDeclaration) d).ident.getSymbol();
      else if (d instanceof ValueDefinition)
        val = (ValueSymbol)((ValueDefinition) d).ident.getSymbol(); 
      else if (d instanceof PredicateValueDefinition)
        val = (ValueSymbol)((PredicateValueDefinition) d).ident.getSymbol(); 
      else if (d instanceof TheoremDefinition)
        val = (ValueSymbol)((TheoremDefinition) d).ident.getSymbol(); 
      if (fun != null)
      {
        addTasks(fun, d, i);
        i++;
      }
      else if (val != null) 
        valnames.add(val.ident.string);
    }
  }

  /***************************************************************************
   * Add all tasks related to function.
   * @param fun the symbol denoting the function.
   * @param decl the declaration from which the function was derived.
   * @param counter a unique counter for the declaration
   *************************************************************************/
  private void addTasks(FunctionSymbol fun, Declaration decl, int counter)
  {
    String description = decl.toString();   
    description = description.substring(0, description.length()-1);
    TaskFolder folder = new TaskFolder(null, Main.shortName(fun), decl.toStringCore());
    fun.setTaskFolder(folder);
    new CheckTask(folder, "Execute operation", 
        "Check operation for all applicable inputs", fun, decl);
    OpInfo info = OpInfo.getInfo(decl);
    TaskFolder sfolder = new TaskFolder(folder, "Validate specification", 
        "Make sure the contract of the operation is meaningful");
    addValidationTasks(sfolder, info, counter);
    TaskFolder spfolder = new TaskFolder(folder, "Verify specification preconditions", 
        "Make sure all operation applications in specification are legal");
    TaskFolder cfolder = new TaskFolder(folder, "Verify correctness of result", 
        "Make sure the result of the operation satisfies the contract");
    TaskFolder lfolder = new TaskFolder(folder, "Verify iteration and recursion", 
          "Ensure the correctness of loop invariants and termination measures");
    TaskFolder ipfolder = new TaskFolder(folder, "Verify implementation preconditions", 
        "Make sure all operation applications in implementation are legal");
    List<FunctionSpec> pre = info.pre;
    if (info.commands != null)
      addProcedureTasks(info, pre, cfolder, lfolder, ipfolder, counter);
    else
    {
      List<Binder> binders = getFunctionMeasureBinders(info.decTerms);
      addMeasureTasks(info, pre, lfolder, binders, counter);
      addPreconditions(counter + "_" + "PreOp",
          ipfolder, lfolder,
          info.symbol, info.param, pre, info.decTerms, info.bodyexp);
      addCorrectnessTasks(cfolder, info, decl instanceof TheoremParamDefinition,
          counter);
    }
    addSpecificationTasks(info, pre, spfolder, lfolder, counter);
  }
  
  /***************************************************************************
   * Create, optimize, and process theorem
   * @param multiple does theorem have non-deterministic semantics?
   * @param ident its identifier
   * @param params its parameters
   * @param spec its specification
   * @param body its body
   * @return the (optimized, processed and translated) theorem 
   *         (null, if something went wrong)
   **************************************************************************/
  private TheoremParamDefinition createTheorem(Multiple multiple,
    Identifier ident, List<Parameter> params, List<FunctionSpec> spec,
    Expression formula)
  {
    formula = Quantifiers.minimize(formula);
    // adequate minimization of choices requires much more work
    // formula = Choices.minimizeTheorem(formula, valnames);
    TheoremParamDefinition def = new TheoremParamDefinition(
        multiple, ident, params, spec, formula);
    def = (TheoremParamDefinition)cloner.visit(def);
    checker.process(def);
    if (checker.hasErrors()) return null;
    translator.process(def);
    return def;
  }
  
  /***************************************************************************
   * Add to folder all tasks related to the validation of a function contract.
   * @param folder the folder to which to add the tasks
   * @param info the information about the function 
   * @param counter a unique counter for the declaration
   **************************************************************************/
  private void addValidationTasks(TaskFolder folder, OpInfo info, int counter)
  {
    if (info.preFormula == null)
      new NoTask(folder, "No precondition", 
          "Specification does not contain a 'requires' clause");
    if (info.postFormula == null)
      new NoTask(folder, "No postcondition", 
          "Specification does not contain an 'ensures' clause");

    // implicitly defined function
    if (info.postFormula != null)
    {
      Identifier ident = new Identifier("_" + info.symbol.ident.string + "_" +
          counter + "_Spec");
      Identifier result = new Identifier(TypeChecker.resultName);
      QuantifiedVariableCore qvc = 
          new IdentifierTypeQuantifiedVar(result, info.type);
      List<QuantifiedVariableCore> qvl = new ArrayList<QuantifiedVariableCore>();
      qvl.add(qvc);
      FunctionDefinition def = new FunctionDefinition(
          new Multiple(false), ident,
          Arrays.asList(info.param),
          info.type, info.pre, 
          new ChooseExp(new QuantifiedVariable(qvl, info.postFormula)));
      def = (FunctionDefinition)cloner.visit(def);
      checker.process(def);
      if (!checker.hasErrors()) 
      {
        translator.process(def);
        new CheckTask(folder, "Execute specification", 
            "Compute legal outputs for all applicable inputs", 
            (FunctionSymbol)def.ident.getSymbol(), def);
      }
    }

    // precondition is satisfiable but not trivial
    if (info.preFormula != null)
    {
      Identifier ident = new Identifier("_" + info.symbol.ident.string + "_" +
          counter + "_PreSat");
      List<QuantifiedVariableCore> qvl = new ArrayList<QuantifiedVariableCore>();
      for (Parameter p : info.param)
      {
        QuantifiedVariableCore qvc = 
            new IdentifierTypeQuantifiedVar(p.ident, p.type);
        qvl.add(qvc);
      }
      TheoremParamDefinition def = createTheorem(
          new Multiple(false), ident,
          new ArrayList<Parameter>(),
          new ArrayList<FunctionSpec>(), 
          new ExistsExp(new QuantifiedVariable(qvl, null), info.preFormula));
      if (def != null) 
        new CheckTask(folder, "Is precondition satisfiable?", 
            "Is precondition true for some input?", 
            (FunctionSymbol)def.ident.getSymbol(), def);
      Identifier ident2 = new Identifier("_" + info.symbol.ident.string + "_" +
            counter + "_PreNotTrivial");
      TheoremParamDefinition def2 = createTheorem(
          new Multiple(false), ident2,
          new ArrayList<Parameter>(),
          new ArrayList<FunctionSpec>(), 
          new ExistsExp(new QuantifiedVariable(qvl, null), new NotExp(info.preFormula)));
      if (def2 != null) 
        new CheckTask(folder, "Is precondition not trivial?", 
            "Is precondition false for some input?", 
            (FunctionSymbol)def2.ident.getSymbol(), def2);
    }

    // postcondition is satisfiable but not trivial (for some/all inputs)
    if (info.postFormula != null)
    {
      Identifier ident = new Identifier("_" + info.symbol.ident.string + "_" +
          counter + "_PostSat");
      Identifier result = new Identifier(TypeChecker.resultName);
      QuantifiedVariableCore qvc = 
          new IdentifierTypeQuantifiedVar(result, info.type);
      List<QuantifiedVariableCore> qvl = new ArrayList<QuantifiedVariableCore>();
      qvl.add(qvc);
      TheoremParamDefinition def = createTheorem(
          new Multiple(false), ident,
          Arrays.asList(info.param),
          info.pre, 
          new ExistsExp(new QuantifiedVariable(qvl, null), info.postFormula));
      if (def != null) 
        new CheckTask(folder, "Is postcondition always satisfiable?", 
            "Is postcondition true for every input and some output?", 
            (FunctionSymbol)def.ident.getSymbol(), def);
      Identifier ident2 = new Identifier("_" + info.symbol.ident.string + "_" +
            counter + "_PostNotTrivialAll");
      TheoremParamDefinition def2 = createTheorem(
          new Multiple(false), ident2,
          Arrays.asList(info.param),
          info.pre, 
          new ExistsExp(new QuantifiedVariable(qvl, null), new NotExp(info.postFormula)));
      if (def2 != null) 
        new CheckTask(folder, "Is postcondition always not trivial?", 
            "Is postcondition false for every input and some output?", 
            (FunctionSymbol)def2.ident.getSymbol(), def2);
      Identifier ident3 = new Identifier("_" + info.symbol.ident.string + "_" +
            counter + "_PostNotTrivialSome");
      List<QuantifiedVariableCore> qvl0 = new ArrayList<QuantifiedVariableCore>();
      for (Parameter p : info.param)
      {
        QuantifiedVariableCore qvc0 = 
            new IdentifierTypeQuantifiedVar(p.ident, p.type);
        qvl0.add(qvc0);
      }
      TheoremParamDefinition def3 = createTheorem(
          new Multiple(false), ident3,
          new ArrayList<Parameter>(),
          new ArrayList<FunctionSpec>(), 
          new ExistsExp(new QuantifiedVariable(qvl0, info.preFormula), 
              new ExistsExp(new QuantifiedVariable(qvl, null),
                  new NotExp(info.postFormula))));
      if (def3 != null)
        new CheckTask(folder, "Is postcondition sometimes not trivial?", 
            "Is postcondition false for some input and output?", 
            (FunctionSymbol)def3.ident.getSymbol(), def3);
    }

    // output is uniquely defined for all inputs
    if (info.postFormula != null)
    {
      Identifier ident = new Identifier("_" + info.symbol.ident.string + "_" +
          counter + "_PostUnique");
      Identifier result = new Identifier(TypeChecker.resultName);
      Identifier result0 = new Identifier("_" + TypeChecker.resultName);
      QuantifiedVariableCore qvc = 
          new IdentifierTypeQuantifiedVar(result, info.type);
      QuantifiedVariableCore qvc0 = 
          new IdentifierTypeQuantifiedVar(result0, info.type);
      List<QuantifiedVariableCore> qvl = new ArrayList<QuantifiedVariableCore>();
      qvl.add(qvc);
      List<QuantifiedVariableCore> qvl0 = new ArrayList<QuantifiedVariableCore>();
      qvl0.add(qvc0);
      List<Binder> binders = new ArrayList<Binder>();
      binders.add(new Binder(result, new IdentifierExp(result0)));
      TheoremParamDefinition def = createTheorem(
          new Multiple(false), ident,
          Arrays.asList(info.param),
          info.pre, 
          new ForallExp(new QuantifiedVariable(qvl, info.postFormula),          
              new ForallExp(new QuantifiedVariable(qvl0, 
                  new LetExp(binders, info.postFormula)),
                  new EqualsExp(new IdentifierExp(result),
                      new IdentifierExp(result0)))));
      if (def != null) 
        new CheckTask(folder, "Is result uniquely determined?", 
            "For every input, is postcondition true for only one output?", 
            (FunctionSymbol)def.ident.getSymbol(), def);
    }
  }

  /***************************************************************************
   * Add to folder all tasks related to the correctness of function result.
   * @param folder the folder to which to add the tasks
   * @param info the information about the function 
   * @param theorem true if function result must be 'true'
   * @param counter a unique counter for the declaration
   **************************************************************************/
  private void addCorrectnessTasks(TaskFolder folder, OpInfo info,
    boolean theorem, int counter)
  {
    FunctionSymbol fun = (FunctionSymbol)info.symbol.ident.getSymbol();
    List<Expression> postFormulas = new ArrayList<Expression>();
    if (theorem)
      postFormulas.add(AST.reference(TypeChecker.resultName));
    else
    {
      Expression constraint = getConstraint(fun.type, 
          AST.reference(TypeChecker.resultName), info.bodyexp.getType());
      if (constraint != null) postFormulas.add(constraint);
    }
    for (FunctionSpec post : info.post)
    {
      FunctionSpec.EnsuresSpec post0 = (FunctionSpec.EnsuresSpec)post;
      postFormulas.add(post0.exp);
    }
    int i = 0;
    for (Expression postFormula : postFormulas)
    {
      Identifier ident = new Identifier("_" + info.symbol.ident.string + "_" +
          + counter + "_OutputCorrect" + i);
      List<Binder> binders = new ArrayList<Binder>();
      Identifier result = new Identifier(TypeChecker.resultName);
      Expression bodyexp0 = UseContracts.execute(info.bodyexp, fun, callers);
      binders.add(new Binder(result, bodyexp0));
      List<FunctionSpec> pre = info.pre;
      TheoremParamDefinition def = createTheorem(
          new Multiple(false), ident,
          Arrays.asList(info.param),
          pre, new LetExp(binders, postFormula));
      if (def != null) 
      {
        CheckTask task = new CheckTask(folder, "Is result correct?", 
            postFormula.toString(), 
            (FunctionSymbol)def.ident.getSymbol(), def);
        task.setSourcePosition(postFormula.getPosition());
      }
      i++;
    }
  }
  
  
  /***************************************************************************
   * Add to folder all tasks related to the preconditions of specification.
   * @param info the information about the function  
   * @param pre the preconditions of the function.  
   * @param folder the folder to which to add precondition tasks
   * @param folder the folder to which to add recursion tasks
   * @param counter a unique counter for the declaration
   **************************************************************************/
  private void addSpecificationTasks(OpInfo info, 
    List<FunctionSpec> pre, TaskFolder pfolder, TaskFolder rfolder, int counter)
  {
    addPreconditions(counter + "_PreReq", 
        pfolder, rfolder, info.symbol, info.param, new ArrayList<FunctionSpec>(), 
        info.decTerms,
        info.preFormula);
    Identifier result = new Identifier(TypeChecker.resultName);
    QuantifiedVariableCore qvc = 
        new IdentifierTypeQuantifiedVar(result, info.type);
    List<QuantifiedVariableCore> qvl = new ArrayList<QuantifiedVariableCore>();
    qvl.add(qvc);
    addPreconditions(counter + "_PreEns", 
        pfolder, rfolder, info.symbol, info.param, pre, info.decTerms,
        new ForallExp(new QuantifiedVariable(qvl, null), info.postFormula));
    int i = 0;
    for (FunctionSpec s : info.dec)
    {
      FunctionSpec.DecreasesSpec s0 = (FunctionSpec.DecreasesSpec)s;
      for (Expression e : s0.exps)
      {
        addPreconditions(counter + "_PreDec" + "_" + i + "_", 
            pfolder, rfolder, info.symbol, info.param, pre, info.decTerms, e);
        i++;
      }
    }
  }
  
  /***************************************************************************
   * Add to folder all tasks checking preconditions arising from an expression.
   * @param key the prefix to use for the generated operations
   * @param folder the folder to which to add precondition tasks to.
   * @param folder the folder to which to add recursion tasks to.
   * @param fun the operation in which the expression is evaluated.
   * @param params the parameters of the operation.
   * @param pre the preconditions of the operation.
   * @param measures the measures of the operation.
   * @param exp the expression that is evaluated (may be null).
   ***************************************************************************/
  private void addPreconditions(String key, TaskFolder pfolder, TaskFolder rfolder,
    FunctionSymbol fun, Parameter[] params,
    List<FunctionSpec> pre, Expression[] measures, Expression exp)
  {
    if (exp == null) return;
    List<Condition> cs = Preconditions.generate(exp, fun, callers);
    List<Binder> binders = getFunctionMeasureBinders(measures);
    int n = cs.size();
    for (int i=0; i<n; i++)
    {
      Condition cond = cs.get(i);
      Identifier ident = new Identifier("_" + fun.ident.string + "_" + key + i);
      // checker.process(params, cond.exp);
      Expression exp0 = cond.exp;
      if (cond.kind == Condition.Kind.FunctionMeasureDecreased)
      {
        if (binders != null) exp0 = new LetParExp(binders, exp0);
      }
      TheoremParamDefinition def = createTheorem(
          new Multiple(false), ident,
          Arrays.asList(params),
          pre, exp0);
      if (def != null) 
      {
        TaskFolder folder;
        String name;
        String description;
        switch(cond.kind)
        {
        case Assertion:
          folder = pfolder;
          name = "Does assertion hold?";
          description = cond.phrase.toString();
          break;
        case Choice:
          folder = pfolder;
          name = "Is choice possible?";
          description = cond.phrase.toString();
          break;
        case Select:
          folder = pfolder;
          name = "Is index value legal?";
          description = cond.phrase.toString();
          break;
        case Update:
          folder = pfolder;
          name = "Is update value legal?";
          description = cond.phrase.toString();
          break;
        case FunctionMeasureDecreased:
          folder = rfolder;
          name = "Is function measure decreased?";
          description = cond.phrase.toString();
          break;
        default:
          folder = pfolder;
          name = "Does operation precondition hold?";
          description = cond.phrase.toString();
        }
        CheckTask task = new CheckTask(folder, name, description,
            (FunctionSymbol)def.ident.getSymbol(), def);
        task.setSourcePosition(cond.pos);
        task.addAuxiliaryPositions(cond.path);
      }
    }
  }
    
  /***************************************************************************
   * Get the constraint for a given type to be matched by an expression 
   * of another type (assumes that both types already weakly match)
   * @param type the type to be matched
   * @param exp the expression
   * @param etype the type of the expression
   * @return the constraint (null, if none)
   **************************************************************************/
  public static Expression getConstraint(Type type, Expression exp, Type etype)
  {
    // if type matches strongly, no constraint necessary
    if (TypeChecker.matchesStrong(etype, type)) return null;
    
    // add assumption from subtype predicate of argument value
    if (etype instanceof SubType)
    {
      SubType etype0 = (SubType)etype;  
      Expression post = getConstraint(type, exp, etype0.type);
      if (post == null) return null;
      Expression pre = subTypeApp(etype0, exp, etype0);
      return new ImpliesExp(pre, post);
    }
    
    // etype is not a subtype anymore
    if (type instanceof SubType)
    {
      SubType type0 = (SubType)type;
      return subTypeApp(type0, exp, etype);
    }
    
    // stype and vtype have the same top-level constructor
    if (type instanceof UnitType) return null;
    if (type instanceof BoolType) return null;
    if (type instanceof IntType)
    {
      IntType type0 = (IntType)type;
      return new AndExp(
          new LessEqualExp(AST.number(type0.ivalue1), exp),
          new LessEqualExp(exp, AST.number(type0.ivalue2)));
    }
    if (type instanceof SetType)
    {
      SetType type0  = (SetType)type;
      SetType etype0 = (SetType)etype;
      String elemName = "_e";
      Expression base = getConstraint(type0.type, AST.reference(elemName), etype0.type);
      if (base == null) return null;
      List<QuantifiedVariableCore> qv = new ArrayList<QuantifiedVariableCore>();
      qv.add(new QuantifiedVariableCore.IdentifierSetQuantifiedVar(
              new Identifier(elemName), exp));
      return new ForallExp(new QuantifiedVariable(qv, null), base);
    }
    if (type instanceof TupleType)
    {
      TupleType type0  = (TupleType)type;
      TupleType etype0 = (TupleType)etype;
      Expression cond = null;
      int n = type0.types.length;
      for (int i=0; i<n; i++)
      {
        Expression exp0 = new TupleSelectionExp(exp, AST.decimal(i));
        Expression cond0 = getConstraint(type0.types[i], exp0, etype0.types[i]);
        cond = AST.and(cond, cond0);
      }
      return cond;
    }
    if (type instanceof RecordType)
    {
      RecordType type0  = (RecordType)type;
      RecordType etype0 = (RecordType)etype;
      Expression cond = null;
      int n = type0.symbols.length;
      for (int i=0; i<n; i++)
      {
        ValueSymbol symbol = type0.symbols[i];
        Identifier id = new Identifier(symbol.ident.string);
        Expression exp0 = new RecordSelectionExp(exp, id);
        ValueSymbol esymbol = (ValueSymbol)etype0.param[i].ident.getSymbol();
        Expression cond0 = getConstraint(symbol.type, exp0, esymbol.type);
        cond = AST.and(cond, cond0);
      }
      return cond;
    }
    if (type instanceof MapType)
    {
      MapType type0  = (MapType)type;
      MapType etype0 = (MapType)etype;
      // covariant range matching
      String yid = "_y";
      Expression cond0 = getConstraint(type0.type2, AST.reference(yid), etype0.type2);
      if (cond0 != null) 
      {
        List<QuantifiedVariableCore> qv = new ArrayList<QuantifiedVariableCore>();
        qv.add(new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(
                new Identifier(yid), etype0.type2));
        cond0 = new ForallExp(new QuantifiedVariable(qv, null), cond0);
      }    
      // contravariant argument matching
      String xid = "_x";
      Expression cond1 = getConstraint(etype0.type2, AST.reference(xid), type0.type2);
      if (cond1 != null) 
      {
        List<QuantifiedVariableCore> qv = new ArrayList<QuantifiedVariableCore>();
        qv.add(new QuantifiedVariableCore.IdentifierTypeQuantifiedVar(
                new Identifier(xid), type0.type2));
        cond1 = new ForallExp(new QuantifiedVariable(qv, null), cond1);
      }   
      return AST.and(cond1, cond0);
    }
    if (type instanceof RecursiveType)
    {
      // currently there is no "depth" function in the language
      // rather than defining a type-specific one, we are lazy
      // and just use the static type information
      RecursiveType type0  = (RecursiveType)type;
      RecursiveType etype0 = (RecursiveType)etype;
      return new LessEqualExp(AST.number(etype0.ivalue), AST.number(type0.ivalue));
    }
    assert false;
    return null;
  }
  
  /***************************************************************************
   * Construct application of subtype predicate.
   * @param type the subtype
   * @param exp the argument
   * @param etype the type of the argument
   * @return the application expression
   **************************************************************************/
  private static Expression subTypeApp(SubType type, Expression exp, Type etype)
  {
    List<Binder> binders = new ArrayList<Binder>();
    binders.add(new Binder(new Identifier(TypeChecker.paramName), exp));
    TypeDefinition def = (TypeDefinition)type.pred.getDefinition();
    return new LetExp(binders, def.exp);
  }
  
  /***************************************************************************
   * Add task that measure is non-negative.
   * @param info the operation information
   * @param pre the preconditions
   * @param folder the task folder
   * @param binders the binders for the measures (may be null)
   * @param counter a unique counter for the declaration
   ***************************************************************************/
  private void addMeasureTasks(OpInfo info, List<FunctionSpec> pre, 
    TaskFolder folder, List<Binder> binders, int counter)
  {
    if (binders == null) return;
    Expression exp = null;
    for (Binder b : binders)
    {
      exp = AST.and(exp, 
          new GreaterEqualExp(AST.reference(b.ident.string), 
              new NumberLiteralExp(AST.decimal(0))));
    }
    exp = new LetParExp(binders, exp);
    Identifier ident = new Identifier("_" + info.symbol.ident.string + "_" +
        + counter + "_FunOp");
    TheoremParamDefinition def = createTheorem(
        new Multiple(false), ident,
        Arrays.asList(info.param), pre, exp);
    if (def != null) 
    {
      CheckTask task = new CheckTask(folder, 
          "Is function measure non-negative?",
          info.dec.get(0).toString(), 
          (FunctionSymbol)def.ident.getSymbol(), def);
      task.setSourcePosition(info.dec.get(0).getPosition());
    }
  }
  
  /***************************************************************************
   * Add to folders all tasks related to a procedure.
   * @param info the information about the procedure.
   * @param pre the preconditions of the procedure.
   * @param cfolder the folder for correctness tasks.
   * @param lfolder the folder for loop tasks
   * @param pfolder the folder for precondition tasks.
   * @param counter a unique counter for the declaration
   **************************************************************************/
  private void addProcedureTasks(OpInfo info, 
    List<FunctionSpec> pre, TaskFolder cfolder, TaskFolder lfolder,
    TaskFolder pfolder, int counter)
  {
    List<Binder> binders = getFunctionMeasureBinders(info.decTerms);
    addMeasureTasks(info, pre, lfolder, binders, counter);
    List<Condition> conditions = Commands.process(info, callers);
    int cnumber = 0;
    int lnumber = 0;
    int pnumber = 0;
    int fnumber = 0;
    for (Condition cond : conditions)
    {
      // checker.process(info.param, cond.exp);
      TaskFolder folder = null;
      String name = null;
      String description = cond.phrase.toString();
      Expression exp0 = cond.exp;
      String key = null;
      int number = 0;
      switch(cond.kind)
      {
      case Constraint:
        folder = pfolder;
        name = "Does type constraint of operation hold?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case Application:
        folder = pfolder;
        name = "Does operation precondition hold?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case Select:
        folder = pfolder;
        name = "Is index value legal?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case Update:
        folder = pfolder;
        name = "Is update value legal?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case Assignment:
        folder = pfolder;
        name = "Is assigned value legal?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case Assertion:
        folder = pfolder;
        name = "Does assertion hold?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case Choice:
        folder = pfolder;
        name = "Is choice possible?";
        key = "PreOp";
        number = pnumber;
        pnumber++;
        break;
      case InvariantInitial:
        folder = lfolder;
        name = "Does loop invariant initially hold?";
        key = "LoopOp";
        number = lnumber;
        lnumber++;
        break;
      case InvariantPreserved:
        folder = lfolder;
        name = "Is loop invariant preserved?";
        key = "LoopOp";
        number = lnumber;
        lnumber++;
        break;
      case LoopMeasureNonNegative:
        folder = lfolder;
        name = "Is loop measure non-negative?";
        key = "LoopOp";
        number = lnumber;
        lnumber++;
        break;
      case LoopMeasureDecreased:
        folder = lfolder;
        name = "Is loop measure decreased?";
        key = "LoopOp";
        number = lnumber;
        lnumber++;
        break;
      case FunctionMeasureDecreased:
        folder = lfolder;
        name = "Is function measure decreased?";
        key = "FunOp";
        number = fnumber;
        fnumber++;
        if (binders != null) exp0 = new LetParExp(binders, exp0);
        break;
      case ResultCorrect:
        folder = cfolder;
        name = "Is result correct?";
        key = "CorrOp";
        number = cnumber;
        cnumber++;
        break;
      }
      Identifier ident = new Identifier("_" + info.symbol.ident.string + "_"
          + counter + "_" + key + number);
      TheoremParamDefinition def = createTheorem(
          new Multiple(false), ident,
          Arrays.asList(info.param), pre, exp0);
      if (def != null) 
      {
        CheckTask task = new CheckTask(folder, name,
            description, (FunctionSymbol)def.ident.getSymbol(), def);
        task.setSourcePosition(cond.pos);
        task.addAuxiliaryPositions(cond.path);
      }
    }
  } 
  
  /****************************************************************************
   * Get name of function measure
   * @param i the number of the measure
   * @return its name
   ***************************************************************************/
  public static String getFunctionMeasureName(int i)
  {
    if (i == 0) return funMeasureName;
    return funMeasureName + i;
  }
  
  /****************************************************************************
   * Get list of binders for function measures.
   * @param measures the measures (may be null)
   * @return the binders (may be null)
   ***************************************************************************/
  private static List<Binder> getFunctionMeasureBinders(Expression[] measures)
  {
    if (measures == null) return null;
    int n = measures.length;
    List<Binder> binders = new ArrayList<Binder>();
    for (int i=0; i<n; i++)
    {
      String name = getFunctionMeasureName(i);
      binders.add(new Binder(new Identifier(name), measures[i]));
    }
    return binders;     
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------