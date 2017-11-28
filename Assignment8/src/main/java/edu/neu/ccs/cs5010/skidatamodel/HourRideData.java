package edu.neu.ccs.cs5010.skidatamodel;

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

  /**
   * method to construct hour data.
   * @param hour hour .
   * @param busyLifts array of busiest lifts.
   * @return data of hour constructed-number of busy lifts and lifts.
   */
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
