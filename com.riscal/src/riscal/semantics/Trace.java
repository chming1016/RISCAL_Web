// ---------------------------------------------------------------------------
// Trace.java
// The trace of a procedure execution.
// $Id: Trace.java,v 1.5 2018/03/23 19:54:04 schreine Exp $
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
package riscal.semantics;

import java.util.*;

import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.semantics.Types.*;
import riscal.types.Environment.Symbol.*;

public class Trace
{
  // -------------------------------------------------------------------------
  // a trace entry
  // -------------------------------------------------------------------------
  public abstract static class Entry
  {
    public final Context context; // may be null
    public final AST phrase;      // may be null
    public Entry(Context context, AST phrase)
    {
      this.context = context == null ? null : new Context(context);
      this.phrase = phrase;
    }
    public String optionalString() { return null; }
    public static String toString(PatternCommand pcommand)
    {
      String tag = null;
      if (pcommand instanceof PatternCommand.IdentifierPatternCommand)
      {
        PatternCommand.IdentifierPatternCommand pcommand0 = 
            (PatternCommand.IdentifierPatternCommand)pcommand;
        tag = pcommand0.ident.string;
      }
      else if (pcommand instanceof PatternCommand.ApplicationPatternCommand)
      {
        PatternCommand.ApplicationPatternCommand pcommand0 = 
            (PatternCommand.ApplicationPatternCommand)pcommand;
        tag = pcommand0.ident.string + "(" +
            AST.toString(pcommand0.params, ", ") + ")";
      }
      return tag;
    }
    public static class Assignment extends Entry
    {
      public final Value value;
      public Assignment(Context context, Command command, Value value)
      {
        super(context, command);
        this.value = value;
      }
      public String toString()
      {
        return ((Command)phrase).toStringCore();
      }
      public String optionalString()
      {
        return value.toString();
      }
    }
    public static class Choice extends Entry
    {
      public final Value[] values; // may be null (choice failed)
      public Choice(Context context, AST command, Value[] values)
      {
        super(context, command);
        this.values = values;
      }
      public String toString()
      {
        if (phrase instanceof Command)
          return ((Command)phrase).toStringCore();
        else if (phrase instanceof PatternCommand.ApplicationPatternCommand)
        {
          PatternCommand.ApplicationPatternCommand phrase0 =
              (PatternCommand.ApplicationPatternCommand)phrase;
          return toString(phrase0);
        }
        else
          return phrase.toString();
      }
      public String optionalString()
      {
        return AST.toString(values, ", ");
      }
    }
    public static class BoolTest extends Entry
    {
      public final boolean value;
      public BoolTest(Context context, Expression cond, boolean value)
      {
        super(context, cond);
        this.value = value;
      }
      public String toString()
      {
        return phrase.toString();
      }
      public String optionalString()
      {
        return Boolean.toString(value);
      }
    }
    public static class MatchTest extends Entry
    {
      public final int index;
      public final Value[] values;
      public MatchTest(Context context, Command.MatchCommand command, 
        int index, Value[] values)
      {
        super(context, command);
        this.index = index;
        this.values = values;
      }
      public String toString()
      {
        Command.MatchCommand command = (Command.MatchCommand)phrase;
        return "match " + command.exp;
      }
      public String optionalString()
      {
        Command.MatchCommand command = (Command.MatchCommand)phrase;
        String tag = toString(command.pcommand[index]);
        return tag
            + (values.length == 0 ? "" : ": ")
            + AST.toString(values, ", ");
      }
    }
    public static class Application extends Entry
    {
      public Application(Expression.ApplicationExp exp)
      {
        super(null, exp);
      }
      public String toString()
      {
        return phrase.toString();
      }
    }
    public static class Arguments extends Entry
    {
      public ValueSymbol[] params;
      public final Value[] values;
      public Arguments(Context c, ValueSymbol[] params, Value[] values)
      {
        // context after entry of function
        super(c, null);
        this.params = params;
        this.values = values;
      }
      public String toString()
      {
        return "call";
      }
      public String optionalString()
      {
        StringBuffer result = new StringBuffer();
        int n = params.length;
        for (int i=0; i<n; i++)
        {
          result.append(params[i].ident.string);
          result.append(": ");
          result.append(values[i].toString());
          if (i < n-1) result.append(", ");
        }
        return result.toString();
      }
    }
    public static class Result extends Entry
    {
      public final Value value;
      public Result(Context context, Expression exp, Value value)
      {
        // context before return of function
        super(context, exp);
        this.value = value;
      }
      public String toString()
      {
        if (phrase == null)
          return "return;";
        else
          return "return " + phrase.toString() + ";";
      }
      public String optionalString()
      {
        if (phrase == null)
          return null;
        else
          return value.toString();
      }
    }
  }
  
  // the trace object (may be null)
  private static List<Entry> trace = null;
  
  /****************************************************************************
   * Create new trace object (deleting any previous one)
   ***************************************************************************/
  public static void create()
  {
    trace = new ArrayList<Entry>();
  }
  
  /****************************************************************************
   * Extract trace object (deleting the local reference), may return null
   ***************************************************************************/
  public static List<Entry> extract()
  {
    List<Entry> result = trace;
    trace = null;
    return result;
  }
  
  /****************************************************************************
   * Print trace object to stdiout ("no trace", if none)
   ***************************************************************************/
  public static void print()
  {
    if (trace == null)
    {
      System.out.println("(no trace)");
      return;
    }
    int n = trace.size();
    for (int i=0; i<n; i++)
    {
      Trace.Entry entry = trace.get(i);
      System.out.print(i + ":\t" + entry);
      String optional = entry.optionalString();
      if (optional != null) System.out.print(" (" + optional + ")");
      System.out.println();
    }
  }
  
  /****************************************************************************
   * Add assignment entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void assignment(Context context, 
    Command command, Value value)
  {
    if (trace == null) return;
    trace.add(new Entry.Assignment(context, command, value));
  }
  
  /****************************************************************************
   * Add choice entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void choice(Context context, 
    AST command, Value[] values)
  {
    if (trace == null) return;
    trace.add(new Entry.Choice(context, command, values));
  }
  
  /****************************************************************************
   * Add boolean test entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void booltest(Context context, 
    Expression exp, boolean value)
  {
    if (trace == null) return;
    trace.add(new Entry.BoolTest(context, exp, value));
  }
  
  /****************************************************************************
   * Add return entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void matchtest(Context context, 
    Command.MatchCommand command, int index, Value[] values)
  {
    if (trace == null) return;
    trace.add(new Entry.MatchTest(context, command, index, values));
  }
  
  /****************************************************************************
   * Add call entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void application(Expression.ApplicationExp exp)
  {
    if (trace == null) return;
    trace.add(new Entry.Application(exp));
  }
  
  /****************************************************************************
   * Add arguments entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void arguments(Context context,
    ValueSymbol[] params, Value[] values)
  {
    if (trace == null) return;
    trace.add(new Entry.Arguments(context, params, values));
  }
  
  /****************************************************************************
   * Add return entry (ignore, if there is no trace object)
   ***************************************************************************/
  public static void result(Context context, Expression exp, Value value)
  {
    if (trace == null) return;
    trace.add(new Entry.Result(context, exp, value));
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------