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

package com.mckesson.eig.utility.testing;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * This utility class creates a spring bean factory for use by the junit test
 * cases.
 */
public final class UnitTestSpringInitializer {

	private static final String SPRING_CONFIG_FILE
		= "com/mckesson/eig/utility/config/spring_config.xml";
	private static BeanFactory _factory = null;

	public static void init() {
		if (_factory == null) {
			_factory = new XmlBeanFactory(new ClassPathResource(
					SPRING_CONFIG_FILE));
		}
		SpringUtilities.getInstance().setBeanFactory(_factory);
	}

	public static void init(String fileName) {
		_factory = new XmlBeanFactory(new ClassPathResource(fileName));
		SpringUtilities.getInstance().setBeanFactory(_factory);
	}

	private UnitTestSpringInitializer() {
	}
}
