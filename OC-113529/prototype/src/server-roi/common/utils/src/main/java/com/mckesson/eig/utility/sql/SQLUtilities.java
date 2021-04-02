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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Michael Macaluso
 */
public class SQLUtilities
{

	private static final int PARAMETER_STRING_LENGTH = QuerySQLConstants.PARAMETER.length();

	/**
	 * @param parameterValue
	 * @return String
	 */
	public static String ConvertToLikeValue(String parameterValue)
	{
		if (null == parameterValue)
		{
			return null;
		}

		parameterValue = parameterValue.replace('*', '%');

		if (!parameterValue.endsWith("%"))
		{
			parameterValue = parameterValue + "%";
		}

		return parameterValue;
	}

	/**
	 * @param parameterValue
	 * @return String
	 */
	public static String ConvertToUpperCase(String parameterValue)
	{
		if (null == parameterValue)
		{
			return null;
		}

		parameterValue = parameterValue.toUpperCase();

		return parameterValue;
	}

	/**
	 * @param parameterValue
	 * @return String
	 */
	public static String ConvertToLowerCase(String parameterValue)
	{
		if (null == parameterValue)
		{
			return null;
		}

		parameterValue = parameterValue.toLowerCase();

		return parameterValue;
	}

	/**
	 * @param preparedStatement
	 */
	public static void silentlyClose(PreparedStatement preparedStatement)
	{
		try { preparedStatement.close(); } catch (Exception e) {}
	}

	/**
	 * @param resultSet
	 */
	public static void silentlyClose(ResultSet resultSet)
	{
		try { resultSet.close(); } catch (Exception e) {}
	}

	/**
	 * @param connection
	 */
	public static void silentlyClose(Connection connection)
	{
		try { connection.close(); } catch (Exception e) {}
	}

	/**
	 * @param sqlFragmentParameters
	 * @param parameterAssigners
	 * @param parameterMap
	 */
	public static void processParameters(StringBuilder clauseBuilder, String sqlString, SQLFragmentParameter[] sqlFragmentParameters, Collection<ParameterAssigner<?>> parameterAssigners, Map<String, Object> parameterMap)
	{
		if (null != sqlFragmentParameters && sqlFragmentParameters.length > 0)
		{
			int startIndex = 0;
			for (SQLFragmentParameter sqlFragmentParameter : sqlFragmentParameters)
			{
				String name = sqlFragmentParameter.getName();
				Object value = parameterMap.get(name);

				// first copy everything from the startIndex to this location
				int offset = sqlFragmentParameter.getOffset();
				clauseBuilder.append(sqlString.substring(startIndex, offset));

				if (isParameterInlined(sqlFragmentParameter))
				{
					// if here, then only place the inlined value
					String inlineString = buildModifiedString(value, sqlFragmentParameter.getModifiers());
					clauseBuilder.append(inlineString);
				}
				else
				{
					// if here, the do normal parameter work
					clauseBuilder.append(QuerySQLConstants.PARAMETER);

					ParameterAssigner<?> parameterAssigner = buildParameterAssigner(sqlFragmentParameter, value);
					if (null != parameterAssigner)
					{
						parameterAssigners.add(parameterAssigner);
					}
				}

				startIndex = offset + PARAMETER_STRING_LENGTH;
			}
			clauseBuilder.append(sqlString.substring(startIndex));
		}
		else
		{
			clauseBuilder.append(sqlString);
		}
	}

	/**
	 * An inlined parameter is to have the value placed in the sql instead of a normal parameter.  Why, do you
	 * ask?  Well it appears that the oracle pseudo-column ROWNUM is not adhered to in our testing when bound
	 * as a parameter.  Therefore, this mechanism is implemented to work around this feature.
	 *
	 * @param sqlFragmentParameter
	 * @return boolean
	 */
	public static boolean isParameterInlined(SQLFragmentParameter sqlFragmentParameter)
	{
		String modifiers = sqlFragmentParameter.getModifiers();
		if (StringUtilities.hasContent(modifiers))
		{
			String modifiersUppercase = modifiers.toUpperCase();
			if (modifiersUppercase.contains("INLINE"))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param sqlFragmentParameter
	 * @param value
	 * @return ParameterAssigner
	 */
	public static ParameterAssigner<?> buildParameterAssigner(SQLFragmentParameter sqlFragmentParameter, Object value)
	{
		ParameterAssigner<?> parameterAssigners = null;
		switch (sqlFragmentParameter.getType())
		{
			case STRING:
				parameterAssigners = buildParameterAssignerString(value, sqlFragmentParameter.getModifiers());
				break;

			case LONG:
				parameterAssigners = buildParameterAssignerNumber(value);
				break;

			case TIMESTAMP:
				parameterAssigners = buildParameterAssignerTimestamp(value);
				break;

			case DATE:
				parameterAssigners = buildParameterAssignerDate(value);

			default:
				break;
		}
		return parameterAssigners;
	}

	/**
	 * @param value
	 * @return ParameterAssigner<?>
	 */
	public static ParameterAssigner<?> buildParameterAssignerNumber(Object value)
	{
		return new ParameterAssignerNumber((Number) value);
	}

	/**
	 * @param value
	 * @return ParameterAssigner<?>
	 */
	public static ParameterAssigner<?> buildParameterAssignerTimestamp(Object value)
	{
		return new ParameterAssignerTimestamp((Timestamp) value);
	}

	/**
	 * @param value
	 * @return ParameterAssigner<?>
	 */
	public static ParameterAssigner<?> buildParameterAssignerDate(Object value)
	{
		Date valueAsDate;
		if (value instanceof Timestamp)
		{
			valueAsDate = new Date(((Timestamp) value).getTime());
		}
		else if (value instanceof Date)
		{
			valueAsDate = (Date) value;
		}
		else
		{
			// just assume good old java.util.Date instance.
			valueAsDate = new Date(((java.util.Date) value).getTime());
		}
		return new ParameterAssignerDate(valueAsDate);
	}

	/**
	 * @param value
	 * @param modifiers
	 * @return ParameterAssignerString
	 */
	public static ParameterAssigner<?> buildParameterAssignerString(Object value, String modifiers)
	{
		String valueString = buildModifiedString(value, modifiers);
		return new ParameterAssignerString(valueString);
	}

	/**
	 * @param value
	 * @param modifiers
	 * @return
	 */
	public static String buildModifiedString(Object value, String modifiers)
	{
		String valueString = value.toString();
		if (StringUtilities.hasContent(modifiers))
		{
			StringTokenizer tokenizer = new StringTokenizer(modifiers.toUpperCase(), ", ");
			while (tokenizer.hasMoreTokens())
			{
				String modifier = tokenizer.nextToken();
				valueString = modifyString(valueString, modifier);
			}
		}
		return valueString;
	}

	/**
	 * @param valueString
	 * @param modifierUpperCase
	 * @return modified (if needed) valueString
	 */
	public static String modifyString(String valueString, String modifierUpperCase)
	{
		if (modifierUpperCase.equals("LIKE"))
		{
			valueString = ConvertToLikeValue(valueString);
		}
		else if (modifierUpperCase.equals("UPPER"))
		{
			valueString = ConvertToUpperCase(valueString);
		}
		else if (modifierUpperCase.equals("LOWER"))
		{
			valueString = ConvertToLowerCase(valueString);
		}
		return valueString;
	}
}
