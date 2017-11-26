package edu.neu.ccs.cs5010.ski_data_model;

public class SkierData extends DataModelItem {
  public SkierData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }
  public SkierData(int[] fields) {
    super(fields);
  }
  public int getSkierId() {
    return getField(0);
  }
  public int getNumRides() {
    return getField(1);
  }
  public int getTotalVertical() {
    return getField(2);
  }
  public int getNumViews() {
    return getField(3);
  }
  public void incNumViews() {
    updateField(3, getNumViews() + 1);
  }
  public static SkierData constructSkierData(int skierId, int numRides, int verticalDist) {
    return new SkierData(new int[]{skierId, numRides, verticalDist, 0});
  }
}