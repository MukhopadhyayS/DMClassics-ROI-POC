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
package com.mckesson.eig.workflow.process.datavault.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.JpdlException;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Services;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.datavault.DVUtil;
import com.mckesson.eig.workflow.process.datavault.ProcessDVHelper;
import com.mckesson.eig.workflow.process.datavault.ProcessDataVault;
import com.mckesson.eig.workflow.process.datavault.model.ProcessAssignDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessAttributeDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessOwnerDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessVersionDVInfo;

/**
 * @author OFS
 *
 * @date Apr 3, 2009
 * @since HECM 1.0.3; Apr 3, 2009
 */
public class ProcessDVDAO {

    private static JbpmConfiguration _jc;
    private static Session _session;
    private static Connection _connection;

    private static final int HECM_APP_ID 	= 1;

    private static final Log LOG     = LogFactory.getLogger(ProcessDataVault.class);
	private static final Log CONSOLE = LogFactory.getLogger("sop");

    static {

        if (_jc == null) {
            _jc = JbpmConfiguration.getInstance();
        }

        String resName = "/com/mckesson/eig/workflow/datavault/hibernate.cfg.test.local.xml";

        URL hbConfig = ProcessDVDAO.class.getResource(resName);
        DbPersistenceServiceFactory psf = 
            (DbPersistenceServiceFactory) _jc.getServiceFactory(Services.SERVICENAME_PERSISTENCE);
        psf.setConfiguration(new Configuration().configure(hbConfig));
        if (_session == null) {
            _session = psf.getSessionFactory().openSession();
        }
        if (_connection == null) {
            _connection = _session.connection();
        }
    }

    private String _querySelectProcessMaxId = "SELECT MAX(PROCESS_ID) FROM HECM_WLIST.WF_PROCESS";

    private String _querySelectProcessSeq   = "SELECT HECM_WLIST.WF_PROCESS_SEQ.NEXTVAL FROM DUAL";

    private String _querySelectActorSeq     = "SELECT HECM_WLIST.WF_ACTOR_SEQ.NEXTVAL FROM DUAL";

    private String _querySelectActor = "SELECT ACTOR.ACTOR_ID, STAFF.STAFF_LOGIN_SEQ "
                                        + "	FROM STAFF_LOGIN STAFF, WF_ACTOR ACTOR "
                                        + " WHERE ACTOR.ENTITY_ID(+) = STAFF.STAFF_LOGIN_SEQ " 
                                        + "	AND STAFF.ACCESS_ID = ? " 
                                        + " AND ACTOR.ENTITY_TYPE_ID(+) = ? ";

    private String _querySelectGroup = "SELECT ACTOR.ACTOR_ID, GI.GROUP_ID "
                                        + "	FROM HECM_CONFIG.GROUP_INFO GI, WF_ACTOR ACTOR "
                                        + " WHERE ACTOR.ENTITY_ID(+) = GI.GROUP_ID " 
                                        + "	AND GI.GROUP_NAME = ? " 
                                        + " AND ACTOR.ENTITY_TYPE_ID(+) = ? ";

    private String _querySelectDomain = "SELECT ACTOR.ACTOR_ID, ORG.ORG_ID "
                                         + " FROM HECM_CONFIG.ORG_INFO ORG, WF_ACTOR ACTOR "
                                         + " WHERE ACTOR.ENTITY_ID(+) = ORG.ORG_ID "
                                         + " AND ORG.ORG_NAME = ? "
                                         + " AND ACTOR.ENTITY_TYPE_ID(+) = ? ";

    private String _queryInsertProcessSeq = " INSERT INTO HECM_WLIST.WF_PROCESS " 
                                              + "	    (PROCESS_ID, CREATE_DATETIME, " 
                                              + "		MODIFY_DATETIME) " 
                                              + " VALUES " 
                                              + "   	(HECM_WLIST.WF_PROCESS_SEQ.NEXTVAL, " 
                                              + "	    sysdate, sysdate)";

