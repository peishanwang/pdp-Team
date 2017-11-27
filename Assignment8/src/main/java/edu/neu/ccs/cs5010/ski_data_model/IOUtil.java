package edu.neu.ccs.cs5010.ski_data_model;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public interface IoUtil {
  /**
   * Get reader using input file's path.
   * @param filePath input file's path
   * @return file reader
   */
  static Reader getReader(final String filePath) {
    try {
      return new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find input file: " + filePath, e);
    }
  }

  /**
   * Get writer using output file's path.
   * @param filePath output file's path
   * @return file writer
   */
  static OutputStream getWriter(final String filePath) {
    try {
      return new FileOutputStream(filePath, false /* create new everytime */);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find output file: " + filePath, e);
    }
  }

  static OutputStream getBufferedWriter(final String filePath, final int bufferSize) {
    return new BufferedOutputStream(getWriter(filePath), bufferSize);
  }

  static OutputStream getBufferedWriter(final String filePath) {
    return getBufferedWriter(filePath, DEFAULT_WRITE_BUFFER_SIZE);
  }

  static Writer getTextWriter(final String filePath) {
    try {
      return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),
              StandardCharsets.UTF_8), DEFAULT_WRITE_BUFFER_SIZE);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find output file: " + filePath, e);
    }
  }

  static RandomAccessFile getRandomFileAccessor(final String filePath) {
    try {
      return new RandomAccessFile(filePath, "rw");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find input file: " + filePath, e);
    }
  }

  static byte[] intArrayToByteArray(int[] num) {
    ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * num.length);
    for (int i : num) {
      buffer.putInt(i);
    }
    return buffer.array();
  }

  int DEFAULT_WRITE_BUFFER_SIZE = 4096;
}
