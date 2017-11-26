package edu.neu.ccs.cs5010.ski_data_model;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

public class DataSourceFileWriter implements Closeable {
  public DataSourceFileWriter(String filePath, int width) {
    fixedColWidth = width;
    writer = IOUtil.getBufferedWriter(filePath);
    numRowsWritten = 0;
  }

  public void write(DataModelItem data) throws IOException {
    if (data.getNumFields() * Integer.BYTES != fixedColWidth) {
      throw new IllegalArgumentException("Invalid width for data being written");
    }
    writer.write(IOUtil.intArrayToByteArray(data.getFields()));
    numRowsWritten++;
  }

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