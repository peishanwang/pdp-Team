package edu.neu.ccs.cs5010.ski_data_model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

interface Factory<T extends DataModelItem> {
  T create(DataModelItem item);
}

public abstract class IDataModel<T extends DataModelItem> {
  public IDataModel(final String sourcePath,
                    DataSourceOpenMode openMode,
                    int numFields,
                    Factory<T> factory) {
    if (openMode == DataSourceOpenMode.CREATE_MODEL) {
      this.dataSource = new DataSource(
            sourcePath,
            openMode,
            numFields);
    } else if (openMode == DataSourceOpenMode.ACCESS_MODEL) {
      this.dataSource = new CachedDataSource(
              sourcePath,
              numFields);
    } else {
      throw new IllegalStateException("invalid state.");
    }
    this.openMode = openMode;
    this.itemFactory = factory;
  }


  public void addDataInfo(T itemData) {
    synchronized (dataSource) {
      dataSource.create(itemData);
    }
  }

  public T getDataInfo(int itemId) {
    DataModelItem item = null;
    synchronized (dataSource) {
      item = dataSource.read(itemId);
    }
    return itemFactory.create(item);
  }

  public List<T> getDataListInfo(int itemId) {
    throw new NotImplementedException();
  }

  void updateDataInfo(int itemId, T itemData) {
    synchronized (dataSource) {
      dataSource.update(itemId, itemData);
    }
  }

  DataSourceOpenMode getDSMode() {return openMode;}

  public void close() {
    synchronized (dataSource) {
      dataSource.close();
    }
  }

  private final Factory<T> itemFactory;
  private final DataSourceOpenMode openMode;
  protected final DataSource dataSource;
}
