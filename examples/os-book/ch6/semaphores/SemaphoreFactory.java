/**
 * SemaphoreFactory.java
 *
 * Figure 6.8
 *
 * This program uses a semaphore as a means of handling synchronization.
 * It creates 5 threads and a thread can perform its critical section
 * only if it is able to complete a acquire() operation on the sempahore.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */
// import java.util.concurrent.Semaphore;

public class SemaphoreFactory {
  public static void main( String args[] ) {
    Semaphore sem = new Semaphore( 1 );
    Thread[] bee = new Thread[5];
    for ( int i = 0; i < 5; i++ )
      bee[i] = 
        new Thread(
          new Worker( 
            String.format( "Worker %d", i), 
            sem ) );
    for ( int i = 0; i < 5; i++ )
      bee[i].start();
  }
}

