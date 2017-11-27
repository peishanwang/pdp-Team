package edu.neu.ccs.cs5010;

import java.io.IOException;
import java.util.List;

public class SkiQueryProcessor {
  private static final String PATH = "PDPAssignment8.csv";

  public static void main(String[] args) {
    SkiQueryProcessor processor = new SkiQueryProcessor();
    processor.processQuery();

  }

  public void processQuery() {
    IQueryParser parser = new QueryParser();
    QueryExecutor executor = new QueryExecutor(parser.parseInfo(PATH),20);
    executor.start();
    try {
      executor.join();
    } catch (InterruptedException e) {
      e.getStackTrace();
    }

  }
}
