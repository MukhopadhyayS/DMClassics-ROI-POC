/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.docx.exceptions;

/**
 * @author Eric yu
 *
 */
public class ConversionException extends Exception {
	private static final long serialVersionUID = -253710329426771793L;

	/**
	 * 
	 */
	public ConversionException() {
	}

	/**
	 * @param message
	 */
	public ConversionException(String message) {
		super(message);
	}

	/**
	 * @param t
	 */
	public ConversionException(Throwable t) {
		super(t);
	}

	/**
	 * @param message
	 * @param t
	 */
	public ConversionException(String message, Throwable t) {
		super(message, t);
	}
}
