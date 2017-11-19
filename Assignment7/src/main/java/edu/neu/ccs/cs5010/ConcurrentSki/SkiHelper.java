package edu.neu.ccs.cs5010.ConcurrentSki;

public class SkiHelper {
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
}