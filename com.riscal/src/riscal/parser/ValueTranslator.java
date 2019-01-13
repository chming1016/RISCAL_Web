// ---------------------------------------------------------------------------
// ASTVisitor.java
// Translation from ANTLR 4 parse trees to abstract syntax trees.
// $Id: ValueTranslator.java,v 1.1 2016/12/12 14:35:34 schreine Exp $
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
package riscal.parser;

import riscal.semantics.Types.*;
import java.util.*;
import org.antlr.v4.runtime.tree.*;

public class ValueTranslator extends ValueBaseVisitor<Value>
{
  public Value visitValueFile(ValueParser.ValueFileContext c)
  {
    return visit(c.value());
  }
  public Value.Unit visitUnit(ValueParser.UnitContext c)
  {
    return new Value.Unit();
  }
  public Value.Bool visitTrue(ValueParser.TrueContext c)
  {
    return new Value.Bool(true);
  }
  public Value.Bool visitFalse(ValueParser.FalseContext c)
  {
    return new Value.Bool(false);
  }
  public Value.Int visitDecimalNumber(ValueParser.DecimalNumberContext c)
  {
    Integer value = Integer.parseInt(c.DECIMAL().getText());
    return new Value.Int(value);
  }
  public Value.Int visitNegDecimalNumber(ValueParser.NegDecimalNumberContext c)
  {
    Integer value = Integer.parseInt(c.DECIMAL().getText());
    return new Value.Int(-value);
  }
  public Value.Set visitSet(ValueParser.SetContext c)
  {
    Value.Set result = new Value.Set();
    for (ValueParser.ValueContext c0 : c.value())
      result.add(visit(c0));
    return result;
  }
  public Value.Map visitMap(ValueParser.MapContext c)
  {
    Value.Map result = new Value.Map();
    List<ValueParser.ValueContext> values = c.value();
    int i = 0;
    int n = values.size();
    while (i < n)
    {
      Value key = visit(values.get(i));
      Value value = visit(values.get(i+1));
      result.put(key, value);
    }
    return result;
  }
  public Value.Array visitArray(ValueParser.ArrayContext c)
  {
    TerminalNode dec = c.DECIMAL();
    int option = dec == null ? -1 : Integer.parseInt(dec.getText());
    List<ValueParser.ValueContext> values = c.value();
    int n = values.size();
    Value.Array result = new Value.Array(option, n);
    for (int i=0; i<n; i++)
      result.set(i, visit(values.get(i)));
    return result;
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------