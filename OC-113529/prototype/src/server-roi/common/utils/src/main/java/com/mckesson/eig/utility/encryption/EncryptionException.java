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

package com.mckesson.eig.utility.encryption;

import com.mckesson.eig.utility.exception.ChainedException;

/**
 * Extends ChainedException rather than ApplicationException to support the CI
 * secure token library.
 */
public class EncryptionException extends ChainedException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new runtime exception with <code>null</code> as its detail
     * message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public EncryptionException() {
        super();
    }

    /**
     * Construct with a message only. There is no exception to cascade. In this
     * case this class will act like any other RuntimeException.
     *
     * @param message
     *            Exception message
     */
    public EncryptionException(String message) {
        super(message);
    }

    /**
     * Returns the detail message string of this throwable instance (which may
     * not be <code>null</code>).
     *
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            <code>getCause()</code>. (A <tt>null</tt> value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public EncryptionException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns the detail message string of this throwable instance (which may
     * not be <code>null</code>) and cascades the exception.
     *
     * @param message
     *            description about the exception.
     * @param cause
     *            exception we are cascading.
     */
    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
