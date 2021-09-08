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
package com.mckesson.eig.workflow.worklist.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.IDListResult;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.service.WorkflowService;
import com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria;
import com.mckesson.eig.workflow.worklist.api.CreateWLCriteria;
import com.mckesson.eig.workflow.worklist.api.ListACLs;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.Worklist;



/**
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0
 *
 * This interface declares the bussiness methods the client will invoke
 * in order to view and manage worklists.
 */
@WebService(name            = "WorklistPortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/worklist-v1")
@SOAPBinding(style          = Style.DOCUMENT,
             use            = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface WorklistService
extends WorkflowService {

    /**
     * This method returns all the work lists that are associated with the
     * specified actor and the index of the worklist will range from the
     * start index value to (start + count) and also by sorting the records
     * based on the attribute specified in the sort order and the ordering
     * of the records is either descending or ascending based on the sort
     * order specified.
     *
     * @param belongsTo
     *         The owning actor of the worklist.
     *
     * @param startIndex
     *         Start index value.
     *
     * @param count
     *         Number of records.
     *
     * @param sortOrder
     *         Order with which the list has to be sorted.
     *
     * @return listWorklist
     *         List of worklist.
     */
    @WebMethod(operationName = "getWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getWorklists")
    @WebResult(name          = "listWorklist")
    ListWorklist getWorklists(
            @WebParam(name = "belongsTo") Actor belongsTo,
            @WebParam(name = "startIndex") int startIndex,
            @WebParam(name = "count") int count,
            @WebParam(name = "sortOrder") SortOrder sortOrder);
    
    /**
     * Get the list of the worklists which are owned by and assigned to the same actor.
     *  
     * @param owner
     *        actor who owned the worklists.
     *          
     * @return ownedWorklists
     *        set of worklists which are ownedBy and assigned by the same actor.  
     */
    @WebMethod(operationName = "getOwnedWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getOwnedWorklists")
    @WebResult(name          = "listWorklist")
    ListWorklist getOwnedWorklists(@WebParam(name = "owner") Actor owner);

    /**
     * This method returns all the work lists and the index of the worklist
     * will range from the start index value to (start + count) and also by
     * sorting the records based on the attribute specified in the sort order
     * and the ordering of the records is either descending or ascending
     * based on the sort order specified.
     *
     * @param startIndex
     *         Start index value.
     *
     * @param count
     *         Number of records.
     *
     * @param sortOrder
     *         Order with which the list has to be sorted.
     *
     * @return listWorklist
     *         List of worklist.
     */
    @WebMethod(operationName = "getAllWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getAllWorklists")
    @WebResult(name          = "listWorklist")
    ListWorklist getAllWorklists(
            @WebParam(name = "startIndex") int startIndex,
            @WebParam(name = "count") int count,
            @WebParam(name = "sortOrder") SortOrder sortOrder);

    /**
     * This method returns the number of work lists.
     *
     * @return count
     *          Number of worklists.
     */
    @WebMethod(operationName = "getAllWorklistsCount",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getAllWorklistsCount")
    @WebResult(name          = "worklistsCount")
    long getAllWorklistsCount();

    /**
     * This method returns the number of work lists that are associated
     * with the specified actor.
     *
     * @param belongsTo
     *          The owning actor of the worklist.
     *
     * @return count
     *          Number of worklists.
     */
    @WebMethod(operationName = "getWorklistsCount",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getWorklistsCount")
    @WebResult(name          = "worklistsCount")
    long getWorklistsCount(@WebParam(name = "belongsTo") Actor belongsTo);

    /**
     * This method returns the list of worklists which belongs to the current
     * user or current domain and assigned to the current user or current
     * users user group is returned starting from the specified start index
     * till the (start index + count) value and also by sorting the records
     * based on the attribute specified in the sort order and the ordering
     * of the records is either descending or ascending based on the sort
     * order specified.
     *
     * @param assignedWLCriteria
     *          Wrapper containing owning actors and assigned actors.
     *
     * @param startIndex
     *          Start index value.
     *
     * @param count
     *          Number of records.
     *
     * @param sortOrder
     *          Order with which the list has to be sorted.
     *
     * @return listWorklist
     *          List of worklist.
     */
    @WebMethod(operationName = "getAssignedWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getAssignedWorklists")
    @WebResult(name          = "listWorklist")
    ListWorklist getAssignedWorklists(
            @WebParam(name = "assignedWLCriteria") AssignedWLCriteria assignedWLCriteria,
            @WebParam(name = "startIndex") int startIndex,
            @WebParam(name = "count") int count,
            @WebParam(name = "sortOrder") SortOrder sortOrder);

    /**
     * This method returns the count of worklists which belongs to the current
     * user or current domain and assigned to the current user or current users
     * user group.
     *
     * @param assignedWLCriteria
     *          Wrapper containing owning actors and assigned actors.
     *
     * @return count
     *          Number of worklists.
     */
    @WebMethod(operationName = "getAssignedWorklistsCount",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getAssignedWorklistsCount")
    @WebResult(name          = "worklistsCount")
    long getAssignedWorklistsCount(
            @WebParam(name = "assignedWLCriteria") AssignedWLCriteria assignedWLCriteria);

    /**
     * This method returns the list of worklists which belongs to the current
     * user and assigned to the current user or current
     * users user group is returned starting from the specified start index
     * till the (start index + count) value and also by sorting the records
     * based on the attribute specified in the sort order and the ordering
     * of the records is either descending or ascending based on the sort
     * order specified.
     *
     * @param createdBy
     *        The owning actor of the worklist.
     *
     * @return listWorklist
     *         List of worklist.
     */
    @WebMethod(operationName = "getAllAssignedWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getAllAssignedWorklists")
    @WebResult(name          = "listWorklist")
    ListWorklist getAllAssignedWorklists(
            @WebParam(name = "createdBy")  Actor createdBy,
            @WebParam(name = "startIndex") int startIndex,
            @WebParam(name = "count")      int count,
            @WebParam(name = "sortOrder")  SortOrder sortOrder);

    /**
     * This method returns the count of worklists which belongs to the current
     * user and assigned to the current user or current users
     * user group.
     *
     * @param createdBy
     *        The owning actor of the worklist.
     *
     * @return count
     *         Number of worklists.
     */
    @WebMethod(operationName = "getAllAssignedWorklistsCount",
               action = "http://eig.mckesson.com/wsdl/worklist-v1/getAllAssignedWorklistsCount")
    @WebResult(name   = "worklistsCount")
    long getAllAssignedWorklistsCount(@WebParam(name = "createdBy") Actor createdBy);
    
    /**
     * This method resolves the task_acl for the passed actors (belongs to the current
     * user also other actors who belongs to this worklist) 
     *
     * @param actors
     *        The actors to whom this worklist belongs to.
     * 
     */
    void resolveTaskAclsByActors(Actors actors);

    /**
     * This method returns the list of worklists which belongs to current user
     * or current domain and contains tasks created by the user is returned
     * starting from the specified start index till the (start index + count)
     * value and also by sorting the records based on the attribute specified
     * in the sort order and the ordering of the records is either descending
     * or ascending based on the sort order specified.
     *
     *
     * @param createWLCriteria
     *          contains list of owners and list of actors who created the task.
     *
     * @param startIndex
     *          Start Index.
     *
     * @param count
     *          Number of Records.
     *
     * @param sortOrder
     *          Order with which the list has to be sorted.
     *
     * @return listWorklist
     *          List of worklist.
     */
    @WebMethod(operationName = "getCreatableWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getCreatableWorklists")
    @WebResult(name          = "listWorklist")
    ListWorklist getCreatableWorklists(
            @WebParam(name = "createWLCriteria") CreateWLCriteria createWLCriteria,
            @WebParam(name = "startIndex")       int startIndex,
            @WebParam(name = "count")            int count,
            @WebParam(name = "sortOrder")        SortOrder sortOrder);

    /**
     * This method returns the count of worklists which belongs to
     * current user or current domain and contains tasks created by
     * the user.
     *
     * @param createWLCriteria
     *         contains list of owners and list of actors who created the task.
     *
     * @return listWorklist
     *          List of worklist.
     */
    @WebMethod(operationName = "getCreatableWorklistsCount",
               action = "http://eig.mckesson.com/wsdl/worklist-v1/getCreatableWorklistsCount")
    @WebResult(name   = "worklistsCount")
    long getCreatableWorklistsCount(
            @WebParam(name = "createWLCriteria") CreateWLCriteria createWLCriteria);

    /**
     * This method returns the details of the worklist along with the owners
     * and the access controls of the users/groups on the worklist for the
     * worklistID passed.
     *
     * @param worklistID
     *          Worklist ID for the worklist that has to fetched.
     *
     * @return worklist
     *          Worklist corresponding to the ID passed.
     */
    @WebMethod(operationName = "getWorklist",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/getWorklist")
    @WebResult(name          = "worklistsCount")
    Worklist getWorklist(@WebParam(name = "worklistID") long worklistID);

    /**
     * This method creates a new worklist if the worklist is valid
     * and associates the newly created worklist with the actors and
     * returns the id of the created worklist.
     *
     * @param worklist
     *          Worklist to be created.
     *
     * @return worklistID
     *          Worklist ID of the newly created worklist.
     */
    @WebMethod(operationName = "createWorklist",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/createWorklist")
    @WebResult(name          = "worklistsCount")
    long createWorklist(@WebParam(name = "worklist") Worklist worklist);
    
    /**
     * This method creates a new worklist if the worklist is valid
     * and associates the newly created worklist with the actors and
     * returns the id of the created worklist.
     *
     * @param worklist
     *          Worklist to be created.
     *
     * @return worklist
     *          Newly created worklist.
     */
    @WebMethod(operationName = "createNewWorklist",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/createNewWorklist")
    @WebResult(name          = "worklist")
    Worklist createNewWorklist(@WebParam(name = "worklist") Worklist worklist);

    /**
     * This method validates and updates the worklist passed. The associated
     * access controls and owners of the worklist are updated/created/deleted
     * depending on the worklist existence.
     *
     * @param worklist
     *          Worklist to be updated.
     */
    @WebMethod(operationName = "updateWorklist",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/updateWorklist")
    void updateWorklist(@WebParam(name = "worklist") Worklist worklist);
    
    /**
     * This method validates and updates the worklist passed. The associated
     * access controls and owners of the worklist are updated/created/deleted
     * depending on the worklist existence.
     *
     * @param worklist
     *          Worklist to be updated.
     *
     * @param worklist Updated Worklist
     */
    @WebMethod(operationName = "updateExistingWorklist",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/updateExistingWorklist")
    @WebResult(name          = "worklist")
    Worklist updateExistingWorklist(@WebParam(name = "worklist") Worklist worklist);

    /**
     * This method updates the assignment access control of the specified list
     * of acls for the specified actor and the worklist.
     *
     * If ACL or worklist is not given, then it will remove all ACLs
     * @param listACL acls
     * @param worlistIDs worklistID
     * @param assignedTo Actor associated with the worklist
     */
    @WebMethod(operationName = "updateWorklistACLs",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/updateExistingWorklist")
    @WebResult(name          = "worklist")
    void updateWorklistACLs(
            @WebParam(name = "listACL")    ListACLs listACL,
            @WebParam(name = "worlistIDs") long[] worlistIDs,
            @WebParam(name = "assignedTo") Actor assignedTo);

    /**
     * This method deletes the worklist that is referenced  by the specified
     * worklistIDs and if all the worklists are not deleted the undeleted ids
     * along with the corresponding error codes are returned.
     *
     * @param ids
     *         IDs to be deleted.
     *
     * @return idListResult
     *          Result of the delete event.
     *
     */
    @WebMethod(operationName = "deleteWorklists",
               action        = "http://eig.mckesson.com/wsdl/worklist-v1/deleteWorklists")
    @WebResult(name          = "idListResult")
    IDListResult deleteWorklists(@WebParam(name = "ids") long[] ids);

    /**
     * This method used to return the owner actor of the given worklist id. 
     *
     * @param worklistId - Indicates the worklist ID
     *
     * @return Owner Actor
     */
    Actor getOwner(long worklistId);
}
