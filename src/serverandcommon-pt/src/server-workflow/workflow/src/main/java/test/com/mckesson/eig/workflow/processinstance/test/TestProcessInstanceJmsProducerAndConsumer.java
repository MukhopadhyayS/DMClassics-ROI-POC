package com.mckesson.eig.workflow.processinstance.test;

import java.io.File;
import java.io.InputStream;

import com.mckesson.eig.utility.log.LogContext;
import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.session.WsSession;
import com.mckesson.eig.wsfw.test.SoapRequestBuilder;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class TestProcessInstanceJmsProducerAndConsumer extends AbstractWorkflowTestCase {

    private static final String USER_NAME = "testuser";

    private static String _transID;

    /**
     * Holds the instance of <code>SoapRequestBuilder</code>.
     */
    private SoapRequestBuilder _requestBuilder;

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _client;

    /**
     * Sets up the data required for this test case.
     */
    protected void setUp() throws Exception {

        super.setUp();

        //Client initialization
        init();
        initializeQueue("processInstanceQueue");

        //Server initialization
        File webxml = new File("src/test/com/mckesson/eig/workflow/processinstance/test/web.xml");
        ServletRunner sr = new ServletRunner(webxml);

        _client = sr.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);

        _requestBuilder = new SoapRequestBuilder(EIGConstants.TYPE_NS_V1);

        TransactionId transactionId = new TransactionId(USER_NAME, "127.0.0.1");
        _transID = transactionId.getValue();

        WsSession.setSessionData(WsSession.CLIENT_IP, "127.0.0.1");
        WsSession.setSessionData(WsSession.USER_NAME, USER_NAME);
        WsSession.setSessionData(WsSession.APP_ID, "appID");
        LogContext.put("transactionid", transactionId);
    }



    /**
     * Destroys the data associated with this test case.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testProcessInstanceJmsService() {

        try {

            final long waitTime = 1000;
            _requestBuilder.setOperationData("startProcessInstance");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter userActor =
                _requestBuilder.addParameter("actor", "");

            userActor.addParameter("appID", "1");
            userActor.addParameter("entityID", "1");
            userActor.addParameter("entityType", "1");
            userActor.addParameter("version", "1");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter actors =
                _requestBuilder.addParameter("actors", "");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter actor =
                actors.addParameter("actors", "");

            actor.addParameter("appID", "1");
            actor.addParameter("entityID", "1");
            actor.addParameter("entityType", "1");
            actor.addParameter("version", "1");

            actors.addParameter("size", "1");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter processName =
                _requestBuilder.addParameter("processId", "100");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter variableList =
                _requestBuilder.addParameter("variableList", "");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter variable1 =
                variableList.addParameter("variables", "");
            variable1.addParameter("key", "ProcessVersion");
            variable1.addParameter("value", "1");

            com.mckesson.eig.wsfw.test.SoapRequestBuilder.Parameter variable2 =
                variableList.addParameter("variables", "");
            variable2.addParameter("key", "SelectedContent.0");
            variable2.addParameter("value", "5024");

            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            //_requestBuilder.buildSoapRequestWithSecurityHeader("system", "hecmadmin");

            WebRequest request =
                new PostMethodWebRequest("http://hostname.ingored.com/services/processinstance",
                                          requestMessage,
                                          "text/xml");

            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            Thread.sleep(waitTime);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
