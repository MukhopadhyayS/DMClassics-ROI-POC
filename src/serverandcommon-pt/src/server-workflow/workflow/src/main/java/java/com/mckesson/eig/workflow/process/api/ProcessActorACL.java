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

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actor;

/**
 * @author sahuly
 * @date   Feb 13, 2009
 * @since  HECM 1.0; Feb 13, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessActorACL")
public class ProcessActorACL implements Serializable {

    private long _processId;
    
    private Actor _actor;
    
    private String _permissionName;
    
    //private int _version;
    
    private Date _createdTS;

    private Date _updatedTS;
    
    public ProcessActorACL() {
    }

    public ProcessActorACL(long processID, String permissionName, Actor actor) {
        
        _processId = processID;
        _permissionName = permissionName;
        _actor = actor;
    }

    public long getProcessId() {
        return _processId;
    }
    
    @XmlElement(name = "processId")
    public void setProcessId(long id) {
        _processId = id;
    }

    public Actor getActor() {
        return _actor;
    }
    
    @XmlElement(name = "actor")
    public void setActor(Actor actor) {
        this._actor = actor;
    }

    public String getPermissionName() {
        return _permissionName;
    }

    @XmlElement(name = "permissionName")
    public void setPermissionName(String name) {
        _permissionName = name;
    }

//    public int getVersion() {
//        return _version;
//    }
//    
//    @XmlElement(name = "version")
//    public void setVersion(int version) {
//        this._version = version;
//    }

    public Date getCreatedTS() {
        return _createdTS;
    }

    public void setCreatedTS(Date createdts) {
        _createdTS = createdts;
    }

    public Date getUpdatedTS() {
        return _updatedTS;
    }

    public void setUpdatedTS(Date updatedts) {
        _updatedTS = updatedts;
    }

}
