/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Karthik Easwaran
 * @date   Apr 23, 2013
 * @since  Apr 23, 2013
 */
public class ROITestSuite extends TestSuite {

    private Class<?> _klass;
    private Vector<Test> fTests= new Vector<Test>(10);

    public ROITestSuite() {
    }

    /**
     * @param theClass
     */
    public ROITestSuite(Class<? extends TestCase> clazz) {

        setName(clazz.getName());
        Class superClass = clazz;
        List<String> names = new ArrayList<String>();
        while (Test.class.isAssignableFrom(superClass)) {

            Method[] declaredMethods = superClass.getDeclaredMethods();
            List<Method> methodList = Arrays.asList(declaredMethods);
            Collections.sort(methodList, MethodComparator.getMethodComparatorForJUnit3());

            for (Method each : methodList) {
                addTestMethod(each, names, superClass);
            }
            superClass= superClass.getSuperclass();
        }
        _klass = clazz;
    }

    /**
     * @param name
     */
    public ROITestSuite(String name) {
        super(name);
    }

    /**
     * @param classes
     */
    public ROITestSuite(Class<? extends TestCase>... classes) {
        for (Class<? extends TestCase> each : classes)
            addTest(new ROITestSuite(each));
    }

    /**
     * @param theClass
     * @param name
     */
    public ROITestSuite(Class<? extends TestCase> theClass, String name) {
        super(theClass, name);
    }

    /**
     * @param classes
     * @param name
     */
    public ROITestSuite(Class<? extends TestCase>[] classes, String name) {
        super(classes, name);
    }

    protected void addTestMethod(Method m, List<String> names, Class<? extends TestCase> theClass) {
        String name= m.getName();
        if (names.contains(name))
            return;
        if (! isPublicTestMethod(m)) {
            if (isTestMethod(m))
                addTest(warning("Test method isn't public: "+m.getName()));
            return;
        }
        names.add(name);
        addTest(createTest(theClass, name));
    }

    protected boolean isPublicTestMethod(Method m) {
        return isTestMethod(m) && Modifier.isPublic(m.getModifiers());
     }

    protected boolean isTestMethod(Method m) {
        String name= m.getName();
        Class[] parameters= m.getParameterTypes();
        Class returnType= m.getReturnType();
        return parameters.length == 0 && name.startsWith("test") && returnType.equals(Void.TYPE);
     }

}
