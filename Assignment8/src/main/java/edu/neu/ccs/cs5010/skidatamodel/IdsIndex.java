package edu.neu.ccs.cs5010.skidatamodel;

public abstract class IdsIndex<T extends DataModelItem> extends IdataModel<T> {

  public IdsIndex(final String sourcePath,
                  DataSourceOpenMode openMode,
                  int numFields,
                  DataModelItemFactory<T> factory) {
    super(sourcePath, openMode, numFields, factory);
  }

  abstract int getIndexColumn();
}
