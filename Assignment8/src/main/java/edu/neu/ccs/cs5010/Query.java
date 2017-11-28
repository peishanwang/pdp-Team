package edu.neu.ccs.cs5010;

/**
 * Class of Query containing query type and parameter.
 */
public class Query {
  /**
   * Enum of query type.
   */
  public enum QueryType {
    SKIER_SUMMARY,
    SKIER_RIDE_DETAILS,
    BUSY_LIFTS_PER_HOUR,
    LIFT_SUMMARY
  }

  /**
   * Constructor of Query.
   * @param queryType type of query
   * @param queryKey parameter of query
   */
  public Query(QueryType queryType, int queryKey) {
    this.queryType = queryType;
    this.queryKey = queryKey;
  }

  /**
   * Constructor of Query.
   * @param queryId query type id
   * @param queryKey parameter of query
   */
  public Query(int queryId, int queryKey) {
    if (queryId < 1 || queryId > QueryType.values().length) {
      throw new IllegalArgumentException("invalid queryId: " + queryId);
    }
    this.queryType = QueryType.values()[queryId - 1];
    this.queryKey = queryKey;
  }

  /**
   * Returns query type.
   * @return query type.
   */
  public QueryType getType() {
    return queryType;
  }

  /**
   * Returns parameter of query.
   * @return parameter of query.
   */
  public int getKey() {
    return queryKey;
  }

  private QueryType queryType;
  private int queryKey;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Query query = (Query) obj;

    if (queryKey != query.queryKey) {
      return false;
    }
    return queryType == query.queryType;
  }

  @Override
  public int hashCode() {
    int result = queryType.hashCode();
    result = 31 * result + queryKey;
    return result;
  }
}
