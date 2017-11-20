package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalHeaderInformationNullException;
import org.junit.Test;


public class SkiDataProcessorTest {
  private static final String FILE_NAME = "PDPAssignment.csv";
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private static final String NULL_HEADER = "nullHeader.csv";

  private SkiDataProcessor skiDataProcessorSequential =
      new SkiDataProcessor(FILE_NAME, true);
  private SkiDataProcessor skiDataProcessorConcurrent =
      new SkiDataProcessor(FILE_NAME, false);

  @Test
  public void checkSequential() throws InterruptedException {
    skiDataProcessorSequential.processData();
  }

  @Test
  public void checkParallel() throws InterruptedException {
    skiDataProcessorConcurrent.processData();
  }

  @Test
  public void testMain() {
    SkiDataProcessor.main(new String[]{FILE_NAME, SEQUENTIAL});
    SkiDataProcessor.main(new String[]{FILE_NAME, CONCURRENT});
  }

  @Test(expected = IllegalHeaderInformationNullException.class)
  public void testNullHeader() {
    SkiDataProcessor.main(new String[]{NULL_HEADER, SEQUENTIAL});
  }


}

