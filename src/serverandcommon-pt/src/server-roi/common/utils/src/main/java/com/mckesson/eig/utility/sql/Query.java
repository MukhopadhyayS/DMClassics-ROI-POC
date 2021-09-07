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
import java.util.Collection;
import java.util.List;

/**
 * @author Michael Macaluso
 */
public interface Query<T>
{

	/**
	 * Default maximum number of seconds that a qeruy should take before being cancelled.
	 */
	public static final int DEFAULT_QUERY_TIMEOUT = 90;

	/**
	 * @param maxResults
	 * @throws Exception
	 */
	void initialize(int maxResults) throws Exception;

	/**
	 * @return the queryID
	 */
	String getQueryID();

	/**
	 * @return the queryString
	 */
	String getQueryString();

	/**
	 * @return the maxResults
	 */
	int getMaxResults();

	/**
	 * @return the fetchSize
	 */
	int getFetchSize();

	/**
	 * @param fetchSize
	 */
	void setFetchSize(int fetchSize);

	/**
	 * @return the parameterAssigners
	 */
	Collection<ParameterAssigner<?>> getParameterAssigners();

	/**
	 * @param conn
	 * @return List<T>
	 * @throws Exception
	 */
	List<T> execute(Connection conn) throws Exception;

	/**
	 * @param conn
	 * @param timeoutInSeconds
	 * @return List<T>
	 * @throws Exception
	 */
	List<T> execute(Connection conn, int timeoutInSeconds) throws Exception;
}