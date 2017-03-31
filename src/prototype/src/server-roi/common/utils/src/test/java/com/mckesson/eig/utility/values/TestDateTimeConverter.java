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

package com.mckesson.eig.utility.values;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestDateTimeConverter extends TestCase {
    private DateTimeConverter _dateTimeConverter;

    private String _format;

    private Class< ? > _validclass;

    private Class< ? > _invalidclass;

    public static Test suite() {
        return new CoverageSuite(TestDateTimeConverter.class,
                DateTimeConverter.class);
    }

    public TestDateTimeConverter(String name) {
        super(name);
    }

    @Override
	protected void setUp() {
        _validclass = DateTime.class;
        _format = "M/d/yy h:mm:ss";
        _validclass = DateTime.class;
        _invalidclass = DateTimeConverter.class;
        _dateTimeConverter = new DateTimeConverter(_format);
    }

    @Override
	protected void tearDown() {
        _dateTimeConverter = null;
    }

    public void testConvert() {

        Object convert = _dateTimeConverter.convert(_validclass, "03/03/02 3:22:22");
		assertNotNull(convert);
        assertEquals(convert.getClass(), DateTime.class);
    }

    public void testNullValue() {
        assertNull(_dateTimeConverter.convert(_validclass, null));
    }

    public void testInvalidClass() {
        try {
            _dateTimeConverter.convert(_invalidclass, "03/03/02 3:22:22");
            fail();
        } catch (Exception e) {
            return;
        }
    }
    public void testInvalidValue() {
        try {
            DateTime dt = new DateTime();
            _dateTimeConverter.convert(_validclass, dt);
            fail();
        } catch (Exception e) {
            return;
        }
    }
}
