// ---------------------------------------------------------------------------
// Environment.java
// The environment for type checking and translation.
// $Id: Environment.java,v 1.29 2018/05/16 15:01:23 schreine Exp $
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
package riscal.types;

import java.util.*;

import riscal.syntax.*;
import riscal.syntax.AST.Type;
import riscal.types.Environment.Symbol.*;
import riscal.semantics.Types.*;
import riscal.util.*;
import riscal.tasks.*;

public class Environment
{
  // the generic table type we use
  private class Map<S> extends LinkedHashMap<String,S>
  {
    private static final long serialVersionUID = 28091967L;
    public Map() { super(); }
    public Map(Map<S> t) { super(t); }
  }
  
  // the separate name spaces (functions may be overloaded)
  private Map<TypeSymbol> types;
  private Map<ValueSymbol> values;
  private Map<List<FunctionSymbol>> functions;
  
  public static abstract class Symbol
  {
    public final AST.Identifier ident; // the identifier
    protected Symbol(AST.Identifier ident) 
    { 
      // link symbol to identifier and vice versa
      assert ident == null;
      this.ident = ident; 
      ident.setSymbol(this);
    }
    
    // the information on named types
    public final static class TypeSymbol extends Symbol
    {
      private AST.Type type; // may be temporarily null for recursive types
      public TypeSymbol(AST.Identifier ident, AST.Type type) 
      { 
        super(ident);
        this.type = type; 
      }
      public AST.Type getType() { return type; }
      public void setType(AST.Type type) { this.type = type; }
      public String toString() { return ident.string + ":" + type; }
    }

    // the information on values and variables
    public final static class ValueSymbol extends Symbol
    {
      // the kind of declaration
      // global value: stored in global frame
      // local value or variable: stored in local frame 
      public static enum Kind { global, value, variable; }
      public final AST.Type type;        // its type
      public final Kind kind;            // the declaration kind
      
      public ValueSymbol(AST.Identifier ident, AST.Type type, Kind kind)
      {
        super(ident);
        this.type = type;
        this.kind = kind;
      }
      public String toString() 
      { 
        return ident.string + ":" +
               (kind == Kind.global ? "global" : 
                kind == Kind.value ? "value" : "variable") 
                + ":" + (kind == Kind.global ? value : slot) + ":" + type; 
      }
      
      // the value of the object (null, if unknown)
      private Value value = null;              
      public Value getValue() { return value; }
      public void setValue(Value value) { this.value = value; }
      
      // the value sequence of the object (null, if unknown)
      private Seq<Value> values = null;              
      public Seq<Value> getValues() { return values; }
      public void setValues(Seq<Value> values) { this.values = values; }
      
      // if local, the frame slot of the value (-1, if unknown)
      private int slot = -1;
      public int getSlot() { return slot; }
      public void setSlot(int slot) { this.slot = slot; }
      
      // the number of slots in the "function" frame (-1, if unknown)
      private int slots = -1;
      public int getSlots() { return slots; }
      public void setSlots(int slots) { this.slots = slots; }
      
      // the syntactic (declaration) type (null, if unknown)
      private Type stype = null;
      public Type getSyntacticType() { return stype; }
      public void setSyntacticType(Type stype) { this.stype = stype; }
    }

    // the information of functions, predicates, and procedures
    public final static class FunctionSymbol extends Symbol
    {
      public final boolean multiple;     // is the function non-deterministic?
      public final ValueSymbol[] params; // its parameters
      public final AST.Type[] types;     // the argument types
      public final AST.Type type;        // the result type (Bool for predicates)
      
      public FunctionSymbol(boolean multiple, 
        AST.Identifier ident, ValueSymbol[] params, AST.Type type)
      {
        super(ident);
        this.multiple = multiple;
        this.params = params;
        this.types = new Type[params.length];
        for (int i=0; i<params.length; i++) types[i] = params[i].type;
        this.type = type;
      }
      
      // the definition of the symbol (null, if only declared)
      private AST.Declaration def = null;
      public boolean isDefined() { return def != null; }
      public AST.Declaration getDefinition() { return def; }
      public void makeDefined(AST.Declaration def) { this.def = def; }
      public String toString() 
      { 
        return ident.string + ":" + isDefined() + ":" + slots + ":" + 
          AST.toString(types, ",") + ":" + type; 
      }
      
      // the value of the object (null, if unknown)
      private FunSem value = null;              
      public FunSem getValue() { return value; }
      public void setValue(FunSem value) { this.value = value; }
      
      // the preconditions of the functions (null, if none)
      private List<ContextCondition> pre = null;
      public List<ContextCondition> getPreconditions() { return pre; }
      public void setPreconditions(List<ContextCondition> pre) { this.pre = pre; }
      
      // the number of slots in the function frame (-1, if unknown)
      private int slots = -1;
      public int getSlots() { return slots; }
      public void setSlots(int slots) { this.slots = slots; }
      
      // the result symbol (null, if unknown)
      private ValueSymbol result = null;
      public ValueSymbol getResult() { return result; }
      public void setResult(ValueSymbol result) { this.result = result; }
      
      // folder of associated tasks (may be null)
      private TaskFolder tasks = null;
      public TaskFolder getTaskFolder() { return tasks; }
      public void setTaskFolder(TaskFolder tasks) { this.tasks = tasks; }
    }
  }
  
