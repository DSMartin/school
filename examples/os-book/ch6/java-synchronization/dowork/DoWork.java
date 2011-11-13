/**
 * DoWork.java
 * 
 * This method is used to distinguish between the notify() and notifyAll()
 * methods. Run the program using notify(). The program will shortly hang as the
 * incorrect thread (i.e. the thread whose turn is not next!) receives the
 * notification. After that, change the call to notify() to notifyAll(). The
 * program should run correctly using notifyAll().
 *
 * Figure 6.32
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 * 
 */

public class DoWork {
  private int turn = 1;

  // myNumber is the number of the thread that wishes to do some work
  public synchronized void doWork( int myNumber ) {
    System.out.printf( "> Worker %d wants to enter critical section\n", myNumber );
    while ( turn != myNumber ) {
      try {
        System.out.printf( "- Worker %d will wait\n", myNumber );
        wait();
        System.out.printf( "! Worker %d has been notified\n", myNumber );
      } catch (InterruptedException e) { 
      }
    }   
    // do some work for awhile
    System.out.printf( "* Worker %d IS IN CRITICAL SECTION\n", myNumber );     
    SleepUtilities.nap();
    
    // ok, we're finished. Now indicate to the next waiting
    // thread that it is their turn to do some work.
    System.out.printf( "< Worker %d is exiting critical section\n", myNumber );     
    
    turn = (turn + 1) % 5;    
    // change this to notifyAll() to see it run correctly!
    notify();
  }
}
