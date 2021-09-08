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

package com.mckesson.eig.utility.concurrent;

import com.mckesson.dm.core.common.logging.OCLogger;

public class WrappedRunnable implements Runnable {

    private static final OCLogger LOG = new OCLogger(WrappedRunnable.class);

    private final Runnable _runnable;

    public WrappedRunnable(final Runnable r) {
        _runnable = r;
    }

    public void run() {
        try {
            _runnable.run();
        } catch (Error e) {
            error(e);
            throw e;
        } catch (Exception e) {
            error(e);
        }
    }

    private void error(Throwable t) {
        LOG.error("Exception occurred in error ",t);
    }
}
