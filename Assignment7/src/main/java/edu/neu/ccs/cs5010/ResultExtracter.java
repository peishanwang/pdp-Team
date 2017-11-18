package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;

public class ResultExtracter implements IResultExtracter {
  private static final int SKIER_NUM = 100;
  IResort resort;

  public ResultExtracter(IResort resort) {
    this.resort = resort;
  }

  public List<Object[]> extractResult1() {
    List<ISkier> topHundredSkier = resort.getTopSkier(SKIER_NUM);
    List<Object[]> result = new ArrayList<>();
    for (int i = 0; i < SKIER_NUM; i++) {
      ISkier currSkier = topHundredSkier.get(i);
      result.add(new String[]{Integer.toString(currSkier.getSkierId()),
          Integer.toString(currSkier.getTotalVertical())});
      System.out.println(Integer.toString(currSkier.getSkierId()) + "," +
          Integer.toString(currSkier.getTotalVertical()));
    }
    return result;
  }
}
