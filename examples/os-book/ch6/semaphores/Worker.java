/**
 * Worker.java
 * 
 * This thread is used to demonstrate the operation of a semaphore.
 * 
 * Figure 6.8
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */
// import java.util.concurrent.Semaphore;

public class Worker implements Runnable {

  private Semaphore sem;

  private String name;

  public Worker( String name, Semaphore sem ) {
    this.name = name;
    this.sem = sem;
  }

  public void run() {
    while ( true ) {
      System.out.printf( "> %s wants to enter critical section\n", name );
      sem.acquire();
      System.out.printf( "* %s IS IN CRITICAL SECTION\n", name );     
      MutualExclusionUtilities.criticalSection( name );
      System.out.printf( "< %s is exiting critical section\n", name );     
      sem.release();
      MutualExclusionUtilities.remainderSection( name );
    }
  }
}
