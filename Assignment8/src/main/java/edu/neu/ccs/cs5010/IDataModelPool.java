package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.IDataModel;

public interface IDataModelPool<D extends IDataModel<? extends DataModelItem>> {

  /**
   * Returns requested model.
   * @return requested model.
   */
  D requestModel();

  /**
   * Return model to the model pool.
   * @param model model to be returned.
   */
  void returnModel(D model);

  /**
   * Close the DataModel.
   */
  void close();
}
