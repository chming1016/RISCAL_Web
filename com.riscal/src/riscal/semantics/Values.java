// ---------------------------------------------------------------------------
// Types.java
// The types used by the denotational semantics.
// $Id: Values.java,v 1.27 2018/03/21 17:43:08 schreine Exp $
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
import riscal.syntax.AST.RecursiveIdentifier.*;
import riscal.syntax.AST.Type.*;
import riscal.Main;
import riscal.semantics.Types.*;
import riscal.types.*;
import riscal.types.Environment.Symbol.*;
import riscal.util.*;

import java.util.*;
import java.math.*;

public final class Values
{
  // set of (type,list) pairs created so far
  private static Map<Type,List<Value>> typeMap;
  
  // set of (int,value) pairs created so far
  private static Map<Integer,Value.Int> intMap;
  
  // empty set of values
  private static Value.Set emptySet = new Value.Set();
  
  static
  {
    typeMap = new HashMap<Type,List<Value>>();
    
    List<Value>  unitList = new ArrayList<Value>(1);
    unitList.add(new Value.Unit());
    typeMap.put(new Type.UnitType(), unitList);
    
    List<Value> boolList = new ArrayList<Value>(2);
    boolList.add(new Value.Bool(false));
    boolList.add(new Value.Bool(true));
    typeMap.put(new Type.BoolType(), boolList);
    
    intMap = new HashMap<Integer,Value.Int>();
  }
  
  /***************************************************************************
   * Check whether a value of a given type satisfies the range restrictions
   * of a weakly matching type.
   * @param value the value
   * @param vtype the type of the value, weakly matching stype.
   * @param stype the static type whose size restrictions are to be satisfied.
   * @return true if the value satisfies the range restrictions of the type.
   **************************************************************************/
  public static boolean checkSize(Value value, Type vtype, Type stype)
  {
    if (stype instanceof SubType)
    {
      SubType stype0 = (SubType)stype;
      return checkSize(value, vtype, stype0.type);
    }
    if (vtype instanceof SubType)
    {
      SubType vtype0 = (SubType)vtype;
      return checkSize(value, vtype0.type, stype);
    }
    if (stype instanceof UnitType) return true;
    if (stype instanceof BoolType) return true;
    if (stype instanceof IntType)
    {
      IntType stype0 = (IntType)stype;
      Value.Int value0 = (Value.Int)value;
      int v = value0.getValue();
      return stype0.ivalue1 <= v && v <= stype0.ivalue2;
    }
    if (stype instanceof SetType)
    {
      SetType stype0 = (SetType)stype;
      Value.Set value0 = (Value.Set)value;
      if (value0.size() > stype0.getSize()) return false;
      SetType vtype0 = (SetType)vtype;
      if (!TypeChecker.matchesStrong(vtype0.type, stype0.type))
      {
        for (Value v : value0)
        {
          if (!checkSize(v, vtype0.type, stype0.type)) return false;
        }
      }
      return true;
    }
    if (stype instanceof MapType)
    {
      MapType stype0 = (MapType)stype;
      MapType vtype0 = (MapType)vtype;
      if (Types.isArray(stype0))
      {
        Value.Array value0 = (Value.Array)value;
        if (!TypeChecker.matchesStrong(vtype0.type2, stype0.type2))
        {
          int n = value0.size();
          for (int i=0; i<n; i++)
          {
            if (!checkSize(value0.get(i), vtype0.type2, stype0.type2)) return false;
          }
        }
        return true;
      }
      else
      {
        Value.Map value0 = (Value.Map)value;
        if (!TypeChecker.matchesStrong(vtype0.type1, stype0.type1))
        {
          for (Value v : value0.keySet())
          {
            if (!checkSize(v, vtype0.type1, stype0.type1)) return false;
          }
        }
        if (!TypeChecker.matchesStrong(vtype0.type2, stype0.type2))
        {
          for (Value v : value0.values())
          {
            if (!checkSize(v, vtype0.type2, stype0.type2)) return false;
          }
        }
        return true;
      }
    }
    if (stype instanceof TupleType)
    {
      TupleType stype0 = (TupleType)stype;
      TupleType vtype0 = (TupleType)vtype;
      Value.Array value0 = (Value.Array)value;
      int n = stype0.types.length;
      for (int i=0; i<n; i++)
      {
        Type s0 = stype0.types[i];
        Type v0 = vtype0.types[i];
        if (TypeChecker.matchesStrong(v0, s0)) continue;
        if (!checkSize(value0.get(i), v0, s0)) return false;
      }
      return true;
    }
    if (stype instanceof RecordType)
    {
      RecordType stype0 = (RecordType)stype;
      RecordType vtype0 = (RecordType)vtype;
      Value.Array value0 = (Value.Array)value;
      int n = stype0.symbols.length;
      for (int i=0; i<n; i++)
      {
        ValueSymbol s0 = (ValueSymbol)stype0.symbols[i];
        ValueSymbol v0 = (ValueSymbol)vtype0.symbols[i];
        if (TypeChecker.matchesStrong(v0.type, s0.type)) continue;
        if (!checkSize(value0.get(i), v0.type, s0.type)) return false;
      }
      return true;
    }
    if (stype instanceof RecursiveType)
    {
      RecursiveType stype0 = (RecursiveType)stype;
      RecursiveType vtype0 = (RecursiveType)vtype;
      RecursiveTypeItem sitem = stype0.ritem;
      RecursiveTypeItem vitem = vtype0.ritem;
      Value.Array value0 = (Value.Array)value;
      int option = value0.getOption();
      RecursiveIdentifier sident = sitem.ridents[option];
      RecursiveIdentifier vident = vitem.ridents[option];
      if (sident instanceof RecIdentifier) return true;
      RecApplication sident0 = (RecApplication)sident;
      RecApplication vident0 = (RecApplication)vident;
      int n = sident0.types.length;
      for (int i=0; i<n; i++)
      {
        Type s0 = sident0.types[i];
        Type v0 = vident0.types[i];
        if (TypeChecker.matchesStrong(v0, s0)) continue;
        if (!checkSize(value0.get(i), v0, s0)) return false;
      }
      int depth = getDepth(value, stype0, stype0.symbols);
      return depth <= stype0.ivalue;
    }
    return false;
  }
    
