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

package com.mckesson.eig.workflow.engine.api.database;

import java.util.List;

import org.jbpm.graph.exe.ExecutionContext;

import com.mckesson.eig.utility.database.DBAccessDAO;
import com.mckesson.eig.utility.database.DBParameter;
import com.mckesson.eig.utility.database.DBResult;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.engine.BaseActionHandler;

/**
 * Executes a database query or stored procedure using runtime process variables.
 * 
 * @author Ronnie Andrews, Jr + BP Stieffen.
 */
public class DBActionHandler extends BaseActionHandler {
	
	/**
     * Object represents the Log4JWrapper object.
     */
    protected static final Log LOG = LogFactory.getLogger(DBActionHandler.class);
    
    /**
     * Connection URL to desired DB including username + password.
     */
    private String _jdbcConnectionURL;
    
    /**
     * Full class name of JDBC driver to use.
     */
    private String _jdbcDriverClassName;
    
    /**
     * Timeout for DB query.
     */
    private int _operationTimeoutInSeconds;
    
    /**
     * Operation type such as select=0, insert=1, update=2, or delete=3.
     */
    private int _operationType;
    
    /**
     * List of parameters for query or stored procedure.
     */
    private List<DBParameter> _parameterList;
    
    /**
     * List of names for result sets.
     */
    private List<String> _resultSetNames;
    
    /**
     * JDBC SQL string to execute.
     */
    private String _sqlString;
    
    /**
     * Statement type such as standard=0, prepared=1, and callable=2.
     */
    private int _statementType;

	/**
	 * Executes desired database query or stored procedure using runtime values.
	 */
	public void executeAction(ExecutionContext context) {
		
		LOG.debug("DBActionHandler:executeAction >> start");
		
		try {
			
			//populate will force validation via the DAO
			DBAccessDAO dbAccessDAO = DBAccessDAO.getInstance();
			populateDAO(dbAccessDAO, context);
		
			List<DBResult> resultList = dbAccessDAO.execute();
			packageResults(resultList, context);
            
            // Create process instance history
            createProcessInstanceHistory("Database Action", "DBActionHandler", "true"
                    , context);
		} catch (Exception e) {
	
	    	LOG.debug("DBActionHandler: executeAction failure. Exception was " + e.toString());
	    	throw new WorkflowEngineException(e);
	
	    } finally {
	    	
	    	LOG.debug("DBActionHandler:executeAction >> end");
	    }
	}

	/**
	 * Validate information obtained from process definition.
	 */
	public void validate() {
		
		LOG.debug("DBActionHandler:validate >> start");
		LOG.debug("All DBActionHandler validation is done through the populateDAO method");
		LOG.debug("DBActionHandler:validate >> end");
	}
	
	/**
	 * Populate DAO with required values.
	 * 
	 * @param dbAccessDAO
	 * @param context
	 */
	private void populateDAO(DBAccessDAO dbAccessDAO, ExecutionContext context) {
	
		LOG.debug("DBActionHandler:populateDAO >> start");
		
		try {
			
			dbAccessDAO.setJDBCConnectionURL(getJDBCConnectionURL());
			dbAccessDAO.setJDBCDriverClassName(getJDBCDriverClassName());
			dbAccessDAO.setOperationTimeoutInSeconds(getOperationTimeoutInSeconds());
			dbAccessDAO.setOperationType(getOperationType());
			dbAccessDAO.setParameterList(getParameterList());
			dbAccessDAO.setSqlString(getSqlString());
			dbAccessDAO.setStatementType(getStatementType());
			
			//perform subsitution on process variables
			if (CollectionUtilities.hasContent(dbAccessDAO.getParameterList())) {
				
				for (DBParameter dbParam : dbAccessDAO.getParameterList()) {
					
					if (dbParam.isValueASubstitutionVariable()) {

                        LOG.debug("Performing substitution value: " + dbParam.getParameterValue());
                        Object parameterObject = context.getVariable(dbParam
                                .getParsedSubstitutionVariable());

                        if (parameterObject == null) {
                            String msg = "No process variable found for: "
                                    + dbParam.getParameterValue();
                            LOG.error(msg);
                            throw new IllegalArgumentException(msg);
                        }

                        dbParam.setParameterObjectValue(parameterObject);
                    }
				}
			}
			
		} finally {
			LOG.debug("DBActionHandler:populateDAO >> end");
		}
	}
	
