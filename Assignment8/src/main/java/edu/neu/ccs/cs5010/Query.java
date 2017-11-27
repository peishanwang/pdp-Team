package edu.neu.ccs.cs5010;

public class Query {
  private int queryId;
  private int parameter;

  public Query(int queryId, int parameter) {
    this.queryId = queryId;
    this.parameter = parameter;
  }

  public int getQueryId() {
    return queryId;
  }

  public int getParameter() {
    return parameter;
  }
}
