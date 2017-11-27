package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.IOUtil;

import java.io.*;
import java.util.List;

public class TxtGenerator {
  /**
   * Constructor of IoUtil.
   * @param filePath path of input/output file
   */
  public TxtGenerator(final String filePath) {
    this.filePath = filePath;
    writer = IOUtil.getTextWriter(filePath);
  }

  public void write(String result) {
    try {
      writer.write(result + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to write file: " + this.filePath, e);
    }
  }

  public void write(List<String> result) {
    for (int i = 0; i < result.size(); i++) {
      write(result.get(i));
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
