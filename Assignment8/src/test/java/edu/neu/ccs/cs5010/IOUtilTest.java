
package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.IOUtil;
import org.junit.Test;

import java.io.File;

public class IOUtilTest {
  private static final String WRONG_PATH = "directory" + File.separator + "nonono";

  @Test(expected = IllegalStateException.class)
  public void testReaderException() {
    IOUtil.getReader(WRONG_PATH);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriterException() {
    IOUtil.getWriter(WRONG_PATH);
  }

  @Test(expected = IllegalStateException.class)
  public void testTextWriterException() {
    IOUtil.getTextWriter(WRONG_PATH);
  }

  @Test(expected = IllegalStateException.class)
  public void testAccessorException() {
    IOUtil.getRandomFileAccessor(WRONG_PATH);
  }
}