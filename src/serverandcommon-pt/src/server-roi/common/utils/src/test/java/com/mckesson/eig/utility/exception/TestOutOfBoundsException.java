/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.exception;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestOutOfBoundsException extends TestApplicationException {

    public TestOutOfBoundsException(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestOutOfBoundsException.class,
                OutOfBoundsException.class);
    }

    public ApplicationException createException() {
        return new OutOfBoundsException();
    }

    public ApplicationException createException(String message) {
        return new OutOfBoundsException(message);
    }

    public ApplicationException createException(Throwable t) {
        return new OutOfBoundsException(t);
    }

    public ApplicationException createException(String message, Throwable t) {
        return new OutOfBoundsException(message, t);
    }

}
