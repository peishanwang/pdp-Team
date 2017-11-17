package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SkierAnalyser {
  private Map<Integer, List<Integer>> mapOfSkiers;

  public SkierAnalyser(){
    mapOfSkiers = new HashMap<>();
  }

  public void addSkierToMap(int skierId, int liftId){
    if(mapOfSkiers.containsKey(skierId)) {
      mapOfSkiers.get(skierId).add(liftId);
    } else {
      List<Integer> liftList = new ArrayList<>();
      liftList.add(liftId);
      mapOfSkiers.put(skierId, liftList);
    }
  }

  public Map<Integer, Integer> analyseSkierInformation(){
    Map<Integer, Integer> mapOfSkierToLiftInfo = new HashMap<>();
    for(int skier : mapOfSkiers.keySet()){
      int verticalMetersOfSkier = computeInfoOfSkier(mapOfSkiers.get(skier));
      mapOfSkierToLiftInfo.put(skier, verticalMetersOfSkier);
    }
    return mapOfSkierToLiftInfo;
  }

  private int computeInfoOfSkier(List<Integer> lifts) {
    int verticalMeters = 0;
    for(int lift : lifts){
      if(lift <= 10){
        verticalMeters += 200;
      } else if(lift <= 20){
        verticalMeters += 300;
      } else if(lift <= 30){
        verticalMeters += 400;
      } else if(lift <= 40){
        verticalMeters += 500;
      }
    }
    return verticalMeters;
  }

  public Map<Integer, Integer> topTenVerticalMeters(Map<Integer, Integer> skierInformation){
    Map<Integer, Integer> topSkiersInfo = new LinkedHashMap<>();
    PriorityQueue<Map.Entry<Integer, Integer>> queue =
            new PriorityQueue<Map.Entry<Integer, Integer>>((e1,e2)->e2.getValue()-e1.getValue());

    for(Map.Entry<Integer, Integer> skierEntry : skierInformation.entrySet() ){
      queue.add(skierEntry);
    }


    for(int i=0;i<10;i++){
      Map.Entry<Integer, Integer> skierInfo = queue.poll();
      topSkiersInfo.put(skierInfo.getKey(), skierInfo.getValue());
    }

    return topSkiersInfo;
  }



  //testing
  public static void main(String[] args) {
    SkierAnalyser skierAnalyser = new SkierAnalyser();
    skierAnalyser.addSkierToMap(1, 4);
    skierAnalyser.addSkierToMap(1, 8);
    skierAnalyser.addSkierToMap(1, 9);
    skierAnalyser.addSkierToMap(2, 14);
    skierAnalyser.addSkierToMap(8, 98);
    skierAnalyser.addSkierToMap(19, 13);
    skierAnalyser.addSkierToMap(12, 33);
    skierAnalyser.addSkierToMap(9, 6);
    skierAnalyser.addSkierToMap(29, 15);
    skierAnalyser.addSkierToMap(54, 26);
    skierAnalyser.addSkierToMap(9, 27);
    skierAnalyser.addSkierToMap(89, 28);
    skierAnalyser.addSkierToMap(19, 35);
    skierAnalyser.addSkierToMap(32, 25);
    Map<Integer, Integer> topSkiersInfo = skierAnalyser.topTenVerticalMeters(skierAnalyser.analyseSkierInformation());
    for(Map.Entry<Integer, Integer> topSkier : topSkiersInfo.entrySet()){
      System.out.println(topSkier.getKey() + " " + topSkier.getValue());
    }
  }

}
