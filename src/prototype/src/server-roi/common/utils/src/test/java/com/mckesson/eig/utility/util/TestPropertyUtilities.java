/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.testing.UnitTest;

/**
  * Test case for the class PropertyUtilities.
 */
public class TestPropertyUtilities extends UnitTest {
    /**
     * Constructs the class.
     * @param arg0
     * Name of the test case.
     */
    public TestPropertyUtilities(String arg0) {
        super(arg0);
    }
    /**
     * Tests the constructors .
     */
    public void testConstructor() {
        assertNotNull(ReflectionUtilities
                .callPrivateConstructor(PropertyUtilities.class));
    }
    /**
     * Tests the constructors are rpivate .
     */
    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(PropertyUtilities.class));
    }
    /**
     * Tests load() with file.
     */
    public void testLoadWithFile() {
      
        assertNotNull(PropertyUtilities.
                load("com/mckesson/eig/utility/io/test.properties"));
        assertNull(PropertyUtilities.
                load("com/mckesson/eig/utility/io/test1.txt"));
    }
    /**
     * Tests load() with InputStream.
     */
    public void testLoadWithInputStream() {
        String fileNameValid = "com/mckesson/eig/utility/io/test.properties";
        File fileValid = FileLoader.getResourceAsFile(fileNameValid);
        FileInputStream fileInputStreamOne;
        try {
            fileInputStreamOne = new  FileInputStream(fileValid);
            assertNotNull(PropertyUtilities.load(fileInputStreamOne));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
           
        } 
               
    }
    

}
