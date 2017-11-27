package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.IoUtil;

import java.io.*;

public class TxtGenerator {
  /**
   * Constructor of IoUtil.
   * @param filePath path of input/output file
   */
  public TxtGenerator(final String filePath) {
    this.filePath = filePath;
    writer = IoUtil.getTextWriter(filePath);
  }

  public void writeLine(String result) {
    try {
      writer.write(result + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Unable to write file: " + this.filePath, e);
    }
  }

  public void close() {
    try {
      writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unable to close output file: " + filePath, e);
    }
  }

  private final String filePath;
  private final Writer writer;
}
