package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.ISkierWithVertical;
import edu.neu.ccs.cs5010.concurrentsystem.SkierWithVertical;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SkierWithVerticalTest {

  private ISkierWithVertical skierWithVertical1;
  private ISkierWithVertical skierWithVertical2;
  private ISkierWithVertical skierWithVertical3;

  @Before
  public void before(){
    skierWithVertical1 = new SkierWithVertical(1, 4);
    skierWithVertical2 = new SkierWithVertical(1, 4);
    skierWithVertical3 = new SkierWithVertical(3, 10);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(skierWithVertical1.hashCode(), skierWithVertical2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
            skierWithVertical1.hashCode() == skierWithVertical3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, skierWithVertical1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, skierWithVertical1.equals(skierWithVertical2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, skierWithVertical2.equals(skierWithVertical3));
  }

  @Test
  public void testEqualsSame() {
    ISkierWithVertical skierWithVertical4 = skierWithVertical1;
    Assert.assertEquals(true, skierWithVertical1.equals(skierWithVertical4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, skierWithVertical1.equals(writer));
  }
}
