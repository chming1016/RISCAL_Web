// ---------------------------------------------------------------------------
// TaskFolder.java
// A folder of checking tasks.
// $Id: TaskFolder.java,v 1.4 2017/11/13 11:00:45 schreine Exp $
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

public class TaskFolder extends TaskBase
{
  private Collection<Task> tasks;         // the non-folder tasks
  private Collection<TaskFolder> folders; // the subfolders

  /****************************************************************************
   * Construct root folder.
   * @param parent the parent of the folder (null, if this task is root).
   * @param name the name of the folder.   
   * @param description a description of its contents (may be null)
   ***************************************************************************/
  public TaskFolder(TaskFolder parent, String name, String description)
  {
    super(parent, name, description, null);
    this.tasks = new ArrayList<Task>();
    this.folders = new ArrayList<TaskFolder>();
    if (parent != null) parent.addFolder(this);
  }
  
  /***************************************************************************
   * Set new parent folder
   * @param parent the parent (null, if root)
   **************************************************************************/
  public void setParent(TaskFolder parent)
  {
    super.setParent(parent);
    if (parent != null) parent.addFolder(this);
  }
  
  /***************************************************************************
   * Get tasks as array.
   * @return the tasks.
   **************************************************************************/
  public Task[] getTasks()
  {
    return tasks.toArray(new Task[tasks.size()]);
  }
  
  /***************************************************************************
   * Get subfolders as array.
   * @return the subfolders.
   **************************************************************************/
  public TaskFolder[] getFolders()
  {
    return folders.toArray(new TaskFolder[folders.size()]);
  }
  
  /***************************************************************************
   * Add task to folder.
   * @param task task to be added.
   **************************************************************************/
  public void addTask(Task task)
  {
    tasks.add(task);
  }
  
  /***************************************************************************
   * Add subfolder to folder.
   * @param folder subfolder to be added.
   **************************************************************************/
  public void addFolder(TaskFolder folder)
  {
    folders.add(folder);
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------