//---------------------------------------------------------------------------
//Main.java
//RISC Algorithm Language main program.
//$Id: Main.java,v 1.99 2018/06/14 13:22:44 schreine Exp schreine $
//
//Author: Wolfgang Schreiner <Wolfgang.Schreiner@risc.jku.at>
//Copyright (C) 2016-, Research Institute for Symbolic Computation (RISC)
//Johannes Kepler University, Linz, Austria, http://www.risc.jku.at
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
//----------------------------------------------------------------------------
package riscal;

import java.io.*;
import java.util.*;
import java.util.prefs.*;
//import java.awt.SplashScreen;

import org.antlr.v4.runtime.*;

import riscal.syntax.*;
import riscal.syntax.AST.Source;
import riscal.syntax.AST.SourcePosition;
import riscal.syntax.AST.Specification;
import riscal.syntax.AST.Type;
import riscal.tasks.Task;
import riscal.tasks.TaskFolder;
import riscal.syntax.AST.Declaration;
import riscal.syntax.AST.Declaration.*;
import riscal.parser.*;
import riscal.types.Environment;
import riscal.types.Environment.Symbol.*;
import riscal.types.TypeChecker;
import riscal.types.TypeChecker.TypeError;
import riscal.tasks.*;
import riscal.semantics.*;
import riscal.semantics.Types.*;
import riscal.semantics.Translator.*;
import riscal.util.*;
import com.riscal.*;

public class Main
{

  // software version
  public static final String version = "1.0.20 (Jan 12, 2019)";
	  
  // message printed on startup
  public static final String copyright =
  "RISC Algorithm Language Web Version " + version + "\n" + 
  "http://www.risc.jku.at/research/formal/software/RISCAL\n" +
  "(C) 2016-, Research Institute for Symbolic Computation (RISC)\n" +
  "This is free software distributed under the terms of the GNU GPL.\n" +
  //"Execute \"RISCAL -h\" to see the available command line options.\n" +
  "-----------------------------------------------------------------";
 
 // the user preferences
 public static final String prefname = "/riscal";
 public static final Preferences preferences = Preferences.userRoot().node(prefname);
 
 // the maximum external value allowed
 private static int MAX_VALUE = Integer.MAX_VALUE;
 
 // the number of "percentage" units
 public static final int MAX_PERCENTAGE = 1000;
 
 // the number of units of the progress bar
 // (bigger values may slow down execution by GUI overhead)
 private static final int MAX_PROGRESS = 250;
 
 // the path of the specification file being processed
 public static String path = null;
 
 // the file corresponding to the path
 public static File file = null;
 
 // the lines of the file being processed
 private static List<String> lines = null;
 
 // the abstract syntax tree of the file, its source, 
 // and whether it has changed since last check
 private static AST.Specification spec = null;
 private static AST.Source source = null;
 private static boolean changed = false;
 
 // the current input/output interface
 private static BufferedReader reader = null;
 private static PrintWriter writer = null;
 
 // the default value provided by option -v
 private static Integer defaultValue = null;
 
 // true if parsed specification is to be printed
 private static boolean printParsed = false;
 
 // true if typed specification is to be printed
 private static boolean printTyped = false;
 
 // true if execution is to be silent
 private static boolean silent = false;
 
//true if execution is to be refresh
private static boolean refresh = false;
 
 // true if nondeterministic execution is supported
 private static boolean nondeterministic = false;
 
 // the name of the function/procedure to execute
 private static String execMain = null;
 
 // true if symbols are to be printed
 private static boolean printSymbols = false;
 public static boolean printSymbols() { return printSymbols; }
 public static void setPrintSymbols(boolean p) { printSymbols = p; }
 
 // true if no gui is to be used
 private static boolean noGUI = false;

 // true if current execution is to be stopped
 private static boolean stopped = false;
 
 // the list of functions and the selected one
 private static String[] functions = null;
 private static Integer selfun = null;
 
 // a table of values for specific constants
 private static Map<String,Integer> values = new LinkedHashMap<String,Integer>();
 
 // the maximum number of inputs
 private static Long maxNumber = null;
 
 // the number of inputs to be checked and their percentage
 private static Long checkNumber = null;
 private static Long checkPercentage = null;
 
 // the number of runs to be checked
 private static Long checkRuns = null;
 
 // the parallelism options
 private static boolean multiThreaded = false;
 private static Integer threads = null;
 private static boolean distributed = false;
 private static String distScript = null;

 // a predictable random number generator
 private static Random random = new Random(0);
 
 // true if program runs in server mode
 private static boolean server = false;
 
 // the list of connected servers (none, if not yet connected)
 private static List<Parallel.Server> servers = null;
 
 // tracing is supported and actually visualized
 private static boolean trace = false;
 private static boolean showTrace = false;
 
 // a choice has been performed in the last execution
 private static boolean performedChoice = false;
 
 /***************************************************************************
  * The command line interface.
  * @param args the command line arguments (see help())
  **************************************************************************/
 public static void main(String args[])
 {
   /*
   // the default I/O interface
   reader = new BufferedReader(new InputStreamReader(System.in));
   writer = new PrintWriter(System.out, true);
  
   // read user preferences
   readPreferences();
   
   // process arguments
   processArguments(args);
   
   // close splash screen if there is no GUI needed
   if (server || noGUI)
   {
     try
     {
       SplashScreen screen = SplashScreen.getSplashScreen();
       if (screen != null) screen.close();
     }
     catch(Exception e) { }
   }
   
   if (server)
   {
     // run in server mode
     Parallel.runServer(threads);
   }
   else if (noGUI)
   {
     // run in text mode
     mainInit();
     mainCore();
   }
   else
   {
     // run in GUI mode
     java.awt.SplashScreen.getSplashScreen();
     MainSWT.main(path);  
   }
   
   // gracefully terminate execution
   exit(0);
   */
 }
 
  /***************************************************************************
   * Initialization
   **************************************************************************/
  public static void init() {
	// the default I/O interface
	reader = new BufferedReader(new InputStreamReader(System.in));
	writer = new PrintWriter(System.out, true);
	// read user preferences
	readPreferences();
	//MainSWT.main(path);
  }
 /***************************************************************************
  * Print help on command line arguments and exit.
  **************************************************************************/
 public static void help()
 {
   writer.println("RISCAL [ <options> ] [ <path> ]");
   writer.println("<path>: path to a specification file");
   writer.println("<options>: the following command line options");
   writer.println("-h: print this message and exit");
   writer.println("-s <T>: run in server mode with T threads");
   writer.println("-nogui: do not use graphical user interface");
   writer.println("-p: print the parsed specification");
   writer.println("-t: print the typed specification with symbols");
   writer.println("-v <I>: use integer <I> for all external values");
   writer.println("-nd: nondeterministic execution is allowed");
   writer.println("-e <F>: execute parameter-less function/procedure F");
   writer.println("-trace: visualize execution trace");
   exit(-1);
 }
 
