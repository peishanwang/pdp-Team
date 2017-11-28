package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.LruCache;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LruCacheTest {
  private LruCache<String, Integer> cache;
  private static final int LRU_CACHE_SIZE = 2;

  @Before
  public void before() {
    cache = new LruCache<>(LRU_CACHE_SIZE);
  }

  @Test
  public void testEviction() {
    cache.put("a", 1);
    cache.put("b", 2);
    assertEquals(cache.get("a"), new Integer(1));
    cache.put("c", 3);
    assertEquals(cache.get("b"), null);
  }
}
