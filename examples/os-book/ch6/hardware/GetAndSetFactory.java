/**
 * GetAndSetFactory.java
 *
 * This program tests the get-and-set instructions.
 */

public class GetAndSetFactory {
  public static void main( String args[] ) {
    HardwareData lock = new HardwareData( false );
    Thread[] worker = new Thread[5];
    for ( int i = 0; i < 5; i++ ) {
      worker[i] = new Thread( 
                    new Worker1( 
                      String.format( "worker %d", i ), 
                      lock ) );
      worker[i].start();
    }
  }
}