 /****************************************************************************
  * Read user preferences.
  ***************************************************************************/
 private static void readPreferences()
 {
   try
   {
     String p = preferences.get("path", null);
     if (p != null) path = p;
     boolean si = preferences.getBoolean("silent", false);
     if (si != false) silent = si;
     boolean n = preferences.getBoolean("nondeterministic", false);
     if (n != false) nondeterministic = n;
     long mi = preferences.getLong("max_number", -1);
     if (mi != -1) maxNumber = mi;
     long cn = preferences.getLong("check_number", -1);
     if (cn != -1) checkNumber = cn;
     long cp = preferences.getLong("check_percentage", -1);
     if (cp != -1) checkPercentage = cp;
     long cr = preferences.getLong("check_runs", -1);
     if (cr != -1) checkRuns = cr;
     int d = preferences.getInt("default_value", -1);
     if (d != -1) defaultValue = d;
     String m = preferences.get("exec_main", null);
     if (m != null) execMain = m;
     boolean mt = preferences.getBoolean("multi_threaded", false);
     if (mt != false) multiThreaded = mt;
     boolean di = preferences.getBoolean("distributed", false);
     if (di != false) distributed = di;
     int tr = preferences.getInt("threads", -1);
     if (tr != -1) threads = tr;
     String ds = preferences.get("dist_script", null);
     if (ds != null) distScript = ds;
     Preferences f = preferences.node("functions");
     int fn = f.keys().length;
     if (fn != 0)
     {
       // preserve order of functions for menu
       functions = new String[fn];
       for (int i=0; i<fn; i++)
         functions[i] = f.get(Integer.toString(i), "");
     }
     int s = preferences.getInt("selected_fun", -1);
     if (s != -1) selfun = s;
     Preferences v = preferences.node("values");
     int vn = v.keys().length;
     for (int i=0; i<vn; i++)
     {
       String[] entry = v.get(Integer.toString(i), "").split(":");
       if (entry.length != 2) continue;
       Integer ival = toInteger(entry[1]);
       if (ival == null) continue;
       values.put(entry[0], ival);
     }
   }
   catch(BackingStoreException e)
   {
     System.out.println(e);
   }
 }
 
 /****************************************************************************
  * Write user preferences.
  ***************************************************************************/
 public static void writePreferences()
 {
   try
   {
     preferences.clear();
     if (path != null) preferences.put("path", path);
     preferences.putBoolean("silent", silent);
     preferences.putBoolean("nondeterministic", nondeterministic);
     if (defaultValue != null) preferences.putInt("default_value", defaultValue);
     if (maxNumber != null) preferences.putLong("max_number", maxNumber);
     if (checkNumber != null) preferences.putLong("check_number", checkNumber);
     if (checkPercentage != null) preferences.putLong("check_percentage", checkPercentage);
     if (checkRuns != null) preferences.putLong("check_runs", checkRuns);
     if (execMain != null) preferences.put("exec_main", execMain);
     preferences.putBoolean("multi_threaded", multiThreaded);
     if (threads != null) preferences.putInt("threads", threads);
     preferences.putBoolean("distributed", distributed);
     if (distScript != null) preferences.put("dist_script", distScript);
     Preferences funs = preferences.node("functions");
     funs.clear();
     if (functions != null)
     {
       // preserve order of functions for menu
       int n = functions.length;
       for (int i=0; i<n; i++) funs.put(Integer.toString(i), functions[i]);
     }
     if (selfun != null) preferences.putInt("selected_fun", selfun);
     Preferences vals = preferences.node("values");
     vals.clear();
     int i=0;
     for (Map.Entry<String,Integer> entry : values.entrySet())
     {
       // preserve order of values for menu
       vals.put(Integer.toString(i), entry.getKey()+":"+entry.getValue());
       i++;
     }
   }
   catch(BackingStoreException e)
   {
     System.out.println(e);
   }
 }
 
 /****************************************************************************
  * Set the input/output interface
  * @param reader the reader (if null, keep default)
  * @param writer the writer (if null, keep default)
  ***************************************************************************/
 public static void setInputOutput(BufferedReader reader, PrintWriter writer)
 {
   if (reader != null) Main.reader = reader;
   if (writer != null) Main.writer = writer;
 }
 
 /****************************************************************************
  * Get the output interface.
  * @return the writer.
  ***************************************************************************/
 public static PrintWriter getOutput()
 {
   return writer;
 }
 
 /****************************************************************************
  * The initial execution.
  ***************************************************************************/
 public static void mainInit()
 {
   writer.println(copyright);
 }
 
 /****************************************************************************
  * The core execution.
  ***************************************************************************/
 public static void mainCore()
 {
   if (path != null) file = new File(path);
   if (file != null) lines = readFile(file);
   AST.Source source = lines == null ? null : new AST.Source(file, lines);
   mainCore(source);
 }
 
 /****************************************************************************
  * The core to be executed on the current source.
  * @param the source.
  * @return the specification (null, on error)
  ***************************************************************************/
 public static AST.Specification mainCore(AST.Source source)
 {
   // parse specification
   Main.source = source;
   spec = null; 
   changed = true;
   if (source != null) spec = parseText(source);
   if (spec == null) return null;
   if (printParsed) writer.print(spec);
   
   // process specification
   Environment env = process(spec, reader, writer);
   if (env == null) 
     spec = null;
   else
   {
     if (printTyped) 
     {
       printSymbols = true;
       writer.print(spec);
       printSymbols = false;
     }
     if (execMain != null) 
     {
       boolean okay = execute(execMain, env);
       if (!okay) spec = null;
     }
   }
   
   // signal termination
   if (spec != null) writer.println("Type checking and translation completed.");
   return spec;
 }
 
 /***************************************************************************
  * If there is no GUI, terminate the program.
  * @param code the return code of the program.
  **************************************************************************/
 public static void exit(int code)
 {
   writePreferences();
   terminateServers();
   System.exit(code);
 }
 
 /***************************************************************************
  * Terminate connected servers (if any)
  **************************************************************************/
 public static void terminateServers()
 {
   if (servers != null) 
   {
     Parallel.terminateServers(servers);
     writer.println("Servers terminated.");
     servers = null;
   }
 }
 
 /***************************************************************************
  * Print error message and potentially exit.
  * @param error the error message
  * @param exit if true then exit
  **************************************************************************/
 public static void error(String error, boolean exit)
 {
   writer.println("ERROR: " + error);
   if (exit) exit(-1);
 }
 
 /***************************************************************************
  * Print error message.
  * @param error the error message
  **************************************************************************/
 public static void error(String error)
 {
   error(error, false);
 }
 
 /***************************************************************************
  * Print message.
  * @param msg the message.
  * @param exit if true then exit
  **************************************************************************/
 public static void println(String msg)
 {
   writer.println(msg);
 }
 
 /***************************************************************************
  * Print error message and exit.
  * @param error the error message
  **************************************************************************/
 public static void optionError(String error)
 {
   error(error, false);
   writer.println("Use -h for help on command line arguments");
   exit(-1);
 }
 
