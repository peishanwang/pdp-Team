package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.IDataModel;

public interface IDataModelPool<DM extends IDataModel<? extends DataModelItem>> {
  DM requestModel();
  void returnModel(DM model);
  void close();
}
