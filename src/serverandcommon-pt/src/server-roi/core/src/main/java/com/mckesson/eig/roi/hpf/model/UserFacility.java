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

package com.mckesson.eig.roi.hpf.model;

import java.io.Serializable;


/**
 * @author Ganeshram
 * @date   Jun 17, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class UserFacility
implements Serializable {

    private Integer _userId;
    private String _facility;

    public Integer getUserId() { return _userId; }
    public void setUserId(Integer id) { _userId = id; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    @Override
    public boolean equals(Object other) {

        if ((other == null)) {
            return false;
        }
        if (!(other instanceof UserFacility)) {
            return false;
        }
        UserFacility castOther = (UserFacility) other;

        return (getUserId() == castOther.getUserId())
               && (getFacility().equals(castOther.getFacility()));
    }

    @Override
    public int hashCode() {

        int result = 0;

        if ((_userId == null) && (_facility == null)) {
            result = System.identityHashCode(this);
        }
        if ((_userId != null) && (_facility != null)) {
            result = _userId.hashCode() + _facility.hashCode();
        }
       return result;
    }
}
