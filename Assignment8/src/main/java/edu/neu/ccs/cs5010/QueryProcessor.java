package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;

public class QueryProcessor extends Thread {
  private int processorId;
  private List<Query> queryList;
  private Database database;
  private TxtGenerator txtGenerator;

  public QueryProcessor(int processorId, List<Query> queryList) {
    this.processorId = processorId;
    this.queryList = queryList;
    database = new Database();
    this.txtGenerator = new TxtGenerator("Thread" + processorId + ".txt");
  }

  @Override
  public void run() {
    for (int i = 0; i < queryList.size(); i++) {
      txtGenerator.write(database.getResult(queryList.get(i)));
    }
    txtGenerator.close();
    database.close();
  }
}
