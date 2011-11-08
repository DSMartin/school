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
      System.out.println( name + " wants to enter CS" );
      key.set( true );
      do {
        // JFM: there is a problem lurking here
        mutex.swap( key );
      } while( key.get() == true );
      MutualExclusionUtilities.criticalSection( name );
      System.out.println( name + " is out of critical section" );     
      mutex.set( false );
      MutualExclusionUtilities.remainderSection( name );
    }
  }
}