  /***************************************************************************
   * Get depth of value counting the application of the 
   * constructors of the given recursive types.
   * @param value the value
   * @param type the type of the value
   * @param symbols the symbols of the recursive types to be counted.
   * @return the depth of the value, i.e., the maximum number of 
   *         applications of recursive type constructors from the
   *         given set of types in the value respectively its components.
   **************************************************************************/
  private static int getDepth(Value value, Type type, TypeSymbol[] symbols)
  {
    if (type instanceof UnitType) return 0;
    if (type instanceof BoolType) return 0;
    if (type instanceof IntType) return 0;
    if (type instanceof SetType)
    {
      SetType type0 = (SetType)type;
      Value.Set value0 = (Value.Set)value;
      return maxDepth(value0, type0.type, symbols);
    }
    if (type instanceof SubType)
    {
      SubType type0 = (SubType)type;
      return getDepth(value, type0.type, symbols);
    }
    if (type instanceof MapType)
    {
      MapType type0 = (MapType)type;
      if (Types.isArray(type0))
      {
        Value.Array value0 = (Value.Array)value;
        return maxDepth(value0, type0.type2, symbols);
      }
      else
      {
        Value.Map value0 = (Value.Map)value;
        int depth1 = maxDepth(value0.keySet(), type0.type1, symbols);
        int depth2 = maxDepth(value0.values(), type0.type1, symbols);
        return Math.max(depth1, depth2);
      }
    }
    if (type instanceof TupleType)
    {
      TupleType type0 = (TupleType)type;
      Value.Array value0 = (Value.Array)value;
      int n = type0.types.length;
      int depth = 0;
      for (int i=0; i<n; i++)
      {
        int d = getDepth(value0.get(i), type0.types[i], symbols);
        if (d > depth) depth = d;
      }
      return depth;
    }
    if (type instanceof RecordType)
    {
      RecordType type0 = (RecordType)type;
      ValueSymbol[] vsymbols = type0.symbols;
      Value.Array value0 = (Value.Array)value;
      int n = symbols.length;
      int depth = 0;
      for (int i=0; i<n; i++)
      {
        int d = getDepth(value0.get(i), vsymbols[i].type, symbols);
        if (d > depth) depth = d;
      }
      return depth;
    }
    if (type instanceof RecursiveType)
    {
      RecursiveType type0 = (RecursiveType)type;
      Value.Array value0 = (Value.Array)value;
      int option = value0.getOption();
      TypeSymbol symbol = (TypeSymbol)type0.ritem.ident.getSymbol();
      RecursiveIdentifier rident = type0.ritem.ridents[option];
      if (rident instanceof RecIdentifier) return 0;
      RecApplication rident0 = (RecApplication)rident;
      FunctionSymbol fsymbol = (FunctionSymbol)rident0.ident.getSymbol();
      int n = fsymbol.types.length;
      int depth = 0;
      for (int i=0; i<n; i++)
      {
        int d = getDepth(value0.get(i), fsymbol.types[i], symbols);
        if (d > depth) depth = d;
      }
      for (TypeSymbol s : symbols)
      {
        if (symbol == s) return 1+depth;
      }
      return depth;
    }
    return -1;
  }
  private static int maxDepth(Iterable<Value> value, Type type, TypeSymbol[] symbols)
  {
    int depth = 0;
    for (Value v : value)
    {
      int d = getDepth(v, type, symbols);
      if (d > depth) depth = d;
    }
    return depth;
  }
  
