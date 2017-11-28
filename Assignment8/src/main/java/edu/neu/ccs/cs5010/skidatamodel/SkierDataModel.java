package edu.neu.ccs.cs5010.skidatamodel;

import java.io.File;

/**
 * SkierDataModel can access skier's vertical and number of rides from file.
 */
public class SkierDataModel extends IDataModel<SkierData> {
  /**
   * Constructor of SkierDataModel.
   * @param baseStorePath path where this file store.
   * @param openMode mode of accessing this record.
   */
  public SkierDataModel(final String baseStorePath, DataSourceOpenMode openMode) {
    super(baseStorePath + File.separator + SKIER_FILE_STORE,
            openMode,
            NUM_FIELDS,
            SkierData::new);
  }

  /**
   * Constructor of SkierDataModel.
   * @param baseStorePath path where this file store.
   */
  public SkierDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  @Override
  public SkierData getDataInfo(int itemId) {
    return super.getDataInfo(itemId - 1 /* rowId */);
  }

  @Override
  public void updateDataInfo(int itemId, SkierData itemData) {
    super.updateDataInfo(itemId - 1 /* rowId */, itemData);
  }

  private static final String SKIER_FILE_STORE = "skier.dat";
  private static final int NUM_FIELDS = 4;

}