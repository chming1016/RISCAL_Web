// ---------------------------------------------------------------------------
// Parallel.java
// Parallel execution of models.
// $Id: Parallel.java,v 1.31 2018/06/01 08:57:19 schreine Exp $
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
package riscal;

import java.util.*;
import java.util.concurrent.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;
import java.text.*;
import java.util.function.*;
import java.util.zip.*;

import riscal.types.Environment.Symbol.*;
import riscal.semantics.Translator;
import riscal.semantics.Types.*;
import riscal.util.*;
import riscal.util.Seq.*;
import riscal.syntax.*;
import riscal.parser.*;

public final class Parallel
{
  // the average amount of time between two requests to the local task queue
  private static final long REQTIME = 1000000000; // (1s, in ns)
  
  // the maximum number of tasks in queue (limit memory usage)
  private static final int MAXTASKS = 2000000;
  
  // the time a server waits for a message before termination 
  private static final int TIMEOUT = 15000; // (15s, in ms)
  
  // set if there is an ongoing parallel execution
  private static Parallel parallel = null;
  
  // set during parallel execution to the replacement/original output stream
  private static StringWriter swriter = null;
  private static PrintWriter owriter = null; 
  
  // ==========================================================================
  //
  // Client: 
  //
  // - main thread executes the scheduler
  // - pool threads execute tasks
  // - server proxy threads handle connections to servers
  //
  // * during checking, each server thread has exclusive access to its 
  //   connection (only this thread performs send() and receive() operations).
  //
  // * outside checking, the main thread may access the server connections.
  // 
  // * scheduler thread and proxy threads share access to task queue.
  //   
  // * all threads share access to the state of the "scheduler" and
  //   the "parallel" object.
  //
  // Server:
  //
  // - main thread executes scheduler and handles connection to client
  // - pool threads execute tasks
  //
  // * scheduler thread has exclusive access to the connection
  //   (only this thread performs send() and receive() operations)
  // 
  // * all threads share access to the state of the "scheduler" and
  //   the "parallel" object.
  // ==========================================================================
  //
  // the client-side external interface
  //
  // ==========================================================================
  
  /****************************************************************************
   * Determine if there is an ongoing parallel execution.
   * @param true if a parallel execution is going on.
   ***************************************************************************/
  public static boolean isRunning() { return parallel != null; }
  
  /****************************************************************************
   * Apply a function to a sequence of values by parallel execution.
   * @param threads the number of local threads to be used.
   * @param servers the remote servers to be used (may be null).
   * @param fun the function.
   * @param fnum the number of the function in the list of functions
   *        (only needed, if servers != null)
   * @param values the sequence of values (may be null to indicate 
   *               all values of the functions argument type)
   * @param size the length of the sequence.
   * @return the result of the execution 
   *         (NoResult, if already a parallel execution is running)
   ***************************************************************************/
  public static Result execute(int threads, List<Server> servers,
    FunctionSymbol fun, int fnum, Seq<Value> values, long size)
  {
    if (isRunning()) return new NoResult();
    if (servers != null)
    {
      boolean okay = checkAlive(servers);
      if (!okay) return new NoResult();
    }
    Main.initProgress();
    LocalSupplier supplier = new LocalSupplier(fun, values, size);
    parallel = new Parallel(threads, servers, 0, supplier, fnum);
    supplier.setParallel(parallel);
    long time = System.currentTimeMillis();
    Result result = parallel.run(); 
    result.setTime(System.currentTimeMillis()-time);
    parallel = null;
    Main.reportProgress(0, 1);
    return result;
  }
  
  /****************************************************************************
   * Connect to remote servers.
   * @param command the command to be executed for establishing 
   *                the connection to the servers 
   *                (can be multiple commands, one command per line)
   * @return the list of connected servers (null, if an error occurred)
   ***************************************************************************/
  public static List<Server> connectServers(String command)
  {
    List<Server> servers = new ArrayList<Server>();
    String[] commands = command.split("\n");
    for (String c : commands)
    {
      if (c.trim().isEmpty()) continue;
      Server server = Server.connect(c);
      if (server == null) return null;
      servers.add(server);
    }
    Main.println("Connected to remote servers.");
    return servers;
  }

  /****************************************************************************
   * Terminate connection to remote servers.
   * @param servers the list of connected servers.
   ***************************************************************************/
  public static void terminateServers(List<Server> servers)
  {
    for (Server server : servers)
      server.terminate();
  }
  
  // --------------------------------------------------------------------------
  // Thread that reads a single line from server.
  // --------------------------------------------------------------------------
  private static class ReadThread extends Thread
  {
    public String answer;
    private Server server;
    public ReadThread(Server server) { this.server = server; }
    public void run()
    {
      answer = server.connection.readLine();
    }
  }
  
  /***************************************************************************
   * Check whether servers are still alive
   * @param servers the servers
   **************************************************************************/
  public static boolean checkAlive(List<Server> servers)
  {
    int n = servers.size();
    for (int s=0; s<n; s++)
    {
      Server server = servers.get(s);
      server.connection.send("ping");      
      ReadThread thread = new ReadThread(server);
      thread.start();
      for (int i=0; i<20; i++)
      {
        if (thread.answer != null) break;
        try { Thread.sleep(100); } catch(InterruptedException e) { }
      }
      String answer = thread.answer;
      if (answer == null || !answer.equals("pong"))
      {
        if (answer == null)
          Main.println("ERROR: no answer received from server " + server.address);
        else
          Main.println("ERROR: expected \"pong\" but received \"" + answer + 
              "\" from server " + server.address);
        server.connection.close();
        Main.println("Reconnecting server...");
        Server server0 = Server.connect(server.command);
        if (server0 == null) 
        {
          Main.println("Could not reconnect to server");
          return false;
        }
        if (server.source != null)
        {
          List<Server> servers0 = new ArrayList<Server>();
          servers0.add(server0);
          boolean okay = process(servers0,
                  server.source, server.nondet, server.defvalue, server.map);
          if (!okay) return false;
        }
        servers.set(s, server0);
        server = server0;
      }
    }
    return true;
  }
  
  /***************************************************************************
   * Let servers process source.
   * @param servers the servers
   * @param source the source
   * @param nondet nondeterministic translation requested.
   * @param defval the default value for constants.
   * @param map a map of specific constants to values.
   * @returns true if everything went fine.
   **************************************************************************/
  public static boolean process(List<Server> servers, 
    AST.Source source, boolean nondet, Integer defvalue, Map<String,Integer> map)
  {
    // check whether servers are alive
    boolean okay = checkAlive(servers);
    if (!okay) return false;
    
    // send sources (processed in parallel)
    for (Server server : servers)
      server.process(source, nondet, defvalue, map);
    
    // wait for responses
    for (Server server : servers)
    {
      String tag = server.connection.receiveTag();
      if (tag == null)
      {
        Main.println("no response from server " + server.address);
        return false;
      }
      if (!tag.equals("okay"))
      {
        Main.println("invalid response \"" + tag + "\" from server " + server.address);
        return false;
      }
    }
    return true;
  }
  
  // ==========================================================================
  //
  // the server-side external interface
  //
  // ==========================================================================
  
  // the interface to writer error messages to in server mode
  private static PrintWriter errorWriter;
  
  /***************************************************************************
   * Run a server listening for requests from a client.
   * @param threads the number of threads to be used by the server.
   **************************************************************************/
  public static void runServer(int threads)
  {
    try
    {
      File errorFile = File.createTempFile("riscal", ".log");
      errorWriter = new PrintWriter(new FileWriter(errorFile));
      String hostName = InetAddress.getLocalHost().getCanonicalHostName();
      ServerSocket serverSocket = new ServerSocket(0);
      int portNumber = serverSocket.getLocalPort();
      SecureRandom random = new SecureRandom();
      String password = new BigInteger(130, random).toString(32);
      System.out.println(hostName + " " + portNumber + " " + password);
      boolean okay = runServer(serverSocket, password, threads);
      if (okay) errorFile.delete();
    }
    catch(UnknownHostException e)
    {
      reportError(null, "cannot determine address of local host.\n");
      System.exit(-1);
    }
    catch(IOException e)
    {
      reportError(null, "IOException when initializing server: " + e.getMessage());
      System.exit(-1);
    }
  }
    
