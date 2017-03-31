/*
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
package com.mckesson.eig.utility.testing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class InvocationAdapter implements MethodInterceptor {

    private InvocationHandler _handler;

    public InvocationAdapter(InvocationHandler handler) {
        _handler = handler;
    }

    public Object intercept(Object enhanced, Method method, Object[] args,
            MethodProxy proxy) throws Throwable {

        return _handler.invoke(enhanced, method, args);
    }
}
