package edu.neu.ccs.cs5010;

import java.util.ArrayList;
import java.util.List;

public class QueryProcessor extends Thread {
  private int processorId;
  private List<Query> queryList;
  private Database database;
  private List<String> result;

  public QueryProcessor(int processorId, List<Query> queryList) {
    this.processorId = processorId;
    this.queryList = queryList;
    database = new Database();
    result = new ArrayList<>();
  }

  @Override
  public void run() {
    for (int i = 0; i < queryList.size(); i++) {
      int type = queryList.get(i).getQueryId();
      int parameter = queryList.get(i).getParameter();
      result.add(database.getResult(type, parameter));
    }
    TxtGenerator generator = new TxtGenerator(".", "UTF-8");
    generator.generateOutput(result, "Thread" + processorId + ".txt");
  }
}
