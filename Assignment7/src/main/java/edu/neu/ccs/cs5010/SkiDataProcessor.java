package edu.neu.ccs.cs5010;


public class SkiDataProcessor implements ISkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";
  private String path;
  private String flag;
  private IResort resort;

  public SkiDataProcessor(String path, String flag) {
    this.path = path;
    this.flag = flag;
    resort = new Resort();
  }

  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
    ISkiDataProcessor processor = new SkiDataProcessor(cmdParser.getCsvFile(), cmdParser.getFlag());
    long startTime = System.currentTimeMillis();
    processor.processData();
    long endTime = System.currentTimeMillis();
    processor.outputResult();
    System.out.println("Time taken: " + String.valueOf(endTime-startTime));
  }

  public void processData() {
    InfoParser infoParser = new InfoParser(resort);
    infoParser.parseInfo(path);
  }

  public void outputResult() {
    IResultWriter writer = new ResultWriter(resort);
    writer.writeResult1();
    writer.writeResult2();
    writer.writeResult3();
  }
}