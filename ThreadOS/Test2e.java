import java.util.Date;

class Test2e extends Thread {

  public void run() {
    long startTime = new Date().getTime();
    String[] args1 = SysLib.stringToArgs( "TestThread2e a 2000" );
    String[] args2 = SysLib.stringToArgs( "TestThread2e b 400" );
    String[] args3 = SysLib.stringToArgs( "TestThread2e c 300" );
    String[] args4 = SysLib.stringToArgs( "TestThread2e d 100" );
    String[] args5 = SysLib.stringToArgs( "TestThread2e e 200" );
    String[] args6 = SysLib.stringToArgs( "TestThread2e f 3000" );
    String[] args7 = SysLib.stringToArgs( "TestThread2e g 600" );
    String[] args8 = SysLib.stringToArgs( "TestThread2e h 700" );
    String[] args9 = SysLib.stringToArgs( "TestThread2e i 1000" );
    String[] args10 = SysLib.stringToArgs( "TestThread2e j 1500" );
    String[] args11 = SysLib.stringToArgs( "TestThread2e k 1800" );
    String[] args12 = SysLib.stringToArgs( "TestThread2e l 400" );
    String[] args13 = SysLib.stringToArgs( "TestThread2e m 300" );
    String[] args14 = SysLib.stringToArgs( "TestThread2e n 100" );
    String[] args15 = SysLib.stringToArgs( "TestThread2e o 200" );
    SysLib.exec( args1 );
    SysLib.exec( args2 );
    SysLib.exec( args3 );
    SysLib.exec( args4 );
    SysLib.exec( args5 );
    SysLib.exec( args6 );
    SysLib.exec( args7 );
    SysLib.exec( args8 );
    SysLib.exec( args9 );
    SysLib.exec( args10 );
    SysLib.exec( args11 );
    SysLib.exec( args12 );
    SysLib.exec( args13 );
    SysLib.exec( args14 );
    SysLib.exec( args15 );
    for (int i = 0; i < 15; i++ )
      SysLib.join();
    long endTime = new Date().getTime();
    long totalTime = endTime - startTime;
    SysLib.cout( "Test2d finished; total time = " + totalTime + "\n" );
    SysLib.exit();
  }
}
