// ---------------------------------------------------------------------------
// CheckTask.java
// A checking task.
// $Id: CheckTask.java,v 1.6 2018/05/29 12:54:47 schreine Exp $
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
import riscal.syntax.AST.*;
import riscal.types.Environment.Symbol.*;

public class CheckTask extends TaskBase
{
  private final FunctionSymbol fun;
  private SourcePosition pos = null;
  
  /****************************************************************************
   * Construct check task with denoted information.
   * @param parent the parent of the task
   * @param name its name
   * @param description its description (may be null)
   * @param fun the symbol of the function to be executed by the task
   * @param decl its declaration
   ***************************************************************************/
  public CheckTask(TaskFolder parent, String name, String description,
    FunctionSymbol fun, Declaration decl)
  {
    super(parent, name, description, decl.toStringCore());
    this.fun = fun;
    if (parent != null) parent.addTask(this);
  }
  
  /***************************************************************************
   * Set new parent folder
   * @param parent the parent (null, if root)
   **************************************************************************/
  public void setParent(TaskFolder parent)
  {
    super.setParent(parent);
    if (parent != null) parent.addTask(this);
  }
  
  /****************************************************************************
   * Indicate whether task is executable.
   * @return true, if the task is executable
   ***************************************************************************/
  public boolean isExecutable()
  {
    return true;
  }
  
  /****************************************************************************
   * Get executable function
   * @return the function (null, if none)
   ***************************************************************************/
  public FunctionSymbol getFunction()
  {
    return fun;
  }
  
  /****************************************************************************
   * Get source position.
   * @return the source position (null, if none)
   ***************************************************************************/
  public SourcePosition getSourcePosition()
  {
    if (pos != null) return pos;
    return fun.ident.getPosition();
  }
  
  /****************************************************************************
   * Set source position.
   * @param pos the source position (null, if none)
   ***************************************************************************/
  public void setSourcePosition(SourcePosition pos)
  {
    this.pos = pos;
  }
  
  /****************************************************************************
   * Add auxiliary positions.
   * @param pos the positions.
   ***************************************************************************/
  public void addAuxiliaryPositions(List<SourcePosition> pos)
  {
    auxiliary.addAll(pos);
  }
}
//-----------------------------------------------------------------------------
// end of file
//-----------------------------------------------------------------------------