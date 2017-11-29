package edu.neu.ccs.cs5010.skidatamodel;

public class DataModelItem {
  /**
   * DataModelItem constructor.
   * @param fields fields of data model.
   */
  public DataModelItem(int[] fields) {
    this.fields = fields.clone();
    this.numFields = fields.length;
  }

  public DataModelItem(DataModelItem item) {
    this.fields = item.getFields();
    this.numFields = fields.length;
  }

  /**
   * method to get key of column.
   * @return column key.
   */
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

  /**
   * method to get field .
   * @param index index info.
   * @return field info.
   */
  public int getField(int index) {
    if (index < 0 || index > this.numFields) {
      throw new IllegalArgumentException("invalid index");
    }
    return this.fields[index];
  }

  /**
   * method to update field.
   * @param index index to update.
   * @param newValue newvalue.
   */
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
