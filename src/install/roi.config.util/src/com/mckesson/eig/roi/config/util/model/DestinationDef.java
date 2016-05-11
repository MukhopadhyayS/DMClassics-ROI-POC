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
 * @date   Mar 4, 2009
 * @since  HPF 13.1 [ROI]; Mar 4, 2009
 */
public class DestinationDef
implements Serializable {

    private String _type;
    private String _name;
    private String _description;
    private Properties _properties;
    private String _deviceId;

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public Properties getProperties() { return _properties; }
    public void setProperties(Properties properties) { _properties = properties; }

    public String getDeviceId() { return _deviceId; }
    public void setDeviceId(String id) { _deviceId = id; }

}
