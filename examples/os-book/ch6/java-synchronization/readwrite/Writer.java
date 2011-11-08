/**
 * Writer.java
 *
 * A writer to the database.
 *
 * Figure 6.16
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

public class Writer implements Runnable {
  private ReadWriteLock server;
  private int writerNum;
  
  public Writer( int w, ReadWriteLock db ) {
    writerNum = w;
    server = db;
  }
  
  public void run() {
    while ( true ) {
      SleepUtilities.nap();
      System.out.printf( "Writer %d wants to write.\n", writerNum );
      server.acquireWriteLock(writerNum);
      // you have access to write to the database
      // write for awhile ...
      SleepUtilities.nap();
      server.releaseWriteLock( writerNum );
    }
  }
}
