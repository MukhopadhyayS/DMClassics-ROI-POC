package com.mckesson.eig.roi.utils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.zaxxer.hikari.HikariDataSource;

public class DSWrapper implements DataSource {
	private static final int DEFAULT_IDLE_AGE_MINUTE = 5;
	//private BoneCPDataSource _ds = new BoneCPDataSource();
	private HikariDataSource _ds = new HikariDataSource();
	
	public DSWrapper(String driverClass, String dbUrl) {
		//_ds.setDriverClass(driverClass);
	    _ds.setDriverClassName(driverClass);
		setDbURL(dbUrl);
	}

	/*public DSWrapper(String driverClass, String dbUrl, String username,
			String password, int min, int max) {
		//_ds.setDriverClass(driverClass);
	    _ds.setDriverClassName(driverClass);
		setDbURL(dbUrl);
		setUserName(username);
		setPassword(password);
		setMinmumConnection(min);
		setMaximumConnection(max);
		setIdleMaxAge(DEFAULT_IDLE_AGE_MINUTE);
	}*/
	
	public DSWrapper(String driverClass, String dbUrl, String username,
            String password) {
        //_ds.setDriverClass(driverClass);
        _ds.setDriverClassName(driverClass);
        setDbURL(dbUrl);
        setUserName(username);
        setPassword(password);
        setIdleMaxAge(DEFAULT_IDLE_AGE_MINUTE);
    }

	/*public DSWrapper(String driverClass, String dbUrl, String username,
			String password) {
		this(driverClass, dbUrl, username, password,
				ROIConstants.PLUGIN_MIN_DB_CONNECTION,
				ROIConstants.PLUGIN_MAX_DB_CONNECTION);
	}
*/
	public Connection getConnection() throws SQLException {
		return _ds.getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		_ds.setUsername(username);
		_ds.setPassword(password);
		return _ds.getConnection();
	}

	public void close() {
		_ds.close();
	}

	public void setUserName(String username) {
		_ds.setUsername(username);
	}

	public void setPassword(String password) {
		_ds.setPassword(password);
	}

	public void setDbURL(String url) {
		_ds.setJdbcUrl(url);
	}

	/*public void setMinmumConnection(int connection) {
	    _ds.setMinConnectionsPerPartition(connection);
	}

	public void setMaximumConnection(int connection) {
		_ds.setMaxConnectionsPerPartition(connection);
	}*/

	public void setIdleMaxAge(long minutes) {
		//_ds.setIdleMaxAgeInMinutes(minutes);
		_ds.setIdleTimeout(minutes);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return _ds.getLogWriter();
	}

	public void setLogWriter(java.io.PrintWriter out) throws SQLException {
		_ds.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		_ds.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return _ds.getLoginTimeout();
	}

	public <T> T unwrap(java.lang.Class<T> iface) throws java.sql.SQLException {
		return (T) _ds.unwrap(iface);
	}

	public boolean isWrapperFor(java.lang.Class<?> iface)
			throws java.sql.SQLException {
		return _ds.isWrapperFor(iface);
	}
	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
	    // To support compilation in JDK 1.7
        // If the Datasource has to be compiled with JDK1.7, then getParentLogger() method has to be introduced.
	    throw new SQLFeatureNotSupportedException();
	}
}
