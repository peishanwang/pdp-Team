package edu.neu.ccs.cs5010;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.neu.ccs.cs5010.concurrentsystem.SkiHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static edu.neu.ccs.cs5010.IoUtil.getReader;


/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */

public class InfoParser {
  private Map<String, Integer> headerToIndex;
  public InfoParser() {
  }

  public void parseInfo(String path, IRideInfoConsumer rideInfoConsumer) {
    CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    CsvParser parser = new CsvParser(settings);
    parser.beginParsing(getReader(path));
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

  private RideInfo parseRideInfoRow(String[] row) {
    return new RideInfoBuilder()
            .setResortId(getRowItemInt(row, "resort"))
            .setDay(getRowItemInt(row, "day"))
            .setSkier(getRowItemInt(row, "skier"))
            .setLiftId(getRowItemInt(row, "lift"))
            .setTime(getRowItemInt(row, "time"))
            .build();
  }

  private Integer getRowItemInt(String[] row, String colName) {
    return Integer.parseInt(getRowItem(row, colName));
  }

  private String getRowItem(String[] row, String colName) {
    Integer colIdx = headerToIndex.get(colName);
    if (colIdx == null) {
      throw new IllegalArgumentException("");
    }
    return row[colIdx];
  }

  private void parseHeaders(String[] headers) {
    headerToIndex = new HashMap<>();
    for (int i = 0; i < headers.length; i++) {
      headerToIndex.put(headers[i], i);
    }
  }
}
