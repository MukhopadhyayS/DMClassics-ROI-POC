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
package com.mckesson.eig.utility.testing.jdepend;

import jdepend.framework.JavaPackage;

/**
 * A template for testing the conformance of a single package from the main
 * sequence (D) withing a tolerance.
 */
public abstract class AbstractDistanceTest extends AbstractJDependTest {

    public AbstractDistanceTest(String name) {
        super(name);
        getjdepend().analyze();
    }

    /**
     * Tests the conformance of a single package to a distance from the main
     * sequence (D) within a tolerance.
     */
    public void testDistanceFromMainSequence() {

        double ideal = 0.0;
        double tolerance = getDistanceTolerance(); // project-dependent

        JavaPackage p = getjdepend().getPackage(getPackageName());

        assertNotNull(p);
        assertEquals("Distance exceeded: " + p.getName(), ideal, p.distance(),
                tolerance);
    }

    /**
     * Tolerance for deviance from main sequence
     */
    public abstract double getDistanceTolerance();
}
