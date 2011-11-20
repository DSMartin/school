// 
//  TestThread3b.java
//  Perform I/O bound operations by reading and writing randomly across the
//  Disk.
//  Created by John Hildebrant on 2011-11-13.
// 

import java.util.Random;
import java.util.Date;

class TestThread3b extends Thread {
  
  public void run() {
    long startTime = new Date().getTime();
    byte[] byteArray = new byte[512];
    Random generator = new Random();
    for (int i = 0; i < 120; i++) {
      // read random bytes from blocks with index 0 through 1000 (non-inclusive)
      SysLib.rawread(generator.nextInt(1000), byteArray);
      // write random bytes from blocks with index 0 through 1000 (non-inclusive)
      generator.nextBytes(byteArray);
      SysLib.rawwrite(generator.nextInt(1000), byteArray);
    }
    long endTime = new Date().getTime();
    SysLib.cout("TestThread3b finished in " + (endTime - startTime) + " msec.\n");
    SysLib.exit();
  }
}