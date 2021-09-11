/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. All
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
import org.jbpm.jpdl.el.impl.JbpmExpressionEvaluator;


import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.engine.BaseActionHandler;
import com.mckesson.eig.workflow.engine.api.ExpressionHelper;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;


/**
 * This class implements Conditional Wait action handler.
 *
 * @author McKesson
 * @date   March 4, 2009
 * @since  HECM 2.0; March 4, 2009
 */
public class ConditionalWaitActionHandler extends BaseActionHandler {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Object represents the Log4JWrapper object.
     */
    protected static final OCLogger LOG = new OCLogger(ConditionalWaitActionHandler.class);

    /**
     * Wait condition.
     */
    private String _waitCondition;

    /**
     * Retry Timer number
     */
    private int _retryTimerNumber;

    /**
     * Unit of measurement for retry Timer number.
     */
    private String _retryTimerUnitOfMeasurement;

    /**
     * @see org.jbpm.graph.def.ActionHandler
     * @param ExecutionContext will have execution context details
     *
     * #execute(org.jbpm.graph.exe.ExecutionContext)
     *
     */
    public void executeAction(ExecutionContext context) {

        LOG.debug("ConditionalWaitActionHandler:execute >> start");
        LOG.debug("ConditionalWaitActionHandler:condition >>" + _waitCondition);

        Boolean result = false;

        try {
            validate();

            for (int i = 0;; i++) {

                String selectedContent = (String) context.getVariable("SelectedContent." + i);

                if (context.getVariable("SelectedContent." + i) == null) {
                    break;
                }

                String waitCondition = _waitCondition;

                /**
                 * Resolve condition for variables needing lookup from external systems.
                 */
                ExpressionHelper eh = new ExpressionHelper(selectedContent);
                waitCondition = eh.resolveExpression(waitCondition);

                /**
                 * Evaluate expression using JBPM evaluator.
                 */
                LOG.debug("Final evaluation condition >>" + waitCondition);
                JbpmExpressionEvaluator eval = new JbpmExpressionEvaluator();
                result = (Boolean) eval.evaluate(waitCondition, context);

                //if any one expression is false; stop executing further.
                if (!result) {
                    break;
                }
            }
            /**
             * Create common data for process instance history record.
             */
            ProcessInstanceHistory pih = new ProcessInstanceHistory();
            pih.setProcessId(Long.parseLong(context.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    context.getVariable(PROCESS_VERSION_VARIABLE)));
            pih.setProcessInstanceId(context.getProcessInstance().getId());
            pih.setEventLevel("Action");
            pih.setEventName("Wait Condition");
            pih.setEventDatetime(new java.util.Date());
            pih.setEventOriginator(context.getToken().getNode().getName());
            pih.setEventComments(_waitCondition);
            pih.setCreateDateTime(new java.util.Date());


            if (result.booleanValue()) {
                LOG.debug("ConditionalWaitActionHandler:expression eval >> true");
                /**
                 * Insert process instance history record for true condition into table.
                 */
                pih.setEventStatus("true");
                ProcessInstanceUtil.createProcessInstanceHistory(pih);

            } else {

                LOG.debug("ConditionalWaitActionHandler:expression eval >> false");

                /**
                 * Insert process instance history record for false condition into table.
                 */
                pih.setEventStatus("false");
                ProcessInstanceUtil.createProcessInstanceHistory(pih);

                /**
                 *    Wait time on process is executed asynchronously through timer job.
                 * Therefore, wait action handler suspends execution at least for the
                 * amount of time specified in process configuration.
                 */
                createTimer(context.getToken(),
                            addTime(_retryTimerNumber, _retryTimerUnitOfMeasurement),
                            NotifyEngineActionHandler.class.getName());

                /**
                 * Current token needs to stop execution after this point and
                 * repeat execution.
                 */
                blockEngineExecution(context, "repeat");
            }
        } catch (WorkflowEngineException wee) {
            LOG.error("ConditionalWaitActionHandler: Execute failure. Exception was: "
                   + wee.toString());
            throw wee;
        }

        LOG.debug("ConditionalWaitActionHandler:execute >> end");
    }

    /**
     * Validates if this action handler is configured correctly prior to execution.
     */
    public void validate() {
        validateRetryTimerUnitOfMeasurement();
        validateWaitCondition();
    }

    /**
     * This method is used to validate waitCondition attribute.
     */
    private void validateWaitCondition() {
        if (StringUtilities.isEmpty(_waitCondition)) {
            throw new WorkflowEngineException("Empty waitCondition attribute.",
                    WorkflowEC.INVALID_TIMER_CONDITION);
        }
    }

    /**
     * This method is used to validate retryTimerUnitOfMeasurement attribute.
     */
    private void validateRetryTimerUnitOfMeasurement() {
        if (StringUtilities.isEmpty(_retryTimerUnitOfMeasurement)) {
            throw new WorkflowEngineException("Empty Timer unit of measurement attribute: "
                    + _retryTimerUnitOfMeasurement,
                    WorkflowEC.INVALID_TIMER_UOM);
        }

        if (!(UOM_MAP.containsKey(_retryTimerUnitOfMeasurement))) {
            throw new WorkflowEngineException("Invalid Timer unit of measurement attribute."
                    + _retryTimerUnitOfMeasurement,
                    WorkflowEC.INVALID_TIMER_UOM);
        }
    }

    /**
     * This method is used to get waitCondition
     *
     * @return waitCondition
     */
    public String getWaitCondition() {
        return _waitCondition;
    }

    /**
     * This method is used to set waitCondition
     *
     * @param waitCondition
     */
    public void setWaitCondition(String waitCondition) {
        _waitCondition = waitCondition;
    }

    /**
     * This method is used to get retry timer number
     *
     * @return retryTimerNumber
     */
    public int getRetryTimerNumber() {
        return _retryTimerNumber;
    }

    /**
     * This method is used to set the retry timer number
     *
     * @param retryTimerNumber
     */
    public void setRetryTimerNumber(int number) {
        _retryTimerNumber = number;
    }

    /**
     * This method is used to get retry timer UnitOfMeasurement
     *
     * @return retryTimerUnitOfMeasurement
     */
    public String getRetryTimerUnitOfMeasurement() {
        return _retryTimerUnitOfMeasurement;
    }

    /**
     * This method is used to set retry timer UnitOfMeasurement
     *
     * @param retryTimerUnitOfMeasurement
     */
    public void setRetryTimerUnitOfMeasurement(String unitOfMeasurement) {
        _retryTimerUnitOfMeasurement = unitOfMeasurement;
    }
}
