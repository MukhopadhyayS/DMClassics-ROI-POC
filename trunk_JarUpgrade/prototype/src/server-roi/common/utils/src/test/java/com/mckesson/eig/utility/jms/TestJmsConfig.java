/* 
 * Copyright 2002-04 McKesson Information Solutions
 *
 * The copyright to the computer program(s) herein
 * is the property of McKesson Information Solutions.
 * The program(s) may be used and/or copied only with
 * the written permission of McKesson Information Solutions
 * or in accordance with the terms and conditions 
 * stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */

package com.mckesson.eig.utility.jms;

import junit.framework.TestCase;
public class TestJmsConfig extends TestCase {

	private JmsConfig _config;

	public TestJmsConfig(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
         _config = new JmsConfig(JmsConfig.URL, JmsConfig.JMS_QUEUE_NAME, 
                 JmsConfig.CONNECTION_FACTORY);
	}

	protected void tearDown() throws Exception {
	    _config = null; 
		super.tearDown();
	}

	public void testDefault() {
		assertEquals(JmsConfig.URL, _config.getUrl());
        assertEquals(JmsConfig.JMS_QUEUE_NAME, _config.getJmsQueueName());
		assertEquals(JmsConfig.CONNECTION_FACTORY, _config.getConnectionFactory());
	}

	public void testConstructor() {      
		_config = new JmsConfig();
        _config.setUrl("jnp://abc:123");
        _config.setJmsQueueName("defMDM");
        _config.setConnectionFactory("ghi");
        _config.setPhysicalName("physicalname");
        assertEquals("jnp://abc:123", _config.getUrl());
        assertEquals("defMDM", _config.getJmsQueueName());
        assertEquals("ghi", _config.getConnectionFactory());
        assertEquals("physicalname", _config.getPhysicalName());
	}

	public void testGetUrl() {
		_config = new JmsConfig("http://127.0.0.1:2500", "fooMDM", "foo3");
		assertEquals("http://127.0.0.1:2500", _config.getUrl());
	}

    public void testToString() {
		_config = new JmsConfig("http://127.0.0.1:2500", "fooMDM", "foo3");
		assertEquals("Url=[http://127.0.0.1:2500] JMS Queue=[fooMDM] "
                + "Connection Factory=[foo3] Physical Name=[null]",
                _config.toString());
    }
    
    
	
}

