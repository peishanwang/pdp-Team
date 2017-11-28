package edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public interface ILiftWithRides {
  /**
   * method to get lift id.
   * @return liftid
   */
  Integer getLiftId();

  /**
   * method to get number of rides.
   * @return number of rides
   */
  Integer getNumRides();
}
