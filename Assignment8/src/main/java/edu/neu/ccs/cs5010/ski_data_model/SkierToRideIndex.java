package edu.neu.ccs.cs5010.ski_data_model;

import java.util.Arrays;

class SkierIndexData extends DataModelItem {
  public SkierIndexData(DataModelItem dataModelItem) {
    super(dataModelItem);
    if (dataModelItem.getNumFields() != SkierToRideIndex.INDEX_NUM_FIELDS) {
      throw new IllegalArgumentException("");
    }
    nextAvailableField = dataModelItem.getFields()[1];
  }
  public SkierIndexData(int[] fields) {
    super(fields);
    if (fields.length != SkierToRideIndex.INDEX_NUM_FIELDS) {
      throw new IllegalArgumentException("");
    }
    nextAvailableField = fields[1];
  }
  public int getSkierId() {
    return getField(0);
  }
  public int[] getRidesId() {
    return Arrays.copyOfRange(getFields(), 2, 2 + nextAvailableField);
  }
  public void addRide(int rideNum) {
    if (rideNum == 0) {
      throw new IllegalStateException("");
    }
    getFields()[2 + nextAvailableField++] = rideNum;
    getFields()[1] = nextAvailableField;
  }
  public static SkierIndexData constructRawLiftRidesData(int skierId,
                                                         int[] ridesId) {
    int[] skierRidesIndexData = new int[SkierToRideIndex.INDEX_NUM_FIELDS];
    skierRidesIndexData[0] = skierId;
    skierRidesIndexData[1] = 0;
    if (ridesId.length > (SkierToRideIndex.INDEX_NUM_FIELDS - 2)) {
      throw new IllegalStateException("unknown index entries");
    }
    for (int i = 0; i < ridesId.length; i++) {
      if (ridesId[i] == 0) {
        skierRidesIndexData[1] = i;
        break;
      }
      skierRidesIndexData[2 + i] = ridesId[i];
    }
    return new SkierIndexData(skierRidesIndexData);
  }
  private int nextAvailableField;
}

public class SkierToRideIndex extends IDSIndex<SkierIndexData> {
  public SkierToRideIndex(final String baseStorePath, DataSourceOpenMode mode) {
    super(baseStorePath + '/' + RAW_LIFT_RIDES_FILE_INDEX_STORE,
            mode,
            INDEX_NUM_FIELDS,
            SkierIndexData::new);
    if (mode == DataSourceOpenMode.CREATE_MODEL) {
      for (int i = 1; i <= MAX_SKIER_INDEX_SIZE; i++) {
          SkierIndexData indexData = SkierIndexData.constructRawLiftRidesData(i, new int[]{});
          addDataInfo(indexData);
      }
    } else if (mode == DataSourceOpenMode.ACCESS_MODEL) {
      int numItems = dataSource.getNumDataItems();
      if (numItems != MAX_SKIER_INDEX_SIZE) {
        throw new IllegalStateException("Num items already present: " + numItems);
      }
    } else {
      throw new IllegalStateException("illegal open state");
    }
  }

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
