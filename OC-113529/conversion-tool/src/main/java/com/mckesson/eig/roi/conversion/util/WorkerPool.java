package com.mckesson.eig.roi.conversion.util;

public class WorkerPool {

	private Worker[] _workers;
	private static final int DEFAULT_SIZE = 5;
	private static final int WAITING_TIME = 10;

	public WorkerPool() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Creates a new worker pool for the given number of workers and with the
	 * given name prefix.
	 * 
	 * @param size
	 *            the size of the worker pool.
	 * @param namePrefix
	 *            the name prefix for all created workers.
	 */
	public WorkerPool(final int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be > 0");
		}
		_workers = new Worker[size];
	}

	/**
	 * Checks whether workers are available.
	 * 
	 * @return true, if at least one worker is idle, false otherwise.
	 */
	public synchronized boolean isWorkerAvailable() {
		for (int i = 0; i < _workers.length; i++) {
			if (_workers[i] == null) {
				return true;
			}
			if (_workers[i].isAvailable()) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Waits until a worker will be available.
	 */
	private synchronized void waitForWorkerAvailable() {
		while (!isWorkerAvailable()) {
			try {
				this.wait(WAITING_TIME);
			} catch (InterruptedException ie) {
			}
		}
	}

	/**
	 * Returns a workerhandle for the given workload. This method will wait
	 * until an idle worker is found.
	 * 
	 * @param r
	 *            the workload for the worker
	 * @return a handle to the worker.
	 */
	public synchronized Worker getWorkerForWorkload(final Runnable r) {
		waitForWorkerAvailable();

		for (int i = 0; i < _workers.length; i++) {
			if (_workers[i] == null) {
				_workers[i] = new Worker();
				_workers[i].setWorkerPool(this);
				_workers[i].setWorkload(r);
				return _workers[i];
			}
			if (_workers[i].isAvailable()) {
				_workers[i].setWorkload(r);
				return _workers[i];
			}
		}
		return null;
	}

	public void workerFinished(final Worker worker) {
		if (!worker.isFinish()) {
			throw new IllegalArgumentException(
					"This worker is not in the finish state.");
		}
		for (int i = 0; i < _workers.length; i++) {
			if (_workers[i] == worker) {
				synchronized (this) {
					_workers[i] = null;
					this.notifyAll();
				}
				return;
			}
		}
	}

	public synchronized void workerAvailable(final Worker worker) {
		for (int i = 0; i < _workers.length; i++) {
			if (_workers[i] == worker) {
				synchronized (this) {
					this.notifyAll();
				}
				return;
			}
		}
	}

	public boolean isAllWorkersAvailable() {
		for (int i = 0; i < _workers.length; i++) {
			if (_workers[i] != null) {
				if (!_workers[i].isAvailable()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Finishes all worker of this pool.
	 */
	public void finishAll() {
		for (int i = 0; i < _workers.length; i++) {
			if (_workers[i] != null) {
				_workers[i].finish();
			}
		}
	}
}
