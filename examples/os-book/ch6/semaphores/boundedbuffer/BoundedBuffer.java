/**
 * BoundedBuffer.java
 *
 * Figure 6.9
 *
 * This program implements the bounded buffer with semaphores.
 * Note that the use of count only serves to output whether
 * the buffer is empty of full.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */


import java.util.*;

@SuppressWarnings("unchecked")

public class BoundedBuffer<E> implements Buffer<E> {
  
  private static final int BUFFER_SIZE = 1;   // was 5
  
  private Semaphore mutex;
  private Semaphore empty;
  private Semaphore full;
  
  private int count;  // number of items in the buffer
  private int in;     // index of next free position in the buffer (where to insert to)
  private int out;    // index of next full position in the buffer (where to remove from)
  private E[] buffer;
  
  public BoundedBuffer() {
    // buffer is initially empty
    count  = 0;
    in     = 0;
    out    = 0;
    buffer = (E[]) new Object[BUFFER_SIZE];
    mutex  = new Semaphore( 1 );            // at most 1 thread can modify buffer at any time
    full   = new Semaphore( 0 );            // no buffer cells are full (initially)
    empty  = new Semaphore( BUFFER_SIZE );  // all buffer cells are empty (initially)
  }
  
  // producer calls this method
  public void insert( E item ) {
    empty.acquire();
    mutex.acquire();
    // add an item to the buffer
    buffer[in] = item;
    in = (in + 1) % BUFFER_SIZE;
    ++count;
    System.out.printf( "* Producer INSERTING '%s'; Buffer Size = %d%s\n", 
                       item, count, ( count == BUFFER_SIZE ? " (now FULL)" : "" ) );    
    mutex.release();
    full.release();
  }
  
  // consumer calls this method
  public E remove() {
    full.acquire();
    mutex.acquire();
    // remove an item from the buffer
    E item = buffer[out];
    out = (out + 1) % BUFFER_SIZE;
    --count;
    System.out.printf( "* Consumer REMOVING  '%s'; Buffer Size = %d%s\n", 
                       item, count, ( count == 0 ? " (now EMPTY)" : "" ) );    
    mutex.release();
    empty.release();
    return item;
  }
}
