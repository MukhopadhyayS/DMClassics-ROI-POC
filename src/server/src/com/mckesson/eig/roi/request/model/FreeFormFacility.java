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

package com.mckesson.eig.roi.request.model;

import com.mckesson.eig.roi.base.model.BaseModel;

public class FreeFormFacility extends BaseModel {

    private static final long serialVersionUID = 1L;
    
    private long    id;
    private String  freeFormFacilityName;
    
    public void setId(long id) { 
            this.id = id; 
    }
    
    public long getId() { 
        return id; 
    }
    
    public String getFreeFormFacilityName() {
        return freeFormFacilityName;
    }

    public void setFreeFormFacilityName(String freeFormFacilityName) {
        this.freeFormFacilityName = freeFormFacilityName;
    }    
   
    @Override
    public String toString() {
        
        return new StringBuffer()
                    .append("FreeFormFacility:")
                    .append(id)
                    .append(" , Facility:")
                    .append(freeFormFacilityName)
                .toString();
    }
    
    
}
