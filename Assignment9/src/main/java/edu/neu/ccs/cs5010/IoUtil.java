package edu.neu.ccs.cs5010;

import java.io.*;

public class IoUtil {
  private static final String ENCODING = "UTF-8";
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
      return null;
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
