// ---------------------------------------------------------------------------
// TaskTree.java
// A tree representing checking tasks.
// $Id: TaskTree.java,v 1.11 2018/05/29 12:54:47 schreine Exp $
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
package com.riscal;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import riscal.Main;
import riscal.syntax.AST.SourcePosition;
import riscal.tasks.*;
import riscal.types.Environment.Symbol.*;

public class TaskTree
{
  // the SWT tree (subclassing not allowed)
  private Tree tree;
  
  // the top window
  private final TopWindow top;
  
  // the task colors
  private Color taskColorOpen; 
  private Color taskColorClosed;
  
  // the color for non-executable tasks that are also not folders
  private Color noTaskColor;
  
  // the function to which the tree currently belongs (null, if none)
  private FunctionSymbol fun;
  
  /***************************************************************************
   * Create a tree displaying task structures.
   * @param parent the parent widget.
   * @param top the top window.
   **************************************************************************/
  public TaskTree(Composite parent, TopWindow top)
  {    
    tree = new Tree(parent, SWT.SINGLE);
    this.top = top;
    createMenu();
    createHandlers();
    taskColorOpen = tree.getDisplay().getSystemColor(SWT.COLOR_RED);
    taskColorClosed = tree.getDisplay().getSystemColor(SWT.COLOR_BLUE);
    noTaskColor = tree.getDisplay().getSystemColor(SWT.COLOR_DARK_MAGENTA);
  }
  
  /****************************************************************************
   * Get visual representation of tree.
   * @return the tree.
   ***************************************************************************/
  public Tree getTree()
  {
    return tree;
  }
  
