package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;

/**
 * QueryCmdParser is used to parse argument from command line.
 */
public class QueryCmdParser {
  private static final int NEEDED_ARGS = 2;
  private static final int CSVFILE_INDEX = 0;
  private static final int NUM_QUERIES_INDEX = 1;
  private String csvFile;
  private int numberOfQueries;

  /**
   * Constructor of QueryCmdParser.
   * @param args input arguments
   */
  public QueryCmdParser(String[] args) {
    if (args.length != NEEDED_ARGS) {
      throw new IllegalCmdArgumentException("You didn't provide right number of arguments.");
    }
    csvFile = args[CSVFILE_INDEX];
    numberOfQueries = Integer.parseInt(args[NUM_QUERIES_INDEX]);
    if (numberOfQueries < 0 || numberOfQueries % 20 != 0) {
      throw new IllegalCmdArgumentException("Illegal number of queries.");
    }
  }

  /**
   * Returns csv file path.
   * @return csv file path.
   */
  public String getCsvFile() {
    return csvFile;
  }

  /**
   * Returns number of queries.
   * @return number of queries.
   */
  public int getNumberOfQueries() {
    return numberOfQueries;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    QueryCmdParser that = (QueryCmdParser) obj;

    if (getNumberOfQueries() != that.getNumberOfQueries()) {
      return false;
    }
    return getCsvFile().equals(that.getCsvFile());
  }

  @Override
  public int hashCode() {
    int result = getCsvFile().hashCode();
    result = 31 * result + getNumberOfQueries();
    return result;
  }
}
