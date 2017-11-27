package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.IoUtil;
import org.junit.Test;

public class IoUtilTest {
  private static final String WRONG_PATH = "directory\\nonono";

  @Test(expected = IllegalStateException.class)
  public void testReaderException() {
    IoUtil.getReader(WRONG_PATH);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriterException() {
    IoUtil.getWriter(WRONG_PATH);
  }

  @Test(expected = IllegalStateException.class)
  public void testTextWriterException() {
    IoUtil.getTextWriter(WRONG_PATH);
  }

  @Test(expected = IllegalStateException.class)
  public void testAccessorException() {
    IoUtil.getRandomFileAccessor(WRONG_PATH);
  }

}
