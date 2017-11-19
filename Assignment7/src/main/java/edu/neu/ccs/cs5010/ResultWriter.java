package edu.neu.ccs.cs5010;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;


public class ResultWriter implements IResultWriter {
  private IResultExtracter extracter;

  public ResultWriter(IResort resort) {
    extracter = new ResultExtracter(resort);
  }

  public void writeResult1() {
    CsvWriter writer = new CsvWriter(IoUtil.getWriter("skier2.csv"), new CsvWriterSettings());

    // Write the record headers of this file
    writer.writeHeaders("SkierId", "Vertical");

    // Here we just tell the writer to write everything and close the given output Writer instance.
    writer.writeRows(extracter.extractResult1());
    writer.close();
  }

  public void writeResult2() {
    CsvWriter writer = new CsvWriter(IoUtil.getWriter("lift2.csv"), new CsvWriterSettings());

    // Write the record headers of this file
    writer.writeHeaders("LiftID", "NumberOfRides");

    // Here we just tell the writer to write everything and close the given output Writer instance.
    writer.writeRows(extracter.extractResult2());
    writer.close();
  }

  public void writeResult3() {
    CsvWriter writer = new CsvWriter(IoUtil.getWriter("hour2.csv"), new CsvWriterSettings());
    // Here we just tell the writer to write everything and close the given output Writer instance.
    writer.writeRows(extracter.extractResult3());
    writer.close();
  }

}
