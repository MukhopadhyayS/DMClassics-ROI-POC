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
package com.mckesson.eig.utility.net;

import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;

/**
 * Test case to test the class MimeConstants.
 */
public class TestMimeConstants extends UnitTest {
    
    /**
     * Constructs the test case.
     * @param name
     * Name of the test case
     */
    public TestMimeConstants(String name) {
        super(name);
    }
   
    /**
     * Tests the constructor is private.
     */
    public void testConstructorIsPrivate() {
        assertTrue(ClassUtilities
                .areAllConstructorsPrivate(MimeConstants.class));
        ReflectionUtilities.callPrivateConstructor(MimeConstants.class);
    }
    
    /**
     * Tests the constant BMP.Valid value is image/bmp.
     */
    public void testBmp() {
         assertEquals("image/bmp", MimeConstants.BMP);
         assertNotSame("image/bm", MimeConstants.BMP);
     }
    
    /**
     * Tests the constant GIF.Valid value is image/gif.
     */
    public void testGif() {
         assertEquals("image/gif", MimeConstants.GIF);
         assertNotSame("", MimeConstants.GIF);
     }
    
    /**
     * Tests the constant IMNET_ASCII.Valid value is application/imnet-ascii.
     */
    public void testImnetAscii() {
         assertEquals("application/imnet-ascii", MimeConstants.IMNET_ASCII);
         assertNotSame("null", MimeConstants.IMNET_ASCII);
     }
    
    /**
     * Tests the constant JPEG.Valid value is image/jpeg.
     */
    public void testJpeg() {
         assertEquals("image/jpeg", MimeConstants.JPEG);
         assertNotSame(null, MimeConstants.JPEG);
     }
    
    /**
     * Tests the constant OCTET_STREAM.Valid value is application/octet-stream.
     */
    public void testOctetStream() {
         assertEquals("application/octet-stream", MimeConstants.OCTET_STREAM);
         assertNotSame("octet-stream", MimeConstants.OCTET_STREAM);
     }
    
    /**
     * Tests the constant PDF.Valid value is application/pdf.
     */
    public void testPdf() {
         assertEquals("application/pdf", MimeConstants.PDF);
         assertNotSame("/pdf", MimeConstants.PDF);
     }
    /**
     * Tests the constant PNG.Valid value is image/png.
     */
    public void testPng() {
         assertEquals("image/png", MimeConstants.PNG);
         assertNotSame("image", MimeConstants.PNG);
     }
    
    /**
     * Tests the constant TEXT_PLAIN.Valid value is text/plain.
     */
    public void testTextPlain() {
         assertEquals("text/plain", MimeConstants.TEXT_PLAIN);
         assertNotSame("text/", MimeConstants.TEXT_PLAIN);
     }
   
    /**
     * Tests the constant TEXT_XML.Valid value is text/xml.
     */
    public void testTextXml() {
         assertEquals("text/xml", MimeConstants.TEXT_XML);
         assertNotSame("texl", MimeConstants.TEXT_XML);
     }
    
    /**
     * Tests the constant TIFF.Valid value is image/tiff.
     */
    public void testTextTiff() {
         assertEquals("image/tiff", MimeConstants.TIFF);
     }
    }
