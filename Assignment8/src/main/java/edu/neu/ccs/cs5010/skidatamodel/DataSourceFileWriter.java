package edu.neu.ccs.cs5010.skidatamodel;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class DataSourceFileWriter implements Closeable {

  /**
   * DataSourceFileWriter Constructor.
   * @param filePath path of file.
   * @param width width of data source.
   */
  public DataSourceFileWriter(String filePath, int width) {
    fixedColWidth = width;
    writer = IoUtil.getBufferedWriter(filePath);
    numRowsWritten = 0;
  }

  /**
   * method to write data to file.
   * @param data datamodel to be written
   * @throws IOException IOException constructor
   */
  public void write(DataModelItem data) throws IOException {
    if (data.getNumFields() * Integer.BYTES != fixedColWidth) {
      throw new IllegalArgumentException("Invalid width for data being written");
    }
    writer.write(IoUtil.intArrayToByteArray(data.getFields()));
    numRowsWritten++;
  }

  /**
   * method to return number of rows that written.
   * @return number of rows written.
   */
  public int getNumRowsWritten() {
    return numRowsWritten;
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }

  private int numRowsWritten;
  private final OutputStream writer;
  private final int fixedColWidth;
}
