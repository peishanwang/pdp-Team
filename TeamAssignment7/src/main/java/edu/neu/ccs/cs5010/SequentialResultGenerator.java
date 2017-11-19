package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.Map;

public class SequentialResultGenerator implements IResultGenerator {
  List<String[]> allFileData;

  public SequentialResultGenerator(List<String[]> allInfo){
    this.allFileData = allInfo;
  }

  @Override
  public Map<Skier, Integer> getSkierInfo() {
    SkierAnalyser skierAnalyser = new SkierAnalyser();
    for(int i = 0; i < allFileData.size() ;i++) {
      Skier skier = new Skier(Integer.valueOf(allFileData.get(i)[2]));
      Lift lift = new Lift(Integer.valueOf(allFileData.get(i)[3]));
      skierAnalyser.addSkierToMap(skier, lift);
    }

    return skierAnalyser.topTenVerticalMeters(skierAnalyser.analyseSkierInformation());
  }

  @Override
  public Map<Integer, Integer> getLiftInfo() {
    return null;
  }

  @Override
  public List<Map<Integer, Integer>> getHourInfo() {
    return null;
    //get map from hour analyser and append everything to list.
  }

//  public static void main(String[] args) {
//    SequentialResultGenerator sequentialResultGenerator = new SequentialResultGenerator(new AllInfo("PDPAssignment.csv").getData());
//    Map<Skier, Integer> allInfo = sequentialResultGenerator.getSkierInfo();
//    for(Map.Entry<Skier, Integer> entry : allInfo.entrySet()){
//      System.out.println(entry.getKey().getSkierId());
//      System.out.println(entry.getValue());
//    }
//  }
}
