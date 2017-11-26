package edu.neu.ccs.cs5010.ski_data_model;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

public final class IOUtil {
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

  static Reader getBufferedReader(final String filePath, final int bufferSize) {
    return new BufferedReader(getReader(filePath), bufferSize);
  }

  static Reader getBufferedReader(final String filePath) {
    return getBufferedReader(filePath, DEFAULT_READ_BUFFER_SIZE);
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

  static RandomAccessFile getRandomFileAccessor(final String filePath) {
    try {
      return new RandomAccessFile(filePath, "rw");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find input file: " + filePath, e);
    }
  }

  static byte[] intToByteArray(int x) {
    return ByteBuffer.allocate(Integer.BYTES).putInt(x).array();
  }

  static byte[] intArrayToByteArray(int[] x) {
    ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * x.length);
    for (int i : x) {
      buffer.putInt(i);
    }
    return buffer.array();
  }

  private static final int DEFAULT_READ_BUFFER_SIZE = 4096;
  private static final int DEFAULT_WRITE_BUFFER_SIZE = 4096;
}
