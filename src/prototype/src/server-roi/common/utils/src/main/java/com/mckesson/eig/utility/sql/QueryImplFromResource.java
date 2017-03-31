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

import java.util.Collection;
import java.util.Map;
import java.util.MissingResourceException;

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Michael Macaluso
 */
public abstract class QueryImplFromResource<T> extends QueryImpl<T>
{
	/**
	 * class to use as the base for the name as well as the class loader.
	 */
	private final Class<?> _baseClass;

	/**
	 * componentType property
	 */
	private final String _componentType;

	/**
	 * @param baseClass
	 * @param componentType
	 */
	public QueryImplFromResource(Class<?> baseClass, String componentType)
	{
		_baseClass = baseClass;
		_componentType = componentType;
	}

	/**
	 * @return the baseClass
	 */
	public final Class<?> getBaseClass()
	{
		return _baseClass;
	}

	/**
	 * @return the componentType
	 */
	public final String getComponentType()
	{
		return _componentType;
	}

	@Override
	protected void initialize(StringBuilder queryStringBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap) throws Exception
	{
		SQLFragment sqlFragment = SQLFragmentReader.DEFAULT.getSQLFragment(_baseClass, _componentType);
		if (null == sqlFragment)
		{
			throw new MissingResourceException("Missing sql resource file", _baseClass.getName(), _componentType);
		}

		String sqlString = sqlFragment.getSqlString();
		if (StringUtilities.isEmpty(sqlString))
		{
			throw new IllegalArgumentException("SQL Resource does not contain any meaningful data (" + _baseClass.getName() + ", " + _componentType +")");
		}

		SQLUtilities.processParameters(queryStringBuilder, sqlString, sqlFragment.getSqlFragmentParameters(), parameterAssigners, parameterMap);
	}
}
