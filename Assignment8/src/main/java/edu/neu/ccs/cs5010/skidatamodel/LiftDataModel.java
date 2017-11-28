package edu.neu.ccs.cs5010.skidatamodel;

import java.io.File;

/**
 * RawLiftRidesDataModel can access each lift's LiftData.
 */
public class LiftDataModel extends IdataModel<LiftData> {
  /**
   * Constructor of LiftDataModel.
   * @param baseStorePath path where this file store.
   * @param openMode mode of accessing this record.
   */
  public LiftDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + File.separator + LIFT_FILE_STORE,
            openMode,
            NUM_FIELDS,
            LiftData::new);
  }

  /**
   * Constructor of LiftDataModel.
   * @param baseStorePath path where this file store.
   */
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
}
