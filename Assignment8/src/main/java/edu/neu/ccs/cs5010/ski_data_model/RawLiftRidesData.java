package edu.neu.ccs.cs5010.ski_data_model;

public class RawLiftRidesData extends DataModelItem {

  public RawLiftRidesData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }

  public RawLiftRidesData(int[] fields) {
    super(fields);
  }

  public int getRideNum() {
    return getField(0);
  }

  public int getSkierId() {
    return getField(1);
  }

  public int getLiftId() {
    return getField(2);
  }

  public int getTime() {
    return getField(3);
  }

  public static RawLiftRidesData constructRawLiftRidesData(int rideNum,
                                                           int skierId,
                                                           int liftId,
                                                           int time) {
    return new RawLiftRidesData(new int[]{rideNum, skierId, liftId, time});
  }
}
