package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.IDataModel;

public interface IDataModelPool<D extends IDataModel<? extends DataModelItem>> {

  D requestModel();

  void returnModel(D model);

  void close();
}
