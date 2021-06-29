/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.config.util.api;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;


/**
 *
 * @author OFS
 * @date   Sep 30, 2008
 * @since  HPF 13.1 [ROI]; Sep 30, 2008
 */
public class LogInitializer {

    public static final String LOG_PROPERTIES_FILE = "config\\logging\\log.properties";

    public static void initializeLogger() {

        try {

            Properties logProperties = new Properties();
            logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
            PropertyConfigurator.configure(logProperties);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
