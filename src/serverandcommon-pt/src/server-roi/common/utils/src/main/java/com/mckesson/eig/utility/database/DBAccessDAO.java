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

package com.mckesson.eig.utility.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Executes a database query or stored procedure using provided variables.
 *
 * @author Ronnie Andrews, Jr + BP Stieffen.
 */
public final class DBAccessDAO {

	//defined statement types
	public static final int STANDARD_STATEMENT = 0;
	public static final int PREPARED_STATEMENT = 1;
	public static final int CALLABLE_STATEMENT = 2;
	public static final int[] SUPPORTED_STATEMENT_TYPES = new int[] {
		STANDARD_STATEMENT, PREPARED_STATEMENT, CALLABLE_STATEMENT
	};

	//defined operation types
	public static final int SELECT_OPERATION_TYPE = 0;
	public static final int INSERT_OPERATION_TYPE = 1;
	public static final int UPDATE_OPERATION_TYPE = 2;
	public static final int DELETE_OPERATION_TYPE = 3;
	public static final int[] SUPPORTED_OPERATION_TYPES = new int[] {
		SELECT_OPERATION_TYPE, INSERT_OPERATION_TYPE, UPDATE_OPERATION_TYPE,
		DELETE_OPERATION_TYPE
	};


	/**
     * Object represents the Log4JWrapper object.
     */
    protected static final OCLogger LOG = new OCLogger(DBAccessDAO.class);

    /**
     * Connection URL to desired DB including username + password.
     */
    private String _jdbcConnectionURL;

    /**
     * Full class name of JDBC driver to use.
     */
    private String _jdbcDriverClassName;

	/**
     * Timeout for DB query.
     */
    private int _operationTimeoutInSeconds;

    /**
     * Operation type such as select=0, insert=1, update=2, or delete=3.
     */
    private int _operationType;

    /**
     * Indexes of registered output parameters.
     */
 	private List<Integer> _outputParmIndexes;

    /**
     * List of parameters for query or stored procedure.
     */
    private List<DBParameter> _parameterList;

    /**
     * JDBC SQL string to execute.
     */
    private String _sqlString;

    /**
     * Statement type such as standard=0, prepared=1, and callable=2.
     */
    private int _statementType;

    /**
     * Default constructor.
     */
    private DBAccessDAO() {

    	LOG.debug("DBAccessDAO:DBAccessDAO() >> start");
    	LOG.debug("DBAccessDAO:DBAccessDAO() >> end");
    }

    /**
     * Get instance of this class.
     *
     * @return DBAccessDAO instance
     */
    public static DBAccessDAO getInstance() {

    	LOG.debug("DBAccessDAO:getInstance() >> start");

    	try {
    		return new DBAccessDAO();
    	} finally {
    		LOG.debug("DBAccessDAO:getInstance() >> end");
    	}
    }

    /**
     * Close db resources safely.
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void closeDBResources(ResultSet rs, Statement st,
    		Connection conn) {

    	if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error("Unable to close result set", e);
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LOG.error("Unable to close statement", e);
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error("Unable to close database connection", e);
			}
		}
    }

    /**
     * Execute query or stored procedure.
     *
     * @return result from query or stored procedure
     */
    public List<DBResult> execute() {

    	LOG.debug("DBAccessDAO:execute() >> start");

    	Connection conn = null;
    	Statement st = null;
    	List<DBResult> resultList = null;

    	try {

    		//obtain connection using URL
    		conn = getConnection();

    		//determine which type of statement we need and load params if needed
    		st = getStatement(conn);

    		if (st instanceof CallableStatement) {
                resultList = executeStatement((CallableStatement) st, _outputParmIndexes);
    		} else {
                resultList = executeStatement(st);
    		}

    		return resultList;

    	} catch (Exception e) {
    		String msg = "Unable to execute query or stored procedure: " + e.getMessage();
    		LOG.error(msg);
    		throw new ApplicationException(msg, e);
    	} finally {
    		closeDBResources(null, st, conn);
    		LOG.debug("DBAccessDAO:execute() >> end");
    	}
    }

