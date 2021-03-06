package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.exceptions.IllegalCmdArgumentException;

/**
 * This is a class to parse the command line input arguments.
 *
 */

public class CmdParser implements ICmdParser{
  private static final int NEEDED_ARGS = 2;
  private static final int CSVFILE_INDEX = 0;
  private static final int FLAG_INDEX = 1;
  private static final int HASHCODE_COEFFICIENT = 31;
  private static final String SEQUENTIAL = "Sequential";
  private static final String CONCURRENT = "Concurrent";
  private String csvFile;
  private String flag;

  /**
   * Constructor of CmdParser.
   * @param args input arguments.
   */
  public CmdParser(String[] args) {
    if (args.length != NEEDED_ARGS) {
      throw new IllegalCmdArgumentException("You didn't provide right number of arguments.");
    }
    csvFile = args[CSVFILE_INDEX];
    flag = args[FLAG_INDEX];
    if (flag.equalsIgnoreCase(SEQUENTIAL)) {
      flag = SEQUENTIAL;
    } else if (flag.equalsIgnoreCase(CONCURRENT)) {
      flag = CONCURRENT;
    } else {
      throw new IllegalCmdArgumentException("neither sequential nor concurrent");
    }
  }

  @Override
  public String getCsvFile() {
    return csvFile;
  }

  @Override
  public String getFlag() {
    return flag;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CmdParser cmdParser = (CmdParser) obj;
    return getCsvFile().equals(cmdParser.getCsvFile())
        && getFlag().equals(cmdParser.getFlag());
  }

  @Override
  public int hashCode() {
    int result = getCsvFile().hashCode();
    result = HASHCODE_COEFFICIENT * result + getFlag().hashCode();
    return result;
  }
}
