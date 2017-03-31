/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author Michael Macaluso
 */
public abstract class QueryImpl<T> implements Query<T>
{

	private final String _queryID;

	private int _maxResults;

	private int _fetchSize;

	private String _queryString;

	private Collection<ParameterAssigner<?>> _parameterAssigners;

	public QueryImpl()
	{
		_queryID = UUID.randomUUID().toString();
		_fetchSize = 100;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#initialize(int)
	 */
	public void initialize(int maxResults) throws Exception
	{
		_maxResults = maxResults;

		Map<String, Object> parameterMap = buildParameterMap();

		Collection<ParameterAssigner<?>> parameterAssigners = new ArrayList<ParameterAssigner<?>>(30);

		StringBuilder queryStringBuilder = new StringBuilder();

		initialize(queryStringBuilder, parameterAssigners, parameterMap);

		_queryString = queryStringBuilder.toString();
		_parameterAssigners = parameterAssigners;
	}

	/**
	 * @return map of parameter name to parameter value
	 */
	protected Map<String, Object> buildParameterMap()
	{
		Map<String, Object> parameterMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
		parameterMap.put(SQLFragmentParameterNames.MaximumResults, new Long(_maxResults));
		parameterMap.put(SQLFragmentParameterNames.QueryId, _queryID);
		return parameterMap;
	}

	/**
	 * @param queryStringBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 * @throws Exception
	 */
	protected abstract void initialize(StringBuilder queryStringBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap) throws Exception;

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#getQueryID()
	 */
	public String getQueryID()
	{
		return _queryID;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#getQueryString()
	 */
	public String getQueryString()
	{
		return _queryString;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#getMaxResults()
	 */
	public int getMaxResults()
	{
		return _maxResults;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#getFetchSize()
	 */
	public int getFetchSize()
	{
		return _fetchSize;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#setFetchSize(int)
	 */
	public void setFetchSize(int fetchSize)
	{
		_fetchSize = fetchSize;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#getParameterAssigners()
	 */
	public Collection<ParameterAssigner<?>> getParameterAssigners()
	{
		return _parameterAssigners;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#execute(java.sql.Connection)
	 */
	public List<T> execute(Connection conn) throws Exception
	{
		return execute(conn, DEFAULT_QUERY_TIMEOUT);
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.Query#execute(java.sql.Connection, int)
	 */
	public List<T> execute(Connection conn, int timeoutInSeconds) throws Exception
	{
		List<T> results = createResultList(_maxResults);

		execute(conn, timeoutInSeconds, results);

		return results;
	}

	/**
	 * @param conn
	 * @param timeoutInSeconds
	 * @param remoteObjs
	 * @throws SQLException
	 */
	private void execute(Connection conn, int timeoutInSeconds, List<T> results) throws SQLException
	{
		int maxRows = getMaxResults();
		long millisBefore = System.currentTimeMillis();
		PreparedStatement preparedStatement = createPreparedStatement(conn, _queryString);
		try
		{
			setParameters(preparedStatement, _queryString, _parameterAssigners, 1);
			setQueryTimeout(preparedStatement, timeoutInSeconds);
			setMaxRows(preparedStatement, maxRows);
			setFetchSize(preparedStatement, _fetchSize);

			onPreExecute(preparedStatement, _queryString, _parameterAssigners, timeoutInSeconds, maxRows);
			millisBefore = System.currentTimeMillis();
			ResultSet resultSet = executeQuery(preparedStatement);
			long executeTimeMillis = System.currentTimeMillis() - millisBefore;
			onPostExecute(preparedStatement, _queryString, _parameterAssigners, timeoutInSeconds, maxRows, executeTimeMillis);

			millisBefore = System.currentTimeMillis();
			int actualRows = 0;
			int maxRowsRemaining = maxRows;
			try
			{
				while (resultSet.next() && maxRowsRemaining > 0)
				{
					T result = bindResult(resultSet);
					results.add(result);
					maxRowsRemaining--;
					actualRows++;
				}
			}
			finally
			{
				SQLUtilities.silentlyClose(resultSet);
			}
			long bindTimeMillis = System.currentTimeMillis() - millisBefore;
			onPostBind(_queryString, _parameterAssigners, maxRows, actualRows, executeTimeMillis, bindTimeMillis);
		}
		catch (SQLException e)
		{
			long whenExceptionTimeMillis = System.currentTimeMillis() - millisBefore;
			onSQLException(preparedStatement, e, _queryString, _parameterAssigners, timeoutInSeconds, maxRows, whenExceptionTimeMillis);
		}
		finally
		{
			SQLUtilities.silentlyClose(preparedStatement);
		}
	}

	/**
	 * @param preparedStatement
	 * @param fetchSize
	 * @throws SQLException
	 */
	protected void setFetchSize(PreparedStatement preparedStatement, int fetchSize) throws SQLException
	{
		preparedStatement.setFetchSize(fetchSize);
	}

	/**
	 * @param maxSize
	 * @return List<T>
	 */
	protected List<T> createResultList(int maxSize)
	{
		return new ArrayList<T>(maxSize);
	}

	/**
	 * @param conn
	 * @param queryString
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	protected PreparedStatement createPreparedStatement(Connection conn, String queryString) throws SQLException
	{
		PreparedStatement preparedStatement = conn.prepareStatement(queryString);
		return preparedStatement;
	}

	/**
	 * @param preparedStatement
	 * @param queryString
	 * @param parameterAssigners
	 * @param parameterIndex
	 * @throws SQLException
	 */
	protected int setParameters(PreparedStatement preparedStatement, String queryString, Collection<ParameterAssigner<?>> parameterAssigners, int parameterIndex) throws SQLException
	{
		for (ParameterAssigner<?> parameterAssigner : parameterAssigners)
		{
			parameterAssigner.addParameter(preparedStatement, parameterIndex++);
		}

		return parameterIndex;
	}

	/**
	 * @param preparedStatement
	 * @param timeoutInSeconds
	 * @throws SQLException
	 */
	protected void setQueryTimeout(PreparedStatement preparedStatement, int timeoutInSeconds) throws SQLException
	{
		preparedStatement.setQueryTimeout(timeoutInSeconds);
	}

	/**
	 * @param preparedStatement
	 * @param maxRows
	 * @throws SQLException
	 */
	protected void setMaxRows(PreparedStatement preparedStatement, int maxRows) throws SQLException
	{
		preparedStatement.setMaxRows(maxRows);
	}

	/**
	 * @param preparedStatement
	 * @param parameterAssigners
	 * @param queryString
	 * @return ResultSet
	 * @throws SQLException
	 */
	protected ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException
	{
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet;
	}

	/**
	 * @param resultSet
	 * @return T
	 * @throws SQLException
	 */
	protected abstract T bindResult(ResultSet resultSet) throws SQLException;

	/**
	 * @param preparedStatement
	 * @param queryString
	 * @param parameterAssigners
	 * @param timeoutInSeconds
	 * @param maxRows
	 */
	protected void onPreExecute(PreparedStatement preparedStatement, String queryString, Collection<ParameterAssigner<?>> parameterAssigners, int timeoutInSeconds, int maxRows)
	{
	}

	/**
	 * @param preparedStatement
	 * @param queryString
	 * @param parameterAssigners
	 * @param timeoutInSeconds
	 * @param maxRows
	 * @param beforeMillis
	 * @param afterMillis
	 */
	protected void onPostExecute(PreparedStatement preparedStatement, String queryString, Collection<ParameterAssigner<?>> parameterAssigners, int timeoutInSeconds, int maxRows, long executeTimeMillis)
	{
	}

	/**
	 * @param queryString
	 * @param parameterAssigners
	 * @param maxRows
	 * @param actualRows
	 * @param millisBefore
	 * @param currentTimeMillis
	 */
	protected void onPostBind(String queryString, Collection<ParameterAssigner<?>> parameterAssigners, int maxRows, int actualRows, long executeTimeMillis, long bindTimeMillis)
	{
	}

	/**
	 * @param preparedStatement
	 * @param e
	 * @param queryString
	 * @param parameterAssigners
	 * @param timeoutInSeconds
	 * @param maxRows
	 * @throws SQLException
	 */
	protected void onSQLException(PreparedStatement preparedStatement, SQLException e, String queryString, Collection<ParameterAssigner<?>> parameterAssigners, int timeoutInSeconds, int maxRows, long whenExceptionTimeMillis) throws SQLException
	{
		throw e;
	}
}