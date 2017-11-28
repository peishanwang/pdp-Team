package edu.neu.ccs.cs5010;


import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.neu.ccs.cs5010.skidatamodel.IoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This is a class to parse the information from the .csv file.
 */
public class QueryParser implements IQueryParser {
  private static final Logger LOGGER
      = Logger.getLogger(QueryParser.class.getName());


  @Override
  public List<Query> parseInfo(String path, int numberOfQueries) {
    List<Query> queryList = new ArrayList<>();
    CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    CsvParser parser = new CsvParser(settings);
    List<String[]> rows = parser.parseAll(IoUtil.getReader(path));

    long startTime = System.currentTimeMillis();
    int numRows = 0;
    for (String[] row : rows) {
      queryList.add(new Query(Integer.parseInt(row[0]), Integer.parseInt(row[1])));
      numRows++;
      if (numRows == numberOfQueries) {
        break;
      }
    }
    long endTime = System.currentTimeMillis();
    String str = String.format("time taken to read rows: %1$d ms", endTime - startTime);
    LOGGER.info(str);
    parser.stopParsing();
    return queryList;
  }
}
