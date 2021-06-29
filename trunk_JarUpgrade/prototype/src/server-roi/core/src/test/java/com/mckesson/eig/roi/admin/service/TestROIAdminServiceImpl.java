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

package com.mckesson.eig.roi.admin.service;

import java.util.List;

import com.mckesson.eig.roi.admin.model.Country;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;

public class TestROIAdminServiceImpl
extends BaseROITestCase {

    protected static final String ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;

    public void initializeTestData() throws Exception {
      _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
    
    
    /**
     * Tests update of Weight method with maximum
     */
    public void testRetrieveAllCountries() {

        try {
            List<Country> countryList = _adminService.retrieveAllCountries();
            System.out.println(countryList.size());
            assertTrue(countryList.size() > 0);
        } catch (ROIException e) {
           // assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
    }
   
    public void testCreateCountryCode() {

       /* try {
            CountryCodeDetails countryCodeDetails = new CountryCodeDetails();
            countryCodeDetails.setAppKey("ROI_DEFAULT_COUNTRY");
            countryCodeDetails.setCountryCode("USA");
            countryCodeDetails.setCountryName("United States Of America");
            countryCodeDetails.setCreatedBy(1L);
            countryCodeDetails.setModifiedBy(1L);
            countryCodeDetails.setCreatedDt(new Date());
            countryCodeDetails.setModifiedDt(new Date());
            countryCodeDetails.setRecordVersion(1);
            _adminService.createCountryCode(countryCodeDetails);            
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }*/
    }
    
    
    public void testUpdateCountryCode() {
        try {
            Country country = new Country();
            country.setCountryCode("USA");
            country.setCountryName("United States Of America");
            _adminService.updateCountryCode(country);            
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
    }
    
    public void testRetrieveDefaultCountry() {
        /*try {
            Country country = _adminService.retrieveDefaultCountry();
            assertNotNull(country);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }*/
    }
    
}
