package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.Map;

public interface IResultGenerator {
  Map<Integer, Integer> getSkierInfo();
  Map<Integer, Integer> getLiftInfo();
  List<Map<Integer, Integer>> getHourInfo();
}
