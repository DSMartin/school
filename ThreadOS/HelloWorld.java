public class HelloWorld extends Thread {
  public HelloWorld() {
  }
  public void run() {
    SysLib.cout( "Hello, world\n" );  // using SysLib.cout vs System.out.println
    SysLib.exit();                    // don't forget to explicitly exit
    return;
  }
}