    private String _queryInsertProcessVersion = " INSERT INTO HECM_WLIST.WF_PROCESS_VERSION "
                                                  + " (PROCESS_ID, VERSION_ID, "
                                                  + "  NAME, DESCRIPTION, PROCESS_DEFINITION, "
                                                  +	"  PROCESS_GRAPH, LOCKED_BY, "
                                                  +	"  STATUS, CREATE_DATETIME, EFFECTIVE_DATE,"
                                                  +	"  EXPIRATION_DATE, MAX_INSTANCE_DURATION, "
                                                  +	"  RETENTION_PERIOD, NOTIFY_EXCEPTIONS, "
                                                  +	"  EXCEPTION_EMAILADDRESS, IS_ACTIVE) "
                                                  + " VALUES "
                                                  +	" (?, ?, ?, ?, ?, ?, ?, ?, "
                                                  +	"  ?, ?, ?, ?, ?, ?, ?, ?) ";

    private String _queryInsertProcessAttribute = " INSERT INTO HECM_WLIST.WF_PROCESS_ATTRIBUTES "
                                                    + " 	(PROCESS_ID, VERSION_ID, " 
                                                    + " 	ATTRIBUTE_NAME, ATTRIBUTE_VALUE, "
                                                    + " 	ATTRIBUTE_TYPE, CREATE_DATETIME, "
                                                    + " 	MODIFY_DATETIME) "
                                                    + " VALUES "
                                                    + "		(?, ?, ?, ?, ?, sysdate, sysdate) ";

    private String _queryInsertProcessAssign = " INSERT INTO HECM_WLIST.WF_PROCESS_ASSIGN"
                                                 + " 	(PROCESS_ID, ACTOR_ID, PERMISSION_NAME, " 
                                                 + "		CREATE_DATETIME, MODIFY_DATETIME) " 
                                                 + " VALUES " 
                                                 + "		(?, ?, ?, sysdate, sysdate) ";

    private String _queryInsertProcessOwner = " INSERT INTO HECM_WLIST.WF_PROCESS_OWNERS "
                                                + " 	(PROCESS_ID, ACTOR_ID, " 
                                                + " 	CREATE_DATETIME, MODIFY_DATETIME) " 
                                                + " VALUES "
                                                + " 	(?, ?, sysdate, sysdate) ";

    private String _queryInsertActor = " INSERT INTO HECM_WLIST.WF_ACTOR" 
                                         + "	(ACTOR_ID, APP_ID, ENTITY_TYPE_ID, " 
                                         + " 	ENTITY_ID, RECORD_VERSION) " 
                                         + " VALUES "
                                         + " 	(?, ?, ?, ?, 0) ";

    private String _queryUpdateProcessVersion = "  UPDATE HECM_WLIST.WF_PROCESS_VERSION PV SET " 
    		                                    + " PV.DESCRIPTION = ?, "
            		                            + " PV.PROCESS_DEFINITION = ?, "
            		                            + " PV.PROCESS_GRAPH = ?, " 
            		                            + " PV.LOCKED_BY = ?, " 
            		                            + " PV.STATUS = ?, "
            		                            + " PV.CREATE_DATETIME = ?, "
            		                            + " PV.EFFECTIVE_DATE = ?, "
            		                            + " PV.EXPIRATION_DATE = ?, "
            		                            + " PV.MAX_INSTANCE_DURATION = ?, "
            		                            + " PV.RETENTION_PERIOD = ?, "
            		                            + " PV.NOTIFY_EXCEPTIONS = ?, "
            		                            + " PV.EXCEPTION_EMAILADDRESS = ?, "
            		                            + " PV.IS_ACTIVE = ? "
            		                            + "WHERE "
            		                            + " PV.VERSION_ID = ? "
            		                            + "AND " 
            		                            + " PV.NAME = ?";

