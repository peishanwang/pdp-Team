package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LiftTest {
  private ILift lift1;
  private ILift lift2;
  private ILift lift3;

  @Before
  public void before() {
    lift1 = new Lift(1);
    lift2 = new Lift(1);
    lift3 = new Lift(2);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(lift1.hashCode(), lift2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        lift2.hashCode() == lift3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, lift1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, lift1.equals(lift2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, lift2.equals(lift3));
  }

  @Test
  public void testEqualsSame() {
    ILift lift4 = lift1;
    Assert.assertEquals(true, lift1.equals(lift4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, lift1.equals(writer));
  }
}