	/**
	 * Package results into process variables.
	 * 
	 * @param resultList
	 * @param context
	 */
	private void packageResults(List<DBResult> resultList, ExecutionContext context) {
		
		LOG.debug("DBActionHandler:packageResults >> start");
		
		try {
			
			if (resultList.size() != _resultSetNames.size()) {
				throw new WorkflowEngineException(
					"Number of result sets defined in process definition"
					+ "did not match actual from database", 
					WorkflowEC.INVALID_NUMBER_OF_RESULT_SETS);
			}
			
			//process all result sets
			for (int i = 0; i < resultList.size(); i++) {
				
				DBResult dbResult = resultList.get(i);
				
				LOG.debug("Number of rows affected for result set: " 
					+ dbResult.getRowsAffected());
				
				List<String> columnNames = dbResult.getResultColumnNames();
				List<String> columnTypes = dbResult.getResultColumnTypes();
				
				if (CollectionUtilities.hasContent(columnNames)) {
					
					for (int j = 1; j <= dbResult.getRowsAffected(); j++) {
						
						for (int k = 0; k < columnNames.size(); k++) {
							
							String colName = columnNames.get(k);
							String colType = columnTypes.get(k);
							Object resultValue = 
								dbResult.getResult(j, colName);
							
							long processId = getProcessId(context);
							String activityName = 
								getActivityName(context);
							
							StringBuffer processVariableKey = 
								new StringBuffer();
							processVariableKey.append(
								processId);
							processVariableKey.append(
								PROCESS_VARIABLE_DELIMITER);
							processVariableKey.append(
								activityName);
							processVariableKey.append(
								PROCESS_VARIABLE_DELIMITER);
							processVariableKey.append(
								_resultSetNames.get(i));
							processVariableKey.append(
								PROCESS_VARIABLE_DELIMITER);
							processVariableKey.append(
								colName);
							processVariableKey.append(
								PROCESS_VARIABLE_DELIMITER);
							processVariableKey.append(
								colType);
							processVariableKey.append(
								PROCESS_VARIABLE_DELIMITER);
							processVariableKey.append(
								j - 1);

							context.setVariable(
								processVariableKey.toString(), 
								resultValue);
						}
					}
				}
			}
		
		} finally {
			LOG.debug("DBActionHandler:packageResults >> end");
		}
	}

	/**
	 * @return the _jdbcConnectionURL
	 */
	public String getJDBCConnectionURL() {
		return _jdbcConnectionURL;
	}

	/**
	 * @param connectionURL the _jdbcConnectionURL to set
	 */
	public void setJDBCConnectionURL(String connectionURL) {
		_jdbcConnectionURL = connectionURL;
	}
    
    /**
	 * @return the _jdbcDriverClassName
	 */
	public String getJDBCDriverClassName() {
		return _jdbcDriverClassName;
	}

	/**
	 * @param driverClassName the _jdbcDriverClassName to set
	 */
	public void setJDBCDriverClassName(String driverClassName) {
		_jdbcDriverClassName = driverClassName;
	}

	/**
	 * @return the _operationTimeoutInSeconds
	 */
	public int getOperationTimeoutInSeconds() {
		return _operationTimeoutInSeconds;
	}

	/**
	 * @param timeoutInSeconds the _operationTimeoutInSeconds to set
	 */
	public void setOperationTimeoutInSeconds(int timeoutInSeconds) {
		_operationTimeoutInSeconds = timeoutInSeconds;
	}

	/**
	 * @return the _operationType
	 */
	public int getOperationType() {
		return _operationType;
	}

	/**
	 * @param type the _operationType to set
	 */
	public void setOperationType(int type) {
		_operationType = type;
	}
	/**
	 * @return the _parameterList
	 */
	public List<DBParameter> getParameterList() {
		return _parameterList;
	}

	/**
	 * @param list the _parameterList to set
	 */
	public void setParameterList(List<DBParameter> list) {
		_parameterList = list;
	}

	/**
	 * @return the _resultSetNames
	 */
	public List<String> getResultSetNames() {
		return _resultSetNames;
	}

	/**
	 * @param setNames the _resultSetNames to set
	 */
	public void setResultSetNames(List<String> setNames) {
		_resultSetNames = setNames;
	}

	/**
	 * @return the sqlString
	 */
	public String getSqlString() {
		return _sqlString;
	}

	/**
	 * @param sqlString the sqlString to set
	 */
	public void setSqlString(String sqlString) {
		_sqlString = sqlString;
	}

	/**
	 * @return the _statementType
	 */
	public int getStatementType() {
		return _statementType;
	}

	/**
	 * @param type the _statementType to set
	 */
	public void setStatementType(int type) {
		_statementType = type;
	}
}
