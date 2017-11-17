package edu.neu.ccs.cs5010;


public class SkiDataProcessor {
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

  }








}