 /***************************************************************************
  * Process command line arguments.
  **************************************************************************/
 public static void processArguments(String[] args)
 {
   int n = args.length;
   int i = 0;
   while (i < n)
   {
     String arg = args[i];
     i++;
     switch (arg)
     {
     case "-h" :
     {
       help();
       break;
     }
     case "-s" :
     {
       server = true;
       if (i == n) optionError("option -s needs argument <T>");
       threads = toInteger(args[i]);
       if (threads == null) 
         optionError("-s argument <" + args[i] + "> does not denote an integer");
       if (threads < 1) 
         optionError("-s argument <" + args[i] + "> must be at least 1");
       i++;
       break;
     }
     case "-nogui" :
     {
       noGUI = true;
       break;
     }
     case "-p" :
     {
       printParsed = true;
       break;
     }
     case "-t" :
     {
       printTyped = true;
       break;
     }
     case "-v" :
     {
       if (i == n) optionError("option -v needs argument <I>");
       defaultValue = toInteger(args[i]);
       if (defaultValue == null)
         optionError("-v argument <" + args[i] + "> does not denote an integer");
       i++;
       break;
     }
     case "-nd" :
     {
       nondeterministic = true;
       break;
     }
     case "-e" :
     {
       if (i == n) optionError("option -e needs argument <F>");
       execMain = args[i];
       i++;
       break;
     }
     case "-trace" :
     {
       trace = true;
       break;
     }
     default:
     {
       if (arg.startsWith("-")) 
         optionError("unknown option " + arg); 
       path = arg;
     }
     }
   }
 }
 
 /***************************************************************************
  * Converts string to integer.
  * @param arg the string
  * @return the integer (null, if none)
  **************************************************************************/
 public static Integer toInteger(String arg)
 {
   try
   {
     return Integer.parseInt(arg);
   }
   catch(NumberFormatException e)
   {
     return null;
   }
 }
 
 /***************************************************************************
  * Converts string to long integer.
  * @param arg the string
  * @return the long integer (null, if none)
  **************************************************************************/
 public static Long toLong(String arg)
 {
   try
   {
     return Long.parseLong(arg);
   }
   catch(NumberFormatException e)
   {
     return null;
   }
 }
 
 /***************************************************************************
  * Read file and return its contents.
  * @param file the file
  * @return the lines of the file (null, if something went wrong; in
  *         this case a warning is printed).
  **************************************************************************/
 public static List<String> readFile(File file)
 {
   writer.println("Reading file " + file.getAbsolutePath());
   try(BufferedReader reader = new BufferedReader(new FileReader(file));)
   { 
     List<String> result = new ArrayList<String>();
     while (true)
     {
       String line = reader.readLine();
       if (line == null) return result;
       result.add(line);
     }
   }
   catch(IOException e)
   {
     error("could not read file " + e.getMessage(), false);
     return null;
   }
 }
 
 /***************************************************************************
  * Parse specification and return its abstract syntax tree.
  * @param source the source of the specification.
  * @return the abstract syntax tree (null, if something went wrong;
  *         in this case a warning is printed).
  **************************************************************************/
 public static AST.Specification parseText(AST.Source source)
 {
   try
   {
     ANTLRInputStream input = new ANTLRInputStream(source.text);
     RISCALLexer lexer = new RISCALLexer(input);
     TokenStream tokens = new CommonTokenStream(lexer);
     RISCALParser parser = new RISCALParser(tokens);
     ErrorListener listener = new ErrorListener(source);
     parser.removeErrorListeners();
     parser.addErrorListener(listener);
     RISCALParser.SpecificationContext context = parser.specification();
     int errors = listener.getErrors();
     if (errors > 0)
     {
       error(errors + " syntax errors", false);
       return null;
     }
     TreeTranslator translator = new TreeTranslator(source, tokens);
     return (AST.Specification)translator.visit(context);
   }
   catch(Exception e)
   {
     StringWriter w = new StringWriter();
     e.printStackTrace(new PrintWriter(w));
     error("parser error (" + w.toString() +")", false);
     return null;
   }
 }
 
 private static class ErrorListener extends BaseErrorListener
 {
   private Source source;
   private int errors = 0;
   private ErrorListener(Source source)
   {
     this.source = source;
     errors = 0;
   }
   public void syntaxError(Recognizer<?,?> recognizer,
     Object offendingSymbol, int line, int charPositionInLine,
     String msg, RecognitionException e)
   {
     errors++;
     try { Thread.sleep(1); } catch(Exception ex) { }
     if (line >= 1 && line <= source.lines.size())
     {
       writer.println(source.lines.get(line-1));
       for (int i = 0; i < charPositionInLine; i++)
         writer.print(' ');
       writer.println('^');
     }
     writer.println(msg);
     //MainSWT.reportError(msg,
     //    new SourcePosition(source, line-1, charPositionInLine, -1, -1));
   }
   public int getErrors() { return errors; }
 }
 
 // a class for interactively reading integer values
/*  private static final class IntegerReader
 {
   private BufferedReader reader;
   private PrintWriter writer;
   
   *//**************************************************************************
    * Create reader with given input/output interface.
    * @param reader the reader to read from.
    * @param writer the writer to write to.
    *************************************************************************//*
   public IntegerReader(BufferedReader reader, PrintWriter writer)
   {
     this.reader = reader;
     this.writer = writer;
   }
   
   *//*************************************************************************
    * Repeatedly ask user for integer until valid one is entered.
    * @param message the message to be printed.
    * @param min the minimum value of the integer.
    * @param max the maximum value of the integer.
    * @return the integer entered by the user.
    ************************************************************************//*
   public int read(String message, int min, int max)
   {
     while (true)
     {
       try
       {
         writer.print(message + ": ");
         writer.flush();
         String line = reader.readLine();
         int value = Integer.parseInt(line);
         if (value >= min && value <= max) return value;
         writer.println("Value must be in range " + min + ".." + max + ".");
       }
       catch(IOException e)
       {
         writer.println("Input error: " + e.getMessage());
       }
       catch(NumberFormatException e)
       {
         writer.println("Value must be an integer.");
       }
     }
   }
 }*/

