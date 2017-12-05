package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class IoUtilTest {
  private static final String TEST_STRING = "hello";

  @Test
  public void testInput() {
    String testString = TEST_STRING;
    IoUtil input = new IoUtil(new ByteArrayInputStream(testString.getBytes()));
    Assert.assertEquals(testString, input.getLine());
  }

  @Test(expected = RuntimeException.class)
  public void inException() {
    IoUtil input = new IoUtil(new PipedOutputStream());
    input.getLine();
  }

  @Test
  public void testOutput() {
    ByteArrayOutputStream test = new ByteArrayOutputStream();
    IoUtil output = new IoUtil(test);
    output.writeLine(TEST_STRING);
    byte[] byteArray = test.toByteArray();
    Assert.assertEquals(TEST_STRING, new String(byteArray).trim());
  }

  @Test(expected = RuntimeException.class)
  public void outException() {
    IoUtil output = new IoUtil(new PipedInputStream());
    output.writeLine("anything");
  }
}
