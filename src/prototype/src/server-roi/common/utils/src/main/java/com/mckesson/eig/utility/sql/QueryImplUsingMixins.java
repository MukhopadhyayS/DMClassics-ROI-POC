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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author Michael Macaluso
 */
public abstract class QueryImplUsingMixins<T> extends QueryImpl<T>
{

	/**
	 *
	 */
	public QueryImplUsingMixins()
	{
	}

	@Override
	protected void initialize(StringBuilder queryStringBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap) throws Exception
	{
		Collection<QueryMixin> queryMixins = new ArrayList<QueryMixin>();

		buildQueryMixins(queryMixins, parameterMap);

		StringBuilder selectStringBuilder = new StringBuilder(1024);
		StringBuilder fromStringBuilder = new StringBuilder(1024);
		StringBuilder whereStringBuilder = new StringBuilder(1024);
		StringBuilder indexHintStringBuilder = new StringBuilder(128);
		StringBuilder orderByStringBuilder = new StringBuilder(128);
		for (QueryMixin queryMixin : queryMixins)
		{
			queryMixin.addArtifactsForSelectClause(selectStringBuilder, parameterAssigners, parameterMap);
			queryMixin.addArtifactsForFromClause(fromStringBuilder, parameterAssigners, parameterMap);
			queryMixin.addArtifactsForWhereClause(whereStringBuilder, parameterAssigners, parameterMap);
			queryMixin.addArtifactsForIndexHintClause(indexHintStringBuilder, parameterAssigners, parameterMap);
			queryMixin.addArtifactsForOrderByClause(orderByStringBuilder, parameterAssigners, parameterMap);
		}

		queryStringBuilder
			.append("SELECT").append(QuerySQLConstants.NEWLINE);

		if (indexHintStringBuilder.length() > 0)
		{
			queryStringBuilder
				.append(indexHintStringBuilder);
		}

		queryStringBuilder
			.append(selectStringBuilder)
			.append("FROM").append(QuerySQLConstants.NEWLINE)
			.append(fromStringBuilder)
			.append("WHERE").append(QuerySQLConstants.NEWLINE)
			.append(whereStringBuilder);

		if (orderByStringBuilder.length() > 0)
		{
			queryStringBuilder
				.append("ORDER BY").append(QuerySQLConstants.NEWLINE)
				.append(orderByStringBuilder);
		}
	}

	/**
	 * @param queryMixins
	 * @param parameterMap
	 */
	protected abstract void buildQueryMixins(Collection<QueryMixin> queryMixins, Map<String, Object> parameterMap);
}
