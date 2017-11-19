package edu.neu.ccs.cs5010;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.neu.ccs.cs5010.ConcurrentSki.LiftHourQueueItem;
import edu.neu.ccs.cs5010.ConcurrentSki.SkiHelper;

import java.util.HashMap;
import java.util.Map;

import static edu.neu.ccs.cs5010.IoUtil.getReader;

public class InfoParser {

  private IResort resort;

  public InfoParser(IResort resort) {
    this.resort = resort;
  }

  public void parseInfo(String path) {
    CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    CsvParser parser = new CsvParser(settings);
    parser.beginParsing(getReader(path));
    String[] headers = parser.parseNext();
    Map<String, Integer> headerToIndex = parseHeaders(headers);
    String[] row;
    while ((row = parser.parseNext()) != null) {
      parseSkierInfo(row[headerToIndex.get("skier")], row[headerToIndex.get("lift")]);
      parseLiftInfo(row[headerToIndex.get("lift")], row[headerToIndex.get("time")]);
    }
    parser.stopParsing();
  }

  private Map<String, Integer> parseHeaders(String[] headers) {
    Map<String, Integer> headerToIndex = new HashMap<>();
    for (int i = 0; i < headers.length; i++) {
      headerToIndex.put(headers[i], i);
    }
    return headerToIndex;
  }

  private void parseSkierInfo(String skierId, String liftId) {
    int skierIdValue = Integer.parseInt(skierId);
    int liftIdValue = Integer.parseInt(liftId);
    resort.addSkierVertical(skierIdValue,
        SkiHelper.getVerticalDistanceMetres(liftIdValue));
  }

  private void parseLiftInfo(String liftId, String time) {
    int liftIdValue = Integer.parseInt(liftId);
    int timeValue = Integer.parseInt(time);
    resort.addLiftRide(liftIdValue);
    resort.addLiftRideWithTime(liftIdValue, (timeValue - 1)/60);
  }

}
