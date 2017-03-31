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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Michael Macaluso
 */
public class ParameterAssignerDate extends AbstractParameterAssigner<Date>
{

	/**
	 * @param value
	 */
	public ParameterAssignerDate(Date value)
	{
		super(Types.DATE, value);
	}

	@Override
	protected void addParameterFromNonNullValue(PreparedStatement ps, int parameterIndex, Date value) throws SQLException
	{
		ps.setDate(parameterIndex, value);
	}
}
