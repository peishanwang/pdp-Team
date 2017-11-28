package edu.neu.ccs.cs5010.skidatamodel;

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
    if (dataItem == null) {
      dataItem = super.read(itemId);
      // update in cache if not already present
      cache.put(itemId, dataItem);
    }
    return dataItem;
  }

  @Override
  public void update(int itemId, DataModelItem newValue) {
    super.update(itemId, newValue);
    // update in cache
    cache.put(itemId, newValue);
  }

  private LRUCache<Integer, DataModelItem> cache;
  private static final int MAX_CACHE_ITEMS = 1000;
}
