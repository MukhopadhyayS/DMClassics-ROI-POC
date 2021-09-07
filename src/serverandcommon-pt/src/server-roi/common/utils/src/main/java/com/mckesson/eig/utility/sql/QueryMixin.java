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

/**
 * @author Michael Macaluso
 */
public interface QueryMixin
{
	/**
	 * @param clauseBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 */
	void addArtifactsForSelectClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap);

	/**
	 * @param clauseBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 */
	void addArtifactsForFromClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap);

	/**
	 * @param clauseBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 */
	void addArtifactsForWhereClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap);

	/**
	 * @param clauseBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 */
	void addArtifactsForIndexHintClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap);

	/**
	 * @param clauseBuilder
	 * @param parameterAssigners
	 * @param parameterMap
	 */
	void addArtifactsForOrderByClause(StringBuilder clauseBuilder, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap);
}
