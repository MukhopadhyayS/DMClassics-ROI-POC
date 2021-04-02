/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.conversion.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;

import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.config.Constants;

/**
 * @author bhanu
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	/**
	 * 
	 */
	public static void initialize() throws HibernateException {
		sessionFactory = null;
		String serverName = Configuration.getProperties().getProperty(Constants.DB_SERVER_PROPERTY);
		String username = Configuration.getProperties().getProperty(Constants.DB_USERNAME_PROPERTY);
		String password = Configuration.getProperties().getProperty(Constants.DB_PASSWORD_PROPERTY);
		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
		configuration.setProperty(Environment.URL, "jdbc:jtds:sqlserver://" + serverName + ":1433;DatabaseName=cabinet");
		configuration.setProperty(Environment.USER, username);
		configuration.setProperty(Environment.PASS, password);

		sessionFactory = configuration.buildSessionFactory();
	}
	
	/**
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 
	 */
	public static void destroy() {
		if(sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
