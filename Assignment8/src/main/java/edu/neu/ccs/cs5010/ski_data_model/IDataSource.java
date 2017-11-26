package edu.neu.ccs.cs5010.ski_data_model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface IDataSource {
  default void create(DataModelItem item) {
    throw new NotImplementedException();
  }
  default DataModelItem read(int id) {
    throw new NotImplementedException();
  }
  default void update(int id, DataModelItem item) {
    throw new NotImplementedException();
  }
  default void delete(int id) {
    throw new NotImplementedException();
  }
  int getNumDataItems();
  void close();
}