    /**
     * Get connection using URL.
     *
     * @return connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {

    	LOG.debug("DBAccessDAO:getConnection() >> start");

    	try {

    		Class.forName(_jdbcDriverClassName);
    		Connection conn = DriverManager.getConnection(_jdbcConnectionURL);
    		return conn;

    	} finally {
    		LOG.debug("DBAccessDAO:getConnection() >> end");
    	}
    }

    /**
     * Get populated statement using standard=0, prepared=1, and callable=2.
     *
     * @param conn
     * @return statement
     * @throws SQLException
     */
    public Statement getStatement(Connection conn) throws SQLException {

    	LOG.debug("DBAccessDAO:getStatement() >> start");

    	try {

    		Statement st = null;

    		switch (_statementType) {

    		case STANDARD_STATEMENT:
    			st = conn.createStatement();
    			break;

    		case PREPARED_STATEMENT:
    			PreparedStatement ps = conn.prepareStatement(_sqlString);
    			initializeStatement(ps, false);
    			st = ps;
    			break;

    		case CALLABLE_STATEMENT:
    			CallableStatement cs = conn.prepareCall(_sqlString);
    			initializeStatement(cs, true);
    			st = cs;
    			break;

    		default:
    			throw new IllegalArgumentException("Invalid statement type: "
    					+ _statementType);
        	}

    		st.setQueryTimeout(_operationTimeoutInSeconds);
    		LOG.debug("Using query timeout in seconds: " + _operationTimeoutInSeconds);

    		return st;

    	} finally {
    		LOG.debug("DBAccessDAO:getStatement() >> end");
    	}
    }

    /**
     * Initialize prepared or callable statement.
     *
     * @param ps
     * @param isCallable
     * @throws SQLException
     */
    private void initializeStatement(PreparedStatement ps, boolean isCallable)
    	throws SQLException {

    	LOG.debug("DBAccessDAO:initializeStatement() >> start");

    	try {

    		if (!CollectionUtilities.isEmpty(_parameterList)) {

    			for (int i = 0, j = 1; i < _parameterList.size(); i++, j++) {

    				DBParameter dbParam = _parameterList.get(i);

    				if (!dbParam.isResultParameter()) {
    					ps.setObject(j, dbParam.getParameterObjectValue(),
    						dbParam.getParameterObjectType());
    				} else {

    					if (!isCallable) {
    						throw new IllegalArgumentException(
    							"Only stored procedures can have "
    							+ "output parameters");
    					} else {
    						CallableStatement cs = (CallableStatement) ps;

    						if (_outputParmIndexes == null) {
    							_outputParmIndexes =
    							new ArrayList<Integer>();
    						}

    						_outputParmIndexes.add(j);
    						cs.registerOutParameter(j,
    							dbParam.getParameterObjectType());
    					}
    				}

    				LOG.debug("Added db parameter: " + dbParam);
    			}
    		}

    	} finally {
    		LOG.debug("DBAccessDAO:initializeStatement() >> end");
    	}
    }

    /**
     * Execute given statement and return results.
     *
     * @param st
     * @param resultList
     * @throws SQLException
     */
    private List<DBResult> executeStatement(Statement st) throws SQLException {

        LOG.debug("DBAccessDAO:executeStatement() >> start");

        ResultSet rs = null;
        DBResult dbResult = null;

        try {

            List<DBResult> resultList = new ArrayList<DBResult>();

            boolean resultsAvailable = false;

            if (st instanceof PreparedStatement) {
                PreparedStatement ps = (PreparedStatement) st;
                resultsAvailable = ps.execute();
            } else {
                resultsAvailable = st.execute(_sqlString);
            }

            if (!resultsAvailable) {

                //must be an insert, update, or delete
                int rowsAffected = st.getUpdateCount();
                dbResult = new DBResult(rowsAffected);
                resultList.add(dbResult);

            } else {

                //select via standard or prepared statements
                rs = st.getResultSet();
                dbResult = processResultSet(rs);
                resultList.add(dbResult);
            }

            LOG.debug("Number of result objects from db call: " + resultList.size());

            return resultList;

        } finally {
            closeDBResources(rs, null, null);
            LOG.debug("DBAccessDAO:executeStatement() >> end");
        }
    }

    /**
     * Execute given statement and return results.
     *
     * @param ct
     * @param outputIndexes
     * @throws SQLException
     */
    private List<DBResult> executeStatement(CallableStatement ct, List<Integer> outputIndexes)
    	throws SQLException {

        LOG.debug("DBAccessDAO:executeStatement(ct, outputIndexes) >> start");

        try {

            List<DBResult> resultList = new ArrayList<DBResult>();

            if (CollectionUtilities.hasContent(outputIndexes)) {
            	extractResultFromOutParameter(ct, outputIndexes, resultList);
            } else {
                extractResultAsReturned(ct, resultList);
            }

            LOG.debug("Number of result objects from db call: " + resultList.size());

            return resultList;

        } finally {
            LOG.debug("DBAccessDAO:executeStatement(ct, outputIndexes) >> end");
        }
    }

    private void extractResultAsReturned(CallableStatement ct, List<DBResult> resultList)
            throws SQLException {
        ResultSet rs = null;

        boolean resultSetExists = ct.execute();

        if (resultSetExists) {
            do {
                try {
                    rs = ct.getResultSet();
                    DBResult dbResult = processResultSet(rs);
                    resultList.add(dbResult);
                } finally {
                    closeDBResources(rs, null, null);
                }
            } while (ct.getMoreResults(Statement.CLOSE_CURRENT_RESULT));
        }
    }

