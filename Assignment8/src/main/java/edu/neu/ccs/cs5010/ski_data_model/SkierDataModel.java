package edu.neu.ccs.cs5010.ski_data_model;

import java.util.Random;

public class SkierDataModel extends IDataModel<SkierData> {
  public SkierDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + '/' + SKIER_FILE_STORE,
            openMode,
            NUM_FIELDS,
            SkierData::new);
  }

  public SkierDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  public SkierData getDataInfo(int itemId) {
    return super.getDataInfo(itemId - 1 /* rowId */);
  }

  @Override
  void updateDataInfo(int itemId, SkierData itemData) {
    super.updateDataInfo(itemId - 1 /* rowId */, itemData);
  }

  private static final String SKIER_FILE_STORE = "skier.dat";
  private static final int NUM_FIELDS = 4;


  public static void main(String[] args) {
    String basePath = "D:\\pdp_team_assignments\\Assignment8";
//    SkierDataModel skierDataModel = new SkierDataModel(basePath, DataSourceOpenMode.CREATE_MODEL);
//    Random random = new Random();
//    for (int i = 1; i <= 10; i++) {
//      SkierData skierData = SkierData.constructSkierData(
//              i,
//              random.nextInt(10), random.nextInt
//              (500));
//      skierDataModel.addDataInfo(skierData);
//      System.out.println("writing skierInfo, skierId: " + skierData.getSkierId() + ", numRides: " +
//              skierData
//              .getNumRides() + ", numVertical: " + skierData.getTotalVertical() + ", numViews: "
//              + skierData.getNumViews());
//    }
//    skierDataModel.close();

    SkierDataModel skierDataModel = new SkierDataModel(basePath);
    for (int i = 1; i <= 40000; i++) {
      SkierData skierData = skierDataModel.getDataInfo(i);
      System.out.println("read skierInfo for id: " + i + ", skierId: " + skierData.getSkierId() +
              ", " + "numRides: " +
              skierData
                      .getNumRides() + ", numVertical: " + skierData.getTotalVertical() + ", numViews: "
              + skierData.getNumViews());
    }
    skierDataModel.close();
  }
}