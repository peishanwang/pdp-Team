package edu.neu.ccs.cs5010;

import java.io.IOException;

public class SkiQueryProcessor {


  public static void main(String[] args) {
    try {
      System.out.println(DatFileReader.readFromRandomAccessFile("skier.dat",2));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
