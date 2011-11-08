/**
 * Time-of-day server listening to port 6013.
 *
 * Figure 3.26
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */ 

import java.util.Date;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DateServer {
  private static final int PORT = 6013;
  public static void main( String[] args ) {
    try {
      ServerSocket sock = new ServerSocket( PORT );
      while ( true ) { // listen for connections
        Socket client = sock.accept(); // we have a connection
        OutputStream outStream = client.getOutputStream();
        PrintWriter pw = new PrintWriter( outStream, true );
        pw.println( new Date().toString() );
        client.close(); // date served; terminate connection
      }
    } 
    catch ( IOException e ) {
      System.err.println( e );
    }
  }
}
