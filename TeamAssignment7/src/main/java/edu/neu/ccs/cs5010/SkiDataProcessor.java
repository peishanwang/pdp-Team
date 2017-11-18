package edu.neu.ccs.cs5010;


public class SkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private IAllInfo allInfo;
  private String flag;

  public SkiDataProcessor(String path, String flag) {
    allInfo = new AllInfo(path);
    this.flag = flag;
  }

  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
  }

  public void processData() {
    if (flag.equals(SEQUENTIAL)) {

    }

  }
}