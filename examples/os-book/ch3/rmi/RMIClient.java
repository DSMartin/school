/**
 * The RMI Client
 *
 * Figure 3.33
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.rmi.Naming;

public class RMIClient  {  

  public static final String IP_ADDRESS = "127.0.0.1";

  public static void main( String args[] ) { 
    try {
      String host = "rmi://" + IP_ADDRESS + "/RMIDateObject";
      RemoteDate dateServer = (RemoteDate)Naming.lookup( host );
      System.out.println( dateServer.getDate() );
    }
    catch ( Exception e ) {
      System.err.println( e );
    }
  }
}

