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
package com.mckesson.eig.roi.utils;

import java.io.File;
import java.io.IOException;

import com.mckesson.eig.roi.test.BaseROITestCase;

/**
*
* @author OFS
* @date   June 24, 2013
* @since  HPF 16.0 [ROI]; June 24, 2013
*/
public class TestDirectoryUtil 
extends BaseROITestCase {
    
    public void testGetCacheDirectory() {
        
        String cache = DirectoryUtil.getCacheDirectory();
        assertNotNull(cache);        
    }
    
    public void testGetCacheDirectoryNegative() {
        
        String cache = DirectoryUtil.getCacheDirectory();
        File file = null;
        try {
            file = AccessFileLoader.getFile(cache);
        } catch (IOException e) {
            
        }
        if(file != null) {
           file.delete();
        }
        String newCache = DirectoryUtil.getCacheDirectory();
        assertNotNull(newCache);        
    }
    
    public void testGetTempDirectory() {
        
        String cache = DirectoryUtil.getTempDirectory();
        assertNotNull(cache);        
    }
    
    public void testGetTempDirectoryNegative() {
        
        String cache = DirectoryUtil.getTempDirectory();
        File file = null;
        try {
            file = AccessFileLoader.getFile(cache);
        } catch (IOException e) {
        }
        if(file != null) {
           file.delete();
        }
        String newCache = DirectoryUtil.getTempDirectory();
        assertNotNull(newCache);  
    }

    public void testSpringUtil() {
        Object obj = SpringUtil.getObjectFromSpring("authenticator.roi");
        assertNotNull(obj);
    }
    
    public void testSpringUtilNegative() {
        Object obj = SpringUtil.getObjectFromSpring("invalid bean name");
        assertNull(obj);
    }
    
    public void testCELStopMessageLogger() {
        CELStopMessageLogger  messageLogger = new CELStopMessageLogger();
        messageLogger.shutdown();
    }
    
    public void testCELStartMessageLogger() {
        CELStartMessageLogger  messageLogger = new CELStartMessageLogger();
        messageLogger.init();
        messageLogger.logMessage();
    }
    
    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }
}
