package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist.ILiftHourQueueItem;
import edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist.LiftHourQueueItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LiftHourQueueItemTest {
  private ILiftHourQueueItem liftHourQueueItem1;
  private ILiftHourQueueItem liftHourQueueItem2;
  private ILiftHourQueueItem liftHourQueueItem3;

  @Before
  public void before(){
    liftHourQueueItem1 = new LiftHourQueueItem(1, 4);
    liftHourQueueItem2 = new LiftHourQueueItem(1, 4);
    liftHourQueueItem3 = new LiftHourQueueItem(3, 10);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(liftHourQueueItem1.hashCode(), liftHourQueueItem2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
            liftHourQueueItem1.hashCode() == liftHourQueueItem3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, liftHourQueueItem1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, liftHourQueueItem1.equals(liftHourQueueItem2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, liftHourQueueItem2.equals(liftHourQueueItem3));
  }

  @Test
  public void testEqualsSame() {
    ILiftHourQueueItem liftHourQueueItem4 = liftHourQueueItem1;
    Assert.assertEquals(true, liftHourQueueItem1.equals(liftHourQueueItem4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    Query query = new Query(1, 1);
    Assert.assertEquals(false, liftHourQueueItem1.equals(query));
  }
}
