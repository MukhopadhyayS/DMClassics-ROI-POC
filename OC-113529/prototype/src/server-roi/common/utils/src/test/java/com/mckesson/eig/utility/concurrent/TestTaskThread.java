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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 *
 * Test case to test the class TaskThread.
 */
public class TestTaskThread extends TestCase {

    /**
     * Holds an instance of type TaskThread.
     */
    private TaskThread _taskThread;

    /**
     * Constructs the class.
     *
     * @param name
     *            Name of the test case.
     */
    public TestTaskThread(String name) {
        super(name);
    }

    /**
     * Set up the test. Creates an instance of the class that need to be tested.
     *
     * @throws Exception
     *             if the set up is not made properly.
     */
    @Override
	protected void setUp() throws Exception {
        super.setUp();
        _taskThread = new TaskThread(new InnerClass());

    }

    /**
     * This will remove all the data associated with the test.
     *
     * @throws Exception
     *             if the tear down is not made properly.
     */
    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the constructor with argumets name and task.
     */
    public void testConstructorWithName() {

        _taskThread = new TaskThread(new InnerClass(), this.getName());
        assertEquals(_taskThread.getName(), this.getName());
    }

    /**
     * Tests exception for constructor with null.
     */
    public void testConstructorWithNull() {

        try {
            _taskThread = new TaskThread(null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /**
     * Tests getThrowable() and setThrowable() methods.
     */
    public void testThrowable() {

        _taskThread.setThrowable(new Throwable("test message"));
        assertEquals(_taskThread.getThrowable().getMessage(), new Throwable(
                "test message").getMessage());
    }

    /**
     * Tests the run() method.
     */
    public void testRun() {
        assertNotNull(_taskThread);
        try {
            _taskThread.run();
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Tests pause() and restart() method.
     */
    public void testPauseAndRestart() {
        try {
            _taskThread.pause();
            _taskThread.restart();
            _taskThread.checkForPause();
        } catch (Exception e) {
            fail();
        }
    }

   /**
     * Tests pause() and restart() method.
     */
    public void testYield() {
        try {
            _taskThread.checkForPause();
        } catch (Exception e) {
            fail();
        }
    }

   /**
     * Innerclass that implements the interface TaskLoop.
     */
    static class InnerClass implements TaskLoop {

       /**
         * Holds an instance of type List.
         */
        private List<Thread> _list;

       /**
         * Holds an instance of type Iterator.
         */
        private Iterator<Thread> _it;

       /**
         * Boolean variable to test if the task is complete or not.
         */
        private boolean _complete = false;

       /**
         * Initializes a list with objects of type thread. Overrides the method
         * initialize().
         *
         * @throws Exception
         *             e if initialize is not executed properly.
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#initialize()
         */
        public void initialize() throws Exception {
            SynchronizationMockClass synchronizationMockClass = new SynchronizationMockClass();
            Thread threadOne = new Thread(
            	new SyncTask(
                    synchronizationMockClass,
                    new WrappedRunnable(new MockClass())));
            Thread threadTwo = new Thread(
            	new SyncTask(
                    synchronizationMockClass,
                    new WrappedRunnable(new MockClass())));
            _list = new ArrayList<Thread>();
            _list.add(threadOne);
            _list.add(threadTwo);
            _it = _list.iterator();
        }

       /**
         * Checks if the list has elements and returns true or false
         * respectively. Overriden method.
         *
         * @return boolean.
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#hasNext()
         */
        public boolean hasNext() {
            if (_it.hasNext()) {
                return true;
            }
            return false;
        }

       /**
         *
         * Advances the iterator to point to the next element inthe list.
         *
         * @throws Exception
         *             if there are no more elements in the list.
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#next()
         */
        public void next() throws Exception {
            if (hasNext()) {
                _it.next();
            }
        }

        /**
         * Sets complete to true if the task is complete. Overriden method.
         * (non-Javadoc)
         *
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#hasNext()
         *
         */
        public void completed() {
            _complete = true;
        }

      /**
         * Sets complete to true if the task is cancelled. Overriden method.
         * (non-Javadoc)
         *
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#hasNext()
         *
         */
        public void cancelled() {
            _complete = false;
        }

       /**
         * Prints the stack trace of the Exception. Overriden method.
         * (non-Javadoc)
         *
         * @param t
         *            Throwable -Exception *
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#hasNext()
         */
        public void error(Throwable t) {
            t.printStackTrace();
        }

       /**
         * Sets the instances to null. Overriden method. (non-Javadoc)
         *
         * @see com.mckesson.eig.utility.concurrent.TaskLoop#hasNext()
         *
         */
        public void cleanup() {
            _it = null;
            _list = null;
        }

        /**
         * @return the complete
         */
        public boolean isComplete() {
            return _complete;
        }
    }

    /**
     * Inner class that implements the interface Synchronization.
     */
    static class SynchronizationMockClass implements Synchronization {

       /**
         * Contains an SOP to test if the method is executed. Overrides this
         * method from the interface Synchronization.
         */
        public void acquire() {
            System.out.println("In Acquire==>" + getClass().getSimpleName());
        }

       /**
         * Contains an SOP to test if the method is executed. Overrides this
         * method from the interface Synchronization.
         */
        public void release() {
            System.out.println("In Release==>" + getClass().getCanonicalName());
        }
    }
}
