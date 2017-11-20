package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.concurrentsystem.SkiHelper;
import edu.neu.ccs.cs5010.exceptions.IllegalSkiHelperArgumentException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SkiHelperTest {
  SkiHelper skiHelper1 = new SkiHelper();
  SkiHelper skiHelper2 = new SkiHelper();

  @Test
  public void testSkiHelperForCase1() {
    assertEquals(SkiHelper.getVerticalDistanceMetres(liftId1), height200);
  }

  @Test
  public void testSkiHelperForCase2() {
    assertEquals(SkiHelper.getVerticalDistanceMetres(liftId11), height300);
  }

  @Test
  public void testSkiHelperForCase3() {
    assertEquals(SkiHelper.getVerticalDistanceMetres(liftId21), height400);
  }

  @Test
  public void testSkiHelperForCase4() {
    assertEquals(SkiHelper.getVerticalDistanceMetres(liftId31), height500);
  }

  @Test(expected = IllegalSkiHelperArgumentException.class)
  public void testForIllegalArgument() {
    SkiHelper.getVerticalDistanceMetres(liftId41);
  }

  @Test
  public void testEquals(){
    assertFalse(skiHelper1.equals(skiHelper2));
  }

  @Test
  public void testHashcode(){
    assertNotEquals(skiHelper1.hashCode(), skiHelper2.hashCode());
  }

  private int liftId1 = 1;
  private int liftId11 = 11;
  private int liftId21 = 21;
  private int liftId31 = 31;
  private int liftId41 = 41;
  private int height200 = 200;
  private int height300 = 300;
  private int height400 = 400;
  private int height500 = 500;

}
