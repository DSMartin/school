/**
 * A simple program which tests the Buffer implementation.
 */

public class Test {
  public static void main( String[] args ) {
    Buffer<String> buffer = new BufferImpl<String>();

    // Aho, Weinberger & Kernighan are the authors of 
    // the awk programming language (& Unix/Linux utility program)
    buffer.insert( "Aho" );
    buffer.insert( "Weinberger" );
    buffer.insert( "Kernighan" );

    for ( int i = 0; i <= 2; i++ )
      System.out.println( buffer.remove() );
  }
}
