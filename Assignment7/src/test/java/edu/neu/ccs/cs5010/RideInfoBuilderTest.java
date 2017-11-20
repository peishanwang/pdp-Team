package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.IRideInfoBuilder;
import org.junit.Before;
import org.junit.Test;

public class RideInfoBuilderTest {
  IRideInfoBuilder builder;

  @Before
  public void before() {
    builder = new RideInfoBuilder();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResortId() {
    builder.setResortId(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDay1() {
    builder.setDay(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDay2() {
    builder.setDay(366);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSkierId() {
    builder.setSkier(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLiftId1() {
    builder.setLiftId(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLiftId2() {
    builder.setLiftId(41);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTime1() {
    builder.setTime(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTime2() {
    builder.setTime(361);
  }
}
