// ---------------------------------------------------------------------------
// AST.java
// Abstract syntax trees
// $Id: AST.java,v 1.96 2018/06/14 13:22:44 schreine Exp schreine $
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
import java.io.*;

import riscal.Main;
import riscal.syntax.AST.Expression.IdentifierExp;
import riscal.syntax.AST.Expression.NumberLiteralExp;
import riscal.types.Environment.*;
import riscal.types.Environment.Symbol.*;
import riscal.types.TypeChecker;

/***
 * 
 * Abstract syntax trees.
 *
 */
public abstract class AST
{
  // prevent construction from outside the class
  private AST() { }

  // the source position associated to the tree
  private SourcePosition position;
  public void setPosition(SourcePosition position) { this.position = position; }
  public SourcePosition getPosition() { return position; }
  
  // the source of an abstract syntax tree
  public static final class Source
  {
    public final File file;
    public final List<String> lines;
    public final String text;
    public Source(File file, List<String> lines)
    { 
      this.file = file; this.lines = lines; 
      StringBuilder builder = new StringBuilder();
      String eol = System.lineSeparator();
      for (String line : lines) builder.append(line).append(eol);
      text = builder.toString();
    }
  }
  
  // the position of an abstract syntax tree in a source
  public static final class SourcePosition
  {
    public final Source source;
    public final int lineFrom;
    public final int charFrom;
    public final int lineTo;
    public final int charTo;
    public SourcePosition(Source source, 
      int lineFrom, int charFrom, int lineTo, int charTo)
    {
      this.source = source;
      this.lineFrom = lineFrom; this.charFrom = charFrom;
      this.lineTo = lineTo; this.charTo = charTo;
    }
    public String toString()
    {
      return source.file.getAbsolutePath() + ":[" 
          + (lineFrom+1) + ":" + charFrom + ","
          + (lineTo+1) + ":" + charTo + "]";
    }
    public String toShortString()
    {
      return "line " + (lineFrom+1) + " in file " + source.file.getName();
    }
  }
  
  // the string representation of a sequence of abstract syntax trees
  public static String toString(Object[] asts)
  {
    StringBuffer buffer = new StringBuffer();
    for (Object ast : asts)
      buffer.append(ast);
    return buffer.toString();
  }
  public static String toString(Object[] asts, String sep)
  {
    StringBuffer buffer = new StringBuffer();
    int i = 0;
    int n = asts.length;
    for (Object ast : asts)
    {
      buffer.append(ast);
      i++; 
      if (i < n) buffer.append(sep);
    }
    return buffer.toString();
  }
  
  // --------------------------------------------------------------------------
  //
  // Specifications.
  //
  // --------------------------------------------------------------------------
  public static final class Specification extends AST
  {
    public final Declaration[] declarations;
    public Specification(List<Declaration> declarations) 
    { 
      this.declarations = declarations.toArray(new Declaration[declarations.size()]); 
    }
    public String toString() { return toString(declarations); }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }

