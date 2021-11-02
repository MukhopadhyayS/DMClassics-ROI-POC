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
 * @author Rethinamt
 * @date   Jul 29, 2011
 * @since  HPF 15.2 [ROI];
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxPerFacilityList", propOrder = {
    "_salesTaxFacilities"
})
public class TaxPerFacilityList {

    @XmlElement(name = "TaxPerFacility")
    private List<TaxPerFacility> _salesTaxFacilities;
    
    public TaxPerFacilityList() { };

    public TaxPerFacilityList(List<TaxPerFacility> list) { setSalesTaxFacilityList(list); }

    public List<TaxPerFacility> getSalesTaxFacilityList() { return _salesTaxFacilities; }
    public void setSalesTaxFacilityList(List<TaxPerFacility> salesTaxPerFacilitiesList) {
        _salesTaxFacilities = salesTaxPerFacilitiesList;
    }
}
