package edu.neu.ccs.cs5010;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * QueryProcessor is a Runnable class to process query.
 */
public class QueryProcessor implements Runnable {
  private static final Logger LOGGER
          = Logger.getLogger(QueryProcessor.class.getName());
  private int processorId;
  private List<Query> queryList;
  private ModelDatabase database;
  private CyclicBarrier syncBarrier;
  private TxtGenerator txtGenerator;

  /**
   * Constructor of QueryProcessor.
   * @param processorId processor id
   * @param database database to get information from .dat file
   * @param barrier barrier
   * @param queryList list of queries to be processed
   */
  public QueryProcessor(int processorId, ModelDatabase database, CyclicBarrier barrier, List<Query>
          queryList) {
    this.processorId = processorId;
    this.queryList = queryList;
    this.database = database;
    this.syncBarrier = barrier;
    this.txtGenerator = new TxtGenerator("Thread" + this.processorId + ".txt");
  }

  @Override
  public void run() {
    try {
      this.syncBarrier.await();
      long threadStartTime = System.currentTimeMillis();
      for (int i = 0; i < queryList.size(); i++) {
        txtGenerator.writeLine(database.performQuery(queryList.get(i)));
      }
      txtGenerator.close();
      long threadEndTime = System.currentTimeMillis();
      LOGGER.log(Level.FINE, "Time taken by thread: " + this.processorId + " = " +
              (threadEndTime - threadStartTime));
      this.syncBarrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      throw new IllegalStateException("Fail to write queries output for thread: " + processorId, e);
    }
  }
}