  /***************************************************************************
   * Determine the size of an array type.
   * @param type the map type.
   * @return the size of the array (-1, if map is not implemented by array)
   **************************************************************************/
  public static int getArraySize(AST.Type.MapType type)
  {
    if (!(type.type1 instanceof IntType)) return -1;
    IntType type1 = (IntType)type.type1;
    if (type1.ivalue1 != 0) return -1;
    if (type1.ivalue2 > Integer.MAX_VALUE-1)
      throw new RuntimeException("type size is too large");
    return (int)(type1.ivalue2+1L);
  }
  
  /***************************************************************************
   * Get list of all values of a canonical type (eager version).
   * @param type a canonical type where type.size() is representable as an int.
   * @return the list of all its values (may throw an exception, 
   *   if size of type is too large)
   **************************************************************************/
  public static List<Value> getValueListEager(Type type) 
  { 
    // potentially return a previously created list
    List<Value> result = typeMap.get(type);
    if (result != null) return result;
        
    if (type instanceof IntType)
    {
      IntType type0 = (IntType)type;
      result = getIntegerList(type0.ivalue1, type0.ivalue2);
    }
    else if (type instanceof SetType)
    {
      SetType type0 = (SetType)type;
      List<Value> elems = getValueList(type0.type);
      result = getSetList(elems);
    }
    else if (type instanceof MapType)
    {
      MapType type0 = (MapType)type;
      List<Value> values = getValueList(type0.type2);
      int size = getArraySize(type0);
      if (size != -1)
        result = getArrayList(size, values);
      else
      {
        List<Value> keys = getValueList(type0.type1);
        result = getMapList(keys, values);
      }
    }
    else if (type instanceof TupleType)
    {
      TupleType type0 = (TupleType)type;
      @SuppressWarnings("unchecked")
      List<Value>[] values = Arrays.stream(type0.types)
      .map(Values::getValueList).toArray(List[]::new);
      result = getTupleList(values);
    }
    else if (type instanceof RecordType)
    {
      RecordType type0 = (RecordType)type;
      ValueSymbol[] symbols = type0.symbols;
      @SuppressWarnings("unchecked")
      List<Value>[] values = Arrays.stream(symbols)
      .map((ValueSymbol symbol)->getValueList(symbol.type))
      .toArray(List[]::new);
      result = getTupleList(values);
    }
    else if (type instanceof RecursiveType)
    {
      RecursiveType type0 = (RecursiveType)type;
      result = getRecursiveList(type0);
    }
    
    // remember the newly created list
    typeMap.put(type, result);
    return result;
  }
  
  /***************************************************************************
   * Get list of all values of a canonical type (lazy version).
   * @param type a canonical type where type.size() is representable as an int.
   * @return the list of all its values (may throw an exception, 
   *   if size of type is too large)
   **************************************************************************/
  public static SeqList<Value> getValueList(Type type)
  {
    return new SeqList<Value>(getValueSeq(type), type.getSize());
  }
  
  /***************************************************************************
   * An array list backed by a lazy sequence
   **************************************************************************/
  private static class SeqList<T> extends ArrayList<T>
  {
    public static final long serialVersionUID = 28091967;
    
    private Seq<T> seq; // the not yet consumed part of the sequence
    private long size;  // the total number of elements
    
    public SeqList(Seq<T> seq, long size)
    {
      this.seq = seq;
      this.size = size;
    }
 
    public T get(int index)
    {
      int size = super.size();
      for (int i=size; i<index+1; i++)
      {
        Seq.Next<T> next = seq.get();
        if (next == null) throw new RuntimeException("sequence is empty");
        super.add(next.head);
        seq = next.tail;
      }
      return super.get(index);
    }
    
    public int size()
    {
      // delay error until size is actually requested
      if (size > Integer.MAX_VALUE) 
        throw new RuntimeException("type size " + size + " is too large");
      return (int)size;
    }
    
    public long sizeLong()
    {
      return size;
    }
  }
  
