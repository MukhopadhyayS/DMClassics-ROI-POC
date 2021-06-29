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

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool extends AbstractPooledRunner {
    private static final int THREAD_POOL = 40;
    private static final int SEC = 5 * 60;
    public ThreadPool() {
        this(1, THREAD_POOL);
    }

    public ThreadPool(final int min, final int max) {
    	super(new ThreadPoolExecutor(min, max, SEC, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new DaemonThreadFactory()));
    }
}
