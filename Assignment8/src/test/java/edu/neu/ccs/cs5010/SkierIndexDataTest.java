package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.SkierIndexData;
import org.junit.Before;
import org.junit.Test;

public class SkierIndexDataTest {
  private SkierIndexData data;

  @Before
  public void before() {
    data = new SkierIndexData(new int[102]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFieldsException() {
    data = new SkierIndexData(new int[]{1, 2});
  }

  @Test(expected = IllegalStateException.class)
  public void testAddRide() {
    data.addRide(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testConstruct() {
    SkierIndexData.constructSkierToRideData(1, new int[101]);
  }

  @Test
  public void testConstruct2() {
    int[] rides = new int[100];
    rides[0] = 1;
    SkierIndexData.constructSkierToRideData(1, rides);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetField() {
    data.getField(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetField2() {
    data.getField(200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateField() {
    data.updateField(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateField2() {
    data.updateField(200, 1);
  }

}
