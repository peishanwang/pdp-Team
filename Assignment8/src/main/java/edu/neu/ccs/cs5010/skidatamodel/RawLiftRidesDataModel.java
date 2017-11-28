package edu.neu.ccs.cs5010.skidatamodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * RawLiftRidesDataModel can access skier ride raw data.
 */
public class RawLiftRidesDataModel extends IDataModel<RawLiftRidesData> {
  /**
   * RawLiftRidesDataModel constructor.
   * @param baseStorePath base store path.
   * @param openMode create / access mode.
   *
   */
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

  /**
   * Constructor of RawLiftRidesDataModel.
   * @param baseStorePath path where this file store.
   */
  public RawLiftRidesDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  public void addDataInfo(RawLiftRidesData itemData) {
    super.addDataInfo(itemData);
  }
  /**
   * method to get list of lifts.
   * @param skierId id of skier.
   * @return list of lifts.
  */
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