  /***************************************************************************
   * Get lazy sequence of all values of a canonical type.
   * @param type a canonical type where type.size() is representable as an int.
   * @return the sequence of all its values (may throw an exception, 
   *   if size of type is too large)
   **************************************************************************/
  public static Seq<Value> getValueSeq(Type type) 
  {      
    // we mostly enumerate all elements of subtypes in a non-lazy way 
    // (since *we* are lazy and it is not clear whether the effort would be 
    // worthwhile; the access patterns are pretty random, anyway)
    if (type instanceof BoolType)
    {
      return Seq.cons(new Value.Bool(false), 
          Seq.cons(new Value.Bool(true), Seq.empty()));
    }
    if (type instanceof IntType)
    {
      IntType type0 = (IntType)type;
      return Seq.supplier(()->getIntegerNext(type0.ivalue1, type0.ivalue2));
    }
    if (type instanceof SetType)
    {
      SetType type0 = (SetType)type;
      List<Value> elems = getValueList(type0.type);
      int n = elems.size();
      if (n <= 62) 
        return Seq.supplier(()->getSetNext(elems, 0L, 1L << n));
      else
        return Seq.supplier(()->getSetNext(elems, BigInteger.ZERO, 
            BigInteger.ONE.shiftLeft(n)));
    }
    if (type instanceof SubType)
    {
      SubType type0 = (SubType)type;
      Seq<Value> elems = getValueSeq(type0.type);
      FunSem.Single fun = (FunSem.Single)type0.pred.getValue();
      return new Seq.SeqFilter<Value>(elems,
          (Value value)->
      {
        Value.Bool result = (Value.Bool)fun.apply(new Argument(new Value[] { value }));
        return result.getValue();
      });
    }
    if (type instanceof MapType)
    {
      MapType type0 = (MapType)type;
      List<Value> values = getValueList(type0.type2);
      int size = getArraySize(type0);
      if (size != -1) 
      {
        int[] counter = new int[size];
        return Seq.supplier(()->getArrayNext(values, counter));
      }
      List<Value> keys = getValueList(type0.type1);
      int[] counter = new int[keys.size()];
      return Seq.supplier(()->getMapNext(keys, values, counter));
    }
    if (type instanceof TupleType)
    {
      TupleType type0 = (TupleType)type;
      @SuppressWarnings("unchecked")
      SeqList<Value>[] values = Arrays.stream(type0.types)
      .map(Values::getValueList).toArray(SeqList[]::new);
      long maxsize = Arrays.stream(type0.types)
          .mapToLong((Type t)->t.getSize()).max().getAsLong();
      if (maxsize <= Integer.MAX_VALUE)
      {
        int[] counter = new int[values.length];
        return Seq.supplier(()->getTupleNext(values, counter));
      }
      else
      {
        long[] counter = new long[values.length];
        return Seq.supplier(()->getTupleNext(values, counter));
      }
    }
    if (type instanceof RecordType)
    {
      RecordType type0 = (RecordType)type;
      ValueSymbol[] symbols = type0.symbols;
      @SuppressWarnings("unchecked")
      SeqList<Value>[] values = Arrays.stream(symbols)
      .map((ValueSymbol symbol)->getValueList(symbol.type))
      .toArray(SeqList[]::new);
      long maxsize = Arrays.stream(symbols)
          .mapToLong((ValueSymbol symbol)->symbol.type.getSize())
          .max().getAsLong();
      if (maxsize <= Integer.MAX_VALUE)
      {
        int[] counter = new int[values.length];
        return Seq.supplier(()->getTupleNext(values, counter));
      }
      else
      {
        long[] counter = new long[values.length];
        return Seq.supplier(()->getTupleNext(values, counter));
      }
    }
    if (type instanceof RecursiveType)
    {
      // no laziness here, we (currently) shy away from implementation effort
      RecursiveType type0 = (RecursiveType)type;
      return Seq.list(getRecursiveList(type0));
    }
    return null;
  }
  
  /****************************************************************************
   * Get (potentially previously created) Int value for given int.
   * @param i the int value
   * @return the corresponding Int value.
   ***************************************************************************/
  private static Value.Int getInt(int i)
  {
    Value.Int value = intMap.get(i);
    if (value == null)
    {
      value = new Value.Int(i);
      intMap.put(i, value);
    }
    return value;
  }
  
  /****************************************************************************
   * Get list of all integers in interval.
   * @param from the lower bound of the interval.
   * @param to the upper bound of the interval.
   * @return the list of all integers in the interval.
   ***************************************************************************/
  private static List<Value> getIntegerList(long from0, long to0)
  {
    if (from0 < Integer.MIN_VALUE || to0 > Integer.MAX_VALUE)
      throw new RuntimeException("type size is too large");
    int from = (int)from0;
    int to = (int)to0;
    int n = Math.max(0, to-from+1);
    List<Value> result = new ArrayList<Value>(n);
    for (int i = from; i <= to; i++)
    {
      Main.checkStopped();
      result.add(getInt(i));
    }
    return result;
  }
  
