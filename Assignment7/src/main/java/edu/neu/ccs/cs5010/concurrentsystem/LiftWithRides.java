package edu.neu.ccs.cs5010.concurrentsystem;

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

  @Override
  public Integer getLiftId() {
    return liftId;
  }

  @Override
  public Integer getNumRides() {
    return numRides;
  }

  private Integer liftId;
  private Integer numRides;
}