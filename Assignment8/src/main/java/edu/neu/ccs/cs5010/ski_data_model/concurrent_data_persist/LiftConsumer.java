package edu.neu.ccs.cs5010.ski_data_model.concurrent_data_persist;

import edu.neu.ccs.cs5010.ski_data_model.DataSourceOpenMode;
import edu.neu.ccs.cs5010.ski_data_model.LiftData;
import edu.neu.ccs.cs5010.ski_data_model.LiftDataModel;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This is part of PDP Assignment 7.
 *
 * @author Manika and Peishan
 */
public class LiftConsumer implements Consumer<Integer> {
  private ConcurrentMap<Integer, AtomicInteger> liftNumRidesMap;
  private AtomicBoolean finished;

  /**
   * LiftConsumer constructor.
   */
  public LiftConsumer() {
    liftNumRidesMap = new ConcurrentSkipListMap<>();
    finished = new AtomicBoolean(false);
  }

  /**
   * method overridden by Constructor class.
   * @param liftId id of lift.
   */
  @Override
  public void accept(Integer liftId) {
    if (liftId == null) {
      // this is signal from one of threads that queue is empty now
      if (finished.compareAndSet(false, true)) {
        // write file
        LiftDataModel liftDataModel = new LiftDataModel(SkiDataProcessor.SKI_DATA_MODEL_BASE_PATH,
                DataSourceOpenMode.CREATE_MODEL);
        for (Map.Entry<Integer, AtomicInteger> liftNumRidesEntry :
                liftNumRidesMap.entrySet()) {
          LiftData data = LiftData.constructLiftData(liftNumRidesEntry.getKey(),
                  liftNumRidesEntry.getValue().get());
          liftDataModel.addDataInfo(data);
        }
        liftDataModel.close();
      }
      return;
    }
    liftNumRidesMap.putIfAbsent(liftId, new AtomicInteger(0));
    liftNumRidesMap.get(liftId).incrementAndGet();
  }

}
