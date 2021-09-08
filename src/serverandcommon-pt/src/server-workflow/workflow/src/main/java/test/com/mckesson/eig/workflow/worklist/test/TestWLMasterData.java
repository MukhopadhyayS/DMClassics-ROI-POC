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
package com.mckesson.eig.workflow.worklist.test;

import com.mckesson.eig.workflow.worklist.api.WLMasterData;
import com.mckesson.eig.workflow.worklist.service.TaskService;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 *
 * This class is to test whether getWorklistsMasterData return WLMasterData object;
 * which will have list of TaskStatus, list of Priority and task duration.
 */
public class TestWLMasterData
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static TaskService _manager;
    private static WLMasterData _wlMasterData;
    private static final int FIVE = 5;
    private static final int THREE = 3;

    public void testSetUp() {

        init();
        _manager = (TaskService) getManager(TASK_MANAGER);
    }

    public void testMasterData() {

        _wlMasterData = _manager.getWorklistsMasterData();

        assertEquals(FIVE, _wlMasterData.getTaskStatusList().size());
        assertEquals(THREE, _wlMasterData.getPriorityList().size());
        assertNotNull(_wlMasterData.getDefaultTaskDuration());
    }
}
