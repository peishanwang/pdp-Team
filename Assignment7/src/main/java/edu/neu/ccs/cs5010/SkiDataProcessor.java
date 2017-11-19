package edu.neu.ccs.cs5010;


import edu.neu.ccs.cs5010.concurrentski.ConcurrentSki;
import java.io.IOException;

public class SkiDataProcessor implements ISkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private static final String OUTPUT_FILE1 = "skier2.csv";
  private static final String OUTPUT_FILE2 = "lift2.csv";
  private static final String OUTPUT_FILE3 = "hour2.csv";
  private String path;
  private String flag;
  private IResort resort;

  public SkiDataProcessor(String path, String flag) {
    this.path = path;
    this.flag = flag;
    resort = new Resort();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    ICmdParser cmdParser = new CmdParser(args);
    ISkiDataProcessor processor = new SkiDataProcessor(cmdParser.getCsvFile(), cmdParser.getFlag());
    if(cmdParser.getFlag().equals(SEQUENTIAL)) {
      long startTime = System.currentTimeMillis();
      processor.processData();
      long endTime = System.currentTimeMillis();
      processor.outputResult();
      System.out.println("Time taken: " + String.valueOf(endTime - startTime));
    } else if(cmdParser.getFlag().equals(CONCURRENT)){
      ConcurrentSki concurrentSki = new ConcurrentSki();
      concurrentSki.parseFile(cmdParser.getCsvFile());
    }
  }

  public void processData() {
    InfoParser infoParser = new InfoParser(resort);
    infoParser.parseInfo(path);
  }

  public void outputResult() {
    IResultWriter writer = new ResultWriter();
    IResultExtracter extracter = new ResultExtracter(resort);
    writer.write(OUTPUT_FILE1, extracter.extractResult1());
    writer.write(OUTPUT_FILE2, extracter.extractResult2());
    writer.write(OUTPUT_FILE3, extracter.extractResult3());
  }
}