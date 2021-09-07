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

package com.mckesson.eig.workflow.process.api;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Feb 27, 2009
 * @since  HECM 2.0; Feb 27, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Process", namespace = EIGConstants.TYPE_NS_V1)
public class Process {

    private long _processId;
    private Long _modifiedUserId;
    private Set<ProcessVersion> _processVersions;
    private Set<Actor> _ownerActors;
    private Set<ProcessActorACL> _assignedActors;
    private Actors _owners;
    private ProcessActorACLS _processACLS;
    private Date _modifiedDateTime;
    private Date _createDateTime;

    public long getProcessId() {
        return _processId;
    }

    @XmlElement(name = "processId")
    public void setProcessId(long id) {
        _processId = id;
    }

    public Long getModifiedUserId() {
        return _modifiedUserId;
    }

    public Set<ProcessVersion> getProcessVersions() {
        return _processVersions;
    }

    @XmlElement(name = "processVersions", type = ProcessVersion.class)
    public void setProcessVersions(Set<ProcessVersion> processVersions) {
        _processVersions = processVersions;
    }

    @XmlElement(name = "modifiedUserId")
    public void setModifiedUserId(Long userId) {
        _modifiedUserId = userId;
    }

    public Date getModifiedDateTime() {
        return _modifiedDateTime;
    }

    @XmlElement(name = "modifiedDateTime")
    public void setModifiedDateTime(Date dateTime) {
        _modifiedDateTime = dateTime;
    }

    public Date getCreateDateTime() {
        return _createDateTime;
    }

    @XmlElement(name = "createDateTime")
    public void setCreateDateTime(Date dateTime) {
        _createDateTime = dateTime;
    }

    public Set<Actor> getOwnerActors() {
        return (_ownerActors == null) ? new HashSet<Actor>() : getOwners().getActors();
    }

    public void setOwnerActors(Set<Actor> actors) {

        _ownerActors = actors;
        getOwners().setActors(actors);
    }

    public Set<ProcessActorACL> getAssignedActors() {
        return (_assignedActors == null) ? new HashSet<ProcessActorACL>()
                                         : _assignedActors;
    }

    public void setAssignedActors(Set<ProcessActorACL> processActorACLS) {

        _assignedActors = processActorACLS;
        getProcessACLS().setProcessActorACLS(processActorACLS);
    }

    /**
     * This method is used to retrieve the actors.
     * @return actors
     */
    public ProcessActorACLS getProcessACLS() {

        if (_processACLS == null) {
            _processACLS = new ProcessActorACLS();
        }
        return _processACLS;
    }

    /**
     * This method is used to set the actors.
     * @param actors
     */
    @XmlElement(name = "processACLS", type = ProcessActorACLS.class)
    public void setProcessACLS(ProcessActorACLS actors) {
        _processACLS = actors;
    }

    /**
     * This method is used to retrieve the owning actors.
     * @return owners
     */
    public Actors getOwners() {

        if (_owners == null) {
            _owners = new Actors();
        }
        return _owners;
    }

    /**
     * This method is used to set the owning actors.
     * @param owners
     */
    @XmlElement(name = "owners", type = Actors.class)
    public void setOwners(Actors owners) {
        _owners = owners;
    }

    /**
     * This method returns a string representation of the ProcessVersion List that contains
     * ProcessVersion objects.
     *
     * @return strBuff
     */
    public String toString() {
        StringBuffer theProcess = new StringBuffer("Process[");
        theProcess.append("ProcessId=" + _processId
                + ", ModifiedUserId="  + _modifiedUserId
                + ", ProcessVersions=" + _processVersions
                + ", OwnerActors=" + _ownerActors
                + ", AssignedActors=" + _assignedActors
                + ", Owners=" + _owners
                + ", ProcessACLS=" + _processACLS
                + ", ModifiedDateTime=" + _modifiedDateTime
                + ", CreateDateTime=" + _createDateTime
                + "]");
        return theProcess.toString();
   }


    public ProcessVersion getLatestProcessVersion() {
        Set<ProcessVersion> versions = getProcessVersions();
        if (CollectionUtilities.isEmpty(versions)) {
            return null;
        }
        Iterator<ProcessVersion> iter = versions.iterator();
        ProcessVersion latestVersion = null;
        while (iter.hasNext()) {
            ProcessVersion version = iter.next();
            if (latestVersion == null) {
                latestVersion = version;
            }
            if (version.getVersionId() > latestVersion.getVersionId()) {
                latestVersion = version;
            }

        }
        return latestVersion;
    }
}
