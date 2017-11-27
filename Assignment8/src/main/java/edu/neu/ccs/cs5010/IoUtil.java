package edu.neu.ccs.cs5010;

/**
 * This is a class for input and output utility.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public interface IoUtil {

  /**
   * Get reader using input file's path.
   * @param relativePath input file's path
   * @return file reader
   */
  static Reader getReader(String relativePath) {
    try {
      return new InputStreamReader(new FileInputStream(relativePath), "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new IllegalStateException("Unable to read input", e);
    }
  }
}
