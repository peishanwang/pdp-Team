package edu.neu.ccs.cs5010;

public class SkiQueryProcessor {
  private static final String PATH = "PDPAssignment8.csv";
  private static final int NUM_THREADS = 20;

  public static void main(String[] args) {
    SkiQueryProcessor processor = new SkiQueryProcessor();
    processor.processQuery();
  }

  public void processQuery() {
    IQueryParser parser = new QueryParser();
    QueryExecutor executor = new QueryExecutor(parser.parseInfo(PATH),NUM_THREADS);
    executor.execute();
  }
}
