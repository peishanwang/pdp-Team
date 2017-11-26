package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

/**
 * This is an interface of consumer containing accept and stop method.
 */
public interface IRideInfoConsumer {

  /**
   * Accept ride information and process them.
   * @param rideInfo ride information.
   */
  void accept(RideInfo rideInfo);

  /**
   * Stop the consumer after process all the data.
   */
  void stop();
}
