package edu.neu.ccs.cs5010;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.util.List;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class ResultWriter implements IResultWriter {

  public void write(String path, List<Object[]> rows) {
    CsvWriter writer = new CsvWriter(IoUtil.getWriter(path), new CsvWriterSettings());
    writer.writeRows(rows);
    writer.close();
  }
}
