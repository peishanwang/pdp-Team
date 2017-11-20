package edu.neu.ccs.cs5010;


import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.util.HashMap;
import java.util.Map;

import static edu.neu.ccs.cs5010.IoUtil.getReader;

/**
 * This is a class to parse the information from the .csv file.
 *
 */

public class InfoParser implements IInfoParser {
  private Map<String, Integer> headerToIndex;

  @Override
  public void parseInfo(String path, IRideInfoConsumer rideInfoConsumer) {
    CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    CsvParser parser = new CsvParser(settings);
    parser.beginParsing(IoUtil.getReader(path));
    String[] headers = parser.parseNext();
    parseHeaders(headers);
    String[] row;

    long startTime = System.currentTimeMillis();
    while ((row = parser.parseNext()) != null) {
      rideInfoConsumer.accept(parseRideInfoRow(row));
    }
    long endTime = System.currentTimeMillis();
    System.out.println("time taken to consume rows: " + (endTime - startTime));
    parser.stopParsing();
    rideInfoConsumer.stop();
  }

  /**
   * Parse information for each row.
   * @param row String array containing a row's information.
   * @return RideInfo object containing information of this row.
   */
  private RideInfo parseRideInfoRow(String[] row) {
    return new RideInfoBuilder()
            .setResortId(getRowItemInt(row, "resort"))
            .setDay(getRowItemInt(row, "day"))
            .setSkier(getRowItemInt(row, "skier"))
            .setLiftId(getRowItemInt(row, "lift"))
            .setTime(getRowItemInt(row, "time"))
            .build();
  }

  /**
   * Get the header's index using it's name.
   * @param row String array containing a row's information.
   * @param colName header's name.
   * @return index of the header.
   */
  private int getRowItemInt(String[] row, String colName) {
    Integer colIdx = headerToIndex.get(colName);
    if (colIdx == null) {
      throw new IllegalArgumentException("");
    }
    return Integer.parseInt(row[colIdx]);
  }

  /**
   * Parse headers of the .csv file and store the information in a HashMap.
   * @param headers String array containing headers' information.
   */
  private void parseHeaders(String[] headers) {
    headerToIndex = new HashMap<>();
    for (int i = 0; i < headers.length; i++) {
      headerToIndex.put(headers[i], i);
    }
  }
}
