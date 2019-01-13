// ---------------------------------------------------------------------------
// Task.java
// The task interface.
// $Id: Task.java,v 1.7 2018/05/29 12:54:47 schreine Exp $
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

public interface Task
{
  /****************************************************************************
   * Get folder containing current task.
   * @return the parent folder (may be null).
   ***************************************************************************/
  public TaskFolder getParent();

  /****************************************************************************
   * Get the name of the task.
   * @return the task name.
   ***************************************************************************/
  public String getName();
  
  /****************************************************************************
   * Get the optional (multi-line) description of the task.
   * @return the task description (may be null)
   ***************************************************************************/
  public String getDescription();
  
  /****************************************************************************
   * Get the optional (multi-line) definition of the task.
   * @return the task definition (may be null)
   ***************************************************************************/
  public String getDefinition();
  
  /****************************************************************************
   * Get executable function
   * @return the function (null, if none)
   ***************************************************************************/
  public FunctionSymbol getFunction();
  
  /****************************************************************************
   * Get source position.
   * @return the source position (null, if none)
   ***************************************************************************/
  public SourcePosition getSourcePosition();
  
  /****************************************************************************
   * Get auxiliary source positions.
   * @return the auxiliary source positions.
   ***************************************************************************/
  public List<SourcePosition> getAuxiliaryPositions();
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------