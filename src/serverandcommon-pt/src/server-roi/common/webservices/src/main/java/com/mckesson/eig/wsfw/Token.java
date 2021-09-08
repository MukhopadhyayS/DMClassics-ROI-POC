/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.wsfw;

import javax.servlet.http.HttpSession;

import EDU.oswego.cs.dl.util.concurrent.ReentrantLock;

import com.mckesson.eig.utility.concurrent.ConcurrencyInterruptedException;
import com.mckesson.eig.utility.concurrent.ConcurrencyTimeoutException;
import com.mckesson.eig.utility.concurrent.Synchronization;
import com.mckesson.eig.utility.values.Minutes;

/**
 * Provides methods for acquiring and releasing a lock for a particular session.
 * 
 */
public final class Token implements Synchronization {

    /**
     * Value for wait time.
     * 
     */
    private static final int MIN = 5;

    /**
     * Name of the session.
     */
    private static final String SESSION_NAME = "com.mckesson.eig.wsfw.Token";

    /**
     * Wait time.
     */
    private long _waitTime = new Minutes(MIN).toMillis();

    /**
     * Error message to throw.
     */
    private static final String ERROR_MESSAGE = 
        "Could not acquire session token.";

    /**
     * Instantiates <code>ReentrantLock</code>.
     */
    private final ReentrantLock _lock = new ReentrantLock();

    /**
     * Sole Constructor.
     */
    private Token() {
    }

    /**
     * Acquires the lock.
     * 
     * @throws ConcurrencyTimeoutException
     * @see com.mckesson.eig.utility.concurrent.Synchronization#acquire()
     */
    public void acquire() {
        try {
            if (!_lock.attempt(_waitTime)) {
                throw new ConcurrencyTimeoutException(ERROR_MESSAGE).log();
            }
        } catch (InterruptedException e) {
            throw new ConcurrencyInterruptedException(ERROR_MESSAGE, e).log();
        }
    }

    /**
     * Release this lock.
     */
    public void release() {
        _lock.release();
    }

    /**
     * Change wait Time
     */
    void setWaitTime(long millisec) {
        _waitTime = millisec;
    }
    
    /**
     * Number of Locks
     */
    long getLockCount() {
        return _lock.holds();
    }
    
    /**
     * It acquires the lock for the mentioned session.
     * 
     * @param session
     *            HttpSession to acquire.
     * @see com.mckesson.eig.utility.concurrent.Synchronization#acquire()
     */
    public static void acquire(HttpSession session) {
        Token token = find(session);
        token.acquire();
    }
    /**
     * It releases the lock for the mentioned session.
     * 
     * @param session
     *            HttpSession to be released.
     * @see com.mckesson.eig.utility.concurrent.Synchronization#acquire()
     */
    public static void release(HttpSession session) {
        Token token = find(session);
        token.release();
    }

    /**
     * Returns the object bound with the specified name in this session, if
     * object is not found it binds an object to this session using this Class
     * name.
     * 
     * @param session
     *            HttpSession .
     * @return this token after setting the attributes for the session.
     */
    public static synchronized Token find(HttpSession session) {
        Token token = (Token) session.getAttribute(SESSION_NAME);
        if (token == null) {
            token = new Token();
            session.setAttribute(SESSION_NAME, token);
        }
        return token;
    }
}
