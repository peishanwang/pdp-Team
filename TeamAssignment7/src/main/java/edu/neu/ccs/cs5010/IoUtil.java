package edu.neu.ccs.cs5010;

import java.io.*;

public interface IoUtil {

  static Reader getReader(String relativePath){
    try {
      return new InputStreamReader(new FileInputStream(relativePath), "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new IllegalStateException("Unable to read input", e);
    }
  }

  static Writer getWriter(String relativePath){
    try {
      return new OutputStreamWriter(new FileOutputStream(relativePath), "UTF-8");
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      throw new IllegalStateException("Unable to read input", e);
    }
  }
}
