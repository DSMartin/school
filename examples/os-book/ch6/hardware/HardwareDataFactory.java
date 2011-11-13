/**
 * HardwareDataFactory.java
 *
 * This program tests the getAndSet or swap instructions.
 */

public class HardwareDataFactory {
  public static void main( String args[] ) {
    HardwareData lock = new HardwareData( false );
    Thread[] worker = new Thread[5];
    for ( int i = 0; i < 5; i++ ) {
      worker[i] = new Thread( 
                    new Worker( 
                      String.format( "Worker %d", i ), 
                      lock ) );
      worker[i].start();
    }
  }
}