  /****************************************************************************
   * Get next integer in interval.
   * @param from the lower bound of the interval.
   * @param to the upper bound of the interval.
   * @return the next integer in the interval (null, if none).
   ***************************************************************************/
  private static Seq.Next<Value> getIntegerNext(long from0, long to0)
  {
    Main.checkStopped();
    if (from0 < Integer.MIN_VALUE || to0 > Integer.MAX_VALUE)
      throw new RuntimeException("type size is too large");
    if (from0 > to0) return null;
    return new Seq.Next<Value>(getInt((int)from0), 
        Seq.supplier(()->getIntegerNext(from0+1, to0)));
  }
  
  /****************************************************************************
   * Get list of all sets of values taken from a collection of elements.
   * @param elems the collection of elements.
   * @return the list of all subsets.
   ***************************************************************************/
  public static List<Value> getSetList(Collection<Value> elems)
  {
    int n = elems.size();
    List<Value> result = new ArrayList<Value>(1 << n);
    result.add(emptySet);
    int r = 1;
    for (Value value : elems)
    {
      Main.checkStopped();
      for (int j=0; j<r; j++)
      {
        Main.checkStopped();
        Value.Set s0 = (Value.Set)result.get(j);
        Value.Set s1 = new Value.Set(s0);
        s1.add(value);
        result.add(s1);
      }
      r *= 2;
    }
    return result;
  }
  
  /****************************************************************************
   * Get list of all sets of values taken from a collection of elements.
   * @param elems the collection of elements.
   * @param min the minimum number of elements in a subset
   * @param max the maximum number of elements in a subset
   * @return the list of all subsets.
   ***************************************************************************/
  public static List<Value> getSetList(Collection<Value> elems, int min, int max)
  {
    List<Value> result = new ArrayList<Value>();
    int s = 1 << elems.size();
    for (int i=0; i<s; i++)
    {
      int j = i;
      int c = 0;
      while (j > 0) { if ((j&1)==1) c++; j = j>>1; }
      if (c < min || c > max) continue;
      Value.Set set = new Value.Set();
      j = i;
      for (Value e : elems)
      {
        if (j == 0) break;
        if ((j&1)==1) set.add(e); 
        j = j>>1;
      }
      result.add(set);
    }
    return result;
  }
  
  /****************************************************************************
   * Get next set of values taken from a collection of elements.
   * @param elems the collection of elements.
   * @param i the index of the first set
   * @param n the total number of sets
   * @return the next subset (null, if none).
   ***************************************************************************/
  public static Seq.Next<Value> getSetNext(List<Value> elems, long i, long n)
  {
    if (i == n) return null;
    Value.Set result = new Value.Set();
    long j = i;
    int elem = 0;
    while (j > 0)
    {
      Main.checkStopped();
      if ((j & 1) != 0) result.add(elems.get(elem));
      j = j >> 1;
      elem++;
    }
    return new Seq.Next<Value>(result,
        Seq.supplier(()->getSetNext(elems, i+1, n)));
  }
  
  /****************************************************************************
   * Get next set of values taken from a collection of elements.
   * @param elems the collection of elements.
   * @param i the index of the first set
   * @param n the total number of sets
   * @return the next subset (null, if none).
   ***************************************************************************/
  public static Seq.Next<Value> getSetNext(List<Value> elems, BigInteger i, BigInteger n)
  {
    if (i.equals(n)) return null;
    Value.Set result = new Value.Set();
    BigInteger j = i;
    int elem = 0;
    while (j.compareTo(BigInteger.ZERO) > 0)
    {
      Main.checkStopped();
      if (j.testBit(0)) result.add(elems.get(elem));
      j = j.shiftRight(1);
      elem++;
    }
    return new Seq.Next<Value>(result,
        Seq.supplier(()->getSetNext(elems, i.add(BigInteger.ONE), n)));
  }
  
