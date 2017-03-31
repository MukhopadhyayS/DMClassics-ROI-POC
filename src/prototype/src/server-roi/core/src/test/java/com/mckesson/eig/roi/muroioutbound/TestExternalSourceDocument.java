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
package com.mckesson.eig.roi.muroioutbound;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.RetrieveParameter;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.utils.AccessFileLoader;

/**
*
* @author OFS
* @date   June 24, 2013
* @since  HPF 16.0 [ROI]; June 24, 2013
*/
public class TestExternalSourceDocument 
extends TestCase {
    
    private static ExternalSourceDocument extSourceDoc = new ExternalSourceDocument();;
    private static final String attId = "1";
    
    private static final List<CcdDocument> ccdDocuments = new ArrayList<CcdDocument>();
    private static final String encounter = "Encounter";
    private static final String extFacility = "AD";
    private static final String facility = "A";
    private static final String fulfillDate = String.valueOf(new Date());
    private static final int id = 100;
    private static final String MRN = "FralickM01";
    private static final String outbound = "Outbound";
    private static final String outboundType = "outbound type";
    private static final String receivedDate = String.valueOf(new Date());
    private static final String referenceId = "1001";
    private static final int reqId = 001;
    private static final String reqStatus = "Logged";
    private static final String reqType = "Billable";
    private static final String retry = "retry";
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        extSourceDoc.setAttId(attId);
        OutputStream outputStream = AccessFileLoader.getFileOutputStream(AccessFileLoader.getFile("file"), false);
        CcdDocument ccdDoc = new CcdDocument();
        ccdDoc.setFileName("file");
        ccdDoc.setOutputStream(outputStream);
        ccdDocuments.add(ccdDoc);
        extSourceDoc.setCcdDocuments(ccdDocuments);
        extSourceDoc.setEncounter(encounter);
        extSourceDoc.setExtFacility(extFacility);
        extSourceDoc.setFacility(facility);
        extSourceDoc.setFulfillDate(fulfillDate);
        extSourceDoc.setId(id);
        extSourceDoc.setMrn(MRN);
        extSourceDoc.setOutbound(outbound);
        extSourceDoc.setOutbound(true);
        extSourceDoc.setOutputType(outboundType);
        extSourceDoc.setReceivedDate(receivedDate);
        extSourceDoc.setReferenceId(referenceId);
        extSourceDoc.setReqID(reqId);
        extSourceDoc.setReqStatus(reqStatus);
        extSourceDoc.setReqType(reqType);
        extSourceDoc.setRetry(retry);
        extSourceDoc.setRetry(true);
        extSourceDoc.addCcdDocument(ccdDoc);
    
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testExternalSourceDocumentModel() {
        
        assertEquals(attId, extSourceDoc.getAttId());
        assertEquals(ccdDocuments, extSourceDoc.getCcdDocuments());
        assertEquals(encounter, extSourceDoc.getEncounter());
        assertEquals(extFacility, extSourceDoc.getExtFacility());
        assertEquals(facility, extSourceDoc.getFacility());
        assertEquals(fulfillDate, extSourceDoc.getFulfillDate());
        assertEquals(id, extSourceDoc.getId());
        assertEquals(MRN, extSourceDoc.getMrn());
        assertEquals("Y", extSourceDoc.getOutbound());
        assertTrue(extSourceDoc.isOutbounded());
        assertEquals(outboundType,  extSourceDoc.getOutputType());
        assertEquals(receivedDate, extSourceDoc.getReceivedDate());
        assertEquals(referenceId, extSourceDoc.getReferenceId());
        assertEquals(reqId, extSourceDoc.getReqID());
        assertEquals(reqStatus, extSourceDoc.getReqStatus());
        assertEquals(reqType, extSourceDoc.getReqType());
        assertEquals("Y", extSourceDoc.getRetry()); 
        extSourceDoc.setAttId(null);
        assertNotNull(extSourceDoc.getAttId());
        assertNotNull(extSourceDoc.getAttIds());
        assertNotNull(extSourceDoc.copy(extSourceDoc));
        assertNotNull(extSourceDoc.getOutputStream());
    }
    
    public void testExternalSourceDocumentModelConstruct() {
        
        Map<RetrieveParameter, String> data = new HashMap<RetrieveParameter, String>();
        data.put(RetrieveParameter.MRN, MRN);
        data.put(RetrieveParameter.ENCOUNTER, encounter);
        data.put(RetrieveParameter.FACILITY, facility);
        data.put(RetrieveParameter.REQID, String.valueOf(reqId));
        data.put(RetrieveParameter.REQTYPE, reqType);
        data.put(RetrieveParameter.RECEIPTDATE, receivedDate);
        data.put(RetrieveParameter.ASSIGNING_AUTHORITY, "Assigning_Authority");
        ExternalSourceDocument extDoc = new ExternalSourceDocument(data);
        
        assertEquals(MRN, extDoc.getMrn());
        assertEquals(receivedDate, extDoc.getReceivedDate());
        assertEquals(reqId, extDoc.getReqID());
        assertEquals(reqType, extDoc.getReqType());
        assertEquals(facility, extDoc.getFacility());
        assertEquals(encounter, extDoc.getEncounter());
    }

}
