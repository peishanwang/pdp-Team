package edu.neu.ccs.cs5010;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */

public interface ILift {
  void addRide();

  void addRideWithTime(int timeIndex);

  int getTotalRides();

  int getLiftId();

  int getHourRides(int hourIndex);

}
