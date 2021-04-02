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
import java.util.Hashtable;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.mckesson.eig.utility.jndi.JndiUtilities;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public class JmsService {

	public static final String JNDI_CONTEXT_FACTORY =
	    "org.apache.activemq.jndi.ActiveMQInitialContextFactory";

	private static final Log LOG = LogFactory.getLogger(JmsService.class);

	private JmsConfig _config;
	private Context _context;

	public JmsService() {
	}

	public JmsService(JmsConfig config) {
		_config = config;
		_context = getDefaultContext();

		if (LOG.isDebugEnabled()) {
			LOG.debug("Establishing jms configuration using, "
					+ "connection factory =  ["
					+ _config.getConnectionFactory() + "], and url = ["
					+ _config.getUrl() + "]");
		}
	}

	public QueueConnection createConnection(QueueConnectionFactory qcf) {
		try {
			return qcf.createQueueConnection();
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	/**
	 * This message processes a non-transacted session.
	 *
	 * @param qc
	 *            connection to the queue
	 * @param mode
	 *            legal values are Session.AUTO_ACKNOWLEDGE,
	 *            Session.CLIENT_ACKNOLEDGE, Session.DUPS_OK_ACKNOWLEDGE
	 * @return
	 */
	public QueueSession createSession(QueueConnection qc, int mode) {
		return createSession(qc, false, mode);
	}

	/**
	 *
	 * @param qc
	 *            connection to the queue
	 * @param transacted
	 *            is this session transacted
	 * @param mode
	 *            legal values are Session.AUTO_ACKNOWLEDGE,
	 *            Session.CLIENT_ACKNOLEDGE, Session.DUPS_OK_ACKNOWLEDGE
	 * @return
	 */
	public QueueSession createSession(QueueConnection qc, boolean transacted,
			int mode) {
		try {
			return qc.createQueueSession(transacted, mode);
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public void start(QueueConnection qc) {
		try {
			qc.start();
		} catch (Exception e) {
			throw new JmsException("Error: starting queue connection", e);
		}
	}

	public void stop(QueueConnection qc) {
		try {
			qc.stop();
		} catch (Exception e) {
			throw new JmsException("Error: stopping queue connection", e);
		}
	}

	public QueueSender createSender(QueueSession session, Queue q) {
		try {
			return session.createSender(q);
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public Message createTextMessage(QueueSession session, String s) {
		try {
			return session.createTextMessage(s);
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public ObjectMessage createObjectMessage(QueueSession session,
			Serializable s) {
		try {
			return session.createObjectMessage(s);
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public void sendTextMessage(QueueSender sender, QueueSession session,
			String message) {
		LOG.debug("Preparing to send message..............");

		send(sender, createTextMessage(session, message));

		if (LOG.isDebugEnabled()) {
			LOG.debug("The following message was sent.");
			LOG.debug("...............................");
			LOG.debug(message);
			LOG.debug("...............................");
		}
	}

	public void sendObjectMessage(QueueSender sender, QueueSession session,
			Serializable o) {
		LOG.debug("Sending Message");

		send(sender, createObjectMessage(session, o));

		if (LOG.isDebugEnabled()) {
			LOG.debug("The following object was sent:  " + o.toString());
		}
	}

	public void send(QueueSender s, Message m) {
		try {
			s.send(m);
		} catch (Exception e) {
			throw new JmsException("Error: sending message to queue", e);
		}
	}

	public void sendMessage(String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Sending AUTO_ACKNOWLEDGE message to queue:  "
					+ _config.getJmsQueueName());
		}

		QueueConnection connection = createConnection(lookupConnectionFactory(_context));
		QueueSession session = createSession(connection,
				javax.jms.QueueSession.AUTO_ACKNOWLEDGE);

		start(connection);

		QueueSender sender = createSender(session, lookupQueue(_context,
				_config.getJmsQueueName()));

		sendTextMessage(sender, session, message);
		close(sender);
		close(session);
		close(connection);
	}

	public QueueSender createSender(QueueSession session, String queueName) {
		try {
			Queue q = session.createQueue(queueName);
			return session.createSender(q);
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public void sendMessage(QueueConnection connection, String queueName,
			String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Sending AUTO_ACKNOWLEDGE message to queue:  "
					+ queueName);
		}

		QueueSession session = createSession(connection,
				javax.jms.QueueSession.AUTO_ACKNOWLEDGE);

		start(connection);
		QueueSender sender = createSender(session, queueName);

		sendTextMessage(sender, session, message);
		close(sender);
		close(session);
		close(connection);
	}

	public void sendMessage(Context c, String qName, String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Sending AUTO_ACKNOWLEDGE message to queue:  " + qName);
		}

		QueueConnection connection = createConnection(lookupConnectionFactory(c));
		QueueSession session = createSession(connection,
				Session.AUTO_ACKNOWLEDGE);

		start(connection);

		QueueSender sender = createSender(session, lookupQueue(_context,
				_config.getJmsQueueName()));

		sendTextMessage(sender, session, message);
		close(sender);
		close(session);
		close(connection);
	}

	public void sendMessage(Context c, String qName, Serializable o) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Sending AUTO_ACKNOWLEDGE message to queue:  " + qName);
		}

		QueueConnection connection = createConnection(lookupConnectionFactory(c));
		QueueSession session = createSession(connection,
				Session.AUTO_ACKNOWLEDGE);

		start(connection);

		QueueSender sender = createSender(session, lookupQueue(_context,
				_config.getJmsQueueName()));

		sendObjectMessage(sender, session, o);
		close(sender);
		close(session);
		close(connection);
	}

	public void close(QueueSession session) {
		try {
			session.close();
		} catch (Exception e) {
			throw new JmsException("Error: closing queue session", e);
		}
	}

	public void close(QueueConnection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			throw new JmsException("Error: closing connections", e);
		}
	}

	public void close(QueueSender sender) {
		try {
			sender.close();
		} catch (Exception e) {
			throw new JmsException("Error: closing connections", e);
		}
	}

	/**
	 * @param c
	 * @return
	 */
	public QueueConnectionFactory lookupConnectionFactory(Context c) {
		try {
			return (QueueConnectionFactory) JndiUtilities.lookup(c, _config
					.getConnectionFactory());
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public Queue lookupQueue(Context c, String name) {
		try {
			return verifyQueue(name, JndiUtilities.lookup(c, name));
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public Queue verifyQueue(String name, Object o) {
		if (o != null) {
			return (Queue) o;
		}
		throw new NullPointerException("Could not find Queue:  " + name);
	}

	public Object getObject(ObjectMessage m) {
		try {
			return m.getObject();
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}

	public Context getDefaultContext() {
		try {
			Hashtable<String, String> context = new Hashtable<String, String>();
			context.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_CONTEXT_FACTORY);
			context.put(Context.PROVIDER_URL, _config.getUrl());
			context.put("queue." + _config.getJmsQueueName(), _config.getPhysicalName());
			Context jmsContext = new InitialContext(context);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Establishing default context with "
						+ "the following properties =  ["
						+ jmsContext.getEnvironment().toString() + "]");
			}
			return jmsContext;
		} catch (Exception e) {
			throw new JmsException(e);
		}
	}
}
