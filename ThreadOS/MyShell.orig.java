// 
//  MyShell.java
//  ThreadOS
//  
//  Created by John Hildebrant on 2011-10-21.
// 

class MyShell extends Thread {
  /**
  * Prints out "Hello World" 
  * and the command line arguments.
  * @param arg A string array containing 
  * the command line arguments.
  * @exception Any exception
  * @return No return value.
  */
  
  public void run() {
    // process a command line
    for (int cmdLine = 1; ; cmdLine++) {
      String str = "";
      // only process a command and increment counter if user types any
      // character before a new line character is received
      do {
        StringBuffer sb = new StringBuffer();
        // use error output file descriptor to print shell prompt
        SysLib.cerr("shell[" + cmdLine + "]% ");
        // receive input from console
        SysLib.cin(sb);
        str = sb.toString();
      } while (str.length() == 0);
      
      // create an array of all arguments
      String[] args = SysLib.stringToArgs(str);
      
      // split commands into array of commands where each command
      // is separated by a ";" or "&"
      int cmd = 0;
      
      // pro
      for (int k = 0; k < args.length; k++) {
        if ((!args[k].equals(";")) && (!args[k].equals("&")) && (k != args.length - 1))
          continue;
        String[] cmds = generateCmd(args, cmd, k == args.length - 1 ? k + 1 : k);

        if (cmds != null) {
          SysLib.cerr(cmds[0] + "\n");
          if (cmds[0].equals("exit")) {
            SysLib.exit();
            return;
          }
          // as long as a command executes successfully 
          int retCode = SysLib.exec(cmds);
          while ((retCode != -1) && 
            (!args[k].equals("&")) && 
            (SysLib.join() != retCode));
        }
        cmd = k + 1;
      }
    }
  }

  private String[] generateCmd(String[] paramArrayOfString, int paramInt1, int paramInt2) {
    if ((paramArrayOfString[(paramInt2 - 1)].equals(";")) || (paramArrayOfString[(paramInt2 - 1)].equals("&"))) {
      paramInt2 -= 1;
    }
    if (paramInt2 - paramInt1 <= 0)
      return null;
    String[] arrayOfString = new String[paramInt2 - paramInt1];
    for (int i = paramInt1; i < paramInt2; i++)
      arrayOfString[(i - paramInt1)] = paramArrayOfString[i];
    return arrayOfString;
  }
}