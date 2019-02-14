// ---------------------------------------------------------------------------
// MainSWT.java
// The main program for the SWT-based GUI.
// $Id: MainSWT.java,v 1.18 2018/03/23 15:51:27 schreine Exp $
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

import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

import riscal.*;
import riscal.syntax.AST.SourcePosition;
import riscal.types.Environment.Symbol.FunctionSymbol;

public class MainSWT
{
  // the thread executing an application request
  private static Thread thread;
  
  // the content of the main window
  private static TopWindow top;

  /***************************************************************************
   * Starts the GUI, returns on exit.
   * @param path the path of the specification file (may be null)
   **************************************************************************/
  public static void main(String path)
  { 
    // give the GUI thread maximum priority
    //Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    
    // make sure preferences are regularly written
    //WriteThread writeThread = new WriteThread();
    //writeThread.start();
    
    // create GUI
    //Display display = new Display();
    //top = TopWindow.create(display);
    
    // perform initial application processing
    //if (path != null) TopWindow.openFile(new File(path));
    
    // process events
    /*
    try
    {
      while (!top.isDisposed())
      {
        if (!display.readAndDispatch())
          display.sleep();
      }
      writeThread.stopped = true;
    }
    catch(SWTException e)
    {
      System.out.println(e.getMessage());
    }
    */
  }
  
  /***************************************************************************
   * Execute application request.
   * @param request the request.
   **************************************************************************/
  public static void execute(Runnable request)
  {
	//Main.getOutput().println("MainSWT execute");
    if (thread != null) return;
    thread = new Thread(request);
    thread.setPriority(Thread.MIN_PRIORITY);
    thread.start();
  }
  
  /***************************************************************************
   * Return true if application request is executing.
   * @return true if an execution is going on.
   **************************************************************************/
  public static boolean isExecuting()
  {
    return thread != null;
  }
  
  /***************************************************************************
   * Indicate termination of application request.
   * Must be only called by the thread that has executed the request.
   **************************************************************************/
  public static void terminate()
  {
    if (thread != Thread.currentThread())
      Main.error("foreign thread indicated termination", true);
    thread = null;
  }
  
  /***************************************************************************
   * Indicate change in stop status.
   * @param the new stop status
   **************************************************************************/
  public static void stop(boolean status)
  {
    if (top == null) return;
    top.updateView(new Runnable() {
      public void run()
      {
        top.enableStopButton(!status);
      }
    });
  }
  
  // execution was stopped during display of trace
  private static boolean stopped = false;
  
  /***************************************************************************
   * Display generated trace (if any)
   * @param header the header of the trace window
   **************************************************************************/
  public static void showTrace(String header)
  {
    if (top == null) return;
    stopped = false;
    top.updateView(new Runnable() {
      public void run()
      {
        try
        {
          //top.showTrace(header);
        }
        catch(Main.Stopped e)
        {
          stopped = true;
        }
      }
    });
    if (stopped == true) throw new Main.Stopped();
  }
  
  /****************************************************************************
   * Report some error in the source code.
   * @param message the error message
   * @param position the position of the error (lineTo and charTo may be -1)
   ***************************************************************************/
  /*
  public static void reportError(String message, SourcePosition position)
  {
    if (top == null) return;
    top.reportError(message, position);
  }
  */
  /***************************************************************************
   * Set maximum value of progress bar
   * @param n the value.
   **************************************************************************/
  public static void setMaximumProgress(int n)
  {
    if (top == null) return;
    top.updateView(new Runnable() {
      public void run() {
        top.progressBar().setMaximum(n);
      }});
  }
  
  /***************************************************************************
   * Set value of progress bar
   * @param n the value.
   **************************************************************************/
  public static void setProgress(int n)
  {
    if (top == null) return;
    top.updateView(new Runnable() {
      public void run() {
        top.progressBar().setSelection(n);
      }});
  }
  
  /****************************************************************************
   * Execute function/procedure with all possible arguments.
   * @param fun the symbol of the function/procedure.
   * @return the success of the execution.
   ***************************************************************************/
  public static boolean execute(FunctionSymbol fun)
  {
	return top.execute(fun);
  }
  
  /*************************************************************************
   * Set location of shell at pos but ensure that content is fully visible.
   * @param shell the shell.
   * @param x first coordinate of suggested position.
   * @param y second coordinate of suggested position.
   ************************************************************************/
  public static void setLocation(Shell shell, int x, int y)
  {
    Point size = shell.getSize();
    Display display = shell.getDisplay();
    Rectangle r = display.getClientArea();
    if (x+size.x+5 > r.width)
    {
      x = r.width-size.x-5;
      if (x < 0) x=0;
    }
    if (y+size.y+5 > r.height)
    {
      y = r.height-size.y-5;
      if (y < 0) y=0;
    }
    shell.setLocation(x, y);
  }
  
  // -------------------------------------------------------------------------
  // a thread writing preferences
  // -------------------------------------------------------------------------
  public static final class WriteThread extends Thread
  {
    public boolean stopped = false;
    public void run()
    {
      while (true)
      {
        for (int i=0; i<15; i++)
        {
          try { Thread.sleep(1000); } catch(Exception e) { }
          if (stopped) return;
        }
        Main.writePreferences();
      }
    }
  }
}
// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------