  // ==========================================================================
  //
  // multi-threaded execution
  //
  // ==========================================================================
  
  // --------------------------------------------------------------------------
  //
  // the outcome of an execution 
  //
  // --------------------------------------------------------------------------
  public interface Result 
  { 
    public void setTime(long time); // the time that the execution took (in ms)
    public long getTime();
  }

  public static class ResultBase implements Result
  {
    private long time;
    public ResultBase() { this.time = -1; }
    public void setTime(long time) { this.time = time; }
    public long getTime() { return time;}
  }
      
  // --------------------------------------------------------------------------
  //
  // The normal outcome of an execution
  //
  // --------------------------------------------------------------------------
  public static class OkayResult extends ResultBase implements Result
  {
    public final long received; // the number of received inputs
    public final long checked;  // the number of checked inputs
    public final long skipped;  // the number of skipped (inadmissible) inputs
    public final long ignored;  // the number of deliberately ignored inputs
    public OkayResult(long received, long checked, long skipped, long ignored) 
    { 
      this.received = received;
      this.checked = checked;
      this.skipped = skipped;
      this.ignored = ignored;
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // The description of a failed task
  //
  // --------------------------------------------------------------------------
  public static class ErrorResult extends ResultBase implements Result
  {
    public final String task;    // a description of the task
    public final String error;   // a description of the error
    public final String output;  // the captured output
    public ErrorResult(String task, String error, String output)
    {
      this.task = task;
      this.error = error;
      this.output = output;
    }
  }

  // --------------------------------------------------------------------------
  //
  // The description of a failed server
  //
  // --------------------------------------------------------------------------
  public static class FailureResult extends ResultBase implements Result
  {
    public final Server server;  // the server
    public final String error;   // a description of the error
    public final String output;  // the captured output
    public FailureResult(Server server, String error, String output)
    {
      this.server = server;
      this.error = error;
      this.output = output;
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // Execution was stopped
  //
  // --------------------------------------------------------------------------
  public static class StopResult extends ResultBase implements Result { }
  
  // --------------------------------------------------------------------------
  //
  // Execution could not be completed.
  //
  // --------------------------------------------------------------------------
  public static class NoResult extends ResultBase implements Result { }

  // --------------------------------------------------------------------------
  //
  // A supplier of tasks
  //
  // --------------------------------------------------------------------------
  public interface TaskSupplier 
  {
    /*************************************************************************
     * Ask for new tasks.
     * @param n the desired number of tasks.
     * @param consumer the consumer of tasks.
     * @return the number of tasks delivered (0 indicates end of supply)
     ************************************************************************/
    public int ask(int n, Consumer<Task> consumer);
    
    /*************************************************************************
     * Returns true if supplier can provide identifiers instead of tasks
     * (based on a commonly accepted mapping of identifiers to tasks).
     * @return true if the suppler can provide identifiers.
     ************************************************************************/
    public boolean hasIds();
    
    /*************************************************************************
     * Ask for new tasks by identifiers.
     * @param n the desired number of tasks.
     * @return an array of two values where the first is the
     *         identifier of the first task and the second is
     *         the number of identifiers provided (null if no more)
     ************************************************************************/
    public long[] askIds(int n);
    
    /*************************************************************************
     * Report progress.
     * @param checked the number of checked tasks
     ************************************************************************/
    public void reportProgress(long checked);
  }
  
  // -------------------------------------------------------------------------
  //
  // A task supplier fed from a local sequence of values 
  //
  // -------------------------------------------------------------------------
  private static class LocalSupplier implements TaskSupplier
  {
    private FunctionSymbol fun;
    private Seq<Value> values;
    private long size;
    private long counter;
    private boolean hasIds;
    private Parallel parallel;
    
    public LocalSupplier(FunctionSymbol fun, Seq<Value> values, long size)
    {
      this.fun = fun;
      this.values = values;
      this.size = size;
      this.counter = 0;
      if (values == null)
      {
        this.values = Main.getValues(fun);
        hasIds = true;
      }
      else
        hasIds = false;
      parallel = null;
    }
    
    public void setParallel(Parallel parallel) { this.parallel = parallel; }
    
    /*************************************************************************
     * Ask for new tasks.
     * @param n the desired number of tasks.
     * @param consumer the consumer of the task
     * @return the number of tasks delivered (0 indicates end of supply)
     ************************************************************************/
    public int ask(int n, Consumer<Task> consumer)
    {
      // allow multi-threaded access
      synchronized(this)
      {
        try
        {
          int result = 0;
          // Main.reportProgress(counter, size);
          for (int i=0; i<n; i++)
          {
            Seq.Next<Value> next = values.get();
            if (next == null) break;
            consumer.accept(newTask(fun, next.head));
            counter++;
            values = next.tail;
            result++;
          }
          return result;
        }
        catch(Main.Stopped e)
        {
          if (parallel != null) parallel.setStopped();
          return 0;
        }
      }
    }
    
    public boolean hasIds() { return hasIds; }

    public long[] askIds(int n)
    {
      // allow multi-threaded access
      synchronized(this)
      {
        try
        {
          if (!hasIds) return null;
          Main.reportProgress(counter, size);
          long[] results = null;
          for (int i=0; i<n; i++)
          {
            Seq.Next<Value> next = values.get();
            if (next == null) break;
            if (results == null)
            {
              results = new long[2];
              results[0] = counter;
            }
            counter++;
            values = next.tail;
          }
          if (results == null) return results;
          results[1] = counter-results[0];
          return results[1] == 0 ? null : results;
        }
        catch(Main.Stopped e)
        {
          if (parallel != null) parallel.setStopped();
          return null;
        }
      }
    }
    
    /*************************************************************************
     * Report progress.
     * @param checked the number of checked tasks
     ************************************************************************/
    public void reportProgress(long checked)
    {
      Main.reportProgress(checked, size);
    }
  }  

  /****************************************************************************
   * Construct a new task.
   * @param fun the function executed by the task.
   * @param value the argument to the function.
   * @return the task.
   ***************************************************************************/
  private static Task newTask(FunctionSymbol fun, Value value)
  {
    Value[] args;
    if (fun.types.length == 1)
      args = new Value[] { value };
    else
    {
      Value.Array value0 = (Value.Array)value;
      args = value0.get();
    }
    return new Task(fun, new Argument(args));
  }
  
  // the number of local threads being used
  private int threads;
  
  // the remote servers being used (may be null)
  private List<Server> servers;
  
  // the number of servers that are not yet idle
  private int running;
  
  // the total number of servers (if servers is null)
  private int snumber;
  
  // the supplier of tasks
  private TaskSupplier supplier;
  
  // the execution result (null, if none yet)
  private volatile Result result;
  
  // the number of the function in the list of functions
  private int fnum;

  // execution statistics
  private long received; // number of received inputs
  private long checked;  // number of actually checked inputs
  private long skipped;  // number of skipped inputs (preconditions violated)
  private long ignored;  // number of ignored inputs
  
  /****************************************************************************
   * Initialize parallel execution with local threads and remote servers 
   * picking tasks from a supplier.
   * @param threads the number of local threads
   * @param servers the remote servers (may be null)
   * @param snumber the total number of servers (if servers is null)
   * @param supplier the supplier of tasks
   * @param fun the number of the function applied in the tasks
   ***************************************************************************/
  private Parallel(int threads, List<Server> servers, int snumber,
    TaskSupplier supplier, int fnum)
  {
    this.threads  = threads;
    this.servers = servers;
    this.running = 1 + (servers == null ? 0 : servers.size());
    this.snumber = servers == null ? snumber : servers.size();
    this.supplier = supplier;
    this.result = null;
    this.fnum = fnum;
  }
  
  /****************************************************************************
   * Set execution result (but do not overwrite previously set result)
   * @param result the result
   ***************************************************************************/
  private synchronized void setResult(Result result)
  {
    // do not overwrite previously determined error result
    if (this.result == null) this.result = result;
  }

  /****************************************************************************
   * Check whether a result has been set.
   * @param true if a result has been set.
   ***************************************************************************/
  public synchronized boolean hasResult()
  {
    return result != null;
  }
  
  /****************************************************************************
   * Indicate partial result.
   * @param rn the number of inputs received by process.
   * @param cn the number of inputs checked by process.
   * @param sn the number of inputs skipped by process.
   * @param in the number of inputs ignored by process.
   ***************************************************************************/
  public synchronized void partialResult(long rn, long cn, long sn, long in)
  {
    this.received += rn;
    this.checked += cn;
    this.skipped += sn;
    this.ignored += in;
  }
  
  /****************************************************************************
   * print current execution status.
   * @param writer the writer to print to.
   * @param rn the number of inputs received by process.
   * @param cn the number of inputs checked by process.
   * @param sn the number of inputs skipped by process.
   * @param in the number of inputs ignored by process.
   ***************************************************************************/
  public synchronized void printStatus(PrintWriter writer,
    long rn, long cn, long sn, long in)
  {
    writer.println((received+rn) + " inputs (" + 
        (checked+cn) + " checked, " +
        (skipped+sn) + " inadmissible, " + 
        (ignored+in) + " ignored, " +
        ((received+rn)-(checked+cn)-(skipped+sn)-(ignored+in)) + " open)...");
    supplier.reportProgress(checked+cn);
  }
  
  /****************************************************************************
   * Write status into the message (and reset it to zero).
   * @param message the message.
   ***************************************************************************/
  public synchronized void writeStatus(List<String> message)
  {
    message.add(received+"");
    message.add(checked+"");
    message.add(skipped+"");
    message.add(ignored+"");
    received = 0;
    checked = 0;
    skipped = 0;
    ignored = 0;
  }
  
  /****************************************************************************
   * Indicate completion of a task handler.
   ***************************************************************************/
  public synchronized void setDone()
  {
    running--;
    if (running == 0) setResult(new OkayResult(received, checked, skipped, ignored));
  }
  
  /*************************************************************************
   * Indicate the failure of a task
   * @param task a description of the failed task
   * @param error a description of the error 
   * @param output the output produced by the task
   ************************************************************************/
  public synchronized void setError(String task, String error, String output)
  {
    setResult(new ErrorResult(task, error, output));
  }
  
  /****************************************************************************
   * Indicate stopping of execution.
   ***************************************************************************/
  public synchronized void setStopped()
  {
    setResult(new StopResult());
  }
  
  /*************************************************************************
   * Indicate the abortion of a server.
   * @param server the failed server.
   * @param error a description of the error 
   ************************************************************************/
  public synchronized void setFailure(Server server, String error, String output)
  {
    setResult(new FailureResult(server, error, output));
  }
  
  /****************************************************************************
   * Execute tasks provided by supplier.
   * @return the outcome of the execution.
   ***************************************************************************/
  public Result run()
  {
    // set output stream
    swriter = new StringWriter();
    PrintWriter writer = new PrintWriter(swriter, true);
    owriter = Main.getOutput();
    Main.setInputOutput(null, writer);
    
    // start servers
    ServerProxy[] proxies = null;
    if (servers != null)
    {
      proxies = new ServerProxy[snumber];
      for (int i=0; i<snumber; i++)
      {
        Server server = servers.get(i);
        proxies[i] = new ServerProxy(server, snumber, fnum, supplier, this);
        proxies[i].start();
      }
    }
    
    // run scheduler in local thread
    Scheduler scheduler = new Scheduler(threads, supplier, snumber, this);
    scheduler.run();
    
    // wait for termination of server threads
    if (proxies != null)
    {
      for (ServerProxy proxy : proxies)
      {
        while (proxy.isAlive())
        {
          try { Thread.sleep(100); } catch(InterruptedException e) { }
          if (result != null) continue;           
          try 
          { 
            Main.checkStopped(); 
          } 
          catch(Main.Stopped e) 
          { 
            setStopped();
          }
        }
      }
    }
    
    // reset output stream
    Main.setInputOutput(null, owriter);
    swriter = null;
    owriter = null;
    
    // return result (if any)
    if (result == null) result = new NoResult();
    return result;
  }
  
  // --------------------------------------------------------------------------
  // 
  // A scheduler of tasks
  // 
  // --------------------------------------------------------------------------
  private static class Scheduler extends Thread implements Consumer<Task>
  {
    // the number of threads to be used by the scheduler
    private int threads;
    
    // the supplier of tasks
    private TaskSupplier supplier;
    
    // the number of additional servers in use
    private long servers;
    
    // the parallelism coordinator
    private Parallel parallel;

    // execution statistics
    private long received; // number of received inputs
    private long skipped;  // number of skipped inputs (preconditions violated)
    private long checked;  // number of actually checked inputs
    private long ignored;  // number of ignored inputs

    // the service executor
    private ThreadPoolExecutor executor;
    
    /*************************************************************************
     * Create task scheduler that uses local threads.
     * @param threads the number of threads to be used.
     * @param supplier the supplier of tasks.
     * @param the number of additional servers being used.
     * @param parallel the parallelism coordinator.
     * @param supplier the supplier of tasks.
     ************************************************************************/
    private Scheduler(int threads, TaskSupplier supplier, long servers,
      Parallel parallel)
    {
      // static information
      this.threads = threads;
      this.supplier = supplier;
      this.servers = servers;
      this.parallel = parallel;
      this.received = 0;
      this.skipped = 0;
      this.checked = 0;
      this.ignored = 0;
      
      // create executor
      BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
      executor = new ThreadPoolExecutor(threads, threads, 
          0, TimeUnit.SECONDS, queue, new SchedulerFactory());
    }
    
    /*************************************************************************
     * Accept task for execution.
     * @param task the task.
     ************************************************************************/
    public void accept(Task task)
    {
      if (Main.ignoreInput()) 
        ignored++;
      else
        executor.execute(new SchedulerTask(task, this));
    }
    
    /*************************************************************************
     * increase number of skipped inputs (thread-safe)
     ************************************************************************/
    public synchronized void skippedInput() { skipped++; }

    /*************************************************************************
     * increase number of checked inputs (thread-safe)
     ************************************************************************/
    public synchronized void checkedInput() { checked++;}
    
    /*************************************************************************
     * set stopped status (thread-safe)
     ************************************************************************/
    public synchronized void setStopped() 
    { 
      parallel.setStopped();
    }
    
    /*************************************************************************
     * Indicate the failure of a task
     * @param task the failed task
     * @param error a description of the error 
     ************************************************************************/
    public synchronized void setError(Task task, String error)
    {
      parallel.setError(task.toString(), error, swriter.toString());
    }
    
    /*************************************************************************
     * Start execution of the scheduler.
     ************************************************************************/
    public void run()
    {
      // the number of inputs to be checked (may be null)
      Long checkNumber = Main.getCheckNumber();
      
      // the percentage of inputs that is used for checking
      double cfactor = Main.getCheckPercentage() == null ? 1 :
        Main.getCheckPercentage()/(double)Main.MAX_PERCENTAGE;
      
      // the time since the last report
      long rtime = System.nanoTime();
      
      // the current estimated average time of processing a task
      long ptime = 100000000; // initially: 100 ms
      
      // the current number of tasks we ask for 
      int number = Math.min(MAXTASKS, (int)(2*threads/cfactor));
      try
      {
        BlockingQueue<Runnable> queue = executor.getQueue();
        S: while (true)
        {
          // measure time to acquire new tasks
          long atime = System.nanoTime();
          int tasks = supplier.ask(number, this);
          long atime0 = System.nanoTime()-atime;
          if (tasks == 0) break;
          received += tasks;
          if (owriter != null)
          {
            synchronized(this)
            {
              parallel.partialResult(received, checked, skipped, ignored);
              received = checked = skipped = ignored = 0;
            }
          }
          int s = queue.size();
          // to avoid empty queues, we keep twice the number of tasks in queue 
          // as estimated to be needed for acquiring new tasks
          long qsize = Math.min(MAXTASKS, 2*(atime0/ptime));
          long t = System.nanoTime();
          do
          {
            long rtime0 = System.nanoTime();
            if (rtime0-rtime >= 2000000000)
            {
              // a report every two seconds
              parallel.printStatus(owriter, received, checked, skipped, ignored);
              rtime = rtime0;
            }
            if (parallel.hasResult()) { break S; }
            if (checkNumber != null && checked >= checkNumber) break S;
            Main.checkStopped();
            try { Thread.sleep(10); } catch (InterruptedException e) { }
          }
          while (queue.size() > qsize);
          long t0 = System.nanoTime()-t;
          int s0 = queue.size();
          int s1 = s-s0;
          if (s1 != 0) 
          {
            // in average the server receives one request every REQTIME units
            ptime = t0/s1;
            number = Math.max(1,Math.min(MAXTASKS,(int)(REQTIME*(1+servers)/ptime/cfactor)));
          }
        }
      }
      catch(Main.Stopped e)
      {
        // indicate that we were stopped
        setStopped();
      }
      catch(Exception e)
      {
        reportError(null, "scheduler exception " + e.getMessage());
      }
      
      // shutdown executor
      if (parallel.hasResult())
        executor.shutdownNow();
      else
        executor.shutdown();
      try { while (!executor.awaitTermination(1, TimeUnit.MINUTES)) { } }
      catch(InterruptedException e) { } ;

      // indicate termination
      parallel.partialResult(received, checked, skipped, ignored);
      parallel.setDone();
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // A thread factory creating threads with minimum priority to not 
  // interfere with GUI thread.
  //
  // --------------------------------------------------------------------------
  public static class SchedulerFactory implements ThreadFactory
  {
    public Thread newThread(Runnable r)
    {
      Thread thread = new Thread(r);
      thread.setPriority(Thread.MIN_PRIORITY);
      return thread;
    }
  }
  
  // --------------------------------------------------------------------------
  //
  // A task storing an error in a given object
  // 
  // --------------------------------------------------------------------------
  public static class SchedulerTask implements Runnable
  {
    private Task task;
    private Scheduler scheduler;
    public SchedulerTask(Task task, Scheduler scheduler)
    {
      this.task = task;
      this.scheduler = scheduler;;
    }
    public void run()
    {
      TaskResult result = task.call();
      if (result.stopped) 
        scheduler.setStopped();
      else if (result.error != null) 
        scheduler.setError(task, result.error);
      else if (result.checked)
        scheduler.checkedInput();
      else
        scheduler.skippedInput();
    }
  }

  // --------------------------------------------------------------------------
  //
  // A task to be executed by a thread
  //
  // --------------------------------------------------------------------------
  public static class Task implements Callable<TaskResult>
  {
    public final FunctionSymbol fun;  // the operation
    public final Argument arg;        // the argument

    /*************************************************************************
     * Create task for execution of fun(value)
     * @param fun the operation
     * @param arg the argument to which the operation is applied
     *************************************************************************/
    public Task(FunctionSymbol fun, Argument arg)
    {
      this.fun = fun;
      this.arg = arg;
    }
    
    /**************************************************************************
     * Get string description of this task.
     * @returns the description.
     *************************************************************************/
    public String toString()
    {
      return Main.toString(fun, arg.values);
    }
    
    /**************************************************************************
     * Execute the task.
     * @return a description of the task result
     *************************************************************************/
    public TaskResult call()
    {
      try
      {
        int slots = fun.getSlots();
        List<ContextCondition> pre = fun.getPreconditions();
        int[] p = Translator.toSlots(fun.params);
        String[] names = Translator.toNames(fun.params);
        Context c = new Context(slots, p, arg, names);
        boolean okay = Main.checkPrecondition(pre, c);
        if (!okay) return new TaskResult(false, null, false);
        FunSem f = fun.getValue();
        if (f instanceof FunSem.Single)
        {
          FunSem.Single f0 = (FunSem.Single)f;
          f0.apply(arg);
        }
        else
        {
          FunSem.Multiple f0 = (FunSem.Multiple)f;
          Seq<Value> results = f0.apply(arg);
          Long runs = Main.getCheckRuns();
          long counter = 0;
          while (true)
          {
            if (runs != null && counter >= runs) break;
            Next<Value> next = results.get();
            if (next == null) break;
            results = next.tail;
            counter++;
          }
        }
        return new TaskResult(false, null, true);
      }
      catch(Main.Stopped e)
      {
        return new TaskResult(true, null, false);
      }
      catch(Exception e)
      {
        StringWriter swriter = new StringWriter();
        PrintWriter pwriter = new PrintWriter(swriter);
        Main.handleException(e, pwriter);
        String message = swriter.toString();
        return new TaskResult(false, message.substring(0, message.length()-1), false);
      }
    }
  }
  
  // -------------------------------------------------------------------------
  //
  // the result of a task
  //
  // -------------------------------------------------------------------------
  private static class TaskResult
  {
    // test in order:
    // stopped: execution was stopped.
    // error != null: an error has occurred
    // checked: the input was checked (otherwise skipped)
    public final boolean stopped;
    public final String error;
    public final boolean checked;
    public TaskResult(boolean stopped, String error, boolean checked)
    {
      this.stopped = stopped;
      this.error = error;
      this.checked = checked;
    }
  }
  
  // ==========================================================================
  //
  // distributed execution protocol:
  //
  // Startup (within 100s):
  // Client->Server: init[password,version]
  // Server->Client: okay | error
  //
  // Sequences of "Ping Pong" "Process Source" or "Check Function"
  //
  // Ping Pong:
  // Client->Server: ping
  // Server->Client: pong
  //
  // Process Source:
  // Client->Server: source[nondet,defval,consts...,source,lines...]
  // Server->Client: okay | error
  //
  // Check Function:
  // Client->Server:  check[fnum,number,perc,runs,servers]
  // Server->Client:  ask[number,received,checked,skipped,ignored]
  // Client->Server:  valuenone | valueseq[from,number] | valuelist[number,values...]
  // Server->Client:  okayresult[received,checked,skipped,ignored] | 
  //                  errorresult[task,error...,output...] | error
  //
  // Stop Execution:
  // Client->Server:  stop
  // Server->Client:  stopped
  //
  // Termination (any time):
  // Client->Server: terminate
  // Server->Client: terminated
  //
  // Keep Server Alive (when not checking):
  // Client->Server: keepalive
  //
  // Timeout (after TIMEOUT time has passed without receiving a message):
  // Server->Client: timeout
  //
  // ==========================================================================
  
  // -----------------------------------------------------------------------
  //
  // A connection end point with which we can exchange messages
  //
  // -----------------------------------------------------------------------
  private static class Connection
  {
    public final Socket socket;
    
    // not to be used for direct reading/writing
    private final BufferedReader reader;
    private final PrintWriter writer;
    
    // last time we read/wrote from/to this connection
    private long rtime = -1;
    private long wtime = -1;
    
    // set to true if closed
    private boolean closed = false;
    
    /*************************************************************************
     * Create connection from socket.
     * @param socket the socket.
     * @throws IOException if a problem occurs.
     ************************************************************************/
    public Connection(Socket socket) throws IOException
    {
      this.socket = socket;
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), false);
      
      // broken detections should eventually lead to server shutdown
      socket.setKeepAlive(true);
    }
    
    /**************************************************************************
     * Close connection.
     *************************************************************************/
    public void close()
    {
      send("closed");
      try
      {
        reader.close();
        writer.close();
        socket.close();
        closed = true;
      }
      catch(IOException e)
      {
      }
    }
    
    /*************************************************************************
     * Return true if connection was closed.
     ************************************************************************/
    public boolean isClosed() { return closed; }
    
    /**************************************************************************
     * Get time when last message was read/written (-1, if never).
     *************************************************************************/
    public long getReadTime() { return rtime; }
    public long getWriteTime() { return wtime; }
    
    /**************************************************************************
     * Write message tag.
     * @param tag the tag.
     *************************************************************************/
    private void writeTag(String line)
    {
      // reportError(null, "send(" + socket.getInetAddress() + "): " + line);
      writer.println(line);
    }
    
    /**************************************************************************
     * Write one line of the message content.
     * @param line the line.
     *************************************************************************/
    private void writeContent(String line)
    {
      // reportError(null, "send(" + socket.getInetAddress() + "): " + line);
      writer.println("." + line);
    }
    
    /**************************************************************************
     * Flush the message.
     *************************************************************************/
    private void flush()
    {
      writer.flush();
      wtime = System.currentTimeMillis();
    }
    
    /**************************************************************************
     * Read one line of the message (filtering "keepalive" messages)
     * @return the line (null, if an error occurred)
     *************************************************************************/
    private String readLine()
    {
      try
      {
        while (true)
        {
          String line = reader.readLine();
          rtime = System.currentTimeMillis();
          // reportError(null, "receive(" + socket.getInetAddress() + "): " + line);
          if (line == null || !line.equals("keepalive")) return line;
        }
      }
      catch(IOException e)
      {
        reportError(null, "IOException: " + e.getMessage());
        return null;
      }
    }
    
    /**************************************************************************
     * Determine whether there is input to be read.
     * @return true if there is input to be read.
     *************************************************************************/
    public boolean canRead()
    {
      try
      {
        return reader.ready();
      }
      catch(IOException e)
      {
        // do not delay attempt to write
        return true;
      }
    }
    
    /**************************************************************************
     * Send a tag-only message to the connection
     * @param tag the tag of the message
     ************************************************************************/
    public void send(String tag)
    {
      writeTag(tag);
      flush();
    }
    
    /**************************************************************************
     * Send a tagged message to the connection
     * @param tag the tag of the message
     * @param message its payload
     ************************************************************************/
    public void send(String tag, String[] message)
    {
      writeTag(tag);
      for (String s : message) writeContent(s);
      flush();
    }
    
    /**************************************************************************
     * Send a tagged message to the connection
     * @param tag the tag of the message
     * @param message its payload
     ************************************************************************/
    public void send(String tag, List<String> message)
    {
      writeTag(tag);
      for (String s : message) writeContent(s);
      flush();
    }
    
    /**************************************************************************
     * Add lines to message 
     * @param lines the payload (broken up into multiple lines preceded
     *        by the number of lines)
     * @param message the message to add to
     * @return the message.
     ************************************************************************/
    public static List<String> addLines(String lines, List<String> message)
    {
      String[] lines0 = lines.split("\n");
      message.add(lines0.length+"");
      for (String s : lines0) message.add(s);
      return message;
    }
   
    /**************************************************************************
     * Receive tag of the next message.
     * @return the tag (null, if an error occurred)
     *************************************************************************/
    public String receiveTag()
    {
      return readLine();
    }
    
    /**************************************************************************
     * Receive a single line of the content of the next message.
     * @return the line (null, if an error occurred)
     *************************************************************************/
    public String receiveContent()
    {
      return readLine().substring(1);
    }
    
    /**************************************************************************
     * Receive n lines of the message
     * @param n the number of lines
     * @param message the list to be filled with the lines (is cleared before).
     * @return the list (null, if an error occurred)
     *************************************************************************/
    public List<String> receive(int n, List<String> message)
    {
      message.clear();
      for (int i=0; i<n; i++)
      {
        String line = receiveContent();
        if (line == null) return null;
        message.add(line);
      }
      return message;
    }
    
    /**************************************************************************
     * Receive a the message whose first line is the number of subsequent lines
     * @param message the list to be filled with the lines (is cleared before).
     * @return the list (null, if an error occurred)
     *************************************************************************/
    public List<String> receive(List<String> message)
    {
      message.clear();
      String line = receiveContent();
      if (line == null) return null;
      Integer n0 = Main.toInteger(line);
      if (n0 == null) return null;
      int n = n0;
      for (int i=0; i<n; i++)
      {
        line = receiveContent();
        if (line == null) return null;
        message.add(line);
      }
      return message;
    }
  }
  
  // -----------------------------------------------------------------------
  //
  // The connection to a server
  //
  // -----------------------------------------------------------------------
  public final static class Server
  {
    // the command by which the connection is to be (re)established
    public final String command;
    
    // set to server connection
    public String address;
    public int port;
    public Connection connection;
    
    // the execution model (may be null)
    public AST.Source source;
    public boolean nondet;
    public Integer defvalue;
    public Map<String,Integer> map;

    /**************************************************************************
     * Create connection to remote server.
     * @param c the command establishing the connection.
     * @return the server (null, if an error occurred)
     *************************************************************************/
    public static Server connect(String c)
    {
      try
      {
        Main.println("Executing \"" + c + "\"...");
        Process process = Runtime.getRuntime().exec(c);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        if (line == null) 
        {
          Main.error("script does not print any output");
          return null;
        }
        String[] tokens = line.split(" ");
        if (tokens.length != 3)
        {
          Main.error("script prints invalid line \"" + line + 
              "\" (not three tokens)");
          return null;
        }
        Integer port = Main.toInteger(tokens[1]);
        if (port == null)
        {
          Main.error("script prints invalid line \"" + line + 
              "\" (not valid port number)");
          return null;
        }
        Main.println("Connecting to " + tokens[0] + ":" + port + "...");
        return new Server(c, tokens[0], port, tokens[2]);
      }
      catch(IOException e)
      {
        Main.error("exception raised when processing script (" + e.getMessage() + ")");
        return null;
      }
    }
      
    /**************************************************************************
     * Creates connection to a server
     * @param command the command to restart the server.
     * @param address the address of the server
     * @param port the port number
     * @param password the password to be provided
     * @throws IOException if a connection error occurred
     *************************************************************************/
    public Server(String command, 
      String address, int port, String password) throws IOException
    {
      this.command = command;
      this.address = address;
      this.port = port;
      this.connection = new Connection(new Socket(address, port));
      
      connection.send("init", new String[] { password, Main.version });
      String okay = connection.receiveTag();
      if (okay == null) throw new IOException("no password response");
      if (!okay.equals("okay")) 
      {
        List<String> error = connection.receive(new ArrayList<String>());
        throw new IOException("\"okay\" expected but \"" + okay + 
            "\" received:\n" + Parallel.toString(error));
      }
      Thread keepAlive = new Thread() {
        public void run()
        {
          while (true)
          {
            try { Thread.sleep(TIMEOUT/3); } catch(InterruptedException e) { }
            if (connection.isClosed()) return;
            long t0 = connection.getWriteTime();
            long t1 = System.currentTimeMillis();
            if (t0 != -1 && t1-t0 < TIMEOUT/3) continue;
            connection.send("keepalive");
          }
        }};
      keepAlive.start();
      Thread watchDog = new Thread() {
        public void run()
        {
          while (true)
          {
            try { Thread.sleep(TIMEOUT); } catch(InterruptedException e) { }
            if (connection.isClosed()) return;
            if (owriter == null) continue;
            if (connection.canRead()) continue;
            long t0 = connection.getReadTime();
            long t1 = System.currentTimeMillis();
            if (t0 == -1 || t1-t0 < TIMEOUT) continue;
            owriter.println("WARNING: no sign of life from server " + address +
                " (you may want to stop and restart the execution).");
          }
        }};
      watchDog.start();
    }
    
    /*************************************************************************
     * Send model to server.
     * @param source the source.
     * @param nondet the nondeterminism flag.
     * @param defvalue the default value.
     * @param map the map of constant values.
     ************************************************************************/
    public void process(AST.Source source, 
      boolean nondet, Integer defvalue, Map<String,Integer> map)
    {
      this.source = source;
      this.nondet = nondet;
      this.defvalue = defvalue;
      this.map = map;
      List<String> message = new ArrayList<String>();
      message.add(nondet+"");
      if (defvalue == null) defvalue = -1;
      message.add(defvalue+"");
      int n = map.size();
      message.add((2*n)+"");
      for (Map.Entry<String,Integer> entry : map.entrySet())
      {
        message.add(entry.getKey());
        message.add(entry.getValue()+"");
      }
      message.add(source.file.getAbsolutePath());
      Connection.addLines(source.text, message);
      connection.send("source", message);
    }
    
    /**************************************************************************
     * Terminate connection.
     *************************************************************************/
    public void terminate()
    {
      connection.close();
    }
  }

  /***************************************************************************
   * Concatenate list of lines to a single string
   * @param lines the sequence of lines (may be null)
   * @return a single string resulting from the concatentation of the
   *         lines where lines are separated by a new line.
   **************************************************************************/
  public static String toString(List<String> lines)
  {
    if (lines == null) return "(null)";
    StringBuffer b = new StringBuffer();
    int n = lines.size();
    if (n > 0)
    {
      b.append(lines.get(0));
      for (int i=1; i<n; i++)
      {
        b.append('\n');
        b.append(lines.get(i));
      }
    }
    return b.toString();
  }
  
  /****************************************************************************
   * Report error on server.
   * @param client the connection to the client (may be null)
   * @param message the text to be reported
   ***************************************************************************/
  private static void reportError(Connection client, String text)
  {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    String time = formatter.format(new Date());
    String output = "ERROR (" + time + "): " + text;
    System.err.println(output);
    System.err.flush();
    if (errorWriter != null)
    {
      errorWriter.println(output);
      errorWriter.flush();
    }
    if (client != null) 
    {
      List<String> message = Connection.addLines(output, new ArrayList<String>());
      client.send("error", message);
    }
  }
  
  /****************************************************************************
   * Run server accepting connection requests from denoted server socket 
   * that provide the denoted password.
   * @param serverSocket the server socket to listen on.
   * @param password the password to be provided.
   * @param threads the number of threads to be used by the server.
   * @param return true if the server terminated normally
   ***************************************************************************/
  private static boolean runServer(ServerSocket serverSocket, String password,
    int threads)
  {
    // password is only valid for one connection, thus we do not care
    // that it is transmitted in plain-text and printed in error message
    Connection client = null;
    try
    {
      // we only wait 15s for a request
      serverSocket.setSoTimeout(15000);
      client = new Connection(serverSocket.accept());
      Connection client0 = client;
      Thread keepAlive = new Thread() {
        public void run()
        {
          while (true)
          {
            try { Thread.sleep(TIMEOUT/3); } catch(InterruptedException e) { }
            if (client0.isClosed()) return;
            long t0 = client0.getWriteTime();
            long t1 = System.currentTimeMillis();
            if (t0 != -1 && t1-t0 < TIMEOUT/3) continue;
            client0.send("keepalive");
          }
        }};
      keepAlive.start();
      Thread watchDog = new Thread() {
        public void run()
        {
          while (true)
          {
            try { Thread.sleep(TIMEOUT); } catch(InterruptedException e) { }
            if (client0.isClosed()) return;
            if (client0.canRead()) continue;
            long t0 = client0.getReadTime();
            long t1 = System.currentTimeMillis();
            if (t0 == -1 ? t1 < TIMEOUT : t1-t0 < TIMEOUT) continue;
            client0.send("timeout");
            System.exit(-1);
          }
        }};
      watchDog.start();
      String tag = client.receiveTag();
      if (!tag.equals("init")) 
      {
        reportError(client, "invalid initialization: tag " +
        "\"init\" expected but \"" + tag + "\" received");
        return false;
      }
      List<String> message = client.receive(2, new ArrayList<String>(3));
      if (message == null) 
      {
        reportError(client, "invalid initialization");
        return false;
      }
      String password0 = message.get(0);
      if (!password0.equals(password))
      {
        reportError(client, "invalid initialization: password " +
        "\"" + password + "\" expected but \"" + password0 + "\" received");
        return false;
      }
      String version = message.get(1);
      if (!version.equals(Main.version)) 
      {
        reportError(client, "invalid initialization: version " +
        "\"" + Main.version + "\" expected but \"" + version + "\" received");
        return false;
      }
      client.send("okay");
      return runServer(client, threads);
    }
    catch(Exception e)
    {
      StringWriter swriter = new StringWriter();
      PrintWriter swriter0 = new PrintWriter(swriter);
      e.printStackTrace(swriter0);
      swriter0.close();
      reportError(client, "Exception: " + swriter.toString());
      return false;
    }
    finally
    {
      try
      {
        if (client != null) client.socket.close();
      }
      catch(IOException e)
      {
        reportError(client, "IOException: " + e.getMessage());
      }
    }
  }
  
  /****************************************************************************
   * Run server communicating with client via denoted connection
   * @param connection
   * @param threads the number of threads to be used by the server.
   * @return true if the server terminated normally.
   ***************************************************************************/
  private static boolean runServer(Connection client, int threads)
  {
    String command = "";
    try
    {
      List<FunctionSymbol> funs = null;
      while (true)
      {
        command = client.receiveTag();
        if (command == null) return false;
        switch (command)
        {
        case "ping" :
          client.send("pong");
          break;
        case "source" :
          AST.Specification spec = processSource(client);
          if (spec == null) return false;
          funs = Main.getFunctions(spec, true);
          break;
        case "check":
          if (funs == null)
          {
            reportError(client, "no specification");
            return false;
          }
          boolean okay = checkOperation(funs, client, threads);
          if (!okay) return false;
          break;
        case "stop":
          client.send("stopped");
          break;
        case "terminate":
          client.send("terminated");
          return true;
        default:
          reportError(client, "unknown command: " + command);
          return false;
        }
      }
    }
    catch(IOException e)
    {
      reportError(client, "IOException when reading command \"" + 
          command + "\": " + e.getMessage());
      return false;
    }
    catch(NumberFormatException e)
    {
      reportError(client, "NumberFormatException when parsing command \"" + 
          command + "\": " + e.getMessage());
      return false;
    }
  }
  
  /****************************************************************************
   * Parse a string to a boolean
   * @param line a line
   * @return the boolean denoted by the line
   * @throws NumberFormatException if a format error occurred
   ***************************************************************************/
  private static boolean parseBoolean(String line) throws NumberFormatException
  {
    if (line.equals("true")) return true;
    if (line.equals("false")) return false;
    throw new NumberFormatException("invalid boolean: " + line);
  }
  
  /****************************************************************************
   * Process a source file triggered by a message of the following form:
   * message format:
   * - nondeterministic (true/false)
   * - default value
   * - 2*number of specific constants
   * - sequence of constant definitions:
   *   - constant name
   *   - constant value
   * - number of lines in source
   * - path of source file
   * - sequence of source lines:
   *   - source line
   * @param client the client to which we are connected.
   * @return the processed specification (null, if an error occurred)
   * @throws IOException on a protocol error
   * @throws NumberFormatException on a protocol error
   ***************************************************************************/
  private static AST.Specification processSource(Connection client) 
      throws IOException, NumberFormatException
  {
    // ------------------------------------------------------------------------
    // 1. Read Message
    // ------------------------------------------------------------------------
    
    List<String> message = client.receive(2, new ArrayList<String>(2));
    if (message == null)
    {
      reportError(client, "invalid \"source\" message (two fields expected)");
      return null;
    }
    
    // nondeterministic (true/false)
    boolean nondet = parseBoolean(message.get(0));
    
    // default value
    int defvalue = Integer.parseInt(message.get(1));
    
    // specific constant definitions
    message = client.receive(message);
    if (message == null)
    {
      reportError(client, "invalid source message (constant definitions expected)");
      return null;
    }
    int cnumber = message.size()/2;
    Map<String, Integer> cmap = new LinkedHashMap<String, Integer>(cnumber);
    for (int i=0; i<cnumber; i++)
    {
      String cname = message.get(2*i);
      int cvalue = Integer.parseInt(message.get(2*i+1));
      cmap.put(cname, cvalue);
    }
    
    // the path of the file
    String path = client.receiveContent();
    
    // sequence of source lines
    List<String> lines = new ArrayList<String>(client.receive(message));
    
    // ------------------------------------------------------------------------
    // 2. Process Message
    // ------------------------------------------------------------------------
    
    // set translation flags
    Main.setNondeterministic(nondet);
    Main.setDefaultValue(defvalue);
    Main.setValueMap(cmap);
    
    // process source
    AST.Source source = new AST.Source(new File(path), lines);
    owriter = Main.getOutput();
    StringWriter swriter = new StringWriter();
    Main.setInputOutput(null, new PrintWriter(swriter));
    AST.Specification spec = Main.mainCore(source);
    Main.setInputOutput(null, owriter);
    owriter = null;
    if (spec == null)
    {
      reportError(client, "error in processing source:\n" + swriter.toString());
      return null;
    }
    
    // return result
    client.send("okay");
    return spec;
  }
  
  /****************************************************************************
   * Check an operation triggered by a message of the following form:
   * message format:
   * - function number
   * - number of inputs (-1: all)
   * - percentage of inputs (-1: full)
   * - number of nondeterministic runs (-1: all)
   * - total number of servers
   * @param funs the list of function symbols
   * @param client the client to which we are connected.
   * @param threads the number of threads to be used by the server.
   * @return true if everything went fine.
   * @throws IOException on a protocol error
   * @throws NumberFormatException on a protocol error
   ***************************************************************************/
  private static boolean checkOperation(List<FunctionSymbol> funs,
    Connection client, int threads) 
        throws IOException, NumberFormatException
  {
    // ------------------------------------------------------------------------
    // 1. Read Message
    // ------------------------------------------------------------------------ 
    
    List<String> message = client.receive(5, new ArrayList<String>(5));
    if (message == null)
    {
      reportError(client, "invalid \"check\" message (five fields expected)");
      return false;
    }
    
    // operation number
    int fnum = Integer.parseInt(message.get(0));
    
    // number of inputs
    Long number = Long.parseLong(message.get(1));
    if (number == -1) number = null;
    
    // percentage of inputs
    Long perc = Long.parseLong(message.get(2));
    if (perc == -1) perc = null;
    
    // number of nondeterministic runs
    Long runs = Long.parseLong(message.get(3));
    if (runs == -1) runs = null;
    
    // the number of servers
    int servers = Integer.parseInt(message.get(4));
    
    // ------------------------------------------------------------------------
    // 2. Process Message
    // ------------------------------------------------------------------------
    
    // determine function
    if (fnum < 0 || fnum >= funs.size())
    {
      reportError(client, "invalid function number " + fnum);
      return false;
    }
    FunctionSymbol fun = funs.get(fnum);
    
    // set checking options
    Main.setCheckNumber(number);
    Main.setCheckPercentage(perc);
    Main.setCheckRuns(runs);
    
    // execute function in server mode
    Result result = executeServer(fun, fnum, client, servers, threads);
    if (result instanceof OkayResult)
    {
      OkayResult result0 = (OkayResult)result;
      client.send("okayresult", 
          new String[] { result0.received+"", 
              result0.checked+"", result0.skipped+"", result0.ignored+"" });
    }
    else if (result instanceof ErrorResult)
    {
      ErrorResult eresult = (ErrorResult)result;
      message = new ArrayList<String>();
      message.add(eresult.task.toString());
      Connection.addLines(eresult.error, message);
      Connection.addLines(eresult.output, message);
      client.send("errorresult", message);
    }
    else if (result instanceof StopResult)
    {
      client.send("stopped");
    }
    else 
    {
      reportError(client, "unexpected kind of execution result");
      return false;
    }
    return true;
  }
  
  /***************************************************************************
   * Execute function in server mode.
   * @param fun the function.
   * @param fnum the number of the function in the list of functions.
   * @param client the client to which we are connected.
   * @param servers the number of servers
   * @param threads the number of threads to be used by the server.
   * @return the result (null, if a parallel execution is already runnning)
   **************************************************************************/
  private static Result executeServer(FunctionSymbol fun, int fnum,
    Connection client, int servers, int threads)
  {
    if (isRunning()) return null;
    RemoteSupplier supplier = new RemoteSupplier(fun, client);
    parallel = new Parallel(threads, null, servers, supplier, fnum);
    supplier.setParallel(parallel);
    Result result = parallel.run(); 
    parallel = null;
    return result;
  }
  
  // -------------------------------------------------------------------------
  //
  // A task supplier fed from a remote client
  //
  // -------------------------------------------------------------------------
  private static class RemoteSupplier implements TaskSupplier
  {
    private FunctionSymbol fun;
    private Connection client;
    private Parallel parallel;
    
    // the sequence of values locally available where
    // start is the index of the first element of the sequence
    private Seq<Value> values;
    private long start;
    
    /*************************************************************************
     * Create a supplier for a remote client.
     * @param fun the function executed by the tasks.
     * @param client the client to which we are connected.
     ************************************************************************/
    public RemoteSupplier(FunctionSymbol fun, Connection client)
    {
      this.fun = fun;
      this.client = client;
      this.parallel = null;
      this.values = Main.getValues(fun);
      this.start = 0;
    }
    
    public void setParallel(Parallel parallel) { this.parallel = parallel; }
    
    public boolean hasIds() { return false; }
    public long[] askIds(int n) { return null; }
    
    /*************************************************************************
     * Ask for new tasks.
     * @param n the desired number of tasks.
     * @param consumer the consumer of tasks.
     * @return the number of tasks delivered
     ************************************************************************/
    public int ask(int n, Consumer<Task> consumer)
    {
      try
      {
        List<String> amessage = new ArrayList<String>();
        amessage.add(n+"");
        parallel.writeStatus(amessage);
        client.send("ask", amessage);
        String tag = client.receiveTag();
        switch (tag)
        {
        case "valueseq":
          List<String> message = client.receive(2, new ArrayList<String>(2));
          if (message == null)
          {
            reportError(client, "invalid \"valueseq\" message (two fields expected)");
            return 0;
          }
          long from = Long.parseLong(message.get(0));
          int number = Integer.parseInt(message.get(1));
          if (from < start)
          {
            reportError(client, "from index " + from + 
                " less than start index " + start);
            return 0;
          }
          if (number < 0)
          {
            reportError(client, "negative number u" + number);
            return 0;
          }
          while (start < from)
          {
            Seq.Next<Value> next = values.get();
            if (next == null)
            {
              reportError(client,
                  "sequence exhausted at position " + start + " when"
                  + " proceeding to value " + from);
              return 0;
            }
            values = next.tail;
            start++;
          }
          int tasks = 0;
          for (int i=0; i<number; i++)
          {
            Seq.Next<Value> next = values.get();
            if (next == null)
            {
              reportError(client,
                  "sequence exhausted at position " + start + " when"
                  + " acquiring value " + from);
              return 0;
            }
            consumer.accept(newTask(fun, next.head));
            values = next.tail;
            start++;
            tasks++;
          }
          return tasks;
        case "valuelist": 
          /*
          String message0 = client.receiveContent();
          int number0 = Integer.parseInt(message0);
          for (int i=0; i<number0; i++)
          {
            message0 = client.receiveContent();
            Value value = parseValue(message0);
            consumer.accept(newTask(fun, value));
          }
          return number0;
          */
          String message0 = client.receiveContent();
          List<String> message1 = uncompress(message0);
          int number0 = message1.size();
          for (int i=1; i<number0; i++)
          {
            message0 = message1.get(i);
            Value value = parseValue(message0);
            consumer.accept(newTask(fun, value));
          }
          return number0-1;
        case "valuenone":
          return 0;
        case "stop":
          parallel.setStopped();
          return 0;
        case "terminate":
          parallel.setStopped();
          client.send("terminated");
          return 0;
        default:
          reportError(client, "unknown answer \"" + tag + "\" when asking for values");
          return 0;
        }
      }
      catch(Exception e)
      {
        reportError(client, "exception when asking for values: " + e.getMessage());
        return 0;
      }
    }
    
    /*************************************************************************
     * Report progress.
     * @param checked the number of checked tasks
     ************************************************************************/
    public void reportProgress(long checked) { }
  }
  
  // ------------------------------------------------------------------------
  // 
  // a thread handling the connection to a remote server
  //
  // ------------------------------------------------------------------------
  private static class ServerProxy extends Thread
  {
    private Server server;
    private int snumber;
    private int fnum;
    private TaskSupplier supplier;
    private Parallel parallel;
    
    /**************************************************************************
     * Create a proxy thread handling a server connection for a function check.
     * @param server the server.
     * @param snumber the total number of servers.
     * @param fnum the number of the function to be checked.
     * @param supplier the supplier of checking tasks for that function.
     * @param parallel the parallelism coordinator.
     *************************************************************************/
    public ServerProxy(Server server, int snumber, 
      int fnum, TaskSupplier supplier, Parallel parallel)
    {
      this.server = server;
      this.snumber = snumber;
      this.fnum = fnum;
      this.supplier = supplier;
      this.parallel = parallel;
    }
    public void run()
    {
      Long cnum = Main.getCheckNumber();
      if (cnum == null) cnum = -1L;
      Long perc = Main.getCheckPercentage();
      if (perc == null) perc = -1L;
      Long runs = Main.getCheckRuns();
      if (runs == null) runs = -1L;
      server.connection.send("check", new String[] {
          fnum+"", cnum+"", perc+"", runs+"", snumber+""});
      while(true)
      {
        while (!server.connection.canRead())
        {
          try { Thread.sleep(100); } catch(InterruptedException e) { }
          if (parallel.hasResult()) 
          {
            server.connection.send("stop");
            while (true)
            {
              String line = server.connection.receiveTag();
              if (line == null || line.equals("stopped")) return;
            }
          }   
        }
        String tag = server.connection.receiveTag();
        switch (tag)
        {
        case "ask":
          String line = server.connection.receiveContent();
          if (line == null)
          {
            parallel.setFailure(server, "ask message without number", "");
            return;
          }
          Integer number = Main.toInteger(line);
          if (number == null)
          {
            parallel.setFailure(server, "ask message with invalid number \"" + 
                line + "\"", "");
            return;
          }
          List<String> amessage = server.connection.receive(4, new ArrayList<String>(4));
          if (amessage == null)
          {
            parallel.setFailure(server, "invalid \"ask\" message (four more fields expected)", "");
            return;
          }
          Long rn = Main.toLong(amessage.get(0));
          Long cn = Main.toLong(amessage.get(1));
          Long sn = Main.toLong(amessage.get(2));
          Long in = Main.toLong(amessage.get(3));
          if (rn == null || cn == null || sn == null || in == null)
          {
            parallel.setFailure(server, "invalid \"ask\" message: " + 
                rn + " " + sn + " " + cn + " " + in, "");
            return;
          }
          parallel.partialResult(rn,cn,sn,in);
          if (supplier.hasIds())
          {
            long ids[] = supplier.askIds(number);
            if (ids == null) 
            {
              server.connection.send("valuenone");
            }
            else
            {
              long from = ids[0];
              long number0 = ids[1];
              server.connection.send("valueseq", new String[] { from+"", number0+"" });
            }
          }
          else
          {
            List<String> message = new ArrayList<String>();
            message.add(null);
            int tasks = supplier.ask(number,new Consumer<Task>() {
              public void accept(Task task)
              {
                Value[] values = task.arg.values;
                Value value = values.length == 1 ? 
                    values[0] : new Value.Array(values);
                message.add(value.toString());
              }});
            if (tasks == 0) 
              server.connection.send("valuenone");
            else
            {
              message.set(0, tasks+"");
              // server.connection.send("valuelist", message);
              server.connection.send("valuelist", new String[] { compress(message) });
            }
          }
          break;
        case "okayresult":
          List<String> message = server.connection.receive(4, new ArrayList<String>(4));
          if (message == null)
          {
            parallel.setFailure(server, "invalid \"okayresult\" message (four fields expected)", "");
            return;
          }
          Long rn0 = Main.toLong(message.get(0));
          Long cn0 = Main.toLong(message.get(1));
          Long sn0 = Main.toLong(message.get(2));
          Long in0 = Main.toLong(message.get(3));
          if (rn0 == null || cn0 == null || sn0 == null || in0 == null)
          {
            parallel.setFailure(server, "invalid \"okayresult\" message: " + 
                rn0 + " " + sn0 + " " + cn0 + " " + in0, "");
            return;
          }
          parallel.partialResult(rn0,cn0,sn0,in0);
          parallel.setDone();
          return;
        case "errorresult":
          List<String> emessage = server.connection.receive(1, new ArrayList<String>(1));
          if (emessage == null)
          {
            parallel.setFailure(server, "invalid \"errorresult\" message (one field expected)", "");
            return;
          }
          String task = emessage.get(0);
          emessage = server.connection.receive(emessage);
          if (emessage == null)
          {
            parallel.setFailure(server, "invalid \"errorresult\" message (error description expected)", "");
            return;
          }
          String error = Parallel.toString(emessage);
          emessage = server.connection.receive(emessage);
          if (emessage == null)
          {
            parallel.setFailure(server, "invalid \"errorresult\" message (output description expected)", "");
            return;
          }
          String output = Parallel.toString(emessage);
          parallel.setError(task, error, output);
          return;
        case "error":
          List<String> errors = server.connection.receive(new ArrayList<String>());
          parallel.setFailure(server, "error received from server", 
              Parallel.toString(errors));
          return;
        case "timeout":
          parallel.setFailure(server, "timeout received from server", "");
          return;
        default:
          parallel.setFailure(server, "unexpected message tag \"" + tag + "\"", "");
          return;
        }
      }
    }
  }
  
  /****************************************************************************
   * Parse text to a value.
   * @param text the text.
   * @return the value (null on parsing error)
   ***************************************************************************/
  public static Value parseValue(String text)
  {
    try
    {
      ANTLRInputStream input = new ANTLRInputStream(text);
      ValueLexer lexer = new ValueLexer(input);
      TokenStream tokens = new CommonTokenStream(lexer);
      ValueParser parser = new ValueParser(tokens);
      ValueParser.ValueFileContext context = parser.valueFile();
      return new ValueTranslator().visit(context);
    }
    catch(Exception e)
    {
      reportError(null, "parse error: " + e.getMessage());
      return null;
    }
  }
  
  /****************************************************************************
   * Compress a message (a sequence of lines) into a single printable line.
   * @param message a sequence of lines.
   * @return a single printable line (no line feeds within).
   ***************************************************************************/
  private static String compress(List<String> message)
  {
    try
    {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      DeflaterOutputStream gzip = 
          new DeflaterOutputStream(Base64.getEncoder().wrap(out),
              new Deflater(Deflater.BEST_SPEED));
      for (String line : message)
      {
        gzip.write((line+"\n").getBytes("UTF-8"));
      }
      gzip.close();
      return out.toString("UTF-8");
    }
    catch(Exception e)
    {
      Main.error("Exception in compress(): " + e.getMessage(), true);
      return null;
    }
  }
  
  /****************************************************************************
   * Uncompress a single printable line to a message (a sequence of lines).
   * @param string a single printable line (no line feeds within).
   * @return a message (a sequence of lines)
   ***************************************************************************/
  private static List<String> uncompress(String string)
  {
    try
    {
      ByteArrayInputStream in = new ByteArrayInputStream(string.getBytes("UTF-8"));
      InflaterInputStream gzip = new InflaterInputStream(Base64.getDecoder().wrap(in));
      BufferedReader reader = new BufferedReader(new InputStreamReader(gzip, "UTF-8"));
      List<String> message = new ArrayList<String>();
      while (true)
      {
        String line = reader.readLine();
        if (line == null) break;
        message.add(line);
      }
     return message;
    }
    catch(Exception e)
    {
      System.out.println("uncompress: " + e.getMessage());
      // Main.error("Exception in uncompress(): " + e.getMessage(), true);
      return null;
    }
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------