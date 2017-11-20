package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.IRideInfoBuilder;
import edu.neu.ccs.cs5010.exceptions.IllegalRideInfoBuilderException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RideInfoBuilderTest {
  private IRideInfoBuilder builder;
  private IRideInfoBuilder builder1;
  private IRideInfoBuilder builder2;
  private IRideInfoBuilder builder3;

  @Before
  public void before() {
    builder = new RideInfoBuilder();
    builder1 = new RideInfoBuilder();
    builder2 = new RideInfoBuilder();
    builder3 = new RideInfoBuilder();
    builder3.setTime(1);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        builder2.hashCode() == builder3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, builder1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, builder1.equals(builder2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, builder2.equals(builder3));
  }

  @Test
  public void testEqualsSame() {
    IRideInfoBuilder builder4 = builder1;
    Assert.assertEquals(true, builder1.equals(builder4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    IResultWriter writer = new ResultWriter();
    Assert.assertEquals(false, builder1.equals(writer));
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testResortId() {
    builder.setResortId(-1);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testDay1() {
    builder.setDay(-1);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testDay2() {
    builder.setDay(366);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testSkierId() {
    builder.setSkier(-1);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testLiftId1() {
    builder.setLiftId(-1);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testLiftId2() {
    builder.setLiftId(41);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testTime1() {
    builder.setTime(-1);
  }

  @Test(expected = IllegalRideInfoBuilderException.class)
  public void testTime2() {
    builder.setTime(361);
  }

  @Test
  public void testBranch1() {
    builder3.setLiftId(1);
    Assert.assertEquals(false, builder2.equals(builder3));
  }

  @Test
  public void testBranch2() {
    builder3.setSkier(1);
    Assert.assertEquals(false, builder2.equals(builder3));
  }

  @Test
  public void testBranch3() {
    builder3.setDay(1);
    Assert.assertEquals(false, builder2.equals(builder3));
  }

  @Test
  public void testBranch4() {
    builder3.setResortId(1);
    Assert.assertEquals(false, builder2.equals(builder3));
  }
}
