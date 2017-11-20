package edu.neu.ccs.cs5010;

import org.junit.Test;


public class IoUtilTest {
  private static final String WRONG_FILE = "nonono";

  @Test(expected = IllegalStateException.class)
  public void testFileNotFound() {
    IoUtil.getReader(WRONG_FILE);
  }

}
