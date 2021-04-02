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
package com.mckesson.eig.utility.io;
import java.io.File;
import java.net.URL;
/**
 * Provides method which returns the file name for the specified URL.
 * 
 */
public class JDK14MinusURLToFileConverter implements URLToFileConverter {
    /**
     * constructor used for getting  file name for the specified
     * <code>URL</code>.
     */
    public JDK14MinusURLToFileConverter() {
        super();
    }
    /**
     * Returns the file name of this <code>URL</code>.
     * 
     * @param url
     *            source
     * @return file name of this <code>URL</code>.
     */
    public File toFile(URL url) {
        if (url == null) {
            return null;
        }
        return new File(url.getFile());
    }
}
