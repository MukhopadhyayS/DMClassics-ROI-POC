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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author Vidhya.C.S
 * @date   Apr 28, 2008
 * @since  HPF 13.1 [ROI]; Apr 28, 2008
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingTemplateList", propOrder = {
    "_billingTemplates"
})
public class BillingTemplatesList {

    @XmlElement(name="billingTemplates")
    private List <BillingTemplate> _billingTemplates;
    public BillingTemplatesList() { };

    public BillingTemplatesList(List<BillingTemplate> list) { setBillingTemplates(list); }

    public List<BillingTemplate> getBillingTemplates() { return _billingTemplates; }
    public void setBillingTemplates(List<BillingTemplate> billingTemplates) {
        _billingTemplates = billingTemplates;
    }
}