  // --------------------------------------------------------------------------
  //
  // Declarations
  //
  // --------------------------------------------------------------------------
  public static abstract class Declaration extends AST
  {
    private Declaration() { }
    public abstract<T> T accept(ASTVisitor<T> v);
    public String toStringCore()
    {
      String description = toString();
      return description.substring(0, description.length()-1);
    }
    public static final class ValueDeclaration extends Declaration
    {
      public final Identifier ident;
      public ValueDeclaration(Identifier ident) { this.ident = ident; }
      public String toString() { return "val " + ident + ": ℕ;\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class FunctionDeclaration extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public final Type type; 
      public FunctionDeclaration(Multiple multiple, 
        Identifier ident, List<Parameter> param, Type type)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]); 
        this.type = type;
      }
      public String toString() 
      { return multiple + "fun " + ident + "(" + toString(param, ", ") + "): " + type + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PredicateDeclaration extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public PredicateDeclaration(Multiple multiple, 
        Identifier ident, List<Parameter> param)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]); 
      }
      public String toString() 
      { return multiple + "pred " + ident + "(" + toString(param, ", ") + ");\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ProcedureDeclaration extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public final Type type; 
      public ProcedureDeclaration(Multiple multiple, 
        Identifier ident, List<Parameter> param, Type type)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]);
        this.type = type; 
      }
      public String toString() 
      { return multiple + "proc " + ident + "(" + toString(param, ", ") + "): " + type + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TypeDefinition extends Declaration
    {
      public final Identifier ident; public final Type type; 
      public final Expression exp;
      public TypeDefinition(Identifier ident, Type type, Expression exp)
      { this.ident = ident; this.type = type; this.exp = exp; }
      public String toString() 
      { return "type " + ident + " = " + type + (exp == null ? "" : exp) + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecTypeDefinition extends Declaration
    {
      public final Expression exp;
      public final RecursiveTypeItem[] ritems;
      public RecTypeDefinition(Expression exp, List<RecursiveTypeItem> ritems)
      { 
        this.exp = exp; 
        this.ritems = ritems.toArray(new RecursiveTypeItem[ritems.size()]); 
      }
      public String toString() 
      { return "rectype(" + exp + ") " + toString(ritems, " and ") + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class EnumTypeDefinition extends Declaration
    {
      public final RecursiveTypeItem ritem;
      public EnumTypeDefinition(RecursiveTypeItem ritem)
      {  
        this.ritem = ritem; 
      }
      public String toString() 
      { return "enumtype" + ritem + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ValueDefinition extends Declaration
    {
      public final Identifier ident; public final Type type; // may be null
      public final Expression exp;
      public ValueDefinition(Identifier ident, Type type, Expression exp)
      { this.ident = ident; this.type = type; this.exp = exp; }
      public String toString() 
      { return "val " + ident + 
          (type == null ? "" : ": " + type) + " = " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class FunctionDefinition extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public final Type type; public final FunctionSpec[] spec;
      public final Expression exp;
      public FunctionDefinition(Multiple multiple, 
        Identifier ident, List<Parameter> param,
        Type type, List<FunctionSpec> spec, Expression exp)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]);
        this.type = type; 
        this.spec = spec.toArray(new FunctionSpec[spec.size()]); 
        this.exp = exp; 
      }
      public String toString() 
      { return multiple + "fun " + ident + "(" + toString(param, ", ") + "): " + type + 
          (spec.length == 0 ? "" : "\n") +
          toString(spec) + " = " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PredicateDefinition extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public final FunctionSpec[] spec;
      public final Expression exp;
      public PredicateDefinition(Multiple multiple, 
        Identifier ident, List<Parameter> param,
        List<FunctionSpec> spec, Expression exp)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]);
        this.spec = spec.toArray(new FunctionSpec[spec.size()]); 
        this.exp = exp; 
      }
      public String toString() 
      { return multiple + "pred " + ident + "(" + toString(param, ", ") + ")" + 
          (spec.length == 0 ? "" : "\n") +
          toString(spec) + " ⇔ " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TheoremParamDefinition extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public final FunctionSpec[] spec;
      public final Expression exp;
      public TheoremParamDefinition(Multiple multiple, 
        Identifier ident, List<Parameter> param,
        List<FunctionSpec> spec, Expression exp)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]);
        this.spec = spec.toArray(new FunctionSpec[spec.size()]); 
        this.exp = exp; 
      }
      public String toString() 
      { return multiple + "theorem " + ident + "(" + toString(param, ", ") + ")" + 
          (spec.length == 0 ? "" : "\n") +
          toString(spec) + " ⇔ " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ProcedureDefinition extends Declaration
    {
      public final Multiple multiple;
      public final Identifier ident; public final Parameter[] param;
      public final Type type; public final FunctionSpec[] spec;
      public final Command[] commands; public final Expression exp;
      public ProcedureDefinition(Multiple multiple, 
        Identifier ident, List<Parameter> param,
        Type type, List<FunctionSpec> spec, List<Command> commands,
        Expression exp)
      { 
        this.multiple = multiple;
        this.ident = ident; 
        this.param = param.toArray(new Parameter[param.size()]);
        this.type = type; 
        this.spec = spec.toArray(new FunctionSpec[spec.size()]); 
        this.commands = commands.toArray(new Command[commands.size()]);
        this.exp = exp;
      }
      public String toString() 
      { return multiple + "proc " + ident + "(" + toString(param, ", ") + "): " + type + 
          (spec.length == 0 ? "" : "\n") + toString(spec) + 
          "{\n" + toString(commands) + "return " + exp + ";\n" + "}\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TheoremDefinition extends Declaration
    {
      public final Identifier ident;  public final Expression exp;
      public TheoremDefinition(Identifier ident, Expression exp)
      { this.ident = ident; this.exp = exp; }
      public String toString() 
      { return "theorem " + ident + " ⇔ " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PredicateValueDefinition extends Declaration
    {
      public final Identifier ident;  public final Expression exp;
      public PredicateValueDefinition(Identifier ident, Expression exp)
      { this.ident = ident; this.exp = exp; }
      public String toString() 
      { return "pred " + ident + " ⇔ " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }

  // --------------------------------------------------------------------------
  //
  // Function specifications
  //
  // --------------------------------------------------------------------------
  public static abstract class FunctionSpec extends AST
  {
    private FunctionSpec() { }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class RequiresSpec extends FunctionSpec
    {
      public final Expression exp;
      public RequiresSpec(Expression exp) { this.exp = exp; }
      public String toString() { return "requires " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class EnsuresSpec extends FunctionSpec
    {
      public final Expression exp;
      public EnsuresSpec(Expression exp) { this.exp = exp; }
      public String toString() { return "ensures " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class DecreasesSpec extends FunctionSpec
    {
      public final Expression[] exps;
      public DecreasesSpec(List<Expression> exps) 
      { this.exps = exps.toArray(new Expression[exps.size()]); }
      public String toString() { return "decreases " + toString(exps, ", ") + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ContractSpec extends FunctionSpec
    { 
      public ContractSpec() { }
      public String toString() { return "inline;\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }

  // --------------------------------------------------------------------------
  //
  // Commands
  //
  // --------------------------------------------------------------------------
  public static abstract class Command extends AST
  {
    // the set of variables (potentially) modified by the command
    private Set<ValueSymbol> frame = null;
    public void setFrame(Set<ValueSymbol> frame) { this.frame = frame; }
    public Set<ValueSymbol> getFrame() { return frame; }
    
    private Command() { }
    public abstract<T> T accept(ASTVisitor<T> v);
    public String toStringCore()
    {
      String description = toString();
      return description.substring(0, description.length()-1);
    }
    
    public static abstract class LoopCommand extends Command
    {
      public final LoopSpec[] spec;
      public LoopCommand(List<LoopSpec> spec) 
      { 
        this.spec = spec.toArray(new LoopSpec[spec.size()]);; 
      }
      private final List<ValueSymbol> vars = new ArrayList<ValueSymbol>();
      private final List<ValueSymbol> oldvars = new ArrayList<ValueSymbol>();
      public void add(ValueSymbol var, ValueSymbol ovar)
      { vars.add(var); oldvars.add(ovar); }
      public void addAll(List<ValueSymbol> vars, List<ValueSymbol> oldvars)
      { this.vars.addAll(vars); this.oldvars.addAll(oldvars); }
      public List<ValueSymbol> getVars() { return vars; }
      public List<ValueSymbol> getOldVars() { return oldvars; }
    }
    
    public static final class EmptyCommand extends Command
    {
      public EmptyCommand() {}
      public String toString() { return ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class AssignmentCommand extends Command
    {
      public final Identifier ident; 
      public final Selector[] sels;
      public final Expression exp;
      public AssignmentCommand(Identifier ident, List<Selector>sels, Expression exp) 
      { 
        this.ident = ident; 
        this.sels = new Selector[sels.size()]; sels.toArray(this.sels);
        this.exp = exp; 
      }
      public String toString() 
      {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ident);
        for (Selector sel : sels) buffer.append(sel);
        buffer.append(" ≔ ").append(exp).append(";\n"); 
        return buffer.toString();
      }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ChooseCommand extends Command
    {
      public final QuantifiedVariable qvar; 
      public ChooseCommand(QuantifiedVariable qvar) { this.qvar = qvar; }
      public String toString() { return "choose " + qvar + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ChooseElseCommand extends Command
    {
      public final QuantifiedVariable qvar; 
      public final Command command1; public final Command command2;
      public ChooseElseCommand(QuantifiedVariable qvar, 
        Command command1, Command command2) 
      { this.qvar = qvar; this.command1 = command1; this.command2 = command2; }
      public String toString() 
      { return "choose " + qvar + " then\n" + command1 + "else\n" + command2; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ChooseDoCommand extends LoopCommand
    {
      public final QuantifiedVariable qvar;
      public final Command command;
      private ValueSymbol allSet;
      public ChooseDoCommand(QuantifiedVariable qvar,  List<LoopSpec> spec, Command command)
      { 
        super(spec);
        this.qvar = qvar; 
        this.command = command; 
      }
      public void setAllSet(ValueSymbol allSet) { this.allSet = allSet; }
      public ValueSymbol getAllSet() { return allSet; }
      public String toString() 
      { return "choose " + qvar + " do\n" + toString(spec) + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ExpCommand extends Command
    {
      public final Expression exp; 
      public ExpCommand(Expression exp) { this.exp = exp; }
      public String toString() 
      { return exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IfThenCommand extends Command
    {
      public final Expression exp; public final Command command; 
      public IfThenCommand(Expression exp, Command command)
      { this.exp = exp; this.command = command; }
      public String toString() 
      { return "if " + exp + " then\n" + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IfThenElseCommand extends Command
    {
      public final Expression exp; 
      public final Command command1; public final Command command2;
      public IfThenElseCommand(Expression exp, 
        Command command1, Command command2)
      { this.exp = exp; this.command1 = command1; this.command2 = command2; }
      public String toString() 
      { return "if " + exp + " then\n" + command1 + "else\n" + command2; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MatchCommand extends Command
    {
      public final Expression exp; public final PatternCommand[] pcommand;
      public MatchCommand(Expression exp, List<PatternCommand> pcommand)
      { 
        this.exp = exp; 
        this.pcommand = pcommand.toArray(new PatternCommand[pcommand.size()]); 
      }
      public String toString() 
      { return "match " + exp + " with\n" + "{\n" + toString(pcommand) + "}\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class WhileCommand extends LoopCommand
    {
      public final Expression exp;
      public final Command command;
      public WhileCommand(Expression exp, List<LoopSpec> spec, Command command)
      { 
        super(spec);
        this.exp = exp; 
        this.command = command; 
      }
      public String toString() 
      { return "while " + exp + " do\n" + toString(spec) + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class DoWhileCommand extends LoopCommand
    {
      public final Command command;
      public final Expression exp; 
      public DoWhileCommand(Command command, Expression exp, List<LoopSpec> spec)
      { 
        super(spec);
        this.command = command; this.exp = exp; 
      }
      public String toString() 
      { return "do\n" + toString(spec) + command + "while " + exp + "\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ForCommand extends LoopCommand
    {
      public final Command command1; public final Expression exp; 
      public final Command command2; public final Command command3;
      public ForCommand(Command command1, Expression exp, Command command2,
        List<LoopSpec> spec, Command command3)
      { 
        super(spec);
        this.command1 = command1; this.exp = exp; this.command2 = command2;
        this.command3 = command3; 
      }
      public String toString() 
      { String s1 = command1.toString();
        s1 = s1.substring(0, s1.length()-1);
        String s2 = command2.toString();
        s2 = s2.substring(0, s2.length()-1);
        return "for " + s1 + " " + exp + "; " + s2 + " do\n" 
          + toString(spec) + command3; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ForInCommand extends LoopCommand
    {
      public final QuantifiedVariable qvar; 
      public final Command command;
      private ValueSymbol allSet;
      public ForInCommand(QuantifiedVariable qvar, 
        List<LoopSpec> spec, Command command)
      { 
        super(spec);
        this.qvar = qvar; 
        this.command = command; 
      }
      public void setAllSet(ValueSymbol allSet) { this.allSet = allSet; }
      public ValueSymbol getAllSet() { return allSet; }
      public String toString() 
      { return "for " + qvar + " do\n" + toString(spec) + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class VarCommand extends Command
    {
      public final Identifier ident; public final Type type; 
      public final Expression exp; // may be null
      public VarCommand(Identifier ident, Type type, Expression exp)
      { this.ident = ident; this.type = type; this.exp = exp; }
      public String toString() 
      { return "var " + ident + ": " + type +
          (exp == null ? "" : " ≔ " + exp) + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ValCommand extends Command
    {
      public final Identifier ident; 
      public final Type type; // may be null
      public final Expression exp;
      public ValCommand(Identifier ident, Type type, Expression exp)
      { this.ident = ident; this.type = type; this.exp = exp; }
      public String toString() 
      { return "val " + ident + (type == null ? "" : ": " + type) 
          + " ≔ " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class AssertCommand extends Command
    {
      public final Expression exp;
      public AssertCommand(Expression exp) { this.exp = exp; }
      public String toString() { return "assert " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PrintCommand extends Command
    {
      public final String string; // may be null
      public final Expression[] exps;
      public PrintCommand(String string, List<Expression> exps) 
      { 
        this.string = string; 
        this.exps = exps.toArray(new Expression[exps.size()]); 
      }
      public String toString() 
      { return "print " + (string == null ? "" : string + 
          (exps.length == 0 ? "" : ", ")) +
          toString(exps, ", ") + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class CheckCommand extends Command
    {
      public final Identifier ident; 
      public final Expression exp; // may be null
      public CheckCommand(Identifier ident, Expression exp) 
      { 
        this.ident = ident; 
        this.exp = exp; 
      }
      public String toString() 
      { return "check " + ident +  ( exp == null ? "" :" with " + exp  + ";\n");}
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class CommandSequence extends Command
    {
      public final Command[] commands;
      public CommandSequence(List<Command> commands)
      { this.commands = new Command[commands.size()];
        commands.toArray(this.commands);
      }
      public String toString() 
      {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\n");
        for (Command c : commands) buffer.append(c);
        buffer.append("}\n");
        return buffer.toString();
      }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }

  // --------------------------------------------------------------------------
  //
  // Loop specifications
  //
  // --------------------------------------------------------------------------
  public static abstract class LoopSpec extends AST
  {
    private LoopSpec() { }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class InvariantLoopSpec extends LoopSpec
    {
      public final Expression exp;
      public InvariantLoopSpec(Expression exp) { this.exp = exp; }
      public String toString() { return "invariant " + exp + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class DecreasesLoopSpec extends LoopSpec
    {
      public final Expression[] exps;
      public DecreasesLoopSpec(List<Expression> exps) 
      { this.exps = exps.toArray(new Expression[exps.size()]); }
      public String toString() { return "decreases " + toString(exps, ", ") + ";\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }

  // --------------------------------------------------------------------------
  //
  // Expressions
  //
  // --------------------------------------------------------------------------
  public static abstract class Expression extends AST
  {
    private Expression() { }
    // the type of the expression
    private Type type;
    public void setType(Type type) { this.type = type; }
    public Type getType() { return type; }
    public abstract<T> T accept(ASTVisitor<T> v);
    private static final String exp(Expression exp) 
    { 
      if (exp instanceof UnitExp || 
          exp instanceof TrueExp || exp instanceof FalseExp ||
          exp instanceof NumberLiteralExp || exp instanceof EmptySetExp ||
          exp instanceof RecIdentifierExp || exp instanceof RecApplicationExp ||
          exp instanceof IdentifierExp || exp instanceof ApplicationExp ||
          exp instanceof MapSelectionExp || exp instanceof TupleSelectionExp || 
          exp instanceof RecordSelectionExp || exp instanceof EnumeratedSetExp || 
          exp instanceof TupleExp || exp instanceof RecordExp ||
          exp instanceof SetSizeExp || exp instanceof SetBuilderExp || 
          exp instanceof ArrayBuilderExp || exp instanceof MapBuilderExp)
        return exp.toString();
      else
        return "(" + exp + ")"; 
    }
    public static final class UnitExp extends Expression
    {
      public UnitExp() { }
      public String toString() { return "()"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TrueExp extends Expression
    {
      public TrueExp() { }
      public String toString() { return "⊤"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class FalseExp extends Expression
    {
      public FalseExp() { }
      public String toString() { return "⊥"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class NumberLiteralExp extends Expression
    {
      public final Decimal literal;
      public NumberLiteralExp(Decimal literal) { this.literal = literal; }
      public String toString() { return literal.toString(); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class EmptySetExp extends Expression
    {
      public final Type type;
      public EmptySetExp(Type type) { this.type = type; }
      public String toString() { return "∅[" + type + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecIdentifierExp extends Expression
    {
      public final Identifier ident1; public final Identifier ident2;
      public RecIdentifierExp(Identifier ident1, Identifier ident2) 
      { this.ident1 = ident1; this.ident2 = ident2; }
      public String toString() { return ident1 + "!" + ident2; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecApplicationExp extends Expression
    {
      public final Identifier ident1; public final Identifier ident2;
      public final Expression[] exps;
      public RecApplicationExp(Identifier ident1, Identifier ident2,
        List<Expression> exps) 
      { 
        this.ident1 = ident1; this.ident2 = ident2; 
        this.exps = exps.toArray(new Expression[exps.size()]);
      }
      public String toString() 
      { return ident1 + "!" + ident2 + "(" + toString(exps, ", ") + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IdentifierExp extends Expression
    {
      public final Identifier ident;
      public IdentifierExp(Identifier ident) { this.ident = ident; }
      public String toString() { return ident.toString(); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ApplicationExp extends Expression
    {
      public final Identifier ident; public final Expression[] exps;
      public ApplicationExp(Identifier ident, List<Expression> exps) 
      { 
        this.ident = ident; 
        this.exps = exps.toArray(new Expression[exps.size()]);
      }
      public String toString() { return ident + "(" + toString(exps, ", ") + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PowersetExp extends Expression
    {
      public final Expression exp;
      public PowersetExp(Expression exp) { this.exp = exp; }
      public String toString() { return "Set(" + exp(exp) + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class Powerset1Exp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public Powerset1Exp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return "Set(" + exp(exp1) + "," + exp(exp2) + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class Powerset2Exp extends Expression
    {
      public final Expression exp1; public final Expression exp2; public final Expression exp3;
      public Powerset2Exp(Expression exp1, Expression exp2, Expression exp3) 
      { this.exp1 = exp1; this.exp2 = exp2; this.exp3 = exp3; }
      public String toString() 
      { return "Set(" + exp(exp1) + "," + exp(exp2) + "," + exp(exp3) + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MapSelectionExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public MapSelectionExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "[" + exp2 + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TupleSelectionExp extends Expression
    {
      public final Expression exp; public final Decimal index;
      public TupleSelectionExp(Expression exp, Decimal index) 
      { this.exp = exp; this.index = index; }
      public String toString() { return exp(exp) + "." + index; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecordSelectionExp extends Expression
    {
      public final Expression exp; public final Identifier ident;
      public RecordSelectionExp(Expression exp, Identifier ident) 
      { this.exp = exp; this.ident = ident; }
      public String toString() { return exp(exp) + "." + ident; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class NegationExp extends Expression
    {
      public final Expression exp;
      public NegationExp(Expression exp) { this.exp = exp; }
      public String toString() { return "-" + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class FactorialExp extends Expression
    {
      public final Expression exp;
      public FactorialExp(Expression exp) { this.exp = exp; }
      public String toString() { return exp(exp) + "!"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class BigUnionExp extends Expression
    {
      public final Expression exp;
      public BigUnionExp(Expression exp) { this.exp = exp; }
      public String toString() { return "⋃" + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class BigIntersectExp extends Expression
    {
      public final Expression exp;
      public BigIntersectExp(Expression exp) { this.exp = exp; }
      public String toString() { return "⋂" + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PowerExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public PowerExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "^" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TimesExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public TimesExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "⋅" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TimesExpMult extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public TimesExpMult(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "⋅..⋅" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class DividesExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public DividesExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "/" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RemainderExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public RemainderExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "%" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PlusExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public PlusExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "+" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PlusExpMult extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public PlusExpMult(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "+..+" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MinusExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public MinusExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "-" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class EnumeratedSetExp extends Expression
    {
      public final Expression[] exps; 
      public EnumeratedSetExp(List<Expression> exps) 
      { this.exps = exps.toArray(new Expression[exps.size()]); }
      public String toString() { return "{" + toString(exps, ",") + "}"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class CartesianExp extends Expression
    {
      public final Expression[] exps; 
      public CartesianExp(List<Expression> exps) 
      { this.exps = exps.toArray(new Expression[exps.size()]); }
      public String toString() { return toString(exps, "×") ; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IntervalExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public IntervalExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + ".." + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TupleExp extends Expression
    {
      public final Expression[] exps; 
      public TupleExp(List<Expression> exps) 
      { this.exps = exps.toArray(new Expression[exps.size()]); }
      public String toString() { return "〈" + toString(exps, ",") + "〉"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecordExp extends Expression
    {
      public final ExpressionIdentifier[] eidents; 
      public RecordExp(List<ExpressionIdentifier> eidents) 
      { this.eidents = eidents.toArray(new ExpressionIdentifier[eidents.size()]); }
      public String toString() { return "〈" + toString(eidents, ",") + "〉"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class SetSizeExp extends Expression
    {
      public final Expression exp;
      public SetSizeExp(Expression exp) { this.exp = exp; }
      public String toString() { return "|" + exp + "|"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class UnionExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public UnionExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "∪" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IntersectExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public IntersectExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "∩" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class WithoutExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public WithoutExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + "\\" + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class NumberExp extends Expression
    {
      public final QuantifiedVariable qvar; 
      public NumberExp(QuantifiedVariable qvar) 
      { this.qvar = qvar; }
      public String toString() { return "#" + qvar ; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class SetBuilderExp extends Expression
    {
      public final Expression exp; public final QuantifiedVariable qvar; 
      public SetBuilderExp(Expression exp, QuantifiedVariable qvar) 
      { this.exp = exp; this.qvar = qvar; }
      public String toString() { return "{" + exp + " | " + qvar + "}"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class SumExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp; 
      public SumExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "∑" + qvar + ". " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ProductExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp; 
      public ProductExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "∏" + qvar + ". " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MinExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp; 
      public MinExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "min " + qvar + ". " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MaxExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp; 
      public MaxExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "max " + qvar + ". " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ArrayBuilderExp extends Expression
    {
      public final Type type; 
      public final Expression exp1; public final Expression exp2;
      public ArrayBuilderExp(Type type, Expression exp1, Expression exp2) 
      { this.type = type; this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() 
      { return "Array[" + exp1 + "," + type + "](" + exp2 + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MapBuilderExp extends Expression
    {
      public final Type type1; public final Type type2; 
      public final Expression exp;
      public MapBuilderExp(Type type1, Type type2, Expression exp) 
      { this.type1 = type1; this.type2 = type2; this.exp = exp; }
      public String toString() 
      { return "Map[" + type1 + "," + type2 + "](" + exp + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ChooseExp extends Expression
    {
      public final QuantifiedVariable qvar; 
      public ChooseExp(QuantifiedVariable qvar) { this.qvar = qvar; }
      public String toString() { return "choose " + qvar; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class MapUpdateExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public final Expression exp3;
      public MapUpdateExp(Expression exp1, Expression exp2, 
        Expression exp3) 
      { this.exp1 = exp1; this.exp2 = exp2; this.exp3 = exp3; }
      public String toString() 
      { return exp(exp1) + " with [" + exp2 + "] = " + exp(exp3); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TupleUpdateExp extends Expression
    {
      public final Expression exp1; public final Decimal index;
      public final Expression exp2;
      public TupleUpdateExp(Expression exp1, Decimal index, Expression exp2) 
      { this.exp1 = exp1; this.index = index; this.exp2 = exp2; }
      public String toString() 
      { return exp(exp1) + " with ." + index + " = " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecordUpdateExp extends Expression
    {
      public final Expression exp1; public final Identifier ident;
      public final Expression exp2;
      public RecordUpdateExp(Expression exp1, Identifier ident,
        Expression exp2) 
      { this.exp1 = exp1; this.ident = ident; this.exp2 = exp2; }
      public String toString() 
      { return exp(exp1) + " with ." + ident + " = " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class EqualsExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public EqualsExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " = " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class NotEqualsExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public NotEqualsExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ≠ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class LessExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public LessExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " < " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class LessEqualExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public LessEqualExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ≤ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class GreaterExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public GreaterExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " > " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class GreaterEqualExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public GreaterEqualExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ≥ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class InSetExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public InSetExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ∈ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class SubsetExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public SubsetExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ⊆ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class NotExp extends Expression
    {
      public final Expression exp;
      public NotExp(Expression exp) { this.exp = exp; }
      public String toString() { return "¬" + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class AndExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public AndExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ∧ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class OrExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public OrExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ∨ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    } 
    public static final class ImpliesExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public ImpliesExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ⇒ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class EquivExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public EquivExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() { return exp(exp1) + " ⇔ " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IfThenElseExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public final Expression exp3;
      public IfThenElseExp(Expression exp1, Expression exp2,
        Expression exp3) 
      { this.exp1 = exp1; this.exp2 = exp2; this.exp3 = exp3; }
      public String toString() 
      { return "if " + exp1 + " then " + exp2 + " else " + exp(exp3); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }   
    public static final class MatchExp extends Expression
    {
      public final Expression exp; public final PatternExpression[] pexp;
      public MatchExp(Expression exp, List<PatternExpression> pexp)
      { 
        this.exp = exp; 
        this.pexp = pexp.toArray(new PatternExpression[pexp.size()]); 
      }
      public String toString() 
      { return "match " + exp + " with\n" + "{\n" + toString(pexp, "; ") + "}\n"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ForallExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp;
      public ForallExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "∀" + qvar + ". " + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ExistsExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp;
      public ExistsExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "∃" + qvar + ". " + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class LetExp extends Expression
    {
      public final Binder[] binder; public final Expression exp;
      public LetExp(List<Binder> binder, Expression exp) 
      { 
        this.binder = binder.toArray(new Binder[binder.size()]); 
        this.exp = exp;
      }
      public String toString() { return "let " + toString(binder, ", ") + " in " + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class LetParExp extends Expression
    {
      public final Binder[] binder; public final Expression exp;
      public LetParExp(List<Binder> binder, Expression exp) 
      { 
        this.binder = binder.toArray(new Binder[binder.size()]); 
        this.exp = exp;
      }
      public String toString() { return "letpar " + toString(binder, ", ") + " in " + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ChooseInExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp;
      public ChooseInExp(QuantifiedVariable qvar, Expression exp) 
      { this.qvar = qvar; this.exp = exp; }
      public String toString() { return "choose " + qvar + " in " + exp(exp); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ChooseInElseExp extends Expression
    {
      public final QuantifiedVariable qvar; public final Expression exp1;
      public final Expression exp2;
      public ChooseInElseExp(QuantifiedVariable qvar, Expression exp1,
        Expression exp2) 
      { this.qvar = qvar; this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() 
      { return "choose " + qvar + " in " + exp1 + " else " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class AssertExp extends Expression
    {
      public final Expression exp1; public final Expression exp2;
      public AssertExp(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; }
      public String toString() 
      { return "assert " + exp1 + " in " + exp(exp2); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PrintExp extends Expression
    {
      public final String string; // may be null
      public final Expression exp;
      public PrintExp(String string, Expression exp) 
      { 
        this.string = string; 
        this.exp = exp; 
      }
      public String toString() 
      { return "print " + (string == null ? "" : "\"" + string + "\", ") +
          exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class PrintInExp extends Expression
    {
      public final String string; // may be null
      public final Expression[] exps;
      public final Expression exp;
      public PrintInExp(String string, List<Expression> exps, Expression exp) 
      { 
        this.string = string; 
        this.exps = exps.toArray(new Expression[exps.size()]); 
        this.exp = exp;
      }
      public String toString() 
      { return "print " + (string == null ? "" : "\"" + string + "\", ") +
          toString(exps, ", ") + " in " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class CheckExp extends Expression
    {
      public final Identifier ident; 
      public final Expression exp; // may be null
      public CheckExp(Identifier ident, Expression exp) 
      { 
        this.ident = ident; 
        this.exp = exp; 
      }
      public String toString() 
      { return "check " + ident +  ( exp == null ? "" :" with " + exp);}
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }

  // --------------------------------------------------------------------------
  //
  // Types
  //
  // --------------------------------------------------------------------------
  public static abstract class Type extends AST
  {
    // a size too large for representation
    public static final long INFINITY = Long.MAX_VALUE;
    
    // if not null, the value denotes the number of elements
    private Long size;    
    private Type() { this.size = null; }

    // public because of handling of recursive types in type checker
    public void setSize(long size) { this.size = size; }
    protected abstract void setSize();
    public long getSize() 
    { 
      // determine type size lazily
      if (size == null) setSize();
      return size; 
    }
    
    // a type is canonical if it is one of the following: 
    // - UnitType, BoolType
    // - MapType, TupleType, SetType with canonical subtypes
    // - IntType with evaluated interval bounds
    // - RecordType with (for the selectors) value symbols with canonical types 
    // - RecursiveType with evaluated depth, rec. item for the type itself,
    //     and type symbols for the mutual recursion cycle
    // - SubType with type symbol and canonical subtype
    // types IdentifierType, NatType, ArrayType  are not canonical
    // (replaced by referenced type, IntType, MapType)
    // canonical types have equals() and hashCode() defined to allow
    // to use them as keys in maps etc 
    // (except for recursive types which use reference equality)
    // invocation of getSize() on canonical type computes its size
    private boolean canonical = false;
    public boolean isCanonical() { return canonical; }
    public void setCanonical() { canonical = true; }
    
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class UnitType extends Type
    {
      public UnitType() { }
      public String toString() { return "()"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) { return o instanceof UnitType; }
      public int hashCode() { return 0; }
      protected void setSize() { setSize(1L); }

    }
    public static final class BoolType extends Type
    {
      public BoolType() { }
      public String toString() { return "Bool"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) { return o instanceof BoolType; }
      public int hashCode() { return 0; }
      protected void setSize() { setSize(2L); }     
    }
    public static final class IntType extends Type
    {
      // (exp1,exp2) may be null, if (int1,int2) are set.
      public final Expression exp1; public final Expression exp2;
      public final Long ivalue1; public final Long ivalue2;
      public IntType(Expression exp1, Expression exp2) 
      { this.exp1 = exp1; this.exp2 = exp2; 
        this.ivalue1 = null; this.ivalue2 = null; }
      public IntType(long ivalue1, long ivalue2)
      { 
        this.exp1 = null; this.exp2 = null; 
        this.ivalue1 = ivalue1; this.ivalue2 = ivalue2; 
      }
      public String toString() { 
        if (exp1 == null)
          return "ℤ[" + ivalue1 + "," + ivalue2 + "]";
        else
          return "ℤ[" + exp1 + "," + exp2 + "]"; 
      }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof IntType)) return false; 
        IntType i = (IntType)o;
        return ivalue1.equals(i.ivalue1) && ivalue2.equals(i.ivalue2);
      }
      public int hashCode() 
      { return Objects.hash(ivalue1.hashCode(), ivalue2.hashCode()); }
      protected void setSize() 
      { setSize(TypeChecker.intSize(ivalue1, ivalue2)); }
    }
    public static final class MapType extends Type
    {
      public final Type type1; public final Type type2;
      public MapType(Type type1, Type type2) 
      { this.type1 = type1; this.type2 = type2; }
      public String toString() { return "Map[" + type1 + "," + type2 + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof MapType)) return false; 
        MapType m = (MapType)o;
        return type1.equals(m.type1) && type2.equals(m.type2);
      }
      public int hashCode() 
      { return Objects.hash(type1.hashCode(), type2.hashCode()); }
      protected void setSize() 
      { setSize(TypeChecker.powerSize(type2.getSize(), type1.getSize())); }
    }
    public static final class TupleType extends Type
    {
      public final Type[] types;
      public TupleType(List<Type> types) 
      { this.types = types.toArray(new Type[types.size()]); }
      public TupleType(Type[] types) { this.types = types; }
      public String toString() { return "Tuple[" + toString(types, ",") + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof TupleType)) return false; 
        TupleType t = (TupleType)o;
        int n = types.length;
        if (t.types.length != n) return false;
        for (int i=0; i<n; i++)
          if (!types[i].equals(t.types[i])) return false;
        return true;
      }
      public int hashCode() { return Arrays.hashCode(types); }
      protected void setSize() 
      { setSize(TypeChecker.tupleSize(types)); }
    }
    public static final class RecordType extends Type
    {
      public final Parameter[] param; public final ValueSymbol[] symbols;
      public RecordType(List<Parameter> param) 
      { this.param = param.toArray(new Parameter[param.size()]); this.symbols = null; }
      public RecordType(List<ValueSymbol> symbols, boolean canonical) 
      { canonical = true;
        this.param = null; this.symbols = symbols.toArray(new ValueSymbol[symbols.size()]); }
      public String toString() 
      {
        Parameter[] param0 = 
            param != null ? param : 
              Arrays.stream(symbols)
              .map((ValueSymbol s)->new Parameter(s.ident, s.type))
              .toArray(Parameter[]::new);
        return "Record[" + toString(param0, ",") + "]";
      }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof RecordType)) return false; 
        RecordType r = (RecordType)o;
        int n = symbols.length;
        if (r.symbols.length != n) return false;
        for (int i=0; i<n; i++)
        {
          if (!symbols[i].ident.string.equals(r.symbols[i].ident.string))
            return false;
          if (!symbols[i].type.equals(r.symbols[i].type))
            return false;
        }
        return true;
      }
      public int hashCode() { 
        return Objects.hash(
            Arrays.hashCode(Arrays.stream(symbols)
                .map((Symbol s)->s.ident.string)
                .toArray(String[]::new)),
            Arrays.hashCode(Arrays.stream(symbols)
                .map((Symbol s)->((ValueSymbol)s).type.hashCode())
                .toArray(String[]::new)));             
        }
      protected void setSize() 
      { setSize(TypeChecker.recordSize(symbols)); }
    }
    public static final class RecursiveType extends Type
    {
      public final Expression exp; public final Long ivalue;
      public final Identifier ident; public final RecursiveTypeItem ritem;
      public final RecursiveTypeItem[] ritems;
      public final TypeSymbol[] symbols;
      public RecursiveType(Expression exp, Identifier ident,
        List<RecursiveTypeItem> ritems) 
      { 
        this.exp = exp; this.ivalue = null;
        this.ident = ident; this.ritem = null;
        this.ritems = ritems.toArray(new RecursiveTypeItem[ritems.size()]); 
        this.symbols = null;
      }
      public RecursiveType(long ivalue, RecursiveTypeItem ritem,
        List<TypeSymbol> symbols) 
      { 
        this.ivalue = ivalue; this.exp = null;
        this.ritem = ritem; this.ident = null;
        this.symbols = symbols.toArray(new TypeSymbol[symbols.size()]); 
        this.ritems = null;
      }
      public String toString() 
      { 
        return "Recursive " + 
          ( ident == null ? ritem.ident : ident ) + "[" + 
          ( exp == null ? ivalue : exp ) + 
          "," + 
          ( ritems == null ? toStringRecItems() : toString(ritems, " and ") ) 
          + "]"; 
      }
      private String toStringRecItems()
      {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        int n = symbols.length;
        for (TypeSymbol s : symbols)
        {
          RecursiveType t = (RecursiveType)s.getType();
          builder.append(t.ritem);
          i++;
          if (i < n) builder.append(" and ");
        }
        return builder.toString();
      }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      protected void setSize() { /* handled in type checker */ }
    }
    public static final class NatType extends Type
    {
      public final Expression exp;
      public NatType(Expression exp) { this.exp = exp; }
      public String toString() { return "ℕ[" + exp + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      protected void setSize() {  /* not canonic */ }
    }
    public static final class SetType extends Type
    {
      public final Type type;
      public SetType(Type type) { this.type = type; }
      public String toString() { return "Set[" + type + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof SetType)) return false; 
        SetType s = (SetType)o;
        return type.equals(s.type);
      }
      public int hashCode() { return type.hashCode(); }
      protected void setSize() 
      { setSize(TypeChecker.powerSize(2, type.getSize())); }
    }
    public static final class ArrayType extends Type
    {
      public final Expression exp;
      public final Type type;
      public ArrayType(Expression exp, Type type) 
      { this.exp = exp; this.type = type; }
      public String toString() { return "Array[" + exp + "," + type + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      protected void setSize() { /* not canonic */ }
    }
    public static final class IdentifierType extends Type
    {
      public final Identifier ident;
      public IdentifierType(Identifier ident) { this.ident = ident; }
      public String toString() { return ident.toString(); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      protected void setSize() { /* not canonic */ }
    }
    public static final class SubType extends Type
    {
      public final Identifier ident;
      public final Type type;
      public final FunctionSymbol pred;
      public SubType(Identifier ident, Type type, FunctionSymbol pred) 
      { this.ident = ident; this.type = type; this.pred = pred; }
      public String toString() { return ident.string; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof SubType)) return false; 
        SubType s = (SubType)o;
        return ident.symbol == s.ident.symbol;
      }
      public int hashCode() { return ident.toString().hashCode(); }
      protected void setSize() 
      { setSize(TypeChecker.subSize(type, pred)); }
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Quantified variables
  //
  // --------------------------------------------------------------------------
  public static final class QuantifiedVariable extends AST
  {
    public final QuantifiedVariableCore[] qvcore; 
    public final Expression exp; // may be null
    public QuantifiedVariable(List<QuantifiedVariableCore> qvcore, Expression exp)
    { 
      this.qvcore = qvcore.toArray(new QuantifiedVariableCore[qvcore.size()]); 
      this.exp = exp; 
    }
    public String toString() { return toString(qvcore, ", ") +
        (exp == null ? "" : " with " + exp); }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }

  // --------------------------------------------------------------------------
  //
  // Quantified Variable Core
  //
  // --------------------------------------------------------------------------
  public static abstract class QuantifiedVariableCore extends AST
  {
    public final Identifier ident; 
    private QuantifiedVariableCore(Identifier ident) { this.ident = ident; }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class IdentifierTypeQuantifiedVar 
    extends QuantifiedVariableCore
    {
      public final Type type;
      public IdentifierTypeQuantifiedVar(Identifier ident, Type type)
      { super(ident); this.type = type; }
      public String toString() { return ident + ":" + type; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class IdentifierSetQuantifiedVar 
    extends QuantifiedVariableCore
    {
      public final Expression exp;
      public IdentifierSetQuantifiedVar(Identifier ident, Expression exp)
      { super(ident); this.exp = exp; }
      public String toString() { return ident + "∈" + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Binders
  //
  // --------------------------------------------------------------------------
  public static final class Binder extends AST
  {
    public final Identifier ident; public final Expression exp;
    public Binder(Identifier ident, Expression exp)
    { this.ident = ident; this.exp = exp; }
    public String toString() { return ident + " = " + exp; }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }
  
  // --------------------------------------------------------------------------
  //
  // Pattern commands
  //
  // --------------------------------------------------------------------------
  public static abstract class PatternCommand extends AST
  {
    public final Command command;
    public PatternCommand(Command command) { this.command = command; }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class IdentifierPatternCommand extends PatternCommand
    {
      public final Identifier ident;
      public IdentifierPatternCommand(Identifier ident, Command command)
      { super(command); this.ident = ident; }
      public String toString() { return ident + " -> " + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ApplicationPatternCommand extends PatternCommand
    {
      public final Identifier ident; public Parameter[] params;
      public ApplicationPatternCommand(Identifier ident, List<Parameter> params,
        Command command)
      { 
        super(command); this.ident = ident; 
        this.params = params.toArray(new Parameter[params.size()]);
      }
      public String toString() 
      { return ident + "(" + toString(params, ", ") + ") -> " + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class DefaultPatternCommand extends PatternCommand
    {
      public DefaultPatternCommand(Command command) { super(command); }
      public String toString() { return "_ -> " + command; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Pattern expressions
  //
  // --------------------------------------------------------------------------
  public static abstract class PatternExpression extends AST
  {
    public final Expression exp;
    private PatternExpression(Expression exp) { this.exp = exp; }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class IdentifierPatternExp extends PatternExpression
    {
      public final Identifier ident; 
      public IdentifierPatternExp(Identifier ident, Expression exp)
      { super(exp); this.ident = ident; }
      public String toString() { return ident + " -> " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class ApplicationPatternExp extends PatternExpression
    {
      public final Identifier ident; public Parameter[] params;
      public ApplicationPatternExp(Identifier ident, List<Parameter> params,
        Expression exp)
      { 
        super(exp); this.ident = ident; 
        this.params = params.toArray(new Parameter[params.size()]);; 
      }
      public String toString() 
      { return ident + "(" + toString(params, ", ") + ") -> " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class DefaultPatternExp extends PatternExpression
    {
      public DefaultPatternExp(Expression exp) { super(exp); }
      public String toString() { return "_ -> " + exp; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Parameters
  //
  // --------------------------------------------------------------------------
  public static final class Parameter extends AST
  {
    public final Identifier ident; public final Type type;
    public Parameter(Identifier ident, Type type)
    { this.ident = ident; this.type = type; }
    public String toString() { return ident + ":" + type; }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }

  // --------------------------------------------------------------------------
  //
  // Expression Identifiers.
  //
  // --------------------------------------------------------------------------
  public static final class ExpressionIdentifier extends AST
  {
    public final Identifier ident; public final Expression exp;
    public ExpressionIdentifier(Identifier ident, Expression exp)
    { this.ident = ident; this.exp = exp; }
    public String toString() { return ident + ":" + exp; }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }
  
  // --------------------------------------------------------------------------
  //
  // Recursive Identifiers.
  //
  // --------------------------------------------------------------------------
  public static abstract class RecursiveIdentifier extends AST
  {
    public final Identifier ident;
    private RecursiveIdentifier(Identifier ident) { this.ident = ident; }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class RecIdentifier extends RecursiveIdentifier
    {
      public RecIdentifier(Identifier ident) { super(ident); }
      public String toString() { return ident.toString(); }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecApplication extends RecursiveIdentifier
    {
      public final Type[] types;
      public RecApplication(Identifier ident, List<Type> types) 
      { 
        super(ident);
        this.types = types.toArray(new Type[types.size()]);
      }
      public String toString() { return ident + "(" + toString(types, ", ") + ")"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }

  // --------------------------------------------------------------------------
  //
  // Recursive Type Items
  //
  // --------------------------------------------------------------------------
  public static final class RecursiveTypeItem extends AST
  {
    public final Identifier ident; public final RecursiveIdentifier[] ridents;
    public RecursiveTypeItem(Identifier ident, List<RecursiveIdentifier> ridents) 
    { 
      this.ident = ident; 
      this.ridents = ridents.toArray(new RecursiveIdentifier[ridents.size()]);
    }
    public String toString() { return ident + " = " + toString(ridents, " | "); }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }
  
  // --------------------------------------------------------------------------
  //
  // Selectors.
  //
  // --------------------------------------------------------------------------
  public static abstract class Selector extends AST
  {
    private Selector() { }
    public abstract<T> T accept(ASTVisitor<T> v);
    public static final class MapSelector extends Selector
    {
      public final Expression exp;
      public MapSelector(Expression exp) {  this.exp = exp; }
      public String toString() { return "[" + exp + "]"; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class TupleSelector extends Selector
    {
      public final Decimal index;
      public TupleSelector(Decimal index) 
      { this.index = index; }
      public String toString() { return "." + index; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
    public static final class RecordSelector extends Selector
    {
      public final Identifier ident;
      public RecordSelector(Identifier ident) { this.ident = ident; }
      public String toString() { return "." + ident; }
      public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Multiple tag
  //
  // --------------------------------------------------------------------------
  public static final class Multiple extends AST
  {
    public final boolean value;
    public Multiple(boolean value) { this.value = value; }
    public String toString() { return value ? "multiple " : ""; }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
  }
  
  // --------------------------------------------------------------------------
  //
  // Identifiers
  //
  // --------------------------------------------------------------------------
  public static final class Identifier extends AST
  {
    public final String string;
    private Symbol symbol; 
    public Identifier(String string) 
    { this.string  = string; this.symbol = null; }
    public String toString() 
    { 
      return string + (Main.printSymbols() && symbol != null ? 
          "[" + symbol + "]" : "");
    }
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    public void setSymbol(Symbol symbol) { this.symbol = symbol; }
    public Symbol getSymbol() { return symbol; }
  }
  
  // --------------------------------------------------------------------------
  //
  // Decimal number literals
  //
  // --------------------------------------------------------------------------
  public static final class Decimal extends AST
  {
    public<T> T accept(ASTVisitor<T> v) { return v.visit(this); }
    public final String value;
    private Integer ivalue;
    public Decimal(String value) { this.value  = value; this.ivalue = null; }
    public String toString() { return value; }
    public void setInteger(Integer ivalue) { this.ivalue = ivalue; }
    public Integer getInteger() { return ivalue; }
  }
  
  // -------------------------------------------------------------------------
  //
  // static auxiliaries
  //
  // -------------------------------------------------------------------------
  
  /***************************************************************************
   * Construct conjunction of two formulas.
   * @param e1 first formula (may be null)
   * @param e2 second formula (may be null)
   * @return the conjunction (may be e1 or e2 or null)
   **************************************************************************/
  public static Expression and(Expression e1, Expression e2)
  {
    if (e1 == null) return e2;
    if (e2 == null) return e1;
    return new Expression.AndExp(e1, e2);
  }
  
  /***************************************************************************
   * Construct a reference to an identifier.
   * @param name the name of the identifier.
   * @return an expression referencing the identifier.
   **************************************************************************/
  public static Expression reference(String name)
  {
    return new IdentifierExp(new Identifier(name));
  }
  
  /***************************************************************************
   * Construct a number literal expression.
   * @param value the value of the literal.
   * @return the expression denoting the literal.
   **************************************************************************/
  public static Expression number(long value)
  {
    return new NumberLiteralExp(decimal(value));
  }
  
  /***************************************************************************
   * Construct a number literal
   * @param value the value of the literal.
   * @return the literal.
   **************************************************************************/
  public static Decimal decimal(long value)
  {
    return new Decimal(Long.toString(value));
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------