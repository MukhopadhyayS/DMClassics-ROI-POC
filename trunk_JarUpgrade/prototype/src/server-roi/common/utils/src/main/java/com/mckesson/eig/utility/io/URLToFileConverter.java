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
 * This should be implemented by a class which needs to convert the resource URL
 * into a file.
 * 
 */
public interface URLToFileConverter {

    /**
     * when an class implements this interface ,this method is used for making
     * the conversion.
     * 
     * @param url
     *            source URL.
     * @return URL converted file.
     */
    File toFile(URL url);
}