  /***************************************************************************
   * Create menu for tree
   **************************************************************************/
  private void createMenu()
  {
    final Menu menu = new Menu(tree.getShell(), SWT.POP_UP);
    tree.setMenu(menu);
    menu.addListener (SWT.Show, new Listener () {
      public void handleEvent (Event event) {
        MenuItem [] menuItems = menu.getItems ();
        for (int i=0; i<menuItems.length; i++) {
          menuItems [i].dispose ();
        }
        TreeItem [] treeItems = tree.getSelection();
        if (treeItems.length != 1) return;
        final TreeItem item = treeItems[0];
        Task task = (Task)item.getData();
        if (task.getFunction() != null)
        {
          MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
          menuItem.setText ("Execute Task");
          //menuItem.setImage(top.getStartImage());
          menuItem.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
              executeTask(task, item);
            }
          });
        }
        final String description = task.getDescription();
        if (description != null)       
        {
          MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
          menuItem.setText ("Print Description");
          menuItem.setImage(null);
          menuItem.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
              Main.println(description);
            }
          });
        }
        final String definition = task.getDefinition();
        if (definition != null)       
        {
          MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
          menuItem.setText ("Print Definition");
          menuItem.setImage(null);
          menuItem.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
              Main.println(definition);
            }
          });
        }
      };
    });
  }
  
  /****************************************************************************
   * Execute a task associated to the tree item.
   * @param task to be executed.
   * @param the associated tree item.
   ***************************************************************************/
  private void executeTask(Task task, TreeItem item)
  {   
    FunctionSymbol fun = task.getFunction();
    if (fun == null) return;
    top.startExecution(false, new Runnable()
    {
      public void run() 
      {
        boolean okay = MainSWT.execute(fun);
        top.updateView(new Runnable() {
          public void run() {
            tree.deselect(item);
            if (okay)
            {
              item.setForeground(taskColorClosed);
              //item.setImage(top.getTaskClosedImage());
            }
          }});
      }
    });
  }
  
  /****************************************************************************
   * Create the event handlers for the tree.
   ***************************************************************************/
  public void createHandlers()
  {
    // set expansion handler
    tree.addTreeListener(new TreeListener() {
      public void treeExpanded(TreeEvent e) {
      }

      public void treeCollapsed(TreeEvent e) {
      }
    });
    
    // set click handlers
    tree.addSelectionListener(new SelectionAdapter() {
      
      // item is double clicked
      public void widgetDefaultSelected(SelectionEvent e) 
      { 
        TreeItem item = (TreeItem)e.item;
        Task task = (Task)item.getData();
        executeTask(task, item);
      }
      
      // item is selected by single click
      public void widgetSelected(SelectionEvent e) 
      {
        TreeItem item = (TreeItem)e.item;
        Task task = (Task)item.getData();
        SourcePosition pos = task.getSourcePosition();
        if (pos == null) pos = fun.ident.getPosition();
        top.markText(pos, true);
        for (SourcePosition pos0 : task.getAuxiliaryPositions())
          top.markText(pos0, false);
      }
    });
    
    // set hover handler
    tree.addMouseTrackListener(new MouseTrackAdapter() {
      public void mouseHover(MouseEvent e) {
        Tree tree = (Tree)e.widget;
        Task task = find(tree.getItems(), e.x, e.y);
        if (task == null) return;
        new TaskTip(top.getShell(), tree.toDisplay(e.x, e.y), task);
      }
      public void mouseExit(MouseEvent e) {
        TaskTip.close();
      }
    });
  }

  /****************************************************************************
   * Find task associated to item in set of items denoted by mouse position.
   * @param items the set of tree items to be recursively searched.
   * @param x horizontal coordinate of mouse position.
   * @param y vertical coordinate of mouse position.
   * @return the denoted task (or null, if none was found)
   ***************************************************************************/
  private Task find(TreeItem[] items, int x, int y)
  {
    for (TreeItem item : items)
    {
      TreeItem[] items0 = item.getItems();
      Task task = find(items0, x, y);
      if (task != null) return task;
      task = (Task)item.getData();
      if (task == null) continue;
      Rectangle b = item.getBounds();
      if (x < b.x || x > b.x + b.width) continue;
      if (y < b.y || y > b.y + b.height) continue;
      return task;
    }
    return null;
  }
  
  /****************************************************************************
   * Display tasks in given root folder.
   * @param root the root folder for display (null clears task tree)
   * @param fun the function to which the tasks belong (null if none)
   ***************************************************************************/
  public void displayTasks(TaskFolder root, FunctionSymbol fun)
  {
    // clear status information
    this.fun = null;
    tree.removeAll();
    
    // process top-level tasks and folders
    if (root != null)
    {
      this.fun = fun;
      Task[] tasks = root.getTasks();
      for (Task t : tasks)
        displayTasks(tree, t);
      TaskFolder[] folders = root.getFolders();
      for (TaskFolder f : folders)
        displayTasks(tree, f);
    }
  }
  
  /***************************************************************************
   * Display task within parent component.
   * @param parent the parent component (type Tree or TreeItem).
   * @param task the task to be displayed.
   **************************************************************************/
  private void displayTasks(Object parent, Task task)
  {
    // create tree node and associate task to it
    TreeItem node = (parent instanceof Tree) ?
        new TreeItem((Tree)parent,SWT.NULL) :
        new TreeItem((TreeItem)parent, SWT.NULL);
    node.setExpanded(true);
    
    // set tree node content
    node.setText(task.getName());
    node.setData(task);

    // if task is atomic, we are done
    if (!(task instanceof TaskFolder)) 
    {  
      if (task.getFunction() != null)
      {
        node.setForeground(taskColorOpen);
        //node.setImage(top.getTaskOpenImage());
      }
      else
        node.setForeground(noTaskColor);
      return;
    }
    
    // otherwise, process tasks and folders recursively
    TaskFolder folder = (TaskFolder)task;
    Task[] tasks = folder.getTasks();
    for (Task t : tasks)
      displayTasks(node, t);
    TaskFolder[] folders = folder.getFolders();
    for (Task t : folders)
      displayTasks(node, t);
  }
  
  /****************************************************************************
   * Popup information on a task
   ***************************************************************************/
  private final static class TaskTip 
  {
    private Shell shell;
    private Label label;
    
    // only one instance of task tip at a time
    private static TaskTip tip = null;
    
    /**************************************************************************
     * Create popup window displaying information on given task.
     * @param parent the parent window.
     * @param pos the position of the window.
     * @param task the task.
     *************************************************************************/
    public TaskTip(Shell parent, Point pos, Task task)
    {         
      // close any previous tip
      close();
      tip = null;
      
      // determine text 
      String text = task.getDescription();
      if (text == null) return;
      
      // cthis will be the new tipi
      tip = this;
      
      // create window
      shell = new Shell(parent, SWT.ON_TOP | SWT.TOOL); 
      GridLayout gridLayout = new GridLayout();
      gridLayout.numColumns = 1;
      gridLayout.marginWidth = 2;
      gridLayout.marginHeight = 2;
      shell.setLayout(gridLayout);
      Color color = shell.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
      Color tcolor = shell.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
      shell.setBackground(color);
      
      // set label
      label = new Label(shell, SWT.NONE);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
          | GridData.VERTICAL_ALIGN_CENTER));
      label.setText(text);
      label.setBackground(color);
      label.setForeground(tcolor);
      
      // add listener
      shell.addMouseTrackListener(new MouseTrackAdapter() { 
        public void mouseEnter(MouseEvent e)
        {
          close();
        }
      });
      
      // open window
      shell.pack();
      MainSWT.setLocation(shell, pos.x+10, pos.y+10);
      shell.open();
    }
    
    /*************************************************************************
     * Close tip instance.
     ************************************************************************/
    public static void close()
    {
      if (tip != null) tip.shell.close();
      tip = null;
    }
  }
}
//---------------------------------------------------------------------------
// end of file
//---------------------------------------------------------------------------