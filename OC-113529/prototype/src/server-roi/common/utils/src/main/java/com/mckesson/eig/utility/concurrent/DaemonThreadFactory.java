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

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

    private final Integer _priority;

    public DaemonThreadFactory() {
        _priority = null;
    }

    private DaemonThreadFactory(int priority) {
        _priority = Integer.valueOf(priority);
    }

    public Thread newThread(Runnable command) {
        Thread t = new Thread(command);
        t.setName("DaemonThread:0x" + Integer.toHexString(t.hashCode()));
        t.setDaemon(true);
        if (_priority != null) {
            t.setPriority(_priority.intValue());
        }
        return t;
    }

    public static DaemonThreadFactory makeLowerPriority() {
        return new DaemonThreadFactory(calculateLowerPriority());
    }

    public static DaemonThreadFactory makeMinPriority() {
        return new DaemonThreadFactory(Thread.MIN_PRIORITY);
    }

    private static int calculateLowerPriority() {
        int relative = Math.min(Thread.currentThread().getPriority(), Thread.NORM_PRIORITY);
        return Math.max(relative - 2, Thread.MIN_PRIORITY);
    }
}
