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

  private String name;
  private ReadWriteLock db;
  
  public Writer( String name, ReadWriteLock db ) {
    this.name = name;
    this.db = db;
  }
  
  public void run() {
    while ( true ) {
      SleepUtilities.nap();
      System.out.printf( "> %s wants to write\n", name );
      db.acquireWriteLock();
      System.out.printf( "* %s is WRITING\n", name );
      // you have access to write to the database
      // write for awhile ...
      SleepUtilities.nap();
      System.out.printf( "< %s has written\n", name );
      db.releaseWriteLock();
    }
  }
}
