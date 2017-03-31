/**
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.DynaBean;

public class DynaBeanProperty implements BeanProperty {

    private final String _propertyName;

    public DynaBeanProperty(String propertyName) {
        _propertyName = propertyName;
    }

    public Object read(Object bean) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        return ((DynaBean) bean).get(_propertyName);
    }
}
