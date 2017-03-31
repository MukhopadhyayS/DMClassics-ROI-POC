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

package com.mckesson.eig.utility.exception;

/**
 * @see ApplicationException
 */
public class InvalidStateException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
     * Default constructor. There is no exception to cascade and no additional
     * message. In this case the class is just like a RuntimeException
     *
     * @see ApplicationException()
     */
    public InvalidStateException() {
    }

    /**
     * Construct with a Throwable. In this case the class acts as a cascaded
     * exception.
     *
     * @param cause
     *            the exception we are cascading. We set the message based on
     *            the message from the cascaded exception
     *
     * @see ApplicationException(String)
     */
    public InvalidStateException(String message) {
        super(message);
    }

    /**
     * Construct with a Throwable. In this case the class acts as a cascaded
     * exception.
     *
     * @param cause
     *            the exception we are cascading. We set the message based on
     *            the message from the cascaded exception
     *
     * @see ApplicationException(String)
     */
    public InvalidStateException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct with a message and a cause. Since there is a chained exception
     * the #getExtendedCause() for this exception will return the cascaded
     * exceptions #getExtendedCause() or the actual exception.
     *
     * @param msg
     *            Message to describe the exception
     * @param cause
     *            the exception we are cascading
     */
    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
