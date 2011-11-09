/**
 * This program creates a separate thread by implementing the Runnable interface.
 *
 * Figure 4.11
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */


class Sum {

  private int sum;

  public int get() {
    return sum;
  }

  public void set( int sum ) {
    this.sum = sum;
  }
}

class Summation implements Runnable {

  private int upper;
  private Sum sumValue;

  public Summation( int upper, Sum sumValue ) {
    this.upper = upper;
    this.sumValue = sumValue;
  }

  public void run() {
    int sum = 0;
    for ( int i = 0; i <= upper; i++ )
      sum += i;
    sumValue.set( sum );
  }
}

public class Driver {

  public static void main( String[] args ) {
    if ( args.length != 1 ) {
      System.err.println( "Usage Driver <integer>" );
      System.exit( 0 );
    }
    int upper = Integer.parseInt( args[0] );
    if ( upper < 0 ) {
      System.err.printf( "Argument %d must be non-negative\n", upper ) ;
      System.exit( 0 );
    }
    // Create the shared object
    Sum sumObject = new Sum();
    
    // Spawn a thread to do the work, passing it a Summation runnable instance
    Thread worker = new Thread( new Summation( upper, sumObject ) );
    worker.start();
      
    try {
      worker.join();
    } 
    catch ( InterruptedException e ) { 
    }
    System.out.printf( "The sum of %d is %d\n", upper, sumObject.get() );
  }
}
