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

package com.mckesson.eig.audit.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

import com.mckesson.eig.audit.UnitTestSpringInitialization;
import com.mckesson.eig.utility.components.ComponentUtil;
import com.mckesson.eig.utility.components.model.ComponentInfo;

/**
 * @author Sahul Hameed Y
 * @date   Apr 8, 2008
 * @since  HECM 1.0; Apr 8, 2008
 */
public class TestComponentServiceImpl extends TestCase {

    
    private static ComponentServiceImpl _componentServiceImpl = null;
    public static final String FILE_PATH = 
        "etc/com/mckesson/eig/components/audit.component-info-base.xml";
    
    public static final String TEMP_FILE_PATH = 
        "src/test/com/mckesson/eig/components/";
    
    protected void setUp() throws Exception {
        
        System.setProperty("CONFIG_HOME", "src\\test");
        super.setUp();
        UnitTestSpringInitialization.init();
        copyFile();
        _componentServiceImpl = new ComponentServiceImpl();
    }
    
    public void testGetComponentDetails() 
    throws JAXBException {
        
        String componentDetails   = _componentServiceImpl.getComponentDetails();       
        ComponentInfo componentInfo =  
            ComponentUtil.unMarshallObject(new StringReader(componentDetails));

        assertEquals("System", componentInfo.getApplicationInfo().getComponentName());
        assertEquals("Audit", componentInfo.getApplicationInfo().getProductName());
    }
    
    /**
     * test method to get the component details with exception
     */
    public void testGetComponentDetailsWithException() {
        
        new File(TEMP_FILE_PATH + "audit.component-info-base.xml").delete();
        assertTrue(new File("src/test/com/mckesson/eig/components").delete());
        new ComponentServiceImpl().getComponentDetails();
    }
    
    /**
     * copy the file from etc to test folder
     * @throws Exception
     */
    private void copyFile() 
    throws Exception {
        
        File file = new File(TEMP_FILE_PATH);
        file.mkdir();
        
        FileReader inputFile  = new FileReader(new File(FILE_PATH));
        FileWriter outputFile = 
            new FileWriter(new File(TEMP_FILE_PATH + "audit.component-info-base.xml"));
        int c;

        while ((c = inputFile.read()) != -1) {
          outputFile.write(c);
        }
        
        inputFile.close();
        outputFile.close();
    }
}
