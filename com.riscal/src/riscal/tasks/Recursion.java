// ---------------------------------------------------------------------------
// Recursion.java
// Determine recursive operation dependencies
// $Id: Recursion.java,v 1.4 2018/04/19 10:27:34 schreine Exp $
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

import java.util.*;

import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.types.Environment.Symbol.*;

public class Recursion extends ASTVisitor.Base<Void>
{
  /****************************************************************************
   * A representation of the call dependencies of a specification.
   ***************************************************************************/
  public static class CallMap
  {
    // the call dependencies
    private Map<FunctionSymbol,Set<FunctionSymbol>> calls = 
        new LinkedHashMap<FunctionSymbol,Set<FunctionSymbol>>();
    
    // the set to which caller is mapped by calls (allocates it, if not yet done)
    public Set<FunctionSymbol> getCalled(FunctionSymbol caller)
    {
      Set<FunctionSymbol> called = calls.get(caller);
      if (called == null)
      {
        called = new LinkedHashSet<FunctionSymbol>();
        calls.put(caller, called);
      }
      return called;
    }
    
    // adds a call of caller to callee
    public void addCall(FunctionSymbol caller, FunctionSymbol callee)
    {
      Set<FunctionSymbol> called = getCalled(caller);
      called.add(callee);
    }
    
    // returns true if caller calls callee
    public boolean calls(FunctionSymbol caller, FunctionSymbol callee)
    {
      return getCalled(caller).contains(callee);
    }
    
    // computes the transitive closure
    private void close()
    {
      // duplicate iteration sets since the originals may be modified
      boolean changed;
      do
      {
        changed = false;
        Set<FunctionSymbol> funs = calls.keySet();
        for (FunctionSymbol f : new LinkedHashSet<FunctionSymbol>(funs))
        {
          Set<FunctionSymbol> called = getCalled(f);
          int n = called.size();
          for (FunctionSymbol g : new LinkedHashSet<FunctionSymbol>(called))
          {
            Set<FunctionSymbol> called0 = getCalled(g);
            called.addAll(called0);
          }
          if (n != called.size()) changed = true;
        }
      }
      while (changed);
    }
    
    // print the call map
    public void print()
    {
      Set<FunctionSymbol> funs = calls.keySet();
      for (FunctionSymbol f : funs)
      {
        System.out.print(f.ident.string + " ->");
        Set<FunctionSymbol> called = getCalled(f);
        for (FunctionSymbol c : called)
          System.out.print(" " + c.ident.string);
        System.out.println();
      }
    }
  }
  
  private FunctionSymbol current = null;  // the current function visited
  private CallMap result = new CallMap(); // the call map computed by the visit
  
  /***************************************************************************
   * Returns the (indirect) call map of the specification.
   * @param spec the specification.
   * @return the map denoting the transitive closure of the direct call map
   **************************************************************************/
  public static CallMap analyze(Specification spec)
  {
    Recursion recursion = new Recursion();
    spec.accept(recursion);
    recursion.result.close();
    return recursion.result;
  }
  
  public Void visit(Declaration.FunctionDefinition def)
  {
    current = (FunctionSymbol)def.ident.getSymbol();
    super.visit(def);
    current = null;
    return null;
  }
  
  public Void visit(Declaration.PredicateDefinition def)
  {
    current = (FunctionSymbol)def.ident.getSymbol();
    super.visit(def);
    current = null;
    return null;
  }
  
  public Void visit(Declaration.TheoremParamDefinition def)
  {
    current = (FunctionSymbol)def.ident.getSymbol();
    super.visit(def);
    current = null;
    return null;
  }
  
  public Void visit(Declaration.ProcedureDefinition def)
  {
    current = (FunctionSymbol)def.ident.getSymbol();
    super.visit(def);
    current = null;
    return null;
  }
  
  public Void visit(Declaration.TypeDefinition def)
  {
    // subtype predicate cannot be called recursively
    /*
    if (def.exp == null) return super.visit(def);
    TypeSymbol symbol = (TypeSymbol)def.ident.getSymbol();
    SubType type = (SubType)symbol.getType();
    def.type.accept(this);
    current = type.pred;
    def.exp.accept(this);
    current = null;
    */
    return null;
  }
        
  public Void visit(Expression.ApplicationExp app)
  {
    FunctionSymbol fun = (FunctionSymbol)app.ident.getSymbol();
    result.addCall(current, fun);
    return null;
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------