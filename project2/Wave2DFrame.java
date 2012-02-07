import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JFrame;

class Wave2D implements Runnable {

  static final int defaultN = 100;  // the default system size
  static final int defaultCellWidth = 8;
  static final Color bgColor = new Color(255, 255, 255);//white background
  private int N = 0;                       // simulation size
  private double z[][][];                  // simulation space
  private JFrame gWin;                     // a graphics window
  private int cellWidth;                   // each cell's width in the window
  private Insets theInsets;                // the insets of the window
  private Color wvColor[];                 // wave color

  public Wave2D( double[][][] space ) {
    this.z = space;
    this.N = z[0].length;
    startGraphics();
  }

  public void run( ) {
  // implement it!
  }

  private void startGraphics( ) {
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
    g.fillRect(theInsets.left,
    theInsets.top,
    N * cellWidth,
    N * cellWidth);

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
    Graphics g = gWin.getGraphics();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        // convert a wave height to a color index ( 0 through to 20 )
        int index = (int) (z[0][i][j] / 2 + 10);
        index = (index > 20) ? 20 : ((index < 0) ? 0 : index);

        g.setColor(wvColor[index]);
        g.fill3DRect(theInsets.left + i * cellWidth,
        theInsets.top + j * cellWidth,
        cellWidth, cellWidth, true);
      }
    }
  }

  public static void main( String args[] ) {

    int size = Integer.parseInt( args[0] );

    // prepare a simulation space
    double[][][] space = new double[3][size][size];

    // instantiate slave threads
    Wave2D wave2D = new Wave2D( space );
  }
}
