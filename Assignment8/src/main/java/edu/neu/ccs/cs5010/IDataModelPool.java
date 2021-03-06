package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.DataModelItem;
import edu.neu.ccs.cs5010.skidatamodel.IdataModel;

public interface IDataModelPool<D extends IdataModel<? extends DataModelItem>> {

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
