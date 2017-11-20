package edu.neu.ccs.cs5010.concurrentsystem;

/**
 * Class to get vertical distance meters according to liftid.
 *
 * @author Manika Sharma and Peishan Wang
 */
public class SkiHelper {

  /**
   * Class to get vertical distance meters according to liftid.
   * @param liftId id of lift.
   * @return the vertical distance meter.
   */
  public static int getVerticalDistanceMetres(int liftId) {
    if (liftId >= 1 && liftId <= 10) {
      return 200;
    } else if (liftId >= 11 && liftId <= 20) {
      return 300;
    } else if (liftId >= 20 && liftId <= 30) {
      return 400;
    } else if (liftId >= 30 && liftId <= 40) {
      return 500;
    } else {
      throw new IllegalArgumentException("invalid liftId: " + liftId);
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}