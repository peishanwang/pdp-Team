package edu.neu.ccs.cs5010;


public class SkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";

  private String flag;

  public SkiDataProcessor(String path, String flag) {
    this.flag = flag;
  }

  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
  }

  public void processData() {
    
  }
}