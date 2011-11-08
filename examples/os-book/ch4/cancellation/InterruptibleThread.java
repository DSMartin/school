/**
 * An example program illustrating thread interruption
 *
 * Figure 4.16
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

public class InterruptibleThread implements Runnable {
  /**
   * This thread will continue to run as long
   * as it is not interrupted.
   */
  private int threadNum;
  public InterruptibleThread( int threadNum ) { 
    this.threadNum = threadNum;
  }
  public void run() {
    while ( true ) {
      // do some work for a while, periodically checking for interrupts
      System.out.printf( "Thread %d working\n", threadNum );
      try { 
        Thread.sleep( 500 ); 
      } 
      catch ( InterruptedException e ) { 
        System.out.printf( "Thread %d interrupted\n", threadNum );
        break;
      } 
      // JFM: original code checked for interruptions via isInterrupted()
      // Leaving it here, but the InterruptedException catch above will clear the flag
      // so the if clause below is unlikely to be false 
      // (unless interrupt is generated between end of try/catch and start of if)
      if ( Thread.currentThread().isInterrupted() ) {
        System.out.println( "I'm interrupted!" );
        break;
      }
    }
    System.out.printf( "Thread %d cleaning up and terminating\n", threadNum );
  }

  public static void main( String[] args ) {
    Thread worker1 = new Thread( new InterruptibleThread( 1 ) );
    Thread worker2 = new Thread( new InterruptibleThread( 2 ) );
    worker1.start();
    worker2.start();
    //  wait 2 seconds before interrupting worker
    try { 
      Thread.sleep( 2000 ); 
    } 
    catch ( InterruptedException e ) { 
    }
    // Use deferred cancellation with worker1, allowing it to terminate itself in an orderly fashion
    worker1.interrupt();
    // Use asynchronous cancellation with worker2, immediately terminating it
    // NOTE: stop() has been deprecated & should be avoided (except for demos)
    // http://download.oracle.com/javase/6/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
    worker2.stop(); 
  }
}
