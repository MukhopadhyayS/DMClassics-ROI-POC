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

package com.mckesson.eig.roi.admin.dao;


import java.util.List;

import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.admin.model.TaxPerFacilityList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author rethinamt
 * @date   Aug 01, 2011
 * @since  HPF 15.2 [ROI]
 */
public interface TaxPerFacilityDAO
extends ROIDAO {

    /**
     * This method creates a new SalesTaxPerFacility
     *
     * @param salesTaxPerFacility SalesTax Per Facility details to be created
     * @return Unique Id of the SalesTaxPerFacility
     */
    long createTaxPerFacility(TaxPerFacility salesTaxPerFacility);

    /**
     * This method fetches the salesTaxPerFacility details
     *
     * @param salesTaxPerFacilityId Unique id of the SalesTaxPerFacility
     * @return salesTax for Facility details
     */
    TaxPerFacility retrieveTaxPerFacility(long salesTaxPerFacilityId);

    /**
     * This method fetches all the SalesTaxPerFacility
     *
     * @return List of all SalesTaxPerFacility
     */
    TaxPerFacilityList retrieveAllTaxPerFacilities();

    /**
     * This method updates the salesTaxFacility details
     *
     * @param salesTaxFacility Details of the salesTaxFacility to be updated
     * @param old Details of the old salesTaxFacility
     * @return Updated salesTaxFacility
     */
    TaxPerFacility updateTaxPerFacility(TaxPerFacility salesTaxPerFacility, TaxPerFacility old);

    /**
     * This method fetches the salesTaxFacility details by name
     *
     * @param salesTaxFacility Name of the Media Type
     * @return salesTaxFacility detail for the given name
     */
    TaxPerFacility getSalesTaxFacilityByCode(String salesTaxPerFacilityCode);

    /**
     * This method deletes the selected salesTaxFacility
     *
     * @param salesTaxFacilityId Id of the salesTaxFacility to be deleted
     */
    TaxPerFacility deleteTaxPerFacility(long salesTaxPerFacilityId);
	
	/**
     * This method used to get the default salesTaxFacility
     */
    TaxPerFacility getDefaultTaxPerFacility();
	
	/**
     * This method used to clear the default salesTaxFacility
     */
    void clearDefaultTaxPerFacility(TaxPerFacility defaultTaxFac);

    /**
     * This method retrieves all the facilities of the given user
     */
    List<String> retrieveAllFacilitiesByUser(String loginId);
    

}
