package edu.neu.ccs.cs5010;


import edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist.ILiftWithRides;
import edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist.LiftWithRides;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LiftWithRidesTest {

  private ILiftWithRides liftWithRides1;
  private ILiftWithRides liftWithRides2;
  private ILiftWithRides liftWithRides3;

  @Before
  public void before(){
    liftWithRides1 = new LiftWithRides(1, 4);
    liftWithRides2 = new LiftWithRides(1, 4);
    liftWithRides3 = new LiftWithRides(3, 10);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(liftWithRides1.hashCode(), liftWithRides2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
            liftWithRides1.hashCode() == liftWithRides3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, liftWithRides1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, liftWithRides1.equals(liftWithRides2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, liftWithRides2.equals(liftWithRides3));
  }

  @Test
  public void testEqualsSame() {
    ILiftWithRides liftWithRides4 = liftWithRides1;
    Assert.assertEquals(true, liftWithRides1.equals(liftWithRides4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    Query query = new Query(1, 1);
    Assert.assertEquals(false, liftWithRides1.equals(query));
  }
}

