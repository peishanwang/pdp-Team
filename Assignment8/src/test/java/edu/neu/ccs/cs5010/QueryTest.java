package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryTest {
  private Query query;
  private Query query1;
  private Query query2;
  private Query query3;

  @Before
  public void before() {
    query = new Query(Query.QueryType.LIFT_SUMMARY, 100);
    query1 = new Query(Query.QueryType.LIFT_SUMMARY, 10);
    query2 = new Query(Query.QueryType.LIFT_SUMMARY, 10);
    query3 = new Query(Query.QueryType.LIFT_SUMMARY, 20);
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

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(query1.hashCode(), query2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        query2.hashCode() == query3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, query1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, query1.equals(query2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, query2.equals(query3));
  }

  @Test
  public void testEqualsSame() {
    Query query4 = query1;
    Assert.assertEquals(true, query1.equals(query4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    QueryCmdParser parser = new QueryCmdParser(new String[]{"", "20"});
    Assert.assertEquals(false, query1.equals(parser));
  }
}
