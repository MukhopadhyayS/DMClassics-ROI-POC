/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.jms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author sahuly
 * @date   Dec 22, 2008
 */
public class MessagePoster {

    private JmsTemplate _jmsTemplate;
    private Queue _queue;
    private Topic _topic;

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this._jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void setQueue(Queue queue) {
        this._queue = queue;
    }
    
    public Queue getQueue() {
        return _queue;
    }

    public void setTopic(Topic topic) {
        this._topic = topic;
    }

    public Topic getTopic() {
        return _topic;
    }

    /**
     * Post the message in the queue/topic which is configured in the spring.xml
     * 
     * @param message
     *        message to be posted  
     */
    public void sendTextMessage(final String message) {

        _jmsTemplate.send((_queue != null) ? _queue : _topic, new MessageCreator() {

            public Message createMessage(Session session) 
            throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
