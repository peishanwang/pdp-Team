package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryTest {
  private Query query;

  @Before
  public void before() {
    query = new Query(Query.QueryType.LIFT_SUMMARY, 100);
  }

  @Test
  public void testGetKey() {
    Assert.assertEquals(100, query.getKey());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException1() {
    query = new Query(0, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException2() {
    query = new Query(5, 100);
  }
}
