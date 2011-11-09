/**
 * Factory.java
 *
 * This class creates the reader and writer threads and
 * the database they will be using to coordinate access.
 */


public class Factory {
  public static final int NUM_READERS = 3;
  public static final int NUM_WRITERS = 2;

  public static void main( String args[] ) {
    ReadWriteLock db = new Database();
    Thread[] readerArray = new Thread[NUM_READERS];
    Thread[] writerArray = new Thread[NUM_WRITERS];

    for ( int i = 0; i < NUM_READERS; i++ ) {
      readerArray[i] = new Thread( new Reader( "Reader " + i, db ) );
      readerArray[i].start();
    }

    for ( int i = 0; i < NUM_WRITERS; i++ ) {
      writerArray[i] = new Thread( new Writer( "Writer " + i, db ) );
      writerArray[i].start();
    }
  }
}
