package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class QueryProcessor implements Runnable {
  private int processorId;
  private List<Query> queryList;
  private Database database;
  private TxtGenerator txtGenerator;
  private CyclicBarrier barrier;

  public QueryProcessor(int processorId, List<Query> queryList, CyclicBarrier barrier) {
    this.processorId = processorId;
    this.queryList = queryList;
    this.barrier = barrier;
    database = new Database();
    this.txtGenerator = new TxtGenerator("Thread" + processorId + ".txt");
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < queryList.size(); i++) {
        txtGenerator.write(database.getResult(queryList.get(i)));
      }
      database.close();
      txtGenerator.close();
      barrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      e.fillInStackTrace();
    }
  }
}
