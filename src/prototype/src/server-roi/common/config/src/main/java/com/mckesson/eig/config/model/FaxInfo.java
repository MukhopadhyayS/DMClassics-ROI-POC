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
@XmlType(name = "FaxInfo")
@XmlRootElement(name = "faxinfo", namespace = EIGConstants.TYPE_NS_V1)
public class FaxInfo {

	/**
	 * Holds instance of Message as String.
	 */
	private String _message;

	/**
	 * Holds instance of TO as String.
	 */
	private String _to;

	/**
	 * Returns Message instance value.
	 * 
	 * @return String
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * Hold the Message value.
	 * 
	 * @param message
	 */
	@XmlElement(name = "message")
	public void setMessage(String message) {
		this._message = message;
	}

	/**
	 * Returns TO instance value.
	 * 
	 * @return String
	 */
	public String getTo() {
		return _to;
	}

	/**
	 * Hold the TO value.
	 * 
	 * @param to
	 */
	@XmlElement(name = "to")
	public void setTo(String to) {
		this._to = to;
	}
}
