/**
 * This is the producer thread for the bounded buffer problem.
 *
 * Figure 6.12
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */


import java.util.Date;

public class Producer implements Runnable {

  private Buffer<Date> buffer;
  
  public Producer( Buffer<Date> buffer ) {
    this.buffer = buffer;
  }
  
  public void run() {
    Date message;
    while ( true ) {
      SleepUtilities.nap();
      // produce an item & enter it into the buffer
      message = new Date();      
      System.out.printf( "> Producer wants to insert\n" );
      buffer.insert( message );
      System.out.printf( "< Producer has inserted\n" );
    }
  }
}
