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
package com.mckesson.eig.workflow.api;

import com.mckesson.eig.utility.exception.ClientErrorCodes;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * This class defines all the common error codes and the messages in the
 * workflow module which has to be thrown with the exception message.
 */
public class WorkflowEC
extends ClientErrorCodes {

    /**
     * ALERT action handler error codes - Use 001 - 010 initially.
     */
    public static final String INVALID_EMAIL_SERVER_NAME       = "WF_WE_001";
    public static final String INVALID_EMAIL_SERVER_PORT       = "WF_WE_002";
    public static final String INVALID_NOTIFICATION_RECIPIENTS = "WF_WE_003";
    public static final String INVALID_NOTIFICATION_SENDER     = "WF_WE_004";
    public static final String INVALID_NOTIFICATION_SUBJECT    = "WF_WE_005";
    public static final String INVALID_NOTIFICATION_DETAIL     = "WF_WE_006";
    public static final String INVALID_NOTIFICATION_REPLY_TO   = "WF_WE_007";

    /**
     * Process, ProcessList, ProcessInstance, & ProcessInstanceList services
     * error codes. Use 011 - 050 initially.
     */
    public static final String INVALID_JBPM_CONTEXT             = "WF_WE_011";
    public static final String INVALID_PROCESS                  = "WF_WE_012";
    public static final String INVALID_PROCESS_INSTANCE         = "WF_WE_013";
    public static final String INVALID_TOKEN                    = "WF_WE_014";
    public static final String START_PROCESS_INSTANCE_ERROR     = "WF_WE_015";
    public static final String TERMINATE_PROCESS_INSTANCE_ERROR = "WF_WE_016";
    public static final String NOTIFY_PROCESS_INSTANCE_ERROR    = "WF_WE_017";
    public static final String SUSPEND_PROCESS_INSTANCE_ERROR   = "WF_WE_018";
    public static final String RESUME_PROCESS_INSTANCE_ERROR    = "WF_WE_019";


    /**
     * WAIT action handlers (Timer & Conditional) error codes Use 051 - 060
     */
    public static final String INVALID_TIMER_NUMBER            = "WF_WE_051";
    public static final String INVALID_TIMER_UOM               = "WF_WE_052";
    public static final String INVALID_TIMER_CONDITION         = "WF_WE_053";

    /**
     * Database action handler error codes Use 061 - 070
     */
    public static final String INVALID_NUMBER_OF_RESULT_SETS   = "WF_WE_061";

    /**
     * Common errors use EC series.
     */
    public static final String EC_NULL_ACTOR                   = "WF_EC_15";
    public static final String EC_INVALID_ACTOR                = "WF_EC_18";
    public static final String EC_NULL_ACTORS                  = "WF_EC_19";
    public static final String EC_OTHER_SERVER_ERROR           = "WF_EC_141";

    public static final String MSG_NULL_ACTOR                  = "Actor value is null";
    public static final String MSG_INVALID_ACTOR               = "Actor is invalid";
    public static final String MSG_NULL_ACTORS                 = "Actors value is null";
    public static final String MSG_OTHER_SERVER_ERROR          = "Other Server Error";

}
