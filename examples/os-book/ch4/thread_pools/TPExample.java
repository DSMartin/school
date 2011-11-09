/**
 * A example program illustrating a cached thread pool
 *
 * Figure 5.20
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 * 
 * JFM: made a few changes:
 *   program executes tasks from thread pool, then from separate threads
 *   times for launching tasks are computed and shown
 *   program takes two arguments: # of tasks for thread pool, # of tasks for separate threads
 * More ideas for measuring & manipulating thread pools might be found in the documentation:
 *   http://download.oracle.com/javase/6/docs/api/java/util/concurrent/ThreadPoolExecutor.html
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.Date;

public class TPExample {

  private static final int MAX_TASKS = 5;

  public static void main( String[] args ) {
    int numTasksPool = MAX_TASKS;
    int numTasksSeparate = MAX_TASKS;
    Date startTime1, midTime1, endTime1, startTime2, endTime2;
    long taskTime1 = 0, poolTime1 = 0, taskTime2 = 0;
    if ( args.length > 0 )
      numTasksPool = Integer.parseInt( args[0].trim() ); 
    if ( args.length > 1 )
      numTasksSeparate = Integer.parseInt( args[1].trim() ); 
    else
      numTasksSeparate = numTasksPool;
      
    // JFM: compare execution time with threadpools 
    //   vs separately creating individual threads for each task
    
    if ( numTasksPool > 0 ) {
      System.out.printf( "Running %d tasks from thread pool\n\n", numTasksPool );
      startTime1 = new Date();
      // JFM: using Fixed vs Cached as we know the # of threads
      ExecutorService pool = Executors.newFixedThreadPool( numTasksPool );
      // run each task using a thread in the pool
      midTime1 = new Date();
      for ( int i = 1; i <= numTasksPool; i++ )
        try {
          pool.execute( new Task( i ) );
        }
        catch ( RejectedExecutionException e ) {
          System.out.printf( "\nRejectedExecutionException at task %d\n", i );
          break;
        }
        catch ( OutOfMemoryError e ) {
          System.out.printf( "\nOutOfMemoryError at task %d\n", i );
          break;
        }
      pool.shutdown();
      endTime1 = new Date();
      poolTime1 = midTime1.getTime() - startTime1.getTime();
      taskTime1 = endTime1.getTime() - midTime1.getTime();
    }
    
    if ( numTasksSeparate > 0 ) {
      System.out.printf( "\nRunning %d tasks in separate threads\n\n", numTasksSeparate );
      startTime2 = new Date();
      for ( int i = 1; i <= numTasksSeparate; i++ )
        try {
          new Thread( new Task( i ) ).start();
        }
        catch ( OutOfMemoryError e ) {
          System.out.printf( "\nOutOfMemoryError at task %d\n", i );
          break;
        }
      endTime2 = new Date();
      taskTime2 = endTime2.getTime() - startTime2.getTime();
    }
    
    if ( numTasksPool > 0 )
      System.out.printf( "\nTime for using %d threads from pool:   %5d ms (+ %d ms to create pool)\n", 
                         numTasksPool, taskTime1, poolTime1 );
    if ( numTasksSeparate > 0 )
      System.out.printf( "\nTime for creating %d separate threads: %5d ms\n", 
                         numTasksSeparate, taskTime2 );
  }
}

class Task implements Runnable {
  private int taskNum;
  public Task( int taskNum ) {
    this.taskNum = taskNum;
  }
  public void run() {
    System.out.printf( ".", taskNum );
    if ( taskNum % 50 == 0 )
      System.out.printf( " [%5d]\n", taskNum );
  }
}
