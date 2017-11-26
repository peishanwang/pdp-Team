package edu.neu.ccs.cs5010.ski_data_model;

import java.util.Random;

public class LiftDataModel extends IDataModel<LiftData> {
  public LiftDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + '/' + LIFT_FILE_STORE,
            openMode,
            NUM_FIELDS,
            LiftData::new);
  }

  public LiftDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  public LiftData getDataInfo(int itemId) {
    return super.getDataInfo(itemId - 1 /* rowId */);
  }

  @Override
  void updateDataInfo(int itemId, LiftData itemData) {
    super.updateDataInfo(itemId - 1 /* rowId */, itemData);
  }

  private static final String LIFT_FILE_STORE = "lifts.dat";
  private static final int NUM_FIELDS = 2;


  public static void main(String[] args) {
    String basePath = "D:\\pdp_team_assignments\\Assignment8";
//    LiftDataModel liftDataModel = new LiftDataModel(basePath, DataSourceOpenMode.CREATE_MODEL);
//    Random random = new Random();
//    for (int i = 1; i <= 10; i++) {
//      LiftData liftData = LiftData.constructLiftData(
//              i,
//              random.nextInt(10));
//      liftDataModel.addDataInfo(liftData);
//      System.out.println("write liftInfo for id: " + i + ", liftId: "
//              + liftData.getLiftId() +
//              ", " + "numRides: " +
//              liftData
//                      .getNumRides());
//    }
//    liftDataModel.close();

    LiftDataModel liftDataModel = new LiftDataModel(basePath);
    for (int liftId = 1; liftId <= 40; liftId++) {
      LiftData liftData = liftDataModel.getDataInfo(liftId);
      System.out.println("read liftInfo for id: " + liftId + ", liftId: "
              + liftData.getLiftId() +
              ", " + "numRides: " +
              liftData
                      .getNumRides());
    }
    liftDataModel.close();
  }
}
