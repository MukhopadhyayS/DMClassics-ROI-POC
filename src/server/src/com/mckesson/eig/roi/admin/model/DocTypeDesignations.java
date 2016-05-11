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

import java.io.Serializable;
import java.util.List;



/**
 * @author OFS
 * @date   Sep 10, 2008
 * @since  HPF 13.1 [ROI]; Sep 10, 2008
 */
public class DocTypeDesignations
implements Serializable {

    private List<Designation> _designation;

    public List<Designation> getDesignation() { return _designation; }
    public void setDesignation(List<Designation> designation) { _designation = designation; }

    public Designation getDesignation(String type) {

        for (Designation desig : _designation) {

            if (type.equals(desig.getType())) {
                return desig;
            }
        }
       return null;
    }

}
