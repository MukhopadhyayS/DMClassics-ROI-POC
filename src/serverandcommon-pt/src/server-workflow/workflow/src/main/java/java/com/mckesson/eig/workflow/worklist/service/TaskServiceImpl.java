/*
 * Copyright 2007-2010 McKesson Corporation and/or one of its subsidiaries. All
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
package com.mckesson.eig.workflow.worklist.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.service.AbstractWorkflowService;
import com.mckesson.eig.workflow.service.WorkflowAuditManger;
import com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskList;
import com.mckesson.eig.workflow.worklist.api.TaskListResult;
import com.mckesson.eig.workflow.worklist.api.WLMasterData;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.api.WorklistReportDO;
import com.mckesson.eig.workflow.worklist.dao.TaskDAO;
import com.mckesson.eig.workflow.worklist.dao.WorklistDAO;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 *
 * This class contains the service implementation to view and manage tasks
 */
@WebService(
        name              = "TaskPortType_v1_0",
        portName          = "task_v1_0",
        serviceName       = "TaskService_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/task-v1",
        endpointInterface = "com.mckesson.eig.workflow.worklist.service.TaskService")
public class TaskServiceImpl
extends AbstractWorkflowService
implements TaskService {

    private static final String TASK_DAO_ID     = "TaskDAO";
    private static final String WORKLIST_DAO_ID = "WorklistDAO";
    private static final String TASK_ACLS       = "Task_Acls";
    private static final String KEY_ACTOR       = "Key_Actor";
    
    private static final byte CREATE_TASK       = 1;
    private static final byte PROCESS_TASK      = 2;
    private static final byte REASSIGN_TASK     = 3;
    private static final byte COMPLETE_TASK     = 4;
    
    private static final String TASK_STATUS_NEW        = "new";
    private static final String TASK_STATUS_INPROGRESS = "in progress";
    

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(TaskServiceImpl.class);
    
    /**
     * Instantiates this implementation of business service.
     */
    public TaskServiceImpl() {
        super();
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getAssignedWorklistTasks(com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria,
     *                            long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public TaskList getAssignedTasks(AssignedTasksCriteria assignedTasksCriteria,
                                     int index,
                                     int count,
                                     SortOrder sortOrder) {

        final String logSourceMethod =
            "getAssignedTasks(assignedTasksCriteria, index, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskList taskList;

        try {

            sortOrder =  getValidatedTaskSortOrder(sortOrder);
            validateStartandCount(index, count);
            validateAssignedTasksCriteria(assignedTasksCriteria);
            taskList = getTaskDAO().getAssignedTasks(assignedTasksCriteria,
                                                     index,
                                                     count,
                                                     sortOrder);
            LOG.debug(logSourceMethod + "<<End");
            return taskList;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getCreatedTasks(com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria,
     *                long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public TaskList getCreatedTasks(CreatedTasksCriteria createdTasksCriteria,
                                    int index,
                                    int count,
                                    SortOrder sortOrder) {

        final String logSourceMethod =
            "getCreatedTasks(createdTasksCriteria, index, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskList taskList;

        try {

            sortOrder =  getValidatedTaskSortOrder(sortOrder);
            validateStartandCount(index, count);
            validateCreatedTasksCriteria(createdTasksCriteria);
            taskList = getTaskDAO().getCreatedTasks(createdTasksCriteria,
                                                    index,
                                                    count,
                                                    sortOrder);
            LOG.debug(logSourceMethod + "<<End");
            return taskList;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getWorklistsMasterData()
     */
    public WLMasterData getWorklistsMasterData() {

        final String logSourceMethod = "getWorklistsMasterData()";
        LOG.debug(logSourceMethod + ">>Start");

        WLMasterData wlMasterData;

        try {

            wlMasterData = getTaskDAO().getWorklistsMasterData();

            LOG.debug(logSourceMethod + "<<End");
            return wlMasterData;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getAssignedWorklistTasksCount(
     *      com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria)
     */
    public long getAssignedTasksCount(AssignedTasksCriteria assignedTaskcriteria) {

        final String logSourceMethod =
            "getAssignedTasksCount(assignedTaskcriteria)";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            validateAssignedTasksCriteria(assignedTaskcriteria);
            count = getTaskDAO().getAssignedTasksCount(assignedTaskcriteria);

            LOG.debug(logSourceMethod + "<<End");
            return count;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #getCreatedTasksCount(
     *      com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria)
     */
    public long getCreatedTasksCount(CreatedTasksCriteria createdTasksCriteria) {

        final String logSourceMethod = "getCreatedTasksCount(createdTasksCriteria)";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            validateCreatedTasksCriteria(createdTasksCriteria);
            count = getTaskDAO().getCreatedTasksCount(createdTasksCriteria);

            LOG.debug(logSourceMethod + "<<End");
            return count;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #createTask(com.mckesson.eig.workflow.worklist.api.Task)
     */
    public long createTask(Task taskDetails) {

        final String logSourceMethod = "createTask(taskDetails)";
        LOG.debug(logSourceMethod + ">>Start");
        long taskId = 0;

        try {
            
            Actor owner = getLoggedInActor();
            
            validateTask(taskDetails);
            validateActor(owner);
            Worklist worklist = getWorklistDAO().getWorklist(taskDetails.getWorklistID(), false);
            checkPrivilege(worklist.getWorklistID(), CREATE_TASK);

            taskId = getTaskDAO().createTask(taskDetails, owner);

            if (TASK_STATUS_NEW.equals(taskDetails.getStatusID())) {

                WorkflowAuditManger auditMgr = getWorkflowAuditManager();
                AuditEvent auditEvent = auditMgr.prepareSucessFailAuditEvent(
                                                 getDomainID(worklist.getOwnerActors()),
                                                 WsSession.getSessionUserId(),
                                                 AE_TASK_CREATE,
                                                 taskDetails.toCreateTaskAuditComment(),
                                                 AuditEvent.SUCCESS);
                auditMgr.createAuditEntry(auditEvent);
            }
            LOG.debug(logSourceMethod + "<<End");
            return taskId;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #getTaskDetails(long)
     */
    public Task getTaskDetails(long taskID) {

        final String logSourceMethod = "getTaskDetails(taskID)";
        LOG.debug(logSourceMethod + ">>Start");

        Task taskDetails;
        try {
            
            Actor owner = getLoggedInActor();
            validateTaskID(taskID);
            taskDetails = getTaskDAO().getTaskDetails(taskID, owner);

            LOG.debug(logSourceMethod + "<<End");
            return taskDetails;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #ownTasks(long, long[])
     */
    public TaskListResult ownTasks(long worklistID, long[] taskIDs) {

        final String logSourceMethod = "ownTasks(worklistID, taskIDs)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskListResult taskListResult = null;

        try {
            
            Actor owner = getLoggedInActor();
            validateWorklistID(worklistID);
            validateTaskIDs(taskIDs);
            validateActor(owner);
            checkPrivilege(worklistID, PROCESS_TASK);
            
            taskListResult = getTaskDAO().ownTasks(worklistID, taskIDs, owner);

            Worklist wl = getWorklistDAO().getWorklist(worklistID, false);
            WorkflowAuditManger auditMgr = getWorkflowAuditManager();

            List< ? > taskList = taskListResult.getProcessedTasksList();

            Task task;
            AuditEvent auditEvent;

            for (int i = taskList.size(); --i >= 0;) {

                task = (Task) taskList.get(i);
                auditEvent = 
                auditMgr.prepareSucessFailAuditEvent(getDomainID(wl.getOwnerActors()),
                                                     WsSession.getSessionUserId(),
                                                     AE_TASK_PROCESS,
                                                     task.toProcessAuditComment(TASK_STATUS_NEW),
                                                     AuditEvent.SUCCESS);
                auditMgr.createAuditEntry(auditEvent);
            }

            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #disownTasks(long[])
     *
     */
    public TaskListResult disownTasks(long[] taskIDs) {

        final String logSourceMethod = "disownTasks(taskIDs)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskListResult taskListResult = null;

        try {
            
            Actor owner = getLoggedInActor();
            validateTaskIDs(taskIDs);
            validateActor(owner);
            long worklistID = 0;
            
            for (int i = taskIDs.length; --i >= 0;) {
                
                worklistID = getTaskDAO().getWorklistID(taskIDs[i]);
                if (worklistID != 0) {
                    break;
                }
            }
            
            checkPrivilege(worklistID, PROCESS_TASK);
            taskListResult = getTaskDAO().disownTasks(taskIDs, owner);
            List< ? > taskList = taskListResult.getProcessedTasksList();

            WorkflowAuditManger wam = getWorkflowAuditManager();

            Task task;
            Worklist wl;
            AuditEvent auditEvent;

            for (int i = taskList.size(); --i >= 0;) {

                task = (Task) taskList.get(i);

                wl = getWorklistDAO().getWorklist(task.getWorklistID(), false);
                auditEvent = 
                wam.prepareSucessFailAuditEvent(getDomainID(wl.getOwnerActors()),
                                                WsSession.getSessionUserId(),
                                                AE_TASK_PROCESS,
                                                task.toProcessAuditComment(TASK_STATUS_INPROGRESS),
                                                AuditEvent.SUCCESS);
                wam.createAuditEntry(auditEvent);
            }

            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
                throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #completeTasks(TaskList)
     *
     */
    public TaskListResult completeTasks(TaskList taskList) {

        final String logSourceMethod = "completeTasks(taskList)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskListResult taskListResult;

        try {
            
            Actor owner = getLoggedInActor();
            validateTaskList(taskList);
            validateActor(owner);
            HashMap<Long, String> originalStatus = getStatusIDs(taskList);
            
            long worklistID = 0;
            Task task;
            
            for (int i = taskList.getTasks().size(); --i >= 0;) {
                
                task = taskList.getTasks().get(i);
                worklistID = task.getWorklistID();
                if (worklistID != 0) {
                    break;
                }
            }
            
            checkPrivilege(worklistID, COMPLETE_TASK);

            taskListResult = getTaskDAO().completeTasks(taskList, owner);
            List< ? > processedTasks = taskListResult.getProcessedTasksList();

            WorkflowAuditManger wam = getWorkflowAuditManager();

            Worklist wl;
            AuditEvent auditEvent;

            for (int i = processedTasks.size(); --i >= 0;) {

               task = (Task) processedTasks.get(i);

                wl   = getWorklistDAO().getWorklist(task.getWorklistID(), false);
                auditEvent   = 
                wam.prepareSucessFailAuditEvent(getDomainID(wl.getOwnerActors()),
                                                WsSession.getSessionUserId(),
                                                AE_TASK_COMPLETE,
                                                task.toProcessAuditComment(
                                                     originalStatus.get(task.getTaskID())),
                                                AuditEvent.SUCCESS);

                wam.createAuditEntry(auditEvent);
            }

            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #deleteTasks(long, long[])
     */
    public TaskListResult deleteTasks(long worklistID, long[] taskIDs) {

        final String logSourceMethod = "deleteTasks(worklistID, taskIDs)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskListResult taskListResult = null;

        try {
            
            Actor owner = getLoggedInActor();
            validateWorklistID(worklistID);
            validateTaskIDs(taskIDs);
            validateActor(owner);
            
            boolean isDeleteTaskPrivilege = checkDeleteTaskPrivilege(worklistID);
            taskListResult = getTaskDAO().deleteTasks(worklistID, 
                                                      taskIDs, 
                                                      owner, 
                                                      isDeleteTaskPrivilege);
            Worklist wl = getWorklistDAO().getWorklist(worklistID, false);

            WorkflowAuditManger wam = getWorkflowAuditManager();
            List< ? > taskList = taskListResult.getProcessedTasksList();

            Task task;
            AuditEvent auditEvent;

            for (int i = taskList.size(); --i >= 0;) {

                task = (Task) taskList.get(i);
                auditEvent = wam.prepareSucessFailAuditEvent(getDomainID(wl.getOwnerActors()),
                                                             WsSession.getSessionUserId(),
                                                             AE_TASK_DELETE,
                                                             task.toDeleteTaskAuditComment(),
                                                             AuditEvent.SUCCESS);
                wam.createAuditEntry(auditEvent);
            }

            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
                throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #makeTasksEditable(long, long[])
     */
    public TaskListResult makeTasksEditable(long worklistID, long[] taskIDs) {

        final String logSourceMethod = "makeTasksEditable(worklistID, taskIDs)";
        LOG.debug(logSourceMethod + ">>Start");

        try {
            
            Actor owner = getLoggedInActor();
            validateWorklistID(worklistID);
            validateTaskIDs(taskIDs);
            validateActor(owner);
            
            TaskListResult taskListResult = getTaskDAO().makeTasksEditable(worklistID, 
                                                                           taskIDs, 
                                                                           owner);
            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #updateTask(com.mckesson.eig.workflow.worklist.api.Task)
     */
    public void updateTask(Task taskDetails) {

        final String logSourceMethod = "updateTask(taskDetails)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            Actor owner = getLoggedInActor();
            validateTask(taskDetails);
            validateTaskID(taskDetails.getTaskID());
            
            if (taskDetails.getTaskCreator().getEntityID() != WsSession.getSessionUserId()) {
                checkPrivilege(taskDetails.getWorklistID(), CREATE_TASK);
            }
            
            getTaskDAO().updateTask(taskDetails, owner);
            
            Worklist worklist = getWorklistDAO().getWorklist(taskDetails.getWorklistID(), false);
            
            WorkflowAuditManger wam = getWorkflowAuditManager();
            AuditEvent auditEvent = 
            wam.prepareSucessFailAuditEvent(getDomainID(worklist.getOwnerActors()),
                                            WsSession.getSessionUserId(),
                                            AE_TASK_UPDATE,
                                            taskDetails.toUpdateTaskAuditComment(),
                                            AuditEvent.SUCCESS);
            wam.createAuditEntry(auditEvent);

            LOG.debug(logSourceMethod + "<<End");
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #releaseTasks(TaskList)
     *
     */    
    public TaskListResult releaseTasks(TaskList taskList) {

        final String logSourceMethod = "releaseTasks(taskList)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskListResult taskListResult;

        try {
            
            Actor owner = getLoggedInActor();
            validateTaskList(taskList);
            validateActor(owner);
            
            taskListResult = getTaskDAO().releaseTasks(taskList, 
                                                        owner);

            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     * #reassignTasks(long, TaskList)
     *
     */
    public TaskListResult reassignTasks(long toWorklist, TaskList taskList) {

        final String logSourceMethod = "reassignTasks(toWorklist, taskList)";
        LOG.debug(logSourceMethod + ">>Start");

        TaskListResult taskListResult;

        try {
            
            Actor owner = getLoggedInActor();
            validateTaskList(taskList);
            validateActor(owner);
            validateWorklistID(toWorklist);
            
            long fromWorklistID = 0;
            Task task;
            
            for (int i = taskList.getTasks().size(); --i >= 0;) {
                
                task = taskList.getTasks().get(i);
                fromWorklistID = task.getWorklistID();
                if (fromWorklistID != 0) {
                    break;
                }
            }
            //Check whether reassign worklist is other than the current worklist
            if (fromWorklistID == toWorklist) {
                throw new WorklistException(WorklistEC.MSG_REASSIGN_TO_SAME_WL,
                                            WorklistEC.EC_REASSIGN_TO_SAME_WL); 
            }
            
            checkPrivilege(fromWorklistID, REASSIGN_TASK);

            taskListResult = getTaskDAO().reassignTasks(fromWorklistID, 
                                                        toWorklist, 
                                                        taskList, 
                                                        owner);
            List< ? > processedTasks = taskListResult.getProcessedTasksList();

            WorkflowAuditManger wam = getWorkflowAuditManager();

            AuditEvent auditEvent;
            Worklist wl = getWorklistDAO().getWorklist(toWorklist, false);

            for (int i = processedTasks.size(); --i >= 0;) {

               task = (Task) processedTasks.get(i);
               auditEvent = 
               wam.prepareSucessFailAuditEvent(getDomainID(wl.getOwnerActors()),
                                               WsSession.getSessionUserId(),
                                               AE_TASK_REASSIGN,
                                               task.toReassignTaskAuditComment(toWorklist),
                                               AuditEvent.SUCCESS);

                wam.createAuditEntry(auditEvent);
            }

            LOG.debug(logSourceMethod + "<<End");
            return taskListResult;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.TaskService
     *  #generateWorklistReport(com.mckesson.eig.workflow.worklist.api.WorklistReportDO)
     */
    public void generateWorklistReport(WorklistReportDO worklistReportDO) {

        try {
            validateWorklistReportDO(worklistReportDO);
            getTaskDAO().generateWorklistReport(worklistReportDO);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * This method is used to validate the WorklistReportDO.
     * 
     * @param worklistReportDO
     */
    private void validateWorklistReportDO(WorklistReportDO worklistReportDO) {
        
        String fileName = worklistReportDO.getCsvFileName();
        if (fileName == null || fileName.trim().length() == 0 || fileName.indexOf('\\') == -1) {
            throw new WorklistException(WorklistEC.MSG_INVALID_CSV_FILE_NAME,
                    WorklistEC.EC_INVALID_CSV_FILE_NAME);
        }
    }    
 
    /**
     * This method verifies if the passed taskIDs are not null and valid.
     * @param taskIDs
     */
    private void validateTaskIDs(long[] taskIDs) {

        if (taskIDs == null) {
            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_TASK_IDS,
                                                         WorklistEC.EC_NULL_TASK_IDS);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method verifies if the passed taskList are not null and valid.
     * @param taskList
     */
    private void validateTaskList(TaskList taskList) {

        if (taskList == null) {
            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_TASK_LIST,
                                                         WorklistEC.EC_NULL_TASK_LIST);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method verifies if the passed taskID is valid.
     * @param taskID
     */
    private void validateTaskID(long taskID) {

        if (taskID <= 0) {

            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_TASK_ID,
                                                         WorklistEC.EC_INVALID_TASK_ID);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method verifies if the passed AssignedTasksCriteria is null.
     * @param assignedTasksCriteria
     */
    private void validateAssignedTasksCriteria(AssignedTasksCriteria assignedTasksCriteria) {

        if (assignedTasksCriteria == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_CRITERIA,
                                                         WorklistEC.EC_NULL_CRITERIA);
            LOG.error(we);
            throw we;
        }

        validateWorklistID(assignedTasksCriteria.getWorklistID());
        validateStatusIDs(assignedTasksCriteria.getStatusIDs());
    }

    /**
     * This method verfies whether the statusIDs are null or empty
     * @param statusIDs
     */
    private void validateStatusIDs(String[] statusIDs) {

        if (statusIDs == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_STATUS_IDS,
                                                         WorklistEC.EC_NULL_STATUS_ID);
            LOG.error(we);
            throw we;
        } else if (statusIDs.length == 0) {

            WorklistException we = new WorklistException(WorklistEC.MSG_EMPTY_STATUS_ID,
                                                         WorklistEC.EC_EMPTY_STATUS_ID);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method verifies if the passed CreatedTasksCriteria is null.
     * @param createdTasksCriteria
     */
    private void validateCreatedTasksCriteria(CreatedTasksCriteria createdTasksCriteria) {

        if (createdTasksCriteria == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_CRITERIA,
                                                         WorklistEC.EC_NULL_CRITERIA);
            LOG.error(we);
            throw we;
        }

        validateActor(createdTasksCriteria.getCreatedBy());
        validateWorklistID(createdTasksCriteria.getWorklistID());
    }

    /**
     * This method verifies if a passed actor is null or not.
     * @param actor
     */
    private void validateActor(Actor actor) {

        if (actor == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_ACTOR,
                                                         WorklistEC.EC_NULL_ACTOR);
            LOG.error(we);
            throw we;
        }
        actor.validateActor();
    }

    /**
     * This method validates the worklist ID passed to see if the worklistID
     * is less than or equal to zero.
     *
     * @param worklistID
     *          WorklistID to be verified.
     */
    private void validateWorklistID(long worklistID) {

        if (worklistID < 1) {
            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_WORKLIST_ID,
                                                         WorklistEC.EC_INVALID_WORKLIST_ID);
            LOG.error(we);
            throw we;
        }

        try {
            getWorklistDAO().get(Worklist.class, worklistID);
        } catch (WorkflowException e) {
            throw new WorklistException(WorklistEC.MSG_WL_NT_AVAILABLE, 
                    WorklistEC.EC_WL_NT_AVAILABLE);
        }
    }

    /**
     * This method checks if the start value is not negative and the
     * count is no less than zero.
     *
     * @param startIndex
     * @param count
     */
    private void validateStartandCount(int startIndex, int count) {

        if (startIndex < 0 || count < 1) {

            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_START_COUNT,
                                                         WorklistEC.EC_INVALID_START_COUNT);
            LOG.error(we);
            throw we;
        }
    }
    
    /**
     * This method is used to validate the task before creating
     * a task. The task is validated for all the mandatory attributes
     * and maximum allowed length.
     *
     * @param task
     *          Task to be created.
     *
     */
    private void validateTask(Task task) {

        if (task == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_TASK,
                                                         WorklistEC.EC_NULL_TASK);
            LOG.error(we);
            throw we;
        }

        if (task.getStartDate() == null || task.getEndDate() == null
                || task.getStartDate().after(task.getEndDate())
                || task.getEndDate().before(task.getStartDate())) {

            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_START_AND_END_DATE,
                                                         WorklistEC.EC_INVALID_START_AND_END_DATE);
            LOG.error(we);
            throw we;
        }

        validateWorklistID(task.getWorklistID());
        validateActor(task.getTaskCreator());

        String errorCode = task.validate();

        if (errorCode.trim().length() > 0) {

            WorklistException we = new WorklistException(WorklistEC.MSG_INV_TASK, errorCode);
            LOG.error(we);
            throw we;
        }
    }
    
    /**
     * check whether the logged in actor have the privilege to access the worklist
     * 
     * @param worklistID
     *        checking worklistID whether the actor assigned to this worklist
     *        
     * @param taskOperation
     *        task status to be updated.
     */
    private void checkPrivilege(long worklistID, byte taskOperation) {
        
        boolean hasPrivilege = false;

        if (isOwnedWorklist(worklistID)) {
            return;
        }
        
        switch (taskOperation) {
            
            case CREATE_TASK   :
                hasPrivilege = getTaskDAO().checkPrivilage(worklistID, "canCreateTask");
                break;
            case PROCESS_TASK  :
                hasPrivilege = getTaskDAO().checkProcessTaskPrivilege(worklistID);
                break;
            case REASSIGN_TASK :
                hasPrivilege = getTaskDAO().checkPrivilage(worklistID, "canReassignTask");
                break;
            case COMPLETE_TASK :
                hasPrivilege = getTaskDAO().checkPrivilage(worklistID, "canCompleteTask");
                break;
            default            : 
                hasPrivilege = false;
        }
        
        if (!hasPrivilege) {
            
            WorklistException we = new WorklistException(WorklistEC.MSG_INSUFFICIENT_PRIVILEGE, 
                                                         WorklistEC.EC_INSUFFICIENT_PRIVILEGE);
            LOG.error(we);
            throw we;
        }
    }

    private boolean isOwnedWorklist(long worklistID) {
        Worklist worklist = getWorklistDAO().getWorklist(worklistID, false);
        
        Set< ? > acl = worklist.getAssignedTo().getACLs();
        long owner = worklist.getOwnerID();
        
        Actor assignedTo = null;
        
        if (acl != null && acl.iterator().hasNext()) {
            TaskACL taskACL = (TaskACL) acl.iterator().next();
            assignedTo = taskACL.getActor();
        } else {
            return false;    
        }

        if (owner == assignedTo.getActorID()) {
            return true;
        }

        return false;
    }
    
    /**
     * check whether the actor have the privilege to do delete a task.
     * 
     * @param worklistID
     *        task to be deleted from this worklistID.
     */
    private boolean checkDeleteTaskPrivilege(long worklistID) {
        
        return getTaskDAO().checkPrivilage(worklistID, "canDeleteTask");
    }

    /**
     * set the statusID for the corresponding task in the hashtable before complete the task.
     *
     * @param taskList
     *        set of tasks.
     * @return tasks
     */
    private HashMap<Long , String> getStatusIDs(TaskList taskList) {

        HashMap<Long, String> tasks = new HashMap<Long, String>();

        for (int i = taskList.getTasks().size(); --i >= 0;) {

            Task task = taskList.getTasks().get(i);
            tasks.put(task.getTaskID(), task.getStatusID());
        }
        return tasks;
    }

    /**
     * This method validates the sort order specified for the tasks. If the
     * sort order is found to be invalid the sort order is set to sort by the
     * default atrribute(statusID) of the task.
     *
     * @param sortOrder
     */
    private SortOrder getValidatedTaskSortOrder(SortOrder sortOrder) {

        List<String> sortAttributes = new ArrayList<String>();
        sortAttributes.add("statusID");
        sortAttributes.add("priorityID");
        sortAttributes.add("taskName");
        sortAttributes.add("taskDescription");
        sortAttributes.add("startDate");
        sortAttributes.add("endDate");
        
        if ((sortOrder == null)
            || (sortOrder.getAttr() == null)
            || (StringUtilities.isEmpty(sortOrder.getAttr().getName()))
            || (!sortAttributes.contains(sortOrder.getAttr().getName()))) {

            throw new WorklistException(WorklistEC.MSG_INVALID_SORTORDER,
                                        WorklistEC.EC_INVALID_SORTORDER);
        }
        return sortOrder;
    }
    
    /**
     * Get the Actor from the session
     * 
     * @return actor
     *         Logged in Actor 
     */
    private Actor getLoggedInActor() {
        return (Actor) WsSession.getSessionData(KEY_ACTOR);
    }
    
    /**
     * This method returns the domain id of the current actor.
     * @return
     */
    public long getDomainID(Set<Actor> ownerActors) {
        return (ownerActors.iterator().next()).getEntityID();
    }

    /**
     * This method returns the TaskDAO implementation for this business service.
     * @return TaskDAO
     */
    private TaskDAO getTaskDAO() {
        return (TaskDAO) getDAO(TASK_DAO_ID);
    }

    /**
     * This method returns the WorklistDAO implementation for this business service.
     * @return worklistDAO
     */
    private WorklistDAO getWorklistDAO() {
        return (WorklistDAO) getDAO(WORKLIST_DAO_ID);
    }

}
