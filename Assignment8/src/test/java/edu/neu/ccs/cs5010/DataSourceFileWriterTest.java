package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.DataModelItem;
import edu.neu.ccs.cs5010.skidatamodel.DataSourceFileWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DataSourceFileWriterTest {
  private static final String TEST_PATH = "test";
  private DataSourceFileWriter writer;

  @Before
  public void before() {
    writer = new DataSourceFileWriter(TEST_PATH, 40);
  }

  @Test
  public void testGetNumRows() {
    Assert.assertEquals(0, writer.getNumRowsWritten());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrite() throws IOException{
    writer.write(new DataModelItem(new int[5]));
  }
}
