/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. All
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

import java.util.List;

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
@XmlType(name = "ListACLs", namespace = EIGConstants.TYPE_NS_V1)
public class ListACLs
extends BasicWorklistDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 5886142271347667743L;

    /**
     * Holds the privileges of the actors in the respective worklists.
     */
    private List<TaskACL> _taskACLs;

    /**
     * This constructor instantiates this wrapper.
     */
    public ListACLs() {
        super();
    }

    /**
     * This constructor instantiates this wrapper with the privileges.
     */
    public ListACLs(List<TaskACL> taskACLs) {
        this();
        setACLs(taskACLs);
    }

    /**
     * This method is used to retrieve the privileges.
     * @return taskACLs
     */
    public List<TaskACL> getACLs() {
        return _taskACLs;
    }

    /**
     * This method is used to set the privileges.
     * @param taskACLs
     */
    @XmlElement(name = "acls", type = TaskACL.class)
    public void setACLs(List<TaskACL> taskACLs) {
        _taskACLs = taskACLs;
    }
}
