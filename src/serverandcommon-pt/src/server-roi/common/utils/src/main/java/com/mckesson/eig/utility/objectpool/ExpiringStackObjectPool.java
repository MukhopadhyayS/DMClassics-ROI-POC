/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.objectpool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.Timer;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Auto-returns objects to the pool after their lease expires.
 *
 * @author Ronnie Andrews, Jr.
 */
public class ExpiringStackObjectPool extends StackObjectPool implements ActionListener {

	/**
	 * Config file entries.
	 */
	private static final Properties CONFIG_PROPS = new Properties();

	/**
	 * Key for idle check wait interval.
	 */
	private static final String IDLE_CHECK_WAIT_INTERVAL_KEY = "IDLE_CHECK_WAIT_INTERVAL";

	/**
	 * Key for max elapsed time.
	 */
	private static final String MAX_ELAPSED_TIME_KEY = "MAX_ELAPSED_TIME";

	/**
	 * One minute.
	 */
	private static final int ONE_MINUTE_IN_MS = 60000;

	/**
	 * Three minutes.
	 */
	private static final int THREE_MINUTES_IN_MS = 180000;

	/**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(ExpiringStackObjectPool.class);

	/**
	 * Current date.
	 */
	private Long _currentDate;

	/**
	 * Time to wait between idle checks.
	 */
	private int _idleCheckWaitInterval = ONE_MINUTE_IN_MS;

	/**
	 * Maximum lease time (default 3min).
	 */
	private long _maxElapsedTime = THREE_MINUTES_IN_MS;

	/**
	 * Cache of timestamps and objects.
	 */
	private final TreeMap<Long, Object> _dateMap = new TreeMap<Long, Object>(new ReverseComparator<Long>());

	/**
	 * Cache of objects and timestamps.
	 */
	private final Hashtable<Object, Long> _objectMap = new Hashtable<Object, Long>();

	/**
	 * Timer to check for expired leases (default 1min).
	 */
	private Timer _timer;

	/**
	 * Load config file if possible
	 */
	static {

		try {

			URL url =  ClassLoader.getSystemResource("object_pooling.properties");

			if (url != null) {
				CONFIG_PROPS.load(url.openStream());
			}

		} catch (FileNotFoundException e) {
			LOG.warn("object_pooling.properties not found, using defaults");
		} catch (IOException e) {
			LOG.warn("object_pooling.properties not found, using defaults");
		}
	}
	/**
	 * Specific constructor.
	 *
	 * @param poolFactory
	 */
	public ExpiringStackObjectPool(PoolableObjectFactory poolFactory) {
		super(poolFactory);
		this.initTimer();
	}

	/**
	 * Specific constructor.
	 *
	 * @param poolFactory
	 * @param maxIdle
	 */
	public ExpiringStackObjectPool(PoolableObjectFactory poolFactory, int maxIdle) {
		super(poolFactory, maxIdle);
		this.initTimer();
	}

	/**
	 * Specific constructor.
	 *
	 * @param poolFactory
	 * @param maxIdle
	 * @param initIdleCapacity
	 */
	public ExpiringStackObjectPool(PoolableObjectFactory poolFactory,
			int maxIdle, int initIdleCapacity) {
		super(poolFactory, maxIdle, initIdleCapacity);
		this.initTimer();
	}

	/**
	 * Over-ride base borrow.
	 *
	 * @throws Exception
	 */
	@Override
	public Object borrowObject() throws Exception {

		Object obj = null;

		try {

			obj = super.borrowObject();

			if (LOG.isDebugEnabled()) {
				LOG.debug("Borrowed object from pool: " + obj);
			}

			return obj;

		} finally {

			if ((!_timer.isRunning()) || (_currentDate == null)) {

				_currentDate = new Date().getTime();
				_timer.start();

				if (LOG.isDebugEnabled()) {
					LOG.debug("Started expiration check timer");
				}
			}

			_dateMap.put(_currentDate, obj);
			_objectMap.put(obj, _currentDate);
		}
	}

	/**
	 * Over-ride base return.
	 *
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void returnObject(Object obj) throws Exception {

		try {

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning object to pool: " + obj);
			}

			Long timestamp = _objectMap.get(obj);
			_objectMap.remove(obj);
			_dateMap.remove(timestamp);

			if ((_objectMap.size() == 0) && (_timer.isRunning())) {

				_timer.stop();
				_currentDate = null;

				if (LOG.isDebugEnabled()) {
					LOG.debug("Stopped expiration check timer");
				}
			}
		} finally {
			super.returnObject(obj);
		}
	}

	/**
	 * Check for expired objects.
	 */
	public void actionPerformed(ActionEvent actionEvent) {

		try {

			_currentDate = new Date().getTime();

			if (LOG.isDebugEnabled()) {
				LOG.debug("Looking for expired objects");
			}

			List<Object> expiredObjects = new ArrayList<Object>();
			for (Map.Entry<Long, Object> entry : _dateMap.entrySet()) {

				Long key = entry.getKey();
				long lapsedTime = _currentDate.longValue() - key.longValue();

				if (lapsedTime >= _maxElapsedTime) {
					expiredObjects.add(entry.getValue());
				} else {
					break;
				}
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning expired objects back to pool");
			}

			for (Object expiredObj : expiredObjects) {
				try {
					returnObject(expiredObj);
				} catch (Exception e) {
					LOG.warn("Return of object back to pool failed: "
							+ expiredObj);
				}
			}
		} catch (Exception e) {
			LOG.warn("Exception occured while checking for expired objects", e);
		}
	}

	/**
	 * Initialize member variables using config if available.
	 */
	private void initTimer() {

		if (CONFIG_PROPS.containsKey(IDLE_CHECK_WAIT_INTERVAL_KEY)) {
			try {
				_idleCheckWaitInterval = Integer.parseInt(
					CONFIG_PROPS.getProperty(IDLE_CHECK_WAIT_INTERVAL_KEY));
			} catch (Exception e) {
				LOG.warn("No default idle check wait interval settings found");
			}
		}

		if (CONFIG_PROPS.containsKey(MAX_ELAPSED_TIME_KEY)) {
			try {
				_maxElapsedTime = Long.parseLong(CONFIG_PROPS.getProperty(MAX_ELAPSED_TIME_KEY));
			} catch (Exception e) {
				LOG.warn("No default max elapsed time settings found");
			}
		}

		_timer = new Timer(_idleCheckWaitInterval, this);
	}

	private static class ReverseComparator<T extends Comparable<T>> implements Comparator<T> {

		/**
		 * Sort based on reverse
		 */
		public int compare(T arg0, T arg1) {

			T obj1 = arg0;
			T obj2 = arg0;
			int result = obj1.compareTo(obj2) * -1;
			return result;
		}
	}
}