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
package com.mckesson.eig.workflow.worklist;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;


/**
 * This class has common methods which could be used by multiple handlers
 *
 * @author pRasanna
 * @date   Sep 4, 2007
 * @since  HECM 1.0; Sep 4, 2007
 */
public abstract class BasicWorklistActionHandler
implements ActionHandler {

    private static final long serialVersionUID = 1L;

    /**
     * This method fetches the TaskInstance from the ExecutionContext
     *
     * @param context
     * @return
     */
    protected TaskInstance getJBPMTask(ExecutionContext context) {

        TaskMgmtInstance tmi = context.getTaskMgmtInstance();
        return (TaskInstance) tmi.getTaskInstances().iterator().next();
    }

    /**
     * This method deletes the Process Variable from the ContextInstance
     *
     * @param context
     * @param name
     * @return
     */
    protected void deleteProcessVariable(ExecutionContext context, String name) {
        context.getContextInstance().deleteVariable(name);
    }
}
