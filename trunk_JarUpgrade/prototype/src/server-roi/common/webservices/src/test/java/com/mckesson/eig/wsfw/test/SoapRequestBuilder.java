/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.wsfw.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecUsernameToken;

import com.sun.xml.messaging.saaj.soap.MessageImpl;
import com.sun.xml.messaging.saaj.soap.ver1_1.Message1_1Impl;

/**
 * @author sahuly
 * @date   Dec 29, 2008
 * @since  HECM 1.0; Dec 29, 2008
 */
public class SoapRequestBuilder {

    /**
     * Operation to be performed.
     */
    private String _operation;

    /**
     * Service URI. [default for legacy unit tests support].
     */
    private String _serviceURI = "urn:eig.mckesson.com";

    /**
     * List for adding details.
     */
    private List<Parameter> _parameters;

    /**
     * Contructor.
     */
    public SoapRequestBuilder() {
        //default of "urn:eig.mckesson.com" is for legacy unit test cases support.
        //should use constructor with serviceURI parameter for custom namespace for
        //operation and header information.
        this("urn:eig.mckesson.com");
    }

    /**
     * Initializes the arraylist.
     *
     * @param serviceURI
     *            serviceURI. null in our case.
     */
    public SoapRequestBuilder(String serviceURI) {
        _serviceURI = serviceURI;
        _parameters = new ArrayList<Parameter>();
    }

    public void setOperationData(String operation) {
        this._operation = operation;
    }

    /**
     * Appends the specified element to the end of this <code>Parameter</code>.
     *
     * @param name
     *            Name
     * @param value
     *            corresponding value for the name
     * @return Parameter after adding values to it.
     */
    public Parameter addParameter(String name, String value) {

        Parameter parameter = new Parameter(name, value);
        _parameters.add(parameter);
        return parameter;
    }

    /**
     * Builds a SOAP request.
     *
     * @return SOAP message stream.
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message.F
     * @throws IOException
     *             if an I/O error occurs.
     */
    public InputStream buildSoapRequest() throws SOAPException, IOException {

        SOAPMessage message = createSoapMessage();
        System.out.println("SOAP Request: " + messageToString(message));
        return streamSoapMessage(message);
    }

    /**
     * Builds a SOAPRequest with SecurityHeader.
     *
     * @param user
     *            UserName
     * @param password
     *            Password for validating.
     * @return SOAP message stream.
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     * @throws IOException
     *             if an I/O error occurs.
     * @throws ParserConfigurationException
     *             error while parsing the XML.
     */
    public InputStream buildSoapRequestWithSecurityHeader(String user, String password)
    throws SOAPException, IOException, ParserConfigurationException {

        SOAPMessage message = createSoapMessage();
        addHeader(message, "transactionId", "1234");
        addHeader(message, "applicationId", "1");
        addSecurityHeader(message, user, password);
        System.out.println("SOAP Request: " + messageToString(message));
        return streamSoapMessage(message);
    }

    /**
     * Builds a SOAPRequest with the TransactionId Header.
     *
     * @param user
     *            UserName
     * @param password
     *            Password for validating.
     * @return SOAP message stream.
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     * @throws IOException
     *             if an I/O error occurs.
     * @throws ParserConfigurationException
     *             error while parsing the XML.
     */
    public InputStream buildSoapRequestWithTransactionIdHeader()
    throws SOAPException, IOException, ParserConfigurationException {

        SOAPMessage message = createSoapMessage();
        addHeader(message, "transactionId", "1234");
        System.out.println("SOAP Request: " + messageToString(message));
        return streamSoapMessage(message);
    }

    /**
     *  Builds a SOAPRequest with the TransactionId and Security Header.
     *
     * @param user
     *            UserName
     * @param password
     *            Password for validating.
     * @return SOAP message stream.
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     * @throws IOException
     *             if an I/O error occurs.
     * @throws ParserConfigurationException
     *             error while parsing the XML.
     */
    public InputStream buildSoapRequestWithTransIDAndSecurityHeader(String user, String password)
    throws SOAPException, IOException, ParserConfigurationException {

        SOAPMessage message = createSoapMessage();
        addHeader(message, "transactionId", "1234");
        addSecurityHeader(message, user, password);
        System.out.println("SOAP Request: " + messageToString(message));
        return streamSoapMessage(message);
    }

