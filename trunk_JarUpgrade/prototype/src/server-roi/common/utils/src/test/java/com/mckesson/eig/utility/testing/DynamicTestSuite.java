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

package com.mckesson.eig.utility.testing;

import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DynamicTestSuite extends TestSuite {

    public DynamicTestSuite(Class< ? > c) {
        List< Class< ? extends TestCase > > classesFound = new DynamicTestSuiteBuilder(c).getTests();
        // Set testClasses = new HashSet();
        // Set classesCovered = new HashSet();

        for (Class< ? extends TestCase > clazz : classesFound) {
            super.addTestSuite(clazz);

            // Test test = getTest(clazz);

            // if ((test == null) || (!
            // test.getClass().equals(CoverageSuite.class))) {
            // testClasses.add(clazz);
            // } else {
            // CoverageSuite cs = (CoverageSuite) test;
            // testClasses.addAll(Arrays.asList(cs.getTestClasses()));
            // classesCovered.addAll(Arrays.asList(cs.getClassesCovered()));
            // }
        }

        // if ((!testClasses.isEmpty()) && (!classesCovered.isEmpty())) {
        // super.addTest(
        // new CoverageSuite(
        // (Class[]) testClasses.toArray(new Class[testClasses.size()]),
        // (Class[]) classesCovered.toArray(new Class[classesCovered.size()])));
        // }

        setName(c.getName());
    }

    // protected Test invokeSuite(Class c) {
    // return (Test) ReflectionUtilities.callMethod(c, "suite");
    // }
    //
    // protected Test getTest(Class c) {
    // try {
    // return invokeSuite(c);
    // } catch (UtilitiesException e) {
    // return null;
    // }
    // }
}
