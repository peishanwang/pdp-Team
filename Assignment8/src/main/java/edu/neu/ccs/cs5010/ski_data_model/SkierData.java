package edu.neu.ccs.cs5010.ski_data_model;

/**
 * A piece of record to store skier and its total vertical and number of rides.
 */
public class SkierData extends DataModelItem {
  /**
   * Constructor of Skier Data.
   * @param dataModelItem dataModel Item
   */
  public SkierData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }

  /**
   * Constructor of SkierData.
   * @param fields array of integer data.
   */
  public SkierData(int[] fields) {
    super(fields);
  }

  /**
   * Returns skier id.
   * @return skier id.
   */
  public int getSkierId() {
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
   * Returns value of total vertical.
   * @return value of total vertical.
   */
  public int getTotalVertical() {
    return getField(2);
  }

  /**
   * Returns number of Views of specific record.
   * @return number of Views of specific record.
   */
  public int getNumViews() {
    return getField(3);
  }

  /**
   * Increment number of Views.
   */
  public void incNumViews() {
    updateField(3, getNumViews() + 1);
  }

  /**
   * Construct SkierData.
   * @param skierId skier id.
   * @param numRides number of rides
   * @param verticalDist total vertical
   * @return new SkierData
   */
  public static SkierData constructSkierData(int skierId, int numRides, int verticalDist) {
    return new SkierData(new int[]{skierId, numRides, verticalDist, 0});
  }
}