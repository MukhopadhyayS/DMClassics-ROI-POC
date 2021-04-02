package com.mckesson.eig.wsfw.model.authorization;

import junit.framework.TestCase;

public class TestAuthorizationException extends TestCase {

    protected void setUp() throws Exception {
  //      super.setUp();        
    }
    
    protected void tearDown() throws Exception {
    //    super.tearDown();
    }

    public void testContructors() {
        AuthorizationException authorizationException = new AuthorizationException();
        assertNotNull(authorizationException);
        
        String message = "A Message";
        String code = "A Code";
        
        authorizationException = null;
        authorizationException = 
            new AuthorizationException(message, code);
        assertNotNull(authorizationException);
        
        Throwable throwable = new Throwable();
        authorizationException = null;
        authorizationException = 
            new AuthorizationException(message, throwable, code);
        assertNotNull(authorizationException);

        authorizationException = null;
        authorizationException = 
            new AuthorizationException(message, throwable);
        assertNotNull(authorizationException);
        
        authorizationException = null;
        authorizationException = 
            new AuthorizationException(message);
        assertNotNull(authorizationException);
        
        authorizationException = null;
        authorizationException = 
            new AuthorizationException(throwable, code);
        assertNotNull(authorizationException);
        
        authorizationException = null;
        authorizationException = 
            new AuthorizationException(throwable);
        assertNotNull(authorizationException);
                
    }
    
    
}