    private String _queryUpdateProcessOwner = " UPDATE HECM_WLIST.WF_PROCESS_OWNERS PO " 
                                              + " SET PO.ACTOR_ID = ?, MODIFY_DATETIME = sysdate " 
			                                  + " WHERE PO.PROCESS_ID = ? ";

    private String _querySelectProcessAttribute = " SELECT PROCESS_ID " 
    	                                            + " FROM HECM_WLIST.WF_PROCESS_ATTRIBUTES " 
    	                                            + " WHERE VERSION_ID = ? " 
    	                                            + " AND ATTRIBUTE_NAME = ? "
    	                                            + " AND PROCESS_ID = ? ";

    private String _queryUpdateProcessAttribute = " UPDATE HECM_WLIST.WF_PROCESS_ATTRIBUTES SET "
                                                  + "  VERSION_ID = ?, " 
                                                  + "  ATTRIBUTE_VALUE = ?, "
                                                  + "  ATTRIBUTE_TYPE  = ?, "
                                                  + "  MODIFY_DATETIME = sysdate "
                                                  + " WHERE "
                                                  + "  PROCESS_ID = ? "
                                                  + " AND ATTRIBUTE_NAME  = ? ";

    private String _querySelectProcessAssign = " SELECT PROCESS_ID " 
                                               + " FROM HECM_WLIST.WF_PROCESS_ASSIGN " 
                                               + " WHERE ACTOR_ID = ? " 
                                               + " AND PERMISSION_NAME = ? "
                                               + " AND PROCESS_ID = ? ";

    private String _queryUpdateProcessAssign = " UPDATE HECM_WLIST.WF_PROCESS_ASSIGN SET "
                                               + " 	ACTOR_ID = ?, PERMISSION_NAME = ?, " 
                                               + "	MODIFY_DATETIME = sysdate " 
                                               + " WHERE PROCESS_ID = ? ";

    /**
     * @param processVersion
     * @param isExist
     * @throws SQLException
     */
    public void insertProcessOwner(ProcessVersionDVInfo processVersion, boolean isExist)
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class 
    	                         + ".insertProcessOwner(processVersion, isExist)";
    	LOG.debug(logMethod + ">> Started");

        PreparedStatement insertPS = null;
        PreparedStatement updatePS = null;

