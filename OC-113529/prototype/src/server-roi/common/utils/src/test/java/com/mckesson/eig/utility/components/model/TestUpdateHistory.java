/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.components.model;

import java.util.Date;

import com.mckesson.eig.utility.util.DateUtilities;

import junit.framework.TestCase;

/**
 * @author Sahul Hameed Y
 * @date   Apr 14, 2008
 * @since  HECM 1.0; Apr 14, 2008
 */
public class TestUpdateHistory extends TestCase {
    
    private UpdateHistory _updateHistory;
    private static final String UPDATED_ON = DateUtilities.formatISO8601(new Date());
    private static final String COMMENTS = "comments";
    private static final String VERSION  = "version";
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_VALUE = "versionValue";
    private static final String PRODUCT_TYPE = "produtType";
    
    /**
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        _updateHistory = new UpdateHistory();
    }
    
    public void testUpdateHistoryConstructor() {
        
        UpdateHistory updateHistory = new UpdateHistory(UPDATED_ON, VERSION, COMMENTS);
        assertEquals(updateHistory.getUpdatedOnAsString(), UPDATED_ON);
        assertEquals(updateHistory.getComments(), COMMENTS);
        assertEquals(updateHistory.getVersion(), VERSION);
    }
    
    public void testUpdateHistory() {
        
        _updateHistory.setUpdatedOnAsString(UPDATED_ON);
        _updateHistory.setComments(COMMENTS);
        _updateHistory.setVersion(VERSION);
        _updateHistory.setVersionName(VERSION_NAME);
        _updateHistory.setVersionValue(VERSION_VALUE);
        _updateHistory.setProductType(PRODUCT_TYPE);
        
        assertEquals(_updateHistory.getUpdatedOnAsString(), UPDATED_ON);
        assertEquals(_updateHistory.getComments(), COMMENTS);
        assertEquals(_updateHistory.getVersion(), VERSION);
        assertEquals(_updateHistory.getVersionName(), VERSION_NAME);
        assertEquals(_updateHistory.getVersionValue(), VERSION_VALUE);
        assertEquals(_updateHistory.getProductType(), PRODUCT_TYPE);
    }
}
