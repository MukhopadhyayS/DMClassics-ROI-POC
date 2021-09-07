/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Stores result information from a database query or update.
 * 
 * @author Ronnie Andrews, Jr + BP Stieffen.
 */
public class DBResult {
	
	/**
	 * Delimiter for key string.
	 */
	private static final String DELIMITER = ":";
	
	/**
     * Object represents the Log4JWrapper object.
     */
    protected static final OCLogger LOG = new OCLogger(DBResult.class);
    
    /**
     * List of column names used so far.
     */
    private List<String> _resultColumnNames;
    
    /**
     * List of column types used so far.
     */
    private List<String> _resultColumnTypes;
	
	/**
	 * Number of rows fetched or updated.
	 */
	private int _rowsAffected;
	
	/**
	 * Result hash map.
	 */
	private Map<String, Object> _resultMap;

	/**
	 * Constructor with rows affected set to zero.
	 */
	public DBResult() {
		
		this(0);
		LOG.debug("DBResult:DBResult() >> end");
	}
	
	/**
	 * Constructor with rows affected set to given value.
	 * 
	 * @param rowsAffected number of rows fetched or updated
	 */
	public DBResult(int rowsAffected) {
		
		LOG.debug("DBResult:DBResult(int rowsAffected) >> start");
		
		try {			
			setRowsAffected(rowsAffected);			
		} finally {	    	
	    	LOG.debug("DBResult:DBResult(int rowsAffected) >> end");
	    }
	}
	
	/**
	 * Add result.
	 * 
	 * @param rowIndex index starting with 1
	 * @param columnName column name
	 * @param columnTypeName column type
	 * @param resultValue actual result value
	 */
	public void addResult(int rowIndex, String columnName, String columnTypeName, 
		Object resultValue) {
		
		LOG.debug("DBResult:addResult >> start");
		
		try {			
			
			String mapKey = rowIndex + DELIMITER + columnName;
			
			if (_resultMap == null) {
				_resultMap = new HashMap<String, Object>();
			}
			
			_resultMap.put(mapKey, resultValue);
			
			if (_resultColumnNames == null) {
				_resultColumnNames = new ArrayList<String>();
				_resultColumnTypes = new ArrayList<String>();
			}
			
			if (!_resultColumnNames.contains(columnName)) {
				_resultColumnNames.add(columnName);
				_resultColumnTypes.add(columnTypeName);
			}
			
		} finally {	    	
	    	LOG.debug("DBResult:addResult >> end");
	    }	
	}

	/**
	 * Get result.
	 * 
	 * @param rowIndex index starting with 1
	 * @param columnName column name
	 * @return resultValue actual result value
	 */
	public Object getResult(int rowIndex, String columnName) {
		
		LOG.debug("DBResult:getResult >> start");
		
		try {	
			
			if (_resultMap == null) {
				throw new IllegalStateException(
					"Trying to getResult when no results are present");
			}
			
			String mapKey = rowIndex + DELIMITER + columnName;
			Object resultValue = _resultMap.get(mapKey);
			return resultValue;
			
		} finally {	    	
	    	LOG.debug("DBResult:getResult >> end");
	    }	
	}
	
	/**
	 * @return the _resultColumnNames
	 */
	public List<String> getResultColumnNames() {
		return _resultColumnNames;
	}
	
	/**
	 * @return the _resultColumnTypes
	 */
	public List<String> getResultColumnTypes() {
		return _resultColumnTypes;
	}

	/**
	 * @return the _rowsAffected
	 */
	public int getRowsAffected() {
		return _rowsAffected;
	}

	/**
	 * Increment rows affected counter.
	 */
	public void incrementRowsAffected() {
		_rowsAffected++;
	}
	
	/**
	 * @param affected the _rowsAffected to set
	 */
	public void setRowsAffected(int affected) {
		_rowsAffected = affected;
	}

	/**
	 * @return the _resultMap
	 */
	public Map<String, Object> getResultMap() {
		return _resultMap;
	}
}
