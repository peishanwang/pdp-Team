package edu.neu.ccs.cs5010.ski_data_model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RawLiftRidesDataModel extends IDataModel<RawLiftRidesData> {
  public RawLiftRidesDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + File.separator + RAW_LIFT_RIDES_FILE_STORE,
            openMode,
            NUM_FIELDS,
            RawLiftRidesData::new);
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

  private SkierToRideIndex skierRidesIndex;
  private static final String RAW_LIFT_RIDES_FILE_STORE = "liftrides.dat";
  private static final int NUM_FIELDS = 4;

}