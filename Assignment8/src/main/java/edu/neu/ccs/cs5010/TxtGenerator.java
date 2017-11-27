package edu.neu.ccs.cs5010;

import java.io.*;
import java.util.List;

public class TxtGenerator {
  private String path;
  private String encoding;

  /**
   * Constructor of IoUtil.
   * @param path path of input/output file
   * @param encoding encoding type used when read/write text from/to file
   */
  public TxtGenerator(String path, String encoding) {
    this.path = path;
    this.encoding = encoding;
  }

  public void generateOutput(List<String> result, String fileName) {
    File directory = new File(path);
    try {
      boolean folderExisted = directory.exists() || directory.mkdirs();
      if (!folderExisted) {
        throw new IOException("Unable to create path");
      }
      OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + fileName),
          encoding);
      BufferedWriter bfw = new BufferedWriter(osw);
      for (int i = 0; i < result.size(); i++) {
        bfw.write(result.get(i));
        bfw.newLine();
      }
      bfw.close();
      System.out.println(fileName + " Done");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
