
package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.IoUtil;
import org.junit.Test;

import java.io.File;

public class IoUtilTest {
  private static final String WRONG_PATH = "directory" + File.separator + "nonono";

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