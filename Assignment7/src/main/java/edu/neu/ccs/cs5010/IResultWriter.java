package edu.neu.ccs.cs5010;

import java.util.List;

public interface IResultWriter {
  void write(String path, List<Object[]> rows);
}
