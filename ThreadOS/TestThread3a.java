// 
//  TestThread3a.java
//  Perform numerical computation to simulate CPU-bound threads
//  
//  Created by John Hildebrant on 2011-11-13.
// 
class TestThread3a extends Thread {
  private static int count = 0;
  
  public void run() {
    move(30, 1, 3);
    //System.out.println(count + " moves made");
    SysLib.cout("TestThread3a finished...\n");
    SysLib.exit();
  }
  
  // Towers of Hanoi problem
  private static void move(int n, int startPole, int endPole) {
    if (n == 0) {
			return; 
		}
		int intermediatePole = 6 - startPole - endPole;
		move(n-1, startPole, intermediatePole);
		//System.out.println("Move " + n + " from " + startPole + " to " +endPole);
		count++;
		move(n-1, intermediatePole, endPole);
  }
}