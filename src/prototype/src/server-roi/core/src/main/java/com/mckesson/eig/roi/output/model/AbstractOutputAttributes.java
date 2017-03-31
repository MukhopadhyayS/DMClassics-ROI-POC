/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This abstract class is to define output attributes.
 * Validate methods.
 *
 *  @author Eric Yu
 *
 *  @since 16.2
*/
public abstract class AbstractOutputAttributes {

	private Map<String, String> _attributes;

	public Map<String, String> getAttributes() {
		return _attributes;
	}
	
	public void setAttributes(Map<String, String> attributes) {
		_attributes = attributes;
	}
	
	public void addAttributes(String key, String value) {
		if (_attributes == null) {
			_attributes = new HashMap<String, String>();
		}
		_attributes.put(key, value);
	}
}
