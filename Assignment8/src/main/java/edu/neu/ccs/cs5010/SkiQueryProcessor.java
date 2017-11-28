package edu.neu.ccs.cs5010;

public class SkiQueryProcessor {
  private String path;
  private int numberOfQueries;

  public SkiQueryProcessor(String path, int numberOfQueries) {
    this.path = path;
    this.numberOfQueries = numberOfQueries;
  }

  public static void main(String[] args) {
    QueryCmdParser parser = new QueryCmdParser(args);
    SkiQueryProcessor processor = new SkiQueryProcessor(parser.getCsvFile(),
        parser.getNumberOfQueries());
    processor.processQuery();
  }

  public void processQuery() {
    IQueryParser parser = new QueryParser();
    QueryExecutor executor = new QueryExecutor(parser.parseInfo(path, numberOfQueries));
    executor.execute();
  }
}
