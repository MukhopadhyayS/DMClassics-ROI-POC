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

public class TaskThread extends Thread {

    private TaskLoop _task;
    private boolean _paused;
    private Throwable _throwable;

    public TaskThread(TaskLoop task) {
        super();
        initialize(task);
    }

    public TaskThread(TaskLoop task, String threadName) {
        super(threadName);
        initialize(task);
    }

    private void initialize(TaskLoop task) {
        ObjectUtilities.verifyNotNull(task);
        _task = task;
    }

    protected synchronized void setThrowable(Throwable t) {
        _throwable = t;
    }

    public synchronized Throwable getThrowable() {
        return _throwable;
    }

    public synchronized void pause() {
        _paused = true;
    }

    public synchronized void restart() {
        _paused = false;
        notifyAll();
    }

    protected synchronized void checkForPause() {
        try {
            if (_paused) {
                wait();
            } else {
                Thread.yield();
            }
        } catch (InterruptedException ie) {
            interrupt();
        }
    }

    public void run() {
        try {
            _task.initialize();
            checkForPause();
            while ((!interrupted()) && _task.hasNext()) {
                _task.next();
                checkForPause();
            }
            if (interrupted()) {
                _task.cancelled();
            } else {
                _task.completed();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            setThrowable(t);
            _task.error(t);
        } finally {
            _task.cleanup();
        }
    }
}
