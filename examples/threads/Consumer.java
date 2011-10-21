/**
 * The consumer
 *
 * Figure 4.15
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.Date;

class Consumer implements Runnable {
  private Channel<Date> queue;
    private int consumerId;
    public Consumer(Channel<Date> queue, int consumerId) { 
    this.queue = queue;
    this.consumerId = consumerId;
  }
  public void run() {
    Date message;
    while (true) {
      SleepUtilities.nap();
      // consume an item from the buffer
      System.out.println("Consumer " + consumerId + " wants to consume.");
      message = queue.receive();  // consume an item from buffer
      if (message != null)
        System.out.println("Consumer " + consumerId + " consumed " + message);
    }
  }
}
