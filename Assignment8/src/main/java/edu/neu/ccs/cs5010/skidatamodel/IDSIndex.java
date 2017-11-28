package edu.neu.ccs.cs5010.skidatamodel;

public abstract class IDSIndex<T extends DataModelItem> extends IDataModel<T> {

  public IDSIndex(final String sourcePath,
                  DataSourceOpenMode openMode,
                  int numFields,
                  DataModelItemFactory<T> factory) {
    super(sourcePath, openMode, numFields, factory);
  }

  abstract int getIndexColumn();
}
