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

package com.mckesson.eig.workflow.engine.api.state;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.jbpm.graph.exe.ExecutionContext;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.engine.BaseActionHandler;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;

/**
 * This class implements Wait Timer action handler.
 *
 * @author McKesson
 * @date   March 4, 2009
 * @since  HECM 2.0; March 4, 2009
 */
public class TimedWaitActionHandler extends BaseActionHandler {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Object represents the Log4JWrapper object.
     */
    protected static final OCLogger LOG = new OCLogger(TimedWaitActionHandler.class);


    /**
     * Timer number
     */
    private int _timerNumber;

    /**
     * Unit of measurement for Timer number.
     */
    private String _timerUnitOfMeasurement;


    /**
     * @see org.jbpm.graph.def.ActionHandler
     * @param ExecutionContext will have execution context details
     *
     * #execute(org.jbpm.graph.exe.ExecutionContext)
     *
     */
    public void executeAction(ExecutionContext context) {

        LOG.debug("TimedWaitActionHandler:execute >> start");

        try {
            /**
             * Validate parameters have meaningful values.
             */
            validate();

            /**
             * Set EngineIPC variables for logging process instance history
             * in  NotifyEngineActionHandler.
             */
            context.getContextInstance().setVariable("EngineIPC.Notifier",
                                                     "TimedWaitActionHandler");
            context.getContextInstance().setVariable("EngineIPC.TimerNumber", _timerNumber);
            context.getContextInstance().setVariable("EngineIPC.TimerUOM", _timerUnitOfMeasurement);

            /**
             * Wait time on process is executed asynchronously through timer job.
             * Therefore, wait action handler suspends execution at least for the
             * amount of time specified in process configuration.
             */
            createTimer(context.getToken(),
                    addTime(_timerNumber, _timerUnitOfMeasurement),
                    NotifyEngineActionHandler.class.getName());

            /**
             * Insert process instance history record into database table for in progress.
             */
            ProcessInstanceHistory pih = new ProcessInstanceHistory();
            pih.setProcessId(Long.parseLong(context.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    context.getVariable(PROCESS_VERSION_VARIABLE)));
            pih.setProcessInstanceId(context.getProcessInstance().getId());
            pih.setEventLevel("Action");
            pih.setEventName("Wait Timer");
            pih.setEventDatetime(new java.util.Date());
            pih.setEventOriginator(context.getToken().getNode().getName());
            pih.setEventComments(_timerNumber + " " + _timerUnitOfMeasurement);
            pih.setCreateDateTime(new java.util.Date());
            pih.setEventStatus("Wait Timer In Progress");
            ProcessInstanceUtil.createProcessInstanceHistory(pih);

            /**
             * Current token needs to stop execution after this point.
             */
            blockEngineExecution(context, "block");

        } catch (WorkflowEngineException e) {
           LOG.error("TimedWaitActionHandler: Execute failure. Exception was: " + e.toString());
           throw e;
        }
        LOG.debug("TimedWaitActionHandler:execute >> end");
     }

    /**
     * Validates if this action handler is configured correctly prior to execution.
     */
    public void validate() {

        validateTimerUnitOfMeasurement();
    }

    /**
     * This method is used to validate timerUnitOfMeasurement attribute.
     */
    private void validateTimerUnitOfMeasurement() {
        if (StringUtilities.isEmpty(_timerUnitOfMeasurement)) {
            throw new WorkflowEngineException("Empty Timer unit of measurement attribute.",
                    WorkflowEC.INVALID_TIMER_UOM);
        }

        if (!(UOM_MAP.containsKey(_timerUnitOfMeasurement))) {
            throw new WorkflowEngineException("Invalid Timer unit of measurement attribute.",
                    WorkflowEC.INVALID_TIMER_UOM);
        }
    }

    /**
     * This method is used to get timer number
     *
     * @return timerNumber
     */
    public int getTimerNumber() {
        return _timerNumber;
    }

    /**
     * This method is used to set the timer number
     *
     * @param timerNumber
     */
    public void setTimerNumber(int number) {
        _timerNumber = number;
    }

    /**
     * This method is used to get timerUnitOfMeasurement
     *
     * @return timerUnitOfMeasurement
     */
    public String getTimerUnitOfMeasurement() {
        return _timerUnitOfMeasurement;
    }

    /**
     * This method is used to set timerUnitOfMeasurement
     *
     * @param timerUnitOfMeasurement
     */
    public void setTimerUnitOfMeasurement(String unitOfMeasurement) {
        _timerUnitOfMeasurement = unitOfMeasurement;
    }
}
