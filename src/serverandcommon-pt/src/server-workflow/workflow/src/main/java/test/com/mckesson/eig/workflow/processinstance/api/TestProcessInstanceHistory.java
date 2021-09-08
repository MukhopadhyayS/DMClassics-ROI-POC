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

package com.mckesson.eig.workflow.processinstance.api;

import java.util.Date;

/**
 * @author Mckesson
 * @date   March 19, 2009
 * @since  HECM 2.0; March 19, 2009
 */
public class TestProcessInstanceHistory  extends junit.framework.TestCase {

    private static ProcessInstanceHistory _processInstanceHistory;
    private static Date _date = new Date();
    private static final int PROCESS_ID = 100;

    public void testSettersAndGetters() {

        _processInstanceHistory = new ProcessInstanceHistory();
        _processInstanceHistory.setProcessId(PROCESS_ID);
        _processInstanceHistory.setVersionId(1);
        _processInstanceHistory.setProcessInstanceId(1);
        _processInstanceHistory.setEventLevel("eventlevel");
        _processInstanceHistory.setEventName("eventname");
        _processInstanceHistory.setEventOriginator("somebody");
        _processInstanceHistory.setEventDatetime(_date);
        _processInstanceHistory.setEventStatus("eventstatus");
        _processInstanceHistory.setEventComments("eventcomments");
        _processInstanceHistory.setEventComments("eventcomments");
        _processInstanceHistory.setModifiedByUserId(1);
        _processInstanceHistory.setModifyDateTime(_date);
        _processInstanceHistory.setCreateDateTime(_date);


        assertEquals(_processInstanceHistory.getProcessId(), PROCESS_ID);
        assertEquals(_processInstanceHistory.getVersionId(), 1);
        assertEquals(_processInstanceHistory.getProcessInstanceId(), 1);
        assertEquals(_processInstanceHistory.getEventLevel(), "eventlevel");
        assertEquals(_processInstanceHistory.getEventName(), "eventname");
        assertEquals(_processInstanceHistory.getEventOriginator(), "somebody");
        assertEquals(_processInstanceHistory.getEventDatetime(), _date);
        assertEquals(_processInstanceHistory.getEventStatus(), "eventstatus");
        assertEquals(_processInstanceHistory.getEventComments(), "eventcomments");
        assertEquals(_processInstanceHistory.getModifyDateTime(), _date);
        assertEquals(_processInstanceHistory.getCreateDateTime(), _date);
    }
}
