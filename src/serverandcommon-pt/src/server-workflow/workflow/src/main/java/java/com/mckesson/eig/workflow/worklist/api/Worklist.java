/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.worklist.api;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * Worklist data object.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Worklist", namespace = EIGConstants.TYPE_NS_V1)
public class Worklist
extends BasicWorklistDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -4624040426976055525L;

    /**
     * Maximum allowed length for worklist name
     */
    private static final int NAME_LENGTH = 80;

    /**
     * Maximum allowed length for worklist description.
     */
    private static final int DESC_LENGTH = 256;

    /**
     * Holds the id of the worklist.
     */
    private long _worklistID;

    /**
     * Holds the Worklist's name
     */
    private String _name;

    /**
     * Holds the description of the worklist.
     */
    private String _desc;

    /**
     * Holds the total number of tasks in the worklist.
     */
    private long _total = -1;
    
    /**
     * Holds the completed number of tasks in the worklist.
     */
    private long _completed;

    /**
     * Holds the owning actors of the worklist.
     */
    private Set<Actor> _ownerActors;

    /**
     * Holds the wrapper which contains the owning actors.
     */
    private Actors _owners;

    /**
     * Holds the wrapper which contains the access controls.
     */
    private TaskACLs _assignedTo;

    /**
     * To hold the version attribute of the entity.
     */
    private int _version;

    /**
     * To hold the status of the task.
     */
    private Map<String, Long> _statusMap;

    private List<StatusCountPair> _statusList;

    /**
     * holds set of tasks found in the particular worklist
     */
    private Set< ? > _tasks;

    /**
     * This constructor instantiates a new Worklist Data Object.
     */
    public Worklist() {
        super();
    }

    /**
     * This method is used to retrieve the worklist ID.
     * @return worklistID
     */
    public long getWorklistID() {
        return _worklistID;
    }

    /**
     * This method is used to retrieve the worklist description.
     * @return worklistDesc
     */
    public String getDesc() {
        return _desc;
    }

    /**
     * This method is used to retrieve the worklist name.
     * @return worklistName.
     */
    public String getName() {
        return _name;
    }

    /**
     * This method is used to set the worklist ID.
     * @param worklistID
     */
    @XmlElement(name = "worklistID")
    public void setWorklistID(long worklistID) {
        _worklistID = worklistID;
    }

    /**
     * This method is used to set the worklist namme.
     * @param name
     */
    @XmlElement(name = "name", required = true)
    public void setName(String name) {
        _name = name;
    }

    /**
     * This method is used to set the worklist description.
     * @param desc
     */
    @XmlElement(name = "desc", required = true)
    public void setDesc(String desc) {
        _desc = desc;
    }

    /**
     * This method is used to fetch the total task count asccociated
     * with the worklist.
     * @return totalTaskCount
     */
    public long getTotalTaskCount() {
        return _total;
    }

    /**
     * This method is used to set the total task count asccociated
     * with the worklist.
     * @param totalTaskCount
     */
    @XmlElement(name = "totalTaskCount")
    public void setTotalTaskCount(long totalTaskCount) {
        _total = totalTaskCount;
    }

    /**
     * This method is used to retrieve the version.
     * @return
     */
    public int getVersion() {
        return _version;
    }

    /**
     * This method is used to set the Version.
     * @param version
     */
    @XmlElement(name = "version")
    public void setVersion(int version) {
        _version = version;
    }

    /**
    * This method calculates the active task count
     * @return activeTaskCount.
     */
    public long getActiveTaskCount() {
        return (_total - _completed);
    }

    /**
     * This method is used to fetch the owning actors of the worklist.
     * @return ownerActors
     */
    public Set<Actor> getOwnerActors() {
        return (_ownerActors == null) ? new HashSet<Actor>() : _ownerActors;
    }

    /**
     * This method is used to set the owning actors of the worklist.
     * @param ownerActors
     */
    public void setOwnerActors(Set<Actor> ownerActors) {
        _ownerActors = ownerActors;
        getOwners().setActors(ownerActors);
    }

    /**
     * This method is used to fetch the access controls of the worklist.
     * @return acls
     */
    public Set<TaskACL> getAcls() {
        return (_assignedTo == null) ? new HashSet<TaskACL>() : getAssignedTo().getACLs();
    }

    /**
     * This method is used to set the access controls of the worklist.
     * @param acls
     */
    public void setAcls(Set<TaskACL> acls) {
        getAssignedTo().setACLs(acls);
    }

    /**
     * This method is used to set the wrapper which contains the
     * owning actors.
     * @param owners
     */
    @XmlElement(name = "owners", type = Actors.class)
    public void setOwners(Actors owners) {

        _owners = owners;
        if (_owners != null) {
            _ownerActors = _owners.getActors();
        }
    }

    /**
     * This method is used to fetch the wrapper which contains the
     * owning actors.
     * @return owners
     */
    public Actors getOwners() {

        if (_owners == null) {
            _owners = new Actors(_ownerActors);
        }
        return _owners;
    }

    /**
     * This method is used to set the wrapper which contains
     * the access controls.
     * @param assignedTo
     */
    @XmlElement(name = "assignedTo", type = TaskACLs.class)
    public void setAssignedTo(TaskACLs assignedTo) {
        _assignedTo = assignedTo;
    }

    /**
     * This method is used to fetch the wrapper which contains
     *  the access controls.
     * @return assignedTo
     */
    public TaskACLs getAssignedTo() {

        if (_assignedTo == null) {
            _assignedTo = new TaskACLs();
        }
        return _assignedTo;
    }

    /**
     * Returns the id of the owning actor of the worklist.
     * @return actorID.
     */
    public long getOwnerID() {

        if (_ownerActors == null || _ownerActors.isEmpty()) {
            return -1;
        }

        return (_ownerActors.iterator().next()).getActorID();
    }

    /**
     * Returns the different status count for a particular worklist.
     * @return statusMap
     */
    public Map<String, Long> getStatusMap() {
        return _statusMap;
    }

    /**
     * This method is used to set the count for different status for a particular worklist.
     * @param statusMap
     */
    public void setStatusMap(Map<String, Long> statusMap) {
        this._statusMap = statusMap;
    }

    /**
     * This method is used to get the tasks associated with a particular worklist 
     * @return _tasks
     *         set of tasks
     */
    public Set< ? > getTasks() {
        return _tasks;
    }

    /**
     * This method is used to set the tasks associated with a particular worklist.
     * @param tasks
     */
    public void setTasks(Set< ? > tasks) {
        this._tasks = tasks;
    }

    /**
     * This method is used to get the audit comment for the worklist.
     * @return auditComment
     */
    public String toAuditComment() {

        StringBuffer sb = new StringBuffer("wl=")
                          .append(_worklistID).append(F_DELIM)
                          .append(_name);

        getOwners().appendAuditComment(sb, "own");
        getAssignedTo().appendAuditComment(sb);

        return sb.append(R_DELIM)
                 .append("wl.desc=")
                 .append(_desc)
                 .toString();
    }

    @Override
    public String toString() {
        return (_worklistID != -1) ? String.valueOf(_worklistID) : getName() + "." + getOwners();
    }

    @Override
    public boolean equals(Object o) {
        return (o == null) ? false : toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * This method is used to validate the worklist is a valid one or not. If it
     * is not valid the corresponding error message is returned. An empty String
     * is returned if it is a valid worklist. The validity for worklists are
     * performed by checking the if the mandatory attributes such as name and
     * description are present and the length of the name and description are
     * valid. The worklist is also confirmed to have atleast one owning actor.
     *
     * @return errorCode
     *          erro code depending on the validation.
     */
    public String validate() {

        if (_name == null || _name.trim().length() == 0) {
            return WorklistEC.EC_NO_WLNAME;
        }
        if (_desc == null || _desc.trim().length() == 0) {
            return WorklistEC.EC_NO_WLDESC;
        }
        if (_name.trim().length() > NAME_LENGTH) {
            return WorklistEC.EC_INVALID_WORKLIST_NAME_LEN;
        }
        if (_desc.trim().length() > DESC_LENGTH) {
            return WorklistEC.EC_INVALID_WORKLIST_DESC_LEN;
        }
        if ((_ownerActors == null) || _ownerActors.size() == 0) {
            return WorklistEC.EC_NO_BELTO;
        }
        return "";
    }

    public List<StatusCountPair> getStatusList() {
        return _statusList;
    }

    @XmlElement(name = "statusCountPair")
    public void setStatusList(List<StatusCountPair> statusList) {

        if (_statusMap != null) {
            for (Map.Entry<String, Long> e : _statusMap.entrySet()) {
                statusList.add(new StatusCountPair(e.getKey(), e.getValue()));
            }
        }
        this._statusList = statusList;
    }
}
