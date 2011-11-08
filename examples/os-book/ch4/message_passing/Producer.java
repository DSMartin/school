/**
 * The producer class
 *
 * Figure 4.14
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.Date;

class Producer implements Runnable {
  
  private Channel<Date> queue;
  
  public Producer( Channel<Date> queue ) {
    this.queue = queue;
  }
  
  @SuppressWarnings("unchecked")
  public void run() {
    Date message;
    while ( true ) { 
      SleepUtilities.nap();   
      message = new Date();  // produce an item 
      System.out.println( "Producer produced " + message );
      queue.send( message );   // enter it into the buffer
    }
  }
}
