package edu.neu.ccs.cs5010.ski_data_model;

/**
 * Record of lift to number of rides data for each lifts.
 */
public class LiftData extends DataModelItem {

  /**
   * Constructor of LiftData
   * @param dataModelItem dataModel Item
   */
  public LiftData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }

  /**
   * Constructor of LiftData
   * @param fields array of integer data.
   */
  public LiftData(int[] fields) {
    super(fields);
  }

  /**
   * Returns lift id.
   * @return lift id.
   */
  public int getLiftId() {
    return getField(0);
  }

  /**
   * Returns number of rides.
   * @return number of rides.
   */
  public int getNumRides() {
    return getField(1);
  }

  /**
   * Construct LiftData
   * @param liftId lift Id
   * @param numRides number of rides
   * @return new LiftData
   */
  public static LiftData constructLiftData(int liftId,
                                           int numRides) {
    return new LiftData(new int[]{liftId, numRides});
  }
}