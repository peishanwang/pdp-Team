package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryExecutor {
  private int numOfThreads;
  private CyclicBarrier barrier;
  private ExecutorService executor;
  private List<Query> queryList;
  public QueryExecutor(List<Query> queryList,
                       int numOfThreads) {
    this.numOfThreads = numOfThreads;
    this.queryList = queryList;
    executor = Executors.newFixedThreadPool(numOfThreads);
    barrier  = new CyclicBarrier(numOfThreads + 1);
  }

  public void execute() {
    int queriesEachThread = queryList.size() / numOfThreads;
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < numOfThreads; i++) {
      executor.submit(new QueryProcessor(i + 1,
          queryList.subList(i * queriesEachThread, (i + 1) * queriesEachThread),
          barrier));
    }
    try {
      barrier.await();
      long endTime = System.currentTimeMillis();
      long timeTaken = endTime - startTime;
      String str = String.format("Time taken for %1$s threads : %2$d ms",
          numOfThreads,
          timeTaken);
      System.out.println(str);
    } catch (InterruptedException | BrokenBarrierException e) {
      e.fillInStackTrace();
    }
    executor.shutdown();
  }
}
