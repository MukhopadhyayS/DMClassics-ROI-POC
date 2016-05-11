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


package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author OFS
 * @date Sep 24, 2012
 * @since Sep 24, 2012
 *
 */
public class ChargeHistoryList 
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<ChargeHistory> _chargeHistories;
    
    public ChargeHistoryList() { }
    public ChargeHistoryList(List<ChargeHistory> chargeHistory) {
        setChargeHistories(chargeHistory);
    }
    
    public List<ChargeHistory> getChargeHistories() { return _chargeHistories; }
    public void setChargeHistories(List<ChargeHistory> chargeHistories) {
        _chargeHistories = chargeHistories;
    }

}
