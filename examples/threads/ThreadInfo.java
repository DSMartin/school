public class ThreadInfo {

  class SimpleThread extends Thread {
    private int threadNum;
    
    public int getThreadNum() {
      return threadNum;
    }
    
    public SimpleThread( int threadNum ) {
      this.threadNum = threadNum;
      printThreadMessage( "created" );
      printThreadInfo();
    }
    
    public void printThreadMessage( String message ) {
      System.out.printf( "Thread %d %s\n", threadNum, message );
    }
    
    public void printThreadInfo() {
      System.out.printf( "\nThread Info for '%s'\n", this );
      System.out.printf( "  threadNum:    %d\n", this.getThreadNum() );
      System.out.printf( "  Id:           %d\n", this.getId() );
      System.out.printf( "  Name:         %s\n", this.getName() );
      System.out.printf( "  Priority:     %d\n", this.getPriority() );
      System.out.printf( "  Thread Group: %s\n", this.getThreadGroup() );
      System.out.printf( "  State:        %s\n", this.getState() );
      System.out.printf( "  isAlive:      %b\n", this.isAlive() );
      System.out.printf( "\n" );
    }
  
    public void run() {
      printThreadMessage( "started" );
      printThreadInfo();
      for ( int i = 0; i < 3; i++ ) {
        try { 
          printThreadMessage( "sleeping" );
          Thread.sleep( 1000 ); 
        } 
        catch ( InterruptedException e ) { 
          printThreadMessage( "interrupted" );
          printThreadInfo();
        } 
        printThreadMessage( "running" );
        printThreadInfo();
      }
    }
  }

  void go() {
    SimpleThread t1 = new SimpleThread( 1 );
    t1.start();
    SimpleThread t2 = new SimpleThread( 2 );
    t2.start();
    t1.interrupt();
    t1.printThreadMessage( "checking from go()" );
    t1.printThreadInfo();
    t2.printThreadMessage( "checking from go()" );
    t2.printThreadInfo();
  }
  
  public static void main( String[] args ) {
    ThreadInfo tInfo = new ThreadInfo();
    tInfo.go();
  }
}