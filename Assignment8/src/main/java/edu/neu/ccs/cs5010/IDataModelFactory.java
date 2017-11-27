package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.IDataModel;

public interface IDataModelFactory<DM extends IDataModel<? extends DataModelItem>> {
  DM create();
}
