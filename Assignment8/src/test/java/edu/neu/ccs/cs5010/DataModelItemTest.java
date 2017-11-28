package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.DataModelItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataModelItemTest {
  private DataModelItem dataModelItem;

  @Before
  public void before() {
    dataModelItem = new DataModelItem(new int[1]);
  }

  @Test
  public void testGetKeyColumn() {
    Assert.assertEquals(0, dataModelItem.getKeyColumn());
  }

  @Test
  public void testGetKey() {
    Assert.assertEquals(0, dataModelItem.getKey());
  }
}
