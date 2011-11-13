// 
//  TestThread3b.java
//  Perform I/O bound operations by reading and writing randomly across the
//  Disk.
//  Created by John Hildebrant on 2011-11-13.
// 

import java.util.Random;

class TestThread3b extends Thread {
  
  public void run() {
    byte[] byteArray = new byte[512];
    Random generator = new Random();
    
    for (int i = 0; i < 500; i++) {
      // read random bytes from blocks with index 0 through 1000 (non-inclusive)
      SysLib.rawread(generator.nextInt(1000), byteArray);
      // write random bytes from blocks with index 0 through 1000 (non-inclusive)
      generator.nextBytes(byteArray);
      SysLib.rawwrite(generator.nextInt(1000), byteArray);
    }
    SysLib.cout("TestThread3b finished...\n");
    SysLib.exit();
  }
}