package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.DataModelItem;
import edu.neu.ccs.cs5010.skidatamodel.IDataModel;

/**
 * Factory interface to create data model.
 * @param <D> type of data model.
 */
public interface IDataModelFactory<D extends IDataModel<? extends DataModelItem>> {
  /**
   * Returns new DataModel.
   * @return new DataModel.
   */
  D create();
}
