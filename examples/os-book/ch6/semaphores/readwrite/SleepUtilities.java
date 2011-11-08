/**
 * Utilities for causing a thread to sleep.
 * Note, we should be handling interrupted exceptions
 * but choose not to do so for code clarity.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

public class SleepUtilities {
  private static final int NAP_TIME = 5;
  public static void nap() {
    nap(NAP_TIME);
  }
  public static void nap( int duration ) {
    // JFM: substituting duration for NAP_TIME below
    int sleeptime = (int) ( duration * Math.random() );
    try { 
      Thread.sleep( sleeptime * 1000 );
    } catch (InterruptedException e) {
    }
  }
}
