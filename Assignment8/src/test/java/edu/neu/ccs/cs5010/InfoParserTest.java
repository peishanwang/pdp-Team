package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalHeaderInformationNullException;
import edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist.SkiDataProcessor;
import org.junit.Test;

public class InfoParserTest {
  private static final String NULL_HEADER = "nullHeader.csv";

  @Test(expected = IllegalHeaderInformationNullException.class)
  public void testNullHeader() {
    SkiDataProcessor.main(new String[]{NULL_HEADER});
  }
}
