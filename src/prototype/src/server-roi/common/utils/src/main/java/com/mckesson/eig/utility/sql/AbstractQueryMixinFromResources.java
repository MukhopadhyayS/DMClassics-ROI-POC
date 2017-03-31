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

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Michael Macaluso
 */
public abstract class AbstractQueryMixinFromResources extends AbstractQueryMixin
{

	public AbstractQueryMixinFromResources()
	{
	}

	@Override
	public void addArtifactsForSelectClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap)
	{
		processFragment(clauseBuilder, parameterAssigners, parameterMap, "Select");
	}

	@Override
	public void addArtifactsForFromClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap)
	{
		processFragment(clauseBuilder, parameterAssigners, parameterMap, "From");
	}

	@Override
	public void addArtifactsForWhereClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap)
	{
		processFragment(clauseBuilder, parameterAssigners, parameterMap, "Where");
	}

	@Override
	public void addArtifactsForIndexHintClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap)
	{
		processFragment(clauseBuilder, parameterAssigners, parameterMap, "IndexHint");
	}

	@Override
	public void addArtifactsForOrderByClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap)
	{
		processFragment(clauseBuilder, parameterAssigners, parameterMap, "OrderBy");
	}

	/**
	 * @param clauseBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 * @param componentType
	 */
	protected void processFragment(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap, String componentType)
	{
		try
		{
			SQLFragment fragment = getSQLFragment(componentType);
			if (null == fragment)
			{
				return;
			}

			String sqlString = fragment.getSqlString();
			if (StringUtilities.isEmpty(sqlString))
			{
				return;
			}

			SQLUtilities.processParameters(clauseBuilder, sqlString, fragment.getSqlFragmentParameters(), parameterAssigners, parameterMap);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param componentType
	 * @return SQLFragment
	 * @throws IOException
	 */
	protected SQLFragment getSQLFragment(String componentType) throws IOException
	{
		return SQLFragmentReader.DEFAULT.getSQLFragment(getClass(), componentType);
	}
}