  /****************************************************************************
   * Get list of all arrays of length n filled with the denoted values.
   * @param n the length of the array.
   * @param values the list to draw the values from.
   * @return the resulting list of all arrays.
   ***************************************************************************/
  private static List<Value> getArrayList(int n, List<Value> values)
  {
    int m = values.size();          // the number of values
    int s = (int)Arith.power(m, n); // the number of arrays
    
    // create all arrays, initially with size 0, will ultimately receive size n
    List<Value> result = new ArrayList<Value>(s);
    for (int i=0; i<s; i++)
    {
      Main.checkStopped();
      result.add(new Value.Array(n));
    }
    
    // fill all arrays with values
    int d = 1;              // number of subsequent arrays with same value at index i
    for (int i=0; i<n; i++) // add value for index i to each array
    {
      Main.checkStopped();
      int j = 0;            // the index of the current value
      int p = 0;            // the position of the current array in the list
      while (p < s)         // add value to array at every position in list
      {
        Main.checkStopped();
        Value value = values.get(j); // the current value
        for (int k=0; k<d; k++)      // add same value to d arrays
        {
          Main.checkStopped();
          Value.Array array = (Value.Array)result.get(p);
          array.set(i, value);
          p++;
        }
        j = (j+1)%m;        // switch to next value
      }
      d *= m;               // m-times more arrays get same value in next index
    }
    return result;
  }
  
  /****************************************************************************
   * Get next array filled with the denoted values.
   * @param values the list to draw the values from.
   * @param counter the index vector for the values of the next array
   * @return the next array
   ***************************************************************************/
  private static Seq.Next<Value> getArrayNext(List<Value> values, int[] counter)
  {
    Main.checkStopped();
    
    // the number of values and the length of the array
    int s = values.size();
    int n = counter.length;
     
    // determine the result
    Value.Array result = new Value.Array(n);
    for (int i=0; i<n; i++)
      result.set(i, values.get(counter[i]));

    // increment the counter;
    int[] counter0 = new int[n];
    int carry = 1;
    for (int i=0; i<n; i++)
    {
      counter0[i] = counter[i] + carry;
      if (counter0[i] == s)
      {
        counter0[i] = 0;
        carry = 1;
      }
      else
        carry = 0;
    }
    
    // overflow indicates the last value
    Seq<Value> rest = 
        carry == 1 ? Seq.supplier(()->null) : 
          Seq.supplier(()->getArrayNext(values, counter0));
    return new Seq.Next<Value>(result, rest);
  }
  
  /****************************************************************************
   * Get list of all maps for denoted keys filled with the denoted values.
   * @param keys the list to draw the keys from.
   * @param values the list to draw the values from.
   * @return the resulting list of all maps.
   ***************************************************************************/
  private static List<Value> getMapList(List<Value> keys, List<Value> values)
  {
    int n = keys.size();            // the number of keys
    int m = values.size();          // the number of values
    int s = (int)Arith.power(m, n); // the number of arrays
    
    // create all maps, initially empty, will ultimately receive n elements
    List<Value> result = new ArrayList<Value>(s);
    for (int i=0; i<s; i++)
    {
      Main.checkStopped();
      result.add(new Value.Map());
    }
    
    // fill all maps with (key,value)-pairs
    int d = 1;              // number of subsequent maps with same value for key i
    for (int i=0; i<n; i++) // add value for key i to each array
    {
      Main.checkStopped();
      Value key = keys.get(i); // the key i
      int j = 0;               // the index of the current value
      int p = 0;               // the position of the current map in the list
      while (p < s)            // add value to map at every position in list
      {
        Main.checkStopped();
        Value value = values.get(j); // the current value
        for (int k=0; k<d; k++)      // add same value to d maps
        {
          Main.checkStopped();
          Value.Map map = (Value.Map)result.get(p);
          map.put(key, value);
          p++;
        }
        j = (j+1)%m;        // switch to next value
      }
      d *= m;               // m-times more maps get same value for next key
    }
    return result;
  }
  
  /****************************************************************************
   * Get next map for the denoted keys filled with the denoted values.
   * @param keys the list to draw the keys from.
   * @param values the list to draw the values from.
   * @param counter the index vector for the values of the next map.
   * @return the next map.
   ***************************************************************************/
  private static Seq.Next<Value> getMapNext(List<Value> keys,
    List<Value> values, int[] counter)
  {
    Main.checkStopped();
    
    // the number of values and the length of the array
    int s = values.size();
    int n = counter.length;
     
    // determine the result
    Value.Map result = new Value.Map();
    for (int i=0; i<n; i++)
      result.put(keys.get(i), values.get(counter[i]));
    
    // increment the counter;
    int[] counter0 = new int[n];
    int carry = 1;
    for (int i=0; i<n; i++)
    {
      counter0[i] = counter[i] + carry;
      if (counter0[i] == s)
      {
        counter0[i] = 0;
        carry = 1;
      }
      else
        carry = 0;
    }
    
    // overflow indicates the last value
    Seq<Value> rest = 
        carry == 1 ? Seq.supplier(()->null) : 
          Seq.supplier(()->getMapNext(keys, values, counter0));
    return new Seq.Next<Value>(result, rest);
  }
  
  
  /***************************************************************************
   * Get list of all tuples with components from the given value lists.
   * @param values the lists of component values.
   * @return the list of all tuples.
   **************************************************************************/
  public static List<Value> getTupleList(List<Value>[] values)
  {
    List<Value> result = new ArrayList<Value>();
    addCombinations(values, -1, result);
    return result;
  }
  
