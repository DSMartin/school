/**
 * BoundedBuffer.java
 * 
 * This program implements the bounded buffer using Java synchronization.
 *
 * Figure 6.31
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 * 
 */
@SuppressWarnings("unchecked")

public class BoundedBuffer<E> implements Buffer<E> {

  private static final int BUFFER_SIZE = 1; // was 5
  
  // no Semaphores or other synchronized objects
  // using synchronized methods instead ... making this a monitor class
  
  private int count;  // number of items in the buffer
  private int in;     // index of next free position in the buffer (where to insert to)
  private int out;    // index of next full position in the buffer (where to remove from)
  private E[] buffer;

  public BoundedBuffer() {
    // buffer is initially empty
    count = 0;
    in = 0;
    out = 0;
    buffer = (E[]) new Object[BUFFER_SIZE];
  }

  public synchronized void insert(E item) {
    while ( count == BUFFER_SIZE ) {
      try {
        // add me to the wait set for this monitor 
        // (await notification from another thread)
        wait(); 
      } 
      catch ( InterruptedException e ) { 
      }
    }
    // add an item to the buffer
    buffer[in] = item;
    in = ( in + 1 ) % BUFFER_SIZE;
    ++count;
    System.out.printf( "* Producer INSERTING '%s'; Buffer Size = %d%s\n", 
                       item, count, ( count == BUFFER_SIZE ? " (now FULL)" : "" ) );
    // done ... now wake up a thread in the wait set for this monitor
    notify(); 
  }

  // consumer calls this method
  public synchronized E remove() {
    E item;
    while ( count == 0 ) {
      try {
        // add me to the wait set for this monitor 
        // (await notification from another thread)
        wait(); 
      } 
      catch ( InterruptedException e ) {
      }
    }
    // remove an item from the buffer
    item = buffer[out];
    out = ( out + 1 ) % BUFFER_SIZE;
    --count;
    System.out.printf( "* Consumer REMOVING  '%s'; Buffer Size = %d%s\n", 
                       item, count, ( count == 0 ? " (now EMPTY)" : "" ) );    
    // done ... now wake up a thread in the wait set for this monitor
    notify(); 
    return item;
  }
}
