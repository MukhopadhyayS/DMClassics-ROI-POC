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

package com.mckesson.eig.workflow.engine;

import org.jbpm.graph.exe.ProcessInstance;

import com.mckesson.eig.workflow.api.Actor;

public interface ProcessInstanceEngine {

    void startProcessInstance();

    ProcessInstance getProcessInstance(Actor userActor);

    void suspendProcessInstance(Actor userActor);

    void resumeProcessInstance(Actor userActor);

    void terminateProcessInstance(Actor userActor);

    void deleteProcessInstance(Actor userActor);

}
