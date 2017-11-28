package edu.neu.ccs.cs5010.ski_data_model;

public abstract class IDSIndex<T extends DataModelItem> extends IDataModel<T> {

  public IDSIndex(final String sourcePath,
                  DataSourceOpenMode openMode,
                  int numFields,
                  DataModelItemFactory<T> factory) {
    super(sourcePath, openMode, numFields, factory);
  }

  abstract int getIndexColumn();
}
