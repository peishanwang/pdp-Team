package edu.neu.ccs.cs5010.ski_data_model;

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
//    HourRidesDataModel dataModel = new HourRidesDataModel(basePath, DataSourceOpenMode
//            .CREATE_MODEL);
//    dataModel.addDataInfo(HourRideData.constructHourData(
//            0,
//            new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
//    dataModel.addDataInfo(HourRideData.constructHourData(
//            0,
//            new int[]{40, 39, 38, 37, 36, 35, 34, 33, 32, 31}));
//    dataModel.close();

    HourRidesDataModel dataModel = new HourRidesDataModel(basePath);
    for (int i = 0; i < 6; i++) {
      HourRideData rideData = dataModel.getDataInfo(i);
      if (rideData.getBusyLifts().length != 10) {
        throw new IllegalStateException("invalid lifts");
      }
      System.out.print("Hour: " + i + " has rides: ");
      for (int liftId : rideData.getBusyLifts()) {
        System.out.print(liftId + " ");
      }
      System.out.println();
    }
    dataModel.close();
  }
}

