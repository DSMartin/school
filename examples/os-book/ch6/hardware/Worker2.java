// import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Worker.java
 *
 * This is a thread that is used to demonstrate solutions
 * to the critical section problem using the test-and-set instruction.
 *
 */

public class Worker2 implements Runnable {

  private String name;    // the name of this thread
  private HardwareData mutex;   // shared mutex
  
  public Worker2( String name, HardwareData mutex ) {
    this.name = name;
    this.mutex = mutex;
  }
  
  /**
   * This run() method tests the swap() instruction
   */ 
  public void run() {
    HardwareData key = new HardwareData( true );
    while ( true ) {
      System.out.printf( "> %s wants to enter critical section\n", name );
      key.set( true );
      do {
        // JFM: there is a problem lurking here
        mutex.swap( key );
      } while( key.get() == true );
      System.out.printf( "* %s IS IN CRITICAL SECTION\n", name );     
      MutualExclusionUtilities.criticalSection( name );
      System.out.printf( "< %s is exiting critical section\n", name );     
      mutex.set( false );
      MutualExclusionUtilities.remainderSection( name );
    }
  }
}
