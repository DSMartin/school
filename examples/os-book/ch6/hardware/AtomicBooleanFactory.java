/**
 * GetAndSetFactory.java
 *
 * This program tests the get-and-set instructions.
 */
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanFactory {
  public static void main( String args[] ) {
    AtomicBoolean lock = new AtomicBoolean( false );
    Thread[] worker = new Thread[5];
    for ( int i = 0; i < 5; i++ ) {
      worker[i] = new Thread( 
                    new Worker1a( 
                      String.format( "Worker %d", i ), 
                      lock ) );
      worker[i].start();
    }
  }
}
