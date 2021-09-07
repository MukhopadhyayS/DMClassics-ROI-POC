/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.jms;

import java.io.Serializable;

import com.mckesson.eig.utility.util.StringUtilities;

public class JmsConfig implements Cloneable, Serializable {

	private static final long serialVersionUID = 4492513506576991940L;

	protected static final String URL = "jnp://127.0.0.1:1099";
	protected static final String JMS_QUEUE_NAME = "queue/eigAudit";
    protected static final String CONNECTION_FACTORY = "java:/ConnectionFactory";

    private String _url;
    private String _jmsQueue;
	private String _connectionFactory;
	private String _physicalName;

	public JmsConfig() {
	    //Removed the default queue configuration to make
        // the Utility files generic this
        // (SCHEMA, SERVER, JMS_PORT, JMS_QUEUE_NAME, CONNECTION_FACTORY );
	}

    public JmsConfig(String url, String jmsQueue, String connectionFactory) {
        StringUtilities.verifyHasContent(url);
        StringUtilities.verifyHasContent(jmsQueue);
        StringUtilities.verifyHasContent(connectionFactory);

        _url = url;
        _jmsQueue = jmsQueue;
        _connectionFactory = connectionFactory;
    }

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
         _url = url;
    }

    public String getJmsQueueName() {
        return _jmsQueue;
    }

	public String getConnectionFactory() {
		return _connectionFactory;
	}

    public void setJmsQueueName(String jmsQueue) {
         _jmsQueue = jmsQueue;
    }
    public void setConnectionFactory(String connectionFactory) {
        _connectionFactory = connectionFactory;
    }

	@Override
	public String toString() {
		return "Url=["
				+ getUrl()
                + "] JMS Queue=["
                + getJmsQueueName()
                + "] Connection Factory=["
                + getConnectionFactory()
				+ "] Physical Name=["
                + getPhysicalName() + "]";
	}

    public String getPhysicalName() {
        return _physicalName;
    }

    public void setPhysicalName(String name) {
        _physicalName = name;
    }
}

