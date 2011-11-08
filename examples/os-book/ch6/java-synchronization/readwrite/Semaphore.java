/**
 * Semaphore.java
 *
 * A basic counting semaphore using Java synchronization.
 */

public class Semaphore {

  private int value;

  public Semaphore( int initialValue ) {
    this.value = initialValue;
  }

  public synchronized void acquire() {
    while ( value <= 0 ) {
      try {
        wait();
      } catch ( InterruptedException e ) { 
      }
    }
    value--;
  }

  public synchronized void release() {
     ++value;
     notify();
  }
}
