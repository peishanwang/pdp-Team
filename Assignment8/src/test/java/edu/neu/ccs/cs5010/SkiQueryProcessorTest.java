package edu.neu.ccs.cs5010;

import org.junit.Test;


public class SkiQueryProcessorTest {
  private static final String PATH = "PDPAssignment8.csv";
  private static final int NUM_THREADS = 20;

  @Test
  public void main() {
    SkiQueryProcessor.main(new String[]{PATH, Integer.toString(NUM_THREADS)});
  }
}