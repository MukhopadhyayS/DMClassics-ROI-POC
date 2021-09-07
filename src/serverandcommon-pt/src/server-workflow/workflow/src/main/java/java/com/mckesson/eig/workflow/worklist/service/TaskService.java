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
package com.mckesson.eig.workflow.worklist.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.service.WorkflowService;
import com.mckesson.eig.workflow.worklist.api.AssignedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.CreatedTasksCriteria;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskList;
import com.mckesson.eig.workflow.worklist.api.TaskListResult;
import com.mckesson.eig.workflow.worklist.api.WLMasterData;
import com.mckesson.eig.workflow.worklist.api.WorklistReportDO;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 */
@WebService(name            = "TaskPortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/task-v1")
@SOAPBinding(style          = Style.DOCUMENT,
             use            = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TaskService
extends WorkflowService {

    /**
     * This method returns all the tasks that are associated with the
     * specified worklist, status and the index of the task will range
     * from the start index value to (start + count) and also by sorting
     * the records based on the attribute specified in the sort order and
     * the ordering of the records is either descending or ascending based
     * on the sort order specified
     *
     *
     * @param assignedTasksCriteria
     *           holds the worklistID and collection of statusID
     *
     * @param index
     *         Start index value.
     *
     * @param count
     *         Number of records.
     *
     * @param sortOrder
     *         Order with which the list has to be sorted.
     *
     * @return taskList
     *         List of Tasks.
     */
    @WebMethod(operationName = "getAssignedTasks", 
    		action = "http://eig.mckesson.com/wsdl/task-v1/getAssignedTasks")
    @WebResult(name = "taskList")
    TaskList getAssignedTasks(
            @WebParam(name = "assignedTasksCriteria") AssignedTasksCriteria assignedTasksCriteria,
            @WebParam(name = "index") int index,
            @WebParam(name = "count") int count,
            @WebParam(name = "sortOrder") SortOrder sortOrder);

    /**
     * This method returns all the tasks that are associated with the specified
     * worklist and created by the specified Actor and the index of the task will
     * range from the start index value to (start + count) and also by sorting the
     * records based on the attribute specified in the sort order and the ordering
     * of the records is either descending or ascending based on the sort order
     * specified.
     *
     *
     * @param createdTasksCriteria
     *           holds the worklistID, collection of statusID and
     *           the actor who created the task.
     *
     * @param index
     *         Start index value.
     *
     * @param count
     *         Number of records.
     *
     * @param sortOrder
     *         Order with which the list has to be sorted.
     *
     * @return taskList
     *         List of Tasks.
     */
    @WebMethod(operationName = "getCreatedTasks", 
    		action = "http://eig.mckesson.com/wsdl/task-v1/getCreatedTasks")
    @WebResult(name = "taskList")
    TaskList getCreatedTasks(
            @WebParam(name = "createdTasksCriteria") CreatedTasksCriteria createdTasksCriteria,
            @WebParam(name = "index") int index,
            @WebParam(name = "count") int count,
            @WebParam(name = "sortOrder") SortOrder sortOrder);

    /**
    * This will return WLMasterData associated with the Tasks, it encapsulates
    * the PriorityList and TaskStatusList.
    *
    *
    * @return wlMasterData
    *         List of available status and list of available priorities.
    */
    @WebMethod(operationName = "getWorklistsMasterData", 
                      action = "http://eig.mckesson.com/wsdl/task-v1/getWorklistsMasterData")
    @WebResult(name = "wlMasterData")
    WLMasterData getWorklistsMasterData();

    /**
    * This method returns the count of number of tasks that are associated with the
    * specified worklist and created by the specified Actor.If showEmptyWorklists is
    * true it will also count empty worklist, else it will count only worklist having
    * tasks and return it.
    *
    *
    * @param assignedTaskcriteria
    *           contains the parameters which are specific to getAssignedWorklistTasks
    *
    * @return count
    *          number of tasks associated with the specified worklist.
    */
    @WebMethod(operationName = "getAssignedTasksCount", 
                      action = "http://eig.mckesson.com/wsdl/task-v1/getAssignedTasksCount")
    @WebResult(name = "assignedTasksCount")
    long getAssignedTasksCount(
            @WebParam(name = "assignedTaskcriteria") AssignedTasksCriteria assignedTaskcriteria);

    /**
    * This method returns count of number of tasks that are associated with the
    * specified worklist, If showEmptyWorklists is true it will also count empty worklist,
    * else it will count only worklist having tasks.
    *
    *
    * @param createdTasksCriteria
    *          contains the parameters which are specific to getCreatedTasks
    *
    * @return count
    *           number of tasks associates with the specified assigned worklist.
    */
    @WebMethod(operationName = "getCreatedTasksCount", 
                      action = "http://eig.mckesson.com/wsdl/task-v1/getCreatedTasksCount")
    @WebResult(name = "createdTasksCount")
    long getCreatedTasksCount(
            @WebParam(name = "createdTasksCriteria") CreatedTasksCriteria createdTasksCriteria);

    /**
     * This method creates a new task after validating the task returns the id
     * of the created task.
     *
     * @param taskDetails
     *            task to be created.
     *
     * @return taskID of the created task.
     */
    @WebMethod(operationName = "createTask", 
                      action = "http://eig.mckesson.com/wsdl/task-v1/createTask")
    @WebResult(name = "createdTasksCount")
    long createTask(@WebParam(name = "taskDetails") Task taskDetails);

    /**
     * This method returns the Task object for the given task ID; if the task is not found
     * for the given task ID, it will throw an exception (invalid task)
     *
     * @param taskID
     *        unique id for the task.
     *
     * @return taskDetails
     *        task information for a particular taskID.
     */
    @WebMethod(operationName = "getTaskDetails",
                      action = "http://eig.mckesson.com/wsdl/task-v1/getTaskDetails")
    @WebResult(name = "task")  
    Task getTaskDetails(@WebParam(name = "taskID") long taskID);

    /**
     * This method owns the given tasks having New status in the given worklist
     * and for the given owner (Actor), rest of the tasks having other status
     * or belongs to other worklist (could be like the task would have
     * reassigned to some other worklist which client may not be aware of.
     * The client needn't have the updated information of the server)
     * cannot be owned.
     *
     * After successfully owning the tasks, the status of the task will be
     * changed to In Progress and the attribute taskOwner of the Task
     * will be updated with the owning Actor.
     *
     * @param worklistID
     *         Worklist ID of the Tasks, this is for required to validate whether
     *         the task belongs to the worklist
     *
     * @param taskIDs
     *         Tasks which has to be owned
     *
     * @return TaskListResult
     *         which will have list of successfully processed and failed tasks.
     */
    @WebMethod(operationName = "ownTasks",
                      action = "http://eig.mckesson.com/wsdl/task-v1/ownTasks")
    @WebResult(name = "taskListResult")
    TaskListResult ownTasks(
            @WebParam(name = "worklistID") long worklistID,
            @WebParam(name = "taskIDs")    long[] taskIDs);

    /**
     * This method disowns the given tasks having In Progress status
     * for the given owner (Actor), rest of the tasks having other status cannot be owned.
     *
     * After successfully disowning the tasks, the status of the task will be changed to New.
     * The attribute taskOwner of the Task will be cleared.
     *
     * @param taskIDs
     *          Tasks which has to be disowned (return to worklist)
     *
     * @param owner
     *          Tasks which has to be disowned by the given Actor
     *          i.e the owner of the task
     *
     * @return TaskListResult, which will have list of successfully processed and failed
     * tasks
     */
    @WebMethod(operationName = "disownTasks",
                      action = "http://eig.mckesson.com/wsdl/task-v1/disownTasks")
    @WebResult(name = "taskListResult")
    TaskListResult disownTasks(@WebParam(name = "taskIDs") long[] taskIDs);

    /**
     * This method completes the given tasks having In Progress status and owned by the given
     * owner (Actor), rest of the tasks which is not owned or having other status cannot be
     * completed.After successfully completing the tasks, the status of the task will be changed
     *  to Complete
     *
     *
     * @param taskList
     *        Tasks which has to be changed to complete status.
     *        
     * @return TaskListResult, which has information about the operation status for all tasks and
     *         applicable error codes
     */
    @WebMethod(operationName = "completeTasks",
                      action = "http://eig.mckesson.com/wsdl/task-v1/completeTasks")
    @WebResult(name = "taskListResult")
    TaskListResult completeTasks(@WebParam(name = "taskIDs") TaskList taskList);

    /**
     * This method deletes the given tasks in the given worklist and by the given owner (Actor),
     * rest of the tasks belongs to other worklist (could be like the task would have
     * reassigned to some other worklist which client may not be aware of. The client needn't
     * have the updated information of the server) cannot be deleted.
     *
     * @param worklistID
     *        worklistID of the Tasks, this is needed to find whether the
     *        set of tasks belongs to that particular worklist or not.
     *
     * @param tasksIDs
     *        set of tasks to be deleted.
     *
     * @return TaskListResult
     *        contains list of successfully deleted and failed tasks.
     */
    @WebMethod(operationName = "deleteTasks",
                      action = "http://eig.mckesson.com/wsdl/task-v1/deleteTasks")
    @WebResult(name = "taskListResult")
    TaskListResult deleteTasks(
            @WebParam(name = "worklistID") long worklistID,
            @WebParam(name = "taskIDs")    long[] taskIDs);
    
    /**
     * This method enable the tasks to edit by changing the status in to draft from new status.
     * This will check whether the given worklist matches, rest of the tasks having other status or 
     * belongs to other worklist (could be like the task would have reassigned to some other 
     * worklist which client may not be aware of. The client needn't have the updated 
     * information of the server) or the actor who is performing the operation doesnt have create 
     * privilege or not created the task cannot be made editable
     *
     * @param worklistID
     *        worklistID of the Tasks, this is needed to find whether the
     *        set of tasks belongs to that particular worklist or not.
     *
     * @param tasksIDs
     *        set of tasks to be make editable.
     *
     * @return TaskListResult
     *        contains list of successfully processed and failed tasks.
     */
    @WebMethod(operationName = "makeTasksEditable",
                      action = "http://eig.mckesson.com/wsdl/task-v1/makeTasksEditable")
    @WebResult(name = "taskListResult")
    TaskListResult makeTasksEditable(
            @WebParam(name = "worklistID") long worklistID,
            @WebParam(name = "taskIDs")    long[] taskIDs);
    
    /**
     * This method updates a task which is in draft status, the updation is very similar to create, 
     * except that the taskID will be present (i.e. existing task will be fetched from the DB and 
     * perform an update operation) and requires some additional validation, the update requires 
     * create privilege and also the person who has created the task can only update.
     *
     * @param taskDetails
     *            task to be updated.
     */
    @WebMethod(operationName = "updateTask", 
    		action = "http://eig.mckesson.com/wsdl/task-v1/updateTask")
    void updateTask(@WebParam(name = "taskDetails") Task taskDetails);

    /**
     * This method reassign the tasks from one worklist to other.The taskList that has to be 
     * reassigned to the given worklist.
     * 
     * @param toWorklist
     *        The taskList has been reassigned to the given worklist.
     *        
     * @param taskList
     *        set of tasks to be reassigned
     *        
     * @return taskListResult
     *         contains list of successfully processed and failed tasks. 
     */
    @WebMethod(operationName = "reassignTasks",
                      action = "http://eig.mckesson.com/wsdl/task-v1/reassignTasks")
    @WebResult(name = "taskListResult")
    TaskListResult reassignTasks(
            @WebParam(name = "toWorklist") long toWorklist,
            @WebParam(name = "taskList") TaskList taskList);

    /**
     * This method is used to generate worklist report depending on the filter parameters specified
     * by the data object passed.
     *
     * @param worklistReportDO
     *          Worklist Report Data Object.
     */
    @WebMethod(operationName = "generateWorklistReport",
                      action = "http://eig.mckesson.com/wsdl/task-v1/generateWorklistReport")
    void generateWorklistReport(
            @WebParam(name = "worklistReportDO") WorklistReportDO worklistReportDO);
    
    /**
     * This method will release the tasks that are in Draft status. Tasks that are successfully 
     * released will have status changed to 'New' and it will be added to the processed list. 
     * 
     * Tasks which cannot be released will added to failed list.
     * 
     * @param taskList
     * 
     * @return taskListResult
     *         contains list of successfully processed and failed tasks. 
     */
    @WebMethod(operationName = "releaseTasks",
                      action = "http://eig.mckesson.com/wsdl/task-v1/releaseTasks")
    @WebResult(name = "taskListResult")
    TaskListResult releaseTasks(@WebParam(name = "taskList") TaskList taskList);
}
