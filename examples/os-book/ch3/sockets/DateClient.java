/**
 * Client program requesting current date from server.
 *
 * Figure 3.27
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */
 
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class DateClient {
  private static final String IP_ADDRESS = "127.0.0.1";
  private static final int PORT = 6013;
  public static void main( String[] args ) throws IOException {
    Socket sock = null;
    try {
      sock = new Socket( IP_ADDRESS, PORT );
      InputStream in = sock.getInputStream();
      BufferedReader bin = new BufferedReader( new InputStreamReader( in ));
      String line;
      while( ( line = bin.readLine()) != null )
        System.out.println( line );
    } 
    catch ( IOException e ) {
      System.err.println( e );
    } 
    finally {
      sock.close();
    }
  }
}
