/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.docx.model;

import java.util.Properties;

/**
 * @author Eric Yu
 *
 */
public class Configuration {

	private static Properties props = new Properties();
	

	/**
	 * @return
	 */
	public static Properties getProperties() {
		return props;
	}
	
	/**
	 * Get the property value for the key provided.
	 * @param key
	 * @return
	 */
	public static String getProperty (String key) {
		return props.getProperty(key);
	}
	
	/**
	 * Setting the property.
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
}
