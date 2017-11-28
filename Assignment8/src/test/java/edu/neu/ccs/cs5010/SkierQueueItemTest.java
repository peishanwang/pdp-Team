package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist.ISkierQueueItem;
import edu.neu.ccs.cs5010.skidatamodel.concurrentdatapersist.SkierQueueItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SkierQueueItemTest {
  private ISkierQueueItem skierQueueItem1;
  private ISkierQueueItem skierQueueItem2;
  private ISkierQueueItem skierQueueItem3;

  @Before
  public void before(){
    skierQueueItem1 = new SkierQueueItem(1, 4, 1);
    skierQueueItem2 = new SkierQueueItem(1, 4, 1);
    skierQueueItem3 = new SkierQueueItem(3, 10, 1);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(skierQueueItem1.hashCode(), skierQueueItem2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
            skierQueueItem1.hashCode() == skierQueueItem3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, skierQueueItem1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, skierQueueItem1.equals(skierQueueItem2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, skierQueueItem2.equals(skierQueueItem3));
  }

  @Test
  public void testEqualsSame() {
    ISkierQueueItem skierQueueItem4 = skierQueueItem1;
    Assert.assertEquals(true, skierQueueItem1.equals(skierQueueItem4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    Query query = new Query(1, 1);
    Assert.assertEquals(false, skierQueueItem1.equals(query));
  }

}
