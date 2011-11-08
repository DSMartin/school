/**
 * TestIt.java
 *
 * This program creates the threads that are used to demonstrate
 * the difference between notify() and notifyAll().
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

public class TestIt {
  public static void main(String args[]) {
    DoWork pile = new DoWork();
    Thread[] bees = new Thread[5];
    for ( int i = 0; i < 5; i++ )
      bees[i] = 
        new Thread( 
          new Worker( pile, String.format( "Worker %d", i ), i ) );
    for ( int i = 0; i < 5; i++ )
      bees[i].start();
  }
}

