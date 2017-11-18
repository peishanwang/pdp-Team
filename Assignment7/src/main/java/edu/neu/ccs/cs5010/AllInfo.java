package edu.neu.ccs.cs5010;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.util.List;

import static edu.neu.ccs.cs5010.IoUtil.getReader;

public class AllInfo implements IAllInfo {

  List<String[]> data;
  String[] headers;

  public AllInfo(String path) {
    CsvParserSettings parserSettings = new CsvParserSettings();
    parserSettings.setLineSeparatorDetectionEnabled(true);
    RowListProcessor rowProcessor = new RowListProcessor();
    parserSettings.setProcessor(rowProcessor);
    parserSettings.setHeaderExtractionEnabled(true);
    CsvParser parser = new CsvParser(parserSettings);
    parser.parse(getReader(path));
    headers = rowProcessor.getHeaders();
    data = rowProcessor.getRows();
  }

  public List<String[]> getData() {
    return data;
  }

  public String[] getHeaders() {
    return headers;
  }
}
