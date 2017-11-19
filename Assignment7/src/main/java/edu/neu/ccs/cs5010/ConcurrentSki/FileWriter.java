package edu.neu.ccs.cs5010.ConcurrentSki;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class FileWriter {
  public void writeData(List<String> FileDataToWrite, String fileName) throws IOException {
    BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(fileName));
    for(String str : FileDataToWrite){
      writer.write(str);
      writer.write("\n");
    }
    writer.close();

  }
}
