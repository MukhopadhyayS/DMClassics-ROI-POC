/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.utility.log.log4j.appender;

/**
 * @author OFS
 * @date   Jul 8, 2011
 * @since  Jul 8, 2011
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.jdbc.JDBCAppender;

import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * <p>
 * Use the JNDI  to create the Database connection for Logging
 * </p>
 *
 * @see org.apache.log4j.jdbc.JDBCAppender;
 *
 */
public class DatabaseLoggingAppender extends JDBCAppender {

	private static OCLogger LOG = new OCLogger(DatabaseLoggingAppender.class);

	private String _dsSource = "java:/DS";
	private DataSource _dataSource;
	
	private static boolean _connectByProperties = false;

	public void setDsSource(String dsSource) {	this._dsSource = dsSource;	}
	public String getDsSource() {	return _dsSource;	}

	public void setDataSource(DataSource ds) {	_dataSource = ds;	}
	private DataSource getDataSource() {

		if (null !=_dataSource) {
			return _dataSource;
		}

		try {

			InitialContext ic = new InitialContext();
			_dataSource = (DataSource) ic.lookup(getDsSource());
		} catch (NamingException e) {
			// Unfortunately if we catch an exception here, we cannot
			// log using an appender.
			// So calling LOG.error(e) will cause an infinite loop
			// Instead show the error and stace trace in System.out
			e.printStackTrace(System.out);
		}
		return _dataSource;
	}

	
	/**
	 * loads the database conneciton properties from the given property file
	 * @param filename
	 */
	public void setConnectionPropertiesFile(String filename) {

		try {

			Properties prop = new Properties();
			prop.load(DatabaseLoggingAppender.class.getClassLoader().getResourceAsStream(filename));
			setDriver(prop.getProperty("jdbc.driver"));
			setURL(prop.getProperty("jdbc.url"));
			setUser(prop.getProperty("db.user"));
			setPassword(prop.getProperty("db.password"));
			
			_connectByProperties = true;
			
		} catch (Exception e) {
			// Unfortunately if we catch an exception here, we cannot
			// log using an appender.
			// So calling LOG.error(e) will cause an infinite loop
			// Instead show the error and stace trace in System.out
			e.printStackTrace(System.out);
		}
	}

	/**
	 *
	 * Get a Database connection from datasource,
	 * if connection not exists
	 *
	 * @return Connection
	 */
	protected Connection getConnection()
	throws SQLException {

		if (connection == null || connection.isClosed()) {
			
			if (_connectByProperties) {
				connection = super.getConnection();
			} else {
				connection = getDataSource().getConnection();
			}
		}

		return connection;
	}

	/**
	 *
	 * Close the existing Database connection.
	 *
	 */
	protected void closeConnection(Connection con) {

		try {
			connection.close();
		} catch (SQLException ex) {
			LOG.error("SQLException occurred in closeConnection ",ex);
		}
	}
}
