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

package com.mckesson.eig.audit;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * class defines resource bundles properties. 
 *
 */
public final class Config {
    /**
     * Object represents resource Bundle name.
     */
    private static final String BUNDLE_NAME = "com.mckesson.eig.audit.config";
    
    /**
     * Object represents resource Bundle.
     */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    /**
     * Default constructor.
     */
    private Config() {
    }
    /**
     * @param key
     *         used for retrieve <code>resource</code>.
     *              
     * @return object of type <code>String</code>.
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
