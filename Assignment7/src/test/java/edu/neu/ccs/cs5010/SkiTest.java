package edu.neu.ccs.cs5010;

import org.junit.Test;

import java.io.IOException;

public class SkiTest {
  SkiDataProcessor skiDataProcessorSequential = new SkiDataProcessor("PDPAssignment.csv", true);
  SkiDataProcessor skiDataProcessorConcurrent = new SkiDataProcessor("PDPAssignment.csv", false);

  @Test
  public void checkSequential() throws InterruptedException {
    skiDataProcessorSequential.processData();
  }

  @Test
  public void checkParallel() throws InterruptedException {
    skiDataProcessorConcurrent.processData();
  }
}

