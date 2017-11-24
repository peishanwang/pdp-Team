package edu.neu.ccs.cs5010;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DatFileReader {
  private static final int INT_SIZE = 4;
  private static final String MODE = "rw";

  static int readFromRandomAccessFile(String fileName, int unitNum)
      throws FileNotFoundException, IOException {

    // Open the file for reading.
    RandomAccessFile randomFile =
        new RandomAccessFile(fileName, MODE);

    // Move to unitNum - 1 to display unitNum data.
    int byteNum = (unitNum - 1) * INT_SIZE;
    randomFile.seek(byteNum);

    // Read the int stored at this location.
    int result = randomFile.readInt();

    // Close the file.
    randomFile.close();
    return result;
  }
}
