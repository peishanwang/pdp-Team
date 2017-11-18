package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.Map;

public class SequentialResultGenerator implements IResultGenerator {

  @Override
  public Map<Integer, Integer> getSkierInfo() {
    return null;
    //get skier id and lift id and analyser class
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
}
