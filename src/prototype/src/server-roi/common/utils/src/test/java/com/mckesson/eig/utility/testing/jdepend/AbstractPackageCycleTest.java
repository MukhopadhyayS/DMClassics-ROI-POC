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

import java.util.ArrayList;
import java.util.List;

import jdepend.framework.JavaPackage;

public abstract class AbstractPackageCycleTest extends AbstractJDependTest {

    public AbstractPackageCycleTest(String name) {
        super(name);
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        getjdepend().analyze();
    }

    public void testPackageDoesNotContainDependencyCycles() {
        JavaPackage p = getjdepend().getPackage(getPackageName());
        assertNotNull("JDepend did not load package correctly", p);
        if (p.containsCycle()) {
            printCycles(p);
        }
        assertFalse("Cycles exist: " + p.getName(), p.containsCycle());
    }

    protected void printCycles(JavaPackage p) {
        System.out.println(p.getName() + " contains cycle(s).");
        List<JavaPackage> list = new ArrayList<JavaPackage>();
        p.collectCycle(list);
        for(JavaPackage pack : list) {
            System.out.println("\t" + pack.getName());
        }
    }
}
