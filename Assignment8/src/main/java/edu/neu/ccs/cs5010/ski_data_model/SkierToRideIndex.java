package edu.neu.ccs.cs5010.ski_data_model;

import java.io.File;

/**
 * Record to store skier to rideIndex data for each skiers.
 */
public class SkierToRideIndex extends IDSIndex<SkierIndexData> {
  /**
   * Constructor of SkierToRideIndex.
   * @param baseStorePath path where this file store.
   * @param mode mode of accessing this record.
   */
  public SkierToRideIndex(final String baseStorePath, DataSourceOpenMode mode) {
    super(baseStorePath + File.separator + RAW_LIFT_RIDES_FILE_INDEX_STORE,
            mode,
            INDEX_NUM_FIELDS,
            SkierIndexData::new);
     if (mode == DataSourceOpenMode.ACCESS_MODEL) {
       int numItems = 0;
       synchronized (dataSource) {
        numItems = dataSource.getNumDataItems();
       }
      if (numItems != MAX_SKIER_INDEX_SIZE) {
        throw new IllegalStateException("Num items already present: " + numItems);
      }
     }
  }

  /**
   * Constructor of SkierToRideIndex.
   * @param baseStorePath path where this file store.
   */
  public SkierToRideIndex(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  int getIndexColumn() {
    return 1;
  }

  @Override
  public SkierIndexData getDataInfo(int itemId) {
    return super.getDataInfo(itemId - 1 /* rowId */);
  }

  @Override
  void updateDataInfo(int itemId, SkierIndexData itemData) {
    super.updateDataInfo(itemId  - 1/* rowId */, itemData);
  }

  private static final int MAX_SKIER_RIDES_PER_DAY = 100;
  private static final int MAX_SKIER_INDEX_SIZE = 40000;
  static final int INDEX_NUM_FIELDS = 2 + MAX_SKIER_RIDES_PER_DAY;
  private static final String RAW_LIFT_RIDES_FILE_INDEX_STORE = "liftrides_index.dat";
}
