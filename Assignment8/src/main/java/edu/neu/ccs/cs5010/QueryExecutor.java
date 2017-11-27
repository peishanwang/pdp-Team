package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class QueryExecutor {
  public QueryExecutor(List<Query> queryList,
                       int numOfThreads) {
    this.numOfThreads = numOfThreads;
    this.queryList = queryList;
    this.executor = Executors.newFixedThreadPool(numOfThreads);
    this.modelDatabase = new ModelDatabase();
  }

  public void execute() {
    if (queryList.size() % numOfThreads != 0) {
      throw new IllegalCmdArgumentException("Can't divide queries equally among threads");
    }
    int queriesEachThread = queryList.size() / numOfThreads;
    long startTime = System.currentTimeMillis();
    List<Future> queryResultFutures = new ArrayList<>(numOfThreads);
    for (int i = 0; i < numOfThreads; i++) {
      queryResultFutures.add(executor.submit(new QueryProcessor(i + 1,
          modelDatabase,
          queryList.subList(i * queriesEachThread, (i + 1) * queriesEachThread))));
    }
    try {
      for (Future queryResFut : queryResultFutures) {
        queryResFut.get();
      }
      long endTime = System.currentTimeMillis();
      long timeTaken = endTime - startTime;
      String str = String.format("Time taken for %1$s threads : %2$d ms",
          numOfThreads,
          timeTaken);
      System.out.println(str);
    } catch (ExecutionException | InterruptedException e) {
      throw new IllegalStateException("Failed to write output for queries", e);
    }
    executor.shutdown();
    // close database
    modelDatabase.close();
  }

  private final int numOfThreads;
  private final ExecutorService executor;
  private final List<Query> queryList;
  private final ModelDatabase modelDatabase;
}
