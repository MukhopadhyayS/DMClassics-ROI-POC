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

package com.mckesson.eig.utility.testing.jdepend;

import jdepend.framework.DependencyConstraint;
import jdepend.framework.JavaPackage;

public abstract class AbstractDependencyTest extends AbstractJDependTest {

    public AbstractDependencyTest(String name) {
        super(name);
    }

    public void testDependencyConstraints() {
        DependencyConstraint constraint = new DependencyConstraint();
        JavaPackage jp = getConstraint(constraint, getPackageName());
        Class< ? >[] classes = getClassesDependedOn();
        for (Class<?> classe : classes)
		{
            jp.dependsUpon(getConstraint(constraint, classe));
        }
        assertTrue("Dependency mismatch", getjdepend().dependencyMatch(
                constraint));
    }

    public JavaPackage getConstraint(DependencyConstraint dc, Class< ? > c) {
        return getConstraint(dc, c.getPackage().getName());
    }

    public JavaPackage getConstraint(DependencyConstraint dc, String s) {
        return dc.addPackage(s);
    }

    public abstract Class< ? >[] getClassesDependedOn();
}
