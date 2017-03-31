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

package com.mckesson.eig.utility.util;

import com.mckesson.eig.utility.exception.ApplicationException;

/**
 * class UtilitiesException
 *
 * A wrapper for exceptions thrown from classes in this package.
 */
public class UtilitiesException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
     * Default Constructor.
     */
    public UtilitiesException() {
    }

    /**
     * Constructor for when you want to add a message to the exception.
     *
     * @param message
     *            Passed as an argument of type String.
     */
    public UtilitiesException(String message) {
        super(message);
    }

    /**
     * Constructor for when you want to chain an exception.
     *
     * @param cause
     *            Passed as an argument of type Throwable class.
     */
    public UtilitiesException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for adding a message and chaining another exception.
     *
     * @param message
     *            Passed as an argument of type String.
     * @param cause
     *            Passed as an argument of type Throwable class.
     */
    public UtilitiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
