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

package com.mckesson.eig.workflow.process.api;

import com.mckesson.eig.workflow.api.WorkflowEC;

/**
 * @author sahuly
 * @date   Feb 10, 2009
 * @since  HECM 2.0; Feb 10, 2009
 */
public class ProcessEC extends WorkflowEC {
    
    public static final String EC_INVALID_PROCESS_ID           = "WF_EC_200";
    public static final String EC_PROCESS_NOT_AVAILABLE        = "WF_EC_201";
    public static final String EC_INVALID_APPLICATION_ID       = "WF_EC_203";
    public static final String EC_PROCESS_TYPE_NOT_SPECIFIED   = "WF_EC_204";
    public static final String EC_INVALID_PROCESS_TYPE         = "WF_EC_205";
    public static final String EC_EMPTY_TRIGGER_TYPE           = "WF_EC_206";
    
    public static final String MSG_INVALID_PROCESS_ID          = "invalid process id";
    public static final String MSG_PROCESS_NOT_AVAILABLE       = "process not available";
    public static final String MSG_INVALID_APPLICATION_ID      = "invalid application id";
    public static final String MSG_PROCESS_TYPE_NOT_SPECIFIED  = "trigger type not specified";
    public static final String MSG_INVALID_PROCESS_TYPE        = "invalid trigger type";
    public static final String MSG_EMPTY_PROCESS_TYPE          = "process trigger types not found";
}
