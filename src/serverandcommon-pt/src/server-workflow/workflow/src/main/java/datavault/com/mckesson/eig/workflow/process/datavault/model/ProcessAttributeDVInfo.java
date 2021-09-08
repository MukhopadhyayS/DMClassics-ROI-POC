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
package com.mckesson.eig.workflow.process.datavault.model;

/**
 * @author OFS
 *
 * @date Apr 2, 2009
 * @since HECM 1.0.3; Apr 2, 2009
 */
public class ProcessAttributeDVInfo extends ProcessDVInfo {

	private int _versionId;

	private String _name;
	private String _attributeName;
	private String _attributeValue;
	private String _attributeType;

	/**
	 * @return versionId
	 */
	public int getVersionId() {
		return _versionId;
	}

	/**
	 * @param versionId
	 */
	public void setVersionId(int versionId) {
		this._versionId = versionId;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this._name = name;
	}

	/**
	 * @return attributeName
	 */
	public String getAttributeName() {
		return _attributeName;
	}

	/**
	 * @param attributeName
	 */
	public void setAttributeName(String attributeName) {
		this._attributeName = attributeName;
	}

	/**
	 * @return attributeValue
	 */
	public String getAttributeValue() {
		return _attributeValue;
	}

	/**
	 * @param attributeValue
	 */
	public void setAttributeValue(String attributeValue) {
		this._attributeValue = attributeValue;
	}

	/**
	 * @return attributeType
	 */
	public String getAttributeType() {
		return _attributeType;
	}

	/**
	 * @param attributeType
	 */
	public void setAttributeType(String attributeType) {
		this._attributeType = attributeType;
	}
}