  /***************************************************************************
   * Create an empty environment
   **************************************************************************/
  public Environment()
  {
    this.types = new Map<TypeSymbol>();
    this.values = new Map<ValueSymbol>();
    this.functions = new Map<List<FunctionSymbol>>();
  }
  
  /***************************************************************************
   * Create a duplicate of a given environment.
   * @param env the environment to be duplicated
   **************************************************************************/
  public Environment(Environment env)
  {
    this.types = new Map<TypeSymbol>(env.types);
    this.values = new Map<ValueSymbol>(env.values);
    this.functions = new Map<List<FunctionSymbol>>(env.functions);
  }
  
  /**************************************************************************
   * Put a type into the environment (overriding any entry with same name)
   * @param ident the identifier of the type
   * @param type the type itself (must have canonical version)
   * @return the new type symbol 
   *************************************************************************/
  public TypeSymbol putType(AST.Identifier ident, AST.Type type)
  {
    TypeSymbol symbol = new TypeSymbol(ident, type);
    putTypeSymbol(symbol);
    return symbol;
  }
  
  /**************************************************************************
   * Put type symbol into environment (overriding any entry with same name)
   * @param symbol the symbol
   *************************************************************************/
  public void putTypeSymbol(TypeSymbol symbol)
  {
    types.put(symbol.ident.string, symbol);
  }
  
  /**************************************************************************
   * Put a value into the environment (overriding any entry with same name)
   * @param ident the identifier of the value
   * @param type the type of the value (must have canonical version)
   * @param level the declaration kind
   * @return the new value symbol 
   *************************************************************************/
  public ValueSymbol putValue(AST.Identifier ident, AST.Type type, 
    ValueSymbol.Kind kind)
  {
    ValueSymbol symbol = new ValueSymbol(ident, type, kind);
    putValueSymbol(symbol);
    return symbol;
  }
  
  /**************************************************************************
   * Put value symbol into environment (overriding any entry with same name)
   * @param symbol the symbol
   *************************************************************************/
  public void putValueSymbol(ValueSymbol symbol)
  {
    values.put(symbol.ident.string, symbol);
  }
  
  /**************************************************************************
   * Put a function into the environment (overriding any entry with same 
   * name and argument types)
   * @param multiple is the function non-deterministic?
   * @param ident the identifier of the function
   * @param params the parameter symbols 
   * @param type the result type (Bool for predicates, must have canonical v.)
   * @return the function symbol
   *************************************************************************/
  public FunctionSymbol putFunction(boolean multiple,
    AST.Identifier ident, ValueSymbol[] params,
    AST.Type type)
  {
    FunctionSymbol symbol = new FunctionSymbol(multiple, ident, params, type);
    putFunctionSymbol(symbol);
    return symbol;
  }
  
  /**************************************************************************
   * Put function symbol into environment (overriding any entry with same name)
   * @param symbol the symbol
   *************************************************************************/
  public void putFunctionSymbol(FunctionSymbol symbol)
  {
    List<FunctionSymbol> symbols = functions.get(symbol.ident.string);
    if (symbols == null)
    {
      symbols = new LinkedList<FunctionSymbol>();
      symbols.add(symbol);
      functions.put(symbol.ident.string, symbols);
      return;
    }
    FunctionSymbol symbol0 = null;
    for (FunctionSymbol s : symbols)
    {
      if (Arrays.equals(s.types, symbol.types))
      {
        symbol0 = s;
        break;
      }
    }
    if (symbol0 != null) symbols.remove(symbol0);
    symbols.add(symbol);
  }
  
  /****************************************************************************
   * Returns the type associated to the given identifier
   * (sets the symbol of the identifier as a side effect).
   * @param ident the identifier
   * @return the associated type (null, if none)
   ***************************************************************************/
  public TypeSymbol getType(AST.Identifier ident)
  {
    TypeSymbol symbol = types.get(ident.string);
    ident.setSymbol(symbol);
    return symbol;
  }
  
  /****************************************************************************
   * Get all types in current environment.
   * @return the collection of all type symbols.
   ***************************************************************************/
  public Collection<TypeSymbol> getTypes()
  {
    return types.values();
  }
  
  /****************************************************************************
   * Returns the value associated to the given identifier
   * (sets the symbol of the identifier as a side effect).
   * @param ident the identifier
   * @return the associated value (null, if none)
   ***************************************************************************/
  public ValueSymbol getValue(AST.Identifier ident)
  {
    ValueSymbol symbol = values.get(ident.string);
    ident.setSymbol(symbol);
    return symbol;
  }
  
  /****************************************************************************
   * Get all values in current environment.
   * @return the collection of all value symbols.
   ***************************************************************************/
  public Collection<ValueSymbol> getValues()
  {
    return values.values();
  }
  
  /****************************************************************************
   * Returns the functions associated to the given identifier
   * @param ident the identifier
   * @return the associated functions (null, if none)
   ***************************************************************************/
  public List<FunctionSymbol> getFunctions(AST.Identifier ident)
  {
    return functions.get(ident.string);
  }
  
  /****************************************************************************
   * Get all function in current environment.
   * @return the collection of all value symbols.
   ***************************************************************************/
  public Collection<FunctionSymbol> getFunctions()
  {
    List<FunctionSymbol> result = new ArrayList<FunctionSymbol>();
    for (Collection<FunctionSymbol> fs : functions.values())
      result.addAll(fs);
    return result;
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------