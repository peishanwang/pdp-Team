package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.DataSource;
import edu.neu.ccs.cs5010.ski_data_model.DataSourceOpenMode;
import org.junit.Before;
import org.junit.Test;

public class DataSourceTest {
  private DataSource dataSource;
  private static final String PATH = "PDPAssignment8.csv";
  private static final int WIDTH = 1;

  @Before
  public void before() {
    dataSource = new DataSource(PATH, DataSourceOpenMode.ACCESS_MODEL, WIDTH);
  }


  @Test
  public void testConstructor() {
    dataSource = new DataSource(PATH, WIDTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModeException() {
    dataSource = new DataSource(PATH, DataSourceOpenMode.WRONG_MODE, WIDTH);
  }

  @Test(expected = IllegalStateException.class)
  public void testValidateStateException() {
    dataSource.create(new DataModelItem(new int[] {1}));
  }




}
