package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CmdParserTest {
  private static final String FILE_NAME = "PDPAssignment.csv";
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private static final String WRONG_FLAG = "nonono";
  private ICmdParser parser1;
  private ICmdParser parser2;
  private ICmdParser parser3;

  @Before
  public void before() {
    parser1 = new CmdParser(new String[]{FILE_NAME, SEQUENTIAL});
    parser2 = new CmdParser(new String[]{FILE_NAME, SEQUENTIAL});
    parser3 = new CmdParser(new String[]{FILE_NAME, CONCURRENT});
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(parser1.hashCode(), parser2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        parser2.hashCode() == parser3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, parser1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, parser1.equals(parser2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, parser2.equals(parser3));
  }

  @Test
  public void testEqualsSame() {
    ICmdParser parser4 = parser1;
    Assert.assertEquals(true, parser1.equals(parser4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, parser1.equals(writer));
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException() {
    parser1 = new CmdParser(new String[]{FILE_NAME, WRONG_FLAG});
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testNumArgsException() {
    parser1 = new CmdParser(new String[]{FILE_NAME});
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testNumArgsException2() {
    parser1 = new CmdParser(new String[]{FILE_NAME, SEQUENTIAL, FILE_NAME});
  }
}
