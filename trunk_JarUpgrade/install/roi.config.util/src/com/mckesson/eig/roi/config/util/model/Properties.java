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

package com.mckesson.eig.roi.config.util.model;

import java.io.Serializable;
import java.util.Map;


/**
 *
 * @author OFS
 * @date   Mar 4, 2009
 * @since  HPF 13.1 [ROI]; Mar 4, 2009
 */
public class Properties
implements Serializable {

    private Map<String, String> _property;

    public Map<String, String> getProperty() { return _property; }
    public void setProperty(Map<String, String> property) { _property = property; }

}
