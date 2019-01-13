// ---------------------------------------------------------------------------
// Seq.java
// Lazily evaluated generic sequences.
// $Id: Seq.java,v 1.12 2016/10/13 15:54:18 schreine Exp $
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
package riscal.util;

import java.util.*;
import java.util.function.*;

import riscal.Main;

// a sequence of T-values supplies Next<T> nodes
public interface Seq<T> extends Supplier<Seq.Next<T>>
{ 
  // constructing an empty sequence
  public static<T> Seq<T> empty() 
  { return new SeqEmpty<T>(); }
  
  // constructing a sequence from a head element and a tail
  public static<T> Seq<T> cons(T head, Seq<T> tail)
  { return new SeqCons<T>(head, tail); }
  
  // constructing a sequence from a supplier only
  public static<T> Seq<T> supplier(Supplier<Seq.Next<T>> supplier)
  { return new SeqSupplier<T>(supplier); }

  // construct sequence from list
  public static<T> Seq<T> list(List<T> list) 
  { return new SeqList<T>(0, list); }
  
  // unroll this sequence to list
  public default List<T> list() 
  { 
    Seq<T> seq = this;
    List<T> result = new ArrayList<T>();
    while (true)
    {
      Next<T> next = seq.get();
      if (next == null) break;
      result.add(next.head);
      seq = next.tail;
    }
    return result; 
  }
  
  // only preserve element that satisfies given predicate
  public default Seq<T> filter(Predicate<T> p)
  { return new SeqFilter<T>(this, p); }
  
  // construct sequence from two sequences
  public static<T> Seq<T> append(Seq<T> seq1, Seq<T> seq2)
  { return new SeqAppend<T>(seq1, seq2); }
    
  // applying a function to the sequence (and joining the results)
  public<R> Seq<R> apply(Function<T,R> f);
  public<R> Seq<R> applyJoin(Function<T,Seq<R>> f);

  // an element of the sequence
  public final static class Next<T>
  {
    public final T head;
    public final Seq<T> tail;
    public Next(T head, Seq<T> tail)
    {
      this.head = head;
      this.tail = tail;
    }
  }
 
  // the base class of sequence implementations
  public static abstract class SeqClass<T> implements Seq<T>
  {
    public abstract Next<T> get();
    public<R> Seq<R> apply(Function<T,R> f) { return new SeqApply<T,R>(f, this); }
    public<R> Seq<R> applyJoin(Function<T,Seq<R>> f){ return new SeqApplyJoin<T,R>(f, this); }
  }
    
  // an empty sequence of T-values
  public final static class SeqEmpty<T> extends SeqClass<T> implements Seq<T>
  {
    public SeqEmpty() { }
    public Next<T> get() { return null; }
  }
  
  // a non-empty sequence of T-values
  public final static class SeqCons<T> extends SeqClass<T> implements Seq<T>
  {
    private final Next<T> next;
    public SeqCons(T head, Seq<T> tail) { next = new Next<T>(head, tail); }
    public Next<T> get() { Main.checkStopped(); return next; }
  }
  
  // a sequence of T-values provided by a function
  public final static class SeqSupplier<T> extends SeqClass<T> implements Seq<T>
  {
    private final Supplier<Seq.Next<T>> supplier;
    public SeqSupplier(Supplier<Seq.Next<T>> supplier) { this.supplier = supplier; }
    public Next<T> get() { Main.checkStopped(); return supplier.get(); }
  }
  
  // a sequence of T-values provided by two sequences
  public final static class SeqAppend<T> extends SeqClass<T> implements Seq<T>
  {
    private final Seq<T> seq1; private final Seq<T> seq2;
    public SeqAppend(Seq<T> seq1, Seq<T> seq2) { this.seq1 = seq1; this.seq2 = seq2; }
    public Next<T> get() 
    { 
      Main.checkStopped();
      Next<T> next = seq1.get();
      if (next == null) 
        return seq2.get();
      else
        return new Next<T>(next.head, new SeqAppend<T>(next.tail, seq2));
    }
  }

  // a sequence of R-values derived from a sequence of T values by
  // application of a function f:T->R 
  public final static class SeqApply<T,R> extends SeqClass<R> implements Seq<R>
  {
    private final Function<T,R> f; private final Seq<T> seq;
    public SeqApply(Function<T,R> f, Seq<T> seq) { this.f = f; this.seq = seq; }
    public Next<R> get() 
    { 
      Main.checkStopped();
      Next<T> next = seq.get();
      if (next == null) return null;
      return new Next<R>(f.apply(next.head), 
                         new SeqApply<T,R>(f, next.tail)); 
    }
  }
  
  // a sequence of R-values derived from a sequence of T values by
  // application of a function f:T->Seq<R> by joining the results 
  public final static class SeqApplyJoin<T,R> extends SeqClass<R> implements Seq<R>
  {
    private final Function<T,Seq<R>> f;
    private final Seq<R> seqR; private final Seq<T> seqT;
    public SeqApplyJoin(Function<T,Seq<R>> f, Seq<T> seqT) 
    { this(f, new SeqEmpty<R>(), seqT); }
    private SeqApplyJoin(Function<T,Seq<R>> f, 
      Seq<R> seqR, Seq<T> seqT) 
    { this.f = f; this.seqR = seqR; this.seqT = seqT; }
    public Next<R> get() 
    { 
      Seq<R> seqR0 = seqR;
      Seq<T> seqT0 = seqT; 
      while (true)
      {
        Main.checkStopped();
        Next<R> next = seqR0.get();
        if (next != null) 
        {
          final Seq<T> seqT1 = seqT0;
          return new Next<R>(next.head, 
            new SeqApplyJoin<T,R>(f, next.tail, seqT1));
        }
        Next<T> nextT = seqT0.get();
        if (nextT == null) return null;
        seqR0 = f.apply(nextT.head);
        seqT0 = nextT.tail;
      }
    }
  }
 
  // a non-empty sequence of T-values
  public final static class SeqList<T> extends SeqClass<T> implements Seq<T>
  {
    private final int index;
    private final List<T> list;
    public SeqList(int index, List<T> list) 
    { this.index = index; this.list = list; }
    public Next<T> get() 
    { 
      Main.checkStopped();
      if (index == list.size())
        return null;
      else
        return new Next<T>(list.get(index), new SeqList<T>(index+1, list)); 
    }
  }  
  
  // those elements of the given sequence that satisfy the predicate
  public final static class SeqFilter<T> extends SeqClass<T> implements Seq<T>
  {
    private final Seq<T> seq;
    private final Predicate<T> pred;    
    public SeqFilter(Seq<T> seq, Predicate<T> pred) 
    { this.seq = seq; this.pred = pred; }
    public Next<T> get() 
    { 
      Main.checkStopped();
      Seq<T> seq0 = seq;
      T value;
      do
      {
        Main.checkStopped();
        Next<T> next = seq0.get();
        if (next == null) return null;
        value = next.head;
        seq0 = next.tail;
      }
      while (!pred.test(value));
      return new Seq.Next<T>(value, new SeqFilter<T>(seq0, pred));
    }
  }  
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------