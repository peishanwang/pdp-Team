package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.HourRideData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HourRideDataTest {
  private HourRideData data;

  @Before
  public void before() {
    data = new HourRideData(new int[10]);
  }

  @Test
  public void testGetHour() {
    Assert.assertEquals(0, data.getHour());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    HourRideData.constructHourData(0, new int[9]);
  }
}