 /***************************************************************************
  * Type-check and annotate specification in given environment
  * @param spec the specification.
  * @param writer the writer for printing error messages.
  * @param reader the reader for reading integer values
  * @return the generated environment (null, if error)
  **************************************************************************/
 public static Environment process(AST.Specification spec, 
   BufferedReader reader, PrintWriter writer)
 {
   // set up type checker
   Environment env = new Environment();
   ErrorWriter error = new ErrorWriter(writer);
   Translator translator = new Translator(writer, nondeterministic);
   TypeChecker checker = new TypeChecker(env, error);
   
   // type-check and translate declarations
   // IntegerReader ireader = new IntegerReader(reader, writer);
   for (Declaration d : spec.declarations)
   {
     if (!(d instanceof ValueDeclaration)) 
     {
       checker.process(d);
       if (error.getErrors() != 0) break;
       try
       {
         if (d instanceof ValueDefinition)
         {
           ValueDefinition d0 = (ValueDefinition)d;
           writer.println("Computing the value of " + d0.ident + "...");
         }
         else if (d instanceof PredicateValueDefinition)
         {
           PredicateValueDefinition d0 = (PredicateValueDefinition)d;
           writer.println("Computing the truth value of " + d0.ident + "...");
         }
         else if (d instanceof TheoremDefinition)
         {
           TheoremDefinition d0 = (TheoremDefinition)d;
           writer.println("Computing the truth value of " + d0.ident + "...");
         }
         translator.process(d);
         if (d instanceof TypeDefinition)
         {
           // may evaluate subtype predicate only after translation
           TypeDefinition d0 = (TypeDefinition)d;
           if (d0.exp != null)
           {
             writer.println("Evaluating the domain of " + d0.ident + "...");
             TypeSymbol s = (TypeSymbol)d0.ident.getSymbol();
             if (s.getType().getSize() == 0)
               error.reportError(new TypeError(d, "subtype is empty, must have at least one value"));
           }
         }
       }
       catch(Stopped e)
       {
         writer.println("ERROR: execution was stopped.");
         return null;
       }
       catch(TranslationError e)
       {
         error.reportError(new TypeError(d, "error in evaluation of declaration: " 
          + e.getMessage()));
         return null;
       }
       catch(Exception e)
       {
         handleException(e, writer);
         error.reportError(new TypeError(d, "error in evaluation of declaration"));
         return null;
       }
       catch(Error e)
       {
         handleException(e, writer);
         error.reportError(new TypeError(d, "error in evaluation of declaration"));
         return null;
       }
       continue;
     }
     // set value for external declaration
     ValueDeclaration d0 = (ValueDeclaration)d;
     ValueSymbol symbol = env.getValue(d0.ident);
     if (symbol != null)
     {
       error.reportError(new TypeError(d, "value already declared at " +
           symbol.ident.getPosition().toShortString()));
     }
     int value = 0;
     if (defaultValue != null) value = defaultValue;
     Integer v = values.get(d0.ident.string);
     if (v != null) value = v;
     if (MAX_VALUE == -1 || value <= MAX_VALUE)
       writer.println("Using " + d0.ident + "=" + value + ".");
     else if (MAX_VALUE != -1 && value > MAX_VALUE)
     {
       writer.println("WARNING: " + d0.ident + "=" + value + 
           " too large, using " + d0.ident + "=" + MAX_VALUE + " instead.");
       value = MAX_VALUE;
     }
     checker.process(d0, value);
   }
   
   // report errors
   int errors = error.getErrors();
   if (errors > 0)
   {
     writer.println(errors +
         (errors == 1 ? " error" : " errors") + " encountered");
     stopped = false;
     MainSWT.stop(stopped);
     return null;
   }
   
   // generate validation tasks
   try
   {
     if (error.getErrors() == 0)
       Validation.addTasks(spec, checker, translator);
   }
   catch(Exception e)
   {
     handleException(e, writer);
     writer.println("Internal error in generation of verification tasks");
   }
   
   return env;
 }
 
 /****************************************************************************
  * Get the list of functions from the given specification
  * @param spec the specification
  * @param generated true if also the generated functions are included
  * @return the list of functions
  ***************************************************************************/
 public static List<FunctionSymbol> getFunctions(Specification spec,
   boolean generated)
 {
   List<FunctionSymbol> funs = new ArrayList<FunctionSymbol>();
   for (Declaration d : spec.declarations)
   {
     FunctionSymbol s = null;
     if (d instanceof FunctionDefinition)
     {
       FunctionDefinition d0 = (FunctionDefinition)d;
       s = (FunctionSymbol)d0.ident.getSymbol();
     }
     else if (d instanceof PredicateDefinition)
     {
       PredicateDefinition d0 = (PredicateDefinition)d;
       s = (FunctionSymbol)d0.ident.getSymbol();
     }
     else if (d instanceof TheoremParamDefinition)
     {
       TheoremParamDefinition d0 = (TheoremParamDefinition)d;
       s = (FunctionSymbol)d0.ident.getSymbol();
     }
     else if (d instanceof ProcedureDefinition)
     {
       ProcedureDefinition d0 = (ProcedureDefinition)d;
       s = (FunctionSymbol)d0.ident.getSymbol();
     }
     if (s != null)
     {
       TaskFolder folder = s.getTaskFolder();
       if (folder == null || !generated)
         funs.add(s);
       else
         addFunctions(funs, folder);
     }
   }
   return funs;
 }
 
 /***************************************************************************
  * Add task functions to list of functions.
  * @param funs the list of functions.
  * @param folder the folder to hold the tasks.
  **************************************************************************/
 private static void addFunctions(List<FunctionSymbol> funs, TaskFolder folder)
 {
   for (Task task : folder.getTasks())
   {
     FunctionSymbol fun = task.getFunction();
     if (fun != null) funs.add(fun);
   }
   for (TaskFolder folder0 : folder.getFolders())
     addFunctions(funs, folder0);
 }
 
 /****************************************************************************
  * Execute function/procedure in given environment.
  * @param main the name of the function/procedure.
  * @param env the environment that holds the functions.
  * @return the success of the execution.
  ***************************************************************************/
 public static boolean execute(String main, Environment env)
 {
   List<FunctionSymbol> funs = env.getFunctions(new AST.Identifier(main));
   if (funs == null)
   {
     writer.println("There is no function " + main + "() defined.");
     return false;
   }
   for (FunctionSymbol fun : funs)
   {
     if (fun.types.length == 0) 
     {
       return execute(fun);
     }
   }
   writer.println("There is no parameterless function " + main + "() defined.");
   return false;
 }
 
 /****************************************************************************
  * Execute function/procedure with all possible arguments.
  * @param fun the symbol of the function/procedure.
  * @return the success of the execution.
  ***************************************************************************/
 public static boolean execute(FunctionSymbol fun)
 {
   // reset choice check
   performedChoice = false;
   
   // check function without arguments
   AST.Type[] types = fun.types;
   if (types.length == 0)
   {
     writer.println("Executing " + fun.ident + "().");
     long time = System.currentTimeMillis();
     boolean okay = execute(-1L, fun, new Value[]{ });
     time = System.currentTimeMillis()-time;
     if (okay) 
     {
       writer.println("Execution completed (" + time + " ms).");
       if (okay && performedChoice && (!nondeterministic || checkRuns != null))
         writer.println("WARNING: not all nondeterministic branches have been considered.");
     }
     else
       writer.println("ERROR encountered in execution.");
     return okay;
   }
   
   // check function with at least one argument
   AST.Type type = new AST.Type.TupleType(types);
   // size is lazily computed by invocation of getSize()
   // TypeChecker.setSize(type, false);
   long size = type.getSize();
   if (maxNumber != null && size > maxNumber)
   {
     writer.println("Number of inputs " + size + " is greater than maximum " + maxNumber + ".");
     writer.println("Use smaller types or increase maximum number of inputs.");
     return false;
   }
   boolean result = execute(fun, null, size);
   if (result && performedChoice && (!nondeterministic || checkRuns != null))
     writer.println("WARNING: not all nondeterministic branches have been considered.");
   return result;
 }
 
 /****************************************************************************
  * Execute function/procedure with given arguments.
  * @param fun the symbol of the function/procedure.
  * @param values the argument values (may be null,
  *   indicating all values of the argument type).
  * @param size the number of values.
  * @return the success of the execution.
  ***************************************************************************/
 public static boolean execute(FunctionSymbol fun, Seq<Value> values, long size)
 {
   // startup message
   printExecutingStart(fun, values, size);
       
   // prevent nested parallelism
   if (Parallel.isRunning() || (!multiThreaded && !distributed))
     return executeSequential(fun, values, size);
   
   // multi-threaded/distributed execution
   return executeParallel(fun, values, size);
 }
 
