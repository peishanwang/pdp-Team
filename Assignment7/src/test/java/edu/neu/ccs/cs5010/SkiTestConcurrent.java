package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentski.ConcurrentSki;
import org.junit.Test;

import java.io.IOException;

public class SkiTestConcurrent {
  ConcurrentSki concurrentSki = new ConcurrentSki();

  @Test
  public void testConcurrentSkiOperations() throws IOException, InterruptedException {
    concurrentSki.parseFile("PDPAssignment.csv");
  }

}

