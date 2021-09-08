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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.io.IOUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Generic object pool based on classes as the keys.
 *
 * @author Ronnie Andrews, Jr.
 */
public final class EIGObjectPool {

	/**
	 * Config file entries.
	 */
	private static final Properties CONFIG_PROPS = new Properties();

	/**
	 * Key for init idle capacity.
	 */
	private static final String DEFAULT_INIT_IDLE_CAPACITY_KEY = "DEFAULT_INIT_IDLE_CAPACITY";

	/**
	 * Key for max idle.
	 */
	private static final String DEFAULT_MAX_IDLE_OBJECTS_KEY = "DEFAULT_MAX_IDLE_OBJECTS";

	/**
	 * Key suffix for init idle capacity.
	 */
	private static final String INIT_IDLE_CAPACITY_KEY_SUFFIX = "_INIT_IDLE_CAPACITY";

	/**
	 * Key suffix for max idle.
	 */
	private static final String MAX_IDLE_OBJECTS_KEY_SUFFIX = "_MAX_IDLE_OBJECTS";

	/**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(EIGObjectPool.class);

	/**
	 * Singleton instance.
	 */
	private static EIGObjectPool _instance = new EIGObjectPool();

	/**
	 * Object pool map.
	 */
	private final Hashtable<Class<?>, ObjectPool> _objectPoolMap;

	/**
	 * Load config file if possible
	 */
	static {
		InputStream inputStream = null;
		try {
			URL url =  ClassLoader.getSystemResource("object_pooling.properties");
			if (url != null) {
				inputStream = url.openStream();
				CONFIG_PROPS.load(inputStream);
			}
		} catch (FileNotFoundException e) {
			LOG.warn("object_pooling.properties not found, using defaults");
		} catch (IOException e) {
			LOG.warn("object_pooling.properties not found, using defaults");
		} finally {
			IOUtilities.close(inputStream);
		}
	}

	/**
	 * Default constructor.
	 */
	private EIGObjectPool() {

		_objectPoolMap = new Hashtable<Class<?>, ObjectPool>();
	}

	/**
	 * Get singleton instance.
	 *
	 * @return
	 */
	public static EIGObjectPool getInstance() {
		return _instance;
	}

	/**
	 * Borrow object from pool for given class.
	 *
	 * @param desiredClass
	 * @return
	 */
	public Object borrowObject(Class<?> desiredClass) {

		if (desiredClass == null) {
			String msg = "Given class was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		ObjectPool pool = _objectPoolMap.get(desiredClass);

		if (pool == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Creating pool for class: " + desiredClass.getName());
			}

			//add pool
			PoolableObjectFactory poolFactory =
				new ClassPoolableObjectFactory(desiredClass);

			pool = createExpiringStackObjectPool(desiredClass, poolFactory);

			_objectPoolMap.put(desiredClass, pool);
		} else {

			if (LOG.isDebugEnabled()) {
				LOG.debug("Re-using pool for class: " + desiredClass.getName());
			}
		}

		Object pooledObject = null;

		try {
			pooledObject = pool.borrowObject();
		} catch (Exception e) {
			throw new ApplicationException(
					"Unable to borrow object from pool of type: "
					+ desiredClass.getName());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Borrowing object: " + pooledObject);
		}

		return pooledObject;
	}

	/**
	 * Clear pools of objects.
	 *
	 * @throws Exception
	 */
	public void clearPools() throws Exception {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Clearing all object pools");
		}

		for (ObjectPool pool : _objectPoolMap.values()) {
			pool.clear();
		}
	}

	/**
	 * Close pools, will clear first.
	 *
	 * @throws Exception
	 */
	public void closePools() throws Exception {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Closing all object pools");
		}

		for (ObjectPool pool : _objectPoolMap.values()) {
			pool.clear();
			pool.close();
		}

		_objectPoolMap.clear();
	}

	/**
	 * Return object back to the pool.
	 *
	 * @param pooledObject
	 */
	public void returnObject(Object pooledObject) {

		if (pooledObject == null) {
			String msg = "Given object was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		ObjectPool pool = _objectPoolMap.get(pooledObject.getClass());

		if (pool != null) {
			try {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Returning object back to pool: " + pooledObject);
				}

				pool.returnObject(pooledObject);

			} catch (Exception e) {
				LOG.warn("Ignoring failed return to pool for object");
			}
		}
	}

	/**
	 * Create expiring object pool.
	 *
	 * @param desiredClass
	 * @param poolFactory
	 * @return
	 */
	private ObjectPool createExpiringStackObjectPool(Class<?> desiredClass,
			PoolableObjectFactory poolFactory) {

		int initIdleCapacity = -1;

		String classInitIdleCapacityKey = desiredClass.getName()
			+ INIT_IDLE_CAPACITY_KEY_SUFFIX;

		if (CONFIG_PROPS.containsKey(classInitIdleCapacityKey)) {

			try {
				initIdleCapacity = Integer.parseInt(
						CONFIG_PROPS.getProperty(classInitIdleCapacityKey));
			} catch (Exception e) {
				LOG.warn("No class pool settings found for: "
						+ desiredClass.getName());
			}
		}

		if (CONFIG_PROPS.containsKey(DEFAULT_INIT_IDLE_CAPACITY_KEY)) {

			try {
				initIdleCapacity = Integer.parseInt(
				    CONFIG_PROPS.getProperty(DEFAULT_INIT_IDLE_CAPACITY_KEY));
			} catch (Exception e) {
				LOG.warn("No default pool idle capacity settings found for: "
						+ desiredClass.getName());
			}
		}

		int maxIdle = -1;

		String classMaxIdleKey = desiredClass.getName() + MAX_IDLE_OBJECTS_KEY_SUFFIX;

		if (CONFIG_PROPS.containsKey(classMaxIdleKey)) {
			try {
				maxIdle = Integer.parseInt(CONFIG_PROPS.getProperty(classMaxIdleKey));
			} catch (Exception e) {
				LOG.warn("No class specific pool max idle settings found for: "
						+ desiredClass.getName());
			}
		}

		if (CONFIG_PROPS.containsKey(DEFAULT_MAX_IDLE_OBJECTS_KEY)) {
			try {
				maxIdle = Integer.parseInt(CONFIG_PROPS.getProperty(DEFAULT_MAX_IDLE_OBJECTS_KEY));
			} catch (Exception e) {
				LOG.warn("No default pool max idle settings found for: "
						+ desiredClass.getName());
			}
		}

		ObjectPool pool = initializeExpiringStackObjectPool(poolFactory, maxIdle, initIdleCapacity);

		return pool;
	}

	/**
	 * Initialize object pool.
	 *
	 * @param poolFactory
	 * @param maxIdle
	 * @param initIdleCapacity
	 * @return
	 */
	private ObjectPool initializeExpiringStackObjectPool(PoolableObjectFactory poolFactory,
			int maxIdle, int initIdleCapacity) {

		ObjectPool pool = null;

		if ((maxIdle != -1) && (initIdleCapacity != -1)) {
			pool = new ExpiringStackObjectPool(poolFactory, maxIdle, initIdleCapacity);
		} else if (maxIdle != -1) {
			pool = new ExpiringStackObjectPool(poolFactory, maxIdle);
		} else {
			pool = new ExpiringStackObjectPool(poolFactory);
		}

		return pool;
	}
}
