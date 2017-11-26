package edu.neu.ccs.cs5010.ski_data_model;

import java.util.Arrays;
import java.util.Random;

class HourRideData extends DataModelItem {
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
    fieldsData[0] = hour;
    System.arraycopy(busyLifts, 0, fieldsData, 1, busyLifts.length);
    return new HourRideData(fieldsData);
  }
}

public class HourRidesDataModel extends IDataModel<HourRideData> {
  public HourRidesDataModel(final String baseStorePath,
                            DataSourceOpenMode openMode) {
    super(baseStorePath + '/' + HOUR_FILE_STORE,
            openMode,
            NUM_FIELDS,
            HourRideData::new);
  }

  public HourRidesDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  private static final String HOUR_FILE_STORE = "hours.dat";
  private static final int NUM_FIELDS = 11;


  public static void main(String[] args) {
    String basePath = "D:\\pdp_team_assignments\\Assignment8";
    HourRidesDataModel dataModel = new HourRidesDataModel(basePath, DataSourceOpenMode
            .CREATE_MODEL);
    dataModel.addDataInfo(HourRideData.constructHourData(
            0,
            new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
    dataModel.addDataInfo(HourRideData.constructHourData(
            0,
            new int[]{40, 39, 38, 37, 36, 35, 34, 33, 32, 31}));
    dataModel.close();

    dataModel = new HourRidesDataModel(basePath);
    HourRideData rideData = dataModel.getDataInfo(0);
    System.out.print("Hour: " + 0 + " has rides: ");
    for (int liftId : rideData.getBusyLifts()) {
      System.out.print(liftId + " ");
    }
    System.out.println();
    rideData = dataModel.getDataInfo(1);
    System.out.print("Hour: " + 1 + " has rides: ");
    for (int liftId : rideData.getBusyLifts()) {
      System.out.print(liftId + " ");
    }
    System.out.println();
    dataModel.close();
  }
}

