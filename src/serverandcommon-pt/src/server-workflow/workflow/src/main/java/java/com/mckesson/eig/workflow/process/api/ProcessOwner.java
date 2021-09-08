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


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessActor")
public class ProcessOwner implements Serializable {

    private long _processId;

    private Actor _actor;


    private Date _createdTS;

    private Date _updatedTS;

    public ProcessOwner() {
    }

    public ProcessOwner(long processID, Actor actor) {
        _processId = processID;
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
