package edu.neu.ccs.cs5010;

public class LiftAnalyser {
  int[] liftArray = new int[40];

  public void addLiftCount(int liftID){
    liftArray[liftID-1]++;
  }

  public int[] getLiftInfo(){
    return liftArray;
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
    int[] liftInfo = liftAnalyser.getLiftInfo();
    for(int i = 0; i < liftInfo.length ;i++){
      System.out.print(liftInfo[i]+" ");
    }
  }
}
