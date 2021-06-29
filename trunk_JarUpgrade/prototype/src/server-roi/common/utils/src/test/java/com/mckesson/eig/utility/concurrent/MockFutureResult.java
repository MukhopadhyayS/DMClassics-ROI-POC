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

package com.mckesson.eig.utility.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This is a Mock class for <code>FutureResult</code>.
 */
public class MockFutureResult<T> extends FutureTask<T> {

	private T _value = null;
    private Exception _exception = null;

    /**
     * Constructor of MockFutureResult class.
     *
     * @param e
     *            InterruptedException class.
     */
    public MockFutureResult() {
    	super(new Callable<T>() { public T call() { return null; }});
    }

    public MockFutureResult(Exception e) {
    	this();
        _exception = e;
    }

	/**
	 * @param value the value to set
	 */
    @Override
	public void set(T value) {
    	_value = value;
    }

	/**
	 * @return the exception
	 */
	public Exception getException()
	{
		return _exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(Exception exception)
	{
		_exception = exception;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException {
        if (_exception instanceof TimeoutException) {
            throw (TimeoutException) _exception;
        } else if (_exception instanceof InterruptedException) {
            throw (InterruptedException) _exception;
        } else if (_exception instanceof ExecutionException) {
            throw (ExecutionException) _exception;
        }
        return _value;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
        if (_exception instanceof InterruptedException) {
            throw (InterruptedException) _exception;
        } else if (_exception instanceof ExecutionException) {
            throw (ExecutionException) _exception;
        }
        return _value;
    }
}
