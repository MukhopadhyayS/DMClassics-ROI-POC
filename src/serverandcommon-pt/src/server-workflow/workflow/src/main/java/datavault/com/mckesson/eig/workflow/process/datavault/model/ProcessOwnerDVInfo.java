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
public class ProcessOwnerDVInfo extends ProcessDVInfo {

	private int _ownerId;

	private String _name;
    private String _ownerName;
	/**
	 * @return ownerId
	 */
	public int getOwnerId() {
		return _ownerId;
	}

	/**
	 * @param ownerId
	 */
	public void setOwnerId(int ownerId) {
		this._ownerId = ownerId;
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
     * @return ownerName
     */
    public String getOwnerName() {
        return _ownerName;
    }

    /**
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        this._ownerName = ownerName;
    }
}
