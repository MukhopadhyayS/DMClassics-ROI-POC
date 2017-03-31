package com.mckesson.eig.api;

import junit.framework.TestCase;

import org.apache.cxf.frontend.ClientProxy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsdl.testws_v1.TestWSServiceV10;
import com.mckesson.eig.wsdl.testws_v1.WSServiceImpl;

public class TestAPIWebserviceBase extends TestCase {

    private static APIWebServiceBase _serviceBase;
    
    private static final String SPRING_CONFIG_FILE = 
                    "com/mckesson/eig/api/mock.server.cxf.xml";
    @Override
    protected void setUp() throws Exception {
        
        loadSpringConfigFile(SPRING_CONFIG_FILE);
        _serviceBase = new APIWebServiceBase();
        super.setUp();
    }
    
    private void loadSpringConfigFile(String configFile) {

        if (SpringUtilities.getInstance().getBeanFactory() == null) {

            BeanFactory springContext = new ClassPathXmlApplicationContext(configFile);
            SpringUtilities.getInstance().setBeanFactory(springContext);
        }
    }
    
    public void testWsUtilWithNullUserName() {

        try {
            TestWSServiceV10 locator = (TestWSServiceV10) SpringUtilities
                                            .getInstance()
                                            .getBeanFactory().getBean("testserviceimpl");
            WSServiceImpl service = locator.getTestwsV10();
            _serviceBase.initializeService(ClientProxy.getClient(service), null, null);
            service.sayHellow();
        } catch (Exception e) {
            assertEquals("Security processing failed.", e.getMessage());
        }
    }
    
    /**
     * Basic Test method to set the headers
     */
    public void testWsUtil() {

        try {
            TestWSServiceV10 locator = (TestWSServiceV10) SpringUtilities
                                            .getInstance()
                                            .getBeanFactory().getBean("testserviceimpl");
            /*TestWSServiceV10 locator = new TestWSServiceV10(new URL(
                                      "http://127.0.0.1:8045/test/services/testservice?wsdl"));*/
            WSServiceImpl service = locator.getTestwsV10();
            _serviceBase.initializeService(ClientProxy.getClient(service), "system", "hecmadmin");
            assertEquals("Hellow", service.sayHellow());
        } catch (Exception e) {
            fail("TODO for CXF Migration");
        }
    }
    
    public void testWsUtilWithSecondarySession() {

        try {
            TestWSServiceV10 locator = (TestWSServiceV10) SpringUtilities
                                            .getInstance()
                                            .getBeanFactory().getBean("testserviceimpl");
            WSServiceImpl service = locator.getTestwsV10();
            _serviceBase.initializeService(
                ClientProxy.getClient(service), "system", "hecmadmin", true);
            assertEquals("Hellow", service.sayHellow());
        } catch (Exception e) {
            fail("TODO for CXF Migration");
        }
    }
    
    public void testWsUtilWithNullClient() {

        try {
            _serviceBase.initializeService(null, "system", "hecmadmin");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Service proxy cannot be null.");
        }
    }
}