 /****************************************************************************
  * Execute function/procedure with given arguments in parallel.
  * @param fun the symbol of the function/procedure.
  * @param values the argument values (may be null,
  *   indicating all values of the argument type).
  * @param size the number of values.
  * @return the success of the execution.
  ***************************************************************************/
 public static boolean executeParallel(FunctionSymbol fun, Seq<Value> values, 
   long size)
 {
   // determine number of threads
   int t = 0;
   if (threads != null) t = threads;
   if (distributed)
   {
     // remote servers plus at least one local thread
     if (t < 1) t = 1;
   }
   else
   {
     // at least 2 local threads
     if (t < 2) t = 2;
   }

   // determine result by multithreaded/distributed execution
   Parallel.Result result = null; 
   if (!distributed)
   {    
     writer.println("PARALLEL execution with " + t + " threads (output disabled).");
     result = Parallel.execute(t, null, fun, -1, values, size);
   }
   else
   {
     // connect to remote servers
     if (servers == null)
     {
       servers = Parallel.connectServers(distScript);
       if (servers == null) 
       {
         writer.println("ERROR: could not connect to remote servers");
         return false;
       }
       changed = true;
     }
     if (changed)
     {
       boolean okay = Parallel.process(servers, source, 
           nondeterministic, defaultValue, Main.values);
       if (!okay) 
       {
         writer.println("ERROR: servers could not process source");
         return false;
       }
       changed = false;
     }
     // hack to determine function number for remote execution
     // (reads global variable "spec")
     List<FunctionSymbol> funs = getFunctions(spec, true);
     int fnum = 0;
     for (FunctionSymbol f : funs)
     {
       if (f == fun) break;
       fnum++;
     }
     writer.println("PARALLEL execution with " + threads + 
         " local threads and " + servers.size() + 
         " remote servers (output disabled).");
     result = Parallel.execute(t, servers, fun, fnum, values, size);
   } 
    
   String all = values == null ? "ALL " : "SELECTED ";
   // result is okay
   if (result instanceof Parallel.OkayResult) 
   {
     Parallel.OkayResult result0 = (Parallel.OkayResult)result;
     if (checkNumber == null && checkPercentage == null & checkRuns == null)
       writer.println("Execution completed for " + all + "inputs (" + 
           result0.getTime() + " ms, " +
           result0.checked + " checked, " +   
           result0.skipped + " inadmissible).");
     else
       writer.println("Execution completed for SOME inputs (" + 
           result0.getTime() + " ms, " +
           result0.checked + " checked, " +   
           result0.skipped + " inadmissible, " + 
           (result0.received-result0.checked-result0.skipped) + " ignored).");
     return true;
   }
     
   // execution resulted in a failed task
   if (result instanceof Parallel.ErrorResult)
   {
     Parallel.ErrorResult eresult = (Parallel.ErrorResult)result;
     writer.print("ERROR in execution of " + eresult.task + ": ");
     writer.println(eresult.error);
     String output = eresult.output;
     if (output.length() > 0)
     {
       writer.println("The following output was produced:");
       writer.println(output);
     }
     writer.println("ERROR encountered in execution.");
     return false;
   }
   
   // execution resulted in a failed server
   if (result instanceof Parallel.FailureResult)
   {
     Parallel.FailureResult fresult = (Parallel.FailureResult)result;
     writer.print("ERROR in execution on server " + fresult.server.address + ": ");
     writer.println(fresult.error);
     String output = fresult.output;
     if (output.length() > 0)
     {
       writer.println("The following output was produced:");
       writer.println(output);
     }
     terminateServers();
     writer.println("ERROR encountered in execution.");
     return false;
   }
   
   // execution yielded no result
   if (result instanceof Parallel.NoResult)
   {
     writer.println("ERROR: execution was aborted.");
     return false;
   }
   
   // execution was stopped
   if (result instanceof Parallel.StopResult)
   {
     writer.println("ERROR: execution was stopped.");
     return false;
   }
   
   // internal error
   writer.println("ERROR: unknown result");
   return false;
 }
 
 /***************************************************************************
  * Print information on the start of the checking.
  * @param fun the function being checked.
  * @param values the function arguments (null indicates, all values are used)
  * @param size the number of function arguments.
  **************************************************************************/
 private static void printExecutingStart(FunctionSymbol fun, 
   Seq<Value> values, long size)
 {
   String all = values == null ? "all " : "selected ";
   long checkPercentage0 = checkPercentage == null ? MAX_PERCENTAGE : Math.min(checkPercentage, MAX_PERCENTAGE);
   if (checkNumber != null)
   {
     if (checkPercentage0 == MAX_PERCENTAGE)
     {
       if (size == AST.Type.INFINITY)
         writer.println("Executing " + shortName(fun) + 
             " with some (about " + checkNumber + ") of the (at least 2^63) inputs.");
       else
         writer.println("Executing " + shortName(fun) + 
             " with some (about " + checkNumber + ") of the " + size + " inputs.");
     }
     else
     {
       if (size == AST.Type.INFINITY)
         writer.println("Executing " + shortName(fun) + 
             " with some (about " + checkNumber + ", " + checkPercentage0 + 
             "/" + MAX_PERCENTAGE + ") of the (at least 2^63) inputs.");
       else
         writer.println("Executing " + shortName(fun) + 
             " with some (about " + checkNumber + ", " + checkPercentage0 + 
             "/" + MAX_PERCENTAGE + ") of the " + size + " inputs.");
     }
   }
   else if (size == AST.Type.INFINITY)
   {
     if (checkPercentage0 == MAX_PERCENTAGE)
       writer.println("Executing " + shortName(fun) + " with " + all + "(at least 2^63) inputs.");
     else
       writer.println("Executing " + shortName(fun) + " with some (" + checkPercentage0 + 
           "/" + MAX_PERCENTAGE + ") of the (at least 2^63) inputs.");
   }
   else
   {
     if (checkPercentage0 == MAX_PERCENTAGE)
       writer.println("Executing " + shortName(fun) + " with " + all + size + " inputs.");
     else
       writer.println("Executing " + shortName(fun) + " with some (" + checkPercentage0 +
           "/" + MAX_PERCENTAGE + ") of the " + size + " inputs.");
   }
 }

 /****************************************************************************
  * Initialize the progress bar.
  ***************************************************************************/
 private static int progress0 = 0; // avoid slowdown by progress reporting
 public static void initProgress()
 {
   if (noGUI) return;
   MainSWT.setMaximumProgress(MAX_PROGRESS);
   MainSWT.setProgress(0);
   progress0 = 0;
 }
 
 /****************************************************************************
  * Report progress for fraction (value/max).
  * @param value the current value.
  * @param max the maximum value.
  ***************************************************************************/
 public static void reportProgress(long value, long max)
 {
   if (noGUI) return;
   if (max == 0) return;
   int progress = (int)(value*MAX_PROGRESS/max);
   if (progress == progress0) return;
   MainSWT.setProgress(progress);
   progress0 = progress;
 }
 
 /***************************************************************************
  * Throw a dice to decide whether next input is to be ignored.
  * @return true if the next input is to be ignored
  **************************************************************************/
 public static boolean ignoreInput()
 {
   if (checkPercentage == null) return false;
   long checkPercentage0 = Math.min(checkPercentage, MAX_PERCENTAGE);
   return random.nextInt(MAX_PERCENTAGE) >= checkPercentage0;
 }

 /****************************************************************************
  * Get sequence of all values to which a function with non-0 arity 
  * can be applied.
  * @param fun the function.
  * @return the sequence of values.
  ***************************************************************************/
 public static Seq<Value> getValues(FunctionSymbol fun)
 {
   AST.Type[] types = fun.types;
   AST.Type type;
   if (types.length == 1)
     type = types[0];
   else
   {
     type = new AST.Type.TupleType(types);
     // size is lazily computed by invocation of getSize()
     // TypeChecker.setSize(type, false);
   }
   return Values.getValueSeq(type);
 }
   
