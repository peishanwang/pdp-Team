package edu.neu.ccs.cs5010.ski_data_model;

import java.util.Arrays;

public class SkierIndexData extends DataModelItem {
  public SkierIndexData(DataModelItem dataModelItem) {
    super(dataModelItem);
    if (dataModelItem.getNumFields() != SkierToRideIndex.INDEX_NUM_FIELDS) {
      throw new IllegalArgumentException("");
    }
    nextAvailableField = dataModelItem.getFields()[1];
  }

  public SkierIndexData(int[] fields) {
    super(fields);
    if (fields.length != SkierToRideIndex.INDEX_NUM_FIELDS) {
      throw new IllegalArgumentException("");
    }
    nextAvailableField = fields[1];
  }

  public int getSkierId() {
    return getField(0);
  }

  public int[] getRidesId() {
    return Arrays.copyOfRange(getFields(), 2, 2 + nextAvailableField);
  }

  public void addRide(int rideNum) {
    if (rideNum == 0) {
      throw new IllegalStateException("");
    }
    getFields()[2 + nextAvailableField++] = rideNum;
    getFields()[1] = nextAvailableField;
  }

  public static SkierIndexData constructRawLiftRidesData(int skierId,
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