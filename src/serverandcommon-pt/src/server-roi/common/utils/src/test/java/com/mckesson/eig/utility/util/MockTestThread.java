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
package com.mckesson.eig.utility.util;

public class MockTestThread extends Thread {

    private Thread _thread;
    private boolean _running = true;

    public MockTestThread(Thread thread) {
        _thread = thread;
    }

    public void run() {

        while (isRunning()) {
            _thread.interrupt();
        }
    }

    public synchronized void stopRunning() {
        _running = false;
    }

    public synchronized boolean isRunning() {
        return _running;
    }

}
