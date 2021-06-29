/*
 * Copyright 2009-2010 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.roi.output.model;

import java.io.Serializable;

/**
 * This class contains  the map model
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class MapModel 
implements Serializable {

    private static final long serialVersionUID = 1L;
    /** This holds the key*/
    private Object _key;
    /** This holds the value*/
    private Object _value;

    public MapModel() {
    }

    public MapModel(Object key, Object value) {
        this._key = key;
        this._value = value;
    }

    public Object getKey() {
        return _key;
    }

    public void setKey(Object key) {
        this._key = key;
    }

    public Object getName() {
        return _key;
    }

    public void setName(Object key) {
        this._key = key;
    }

    public Object getValue() {
        return _value;
    }

    public void setValue(Object value) {
        this._value = value;
    }
}
