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
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "WLMasterData", namespace = EIGConstants.TYPE_NS_V1)
public class WLMasterData
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 3803803939471568691L;

    /**
    * Holds the available priorities.
    */
    private List<String> _priorityList;

    /**
    * Holds the available task status.
    */
    private List<String> _taskStatusList;
    
    /**
     * Holds default task duration.
     */
    private String _defaultTaskDuration;

    /**
    * Instantiates an WLMasterData.
    */
    public WLMasterData() {
        super();
    }

    /**
    * Instantiates the WLMasterData with the list of priorities and
    * list of available task status.
    * @param priorityList
    * @param taskStatusList
    */
    public WLMasterData(List<String> priorityList, List<String> taskStatusList) {

        this();
        setPriorityList(priorityList);
        setTaskStatusList(taskStatusList);
    }

    /**
    * This method is used to get the list of available priorities.
    * @return priorityList
    */
    public List<String> getPriorityList() {
        return _priorityList;
    }

    /**
    * This method is used to set the list of priorities.
    * @param list
    */
    @XmlElement(name = "priorityList", type = Priority.class)
    public void setPriorityList(List<String> list) {
        _priorityList = list;
    }

    /**
    * This method is used to get the list of status available.
    * @return taskStatusList
    */
    public List<String> getTaskStatusList() {
        return _taskStatusList;
    }

    /**
    * This method is used to set the list of task status.
    * @param statusList
    */
    @XmlElement(name = "taskStatusList", type = TaskStatus.class)
    public void setTaskStatusList(List<String> statusList) {
        _taskStatusList = statusList;
    }

    /**
     * This method is used to get the task duration.
     * @return
     */
    public String getDefaultTaskDuration() {
        return _defaultTaskDuration;
    }

    /**
     * This method is used to set the task duration.
     * @param defaultTaskDuration
     */
    @XmlElement(name = "defaultTaskDuration", required = true)
    public void setDefaultTaskDuration(String defaultTaskDuration) {
        this._defaultTaskDuration = defaultTaskDuration;
    }
}
