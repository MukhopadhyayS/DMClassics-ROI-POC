/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

/**
 * <code>LogConfigurationDetailList</code> provides the list of available
 * components.
 */
public class LogConfigurationDetailList {

    /**
     * Available components list.
     */
    private List _componentList;

    /**
     * Obtains the componentlist.
     * 
     * @return componentList.
     */
    public List getComponentList() {
        return _componentList;
    }

    /**
     * Sets this componentList
     * 
     * @param componentList
     *            list of components to be set.
     */
    public void setComponentList(List componentList) {
        this._componentList = componentList;
    }

}