 /****************************************************************************
  * Execute function/procedure with given arguments 
  * in a multi-threaded fashion
  * @param fun the symbol of the function/procedure.
  * @param values the argument values (may be null to indicate all values of the type)
  * @param size the number of values.
  * @return the success of the execution.
  ***************************************************************************/
 public static boolean executeSequential(FunctionSymbol fun, 
   Seq<Value> values, long size)
 {
   try
   {
     Seq<Value> values0 = values == null ? getValues(fun) : values;
     int slots = fun.getSlots();
     List<ContextCondition> pre = fun.getPreconditions();
     ValueSymbol[] params = fun.params;
     int[] p = Translator.toSlots(params);
     String[] names = Translator.toNames(params);
     initProgress();
     long counter = 0; // run counter
     int vcounter = 0; // violations of preconditions
     int ccounter = 0; // actually checked
     int icounter = 0; // continuously ignored inputs
     long time = System.currentTimeMillis();
     long stime = time;
     boolean ignored = false;
     long itime = -1;
     while (true)
     {
       checkStopped();
       reportProgress(counter, size);
       Seq.Next<Value> next = values0.get();
       if (next == null) 
       {
         String all = values == null ? "ALL " : "SELECTED ";
         stime = System.currentTimeMillis()-stime;
         if (counter == ccounter+vcounter)
           writer.println("Execution completed for " + all + "inputs (" + 
               stime + " ms, " +
               ccounter + " checked, " + vcounter 
               + " inadmissible).");
         else
           writer.println("Execution completed for SOME inputs (" + 
               stime + " ms, " +
               ccounter + " checked, " + vcounter 
               + " inadmissible, " + (counter-ccounter-vcounter) + " ignored).");
         return true;
       }
       Value value = next.head;
       values0 = next.tail;
       if (ignoreInput()) { counter++; continue; }
       if (checkNumber != null && ccounter >= checkNumber) 
       {
         stime = System.currentTimeMillis()-stime;
         if (size == AST.Type.INFINITY)
           writer.println("Execution completed for SOME inputs (" + 
               stime + " ms, " +
               ccounter + " checked, " + vcounter 
               + " inadmissible).");
         else
           writer.println("Execution completed for SOME inputs (" + 
               stime + " ms, " +
               ccounter + " checked, " + vcounter 
               + " inadmissible, " + (size-ccounter-vcounter) + " ignored).");
         return true;
       }
       counter++;
       Value[] arg;
       if (fun.types.length == 1)
         arg = new Value[]{ value };
       else
       {
         Value.Array value0 = (Value.Array)value;
         arg = value0.get();
       }
       boolean execute = true;
       if (pre != null)
       {
         Context c = new Context(slots, p, new Argument(arg), names);
         execute = checkPrecondition(pre, c);
         if (execute) 
         {
           if (!silent)
           {
             ignored = false;
             if (icounter > 0)   
             {
               writer.println(counter + " inputs (" + ccounter + " checked, " + vcounter 
                   + " inadmissible, " + (counter-ccounter-vcounter) + " ignored)... ");
             }
             icounter = 0;
           }
         }
         else
         {
           vcounter++;
           if (!ignored)
           {
             if (!silent)
               writer.println("Ignoring inadmissible inputs...");
             ignored = true;
             icounter = 0;
             itime = System.currentTimeMillis();
           }
           else
           {
             long itime0 = System.currentTimeMillis();
             if (itime0-itime >= 2000)
             {
               if (!silent)
               {
                 writer.println(counter + " inputs (" + ccounter + " checked, " + vcounter 
                     + " inadmissible, " + (counter-ccounter-vcounter) + " ignored)... ");
               }
               icounter++;
               itime = itime0;
             }
           }
           // writer.println("Ignore run " + (counter-1) + " of deterministic function " + 
           //    fun.ident.string + "(" + toString(arg) + "): precondition is violated.");
         }
       }
       if (execute)
       {
         boolean okay = execute(counter-1, fun, arg);
         ccounter++;
         if (!okay)
         {
           writer.println("ERROR encountered in execution.");
           return false;
         }
       }
       long time0 = System.currentTimeMillis();
       if (time0-time >= 2000)
       {
         if (silent)
         {
           writer.println(counter + " inputs (" + ccounter + " checked, " + vcounter 
               + " inadmissible, " + (counter-ccounter-vcounter) + " ignored)... ");
         }
         time = time0;
       }
     }
   }
   catch(Stopped e)
   {
     writer.println("ERROR: execution is aborted.");
   }
   catch(Exception e)
   {
     handleException(e, writer);
   }
   catch(Error e)
   {
     handleException(e, writer);
   }
   finally
   {
     MainSWT.setProgress(0);
   }
   return false;
 }
 
 /***************************************************************************
  * Check preconditions.
  * @param pre the semantics of the preconditions.
  * @param c the context when the function is called.
  * @result true if all preconditions were satisfied.
  **************************************************************************/
 public static boolean checkPrecondition(List<ContextCondition> pre, Context c)
 {
   for (ContextCondition s : pre)
   {
     if (s instanceof ContextCondition.Single)
     {
       ContextCondition.Single s0 = (ContextCondition.Single)s;
       Value.Bool b = s0.apply(c);
       if (!b.getValue()) return false;
     }
     else
     {
       ContextCondition.Multiple s0 = (ContextCondition.Multiple)s;
       Seq<Value.Bool> bs = s0.apply(c);
       while (true)
       {
         Seq.Next<Value.Bool> next = bs.get();
         if (next == null) break;
         Value.Bool b = next.head;
         if (!b.getValue()) return false;
         bs = next.tail;
       }
     }
   }
   return true;
 }
 
