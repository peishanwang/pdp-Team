package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SkierTest {
  private ISkier skier1;
  private ISkier skier2;
  private ISkier skier3;

  @Before
  public void before() {
    skier1 = new Skier(1);
    skier2 = new Skier(1);
    skier3 = new Skier(2);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(skier1.hashCode(), skier2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        skier2.hashCode() == skier3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, skier1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, skier1.equals(skier2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, skier2.equals(skier3));
  }

  @Test
  public void testEqualsSame() {
    ISkier skier4 = skier1;
    Assert.assertEquals(true, skier1.equals(skier4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, skier1.equals(writer));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompareTo() {
    skier1.compareTo(null);
  }
}
