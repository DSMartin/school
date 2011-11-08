/**
 * DoWork.java
 *
 * Figure 6.38
 *
 * This implements the doWork() method using condition variables.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010. 
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


public class DoWork {
  private int turn;
  private Lock lock;
  private Condition[] condVars;

  public DoWork() {
    turn = 0;
    lock = new ReentrantLock();
    condVars = new Condition[5];
    for ( int i = 0; i < 5; i++ )
      condVars[i] = lock.newCondition();
  }

  // myNumber is the number of the thread that wishes to do some work
  public void doWork( int myNumber ) {
    System.out.printf( "> Worker %d wants to work\n", myNumber );
    lock.lock();
    try {
      // if it's not my turn, I'll wait until I'm signaled
      if ( myNumber != turn ) {
        System.out.printf( "- Worker %d will wait\n", myNumber );
        condVars[myNumber].await();
      }
      // I've been signaled, will do some work for awhile
      System.out.printf( "* Worker %d is working\n", myNumber );
      SleepUtilities.nap();
      // I'm done; I'll signal that it's the next worker's turn
      System.out.printf( "< Worker %d is resting\n", myNumber );
      turn = (turn + 1) % 5;
      condVars[turn].signal();
    }
    catch ( InterruptedException e ) { 
    }
    finally {
      lock.unlock();
    }
  }
}
