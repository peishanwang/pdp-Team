package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;

import java.util.List;
import java.util.concurrent.*;

public class QueryExecutor {

  public QueryExecutor(List<Query> queryList, int numOfThreads) {
    this.numOfThreads = numOfThreads;
    this.queryList = queryList;
    this.executor = Executors.newFixedThreadPool(numOfThreads);
    this.modelDatabase = new ModelDatabase();
    this.syncBarrier = new CyclicBarrier(numOfThreads + 1);
  }

  public QueryExecutor(List<Query> queryList) {
    this(queryList, NUM_DEFAULT_THREADS);
  }

  public void execute() {
    if (queryList.size() % numOfThreads != 0) {
      throw new IllegalCmdArgumentException("Can't divide queries equally among threads");
    }
    int queriesEachThread = queryList.size() / numOfThreads;
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < numOfThreads; i++) {
      executor.submit(new QueryProcessor(i + 1,
          modelDatabase,
          syncBarrier,
          queryList.subList(i * queriesEachThread, (i + 1) * queriesEachThread)));
    }
    try {
      // wait for all threads to start together
      this.syncBarrier.await();
      // again wait for results from all threads
      this.syncBarrier.await();
      long endTime = System.currentTimeMillis();
      long timeTaken = endTime - startTime;
      String str = String.format("Time taken for %1$s threads : %2$d ms",
          numOfThreads,
          timeTaken);
      System.out.println(str);
    } catch (InterruptedException | BrokenBarrierException e) {
      throw new IllegalStateException("Failed to write output for queries", e);
    }
    executor.shutdown();
    // close database
    modelDatabase.close();
  }

  private static final int NUM_DEFAULT_THREADS = 20;
  private final int numOfThreads;
  private final ExecutorService executor;
  private final List<Query> queryList;
  private final ModelDatabase modelDatabase;
  private final CyclicBarrier syncBarrier;
}
