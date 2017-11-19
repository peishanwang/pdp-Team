package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SkierAnalyser {
  private Map<Skier, List<Lift>> mapOfSkiers;

  public SkierAnalyser(){
    mapOfSkiers = new HashMap<>();
  }

  public void addSkierToMap(Skier skier, Lift lift){
    if(mapOfSkiers.containsKey(skier)) {
      mapOfSkiers.get(skier).add(lift);
    } else {
      List<Lift> liftList = new ArrayList<>();
      liftList.add(lift);
      mapOfSkiers.put(skier, liftList);
    }
  }

  public Map<Skier, Integer> analyseSkierInformation(){
    Map<Skier, Integer> mapOfSkierToLiftInfo = new HashMap<>();
    for(Skier skier : mapOfSkiers.keySet()){
      int verticalMetersOfSkier = computeInfoOfSkier(mapOfSkiers.get(skier));
      mapOfSkierToLiftInfo.put(skier, verticalMetersOfSkier);
    }
    return mapOfSkierToLiftInfo;
  }

  private int computeInfoOfSkier(List<Lift> lifts) {
    int verticalMeters = 0;
    for(Lift lift : lifts){
      if(lift.getLiftId() <= 10){
        verticalMeters += 200;
      } else if(lift.getLiftId() <= 20){
        verticalMeters += 300;
      } else if(lift.getLiftId() <= 30){
        verticalMeters += 400;
      } else if(lift.getLiftId() <= 40){
        verticalMeters += 500;
      }
    }
    return verticalMeters;
  }

  public Map<Skier, Integer> topTenVerticalMeters(Map<Skier, Integer> skierInformation){
    Map<Skier, Integer> topSkiersInfo = new LinkedHashMap<>();
    PriorityQueue<Map.Entry<Skier, Integer>> queue =
        new PriorityQueue<>((e1, e2) -> e2.getValue() - e1.getValue());

    for(Map.Entry<Skier, Integer> skierEntry : skierInformation.entrySet() ){
      queue.add(skierEntry);
    }


    for(int i=0;i<5;i++){
      Map.Entry<Skier, Integer> skierInfo = queue.poll();
      topSkiersInfo.put(skierInfo.getKey(), skierInfo.getValue());
    }

    return topSkiersInfo;
  }



  //testing
  public static void main(String[] args) {
    SkierAnalyser skierAnalyser = new SkierAnalyser();
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    skierAnalyser.addSkierToMap(new Skier(1), new Lift(4));
    Map<Skier, Integer> topSkiersInfo = skierAnalyser.topTenVerticalMeters(skierAnalyser.analyseSkierInformation());
    for(Map.Entry<Skier, Integer> topSkier : topSkiersInfo.entrySet()){
      System.out.println(topSkier.getKey().getSkierId() + " " + topSkier.getValue());
    }
  }

}
