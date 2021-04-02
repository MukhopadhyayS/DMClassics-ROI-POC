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
import java.util.List;


/**
 *
 * @author OFS
 * @date   Mar 4, 2009
 * @since  HPF 13.1 [ROI]; Mar 4, 2009
 */
public class EndPointDefList
implements Serializable {

    private List<SourceDef> _sourceDefs;
    private List<DestinationDef> _destinationDefs;

    public List<SourceDef> getSourceDefs() { return _sourceDefs; }
    public void setSourceDefs(List<SourceDef> defs) { _sourceDefs = defs; }

    public List<DestinationDef> getDestinationDefs() { return _destinationDefs; }
    public void setDestinationDefs(List<DestinationDef> defs) { _destinationDefs = defs; }

}
