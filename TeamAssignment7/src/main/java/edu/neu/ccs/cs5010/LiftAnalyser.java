package edu.neu.ccs.cs5010;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class LiftAnalyser {
  Map<Integer, Integer> liftMap = new HashMap<>();

  public void addLiftCount(int liftID){
    if(liftMap.containsKey(liftID)){
      liftMap.put(liftID, liftMap.get(liftID) + 1);
    } else {
      liftMap.put(liftID, 1);
    }
  }

  public Map<Integer, Integer> getLiftInfo(){
    return liftMap;
  }

  public static void main(String[] args) {
    LiftAnalyser liftAnalyser = new LiftAnalyser();
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(7);
    liftAnalyser.addLiftCount(8);
    liftAnalyser.addLiftCount(3);
    liftAnalyser.addLiftCount(9);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(1);
    liftAnalyser.addLiftCount(5);
    liftAnalyser.addLiftCount(8);
    liftAnalyser.addLiftCount(9);
    liftAnalyser.addLiftCount(10);
    liftAnalyser.addLiftCount(10);
    liftAnalyser.addLiftCount(11);
    liftAnalyser.addLiftCount(18);
    Map<Integer, Integer> liftInfo = liftAnalyser.getLiftInfo();
    for(Map.Entry<Integer, Integer> entry : liftInfo.entrySet()){
      System.out.println(entry.getKey());
      System.out.println(entry.getValue());
    }
  }
}
