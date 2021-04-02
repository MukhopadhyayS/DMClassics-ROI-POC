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

package com.mckesson.eig.wsfw.cxf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jms.TextMessage;

import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.local.LocalTransportFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.exception.ErrorHandler;

/**
 * @author sahuly
 * @date   Dec 18, 2008
 * 
 * This is a POJO that will take a JMS Text message that contains a SOAP envelope and 
 * run it through a local transport to process the request on the server.
 */
public class CXFDirectServiceCaller {
    
    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final Log LOG = LogFactory.getLogger(CXFDirectServiceCaller.class);
    
    private boolean _processHeader;
    
    /**
     * Instantiates a CXFDirectServiceCaller
     */
    public CXFDirectServiceCaller() {
        this(false);
    }
    
    /**
     * Instantiates a CXFDirectServiceCaller and need to provide the processHeader to be true 
     * or false. If true has been set, it will strip of the soap header from the envelope 
     * otherwise not. 
     */
    public CXFDirectServiceCaller(boolean processHeader) {
        _processHeader = processHeader;
    }


    /**
     * Receives JMS messages for processing via CXF.
     */
    public void callService(TextMessage msg) {

        String messageXML = null;

        try {

            messageXML = _processHeader ? stripSOAPHeader(msg.getText()) : msg.getText();
            invokeService(messageXML);
        } catch (Throwable t) {

            LOG.fatal("Message received: " + messageXML, t);
            throw new ApplicationException("Error processing JMS service ",
                                            t, 
                                            ClientErrorCodes.SYSTEM_ERROR);
        }
    }

    /**
     * Local transport is used to send messsages which will serialized and 
     * piped from one endpoint to another.
     * 
     * @param messageXML
     *        soap message in the string format
     *          
     * @throws IOException
     */
    public void invokeService(String messageXML) throws IOException {

        LocalTransportFactory obj = 
            (LocalTransportFactory) SpringUtilities.getInstance()
                                                   .getBeanFactory()
                                                   .getBean(LocalTransportFactory.class.getName());

        EndpointInfo ei = new EndpointInfo();
        ei.setAddress("local://message");
        //TODO: Add Bus 
        Destination destination = obj.getDestination(ei, null);
        MessageImpl m = prepareMessage(messageXML, destination);

        SoapMessage sm = new SoapMessage(m);
        sm.setVersion(Soap11.getInstance());

        destination.getMessageObserver().onMessage(sm);
        Throwable t = m.getContent(Exception.class);

        if (t != null) {
            handleException(messageXML, t);
        }
        m.getInterceptorChain().abort();
    }
    
    /**
     * Get the bean name for the Error Handler and process the message as per needed.
     * 
     * @param messageXML    
     *        soap message in the string format
     * @param t
     *        exception
     */
    private void handleException(String messageXML, Throwable t) {

        try {

            ((ErrorHandler) SpringUtilities.getInstance()
                                           .getBeanFactory()
                                           .getBean(ErrorHandler.class.getName()))
                                           .processErrorMessage(messageXML, t);
            return;
        } catch (NoSuchBeanDefinitionException beanException) {
            LOG.debug("Custom defined error handler is not found");
        }
    }
    
    /**
     * convert the message from sting format to CXF specific message format
     * 
     * @param messageXML
     *        soap message in the string format
     * 
     * @param destination
     *        recieving incoming messages
     *  
     * @return message
     *         CXF specific message 
     *      
     */
    private MessageImpl prepareMessage(String messageXML, Destination destination) {

        MessageImpl message = new MessageImpl();

        ByteArrayInputStream bis = new ByteArrayInputStream(messageXML.getBytes()); 

        message.setContent(InputStream.class, bis);
        message.setDestination(destination);

        ExchangeImpl exchangeImpl = new ExchangeImpl();
        exchangeImpl.setOneWay(true);
        exchangeImpl.setInMessage(message);
        message.setExchange(exchangeImpl);
        return message;
    }
    
    /**
     * remove the header part from the incoming message.
     * 
     * @param originalXMLMessage
     * @return
     */
    private String stripSOAPHeader(String originalXMLMessage) {
        
        LOG.debug("Following message was received");
        LOG.debug(originalXMLMessage);

        int startIndex = originalXMLMessage.indexOf(":Header>");
        int endIndex = originalXMLMessage.indexOf(":Body>");

        if ((startIndex == -1) || (endIndex == -1)) {
            return originalXMLMessage;
        }

        StringBuffer newXMLMessage = new StringBuffer();
        newXMLMessage.append(originalXMLMessage.substring(0, startIndex));
        newXMLMessage.append(originalXMLMessage.substring(endIndex));   

        LOG.debug("Following message was transformed");
        LOG.debug(newXMLMessage.toString());
        return newXMLMessage.toString();
    }
}
