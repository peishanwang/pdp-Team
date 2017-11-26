package edu.neu.ccs.cs5010.ski_data_model;

import edu.neu.ccs.cs5010.generateDatFile.Skier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RawLiftRidesData extends DataModelItem {
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

public class RawLiftRidesDataModel extends IDataModel<RawLiftRidesData> {
  public RawLiftRidesDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + '/' + RAW_LIFT_RIDES_FILE_STORE,
            openMode,
            NUM_FIELDS,
            RawLiftRidesData::new);
    this.baseStorePath = baseStorePath;
    skierRidesIndex = new SkierToRideIndex(baseStorePath, openMode);
  }

  public RawLiftRidesDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  public void addDataInfo(RawLiftRidesData itemData) {
    int rideNum = itemData.getRideNum();
    int skierId = itemData.getSkierId();
    if (rideNum <= 0) {
      throw new IllegalArgumentException("rideNum");
    }
    if (skierRidesIndex.getDSMode() != DataSourceOpenMode.ACCESS_MODEL) {
      skierRidesIndex.close();
      skierRidesIndex = new SkierToRideIndex(baseStorePath);
    }
    SkierIndexData skierIndexData = skierRidesIndex.getDataInfo(skierId);
    skierIndexData.addRide(rideNum);
    super.addDataInfo(itemData);
    skierRidesIndex.updateDataInfo(skierId, skierIndexData);
  }


  public List<RawLiftRidesData> getDataListInfo(int skierId) {
   SkierIndexData skierIndexData = skierRidesIndex.getDataInfo(skierId);
   int[] rides = skierIndexData.getRidesId();
   List<RawLiftRidesData> skierRides = new ArrayList<>(rides.length);
    for (int ride : rides) {
      skierRides.add(super.getDataInfo(ride));
    }
   return skierRides;
  }

  @Override
  public RawLiftRidesData getDataInfo(int skierId) {
    throw new NotImplementedException();
  }

  @Override
  void updateDataInfo(int itemId, RawLiftRidesData itemData) {
    throw new NotImplementedException();
  }

  @Override
  public void close() {
    super.close();
    skierRidesIndex.close();
  }

  private final String baseStorePath;
  private SkierToRideIndex skierRidesIndex;
  private static final String RAW_LIFT_RIDES_FILE_STORE = "liftrides.dat";
  private static final int NUM_FIELDS = 4;

  public static void main(String[] args) {
    String basePath = "D:\\pdp_team_assignments\\Assignment8";

    // init index in create mode
    SkierToRideIndex index = new SkierToRideIndex(basePath, DataSourceOpenMode.CREATE_MODEL);
    index.close();

    // use in access mode
    index = new SkierToRideIndex(basePath);
    for (int i = 1; i <= 10; i++) {
      SkierIndexData data = index.getDataInfo(i);
      data.addRide(i);
      data.addRide(i + 1);
      index.updateDataInfo(i, data);
    }
    for (int i = 1; i <= 10; i++) {
      SkierIndexData data = index.getDataInfo(i);
      int[] rides = data.getRidesId();
      System.out.print("Num Rides: " + rides.length + ", rides = ");
      for (int r : rides) {
        System.out.print(r + " ");
      }
      System.out.println();
    }
    index.close();
    RawLiftRidesDataModel dataModel = new RawLiftRidesDataModel(basePath, DataSourceOpenMode
            .CREATE_MODEL);
    Random random = new Random();
    for (int i = 10; i > 0; i--) {
      RawLiftRidesData data = RawLiftRidesData.constructRawLiftRidesData(i,
              random.nextInt(4) + 1,
              random.nextInt(10),
              random.nextInt(10));
      dataModel.addDataInfo(data);
    }
    dataModel.close();

    dataModel = new RawLiftRidesDataModel(basePath);
    for (int i = 0; i <= 5; i++) {
      System.out.println(dataModel.getDataListInfo(1));
    }
    dataModel.close();
  }
}