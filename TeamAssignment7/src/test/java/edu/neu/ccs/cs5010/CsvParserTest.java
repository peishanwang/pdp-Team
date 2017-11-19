package edu.neu.ccs.cs5010;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.junit.Test;

import java.util.List;

import static edu.neu.ccs.cs5010.IoUtil.getReader;

public class CsvParserTest {



  @Test
  public void test(){

    CsvParserSettings parserSettings = new CsvParserSettings();
    parserSettings.setLineSeparatorDetectionEnabled(true);
    RowListProcessor rowProcessor = new RowListProcessor();
    parserSettings.setProcessor(rowProcessor);
    parserSettings.setHeaderExtractionEnabled(true);
    CsvParser parser = new CsvParser(parserSettings);
    parser.parse(getReader("PDPAssignment.csv"));
    String[] headers = rowProcessor.getHeaders();
    List<String[]> allRows = rowProcessor.getRows();

    for(int i = 0;i < 100; i++){
      for(int j = 0;j < allRows.get(i).length; j++){
        System.out.print("\t" + allRows.get(i)[j]);
      }
      System.out.println();
    }

  }
}
