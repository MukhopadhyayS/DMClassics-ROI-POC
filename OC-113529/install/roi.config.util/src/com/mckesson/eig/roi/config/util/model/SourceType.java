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


/**
 *
 * @author OFS
 * @date   Mar 2, 2009
 * @since  HPF 13.1 [ROI]; Mar 2, 2009
 */
public class SourceType
implements Serializable {

    private String _type;
    private boolean _enabled;

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public boolean getEnabled() { return _enabled; }
    public void setEnabled(boolean enabled) { _enabled = enabled; }

}
