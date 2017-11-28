package edu.neu.ccs.cs5010;

/**
 * Main class to process ski data query.
 */
public class SkiQueryProcessor {
  private String path;
  private int numOfThreads;

  /**
   * Constructor of SkiQueryProcessor.
   * @param path input file path
   * @param numOfThreads number of threads to process the data
   */
  public SkiQueryProcessor(String path, int numOfThreads) {
    this.path = path;
    this.numOfThreads = numOfThreads;
  }

  /**
   * Main method.
   * @param args input arguments
   */
  public static void main(String[] args) {
    QueryCmdParser parser = new QueryCmdParser(args);
    SkiQueryProcessor processor = new SkiQueryProcessor(parser.getCsvFile(),
        parser.getNumberOfQueries());
    processor.processQuery();
  }

  /**
   * Method to call the executor processing queries.
   */
  public void processQuery() {
    IQueryParser parser = new QueryParser();
    QueryExecutor executor = new QueryExecutor(parser.parseInfo(path), numOfThreads);
    executor.execute();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    SkiQueryProcessor processor = (SkiQueryProcessor) obj;

    if (numOfThreads != processor.numOfThreads) {
      return false;
    }
    return path.equals(processor.path);
  }

  @Override
  public int hashCode() {
    int result = path.hashCode();
    result = 31 * result + numOfThreads;
    return result;
  }
}
