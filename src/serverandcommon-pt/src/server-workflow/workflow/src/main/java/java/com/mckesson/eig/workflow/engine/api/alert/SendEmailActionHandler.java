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

package com.mckesson.eig.workflow.engine.api.alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.jbpm.graph.exe.ExecutionContext;

import com.mckesson.eig.config.model.MailInfo;
import com.mckesson.eig.config.model.NotificationInfo;
import com.mckesson.eig.config.service.NotificationService;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.engine.BaseActionHandler;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;

public class SendEmailActionHandler extends BaseActionHandler {

    /**
     * Object represents the Log4JWrapper object.
     */
    protected static final Log LOG = LogFactory.getLogger(SendEmailActionHandler.class);

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Send email success message.
     */
    private static final String SEND_EMAIL_SUCCESS_MSG =
    	"Send email action completed successfully";

    /**
     * Workflow App settings id.
     */
    private static final long WORKFLOW_APP_SETTINGS_ID = 2L;

    /**
     * Contains the body of the email message.
     */
    private String _body;

    /**
     * Contains a subject of the email message.
     */
    private String _subject;

    /**
     * Contains a string representing the from email address.
     */
    private String _from;

    /**
     * Contains a string representing the reply-to email address.
     */
    private String _replyTo;

    /**
     * Contains email addresses that are semi-colon delimited.
     */
    private String _to;

    /**
     * To email addresses.
     */
    private List<String> _toEmailAddresses;

    /**
     * @see org.jbpm.graph.def.ActionHandler
     * @param ExecutionContext will have execution context details
     *
     * #execute(org.jbpm.graph.exe.ExecutionContext)
     *
     */
    public void executeAction(final ExecutionContext context) {

    	LOG.debug("SendEmailActionHandler:executeAction >> start");

    	String eventStatus = SEND_EMAIL_SUCCESS_MSG;

        try {

            populate(context);
            doNotify();

        } catch (Exception e) {

        	eventStatus = "Send email action failed: " + e.toString();
        	LOG.error(eventStatus, e);
        	LOG.debug("SendEmailActionHandler: Execute failure. Exception was " + e.toString());
        	throw new WorkflowEngineException(e);

        } finally {

        	Date currentTime = new Date();

        	/**
             * Create process instance history record.
             */
            ProcessInstanceHistory pih = new ProcessInstanceHistory();
            pih.setProcessId(Long.parseLong(context.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    context.getVariable(PROCESS_VERSION_VARIABLE)));
            pih.setProcessInstanceId(context.getProcessInstance().getId());
            pih.setEventLevel("Action");
            pih.setEventName("Send Email");
            pih.setEventDatetime(currentTime);
            pih.setEventOriginator(context.getToken().getNode().getName());
            pih.setEventComments(this.getEventComments());
            pih.setCreateDateTime(currentTime);
            pih.setEventStatus(eventStatus);
            ProcessInstanceUtil.createProcessInstanceHistory(pih);

            LOG.debug("SendEmailActionHandler:executeAction >> end");
        }
    }

    /**
     * Helper method used to send notification
     * 
     * @param mi Mail Information
     */
    private void doNotify() {

        // prepares the notification info
        NotificationInfo ni = prepareNotificationInfo();

        NotificationService service = 
            (NotificationService) SpringUtilities.getInstance()
                                                 .getBeanFactory()
                                                 .getBean(NotificationService.class.getName());

        service.notify(ni, WORKFLOW_APP_SETTINGS_ID);
    }

    /**
     * Helper method that populates the smtp email values with the appropriate runTime values
     *
     * @param context - JBPM ExecutionContextstring for literal replacement
     */
    private void populate(ExecutionContext context) {

        setSubject(render(context, getSubject()));
        setBody(render(context, getBody()));
    }

    /**
     * Helper method used to prepare notification info.
     * @return
     */
    private NotificationInfo prepareNotificationInfo() {

    	LOG.debug("SendEmailActionHandler:prepareNotificationInfo >> start");

    	MailInfo mi = new MailInfo();
    	mi.setSubject(getSubject());
        mi.setEmailBody(getBody());

        //parse To string
        if (!StringUtilities.isEmpty(getTo())) {

        	StringTokenizer st = new StringTokenizer(getTo(), ";", false);
        	_toEmailAddresses  = new ArrayList<String>();

        	while (st.hasMoreTokens()) {
        		_toEmailAddresses.add(st.nextToken());
        	}
        }
        mi.setRecipientAddress(_toEmailAddresses);
        mi.setReplyTo(getReplyTo());

        NotificationInfo ni = new NotificationInfo();
        ni.setMailInfo(mi);
        ni.setSendEmail(true);

        LOG.debug("SendEmailActionHandler:prepareNotificationInfo >> end");

        return ni;
    }

    /**
     * Get event comments.
     *
     * @return
     */
    private String getEventComments() {

    	LOG.debug("SendEmailActionHandler:getEventComments >> start");

    	StringBuffer sb = new StringBuffer();

    	sb.append("SendEmailActionHandler[");

    	//sender
    	sb.append("Sender email:");
    	sb.append(this.getFrom());
    	sb.append("\n");

    	//reply-to
    	sb.append("Reply-To email:");
    	if (!StringUtilities.isEmpty(getReplyTo())) {
    		sb.append(this.getReplyTo());
    	} else {
    		sb.append("Not provided");
    	}
    	sb.append("\n");

    	//to
    	sb.append("To emails:");
    	if (!StringUtilities.isEmpty(getTo())) {
    		sb.append(this.getTo());
    	} else {
    		sb.append("Not provided");
    	}
    	sb.append("\n");

    	//subject
    	sb.append("Subject:");
    	sb.append(this.getSubject());
    	sb.append("\n");

    	//body
    	sb.append("Body:");
    	sb.append(this.getBody());
    	sb.append("\n");

    	sb.append("]");

    	LOG.debug("SendEmailActionHandler:getEventComments >> end");

    	return sb.toString();
    }

    @Override
    public void validate() {
        // validation will be taken care, in mail server component (notification service). 
    }

    /**
     * This method is used to retrieve the subject.
     * @return subject
     */
    public String getSubject() {
        return _subject;
    }

    /**
     * This method is used to set the subject.
     * @param value
     */
    public void setSubject(String value) {
        _subject = value;
    }

    /**
     * This method is used to retrieve the FROM email address string.
     * @return from
     */
    public String getFrom() {
        return _from;
    }

    /**
     * This method is used to set the FROM email address string.
     * @param value
     */
    public void setFrom(String value) {
        _from = value;
    }

    /**
     * This method is used to retrieve the REPLY-TO email address string.
     * @return replyTo
     */
    public String getReplyTo() {
        return _replyTo;
    }

    /**
     * This method is used to set the REPLY-TO email address string.
     * @param value
     */
    public void setReplyTo(String value) {
    	_replyTo = value;
    }

    /**
     * This method is used to retrieve the TO email address string.
     * @return to
     */
    public String getTo() {
        return _to;
    }

    /**
     * This method is used to set the TO email address string.
     * @return value
     */
    public void setTo(String value) {
        _to = value;
    }

    /**
     * This method is used to retrieve the body.
     * @return body
     */
    public String getBody() {
        return _body;
    }

    /**
     * This method is used to set the body.
     * @param value
     */
    public void setBody(String value) {
        _body = value;
    }
}