    private void extractResultFromOutParameter(CallableStatement ct,
            List<Integer> outputIndexes, List<DBResult> resultList)
            throws SQLException {
        ResultSet rs = null;

        ct.execute();

        try {
            for (int outputParamIndex : outputIndexes) {
                rs = (ResultSet) ct.getObject(outputParamIndex);
                DBResult dbResult = processResultSet(rs);
                resultList.add(dbResult);
                rs.close();
            }
        } finally {
            closeDBResources(rs, null, null);
        }
    }

    /**
     * Digest result set.
     *
     * @param rs result set
     * @return results in object form
     * @throws SQLException
     */
    private DBResult processResultSet(ResultSet rs) throws SQLException {

    	LOG.debug("DBAccessDAO:processResultSet() >> start");

    	try {

    		DBResult dbResult = new DBResult();
    		ResultSetMetaData rsMetaData = rs.getMetaData();
    		int columnCount = rsMetaData.getColumnCount();
    		int rowCount = 0;

    		while (rs.next()) {

    		    rowCount++;

    			for (int i = 1; i <= columnCount; i++) {

    				String columnName = rsMetaData.getColumnName(i);
    				String columnTypeName = rsMetaData.getColumnTypeName(i);
    				Object resultValue = rs.getObject(i);
    				dbResult.addResult(rowCount, columnName, columnTypeName,
    					resultValue);
    			}
    		}

    		dbResult.setRowsAffected(rowCount);
    		LOG.debug("Number rows fetched: " + rowCount);

    		return dbResult;

    	} finally {
    		LOG.debug("DBAccessDAO:processResultSet() >> end");
    	}
    }

    /**
	 * @return the _jdbcConnectionURL
	 */
	public String getJDBCConnectionURL() {
		return _jdbcConnectionURL;
	}

	/**
	 * @param connectionURL the _jdbcConnectionURL to set
	 */
	public void setJDBCConnectionURL(String connectionURL) {

		_jdbcConnectionURL = connectionURL;

		if (StringUtilities.isEmpty(connectionURL)) {
			throw new IllegalArgumentException(
				"JDBC compliant connection URL is required");
		}
	}

    /**
	 * @return the _jdbcDriverClassName
	 */
	public String getJDBCDriverClassName() {
		return _jdbcDriverClassName;
	}

	/**
	 * @param driverClassName the _jdbcDriverClassName to set
	 */
	public void setJDBCDriverClassName(String driverClassName) {

		_jdbcDriverClassName = driverClassName;

		if (StringUtilities.isEmpty(driverClassName)) {
			throw new IllegalArgumentException(
				"JDBC compliant driver class name is required");
		}
	}

	/**
	 * @return the _operationTimeoutInSeconds
	 */
	public int getOperationTimeoutInSeconds() {
		return _operationTimeoutInSeconds;
	}

	/**
	 * @param timeoutInSeconds the _operationTimeoutInSeconds to set
	 */
	public void setOperationTimeoutInSeconds(int timeoutInSeconds) {
		_operationTimeoutInSeconds = timeoutInSeconds;
	}

	/**
	 * @return the _operationType
	 */
	public int getOperationType() {
		return _operationType;
	}

	/**
	 * @param type the _operationType to set
	 */
	public void setOperationType(int type) {

		_operationType = type;

		boolean isValid = false;

		for (int operationType : SUPPORTED_OPERATION_TYPES) {

			if (operationType == type) {
				isValid = true;
				break;
			}
		}

		if (!isValid) {
			throw new IllegalArgumentException("Unsupported operation type: " + type);
		}
	}
	/**
	 * @return the _parameterList
	 */
	public List<DBParameter> getParameterList() {
		return _parameterList;
	}

	/**
	 * @param list the _parameterList to set
	 */
	public void setParameterList(List<DBParameter> list) {
		_parameterList = list;
	}

	/**
	 * @return the sqlString
	 */
	public String getSqlString() {
		return _sqlString;
	}

	/**
	 * @param sqlString the sqlString to set
	 */
	public void setSqlString(String sqlString) {

		_sqlString = sqlString;

		if (StringUtilities.isEmpty(sqlString)) {
			throw new IllegalArgumentException("JDBC compliant SQL string is required");
		}
	}

	/**
	 * @return the _statementType
	 */
	public int getStatementType() {
		return _statementType;
	}

	/**
	 * @param type the _statementType to set
	 */
	public void setStatementType(int type) {

		_statementType = type;

		boolean isValid = false;

		for (int statementType : SUPPORTED_STATEMENT_TYPES) {

			if (statementType == type) {
				isValid = true;
				break;
			}
		}

		if (!isValid) {
			throw new IllegalArgumentException("Unsupported statement type: " + type);
		}
	}
}
