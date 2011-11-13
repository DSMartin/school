/**
 * This is the consumer thread for the bounded buffer problem.
 *
 * Figure 6.13
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.*;

public class Consumer implements Runnable {

  private Buffer<Date> buffer;  // was <Date>

  public Consumer( Buffer<Date> buffer ) { 
    this.buffer = buffer;
  }
   
  public void run() {
    Date message;
    while ( true ) {
      SleepUtilities.nap();
      // consume an item from the buffer
      System.out.printf( "> Consumer wants to consume\n" );
      message = buffer.remove();
      System.out.printf( "< Consumer has consumed '%s'\n", message );
    }
  }   
}


