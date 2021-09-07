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

package com.mckesson.eig.workflow.security;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.workflow.service.ApplicationPluginService;
import com.mckesson.eig.workflow.worklist.api.TaskEvent;
import com.mckesson.eig.workflow.worklist.api.TaskEventResult;

/**
 * @author OFS
 * @date   Apr 26, 2009
 * @since  eig.workflow; Apr 26, 2009
 */
public class MockApplicationPluginService implements ApplicationPluginService {

    public TaskEventResult processTaskEvent(TaskEvent taskEvent) {

        TaskEventResult event = new TaskEventResult();
        event.setEmailBody("this is email body " + taskEvent.getWorklistName());
        event.setEmailSubject("this is email subject");
        event.setSenderEmail("MockUser@hecm.com");

        List<String> list = new ArrayList<String>();
        list.add("mockuser @hecm.com");
        event.setRecipientAddress(list);

        return event;
    }
}
