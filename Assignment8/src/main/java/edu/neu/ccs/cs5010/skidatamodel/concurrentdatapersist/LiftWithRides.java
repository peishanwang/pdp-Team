package edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist;

import java.util.Objects;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class LiftWithRides implements ILiftWithRides {
  public LiftWithRides(int liftId, int numRides) {
    this.liftId = liftId;
    this.numRides = numRides;
  }

  /**
   * method to get lift id.
   * @return id of lift
   */
  @Override
  public Integer getLiftId() {
    return liftId;
  }

  /**
   * method to get number of rides.
   * @return number of rides
   */
  @Override
  public Integer getNumRides() {
    return numRides;
  }


  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    LiftWithRides that = (LiftWithRides) object;
    return Objects.equals(liftId, that.liftId)
            && Objects.equals(numRides, that.numRides);
  }

  @Override
  public int hashCode() {

    return Objects.hash(liftId, numRides);
  }

  private Integer liftId;
  private Integer numRides;


}