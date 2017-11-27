package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryCmdTest {
  private static final String FILE_NAME = "PDPAssignment.csv";
  private static final String FILE_NAME2 = "PDPAssignment88.csv";
  private static final String NUM_1 = "20";
  private static final String NUM_2 = "40";
  private static final String NUM_ILLEGAL = "30";
  private QueryCmdParser parser1;
  private QueryCmdParser parser2;
  private QueryCmdParser parser3;

  @Before
  public void before() {
    parser1 = new QueryCmdParser(new String[]{FILE_NAME, NUM_1});
    parser2 = new QueryCmdParser(new String[]{FILE_NAME, NUM_1});
    parser3 = new QueryCmdParser(new String[]{FILE_NAME, NUM_2});
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
  public void testNotEquals2() {
    parser3 = new QueryCmdParser(new String[]{FILE_NAME2, NUM_1});
    Assert.assertEquals(false, parser2.equals(parser3));
  }

  @Test
  public void testEqualsSame() {
    QueryCmdParser parser4 = parser1;
    Assert.assertEquals(true, parser1.equals(parser4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    Query query = new Query(1, 1);
    Assert.assertEquals(false, parser1.equals(query));
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testException() {
    parser1 = new QueryCmdParser(new String[]{FILE_NAME, NUM_ILLEGAL});
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testNumArgsException() {
    parser1 = new QueryCmdParser(new String[]{FILE_NAME});
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testNumArgsException2() {
    parser1 = new QueryCmdParser(new String[]{FILE_NAME, NUM_1, NUM_2});
  }
}
