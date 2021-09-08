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
package com.mckesson.eig.workflow.worklist;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * @author sahuly
 * @date   Dec 19, 2007
 * @since  HECM 1.0; Sep 4, 2007
 */
public class OverdueTaskHandler
extends BasicWorklistActionHandler {

    private static final long serialVersionUID = 1L;

    /**
     * @see org.jbpm.graph.def.ActionHandler
     * @param ExecutionContext will have execution context details
     *
     * #execute(org.jbpm.graph.exe.ExecutionContext)
     *
     */
    public void execute(final ExecutionContext context)
    throws java.lang.Exception {

        TaskInstance ti = getJBPMTask(context);

        Timer timer = new Timer(ti.getToken());
        timer.setDueDate(ti.getDueDate());
        timer.setTransitionName("mark as overdue");

        SchedulerService schService = (SchedulerService) Services
                .getCurrentService(Services.SERVICENAME_SCHEDULER);
        schService.createTimer(timer);
    }
}
