package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.LiftData;
import edu.neu.ccs.cs5010.ski_data_model.LiftDataModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class DataModelPoolTest {
  private static final Logger LOGGER
          = Logger.getLogger(DataModelPoolTest.class.getName());

  private IDataModelPool<LiftDataModel> dataModelPool;
  private static final String TEST_BASE_PATH = ".";
  private static final int TEST_POOL_SIZE = 5;
  private static final int TEST_NUM_THREADS = 100;
  private static final int TEST_THREAD_NUM_ITERATIONS = 1000;
  private static final int TEST_TIMEOUT_SECONDS = 2;

  @Before
  public void before() {
    dataModelPool = new DataModelPool<>(TEST_POOL_SIZE, () -> new LiftDataModel(TEST_BASE_PATH));
  }

  class PoolTester implements Runnable {
    private IDataModelPool<LiftDataModel> dataModelPool;
    private int numTestIterations;
    PoolTester(IDataModelPool<LiftDataModel> dataModelPool, int numIterations) {
      this.dataModelPool = dataModelPool;
      this.numTestIterations = numIterations;
    }

    @Override
    public void run() {
      for (int it = 0; it < numTestIterations; it++) {
        int liftId = ThreadLocalRandom.current().nextInt(40) + 1;
        LiftDataModel liftDataModel = dataModelPool.requestModel();
        LiftData liftData = liftDataModel.getDataInfo(liftId);
        if (liftId != liftData.getLiftId()) {
          throw new IllegalStateException("invalid liftId from model via pool");
        }
        LOGGER.log(Level.FINE, "lift: " + liftId + " has rides: " + liftData.getNumRides());
        dataModelPool.returnModel(liftDataModel);
      }
    }
  }

  @Test
  public void testDataModel() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(TEST_NUM_THREADS);
    List<Future> completionFutures = new ArrayList<>(TEST_NUM_THREADS);
    for (int i = 0; i < TEST_NUM_THREADS; i++) {
      completionFutures.add(executor.submit(new PoolTester(dataModelPool,
              TEST_THREAD_NUM_ITERATIONS)));
    }
    for (Future f : completionFutures) {
      try {
        f.get(TEST_TIMEOUT_SECONDS, TimeUnit.SECONDS);
      } catch (ExecutionException | TimeoutException e) {
        fail("test didn't finish in time");
      }
    }
  }
}
