package com.mckesson.eig.wsfw;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import com.mckesson.eig.utility.concurrent.ConcurrencyTimeoutException;

public class TestToken extends TestCase {

    private static final String TEST_STR = "TEST_STR";
    private static final int TEST_STATE_X = 0;
    private static final int TEST_TIMEOUT = 15000;
    
    private HttpSession _session = new MockHttpSession();
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFind() {
        Token token = Token.find(_session);
        assertNotNull(token);
        assertSame(token, Token.find(_session));
        assertEquals(0, token.getLockCount());
    }
    
    public void testSimpleAcquireToken() {
        Token.acquire(_session);
        Token token = Token.find(_session);
        assertEquals(1, token.getLockCount());
        Token.release(_session);
        assertEquals(0, token.getLockCount());
    }
    
    public void testTimeOutAcquireToken() {
        Token token = Token.find(_session);
        final int wait = 60;
        token.setWaitTime(wait);
        
        TestThread t1 = new TestThread(_session);
        TestThread t2 = new TestThread(_session);
        t1 = new TestThread(_session);
        t1.start();
        Calendar c = new GregorianCalendar();
        long start = c.getTimeInMillis();
        while (!t1.getSuccessfulLock() && t1.isAlive()) {
            if (c.getTimeInMillis() - start > TEST_TIMEOUT) {
                t1.stopLoop();
                fail("Expected to lock first thread");
                return;
            }
        }
        if (!t1.getSuccessfulLock() || !t1.isAlive()) {
            fail("Expected to lock first thread");
        }
        
        t2 = new TestThread(_session);
        t2.start();
        start = c.getTimeInMillis();
        while (t2.isAlive()) {
            if (c.getTimeInMillis() - start > TEST_TIMEOUT) {
                t1.stopLoop();
                t2.stopLoop();
                fail("Expected exception timeout of test.");
                return;
            }
        }
        assertFalse(t2.getSuccessfulLock());

        t1.stopLoop();
        t2.stopLoop();
    }

    public void testInterruptAcquireToken() {
        Token token = Token.find(_session);
        final int wait = 10000;
        token.setWaitTime(wait);
        
        TestThread t1 = new TestThread(_session);
        TestThread t2 = new TestThread(_session);
        t1 = new TestThread(_session);
        t1.start();
        Calendar c = new GregorianCalendar();
        long start = c.getTimeInMillis();
        while (!t1.getSuccessfulLock() && t1.isAlive()) {
            if (c.getTimeInMillis() - start > TEST_TIMEOUT) {
                t1.stopLoop();
                fail("Expected to lock first thread");
                return;
            }
        }
        if (!t1.getSuccessfulLock() || !t1.isAlive()) {
            fail("Expected to lock first thread");
        }
        
        t2 = new TestThread(_session);
        t2.start();
        t2.interrupt();
        start = c.getTimeInMillis();
        while (t2.isAlive()) {
            if (c.getTimeInMillis() - start > TEST_TIMEOUT) {
                t1.stopLoop();
                t2.stopLoop();
                fail("Expected exception timeout of test.");
                return;
            }
        }
        assertFalse(t2.getSuccessfulLock());

        t1.stopLoop();
        t2.stopLoop();
    }

    private class TestThread extends Thread  {

        private HttpSession _tsession;
        private boolean _stop = false;
        private boolean _successfulLock = false;
        
        public TestThread(HttpSession session) {
            _tsession = session;
        }
        
        public void stopLoop() {
            _stop = true;
        }
        
        public boolean getSuccessfulLock() {
            return _successfulLock;
        }
        
        public void run() {
            try {
                Token.acquire(_tsession);
            } catch (ConcurrencyTimeoutException cte) {
                _successfulLock = false;
                return;
            }
            _successfulLock = true;
            int i = 0;
            while (!_stop) {
                i++;
            }
            Token.release(_tsession);
        }
    }

}
