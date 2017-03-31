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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mckesson.eig.utility.io.IOUtilities;

/**
 * @author Michael Macaluso
 */
public class SQLFragmentReader
{
	private static final String PARAMETER_BEGIN = "${";

	private static final String PARAMETER_END = "}";

	public static final SQLFragmentReader DEFAULT = new SQLFragmentReader();

	protected final Map<String, SQLFragment> _sqlFragmentMap = Collections.synchronizedMap(new HashMap<String, SQLFragment>());

	public SQLFragmentReader()
	{
	}

	/**
	 * @parma clazz
	 * @param componentType
	 * @return SQLFragment
	 * @throws IOException
	 */
	public SQLFragment getSQLFragment(Class<?> clazz, String componentType) throws IOException
	{
		String key = clazz.getSimpleName() + "_" + componentType + ".sql";

		SQLFragment queryComponent = null;
		if (_sqlFragmentMap.containsKey(key))
		{
			queryComponent = _sqlFragmentMap.get(key);
		}
		else
		{
			queryComponent = read(clazz, key);
			_sqlFragmentMap.put(key, queryComponent);
		}

		return queryComponent;
	}

	/**
	 * @param queryComponentClass
	 * @param resourceName
	 * @return QueryComponent
	 * @throws IOException
	 */
	public SQLFragment read(Class<?> queryComponentClass, String resourceName) throws IOException
	{
		StringBuilder stringBuilder = new StringBuilder(1024);
		Collection<SQLFragmentParameter> queryComponentParameters = null;

		InputStream inputStream = queryComponentClass.getResourceAsStream(resourceName);
		if (null == inputStream)
		{
			return null;
		}
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			int lineNumber = 1;
			while (null != (line = bufferedReader.readLine()))
			{
				stringBuilder.append(QuerySQLConstants.INDENT);

				int lineStartIndex = 0;
				int indexOfParameterStart;
				while (-1 != (indexOfParameterStart = line.indexOf(PARAMETER_BEGIN, lineStartIndex)))
				{
					stringBuilder.append(line, lineStartIndex, indexOfParameterStart);

					int indexOfParamterEnd = line.indexOf(PARAMETER_END, lineStartIndex);
					String parameterString = line.substring(indexOfParameterStart + PARAMETER_BEGIN.length(), indexOfParamterEnd);
					int offset = stringBuilder.length();
					SQLFragmentParameter parameter = buildParameter(parameterString, resourceName, lineNumber, indexOfParameterStart, offset);
					if (null == queryComponentParameters)
					{
						queryComponentParameters = new ArrayList<SQLFragmentParameter>();
					}
					queryComponentParameters.add(parameter);

					stringBuilder.append('?');

					lineStartIndex = indexOfParamterEnd + PARAMETER_END.length();
				}
				stringBuilder.append(line.substring(lineStartIndex));

				stringBuilder.append(QuerySQLConstants.NEWLINE);

				lineNumber++;
			}
		}
		finally
		{
			IOUtilities.close(inputStream);
		}

		SQLFragment queryComponent = new SQLFragment();

		String sqlString = stringBuilder.toString();
		queryComponent.setSqlString(sqlString);

		if (null != queryComponentParameters)
		{
			SQLFragmentParameter[] parameters = queryComponentParameters.toArray(new SQLFragmentParameter[queryComponentParameters.size()]);
			queryComponent.setSqlFragmentParameters(parameters);
		}

		return queryComponent;
	}

	/**
	 * @param parameterString
	 * @param resourceName
	 * @param indexOfParameterStart
	 * @param lineNumber
	 * @param offset
	 * @return read parameter
	 */
	private SQLFragmentParameter buildParameter(String parameterString, String resourceName, int lineNumber, int indexOfParameterStart, int offset)
	{
		String[] parameterPieces = parameterString.split(":");

		String name = parameterPieces[0];

		SQLType type = null;
		if (parameterPieces.length > 1)
		{
			String typeString = parameterPieces[1];
			type = SQLType.valueOf(typeString.toUpperCase());
		}
		else
		{
			throw new IllegalArgumentException(resourceName + "(Line " + lineNumber + ", column " + indexOfParameterStart + "): Argument " + name + " does not define a type");
		}

		String modifiers = null;
		if (parameterPieces.length > 2)
		{
			modifiers = parameterPieces[2];
		}

		return new SQLFragmentParameter(name, type, modifiers, offset);
	}
}
