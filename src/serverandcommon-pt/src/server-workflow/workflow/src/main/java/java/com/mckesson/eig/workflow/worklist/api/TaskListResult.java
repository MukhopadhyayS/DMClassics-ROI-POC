/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.IDListResult;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Dec 19, 2007
 * @since  HECM 1.0; Dec 19, 2007
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaskListResult", namespace = EIGConstants.TYPE_NS_V1)
public class TaskListResult
extends BasicWorklistDO {

    private IDListResult _idListResult;

    private List<Task> _processedTasksList;

    private List<Task> _unProcessedTasksList;

    /**
     * This method is used to get the list of processed and unprocessed tasks IDs.
     * @return idListResult
     */
    public IDListResult getIdListResult() {
        return _idListResult;
    }

    /**
     * This method is used to get the list of error codes, processed and unprocessed tasksIDs.
     * @param listResult
     */
    @XmlElement(name = "idListResult", type = IDListResult.class)
    public void setIdListResult(IDListResult listResult) {
        _idListResult = listResult;
    }

    /**
     * This method is used to get the list of processed tasks.
     * @return taskList
     */
    public List<Task> getProcessedTasksList() {
        return _processedTasksList;
    }

    /**
     * This method is used to set the list of processed tasks.
     * @param ilistst
     */
    @XmlElement(name = "processedTasksList", type = Task.class)
    public void setProcessedTasksList(List<Task> processedTasksList) {
        _processedTasksList = processedTasksList;
    }

    /**
     * This method is used to get the list of unprocessed tasks.
     * @return
     */
    public List<Task> getUnProcessedTasksList() {
        return _unProcessedTasksList;
    }

    /**
     * This method is used to set the list of unprocessed tasks.
     * @param unProcessedTasksList
     */
    @XmlElement(name = "unProcessedTasksList", type = Task.class)
    public void setUnProcessedTasksList(List<Task> unProcessedTasksList) {
        _unProcessedTasksList = unProcessedTasksList;
    }
}
