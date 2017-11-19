package edu.neu.ccs.cs5010;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
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
    //the file used in the example uses '\n' as the line separator sequence.
    //the line separator sequence is defined here to ensure systems such as MacOS and Windows
    //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
    settings.getFormat().setLineSeparator("\n");
    // creates a CSV parser
    CsvParser parser = new CsvParser(settings);
    // call beginParsing to read records one by one, iterator-style.
    parser.beginParsing(getReader(path));
    String[] headers = parser.parseNext();

    Map<String, Integer> headerToIndex = parseHeaders(headers);

    String[] row;
    while ((row = parser.parseNext()) != null) {
      parseSkierInfo(row[headerToIndex.get("skier")], row[headerToIndex.get("lift")]);
    }

    // The resources are closed automatically when the end of the input is reached,
    // or when an error happens, but you can call stopParsing() at any time.

    // You only need to use this if you are not parsing the entire content.
    // But it doesn't hurt if you call it anyway.
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

  private void parseLiftInfo(String liftId) {

  }


}
