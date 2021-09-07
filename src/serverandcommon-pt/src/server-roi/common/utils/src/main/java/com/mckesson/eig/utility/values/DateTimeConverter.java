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

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.mckesson.eig.utility.util.StringUtilities;

public class DateTimeConverter implements Converter {

    private final String _dateFormat;

    /**
     * @param dateFormat
     *            Passed as an argument of type <code>String</code>.
     */
    public DateTimeConverter(String dateFormat) {
        _dateFormat = dateFormat;
    }

    /**
     * @param type
     *            Passed as an argument of type <code>Class</code>.
     * @param value
     *            Passed as an argument of type <code>Object</code>.
     * @return <code>null</code> or object of type <code>DateTime</code>.
     */
    @SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {

        // Check for null
        if (value == null) {
            return null;
        }
        // Check destination type
        if (!type.equals(DateTime.class)) {
            throw new ConversionException(
                    "Cannot convert to " + type.getName());
        }
        // Check source type
        if (!(value instanceof String)) {
            throw new ConversionException("Cannot convert DateTime from "
                    + value.getClass().getName());
        }
        return StringUtilities.isEmpty((String) value) ? null : new DateTime(
                (String) value, _dateFormat);
    }
}
