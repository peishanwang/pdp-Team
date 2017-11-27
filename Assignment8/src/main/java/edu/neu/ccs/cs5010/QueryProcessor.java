package edu.neu.ccs.cs5010;

import java.util.List;

public class QueryProcessor implements Runnable {
  private int processorId;
  private List<Query> queryList;
  private ModelDatabase database;
  private TxtGenerator txtGenerator;

  public QueryProcessor(int processorId, ModelDatabase database, List<Query> queryList) {
    this.processorId = processorId;
    this.queryList = queryList;
    this.database = database;
    this.txtGenerator = new TxtGenerator("Thread" + this.processorId + ".txt");
  }

  @Override
  public void run() {
    for (int i = 0; i < queryList.size(); i++) {
      txtGenerator.writeLine(database.performQuery(queryList.get(i)));
    }
    txtGenerator.close();
  }
}
