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

/**
 * @author Michael Macaluso
 */
public class SQLFragment
{
	private String _sqlString;

	private SQLFragmentParameter[] _sqlFragmentParameters;

	public SQLFragment()
	{
	}

	/**
	 * @return the sqlString
	 */
	public String getSqlString()
	{
		return _sqlString;
	}

	/**
	 * @param sqlString the sqlString to set
	 */
	public void setSqlString(String sqlString)
	{
		_sqlString = sqlString;
	}

	/**
	 * @return the sqlFragmentParameters
	 */
	public SQLFragmentParameter[] getSqlFragmentParameters()
	{
		return _sqlFragmentParameters;
	}

	/**
	 * @param sqlFragmentParameters the sqlFragmentParameters to set
	 */
	public void setSqlFragmentParameters(SQLFragmentParameter[] sqlFragmentParameters)
	{
		_sqlFragmentParameters = sqlFragmentParameters;
	}
}
