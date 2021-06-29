/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.database;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.List;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.BooleanUtilities;
import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * DTO for a database parameter.
 */
public class DBParameter {

	//defined object types
	public static final int   BOOLEAN_OBJECT_TYPE 	= Types.BOOLEAN;
	public static final int   CHAR_OBJECT_TYPE 		= Types.CHAR;
	public static final int   DATE_OBJECT_TYPE 		= Types.DATE;
	public static final int   DOUBLE_OBJECT_TYPE 	= Types.DOUBLE;
	public static final int   FLOAT_OBJECT_TYPE 	= Types.FLOAT;
    public static final int   INTEGER_OBJECT_TYPE   = Types.INTEGER;
    public static final int   LONG_OBJECT_TYPE   	= Types.BIGINT;
	public static final int   NULL_OBJECT_TYPE 		= -1;
	public static final int   OTHER_OBJECT_TYPE 	= Types.OTHER;
    public static final int   VARCHAR_OBJECT_TYPE   = Types.VARCHAR;

    public static final int   ORACLE_CURSOR_OBJECT_TYPE = -10;

    static {
    	// This code removes the dependency on the oracle jar.  -10 is the
    	// value but if they happen to change it, then this will warn us.
    	try {
    		Class<?> classOracleTypes = Class.forName("oracle.jdbc.OracleTypes");
    		Field fieldCURSOR = classOracleTypes.getField("CURSOR");
    		int cursorObjectType = fieldCURSOR.getInt(null);
    		if (cursorObjectType != ORACLE_CURSOR_OBJECT_TYPE) {
    			Log log = LogFactory.getLogger(DBParameter.class);
    			log.error("oracle.jdbc.OracleTypes.CURSOR value has been changed from its original value of " + ORACLE_CURSOR_OBJECT_TYPE, new IllegalStateException());
    		}
    	} catch (Exception e) {
    	}
    }

	public static final int[] SUPPORTED_DATA_TYPES = new int[]{BOOLEAN_OBJECT_TYPE,
            CHAR_OBJECT_TYPE, DATE_OBJECT_TYPE, DOUBLE_OBJECT_TYPE, FLOAT_OBJECT_TYPE,
            INTEGER_OBJECT_TYPE, LONG_OBJECT_TYPE, NULL_OBJECT_TYPE, OTHER_OBJECT_TYPE,
            ORACLE_CURSOR_OBJECT_TYPE, VARCHAR_OBJECT_TYPE};

	/**
	 * Delimiter for parsing input string.
	 */
	private static final String DELIMITER = ":";

	/**
	 * Substitution prefix.
	 */
	private static final String SUBSTITUTION_PREFIX = "${";

	/**
	 * Substitution suffix.
	 */
	private static final String SUBSTITUTION_SUFFIX = "}";

	/**
     * Object represents the Log4JWrapper object.
     */
    protected static final Log LOG = LogFactory.getLogger(DBParameter.class);

	/**
	 * Parameter parameter object type.
	 */
	private int _parameterObjectType;

	/**
	 * Object value of the parameter.
	 */
	private Object _parameterObjectValue;

	/**
	 * Actual value for parameter.
	 */
	private String _parameterValue;

	/**
	 * True if this parameter is an output parameter.
	 */
	private boolean _resultParameter;

	/**
	 * Parse given string into required parameters.
	 *
	 * @param arguments
	 */
	public DBParameter(String arguments) {

		LOG.debug("DBParameter:DBParameter >> start");

		try {

			parseArguments(arguments);

		} finally {

	    	LOG.debug("DBParameter:DBParameter >> end");
	    }
	}

	/**
	 * True if given value is used for replacement.
	 *
	 * @param value
	 * @return True if a substitute value
	 */
	public boolean isValueASubstitutionVariable() {

		LOG.debug("DBParameter:isValueASubstitutionVariable >> start");

		try {

			if (!getParameterValue().startsWith(SUBSTITUTION_PREFIX)) {
				return false;
			}

			if (!getParameterValue().endsWith(SUBSTITUTION_SUFFIX)) {
				return false;
			}

			return true;

		} finally {

	    	LOG.debug("DBParameter:isValueASubstitutionVariable >> end");
	    }
	}

	/**
	 * Parsed String representing the content of the substitution variable.
	 *
	 * @return value without substitution indicator
	 */
	public String getParsedSubstitutionVariable() {

		LOG.debug("DBParameter:getParsedSubstitutionVariable >> start");

		try {

			if (!isValueASubstitutionVariable()) {
				throw new IllegalArgumentException(
					"Paramter must be subsitution variable to "
					+ "perform substitution parsing");
			}

			String value = getParameterValue().substring(2,
				getParameterValue().length() - 1);
			LOG.debug("Parsed substitution value: " + value);

			return value;

		} finally {

	    	LOG.debug("DBParameter:getParsedSubstitutionVariable >> end");
	    }
	}

