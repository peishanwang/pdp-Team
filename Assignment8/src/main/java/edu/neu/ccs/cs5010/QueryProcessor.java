package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class QueryProcessor implements Runnable {
  private int processorId;
  private List<Query> queryList;
  private Database database;
  private List<String> result;
  private CyclicBarrier barrier;

  public QueryProcessor(int processorId, List<Query> queryList, CyclicBarrier barrier) {
    this.processorId = processorId;
    this.queryList = queryList;
    this.barrier = barrier;
    database = new Database();
    result = new ArrayList<>();
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < queryList.size(); i++) {
        int type = queryList.get(i).getQueryId();
        int parameter = queryList.get(i).getParameter();
        result.add(database.getResult(type, parameter));
      }
      database.close();
      TxtGenerator generator = new TxtGenerator(".", "UTF-8");
      generator.generateOutput(result, "Thread" + processorId + ".txt");
      barrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      e.fillInStackTrace();
    }
  }
}
