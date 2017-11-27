package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutor {
  private List<QueryProcessor> allThreads;
  public QueryExecutor(List<Query> queryList,
                       int numOfThreads) {
    allThreads = new ArrayList<>();
    if (queryList.size() % numOfThreads != 0) {
      throw new IllegalArgumentException("Queries can't be equally divided among threads.");
    }
    int queriesEachThread = queryList.size() / numOfThreads;
    for (int i = 0; i < numOfThreads; i++) {
      allThreads.add(new QueryProcessor(i + 1,
          queryList.subList(i * queriesEachThread, (i + 1) * queriesEachThread)));
    }
  }


  public void start() {
    for (QueryProcessor thread : allThreads) {
      thread.start();
    }
  }

  public void join() {
    try {
      for (QueryProcessor thread : allThreads) {
        thread.join();
      }
    } catch (InterruptedException e) {
      e.getStackTrace();
    }

  }
}
