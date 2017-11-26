package edu.neu.ccs.cs5010.ski_data_model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RawLiftRidesDataModel extends IDataModel<RawLiftRidesData> {
  public RawLiftRidesDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + '/' + RAW_LIFT_RIDES_FILE_STORE,
            openMode,
            NUM_FIELDS,
            RawLiftRidesData::new);
    this.baseStorePath = baseStorePath;
    // only open in access mode
    if (openMode == DataSourceOpenMode.ACCESS_MODEL) {
      skierRidesIndex = new SkierToRideIndex(baseStorePath);
    }
  }

  public RawLiftRidesDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  public void addDataInfo(RawLiftRidesData itemData) {
    super.addDataInfo(itemData);
  }


  public List<RawLiftRidesData> getDataListInfo(int skierId) {
   SkierIndexData skierIndexData = skierRidesIndex.getDataInfo(skierId);
   int[] rides = skierIndexData.getRidesId();
   List<RawLiftRidesData> skierRides = new ArrayList<>(rides.length);
    for (int ride : rides) {
      skierRides.add(super.getDataInfo(ride - 1 /* rowId */));
    }
   return skierRides;
  }

  @Override
  public RawLiftRidesData getDataInfo(int rideId) {
    return super.getDataInfo(rideId - 1 /* rowId */);
  }

  @Override
  void updateDataInfo(int rideId, RawLiftRidesData itemData) {
    super.updateDataInfo(rideId - 1 /* rowId */, itemData);
  }

  @Override
  public void close() {
    super.close();
    if (skierRidesIndex != null) {
      skierRidesIndex.close();
    }
  }

  private final String baseStorePath;
  private SkierToRideIndex skierRidesIndex;
  private static final String RAW_LIFT_RIDES_FILE_STORE = "liftrides.dat";
  private static final int NUM_FIELDS = 4;

  public static void main(String[] args) {
    String basePath = "D:\\pdp_team_assignments\\Assignment8";

    // init index in create mode
//    SkierToRideIndex index = new SkierToRideIndex(basePath, DataSourceOpenMode.CREATE_MODEL);
//    for (int i = 1; i <= 10; i++) {
//      SkierIndexData data = SkierData.constructSkierData(i, 0, 0);
//      data.addRide(i);
//      data.addRide(i + 1);
//      index.addDataInfo(i, data);
//    }
//    index.close();
//    index = new SkierToRideIndex(basePath);
//    for (int i = 1; i <= 10; i++) {
//      SkierIndexData data = index.getDataInfo(i);
//      int[] rides = data.getRidesId();
//      System.out.print("Num Rides: " + rides.length + ", rides = ");
//      for (int r : rides) {
//        System.out.print(r + " ");
//      }
//      System.out.println();
//    }
//    index.close();
//    RawLiftRidesDataModel dataModel = new RawLiftRidesDataModel(basePath, DataSourceOpenMode
//            .CREATE_MODEL);
//    Random random = new Random();
//    for (int i = 10; i > 0; i--) {
//      RawLiftRidesData data = RawLiftRidesData.constructRawLiftRidesData(i,
//              random.nextInt(4) + 1,
//              random.nextInt(10),
//              random.nextInt(10));
//      dataModel.addDataInfo(data);
//    }
//    dataModel.close();

    RawLiftRidesDataModel dataModel = new RawLiftRidesDataModel(basePath);
    for (int skierId = 1; skierId <= 40000; skierId++) {
      List<RawLiftRidesData> ridesData = dataModel.getDataListInfo(skierId);
      if (ridesData.size() != 20) {
        throw new IllegalStateException("");
      }
      Collections.sort(ridesData, Comparator.comparing(o -> ((Integer) o.getTime())));
      System.out.print("For skier: " + skierId + ", ridesInfo: ");
      for (RawLiftRidesData rideData : ridesData) {
        System.out.print("[" + rideData.getRideNum() + ", " + rideData.getTime() + "]");
      }
      System.out.println();
    }
    dataModel.close();
  }
}