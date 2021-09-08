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

import com.mckesson.eig.utility.util.ObjectUtilities;

public class SyncTask implements Runnable {

    private final Synchronization _synchronization;
    private Runnable _task;

    public SyncTask(Synchronization sync, Runnable task) {
        ObjectUtilities.verifyNotNull(sync);
        ObjectUtilities.verifyNotNull(task);
        _synchronization = sync;
        _task = task;
    }

    public void run() {
        _synchronization.acquire();
        try {
            runOnlyOnce();
        } finally {
            _synchronization.release();
        }
    }

    private void runOnlyOnce() {
        if (_task != null) {
            try {
                _task.run();
            } finally {
                _task = null;
            }
        }
    }
}
