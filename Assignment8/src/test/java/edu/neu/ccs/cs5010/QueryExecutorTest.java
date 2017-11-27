package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutorTest {
  private QueryExecutor executor;

  @Before
  public void before() {
    List<Query> queryList = new ArrayList<>();
    queryList.add(new Query(1, 1));
    queryList.add(new Query(1, 2));
    executor = new QueryExecutor(queryList, 3);
  }

  @Test(expected = IllegalCmdArgumentException.class)
  public void testExecuteException() {
    executor.execute();
  }

}