 /****************************************************************************
  * Execute function/procedure.
  * @param counter the number of the run
  * @param fun the symbol of the function/procedure.
  * @param arg the argument of the function.
  * @return the success of the execution.
  ***************************************************************************/
 private static boolean execute(long counter, FunctionSymbol fun, Value[] arg)
 {
   FunSem f = fun.getValue();
   try
   {
     if (f instanceof FunSem.Single)
     {
       FunSem.Single f0 = (FunSem.Single)f;
       if (!silent)
       {
         if (counter == -1L)
           writer.println("Run of deterministic function " + 
               fun.ident.string + "(" + toString(arg) + "):");
         else
           writer.println("Run " + counter + " of deterministic function " + 
               fun.ident.string + "(" + toString(arg) + "):");   
       }
       if (showTrace) Trace.create();
       long time = System.currentTimeMillis();
       Value result = f0.apply(new Argument(arg));
       long time0 = System.currentTimeMillis();
       if (!silent)
         writer.println("Result (" + (time0-time) + " ms): " + result);
       if (showTrace) 
       { 
         String name = fun.ident.toString();
         String arg0 = toString(arg);
         if (arg0.length() > 40) arg0 = arg0.substring(0, 40) + "...";
         MainSWT.showTrace(name + "(" + arg0 + ")"); 
       }
     }
     else
     {
       FunSem.Multiple f0 = (FunSem.Multiple)f;
       Seq<Value> results = f0.apply(new Argument(arg));
       int number = 0;
       long timeA = System.currentTimeMillis();
       long ptime = timeA;
       while (true)
       {
         if (checkRuns != null && number >= checkRuns)
         {
           long timeB = System.currentTimeMillis();
           if (!silent)
             writer.println("Stopped after " + number + " branches, there may be more (" + (timeB-timeA) + " ms).");
           return true;
         }
         if (!silent)
         {
           if (counter == -1L)
             writer.println("Branch " + number + " of nondeterministic function " + 
                 fun.ident.string + "(" + toString(arg) + "):");
           else
             writer.println("Branch " + number + ":" + counter + " of nondeterministic function " + 
                 fun.ident.string + "(" + toString(arg) + "):");
         }
         long time = System.currentTimeMillis();
         Seq.Next<Value> next = results.get();
         long time0 = System.currentTimeMillis();
         if (next == null) break;
         number++;
         if (silent)
         {
          long ptime0 = System.currentTimeMillis();
          if (ptime0 >= ptime + 2000)
          {
            if (counter == -1)
              writer.println(number + " branches of nondeterministic function " + 
                  fun.ident.string + "(" + toString(arg) + ").");
            else
              writer.println(number + " branches for input " + counter + " of nondeterministic function " + 
                  fun.ident.string + "(" + toString(arg) + ").");
            ptime = ptime0;
          }
         }
         else
           writer.println("Result (" + (time0-time) + " ms): " + next.head);
         results = next.tail;
       }
       long timeB = System.currentTimeMillis();
       if (!silent)
         writer.println("No more results (" + (timeB-timeA) + " ms).");
     }
   }
   catch(Exception e)
   {
     writer.print("ERROR in execution of " + toString(fun, arg) + ": ");
     if (e instanceof Stopped)
       writer.println("Execution is aborted.");
     else
       handleException(e, writer);
     return false;
   }
   catch(Error e)
   {
     writer.print("ERROR in execution of " + toString(fun, arg) + ": ");
     handleException(e, writer);
     return false;
   }
   if (stopped)
   {
     stopped = false;
     MainSWT.stop(stopped);
   }
   return true;
 }

 /***************************************************************************
  * Get the string representation of a value sequence.
  * @param arg the value sequence.
  * @return its string representation separated by commas.
  **************************************************************************/
 public static String toString(Value[] arg)
 {
   StringBuffer result = new StringBuffer();
   int n = arg.length;
   for (int i=0; i<n; i++)
   {
     result.append(arg[i]);
     if (i+1<n) result.append(",");
   }
   return result.toString();
 }
 
 /***************************************************************************
  * Get the string representation of a function application.
  * @param fun the function.
  * @param arg the arguments.
  * @return its string representation of the application.
  **************************************************************************/
 public static String toString(FunctionSymbol fun, Value[] arg)
 {
   return fun.ident.string + "(" + Main.toString(arg) + ")";
 }
 
 /***************************************************************************
  * Handle exception
  * @param e the exception
  * @param writer the writer to write messages to.
  **************************************************************************/
 public static void handleException(Throwable e, PrintWriter writer)
 {
   StringWriter w = new StringWriter();
   e.printStackTrace(new PrintWriter(w));
   if (e instanceof RuntimeError)
   {
     RuntimeError e0 = (RuntimeError)e;
     String s = e0.tree.toString();
     if (s.endsWith("\n")) s = s.substring(0, s.length()-1);
     SourcePosition pos = e0.tree.getPosition();
     if (pos == null)
       writer.println("evaluation of\n  " + s +
           "\nat unknown position:");
     else
       writer.println("evaluation of\n  " + s +
           "\nat " + pos.toShortString() + ":");
     writer.println("  " + e.getMessage());
     return;
   }
   String message = e.getMessage();
   if (message == null)
     writer.println("Exception encountered:");
   else
     writer.println("Exception encountered: " + message);
   writer.println(w.toString());
 }
 
 /***************************************************************************
  * Set file path.
  * @param p the path (null, if none)
  **************************************************************************/
 public static void setPath(String p)
 {
   path = p;
 }
 
 /***************************************************************************
  * Get default value for type sizes.
  * @return the value (null, if none).
  **************************************************************************/
 public static Integer getDefaultValue()
 {
   return defaultValue;
 }
 
 /***************************************************************************
  * Set default value for type sizes.
  * @return v the value (null, if none).
  **************************************************************************/
 public static void setDefaultValue(Integer v)
 {
   defaultValue = v;
 }
 
 /***************************************************************************
  * Get maximum input number.
  * @return the value (null, if none).
  **************************************************************************/
 public static Long getMaxNumber()
 {
   return maxNumber;
 }
 
 /***************************************************************************
  * Set maximum input number.
  * @return m the number (null, if none).
  **************************************************************************/
 public static void setMaxNumber(Long m)
 {
   maxNumber = m;
 }
 
 /***************************************************************************
  * Get number of inputs to be checked.
  * @return the value (null, if none).
  **************************************************************************/
 public static Long getCheckNumber()
 {
   return checkNumber;
 }
 
 /***************************************************************************
  * Set number of inputs to be checked.
  * @return m the number (null, if none).
  **************************************************************************/
 public static void setCheckNumber(Long m)
 {
   checkNumber = m;
 }
 
 /***************************************************************************
  * Get percentage of inputs to be checked.
  * @return the value (null, if none).
  **************************************************************************/
 public static Long getCheckPercentage()
 {
   return checkPercentage;
 }
 
 /***************************************************************************
  * Set percentage of inputs to be checked.
  * @return m the number (null, if none).
  **************************************************************************/
 public static void setCheckPercentage(Long m)
 {
   checkPercentage = m;
 }
 
 /***************************************************************************
  * Get number of nondeterministic branches to be checked.
  * @return the value (null, if none).
  **************************************************************************/
 public static Long getCheckRuns()
 {
   return checkRuns;
 }
 
 /***************************************************************************
  * Set number of nondeterministic branches to be checked.
  * @return m the number (null, if none).
  **************************************************************************/
 public static void setCheckRuns(Long m)
 {
   checkRuns = m;
 }
 
 /***************************************************************************
  * Get silent value.
  * @return the value.
  **************************************************************************/
 public static boolean getSilent()
 {
   return silent;
 }
 
 /***************************************************************************
  * Set silent value.
  * @param si the value.
  **************************************************************************/
 public static void setSilent(boolean si)
 {
   silent = si;
 }
 
 /***************************************************************************
  * Get nondeterminism value.
  * @return the value.
  **************************************************************************/
 public static boolean getNondeterministic()
 {
   return nondeterministic;
 }
 
 /***************************************************************************
  * Set nondeterminism value.
  * @param nd the value.
  **************************************************************************/
 public static void setNondeterministic(boolean nd)
 {
   nondeterministic = nd;
 }
 
 /***************************************************************************
  * Get multi-threading status.
  * @return the status.
  **************************************************************************/
 public static boolean getMultiThreaded()
 {
   return multiThreaded;
 }
 
 /***************************************************************************
  * Set multi-threading status.
  * @param mt the status
  **************************************************************************/
 public static void setMultiThreaded(boolean mt)
 {
   multiThreaded = mt;
 }
 
 /***************************************************************************
  * Get number of threads
  * @return the number
  **************************************************************************/
 public static Integer getThreads()
 {
   return threads;
 }
 
