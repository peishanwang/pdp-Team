package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResortTest {
  private IResort resort1;
  private IResort resort2;
  private IResort resort3;

  @Before
  public void before() {
    resort1 = new Resort();
    resort2 = new Resort();
    resort3 = new Resort();
    resort1.addSkierVertical(1, 1);
    resort2.addSkierVertical(1, 1);
    resort3.addSkierVertical(2, 100);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(resort1.hashCode(), resort2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        resort2.hashCode() == resort3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, resort1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, resort1.equals(resort2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, resort2.equals(resort3));
  }

  @Test
  public void testEqualsSame() {
    IResort resort4 = resort1;
    Assert.assertEquals(true, resort1.equals(resort4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, resort1.equals(writer));
  }
}
