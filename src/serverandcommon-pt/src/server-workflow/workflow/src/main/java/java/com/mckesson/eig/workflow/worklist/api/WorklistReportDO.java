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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Feb 8, 2008
 * @since  HECM 1.0; Feb 8, 2008
 *
 * This data object holds the filter parameters that are required to generate the Worklist report.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "WorklistReportDO", namespace = EIGConstants.TYPE_NS_V1)
public class WorklistReportDO
extends BasicWorklistDO {

    /**
     * Representation of start date filter
     */
    public static final String START_DATE_FILTER = "startDate";

    /**
     * Representation of due date filter
     */
    public static final String DUE_DATE_FILTER = "dueDate";


    /**
     * Holds the domain actor for which the report has to be created.
     */
    private Actor _domain;

    /**
     * Holds the primary sort column for the report to be generated.
     */
    private byte _primarySort;

    /**
     * Specifies if the primary sorting has to be descending/ascending
     */
    private boolean _isDesc;

    /**
     * Specifies if the person generating report is admin/not
     */
    private boolean _isAdmin;

    /**
     * Holds the string representation of owner actors
     */
    private Actors _ownerActors;

    /**
     * Holds the selected worklist ids
     */
    private long[] _worklistIDs;

    /**
     * Holds the partial/complete name of the task
     */
    private String _taskName;

    /**
     * Holds the status IDs for which the report has to be generated
     */
    private String[] _statusIDs;

    /**
     * Holds the priority ID for which the report has to be generated
     */
    private int _priorityID;

    /**
     * Represents if the date filter has to applied for due date or start date of the task. This
     * attribute contains the value START_DATE_FILTER if it has to be applied for start date or
     * DUE_DATE_FILTER if it has to be applied for due date. The date filter will be considered for
     * start date by default if the attribute is specified as null.
     */
    private String _dateFilter;

    /**
     * Holds the start date filter
     */
    private Date _startDate;

    /**
     * Holds the end date filter
     */
    private Date _endDate;

    /**
     * Holds the csv file name and its location on the system where the report will be generated.
     */
    private String _csvFileName;
	
	/**
	 * Holds if all the the worklists has to be fetched or only the worklist with the specified
     * ids has to be fetched.
	 */
    private boolean _fetchAllWorklists;

    /**
     * Instantiates a default worklist report data object.
     */
    public WorklistReportDO() {
        super();
    }

    /**
     * This method is used to retrieve the domain actor filter.
     *
     * @return
     *      domain ID
     */
    public Actor getDomain() {
        return _domain;
    }

    /**
     * This method is used to set the domain acctor filter.
     *
     * @param domainID
     *          domain ID
     */
    @XmlElement(name = "domain")
    public void setDomain(Actor domain) {
        _domain = domain;
    }

    /**
     * This method is used to retrieve the primary sort column id.
     *
     * @return
     *      primary sort
     */
    public byte getPrimarySort() {
        return _primarySort;
    }

    /**
     * This method is used to set the primary sort column id.
     *
     * @param primarySort
     *          primary sort
     */
    @XmlElement(name = "primarySort")
    public void setPrimarySort(byte primarySort) {
        _primarySort = primarySort;
    }

    /**
     * This method is used to retrieve the  order of the primary sort.
     *
     * @return
     *     true if the sort has to descending.
     */
    public boolean getIsDesc() {
        return _isDesc;
    }

    /**
     *
     * @param isDesc
     */
    @XmlElement(name = "isDesc")
    public void setIsDesc(boolean isDesc) {
        _isDesc = isDesc;
    }

    /**
     * This method is used to retrieve if the user generating the report is admin/not
     *
     * @return
     *      true if admin
     */
    public boolean getIsAdmin() {
        return _isAdmin;
    }

    /**
     * This method is used to set if the user generating the report is admin/not
     *
     * @param isAdmin
     *          is admin/not
     */
    @XmlElement(name = "isAdmin")
    public void setIsAdmin(boolean isAdmin) {
        _isAdmin = isAdmin;
    }

    /**
     * This method is used to retrieve the owner actors.
     *
     * @return
     *      owners
     */
    public Actors getOwnerActors() {
        return _ownerActors;
    }

    /**
     * This method is used to set the owner actors.
     *
     * @param ownerActors
     *          owners
     */
    @XmlElement(name = "ownerActors")
    public void setOwnerActors(Actors ownerActors) {
        _ownerActors = ownerActors;
    }

    /**
     * This method is used to retrieve the worklist ids.
     *
     * @return
     *      worklist ids
     */
    public long[] getWorklistIDs() {
        return _worklistIDs;
    }

    /**
     * This method is used to set the worklist ids.
     *
     * @param worklistIDs
     *          worklist ids
     */
    @XmlElement(name = "worklistIDs")
    public void setWorklistIDs(long[] worklistIDs) {
        _worklistIDs = worklistIDs;
    }

    /**
     * This method is used to retrieve the task name attribute.
     *
     * @return
     *      task name
     */
    public String getTaskName() {
        return _taskName;
    }

    /**
     * This method is used to set the task name attribute.
     *
     * @param taskName
     *          task name
     */
    @XmlElement(name = "taskName")
    public void setTaskName(String taskName) {
        _taskName = taskName;
    }

    /**
     * This method is used to retrieve the status ids.
     *
     * @return
     *      status ids
     */
    public String[] getStatusIDs() {
        return _statusIDs;
    }

    /**
     * This method is used to set the status ids.
     *
     * @param statusIDs
     *          status ids.
     */
    @XmlElement(name = "statusIDs")
    public void setStatusIDs(String[] statusIDs) {
        _statusIDs = statusIDs;
    }

    /**
     * This method is used to retrieve the priority id.
     *
     * @return
     *      priority id
     */
    public int getPriorityID() {
        return _priorityID;
    }

    /**
     * This method is used to set the priority id.
     *
     * @param priorityid
     *          priority id
     */
    @XmlElement(name = "priorityID")
    public void setPriorityID(int priorityid) {
        _priorityID = priorityid;
    }

    /**
     * This method is used to retrieve the date filter attribute.
     *
     * @return
     *      date filter
     */
    public String getDateFilter() {
        return _dateFilter;
    }

    /**
     * This method is used to set the date filter attribute.
     *
     * @param dateFilter
     *          date filter
     */
    @XmlElement(name = "dateFilter")
    public void setDateFilter(String dateFilter) {
        _dateFilter = dateFilter;
    }

    /**
     * This method is used to retrieve the start date.
     *
     * @return
     *      start date
     */
    public Date getStartDate() {
        return _startDate;
    }

    /**
     * This method is used to retrieve the start date.
     *
     * @param startDate
     *          start date
     */
    @XmlElement(name = "startDate")
    public void setStartDate(Date startDate) {
        _startDate = startDate;
    }

    /**
     * This method is used to retrieve the end date.
     *
     * @return
     */
    public Date getEndDate() {
        return _endDate;
    }

    /**
     * This method is used to set the end date.
     *
     * @param endDate
     */
    @XmlElement(name = "endDate")
    public void setEndDate(Date endDate) {
        _endDate = endDate;
    }

    /**
     * This method is used to get whether all worklists need to be fetch or not.
     *
     * @param csvFileName
     *          csv file name
     */
    public boolean getFetchAllWorklists() {
        return _fetchAllWorklists;
    }
    
    /**
     * This method is used to set whether fetch all worklists or not.
     * @param allWorklists
     */
    @XmlElement(name = "fetchAllWorklists")
    public void setFetchAllWorklists(boolean allWorklists) {
        _fetchAllWorklists = allWorklists;
    }
    
    /**
     * This method is used to retrieve the csv file location where the report will be generated.
     *
     * @return
     *      csv file name
     */
    public String getCsvFileName() {
        return _csvFileName;
    }

    /**
     * This method is used to set the csv file location where the report will be generated.
     *
     * @param csvFileName
     *          csv file name
     */
    @XmlElement(name = "csvFileName")
    public void setCsvFileName(String fileName) {
        _csvFileName = fileName;
    }
    
    public boolean hasOwnerActors() {
        
        if (_ownerActors == null || _ownerActors.getActors() == null 
                                 || _ownerActors.getActors().size() == 0) {
            return false;
        }
        return true;
    }
}