  /***************************************************************************
   * Add to result list of all combinations with components from given lists.
   * @param values the lists of component values.
   * @param option the option value (may be -1)
   * @return the list of all combinations.
   **************************************************************************/
  private static void addCombinations(List<Value>[] values, 
    int option, List<Value> result)
  {
    // determine number of combinations
    int n = values.length;   // the number of components
    int s = 1;               // the number of tuples 
    for (int i=0; i<n; i++) s *= values[i].size();
    
    // create all combinations, indices in result range from r to r+s
    int r = result.size();
    for (int i=0; i<s; i++)
    {
      Main.checkStopped();
      result.add(new Value.Array(option, new Value[n]));
    }
    
    // fill all combinations with values
    int d = 1;              // number of subsequent combs with same value at component i
    for (int i=0; i<n; i++) // set value for component i in each combination
    {
      Main.checkStopped();
      int m = values[i].size(); // the number of values for this component
      int j = 0;                // the index of the current value
      int p = 0;                // the position of the combination in the list
      while (p < s)             // set value for every combination in the list
      {
        Main.checkStopped();
        Value value = values[i].get(j); // the current value
        for (int k=0; k<d; k++)         // set same value for d combinations
        {
          Main.checkStopped();
          Value elem = result.get(r+p);
          Value.Array array = (Value.Array)elem;
          array.set(i, value);
          p++;
        }
        j = (j+1)%m;       // switch to next value
      }
      d *= m;              // m-times more tuples get same value in next index
    }
  }
  
  /****************************************************************************
   * Get next tuple filled with the denoted values.
   * @param values the array of lists to draw the values from.
   * @param counter the index vector for the values of the next tuple
   * @return the next tuple
   ***************************************************************************/
  private static Seq.Next<Value> getTupleNext(List<Value>[] values, int[] counter)
  {
    Main.checkStopped();
    
    // the number of values and the length of the array
    int n = counter.length;
     
    // determine the result
    Value.Array result = new Value.Array(n);
    for (int i=0; i<n; i++)
      result.set(i, values[i].get(counter[i]));
    
    // increment the counter;
    int[] counter0 = new int[n];
    int carry = 1;
    for (int i=0; i<n; i++)
    {
      counter0[i] = counter[i] + carry;
      if (counter0[i] == values[i].size())
      {
        counter0[i] = 0;
        carry = 1;
      }
      else
        carry = 0;
    }
    
    // overflow indicates the last value
    Seq<Value> rest = 
        carry == 1 ? Seq.supplier(()->null) : 
          Seq.supplier(()->getTupleNext(values, counter0));
    return new Seq.Next<Value>(result, rest);
  }
  
  /****************************************************************************
   * Get next tuple filled with the denoted values.
   * @param values the array of lists to draw the values from.
   * @param counter the index vector for the values of the next tuple
   * @return the next tuple
   ***************************************************************************/
  private static Seq.Next<Value> getTupleNext(SeqList<Value>[] values, long[] counter)
  {
    Main.checkStopped();
    
    // the number of values and the length of the array
    int n = counter.length;
     
    // determine the result
    Value.Array result = new Value.Array(n);
    for (int i=0; i<n; i++)
    {
      if (counter[i] > Integer.MAX_VALUE)
        throw new RuntimeException("type size is too large");
      result.set(i, values[i].get((int)counter[i]));
    }
    
    // increment the counter;
    long[] counter0 = new long[n];
    long carry = 1;
    for (int i=0; i<n; i++)
    {
      counter0[i] = counter[i] + carry;
      if (counter0[i] == values[i].sizeLong())
      {
        counter0[i] = 0;
        carry = 1;
      }
      else
        carry = 0;
    }
    
    // overflow indicates the last value
    Seq<Value> rest = 
        carry == 1 ? Seq.supplier(()->null) : 
          Seq.supplier(()->getTupleNext(values, counter0));
    return new Seq.Next<Value>(result, rest);
  }
  
