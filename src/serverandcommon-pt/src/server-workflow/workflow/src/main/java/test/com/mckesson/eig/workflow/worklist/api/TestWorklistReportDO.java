/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.workflow.worklist.api;

import java.util.Date;

import junit.framework.TestCase;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;

/**
 * @author Sahul Hameed Y
 * @date   Feb 16, 2008
 * @since  HECM 1.0; Feb 16, 2008
 */
public class TestWorklistReportDO
extends TestCase {
    
    private static WorklistReportDO _worklistReportDO;
    private static String _csvFileName  = "csv";
    private static String _dateFilter   = "startDate";
    private static Actor _domainID      = new Actor();
    private static Date  _endDate       = new Date();
    private static Actors  _ownerActors = new Actors();
    private static byte _primarySort    = 1;  
    private static int  _priorityID     = 1;
    private static String[] _statusIDs  = new String[] {"new"};
    private static String _taskName     = "name";
    private static long[] _worklistIDs  = {1};
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        _worklistReportDO = new WorklistReportDO();
    }
    
    /**
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown()
    throws Exception {
        super.tearDown();
    }
    
    public void testWorklistReportDO() {
        
        _worklistReportDO.setCsvFileName(_csvFileName);
        _worklistReportDO.setDateFilter(_dateFilter);
        _worklistReportDO.setDomain(_domainID);
        _worklistReportDO.setEndDate(_endDate);
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setIsAdmin(false);
        _worklistReportDO.setIsDesc(false);
        _worklistReportDO.setOwnerActors(_ownerActors);
        _worklistReportDO.setPrimarySort(_primarySort);
        _worklistReportDO.setPriorityID(_priorityID);
        _worklistReportDO.setStartDate(new Date());
        _worklistReportDO.setStatusIDs(new String[] {"new"});
        _worklistReportDO.setTaskName(_taskName);
        _worklistReportDO.setWorklistIDs(_worklistIDs);
        
        assertEquals(_csvFileName, _worklistReportDO.getCsvFileName());
        assertEquals(_dateFilter, _worklistReportDO.getDateFilter());
        assertEquals(_domainID.toString(), _worklistReportDO.getDomain().toString());
        assertNotNull(_worklistReportDO.getStartDate());
        assertEquals(false, _worklistReportDO.getFetchAllWorklists());
        assertEquals(false, _worklistReportDO.getIsAdmin());
        assertEquals(false, _worklistReportDO.getIsDesc());
        assertEquals(_ownerActors, _worklistReportDO.getOwnerActors());
        assertEquals(_primarySort, _worklistReportDO.getPrimarySort());
        assertEquals(_priorityID, _worklistReportDO.getPriorityID());
        assertNotNull(_worklistReportDO.getEndDate());
        assertEquals(_statusIDs[0], _worklistReportDO.getStatusIDs()[0]);
        assertEquals(_taskName, _worklistReportDO.getTaskName());
        assertEquals(_worklistIDs, _worklistReportDO.getWorklistIDs());
    }
}
