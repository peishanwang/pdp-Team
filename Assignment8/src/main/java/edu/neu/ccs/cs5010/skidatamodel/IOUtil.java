package edu.neu.ccs.cs5010.skidatamodel;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class IOUtil {
  /**
   * Get reader using input file's path.
   *
   * @param filePath input file's path
   * @return file reader
   */
  public static Reader getReader(final String filePath) {
    try {
      return new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find input file: " + filePath, e);
    }
  }

  /**
   * Get writer using output file's path.
   *
   * @param filePath output file's path
   * @return file writer
   */
  public static OutputStream getWriter(final String filePath) {
    try {
      return new FileOutputStream(filePath, false /* create new everytime */);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find output file: " + filePath, e);
    }
  }

  /**
   * Get buffered writer.
   *
   * @param filePath   output file's path
   * @param bufferSize buffer size
   * @return buffered writer.
   */
  static OutputStream getBufferedWriter(final String filePath, final int bufferSize) {
    return new BufferedOutputStream(getWriter(filePath), bufferSize);
  }

  /**
   * Get buffered writer.
   *
   * @param filePath output file's path
   * @return buffered writer.
   */
  static OutputStream getBufferedWriter(final String filePath) {
    return getBufferedWriter(filePath, DEFAULT_WRITE_BUFFER_SIZE);
  }

  /**
   * Get text writer.
   *
   * @param filePath output file's path
   * @return text writer.
   */
  public static Writer getTextWriter(final String filePath) {
    try {
      return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),
              StandardCharsets.UTF_8), DEFAULT_WRITE_BUFFER_SIZE);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find output file: " + filePath, e);
    }
  }

  /**
   * Get Random Access File.
   *
   * @param filePath output file's path
   * @return random access file.
   */
  public static RandomAccessFile getRandomFileAccessor(final String filePath) {
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

  static int DEFAULT_WRITE_BUFFER_SIZE = 4096;
}
