/**
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
package com.mckesson.eig.utility.util;

import java.io.InputStream;
import java.util.Properties;

import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public final class PropertyUtilities {

    private static final Log LOG = LogFactory
            .getLogger(PropertyUtilities.class);

    private PropertyUtilities() {
    }

    public static Properties load(String fileName) {
        try {
            return load(FileLoader.getResourceAsInputStream(fileName));
        } catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public static Properties load(InputStream stream) {
        try {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }
}
