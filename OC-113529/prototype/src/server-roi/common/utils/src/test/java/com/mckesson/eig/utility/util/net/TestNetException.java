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

package com.mckesson.eig.utility.util.net;

import junit.framework.Test;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.TestApplicationException;
import com.mckesson.eig.utility.testing.CoverageSuite;

/**
 * @author Kenneth Partlow
 *
 */
public class TestNetException extends TestApplicationException {

    public TestNetException(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestNetException.class, NetException.class);
    }

    @Override
	public ApplicationException createException() {
        return new NetException();
    }

    @Override
	public ApplicationException createException(String message, Throwable t) {
        return new NetException(message, t);
    }

    @Override
	public ApplicationException createException(String message) {
        return new NetException(message);
    }

    @Override
	public ApplicationException createException(Throwable t) {
        return new NetException(t);
    }
}
