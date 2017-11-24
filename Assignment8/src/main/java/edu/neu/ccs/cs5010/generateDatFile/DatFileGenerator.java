package edu.neu.ccs.cs5010.generateDatFile;

import java.io.*;
import java.util.List;

public class DatFileGenerator {

  public static void writeToRandomAccessFile(List<int[]> data, String fileName)
      throws IOException, FileNotFoundException {

    // Open a file for reading and writing.
    RandomAccessFile randomFile =
        new RandomAccessFile("Letters.dat", "rw");

    System.out.println("Writing to the file " + fileName);

    // Write the elements to the binary file.
    for (int[] line : data) {
      for (int element : line) {
        randomFile.writeInt(element);
      }
    }

    // Close the file.
    randomFile.close();
    System.out.println("   ***Done with writing to random access binary file." + fileName);
  }
}
