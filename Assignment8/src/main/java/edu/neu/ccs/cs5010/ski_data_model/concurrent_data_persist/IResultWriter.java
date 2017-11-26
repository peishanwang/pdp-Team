package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import java.util.List;

/**
 * Interface of ResultWriter.
 *
 */
public interface IResultWriter {
  /**
   * Write result to file.
   * @param path output file path.
   * @param rows output file text.
   */
  void write(String path, List<Object[]> rows);
}
