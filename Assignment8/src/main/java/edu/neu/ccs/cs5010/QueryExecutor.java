package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class QueryExecutor {
  private List<QueryProcessor> allThreads;
  private CyclicBarrier barrier;
  public QueryExecutor(List<Query> queryList,
                       int numOfThreads) {
    allThreads = new ArrayList<>();
    int queriesEachThread = queryList.size() / numOfThreads;
    for (int i = 0; i < numOfThreads; i++) {
      allThreads.add(new QueryProcessor(i + 1,
          queryList.subList(i * queriesEachThread, (i + 1) * queriesEachThread)));
    }
    barrier  = new CyclicBarrier(numOfThreads);
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
        barrier.await();
      }
    } catch (InterruptedException | BrokenBarrierException e) {
      e.getStackTrace();
    }

  }
}
