package edu.neu.ccs.cs5010.ski_data_model;

import java.util.Arrays;

public class HourRideData extends DataModelItem {

  public HourRideData(DataModelItem dataModelItem) {
    super(dataModelItem);
  }

  public HourRideData(int[] fields) {
    super(fields);
  }

  public int getHour() {
    return getField(0);
  }

  public int[] getBusyLifts() {
    return Arrays.copyOfRange(getFields(), 1, getNumFields());
  }

  public static HourRideData constructHourData(int hour,
                                               int[] busyLifts) {
    int[] fieldsData = new int[1 + busyLifts.length];
    if (busyLifts.length != 10) {
      throw new IllegalArgumentException("invalid size of busiest lifts");
    }
    fieldsData[0] = hour;
    System.arraycopy(busyLifts, 0, fieldsData, 1, busyLifts.length);
    return new HourRideData(fieldsData);
  }
}
