package edu.neu.ccs.cs5010.ski_data_model;

public class LiftData extends DataModelItem {

  public LiftData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }

  public LiftData(int[] fields) {
    super(fields);
  }

  public int getLiftId() {
    return getField(0);
  }

  public int getNumRides() {
    return getField(1);
  }

  public static LiftData constructLiftData(int liftId,
                                           int numRides) {
    return new LiftData(new int[]{liftId, numRides});
  }
}