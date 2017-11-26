package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import edu.neu.ccs.cs5010.ski_data_model.IOUtil;

import java.util.List;

/**
 * This is a class to write result to file.
 *
 */
public class ResultWriter implements IResultWriter {

  @Override
  public void write(String path, List<Object[]> rows) {
    CsvWriter writer = new CsvWriter(IOUtil.getWriter(path), new CsvWriterSettings());
    writer.writeRows(rows);
    writer.close();
  }
}
