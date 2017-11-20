package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.ConcurrentSki;
import org.junit.Test;

import java.io.IOException;

public class SkiTest {
  private SkiDataProcessor skiDataProcessorSequential = new SkiDataProcessor(
      "PDPAssignment.csv", "Sequential");

  private ConcurrentSki skiDataProcessorConcurrent = new ConcurrentSki();


  @Test
  public void checkMain() throws InterruptedException {
    skiDataProcessorConcurrent.parseFile("PDPAssignment.csv");
    skiDataProcessorSequential.processData();
    skiDataProcessorSequential.outputResult();

  }
}

