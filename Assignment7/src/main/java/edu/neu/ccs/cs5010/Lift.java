package edu.neu.ccs.cs5010;

import java.util.List;

public class Lift implements ILift {
  private int totalRides;
  private int liftId;
  private List<Integer> ridesForHour;

  public Lift(int liftId) {
    this.liftId = liftId;
  }

  public void addRide() {
    totalRides++;
  }

  public void addRideWithTime(int timeIndex) {
    ridesForHour.set(timeIndex, ridesForHour.get(timeIndex) + 1);
  }

}
