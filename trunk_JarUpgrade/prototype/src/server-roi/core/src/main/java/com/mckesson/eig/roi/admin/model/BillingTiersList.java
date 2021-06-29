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
 * @author Ganeshramr
 * @date   Apr 15, 2008
 * @since  HPF 13.1 [ROI]; Apr 15, 2008
 */
public class BillingTiersList
implements Serializable {

    private List<BillingTier> _billingTiers;

    public BillingTiersList() { }

    public BillingTiersList(List<BillingTier> billingTiers) { setBillingTiers(billingTiers); }

    public List <BillingTier> getBillingTiers() { return _billingTiers; }
    public void setBillingTiers(List <BillingTier> tiers) { _billingTiers = tiers;  }

}
