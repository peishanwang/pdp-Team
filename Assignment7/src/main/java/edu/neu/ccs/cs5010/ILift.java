package edu.neu.ccs.cs5010;

public interface ILift {
  void addRide();

  void addRideWithTime(int timeIndex);

  int getTotalRides();

  int getLiftId();

  int getHourRides(int hourIndex);

}
