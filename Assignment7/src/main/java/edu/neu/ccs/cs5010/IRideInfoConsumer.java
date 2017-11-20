package edu.neu.ccs.cs5010;

public interface IRideInfoConsumer {

  public void accept(RideInfo rideInfo);

  public void stop();
}