        try {

            insertPS = _connection.prepareStatement(_queryInsertProcessOwner);
            updatePS = _connection.prepareStatement(_queryUpdateProcessOwner);
            int index;

            ProcessOwnerDVInfo processOwner = processVersion.getProcessOwner();
            index = 1;
            if (!isExist) { 

            	insertPS.setInt(index++, processVersion.getProcessId());
                insertPS.setInt(index++, processOwner.getOwnerId());
                insertPS.executeUpdate();
            } else {

            	updatePS.setInt(index++, processOwner.getOwnerId());
            	updatePS.setInt(index++, processVersion.getProcessId());
            }
            LOG.debug("Process "
            			+ processOwner.getName()
            			+ " - is owned by "
            			+ processOwner.getOwnerName());
        } catch (Exception e) {

        	String errorMsg = "insertProcessOwner(List<ProcessDVInfo>) cause problem ";
        	LOG.error(errorMsg + e);
        	CONSOLE.error(errorMsg);
            _connection.rollback();
        } finally {

            if (insertPS != null) { 
                insertPS.close(); 
            }
            if (updatePS != null) { 
            	updatePS.close(); 
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
    }

    /**
     * @param list List<Process>
     *
     * @throws SQLException
     */
    public void insertProcessAssign(List<ProcessDVInfo> list)
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class + ".insertProcessAssign(list)";
    	LOG.debug(logMethod + ">> Started");

    	PreparedStatement selectPS = null;
    	PreparedStatement insertPS = null;
    	PreparedStatement updatePS = null;

    	ResultSet rs = null;
    	try {

    		selectPS = _connection.prepareStatement(_querySelectProcessAssign);
    		insertPS = _connection.prepareStatement(_queryInsertProcessAssign);
    		updatePS = _connection.prepareStatement(_queryUpdateProcessAssign);

    		Iterator<ProcessDVInfo> processIterator = list.iterator();
            ProcessAssignDVInfo processAssign;
            int index;
            while (processIterator.hasNext()) {

                processAssign = (ProcessAssignDVInfo) processIterator.next();

                index = 1;
                selectPS.setInt(index++, processAssign.getActorId());
        		selectPS.setString(index++, processAssign.getPermission());
        		selectPS.setInt(index++, processAssign.getProcessId());

        		rs = selectPS.executeQuery();
        		int processId = 0;
        		if (rs.next()) {
        			processId = rs.getInt(1);
        		}
                index = 1;
                if (processId > 0) {

                	updatePS.setInt(index++, processAssign.getActorId());
                	updatePS.setString(index++, processAssign.getPermission());
                	updatePS.setInt(index++, processAssign.getProcessId());
                	updatePS.executeUpdate();
	                LOG.debug("Process "
		            			+ processAssign.getName()
		            			+ " - is re-assigned to "
		            			+ processAssign.getActorName());
                } else {

                	insertPS.setInt(index++, processAssign.getProcessId());
	                insertPS.setInt(index++, processAssign.getActorId());
	                insertPS.setString(index++, processAssign.getPermission());
	                insertPS.executeUpdate();
	                LOG.debug("Process "
		            			+ processAssign.getName()
		            			+ " - is assigned to "
		            			+ processAssign.getActorName());
                }
            }
        } catch (Exception e) {

        	String errorMsg = "insertProcessAssign(List<ProcessDVInfo>) cause problem ";
        	LOG.error(errorMsg + e);
        	CONSOLE.error(errorMsg);
            _connection.rollback();
        } finally {

        	if (insertPS != null) { 
                insertPS.close(); 
            }
            if (updatePS != null) { 
            	updatePS.close(); 
            }
            if (selectPS != null) { 
            	selectPS.close(); 
            }
            if (rs != null) {
            	rs.close();
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
    }

    /**
     * @param list List<Process>
     *
     * @throws SQLException
     */
    public void insertProcessAttribute(List<ProcessDVInfo> list)
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class + ".insertProcessAttribute(list)";
    	LOG.debug(logMethod + ">> Started");

    	PreparedStatement selectPS  = null;
    	PreparedStatement insertPS  = null;
    	PreparedStatement updatePS  = null;

    	ResultSet rs = null;
    	try {

        	selectPS = _connection.prepareStatement(_querySelectProcessAttribute);
            insertPS = _connection.prepareStatement(_queryInsertProcessAttribute);
            updatePS = _connection.prepareStatement(_queryUpdateProcessAttribute);

            Iterator<ProcessDVInfo> processIterator = list.iterator();
            ProcessAttributeDVInfo processAttribute;
            int index;
            while (processIterator.hasNext()) {

                processAttribute = (ProcessAttributeDVInfo) processIterator.next();

                index = 1;
        		selectPS.setInt(index++, processAttribute.getVersionId());
        		selectPS.setString(index++, processAttribute.getAttributeName());
        		selectPS.setInt(index++, processAttribute.getProcessId());

        		rs = selectPS.executeQuery();
        		int processId = 0;
        		if (rs.next()) {
        			processId = rs.getInt(1);
        		}
        		index = 1;
        		if (processId > 0) {

        			updatePS.setInt(index++, processAttribute.getVersionId());
        			updatePS.setString(index++, processAttribute.getAttributeValue());
        			updatePS.setString(index++, processAttribute.getAttributeType());
        			updatePS.setInt(index++, processAttribute.getProcessId());
        			updatePS.setString(index++, processAttribute.getAttributeName());
        			updatePS.executeUpdate();
	                LOG.debug("Attribute "
	                		+ processAttribute.getAttributeName()
	                		+ " - "
	                		+ processAttribute.getAttributeValue()
	                		+ " - for Process - " 
	                		+ processAttribute.getName() 
	                		+ " - is updated");
        		} else {

        			insertPS.setInt(index++, processAttribute.getProcessId());
	                insertPS.setInt(index++, processAttribute.getVersionId());
	                insertPS.setString(index++, processAttribute.getAttributeName());
	                insertPS.setString(index++, processAttribute.getAttributeValue());
	                insertPS.setString(index++, processAttribute.getAttributeType());
	                insertPS.executeUpdate();
	                LOG.debug("Attribute "
	                		+ processAttribute.getAttributeName()
	                		+ " - "
	                		+ processAttribute.getAttributeValue()
	                		+ " - for Process - " 
	                		+ processAttribute.getName() 
	                		+ " - is inserted");
        		}
            }
        } catch (Exception e) {

        	String errorMsg = "insertProcessAttribute(List<ProcessDVInfo>) cause problem ";
        	LOG.error(errorMsg + e);
        	CONSOLE.error(errorMsg);
            _connection.rollback();
        } finally {

        	if (insertPS != null) { 
                insertPS.close(); 
            }
            if (updatePS != null) { 
            	updatePS.close(); 
            }
            if (selectPS != null) { 
            	selectPS.close(); 
            }
            if (rs != null) {
            	rs.close();
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
    }

    /**
     * @param list List<Process>
     *
     * @throws SQLException
     */
    public void insertProcessVersion(List<ProcessDVInfo> list)
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class + ".insertProcessVersion(list)";
    	LOG.debug(logMethod + ">> Started");

        PreparedStatement insertPS = null;
        PreparedStatement updatePS = null;
        PreparedStatement ps       = null;
        try {

            insertPS = _connection.prepareStatement(_queryInsertProcessVersion);
            updatePS = _connection.prepareStatement(_queryUpdateProcessVersion);

            Iterator<ProcessDVInfo> processIterator = list.iterator();
            ProcessVersionDVInfo processVersion;
            int index;
            int isPVExist;
            boolean isUpdated = false;
            while (processIterator.hasNext()) {

                processVersion = (ProcessVersionDVInfo) processIterator.next();

                index = 1;
                isPVExist = getProcessId(processVersion.getName(),
                					     processVersion.getVersionId(),
                					     true);

                if (isPVExist > 0) {

                	ps = updatePS;
                	isUpdated = true;
                } else {

                	insertProcessId();
                	isUpdated = false;
                	ps = insertPS;
                	ps.setInt(index++, processVersion.getProcessId());
                	ps.setInt(index++, processVersion.getVersionId());
                	ps.setString(index++, processVersion.getName());
                }
                ps.setString(index++, processVersion.getDescription());
                ps.setObject(index++, processVersion.getDefContent());
                ps.setObject(index++, processVersion.getGraphContent());
                ps.setInt(index++, processVersion.getLockedById());
                ps.setString(index++, processVersion.getStatus());
                ps.setDate(index++, DVUtil.convertUtilToSQLDate(processVersion.getCreatedDate()));
                ps.setDate(index++, DVUtil.convertUtilToSQLDate(processVersion.getEffectiveDate()));
                ps.setDate(index++, DVUtil.convertUtilToSQLDate(processVersion.getExpireDate()));
                ps.setInt(index++, processVersion.getMaxInstanceDuration());
                ps.setInt(index++, processVersion.getRetentionPeriod());
                ps.setString(index++, processVersion.getNotifyException());
                ps.setString(index++, processVersion.getNotifyEmailId());
                ps.setString(index++, processVersion.getActive());
                if (isUpdated) {

                	ps.setInt(index++, processVersion.getVersionId());
                	ps.setString(index++, processVersion.getName());
                	insertProcessOwner(processVersion, true);
                } else {
                	insertProcessOwner(processVersion, false);
                }
                ps.executeUpdate();
                if (isUpdated) {
                	LOG.debug("Process - " + processVersion.getName() + " - is updated");
                } else {
                	LOG.debug("Process - " + processVersion.getName() + " - is inserted");
                }
            }
        } catch (Exception e) {

        	String errorMsg = "insertProcessVersion(List<ProcessDVInfo>) cause problem ";
        	LOG.error(errorMsg + e);
        	CONSOLE.error(errorMsg);
            _connection.rollback();
        } finally {

            if (ps != null) {
                ps.close(); 
            }
            if (insertPS != null) { 
                insertPS.close(); 
            }
            if (updatePS != null) { 
            	updatePS.close(); 
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
    }

    /**
     * @param processSize
     * @throws SQLException
     */
    public void insertProcessId()
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class + ".insertProcessId(processSize)";
    	LOG.debug(logMethod + ">> Started");
        PreparedStatement ps  = null;
        try {

            ps = _connection.prepareStatement(_queryInsertProcessSeq);
        	ps.executeUpdate();
        } catch (Exception e) {

        	String errorMsg = "insertProcessId() cause problem : ";
        	LOG.error(errorMsg + e);
        	CONSOLE.error(errorMsg);
            _connection.rollback();
        } finally {

            if (ps != null) {
                ps.close();
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
    }

    /**
     * @param name String
     * @param versionId int
     * @param versionFilter boolean
     *
     * @return int
     *
     * @throws SQLException
     */
    public int getProcessId(String name, int versionId, boolean versionFilter)
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class 
    	                       + ".getProcessId(name, versionId, versionFilter)";
    	LOG.debug(logMethod + ">> Started >> for ProcessName : " + name);
        PreparedStatement ps  = null;
        ResultSet rs          = null;
        try {

        	String selectQueryForProcessId = "SELECT WFPV.PROCESS_ID "
        		                             + " FROM HECM_WLIST.WF_PROCESS_VERSION WFPV "
        		                             + " WHERE WFPV.NAME = ? ";

        	if (versionFilter) {
        		selectQueryForProcessId += " AND WFPV.VERSION_ID = ? ";
        	}
            ps = _connection.prepareStatement(selectQueryForProcessId);
            ps.setString(1, name);
            if (versionFilter) {
            	ps.setInt(2, versionId);	
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            LOG.error("getProcessId(String) cause problem : " + e);
        } finally {

            if (ps != null) { 
                ps.close(); 
            }
            if (rs != null) { 
                rs.close(); 
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
        return 0;
    }

    /**
     * @return int
     *
     * @throws SQLException
     */
    public int getCurrentProcessId()
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class + ".getCurrentProcessId()";
    	LOG.debug(logMethod + ">> Started");

        PreparedStatement ps  = null;
        ResultSet rs          = null;
        try {

            ps = _connection.prepareStatement(_querySelectProcessMaxId);
            rs = ps.executeQuery();
            int maxProcessId = 0;
            if (rs.next()) {
                maxProcessId = rs.getInt(ProcessDVHelper.COLUMN_NUMBER_1);
            }
            ps = _connection.prepareStatement(_querySelectProcessSeq);
            rs = ps.executeQuery();
            if (rs.next()) {

                int seqValue = rs.getInt(ProcessDVHelper.COLUMN_NUMBER_1);
                if (seqValue < maxProcessId) {

                    for (int i = seqValue; i <= maxProcessId; i++) {
                        rs = ps.executeQuery();
                    }
                    rs.next();
                    seqValue = rs.getInt(ProcessDVHelper.COLUMN_NUMBER_1);
                }
                return seqValue;
            }
        } catch (Exception e) {
            LOG.error("getCurrentProcessId() cause problem : " + e);
        } finally {

            if (ps != null) { 
                ps.close(); 
            }
            if (rs != null) { 
                 rs.close(); 
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
        return 0;
    }

    /**
     * Method to return entity type query (Domain/Group/User)
     * 
     * @param entityType int
     * 
     * @return String
     */
    private String getEntityQuery(int entityType) {

        String entitySQL = null;
        if (ProcessDVHelper.USER_ENTITY_TYPE == entityType) {
            entitySQL = _querySelectActor;
        } else if (ProcessDVHelper.GROUP_ENTITY_TYPE == entityType) {
            entitySQL = _querySelectGroup;
        } else if (ProcessDVHelper.DOMAIN_ENTITY_TYPE == entityType) {
            entitySQL = _querySelectDomain;
        }
        return entitySQL;
    }

    /**
     * Method to validate if the actorid passed belongs to entityType mentioned
     * 
     * @param name String
     * @param entityType int
     * 
     * @return int
     */
    public int getEntityId(String name, int entityType) 
    throws SQLException {

    	final String logMethod = ProcessDVDAO.class + ".getEntityId(name, entityType)";
    	LOG.debug(logMethod + ">> Started");

        PreparedStatement ps  = null;
        ResultSet rs          = null;
        int actorId 		  = 0;
        int entityId 		  = 0;
        try {

            int index = 1;
            ps = _connection.prepareStatement(getEntityQuery(entityType));
            ps.setString(index++, name);
            ps.setInt(index++, entityType);

            rs = ps.executeQuery();
            if (rs.next()) {

                actorId = rs.getInt(1);
                entityId = rs.getInt(2);
            }
            if (entityId == 0) {
                return 0;
            } else if (actorId == 0) {

                ps = _connection.prepareStatement(_querySelectActorSeq);
                rs = ps.executeQuery();

                if (rs.next()) {

                    actorId = rs.getInt(1);

                    ps = _connection.prepareStatement(_queryInsertActor);
                    index = 1;
                    ps.setInt(index++, actorId);
                    ps.setInt(index++, HECM_APP_ID);
                    ps.setInt(index++, entityType);
                    ps.setInt(index++, entityId);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            LOG.error("getEntityId() caused problem : " + e);
        } finally {

            if (ps != null) { 
                ps.close(); 
            }
            if (rs != null) { 
                rs.close(); 
            }
            if (_connection != null) {
                _connection.commit();
            }
            LOG.debug(logMethod + ">> Finished");
        }
        return actorId;
    }

    /**
     * @param list List<ProcessDVInfo>
     */
    public void deployProcess(List<ProcessDVInfo> list) {

    	final String logMethod = ProcessDVDAO.class + ".deployProcess(list)";
    	LOG.debug(logMethod + ">> Started");
        JbpmContext jbpmContext = _jc.createJbpmContext();

        try {

             Iterator<ProcessDVInfo> processIterator = list.iterator(); 
             while (processIterator.hasNext()) {

                 ProcessDVInfo processDV = processIterator.next();
                 if (processDV instanceof ProcessVersionDVInfo) {

                     String defContent = ((ProcessVersionDVInfo) processDV).getDefContent();
                     if (defContent != null) {

                    	 ProcessDefinition processDefinition 
	                         = isValidJbpmProcessDefinition(defContent);
	
	                     jbpmContext.deployProcessDefinition(processDefinition);
	                     LOG.debug(((ProcessVersionDVInfo) processDV).getName()
	                    		 + " is Deployed successfully ");
                     }
                 }
             }
        } catch (Exception e) {
        	LOG.error(e);
        } finally {

        	jbpmContext.close();
            LOG.debug(logMethod + ">> Finished");
        }
    }

    /**
     * @param processDefinitionXml String
     * 
     * @return ProcessDefinition
     */
    private ProcessDefinition isValidJbpmProcessDefinition(String processDefinitionXml) {

    	String errorMsg = null;
        ProcessDefinition processDefinition = null;
        try {
            processDefinition = ProcessDefinition.parseXmlString(processDefinitionXml);
        } catch (JpdlException e) {

        	errorMsg = "Parsing the XML caused errors and XML is not deployable";
        	LOG.error(errorMsg + e);
        	CONSOLE.debug(errorMsg);
        } catch (Exception e) {

        	errorMsg = "Invalid process deefnition";
        	LOG.error(errorMsg + e);
        	CONSOLE.debug(errorMsg);
        }
        return processDefinition;
    }
}
