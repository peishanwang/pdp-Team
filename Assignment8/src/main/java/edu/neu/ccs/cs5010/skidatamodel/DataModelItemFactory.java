package edu.neu.ccs.cs5010.skidatamodel;

interface DataModelItemFactory<T extends DataModelItem> {
  T create(DataModelItem item);
}
