package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.IDataModel;
import edu.neu.ccs.cs5010.ski_data_model.LiftDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;

public class DataModelPool<DM extends IDataModel<? extends DataModelItem>> implements
        IDataModelPool<DM> {

  public DataModelPool(int maxSize, IDataModelFactory<DM> modelCreator) {
    this.maxPoolSize = maxSize;
    this.modelCreator = modelCreator;
    this.dataModelPool = new ConcurrentLinkedDeque<>();
    this.currentPoolSize = 0;
    this.modelsInFlight = 0;
  }

  @Override
  public synchronized DM requestModel() {
    if (currentPoolSize > 0) {
      modelsInFlight++;
      currentPoolSize--;
      return dataModelPool.remove();
    }
    if (modelsInFlight < maxPoolSize) {
      modelsInFlight++;
      return modelCreator.create();
    } else {
      try {
        while (modelsInFlight == maxPoolSize && currentPoolSize == 0) {
          wait();
        }
        modelsInFlight++;
        currentPoolSize--;
        return dataModelPool.remove();
      } catch (InterruptedException e) {
        throw new IllegalStateException("thread interrupted");
      }
    }
  }

  @Override
  public synchronized void returnModel(DM model) {
    dataModelPool.add((DM)model);
    modelsInFlight--;
    currentPoolSize++;
    notifyAll();
  }

  @Override
  public synchronized void close() {
    for (DM model : dataModelPool) {
      model.close();
    }
  }

  private final int maxPoolSize;
  private final IDataModelFactory<DM> modelCreator;
  private final Queue<DM> dataModelPool;
  private int currentPoolSize;
  private int modelsInFlight;

  public static void main(String[] args) throws InterruptedException {
    final String basePath = "D:\\pdp_team_assignments\\Assignment8";
    final IDataModelPool<LiftDataModel> dataModelPool =
            new DataModelPool<>(2, () -> new LiftDataModel(basePath));

    final int numThreads = 5;
    final int numIterations = 10;
    List<Thread> threads = new ArrayList<>(numThreads);
    for (int i = 0; i < numThreads; i++) {
      threads.add(new Thread(new Runnable() {
        @Override
        public void run() {
          for (int it = 0; it < numIterations; it++) {
            int liftId = ThreadLocalRandom.current().nextInt(40) + 1;
            LiftDataModel liftDataModel = dataModelPool.requestModel();
            System.out.println("lift: " + liftId + " has rides: " + liftDataModel.getDataInfo(liftId));
            dataModelPool.returnModel(liftDataModel);
          }
        }
      }));
      threads.get(i).run();
    }
    for (int i = 0; i < numThreads; i++) {
      threads.get(i).join();
    }
  }
}
