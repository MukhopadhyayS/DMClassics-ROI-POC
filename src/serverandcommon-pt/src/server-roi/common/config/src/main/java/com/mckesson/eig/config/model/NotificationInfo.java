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
@XmlType(name = "NotificationInfo")
@XmlRootElement(name = "notificationinfo", namespace = EIGConstants.TYPE_NS_V1)
public class NotificationInfo {

	/**
	 * Holds instance of FaxInfo.
	 */
	private FaxInfo _faxInfo;

	/**
	 * Holds instance of MailInfo.
	 */
	private MailInfo _mailInfo;

	/**
	 * Holds instance of SmsInfo.
	 */
	private SmsInfo _smsInfo;

	/**
	 * Holds instance of boolean.
	 */
	private boolean _sendEmail;

	/**
	 * Holds instance of boolean.
	 */
	private boolean _sendFax;

	/**
	 * Holds instance of boolean.
	 */
	private boolean _sendSms;

	/**
	 * @return faxInfo
	 */
	public FaxInfo getFaxInfo() {
		return _faxInfo;
	}

	/**
	 * @param faxInfo
	 */
	@XmlElement(name = "faxInfo")
	public void setFaxInfo(FaxInfo faxInfo) {
		this._faxInfo = faxInfo;
	}

	/**
	 * @return mailInfo
	 */
	public MailInfo getMailInfo() {
		return _mailInfo;
	}

	/**
	 * @param mailInfo
	 */
	@XmlElement(name = "mailInfo")
	public void setMailInfo(MailInfo mailInfo) {
		this._mailInfo = mailInfo;
	}

	/**
	 * @return smsInfo
	 */
	public SmsInfo getSmsInfo() {
		return _smsInfo;
	}

	/**
	 * @param smsInfo
	 */
	@XmlElement(name = "smsInfo")
	public void setSmsInfo(SmsInfo smsInfo) {
		this._smsInfo = smsInfo;
	}

	/**
	 * @return sendEmail
	 */
	public boolean isSendEmail() {
		return _sendEmail;
	}

	/**
	 * @param sendEmail
	 */
	@XmlElement(name = "sendEmail")
	public void setSendEmail(boolean sendEmail) {
		this._sendEmail = sendEmail;
	}

	/**
	 * @return sendFax
	 */
	public boolean isSendFax() {
		return _sendFax;
	}

	/**
	 * @param sendFax
	 */
	@XmlElement(name = "sendFax")
	public void setSendFax(boolean sendFax) {
		this._sendFax = sendFax;
	}

	/**
	 * @return sendSms
	 */
	public boolean isSendSms() {
		return _sendSms;
	}

	/**
	 * @param sendSms
	 */
	@XmlElement(name = "sendSms")
	public void setSendSms(boolean sendSms) {
		this._sendSms = sendSms;
	}
}
