package com.mckesson.eig.wsfw.model.authentication;

import junit.framework.TestCase;

public class TestAuthenticatedResult extends TestCase {

    private static final String TEST_STR = "TEST_STR";
    private static final int TEST_STATE_X = 0;

    private AuthenticatedResult _result;
    
    protected void setUp() throws Exception {
        super.setUp();
        _result = new AuthenticatedResult();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConstructor() {
        assertNotNull(_result);
    }

    public void testAccessors() {
        assertNull(_result.getAdditionalInfo());
        assertEquals(AuthenticatedResult.AUTHENTICATION_FAILED, _result.getState().intValue());
        assertNull(_result.getTicket());
        assertNull(_result.getUserId());
        _result.setState(AuthenticatedResult.AUTHENTICATED);
        _result.setAdditionalInfo(TEST_STR);
        _result.setTicket(TEST_STR);
        _result.setUserId(TEST_STR);
        assertEquals(AuthenticatedResult.AUTHENTICATED, _result.getState().intValue());
        assertEquals(TEST_STR, _result.getAdditionalInfo());
        assertEquals(TEST_STR, _result.getTicket());
        assertEquals(TEST_STR, _result.getUserId());
    }

    public void testIsAuthenticated() {
        assertFalse(_result.isAuthenticated());

        _result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
        assertFalse(_result.isAuthenticated());

        _result.setState(AuthenticatedResult.AUTHENTICATED_GRACE_LOGIN);
        assertFalse(_result.isAuthenticated());
        
        _result.setState(AuthenticatedResult.AUTHENTICATED_EXPIRING_PD);
        assertTrue(_result.isAuthenticated());
        
        _result.setState(AuthenticatedResult.AUTHENTICATED);
        assertTrue(_result.isAuthenticated());
    }
    
    public void testIsPasswordExpiring() {
        assertFalse(_result.isPdExpiring());

        _result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
        assertFalse(_result.isPdExpiring());

        _result.setState(AuthenticatedResult.AUTHENTICATED_GRACE_LOGIN);
        assertFalse(_result.isPdExpiring());
        
        _result.setState(AuthenticatedResult.AUTHENTICATED_EXPIRING_PD);
        assertTrue(_result.isPdExpiring());
        
        _result.setState(AuthenticatedResult.AUTHENTICATED);
        assertFalse(_result.isPdExpiring());
    }

    public void testIsLocked() {
        _result.setState(AuthenticatedResult.AUTHENTICATED);
        assertFalse(_result.isLocked());

        _result.setAdditionalInfo(AuthenticatedResult.PD_LOCKED_MSG_STR);
        assertFalse(_result.isLocked());
        
        _result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
        assertTrue(_result.isLocked());

        _result.setAdditionalInfo(null);
        assertFalse(_result.isLocked());
    }

    public void testIsExpired() {
        _result.setState(AuthenticatedResult.AUTHENTICATED);
        assertFalse(_result.isExpired());

        _result.setAdditionalInfo(AuthenticatedResult.PD_IS_EXPIRED_MSG_STR);
        assertFalse(_result.isExpired());
        
        _result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
        assertTrue(_result.isExpired());

        _result.setAdditionalInfo(null);
        assertFalse(_result.isExpired());

        _result.setAdditionalInfo(AuthenticatedResult.PD_HAS_EXPIRED_MSG_STR);
        assertTrue(_result.isExpired());

        _result.setAdditionalInfo(null);
        _result.setState(AuthenticatedResult.AUTHENTICATED_GRACE_LOGIN);
        assertTrue(_result.isExpired());
    }
    
    public void testToString() {
        assertNotNull(_result.toString());
    }

}
