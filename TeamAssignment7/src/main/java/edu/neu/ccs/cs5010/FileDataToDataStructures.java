package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class FileDataToDataStructures {
  private Map<Integer, Skier> integerToSkierMap;
  private Map<Integer, Lift> integerLiftMap;
  private Map<Skier, Lift> skierLiftMap;
  private List<String[]> fileData;
  private Map<Skier, List<Lift>> mapOfSkiers;

  public FileDataToDataStructures(List<String[]> allInfo) {
    this.fileData = allInfo;
    integerLiftMap = new HashMap<>();
    integerToSkierMap = new HashMap<>();
    skierLiftMap = new HashMap<>();
    mapOfSkiers = new HashMap<>();
  }

  public void generateMaps(){
    Skier skier;
    Lift lift;
    for(String[] fileRow : fileData){
      int skierId = Integer.valueOf(fileRow[2]);
      int liftId = Integer.valueOf(fileRow[3]);
      if(!integerToSkierMap.containsKey(skierId)){
        skier = new Skier(skierId);
        integerToSkierMap.put(skierId, skier);
      }

      if(!integerLiftMap.containsKey(liftId)){
        lift = new Lift(liftId);
        integerLiftMap.put(liftId, lift);
      }

      skierLiftMap.put(integerToSkierMap.get(skierId), integerLiftMap.get(liftId));
    }
  }

  public void SkierAnalyser(){
    for(Map.Entry<Skier, Lift> skierLiftEntry : skierLiftMap.entrySet()){
      if(mapOfSkiers.containsKey(skierLiftEntry.getKey())) {
        mapOfSkiers.get(skierLiftEntry.getKey()).add(skierLiftEntry.getValue());
      } else {
        List<Lift> liftList = new ArrayList<>();
        liftList.add(skierLiftEntry.getValue());
        mapOfSkiers.put(skierLiftEntry.getKey(), liftList);
      }
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



}
