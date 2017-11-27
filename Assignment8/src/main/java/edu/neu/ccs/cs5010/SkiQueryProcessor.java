package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist.SkiDataProcessor;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

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
