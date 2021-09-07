/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.process.api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * Data Transfer class representing a workflow process with minimal information.
 *
 * @author McKesson
 * @version 1.0
 * @created 19-Feb-2009 01:24:13 PM
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessInfo", namespace = EIGConstants.TYPE_NS_V1)
public class ProcessInfo implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
	private static final long serialVersionUID = -9173724253719115902L;

    private long _processId;

    private Actors _owners;
    private ProcessActorACLS _assigned;

    private Set<Actor> _ownerActors;
    private Set<ProcessActorACL> _assignedActors;
    private Set<ProcessVersionInfo> _processVersionInfo;

    public ProcessInfo() {

        _owners = new Actors();
        _assigned = new ProcessActorACLS();
    }

    /**
     * This method is used to retrieve the process Id value.
     * 
     * @return _processId
     */
    public long getProcessId() {
        return _processId;
    }

   /**
    * This method is used to set the process Id value.
    * 
    * @param processId
    */
    @XmlElement(name = "processId")
    public void setProcessId(long processId) {
        _processId = processId;
    }

    /**
     * This method is used to retrieve the owning actors.
     * 
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
     * 
     * @param owners
     */
    @XmlElement(name = "owners", type = Actors.class)
    public void setOwners(Actors owners) {
        _owners = owners;
    }

    /**
     * @return processActor
     */
    public ProcessActorACLS getAssigned() {

        if (_assigned == null) {
            _assigned = new ProcessActorACLS();
        }
        return _assigned;
    }

    /**
     * @param processActor
     */
    @XmlElement(name = "assigned", type = ProcessActorACLS.class)
    public void setAssigned(ProcessActorACLS processActor) {
        this._assigned = processActor;
    }

    /**
     * @return
     */
    public Set<Actor> getOwnerActors() {

        return (_ownerActors == null) 
                ? new HashSet<Actor>() 
                : getOwners().getActors();
    }

    /**
     * @param actors
     */
    public void setOwnerActors(Set<Actor> actors) {

        _ownerActors = actors;
        getOwners().setActors(actors);
    }

    /**
     * @return assignedActors
     */
    public Set<ProcessActorACL> getAssignedActors() {

        return (_assignedActors == null) 
                ? new HashSet<ProcessActorACL>() 
                : getAssigned().getProcessActorACLS();
    }

    /**
     * @param assignedActors
     */
    public void setAssignedActors(Set<ProcessActorACL> assignedActors) {

        this._assignedActors = assignedActors;
        getAssigned().setProcessActorACLS(assignedActors);
    }

    /**
     * @return processInfo
     */
    public Set<ProcessVersionInfo> getProcessVersionInfo() {
        return _processVersionInfo;
    }

    /**
     * @param processInfo
     */
    public void setProcessVersionInfo(Set<ProcessVersionInfo> processInfo) {
        this._processVersionInfo = processInfo;
    }

    /**
     * This method returns a string representation of the Process List that contains
     * Process objects.
     *
     * @return strBuff
     */
    public String toString() {
        StringBuffer theProcess = new StringBuffer("ProcessInfo[");
        theProcess.append("processId=" + _processId
                + ", owners=set of Actors"
                + ", actors=set of Actors"
                + "]");

        return theProcess.toString();
   }
}
