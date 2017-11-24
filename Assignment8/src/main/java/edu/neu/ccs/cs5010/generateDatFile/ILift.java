package edu.neu.ccs.cs5010.generateDatFile;

/**
 * This is the interface of Lift.
 *
 * @see Lift
 */

public interface ILift {

  /**
   * Add ride to the lift.
   */
  void addRide();

  /**
   * Add ride with its time information to the lift.
   * @param hourIndex hour index.
   */
  void addRideWithHourIndex(int hourIndex);

  /**
   * Get the total number of lift's ride.
   * @return the total number of lift's ride.
   */
  int getTotalRides();

  /**
   * Returns lift id.
   * @return lift id.
   */
  int getLiftId();

  /**
   * Get the number of lift's ride in a specific hour.
   * @param hourIndex index of the hour.
   * @return the number of lift's ride in a specific hour.
   */
  int getHourRides(int hourIndex);

}
