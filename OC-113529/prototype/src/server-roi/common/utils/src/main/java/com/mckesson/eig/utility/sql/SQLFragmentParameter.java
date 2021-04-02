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
public class SQLFragmentParameter
{
	private final String _name;

	private final SQLType _type;

	private final String _modifiers;

	private final int _offset;

	/**
	 * @param name
	 * @param type
	 * @param modifiers
	 * @param offset
	 */
	public SQLFragmentParameter(String name, SQLType type, String modifiers, int offset)
	{
		_name = name;
		_type = type;
		_modifiers = modifiers;
		_offset = offset;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return the type
	 */
	public SQLType getType()
	{
		return _type;
	}

	/**
	 * @return the modifiers
	 */
	public String getModifiers()
	{
		return _modifiers;
	}

	/**
	 * @return the offset
	 */
	public final int getOffset()
	{
		return _offset;
	}
}
