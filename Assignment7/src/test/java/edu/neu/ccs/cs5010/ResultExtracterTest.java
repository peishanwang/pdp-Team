package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResultExtracterTest {
  private IResultExtracter extracter1;
  private IResultExtracter extracter2;
  private IResultExtracter extracter3;

  @Before
  public void before() {
    IResort resort1 = new Resort();
    IResort resort2 = new Resort();
    resort1.addSkierVertical(1, 1);
    resort1.addSkierVertical(1, 1);
    resort2.addSkierVertical(2, 2);
    extracter1 = new ResultExtracter(resort1);
    extracter2 = new ResultExtracter(resort1);
    extracter3 = new ResultExtracter(resort2);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(extracter1.hashCode(), extracter2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        extracter2.hashCode() == extracter3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, extracter1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, extracter1.equals(extracter2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, extracter2.equals(extracter3));
  }

  @Test
  public void testEqualsSame() {
    IResultExtracter extracter4 = extracter1;
    Assert.assertEquals(true, extracter1.equals(extracter4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, extracter1.equals(writer));
  }
}
