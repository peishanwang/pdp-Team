package edu.neu.ccs.cs5010.skidatamodel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


public abstract class IdataModel<T extends DataModelItem> {
  /**
   * IdataModel constructor.
   * @param sourcePath path source.
   * @param openMode mode.
   * @param numFields number of fields.
   * @param factory factory to create.
   */
  public IdataModel(final String sourcePath,
                    DataSourceOpenMode openMode,
                    int numFields,
                    DataModelItemFactory<T> factory) {
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
    this.itemFactory = factory;
  }


  /**
   * method to add data.
   * @param itemData data to be added.
   */
  public void addDataInfo(T itemData) {
    dataSource.create(itemData);
  }

  /**
   * method to get data info.
   * @param itemId id of item.
   * @return info of data.
   */
  public T getDataInfo(int itemId) {
    DataModelItem item = null;
    item = dataSource.read(itemId);
    return itemFactory.create(item);
  }

  public List<T> getDataListInfo(int itemId) {
    throw new NotImplementedException();
  }

  void updateDataInfo(int itemId, T itemData) {
    dataSource.update(itemId, itemData);
  }

  /**
   * method to close connection.
   */
  public void close() {
    synchronized (dataSource) {
      dataSource.close();
    }
  }

  private final DataModelItemFactory<T> itemFactory;
  protected final DataSource dataSource;
}
