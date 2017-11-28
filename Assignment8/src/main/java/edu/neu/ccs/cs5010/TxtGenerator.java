package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.IoUtil;

import java.io.IOException;
import java.io.Writer;

/**
 * TxtGenerator is used to generate .txt output.
 */
public class TxtGenerator {
  /**
   * Constructor of TxtGenerator.
   * @param filePath path of input/output file
   */
  public TxtGenerator(final String filePath) {
    this.filePath = filePath;
    writer = IoUtil.getTextWriter(filePath);
  }

  /**
   * Write one line to .txt file.
   * @param result a line of String
   */
  public void writeLine(String result) {
    try {
      writer.write(result + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Unable to write file: " + this.filePath, e);
    }
  }

  /**
   * Close the writer.
   */
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
