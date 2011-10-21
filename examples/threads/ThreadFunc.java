public class ThreadFunc extends Thread {
  private String param;
  public ThreadFunc( String param ) {
    this.param = param;
  }
  public void run( ) {
    for ( int i = 0; i < 5; i++ ) {
      try {
        Thread.sleep( 2000 );
      } catch ( InterruptedException e ) { 
      };
      System.out.println( "I'm a slave: " + param );
    }
  }
}
