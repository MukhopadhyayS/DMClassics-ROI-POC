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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 *
 * This class represents the Data structure to provide a list of Task instances.
 * This wrapper is required, instead of returning a plain list,
 * to include any business methods as part of data object. Also this
 * provides scope for indexing the Task instances based on various fields.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaskList", namespace = EIGConstants.TYPE_NS_V1)
public class TaskList
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 9036604088669794376L;

    /**
    * Holds the list of tasks in the respective worklist.
    */
    private List<Task> _tasks;

    private long _size;

    /**
    * Instantiates an task list with the list of tasks.
    * @param taskList
    */
    public TaskList(List<Task> taskList) {
        super();
        this._tasks = taskList;
    }

    /**
    * Instantiates an task list.
    */
    public TaskList() {
        super();
    }

    /**
    * This method is used to get the list of tasks in the respective worklist.
    * @return tasks
    */
    public List<Task> getTasks() {
        return _tasks;
    }

    /**
    * This method is used to set the list of tasks in the respective worklist.
    * @param taskList
    */
    @XmlElement(name = "tasks", type = Task.class)
    public void setTasks(List<Task> taskList) {
        this._tasks = taskList;
    }

    public long getSize() {
        return _size;
    }

    @XmlElement(name = "size")
    public void setSize(long size) {
        this._size = size;
    }
}
