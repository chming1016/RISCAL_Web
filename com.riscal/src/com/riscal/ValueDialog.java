// ---------------------------------------------------------------------------
// ValueDialog.java
// A dialog for setting the constant values.
// $Id: ValueDialog.java,v 1.4 2016/10/25 15:42:23 schreine Exp $
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
package com.riscal;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import riscal.Main;

public final class ValueDialog extends Dialog
{   
  // the value map defined by the table
  private Map<String,Integer> map;
  
  /****************************************************************************
   * Construct window connected to denoted top Window
   * @param top the top window.
   * @return a window connected to the GUI.
   ***************************************************************************/
  public static ValueDialog construct(Shell s)
  {
    ValueDialog result = new ValueDialog(s);
    return result;
  }
  
  private ValueDialog(Shell s)
  {
    super(s.getShell(), SWT.NONE); 
    
    //Display display = shell.getDisplay();
    Shell shell = new Shell(s.getShell().getDisplay(), SWT.APPLICATION_MODAL | SWT.ON_TOP);
    GridLayout slayout = new GridLayout();
    shell.setLayout(slayout);
    slayout.numColumns = 1;
    slayout.marginWidth = 3;
    slayout.marginHeight = 3;
    slayout.verticalSpacing = 0;
    
    Label header = new Label(shell, SWT.NONE);
    header.setText("Specific Constants:");
    header.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
    
    Composite main = new Composite(shell, SWT.NONE);
    main.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout mlayout = new GridLayout();
    main.setLayout(mlayout);
    mlayout.numColumns = 2;
    mlayout.marginWidth = 0;
    mlayout.marginHeight = 0;
    mlayout.verticalSpacing = 0;
    
    Table table = new Table(main, SWT.BORDER);
    table.setLayoutData(new GridData(GridData.FILL_BOTH));
    table.setLinesVisible(true);
    table.setHeaderVisible(true);
    TableColumn column1 = new TableColumn(table, SWT.LEFT);
    column1.setText("Name");
    TableColumn column2 = new TableColumn(table, SWT.LEFT);
    column2.setText("Value");
    if (Main.getValueMap().isEmpty())
    {
      // hack to fix display problem for empty tables
      TableItem item = new TableItem(table, SWT.NONE);
      item.setText(new String[] {"", ""});
    }
    for (Map.Entry<String,Integer> e : Main.getValueMap().entrySet())
    {
      TableItem item = new TableItem(table, SWT.NONE);
      item.setText(new String[] {e.getKey(), e.getValue().toString()});
    }
    column1.pack();
    column2.pack();
    if (Main.getValueMap().isEmpty())
    {
      // hack to fix display problem for empty tables
      table.remove(0);
    }
    
    Composite tbuttons = new Composite(main, SWT.NONE);
    GridData tbdata = new GridData (SWT.LEFT, SWT.CENTER, false, false);
    tbuttons.setLayoutData(tbdata);
    tbuttons.setLayout(new RowLayout(SWT.VERTICAL));
    Button add = new Button(tbuttons, SWT.PUSH);
    //add.setImage(s.plusImage());
    add.setToolTipText("Add New Constant");
    add.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        TableItem item = new TableItem(table, SWT.NONE);
        item.setText(new String[] {"_", "0"});
      }
    });
    Button remove = new Button(tbuttons, SWT.PUSH);
    //remove.setImage(s.minusImage());
    remove.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        int[] selected = table.getSelectionIndices();
        table.remove(selected);
      }
    });
    remove.setToolTipText("Remove Selected Constant");
    
    Composite buttons = new Composite(shell, SWT.NONE);
    GridData bdata = new GridData (SWT.CENTER, SWT.NONE, true, false);
    buttons.setLayoutData(bdata);
    buttons.setLayout(new RowLayout(SWT.HORIZONTAL));
    Button okay = new Button(buttons, SWT.PUSH);
    okay.setText("Okay");
    okay.setToolTipText("Confirm Changes");
    okay.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        map = new LinkedHashMap<String,Integer>();
        for (TableItem item : table.getItems())
        {
          String key = item.getText(0);
          String value = item.getText(1);
          if (key.isEmpty() || !Character.isAlphabetic(key.charAt(0))) 
          {
            //top.consoleOutput().println("WARNING: invalid constant name '" + key + "' is ignored.");
            continue;
          }
          if (map.containsKey(key))
          {
            //top.consoleOutput().println("WARNING: duplicate value '" + value + 
            //    "' for constant '" + key + "' is ignored.");
            continue;
          }

          Integer ivalue = Main.toInteger(value);
          if (ivalue == null || ivalue < 0)
          {
            //top.consoleOutput().println("WARNING: invalid value '" + value + 
            //    "' for constant '" + key + "' is ignored.");
            continue;
          }
          map.put(key, ivalue);
        }
        shell.close();
      }
    });
    Button cancel = new Button(buttons, SWT.PUSH);
    cancel.setText("Cancel");
    cancel.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        map = null;
        shell.close();
      }
    });
    cancel.setToolTipText("Drop Changes");
    
    // see example from TableEditor API documentation
    /*
    final TableEditor editor = new TableEditor(table);
    editor.horizontalAlignment = SWT.LEFT;
    editor.grabHorizontal = true;
    editor.minimumWidth = 50;
    final int EDITABLECOLUMN = 1;
    table.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        Control oldEditor = editor.getEditor();
        if (oldEditor != null) oldEditor.dispose();
        TableItem item = (TableItem)e.item;
        if (item == null) return;
        Text newEditor = new Text(table, SWT.NONE);
        newEditor.setText(item.getText(EDITABLECOLUMN));
        newEditor.addModifyListener(new ModifyListener() {
          public void modifyText(ModifyEvent e) {
            Text text = (Text)editor.getEditor();
            editor.getItem().setText(EDITABLECOLUMN, text.getText());
          }
        });
        newEditor.selectAll();
        newEditor.setFocus();
        editor.setEditor(newEditor, item, EDITABLECOLUMN);
      }
    });
    */
    
    // adapted from http://www.java2s.com/Tutorial/Java/0280__SWT/AddselectionlistenertoTableCursor.htm
    // and https://www.tutorials.de/threads/in-einer-editierbaren-tabelle-mit-tab-oder-enter-durch-die-spalten-navigieren.267778/
    /*
    final TableCursor cursor = new TableCursor(table, SWT.NONE);
    final ControlEditor editor = new ControlEditor(cursor);
    editor.grabHorizontal = true;
    editor.grabVertical = true;
    cursor.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        table.setSelection(new TableItem[] { cursor.getRow() });
        final Text text = new Text(cursor, SWT.NONE);
        text.setText(cursor.getRow().getText(cursor.getColumn()));
        text.setSelection(0, text.getText().length());
        text.setFocus();
        editor.setEditor(text);
        text.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent event) {
            switch (event.keyCode) {
            case SWT.CR:
              cursor.getRow().setText(cursor.getColumn(), text.getText());
            case SWT.ESC:
              text.dispose();
              // table.setSelection(new TableItem[] {});
              break;          
          }}
        });
        text.addFocusListener(new FocusAdapter() 
        {
          public void focusLost(FocusEvent e)
          {
            if (shell.isDisposed()) return;
            cursor.getRow().setText(cursor.getColumn(), text.getText());
            text.dispose();
          }
        });
        text.addTraverseListener(new TraverseListener(){
          public void keyTraversed(TraverseEvent e) {
            cursor.getRow().setText(cursor.getColumn(), text.getText());
            int nc = table.getColumnCount();
            int nr = table.getItemCount();
            int c = cursor.getColumn();
            TableItem item = cursor.getRow();
            int r = -1;
            TableItem[] items = table.getItems();
            int n = items.length;
            for (int i=0; i<n; i++)
            {
              if (items[i] == item) { r = i; break; }
            }
            if (r == -1) r = 0;
            if (e.detail == SWT.TRAVERSE_TAB_NEXT) {
              c++;
              if (c == nc) { c = 0; r++; }
              if (r == nr) { r = 0; }
              cursor.setSelection(r, c);
              text.setText(cursor.getRow().getText(cursor.getColumn()));
              text.setSelection(0, text.getText().length());
              e.doit = false;
            }
            else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
              c--;
              if (c == -1) { c = nc-1; r--; }
              if (r == -1) { r = nr-1; }
              cursor.setSelection(r, c);
              text.setText(cursor.getRow().getText(cursor.getColumn()));
              text.setSelection(0, text.getText().length());
              e.doit = false;
            }
          }
        });  
      }
    });
    */
 
    // modal shell: block until disposed
    shell.setSize(300, 300);
    //s.openCentered(shell);
    /*
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    */
  }
  
  /*************************************************************************
   * Get map defined by window.
   * @return the map (null, if changes were not confirmed).
   ************************************************************************/
  public Map<String,Integer> getMap()
  {
    return map;
  }
}
