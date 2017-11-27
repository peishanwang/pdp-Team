package edu.neu.ccs.cs5010.ski_data_model;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DataSource implements IDataSource {
  public DataSource(final String filePath,
                    DataSourceOpenMode openMode,
                    int numFields) {
    this.openMode = openMode;
    this.numFields = numFields;
    this.fixedColWidth = numFields * Integer.BYTES;
    if (openMode == DataSourceOpenMode.CREATE_MODEL) {
      this.batchFileWriter = new DataSourceFileWriter(filePath, fixedColWidth);
    } else if (openMode == DataSourceOpenMode.ACCESS_MODEL) {
      this.fileAccessor = IoUtil.getRandomFileAccessor(filePath);
      this.numTotalRows = getDataColsInFile();
    } else {
      throw new IllegalArgumentException("Invalid open mode");
    }
  }

  public DataSource(final String filePath, int width) {
    this(filePath, DataSourceOpenMode.ACCESS_MODEL, width);
  }

  @Override
  public void create(DataModelItem item) {
    validateState(DataSourceOpenMode.CREATE_MODEL);
    try {
      this.batchFileWriter.write(item);
    } catch (IOException e) {
      throw new IllegalArgumentException("unable to write", e);
    }
  }

  @Override
  public DataModelItem read(int itemId) {
    validateState(DataSourceOpenMode.ACCESS_MODEL);
    if (itemId > getNumDataItems()) {
      throw new IllegalStateException("invalid read");
    }

    int[] fields = new int[numFields];
    try {
      this.fileAccessor.seek((long)itemId * fixedColWidth);
      for (int i = 0; i < numFields; i++) {
        fields[i] = this.fileAccessor.readInt();
      }
    } catch (IOException e) {
      throw new IllegalStateException("unable to read data", e);
    }
    return new DataModelItem(fields);
  }

  @Override
  public void update(int itemId, DataModelItem newValue) {
    validateState(DataSourceOpenMode.ACCESS_MODEL);
    if (itemId > getNumDataItems()) {
      throw new IllegalStateException("skipping unique key");
    } else if (itemId == getNumDataItems()) {
      this.numTotalRows++;
    }
    try {
      this.fileAccessor.seek((long)itemId * fixedColWidth);
      this.fileAccessor.write(IoUtil.intArrayToByteArray(newValue.getFields()));
    } catch (IOException e) {
      throw new IllegalStateException("unable to write data", e);
    }
  }

  @Override
  public int getNumDataItems() {
    return numTotalRows;
  }

  private int getDataColsInFile() {
    validateState(DataSourceOpenMode.ACCESS_MODEL);
    long fileSize = 0;
    try {
      fileSize = this.fileAccessor.length();
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read file ", e);
    }
    if (fileSize % this.fixedColWidth != 0) {
      throw new IllegalStateException("Illegal file format," + fileSize +
              ", width: " + fixedColWidth);
    }
    return (int)(fileSize / this.fixedColWidth);
  }

  @Override
  public void close() {
    try {
      if (fileAccessor != null) {
        fileAccessor.close();
      }
      if (batchFileWriter != null) {
        batchFileWriter.close();
      }
    } catch (IOException e) {
      throw new IllegalStateException("unable to close file", e);
    }
  }

  protected void validateState(DataSourceOpenMode mode) {
    if (this.openMode != mode) {
      throw new IllegalStateException("invalid mode");
    }
  }

  private RandomAccessFile fileAccessor;
  private DataSourceFileWriter batchFileWriter;
  private final DataSourceOpenMode openMode;
  private final int numFields;
  private final int fixedColWidth;
  private int numTotalRows;
}
