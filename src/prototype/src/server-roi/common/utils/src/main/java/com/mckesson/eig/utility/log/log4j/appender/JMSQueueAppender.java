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

package com.mckesson.eig.utility.log.log4j.appender;

import java.util.Properties;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


public class JMSQueueAppender extends AppenderSkeleton {

    private static final Log LOG = LogFactory.getLogger(JMSQueueAppender.class);

    private String _initialContextFactoryName;
    private String _providerURL;
    private String _queueBindingName;
    private String _queuePhysicalName;
    private String _qcfBindingName;
    private QueueConnection _queueConnection;
    private QueueSession _queueSession;
    private QueueSender _queueSender;

    public JMSQueueAppender() {
    }

    @Override
    public void activateOptions() {

        QueueConnectionFactory queueConnectionfactory;
        try {

            Context ctx = getInitialContext();
            queueConnectionfactory = (QueueConnectionFactory)
                                      ctx.lookup(getQueueConnectionFactoryBindingName());
            _queueConnection = queueConnectionfactory.createQueueConnection();
            _queueSession = _queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
             Queue queue = (Queue) ctx.lookup(getQueueBindingName());
            _queueSender = _queueSession.createSender(queue);
            _queueConnection.start();
            ctx.close();
        } catch (Exception exception) {
            errorHandler.error("Error while activating options for appender named ["
                                + name + "].", exception, 0);
        }
    }

    protected InitialContext getInitialContext() throws NamingException {

        Properties properties = new Properties();
        properties.put("java.naming.factory.initial", getInitialContextFactoryName());
        properties.put("queue." + _queueBindingName, getQueuePhysicalName());
        properties.put("java.naming.provider.url", getProviderURL());

        return new InitialContext(properties);
    }

    protected boolean checkEntryConditions() {

        String s = null;
        if (_queueConnection == null) {
            s = "No QueueConnection";
        } else if (_queueSession == null) {
            s = "No QueueSession";
        } else if (_queueSender == null) {
            s = "No Queue Sender";
        }
        if (s != null) {
            errorHandler.error(s + " for JMSAppender named [" + name + "].");
            return false;
        }
        return true;
    }

    /**
     * Close this JMSQueueAppender. Closing releases all resources used by the
     * appender. A closed appender cannot be re-opened.
     */
    public synchronized void close() {

        if (closed) {
            return;
        }

        LogLog.debug("Closing appender [" + name + "].");
        closed = true;

        try {
            if (_queueSession != null) {
                _queueSession.close();
            }
            if (_queueConnection != null) {
                _queueConnection.close();
            }

        } catch (Exception exception) {
            LogLog.error("Error while closing JMSAppender [" + name + "].",
                    exception);
        }
        _queueSender      = null;
        _queueSession     = null;
        _queueConnection  = null;
    }

    @Override
    public void append(LoggingEvent loggingevent) {

        if (!checkEntryConditions()) {
            return;
        }
        try {
            String logMsg = layout.format(loggingevent);
            TextMessage textMessage = _queueSession.createTextMessage(logMsg);
            JMSQueueAppender.LOG.info("Sending Message.." + logMsg);
            _queueSender.send(textMessage);
        } catch (Exception exception) {
            errorHandler.error("Could not publish message in JMSAppender [" + name + "].",
                                exception, 0);
        }
    }

    public String getInitialContextFactoryName() {
        return _initialContextFactoryName;
    }

    public void setInitialContextFactoryName(String s) {
        _initialContextFactoryName = s;
    }

    public String getProviderURL() {
        return _providerURL;
    }

    public void setProviderURL(String s) {
        _providerURL = s;
    }

    public boolean requiresLayout() {
        return false;
    }

    public String getQueueBindingName() {
        return _queueBindingName;
    }

    public void setQueueBindingName(String queueBindingName) {
        _queueBindingName = queueBindingName;
    }

    public String getQueueConnectionFactoryBindingName() {
        return _qcfBindingName;
    }

    public void setQueueConnectionFactoryBindingName(String qcfBindingName) {
        _qcfBindingName = qcfBindingName;
    }

    public String getQueuePhysicalName() {
        return _queuePhysicalName;
    }

    public void setQueuePhysicalName(String physicalName) {
        _queuePhysicalName = physicalName;
    }
}
