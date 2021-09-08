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

package com.mckesson.eig.workflow.worklist.api;

import java.util.List;

/**
 * @author McKesson
 * @date   Apr 25, 2009
 * @since  HecmServices; Apr 25, 2009
 */
public class TaskEventResult {

    private String _senderEmail;
    private String _emailBody;
    private String _emailSubject;

    private List<String> _recipientAddress;

    public String getSenderEmail() {
        return _senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        _senderEmail = senderEmail;
    }

    public String getEmailBody() {
        return _emailBody;
    }

    public void setEmailBody(String emailBody) {
        _emailBody = emailBody;
    }

    public String getEmailSubject() {
        return _emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        _emailSubject = emailSubject;
    }

    public List<String> getRecipientAddress() {
        return _recipientAddress;
    }

    public void setRecipientAddress(List<String> recipientAddress) {
        _recipientAddress = recipientAddress;
    }
}
