/**
 * Database.java
 *
 * This class contains the methods the readers and writers will use
 * to coordinate access to the database. Access is coordinated using semaphores.
 *
 * Figure 6.18
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010. 
 */

// Using Java built-in Semaphore class
// Semaphore.acquire() must be called within try / catch ( InterruptedException e )

import java.util.concurrent.Semaphore;

public class Database implements ReadWriteLock {
  // the number of active readers
  private int readerCount;
  
  Semaphore mutex;  // controls access to readerCount
  Semaphore db;     // controls access to the database
  
  public Database() {
    readerCount = 0;
    mutex = new Semaphore( 1 );
    db = new Semaphore( 1 );
  }
  
  public void acquireReadLock() {
    try {
      mutex.acquire();
    }
    catch ( InterruptedException e ) {
    }
    ++readerCount;
    // if I am the first reader, tell all others
    // that the database is being read
    if ( readerCount == 1 )
      try {
        db.acquire();
      }
      catch ( InterruptedException e ) {
      }
    mutex.release();
  }
  
  public void releaseReadLock() {
    try {
      mutex.acquire();
    }
    catch ( InterruptedException e ) {
    }
    --readerCount;
    // if I am the last reader, tell all others
    // that the database is no longer being read
    if ( readerCount == 0 )
      db.release();    
    mutex.release();
  }
  
  public void acquireWriteLock() {
    try {
      db.acquire();
    }
    catch ( InterruptedException e ) {
    }
  }
  
  public void releaseWriteLock() {
    db.release();
  }
}
