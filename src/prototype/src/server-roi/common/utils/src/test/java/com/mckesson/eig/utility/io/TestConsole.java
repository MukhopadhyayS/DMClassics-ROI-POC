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
package com.mckesson.eig.utility.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import junit.framework.Test;
import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;

public class TestConsole extends UnitTest {

    private static final String ENABLED_PROPERTYNAME = Console.ENABLED_PROPERTYNAME;
    private static final int TEST_ASSERT = 24;
    private static final double TEST_DOUBLE = 9.99;
    private static final float TEST_FLOAT = 9.9e1f;
    private static final int TEST_INT = 9;
    private static final long TEST_LONG = 9;
    private static final int TEST_VALUE = 3;
    private PrintStream _testStream;
    private PrintStream _expectedStream;
    private ByteArrayOutputStream _testBytes;
    private ByteArrayOutputStream _expectedBytes;

    public TestConsole(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestConsole.class, Console.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty(ENABLED_PROPERTYNAME, "true");
        _expectedBytes = new ByteArrayOutputStream();
        _testBytes = new ByteArrayOutputStream();
        _expectedStream = new PrintStream(_expectedBytes, true);
        _testStream = new PrintStream(_testBytes, true);
        Console.setStream(_testStream);
    }

    protected void tearDown() throws Exception {
        _expectedStream.close();
        _testStream.close();
        super.tearDown();
    }

    public void testConstructor() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(Console.class));
        assertNotNull(ReflectionUtilities.callPrivateConstructor(Console.class));
    }

    public void testPrintStackTrace() {
        _expectedStream.println("java.lang.Throwable: HELP");
        Console.printStackTrace(new Throwable("HELP"));
        assertEquals(_expectedBytes.toString().substring(0, TEST_ASSERT), _testBytes
                .toString().substring(0, TEST_ASSERT));

        _testBytes.reset();

        Console.printStackTrace(null);
        assertTrue(_testBytes.size() == 0);

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.printStackTrace(new Throwable("HELP"));
        assertTrue(_testBytes.size() == 0);
    }

    public void testFlush() {
        _expectedStream.println("test flush");
        Console.println("test flush");
        Console.flush();
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println("test flush");
        Console.flush();
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintBoolean() {
        _expectedStream.print(true);
        Console.print(true);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(true);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintChar() {
        _expectedStream.print('C');
        Console.print('C');
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print('C');
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintCharArray() {
        char[] chArray = new char[]{'a', 'b', 'c'};
        _expectedStream.print(chArray);
        Console.print(chArray);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(chArray);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintDouble() {

        _expectedStream.print(TEST_DOUBLE);
        Console.print(TEST_DOUBLE);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(TEST_DOUBLE);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintFloat() {

        _expectedStream.print(TEST_FLOAT);
        Console.print(TEST_FLOAT);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(TEST_FLOAT);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintInt() {

        _expectedStream.print(TEST_INT);
        Console.print(TEST_INT);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(TEST_INT);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintLong() {

        _expectedStream.print(TEST_LONG);
        Console.print(TEST_LONG);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(TEST_LONG);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintObject() {
        Object testObject = "Goodbye";
        _expectedStream.print(testObject);
        Console.print(testObject);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(testObject);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintString() {
        String testString = "Goodbye";
        _expectedStream.print(testString);
        Console.print(testString);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.print(testString);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintLine() {
        _expectedStream.print(System.getProperty("line.separator"));
        Console.println();
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println();
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnBoolean() {
        System.setProperty(ENABLED_PROPERTYNAME, "true");
        _expectedStream.println(true);
        Console.println(true);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(true);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnChar() {
        _expectedStream.println('C');
        Console.println('C');
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println('C');
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnCharArray() {
        char[] chArray = new char[]{'a', 'b', 'c'};
        _expectedStream.println(chArray);
        Console.println(chArray);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(chArray);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnDouble() {

        _expectedStream.println(TEST_DOUBLE);
        Console.println(TEST_DOUBLE);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(TEST_DOUBLE);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnFloat() {
        _expectedStream.println(TEST_FLOAT);
        Console.println(TEST_FLOAT);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(TEST_FLOAT);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnInt() {

        _expectedStream.println(TEST_INT);
        Console.println(TEST_INT);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(TEST_INT);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnLong() {

        _expectedStream.println(TEST_LONG);
        Console.println(TEST_LONG);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(TEST_LONG);
        assertTrue(_testBytes.size() == 0);
    }

    public void testPrintlnObject() {
        Object testObject = "Goodbye";
        _expectedStream.println(testObject);
        Console.println(testObject);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.println(testObject);
        assertTrue(_testBytes.size() == 0);
    }

    public void testWriteBuffer() {
        byte[] buf = new byte[]{1, 2, TEST_VALUE};
        _expectedStream.write(buf, 0, TEST_VALUE);
        Console.write(buf, 0, TEST_VALUE);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.write(buf, 0, TEST_VALUE);
        assertTrue(_testBytes.size() == 0);
    }

    public void testWriteInt() {
        _expectedStream.write(TEST_VALUE);
        Console.write(TEST_VALUE);
        assertEquals(_expectedBytes.toString(), _testBytes.toString());

        _testBytes.reset();

        System.setProperty(ENABLED_PROPERTYNAME, "false");
        Console.write(TEST_VALUE);
        assertTrue(_testBytes.size() == 0);
    }

    public void testNullStream() {
        try {
            Console.setStream(null);
            fail("NullPointerException  thrown from TestConsole:testNullStream");
        } catch (NullPointerException e) {
            return;
        }
    }
}
