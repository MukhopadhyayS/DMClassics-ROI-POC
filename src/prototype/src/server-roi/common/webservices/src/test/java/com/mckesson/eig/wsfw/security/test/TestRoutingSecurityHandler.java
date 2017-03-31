/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.wsfw.security.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.axis.utils.XMLUtils;
import org.apache.ws.security.SOAP11Constants;
import org.apache.ws.security.SOAPConstants;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mckesson.eig.wsfw.test.axis.SoapRequestBuilder;
import com.mckesson.eig.wsfw.test.axis.XMLProcessor;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import junit.framework.TestCase;

/**
 * This class is used to test <code>RoutingWsSecurityHandler</code>, which identifies two 
 * different types of Web service security header and transfers the control to respective 
 * handlers to continue the authentication process. 
 *
 * @author Pranav Amarasekaran
 * @date   Apr 10, 2009
 * @since  Output 1.0
 */
public class TestRoutingSecurityHandler extends TestCase {
	
    /**
     * Denotes the mock application id for testing puprose
     */
    private static final String MOCK_APP_ID = "1";

    /**
     * Initializes and creates and instance of Crypto.
     */
    private static final Crypto CRYPTO = CryptoFactory.getInstance();

    /**
     * Holds the instance of <code>SoapRequestBuilder</code>.
     */
    private SoapRequestBuilder _requestBuilder;

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _client;

    /**
     * Holds the instance of ServletRunner.
     */    
    private ServletRunner _servletRunner;	
	
	public TestRoutingSecurityHandler() {
		super();
	}

    /**
     * Sets up the data required for testing the service.
     * 
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();

        File webxml = new File("WEB-INF/web.xml");
        _servletRunner = new ServletRunner(webxml);
        _client = _servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new SoapRequestBuilder();
    }
    
    /**
     * Tests the webservice by using UserName Token based authentication.
     */
    public void testUNTokenBasedSecurity() {

        try {

            _requestBuilder.setOperationData("welcomeUser", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeaderWithAppID("system", "admin", MOCK_APP_ID);

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/welcome",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "urn:eig.mckesson.com/welcomeUser");

            WebResponse response = _client.getResponse(request);

            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/" + "eig:welcomeUserResponse";
            assertEquals("Hi Welcome to the sample axis service", 
            		xpath.getValue(baseQuery + "/eig:welcomeResult"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * This method adds a header to a soap message of the output service and gets the 
     * corresponding XML response.
     */
    public void testBinaryTokenBasedSecurity() {

        try {
            String xmlSource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<soapenv:Envelope"
                + "   xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + "   xmlns:urn=\"urn:eig.mckesson.com\">"
                + "<soapenv:Body>"
                + "<welcomeUser xmlns=\"urn:eig.mckesson.com\"/>"
                + "</soapenv:Body>" + "</soapenv:Envelope>";

            Document doc = createDocumentFromString(xmlSource);
            addBSTSecurityHeader(doc, "wfprivkey", "wfpazz");

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            XMLUtils.DocumentToStream(doc, os);
            InputStream requestMessage = new ByteArrayInputStream(os.toString().getBytes());

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/welcome",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "urn:eig.mckesson.com/welcomeUser");
            WebResponse response = _client.getResponse(request);

            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/" + "eig:welcomeUserResponse";
            assertEquals("Hi Welcome to the sample axis service", 
            		xpath.getValue(baseQuery + "/eig:welcomeResult"));            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * This method tests the routing security handler without passing any security header
     * information.
     */
    public void testUnsecuredService() {

    	try {

            _requestBuilder.setOperationData("welcomeUser", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/welcome",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "urn:eig.mckesson.com/welcomeUser");

            WebResponse response = _client.getResponse(request);

            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/" + "eig:welcomeUserResponse";
            assertNull(xpath.getValue(baseQuery + "/eig:welcomeResult"));
        } catch (Exception e) {
            fail(e.getMessage());
        }    	
    }

    /**
     * Add a WS-Security BinarySecurityToken to the soap message.
     *
     * @param message
     *            Document
     * @param user
     *            Alias used for key. 
     *            
     * @param password
     *            Password associated with key
     *            
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws TransformerException
     */
    public void addBSTSecurityHeader(Document message,
    								 String user,
    								 String password)
    throws SAXException,
           ParserConfigurationException,
    	   IOException,
    	   TransformerException {

        WSSecHeader secHeader = new WSSecHeader("");
        secHeader.insertSecurityHeader(message);

        SOAPConstants soapConstants = new SOAP11Constants();

        WSSecSignature signer = new WSSecSignature();

        Vector<WSEncryptionPart> parts = new Vector<WSEncryptionPart>(1, 1);
        WSEncryptionPart encP = new WSEncryptionPart("STRTransform",
                soapConstants.getEnvelopeURI(), "Content");
        parts.add(encP);

        signer.setParts(parts);
        signer.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
        signer.setUserInfo(user, password);

        Document signedDoc = null;
        try {
            signedDoc = signer.build(message, CRYPTO, secHeader);
        } catch (WSSecurityException e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Signed Document in addSecurityHeader= "
                + XMLUtils.PrettyDocumentToString(signedDoc));
    }
    
    /**
     * Creates and returns a Document object from xml string.
     *
     * @param xmlSource
     *            xml document in string format.
     * @return
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    private Document createDocumentFromString(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }    
}
