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
public interface ParameterAssigner<T>
{
	/**
	 * @param ps
	 * @param parameterIndex
	 * @throws SQLException
	 */
	void addParameter(PreparedStatement ps, int parameterIndex) throws SQLException;

	/**
	 * @return value
	 */
	T getValue();

	/**
	 * @return int sql type found in <code>java.sql.SQLTypes</code>
	 */
	int getSqlType();
}
