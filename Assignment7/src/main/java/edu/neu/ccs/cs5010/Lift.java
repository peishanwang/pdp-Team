package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;

public class Lift implements ILift {
  private int totalRides;
  private int liftId;
  private List<Integer> ridesForHour;

  public Lift(int liftId) {
    this.liftId = liftId;
    ridesForHour = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      ridesForHour.add(0);
    }
  }

  public void addRide() {
    totalRides++;
  }

  public void addRideWithTime(int hourIndex) {
    ridesForHour.set(hourIndex, ridesForHour.get(hourIndex) + 1);
  }

  public int getTotalRides() {
    return totalRides;
  }

  public int getLiftId() {
    return liftId;
  }

  public int getHourRides(int hourIndex) {
    return ridesForHour.get(hourIndex);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Lift lift = (Lift) obj;

    return getLiftId() == lift.getLiftId();
  }

  @Override
  public int hashCode() {
    return getLiftId();
  }
}
