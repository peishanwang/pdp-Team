package edu.neu.ccs.cs5010.skidatamodel;

import java.io.File;

public class LiftDataModel extends IDataModel<LiftData> {

  /**
   * LiftDataModel constructor.
   * @param baseStorePath base directory path.
   * @param openMode mode-create or access
   */
  public LiftDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + File.separator + LIFT_FILE_STORE,
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
}
