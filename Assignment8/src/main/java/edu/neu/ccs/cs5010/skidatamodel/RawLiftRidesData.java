package edu.neu.ccs.cs5010.skidatamodel;

/**
 * Record of raw data of skier rides.
 */
public class RawLiftRidesData extends DataModelItem {
  /**
   * Constructor of RawLiftRidesData.
   * @param dataModelItem dataModel Item
   */
  public RawLiftRidesData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }

  /**
   * Constructor of RawLiftRidesData.
   * @param fields array of integer data.
   */
  public RawLiftRidesData(int[] fields) {
    super(fields);
  }

  /**
   * Returns ride id.
   * @return ride id.
   */
  public int getRideNum() {
    return getField(0);
  }

  /**
   * Returns skier id.
   * @return skier id.
   */
  public int getSkierId() {
    return getField(1);
  }

  /**
   * Returns lift id.
   * @return lift id.
   */
  public int getLiftId() {
    return getField(2);
  }

  /**
   * Returns time.
   * @return time.
   */
  public int getTime() {
    return getField(3);
  }

  /**
   * Construct RawLiftRidesData.
   * @param rideNum ride id
   * @param skierId skier id
   * @param liftId lift id
   * @param time time
   * @return new RawLiftRidesData.
   */
  public static RawLiftRidesData constructRawLiftRidesData(int rideNum,
                                                           int skierId,
                                                           int liftId,
                                                           int time) {
    return new RawLiftRidesData(new int[]{rideNum, skierId, liftId, time});
  }
}
