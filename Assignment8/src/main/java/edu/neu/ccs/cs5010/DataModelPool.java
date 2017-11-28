package edu.neu.ccs.cs5010;

import edu.neu.ccs.cs5010.skidatamodel.DataModelItem;
import edu.neu.ccs.cs5010.skidatamodel.IdataModel;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class of DataModelPool containing a list of DataModel.
 * @param <D> anything that extends DataModel.
 */
public class DataModelPool<D extends IdataModel<? extends DataModelItem>> implements
        IDataModelPool<D> {

  /**
   * Constructor of DataModelPool.
   * @param maxSize maxsize of pool
   * @param modelCreator factory of specific DataModel
   */
  public DataModelPool(int maxSize, IDataModelFactory<D> modelCreator) {
    this.maxPoolSize = maxSize;
    this.modelCreator = modelCreator;
    this.dataPool = new LinkedList<>();
    this.currentPoolSize = 0;
    this.modelsInFlight = 0;
  }

  @Override
  public synchronized D requestModel() {
    validateState();
    if (currentPoolSize > 0) {
      modelsInFlight++;
      currentPoolSize--;
      return dataPool.remove();
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
        return dataPool.remove();
      } catch (InterruptedException e) {
        throw new IllegalStateException("thread interrupted");
      }
    }
  }

  @Override
  public synchronized void returnModel(D model) {
    validateState();
    dataPool.add((D)model);
    modelsInFlight--;
    currentPoolSize++;
    validateState();
    notifyAll();
  }

  @Override
  public synchronized void close() {
    for (D model : dataPool) {
      model.close();
    }
  }

  /**
   * Validate the state of pool.
   */
  private void validateState() {
    boolean okay = (currentPoolSize >= 0);
    okay &= (currentPoolSize <= maxPoolSize);
    okay &= (modelsInFlight >= 0);
    okay &= (currentPoolSize + modelsInFlight <= maxPoolSize);
    if (!okay) {
      throw new IllegalStateException("Illegal state of pol, currentPoolSize: " + currentPoolSize
              + ", inFlight: " + modelsInFlight + ", maxSize: " + maxPoolSize);
    }
  }

  private final int maxPoolSize;
  private final IDataModelFactory<D> modelCreator;
  private final Queue<D> dataPool;
  private int currentPoolSize;
  private int modelsInFlight;
}
