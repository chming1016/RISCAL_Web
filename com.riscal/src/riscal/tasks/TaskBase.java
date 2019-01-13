// ---------------------------------------------------------------------------
// Task.java
// base class for a checking task.
// $Id: TaskBase.java,v 1.9 2018/05/29 12:54:47 schreine Exp $
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
import riscal.syntax.AST.SourcePosition;
import riscal.types.Environment.Symbol.*;

public class TaskBase implements Task
{
  protected TaskFolder parent;        // its parent
  protected final String name;        // name of the task
  protected final String description; // its description (may be null)
  protected final String definition;  // its definition (may be null)
  
  // auxiliary source positions
  protected final List<SourcePosition> auxiliary = new ArrayList<SourcePosition>();
  
  /****************************************************************************
   * Construct task with denoted information.
   * @param parent the parent of the task (null for a root task)
   * @param name its name
   * @param description its description (may be null)
   * @param definition its definition (may be null)
   ***************************************************************************/
  protected TaskBase(TaskFolder parent, String name, 
    String description, String definition)
  {
    this.parent = parent;
    this.name = name.replace('\n', ' ');
    if (description != null)
    {
      description = description.replace('\n', ' ');
      if (description.length() > 60)
        description = description.substring(0, 60) + "...";
    }
    this.description = description;
    this.definition = definition;
  }

  /***************************************************************************
   * Get parent folder
   * @return the parent (null, if root)
   **************************************************************************/
  public TaskFolder getParent()
  {
    return parent;
  }
  
  /***************************************************************************
   * Set new parent folder
   * @param parent the parent (null, if root)
   **************************************************************************/
  public void setParent(TaskFolder parent)
  {
    this.parent = parent;
  }
  
  /****************************************************************************
   * Get name of task.
   * @return the task name.
   ***************************************************************************/
  public String getName()
  {
    return name;
  }
  
  /****************************************************************************
   * Get the optional (multi-line) description of the task.
   * @return the task description (may be null)
   ***************************************************************************/
  public String getDescription()
  {
    return description;
  }
  
  /****************************************************************************
   * Get the optional (multi-line) definition of the task.
   * @return the task definition (may be null)
   ***************************************************************************/
  public String getDefinition()
  {
    return definition;
  }
  
  /****************************************************************************
   * Get executable function
   * @return the function (null, if none)
   ***************************************************************************/
  public FunctionSymbol getFunction()
  {
    return null;
  }
  
  /****************************************************************************
   * Get source position.
   * @return the source position (null, if none)
   ***************************************************************************/
  public SourcePosition getSourcePosition()
  {
    return null;
  }
  
  /****************************************************************************
   * Get auxiliary source positions.
   * @return the auxiliary source positions.
   ***************************************************************************/
  public List<SourcePosition> getAuxiliaryPositions()
  {
    return auxiliary;
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------