 /***************************************************************************
  * Set number of threads
  * @param tr the number
  **************************************************************************/
 public static void setThreads(Integer tr)
 {
   threads = tr;
 }
 
 /***************************************************************************
  * Get distributed execution status.
  * @return the status.
  **************************************************************************/
 public static boolean getDistributed()
 {
   return distributed;
 }
 
 /***************************************************************************
  * Set distributed execution status.
  * @param de the status
  **************************************************************************/
 public static void setDistributed(boolean de)
 {
  distributed = de;
 }
 
 /***************************************************************************
  * Get distributed execution script.
  * @return the script.
  **************************************************************************/
 public static String getDistScript()
 {
   return distScript;
 }
 
 /***************************************************************************
  * Set distributed execution script.
  * @param script the script.
  **************************************************************************/
 public static void setDistScript(String script)
 {
   distScript = script;
   terminateServers();
 }
 
 /***************************************************************************
  * Get function names.
  * @return the function names.
  **************************************************************************/
 public static String[] getFunctions()
 {
   return functions;
 }
 
 /***************************************************************************
  * Set function names.
  * @param f the function names.
  **************************************************************************/
 public static void setFunctions(String[] f)
 {
   functions = f;
 }
 
 /***************************************************************************
  * Get selected functions
  * @return the index of the function
  **************************************************************************/
 public static Integer getSelectedFunction()
 {
   return selfun;
 }
 
 /***************************************************************************
  * Set selected functions
  * @param f the index of the function.
  **************************************************************************/
 public static void setSelectedFunction(Integer f)
 {
   selfun = f;
 }

 /***************************************************************************
  * Get value map.
  * @return the map
  **************************************************************************/
 public static Map<String,Integer> getValueMap()
 {
   return values;
 }

 /***************************************************************************
  * Set value map.
  * @param v the map.
  **************************************************************************/
 public static void setValueMap(Map<String,Integer> v)
 {
   values = v;
 }
 
 /***************************************************************************
  * Determine whether trace is to be supported.
  * @return true if that is the case.
  **************************************************************************/
 public static boolean supportTrace()
 {
   return trace;
 }
 
 /***************************************************************************
  * Determine whether trace is to be visualized.
  * @return true if that is the case.
  **************************************************************************/
 public static boolean showTrace()
 {
   return showTrace;
 }
 
 /***************************************************************************
  * Visualize trace.
  * @param show new visualization status.
  **************************************************************************/
 public static void showTrace(boolean show)
 {
   showTrace = show;
 }
 
 /***************************************************************************
  * The exception raised for aborting the current execution.
  **************************************************************************/
 public static final class Stopped extends RuntimeException
 {
   private static final long serialVersionUID = 28091967L;
   public Stopped() { super("execution was stopped"); }
 }

 /***************************************************************************
  * Stop execution.
  **************************************************************************/
 public static void stop()
 {
   stopped = true;
   MainSWT.stop(stopped);
 }
 
 /***************************************************************************
  * Throw an exception, if stop signal is set and reset signal
  ***************************************************************************/
 public static void checkStopped()
 {
   if (!stopped) return;
   stopped = false;
   MainSWT.stop(stopped);
   throw new Stopped();
 }
 
 /****************************************************************************
  * A writer for printing error messages; records the number of errors.
  ***************************************************************************/
 public final static class ErrorWriter extends PrintWriter
 {
   // record number of errors
   private int errors;
   
   /**************************************************************************
    * Construct an error writer that prints to given writer.
    * @param writer the writer where error messages are printed.
    *************************************************************************/
   public ErrorWriter(Writer writer)
   {
     super(writer, true);
     errors = 0;
   }
   
   /**************************************************************************
    * Get the number of errors.
    * @return the error number.
    *************************************************************************/
   public int getErrors() { return errors; }
   
   /***************************************************************************
    * Report type error 
    * @param e the type error
    **************************************************************************/
   public void reportError(TypeChecker.TypeError e)
   {
     errors++;
     if (e.tree == null || e.tree.getPosition() == null) 
     {
       // internal error
       println(e.getMessage());
       return;
     }
     SourcePosition position = e.tree.getPosition();
     int line = position.lineFrom;
     int character = position.charFrom;
     AST.Source source = position.source;
     println(source.file.getName() + ":" + (line+1) + ": " + e.getMessage());
     println(source.lines.get(line));
     for (int i=0; i<character; i++) print(" ");
     println("^");
     //MainSWT.reportError(e.getMessage(), position);
   }
 }
 
 /***************************************************************************
  * Get short name of function.
  * @param f the function
  * @return its short name (without size information).
  **************************************************************************/
 public static String shortName(FunctionSymbol f)
 {
   StringBuffer b = new StringBuffer();
   b.append(f.ident.string);
   b.append('(');
   int t = f.types.length;
   for (int j=0; j<t; j++)
   {
     b.append(shortName(f.types[j]));
     if (j+1 < t) b.append(',');
   }
   b.append(')');
   return b.toString();
 }
 
 /***************************************************************************
  * Get short name of type (without size information)
  * @param type the type
  * @return its short name.
  **************************************************************************/
 private static String shortName(Type type)
 {
   if (type instanceof Type.NatType)
     return "ℕ";
   if (type instanceof Type.IntType)
     return "ℤ";
   if (type instanceof Type.SetType)
     return "Set[" + shortName(((Type.SetType)type).type) + "]";
   if (type instanceof Type.ArrayType)
     return "Array[" + shortName(((Type.ArrayType)type).type) + "]";
   if (type instanceof Type.MapType)
   {
     Type t = ((Type.MapType)type).type1;
     if (t instanceof Type.IntType && ((Type.IntType) t).ivalue1 == 0)
       return "Array[" + shortName(((Type.MapType)type).type2) + "]";
     else
       return "Map[" + shortName(t) + ","
       + shortName(((Type.MapType)type).type2) + "]";
   }
   if (type instanceof Type.TupleType)
     return "Tuple[" + shortNames(((Type.TupleType)type).types,",") + "]";
   if (type instanceof Type.RecordType)
     return "Record[" + shortNames(((Type.RecordType)type).symbols,",") + "]";
   if (type instanceof Type.RecursiveType)
     return ((Type.RecursiveType)type).ritem.ident.toString() + "[...]";
   return type.toString();
 }
 private static String shortNames(Type[] asts, String sep)
 {
   StringBuffer buffer = new StringBuffer();
   int i = 0;
   int n = asts.length;
   for (Type ast : asts)
   {
     buffer.append(shortName(ast));
     i++; 
     if (i < n) buffer.append(sep);
   }
   return buffer.toString();
 }
 private static String shortNames(ValueSymbol[] symbols, String sep)
 {
   StringBuffer buffer = new StringBuffer();
   int i = 0;
   int n = symbols.length;
   for (ValueSymbol symbol : symbols)
   {
     buffer.append(symbol.ident + ":" + shortName(symbol.type));
     i++; 
     if (i < n) buffer.append(sep);
   }
   return buffer.toString();
 }
 
 /***************************************************************************
  * Signal that a choice has been performed in deterministic execution mode
  **************************************************************************/
 public static void performChoice()
 {
   performedChoice = true;
 }
}
//---------------------------------------------------------------------------
//end of file
//---------------------------------------------------------------------------