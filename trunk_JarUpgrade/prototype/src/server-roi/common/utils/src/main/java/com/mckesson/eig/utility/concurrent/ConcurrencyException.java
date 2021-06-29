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

import com.mckesson.eig.utility.exception.ApplicationException;

/**
 * Cascades the exception with appropriate message and cause.
 *
 */
public class ConcurrencyException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
     * There is no exception to cascade and no additional message. In this case
     * the class is just like a RuntimeException.
     */
    public ConcurrencyException() {
        super();
    }

    /**
     * Construct with a message only. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.
     *
     * @param message
     *            Exception message
     */
    public ConcurrencyException(String message) {
        super(message);
    }

    /**
     * Construct with a cause.
     *
     * @param cause
     *            The exception we are cascading.
     */
    public ConcurrencyException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct with a message and a cause.
     *
     * @param message
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public ConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
