package com.mckesson.eig.roi.conversion.util;

import org.apache.log4j.Logger;

public final class Worker extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Worker.class);
	private Runnable _workload;
	private volatile boolean _finish;
	private final int _sleeptime;
	private WorkerPool _workerPool;

	private static final int DEFUALT_SLEEPING_TIME = 6000;

	public Worker(final int sleeptime) {
		this._sleeptime = sleeptime;
		this.setDaemon(true);
		start();
	}

	public Worker() {
		this(DEFUALT_SLEEPING_TIME);
	}

	/**
	 * Set the next workload for this worker.
	 *
	 * @param r
	 *            the next workload for the worker.
	 * @throws IllegalStateException
	 *             if the worker is not idle.
	 */
	public void setWorkload(final Runnable r) {
		if (_workload != null) {
			throw new IllegalStateException("This worker is not idle.");
		}
		LOGGER.debug("Start converted." + r);
		// Log.debug("Workload set...");
		synchronized (this) {
			_workload = r;
			// Log.debug("Workload assigned: Notified " + getName());
			this.notifyAll();
		}
	}

	/**
	 * Returns the workload object.
	 *
	 * @return the runnable executed by this worker thread.
	 */
	public synchronized Runnable getWorkload() {
		return _workload;
	}

	/**
	 * Kills the worker after he completed his work. Awakens the worker if he's
	 * sleeping, so that the worker dies without delay.
	 */
	public void finish() {
		_finish = true;
		try {
			this.interrupt();
		} catch (SecurityException se) {
		}
		if (_workerPool != null) {
			_workerPool.workerFinished(this);
		}
	}

	/**
	 * Checks whether this worker has some work to do.
	 *
	 * @return true, if this worker has no more work and is currently sleeping.
	 */
	public boolean isAvailable() {
		return (_workload == null);
	}

	/**
	 * If a workload is set, process it. After the workload is processed, this
	 * worker starts to sleep until a new workload is set for the worker or the
	 * worker got the finish() request.
	 */
	public void run() {
		while (!_finish) {
			if (_workload != null) {
				try {
					_workload.run();
				} catch (Exception e) {
					System.out.println("Worker caught exception on run: " + e);
				}
				LOGGER.debug("Finish converted." + _workload);
				_workload = null;
				if (_workerPool != null) {
					_workerPool.workerAvailable(this);
				}
			}

			if (!_finish) {
				synchronized (this) {
					try {
						// remove lock
						this.wait(_sleeptime);
					} catch (InterruptedException ie) {
						// ignored
					}
				}
			}
		}

		synchronized (this) {
			this.notifyAll();
		}
	}

	/**
	 * Checks whether this worker has received the signal to finish and die.
	 *
	 * @return true, if the worker should finish the work and end the thread.
	 */
	public boolean isFinish() {
		return _finish;
	}

	/**
	 * Returns the worker's assigned pool.
	 *
	 * @return the worker pool (or null, if the worker is not assigned to a
	 *         pool).
	 */
	public WorkerPool getWorkerPool() {
		return _workerPool;
	}

	/**
	 * Defines the worker's assigned pool.
	 *
	 * @param workerPool
	 *            the worker pool (or null, if the worker is not assigned to a
	 *            pool).
	 */
	public void setWorkerPool(final WorkerPool workerPool) {
		this._workerPool = workerPool;
	}
}

