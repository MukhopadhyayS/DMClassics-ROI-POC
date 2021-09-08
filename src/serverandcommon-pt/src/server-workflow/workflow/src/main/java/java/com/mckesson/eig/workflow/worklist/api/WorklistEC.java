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


/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * This class defines all the common error codes and the messages in the
 * worklist module which has to be thrown with the exception message.
 */
public class WorklistEC
extends com.mckesson.eig.workflow.api.WorkflowEC {

    public static final String EC_WORKLIST_EXISTS              = "AWE1";
    public static final String EC_NULL_WL                      = "WF_EC_03";
    public static final String EC_NO_WLNAME                    = "WF_EC_04";
    public static final String EC_NO_WLDESC                    = "WF_EC_05";
    public static final String EC_NO_BELTO                     = "WF_EC_06";
    public static final String EC_INVALID_WORKLIST_NAME_LEN    = "WF_EC_07";
    public static final String EC_INVALID_WORKLIST_DESC_LEN    = "WF_EC_08";
    public static final String EC_DELETEIDS_NULL               = "WF_EC_09";
    public static final String EC_ID_NOT_EXIST                 = "WF_EC_10";
    public static final String EC_WL_NT_AVAILABLE              = "WF_EC_11";
    public static final String EC_INVALID_WORKLIST_ID          = "WF_EC_12";
    public static final String EC_NULL_WL_IDS                  = "WF_EC_13";
    public static final String EC_INVALID_START_COUNT          = "WF_EC_14";
    public static final String EC_NULL_CRITERIA                = "WF_EC_16";
    public static final String EC_NULL_ACLS                    = "WF_EC_17";
    public static final String EC_NULL_LISTACL                 = "WF_EC_20";
    public static final String EC_REASSIGN_TO_SAME_WL          = "WF_EC_21";
    
    public static final String EC_NULL_STATUS_ID               = "WF_EC_121";
    public static final String EC_EMPTY_STATUS_ID              = "WF_EC_122";

    public static final String EC_INVALID_TASK_ID              = "WF_EC_123";
    public static final String EC_NULL_TASK_IDS                = "WF_EC_124";
    public static final String EC_TASK_NOT_FOUND_IN_WL         = "WF_EC_125";
    public static final String EC_INVALID_TASK_STATUS          = "WF_EC_126";
    public static final String EC_NULL_TASK                    = "WF_EC_127";
    public static final String EC_NO_TASK_NAME                 = "WF_EC_128";
    public static final String EC_NO_TASK_PRIORITY             = "WF_EC_129";
    public static final String EC_NO_TASK_START_DATE           = "WF_EC_130";
    public static final String EC_NO_TASK_END_DATE             = "WF_EC_131";
    public static final String EC_INVALID_TASK_NAME_LEN        = "WF_EC_132";
    public static final String EC_INVALID_TASK_DESC_LEN        = "WF_EC_133";
    public static final String EC_INVALID_TASK_COMMENTS_LEN    = "WF_EC_134";
    public static final String EC_INVALID_START_AND_END_DATE   = "WF_EC_135";
    public static final String EC_INVALID_STAT_FOR_OPER        = "WF_EC_136";
    public static final String EC_INVALID_ACTOR_FOR_OPER       = "WF_EC_137";
    public static final String EC_TASK_NOT_EXIST               = "WF_EC_138";
    public static final String EC_NULL_TASK_LIST               = "WF_EC_139";
    public static final String EC_WL_REPORT_FILE_NOTFOUND      = "WF_EC_139";
    public static final String EC_INSUFFICIENT_PRIVILEGE       = "WF_EC_140";
    public static final String EC_INVALID_SORTORDER            = "WF_EC_142";
    public static final String EC_TASK_FOUND                   = "WF_EC_143";
    public static final String EC_INVALID_CSV_FILE_NAME        = "WF_EC_144";

    public static final String MSG_WORKLIST_EXISTS             =
        "Worklist with same name already owned by specified Actor[s]";

    public static final String MSG_NULL_WL                     = "Worklist is null";
    public static final String MSG_ID_NOT_EXIST                = "Worklist ID does not exist";
    public static final String MSG_INV_WL                      = "Invalid Worklist details";
    public static final String MSG_WL_NT_AVAILABLE             = "Worklist not available";
    public static final String MSG_INVALID_WORKLIST_ID         = "Worklist ID is invalid";
    public static final String MSG_NULL_TASK                   = "Task is null";
    public static final String MSG_INV_TASK                    = "Invalid Task details";

    public static final String MSG_NULL_IDS                    = "Worklist IDs is null";
    public static final String MSG_STATUS_IDS                  = "Status IDs is null";
    public static final String MSG_INVALID_TASK_STATUS         = "Invalid Task Status";
    public static final String MSG_NULL_ACLS                   = "ACLs is null";
    public static final String MSG_INVALID_START_COUNT         = "Invalid Start/Count Value";
    public static final String MSG_NULL_CRITERIA               = "Criteria value is null";
    public static final String MSG_NULL_LISTACL                = "ListACL value is null";
    public static final String MSG_REASSIGN_TO_SAME_WL         = 
                                          "Cannot reassign to source Worklist";
    
    public static final String MSG_EMPTY_STATUS_ID             = "Status ID not found";
    public static final String MSG_NO_TASK_NAME                = "Task name not found";
    public static final String MSG_NO_TASK_PRIORITY            = "Task priority not found";
    public static final String MSG_NO_TASK_START_DATE          = "Task start date not found";
    public static final String MSG_NO_TASK_END_DATE            = "Task end date not found";
    public static final String MSG_INVALID_START_AND_END_DATE  = "Invalid  start and end date";
    public static final String MSG_INVALID_TASK_NAME_LEN       = "Task name exceeded the limit";
    public static final String MSG_INVALID_TASK_DESC_LEN       =
                                        "Task descriptin exceeded the limit";
    public static final String MSG_INVALID_TASK_COMMENT_LEN    = "Task comment exceeded the limit";
    public static final String MSG_INVALID_TASK_ID             = "Invalid TaskID";
    public static final String MSG_NULL_TASK_IDS               = "Null Task IDs";
    public static final String MSG_TASK_NOT_FOUND_IN_WL        = "Task not found in the Worklist";
    public static final String MSG_INVALID_STAT_FOR_OPER       =
                                        "Invalid task status for requested operation";

    public static final String MSG_INVALID_ACTOR_FOR_OPER      =
                                        "Invalid Actor for requested operation";

    public static final String MSG_TASK_NOT_ASSIGNED_TO_WL     =
                                        "Task not assigned to any Worklist";

    public static final String MSG_TASK_NOT_EXIST              = "Task not exist";
    public static final String MSG_NULL_TASK_LIST              = "Task List is null";
    public static final String MSG_INSUFFICIENT_PRIVILEGE      = "Invalid Privilege";
    public static final String MSG_WL_REPORT_FILE_NOTFOUND     = "Report file not found";
    public static final String MSG_INVALID_CSV_FILE_NAME       = "Invalid CSV file name";

    public static final String MSG_INVALID_SORTORDER           = "Invalid Sortorder";
    public static final String MSG_INVALID_ENTITY_TYPE         = "Invalid Entity Type";
    public static final String MSG_INVALID_ENTITY_ID           = "Invalid Entity Id";
    public static final String MSG_INVALID_APP_ID              = "Invalid App Id";
}
