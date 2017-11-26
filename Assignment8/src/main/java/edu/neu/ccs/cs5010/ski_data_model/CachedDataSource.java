package edu.neu.ccs.cs5010.ski_data_model;

public class CachedDataSource extends DataSource {
  public CachedDataSource(String filePath, DataSourceOpenMode openMode, int numFields) {
    super(filePath, openMode, numFields);
    cache = new LRUCache<>(MAX_CACHE_ITEMS);
  }

  public CachedDataSource(String filePath, int width) {
    this(filePath, DataSourceOpenMode.ACCESS_MODEL, width);
  }

  @Override
  public DataModelItem read(int itemId) {
    DataModelItem dataItem = cache.get(itemId);
    return dataItem != null ? dataItem : super.read(itemId);
  }

  @Override
  public void update(int itemId, DataModelItem newValue) {
    super.update(itemId, newValue);
    cache.put(itemId, newValue);
  }

  private LRUCache<Integer, DataModelItem> cache;
  private static final int MAX_CACHE_ITEMS = 1000;
}
