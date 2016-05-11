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

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author OFS
 * @date   Sep 29, 2008
 * @since  HPF 13.1 [ROI]; Sep 29, 2008
 */
public class ConfigUtilMessages {

    private static ResourceBundle _messages;

    static {

        _messages = ResourceBundle.getBundle("messages/Messages", Locale.getDefault());
    }

    public static String getMessage(String key) {

        return _messages.getString(key);
    }
}
