package edu.neu.ccs.cs5010;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public interface IRideInfoConsumer {

  void accept(RideInfo rideInfo);

  void stop();
}
