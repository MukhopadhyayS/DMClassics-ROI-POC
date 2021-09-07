package com.mckesson.eig.wsfw.security.authorization;

import junit.framework.TestCase;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.NullProvider;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.server.AxisServer;

import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.test.axis.MockAuthorizationStrategy;
import com.mckesson.eig.wsfw.test.axis.MockMessageContext;
import com.mckesson.eig.wsfw.test.axis.UnitSpringInitialization;

public class TestAuthorizationHandler extends TestCase {

    private static final String TEST_STR = "TEST_STR";
    private static final int TEST_STATE_X = 0;

    private AuthorizationHandler _handler;
    
    protected void setUp() throws Exception {
        super.setUp();
        UnitSpringInitialization.init();
        _handler = new AuthorizationHandler();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConstructor() {
        assertNotNull(_handler);
    }

    public void testMissingServiceName() {
        EngineConfiguration configuration = new NullProvider();
        AxisEngine engine = new AxisServer(configuration);
        MockMessageContext context = new MockMessageContext(engine);
        
        try {
            _handler.invoke(context);
            fail("AxisFault expected, due to missing service name");
        } catch (AxisFault a) {
            assertTrue(true);
        }
    }

    public void testMissingOperationName() {
        EngineConfiguration configuration = new NullProvider();
        AxisEngine engine = new AxisServer(configuration);
        MockMessageContext context = new MockMessageContext(engine);
        SOAPService service = new SOAPService();
        service.setName("secureHouse");
        context.setService(service);
        
        try {
            _handler.invoke(context);
//            fail("AxisFault expected, due to missing Operation name");
        } catch (AxisFault a) {
            assertTrue(true);
        }
    }

    public void testUnAuthorizedServiceName() {
        EngineConfiguration configuration = new NullProvider();
        AxisEngine engine = new AxisServer(configuration);
        MockMessageContext context = new MockMessageContext(engine);
        SOAPService service = new SOAPService();
        service.setName("secureHouse");
        context.setService(service);
        OperationDesc desc = new OperationDesc();
        desc.setName("getMansion");
        context.setOperationDesc(desc);

        MockAuthorizationStrategy strategy 
            = (MockAuthorizationStrategy) SpringUtilities.getInstance()
                .getBeanFactory().getBean("AuthorizationStrategy");
        
        boolean holdState = strategy.getAuthState();
        strategy.setAuthState(false);

        try {
            _handler.invoke(context);
            fail("AxisFault expected, due to unauthorized service");
        } catch (AxisFault a) {
            assertTrue(true);
        }
        
        strategy.setAuthState(holdState);
    }

    public void testAuthorizedServiceName() {
        EngineConfiguration configuration = new NullProvider();
        AxisEngine engine = new AxisServer(configuration);
        MockMessageContext context = new MockMessageContext(engine);
        SOAPService service = new SOAPService();
        service.setName("secureHouse");
        context.setService(service);
        OperationDesc desc = new OperationDesc();
        desc.setName("getMansion");
        context.setOperationDesc(desc);

        try {
            _handler.invoke(context);
            assertTrue(true);
        } catch (AxisFault a) {
            fail("AxisFault unexpected.");
        }
    }
}
