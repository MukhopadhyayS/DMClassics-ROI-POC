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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mckesson.eig.utility.values.Minutes;
import com.mckesson.eig.utility.values.Period;

public class QueuedThread extends AbstractPooledRunner {

    public QueuedThread() {
        this(new Minutes(1));
    }

    public QueuedThread(final Period keepalive) {
        this(keepalive, new DaemonThreadFactory());
    }

    public QueuedThread(final Period keepalive, final ThreadFactory factory) {
        super(new ThreadPoolExecutor(1, Integer.MAX_VALUE, keepalive.toMillis(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), factory));
        // Should never be blocked because of using LinkedQueue.
//        getExecutor().abortWhenBlocked();
    }
}
