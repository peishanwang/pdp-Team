package edu.neu.ccs.cs5010.ski_data_model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface IDataSource {

  default void create(DataModelItem item) {
    throw new NotImplementedException();
  }

  default DataModelItem read(int itemId) {
    throw new NotImplementedException();
  }

  default void update(int itemId, DataModelItem item) {
    throw new NotImplementedException();
  }

  default void delete(int itemId) {
    throw new NotImplementedException();
  }

  int getNumDataItems();

  void close();
}
