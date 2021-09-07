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

package com.mckesson.eig.roi.admin.model;

import java.util.List;

/**
 * @author OFS
 * @date   Feb 24, 2010
 * @since  HPF 15.1 [ROI];
 */
public class ROIParameterLsit {

    private List<ROIParameter> _roiParameters;

    public ROIParameterLsit() { };
    public ROIParameterLsit(List<ROIParameter> roiParameters) {
        _roiParameters = roiParameters;
    }

    public List<ROIParameter> getRoiParameters() { return _roiParameters; }
    public void setRoiParameters(List<ROIParameter> roiParameters) {
        _roiParameters = roiParameters;
    }
}
