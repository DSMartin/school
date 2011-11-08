/**
 * A example program illustrating a cached thread pool
 *
 * Figure 5.20
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.concurrent.ExecutorService;

public class TPExample {
  private static final int MAXTASKS = 5;
  public static void main( String[] args ) {
    int numTasks = MAXTASKS;
    if ( args.length > 0 )
      numTasks = Integer.parseInt( args[0].trim() );
    
    // create the thread pool
    ExecutorService pool = java.util.concurrent.Executors.newCachedThreadPool();

    // run each task using a thread in the pool
    for ( int i = 0; i < numTasks; i++ )
      pool.execute( new Task( i ) );
    // sleep for 5 seconds
    try { 
      Thread.sleep( 5000 ); 
    } 
    catch ( InterruptedException e ) { 
    }
    pool.shutdown();
  }
}


class Task implements Runnable {
  private int taskNum;
  public Task( int taskNum ) {
    this.taskNum = taskNum;
  }
  public void run() {
    System.out.printf( "Task %d running\n", taskNum );
  }
}
