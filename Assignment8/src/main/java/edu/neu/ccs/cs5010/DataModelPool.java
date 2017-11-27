package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.ski_data_model.DataModelItem;
import edu.neu.ccs.cs5010.ski_data_model.IDataModel;
import edu.neu.ccs.cs5010.ski_data_model.LiftDataModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class DataModelPool<DM extends IDataModel<? extends DataModelItem>> implements
        IDataModelPool<DM> {

  public DataModelPool(int maxSize, IDataModelFactory<DM> modelCreator) {
    this.maxPoolSize = maxSize;
    this.modelCreator = modelCreator;
    this.dataModelPool = new LinkedList<>();
    this.currentPoolSize = 0;
    this.modelsInFlight = 0;
  }

  @Override
  public synchronized DM requestModel() {
    validateState();
    if (currentPoolSize > 0) {
      modelsInFlight++;
      currentPoolSize--;
      return dataModelPool.remove();
    }
    if (modelsInFlight < maxPoolSize) {
      modelsInFlight++;
      return modelCreator.create();
    } else {
      if (modelsInFlight != maxPoolSize) {
        throw new IllegalStateException("");
      }
      try {
        while (currentPoolSize == 0 && modelsInFlight == maxPoolSize) {
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
    validateState();
    dataModelPool.add((DM)model);
    modelsInFlight--;
    currentPoolSize++;
    validateState();
    notifyAll();
  }

  @Override
  public synchronized void close() {
    for (DM model : dataModelPool) {
      model.close();
    }
  }

  private void validateState() {
    boolean ok = (currentPoolSize >= 0);
    ok &= (currentPoolSize <= maxPoolSize);
    ok &= (modelsInFlight >= 0);
    ok &= (currentPoolSize + modelsInFlight <= maxPoolSize);
    if (!ok) {
      throw new IllegalStateException("Illegal state of pol, currentPoolSize: " + currentPoolSize
              + ", inFlight: " + modelsInFlight + ", maxSize: " + maxPoolSize);
    }
  }

  private final int maxPoolSize;
  private final IDataModelFactory<DM> modelCreator;
  private final Queue<DM> dataModelPool;
  private int currentPoolSize;
  private int modelsInFlight;

  public static void main(String[] args) throws InterruptedException {
    final String basePath = ".";
    final IDataModelPool<LiftDataModel> dataModelPool =
            new DataModelPool<>(10, () -> new LiftDataModel(basePath));

    final int numThreads = 100;
    final int numIterations = 1000;
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