  /***************************************************************************
   * Get list of all values of the recursive type.
   * @param type the recursive type.
   * @return the list of all values of the type.
   **************************************************************************/
  private static List<Value> getRecursiveList(RecursiveType type)
  { 
    // next[] receives the trees of depth =(n) generated in the
    // current iteration from the following arrays:
    // - prev[] has the trees of depth <(n-1) generated in previous iterations
    // - last[] has the trees of depth =(n-1)
    // - values[] has the trees of depth <=(n-1), it is stored in typeMap

    // generate prev[], last[], values[]
    TypeSymbol[] symbols = type.symbols;
    int n = symbols.length;
    @SuppressWarnings("unchecked")
    List<Value>[] prev = new ArrayList[n];
    @SuppressWarnings("unchecked")
    List<Value>[] last = new ArrayList[n];
    @SuppressWarnings("unchecked")
    List<Value>[] values = new ArrayList[n];
    
    // populate type lists with constants
    for (int i=0; i<n; i++)
    {
      Main.checkStopped();
      RecursiveType t = (RecursiveType)symbols[i].getType();
      RecursiveIdentifier[] ridents = t.ritem.ridents;
      prev[i] = new ArrayList<Value>();
      last[i] = new ArrayList<Value>();
      values[i] = new ArrayList<Value>();
      int m = ridents.length;
      for (int j=0; j<m; j++)
      {
        Main.checkStopped();
        if (!(ridents[j] instanceof RecIdentifier)) continue;
        Value.Array value = new Value.Array(j, new Value[]{ });
        last[i].add(value);
        values[i].add(value);
      }
      typeMap.put(t, values[i]);
    }

    // extend type lists in 'depth' iterations with function applications
    long depth = type.ivalue;
    for (int level = 0; level < depth; level++)
    {
      Main.checkStopped();
      
      // generate next[]
      @SuppressWarnings("unchecked")
      List<Value>[] next = new ArrayList[n];  
      
      // populate next[i] 
      for (int i=0; i<n; i++)                 
      {
        Main.checkStopped();
        next[i] = new ArrayList<Value>();
        RecursiveType t = (RecursiveType)symbols[i].getType();
        RecursiveIdentifier[] ridents = t.ritem.ridents;
        
        // populate next[i] with option j
        int m = ridents.length;
        for (int j=0; j<m; j++)               
        {
          Main.checkStopped();
          
          if (!(ridents[j] instanceof RecApplication)) continue;
          FunctionSymbol fun = (FunctionSymbol)ridents[j].ident.getSymbol();
          int o = fun.types.length;
          
          // determine indices of argument types in current recursion cycle
          Map<Integer,Integer> indices = new HashMap<Integer,Integer>();
          for (int k=0; k<o; k++)
          {
            Main.checkStopped();
            
            Type t0 = fun.types[k];
            if (t0 instanceof RecursiveType)
            {
              RecursiveType t1 = (RecursiveType)t0;
              TypeSymbol s = (TypeSymbol)t1.ritem.ident.getSymbol();
              for (int p = 0; p < n; p++)
              {
                if (s == symbols[p]) 
                {
                  indices.put(k, p);
                  break;
                }
              }
            }
          }   
              
          // if no recursive type occurs as argument or in first
          // iteration take all values
          int s = indices.size();
          if (s == 0 || level == 0)
          {
            @SuppressWarnings("unchecked")
            List<Value>[] args = new ArrayList[o];
            for (int l=0; l<o; l++)
            {
              Integer p = indices.get(l);
              if (p == null)
                args[l] = getValueList(fun.types[l]);
              else
                args[l] = last[p];
            }
            addCombinations(args, j, next[i]);
            continue;
          }
          
          // generate all applications of option j where 
          // - at least one component takes a value from last[] and
          // - all other components take values from prev[]
          // there are (2^s)-1 such possible combinations
          int cases = 1 << s;
          for (int k=1; k<cases; k++)
          {
            Main.checkStopped();
            
            @SuppressWarnings("unchecked")
            List<Value>[] args = new ArrayList[o];
            int l0 = 0;
            for (int l=0; l<o; l++)
            {
              Main.checkStopped();
              
              Integer p = indices.get(l);
              if (p != null)
              {
                args[l] = ((k >> l0) & 1) == 1 ? last[p] : prev[p];
                l0++;
              }
              else
                args[l] = getValueList(fun.types[l]);  
            }
            addCombinations(args, j, next[i]);
          }
        }
      }
      
      // update prev[] values[] and last[]
      for (int i=0; i<n; i++) 
      {
        Main.checkStopped();
        prev[i].addAll(last[i]);
        values[i].addAll(next[i]);
        last[i] = next[i];
      }
    }
    
    // the resulting type
    TypeSymbol s = (TypeSymbol)type.ritem.ident.getSymbol();
    return typeMap.get(s.getType());
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------