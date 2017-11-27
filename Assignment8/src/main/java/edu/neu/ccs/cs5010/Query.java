package edu.neu.ccs.cs5010;

public class Query {
  public enum QueryType {
    SKIER_SUMMARY,
    SKIER_RIDE_DETAILS,
    BUSY_LIFTS_PER_HOUR,
    LIFT_SUMMARY
  }

  public Query(QueryType queryType, int queryKey) {
    this.queryType = queryType;
    this.queryKey = queryKey;
  }

  public Query(int queryId, int queryKey) {
    if (queryId < 1 || queryId > QueryType.values().length) {
      throw new IllegalArgumentException("invalid queryId: " + queryId);
    }
    this.queryType = QueryType.values()[queryId - 1];
    this.queryKey = queryKey;
  }

  public QueryType getType() {
    return queryType;
  }

  public int getKey() {
    return queryKey;
  }

  private QueryType queryType;
  private int queryKey;

}
