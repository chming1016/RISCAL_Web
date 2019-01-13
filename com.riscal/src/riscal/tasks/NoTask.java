// ---------------------------------------------------------------------------
// CheckTask.java
// A checking task.
// $Id: NoTask.java,v 1.1 2017/11/13 14:03:05 schreine Exp $
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

public class NoTask extends TaskBase
{
  /****************************************************************************
   * Construct non-executable task with denoted information.
   * @param parent the parent of the task
   * @param name its name
   * @param description its description (may be null)
   ***************************************************************************/
  public NoTask(TaskFolder parent, String name, String description)
  {
    super(parent, name, description, null);
    if (parent != null) parent.addTask(this);
  }
}
//-----------------------------------------------------------------------------
// end of file
//-----------------------------------------------------------------------------