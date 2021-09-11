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

package com.mckesson.eig.workflow.engine.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.internal.SessionImpl;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.command.CancelProcessInstanceCommand;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.engine.ProcessInstanceEngine;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;
import com.mckesson.eig.workflow.worklist.api.Task;

/**
 * Implements process instance engine. Responsible for executing a process
 * instance.
 *
 * @author eo837ew
 *
 */
public class ProcessInstanceEngineImpl  extends BaseWorkflowEngineImpl
                implements ProcessInstanceEngine {

    /**
     * Holds an instance of process instance this class is wrapping.
     */
    private ProcessInstance _instance = null;

    /**
     * JbpmContext for use throughout the execution of instance.
     */
    private JbpmContext _jbpmContext = null;

    /**
     * Token for use through the execution of this instance.
     */
    private Token _token = null;


    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ProcessInstanceEngineImpl.class);


    /**
     * Default constructor.
     */
    public ProcessInstanceEngineImpl() {

    }

    /**
     * Constructor that accepts ProcessInstance object. This constructor is for creating
     * subprocess instances.
     *
     * @param jbpmContext JbpmContext to be used for executing subprocess.
     * @param processInstance Subprocess instance created by ProcessState node execution.
     */
    private ProcessInstanceEngineImpl(JbpmContext jbpmContext,
                                      ProcessInstance processInstance) {

        if (processInstance == null) {

            throw new WorkflowEngineException(
                    "ProcessInstance cannot be null to create ProcessInstanceEngineImpl",
                    WorkflowEC.INVALID_PROCESS_INSTANCE);
        }

        if (jbpmContext == null) {

            throw new WorkflowEngineException(
                    "JbpmContext cannot be null to create ProcessInstanceEngineImpl",
                    WorkflowEC.INVALID_JBPM_CONTEXT);
        }

        _instance = processInstance;
        _jbpmContext = jbpmContext;

        LOG.debug("Sub Process Instance created successfully: " + _instance.getId());
    }

    /**
     * Constructor that accepts process instance id and JbpmContext.
     */
    public ProcessInstanceEngineImpl(long instanceId, JbpmContext jbpmContext) {

        if (jbpmContext == null) {

            throw new WorkflowEngineException(
                    "JbpmContext cannot be null to create ProcessInstanceEngineImpl",
                    WorkflowEC.INVALID_JBPM_CONTEXT);
        }

        //store jbpmContext for execution of process instance.
        _jbpmContext =  jbpmContext;

        //load process instance
        _instance = _jbpmContext.getProcessInstance(instanceId);

        if (_instance == null) {

            throw new WorkflowEngineException(
                    "ProcessInstance " + instanceId + " is not found",
                    WorkflowEC.INVALID_PROCESS_INSTANCE);
        }

        _token = _instance.getRootToken();
    }

    /**
     * Constructor that accepts token, process instance id and JbpmContext.
     */
    public ProcessInstanceEngineImpl(long token, long instanceId, JbpmContext jbpmContext) {

        this(instanceId, jbpmContext);

        List< ? > tokens = _instance.findAllTokens();
        for (Iterator< ? > itr = tokens.iterator(); itr.hasNext();) {
            Token tkn = (Token) itr.next();
            if (tkn.getId() == token) {
                _token = tkn;
                break;
            }
        }

        if (_token == null) {
            throw new WorkflowEngineException(
                    "Token id " + token  + " not found for process instance " + _instance,
                    WorkflowEC.INVALID_PROCESS_INSTANCE);
        }
    }

    /**
     * Constructor that accepts JbpmContext, processName, and map of variables.
     *
     * @param jbpmContext
     * @param processName
     * @param variables
     */
    public ProcessInstanceEngineImpl(Actor userActor, JbpmContext jbpmContext,
            String processName, Map<String, String> variables) {

        if (jbpmContext == null) {

            LOG.debug("ProcessInstanceEngineImpl is instantiated incorrectly."
                    + " JbpmContext is a required parameter");
            throw new WorkflowEngineException("JbpmContext is required to create process instance",
                                               WorkflowEC.INVALID_JBPM_CONTEXT);
        }

        _jbpmContext = jbpmContext;

        // create new instance of process
        try {

            _instance = _jbpmContext.newProcessInstance(processName);
        } catch (JbpmException e) {

            LOG.error("ProcessInstanceEngineImpl is instantiated incorrectly."
                    + "Instance of \"" + processName + "\" Could not be created");

            throw new WorkflowEngineException(e, WorkflowEC.INVALID_PROCESS);
        }

        // Defense against potential bad instance creation process.
        if (_instance == null) {

            LOG.error("ProcessInstanceEngineImpl is instantiated incorrectly."
                    + "Instance of \"" + processName + "\" Could not be created");
            throw new WorkflowEngineException("Instance of Process: "
                    + processName + " could not be created", WorkflowEC.INVALID_PROCESS);
        }

        // add variables to process instance context and store it locally.
        if (variables != null) {

            _instance.getContextInstance().addVariables(variables);
        }

        LOG.debug("Process Instance created successfully: " + _instance.getId());
    }

    /**
     * Start a work flow process instance and execute it until it reaches a wait
     * state (Subprocess, Task[aka eig.worklist] qualify for wait). Fork is a
     * type of wait only until Join is reached.
     */
    public void startProcessInstance() {

        // if the process has already ended, return.
        if (_instance.hasEnded()) {
            return;
        }

        if (_jbpmContext == null) {
            throw new WorkflowEngineException(
                    "JbpmContext is required to start process instance",
                    WorkflowEC.INVALID_JBPM_CONTEXT);
        }

        _token = _instance.getRootToken();

        runProcessInstance(_token);
    }

    /**
     * Continue work flow process instance execution until it reaches a wait
     * state (Subprocess, Task[aka eig.worklist] qualify for wait). Fork is a
     * type of wait only until Join is reached.
     */
    public void notifyProcessInstance() {

        validateProcessInstance();

        // if the process has already ended, return.
        if (_instance.hasEnded()) {
            return;
        }

        validateJbpmContext();

        runProcessInstance(_token);
    }

    /**
     * This method returns requested process instance.
     *
     * @return ProcessInstance
     */
    public ProcessInstance getProcessInstance(Actor userActor) {

        validate();

        return _instance;
    }

    /**
     * This method resumes requested process instance.
     *
     * @param userActor
     * @param processInstanceId
     */
    public void resumeProcessInstance(Actor userActor) {

        validate();

        // if the process has already ended, or not suspended return.
        if (_instance.hasEnded() || !_instance.isSuspended()) {
            return;
        }

        try {
            _instance.resume();
        } catch (Exception e) {
            throw new WorkflowEngineException(
                    "System could not resume process instance " + _instance.getId()
                    + ": Cause = " + e.getMessage(),
                    WorkflowEC.RESUME_PROCESS_INSTANCE_ERROR);
         }
    }

    /**
     * This method suspends requested process instance.
     *
     * @param userActor
     * @param processInstanceId
     */
    public void suspendProcessInstance(Actor userActor) {

        validate();

        // if the process has already ended or suspended return.
        if (_instance.hasEnded() || _instance.isSuspended()) {
            return;
        }

        try {
            _instance.suspend();
        } catch (Exception e) {
            throw new WorkflowEngineException(
                    "System could not suspend process instance " + _instance.getId()
                    + ": Cause = " + e.getMessage(),
                    WorkflowEC.SUSPEND_PROCESS_INSTANCE_ERROR);
         }
    }

    /**
     * This method terminates a process instance execution.
     *
     * @param userActor
     * @param processInstanceId
     */
    public void terminateProcessInstance(Actor userActor) {

        validate();

        long processInstanceId = _instance.getId();

        // if the process has already ended return.
        if (_instance.hasEnded()) {
            return;
        }

        /**
         * Cancel all subprocesses and the process itself.
         * Canceling subprocesses implies canceling worklist tasks as well.
         */
        try {
            CancelProcessInstanceCommand cpe =
               new CancelProcessInstanceCommand(processInstanceId);
            cpe.setCancelSubProcesses(true);
            cpe.execute(_jbpmContext);
        } catch (Exception e) {
            throw new WorkflowEngineException(
                   "System could not terminate process instance " + processInstanceId
                   + ": Cause = " + e.getMessage(),
                   WorkflowEC.TERMINATE_PROCESS_INSTANCE_ERROR);
        }
    }

    /**
     * This method deletes a process instance.
     *
     * @param userActor
     * @param processInstanceId
     */
    public void deleteProcessInstance(Actor userActor) {

        validate();

        // Custom implementation for deleting a process instance.
        // Don't forget to delete process instance history from HECM_WLIST schema.
    }

    /**
     * Common code to run a process instance from any point that a given token is at.
     */
    private void runProcessInstance(Token token) {

        //retrieve node type
        String nodeType = token.getNode().getClass()
                .getName();
        nodeType = nodeType.substring(nodeType.lastIndexOf('.') + 1);
        Node previousNode = token.getNode();

        try {
            while (!nodeType.endsWith("EndState")) {
                LOG.debug("runProcessInstance>> At: " + nodeType + ":"
                        + token.getNode());

                //when process engine returns true; further processing should stop.
                if (processEngineIPCMessage(nodeType, token, previousNode)) {
                  break;
                }

                //check for blocking node
                if (processBlockingNode(nodeType, token)) {
                    // if current node is blocking and not complete stop processing further.
                    break;
                }

                //save previous node for positioning when execution needs to be blocked.
                previousNode = token.getNode();
                // signal would keep executing parent work flow
                token.signal();

                /**
                 * flush changes after a node is processed.
                 * flush writes to the database; however if there is a transaction
                 * in progress data is not committed.
                 * Atomicity requirement for activities/action handlers.
                 */
                _jbpmContext.getSession().flush();
                _jbpmContext.getSession().getSessionFactory();
                ((SessionImpl)_jbpmContext.getSession()).connection().commit();

                //retrieve current node type
                nodeType = token.getNode().getClass()
                        .getName();
                nodeType = nodeType.substring(nodeType.lastIndexOf('.') + 1);
            }
        } catch (Exception e) {
            LOG.error("runProcessInstance>> JbpmException: " + e.getMessage());

            LOG.debug("runProcessInstance>> suspending process instance " + _instance.getId());
            //handle runtime exception behaviors
            handleRuntimeException(e);

            //Suspend process instance.
            LOG.debug("runProcessInstance>> suspending process instance " + _instance.getId());
            _instance.suspend();
        }

        // if parent instance reached end, mark it as ended.
        if (nodeType.endsWith("EndState")) {
            token.end();
            //insert pih record for completion.
            insertEndProcessPIHRecord();
            LOG.debug("runProcessInstance>> Reached EndState");
        }

        /**
         * flush changes and commit.
         * Scenarios handled: when end node is processed or
         * EngineIPCMessage has interrupted (suspend, resume, terminate).
         */
        try {
            _jbpmContext.getSession().flush();
            ((SessionImpl)_jbpmContext.getSession()).connection().commit();
        } catch (SQLException e) {
            LOG.error("runProcessInstance>> SQLException: " + e.getMessage());
            LOG.debug("runProcessInstance>> suspending process instance " + _instance.getId());
        }
    }

    /**
     * Handle runtime exception behavior.
     *
     * @param e
     */
    private void handleRuntimeException(Exception e) {

      //log process instance history

      //send notification

    }

    /**
     * Processes a blocking node
     *
     * @param nodeType Typed name of blocking node.
     * @return boolean If processing is complete, it returns true.
     */
    private boolean processBlockingNode(String nodeType, Token token)
                    throws Exception {

        if (nodeType.endsWith("ProcessState")
                && "eig.worklist".equals(token.getSubProcessInstance()
                        .getProcessDefinition().getName())) {

            // start worklist sub process
            return startWorklistSubprocess(token);
        } else if (nodeType.endsWith("ProcessState")) {

            // start generic sub process
            return startGenericSubprocess(token);
        } else if (nodeType.endsWith("Fork")) {

            // manage fork
            return processFork(token);
        } else if (nodeType.endsWith("Task")) {
            // processing not complete; always return false
            LOG.debug("Plain JBPM Task node should not have been used in a process");
        }

        // not a blocking node. return false
        return false;
    }

    /**
     * Starts work list subprocess and always returns false as manual task must
     * be completed by user.
     *
     * @return false
     */
    private boolean startWorklistSubprocess(Token token) {

        // if the process has already ended, return.
        if (token.hasEnded()) {
            return false;
        }

        Node worklistNode = token.getNode();

        // work list subprocess is already started by a signal from ProcessState
        // node.
        /**
         * When used as a subprocess, perhaps action handler should perform this logic.
         */
        ProcessInstance wlProcessInstance = token.getSubProcessInstance();

        TaskInstance taskInstance = wlProcessInstance.getTaskMgmtInstance()
                .createStartTaskInstance();

        //Variables are based on following namespace convention
        //<<ProcessName>>.<<ActivityName>>.<<VariableName>>.<<VariableType>>.<<index>>

        String processName = _instance.getProcessDefinition().getName();
        String activityName = worklistNode.getName();

        // set the pooled actor to work list id selected in process definition
        // processid.activityname.worklist.String
        String pooledActor = (String) _instance.getContextInstance().getVariable(processName
                + "." + activityName + "." + "worklist.String");
        taskInstance.setPooledActors(new String[]{pooledActor});

        // set the task name chosen in process definition
        // processid.activityname.taskname.String
        String taskName = (String) _instance.getContextInstance().getVariable(processName
                + "." + activityName + "." + "taskname.String");
        taskInstance.setName(taskName);

        // set due date to current date + due in days.
        // processid.activityname.dueindays.int
        String dueInDays = (String) _instance.getContextInstance().getVariable(processName
                + "." + activityName + "." + "dueindays.int");
        int dueDays = Integer.parseInt(dueInDays);
        Calendar dueDate = Calendar.getInstance();
        dueDate.add(Calendar.DAY_OF_MONTH, dueDays);
        taskInstance.setDueDate(dueDate.getTime());

        // set priority
        //processid.activityname.priority.int
        String priorityString = (String) _instance.getContextInstance().getVariable(processName
                + "." + activityName + "." + "priority.int");
        int priority = Integer.parseInt(priorityString);
        taskInstance.setPriority(priority);

        // set can start early flag
        // processid.activityname.canstartearly.boolean
        String canStartEarlyString = (String) _instance.getContextInstance().getVariable(processName
                + "." + activityName + "." + "canstartearly.boolean");
        boolean canStartEarly = Boolean.parseBoolean(canStartEarlyString);
        taskInstance.setVariable(Task.VK_CAN_START_EARLY, canStartEarly);

        // set actor id to system or manual actor who created process instance
        // processid.activityname.user.String
        String userActor = (String) _instance.getContextInstance().getVariable(processName
                + "." + activityName + "." + "user.String");
        taskInstance.setActorId(userActor);

        LOG.debug("startWorklistSubprocess>> Created task Id:" + taskInstance.getId());

        taskInstance.getToken().signal("release");

        // always return false as processing never completes automatically.
        return false;
    }

    /**
     * Starts a subprocess instance with the same context used by current process.
     *
     * @return true if this subprocess instance has completed without user task assignment.
     */
    private boolean startGenericSubprocess(Token token) {

        // if the process has already ended, return.
        if (token.hasEnded()) {
            return false;
        }

        Node subprocessNode = token.getNode();

        // execute subprocess and return true if completed.

        Token childToken = (Token) token.getChildrenAtNode(subprocessNode).get(0);

        // defense against bad invocation
        if (childToken == null) {
            // there is no subprocess instance at this node. Ignore execution
            // and return true
            return true;
        }

        // create an instance of this class.
        ProcessInstanceEngineImpl subprocessInstance = new ProcessInstanceEngineImpl(
                _jbpmContext, childToken.getProcessInstance());

        // run subprocess
        subprocessInstance.startProcessInstance();

        // return if subprocess has completed or not
        return subprocessInstance.getInstance().hasEnded();
    }

    /**
     * Executes fork and until corresponding join has reached.
     *
     * @return true if this fork has completed without user task assignment.
     */
    private boolean processFork(Token token) throws Exception {
        // if the process has already ended, return.
        if (token.hasEnded()) {
            return false;
        }

        token = _instance.getRootToken();

        //go ahead make updates up to this point.
        //This will prevent stale object state exceptions.
        _jbpmContext.getSession().flush();

        Map< ? , ? > childTokens =  token.getChildren();

        // defense against meaningless process definitions
        if (childTokens == null) {
            // there is no subprocess instance at this node. Ignore execution
            // and return true
            return true;
        }

        Iterator< ? > iter = childTokens.values().iterator();
        boolean outerWaitState = false;

        LOG.debug("processFork>> child token count: " + childTokens.size());

        // Process child tokens one at a time.
        while (iter.hasNext()) {

          Token childToken = (Token) iter.next();

            LOG.debug("processFork>> At Token : " + childToken.getFullName());

            String nodeType = childToken.getNode().getClass().getName();
            nodeType = nodeType.substring(nodeType.lastIndexOf('.') + 1);

            boolean innerWaitState = false;
            try {
                while (!innerWaitState && !nodeType.endsWith("Join")) {

                    LOG.debug("processFork>> At node :" + nodeType + ": " + childToken.getNode());

                    //check for blocking node
                    if (processBlockingNode(nodeType, childToken)) {
                        // if processing blocked node is not complete; stop
                        //processing this token further.
                        outerWaitState = true;
                        innerWaitState = true;
                        LOG.debug("processFork>> outerWaitState, innerWaitState = true");
                    }

                    // signal this token to move to next node
                    childToken.signal();

                    /**
                     * flush changes after a node is processed.
                     * flush writes to the database; however if there is a transaction
                     * in progress data is not committed.
                     * Atomicity requirement for activities/action handlers.
                     */
                    _jbpmContext.getSession().flush();
                    ((SessionImpl)_jbpmContext.getSession()).connection().commit();

                    nodeType = childToken.getNode().getClass()
                            .getName();
                    nodeType = nodeType
                            .substring(nodeType.lastIndexOf('.') + 1);

                    // if this token has reached join. Evaluate if it is OR condition
                    if (nodeType.endsWith("Join")) {
                        LOG.debug("processFork>> At node :" + nodeType + ": "
                                + childToken.getNode());
                        // if it is an OR condition on fork, break on first join.
                        // Defaults to AND on join; therefore processes all tokens.
                        // break;
                    }
                }
            } catch (Exception e) {
                LOG.error("processFork>> JbpmException: " + e.getMessage());
                throw e;
            }
        }
        //return true only if processing is completed for this fork.
        return outerWaitState;
    }

    /**
     * processes ipc message
     *
     * @param nodeType
     * @param token
     * @param previousNode
     * @return boolean returns when further processing should stop.
     */
    private boolean processEngineIPCMessage(String nodeType, Token token, Node previousNode) {
        String ipcMessage = (String) _instance.getContextInstance().
        getVariable("EngineIPC.execution");

        //Stop processing this token if execution should be blocked
        if ("".equals(ipcMessage)) {
            LOG.debug("No ipc message ...");
            return false;
        } else if ("repeat".equals(ipcMessage)) {
            //move token to previous node to make sure current node is re-executed.
            token.setNode(previousNode);
            _instance.getContextInstance().setVariable("EngineIPC.execution", "");
            LOG.debug("Process Instance Engine is repeating execution of node for instance "
                    + _instance.getId() + ", node = " + nodeType
                    + ", due to EngineIPC.execution set to repeat");
            return true;
        } else if ("block".equals(ipcMessage)) {
            //block once only.
            LOG.debug("Process Instance Engine is blocking execution once for instance "
                    + _instance.getId() + ", node = " + nodeType
                    + ", due to EngineIPC.execution set to block");
            _instance.getContextInstance().setVariable("EngineIPC.execution", "");
            return true;
        } else if ("suspend".equals(ipcMessage)) {
            LOG.debug("Process Instance Engine is suspending "
                    + _instance.getId() + ", node = " + nodeType
                    + ", due to user request as EngineIPC.execution set to suspend");
            _instance.getContextInstance().setVariable("EngineIPC.execution", "");
            suspendProcessInstance(null);
            return true;
        } else if ("resume".equals(ipcMessage)) {
            LOG.debug("Process Instance Engine is resuming "
                    + _instance.getId() + ", node = " + nodeType
                    + ", due to user request as EngineIPC.execution set to resume");
            _instance.getContextInstance().setVariable("EngineIPC.execution", "");
            resumeProcessInstance(null);
            return false;
        } else if ("terminate".equals(ipcMessage)) {
            LOG.debug("Process Instance Engine is terminating "
                    + _instance.getId() + ", node = " + nodeType
                    + ", due to user request as EngineIPC.execution set to terminate");
            _instance.getContextInstance().setVariable("EngineIPC.execution", "");
            terminateProcessInstance(null);
            return false;
        }

        return false;
    }

    /**
     * Validates if engine is at least minimally initialized.
     *
     */
    private void validate() {
        validateJbpmContext();
        validateProcessInstance();
    }

    /**
     * Validates if JbpmContext is initialized.
     */
    private void validateJbpmContext() {
        if (_jbpmContext == null) {
            throw new WorkflowEngineException(
                    "JbpmContext is required to resume process instance",
                    WorkflowEC.INVALID_JBPM_CONTEXT);
        }
    }

    /**
     * Validates if ProcessInstance is initialized.
     */
    private void validateProcessInstance() {
        if (_instance == null) {
            throw new WorkflowEngineException(
                    "ProcessInstanceEngineImpl must be initialized correctly",
                    WorkflowEC.INVALID_PROCESS_INSTANCE);
        }
    }

    /**
     * Creates process instance history record for completion.
     */
    private void insertEndProcessPIHRecord() {
        ProcessInstanceHistory pih = new ProcessInstanceHistory();
        pih.setProcessId(Long.parseLong(_instance.getProcessDefinition().getName()));
        pih.setVersionId(Integer.parseInt((String)
                _instance.getContextInstance().getVariable("ProcessVersion")));
        pih.setProcessInstanceId(_instance.getId());
        pih.setEventLevel("Process");
        pih.setEventName("Instance Completed");
        pih.setEventDatetime(new java.util.Date());
        pih.setEventOriginator("Workflow Engine");
        pih.setEventStatus("Completed");
        pih.setCreateDateTime(new java.util.Date());
        ProcessInstanceUtil.createProcessInstanceHistory(pih);
    }
    /**
     * Gets ProcessInstance
     *
     * @return ProcessInstance object stored as attribute.
     */
    public ProcessInstance getInstance() {
        return _instance;
    }

    /**
     * sets ProcessInstance
     *
     * @param pi
     */
    public void setInstance(ProcessInstance pi) {
        _instance = pi;
    }

    /**
     * Gets JbpmContext
     *
     * @return JbpmContext being used by this engine.
     */
    public JbpmContext getJbpmContext() {
        return _jbpmContext;
    }

    /**
     * Sets JbpmContext
     *
     * @param context
     */
    public void setJbpmContext(JbpmContext context) {
        _jbpmContext = context;
    }
}
