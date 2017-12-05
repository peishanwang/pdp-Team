package edu.neu.ccs.cs5010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * IoUtil class is used to handle input and output stuff.
 */
public class IoUtil {
  private static final String ENCODING = "UTF-8";
  private static final String EMPTY_STRING = "";
  private InputStream inputStream;
  private OutputStream outputStream;

  public IoUtil(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public IoUtil(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  protected String getLine() {
    try {
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
      return stdIn.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return EMPTY_STRING;
    }
  }

  protected void writeLine(String line) {
    try {
      PrintWriter out = new PrintWriter(new OutputStreamWriter(
          outputStream, ENCODING), true);
      out.println(line);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
