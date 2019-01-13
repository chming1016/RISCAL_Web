package com.riscal;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.client.service.ExitConfirmation;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.rap.fileupload.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import com.riscal.MainSWT.WriteThread;

import riscal.Main;
import riscal.syntax.*;
import riscal.syntax.AST.*;
import riscal.types.Environment.Symbol.*;
import riscal.tasks.*;
//import riscal.zest.*;

//import org.eclipse.jface.text.*;
//import org.eclipse.jface.text.presentation.*;
//import org.eclipse.jface.text.rules.*;
//import org.eclipse.jface.text.source.*;
//import org.eclipse.rap.jface.*

//import java.awt.SplashScreen;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class TopWindow extends AbstractEntryPoint {

	  // URL of help document
	  public final static String helpURL = 
	    "http://www.risc.jku.at/research/formal/software/RISCAL/manual";
	  
	  // the about text
	  public final static String aboutText = 
		      "<html>\n" +
		      "<body style=\"font-size:10pt;\">\n" +
		      "<img align=right " +
		      "src=\"http://www.risc.jku.at/about/welcome/_images/risc-logo-transparent.gif\"/>\n" +
		      "<h1>RISC Algorithm Language (RISCAL)</h1>\n" +
		      "<b>Web Version " + Main.version + "</b><br>\n" +
		      "For the latest version of this program and further information, see " +
		      "<a href=\"http://www.risc.jku.at/research/formal/snoftware/RISCAL\">" +
		      "http://www.risc.jku.at/research/formal/software/RISCAL</a>.<p>" +
		      "Copyright (C) 2016-, <a href=\"http://www.risc.jku.at\">" +
		      "Research Institute for Symbolic Computation (RISC)</a>,\n"+
		      "Johannes Kepler University, Linz, Austria.<p>" +
		      "This program is free software; you can redistribute it and/or modify " +
		      "it under the terms of the GNU General Public License as published by " +
		      "the Free Software Foundation, either version 3 of the License, or " +
		      "(at your option) any later version.<p>" +
		      "Author: Wolfgang Schreiner " +
		      "&lt;<a href=\"mailto:Wolfgang.Schreineanr@risc.jku.at\">" +
		      "Wolfgang.Schreiner@risc.jku.at</a>&gt;\n<br>" +
		      "</body>\n" +
		      "</html>";
	  
	  // the shell used by this window
	  private Shell shell;
	  
	  // the content of the window
	  private Group editGroup;
	  private Group analyzeGroup;
	  private Group tasksGroup;
	  private Composite controlGroup;
	  private Composite optionGroup;
	  private Composite option2Group;
	  private Composite option3Group;
	  private Composite option4Group;
	  private Composite consoleGroup;
	  
	  // the editor and the console field
	  
	  // the editor, its line ruler, the document being edited
	  //private SourceViewer editor;
	  //private LineNumberRulerColumn lruler;
	  //private Document document;
	  private static Text document;
	  
	  // the output console
	  //private StyledText console;
	  private Text console;
	  
	  // the tasks tree
	  //private TaskTree taskTree;
	  
	  // the font size for editor and console
	  private int fontSize = 10;
	  
	  
	  // the names of the image files
	  private static final String iconImageName = "risc-logo.png";
	  /*
	  private static final String newImageName = "document-new.png";
	  private static final String openImageName = "document-open.png";
	  // private static final String closeImageName = "emblem-unreadable.png";
	  private static final String saveImageName = "document-save.png";
	  private static final String saveAsImageName = "document-save-as.png";
	  private static final String undoImageName = "edit-undo.png";
	  private static final String redoImageName = "edit-redo.png";
	  private static final String quitImageName = "system-log-out.png";
	  private static final String plusImageName = "list-add.png";
	  private static final String minusImageName = "list-remove.png";
	  private static final String aboutImageName = "risc-logo-small.png";
	  private static final String helpImageName = "help-browser.png";
	  private static final String startImageName = "go-next.png";
	  private static final String stopImageName = "process-stop.png";
	  private static final String clearImageName = "edit-clear.png";
	  private static final String refreshImageName = "view-refresh.png";
	  private static final String checkImageName = "emblem-system.png";
	  private static final String recordImageName = "media-record.png";
	  private static final String stoprecordImageName = "media-playback-stop.png";
	  private static final String errorImageName = "software-update-urgent.png";
	  private static final String valueImageName = "format-justify-fill.png";
	  private static final String tasksImageName = "folder.png";
	  private static final String taskOpenImageName = "weather-overcast.png";
	  private static final String taskClosedImageName = "weather-clear.png";
	  */
	  
	  // the images themselves
	  private static Image iconImage;
	  /*
	  private Image newImage;
	  private Image openImage;
	  // private Image closeImage;
	  private Image saveImage;
	  private Image saveAsImage;
	  private Image undoImage;
	  private Image redoImage;
	  private Image quitImage;
	  private Image plusImage;
	  private Image minusImage;
	  private Image aboutImage;
	  private Image helpImage;
	  private Image startImage;
	  private Image stopImage;
	  private Image clearImage;
	  private Image refreshImage;
	  private Image checkImage;
	  private Image recordImage;
	  private Image stoprecordImage;
	  private Image errorImage;
	  private Image valueImage;
	  private Image tasksImage;
	  private Image taskOpenImage;
	  private Image taskClosedImage;
	  */
	  
	  // the console output
	  private PrintWriter consoleOutput;
	  
	  // the log file
	  //private File logFile = null;
	  private OutputStream logStream = null;
	 
	  // the menu
	  private Menu menu;
	  
	  // the buttons
	  private ToolItem newButton;
	  private ToolItem openButton;
	  private ToolItem saveButton;
	  private ToolItem startButton;
	  private ToolItem stopButton;
	  private ToolItem clearButton;
	  private ToolItem refreshButton;
	  private ToolItem checkButton;
	  private ToolItem downloadButton;
	  //private ToolItem recordButton;
	  //private ToolItem stoprecordButton;
	  
	  // the button and menu entry for saving a file
	  private MenuItem saveItem;
	  private MenuItem saveAsItem;
	  
	  // the option selectors
	  private Button silentOption;
	  //private Button traceOption;
	  private Button nondetOption;
	  private Text vsizeField;
	  private Button valueButton;
	  private Combo methodMenu;
	  private Font methodFont;
	  private Button tasksButton;
	  private boolean tasks = false;
	  
	  // seems not so useful with current implementation of lazy type enumeration
	  // private Text inumField;
	  // private String mtext = null;
	  
	  private Text cnumField;
	  private String cnumText = null;
	  private Text cpercField;
	  private String cpercText = null;
	  private Text crunField;
	  private String crunText = null;
	  
	  private Button threadOption;
	  private Text threadField;
	  private String threadText = null;
	  //private Button distOption;
	  //private Button distCommand;
	  
	  // the progress bar
	  private ProgressBar progressBar;
	  
	  // the source file being edited, its modification status, 
	  // the functions defined in the source and the currently selected function
	  private AST.Source source;
	  private boolean modified = false;
	  private List<FunctionSymbol> funs;
	  private FunctionSymbol fun;
	  
	  // translation options and whether they have changed after 
	  // source has been processed the last time
	  private boolean optmodified = false;
	  private boolean nondetselected = false;
	  private String vtext = null;
	  private int funselected = -1;

	  // execution options
	  private boolean silentselected = false;
	  //private boolean traceselected = false;
	  
	  // parallelism options
	  private boolean threadselected = false;
	  //private boolean distselected = false;
	  private static Thread thread;
	  private int fileSequence = 0;
	  
	  // the various kinds of cursors
	  //private final Cursor arrowCursor =  new Cursor(getDisplay(), SWT.CURSOR_ARROW);
	  // private final Cursor ibeamCursor = 
	  //   new Cursor(getDisplay(), SWT.CURSOR_IBEAM);
	  //private final Cursor waitCursor = new Cursor(getDisplay(), SWT.CURSOR_WAIT);
	  
	  // annotation handling
	  //private final static String ERROR_TYPE = "riscal.error";
	  //private static final String PRIMARY_MARKER_TYPE = "riscal.marker.primary";
	  //private static final String SECONDARY_MARKER_TYPE = "riscal.marker.secondary";
	  //private static final String UNDERLINE_SQUIGGLE = "riscal.squiggle";
	  
	  // window sizes/proportions (not sure how they are really related)
	  //private static final int W = 1260;     // window width
	  //private static final int H = 800;      // window height
	  private static final double WF = 1.25; // width scaling factor
	  //private static final int W1 = 55;      // sash width editor area
	  //private static final int W2 = 35;      // sash width console area
	  //private static final int W3 = 10;      // sash width task tree area
	  
    /****************************************************************************
     * Create the main window.
     * @param display the display to be used.
     ***************************************************************************/
    /*
    public static TopWindow create(Display display)
    {
      Shell shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
      shell.setText("RISC Algorithm Language (RISCAL)");
      //iconImage = new Image(display, Main.class.getResourceAsStream("/" + iconImageName));
      //shell.setImage(iconImage);
      shell.setSize(W, H);
      return new TopWindow(shell);
    }
    */
	/*
	protected Shell createShell(Display display) {
		shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		//shell.setText("RISC Algorithm Language (RISCAL)");
		//shell.setSize(W, H);
	    return new Shell(shell);
	}
	*/
    /****************************************************************************
     * Create the main window.
     * @param display the display to be used.
     ***************************************************************************/
	/*
    public TopWindow(Shell shell)
    { 
      super(); 
      this.shell = shell;
      
      shell.addShellListener(new ShellAdapter() { 
        public void shellClosed(ShellEvent e)
        {
          if (MainSWT.isExecuting())
          {
            e.doit = false;
            return;
          }
          boolean confirm = ask("Quit Program", "Do you really want to quit?");
          if (!confirm)
          {
            e.doit = false;
            return;
          }
          if (modified)
          {
            int answer = askCancel("File Not Saved", "Save modified file?");
            if (answer == -1) 
            {
              e.doit = false;
              return;
            }
            if (answer == 1)
            {
              boolean okay = saveFile(false);
              if (!okay) 
              {
                e.doit = false;
                return;
              }
            }
          }
          
        }
      });
      
      // create images
      //createImages();
      
      // create contents
      createMenu();
      createGroups();
      createEditButtons();
      createEditor();
      createControlButtons();
      createControlOptions();
      createConsole();
      //createTaskTree();
      
      // set fonts
      setFonts(0);

      // display splash screen
      //SplashScreen screen = SplashScreen.getSplashScreen();
      //if (screen != null) screen.close();
      
      // set the shell layout
      //setWeights(new int[] {W1,W2,W3});
      layout();
      
      // set the iput/output interface
      Main.setInputOutput(null, consoleOutput);
      
      // open window
      shell.open();
    }
    */
    
	@Override
    public void createContents(Composite parent) {
        //parent.setLayout(new GridLayout(GridData.FILL_BOTH, false));
		shell = parent.getShell();
		
		iconImage = new Image(shell.getDisplay(), Main.class.getResourceAsStream("/" + iconImageName));
	    shell.setImage(iconImage);
		
		// give the GUI thread maximum priority
	    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	    
	    // make sure preferences are regularly written
	    //WriteThread writeThread = new WriteThread();
	    //writeThread.start();
	    
        createMenu();
	    createGroups();
        createEditButtons();
        createEditor();
        createControlButtons();
        createControlOptions();
        createConsole();
        //createTaskTree();
        
        // set fonts
        setFonts(0);
        
        // set the shell layout
        //setWeights(new int[] {W1,W2,W3});
        layout();
        
        // set the iput/output interface
        Main.init();
        Main.setInputOutput(null, consoleOutput);
        Main.mainInit();
        Main.mainCore();
        if (Main.path != null) openFile(new File(Main.path));
        
        // open window
        //shell.open();
        
	    // process events
        /*
	    try
	    {
	      while (!shell.getDisplay().isDisposed())
	      {
	        if (shell.getDisplay().readAndDispatch())
	        	System.out.println("Display Sleep\n");
	        	//shell.getDisplay().sleep();
	      }
	      writeThread.stopped = true;
	    }
	    catch(SWTException e)
	    {
	      System.out.println(e.getMessage());
	    }
	    */
        
        // Exit Confirmation
	    ExitConfirmation exitConfirmService = RWT.getClient().getService( ExitConfirmation.class );
	    exitConfirmService.setMessage( "Do you really wanna leave RISCAL?" );
    }
	private void test() {
		editGroup.setSize(shell.getSize().x/3, shell.getSize().y);
	}
    /***************************************************************************
     * Set the shell layout
     **************************************************************************/
    public void layout()
    {
      // set shell layout
      GridLayout shellLayout = new GridLayout();
      shell.setLayout(shellLayout);
      shellLayout.numColumns = 3;
      shellLayout.marginWidth = 3;
      shellLayout.marginHeight = 3;
      
      // set own layout
      //shell.setLayoutData(new GridData(GridData.FILL_BOTH));
      /*
      GridLayout topLayout = new GridLayout();
      this.setLayout(topLayout);
      topLayout.numColumns = tasks ? 3 : 2;
      topLayout.marginWidth = 3;
      topLayout.marginHeight = 3;
      setLayoutData(new GridData(GridData.FILL_BOTH));
      */
      
      // set layout of edit group
      editGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
      GridLayout editLayOut = new GridLayout();
      editGroup.setLayout(editLayOut);
      editLayOut.numColumns = 1;
      editLayOut.verticalSpacing = 0;
      editLayOut.marginWidth = 3;
      editLayOut.marginHeight = 3;

      // set layout of analyze group
      analyzeGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
      GridLayout analyzeLayOut = new GridLayout();
      analyzeGroup.setLayout(analyzeLayOut);
      analyzeLayOut.numColumns = 1;
      analyzeLayOut.verticalSpacing = 3;
      analyzeLayOut.marginWidth = 0;
      analyzeLayOut.marginHeight = 0;
      
      
      // set layout of tasks group
      
      //if (tasks)
      //{
    	tasksGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout tasksLayOut = new GridLayout();
        tasksGroup.setLayout(tasksLayOut);
        tasksLayOut.numColumns = 1;
        tasksLayOut.verticalSpacing = 3;
        tasksLayOut.marginWidth = 0;
        tasksLayOut.marginHeight = 0;
      //}
      
      
      // set layout of console group
      consoleGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
      GridLayout consoleLayOut = new GridLayout();
      consoleGroup.setLayout(consoleLayOut);
      consoleLayOut.numColumns = 1;
      consoleLayOut.verticalSpacing = 3;
      consoleLayOut.marginWidth = 3;
      consoleLayOut.marginHeight = 3;
      
      // set layout of control group
      GridData controlData = new GridData(GridData.FILL_HORIZONTAL);
      controlGroup.setLayoutData(controlData);
      Layout controlLayOut = new RowLayout(SWT.VERTICAL);
      controlGroup.setLayout(controlLayOut);;
      
      // set layout of option groups
      RowLayout optionLayout = new RowLayout(SWT.HORIZONTAL);
      optionGroup.setLayout(optionLayout);
      optionLayout.spacing = 3;
      RowLayout option2Layout = new RowLayout(SWT.HORIZONTAL);
      option2Group.setLayout(option2Layout);
      option2Layout.spacing = 3;
      RowLayout option3Layout = new RowLayout(SWT.HORIZONTAL);
      option3Group.setLayout(option3Layout);
      option3Layout.spacing = 3;
      RowLayout option4Layout = new RowLayout(SWT.HORIZONTAL);
      option4Group.setLayout(option4Layout);
      option4Layout.spacing = 3;
      
    }
    
    /***************************************************************************
     * Set the fonts.
     * @param increment the increment to the font size.
     **************************************************************************/
    private void setFonts(int increment)
    {
      fontSize += increment;
      Font font = new Font(shell.getDisplay(), "Monospace", fontSize, SWT.NORMAL);
      //editor.getTextWidget().setFont(font);
      //lruler.setFont(font);
      document.setFont(font);
      console.setFont(font);
    }
    
    /****************************************************************************
     * Get denoted image.
     * @param the name of the image.
     * @return the image
     ***************************************************************************/
    /*
    private Image getImage(String name)
    {
      return new Image(shell.getDisplay(), 
          Main.class.getResourceAsStream("/" + name));
    }
    */
    /***************************************************************************
     * Create the images
     **************************************************************************/
    /*
    private void createImages()
    {
      newImage = getImage(newImageName);
      openImage = getImage(openImageName);
      // closeImage = getImage(closeImageName);
      saveImage = getImage(saveImageName);
      saveAsImage = getImage(saveAsImageName);
      undoImage = getImage(undoImageName);
      redoImage = getImage(redoImageName);
      quitImage = getImage(quitImageName);
      plusImage = getImage(plusImageName);
      minusImage = getImage(minusImageName);
      aboutImage = getImage(aboutImageName);
      helpImage = getImage(helpImageName);
      startImage = getImage(startImageName);
      stopImage = getImage(stopImageName);
      clearImage = getImage(clearImageName);
      refreshImage = getImage(refreshImageName);
      checkImage = getImage(checkImageName);
      recordImage = getImage(recordImageName);
      stoprecordImage = getImage(stoprecordImageName);
      errorImage = getImage(errorImageName);
      valueImage = getImage(valueImageName);
      tasksImage = getImage(tasksImageName);
      taskOpenImage = getImage(taskOpenImageName);
      taskClosedImage = getImage(taskClosedImageName);
    }
    */
    /****************************************************************************
     * Create the content groups
     ***************************************************************************/
    private void createGroups()
    {
      editGroup = new Group(shell, SWT.BORDER);
      editGroup.setText("File: (Untitled)");

      analyzeGroup = new Group(shell, SWT.BORDER);
      analyzeGroup.setText("Analysis");
      
      controlGroup = new Composite(analyzeGroup, SWT.NONE);
      consoleGroup = new Composite(analyzeGroup, SWT.NONE);

      tasksGroup = new Group(shell, SWT.BORDER);
      tasksGroup.setText("Tasks");
      tasksGroup.setVisible(false);
    }

    /****************************************************************************
     * Create the menu
     ***************************************************************************/
    private void createMenu()
    {
      menu = new Menu(shell, SWT.BAR);
      shell.setMenuBar(menu);
      
      // the file menu
      MenuItem fileTitle = new MenuItem(menu, SWT.CASCADE);
      fileTitle.setText("File");
      Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
      fileTitle.setMenu(fileMenu);
      MenuItem newItem = new MenuItem(fileMenu, SWT.NULL);
      newItem.setText("New (Ctrl+n)");
      //newItem.setImage(newImage);
      newItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          newFile();
        }
      });
      newItem.setEnabled(true);
      newItem.setAccelerator(SWT.CTRL+'n');
      MenuItem openItem = new MenuItem(fileMenu, SWT.NULL);
      openItem.setText("Open... (Ctrl+o)");
      //openItem.setImage(openImage);
      openItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          //openFile(Main.file);
        }
      });
      openItem.setEnabled(true);
      openItem.setAccelerator(SWT.CTRL+'o');
      //new MenuItem(fileMenu, SWT.SEPARATOR);
      /*
      MenuItem closeItem = new MenuItem(fileMenu, SWT.NULL);
      closeItem.setText("Close (Ctrl+w)");
      closeItem.setImage(closeImage);
      closeItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          closeFile();
        }
      });
      closeItem.setEnabled(true);
      closeItem.setAccelerator(SWT.CTRL+'c');
      */
      saveItem = new MenuItem(fileMenu, SWT.NULL);
      saveItem.setText("Save (Ctrl+s)");
      //saveItem.setImage(saveImage);
      saveItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          saveFile(false);
        }
      });
      saveItem.setEnabled(false);
      saveItem.setAccelerator(SWT.CTRL+'s');
      //saveAsItem = new MenuItem(fileMenu, SWT.NULL);
      //saveAsItem.setText("Save As...");
      //saveAsItem.setImage(saveAsImage);
      //saveAsItem.addSelectionListener(new SelectionAdapter() {
      //  public void widgetSelected(SelectionEvent e) {
      //    saveFile(true);
      //  }
      //});
      //saveAsItem.setEnabled(false);
      //new MenuItem(fileMenu, SWT.SEPARATOR);
      //MenuItem quitItem = new MenuItem(fileMenu, SWT.NULL);
      //quitItem.setText("Quit (Ctrl+q)");
      //quitItem.addSelectionListener(new SelectionAdapter() {
      //  public void widgetSelected(SelectionEvent e) {
          //shell.close();
      //  }
      //});
      //quitItem.setEnabled(true);
      //quitItem.setAccelerator(SWT.CTRL+'q');
      //quitItem.setImage(quitImage);
      
      // the edit menu
      MenuItem editTitle = new MenuItem(menu, SWT.CASCADE);
      editTitle.setText("Edit");
      Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
      editTitle.setMenu(editMenu);
      //MenuItem undoItem = new MenuItem(editMenu, SWT.NULL);
      //undoItem.setText("Undo (Ctrl+z)");
      /*
      undoItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          if (editor.canDoOperation(ITextOperationTarget.UNDO))
            editor.doOperation(ITextOperationTarget.UNDO);
        }
      });
      */
      //undoItem.setEnabled(true);
      //undoItem.setAccelerator(SWT.CTRL+'z');
      //undoItem.setImage(undoImage);
      //MenuItem redoItem = new MenuItem(editMenu, SWT.NULL);
      //redoItem.setText("Redo (Shift+Ctrl+z)");
      /*
      redoItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          if (editor.canDoOperation(ITextOperationTarget.REDO))
            editor.doOperation(ITextOperationTarget.REDO);
        }
      });
      */
      //redoItem.setEnabled(true);
      //redoItem.setAccelerator(SWT.SHIFT+SWT.CTRL+'z');
      //redoItem.setImage(redoImage);
      //new MenuItem(editMenu, SWT.SEPARATOR);
      MenuItem biggerFontItem = new MenuItem(editMenu, SWT.NULL);
      biggerFontItem.setText("Bigger Font (Ctrl+'+')");
      biggerFontItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          setFonts(1);
        }
      });
      biggerFontItem.setEnabled(true);
      biggerFontItem.setAccelerator(SWT.CTRL+'+');
      //biggerFontItem.setImage(plusImage);
      MenuItem smallerFontItem = new MenuItem(editMenu, SWT.NULL);
      smallerFontItem.setText("Smaller Font (Ctrl+'-')");
      smallerFontItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          setFonts(-1);
        }
      });
      smallerFontItem.setEnabled(true);
      smallerFontItem.setAccelerator(SWT.CTRL+'-');
      //smallerFontItem.setImage(minusImage);
      
      // the help menu
      MenuItem helpTitle = new MenuItem(menu, SWT.CASCADE);
      helpTitle.setText("Help");
      Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
      helpTitle.setMenu(helpMenu);
      MenuItem helpItem = new MenuItem(helpMenu, SWT.NULL);
      helpItem.setText("Online Manual");
      
      helpItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
        	//UrlLauncher launcher = RWT.getClient().getService( UrlLauncher.class );
        	//launcher.openURL( helpURL );
          BrowserWindow b = BrowserWindow.construct(shell.getDisplay(), iconImage,
              "Help", 1000, 700);
          b.getBrowser().setUrl(helpURL);
          openCentered(b.getShell());
          
        }
      });
      
      //helpItem.setImage(helpImage);
      new MenuItem(helpMenu, SWT.SEPARATOR);
      MenuItem aboutItem = new MenuItem(helpMenu, SWT.NULL);
      aboutItem.setText("About RISCAL");
      aboutItem.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          BrowserWindow b = BrowserWindow.construct(shell.getDisplay(), iconImage,
              "About", 600, 320);
          b.getBrowser().setText(aboutText);
          openCentered(b.getShell());
        }
      });
      //aboutItem.setImage(aboutImage);
    }
    
    /****************************************************************************
     * Create the editor buttons
     ***************************************************************************/
    private void createEditButtons()
    {
      ToolBar editButtons = new ToolBar(editGroup, SWT.HORIZONTAL | SWT.FLAT);

      newButton = new ToolItem(editButtons, SWT.PUSH);
      //newButton.setImage(newImage);
      newButton.setText("New");
      newButton.setToolTipText("New (Ctrl+n)");
      newButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          newFile();
        }
      });
      newButton.setEnabled(true);
      
      openButton = new ToolItem(editButtons, SWT.PUSH);
      openButton.setText("Open");
      openButton.setToolTipText("Open (Ctrl+o)");
      openButton.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
        	  if (modified)
              {
        		  updateView(new Runnable() {
        	          public void run() {
        	            askCancelResult = askCancel("File Not Saved", "Save modified file?");
        	          }});
              } else {
	              FileDialog fileDialog = new FileDialog( shell, SWT.APPLICATION_MODAL );
	              fileDialog.setText( "Upload Files" );
	              fileDialog.open(new DialogCallback() {
	            	  public void dialogClosed( int returnCode ) {
	            		  if(returnCode == 32 && fileDialog.getFileName()!= "") {
	            			  //String url = startUploadReceiver();
	        	              ServerPushSession pushSession = new ServerPushSession();
	        	              pushSession.start();
	        	              Main.getOutput().println("Stored file: " + fileDialog.getFileName());
	        	              Main.file = new File(fileDialog.getFileName());
	        	              openFile(Main.file);
	            		  }
	            	}
	            } );
              }
            }
          });
      openButton.setEnabled(true);
      
      /*
      ToolItem closeButton = new ToolItem(editButtons, SWT.PUSH);
      closeButton.setImage(closeImage);
      closeButton.setToolTipText("Close (Ctrl+w)");
      closeButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          closeFile();
        }
      });
      closeButton.setEnabled(true);
      */

      /*
      FileUpload fileUpload = new FileUpload( editGroup, SWT.NONE );
      fileUpload.setText( "Open" );
      String url = startUploadReceiver();
      ServerPushSession pushSession = new ServerPushSession();
      fileUpload.addSelectionListener( new SelectionAdapter() {
        @Override
        public void widgetSelected( SelectionEvent e ) {
        	if (modified)
            {
              updateView(new Runnable() {
                public void run() {
                  saveFile(false);
                }});
            }
          pushSession.start();
          fileUpload.submit( url );
          //fileUpload.submit( uploadHandler.getUploadUrl() );
        }
      } );
      */
      
      saveButton = new ToolItem(editButtons, SWT.PUSH);
      //saveButton.setImage(saveImage);
      saveButton.setText("Save");
      saveButton.setToolTipText("Save (Ctrl+s)");
      saveButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          saveFile(false);
        }
      });
      saveButton.setEnabled(false);
      
      downloadButton = new ToolItem(editButtons, SWT.PUSH);
      downloadButton.setText("Download");
      downloadButton.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
        	  try {
        			byte[] fileContent = Files.readAllBytes(Main.file.toPath());
        			sendDownload(fileContent, Main.file.getName());
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            }
          });
      downloadButton.setEnabled(false);
      
    }
    
    public boolean sendDownload(byte[] data, String filename) {
        DownloadService service = new DownloadService(data, filename);
        service.register();

        UrlLauncher launcher = RWT.getClient().getService(UrlLauncher.class);
        launcher.openURL(service.getURL());
        return true;
    }
    
    private String startUploadReceiver() {
        DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
        FileUploadHandler uploadHandler = new FileUploadHandler( receiver );
        uploadHandler.addUploadListener( new FileUploadListener() {

          @Override
          public void uploadProgress( FileUploadEvent event ) {
            // handle upload progress
          }

          @Override
          public void uploadFailed( FileUploadEvent event ) {
            Main.getOutput().println( "upload failed: " + event.getException() );
          }

          @Override
          public void uploadFinished( FileUploadEvent event ) {
            for( FileDetails file : event.getFileDetails() ) {
            	Main.getOutput().println( "received: " + file.getFileName() + "\n");
            	Main.file = receiver.getTargetFiles()[ fileSequence ];
            	openFile(Main.file);
            	fileSequence += 1;
            }
          }
        } );
        return uploadHandler.getUploadUrl();
      }
      
    /****************************************************************************
     * Create the control buttons
     ***************************************************************************/
    private void createControlButtons()
    {
      ToolBar controlButtons = new ToolBar(controlGroup, SWT.HORIZONTAL | SWT.FLAT);
      
      checkButton = new ToolItem(controlButtons, SWT.PUSH);
      //checkButton.setImage(checkImage);
      checkButton.setText("Process Specification");
      checkButton.setToolTipText("Process Specification");
      checkButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          checkFile();
        }
      });
      checkButton.setEnabled(true);
      
      startButton = new ToolItem(controlButtons, SWT.PUSH);
      //startButton.setImage(startImage);
      startButton.setText("Start Execution");
      startButton.setToolTipText("Start Execution");
      startButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          startAnalysis();
        }
      });
      startButton.setEnabled(true);

      stopButton = new ToolItem(controlButtons, SWT.PUSH);
      //stopButton.setImage(stopImage);
      stopButton.setText("Stop Execution");
      stopButton.setToolTipText("Stop Execution");
      stopButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          Main.stop();
        }
      });
      stopButton.setEnabled(false);
      
      new ToolItem(controlButtons, SWT.SEPARATOR);
      
      clearButton = new ToolItem(controlButtons, SWT.PUSH);
      //clearButton.setImage(clearImage);
      clearButton.setText("Clear Output");
      clearButton.setToolTipText("Clear Output");
      clearButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          console.setText("");
        }
      });
      clearButton.setEnabled(true);
      
      /*
      recordButton = new ToolItem(controlButtons, SWT.PUSH);
      //recordButton.setImage(recordImage);
      recordButton.setText("Start Logging");
      recordButton.setToolTipText("Start Logging");
      recordButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          //log(true);
        }
      });
      recordButton.setEnabled(true);
      
      stoprecordButton = new ToolItem(controlButtons, SWT.PUSH);
      //stoprecordButton.setImage(stoprecordImage);
      stoprecordButton.setText("Stop Logging");
      stoprecordButton.setToolTipText("Stop Logging");
      stoprecordButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          //log(false);
        }
      });
      stoprecordButton.setEnabled(false);
      */

      new ToolItem(controlButtons, SWT.SEPARATOR);
      
      refreshButton = new ToolItem(controlButtons, SWT.PUSH);
      //refreshButton.setImage(refreshImage);
      refreshButton.setText("Reset System");
      refreshButton.setToolTipText("Reset System");
      refreshButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          boolean confirm = ask("Reset System", 
                  "Do you really want to reset/clear all settings?");
          if (!confirm)
          {
        	e.doit = false;
            //Main.getOutput().println(confirm);
            return;
          }
          //refresh();
          //Main.getOutput().println(confirm);
        }
      });
      refreshButton.setEnabled(true);
      //refreshButton.setEnabled(false);
      
    }
    /****************************************************************************
     * Create the control options
     ***************************************************************************/
    private void createControlOptions()
    {  
      optionGroup = new Composite(controlGroup, SWT.NONE);
      option3Group = new Composite(controlGroup, SWT.NONE);
      option4Group = new Composite(controlGroup, SWT.NONE);
      option2Group = new Composite(controlGroup, SWT.NONE);
      
      Label gLabel = new Label(optionGroup, SWT.NONE);
      gLabel.setLayoutData(new RowData());
      gLabel.setText("Translation:  ");
      
      nondetOption = new Button(optionGroup, SWT.CHECK);
      nondetOption.setLayoutData(new RowData());
      nondetOption.setText("Nondeterminism");
      nondetOption.setSelection(Main.getNondeterministic());
      nondetselected = nondetOption.getSelection();
      nondetOption.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          optmodified = true;
          nondetselected = nondetOption.getSelection();
          Main.setNondeterministic(nondetselected);
        }
      });
      
      Label vslabel = new Label(optionGroup, SWT.NONE);
      vslabel.setLayoutData(new RowData());
      vslabel.setText("   Default Value:");
      
      vsizeField = new Text(optionGroup, SWT.SINGLE | SWT.BORDER);
      vsizeField.setLayoutData(new RowData(35, 20));
      Integer d = Main.getDefaultValue();
      vsizeField.setText(d == null ? "0" : "" + d);
      vtext = vsizeField.getText();
      vsizeField.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          optmodified = true;
          vtext = vsizeField.getText();         
          Integer vsize0 = Main.toInteger(vtext);
          if (vsize0 == null)
          {
            vsize0 = 0;
            vsizeField.setText("0");
          }
          Main.setDefaultValue(vsize0);
        }
      });
      vsizeField.addVerifyListener(new VerifyListener() {
        public void verifyText(VerifyEvent e) {
          for (int i = 0; i < e.text.length(); i++) {
            if (!Character.isDigit(e.text.charAt(i))) {
              e.doit = false;
              return;
            }}}
      });
      
      Label vs2label = new Label(optionGroup, SWT.NONE);
      vs2label.setLayoutData(new RowData());
      vs2label.setText("   Other Values:");
      
      valueButton = new Button(optionGroup, SWT.PUSH);
      //valueButton.setImage(valueImage);
      valueButton.setText("|||");
      valueButton.setLayoutData(new RowData(32,22));
      valueButton.setToolTipText("Specific Constants");
      valueButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          valueDialog();
        }
      });
      
      /*
      Label maxlabel = new Label(optionGroup, SWT.NONE);
      maxlabel.setLayoutData(new RowData());
      maxlabel.setText("  Input Number:");
      
      inumField = new Text(optionGroup, SWT.SINGLE);
      inumField.setLayoutData(new RowData(70, 20));
      Long max = Main.getMaxNumber();
      inumField.setText(max == null ? "" : "" + max);
      mtext = inumField.getText();
      inumField.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          mtext = inumField.getText();         
          Long inum0 = Main.toLong(mtext);
          if (inum0 == null) inumField.setText("");
          Main.setMaxNumber(inum0);
        }
      });
      inumField.addVerifyListener(new VerifyListener() {
        public void verifyText(VerifyEvent e) {
          for (int i = 0; i < e.text.length(); i++) {
            if (!Character.isDigit(e.text.charAt(i))) {
              e.doit = false;
              return;
            }}}
      });
      */

      Label mlabel = new Label(option2Group, SWT.NONE);
      mlabel.setLayoutData(new RowData());
      mlabel.setText("Operation:    ");
      
      tasksButton = new Button(option2Group, SWT.PUSH);
      //tasksButton.setImage(tasksImage);
      tasksButton.setText("Show/Hide Tasks");
      tasksButton.setToolTipText("Show/Hide Tasks");
      tasksButton.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          toggleTasks();
        }
      });
      tasksButton.setEnabled(true);
      tasksButton.setVisible(true);
      
      methodMenu = new Combo(option2Group, SWT.DROP_DOWN | SWT.READ_ONLY);
      methodMenu.setLayoutData(new RowData(SWT.DEFAULT, SWT.DEFAULT));
      methodFont = new Font(shell.getDisplay(), "Monospace", 10, SWT.NORMAL);
      methodMenu.setFont(methodFont);
      String[] funs = Main.getFunctions();
      if (funs != null) { methodMenu.setItems(funs); analyzeGroup.layout(true, true); };
      Integer sel = Main.getSelectedFunction();
      if (sel != null)
      {
        funselected = sel;
        methodMenu.select(funselected);
        showSelectedFunction();
      }
      methodMenu.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          funselected = methodMenu.getSelectionIndex();
          Main.setSelectedFunction(funselected);
          showSelectedFunction();
          displayTasks();
        }
      });
      
      Label g3Label = new Label(option3Group, SWT.NONE);
      g3Label.setLayoutData(new RowData());
      g3Label.setText("Execution:    ");
      
      silentOption = new Button(option3Group, SWT.CHECK);
      silentOption.setLayoutData(new RowData());
      silentOption.setText("Silent");
      silentOption.setSelection(Main.getSilent());
      silentselected = silentOption.getSelection();
      silentOption.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          // no effect on type checking
          // optmodified = true;
          silentselected = silentOption.getSelection();
          Main.setSilent(silentselected);
        }
      });
      
      /*
      if (Main.supportTrace())
      {
        traceOption = new Button(option3Group, SWT.CHECK);
        traceOption.setLayoutData(new RowData());
        traceOption.setText("Trace");
        traceOption.setSelection(Main.showTrace());
        traceselected = traceOption.getSelection();
        traceOption.addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            // no effect on type checking
            // optmodified = true;
            traceselected = traceOption.getSelection();
            Main.showTrace(traceselected);
          }
        });
      }
      */
      
      Label cnumLabel = new Label(option3Group, SWT.NONE);
      cnumLabel.setLayoutData(new RowData());
      cnumLabel.setText("   Inputs:");
      
      cnumField = new Text(option3Group, SWT.SINGLE | SWT.BORDER);
      cnumField.setLayoutData(new RowData(35, 20));
      Long cnum = Main.getCheckNumber();
      cnumField.setText(cnum == null ? "" : "" + cnum);
      cnumText = cnumField.getText();
      cnumField.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          cnumText = cnumField.getText();         
          Long cnum0 = Main.toLong(cnumText);
          if (cnum0 == null) cnumField.setText("");
          Main.setCheckNumber(cnum0);
        }
      });
      cnumField.addVerifyListener(new VerifyListener() {
        public void verifyText(VerifyEvent e) {
          for (int i = 0; i < e.text.length(); i++) {
            if (!Character.isDigit(e.text.charAt(i))) {
              e.doit = false;
              return;
            }}}
      });
      
      Label cpercLabel = new Label(option3Group, SWT.NONE);
      cpercLabel.setLayoutData(new RowData());
      cpercLabel.setText("   Per Mille:");
      
      cpercField = new Text(option3Group, SWT.SINGLE | SWT.BORDER);
      cpercField.setLayoutData(new RowData(35, 20));
      Long cperc = Main.getCheckPercentage();
      cpercField.setText(cperc == null ? "" : "" + cperc);
      cpercText = cpercField.getText();
      cpercField.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          cpercText = cpercField.getText();         
          Long cperc0 = Main.toLong(cpercText);
          if (cperc0 == null) cpercField.setText("");
          Main.setCheckPercentage(cperc0);
        }
      });
      cpercField.addVerifyListener(new VerifyListener() {
        public void verifyText(VerifyEvent e) {
          for (int i = 0; i < e.text.length(); i++) {
            if (!Character.isDigit(e.text.charAt(i))) {
              e.doit = false;
              return;
            }}}
      });
      
      Label crunLabel = new Label(option3Group, SWT.NONE);
      crunLabel.setLayoutData(new RowData());
      crunLabel.setText("   Branches:");
      
      crunField = new Text(option3Group, SWT.SINGLE | SWT.BORDER);
      crunField.setLayoutData(new RowData(35, 20));
      Long crun = Main.getCheckRuns();
      crunField.setText(crun == null ? "" : "" + crun);
      crunText = crunField.getText();
      crunField.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          crunText = crunField.getText();         
          Long crun0 = Main.toLong(crunText);
          if (crun0 == null) crunField.setText("");
          Main.setCheckRuns(crun0);
        }
      });
      crunField.addVerifyListener(new VerifyListener() {
        public void verifyText(VerifyEvent e) {
          for (int i = 0; i < e.text.length(); i++) {
            if (!Character.isDigit(e.text.charAt(i))) {
              e.doit = false;
              return;
            }}}
      });
      
      Label g4Label = new Label(option4Group, SWT.NONE);
      g4Label.setLayoutData(new RowData());
      g4Label.setText("Parallelism:  ");
      
      threadOption = new Button(option4Group, SWT.CHECK);
      threadOption.setLayoutData(new RowData());
      threadOption.setText("Multi-Threaded");
      threadOption.setSelection(Main.getMultiThreaded());
      threadselected = threadOption.getSelection();
      threadOption.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          threadselected = threadOption.getSelection();
          Main.setMultiThreaded(threadselected);
        }
      });
      
      Label threadLabel = new Label(option4Group, SWT.NONE);
      threadLabel.setLayoutData(new RowData());
      threadLabel.setText("   Threads:");
      
      threadField = new Text(option4Group, SWT.SINGLE | SWT.BORDER);
      threadField.setLayoutData(new RowData(35, 20));
      Integer threads = Main.getThreads();
      threadField.setText(threads == null ? "" : "" + threads);
      threadText = threadField.getText();
      threadField.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e) {
          threadText = threadField.getText();         
          Integer threads0 = Main.toInteger(threadText);
          if (threads0 == null) threadField.setText("");
          Main.setThreads(threads0);
        }
      });
      threadField.addVerifyListener(new VerifyListener() {
        public void verifyText(VerifyEvent e) {
          for (int i = 0; i < e.text.length(); i++) {
            if (!Character.isDigit(e.text.charAt(i))) {
              e.doit = false;
              return;
            }}}
      });
      
      Label spaceLabel = new Label(option4Group, SWT.NONE);
      spaceLabel.setLayoutData(new RowData());
      spaceLabel.setText("  ");
      
      /*
      distOption = new Button(option4Group, SWT.CHECK);
      distOption.setLayoutData(new RowData());
      distOption.setText("Distributed");
      distOption.setSelection(Main.getDistributed());
      distselected = distOption.getSelection();
      distOption.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          distselected = distOption.getSelection();
          Main.setDistributed(distselected);
        }
      });
      
      
      Label scriptlabel = new Label(option4Group, SWT.NONE);
      scriptlabel.setLayoutData(new RowData());
      scriptlabel.setText("   Servers:");
       
      distCommand = new Button(option4Group, SWT.PUSH);
      //distCommand.setImage(valueImage);
      distCommand.setText("|||");
      distCommand.setLayoutData(new RowData(32,22));
      distCommand.setToolTipText("Startup Script");
      distCommand.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          //scriptDialog();
        }
      });
      */
    }
    
    /****************************************************************************
     * Toggle the tasks display area
     ***************************************************************************/
    private void toggleTasks()
    {
      tasks = !tasks;
      tasksGroup.setVisible(tasks);
      //layout();
      /*
      Point size = shell.getSize();
      if (tasks)
    	shell.setSize((int)(size.x/WF), (int)(size.y));
      else
    	shell.setSize((int)(size.x*WF), (int)(size.y));
      */
    }
    /***************************************************************************
     * Display tasks for currently selected function
     *************************************************************************/
    private void displayTasks()
    {
      if (funselected == -1)
      {
        //taskTree.displayTasks(null, null);
        return;
      }
      FunctionSymbol fun = funs.get(funselected);
      TaskFolder folder = fun.getTaskFolder();
      TaskFolder root = new TaskFolder(null, "root", null);
      folder.setParent(root);
      //taskTree.displayTasks(root, fun);
      folder.setParent(null);
    }
    /***************************************************************************
     * Display currently selected function in editor.
     **************************************************************************/
	private void showSelectedFunction()
    {
      if (funs == null || funselected < 0 || funselected >= funs.size()) 
      {
        //markText(null, true);
        return;
      }
      //FunctionSymbol fun = funs.get(funselected);
      //markText(fun.ident.getPosition(), true);
    }
    /****************************************************************************
     * Create the editor
     ***************************************************************************/
    // see the comment on "addStyledTextStrategy" below
    /*
    @SuppressWarnings("deprecation") 
    private void createEditor()
    {
      // the document being edited
      document = new Document("");
      
      
      IDocumentPartitioner partitioner = 
          new FastPartitioner(new SourcePartitionScanner(), 
              SourcePartitionScanner.PARTITIONS);
      partitioner.connect(document);
      document.setDocumentPartitioner(partitioner);
      
      
      // annotation model and access
      IAnnotationModel model = new AnnotationModel();
      IAnnotationAccess access = new SourceAnnotationAccess();
      
      // composite ruler (no visual representation)
      CompositeRuler cruler = new CompositeRuler();
      cruler.setModel(model);
      
      // column 0: annotation ruler 
      AnnotationRulerColumn aruler = new AnnotationRulerColumn(model, 16, access);
      aruler.addAnnotationType(ERROR_TYPE);
      cruler.addDecorator(0, aruler);
      
      // column 1: line number ruler
      lruler = new LineNumberRulerColumn();
      lruler.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
      cruler.addDecorator(1, lruler);

      // overview ruler on the right
      SharedTextColors colors = new SharedTextColors();
      OverviewRuler oruler = new OverviewRuler(access, 12, colors);
      oruler.setModel(model);
      oruler.addAnnotationType(ERROR_TYPE);
      oruler.setAnnotationTypeLayer(ERROR_TYPE, 3);
      oruler.setAnnotationTypeColor(ERROR_TYPE, 
        getDisplay().getSystemColor(SWT.COLOR_RED));
      oruler.addHeaderAnnotationType(ERROR_TYPE);
      
     // the editor itself
     editor = 
        new SourceViewer(editGroup, cruler, oruler, true,
          SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL); 
     editor.setDocument(document, model);
     Control tcontrol = editor.getControl();
     tcontrol.setLayoutData(new GridData(GridData.FILL_BOTH));
     editor.addTextListener(new ITextListener() {
       public void textChanged(TextEvent e) {
         setModified(true);
       }
     });
     editor.getTextWidget().addKeyListener(new KeyAdapter()
     {
       public void keyPressed(KeyEvent e) {
         // CTRL+# after keyword replaces text by operator
         if (e.character == '#' && (e.stateMask & SWT.CTRL) == SWT.CTRL)
           replaceByOperator();
       }
     });
     editor.configure(new MyEditorConfiguration(model));
     
     // set hover manager
     AnnotationBarHoverManager manager = 
       new AnnotationBarHoverManager(cruler, editor, new ErrorHover(model), 
         new ErrorInformationControlCreator());
     manager.install(aruler.getControl());
     
     // set annotation painter
     // only works with deprecated drawing strategy, 
     // not the newer text strategy, seems to be known bug
     // https://bugs.eclipse.org/bugs/show_bug.cgi?id=206913
     
     AnnotationPainter painter = new AnnotationPainter(editor, access);
     // painter.addTextStyleStrategy(UNDERLINE_SQUIGGLE, 
     //     new AnnotationPainter.UnderlineStrategy(SWT.UNDERLINE_SQUIGGLE));
     painter.addDrawingStrategy(UNDERLINE_SQUIGGLE, 
       new AnnotationPainter.SquigglesStrategy());
     painter.addAnnotationType(ERROR_TYPE, UNDERLINE_SQUIGGLE);
     painter.setAnnotationTypeColor(ERROR_TYPE, 
         getDisplay().getSystemColor(SWT.COLOR_RED));
     painter.addAnnotationType(PRIMARY_MARKER_TYPE, UNDERLINE_SQUIGGLE);
     painter.setAnnotationTypeColor(PRIMARY_MARKER_TYPE, 
         getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
     painter.addAnnotationType(SECONDARY_MARKER_TYPE, UNDERLINE_SQUIGGLE);
     painter.setAnnotationTypeColor(SECONDARY_MARKER_TYPE, 
         getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
     editor.addPainter(painter);
    }
    */
    private void createEditor() {
    	document = new Text(editGroup, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    	document.setLayoutData(new GridData(GridData.FILL_BOTH));
    	document.setText("");
    	document.addModifyListener(new ModifyListener() {
    	       @Override
    			public void modifyText(ModifyEvent evt) {
    	    	   setModified(true);
    			}
    	     });
    }
    /****************************************************************************
     * Set editor to a new file.
     * @param file the file (may be null)
     * @param empty if file is null and empty is true, 
     *        the editor content is to be emptied,
     *        otherwise the user is to be asked for a file.
     ***************************************************************************/
    private void setEditor(File file, boolean empty)
    {
      // make sure state is persistent
      Main.writePreferences();
      
      // save modified file
      if (modified)
      {
    	updateView(new Runnable() {
          public void run() {
            askCancelResult = askCancel("File Not Saved", "Save modified file?");
          }});
    	/*
        int answer = askCancelResult;
        if (answer == -1) return;
        if (answer == 1)
        {
          boolean okay = saveFile(false);
          if (!okay) return;
        }
        */
      }
      // get file
      File f;
      if (file != null)
    	f = file;
      else if (empty)
        f = null;
      else
      {
        //f = fileDialog(SWT.OPEN, "Open File");
    	f = Main.file;
        if (f == null) return;
      }

      // get text of file
      AST.Source s = null;
      String text = null;
      if (f != null)
      {
        // read new file
        List<String> lines = Main.readFile(f);
        if (lines == null) return;
        s = new AST.Source(f, lines);
        text = s.text;
      }
      
      // set document
      String text0 = text == null ? "" : text;
      Source s0 = s;
      updateView(new Runnable() {
        public void run() {
          document.setText(text0);
          //editor.getUndoManager().reset();
          setSource(s0);
          setModified(false);
        }
      });
      processSource();
      updateView(new Runnable() {
        public void run() {
          showSelectedFunction();
        }
      });
      
    }
    private int askCancelResult;
    
    /***************************************************************************
     * Set name of file in window.
     * @param file the file (null, if none).
     **************************************************************************/
    private void setSource(AST.Source source)
    {
      this.source = source;
      if (source == null || source.file == null)
      {
        editGroup.setText("File: (Untitled)");
        Main.setPath(null);
      }
      else
      {
        editGroup.setText("File: " + source.file.getPath());
        Main.setPath(source.file.getAbsolutePath());
      }
    }
    /****************************************************************************
     * Set file status to modified.
     * @modified true if file is modified.
     ***************************************************************************/
    private void setModified(boolean modified)
    {
      this.modified = modified;
      saveButton.setEnabled(modified);
      saveItem.setEnabled(modified);
      //saveAsItem.setEnabled(modified);
      downloadButton.setEnabled(!modified);
    }
    /****************************************************************************
     * Create the console area
     ***************************************************************************/
    private void createConsole()
    { 
      //progressBar = new ProgressBar(consoleGroup, SWT.NONE);
      //progressBar.setMinimum(0);
      //progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      console = new Text(consoleGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | 
          SWT.WRAP | SWT.READ_ONLY);
      console.setLayoutData(new GridData(GridData.FILL_BOTH));
      //console.setMargins(3,3,3,3);
      console.setTextLimit(100000);
      consoleOutput = new PrintWriter(new TextOutputStream(console), true);
    }
    
    /****************************************************************************
     * Create the task tree.
     ***************************************************************************/
    /*
    private void createTaskTree()
    {
      taskTree = new TaskTree(tasksGroup, this);
      taskTree.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
    }
    */
    /****************************************************************************
     * Open a file dialog.
     * @param mode the opening mode (SWT.OPEN or SWT.SAVE)
     * @param text the dialog text.
     * @return the selected file (null, if no file was selected).
     ***************************************************************************/
    /*
    private FileDialog fileDialog = null;
    private File fileDialogResult;
    private File fileDialog(int mode, String text)
    {
      fileDialogResult = null;
      updateView(new Runnable() {
        public void run() {
          if (fileDialog == null) fileDialog = new FileDialog(shell, mode);
          fileDialog.setFilterPath(System.getProperty("user.dir"));
          if (mode == SWT.SAVE) fileDialog.setOverwrite(true);
          fileDialog.open();
          String fileName = fileDialog.getFileName();
          if (fileName == null) return;
          if (fileName.equals("")) return;
          if (fileName.equals("Untitled")) return;
          String filterPath = fileDialog.getFilterPath();
          fileDialogResult = new File(filterPath, fileName);
        }
      });
      return fileDialogResult;
    }
    */
    /***************************************************************************
     * Open dialog to ask yes/no question.
     * @param title the dialog title
     * @param question the question asked
     * @return true iff question is answered positively.
     **************************************************************************/
    private boolean getAskResult;
    public boolean ask(String title, String question)
    {
      MessageBox box = 
        new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION
            | SWT.APPLICATION_MODAL);
      box.setText(title);
      box.setMessage(question);
      box.open(new DialogCallback() {
    	  public void dialogClosed( int returnCode ) {   
    		  if(returnCode == SWT.YES) {
    			  refresh();
    			  getAskResult = true;
    		  }
    		  if(returnCode == SWT.NO) {
    			  getAskResult = false;
    		  }
    		  
    	  }
      });
      //return box.open() == SWT.YES;
      return getAskResult;
    }
    
    /***************************************************************************
     * Open dialog to ask yes/no/cancel question.
     * @param title the dialog title
     * @param question the question asked
     * @return 0 for "no", 1 for "yes", -1 for "cancel. 
     **************************************************************************/
    private int getAskCancelResult;
    private int askCancel(String title, String question)
    {
      MessageBox box = 
        new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL | SWT.ICON_QUESTION
            | SWT.APPLICATION_MODAL);
      box.setText(title);
      box.setMessage(question);
      box.open(new DialogCallback() {
    	  public void dialogClosed( int returnCode ) {   
    		  if(returnCode == SWT.YES) {
    			  saveFile(false);
    			  getAskCancelResult = 1;
    		  }
    		  if(returnCode == SWT.NO) {
    			  getAskCancelResult = 0;
    		  }
    	  }
      });
      //if (result == SWT.YES) return 1;
      //if (result == SWT.NO) return 0;
      return getAskCancelResult;
      //return -1;
    }
    /***************************************************************************
     * Open dialog for setting constant values.
     **************************************************************************/
    private Map<String,Integer> map;
    private void valueDialog()
    {
      Display display = shell.getDisplay();
      Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.ON_TOP);
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
      add.setText("Add");
      add.setToolTipText("Add New Constant");
      add.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          TableItem item = new TableItem(table, SWT.NONE);
          item.setText(new String[] {"_", "0"});
        }
      });
      Button remove = new Button(tbuttons, SWT.PUSH);
      //remove.setImage(s.minusImage());
      remove.setText("Remove");
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
      shell.setSize(300, 300);
      openCentered(shell);
      /*
      while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) {
          display.sleep();
        }
      }
      */

      Map<String,Integer> map = getMap();
      if (map == null) return;
      Main.setValueMap(map);
      optmodified = true;
    }
    public Map<String,Integer> getMap()
    {
      return map;
    }
    /***************************************************************************
     * Open dialog for setting distributed excecution script.
     **************************************************************************/
    /*
    private void scriptDialog()
    {
      String script = Main.getDistScript();
      ScriptDialog window = ScriptDialog.construct(this, script);
      script = window.getScript();
      if (script != null) Main.setDistScript(script);
    }
    */
    /***************************************************************************
     * Open window centered at top-level window
     * @param window the window to be opened.
     **************************************************************************/
    public void openCentered(Shell window)
    {
      Point p = shell.getLocation();
      Point s = shell.getSize();
      Point s0 = window.getSize();
      window.setLocation(p.x+s.x/2-s0.x/2,p.y+s.y/2-s0.y/2);
      window.open();
    }
    
    /***************************************************************************
     * Set editor to empty file.
     **************************************************************************/
    private void newFile()
    {
      setEditor(null, true);
      downloadButton.setEnabled(false);
      //Main.file = null;
    }
    
    /***************************************************************************
     * Open new file in editor.
     **************************************************************************/
    private void openFile()
    {
      MainSWT.execute(new Runnable() {
        public void run()
        {
          setEditor(null, false);
          MainSWT.terminate();
        }
      });
    }
    /****************************************************************************
     * Open given file in editor.
     * @param file the file.
     ***************************************************************************/
    public void openFile(File file)
    {
      MainSWT.execute(new Runnable() {
        public void run()
        {
          Main.mainInit();        
          setEditor(file, false);
          MainSWT.terminate();
        }
      });
    }
    
    /***************************************************************************
     * Close file in editor.
     **************************************************************************/
    /*
    private void closeFile()
    {
      setEditor(true);
    }
    */
    
    /***************************************************************************
     * Save file in editor.
     * @param ask if true always ask for file name
     * @return true if file could be successfully saved.
     **************************************************************************/
    private boolean saveFile(boolean ask)
    {
      if (!modified) return true;
      File f = source == null ? null : source.file;
      //if (f == null || ask) f = fileDialog(SWT.SAVE, "Save File");
      //if (f == null) { return false; }
      //String text = editor.getDocument().get();
      if(f == null) {
  	    try {
			f = File.createTempFile("tmpFile", ".riscal");
			Main.file = f;
		} catch (IOException e) {
			Main.getOutput().println("Temp file create fail");
			return false;
		}
      }
      if (f == null) { return false; }
      String text = document.getText();
      try
      {
        PrintWriter writer = new PrintWriter(new FileWriter(f));
        writer.println(text);
        writer.close();
        Main.getOutput().println("Saved file " + f.getAbsolutePath());
      }
      catch(IOException e)
      {
        Main.error("Could not save file " + f.getAbsolutePath(), false);
        return false;
      }
      String[] lines = text.split(System.getProperty("line.separator"));
      Source s = new AST.Source(f, Arrays.asList(lines));
      updateView(new Runnable() {
        public void run() {
          setSource(s);
          setModified(false);
        }
      });
      
      MainSWT.execute(new Runnable() {
        public void run() {
          processSource();
          MainSWT.terminate();
        }
      });
      
      return true;
    }
    /***************************************************************************
     * Check file.
     **************************************************************************/
    private void checkFile()
    {
      if (!optmodified && funs != null) return;
      MainSWT.execute(new Runnable() {
        public void run()
        {
          processSource();
          MainSWT.terminate();
          //Main.getOutput().println("Check File end");
        }
      });
    }
    
    /****************************************************************************
     * Start the execution of some activity.
     * @param changeable if false, then do not perform operation if model is changed
     * @param runnable the activity to be performed.
     ***************************************************************************/
    public void startExecution(boolean changeable, Runnable runnable)
    {
      //Main.getOutput().println("Start Execution");
      MainSWT.execute(new Runnable()
      {
        public void run() 
        {
          boolean change = updateModel();
          if (!change || changeable) runnable.run();
          MainSWT.terminate();
          //Main.getOutput().println("End Execution");
        }
        private int answer;
        public boolean updateModel()
        {
          if (modified)
          {
            // save modified file
        	
            updateView(new Runnable() {
              public void run() {
                answer = askCancel("File Not Saved", "Save modified file?");
              }});
            //if (answer != 1) return true;
            /*
            updateView(new Runnable() {
              public void run() {
                saveFile(false);
              }});
              */
            return true;
          }
          if (optmodified || funs == null)
          {
            processSource();
            return true;
          }
          return false;
        }
      });
    }
    /****************************************************************************
     * Get editor input.
     ***************************************************************************/
	public static String getDocumentInput(){
		String data = document.getText();
		return data;
	}
    /****************************************************************************
     * Start the analysis.
     ***************************************************************************/
    private void startAnalysis()
    {
      //Main.getOutput().println("Start Analysis");
      startExecution(true, new Runnable()
      {
        public void run() 
        {
          if (funselected == -1 || funs == null)
            fun = null;
          else
            fun = funs.get(funselected);
          if (fun != null) execute(fun);
        }
      });
    }
    
    /****************************************************************************
     * Execute function/procedure with all possible arguments.
     * @param fun the symbol of the function/procedure.
     * @return the success of the execution.
     ***************************************************************************/
    public boolean execute(FunctionSymbol fun)
    {
      signalRunning(true);
      boolean result = Main.execute(fun);
      signalRunning(false);
      return result;
    }
    
    /****************************************************************************
     * Enable/disable stop button.
     * @param the enabling status
     ***************************************************************************/
    public void enableStopButton(boolean enabled)
    {
      stopButton.setEnabled(enabled);
    }
    
    /****************************************************************************
     * Show trace of program execution (if any)
     * @param header the header of the trace window
     ***************************************************************************/
    /*
    public void showTrace(String header)
    {
      TraceWindow t = TraceWindow.construct(getDisplay(), iconImage,
          header, 1000, 700);
      Shell tshell = t.shell;
      Display display = tshell.getDisplay();
      openCentered(tshell);
      while (!tshell.isDisposed())
      {
        if (!display.readAndDispatch())
          display.sleep();
        try
        {
          Main.checkStopped();
        }
        catch(Main.Stopped e)
        {
          tshell.close();
          throw e;
        }
      }
    }
    */
    /***************************************************************************
     * Process source file.
     **************************************************************************/
    private void processSource()
    {
      //clearErrors();
      signalRunning(true);
      Specification spec = Main.mainCore(source);
      signalRunning(false);
      setFunctions(spec);
      optmodified = false;
    }
    
    /****************************************************************************
     * Set the global list of functions from the given specification;
     * also changes the corresponding selection menu.
     * @param spec the specification (null on error)
     ***************************************************************************/
    private void setFunctions(Specification spec)
    {
      funselected = -1;
      Main.setSelectedFunction(null);
      if (spec == null)
      {
        funs = null;
        updateView(new Runnable() {
          public void run() {
            methodMenu.setItems(new String[] { });
            analyzeGroup.layout(true, true);
            displayTasks();
          }
        });
        Main.setFunctions(null);
        Main.setSelectedFunction(null);
        showSelectedFunction();
        return;
      }
      funs = Main.getFunctions(spec, false);
      int n = funs.size();
      String[] names = new String[n];
      for (int i=0; i<n; i++)
      {
        FunctionSymbol f = funs.get(i);
        names[i] = Main.shortName(f);
        if (names[i].length() > 40)
          names[i] = names[i].substring(0, 40) + "...";
      }
      
      updateView(new Runnable() {
        public void run() {
          // do not change selection, if possible
          int i = methodMenu.getSelectionIndex();
          String[] names0 = methodMenu.getItems();
          methodMenu.setItems(names);
          analyzeGroup.layout(true, true);
          Main.setFunctions(names);
          if (-1 < i && i < names.length && names[i].equals(names0[i])) 
          {
            funselected = i;
            methodMenu.select(funselected);
            Main.setSelectedFunction(funselected);
          }
          else
          {
            funselected = -1;
            methodMenu.deselectAll();
            Main.setSelectedFunction(null);
            showSelectedFunction();
          }
          displayTasks();
        }
      });
    }
    /***************************************************************************
     * Signal run status of background process.
     * @param running if we are waiting for background process to complete.
     **************************************************************************/
    private void signalRunning(final boolean running)
    {
      updateView(new Runnable() {
        public void run() {
          // setEnabled(!running);
          setControlEnabled(!running);
          if (running)
          {
            //setCursor(waitCursor);
            //editor.getTextWidget().setCursor(waitCursor);
            //console.setCursor(waitCursor);
        	//Main.getOutput().println("Start Execution\n");
          }
          else
          {
            //setCursor(arrowCursor);
            //editor.getTextWidget().setCursor(arrowCursor);
            //console.setCursor(arrowCursor);
        	//Main.getOutput().println("\nStop Execution\n");
          }
        }});
    }
    
    /***************************************************************************
     * Set enabled status of controls where
     * - status of stop button is reverted
     * - save button is not considered
     * @param enabled are buttons enabled?
     **************************************************************************/
    private void setControlEnabled(final boolean enabled)
    {
      stopButton.setEnabled(!enabled);
      menu.setEnabled(enabled);
      newButton.setEnabled(enabled);
      openButton.setEnabled(enabled);
      //saveButton.setEnabled(enabled);
      silentOption.setEnabled(enabled);
      nondetOption.setEnabled(enabled);
      vsizeField.setEnabled(enabled);
      // inumField.setEnabled(enabled);
      cnumField.setEnabled(enabled);
      cpercField.setEnabled(enabled);
      crunField.setEnabled(enabled);
      threadOption.setEnabled(enabled);
      threadField.setEnabled(enabled);
      //distOption.setEnabled(enabled);
      //distCommand.setEnabled(enabled);
      valueButton.setEnabled(enabled);
      methodMenu.setEnabled(enabled);
      startButton.setEnabled(enabled);
      clearButton.setEnabled(enabled);
      //refreshButton.setEnabled(enabled);
      checkButton.setEnabled(enabled);
      //recordButton.setEnabled(enabled && logStream == null);
      //stoprecordButton.setEnabled(enabled && logStream != null);
      tasksButton.setEnabled(enabled);
    }

    /***************************************************************************
     * Execute display update request on behalf of application.
     * @param request the request.
     **************************************************************************/
    public void updateView(Runnable request)
    {
      shell.getDisplay().syncExec(request);
    }

    /***************************************************************************
     * Reset status to initial one.
     **************************************************************************/
    public void refresh()
    {
      //log(false);
      document.setText("");
      silentOption.setSelection(false);
      nondetOption.setSelection(false);
      Main.setNondeterministic(false);
      vsizeField.setText("0");
      Main.setDefaultValue(null);
      Main.setValueMap(new LinkedHashMap<String,Integer>());
      funs = null;
      //methodMenu.setItems(new String[] {});
      analyzeGroup.layout(true, true);
      Main.setFunctions(null);
      funselected = -1;
      Main.setSelectedFunction(null);
      source = null;
      Main.setPath(null);
      modified = false;
      funs = null;
      fun = null;
      optmodified = false;
      nondetselected = false;
      vtext = null;
      // mtext = null;
      Main.terminateServers();
      console.setText(Main.copyright + "\n");

      editGroup.setText("File: (Untitled)");
      saveButton.setEnabled(false);
      downloadButton.setEnabled(false);
      Main.file = null;
      
    }
    
    /***************************************************************************
     * Control logging output to file.
     * @param start if true, start logging, if false, stop it.
     **************************************************************************/
    /*
    public void log(boolean start)
    {
      try
      {
        // get file
        File f = start ? fileDialog(SWT.SAVE, "Set Logfile") : null;
        if (f == null) start = false;
        
        // reset logfile for error printing to console
        File oldFile = logFile;
        OutputStream oldStream = logStream;
        logFile = null;
        logStream = null;  
        
        // close old logfile
        if (oldStream != null) 
        {
          consoleOutput.println("Stop logging to file " + oldFile.getAbsolutePath());
          oldStream.close();
        }
        
        // open new one
        if (f == null)
          analyzeGroup.setText("Analysis");
        else
        {
          consoleOutput.println("Start logging to file " + f.getAbsolutePath());
          logFile = f;
          logStream = new FileOutputStream(logFile);
          analyzeGroup.setText("Analysis (logfile " + 
            logFile.getAbsolutePath() + ")");
        }
      }
      catch(IOException e)
      {
        consoleOutput.println("ERROR: " + e.getMessage());
        start = false;
      }
      
      // switch button status
      recordButton.setEnabled(!start);
      stoprecordButton.setEnabled(start);
    }
    */
    /***************************************************************************
     * Replace in editor text before current caret position by operator.
     **************************************************************************/
    /*
    private void replaceByOperator()
    {
      //String text = document.get();
      String text = document.getText();
      int end = editor.getTextWidget().getCaretOffset();
      int begin = end;
      boolean isLetter = end != 0 && isLetterOrDigit(text.charAt(end-1));
      while (true)
      {
        begin--;
        if (begin == -1) break;
        char ch = text.charAt(begin);
        if (Character.isWhitespace(ch)) break;
        if (isLetter)
        {
          if (!isLetterOrDigit(ch)) break;
        }
        else
        {
          if (isLetterOrDigit(ch)) break;
        }
      }
      begin++;
      String word = text.substring(begin, end);
      String op = null;
      switch (word)
      {
      case ":=": op = "≔"; break;
      case "true": op = "⊤"; break;
      case "false": op = "⊥"; break;
      case "{}": op = "∅"; break;
      case "Intersect": op = "⋂"; break;
      case "Union": op = "⋃"; break;
      case "<<": op = "⟨"; break;
      case ">>": op = "⟩"; break;
      case "intersect": op = "∩"; break;
      case "union": op = "∪"; break;
      case "~=": op = "≠"; break;
      case "<=": op = "≤"; break;
      case ">=": op = "≥"; break;
      case "isin": op = "∈"; break;
      case "subseteq": op = "⊆"; break;
      case "~": op = "¬"; break;
      case "/\\": op = "∧"; break;
      case "\\/": op = "∨"; break;
      case "=>": op = "⇒"; break;
      case "<=>": op = "⇔"; break;
      case "forall": op = "∀"; break;
      case "exists": op = "∃"; break;
      case "*": op = "⋅"; break;
      case "Nat": op = "ℕ"; break;
      case "Int": op = "ℤ"; break;
      case "sum": op = "∑"; break;
      case "product": op = "∏"; break;
      case "times": op = "×"; break;
      }
      if (op == null) return;
      String text0 = text.substring(0, begin) + op + text.substring(end);
      //Text widget = editor.getTextWidget();
      //int line = widget.getTopIndex();
      //document.set(text0);
      document.setText(text0);
      //widget.setTopIndex(line);
      //widget.setCaretOffset(begin+op.length());
    }
    */
    /****************************************************************************
     * Report some error in the source code.
     * @param message the error message
     * @param position the position of the error (lineTo and charTo may be -1)
     ***************************************************************************/
    /*
    public void reportError(String message, SourcePosition position)
    {
      int lineFrom = lineFrom(position);
      Position p = getPosition(position);
      ErrorAnnotation annotation = new ErrorAnnotation(message, lineFrom);
      IAnnotationModel model = editor.getAnnotationModel();
      model.addAnnotation(annotation, p);
    }
    */
    // current list of markers
    //List<MarkerAnnotation> markers = new ArrayList<MarkerAnnotation>();
    
    /****************************************************************************
     * Mark text in the source code.
     * @param position the position to be marked (lineTo and charTo may be -1)
     * @param remove true if previous annotations are to be removed
     *        (null, if any previous marking is to be removed)
     ***************************************************************************/
    /*
    public void markText(SourcePosition position, boolean remove)
    {
      IAnnotationModel model = editor.getAnnotationModel();
      if (remove) 
      {
        for (MarkerAnnotation m : markers) model.removeAnnotation(m);
        markers.clear();
      }
      if (position == null) return;
      int lineFrom = lineFrom(position);
      Position p = getPosition(position);
      MarkerAnnotation marker = new MarkerAnnotation(remove);
      markers.add(marker);
      model.addAnnotation(marker, p);
      if (remove)
      {
        int top = editor.getTopIndex();
        int bottom = editor.getBottomIndex();
        if (lineFrom < top || lineFrom > bottom)
        {
          int dist = (bottom-top)/2;
          editor.setTopIndex(Math.max(0,lineFrom-dist));
        }
      }
    }
    */
    /****************************************************************************
     * Get line at which position starts in source.
     * @param position the position.
     * @return the line.
     ***************************************************************************/
    /*
    private static int lineFrom(SourcePosition position)
    {
      int lineFrom = position.lineFrom;
      int size = position.source.lines.size();
      if (lineFrom >= size) 
      {
        if (size == 0)
          lineFrom = 0;
        else
          lineFrom = size-1;
      }
      return lineFrom;
    }
    */
    /****************************************************************************
     * Translate source position to viewer position
     * @param position the source position.
     * @return the viewer position.
     ***************************************************************************/
    /*
    private static Position getPosition(SourcePosition position)
    {
      int lineFrom = lineFrom(position);
      int charFrom = position.charFrom;
      int size = position.source.lines.size();
      if (lineFrom >= size) charFrom = 0;
      int from = getOffset(lineFrom, charFrom, position.source.lines);
      int to;
      if (position.lineTo == -1 || 
          (position.lineTo == lineFrom && position.charTo <= charFrom))
      {
        int l = position.lineFrom;
        if (l >= size)
        {
          to = Math.max(0,getLength(position.source.lines)-2);
        }
        else
        {
          int end = wordEnd(position.source.lines.get(l), charFrom);
          to = getOffset(lineFrom, end, position.source.lines);
        }
      }
      else if (position.charTo == -1) 
      {
        int l = position.lineTo;
        if (l >= size)
        {
          to = Math.max(0,getLength(position.source.lines)-2);
        }
        else
        {
          int end = wordEnd(position.source.lines.get(l), position.charTo);
          to = getOffset(position.lineTo, end, position.source.lines);
        }
      }
      else
        to = getOffset(position.lineTo, position.charTo, position.source.lines);
      return new Position(from, to-from+1);
    }
    */
    /****************************************************************************
     * Get position of the last character of a word.
     * @param line the line where the word occurs.
     * @param pos the position where the word starts.
     * @return the end position.
     ***************************************************************************/
    /*
    private static int wordEnd(String line, int pos)
    {
      int end = pos;
      while (end+1 < line.length() && isLetterOrDigit(line.charAt(end+1)))
        end++;
      return end;
    }
    */
    /****************************************************************************
     * Return true if character can appear in word.
     * @param ch the character.
     * @return true if ch can appear in a word.
     ***************************************************************************/
    
    private static boolean isLetterOrDigit(char ch)
    {
      return Character.isLetterOrDigit(ch) || ch == '_';
    }
    
    /****************************************************************************
     * Remove all error reports from source code.
     ***************************************************************************/
    public void clearErrors()
    {
      // clear source
      //AnnotationModel model = (AnnotationModel)editor.getAnnotationModel();
      //model.removeAllAnnotations();
    }
    
    /****************************************************************************
     * Get offset of position in file.
     * @param line the line number.
     * @param pos the position within the line.
     * @param lines the list of all lines.
     * @return the offset.
     ***************************************************************************/
    /*
    private static int getOffset(int line, int pos, List<String> lines)
    {
      int offset = pos;
      for (int i=0; i<line; i++)
        offset += lines.get(i).length()+1; // consider new line
      return offset;
    }
    */
    /****************************************************************************
     * Get length of file.
     * @param lines the list of all lines.
     * @return the length.
     ***************************************************************************/
    /*
    private static int getLength(List<String> lines)
    {
      int length = 0;
      for (String line : lines)
        length += line.length()+1; // consider new line
      return length;
    }
    */
    /***************************************************************************
     * Get console output
     * @return the console output
     **************************************************************************/
    public PrintWriter consoleOutput()
    {
      return consoleOutput;
    }
    
    /***************************************************************************
     * Get various images.
     * @return the image.
     **************************************************************************/
    //public Image iconImage() { return iconImage; }
    //public Image plusImage() { return plusImage; }
    //public Image minusImage() { return minusImage; }
    
    /****************************************************************************
     * Get progress bar
     * @return the progress bar.
     ***************************************************************************/
    public ProgressBar progressBar() { return progressBar; }
    
    //-------------------------------------------------------------------------
    //
    // helper classes
    //
    // -------------------------------------------------------------------------
    
    /****************************************************************************
     * A map from a text field to an output stream.
     ***************************************************************************/
    private final class TextOutputStream extends OutputStream
    {
      // the widget
      private Text output;
      
      /*************************************************************************
       * Create an output stream from a Text widget.
       * @param output the widget that displays the output written to the stream.
       *************************************************************************/
      public TextOutputStream(Text output)
      {
        this.output = output;
      }
      
      /**************************************************************************
       * Write a section of an byte array to the output stream.
       * @param b the byte array.
       * @param offset the array index where the section starts.
       * @param length the length of the section.
       *************************************************************************/
      public void write(byte[] b, int offset, int length)
      {
        final String text = new String(b, offset, length);
        
        if (logStream != null)
        {
          try
          {
            logStream.write(b, offset, length);
            logStream.flush();
          }
          catch(IOException e)
          {
            //System.out.println("Error when writing to file " + 
                //logFile.getAbsolutePath() + ": " + e.getMessage());
          }
        }
        
        if (output.isDisposed()) return;
        
        updateView(new Runnable() {
          public void run() {
            //int l = output.getLineCount();
        	//int l = output.getLineHeight();
            if (output.isDisposed()) return;
            //int n = output.getText().length();
            //int limit = output.getTextLimit(); 
            //if (n > limit) output.replaceTextRange(0, n-limit, "");
            output.append(text);
            //int l0 = output.getLineCount();
            //int l0 = output.getLineHeight();
            //if (l != l0) output.setTopIndex(l0-1);
            output.update();
          }
        });
        
      }

      /**************************************************************************
       * Write a byte array to the output stream.
       * @param b the byte array written
       *************************************************************************/
      public void write(byte[] b)
      {
        write(b, 0, b.length);
      }

      /**************************************************************************
       * Write a byte to the output stream.
       * @param b the byte written
       *************************************************************************/
      public void write(int b)
      {
        byte[] a = new byte[1];
        a[0] = (byte)b;
        write(a);
      }
    }
    
    //-------------------------------------------------------------------------
    // the undo manager used for text viewers
    //-------------------------------------------------------------------------
    /*
    private class MyUndoManager extends TextViewerUndoManager
    {
      public MyUndoManager(int level)
      {
        super(level);
      }
      public void undo()
      {
        super.undo();
        setModified(true);
      }
    }
    */
    //-------------------------------------------------------------------------
    // the viewer configuration for class files
    //-------------------------------------------------------------------------
    /*
    private class MyEditorConfiguration extends SourceViewerConfiguration
    { 
      private IAnnotationModel model;
      public MyEditorConfiguration(IAnnotationModel model)
      {
        this.model = model;
      }
      
      public IPresentationReconciler 
      getPresentationReconciler(ISourceViewer viewer)
      {
        PresentationReconciler reconciler = new PresentationReconciler();

        // reconcile comments
        RuleBasedScanner cscanner = new CommentScanner();
        DefaultDamagerRepairer cdrepairer = new DefaultDamagerRepairer(cscanner);
        reconciler.setDamager(cdrepairer, SourcePartitionScanner.COMMENT);
        reconciler.setRepairer(cdrepairer, SourcePartitionScanner.COMMENT);
        
        // reconcile literals
        RuleBasedScanner lscanner = new LiteralScanner();
        DefaultDamagerRepairer ldrepairer = new DefaultDamagerRepairer(lscanner);
        reconciler.setDamager(ldrepairer, SourcePartitionScanner.LITERAL);
        reconciler.setRepairer(ldrepairer, SourcePartitionScanner.LITERAL);
        
        // reconcile specification
        RuleBasedScanner sscanner = new SpecScanner();
        DefaultDamagerRepairer sdrepairer = new DefaultDamagerRepairer(sscanner);
        reconciler.setDamager(sdrepairer, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(sdrepairer, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
      }
      
      public IUndoManager getUndoManager(ISourceViewer viewer)
      {
        return new MyUndoManager(99);
      }
      
      public IAutoEditStrategy[] 
      getAutoEditStrategies(ISourceViewer sourceViewer, String contentType)
      {
        return new IAutoEditStrategy[] { 
            new DefaultIndentLineAutoEditStrategy() };
      }
      
      public IAnnotationHover getAnnotationHover(ISourceViewer viewer)
      {
        return new ErrorHover(model);
      }
    }
    */
    //-------------------------------------------------------------------------
    // the source partition scanner: 
    // - comment partitions for appropriate handling of comments
    // - literal partitions for avoiding highlighting inside string literals
    // - specification literals as a special kind of comments
    //-------------------------------------------------------------------------
    /*
    private static class SourcePartitionScanner extends RuleBasedPartitionScanner
    {
      public final static String COMMENT = "comment";
      public final static String LITERAL = "literal";
      public final static String[] PARTITIONS = { COMMENT, LITERAL } ;
      
      public SourcePartitionScanner()
      {
        // single/multi-line comments
        IToken comment = new Token(COMMENT);
        EndOfLineRule scomments = new EndOfLineRule("//", comment);
        MultiLineRule mcomments = new MultiLineRule("/*", "*\/", comment);
        
        // string/character literals
        IToken literal = new Token(LITERAL);
        SingleLineRule strings = new SingleLineRule("\"", "\"", literal, '\\');
        SingleLineRule chars = new SingleLineRule("'", "'", literal, '\\');
        
        setPredicateRules(new IPredicateRule[] { 
          scomments, mcomments, strings, chars });
      }
    }
    */
    // the comment scanner
    /*
    private class CommentScanner extends RuleBasedScanner
    {
      public CommentScanner()
      {
        // get current display
        Display display = Display.getCurrent();
        
        // define token
        Color commentColor = display.getSystemColor(SWT.COLOR_DARK_GREEN);
        IToken commentToken = new Token(new TextAttribute(commentColor));
   
        // set token
        this.setDefaultReturnToken(commentToken);
      }
    }
    */
    // the string literal scanner
    /*
    private class LiteralScanner extends RuleBasedScanner
    {
      public LiteralScanner()
      {
        // get current display
        Display display = Display.getCurrent();
        
        // define token
        Color literalColor = display.getSystemColor(SWT.COLOR_DARK_RED);
        IToken literalToken = new Token(new TextAttribute(literalColor));
   
        // set token
        this.setDefaultReturnToken(literalToken);
      }
    }
    */
    //-------------------------------------------------------------------------
    // the syntax scanner for specifications
    //-------------------------------------------------------------------------
    /*
    private class SpecScanner extends RuleBasedScanner
    {
      public SpecScanner()
      {
        // get current display
        Display display = Display.getCurrent();
        
        // define tokens
        Color keywordColor = display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
        IToken keywordToken = new Token(new TextAttribute(keywordColor));
        Color typeColor = display.getSystemColor(SWT.COLOR_DARK_CYAN);
        IToken typeToken = new Token(new TextAttribute(typeColor));
        Color constColor = display.getSystemColor(SWT.COLOR_BLUE);
        IToken constToken = new Token(new TextAttribute(constColor));
        Color quantifierColor = display.getSystemColor(SWT.COLOR_RED);
        IToken quantifierToken = new Token(new TextAttribute(quantifierColor));
        Color connectiveColor = display.getSystemColor(SWT.COLOR_BLUE);
        IToken connectiveToken = new Token(new TextAttribute(connectiveColor));
        
        // define keywords
        WordRule keywords = new WordRule(new WordDetector());
        keywords.addWord("val", keywordToken);
        keywords.addWord("multiple", keywordToken);
        keywords.addWord("fun", keywordToken);
        keywords.addWord("pred", keywordToken);
        keywords.addWord("proc", keywordToken);
        keywords.addWord("type", keywordToken);
        keywords.addWord("rectype", keywordToken);
        keywords.addWord("enumtype", keywordToken);
        keywords.addWord("theorem", keywordToken);
        keywords.addWord("requires", keywordToken);
        keywords.addWord("ensures", keywordToken);
        keywords.addWord("decreases", keywordToken);
        keywords.addWord("inline", keywordToken);
        keywords.addWord("choose", keywordToken);
        keywords.addWord("do", keywordToken);
        keywords.addWord("while", keywordToken);
        keywords.addWord("var", keywordToken);
        keywords.addWord("assert", keywordToken);
        keywords.addWord("print", keywordToken);
        keywords.addWord("if", keywordToken);
        keywords.addWord("then", keywordToken);
        keywords.addWord("else", keywordToken);
        keywords.addWord("match", keywordToken);
        keywords.addWord("with", keywordToken);
        keywords.addWord("for", keywordToken);
        keywords.addWord("invariant", keywordToken);
        keywords.addWord("let", keywordToken);
        keywords.addWord("letpar", keywordToken);
        keywords.addWord("modular", keywordToken);
        keywords.addWord("in", keywordToken);
        keywords.addWord("return", keywordToken);
        keywords.addWord("check", keywordToken);
        
        // define builtin types
        WordRule types = new WordRule(new WordDetector());
        types.addWord("Unit", typeToken);
        types.addWord("Bool", typeToken);
        types.addWord("Int", typeToken);
        types.addWord("ℤ", typeToken);
        types.addWord("Nat", typeToken);
        types.addWord("ℕ", typeToken);
        types.addWord("Map", typeToken);
        types.addWord("Tuple", typeToken);
        types.addWord("Record", typeToken);
        types.addWord("Set", typeToken);
        types.addWord("Array", typeToken);
        
        // constants
        WordRule consts = new WordRule(new WordDetector());
        consts.addWord("⊤", constToken);
        consts.addWord("true", constToken);
        consts.addWord("⊥", constToken);
        consts.addWord("false", constToken);
        consts.addWord("∅", constToken);
        consts.addWord("forSet", constToken);
        consts.addWord("chooseSet", constToken);
        consts.addWord("isin", constToken);
        consts.addWord("subseteq", constToken);
        
        // logical connectives
        WordRule connectives = new WordRule(new ConnectiveDetector());
        connectives.addWord("¬", connectiveToken);
        connectives.addWord("~", connectiveToken);
        connectives.addWord("∧", connectiveToken);
        connectives.addWord("/\\", connectiveToken);
        connectives.addWord("∨", connectiveToken);
        connectives.addWord("\\/", connectiveToken);
        connectives.addWord("⇒", connectiveToken);
        connectives.addWord("=>", connectiveToken);
        connectives.addWord("⇔", connectiveToken);
        connectives.addWord("<=>", connectiveToken);
        
        // quantifiers as symbols
        WordRule quantifiers = new WordRule(new QuantifierDetector());
        quantifiers.addWord("∀", quantifierToken);
        quantifiers.addWord("∃", quantifierToken);
        quantifiers.addWord("#", quantifierToken);
        quantifiers.addWord("∑", quantifierToken);
        quantifiers.addWord("Σ", quantifierToken);
        quantifiers.addWord("∏", quantifierToken);
        quantifiers.addWord("Π", quantifierToken);
        
        // quantifiers as words
        WordRule quantifiers2 = new WordRule(new WordDetector());
        quantifiers2.addWord("forall", quantifierToken);
        quantifiers2.addWord("exists", quantifierToken);
        quantifiers2.addWord("sum", quantifierToken);
        quantifiers2.addWord("product", quantifierToken);
        quantifiers2.addWord("min", quantifierToken);
        quantifiers2.addWord("max", quantifierToken);
        
        // hack to avoid some partial keyword matchings ("main", "evaluate", ...)
        keywords.addWord("ain", Token.UNDEFINED);
        keywords.addWord("eval", Token.UNDEFINED);
        
        // set rules
        IRule[] rules = 
          new IRule[] { keywords, types, consts, connectives, 
              quantifiers, quantifiers2 } ;
        setRules(rules);
      }
    }
    */
    //-------------------------------------------------------------------------
    // my word detector (identifier-like)
    //-------------------------------------------------------------------------
    /*
    private class WordDetector implements IWordDetector 
    {
      public boolean isWordStart(char ch)  
      {
        return isLetterOrDigit(ch) && ch != '_';
      }     
      public boolean isWordPart(char ch) 
      {
        return isLetterOrDigit(ch);
      }
     }
    */
    //-------------------------------------------------------------------------
    // my connective detector
    //-------------------------------------------------------------------------
    /*
    private class ConnectiveDetector implements IWordDetector 
    {
      public boolean isWordStart(char ch) 
      {
        return ch == '¬' || ch == '~' 
            || ch == '∧' || ch == '∨'
            || ch == '⇒' || ch == '⇔'
            || ch == '/' || ch == '\\'
            || ch == '=' || ch == '<';
      }
      public boolean isWordPart(char ch)  
      {
        return ch == '>' || ch == '=' || ch == '>' || ch == '\\' || ch == '/';
      }       
    }
    */
    //-------------------------------------------------------------------------
    // my quantifier detector
    //-------------------------------------------------------------------------
    /*
    private class QuantifierDetector implements IWordDetector 
    {
      public boolean isWordStart(char ch) 
      {
        return ch == '∀' || ch == '∃' 
            || ch == '#' 
            || ch == '∑' || ch == 'Σ' 
            || ch == '∏' || ch == 'Π' 
            ;
      }
      public boolean isWordPart(char ch)  
      {
        return false;
      }       
    }
    */
    // -------------------------------------------------------------------------
    // source annotation access: error markers and folding markers
    // -------------------------------------------------------------------------
    /*
    private class SourceAnnotationAccess 
    implements IAnnotationAccess, IAnnotationAccessExtension 
    {
      public Object getType(Annotation annotation) 
      { 
        return annotation.getType(); 
      }
      
      public boolean isMultiLine(Annotation annotation) 
      { 
        return false; 
      }

      public boolean isTemporary(Annotation annotation) 
      { 
        return !annotation.isPersistent(); 
      }

      public String getTypeLabel(Annotation annotation) 
      {
        return "Errors";
      }

      public int getLayer(Annotation annotation) 
      {
        return 3;
      }

      public void paint(Annotation annotation, GC gc, Canvas canvas, 
        Rectangle bounds) 
      {
        Image image = null;
        if (annotation instanceof ErrorAnnotation)
          image = errorImage;
        if (image != null)
          ImageUtilities.drawImage(image, gc, canvas, bounds, SWT.CENTER, SWT.TOP);
      }

      public boolean isPaintable(Annotation annotation) 
      {
        return true;
      }

      public boolean isSubtype(Object annotationType, Object potentialSupertype) 
      {
        return (annotationType.equals(potentialSupertype));
      }

      public Object[] getSupertypes(Object annotationType) 
      {
        return new Object[0];
      }
    }
    */
    //-------------------------------------------------------------------------
    // the error annotation
    //-------------------------------------------------------------------------
    /*
    private class ErrorAnnotation extends Annotation 
    {
      private int line;
      
      public ErrorAnnotation(String text, int line) 
      {
        super(ERROR_TYPE, true, text);
        this.line = line;
      }
      
      public int getLine()
      {
        return line;
      }
    }
    */
    //-------------------------------------------------------------------------
    // the marker annotation
    //-------------------------------------------------------------------------
    /*
    private class MarkerAnnotation extends Annotation 
    {   
      public MarkerAnnotation(boolean primary) 
      {
        super(primary ? PRIMARY_MARKER_TYPE : SECONDARY_MARKER_TYPE, false, "");
      }
    }
    */
    //-------------------------------------------------------------------------
    // error annotation access
    //-------------------------------------------------------------------------
    /*
    private class SharedTextColors implements ISharedTextColors
    {
      public Color getColor(RGB rgb) { return new Color(Display.getDefault(), rgb); }
      public void dispose() { }
    }
    */
    // --------------------------------------------------------------------------
    // error annotation hover
    // --------------------------------------------------------------------------
    /*
    private class ErrorHover implements IAnnotationHover, ITextHover 
    {
      private IAnnotationModel model;
      
      public ErrorHover(IAnnotationModel model)
      {
        this.model = model;
      }
      
      public String getHoverInfo(ISourceViewer viewer, int line) 
      {
        Iterator<Annotation> iterator = model.getAnnotationIterator();
        while (iterator.hasNext()) 
        {
          Annotation info = iterator.next();
          if (!(info instanceof ErrorAnnotation)) continue;
          ErrorAnnotation annotation = (ErrorAnnotation)info;
          if (annotation.getLine() == line) 
            return annotation.getText();
        }
        return null;
      }

      public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) 
      {
        return null;
      }

      public IRegion getHoverRegion(ITextViewer textViewer, int offset) 
      {
        return null;
      }
    }
	*/
    // --------------------------------------------------------------------------
    // annotation configuration
    // --------------------------------------------------------------------------
    /*
    private class ErrorInformationControlCreator
    implements IInformationControlCreator {
      public IInformationControl createInformationControl(Shell shell) 
      {
        return new DefaultInformationControl(shell);
      }
    }
    */
    // --------------------------------------------------------------------------
    // get various images
    // --------------------------------------------------------------------------
    //public Image getStartImage() { return startImage; }
    //public Image getTaskOpenImage() { return taskOpenImage; }
    //public Image getTaskClosedImage() { return taskClosedImage; }
}  
  // ---------------------------------------------------------------------------
  // end of file
  // ---------------------------------------------------------------------------
