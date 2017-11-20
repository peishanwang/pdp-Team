package edu.neu.ccs.cs5010.concurrentsystem;

public class LiftWithRides {
  public LiftWithRides(int liftId, int numRides) {
    this.liftId = liftId;
    this.numRides = numRides;
  }

  public Integer getLiftId() {
    return liftId;
  }

  public Integer getNumRides() {
    return numRides;
  }

  private Integer liftId;
  private Integer numRides;
}