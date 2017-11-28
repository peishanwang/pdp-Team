package edu.neu.ccs.cs5010.ski_data_model;

import java.io.File;

public class HourRidesDataModel extends IDataModel<HourRideData> {
  public HourRidesDataModel(final String baseStorePath,
                            DataSourceOpenMode openMode) {
    super(baseStorePath + File.separator + HOUR_FILE_STORE,
            openMode,
            NUM_FIELDS,
            HourRideData::new);
  }

  public HourRidesDataModel(final String baseStorePath) {
    this(baseStorePath, DataSourceOpenMode.ACCESS_MODEL);
  }

  private static final String HOUR_FILE_STORE = "hours.dat";
  private static final int NUM_FIELDS = 11;
}

