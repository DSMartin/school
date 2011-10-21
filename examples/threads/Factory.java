/**
 * Factory class that creates the MessageQueue class and
 * the producer and consumer threads. 
 *
 * Figure 4.13
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.Date;

public class Factory {
  public static void main( String[] args ) {
    // create the message queue
    Channel<Date> queue = new MessageQueue<Date>();
    // create the producer and consumer threads
    Thread producer = new Thread( new Producer(queue) );
    Thread consumer1 = new Thread( new Consumer(queue, 1) );
    Thread consumer2 = new Thread( new Consumer(queue, 2) );
    // start the threads
    producer.start();
    consumer1.start();
    consumer2.start();
  }
}
