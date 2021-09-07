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
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * Data structure to provide a list of all assigned privileges for any worklist.
 * This wrapper is required, instead of returning a plain list, to include any
 * business methods as part of data object.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaskACLs", namespace = EIGConstants.TYPE_NS_V1)
public class TaskACLs
extends BasicWorklistDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 5886142271347667743L;

    /**
     * Holds the privileges of the actors in the respective worklists.
     */
    private Set<TaskACL> _taskACLs;

    /**
     * This constructor instantiates this wrapper.
     */
    public TaskACLs() {
        super();
    }

    /**
     * This constructor instantiates this wrapper with the privileges.
     */
    public TaskACLs(Set<TaskACL> taskACLs) {
        setACLs(taskACLs);
    }

    /**
     * This method is used to retrieve the privileges.
     * @return taskACLs
     */
    public Set<TaskACL> getACLs() {
        return _taskACLs;
    }

    /**
     * This method is used to set the privileges.
     * @param taskACLs
     */
    @XmlElement(name = "taskACLs", type = TaskACL.class)
    public void setACLs(Set<TaskACL> taskACLs) {
        _taskACLs = (taskACLs == null) ? new HashSet<TaskACL>() : taskACLs;
    }

    /**
     * This method is used to get the audit comment for this wrapper.
     */
    public void appendAuditComment(StringBuffer sb) {

        if (_taskACLs == null) {
            return;
        }

        sb.append(R_DELIM);

        int count = 1;
        for (Iterator<TaskACL> i = _taskACLs.iterator(); i.hasNext(); count++) {

            sb.append("acl.").append(count).append("=");
            (i.next()).appendAuditComment(sb);
            sb.append(R_DELIM);
        }
        sb.deleteCharAt(sb.length() - 1);
    }
}
