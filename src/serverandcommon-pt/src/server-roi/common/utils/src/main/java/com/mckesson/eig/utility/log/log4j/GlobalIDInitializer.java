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

package com.mckesson.eig.utility.log.log4j;

/**
 * @author OFS
 * @date   Jul 8, 2011
 * @since  Jul 8, 2011
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.log.LogContext;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * Initializer for GlobalId
 * <p>
 * The Class initializes the globalId from the Database By calling the
 * procedure <code>EPRS_stats_GetComponentID_ByName</code> by using the
 * JNDI dataSource and the GlobalId is set to the log4j <code>MDC</code>
 * </p>
 *
 */
public class GlobalIDInitializer {

	private static OCLogger LOG = new OCLogger(GlobalIDInitializer.class);

	private String _dsSource = "java:/DS";
	private String _globalName;
	private DataSource _dataSource;
	private Connection _connection;
	private String _serverPort;

	private final String _global = "GLOBAL_ID";
	private final String SERVER_NAME = "COMPUTERNAME";


	public void setDsSource(String dsSource) { _dsSource = dsSource; }
	public String getDsSource() { return _dsSource; }

	public void setGlobalName(String globalName) { _globalName = globalName; }
	public String getGlobalName() {	return _globalName;	}

	public void setServerPort(String serverPort) { _serverPort = serverPort; }
	public String getServerPort() {	return _serverPort;	}

	/**
	 *
	 * Gets the DataSource from the given DataSource Jndi Name using the
	 * if the datasource is not configured, then look up for the name given in the dsSource 
	 * <code>InitialContext</code>
	 *
	 * @return datasource
	 */
	public DataSource getDataSource() {

		if (null !=_dataSource) {
			return _dataSource;
		} else {

			try {

				InitialContext ic = new InitialContext();
				_dataSource = (DataSource) ic.lookup(getDsSource());
			} catch (NamingException e) {

				LOG.error("NamingException occurred in getDataSource ",e);
				throw new ApplicationException(e.getCause());
			}
		}
		return _dataSource;
	}
	
	/**
	 *
	 * Sets the DataSource from the given DataSource Jndi Name using the
	 * <code>InitialContext</code>
	 *
	 * @return datasource
	 */
	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}


	/**
	 *
	 * Get a Database connection from datasource.
	 *
	 * @return connection
	 */
	protected Connection initializeConnection()
	throws SQLException {

		if (_connection == null || _connection.isClosed()) {

			if (_dataSource == null) {
				getDataSource();
			}
			_connection = _dataSource.getConnection();
		}
		return _connection;
	}

	/*
	 *
	 * Gets the GlobalId from the Database by calling the procedure
	 * EPRS_stats_GetComponentID_ByName using the Callable Statement
	 *
	 * @return globalId
	 */
	private long getGlobalID() {

		Connection con = null;
		CallableStatement callStmt = null;
		long globalID = 0;

		try {

			String serverHost = System.getenv(SERVER_NAME);
			String serverGlobalName = getGlobalName() +
										":" +
										serverHost +
										":" +
										getServerPort();

			con = initializeConnection();

			callStmt = con.prepareCall("{call dbo.EPRS_stats_GetComponentID_ByName(?, ?, ?)}");
			callStmt.setString(1, getGlobalName());
			callStmt.setString(2, serverGlobalName);
			callStmt.registerOutParameter(3, java.sql.Types.INTEGER);
			callStmt.execute();
			globalID = callStmt.getLong(3);

			LOG.info("The Retrieved GlobalID for "
					+ getGlobalName() +" is " + globalID);

		} catch (SQLException e) {

			LOG.error("SQLException occurred in getGlobalID ",e);
			throw new ApplicationException(e.getCause());
		} finally {

			if (null != con) {
				closeConnection(con);
			}
		}
		return globalID;
	}


	/**
	 * Close the existing Database connection.
	 */
	protected void closeConnection(Connection con) {

		try {
			con.close();
		} catch (SQLException ex) {

			LOG.error("SQLException occurred in closeConnection ",ex);
			throw new ApplicationException(ex.getCause());
		}
	}


	/**
	 *
	 * Initialize the GlobalId for the application into the Log4j
	 * <code>MDC</code>
	 *
	 */
	public void initAppGlobalID() {
		LogContext.put(_global, getGlobalID());
	}
}
