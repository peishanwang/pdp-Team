package edu.neu.ccs.cs5010.skidatamodel;

import java.util.Arrays;

/**
 * A piece of record to store skier and its rideIndex data for a skier.
 */
public class SkierIndexData extends DataModelItem {
  /**
   * Constructor of SkierIndexData.
   * @param dataModelItem dataModel Item
   */
  public SkierIndexData(DataModelItem dataModelItem) {
    super(dataModelItem);
    if (dataModelItem.getNumFields() != SkierToRideIndex.INDEX_NUM_FIELDS) {
      throw new IllegalArgumentException("");
    }
    nextAvailableField = dataModelItem.getFields()[1];
  }

  /**
   * Constructor of SkierIndexData.
   * @param fields array of integer data.
   */
  public SkierIndexData(int[] fields) {
    super(fields);
    if (fields.length != SkierToRideIndex.INDEX_NUM_FIELDS) {
      throw new IllegalArgumentException("");
    }
    nextAvailableField = fields[1];
  }

  /**
   * Returns Skier id.
   * @return Skier id.
   */
  public int getSkierId() {
    return getField(0);
  }

  /**
   * Returns rides' id.
   * @return rides' id.
   */
  public int[] getRidesId() {
    return Arrays.copyOfRange(getFields(), 2, 2 + nextAvailableField);
  }

  /**
   * Add ride to this record.
   * @param rideNum add ride information to this record.
   */
  public void addRide(int rideNum) {
    if (rideNum == 0) {
      throw new IllegalStateException("");
    }
    getFields()[2 + nextAvailableField++] = rideNum;
    getFields()[1] = nextAvailableField;
  }

  /**
   * Construct Skier to Ride Index data.
   * @param skierId skier id.
   * @param ridesId array of ride id.
   * @return SkierIndexData for this skier.
   */
  public static SkierIndexData constructSkierToRideData(int skierId,
                                                        int[] ridesId) {
    int[] skierRidesIndexData = new int[SkierToRideIndex.INDEX_NUM_FIELDS];
    skierRidesIndexData[0] = skierId;
    skierRidesIndexData[1] = 0;
    if (ridesId.length > (SkierToRideIndex.INDEX_NUM_FIELDS - 2)) {
      throw new IllegalStateException("unknown index entries");
    }
    for (int i = 0; i < ridesId.length; i++) {
      if (ridesId[i] == 0) {
        skierRidesIndexData[1] = i;
        break;
      }
      skierRidesIndexData[2 + i] = ridesId[i];
    }
    return new SkierIndexData(skierRidesIndexData);
  }

  private int nextAvailableField;
}