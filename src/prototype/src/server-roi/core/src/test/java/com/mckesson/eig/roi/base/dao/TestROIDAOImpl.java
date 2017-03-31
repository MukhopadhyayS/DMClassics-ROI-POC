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

package com.mckesson.eig.roi.base.dao;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.dao.InvoiceDataRetriever;
import com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAOImpl;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAOImpl;
import com.mckesson.eig.roi.request.dao.RequestCoreDAOImpl;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAOImpl;
import com.mckesson.eig.roi.request.model.FreeFormFacility;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author OFS
 * @date   Apr 11, 2011
 * @since  HPF 15.1 [ROI]; Apr 11, 2011
 */

public class TestROIDAOImpl
extends BaseROITestCase {

    private static ROIDAOImpl _dao;
    protected static final String BASEDAO = "RequestorTypeDAO";
    private static final long TWENTY = 20L;
    private static final int TEN = 10;
    private static final double P_DOUBLE_VALUE = 20.5d;
    private static String FREEFORMFACILITYNAME = "freeFormFacility";
    protected static String INVOICE_LETTER = "Invoice_LetterDataRetriever"; 
    private static InvoiceDataRetriever _invDataRetriever;

    @Override
    public void initializeTestData()
    throws Exception {
        _dao = (ROIDAOImpl) getService(BASEDAO);
        _invDataRetriever= (InvoiceDataRetriever) getService(INVOICE_LETTER);
        FREEFORMFACILITYNAME = FREEFORMFACILITYNAME + System.currentTimeMillis();
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    /**
     * Test the long value parsing with null
     */
    public void testPLongWithNullValue() {

        long longValue = _dao.toPlong(null);
        assertEquals(0L, longValue);
    }

    /**
     * Test the long value parsing with Valid data
     */
    public void testPLongWithValidValue() {

        long longValue = _dao.toPlong(TWENTY);
        assertEquals(TWENTY, longValue);
    }

    /**
     * Test the int value parsing with null
     */
    public void testPIntWithNullValue() {

        int intValue = _dao.toPint(null);
        assertEquals(0, intValue);
    }

    /**
     * Test the int value parsing with Valid data
     */
    public void testPIntWithValidValue() {

        int intValue = _dao.toPint(TEN);
        assertEquals(TEN, intValue);
    }

    /**
     * Test the boolean value parsing with null
     */
    public void testPBooleanWithNullValue() {

        boolean bValue = _dao.toPboolean(null);
        assertFalse(bValue);
    }

    /**
     * Test the boolean value parsing with Valid data
     */
    public void testPBooleanWithValidValue() {

        boolean bValue = _dao.toPboolean(true);
        assertTrue("Should return True", bValue);
    }

    /**
     * Test the double value parsing with null
     */
    public void testPDoubleWithNullValue() {

        double dValue = _dao.toPdouble(null);
        assertEquals(0.0, dValue);
    }

    /**
     * Test the double value parsing with Valid data
     */
    public void testPDoubleWithValidValue() {

        double dValue = _dao.toPdouble(P_DOUBLE_VALUE);
        assertEquals(P_DOUBLE_VALUE, dValue);
    }
    
    /**
     * Test the SQL Date value parsing with Valid data
     */
    public void testGetSQLTimeStamp() {
        
        Date date = new Date();
        try {            
            Timestamp  time = _dao.getSQLTimeStamp(date);
            int timeState = time.compareTo(date);
            assertEquals(0, timeState);        
        } catch (ROIException e) {
            fail("Get SQLTimeStamp should not thrown exception." + e.getErrorCode());
        }
    }
    
    /**
     * Test the dollar symbol adding with Valid data
     */
    public void testAppendDollarSymbol() {
        
        try {
            String text = _dao.appendDollarSymbol(20);
            assertEquals("$20.00", text);            
        } catch (ROIException e) {
            fail("Append DollarSymbol should not thrown exception." + e.getErrorCode());
        }
    }
    
    /**
     * Test the dollar symbol removing with Valid data
     */
    public void testSupressDollarSymbol() {
        
        try {
            double amount = _dao.supressDollarSymbol("$20.00");
            assertEquals(20.0, amount);            
        } catch (ROIException e) {
            fail("SupressDollarSymbol should not thrown exception." + e.getErrorCode());
        }
    }
    
    /**
     * Test the retrieveGlobalInvoiceDueDays
     */
    public void testRetrieveGlobalInvoiceDueDays() {
        
        try {
            List<Integer> list = _dao.retrieveGlobalInvoiceDueDays();
            assertNotNull(list);            
        } catch (ROIException e) {
            fail("RetrieveGlobalInvoiceDueDays should not thrown exception." + e.getErrorCode());
        }
    }
    
    /**
     * Test the createFreeFormFacilities
     */
    public void testCreateFreeFormFacilities() {
        
        try {            
            FreeFormFacility ffFacility = new FreeFormFacility();
            ffFacility.setFreeFormFacilityName(FREEFORMFACILITYNAME);
            ffFacility.setCreatedDt(new Date());
            ffFacility.setCreatedBy(1);
            ffFacility.setModifiedDt(new Date());
            ffFacility.setModifiedBy(1);
            ffFacility.setRecordVersion(1);
            
            FreeFormFacility freeFormFacility = _dao.createFreeFormFacilities(ffFacility);
            assertNotNull(String.valueOf(freeFormFacility.getId()));            
        } catch (ROIException e) {
            fail("Create FreeForm Facilities should not thrown exception." + e.getErrorCode());
        }        
    }
    
    public void testRetrieveFreeFormFacilitiesByName() {
        
        try {            
            FreeFormFacility ffFacility = _dao.retrieveFreeFormFacilitiesByName(FREEFORMFACILITYNAME, 1);
            assertEquals(FREEFORMFACILITYNAME, ffFacility.getFreeFormFacilityName());            
        } catch (ROIException e) {
            fail("RetrieveFreeFormFacilitiesByName should not thrown exception." + e.getErrorCode());
        }        
    }
    
    public void testRetrieveFreeFormFacilitiesByUser() {
        
        try {            
            List<String> ffFacility = _dao.retrieveFreeFormFacilitiesByUser(1);
            assertNotNull(ffFacility);            
        } catch (ROIException e) {
            fail("RetrieveFreeFormFacilitiesByUser should not thrown exception." + e.getErrorCode());
        }        
    } 
    
    public void testRetrieveLetterTemplate() {
        
        try { 
            LetterTemplateDocument document = _invDataRetriever.retrieveLetterTemplate(1001);
            assertNotNull(document);
        } catch (Exception e) {
            fail("RetrieveLetterTemplate should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testRetrieveLetterData() {
        
        try { 
            Object obj = _invDataRetriever.retrieveLetterData(1001, 1001);
            assertNotNull(obj);
        } catch (Exception e) {
            fail("RetrieveLetterData should not thrown exception. " + e.getMessage());
        }        
    }

    public void testFormatDate() {
    
        try { 
            String formatedDate = BaseLetterDataRetriever.formatDate(new Date(), ROIConstants.ROI_DATE_FORMAT);            
            assertNotNull(formatedDate);            
        } catch (Exception e) {
            fail("FormatDate should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testBaseLetterDataRetriever() {
        
        try {             
            _invDataRetriever.setRcPatientDAO(new RequestCorePatientDAOImpl());
            _invDataRetriever.setRequestCoreDAO(new RequestCoreDAOImpl());
            _invDataRetriever.setRcDeliveryDAO(new RequestCoreDeliveryDAOImpl());
            _invDataRetriever.setRcChargesDAO(new RequestCoreChargesDAOImpl());
            RequestCoreChargesDAO rccDAO = (RequestCoreChargesDAO) _invDataRetriever
                    .getRequestCoreChargesDAO();
            String formatedCurrency = _invDataRetriever.formatToCurrency(100.00);
            Number amount =  _invDataRetriever.parseCurrency("$100");
            
            assertNotNull(rccDAO);
            assertNotNull(_invDataRetriever.getRequestorStatementDAO());
            assertNotNull(_invDataRetriever.getOverDueInvoiceDAO());
            assertEquals("$100.00", formatedCurrency);
            assertEquals(100, amount.intValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testBaseLetterDataRetrieverWithNull() {
        
        try {             
            Number amount =  _invDataRetriever.parseCurrency(null);
            assertEquals(0.00, amount.doubleValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testBaseLetterDataRetrieverWithInvalidInput() {
        
        try {             
            Number amount =  _invDataRetriever.parseCurrency("#100");
            assertEquals(0.00, amount.doubleValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testToNegative() {
        
        try {             
            Double amount =  _invDataRetriever.toNegative(100.00);
            assertEquals(-100.0, amount.doubleValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testToNegativeWithNull() {
        
        try {             
            Double amount =  _invDataRetriever.toNegative(null);
            assertEquals(0.00, amount.doubleValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }

    public void testToPositive() {
        
        try {             
            Double amount =  _invDataRetriever.toPositive(-100.00);
            assertEquals(100.0, amount.doubleValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }
    
    public void testToPositiveWithNull() {
        
        try {             
            Double amount =  _invDataRetriever.toPositive(null);
            assertEquals(0.00, amount.doubleValue());
        } catch (Exception e) {
            fail("BaseLetterDataRetriever should not thrown exception. " + e.getMessage());
        }        
    }
    
}
