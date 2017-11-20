package edu.neu.ccs.cs5010;

/**
 * Interface of Resort class.
 *
 * @see edu.neu.ccs.cs5010.Resort
 */
import java.util.List;

public interface IResort {
  /**
   * Add vertical for a skier in the resort using skier's id.
   * @param skierId skier's id.
   * @param vertical vertical value.
   */
  void addSkierVertical(int skierId, int vertical);

  /**
   * Get top Skier's according to their total number of vertical.
   * @param numberOfSkiers number of top skiers.
   * @return a list of top skiers.
   */
  List<ISkier> getTopSkier(int numberOfSkiers);

  /**
   * Add ride for a lift.
   * @param liftId lift's id.
   */
  void addLiftRide(int liftId);

  /**
   * Add ride with its time information for a lift.
   * @param liftId lift's id.
   * @param hourIndex hour index.
   */
  void addLiftRideWithHourIndex(int liftId, int hourIndex);

  /**
   * Returns the lift list.
   * @return the lift list.
   */
  List<ILift> getLiftList();

}
