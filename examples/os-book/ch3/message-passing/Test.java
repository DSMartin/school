import java.util.Date;

public class Test {
  public static void main( String[] args ) {
    Channel<Date> mailBox = new MessageQueue<Date>();
    Date date1 = new Date();
    System.out.printf( "Sending  %s\n", date1 );
    mailBox.send( date1 );
    Date date2 = mailBox.receive();
    System.out.printf( "Received %s\n", date2 );
  }
}
