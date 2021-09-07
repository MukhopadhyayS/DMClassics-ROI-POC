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

import org.apache.commons.pool.PoolableObjectFactory;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Factory for creating poolable objects for a given class.
 *
 * @author Ronnie Andrews, Jr.
 */
final class ClassPoolableObjectFactory implements PoolableObjectFactory {

	/**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(ClassPoolableObjectFactory.class);

	/**
	 * Desired class to pool objects for.
	 */
	private final Class<?> _desiredClass;

	/**
	 * Specific constructor.
	 *
	 * @param desiredClass
	 */
	public ClassPoolableObjectFactory(Class<?> desiredClass) {

		if (desiredClass == null) {
			String msg = "Given class was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Creating factory for class: " + desiredClass.getName());
		}

		if (!PoolableObject.class.isAssignableFrom(desiredClass)) {
			String msg = "Given class does not implement PoolableObject: " + desiredClass.getName();
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		this._desiredClass = desiredClass;
	}

	/**
	 * Activate object.
	 *
	 * @param obj
	 */
	public void activateObject(Object obj) throws Exception {

		if (obj == null) {
			String msg = "Given object was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("activateObject called on: " + obj);
		}

		//no need to do anything since it is already in a clean state
	}

	/**
	 * Reset object.
	 *
	 * @param obj
	 */
	public void destroyObject(Object obj) throws Exception {

		if (obj == null) {
			String msg = "Given object was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("destroyObject called on: " + obj);
		}

		//clean up object
		PoolableObject poolableObject = (PoolableObject) obj;
		poolableObject.resetObject();
	}

	/**
	 * Create new object.
	 */
	public Object makeObject() throws Exception {

		//use default constructor only
		Object obj = this._desiredClass.newInstance();

		if (LOG.isDebugEnabled()) {
			LOG.debug("makeObject called on: " + obj);
		}

		return obj;
	}

	/**
	 * Invoked when object is returned to pool.
	 */
	public void passivateObject(Object obj) throws Exception {

		if (obj == null) {
			String msg = "Given object was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("passivateObject called on: " + obj);
		}

		//clean up object
		PoolableObject poolableObject = (PoolableObject) obj;
		poolableObject.resetObject();
	}

	/**
	 * True if object is in clean state.
	 */
	public boolean validateObject(Object obj) {

		if (obj == null) {
			String msg = "Given object was null";
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("validateObject called on: " + obj);
		}

		//all objects should be in a good state, not every pool may call validate
		return obj != null;
	}
}
