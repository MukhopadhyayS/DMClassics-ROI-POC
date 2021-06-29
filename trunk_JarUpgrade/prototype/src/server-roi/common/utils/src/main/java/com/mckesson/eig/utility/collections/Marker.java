/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.collections;

import java.io.Serializable;

import com.mckesson.eig.utility.exception.InvalidStateException;

/**
 * Overides Objects <code>clone</code> method and defines a
 * <code>serialVersionUID</code>.
 */
public class Marker implements Serializable, Cloneable {

	/**
     * The serialization runtime associates with each serializable class a
     * version number, called a serialVersionUID, which is used during
     * deserialization to verify that the sender and receiver of a serialized
     * object have loaded classes for that object that are compatible with
     * respect to serialization.
     */
    private static final long serialVersionUID = 5394325993153136563L;

    /**
     * No argument constructor.
     */
    public Marker() {
    }
    
    /**
     * This method creates a new instance of the class of this object and
     * initializes all its fields with exactly the contents of the corresponding
     * fields of this object, as if by assignment; the contents of the fields
     * are not themselves cloned.
     *
     * @return a clone of this instance.
     *
     */
    @Override
	public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InvalidStateException(e);
        }
    }
}
