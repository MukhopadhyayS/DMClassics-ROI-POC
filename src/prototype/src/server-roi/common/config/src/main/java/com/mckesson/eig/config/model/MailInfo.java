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
package com.mckesson.eig.config.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author OFS
 *
 * @date Mar 26, 2009
 * @since HECM 1.0.3; Mar 26, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "MailInfo")
@XmlRootElement(name = "mailinfo", namespace = EIGConstants.TYPE_NS_V1)
public class MailInfo {

	/**
	 * Hold the recipient email ids.
	 */
	private List<String> _recipientAddress;

	/**
	 * Hold the email body.
	 */
	private String _emailBody;

	/**
	 * Hold the sender email id.
	 */
	private String _senderEmailAddress;

	/**
	 * Hold the mail subject.
	 */
	private String _subject;

	private String _replyTo;

	/**
	 * @return
	 */
	public String getEmailBody() {
		return _emailBody;
	}

	/**
	 * @param emailBody
	 */
	@XmlElement(name = "emailBody")
	public void setEmailBody(String emailBody) {
		this._emailBody = emailBody;
	}

	/**
	 * @return
	 */
	public List<String> getRecipientAddress() {
		return _recipientAddress;
	}

	/**
	 * @param recipientAddress
	 */
	@XmlElement(name = "recipientAddress")
	public void setRecipientAddress(List<String> recipientAddress) {
		this._recipientAddress = recipientAddress;
	}

	/**
	 * @return
	 */
	public String getSenderEmailAddress() {
		return _senderEmailAddress;
	}

	/**
	 * @param senderEmailAddress
	 */
	@XmlElement(name = "senderEmailAddress")
	public void setSenderEmailAddress(String senderEmailAddress) {
		this._senderEmailAddress = senderEmailAddress;
	}

	/**
	 * @return
	 */
	public String getSubject() {
		return _subject;
	}

	/**
	 * @param subject
	 */
	@XmlElement(name = "subject")
	public void setSubject(String subject) {
		this._subject = subject;
	}

    public String getReplyTo() {
        return _replyTo;
    }

    @XmlElement(name = "replyToEmailAddress")
    public void setReplyTo(String replyTo) {
        _replyTo = replyTo;
    }
}
