package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HourAnalyser {
  Map<Integer, List<Integer>> mapOfHourToLifts = new HashMap<Integer, List<Integer>>();

  public void addHourAndLift(int hour, int liftId){
    if(mapOfHourToLifts.containsKey(hour)) {
      mapOfHourToLifts.get(hour).add(liftId);
    } else {
      List<Integer> liftList = new ArrayList<>();
      liftList.add(liftId);
      mapOfHourToLifts.put(hour, liftList);
    }
  }

  public Map<Integer, Integer> calculateLiftsPerHour(int hour){
    List<Integer> lifts = mapOfHourToLifts.get(hour);
    Map<Integer, Integer> liftsWithCount = new HashMap<>();
    for(int lift : lifts){
      if(liftsWithCount.containsKey(lift)){
        liftsWithCount.put(lift, liftsWithCount.get(lift) + 1);
      } else {
        liftsWithCount.put(lift, 1);
      }
    }
    return sortLiftsWithCount(liftsWithCount);




  }

  private Map<Integer, Integer> sortLiftsWithCount(Map<Integer, Integer> liftsWithCount) {
    Map<Integer, Integer> sortedLiftsWithCount = new LinkedHashMap<>();
    PriorityQueue<Map.Entry<Integer, Integer>> queue =
            new PriorityQueue<>((e1,e2)->e2.getValue()-e1.getValue());

    for(Map.Entry<Integer, Integer> liftWithCount : liftsWithCount.entrySet() ){
      queue.add(liftWithCount);
    }

    while(!queue.isEmpty()){
      Map.Entry<Integer, Integer> entry = queue.poll();
      sortedLiftsWithCount.put(entry.getKey(), entry.getValue());
    }
    return sortedLiftsWithCount;
  }

  public static void main(String[] args) {
    HourAnalyser hourAnalyser = new HourAnalyser();
    hourAnalyser.addHourAndLift(1, 2);
    hourAnalyser.addHourAndLift(1, 22);
    hourAnalyser.addHourAndLift(2, 12);
    hourAnalyser.addHourAndLift(3, 2);
    hourAnalyser.addHourAndLift(4, 4);
    hourAnalyser.addHourAndLift(6, 3);
    hourAnalyser.addHourAndLift(5, 35);
    hourAnalyser.addHourAndLift(1, 31);
    hourAnalyser.addHourAndLift(2, 32);
    hourAnalyser.addHourAndLift(3, 10);
    hourAnalyser.addHourAndLift(4, 16);
    hourAnalyser.addHourAndLift(5, 10);
    hourAnalyser.addHourAndLift(6, 19);
    hourAnalyser.addHourAndLift(1, 14);
    hourAnalyser.addHourAndLift(2, 20);
    hourAnalyser.addHourAndLift(3, 19);
    hourAnalyser.addHourAndLift(4, 18);
    hourAnalyser.addHourAndLift(5, 17);
    hourAnalyser.addHourAndLift(6, 14);
    hourAnalyser.addHourAndLift(1, 12);
    hourAnalyser.addHourAndLift(2, 11);
    hourAnalyser.addHourAndLift(3, 9);
    hourAnalyser.addHourAndLift(4, 4);
    hourAnalyser.addHourAndLift(5, 25);
    hourAnalyser.addHourAndLift(6, 24);
    hourAnalyser.addHourAndLift(1, 39);
    hourAnalyser.addHourAndLift(2, 38);
    hourAnalyser.addHourAndLift(3, 36);
    hourAnalyser.addHourAndLift(4, 32);
    hourAnalyser.addHourAndLift(5, 1);
    hourAnalyser.addHourAndLift(6, 3);
    hourAnalyser.addHourAndLift(1, 34);
    hourAnalyser.addHourAndLift(2, 2);
    hourAnalyser.addHourAndLift(3, 32);
    hourAnalyser.addHourAndLift(4, 31);
    hourAnalyser.addHourAndLift(5, 30);
    hourAnalyser.addHourAndLift(6, 32);
    hourAnalyser.addHourAndLift(1, 12);
    hourAnalyser.addHourAndLift(3, 22);
    hourAnalyser.addHourAndLift(2, 21);
    hourAnalyser.addHourAndLift(4, 29);
    hourAnalyser.addHourAndLift(5, 28);
    hourAnalyser.addHourAndLift(6, 26);
    hourAnalyser.addHourAndLift(1, 22);
    hourAnalyser.addHourAndLift(2, 12);
    System.out.println("Hour 1");
    hourAnalyser.calculateLiftsPerHour(1);
    System.out.println("Hour 2");
    hourAnalyser.calculateLiftsPerHour(2);
    System.out.println("Hour 3");
    hourAnalyser.calculateLiftsPerHour(3);
    System.out.println("Hour 4");
    hourAnalyser.calculateLiftsPerHour(4);
    System.out.println("Hour 5");
    hourAnalyser.calculateLiftsPerHour(5);
    System.out.println("Hour 6");
    hourAnalyser.calculateLiftsPerHour(6);

  }

}
