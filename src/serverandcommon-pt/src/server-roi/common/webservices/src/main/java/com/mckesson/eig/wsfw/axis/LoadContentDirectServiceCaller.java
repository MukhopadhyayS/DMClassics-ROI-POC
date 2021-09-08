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

package com.mckesson.eig.wsfw.axis;

import java.lang.reflect.Method;

import javax.jms.TextMessage;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author ec7opip
 *
 */
public class LoadContentDirectServiceCaller extends DirectServiceCaller {
    
    private static final OCLogger LOG = new OCLogger(LoadContentDirectServiceCaller.class);

    private String _badBatchHandlerClass = null;
    private String _badBatchHandlerrMethod = null;
 
    
    public LoadContentDirectServiceCaller(String serverConfigFile,
            String targetService) {
        super(serverConfigFile, targetService);
    }

    public LoadContentDirectServiceCaller(String serverConfigFile,
            String targetService, String badBatchHandlerClass, String badBatchHandlerMethod) {
        super(serverConfigFile, targetService);
        _badBatchHandlerClass = badBatchHandlerClass;
        _badBatchHandlerrMethod = badBatchHandlerMethod;
    }

    /**
     * Receives JMS messages for processing via Axis.
     */
    public void callService(TextMessage msg) {
        String messageXml = null;
        String newMessageXML = null;
        LOG.debug("callService() ... ");

        try {
            MessageContext msgContext = new MessageContext(getAxisServer());
            msgContext.setTargetService(getTargetService());

            messageXml = msg.getText();
            LOG.debug("Following message was received");
            LOG.debug(messageXml);

            newMessageXML = stripSOAPHeader(messageXml);
            LOG.debug("Following message was transformed");
            LOG.debug(newMessageXML);

            msgContext.setRequestMessage(new Message(newMessageXML));
            getAxisServer().invoke(msgContext);
        } catch (Throwable t) {
            LOG.error("Service " + getTargetService()
                    + " failed.  Message invoked: " + messageXml, t);
  
            // need to save xml as error batch
            saveErrorBatch(newMessageXML, t);

            throw new ApplicationException("Error processing loadContent service request, "
                    + "error batch saved "
                    + getTargetService(), t, ClientErrorCodes.LOAD_CONTENT_BATCH_XML_BAD);
        }
    }

    private String stripSOAPHeader(String originalXMLMessage) {
        StringBuffer newXMLMessage = null;
        int startIndex = originalXMLMessage.indexOf("<soap:Header>");
        int endIndex = originalXMLMessage.indexOf("<soap:Body>");
        if ((startIndex == -1) || (endIndex == -1)) {
            return originalXMLMessage;
        }
        newXMLMessage = new StringBuffer();
        newXMLMessage.append(originalXMLMessage.substring(0, startIndex));
        newXMLMessage.append(originalXMLMessage.substring(endIndex));
        return newXMLMessage.toString();
    }

    private void saveErrorBatch(String badMessage, Throwable parseError) {
        try {
            // set up call to error batch handler
            Class errorBatchHandler = Class.forName(getHandlerClass());
            // argument definitions
            Object[] methodArgs = new Object[2];
            methodArgs[0] = badMessage;
            methodArgs[1] = parseError;
            // parameter values
            Class[]  paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = Throwable.class;
            // set up method
            Method handlerMethod = 
                errorBatchHandler.getDeclaredMethod(getHandlerMethod(), paramTypes);
            Object instance = errorBatchHandler.newInstance();
            // pull the trigger
            handlerMethod.invoke(instance, methodArgs);
        } catch (Throwable t) {
            throw new ApplicationException("Unable to save error batch by calling "
                    + ((getHandlerClass() != null)  ? getHandlerClass() : "null") 
                    + "." 
                    + ((getHandlerMethod() != null) ? getHandlerMethod() : "null"),
                    t, ClientErrorCodes.LOAD_CONTENT_BATCH_XML_BAD);
        }
    }

    protected String getHandlerClass() {
        return _badBatchHandlerClass;
    }

    protected String getHandlerMethod() {
        return _badBatchHandlerrMethod;
    }
    

}
