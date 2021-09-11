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

package com.mckesson.eig.workflow.worklist;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.JAXBException;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.mckesson.eig.config.exception.NotificationException;
import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.model.NotificationInfo;
import com.mckesson.eig.config.service.NotificationService;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.service.ApplicationPluginService;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskEvent;
import com.mckesson.eig.workflow.worklist.api.TaskEventResult;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.workflow.worklist.service.WorklistServiceImpl;

/**
 * @author OFS
 * @date   Mar 25, 2009
 * @since  eig.workflow; Mar 25, 2009
 */
public class TaskEventNotifier extends BasicWorklistActionHandler {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( TaskEventNotifier.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *
     * @param ExecutionContext will have execution context details 
     *
     * @see org.jbpm.graph.def.ActionHandler#execute(ExecutionContext)
     */
    public void execute(ExecutionContext context) throws Exception {

        if (DO_DEBUG) {
            LOG.debug("execute(context)>>Start");
        }

        TaskInstance jbpmTask = getJBPMTask(context);

        // worklist id for this task
        long wId = Long.parseLong(((PooledActor) jbpmTask.getPooledActors().iterator().next())
                                                         .getActorId());

        WorklistService service = (WorklistService) getBean(WorklistServiceImpl.class.getName());

        Worklist worklist     = service.getWorklist(wId);
        TaskEvent taskEvent   = prepareTaskEvent(worklist);

        if (taskEvent.getAssignedTo() == null) {
        	return;
        }

        TaskEventResult event = processTaskEvent(taskEvent);

        notify(event, taskEvent.getOwner().getAppID());

        if (DO_DEBUG) {
            LOG.debug("execute(context)<<End");
        }
    }

    /** 
     * Helper method used to prepare the notification info and send the mail
     * 
     * @param event
	 * @param appId
     */
    private void notify(TaskEventResult event, long appId) {

        if (DO_DEBUG) {
            LOG.debug("notify(event)>>Start");
        }

        // prepares the mail info
        MailInfo mi = new MailInfo();
        mi.setSubject(event.getEmailSubject());
        mi.setEmailBody(event.getEmailBody());
        mi.setReplyTo(event.getSenderEmail());
        mi.setSenderEmailAddress(event.getSenderEmail());
        mi.setRecipientAddress(event.getRecipientAddress());

        // prepares the notification info
        NotificationInfo ni = new NotificationInfo();
        ni.setSendEmail(true);
        ni.setMailInfo(mi);

        NotificationService service = 
            (NotificationService) SpringUtilities.getInstance()
                                                 .getBeanFactory()
                                                 .getBean(NotificationService.class.getName());

        try {
            service.notify(ni, appId);
        } catch (NotificationException  ne) {

            // As mentioned in the usecase Run-Time Send Notification Action - UC0047A, 
            // Here we can just swallow the exception.
            LOG.info("Unable to send email notification due to '" + ne.getMessage() + "'");
        }

        if (DO_DEBUG) {
            LOG.debug("notify(event)>>Start");
        }
    }


    /**
     * Helper method used to prepare <code>TaskEvent</code>
     * 
     * @param task
     * @param worklist
     * 
     * @return TaskEvent
     */
    private TaskEvent prepareTaskEvent(Worklist worklist) {

        TaskEvent taskEvent = new TaskEvent();
        taskEvent.setStatus("new");
        taskEvent.setWorklistName(worklist.getName());
        taskEvent.setOwner(worklist.getOwnerActors().iterator().next());

        // Personal Worklist
        if (Actor.USER_ENTITY_TYPE == taskEvent.getOwner().getEntityType()) {

        	taskEvent.setAssignedTo(worklist.getAssignedTo());
        	return taskEvent;
        }

        // Group Worklist
        Set<TaskACL> taskACLSet = new HashSet<TaskACL>();
        Set<TaskACL> aclSet     = worklist.getAssignedTo().getACLs();

        Iterator<TaskACL> aclIterator = aclSet.iterator();
        TaskACL taskACL;
        while (aclIterator.hasNext()) {

        	taskACL = aclIterator.next();
        	if (taskACL.getDoEmailAlert()) {
        		taskACLSet.add(taskACL);
        	}
        }

        if (CollectionUtilities.hasContent(taskACLSet)) {

        	worklist.getAssignedTo().setACLs(taskACLSet);
        	taskEvent.setAssignedTo(worklist.getAssignedTo());
        }

        return taskEvent;
    }

    /**
     * Helper method used to process the task event
     * 
     * @param taskEvent
     * @return TaskEventResult
     * 
     */
    private TaskEventResult processTaskEvent(TaskEvent taskEvent) throws JAXBException {

        if (DO_DEBUG) {
            LOG.debug("processTaskEvent(taskEvent)>>Start");
        }

        ApplicationPluginService service = 
            (ApplicationPluginService) getBean(ApplicationPluginService.class.getName());

        TaskEventResult result = service.processTaskEvent(taskEvent);

        if (DO_DEBUG) {
            LOG.debug("processTaskEvent(taskEvent)<<End");
        }
        return result;
    }

    /**
     * Helper method to get the bean from bean factory.
     * 
     * @param beanName Name of the bean
     * @return Instance of the given bean
     */
    private Object getBean(String beanName) {
        return SpringUtilities.getInstance().getBeanFactory().getBean(beanName);
    }
}