    /**
     * Writes this <CODE>SOAPMessage</CODE> object to the given output stream
     * and returns a <code>ByteArrayInputStream</code> so that it uses bytes
     * obtained as its buffer array.
     *
     * @param message
     *            SOAPMessage
     * @return ByteArrayInputStream
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     * @throws IOException
     *             if an I/O error occurs
     */
    private InputStream streamSoapMessage(SOAPMessage message)
    throws SOAPException, IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        message.writeTo(os);
        return new ByteArrayInputStream(os.toString().getBytes());
    }

    /**
     * Add the eig specific soap header to carry a client generated transaction
     * id.
     *
     * @param message
     *            SOAPMessage
     * @param headerName
     *        local header name
     * @param value
     *            header value
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     */
    public void addHeader(SOAPMessage message, String headerName, String value)
    throws SOAPException {

        SOAPElement transIdElement = message.getSOAPHeader().addChildElement(
                headerName, "ns");
        transIdElement.addTextNode(value);
    }

    /**
     * Add a WS-Security SOAP header to the soap message. Note there is a bug in
     * WSS4j that you must add another SOAP header first before adding the
     * Security header. If you do not first add another header, the Username
     * token child element will get lost. Always call #addTransactionIdHeader()
     * first to make sure that this works.
     *
     * @param message
     *            SOAPMessage
     * @param user
     *            USerName
     * @param password
     *            Password
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     * @throws ParserConfigurationException
     *             error while parsing the XML.
     */
    public void addSecurityHeader(SOAPMessage message, String user, String password)
    throws SOAPException, ParserConfigurationException {

        WSSecHeader secHeader = new WSSecHeader("");
        secHeader.insertSecurityHeader(message.getSOAPPart());
        WSSecUsernameToken usernameToken = new WSSecUsernameToken();
        usernameToken.setUserInfo(user, password);
        usernameToken.setPasswordType(WSConstants.PASSWORD_TEXT);
        usernameToken.addNonce();
        usernameToken.addCreated();
        usernameToken.prepare(message.getSOAPPart());
        usernameToken.prependToHeader(secHeader);
    }

    /**
     * Creates a new <code>SOAPMessage</code> and <code>SOAPBodyElement</code>
     * object with the default <code>SOAPPart</code>,
     * <code>SOAPEnvelope</code>,<code>SOAPBody</code>, and
     * <code>SOAPHeader</code> objects.
     *
     * @return SOAP Message after adding desired details.
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message.
     */
    private SOAPMessage createSoapMessage() throws SOAPException {

        MessageImpl msg = new Message1_1Impl();
        SOAPPart sp = msg.getSOAPPart();
        SOAPEnvelope env = sp.getEnvelope();
        env.addNamespaceDeclaration("ns", _serviceURI);
        SOAPBody bdy = env.getBody();

        SOAPBodyElement bodyElement =
            bdy.addBodyElement(env.createName(_operation, "ns", _serviceURI));

        addAllParameters(_parameters.iterator(), bodyElement);
        return msg;
    }

    /**
     * It adds the desired details to the SOAPBody.
     *
     * @param params
     *            Iterator
     * @param bodyElement
     *            Element
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message.
     */
    private void addAllParameters(Iterator< ? > params, SOAPElement bodyElement)
    throws SOAPException {

        while (params.hasNext()) {
            Parameter parm = (Parameter) params.next();
            SOAPElement child = bodyElement.addChildElement(parm.getName())
                    .addTextNode(parm.getValue());
            if (parm.childrenExist()) {
                addAllParameters(parm.getChildren().iterator(), child);
            }
        }
    }

    /**
     * Writes this <code>SOAPMessage</code> object to the given output stream.
     *
     * @param msg
     *            message to be written out.
     * @return out output stream object to which it's written.
     * @throws SOAPException
     *             if there was a problem in externalizing this SOAP message
     * @throws IOException
     *             if an I/O error occurs
     */
    private String messageToString(SOAPMessage msg) throws SOAPException,
            IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        msg.writeTo(out);
        return out.toString();
    }

    /**
     * Provides method for settin gand retrieving teh details.
     *
     */
    public class Parameter {

        /**
         * Name to be added.
         */
        private String _name;

        /**
         * Holds the Corresponding value for name.
         */
        private String _value;

        /**
         * List for adding details.
         */
        private List<Parameter> _children;

        /**
         * Sets this name and value and initializes the <code>_children</code>
         * list.
         *
         * @param name
         *            name to be set.
         * @param value
         *            value to be set.
         */
        Parameter(String name, String value) {
            _name = name;
            _value = value;
            _children = new ArrayList<Parameter>();
        }

        /**
         * Appends the specified element to the end of this list.
         *
         * @param name
         *            Name
         * @param value
         *            Value corresponding to Name.
         * @return parameter with this name and value set.
         */
        public Parameter addParameter(String name, String value) {

            Parameter parameter = new Parameter(name, value);
            _children.add(parameter);
            return parameter;
        }

        /**
         * Returns this name.
         *
         * @return _name
         */
        public String getName() {
            return _name;
        }

        /**
         * Returns this Value.
         *
         * @return _value
         */
        public String getValue() {
            return _value;
        }

        /**
         * Returns this children.
         *
         * @return _children.
         */
        public List<Parameter> getChildren() {
            return _children;
        }

        /**
         * Returns <code>true</code> if this list have childrens
         * <code>false</code>otherwise.
         *
         * @return <code>true</code> if this list have childrens
         *         <code>false</code>otherwise.
         */
        public boolean childrenExist() {
            return (_children.size() > 0) ? true : false;
        }
    }
}
