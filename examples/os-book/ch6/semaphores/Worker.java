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
      sem.acquire();
      System.out.println( name + " is in critical section" );
      MutualExclusionUtilities.criticalSection( name );
      System.out.println( name + " is out of critical section" );
      sem.release();
      MutualExclusionUtilities.remainderSection( name );
    }
  }
}
