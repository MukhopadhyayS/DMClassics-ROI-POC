
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

package com.mckesson.eig.workflow.engine.service;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.engine.ProcessEngine;

public class ProcessEngineImpl extends BaseWorkflowEngineImpl implements ProcessEngine {

    public ProcessEngineImpl() {
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.mckesson.eig.workflow.engine.ProcessEngine#deployProcess(com.mckesson
     * .eig.workflow.process.api.Process)
     */
    public void deployProcess(ProcessDefinition pd) {
        JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
        if (jbpmContext == null) {
            throw new WorkflowEngineException(
                    "JbpmContext cannot be null to create ProcessInstanceEngineImpl",
                    WorkflowEC.INVALID_JBPM_CONTEXT);
        }

        try {
            jbpmContext.deployProcessDefinition(pd);
        }  finally {
            closeJbpmContex(jbpmContext);
        }

    }
}
