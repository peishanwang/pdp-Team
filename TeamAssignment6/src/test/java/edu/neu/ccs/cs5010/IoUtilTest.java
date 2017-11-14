package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IoUtilTest {
  private IIoUtil ioUtil1;
  private IIoUtil ioUtil2;
  private IIoUtil ioUtil3;

  @Before
  public void before() {
    ioUtil1 = new IoUtil("nodes_small.csv",
        "UTF-8");
    ioUtil2 = new IoUtil("nodes_small.csv",
        "UTF-8");
    ioUtil3 = new IoUtil("nodes_10000.csv",
        "UTF-8");
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(ioUtil1.hashCode(), ioUtil2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        ioUtil2.hashCode() == ioUtil3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, ioUtil1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, ioUtil1.equals(ioUtil2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, ioUtil2.equals(ioUtil3));
  }

  @Test
  public void testEqualsSame() {
    IIoUtil ioUtil4 = ioUtil1;
    Assert.assertEquals(true, ioUtil1.equals(ioUtil4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IAnalyser analyser = new Analyser();
    Assert.assertEquals(false, ioUtil1.equals(analyser));
  }

  @Test
  public void testException() {
    ioUtil1 = new IoUtil("nonononono",
        "UTF-8");
  }
}
