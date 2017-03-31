/*
 * Copyright 2002-03 McKesson Information Solutions
 *
 * The copyright to the computer program(s) herein
 * is the property of McKesson Information Solutions.
 * The program(s) may be used and/or copied only with
 * the written permission of McKesson Information Solutions
 * or in accordance with the terms and conditions 
 * stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
package com.mckesson.eig.utility;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.mckesson.eig.utility.testing.DynamicTestSuite;

public class TestAll extends TestSuite {

	public TestAll(String name) {
		super(name);
	}

	public static Test suite() {
		return new DynamicTestSuite(TestAll.class);
	}
}
