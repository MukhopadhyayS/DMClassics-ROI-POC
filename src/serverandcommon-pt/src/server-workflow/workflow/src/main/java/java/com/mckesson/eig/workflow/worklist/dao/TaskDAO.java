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
package com.mckesson.eig.workflow.worklist.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.IDListResult;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.dao.AbstractWorkflowDAO;
import com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACLResolved;
import com.mckesson.eig.workflow.worklist.api.TaskList;
import com.mckesson.eig.workflow.worklist.api.TaskListResult;
import com.mckesson.eig.workflow.worklist.api.WLMasterData;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.api.WorklistReportDO;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 *
 * This class implements the business methods to view and manage tasks.
 */
public class TaskDAO
extends AbstractWorkflowDAO {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( TaskDAO.class);

    private static final String T_STATUS_ID     = "T_STATUS_ID";
    private static final String T_WORKLIST_ID   = "T_WORKLIST_ID";
    private static final String T_ACTOR_ID      = "T_ACTOR_ID";
    private static final String ASCENDING       = " asc";
    private static final String DESCENDING      = " desc";
    public  static final String VK_COMMENTS     = "comments";

    private static final String COMPLETE_TASK   = "complete";
    private static final String DEACTIVATE_TASK = "deactivate";
    private static final String OWN_TASK        = "own";
    private static final String RELEASE         = "release";
    private static final String DISOWN_TASK     = "return to worklist";
    private static final String REASSIGN_TASK   = "reassign";
    private static final String UPDATE_TASK     = "update";

    private static WLMasterData _wlMasterData;

    private static final String CSV_DELIM       = ",";

    private static final int TASK_NAME          = 1;
    private static final int TASK_PRIORITY      = 2;
    private static final int TASK_START_DATE    = 3;
    private static final int TASK_DUE_DATE      = 4;
    private static final int TASK_STATUS        = 5;
    private static final int WORKLIST           = 6;

    private static final int APP_ID             = 1;
    private static final int ENTITY_TYPE        = 2;
    private static final int ENTITY_ID          = 3;

    private static final String WORKFLOW_PROPERTY_FILE 
                                        = "/com/mckesson/eig/workflow/task.properties";

    private static final String TASK_DURATION_KEY = "task.duration";

    /**
     * This constructor instantates this implementation of the DAO.
     */
    public TaskDAO() {
        super();
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getAssignedWorklistTasks(com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria, long,
     *                            com.mckesson.eig.workflow.api.SortOrder)
     */
    public TaskList getAssignedTasks(final AssignedTasksCriteria assignedTasksCriteria,
                                     final long index,
                                     final long count,
                                     final SortOrder sortOrder) {

        final String logSourceMethod =
            "getAssignedWorklistTasks(assignedTasksCriteria, index, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer hqlQuery =
                new StringBuffer()
                    .append(" SELECT ti FROM org.jbpm.taskmgmt.exe.TaskInstance AS ti \n ");

        String joinQuery =
                new StringBuffer()
                    .append(" INNER JOIN ti.pooledActors AS pa  \n ")
                    .append(" INNER JOIN ti.token AS t          \n ")
                    .append(" INNER JOIN t.node AS n            \n ")
                    .append(" WHERE pa.actorId = :T_WORKLIST_ID \n ")
                    .append(" AND n.name IN (:T_STATUS_ID)      \n ").toString();

        if (sortOrder != null) {
            hqlQuery.append(getSortQuery(sortOrder, joinQuery));
        }

        List list = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List resultList =
                    s.createQuery(hqlQuery.toString())
                     .setParameterList(T_STATUS_ID, assignedTasksCriteria.getStatusIDs())
                     .setLong(T_WORKLIST_ID,        assignedTasksCriteria.getWorklistID())
                     .setFirstResult((int) index)
                     .setMaxResults((int) count)
                     .setFetchSize((int) count)
                     .list();

                return toTasks(resultList);
            }
        });

        TaskList taskList = new TaskList();
        taskList.setSize(list.size());
        taskList.setTasks(list);

        LOG.debug(logSourceMethod + "<<End");
        return taskList;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getCreatedTasks(long, com.mckesson.eig.workflow.api.Actor,
     *                     long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public TaskList getCreatedTasks(final CreatedTasksCriteria createdTasksCriteria,
                                    final long index,
                                    final long count,
                                    final SortOrder sortOrder) {

        final String logSourceMethod =
            "getCreatedTasks(createdTasksCriteria, index, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        final Actor createdBy         = loadActor(createdTasksCriteria.getCreatedBy());
        final StringBuffer hqlQuery   =
            new StringBuffer()
                .append(" SELECT ti FROM org.jbpm.taskmgmt.exe.TaskInstance AS ti \n ");

        String joinQuery = new StringBuffer()
                        .append(" INNER JOIN ti.pooledActors AS pa    \n ")
                        .append(" INNER JOIN ti.token        AS t     \n ")
                        .append(" INNER JOIN t.node          AS n     \n ")
                        .append(" WHERE pa.actorId = :T_WORKLIST_ID   \n ")
                        .append(" AND ti.actorId   = :T_ACTOR_ID      \n ")
                        .append(" AND n.name IN (:T_STATUS_ID)        \n ")
                        .toString();

        if (sortOrder != null) {
            hqlQuery.append(getSortQuery(sortOrder, joinQuery));
        }

        List createdTasks = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list =
                    s.createQuery(hqlQuery.toString())
                     .setParameterList(T_STATUS_ID, createdTasksCriteria.getStatusIDs())
                     .setLong(T_WORKLIST_ID,        createdTasksCriteria.getWorklistID())
                     .setString(T_ACTOR_ID,         createdBy.toString())
                     .setFirstResult((int) index)
                     .setMaxResults((int) count)
                     .setFetchSize((int) count)
                     .list();

                return toTasks(list);
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return new TaskList(createdTasks);
    }

    /**
     *
     * @param sortOrder
     * @param joinQuery
     * @return
     */
    private String getSortQuery(SortOrder sortOrder, String joinQuery) {

        StringBuffer sortQuery = new StringBuffer();
        if (sortOrder.getAttr().getName().equals("statusID")) {

            sortQuery.append(" ,com.mckesson.eig.workflow.worklist.api.TaskStatus AS ts \n ")
                     .append(joinQuery)
                     .append(" AND ts.statusID = n.name \n ")
                     .append(" ORDER BY ts.sortOrder    \n ");

        } else if (sortOrder.getAttr().getName().equals("priorityID")) {

            sortQuery.append(" , com.mckesson.eig.workflow.worklist.api.Priority as p \n ")
                     .append(joinQuery)
                     .append(" AND p.priorityID = ti.priority \n ")
                     .append(" ORDER BY p.sortOrder           \n ");
        } else {

            String sortAttribute = sortOrder.getAttr().getName();
            sortQuery.append(joinQuery)
                     .append(" ORDER BY ti.")
                     .append(sortAttribute.equals("taskName")
                          ? "name"        : (sortAttribute.equals("taskDescription"))
                          ? "description" : (sortAttribute.equals("startDate"))
                          ? "start"       : "dueDate");
        }
        sortQuery.append(sortOrder.getIsDesc() ? DESCENDING : ASCENDING);

        return sortQuery.toString();
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getWorklistsMasterData()
     */
    public WLMasterData getWorklistsMasterData() {

        final String logSourceMethod = "getWorklistsMasterData()";
        LOG.debug(logSourceMethod + ">>Start");

        if (_wlMasterData != null) {
            return _wlMasterData;
        }

        synchronized (WLMasterData.class) {
            _wlMasterData = getWLMasterData();
        }
        _wlMasterData.setDefaultTaskDuration(getDefaultTaskDuration());

        LOG.debug(logSourceMethod + "<<End");
        return _wlMasterData;
    }

    /**
     * Method to read task duration from property file.
     * @return task duration.
     */
    private String getDefaultTaskDuration() {
        
        InputStream is = TaskDAO.class.getResourceAsStream(WORKFLOW_PROPERTY_FILE);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new WorkflowException(e);
        }
        return properties.getProperty(TASK_DURATION_KEY);
    }

    /**
     * list of available status and priorities.
     *
     * @return _wlMasterData
     *         list of available status and priorities.
     */
    private WLMasterData getWLMasterData() {

        List taskStatusList = getHibernateTemplate().findByNamedQuery("getTaskStatusList");
        List priorityList   = getHibernateTemplate().findByNamedQuery("getPriorityList");
        return new WLMasterData(priorityList, taskStatusList);
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getAssignedWorklistTasksCount(com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria)
     *
     */
    public long getAssignedTasksCount(final AssignedTasksCriteria
                                            assignedTaskcriteria) {

        final String logSourceMethod =
            "getAssignedWorklistTasksCount(assignedTaskcriteria)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT COUNT(ti.id) FROM                 \n ")
                .append(" org.jbpm.taskmgmt.exe.TaskInstance AS ti \n ")
                .append(" INNER JOIN ti.pooledActors         AS pa \n ")
                .append(" INNER JOIN ti.token                AS t  \n ")
                .append(" INNER JOIN t.node                  AS n  \n ")
                .append(" WHERE pa.actorId = :T_WORKLIST_ID        \n ")
                .append(" AND n.name IN (:T_STATUS_ID)             \n ");

        long count = ((Long) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createQuery(hqlQuery.toString())
                        .setLong(T_WORKLIST_ID,        assignedTaskcriteria.getWorklistID())
                        .setParameterList(T_STATUS_ID, assignedTaskcriteria.getStatusIDs())
                        .uniqueResult();
            }
        })).intValue();

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getCreatedTasksCount(com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria)
     *
     */
    public long getCreatedTasksCount(final CreatedTasksCriteria createdTasksCriteria) {

        final String logSourceMethod =
                "getAssignedWorklistTasksCount(createdTasksCriteria)";
        LOG.debug(logSourceMethod + ">>Start");

        final Actor createdBy       = loadActor(createdTasksCriteria.getCreatedBy());
        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT COUNT(ti) FROM                    \n ")
                .append(" org.jbpm.taskmgmt.exe.TaskInstance AS ti \n ")
                .append(" INNER JOIN ti.pooledActors  AS pa        \n ")
                .append(" INNER JOIN ti.token         AS t         \n ")
                .append(" INNER JOIN t.node           AS n         \n ")
                .append(" WHERE pa.actorId   = :T_WORKLIST_ID      \n ")
                .append(" AND ti.actorId     = :T_ACTOR_ID         \n ")
                .append(" AND n.name IN (:T_STATUS_ID)             \n ");

        long count = ((Long) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

               return s.createQuery(hqlQuery.toString())
                       .setLong(T_WORKLIST_ID,        createdTasksCriteria.getWorklistID())
                       .setString(T_ACTOR_ID,         createdBy.toString())
                       .setParameterList(T_STATUS_ID, createdTasksCriteria.getStatusIDs())
                       .uniqueResult();
            }
        })).intValue();

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }

    /**
     * This method converts the list of JBPM tasks to EIG tasks.
     *
     * @param jbpmTasks
     *              list of jbpm task instances
     * @return worklistTasks
     *              list of eig tasks.
     */
    private List toTasks(List jbpmTasks) {

        int size = jbpmTasks.size();
        List<Task> worklistTasks = new ArrayList<Task>(size);

        for (int i = 0; i < size; i++) {
            worklistTasks.add(new Task((TaskInstance) jbpmTasks.get(i)));
        }
        return worklistTasks;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #createTask(com.mckesson.eig.workflow.worklist.api.Task, Actor)
     *
     */
    public long createTask(final Task task, Actor owner) {

        final String logSourceMethod = "createTask(task, owner)";
        LOG.debug(logSourceMethod + ">>Start");

        validateWorklist(task.getWorklistID());
        loadActor(task.getTaskCreator());
        loadActor(owner);

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        long jbpmTaskID = 0;

        ProcessInstance eigTask = jbpmContext.newProcessInstanceForUpdate(WORKLIST_PROCESS_NAME);
        TaskInstance jbpmTask   = eigTask.getTaskMgmtInstance().createStartTaskInstance();

        jbpmTask.setPooledActors(new String[]{String.valueOf(task.getWorklistID())});

        jbpmTask.setName(task.getTaskName());
        jbpmTask.setDescription(task.getTaskDescription());
        jbpmTask.setDueDate(task.getEndDate());

        jbpmTask.setActorId(task.getTaskCreator().toString());

        jbpmTask.setPriority((int) task.getPriorityID());

        jbpmTask.setVariable(Task.VK_CAN_START_EARLY, task.isCanStartEarly());

        jbpmTaskID = jbpmTask.getId();

        ProcessInstance processInstance =
                        jbpmContext.getTaskInstance(jbpmTaskID).getProcessInstance();
        processInstance.signal();

        if (!jbpmContext.getTaskInstance(jbpmTaskID)
                        .getToken()
                        .getNode()
                        .getName()
                        .equals(task.getStatusID())) {
            processInstance.signal(RELEASE);
        }

        jbpmContext.close();

        // JBPM sets start date to null, so start date is updated through HQL
        updateStartDateForTask(task.getStartDate(), jbpmTaskID);

        LOG.debug(logSourceMethod + "<<End");
        return jbpmTaskID;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getTaskDetails(long)
     *
     */
    public Task getTaskDetails(long taskID, Actor owner) {

        final String logSourceMethod = "getTaskDetails(taskID, owner)";
        LOG.debug(logSourceMethod + ">>Start");
        loadActor(owner);

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        TaskInstance ti;
        Task task;
        try {

            ti = jbpmContext.loadTaskInstance(taskID);
            task = new Task(ti);

            jbpmContext.close();
        } catch (Exception e) {

            WorklistException we =  new WorklistException(WorklistEC.MSG_TASK_NOT_EXIST,
                                                          WorklistEC.EC_TASK_NOT_EXIST);
            LOG.error(we);
            throw we;
        }
        LOG.debug(logSourceMethod + "<<End");
        return task;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #ownTasks(long, long[], Actor)
     */
    public TaskListResult ownTasks(long worklistID, long[] taskIDs, Actor owner) {

        final String logSourceMethod =
            "ownTasks(long worklistID, long[] taskIDs, Actor owner)";
        LOG.debug(logSourceMethod + ">>Start taskIDList.size() " + taskIDs.length);

        loadActor(owner);
        String ownerID = owner.toString();

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();
        for (long taskID : taskIDs) {
            taskIDList.add(taskID);
        }

        ArrayList<Task> processedTasks      = new ArrayList<Task>();
        ArrayList<Long> processedTaskIDs    = new ArrayList<Long>();
        ArrayList<Task> unprocessedTasks    = new ArrayList<Task>();
        ArrayList<Long> unprocessedTaskIDs  = new ArrayList<Long>();
        ArrayList<String> errorCodes        = new ArrayList<String>();

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);
        String taskWorklistID;

        LOG.debug("taskInstanceList.size() " + taskInstanceList.size());
        for (TaskInstance ti : taskInstanceList) {

            taskWorklistID = getWorklistID(ti);
            if (!String.valueOf(worklistID).equals(taskWorklistID)) {
                
                addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                errorCodes.add(WorklistEC.EC_TASK_NOT_FOUND_IN_WL);
                continue;
            } else if (new Date().before(ti.getStart())
                       && !(Boolean) ti.getVariable(Task.VK_CAN_START_EARLY)) {
                    
                addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                errorCodes.add(WorklistEC.EC_INVALID_STAT_FOR_OPER);
                continue;
            }

            try {

                WsSession.setSessionData("CURRENT_DATE_TIME", new Date());
                signal(ti, OWN_TASK);
                if (ti.getDueDate().before((Date) WsSession.getSessionData("CURRENT_DATE_TIME"))) {
                    signal(ti, "mark as overdue");
                }
                ti.getContextInstance().setVariable(Task.VK_OWNED_BY, ownerID);
                jbpmContext.save(ti);

                addTaskResult(ti, processedTasks, processedTaskIDs);
            } catch (Exception e) {

                LOG.error(e);
                addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                errorCodes.add(e instanceof WorkflowException
                    ? ((WorkflowException) e).getErrorCode()
                    : e.getMessage());
            }
        }
        setUnprocessedTasks(processedTaskIDs,
                            unprocessedTasks,
                            unprocessedTaskIDs,
                            errorCodes,
                            taskIDList);
        jbpmContext.close();

        TaskListResult taskList = setTaskListResult(processedTasks,
                                                    unprocessedTasks,
                                                    processedTaskIDs,
                                                    unprocessedTaskIDs,
                                                    errorCodes);

        LOG.debug(logSourceMethod + "<<End processedTaskIDs.size() " + processedTaskIDs.size());
        return taskList;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #disownTasks(long[], Actor)
     */
    public TaskListResult disownTasks(long[] taskIDs, Actor owner) {

        final String logSourceMethod = "disownTasks(taskIDs, owner)";
        LOG.debug(logSourceMethod + ">>Start taskIDList.size() " + taskIDs.length);

        loadActor(owner);
        String ownerID = owner.toString();

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();
        for (long taskID : taskIDs) {
            taskIDList.add(taskID);
        }

        ArrayList<Task> processedTasks      = new ArrayList<Task>();
        ArrayList<Long> processedTaskIDs    = new ArrayList<Long>();
        ArrayList<Task> unprocessedTasks    = new ArrayList<Task>();
        ArrayList<Long> unprocessedTaskIDs  = new ArrayList<Long>();
        ArrayList<String> errorCodes        = new ArrayList<String>();

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);

        String ownedBy;

        for (TaskInstance ti : taskInstanceList) {

            ownedBy = ((String) ti.getContextInstance().getVariable(Task.VK_OWNED_BY));
            if (StringUtilities.hasContent(ownedBy) && ownedBy.equals(ownerID)) {

                try {

                    ti.getContextInstance().setVariable(Task.VK_OWNED_BY, 
                                                        StringUtilities.EMPTYSTRING);
                    signal(ti, DISOWN_TASK);
                    jbpmContext.save(ti);
                    addTaskResult(ti, processedTasks, processedTaskIDs);
                } catch (Exception e) {

                    LOG.error(e);
                    addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                    errorCodes.add(e instanceof WorkflowException
                               ?  ((WorkflowException) e).getErrorCode()
                               :  e.getMessage());
                }
            } else {

                addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                errorCodes.add(WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
            }
        }

       setUnprocessedTasks(processedTaskIDs,
                           unprocessedTasks,
                           unprocessedTaskIDs,
                           errorCodes,
                           taskIDList);
       jbpmContext.close();

       TaskListResult taskList = setTaskListResult(processedTasks,
                                                   unprocessedTasks,
                                                   processedTaskIDs,
                                                   unprocessedTaskIDs,
                                                   errorCodes);

       LOG.debug(logSourceMethod + "<<End processedTaskIDs.size() " + processedTaskIDs.size());
       return taskList;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #completeTasks(TaskList, Actor)
     */
    public TaskListResult completeTasks(TaskList taskList, Actor owner) {

        final String logSourceMethod = "completeTasks(taskList, owner)";
        LOG.debug(logSourceMethod + ">>Start getTasks().size() " + taskList.getTasks().size());

        loadActor(owner);
        String ownerID = owner.toString();

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();
        HashMap<Long, Task> tasks = new HashMap<Long, Task>();

        Task task;
        for (int i = taskList.getTasks().size(); --i >= 0;) {

            task = taskList.getTasks().get(i);

            taskIDList.add(task.getTaskID());
            tasks.put(task.getTaskID(), task);
        }

        ArrayList<Task> processedTasks      = new ArrayList<Task>();
        ArrayList<Long> processedTaskIDs    = new ArrayList<Long>();
        ArrayList<Task> unprocessedTasks    = new ArrayList<Task>();
        ArrayList<Long> unprocessedTaskIDs  = new ArrayList<Long>();
        ArrayList<String> errorCodes        = new ArrayList<String>();

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);
        String ownedBy;

        for (TaskInstance ti : taskInstanceList) {

            ownedBy = ((String) ti.getContextInstance().getVariable(Task.VK_OWNED_BY));
            if (StringUtilities.hasContent(ownedBy) && ownedBy.equals(ownerID)) {

                try {

                    signal(ti, COMPLETE_TASK);
                    ti.setVariable(VK_COMMENTS, tasks.get(ti.getId()).getComments());
                    jbpmContext.save(ti);
                    addTaskResult(ti, processedTasks, processedTaskIDs);
                } catch (Exception e) {

                    LOG.error(e);
                    addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                    errorCodes.add(e instanceof WorkflowException
                               ?  ((WorkflowException) e).getErrorCode()
                               :    e.getMessage());
                }
            } else { // invalid owner

                addTaskResult(ti, unprocessedTasks, unprocessedTaskIDs);
                errorCodes.add(WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
            }
        }

       setUnprocessedTasks(processedTaskIDs,
                           unprocessedTasks,
                           unprocessedTaskIDs,
                           errorCodes,
                           taskIDList);
       jbpmContext.close();

       TaskListResult taskListResult = setTaskListResult(processedTasks,
                                                         unprocessedTasks,
                                                         processedTaskIDs,
                                                         unprocessedTaskIDs,
                                                         errorCodes);

       LOG.debug(logSourceMethod + "<<End processedTaskIDs.size() " + processedTaskIDs.size());
       return taskListResult;
    }


    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #deleteTasks(long, long[], com.mckesson.eig.workflow.api.Actor)
     *
     */
    public TaskListResult deleteTasks(long worklistID,
                                      long[] taskIDs,
                                      Actor owner,
                                      boolean isDeleteTaskPrivilege) {

        final String logSourceMethod =
                     "deleteTasks(worklistID, taskIDs, owner, isDeleteTaskPrivilege)";
        LOG.debug(logSourceMethod + ">>Start taskIDList.size() " + taskIDs.length);

        loadActor(owner);
        String ownerID = owner.toString();

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();

        for (long taskID : taskIDs) {
            taskIDList.add(taskID);
        }

        LOG.debug("taskIDList.size() " + taskIDList.size());

        ArrayList<Task> deletedTasks      = new ArrayList<Task>();
        ArrayList<Long> deletedTasksIDs   = new ArrayList<Long>();
        ArrayList<Task> undeletedTasks    = new ArrayList<Task>();
        ArrayList<Long> undeletedTasksIds = new ArrayList<Long>();
        ArrayList<String> errorCodes      = new ArrayList<String>();

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);
        LOG.debug("taskInstanceList.size() " + taskInstanceList.size());

        Task task = null;

        for (TaskInstance ti : taskInstanceList) {

            String taskWorklistID = getWorklistID(ti);

            if (!isDeleteTaskPrivilege && !ti.getActorId().equals(ownerID)) {

                addTaskResult(ti, undeletedTasks, undeletedTasksIds);
                errorCodes.add(WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
                continue;
            }

            if (taskWorklistID.equals(String.valueOf(worklistID))) {

                task = new Task(ti);
                jbpmContext.getGraphSession()
                           .deleteProcessInstance(ti.getProcessInstance().getId());

                deletedTasks.add(task);
                deletedTasksIDs.add(task.getTaskID());
            } else {

                addTaskResult(ti, undeletedTasks, undeletedTasksIds);
                errorCodes.add(WorklistEC.EC_TASK_NOT_FOUND_IN_WL);
            }
        }

        setUnprocessedTasks(deletedTasksIDs,
                            undeletedTasks,
                            undeletedTasksIds,
                            errorCodes,
                            taskIDList);
        jbpmContext.close();

        TaskListResult taskList = setTaskListResult(deletedTasks,
                                                    undeletedTasks,
                                                    deletedTasksIDs,
                                                    undeletedTasksIds,
                                                    errorCodes);
        LOG.debug(logSourceMethod + "<<End deletedTasksIDs.size() " + deletedTasksIDs.size());
        return taskList;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #makeTasksEditable(long, long[], com.mckesson.eig.workflow.api.Actor)
     *
     */
    public TaskListResult makeTasksEditable(long worklistID, long[] taskIDs, Actor owner) {

        final String logSourceMethod = "makeTasksEditable(worklistID, taskIDs, owner)";
        LOG.debug(logSourceMethod + ">>Start taskIDList.size() " + taskIDs.length);

        loadActor(owner);
        String ownerID = owner.toString();
        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();

        for (long taskID : taskIDs) {
            taskIDList.add(taskID);
        }

        LOG.debug("taskIDList.size() " + taskIDList.size());

        ArrayList<Task> processedTasks      = new ArrayList<Task>();
        ArrayList<Long> processedTasksIDs   = new ArrayList<Long>();
        ArrayList<Task> unprocessedTasks    = new ArrayList<Task>();
        ArrayList<Long> unprocessedTasksIds = new ArrayList<Long>();
        ArrayList<String> errorCodes        = new ArrayList<String>();

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);
        LOG.debug("taskInstanceList.size() " + taskInstanceList.size());

        for (TaskInstance ti : taskInstanceList) {

            String taskWorklistID = getWorklistID(ti);

            if (!ti.getActorId().equals(ownerID)) {

                addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                errorCodes.add(WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
                continue;
            }

            if (taskWorklistID.equals(String.valueOf(worklistID))) {

                try {

                    signal(ti, DEACTIVATE_TASK);
                    jbpmContext.save(ti);
                    addTaskResult(ti, processedTasks, processedTasksIDs);
                } catch (Exception e) {

                    LOG.error(e);
                    addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                    errorCodes.add(e instanceof WorkflowException
                                   ? ((WorkflowException) e).getErrorCode()
                                   : e.getMessage());
                }

            } else {

                addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                errorCodes.add(WorklistEC.EC_TASK_NOT_FOUND_IN_WL);
            }
        }

        setUnprocessedTasks(processedTasksIDs,
                            unprocessedTasks,
                            unprocessedTasksIds,
                            errorCodes,
                            taskIDList);

        jbpmContext.close();

        TaskListResult taskList = setTaskListResult(processedTasks,
                                                    unprocessedTasks,
                                                    processedTasksIDs,
                                                    unprocessedTasksIds,
                                                    errorCodes);
        LOG.debug(logSourceMethod + "<<End processedTaskIDs.size() " + processedTasksIDs.size());
        return taskList;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #updateTask(com.mckesson.eig.workflow.worklist.api.Task)
     *
     */
    public void updateTask(Task task, Actor owner) {

        final String logSourceMethod = "updateTask(task, owner)";
        LOG.debug(logSourceMethod + ">>Start");

        validateWorklist(task.getWorklistID());

        loadActor(task.getTaskCreator());
        loadActor(owner);

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        TaskInstance taskInstance =  jbpmContext.getTaskInstance(task.getTaskID());

        if (taskInstance == null) {

            throw new WorkflowException(WorklistEC.MSG_TASK_NOT_EXIST,
                                        WorklistEC.EC_TASK_NOT_EXIST);
        } else if (!taskInstance.getActorId().equals(owner.toString())) {

            throw new WorkflowException(WorklistEC.MSG_INVALID_ACTOR_FOR_OPER,
                                        WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
        }

        task.toTaskInstance(taskInstance);

        if (!taskInstance.getToken().getNode().getName().equals(task.getStatusID())) {
            signal(taskInstance, RELEASE);
        } else {
            signal(taskInstance, UPDATE_TASK);
        }

        jbpmContext.save(taskInstance);
        jbpmContext.close();
        updateStartDateForTask(task.getStartDate(), task.getTaskID());

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #releaseTasks(TaskList)
     *
     */
    public TaskListResult releaseTasks(TaskList taskList, Actor owner) {

        final String logSourceMethod = "releaseTasks(taskList, owner)";
        LOG.debug(logSourceMethod + ">>Start");

        loadActor(owner);

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();

        for (int i = taskList.getTasks().size(); --i >= 0;) {
            taskIDList.add((taskList.getTasks().get(i)).getTaskID());
        }

        LOG.debug("taskIDList.size() " + taskIDList.size());

        ArrayList<Task> processedTasks      = new ArrayList<Task>();
        ArrayList<Long> processedTasksIDs   = new ArrayList<Long>();
        ArrayList<Task> unprocessedTasks    = new ArrayList<Task>();
        ArrayList<Long> unprocessedTasksIds = new ArrayList<Long>();
        ArrayList<String> errorCodes        = new ArrayList<String>();

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);
        LOG.debug("taskInstanceList.size() " + taskInstanceList.size());

        for (TaskInstance ti : taskInstanceList) {
            try {
                if (!ti.getActorId().equals(owner.toString())) {

                    throw new WorkflowException(WorklistEC.MSG_INVALID_ACTOR_FOR_OPER,
                                                WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
                }
                signal(ti, RELEASE);
                jbpmContext.save(ti);

                addTaskResult(ti, processedTasks, processedTasksIDs);
            } catch (Exception e) {

                LOG.error(e);
                addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                errorCodes.add(e instanceof WorkflowException
                           ?  ((WorkflowException) e).getErrorCode()
                           :    e.getMessage());
            }
        }

        setUnprocessedTasks(processedTasksIDs,
                            unprocessedTasks,
                            unprocessedTasksIds,
                            errorCodes,
                            taskIDList);
        jbpmContext.close();

        TaskListResult taskListResult = setTaskListResult(processedTasks,
                                                          unprocessedTasks,
                                                          processedTasksIDs,
                                                          unprocessedTasksIds,
                                                          errorCodes);

        LOG.debug(logSourceMethod + "<<End processedTaskIDs.size() " + processedTasksIDs.size());
        return taskListResult;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #reassignTasks(long, com.mckesson.eig.workflow.api.TaskList)
     *
     */
    public TaskListResult reassignTasks(long fromWorklistID,
                                        long toWorklistID,
                                        TaskList taskList,
                                        Actor owner) {

        final String logSourceMethod =
                     "reassignTasks(fromWorklistID, toWorklistID, taskList, owner)";
        LOG.debug(logSourceMethod + ">>Start getTasks.size() " + taskList.getTasks().size());

        loadActor(owner);
        String ownerID = owner.toString();

        JbpmContext jbpmContext = createJbpmContext();
        jbpmContext.setActorId(String.valueOf(owner.getActorID()));

        List<Long> taskIDList = new ArrayList<Long>();
        Map<Long, String> map = new HashMap<Long, String>();

        for (int i = taskList.getTasks().size(); --i >= 0;) {

            Task task = (taskList.getTasks().get(i));
            taskIDList.add(task.getTaskID());

            if (StringUtilities.hasContent(task.getReassignReason())) {
                map.put(task.getTaskID(), task.getReassignReason());
            }
        }

        LOG.debug("taskIDList.size() " + taskIDList.size());

        ArrayList<Task> processedTasks      = new ArrayList<Task>();
        ArrayList<Long> processedTasksIDs   = new ArrayList<Long>();
        ArrayList<Task> unprocessedTasks    = new ArrayList<Task>();
        ArrayList<Long> unprocessedTasksIds = new ArrayList<Long>();
        ArrayList<String> errorCodes        = new ArrayList<String>();
        String ownedBy;

        List<TaskInstance> taskInstanceList =
                           jbpmContext.getTaskMgmtSession().findTaskInstancesByIds(taskIDList);
        LOG.debug("taskInstanceList.size() " + taskInstanceList.size());

        for (TaskInstance ti : taskInstanceList) {

            String taskWorklistID = getWorklistID(ti);
            ownedBy = ((String) ti.getContextInstance().getVariable(Task.VK_OWNED_BY));

            if (StringUtilities.isEmpty(ownedBy) || !ownedBy.equals(ownerID)) {

                addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                errorCodes.add(WorklistEC.EC_INVALID_ACTOR_FOR_OPER);
                continue;
            } else if (!taskWorklistID.equals(String.valueOf(fromWorklistID))) {

                addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                errorCodes.add(WorklistEC.EC_TASK_NOT_FOUND_IN_WL);
                continue;
            }

            try {

                signal(ti, REASSIGN_TASK);
                ((PooledActor) ti.getPooledActors().iterator().next())
                                 .setActorId(String.valueOf(toWorklistID));
                ti.getContextInstance().setVariable(Task.VK_OWNED_BY, StringUtilities.EMPTYSTRING);
                ti.getContextInstance().setVariable(Task.VK_REASSIGN_REASON, map.get(ti.getId()));
                jbpmContext.save(ti);

                addTaskResult(ti, processedTasks, processedTasksIDs);
            } catch (Exception e) {

                LOG.error(e);
                addTaskResult(ti, unprocessedTasks, unprocessedTasksIds);
                errorCodes.add(e instanceof WorkflowException
                           ?  ((WorkflowException) e).getErrorCode()
                           :    e.getMessage());
            }
        }

        setUnprocessedTasks(processedTasksIDs,
                            unprocessedTasks,
                            unprocessedTasksIds,
                            errorCodes,
                            taskIDList);
        jbpmContext.close();

        TaskListResult taskListResult = setTaskListResult(processedTasks,
                                                          unprocessedTasks,
                                                          processedTasksIDs,
                                                          unprocessedTasksIds,
                                                          errorCodes);
        LOG.debug(logSourceMethod + "<<End processedTaskIDs.size() " + processedTasksIDs.size());
        return taskListResult;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #generateWorklistReport(com.mckesson.eig.workflow.worklist.api.WorklistReportDO)
     */
    public void generateWorklistReport(final WorklistReportDO worklistReportDO) {

        final String logSourceMethod = "generateWorklistReport(worklistReportDO)";
        LOG.debug(logSourceMethod + ">>Start");
        
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        final StringBuffer hqlQuery =
            new StringBuffer()
            .append(" SELECT w.name,       ti.name,          ts.displayNameKey,   \n")
            .append("        ts.sortOrder, p.displayNameKey, p.sortOrder,         \n")
            .append("        ti.start,     ti.dueDate,       owners     FROM      \n")
            .append(" com.mckesson.eig.workflow.worklist.api.Worklist   AS w,     \n")
            .append(" org.jbpm.taskmgmt.exe.TaskInstance                AS ti,    \n")
            .append(" com.mckesson.eig.workflow.worklist.api.Priority   AS p,     \n")
            .append(" com.mckesson.eig.workflow.worklist.api.TaskStatus AS ts     \n");

            final Actor domain = loadActor(worklistReportDO.getDomain());

            getWorklistReportHQLQuery(hqlQuery, worklistReportDO);
            getTaskFilterQuery(hqlQuery, worklistReportDO);

            hqlQuery.append("order by ")
                    .append(getColumnName(worklistReportDO.getPrimarySort()))
                    .append(worklistReportDO.getIsDesc() ? " desc" : " asc");
            
            getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s) {
                        
                        Query query = s.createQuery(hqlQuery.toString());

                        if (worklistReportDO.hasOwnerActors()) {
                            
                            Set actors = worklistReportDO.getOwnerActors().getActors();

                            query.setParameterList("APP_ID",      getIDs(actors, APP_ID))
                                 .setParameterList("ENTITY_TYPE", getIDs(actors, ENTITY_TYPE))
                                 .setParameterList("ENTITY_ID",   getIDs(actors, ENTITY_ID));
                        }

                        if (worklistReportDO.getFetchAllWorklists()) {
                            query.setParameter(T_ACTOR_ID, domain.getActorID());
                        } else if (worklistReportDO.getWorklistIDs() != null) {
                            query.setParameterList(T_WORKLIST_ID, 
                                                   convertPrimitveLongArrToWrapper(
                                                           worklistReportDO.getWorklistIDs()));
                        }

                        if (worklistReportDO.getStatusIDs() != null) {
                            query.setParameterList("T_STATUS_ID", worklistReportDO.getStatusIDs());
                        }

                        query.setTimestamp("T_START_DATE", worklistReportDO.getStartDate())
                             .setTimestamp("T_END_DATE",   worklistReportDO.getEndDate());

                        ScrollableResults results = query.scroll();
                        
                        PrintStream out;
                        try {
                            out = createCSVFileStream(worklistReportDO.getCsvFileName());
                        } catch (FileNotFoundException e) {
                            
                            LOG.error(e);
                            throw new WorklistException(WorklistEC.MSG_WL_REPORT_FILE_NOTFOUND,
                                                        WorklistEC.EC_WL_REPORT_FILE_NOTFOUND);
                        }
                        
                        out.println("Worklist, Task Name, Task Status, Task Status Sort Order, "
                                + "Task Priority, Priority Sort Order, Task Start Date, "
                                + "Task Due Date, Owners");

                        final int worklistName          = 0;
                        final int taskName              = 1;
                        final int taskStatus            = 2;
                        final int taskStatusSortOrder   = 3;
                        final int taskPriority          = 4;
                        final int taskPrioritySortOrder = 5;
                        final int taskStartDate         = 6;
                        final int taskEndDate           = 7;
                        final int taskOwner             = 8;

                        for (; results.next();) {

                            out.print(appendQuotes(
                                      StringUtilities.getCSV(results.getString(worklistName)))
                                                                                    + CSV_DELIM);
                            out.print(appendQuotes(
                                      StringUtilities.getCSV(results.getString(taskName)))  
                                                                                    + CSV_DELIM);
                            out.print(results.getString(taskStatus)                 + CSV_DELIM);
                            out.print(results.getInteger(taskStatusSortOrder)       + CSV_DELIM);
                            out.print(results.getString(taskPriority)               + CSV_DELIM);
                            out.print(results.getInteger(taskPrioritySortOrder)     + CSV_DELIM);
                            out.print(dateFormatter.format(results.getDate(taskStartDate)) 
                                                                                    + CSV_DELIM);
                            out.print(dateFormatter.format(results.getDate(taskEndDate))      
                                                                                    + CSV_DELIM);
                            out.print((results.get(taskOwner)).toString() + '\n');
                        }
                        return null;
                    }
            });
        LOG.debug(logSourceMethod + ">>End");
    }
    
    /**
     * This method is used to enclose the passed entity value
     * 
     * @param entityVal
     * @return entityval with quotes
     */
    protected String appendQuotes(String entityVal) {
        
        return entityVal.charAt(0) == '"' ? entityVal : '"' + entityVal + '"';
    }

    /**
     * Get the Worklist Report Query
     *
     * @param hqlQuery
     * @param wlReportDO
     */
    private void getWorklistReportHQLQuery(StringBuffer hqlQuery,
                                          WorklistReportDO wlReportDO) {

        StringBuffer joinQuery = new StringBuffer()
                .append(" INNER JOIN w.ownerActors   AS owners                        \n")
                .append(" INNER JOIN ti.token        AS t                             \n")
                .append(" INNER JOIN t.node          AS n                             \n")
                .append(" INNER JOIN ti.pooledActors AS pa                            \n")
                .append(" WHERE w.worklistID = pa.actorId   AND                       \n")
                .append("       p.priorityID = ti.priority  AND                       \n")
                .append("       ts.statusID  = n.name       AND                       \n");

        StringBuffer worklistAssignedJoinQuery =
            new StringBuffer(" INNER JOIN w.acls AS acl       \n");

        if (wlReportDO.getFetchAllWorklists()) {

            hqlQuery.append(wlReportDO.getIsAdmin()
                       ? joinQuery.append(" (owners.actorID = :T_ACTOR_ID OR  \n")
                                  .append(getActorHQLQuery("owners") + ")     \n")
                       : worklistAssignedJoinQuery
                             .append(joinQuery)
                             .append(" ((owners.actorID = :T_ACTOR_ID     AND \n")
                             .append(getActorHQLQuery("acl.actor")   + ") OR  \n")
                             .append(getActorHQLQuery("owners")      + ")     \n"));

        } else {
            getGenerateWorklistReportQuery(hqlQuery.append(joinQuery), wlReportDO);
        }
    }

    /**
     * Generates the hqlQuery for generate worklist report based on worklists and
     * owner actors provided
     *
     * @param hqlQuery
     *        hql query which was already generated
     *
     * @param wlReportDO
     *        Worklist Report Data Object.
     *
     */
    private StringBuffer getGenerateWorklistReportQuery(StringBuffer hqlQuery,
                                                        WorklistReportDO wlReportDO) {

        String worklistCondition    = " w.worklistID IN (:T_WORKLIST_ID) ";

        if (wlReportDO.getWorklistIDs() != null && wlReportDO.hasOwnerActors()) {

            hqlQuery.append("(")
                    .append(worklistCondition)
                    .append(" OR ")
                    .append(getActorHQLQuery("owners"))
                    .append(")");
        } else if (wlReportDO.hasOwnerActors()) {
            hqlQuery.append(getActorHQLQuery("owners"));
        } else if (wlReportDO.getWorklistIDs() != null) {
            hqlQuery.append(worklistCondition);
        }
        return hqlQuery;
    }

    /**
     * Generates the HQL Query for filter the data based on the task details provided.
     *
     * @param hqlQuery
     *        hql query which was already generated
     * @param wlReportDO
     *        Worklist Report Data Object.
     */
    private void getTaskFilterQuery(StringBuffer hqlQuery, WorklistReportDO wlReportDO) {

        if (StringUtilities.hasContent(wlReportDO.getTaskName())) {
            hqlQuery.append(" AND upper(ti.name) like '%")
                    .append(wlReportDO.getTaskName().toUpperCase()).append("%'").append("\n");
        }

        if (wlReportDO.getStatusIDs() != null) {
            hqlQuery.append(" AND n.name in (:T_STATUS_ID) \n");
        }

        if (wlReportDO.getPriorityID() != 0) {
            hqlQuery.append(" AND ti.priority = ").append(wlReportDO.getPriorityID()).append("\n");
        }

        hqlQuery.append(wlReportDO.getDateFilter().equals(WorklistReportDO.START_DATE_FILTER)
                        ? " AND ti.start   >= :T_START_DATE AND ti.start   <= :T_END_DATE \n"
                        : " AND ti.dueDate >= :T_START_DATE AND ti.dueDate <= :T_END_DATE \n");
    }
    
    /**
     * Get the HQL Query for to filter the actors based on the appID, entityType and entityID.
     * 
     * @param type 
     *        type may be owner actor or acls.   
     * 
     * @return actorHQLQuery
     *       return HQL query.   
     */
    private String getActorHQLQuery(String type) {
        
        return new StringBuffer().append("("  + type + ".appID      IN (:APP_ID)      AND  ")
                                 .append(type +        ".entityType IN (:ENTITY_TYPE) AND  ")
                                 .append(type +        ".entityID   IN (:ENTITY_ID)      ) ")
                                 .toString();
    }
    
    /**
     * Get the array of 
     * @param actors
     * @param type
     * @return
     */
    private Object[] getIDs(Set actors, int type) {
        
        Object[] ids = new Object[actors.size()];
        int count    = 0;
        
        for (Iterator i = actors.iterator(); i.hasNext(); count++) {
            
            switch (type) {
                
                case APP_ID      : ids[count] = ((Actor) i.next()).getAppID();      break;
                case ENTITY_TYPE : ids[count] = ((Actor) i.next()).getEntityType(); break;
                default          : ids[count] = ((Actor) i.next()).getEntityID();   break;
            }
        }
        return ids;
    }

    /**
     * This method returns the query parameter corresponding to the column id passed.The query
     * parameter is appended with a separator i.e. comma.
     *
     * @param columnId
     *          The integer value corresponding to the column as defined by the variable _colIDs.
     *
     * @return Column Name
     *          Column Name with a separator.
     */
    private String getColumnName(int columnId) {

        switch (columnId) {

            case TASK_NAME       : return "upper(ti.name)";
            case TASK_PRIORITY   : return "p.sortOrder";
            case TASK_START_DATE : return "ti.start";
            case TASK_DUE_DATE   : return "ti.dueDate";
            case TASK_STATUS     : return "ts.sortOrder";
            case WORKLIST        : return "upper(w.name)";
            default              : return "ti.name";
        }
    }

    /**
     * This method creates the CSV file as specified by the file name passed and also creates the
     * required directories if the directories does not exist.
     *
     * @param csvFileName
     *          name of the csv file with the directory structure.
     *
     * @return
     *      Stream of csv file.
     *
     * @throws FileNotFoundException
     */
    private PrintStream createCSVFileStream(String csvFileName)
    throws FileNotFoundException {

        String dirPath  = csvFileName.substring(0, csvFileName.lastIndexOf('\\'));

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new PrintStream(new FileOutputStream(csvFileName), true);
    }

    /** set the task and taskID in the list.
     * @param ti
     * @param taskList
     * @param taskIDList
     */
    private void addTaskResult(TaskInstance ti,
                               ArrayList<Task> taskList,
                               ArrayList<Long> taskIDList) {

        Task task = new Task(ti);
        taskList.add(task);
        taskIDList.add(task.getTaskID());
    }

    /**
     * set the unprocessed tasks in the corresponding lists.
     * @param processedTaskIDs
     * @param unprocessedTasks
     * @param unprocessedTaskIDs
     * @param errorCodes
     * @param taskIDList
     * @param taskIDSize
     */
    private void setUnprocessedTasks(List<Long>   processedTaskIDs,
                                     List<Task>   unprocessedTasks,
                                     List<Long>   unprocessedTaskIDs,
                                     List<String> errorCodes,
                                     List<Long>   taskIDList) {

        int existingTasksCount = processedTaskIDs.size() + unprocessedTaskIDs.size();
        Task task = null;
        if (existingTasksCount < taskIDList.size()) {

            taskIDList.removeAll(processedTaskIDs);
            taskIDList.removeAll(unprocessedTaskIDs);

            for (long taskID : taskIDList) {

                task = new Task();
                task.setTaskID(taskID);
                LOG.error(task.getTaskID() + " - " + WorklistEC.MSG_TASK_NOT_EXIST);
                unprocessedTasks.add(task);
                unprocessedTaskIDs.add(task.getTaskID());
                errorCodes.add(WorklistEC.EC_TASK_NOT_EXIST);
            }
        }
    }

    /**
     * This method is used to set the list of processed ,unprocessed tasks and task IDs
     *
     * @param  processedTasks
     * @param  unprocessedTasks
     * @param  processedTaskIDs
     * @param  unprocessedTaskIDs
     * @param  errorCodes
     * @return taskListResult
     */
    private TaskListResult setTaskListResult(List<Task> processedTasks,
                                             List<Task> unprocessedTasks,
                                             List<Long> processedTaskIDs,
                                             List<Long> unprocessedTaskIDs,
                                             List<String> errorCodes) {

        TaskListResult taskList = new TaskListResult();
        IDListResult result     = new IDListResult();

        result.setProcessedIDs(processedTaskIDs);
        result.setFailedIDs(unprocessedTaskIDs);
        result.setErrorCodes(errorCodes);

        taskList.setProcessedTasksList(processedTasks);
        taskList.setUnProcessedTasksList(unprocessedTasks);

        taskList.setIdListResult(result);
        return taskList;
    }

    /**
     * Get the worklist ID from the task instance
     *
     * @param ti
     *          TaskInstance of JBPM
     * @return
     *          WorklistID which the input TaskInstance belongs to
     */
    private String getWorklistID(TaskInstance ti) {

        String worklistID = null;
        Set pooledActors = ti.getPooledActors();

        if (CollectionUtilities.hasContent(pooledActors)) {

            PooledActor actor = (PooledActor) pooledActors.iterator().next();
            worklistID = actor.getActorId();
        }
        return worklistID;
    }

    /**
     * Get the worklistID for the corresponding taskID
     *
     * @param taskID
     *        Unique ID of the task.
     *
     * @return worklistID
     *         Unique ID of the worklist.
     */
    public long getWorklistID(long taskID) {

        JbpmContext jbpmContext = createJbpmContext();
        TaskInstance ti = jbpmContext.getTaskMgmtSession().getTaskInstance(taskID);
        long worklistID = 0;

        if (ti != null) {

            String id = getWorklistID(ti);
            if (StringUtilities.hasContent(id)) {
                worklistID = Long.parseLong(id);
            }
        }
        jbpmContext.close();
        return worklistID;
    }

    /**
     * Set the transition for the corresponding task.
     *
     * @param ti
     *        TaskInstance need to be change the transition.
     *
     * @param transition
     *        transition state
     */
    private void signal(TaskInstance ti, String transition) {

        try {
            ti.getToken().signal(transition);
        } catch (JbpmException e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_INVALID_TASK_STATUS,
                                        WorklistEC.EC_INVALID_TASK_STATUS);
        }
    }

    /**
     * validate the worklist.
     *
     * @param worklistID
     *        worklist ID to be validated.
     */
    private void validateWorklist(long worklistID) {

        if (!isWorklistExists(worklistID)) {

            WorklistException we =
                new WorklistException(WorklistEC.MSG_INVALID_WORKLIST_ID,
                                      WorklistEC.EC_INVALID_WORKLIST_ID);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method is used to check whether the worklist is available or
     * not for a particular worklist.
     *
     * @param worklistID
     *         checking worklist ID.
     * @return isExists
     *         return true if worklist exists otherwise false.
     */
    private boolean isWorklistExists(final long worklistID) {

        return ((Integer) getHibernateTemplate().execute(
            new HibernateCallback() {
                public Object doInHibernate(Session s) {

                    return s.createCriteria(Worklist.class)
                            .setProjection(Projections.rowCount())
                            .add(Restrictions.eq("worklistID", worklistID))
                            .uniqueResult();
                }
        })).intValue() > 0;
    }

    /**
     * Need to be update the start date using HQL.The reason for this in JBPM the start date is
     * set as null by invoking the start method in TaskInstance it sets the current date.
     *
     * @param startDate
     *        the date to be updated.
     * @param taskID
     *        unique ID for the task.
     */
    private void updateStartDateForTask(final Date startDate, final long taskID) {

        final String hqlQuery = new StringBuffer()
                                .append(" UPDATE org.jbpm.taskmgmt.exe.TaskInstance \n")
                                .append(" SET start = :start WHERE id = :jbpmTaskID \n")
                                .toString();

        getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createQuery(hqlQuery)
                        .setTimestamp("start", startDate)
                        .setLong("jbpmTaskID", taskID)
                        .executeUpdate();
            }
        });
    }

    /**
     * This method converts the wrapper long array object to primitive long array object.
     *
     * @param listIDs
     * @return
     */
    private Long[] convertPrimitveLongArrToWrapper(long[] listIDs) {

        if (listIDs == null) {
            return null;
        }

        Long[] retArray = new Long[listIDs.length];
        for (int i = 0; i < listIDs.length; i++) {
            retArray[i] = listIDs[i];
        }

        return retArray;
    }

    public boolean checkProcessTaskPrivilege(final long worklistID) {

        final String sessionId = WsSession.getSessionId();

        TaskACLResolved acl = (TaskACLResolved) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session s) {
                  
                      Query query = s.getNamedQuery("canProcessTask");
                      query.setParameter("WORKLIST_ID", worklistID);
                      query.setParameter("SESSION_ID", sessionId);

                      return query.uniqueResult();
                    }
                }
             );

        return acl.getCanReassign() || acl.getCanComplete();
    }

    public boolean checkPrivilage(final long worklistID, final String queryName) {
        
        final String sessionId = WsSession.getSessionId();

        Boolean hasPriv = (Boolean) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session s) {
                  
                      Query query = s.getNamedQuery(queryName);
                      query.setParameter("WORKLIST_ID", worklistID);
                      query.setParameter("SESSION_ID", sessionId);

                      return query.uniqueResult();
                    }
                }
             );
        return (null == hasPriv) ? false : hasPriv;
    }
}
