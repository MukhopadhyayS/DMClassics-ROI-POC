/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.admin.model.TaxPerFacilityList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author OFS
 * @date   Aug 8, 2011
 * @since  Aug 8, 2011
 */
public class TestSalesTaxFacilityServiceImpl
extends BaseROITestCase {

    protected static final String ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    private static BillingAdminService _adminService;
    private static String              _salesTaxFacilityDesc = "Test";

    private static long _salesTaxFacilityId1;
    private static long _salesTaxFacilityId2;
    private static long count = 0;

    private static String _facilityCode1 = "E_P_R";
    private static String _facilityCode2 = "T_E_S_T";

    private DataSource _dataSource;

    private JdbcTemplate _jdbcTemplate;

    private static final float VALIDPERCENTAGE = 10.23f;
    private static final float INVALIDPERCENTAGE = 1780.2893f;


    @Override
    public void initializeTestData() {

        _adminService = (BillingAdminService) getService(ADMIN_SERVICE);

        UserSecurityHibernateDao dao = (UserSecurityHibernateDao) SpringUtilities.getInstance()
                .getBeanFactory().getBean(UserSecurityHibernateDao.class.getName());

        UserSecurity us = new UserSecurity(getUser().getInstanceIdValue(),
                                           UserSecurity.ENTERPRISE,
                                           6101);

        dao.getHibernateTemplate().saveOrUpdate(us);

        createFacility(_facilityCode1);
        createFacility(_facilityCode2);
    }


    private void createFacility(String facilityCode) {

        _dataSource = (DataSource) SpringUtilities.getInstance()
                .getBeanFactory().getBean("dataSource");
        _jdbcTemplate = new JdbcTemplate(_dataSource);

        String[] insertSql = new String[2];
        String[] deleteSql = new String[3];
        insertSql[0] = "insert into FACILITY_FILE(FACILITY_CODE, FACILITY_NAME) values ('"
                            + facilityCode + "', '" + facilityCode  + "')";

        insertSql[1] = "INSERT INTO [dbo].[User_Facility] ([USER_ID], [FACILITY]) values ('"
                + getUser().getInstanceIdValue() + "', '" + facilityCode  + "')";

        deleteSql[0] = "delete from ROI_SalesTaxPerFacility where FACILITY_CODE in ('"
                            + facilityCode + "')";

        deleteSql[1] = "delete from User_Facility where FACILITY in ('" + facilityCode + "')";

        deleteSql[2] = "delete from FACILITY_FILE where FACILITY_CODE in ('" + facilityCode + "')";


        _jdbcTemplate.batchUpdate(deleteSql);
        _jdbcTemplate.batchUpdate(insertSql);
    }

    public void testRetrieveSalesTaxFacilityByUser() {

        try {

            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            _salesTaxFacilityId1 = _adminService.createTaxPerFacility(salesTaxFacility);

            TaxPerFacilityList facilityList = _adminService
                    .retrieveAllTaxPerFacilitiesByUser(getUser().getLoginId());

            assertTrue(facilityList.getSalesTaxFacilityList().size() > 0);

        } catch (ROIException e) {
            fail("Retrieve Sales Tax facility should not throw exception." + e.getErrorCode());
        }
    }

    public void testRetrieveSalesTaxFacilityByUserWithInvalidUser() {

        try {

            _adminService.retrieveAllTaxPerFacilitiesByUser(null);
            fail("Retrieve Sales Tax facility should throw exception.");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.USER_ID_CANNOT_BE_EMPTY);
        }
    }

    /**
     * This test case is for creating the salesTaxFacility and verifying if it
     * returns the newly generated
     * id .
     */
    public void testCreatesalesTaxFacility() {

        try {

           TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            _salesTaxFacilityId1 = _adminService.createTaxPerFacility(salesTaxFacility);
           assertNotSame("Created salesTaxFacility id should be greater than zero", 0,
                   _salesTaxFacilityId1);
        } catch (ROIException  e) {
            fail("Create salesTaxFacility should not thrown exception." + e.getErrorCode());
        }
    }
    /**
     * This test case is for creating the SalesTax for facility with invalid data.
     * id .
     */
    public void testCreatesalesTaxFacilityWithInvalidData() {

        try {

            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            salesTaxFacility.setDescription(appendString(_salesTaxFacilityDesc));
            salesTaxFacility.setTaxPercentage(INVALIDPERCENTAGE);

            _salesTaxFacilityId1 = _adminService.createTaxPerFacility(salesTaxFacility);
            fail("Creation of SalesTax for Facility with Invalid data is not permitted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALESTAX_PERCENTAGE_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.SALESTAX_PERCENTAGE_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.FACILITY_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * this method create salesTax rate for a facility with null user
     */
    public void testCreatesalesTaxFacilityWithNullUser() {

        try {

            initSession(null);
            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            salesTaxFacility.setCode("TEST");
            _adminService.createTaxPerFacility(salesTaxFacility);
            fail("Creation of sales Tax rate for a facility with null user is not "
                    + "permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }

    /**
     * This test case is for creating the sales tax rate with duplicate facility code and
     *  verifying if it returns
     * the appropriate ROIException .
     */
    public void testCreatesalesTaxFacilityWithDuplicateName() {

        try {

            initSession();
            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            _adminService.createTaxPerFacility(salesTaxFacility);
            fail("Create sales tax for a facility with duplicate facility code is not permitted, "
                    + "but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALES_TAX_FACILITY_CODE_IS_NOT_UNIQUE);

        }
    }

    /**
     * This test case is for creating the sales tax rate with no facility code and verifying
     * if it returns the
     * appropriate ROIException .
     */
    public void testCreatesalesTaxFacilityWithoutCode() {

        try {

            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            salesTaxFacility.setCode(null);
            _adminService.createTaxPerFacility(salesTaxFacility);
            fail("Create sales Tax for a facility without facility code is not permitted,"
                    + " but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FACILITY_CODE_IS_BLANK);
        }
    }

    /**
     * This test case is for creating the sales tax for a facility with no description
     * and verifying if it returns
     * the appropriate ROIException .
     */
    public void testCreatesalesTaxFacilityWithoutDescription() {

        try {

            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            salesTaxFacility.setCode(_facilityCode2);
            salesTaxFacility.setDescription(null);

            _salesTaxFacilityId2 = _adminService.createTaxPerFacility(salesTaxFacility);
            assertNotSame("Created sales tax facility id should be greater than zero",
                          0,
                          _salesTaxFacilityId2);

        } catch (ROIException e) {
            fail("Create salesTaxFacility should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the sales tax for a facility  with null
     * data and verifying if it
     * returns the appropriate ROIException .
     */
    public void testCreatesalesTaxFacilityWithNull() {

        try {

             _adminService.createTaxPerFacility(null);
            fail("Create salesTaxFacility with null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }


    /**
     * This test case check the sales tax facility with more description length
     */
    public void testCreatesalesTaxFacilityWithMoreLengthDesc() {

        try {

            TaxPerFacility salesTaxFacility = new TaxPerFacility();
            salesTaxFacility
            .setDescription(_salesTaxFacilityDesc  + new StringBuffer()
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .toString());

            _adminService.createTaxPerFacility(salesTaxFacility);
            fail("Create salesTaxFacility with more desc length is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FACILITY_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for updating the given sales tax percentage for a facility.
     */
    public void testUpdatesalesTaxFacility() {

        try {

            TaxPerFacility salesTaxFacility =
                _adminService.retrieveTaxPerFacility(_salesTaxFacilityId1);

            salesTaxFacility.setTaxPercentage(VALIDPERCENTAGE);
            salesTaxFacility.setDefault(ROIConstants.Y);

            TaxPerFacility updatedSTF = _adminService.updateTaxPerFacility(salesTaxFacility);
            assertEquals(updatedSTF.getTaxPercentage(), salesTaxFacility.getTaxPercentage());
        } catch (ROIException e) {
            fail("updating sales tax for a facility should not thrown exception"
                    + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the sales tax for a facility with invalid data.
     * id .
     */
    public void testUpdatesalesTaxFacilityWithInvalidData() {

        try {

            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            salesTaxFacility.setId(_salesTaxFacilityId1);
            salesTaxFacility.setTaxPercentage(INVALIDPERCENTAGE);

            _adminService.updateTaxPerFacility(salesTaxFacility);
            fail("Updation of salesTax with Invalid data is not permitted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALESTAX_PERCENTAGE_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.SALESTAX_PERCENTAGE_LENGTH_EXCEEDS_LIMIT);
        }
    }
    /**
     * This test case is for updating the given sales tax with null user.
     */
    public void testUpdatesalesTaxFacilityWitNullUser() {

        try {

            initSession(null);
            TaxPerFacility salesTaxFacility =  _adminService.retrieveTaxPerFacility(
                                                        _salesTaxFacilityId1);
            salesTaxFacility.setDescription("Update descript");
             _adminService.updateTaxPerFacility(salesTaxFacility);
            fail("Updating the sales tax for a facility with null user is not permitted,"
                    + " but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }

    /**
     * This test case is for updating the given sales tax for a facility with duplicate code.
     */
    public void testUpdatesalesTaxFacilityWithDuplication() {

        try {
            initSession();
            TaxPerFacility salesTaxFacility = _adminService.retrieveTaxPerFacility(
                                                                _salesTaxFacilityId1);
            salesTaxFacility.setDescription(_salesTaxFacilityDesc + "");
            salesTaxFacility.setCode(_facilityCode2);

            _adminService.updateTaxPerFacility(salesTaxFacility);
            fail("updating salesTaxFacility with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALES_TAX_FACILITY_CODE_IS_NOT_UNIQUE);
        }
    }


    /**
     * This test case is for updating the given sales tax for a facility with empty.
     */
    public void testUpdatesalesTaxFacilityWithEmpty() {

        try {

            TaxPerFacility salesTaxFacility = new TaxPerFacility();
            salesTaxFacility.setId(_salesTaxFacilityId1);

            _adminService.updateTaxPerFacility(salesTaxFacility);
            fail("Updating salesTaxFacility with null data is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FACILITY_CODE_IS_BLANK);
        }
    }

    /**
     * This test case is for updating the Sales Tax for a facility name with exceeded code length.
     */
    public void testUpdatesalesTaxFacilityWithMoreSize() {

        try {

            TaxPerFacility salesTaxFacility = createSalesTaxFacilityData();
            salesTaxFacility.setId(_salesTaxFacilityId1);
            salesTaxFacility.setCode(appendString("Code"));

            _adminService.updateTaxPerFacility(salesTaxFacility);
            fail("Updating sales tax for a facility with code more length is"
                    + " not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FACILITY_CODE_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for updating the given sales tax for a facility with empty.
     */
    public void testUpdatesalesTaxFacilityWithSpace() {

        try {

            TaxPerFacility salesTaxFacility = new TaxPerFacility();
            salesTaxFacility.setId(_salesTaxFacilityId1);
            salesTaxFacility.setCode("");

            _adminService.updateTaxPerFacility(salesTaxFacility);
            fail("Updating sales tax for a facility with code is not permitted, but updated.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FACILITY_CODE_IS_BLANK);
        }
    }

    /**
     * This test case is to get the sales Tax for facility with invalid id.
     */
    public void testRetrievesalesTaxFacilityWithInvalidId() {

        try {

            _adminService.retrieveTaxPerFacility(Integer.MAX_VALUE);
            fail("Retrieving salesTaxFacility with invalid id is not permitted, but retrieved.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This method retrieve all the sales taxes successfully
     */
    public void testRetrieveAllsalesTaxFacility() {

        try {

            TaxPerFacilityList stf = new TaxPerFacilityList();
            stf = _adminService.retrieveAllTaxPerFacilities();
            assertNotSame("The size of retrieved sales tax rate should be greater than zero",
                          0,
                          stf.getSalesTaxFacilityList().size());

        } catch (ROIException e) {
            fail("Service method test retrieveAllsalesTaxFacility should not thrown exception");
        }
    }


    /**
     * This test case is for deleting the given sales tax facility id and to check
     * whether it return nothing
     * or it throws any exception
     */
    public void testDeletesalesTaxFacility() {

        try {

            initSession();
            _adminService.deleteTaxPerFacility(_salesTaxFacilityId2);
        } catch (ROIException e) {
            fail("Delete Sales tax for a facility should not throw Exception"  + e.getMessage());
        }
    }

    /**
     * This test case is for deleting the given sales tax for facility id with null user
     */
    public void testDeletesalesTaxFacilityWithNullUser() {

        try {

            initSession(null);
            _adminService.deleteTaxPerFacility(_salesTaxFacilityId1);
            fail("Deleting sales Tax for a facility with null user is not permitted, "
                    + "but it is deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }

    /**
     * This method checks the deletion with invalid id ROIException should be thrown with error code
     */
    public void testDeletesalesTaxFacilityWithInvalidId() {

        try {

            initSession();
            final long invalidId = 100001;
            _adminService.deleteTaxPerFacility(invalidId);
            fail("Delete salesTaxFacility with invalid id is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case is for deleting the given sales tax facility id and to
     *  check whether it return nothing
     * or it throws any exception
     */
    public void testDeleteAndRetrievesalesTaxFacility() {

        try {

            _adminService.deleteTaxPerFacility(_salesTaxFacilityId1);
            _adminService.retrieveTaxPerFacility(_salesTaxFacilityId1);
            fail("Retrieving the deleted salesTaxFacility id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }


    @Override
    protected String getServiceURL(String serviceMethod) {
        return "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";
    }

    private TaxPerFacility createSalesTaxFacilityData() {

        TaxPerFacility salesTaxFacility = new TaxPerFacility();
        String facility = _facilityCode1 + count++;
        salesTaxFacility.setCode(facility);
        salesTaxFacility.setDescription(_salesTaxFacilityDesc);
        salesTaxFacility.setTaxPercentage(VALIDPERCENTAGE);
        salesTaxFacility.setDefault(ROIConstants.Y);

        createFacility(facility);

        return salesTaxFacility;
    }
}
