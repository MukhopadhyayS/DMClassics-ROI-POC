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
 * @date   Aug 12, 2008
 * @since  HPF 13.5 [ROI]; Aug 12, 2008
 */
public class ROIAppData
implements Serializable {

    private boolean _hasSSNMasking;
    private List<String> _freeFormFacilities;
    private List<Integer> _invoiceDueDays;

    public boolean getHasSSNMasking() {  return _hasSSNMasking; }
    public void setHasSSNMasking(boolean mask) { _hasSSNMasking = mask; }

    public List <String> getFreeFormFacilities() { return _freeFormFacilities; }
    public void setFreeFormFacilities(List <String> freeFormFacilities) {
        _freeFormFacilities = freeFormFacilities;
    }
    
    public List<Integer> getInvoiceDueDays() { return _invoiceDueDays; }
    public void setInvoiceDueDays(List<Integer> invoiceDueDays) { 
        _invoiceDueDays = invoiceDueDays;
    }
}
