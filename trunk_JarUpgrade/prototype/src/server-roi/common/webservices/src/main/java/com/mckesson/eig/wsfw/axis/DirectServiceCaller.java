/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

import java.net.URL;

import javax.jms.TextMessage;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.server.AxisServer;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * This is a POJO that will take a JMS Text message that contains
 * a SOAP envelope and run it through
 * a local copy of axis to process the request on the server.
 * 
 */
public class DirectServiceCaller {

    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final Log LOG = LogFactory
            .getLogger(DirectServiceCaller.class);
    /**
     * Instance of AxisServer - The WebServicesServer.
     */
    private AxisServer _axisServer;
    private String _targetService;

    /**
     * Create a new caller using the specified config file (WSDD) and target
     * service from that WSDD.  An axis server instance is created and 
     * initialized with the WSDD.
     * 
     * @param serverConfigFile
     * @param targetService
     */
    DirectServiceCaller(String serverConfigFile, String targetService) {
        // Load the server-config
        String fullPath = getFullPath(serverConfigFile);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Axis server config file:: " + fullPath);
        }
        FileProvider provider = new FileProvider(fullPath);
        _axisServer = new AxisServer(provider);
        _targetService = targetService;
    }

    /**
     * Receives JMS messages for processing via Axis.
     */
    public void callService(TextMessage msg) {
        String messageXml = null;
        try {
            MessageContext msgContext = new MessageContext(_axisServer);
            msgContext.setTargetService(_targetService);

            messageXml = msg.getText();
            LOG.debug("Following message was recieved");
            LOG.debug(messageXml);

            msgContext.setRequestMessage(new Message(messageXml));
            _axisServer.invoke(msgContext);
        } catch (Throwable t) {
            LOG.fatal("Service " + _targetService
                    + " failed.  Message received: " + messageXml, t);
            throw new ApplicationException("Error processing JMS service "
                    + _targetService, t, ClientErrorCodes.SYSTEM_ERROR);
        }
    }

    /**
     * @return the _axisServer
     */
    public AxisServer getAxisServer() {
        return _axisServer;
    }

    /**
     * @param server the _axisServer to set
     */
    public void setAxisServer(AxisServer server) {
        _axisServer = server;
    }

    /**
     * @return the _targetService
     */
    public String getTargetService() {
        return _targetService;
    }

    /**
     * @param service the _targetService to set
     */
    public void setTargetService(String service) {
        _targetService = service;
    }

    /**
     * Method that returns the fullpath name given a file name.
     * 
     * @param fileName -
     *            The name of the file for which the path has to be determined.
     * @return full path name of the File
     */
    private String getFullPath(String fileName) {
        try {
            URL url = FileLoader.getResourceAsUrl(fileName);
            if (url == null) {
                String desc = "Resource (" + fileName + ") not Found";
                LOG.error(desc);
                return null;
            }
            String fullPath = url.toString();

            // Strip off the file:/ -- there's probably a better way to do this
            // but converting to a File and pulling the Path was giving
            // incorrect results
            fullPath = fullPath.replaceAll("file:/", "");
            fullPath = fullPath.replaceAll("%20", " ");

            LOG.debug("*** Full path =  (" + fullPath + ")");

            return fullPath;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
