// ---------------------------------------------------------------------------
// BrowserWindow.java
// A browser window.
// $Id: BrowserWindow.java,v 1.2 2017/03/17 13:16:07 schreine Exp $
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

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;

/**
 * 
 * A browser window.
 *
 */
public final class BrowserWindow extends Composite
{
  // the shell of the window
  private Shell shell;
  
  // the widgets
  private Browser browser = null; // the browser
  
  /****************************************************************************
   * Construct window connected to denoted GUI.
   * @param display the display.
   * @param image the icon image.
   * @param header the header text of the window.
   * @param width initial width
   * @param height initial height
   * @return a window connected to the GUI.
   ***************************************************************************/
  public static BrowserWindow construct(Display display, 
    Image image, String header,
    int width, int height)
  {
    Shell shell = new Shell(display, 
      SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
    shell.setText("RISC Algorithm Language (RISCAL): " + header);
    shell.setImage(image);
    return new BrowserWindow(shell, width, height);
  }
  
  /****************************************************************************
   * Construct panel for denoted shell.
   * @param shell the shell to which the panel is attached.
   * @param width initial width
   * @param height initial height
   ***************************************************************************/
  public BrowserWindow(Shell shell, int width, int height)
  {
    // initialize window
    super(shell, SWT.NONE);
    this.shell = shell;
    
    // resize it
    shell.setSize(width, height);
    
    // set shell layout for shell and panel
    shell.setLayout(new FillLayout());
    setLayout(new FillLayout());
    
    // create widgets
    createMenu();
    createBrowser();
  }

  /****************************************************************************
   * Create browser.
   ***************************************************************************/
  private void createBrowser()
  {
    browser = new Browser(this, SWT.BORDER);
    browser.setBackground(new Color(getDisplay(), new RGB(255, 255, 255)));
  }
  
  /****************************************************************************
   * Get browser from window.
   * @return the browser.
   ***************************************************************************/
  public Browser getBrowser()
  {
    return browser;
  }
  
  /***************************************************************************
   * Create menu bar.
   **************************************************************************/
  private void createMenu()
  {
    new Menu(shell, SWT.BAR);
    shell.setMenuBar(null);
  }
}
