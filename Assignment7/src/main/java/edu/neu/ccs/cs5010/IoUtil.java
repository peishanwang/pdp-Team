package edu.neu.ccs.cs5010;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
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

  static Reader getReader(String relativePath) {
    try {
      return new InputStreamReader(new FileInputStream(relativePath), "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new IllegalStateException("Unable to read input", e);
    }
  }

  static Writer getWriter(String relativePath) {
    try {
      return new OutputStreamWriter(new FileOutputStream(relativePath), "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new IllegalStateException("Unable to read input", e);
    }
  }
}
