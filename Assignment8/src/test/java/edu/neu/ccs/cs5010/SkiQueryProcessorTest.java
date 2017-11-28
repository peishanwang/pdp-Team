package edu.neu.ccs.cs5010;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SkiQueryProcessorTest {
  private static final String PATH = "PDPAssignment8.csv";
  private static final int NUM_THREADS = 20;
  private static final int NUM_THREADS2 = 10;
  private SkiQueryProcessor processor1;
  private SkiQueryProcessor processor2;
  private SkiQueryProcessor processor3;

  @Before
  public void before() {
    processor1 = new SkiQueryProcessor(PATH, NUM_THREADS);
    processor2 = new SkiQueryProcessor(PATH, NUM_THREADS);
    processor3 = new SkiQueryProcessor(PATH, NUM_THREADS2);
  }

  @Test
  public void testEqualsHashCode() {
    Assert.assertEquals(processor1.hashCode(), processor2.hashCode());
  }

  @Test
  public void testHashCodeNotEquals() {
    Assert.assertEquals(false,
        processor2.hashCode() == processor3.hashCode());
  }

  @Test
  public void testEqualsNull() {
    Assert.assertEquals(false, processor1.equals(null));
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(true, processor1.equals(processor2));
  }

  @Test
  public void testNotEquals() {
    Assert.assertEquals(false, processor2.equals(processor3));
  }

  @Test
  public void testEqualsSame() {
    SkiQueryProcessor processor4 = processor1;
    Assert.assertEquals(true, processor1.equals(processor4));
  }

  @Test
  public void testNotEqualsDiffClass() {
    Query query = new Query(1, 1);
    Assert.assertEquals(false, processor1.equals(query));
  }

  @Test
  public void main() {
    SkiQueryProcessor.main(new String[]{PATH, Integer.toString(NUM_THREADS)});
  }
}