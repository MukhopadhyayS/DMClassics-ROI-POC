/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. All
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
package com.mckesson.eig.workflow.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.instantiation.Delegation;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;


/**
 * This class has common methods which can be used by action handlers
 *
 * @author McKesson
 * @date   January 9, 2009
 * @since  HECM 2.0; January 9, 2009
 */
public abstract class BaseActionHandler
implements ActionHandler {

    private static final long serialVersionUID = 1L;

    public static final String JBPM_ACTIVITY_NAME = "Activity";
    public static final String JBPM_APPLICATION_NAME = "ApplicationName";
    public static final String JBPM_APPLICATION_DESCRIPTION = "ApplicationDescription";
    public static final String JBPM_EMAIL_SERVER_NAME = "EmailServerName";
    public static final String JBPM_PORT_NUMBER = "PortNumber";
    public static final String JBPM_NOTIFICATION_SENDER_EMAIL = "NotificationSenderEmail";
    public static final String JBPM_NOTIFICATION_RECIPIENTS = "NotificationRecipients";
    public static final String JBPM_NOTIFICATION_SUMMARY = "NotificationSummary";
    public static final String JBPM_NOTIFICATION_DETAIL = "NotificationDetail";
    public static final String START_LITERAL = "${";
    public static final String END_LITERAL = "}";

    public static final String PROCESS_VERSION_VARIABLE = "ProcessVersion";
    public static final String PROCESS_VARIABLE_DELIMITER = ".";    
    
    public static final HashMap<String, String> UOM_MAP =
                new HashMap<String, String>() {
                    {
                        put("Days", null);
                        put("Day", null);
                        put("Hours", null);
                        put("Hour", null);
                        put("Minutes", null);
                        put("Minute", null);
                        put("Seconds", null);
                        put("Second", null);
                    }
                };
    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( BaseActionHandler.class);

     /**
     * This method must perform validation of the action handler prior to execution
     *
     * @param
     * @return
     */
    public abstract void validate();

    /**
     * Delegate method for subclassed action handlers.
     *
     * @see ActionHandler
     * @param ExecutionContext will have execution context details
     *
     * #execute(org.jbpm.graph.exe.ExecutionContext)
     */
    public abstract void executeAction(ExecutionContext context);

    /**
     * @see ActionHandler
     * @param ExecutionContext will have execution context details
     *
     * #execute(org.jbpm.graph.exe.ExecutionContext)
     */
    public void execute(ExecutionContext context) {

        /**
         * Start metrics capture
         */
        TimedMetric tm = TimedMetric.start();

        try {
            //set current activity process variable
            context.setVariable(JBPM_ACTIVITY_NAME, context.getToken().getNode().getName());
            executeAction(context);
        } finally {
                tm.logMetric(getClass().getName() + " execute:");
        }
    }


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

    /**
     * Helper method that performs the extraction and replacement of literals with
     * the corresponding JBPM runtime values
     *
     * @param designTime string for literal replacement
     *
     * @return String - after literal replacement with JBPM values has occurred.
     */
    protected String render(final ExecutionContext context, final String designTimeValue) {
        List <String> literals = extractLiterals(designTimeValue);
        return replaceLiterals(context, designTimeValue, literals);
    }

    /**
     * Helper method that traverses designTime string and replaces the literals with
     * the corresponding JBPM value
     *
     * @param designTime string for literal replacement
     * @param list of literal Strings
     *
     * @return String - after literal replacement with JBPM values has occurred.
     */
    private String replaceLiterals(final ExecutionContext context,
                                    final String designTimeValue,
                                    final List<String> literals) {
        StringBuffer runtTimeValue = new StringBuffer(designTimeValue);
        int literalCount = literals.size();
        String jbpmValue = "";
        String currentLiteral = "";
        int currentIndex = 0;
        for (int x = 0; x < literalCount; x++) {
            jbpmValue = (String) context.getVariable(literals.get(x));
            if (!StringUtilities.isEmpty(jbpmValue)) {
                currentLiteral = START_LITERAL + literals.get(x) + END_LITERAL;
                currentIndex = runtTimeValue.indexOf(currentLiteral, currentIndex);
                if (currentIndex != -1) {
                    runtTimeValue.replace(currentIndex,
                                            currentIndex + currentLiteral.length(),
                                            jbpmValue);
                    currentIndex += jbpmValue.length();
                }
            }
        }

        return runtTimeValue.toString();
    }

    /**
     * Helper method that extracts the literals ${literal} from the String and
     * creates a list of them.
     *
     * @param designTimeValue
     *
     * @return Properties - a list of extracted literals.
     */
    private List <String> extractLiterals(final String designTimeValue) {

        List <String> replaceValues = new ArrayList<String>();
        int currentIndex = designTimeValue.indexOf(START_LITERAL);
        if (currentIndex != -1) {
            StringTokenizer tokenizer =
                new StringTokenizer(designTimeValue.substring(currentIndex), START_LITERAL);
            String token = "";
            int endIndex;
            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
                endIndex = token.indexOf(END_LITERAL);
                if (endIndex != -1) {
                    replaceValues.add(token.substring(0, endIndex));
                }
            }
        }
        return replaceValues;
    }

    /**
     * Creates a timer on token with call back delegate and a due date.
     *
     * @param token
     * @param dueDate
     * @param delegateClassName
     */
    protected void createTimer(Token token, Date dueDate, String delegateClassName) {

        Timer timer = new Timer(token);
        timer.setDueDate(dueDate);
        timer.setAction(new Action(new Delegation(delegateClassName)));

        SchedulerService schService = (SchedulerService) Services
                .getCurrentService(Services.SERVICENAME_SCHEDULER);
        schService.createTimer(timer);
    }

    /**
     * Uses EngineIPC variable "EngineIPC.execution" to communicate back to engine.
     *
     * @param context
     */
    protected void blockEngineExecution(ExecutionContext context, String ipcMessage) {

        context.getContextInstance().setVariable("EngineIPC.execution", ipcMessage);
    }

    /**
     * Adds specified time (timeUnits and timeUOM) to current system time.
     * Supports Days, Minutes, Hours, and Seconds for timeUOM.
     *
     *
     * @param timerNumber
     * @param timerUnitOfMeasurement
     * @return resultDate
     */
    protected Date addTime(int timeUnits, String timeUOM) {

        Calendar date = Calendar.getInstance();

        /**
         * Add [Seconds, Minutes, Hours, Days] based on UOM selected.
         */
        if ("Days".equals(timeUOM) || "Day".equals(timeUOM)) {
            date.add(Calendar.DAY_OF_MONTH, timeUnits);
        } else if ("Minutes".equals(timeUOM) || "Minute".equals(timeUOM)) {
            date.add(Calendar.MINUTE, timeUnits);
        } else if ("Hours".equals(timeUOM) || "Hour".equals(timeUOM)) {
            date.add(Calendar.HOUR_OF_DAY, timeUnits);
        } else if ("Seconds".equals(timeUOM) || "Second".equals(timeUOM)) {
            date.add(Calendar.SECOND, timeUnits);
        }

        return date.getTime();
    }
    
    /**
     * Builds a process instance history object and returns it.
     *
     * @param name - The event name
     * @param comment - The event comment
     * @param status - The event status
     * @param context - The execution context
     * @return ProcessInstanceHistory
     */
    protected ProcessInstanceHistory buildProcessInstanceHistory(String name,
            String comment, String status, ExecutionContext context) {
        ProcessInstanceHistory pih = new ProcessInstanceHistory();
        pih.setProcessId(getProcessId(context));
        pih.setVersionId(Integer.parseInt((String)
                context.getVariable(PROCESS_VERSION_VARIABLE)));
        pih.setProcessInstanceId(context.getProcessInstance().getId());
        pih.setEventLevel("Action");
        pih.setEventName(name);
        pih.setEventDatetime(new Date());
        pih.setEventOriginator(context.getToken().getNode().getName());
        pih.setEventComments(comment);
        pih.setCreateDateTime(new Date());
        pih.setEventStatus(status);
        return pih;
    }
    
    /**
     * Convenience method for building and creating pih.
     *
     * @param name - The event name
     * @param comment - The event comment
     * @param status - The event status
     * @param context - The execution context
     * @return void
     */
    protected void createProcessInstanceHistory(String name, String comment, String status,
            ExecutionContext context) {
        ProcessInstanceHistory pih = buildProcessInstanceHistory(name, comment, status, context);
        ProcessInstanceUtil.createProcessInstanceHistory(pih);
    }
    
    /**
     * Get process id from context.
     * 
     * @param context
     * @return
     */
    public long getProcessId(ExecutionContext context) {
    	
    	long processId = Long.parseLong(context.getProcessDefinition().getName());
    	return processId;
    }
    
    /**
     * Get activity name from context.
     * 
     * @param context
     * @return
     */
    public String getActivityName(ExecutionContext context) {
    	
    	String activityName = (String) context.getVariable(JBPM_ACTIVITY_NAME);
    	return activityName;
    }
}
