package edu.neu.ccs.cs5010;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public interface IRideInfoConsumer {

  public void accept(RideInfo rideInfo);

  public void stop();
}
