package edu.neu.ccs.cs5010.ski_data_model;

public class DataModelItem {
  public DataModelItem(int[] fields) {
    this.fields = fields.clone();
    this.numFields = fields.length;
  }

  public DataModelItem(DataModelItem item) {
    this.fields = item.getFields();
    this.numFields = fields.length;
  }

  public int getKeyColumn() {
    // by default assume index for col 0
    return 0;
  }

  public int getKey() {
    // by default assume index for col 0
    return getField(getKeyColumn());
  }

  public int getNumFields() {
    return numFields;
  }

  public int getField(int index) {
    if (index < 0 || index > this.numFields) {
      throw new IllegalArgumentException("invalid index");
    }
    return this.fields[index];
  }

  public void updateField(int index, int newValue) {
    if (index < 0 || index > this.numFields) {
      throw new IllegalArgumentException("invalid index");
    }
    this.fields[index] = newValue;
  }

  int[] getFields() {
    return fields;
  }

  private int[] fields;
  private final int numFields;
}
