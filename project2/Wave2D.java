// 
//  Wave2D.java
//  project2
//  
//  Created by Hildebrant, John on 2012-02-06.
// 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Date;
import javax.swing.JFrame;

class Wave2D
  implements Runnable
{
  static final int defaultN = 100;   // the default system size
  static final int defaultCellWidth = 8;
  static final Color bgColor = new Color(255, 255, 255); // white background
  static final double c = 1.0D;      // wave speed
  static final double dt = 0.1D;     // a time quantum for simulation
  static final double dd = 2.0D;     // a change of the surface
  static final int initLow = 40;     // lower bound for splash center
  static final int initHigh = 60;    // upper bound for splash center
  static final double z_init = 20.0D;// initialize Zt_i,j
  private int N = 0;                 // simulation size
  private int runTime = 0;           // simulation time
  private int interval = 0;          // the interval time to update the
                                     // graphical window
  private int nThreads = 1;          // default to one thread
  private int[] sync = null;         // threads share it and synchronize on it
  private double[][][] z;            // simulation space
  private double[][] z_last;         // compare z to last value to avoid paint
                                     // if cell value is unchanged
  private int time;                  // compute z at various times
  private boolean initPaint = true;  // first paint of graphics
  private JFrame gWin;               // a graphic's window
  private int cellWidth;             // each cell's width in the window
  private Insets theInsets;          // the insets of the window
  private Color[] wvColor;           // wave color
  private int threadId = -1;         // thread index
  private int lowerBound = 0;        // divide the space among threads
  private int upperBound = 0;        // divide the space among threads

  public Wave2D(int[] sync, double[][][] space, int time, int interval, 
                int nThreads, int threadId)
  {
    this.sync = sync;
    z = space;
    N = z[0].length;
    runTime = time;
    this.interval = interval;
    this.nThreads = nThreads;
    this.threadId = threadId;
    int i = N / nThreads;
    int j = N % nThreads;
    lowerBound = i * threadId + (threadId < j ? threadId : 0);
    upperBound = i * (threadId + 1) - 1 + (threadId < j ? threadId + 1 : 0);
    
    // startGraphics should only be called once
    if (threadId == 0) {
      // initialize z_last once
      z_last = new double[N][N];
      startGraphics( );
    }
  }
  
  private boolean inBounds(int iPosition) {
    return (lowerBound <= iPosition) && (iPosition <= upperBound);
  }

  public void execute( ) {
    Date startTime = new Date();
    
    // at t == 0 (initialization)
    int x = N / defaultN;
    
    for (int i = 0; i < N; i++) {
      if (inBounds(i)) {
        for (int j = 0; j < N; j++) {
          if (i > initLow * x && i < initHigh * x && 
              j > initLow * x && j < initHigh * x) {
            z[0][i][j] = z_init;
          }
          // no movement outside initialization boundary
          else {
            z[0][i][j] = 0.0D;
          }
        }
      }
    }

    barrier();
    
    // at t == 1
    for (int i = 1; i < N - 1; i++) {
      if (inBounds(i)) {
        for (int j = 1; j < N - 1; j++) {
          z[1][i][j] = z[0][i][j] + Math.pow(c, 2.0D)/ 2.0D * 
          Math.pow(dt/dd, 2.0D) * (z[0][i + 1][j] + z[0][i - 1][j] + 
          z[0][i][j + 1] + z[0][i][j - 1] - 4.0D * z[0][i][j]);
        }
      }
    }

    for (int i = 0; i < N; i++) {
      if (!inBounds(i)) continue;
      // cells on edge, z is 0
      z[1][i][0] = 0.0D;
      z[1][i][N - 1] = 0.0D;
      z[1][0][i] = 0.0D;
      z[1][N - 1][i] = 0.0D;
    }

    barrier();
    
    // at time >= 2
    for (time = 2; time < runTime; time++) {
      for (int i = 1; i < N - 1; i++) {
        if (inBounds(i)) {
          for (int j = 1; j < N - 1; j++) {
            z[2][i][j] = 2.0D * z[1][i][j] - z[0][i][j] + Math.pow(c, 2.0D) *
            Math.pow(dt/dd, 2.0D) * (z[1][i + 1][j] + z[1][i - 1][j] + 
            z[1][i][j + 1] + z[1][i][j - 1] - 4.0D * z[1][i][j]);
          }
        }
      }
      
      // cells on edge, z is 0
      for (int i = 0; i < N; i++) {
        if (inBounds(i)) {
          z[2][i][0] = 0.0D;
          z[2][i][N - 1] = 0.0D;
          z[2][0][i] = 0.0D;
          z[2][N - 1][i] = 0.0D;
        }
      }
      
      // cell(i,j) computes its new surface height from the previous height
      // of itself
      for (int i = 0; i < N; i++) {
        if (inBounds(i)) {
          for (int j = 0; j < N; j++) {
            z[0][i][j] = z[1][i][j];
            z[1][i][j] = z[2][i][j];
          }
        }
      }

      barrier();
      
      // at every interval refresh surface
      if (threadId == 0 && time % interval == 0) {
        writeToGraphics();
      }

      barrier();
    }

    if (threadId == 0) {
      Date endTime = new Date();
      System.out.println("elapsed time = " + (endTime.getTime() - 
                         startTime.getTime()));
    }
  }

  private void barrier() {
    synchronized ( sync ) {
      
      // increment sync[0], because I reached the barrier
      // if sync[0] has not reached nThreads, there must be someone else who
      // has not called barrier( )
      if (++sync[0] < nThreads) {
        try {
          // let's wait
          sync.wait();
        } catch (InterruptedException e) {
          System.err.println("interrupted");
        }
      // else, all threads called barrier( ) and I am the last
      } else {
        // zero-initialize sync[0] for the next barrier
        sync[0] = 0;
        // wake them all
        sync.notifyAll();
      }
    }
  }

  private void startGraphics()
  {
    // the cell width in a window
    cellWidth = defaultCellWidth / (N / defaultN);
    if (cellWidth == 0) {
      cellWidth = 1;
    }

    // initialize window and graphics:
    gWin = new JFrame("Wave Simulation");
    gWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gWin.setLocation(50, 50);  // screen coordinates of top left corner
    gWin.setResizable(false);
    gWin.setVisible(true);     // show it!
    theInsets = gWin.getInsets();
    gWin.setSize(N * cellWidth + theInsets.left + theInsets.right,
                 N * cellWidth + theInsets.top + theInsets.bottom);

    // wait for frame to get initialized
    long resumeTime = System.currentTimeMillis() + 1000;
    do {
    } while (System.currentTimeMillis() < resumeTime);

    Graphics g = gWin.getGraphics();
    g.setColor(bgColor);
    g.fillRect(theInsets.left, theInsets.top, N * cellWidth, N * cellWidth);

    wvColor = new Color[21];
    wvColor[0] = new Color(0x0000FF);
    wvColor[1] = new Color(0x0033FF);
    wvColor[2] = new Color(0x0066FF);
    wvColor[3] = new Color(0x0099FF);
    wvColor[4] = new Color(0x00CCFF);
    wvColor[5] = new Color(0x00FFFF);
    wvColor[6] = new Color(0x00FFCC);
    wvColor[7] = new Color(0x00FF99);
    wvColor[8] = new Color(0x00FF66);
    wvColor[9] = new Color(0x00FF33);
    wvColor[10] = new Color(0x00FF00);
    wvColor[11] = new Color(0x33FF00);
    wvColor[12] = new Color(0x66FF00);
    wvColor[13] = new Color(0x99FF00);
    wvColor[14] = new Color(0xCCFF00);
    wvColor[15] = new Color(0xFFFF00);
    wvColor[16] = new Color(0xFFCC00);
    wvColor[17] = new Color(0xFF9900);
    wvColor[18] = new Color(0xFF6600);
    wvColor[19] = new Color(0xFF3300);
    wvColor[20] = new Color(0xFF0000);
  }

  private void writeToGraphics() {
    System.out.println("time = " + time);
    Graphics g = gWin.getGraphics();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++)
      {
        // performance improvement to only paint if cell value changes
        if ((!initPaint) &&
            (z_last[i][j] == z[0][i][j]))
          continue;
        z_last[i][j] = z[0][i][j];
        // convert a wave height to a color index ( 0 to 20 )
        int index = (int) (z[0][i][j] / 2 + 10);
        index = (index > 20) ? 20 : ((index < 0) ? 0 : index);
        
        g.setColor(wvColor[index]);
        g.fill3DRect(theInsets.left + i * cellWidth, theInsets.top + 
                     j * cellWidth, cellWidth, cellWidth, true);
      }
    }
    initPaint = false;
  }
  
  public void run() {
    execute();
  }

  private static void usage() {
    System.err.println("usage: java Wave2D size time interval nThreads");
    System.err.println("       where size >= 100 && time >= 3 && interval >= 1"
      + " && nThreads >= 1");
    System.exit(-1);
  }

  public static void main( String args[] )
  {
    if (args.length < 4) {
      usage();
    }
    int size, time, interval, nThreads;
    size = time = interval = nThreads = 0;
    try {
      size = Integer.parseInt( args[0] );
      time = Integer.parseInt( args[1] );
      interval = Integer.parseInt( args[2] );
      nThreads = Integer.parseInt( args[3] );
      if (size < 100 || time < 3 || interval < 1 || nThreads < 1) {
        usage();
      }
    } catch (Exception e) {
      usage();
    }
    
    // prepare a simulation space
    double[][][] space = new double[3][size][size];
    
    // used to count the number of threads that called barrier so far
    int[] sync = new int[1];
    sync[0] = 0;

    Wave2D[] waves = new Wave2D[nThreads];
    Thread[] threads = new Thread[nThreads];
    
    for (int i = 0; i < nThreads; i++) {
      waves[i] = new Wave2D(sync, space, time, interval, nThreads, i);
      // instantiate slave threads
      if (i > 0) {
        threads[i] = new Thread(waves[i]);
        threads[i].start();
      }
    }
    
    // threadId 0
    waves[0].execute( );
  }
}