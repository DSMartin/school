/**
 * Reader.java
 *
 * A reader to the database.
 *
 * Figure 6.15
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */


public class Reader implements Runnable {
  
  private String name;
  private ReadWriteLock db;
  
  public Reader( String name, ReadWriteLock db ) {
    this.name = name;
    this.db = db;
  }
  
  public void run() {
    while ( true ) {
      SleepUtilities.nap();
      System.out.printf( "> %s wants to read\n", name );
      db.acquireReadLock();
      System.out.printf( "+ %s is READING\n", name );
      // you have access to read from the database
      // let's read for awhile .....
      SleepUtilities.nap();
      System.out.printf( "< %s has read\n", name );
      db.releaseReadLock();
    }
  }
}
