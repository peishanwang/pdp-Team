package edu.neu.ccs.cs5010.generateDatFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of lift containing information of lift's rides.
 *
 */
public class Lift implements ILift {
  private int totalRides;
  private int liftId;
  private List<Integer> ridesForHour;

  /**
   * Constructor of lift.
   * @param liftId lift id.
   */
  public Lift(int liftId) {
    this.liftId = liftId;
    ridesForHour = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      ridesForHour.add(0);
    }
  }

  @Override
  public void addRide() {
    totalRides++;
  }

  @Override
  public void addRideWithHourIndex(int hourIndex) {
    ridesForHour.set(hourIndex, ridesForHour.get(hourIndex) + 1);
  }

  @Override
  public int getTotalRides() {
    return totalRides;
  }

  @Override
  public int getLiftId() {
    return liftId;
  }

  @Override
  public int getHourRides(int hourIndex) {
    return ridesForHour.get(hourIndex);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Lift lift = (Lift) obj;

    return getLiftId() == lift.getLiftId();
  }

  @Override
  public int hashCode() {
    return getLiftId();
  }
}
