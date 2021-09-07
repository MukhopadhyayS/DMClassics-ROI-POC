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

package com.mckesson.eig.workflow.worklist.api;

import com.mckesson.eig.workflow.api.Actor;

/**
 * @author OFS
 * @date   Mar 26, 2009
 * @since  HecmServices;
 */
public class TaskEvent {

    private String _status;
    private String _worklistName;

    private Actor _owner;
    private TaskACLs _assignedTo;

    public String getWorklistName() {
        return _worklistName;
    }
    public void setWorklistName(String worklistName) {
        _worklistName = worklistName;
    }
    public String getStatus() {
        return _status;
    }
    public void setStatus(String status) {
        this._status = status;
    }
    public Actor getOwner() {
        return _owner;
    }
    public void setOwner(Actor owner) {
        this._owner = owner;
    }
    public TaskACLs getAssignedTo() {
        return _assignedTo;
    }
    public void setAssignedTo(TaskACLs assignedTo) {
        this._assignedTo = assignedTo;
    }
}
