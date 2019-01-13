// ---------------------------------------------------------------------------
// Types.java
// The types used by the denotational semantics.
// $Id: Types.java,v 1.41 2018/03/23 12:57:51 schreine Exp $
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

import java.util.*;
import java.util.function.*;

import riscal.syntax.AST;
import riscal.syntax.AST.Type.IntType;
import riscal.types.Environment.Symbol.*;
import riscal.util.*;

public final class Types
{
  // -------------------------------------------------------------------------
  // 
  // Runtime Values
  //
  // -------------------------------------------------------------------------
  
  // a runtime value
  public static interface Value 
  { 
    public final class Unit implements Value
    { 
      public Unit() { } 
      public boolean equals(Object o) { return o instanceof Unit; }
      public int hashCode() { return 0; }
      public String toString() { return "()"; }
    }
    public final class Bool implements Value
    { 
      private final boolean value; 
      public Bool(boolean value) { this.value = value; } 
      public boolean getValue() { return value; }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof Bool)) return false;
        Bool b = (Bool)o;
        return value == b.value;
      }
      public int hashCode() { return value ? 1 : 0; }
      public String toString() { return Boolean.toString(value); }
    }
    public final class Int implements Value
    {
      private final int value;
      public Int(int value) { this.value = value; }
      public int getValue() { return value; }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof Int)) return false;
        Int i = (Int)o;
        return value == i.value;
      }
      public int hashCode() { return value; }
      public String toString() { return Integer.toString(value); }
    }
    public final class Set extends LinkedHashSet<Value> implements Value 
    { 
      public static final long serialVersionUID = 280967;
      public Set() { }
      public Set(Collection<Value> set) { super(set); }
      public int hashCode()
      {
        // dramatic improvement over standard hash function
        // http://stackoverflow.com/questions/1536393/good-hash-function-for-permutations
        Iterator<Value> itr = iterator();
        int hash = 1;
        int pos = size();
        while (--pos >= 0)
        {
          Value value = (Value)itr.next();
          hash *= 1779033703 + 2*value.hashCode();
        }
        return hash/2;
      }
      public String toString() 
      {
        StringBuffer buffer = new StringBuffer("{");
        int n = size();
        int i = 0;
        for (Value v : this)
        {
          buffer.append(v);
          i++;
          if (i < n) buffer.append(",");
        } 
        buffer.append("}");
        return buffer.toString();
      }
    }
    public final class Map extends LinkedHashMap<Value,Value> implements Value
    {
      public static final long serialVersionUID = 280967;
      public Map() { super(); }
      public Map(Map map) { super(map); }
      public String toString() 
      {
        StringBuffer buffer = new StringBuffer("<");
        int n = size();
        int i = 0;
        for (java.util.Map.Entry<Value,Value> e : entrySet())
        {
          buffer.append(e.getKey() + "->" + e.getValue());
          i++;
          if (i < n) buffer.append(",");
        } 
        buffer.append(">");
        return buffer.toString();
      }
    }
    public final class Array implements Value, Iterable<Value>
    {
      private final int option;
      private final Value[] values;
      public Array(int option, int n) 
      { 
        this.option = option;
        this.values = new Value[n]; 
      }
      public Array(int option, Value[] values) 
      { 
        this.option = option; 
        int n = values.length;
        this.values = new Value[n];
        for (int i=0; i<n; i++)
          this.values[i] = values[i];
      }
      public Array(int n) { this(-1, n); }
      public Array(Value[] values) { this(-1, values); }
      public Array(Array a) { this(a.option, a.values); }
      public Value[] get() { return values; }
      public Iterator<Value> iterator() { return Arrays.stream(values).iterator(); }
      public Value get(int index) { return values[index]; }
      public void set(int index, Value value) { values[index] = value; }
      public int size() { return values.length; }
      public int getOption() { return option; }
      public boolean equals(Object o) 
      { 
        if (!(o instanceof Array)) return false;
        Array a = (Array)o;
        if (option != a.option) return false;
        int n = values.length;
        if (n != a.values.length) return false;
        for (int i=0; i<n; i++)
          if (!values[i].equals(a.values[i])) return false;
        return true;
      }
      public int hashCode() 
      { 
        return Objects.hash(option, Arrays.hashCode(values)); 
      }
      public String toString() 
      {
        StringBuffer buffer = new StringBuffer();
        if (option != -1) buffer.append(option + ":");
        buffer.append("[");
        int n = values.length;
        for (int i=0; i<n; i++)
        {
          buffer.append(values[i]);
          if (i+1 < n) buffer.append(",");
        } 
        buffer.append("]");
        return buffer.toString();
      }
    }
  }
  
  /***************************************************************************
   * Determine whether map is implemented by array.
   * @param type the map type.
   * @return true iff the type denotes an array.
   **************************************************************************/
  public static boolean isArray(AST.Type.MapType type)
  {
    if (!(type.type1 instanceof IntType)) return false;
    IntType type1 = (IntType)type.type1;
    return type1.ivalue1 == 0;
  }
  
  // -------------------------------------------------------------------------
  // 
  // Execution contexts
  //
  // -------------------------------------------------------------------------
  
  // a map of function symbols to their measure lists
  public static class MeasureMap extends HashMap<FunctionSymbol, List<List<Value[]>>>
  {
    public static final long serialVersionUID = 280967;
    public MeasureMap() { }
    public MeasureMap(MeasureMap map) { super(map); }
  }
  
  // an execution context is a mapping of slots to values (of local variables)
  public final static class Context  
  { 
     // the sequence of variable slots
     private final Value[] array;
     
     // the sequence of variable names
     private final String[] names;
     
     // the current measures (null, if not set in initial call)
     private MeasureMap measures = null;
     
     /************************************************************************
      * Allocate context for denoted number of variable slots.
      * @param slots the number of variable slots.
      ***********************************************************************/
     public Context(int slots) 
     { 
       array = new Value[slots]; 
       names = new String[slots];
     }
     
     /************************************************************************
      * Get text representation of context as a sequence of lines
      ***********************************************************************/
     public String toString()
     {
       StringBuffer result = new StringBuffer();
       int n = array.length;
       for (int i=0; i<n; i++)
       {
         String name = names[i];
         if (name == null) continue;
         Value value = array[i];
         String value0 = value == null ? "(null)" : value.toString();
         result.append(name);
         result.append(": ");
         result.append(value0);
         if (i < n-1) result.append("\n");
       }
       return result.toString();
     }
     
     /************************************************************************
      * Copy a context.
      * @param c the context to be copied.
      ***********************************************************************/
     public Context(Context c) 
     {  
       this(c.array.length);
       int n = c.array.length;
       for (int i=0; i<n; i++)
       {
         array[i] = c.array[i];
         names[i] = c.names[i];
       }
       measures = c.measures;
     }  
     
     /************************************************************************
      * Allocate context for denoted number of variable slots
      * and fill the parameters with the denoted arguments.
      * @param slots the number of variable slots.
      * @param params the parameters
      * @param args the arguments
      * @param names their names
      ***********************************************************************/
     public Context(int slots, int[] params, Argument args, String[] names) 
     { 
       this.array = new Value[slots]; 
       this.names = new String[slots];
       set(params, args.values, names);
       setMeasures(args.measures);
     }
     
     /************************************************************************
      * Get the stored measures.
      * @return the measures
      ***********************************************************************/
     public MeasureMap getMeasures() 
     { 
       return measures; 
     }
     
     /*************************************************************************
      * Store the denoted measures.
      * @param measures the measures to be stored.
      ************************************************************************/
     public void setMeasures(MeasureMap measures) 
     { 
       this.measures = measures; 
     }
     
     /*************************************************************************
      * Get value of certain slot in this context.
      * @param slot the number of the slot.
      * @return the value.
      ************************************************************************/
     public Value get(int slot) 
     { 
       return array[slot]; 
     }
     
     /*************************************************************************
      * Get name of certain slot in this context.
      * @param slot the name of the slot.
      * @return the name
      ************************************************************************/
     public String getName(int slot) 
     { 
       return names[slot]; 
     }
     
    /**************************************************************************
     * Update this context by updating slot with value.
     * @param slot the number of the slot.
     * @param value the value.
     * @param the name of the slot.
     * @return this context.
     *************************************************************************/
    public Context set(int slot, Value value, String name)
    {
      array[slot] = value;
      names[slot] = name;
      return this;
    }
    
    /**************************************************************************
     * Update this context by updating multiple slots with values.
     * @param slots the numbers of the slots.
     * @param values the corresponding values.
     * @param names the corresponding names.
     * @return this context.
     *************************************************************************/
    public Context set(int[] slots, Value[] values, String[] names)
    {
      int n = slots.length;
      for (int i=0; i<n; i++)
      {
        int slot = slots[i];
        this.array[slot] = values[i];
        this.names[slot] = names[i];
      }
      return this;
    }
  }

  // -------------------------------------------------------------------------
  // 
  // Other semantic domains
  //
  // -------------------------------------------------------------------------
  
  // a command maps contexts to (single or multiple) contexts
  public static interface ComSem 
  {
    public interface Single extends ComSem, Function<Context,Context>
    { }
    public interface Multiple extends ComSem, Function<Context,Seq<Context>>
    { }
    public default ComSem.Multiple toMultiple()
    {
      if (this instanceof ComSem.Multiple) return (ComSem.Multiple)this;
      ComSem.Single s = (ComSem.Single)this;
      return (Context c)->Seq.cons(s.apply(new Context(c)), Seq.empty());
    }
  }
  
  // a function from values to command semantics
  public static interface FunComSem 
  {
    public interface Single extends FunComSem, Function<Value[],ComSem.Single>
    { }
    public interface Multiple extends FunComSem, Function<Value[],ComSem.Multiple>
    { }
    public default FunComSem.Multiple toMultiple()
    {
      if (this instanceof FunComSem.Multiple) return (FunComSem.Multiple)this;
      FunComSem.Single s = (FunComSem.Single)this;
      return (Value[] v)->(Context c)->Seq.cons(s.apply(v).apply(c), Seq.empty());
    }
  }
  
  // a function from a single value to command semantics
  public static interface SingleFunComSem 
  {
    public interface Single extends SingleFunComSem, Function<Value,ComSem.Single>
    { }
    public interface Multiple extends SingleFunComSem, Function<Value,ComSem.Multiple>
    { }
    public default SingleFunComSem.Multiple toMultiple()
    {
      if (this instanceof SingleFunComSem.Multiple) return (SingleFunComSem.Multiple)this;
      SingleFunComSem.Single s = (SingleFunComSem.Single)this;
      return (Value v)->(Context c)->Seq.cons(s.apply(v).apply(c), Seq.empty());
    }
  }
  
  // an expression maps contexts to (single or multiple) values
  public static interface ExpSem 
  {
    public interface Single extends ExpSem, Function<Context,Value>
    { }
    public interface Multiple extends ExpSem, Function<Context,Seq<Value>>
    { }
    public default ExpSem.Multiple toMultiple()
    {
      if (this instanceof ExpSem.Multiple) return (ExpSem.Multiple)this;
      ExpSem.Single s = (ExpSem.Single)this;
      return (Context c)->Seq.cons(s.apply(c), Seq.empty());
    }
  }
  
  // a function from values to expression semantics
  public static interface FunExpSem 
  {
    public interface Single extends FunExpSem, Function<Value[],ExpSem.Single>
    { }
    public interface Multiple extends FunExpSem, Function<Value[],ExpSem.Multiple>
    { }
    public default FunExpSem.Multiple toMultiple()
    {
      if (this instanceof FunExpSem.Multiple) return (FunExpSem.Multiple)this;
      FunExpSem.Single s = (FunExpSem.Single)this;
      return (Value[] v)->(Context c)->Seq.cons(s.apply(v).apply(c), Seq.empty());
    }
  }
  
  // a quantified variable maps contexts to (single or multiple) lists of value tuples
  public static interface QvarSem 
  {
    public interface Single extends QvarSem, Function<Context,Seq<Value[]>>
    { }
    public interface Multiple extends QvarSem, Function<Context,Seq<Seq<Value[]>>>
    { }
    public default QvarSem.Multiple toMultiple()
    {
      if (this instanceof QvarSem.Multiple) return (QvarSem.Multiple)this;
      QvarSem.Single s = (QvarSem.Single)this;
      return (Context c)->Seq.cons(s.apply(c), Seq.empty());
    }
  }
  
  // a quantified variable core maps contexts to (single or multiple) lists of values
  public static interface QvarCoreSem 
  {
    public interface Single extends QvarCoreSem, Function<Context,Seq<Value>>
    { }
    public interface Multiple extends QvarCoreSem, Function<Context,Seq<Seq<Value>>>
    { }
    public default QvarCoreSem.Multiple toMultiple()
    {
      if (this instanceof QvarCoreSem.Multiple) return (QvarCoreSem.Multiple)this;
      QvarCoreSem.Single s = (QvarCoreSem.Single)this;
      return (Context c)->Seq.cons(s.apply(c), Seq.empty());
    }
  }
  
  // a binder maps contexts to (single or multiple) contexts
  public static interface BinderSem 
  {
    public interface Single extends BinderSem, Function<Context,Context>
    { }
    public interface Multiple extends BinderSem, Function<Context,Seq<Context>>
    { }
    public default BinderSem.Multiple toMultiple()
    {
      if (this instanceof BinderSem.Multiple) return (BinderSem.Multiple)this;
      BinderSem.Single s = (BinderSem.Single)this;
      return (Context c)->Seq.cons(s.apply(c), Seq.empty());
    }
  }

  // the semantics of a loop specification, an invariant or a variant
  public static interface LoopSpecSem 
  {
    public interface Invariant extends LoopSpecSem { }
    public interface Variant extends LoopSpecSem { }
  }
  
  // the semantics of a function specification, a pre/postcondition or a variant
  public static interface FunSpecSem 
  {
    public interface Precondition extends FunSpecSem { }
    public interface Postcondition extends FunSpecSem { }
    public interface Variant extends FunSpecSem { }
    public interface Inline extends FunSpecSem { }
  }
  
  // a condition maps the current context to a boolean
  public static interface ContextCondition 
  extends FunSpecSem.Precondition, FunSpecSem.Postcondition
  {
    public interface Single extends ContextCondition, Function<Context,Value.Bool>
    { }
    public interface Multiple extends ContextCondition, Function<Context,Seq<Value.Bool>>
    { }
    public default ContextCondition.Multiple toMultiple()
    {
      if (this instanceof ContextCondition.Multiple) return (ContextCondition.Multiple)this;
      ContextCondition.Single s = (ContextCondition.Single)this;
      return (Context c)->Seq.cons(s.apply(c), Seq.empty());
    }
  }

  // a relation maps the current context and the old one to (single or multiple) booleans
  public static interface ContextRelation extends LoopSpecSem.Invariant
  {
    public interface Single extends ContextRelation, BiFunction<Context,Context,Value.Bool>
    { }
    public interface Multiple extends ContextRelation, BiFunction<Context,Context,Seq<Value.Bool>>
    { }
    public default ContextRelation.Multiple toMultiple()
    {
      if (this instanceof ContextRelation.Multiple) return (ContextRelation.Multiple)this;
      ContextRelation.Single s = (ContextRelation.Single)this;
      return (Context c1, Context c2)->Seq.cons(s.apply(c1,c2), Seq.empty());
    }
  }
  
  // a function maps the current context to (single or multiple) value sequences
  public static interface ContextFunction 
  extends LoopSpecSem.Variant, FunSpecSem.Variant
  {
    public interface Single extends ContextFunction, Function<Context,Value[]>
    { }
    public interface Multiple extends ContextFunction, Function<Context,Seq<Value[]>>
    { }
    public default ContextFunction.Multiple toMultiple()
    {
      if (this instanceof ContextFunction.Multiple) return (ContextFunction.Multiple)this;
      ContextFunction.Single s = (ContextFunction.Single)this;
      return (Context c)->Seq.cons(s.apply(c), Seq.empty());
    }
  }
  
  // a function argument
  public static class Argument
  {
    public final Value[] values;
    public final MeasureMap measures;
    public Argument(Value[] args, MeasureMap measures)
    {
      this.values = args;
      this.measures = measures;
    }
    public Argument(Value[] values)
    {
      this(values, new MeasureMap());
    }
  }
  
  // a function maps a sequence of arguments to (single or multiple) results
  public static interface FunSem 
  { 
    public interface Single extends FunSem, Function<Argument,Value>
    { }
    public interface Multiple extends FunSem, Function<Argument,Seq<Value>>
    { }
    public default FunSem.Multiple toMultiple()
    {
      if (this instanceof FunSem.Multiple) return (FunSem.Multiple)this;
      FunSem.Single s = (FunSem.Single)this;
      return (Argument v)->Seq.cons(s.apply(v), Seq.empty());
    }
  }
  
  // -------------------------------------------------------------------------
  // 
  // Auxiliaries
  //
  // -------------------------------------------------------------------------
  
  /****************************************************************************
   * Create stream of all combinations of values of the given streams
   * @param seq the streams
   * @return the stream of combinations.
   ***************************************************************************/
  public static Seq<Value[]> getCombinations(Seq<Value>[] seq)
  {
    // the temporary array for producing the next combination
    int n = seq.length;
    Value[] value = new Value[n]; 
    if (n == 0) return Seq.cons(value, Seq.empty());
    
    // the already produced values and how many have been used so far
    @SuppressWarnings("unchecked")
    List<Value>[] list = new ArrayList[n];
    for (int i=0; i<n; i++) list[i] = new ArrayList<Value>();
    int[] used = new int[n];

    // the values are provided by (recursive) application of this function
    return Seq.supplier(()->getCombinations(0, value, seq, list, used));
  }

  /***************************************************************************
   * Get sequence of combinations starting from current state in the
   * tree of values
   * @param n the number of the next slot to be filled 'value' 
   * @param value the (partial) combination of values determined so far 
   * @param seq the sequences of values for the individual expressions
   * @param list the values already produced for the individual expressions
   * @param used the indices of the next values to be used from 'list'
   * @param combine the combination functions
   * @return the sequence of values.
   **************************************************************************/
  private static Seq.Next<Value[]> getCombinations(int n, Value[] value,
    Seq<Value>[] seq, List<Value>[] list, int used[])
  {
    // the following simulates a depth-first left-to-right search in
    // the value tree with backtracking
    while (n < seq.length)
    {
      // take next value from already produced values
      if (used[n] < list[n].size())
      {
        value[n] = list[n].get(used[n]);
        used[n]++;
        n++;
      }
      else
      {
        // produce next value
        Seq.Next<Value> next = seq[n].get();
        if (next == null)
        {
          // no such value, backtrack
          if (n == 0) return null;
          used[n] = 0;
          n--;
        }
        else
        {
          // use and remember newly produced value
          seq[n] = next.tail;
          value[n] = next.head;
          list[n].add(used[n], value[n]);
          used[n]++;
          n++;
        }
      }
    }
    // construct combination and remember state for producing next value
    Value[] head = new Value[n];
    for (int i=0; i<n; i++) head[i] = value[i];
    final int n0 = n-1;
    Seq<Value[]> tail = Seq.supplier(()->
      getCombinations(n0, value, seq, list, used));
    return new Seq.Next<Value[]>(head, tail);
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------