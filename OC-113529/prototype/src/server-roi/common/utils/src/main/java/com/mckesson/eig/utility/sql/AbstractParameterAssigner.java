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

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Michael Macaluso
 */
public abstract class AbstractParameterAssigner<T> implements ParameterAssigner<T>
{
	private final int _sqlType;

	private final T _value;

	/**
	 * @param sqlType
	 * @param value
	 */
	public AbstractParameterAssigner(int sqlType, T value)
	{
		_sqlType = sqlType;
		_value = value;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.ParameterAssigner#addParameter(java.sql.PreparedStatement, int)
	 */
	public void addParameter(PreparedStatement ps, int parameterIndex) throws SQLException
	{
		if (null == _value)
		{
			ps.setNull(parameterIndex, _sqlType);
		}
		else
		{
			addParameterFromNonNullValue(ps, parameterIndex, _value);
		}
	}

	/**
	 * @param ps
	 * @param parameterIndex
	 * @param value
	 */
	protected abstract void addParameterFromNonNullValue(PreparedStatement ps, int parameterIndex, T value) throws SQLException;

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.ParameterAssigner#getSqlType()
	 */
	public int getSqlType()
	{
		return _sqlType;
	}

	/* (non-Javadoc)
	 * @see com.mckesson.eig.utility.sql.ParameterAssigner#getValue()
	 */
	public T getValue()
	{
		return _value;
	}

	@Override
	public String toString()
	{
		return "Parameter [_sqlType=" + _sqlType + ",_value=" + _value + "]";
	}
}
