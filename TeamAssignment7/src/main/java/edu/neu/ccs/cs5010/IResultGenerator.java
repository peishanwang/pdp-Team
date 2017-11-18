package edu.neu.ccs.cs5010;

import java.util.Map;

public interface IResultGenerator {
  Map<Integer, Integer> getSkierInfo();
  int[] getLiftInfo();
  Map<Integer, Integer> getHourInfo();
}