    /**
     * Parses the incoming data from JBPM into member variables.
     *
     * @param arguments from JBPM
     */
	private void parseArguments(String arguments) {

		LOG.debug("DBParameter:parseArguments >> start");

		try {

			List<String> tokens = StringUtilities.extractTokens(arguments, DELIMITER);
		    setParameterObjectType(Integer.parseInt(tokens.get(0)));
		    setParameterValue(tokens.get(1));
		    setResultParameter(BooleanUtilities.valueOf(tokens.get(2)));

		    LOG.debug("Parsed arguments are: " + toString());

		} finally {

	    	LOG.debug("DBParameter:parseArguments >> end");
	    }
	}

	/**
	 * Determine if the given data type is valid.
	 *
	 * @param parameterDataType data type
	 * @return true if valid
	 */
	private boolean isDataTypeValid(int parameterDataType) {

		LOG.debug("DBParameter:isDataTypeValid >> start");

		try {

			boolean isValid = false;

			for (int dataType : SUPPORTED_DATA_TYPES) {

				if (dataType == parameterDataType) {
					isValid = true;
					break;
				}
			}

			return isValid;

		} finally {

	    	LOG.debug("DBParameter:isDataTypeValid >> end");
	    }
	}

	/**
	 * Display parameter's member variables.
	 */
	@Override
	public String toString() {
	    return "Object Type = [" + _parameterObjectType + "], Value = [" + _parameterValue
                + "], Is Result Parameter? = [" + _resultParameter + "]";
	}

	/**
	 * Create object from given value.
	 *
	 * @param value
	 */
	private void createParameterDataObject(String value) {

		LOG.debug("DBParameter:createParameterDataObject >> start");

		try {

			if ((_parameterObjectType == CHAR_OBJECT_TYPE)
				&& (value.length() != 1)) {

				throw new IllegalArgumentException(
					"char parameters must contain only one character");
			}

			_parameterObjectValue = getMatchingDataObject(value);

		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Unable to create object from given parameter", e);
		} finally {
			LOG.debug("DBParameter:createParameterDataObject >> end");
		}
	}

	/**
	 * Created to avoid Checkstyle complexity warnings.
	 *
	 * @param value
	 * @return
	 */
	private Object getMatchingDataObject(String value) {

		LOG.debug("DBParameter:getMatchingDataObject >> start");

		Object obj = null;

		switch(_parameterObjectType) {

			case BOOLEAN_OBJECT_TYPE:
				obj = BooleanUtilities.valueOf(value);
				break;

			case CHAR_OBJECT_TYPE:
				obj = value.charAt(0);
				break;

			case DATE_OBJECT_TYPE:
				obj = DateUtilities.parseISO8601(value);
				break;

			case DOUBLE_OBJECT_TYPE:
				obj = Double.parseDouble(value);
				break;

			case FLOAT_OBJECT_TYPE:
				obj = Float.parseFloat(value);
				break;

			case INTEGER_OBJECT_TYPE:
				obj = Integer.parseInt(value);
				break;

			case LONG_OBJECT_TYPE:
				obj = Long.parseLong(value);
				break;

			case VARCHAR_OBJECT_TYPE:
				obj = value;
				break;

			case ORACLE_CURSOR_OBJECT_TYPE:
				break;

			default:
				throw new IllegalArgumentException(
						"Invalid parameter object type: " + _parameterObjectType);
		}

		LOG.debug("DBParameter:getMatchingDataObject >> end");

		return obj;
	}

	/**
	 * Get parameter object type.
	 *
	 * @return the _parameterObjectType
	 */
	public int getParameterObjectType() {
		return _parameterObjectType;
	}

	/**
	 * Set parameter object type.
	 *
	 * @param objectType the _parameterObjectType to set
	 */
	public void setParameterObjectType(int objectType) {

		_parameterObjectType = objectType;

		if (!isDataTypeValid(objectType)) {
			throw new IllegalArgumentException(
					"Unsupported parameter object data type: " + objectType);
		}
	}

	/**
	 * Get parameter object value.
	 *
	 * @return the _parameterObjectValue
	 */
	public Object getParameterObjectValue() {
		return _parameterObjectValue;
	}

	/**
	 * Set parameter object value.
	 *
	 * @param objectValue the _parameterObjectValue to set
	 */
	public void setParameterObjectValue(Object objectValue) {
		_parameterObjectValue = objectValue;
	}

	/**
	 * Get parameter value.
	 *
	 * @return the _parameterValue
	 */
	public String getParameterValue() {
		return _parameterValue;
	}

	/**
	 * Set parameter value.
	 *
	 * @param value the _parameterValue to set
	 */
	public void setParameterValue(String value) {

		_parameterValue = value;

		if (!isValueASubstitutionVariable()) {
			createParameterDataObject(value);
		}
	}

	/**
	 * True if this parameter is an output parameter.
	 *
	 * @return the _resultParameter
	 */
	public boolean isResultParameter() {
		return _resultParameter;
	}

	/**
	 * Sets if this paramter is an output parameter.
	 *
	 * @param parameter the _resultParameter to set
	 */
	public void setResultParameter(boolean parameter) {
		_resultParameter = parameter;
	}
}
