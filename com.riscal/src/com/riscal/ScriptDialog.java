// ---------------------------------------------------------------------------
// ScriptDialog.java
// A dialog for setting the distributed excecution script
// $Id: ScriptDialog.java,v 1.6 2016/12/05 16:06:57 schreine Exp $
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public final class ScriptDialog extends Dialog
{   
  private String oscript; // the original script
  private String script;  // the script defined by the dialog
  Text scriptField;       // the text containing the current version
  
  /****************************************************************************
   * Construct window connected to denoted top Window
   * @param top the top window.
   * @param oscript the old version of the script.
   * @return a window connected to the GUI.
   ***************************************************************************/
  public static ScriptDialog construct(TopWindow top, String oscript)
  {
    ScriptDialog result = new ScriptDialog(top, oscript);
    return result;
  }
  
  private ScriptDialog(TopWindow top, String oscript)
  {
    super(top.getShell(), SWT.NONE); 
    this.oscript = oscript == null ? "" : oscript;

    Display display = top.getDisplay();
    Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.ON_TOP);
    GridLayout slayout = new GridLayout();
    shell.setLayout(slayout);
    slayout.numColumns = 1;
    slayout.marginWidth = 6;
    slayout.marginHeight = 6;
    slayout.verticalSpacing = 0;
    
    Label header = new Label(shell, SWT.NONE);
    header.setText("Server Startup Commands:");
    header.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
    
    scriptField = new Text(shell, SWT.MULTI);
    scriptField.setLayoutData(new GridData(GridData.FILL_BOTH));
    scriptField.setText(this.oscript);
    
    Label text = new Label(shell, SWT.NONE);
    text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    text.setText("Each command (one per line) must print a line of form\n" +
        "\t<host> <port> <password>\n"+
        "where fields are separated by a single space, for instance,\n" +
        "\tmyhost.mydomain.org 9876 qnb2l0083e6a5g3b0h18q2ad4\n" +
        "and <host>:<port> is a socket address on which a RISCAL server process listens for\n" +
        "a connection request that provides the denoted <password>; such a server process\n" +
        "must be started on the denoted <host> with option \"-s <threads>\" which subsequently\n" +
        "prints a suitable line of above form." );
    
    Composite buttons = new Composite(shell, SWT.NONE);
    GridData bdata = new GridData (SWT.CENTER, SWT.NONE, true, false);
    buttons.setLayoutData(bdata);
    buttons.setLayout(new RowLayout(SWT.HORIZONTAL));
    Button okay = new Button(buttons, SWT.PUSH);
    okay.setText("Okay");
    okay.setToolTipText("Confirm Changes");
    okay.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        ScriptDialog.this.script = scriptField.getText();
        shell.close();
      }
    });
    Button cancel = new Button(buttons, SWT.PUSH);
    cancel.setText("Cancel");
    cancel.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        shell.close();
      }
    });
    cancel.setToolTipText("Drop Changes");
    Button reset = new Button(buttons, SWT.PUSH);
    reset.setText("Reset");
    reset.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        scriptField.setText(ScriptDialog.this.oscript);     
      }
    });
    reset.setToolTipText("Reset to Original");
    
    // modal shell: block until disposed
    shell.setSize(600, 330);
    top.openCentered(shell);
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
  
  /*************************************************************************
   * Get script defined by window.
   * @return the script (null, if changes were not confirmed).
   ************************************************************************/
  public String getScript()
  {
    return script;
  }
}
