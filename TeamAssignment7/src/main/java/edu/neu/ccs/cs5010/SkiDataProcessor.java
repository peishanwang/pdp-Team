package edu.neu.ccs.cs5010;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkiDataProcessor {
  private static final String SEQUENTIAL = "Sequential";
  private static IAllInfo allInfo;
  private String flag;
  public SkiDataProcessor(String path, String flag) {
    allInfo = new AllInfo(path);
    this.flag = flag;
  }

  public static void main(String[] args) {
    ICmdParser cmdParser = new CmdParser(args);
    FileDataToDataStructures fileDataToDataStructures = new FileDataToDataStructures(allInfo.getData());
  }



  public void processData() {
    IResultGenerator resultGenerator;
    if (flag.equals(SEQUENTIAL)) {
      resultGenerator = new SequentialResultGenerator(allInfo.getData());
    } else {
      resultGenerator = new ConcurrentResultGenerator();
    }
  }
}