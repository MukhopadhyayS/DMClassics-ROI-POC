package com.mckesson.eig.wsfw.session;

import java.util.HashMap;

import junit.framework.TestCase;

public class TestWsSession extends TestCase {

    private static final String TEST_KEY = "EIG_TEST_KEY";

    protected void setUp() throws Exception {
        super.setUp();
        WsSession.initializeSession();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSetAndGetSessionUserId() {
        final long userId = 1;

        WsSession.setSessionUserId(userId);
        assertTrue(WsSession.getSessionUserId() == userId);
    }

    public void testSetAndGetUserSecurityRights() {
        assertNull(WsSession.getUserSecurityRights());
        HashMap map = new HashMap();
        WsSession.setUserSecurityRights(map);
        assertNotNull(WsSession.getUserSecurityRights());
    }

    public void testisUserSecurityRightsLoaded() {
        assertFalse(WsSession.isUserSecurityRightsLoaded());
        HashMap map = new HashMap();
        WsSession.setUserSecurityRights(map);
        assertTrue(WsSession.isUserSecurityRightsLoaded());
    }

    public void testSetGetAndRemoveSessionData() {
        HashMap map = new HashMap();
        WsSession.setSessionData(TEST_KEY, map);
        Object actual = WsSession.getSessionData(TEST_KEY);
        assertEquals(map, actual);

        WsSession.removeSessionData(TEST_KEY);
        assertNull(WsSession.getSessionData(TEST_KEY));
    }

    public void testGetSessionId() {

        final String sessionId = "SESSION_ID";
        WsSession.setSessionData(sessionId, sessionId);
        assertEquals(sessionId, WsSession.getSessionId());
    }
}
