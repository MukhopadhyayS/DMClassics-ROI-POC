/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.util;

import java.util.List;
import java.util.Vector;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestStringUtilities extends UnitTest {
    private static final int NO_OF_PAD_CHARS = 3;
    private static final int COLUMN_SIZE1 = 4;
    private static final int TRUNC_MAX = 5;
    private static final int ELLIPS_WORD_COUNT1 = 6;
    private static final int SUBSTR_ENDCHAR = 7;
    private static final int COLUMN_SIZE2 = 8;
    private static final int PARSE_CONST1 = 10;
    private static final int PARSE_CONST2 = 30;
    private static final int ELLIPS_WORD_COUNT2 = 100;
    private static final int PARSE_CONST3 = 147;
    private static final int CONST_CODE1 = 69798;
    private static final int CONST_CODE2 = 101574;

    private static final String CAP_TEST = "TEST";
    private static final String CAP_TEST_WITH_SPACES = "   TEST   ";

    private static final String TEST = "test";
    private static final String TEST_WITH_SPACES = "   test   ";

    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";
    private static final String ALL_SPACES = "  ";

    public TestStringUtilities(String name) {
        super(name);
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(StringUtilities.class));
        ReflectionUtilities.callPrivateConstructor(StringUtilities.class);
    }

    public void testEquals() {
        assertNullSafeEquals(TEST, TEST);
        assertNullSafeEquals(CAP_TEST, CAP_TEST);
        assertNullSafeEquals(NULL_STRING, NULL_STRING);

        assertNullSafeNotEquals(TEST, CAP_TEST);
        assertNullSafeNotEquals(TEST_WITH_SPACES, CAP_TEST_WITH_SPACES);

        assertNullSafeNotEquals(TEST, NULL_STRING);
        assertNullSafeNotEquals(TEST, EMPTY_STRING);
        assertNullSafeNotEquals(NULL_STRING, EMPTY_STRING);
        assertNullSafeNotEquals(NULL_STRING, TEST);
        assertNullSafeNotEquals(ALL_SPACES, EMPTY_STRING);
        assertNullSafeNotEquals(TEST, TEST_WITH_SPACES);
        assertNullSafeNotEquals(TEST, CAP_TEST_WITH_SPACES);
        assertNullSafeNotEquals(CAP_TEST, CAP_TEST_WITH_SPACES);
        assertNullSafeNotEquals(CAP_TEST, TEST_WITH_SPACES);

    }

    public void testEqualsIgnoreCase() {
        assertEqualsIgnoreCase(TEST, TEST);
        assertEqualsIgnoreCase(CAP_TEST, CAP_TEST);
        assertEqualsIgnoreCase(TEST, CAP_TEST);
        assertEqualsIgnoreCase(NULL_STRING, NULL_STRING);
        assertEqualsIgnoreCase(TEST_WITH_SPACES, CAP_TEST_WITH_SPACES);
        assertEqualsIgnoreCase(NULL_STRING, EMPTY_STRING);
        assertEqualsIgnoreCase(ALL_SPACES, EMPTY_STRING);

        assertNotEqualsIgnoreCase(TEST, NULL_STRING);
        assertNotEqualsIgnoreCase(TEST, EMPTY_STRING);
        assertNotEqualsIgnoreCase(NULL_STRING, TEST);
        assertNotEqualsIgnoreCase(TEST, TEST_WITH_SPACES);
        assertNotEqualsIgnoreCase(TEST, CAP_TEST_WITH_SPACES);
        assertNotEqualsIgnoreCase(CAP_TEST, CAP_TEST_WITH_SPACES);
        assertNotEqualsIgnoreCase(CAP_TEST, TEST_WITH_SPACES);
    }

    public void testEqualsWithTrim() {
        assertEqualsWithTrim(TEST, TEST);
        assertEqualsWithTrim(CAP_TEST, CAP_TEST);
        assertEqualsWithTrim(NULL_STRING, NULL_STRING);
        assertEqualsWithTrim(ALL_SPACES, EMPTY_STRING);
        assertEqualsWithTrim(TEST, TEST_WITH_SPACES);
        assertEqualsWithTrim(CAP_TEST, CAP_TEST_WITH_SPACES);
        assertEqualsWithTrim(NULL_STRING, EMPTY_STRING);

        assertNotEqualsWithTrim(TEST, CAP_TEST);
        assertNotEqualsWithTrim(TEST, NULL_STRING);
        assertNotEqualsWithTrim(TEST, EMPTY_STRING);
        assertNotEqualsWithTrim(NULL_STRING, TEST);
        assertNotEqualsWithTrim(TEST, CAP_TEST_WITH_SPACES);
        assertNotEqualsWithTrim(CAP_TEST, TEST_WITH_SPACES);
        assertNotEqualsWithTrim(TEST_WITH_SPACES, CAP_TEST_WITH_SPACES);
    }

    public void testEqualsIgnoreCaseWithTrim() {
        assertEqualsIgnoreCaseWithTrim(TEST, TEST);
        assertEqualsIgnoreCaseWithTrim(CAP_TEST, CAP_TEST);
        assertEqualsIgnoreCaseWithTrim(NULL_STRING, NULL_STRING);
        assertEqualsIgnoreCaseWithTrim(ALL_SPACES, EMPTY_STRING);
        assertEqualsIgnoreCaseWithTrim(TEST, TEST_WITH_SPACES);
        assertEqualsIgnoreCaseWithTrim(CAP_TEST, CAP_TEST_WITH_SPACES);
        assertEqualsIgnoreCaseWithTrim(TEST, CAP_TEST);
        assertEqualsIgnoreCaseWithTrim(TEST, CAP_TEST_WITH_SPACES);
        assertEqualsIgnoreCaseWithTrim(CAP_TEST, TEST_WITH_SPACES);
        assertEqualsIgnoreCaseWithTrim(TEST_WITH_SPACES, CAP_TEST_WITH_SPACES);
        assertEqualsIgnoreCaseWithTrim(NULL_STRING, EMPTY_STRING);

        assertNotEqualsIgnoreCaseWithTrim(TEST, NULL_STRING);
        assertNotEqualsIgnoreCaseWithTrim(TEST, EMPTY_STRING);
        assertNotEqualsIgnoreCaseWithTrim(NULL_STRING, TEST);
    }

    public void testCompareIgnoreCase() {
        String s1 = "test";
        String s2 = "case";
        String s3 = "Test";
        // test equal strings
        assertTrue(StringUtilities.compareWithoutCaseAndThenWithCase(s1, s1) == 0);
        // test same word, different capitalization
        assertTrue(StringUtilities.compareWithoutCaseAndThenWithCase(s1, s3) == s1.compareTo(s3));
        assertTrue(StringUtilities.compareWithoutCaseAndThenWithCase(s3, s1) == s3.compareTo(s1));
        // test different words
        assertTrue(StringUtilities.compareWithoutCaseAndThenWithCase(s1, s2) == s1.compareTo(s2));
        assertTrue(StringUtilities.compareWithoutCaseAndThenWithCase(s2, s1) == s2.compareTo(s1));
    }

    public void testIsEmpty() {
        assertTrue(StringUtilities.isEmpty(EMPTY_STRING));
        assertTrue(StringUtilities.isEmpty(NULL_STRING));
        assertTrue(StringUtilities.isEmpty(ALL_SPACES));
        assertFalse(StringUtilities.isEmpty(TEST));
    }

    public void testVerifyHasContent() {
        try {
            StringUtilities.verifyHasContent(EMPTY_STRING);
            fail();
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        }

        try {
            StringUtilities.verifyHasContent(NULL_STRING);
            fail();
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        }

        try {
            StringUtilities.verifyHasContent(ALL_SPACES);
            fail();
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        }

        StringUtilities.verifyHasContent(TEST);
    }

    public void testHasContent() {
        assertFalse(StringUtilities.hasContent(EMPTY_STRING));
        assertFalse(StringUtilities.hasContent(NULL_STRING));
        assertFalse(StringUtilities.hasContent(ALL_SPACES));
        assertTrue(StringUtilities.hasContent(TEST));
    }

    public void testPadCharacters() {
        String test = StringUtilities.padCharacters(TEST, 0, "2", 1);
        assertEquals(test, "2" + TEST);

        String test2 = StringUtilities.padCharacters(test, test.length(), "1",
                2);
        assertEquals(test2, test + "11");

        String test3 = StringUtilities.padCharacters((String) null, 0, "2",
                NO_OF_PAD_CHARS);
        assertEquals(null, test3);

        String test4 = StringUtilities.padCharacters(TEST, 0, "2", 0);
        assertEquals(TEST, test4);
    }

    public void testPadCharactersWithStringBuffer() {
        String test = StringUtilities.padCharacters(new StringBuffer(TEST), 0,
                "2", 1);
        assertEquals(test, "2" + TEST);

        String test2 = StringUtilities.padCharacters(new StringBuffer(test),
                test.length(), "1", 2);
        assertEquals(test2, test + "11");

        String test4 = StringUtilities.padCharacters(new StringBuffer(TEST), 0,
                "2", 0);
        assertEquals(TEST, test4);
    }

    public void testPadLeft() {
        String test = StringUtilities.padLeft(TEST, ' ', COLUMN_SIZE1);

        assertEquals(TEST.length(), test.length());
        assertEquals(TEST, test);

        String test2 = StringUtilities.padLeft(TEST, ' ', COLUMN_SIZE2);

        assertEquals(COLUMN_SIZE2, test2.length());
        assertEquals("    " + TEST, test2);
    }

    public void testPadLeftWithString() {
        String test = StringUtilities.padLeft(TEST, " ", COLUMN_SIZE1);

        assertEquals(TEST.length(), test.length());
        assertEquals(TEST, test);

        String test2 = StringUtilities.padLeft(TEST, " ", COLUMN_SIZE2);

        assertEquals(COLUMN_SIZE2, test2.length());
        assertEquals("    " + TEST, test2);
    }

    public void testPadRight() {
        String test = StringUtilities.padRight(TEST, ' ', COLUMN_SIZE1);

        assertEquals(TEST.length(), test.length());
        assertEquals(TEST, test);

        String test2 = StringUtilities.padRight(TEST, ' ', COLUMN_SIZE2);

        assertEquals(COLUMN_SIZE2, test2.length());
        assertEquals(TEST + "    ", test2);
    }

    public void testGetMaxStringLength() {
        Vector<String> v = new Vector<String>();
        v.addElement(CAP_TEST_WITH_SPACES);
        v.addElement(CAP_TEST);
        v.addElement(EMPTY_STRING);
        v.addElement(ALL_SPACES);
        v.addElement(TEST);

        int length = StringUtilities.getMaxStringLength(v.elements());

        assertEquals(CAP_TEST_WITH_SPACES.length(), length);
    }

    public void testSafe() {
        assertSafe(null, EMPTY_STRING);
        assertSafe(EMPTY_STRING, EMPTY_STRING);
        assertSafe(TEST, TEST);
        assertSafe(CAP_TEST, CAP_TEST);
        assertSafe(CAP_TEST_WITH_SPACES, CAP_TEST_WITH_SPACES);
        assertSafe(TEST_WITH_SPACES, TEST_WITH_SPACES);
    }

    public void testTrim() {
        assertTrim(null, null);
        assertTrim(EMPTY_STRING, EMPTY_STRING);
        assertTrim(TEST, TEST);
        assertTrim(CAP_TEST, CAP_TEST);
        assertTrim(CAP_TEST_WITH_SPACES, CAP_TEST);
        assertTrim(TEST_WITH_SPACES, TEST);
    }

    public void testTruncate() {
        assertNull(truncate((String) null, TRUNC_MAX));

        verifyNotTruncated("", TRUNC_MAX);
        verifyNotTruncated("1234", TRUNC_MAX);
        verifyNotTruncated("12345", TRUNC_MAX);

        assertEquals("12345", truncate("123456", TRUNC_MAX));
    }

    private void verifyNotTruncated(String s, int max) {
        assertEquals(s, truncate(s, max));
        assertSame(s, truncate(s, max));
    }

    private String truncate(String s, int max) {
        return StringUtilities.truncate(s, max);
    }

    public void testSafeTrim() {
        assertSafeTrim(null, "");
        assertSafeTrim(EMPTY_STRING, EMPTY_STRING);
        assertSafeTrim(TEST, TEST);
        assertSafeTrim(CAP_TEST, CAP_TEST);
        assertSafeTrim(CAP_TEST_WITH_SPACES, CAP_TEST);
        assertSafeTrim(TEST_WITH_SPACES, TEST);
    }

    public void testSafeLength() {
        assertSafeLength(null, 0);
        assertSafeLength(EMPTY_STRING, EMPTY_STRING.length());
        assertSafeLength(TEST, TEST.length());
        assertSafeLength(CAP_TEST, CAP_TEST.length());
        assertSafeLength(CAP_TEST_WITH_SPACES, CAP_TEST_WITH_SPACES.length());
        assertSafeLength(TEST_WITH_SPACES, TEST_WITH_SPACES.length());
    }

    public void testCapitalize() {
        assertEquals("Foo", StringUtilities.capitalize("foo"));

        String s = "Foo";
        assertSame(s, StringUtilities.capitalize(s));
        assertEquals(s, StringUtilities.capitalize(s));
    }

    public void testDecapitalize() {
        assertEquals("foo", StringUtilities.decapitalize("Foo"));

        String s = "foo";
        assertSame(s, StringUtilities.decapitalize(s));
        assertEquals(s, StringUtilities.decapitalize(s));
    }

    public void testPlural() {
        assertEquals("Foos", StringUtilities.plural("Foo"));

        String string = "foos";
        assertSame(string, StringUtilities.plural(string));
        assertEquals(string, StringUtilities.plural(string));
    }

    public void testReplace() {
        String source = "Quick Brown Fox is Quicker than a Box who is Quick";
        String expected = "Slow Brown Fox is Slower than a Box who is Slow";
        String expected2 = " Brown Fox is er than a Box who is ";

        assertNull(StringUtilities.replace(null, "Quick", "Slow"));
        assertEquals("", StringUtilities.replace("", "Quick", "Slow"));
        String test = StringUtilities.replace(source, "Quick", "Slow");
        assertEquals(expected, test);

        String test2 = StringUtilities.replace(source, null, "Slow");
        assertEquals(source, test2);

        String test3 = StringUtilities.replace(source, "", "Slow");
        assertEquals(source, test3);

        String test4 = StringUtilities.replace(source, "Quick", null);
        assertEquals(source, test4);

        String test5 = StringUtilities.replace(source, "Quick", "");
        assertEquals(expected2, test5);

        String test6 = StringUtilities.replace(source, TEST, "");
        assertEquals(source, test6);
    }

    public void testReplaceFirst() {
        assertNull(StringUtilities.replaceFirst(null, "Quick", "Slow"));
        assertEquals("", StringUtilities.replaceFirst("", "Quick", "Slow"));

        String source = "one two three four ? ? ?";

        assertEquals(source, StringUtilities.replaceFirst(source, null, "Slow"));
        assertEquals(source, StringUtilities.replaceFirst(source, "", "Slow"));
        assertEquals(source, StringUtilities
                .replaceFirst(source, "Quick", null));

        String test1 = StringUtilities.replaceFirst(source, "one", "oonnee");
        assertEquals("oonnee two three four ? ? ?", test1);

        String test2 = StringUtilities.replaceFirst(source, "none", "oonnee");
        assertEquals(source, test2);

        String test3 = StringUtilities.replaceFirst(source, "?", "five");
        assertEquals("one two three four five ? ?", test3);

        String test4 = StringUtilities.replaceFirst(test3, "?", "six");
        String test5 = StringUtilities.replaceFirst(test4, "?", "seven");
        assertEquals("one two three four five six seven", test5);

    }

    public void testExists() {
        assertTrue(StringUtilities.exists(CAP_TEST, CAP_TEST));
        assertFalse(StringUtilities.exists(CAP_TEST, TEST));
        assertFalse(StringUtilities.exists(TEST, CAP_TEST));

        assertTrue(StringUtilities.existsIgnoreCase(CAP_TEST, CAP_TEST));
        assertTrue(StringUtilities.existsIgnoreCase(CAP_TEST, TEST));
        assertTrue(StringUtilities.existsIgnoreCase(TEST, CAP_TEST));
    }

    public void testSafeIndexOf() {
        assertEquals(-1, StringUtilities.safeIndexOf(null, null));
        assertEquals(-1, StringUtilities.safeIndexOf(null, CAP_TEST));
        assertEquals(-1, StringUtilities
                .safeIndexOf(CAP_TEST_WITH_SPACES, TEST));
        assertEquals(NO_OF_PAD_CHARS, StringUtilities.safeIndexOf(
                CAP_TEST_WITH_SPACES, CAP_TEST));
        assertEquals(0, StringUtilities.safeIndexOf(TEST, TEST));
    }

    public void testExtractString() {
        assertNull(StringUtilities.extractStringStartingWith(null, CAP_TEST));
        assertNull(StringUtilities.extractStringStartingWith(null, null));
        assertNull(StringUtilities.extractStringStartingWith(CAP_TEST, "test"));

        assertEquals("TEST   ", StringUtilities.extractStringStartingWith(
                CAP_TEST_WITH_SPACES, CAP_TEST));
    }

    public void testIsString() {
        assertTrue(StringUtilities.isString(null));
        assertTrue(StringUtilities.isString("Hello"));
        assertFalse(StringUtilities.isString(new Integer("3")));

        Integer i = null;
        assertTrue(StringUtilities.isString(i));
    }

    public void testIsSolidString() {
        assertFalse(StringUtilities.isSolidString(null));
        assertFalse(StringUtilities.isSolidString(new Integer("3")));
        assertFalse(StringUtilities.isSolidString(""));
        assertTrue(StringUtilities.isSolidString("test"));
    }

    public void testIsNumeric() {
        assertTrue(StringUtilities.isNumeric("10"));
        assertFalse(StringUtilities.isNumeric("Hello"));
    }

    public void testTrimLength() {
        assertEquals(COLUMN_SIZE1, StringUtilities.trimLength(CAP_TEST));
        assertEquals(COLUMN_SIZE1, StringUtilities
                .trimLength(CAP_TEST_WITH_SPACES));
        assertEquals(COLUMN_SIZE1, StringUtilities.trimLength(TEST));
        assertEquals(COLUMN_SIZE1, StringUtilities.trimLength(TEST_WITH_SPACES));
        assertEquals(0, StringUtilities.trimLength(NULL_STRING));
        assertEquals(0, StringUtilities.trimLength(null));
    }

    public void testStripFromEnd() {
        String source = "Getty foo";
        String source2 = "Getty foo ";
        String source3 = "Getty ";

        assertEquals("Getty ", StringUtilities.stripFromEnd(source, "foo"));
        assertEquals("Getty ", StringUtilities.stripFromEnd(source2, "foo"));
        assertEquals(null, StringUtilities.stripFromEnd(source3, "Lee"));
    }

    public void testStripFromEndWithNullSource() {
        try {
            StringUtilities.stripFromEnd(null, "foo");
            fail();
        } catch (NullPointerException e) {
//            e.printStackTrace();
        }
    }

    public void testStripFromEndWithNullStripField() {
        try {
            StringUtilities.stripFromEnd("foo", null);
            fail();
        } catch (NullPointerException e) {
//            e.printStackTrace();
        }
    }

    public void assertSafeLength(String start, long expected) {
        assertEquals(expected, StringUtilities.length(start));
    }

    public void assertTrim(String start, String expected) {
        assertEquals(expected, StringUtilities.trim(start));
    }

    public void assertSafeTrim(String start, String expected) {
        assertEquals(expected, StringUtilities.safeTrim(start));
    }

    public void assertSafe(String start, String expected) {
        assertEquals(expected, StringUtilities.safe(start));
    }

    public void assertNullSafeEquals(String arg1, String arg2) {
        assertEquals(arg1, arg2, StringUtilities.equals(arg1, arg2));
    }

    public void assertNullSafeNotEquals(String arg1, String arg2) {
        assertNotEquals(arg1, arg2, StringUtilities.equals(arg1, arg2));
    }

    public void assertEqualsWithTrim(String arg1, String arg2) {
        assertEquals(arg1, arg2, StringUtilities.equalsWithTrim(arg1, arg2));
    }

    public void assertNotEqualsWithTrim(String arg1, String arg2) {
        assertNotEquals(arg1, arg2, StringUtilities.equalsWithTrim(arg1, arg2));
    }

    public void assertEqualsIgnoreCaseWithTrim(String arg1, String arg2) {
        assertEquals(arg1, arg2, StringUtilities.equalsIgnoreCaseWithTrim(arg1,
                arg2));
    }

    public void assertNotEqualsIgnoreCaseWithTrim(String arg1, String arg2) {
        assertNotEquals(arg1, arg2, StringUtilities.equalsIgnoreCaseWithTrim(
                arg1, arg2));
    }

    public void assertEqualsIgnoreCase(String arg1, String arg2) {
        assertEquals(arg1, arg2, StringUtilities.equalsIgnoreCase(arg1, arg2));
    }

    public void assertNotEqualsIgnoreCase(String arg1, String arg2) {
        assertNotEquals(arg1, arg2, StringUtilities
                .equalsIgnoreCase(arg1, arg2));
    }

    public void assertEquals(String arg1, String arg2, boolean result) {
        assertTrue(arg1 + " and " + arg2 + " should be equal", result);
    }

    public void assertNotEquals(String arg1, String arg2, boolean result) {
        assertFalse(arg1 + " and " + arg2 + " should not be equal", result);
    }

    public void testGetEllipsis() {
        assertNull(StringUtilities.getEllipsisWords(null, 1));
        assertEquals("... ", StringUtilities.getEllipsisWords(
                "this is a test of iws", 0));
        assertEquals("this is a test... ", StringUtilities.getEllipsisWords(
                "this is a test of iws", COLUMN_SIZE1));
        assertEquals("this is a test of iws", StringUtilities.getEllipsisWords(
                "this is a test of iws", ELLIPS_WORD_COUNT1));
        assertEquals("this is a test of iws", StringUtilities.getEllipsisWords(
                "this is a test of iws", ELLIPS_WORD_COUNT2));
    }

    public void testEndsWith() {
        assertTrue(StringUtilities.endsWith("whatiswhatiswhatis", "is"));
        assertFalse(StringUtilities.endsWith("whatisss", "is"));
        assertFalse(StringUtilities.endsWith("whatisIS", "is"));
    }

    public void testEndsWithIgnoreCase() {
        assertTrue(StringUtilities.endsWithIgnoreCase("whatiswhatiswhatis",
                "is"));
        assertFalse(StringUtilities.endsWithIgnoreCase("whatisss", "is"));
        assertTrue(StringUtilities.endsWithIgnoreCase("whatisIS", "is"));
    }

    public void testIsModifiedBase36() {
        assertFalse(StringUtilities.isModifiedBase36("1"));
        assertTrue(StringUtilities.isModifiedBase36("a"));
    }

    public void testParseModifiedBase36() {
        assertEquals(1, StringUtilities.parseModifiedBase36("1"));
        assertEquals(PARSE_CONST1, StringUtilities.parseModifiedBase36("a"));
        assertEquals(PARSE_CONST2, StringUtilities.parseModifiedBase36("u"));
        assertEquals(PARSE_CONST3, StringUtilities.parseModifiedBase36("bb"));

        try {
            StringUtilities.parseModifiedBase36("10");
            fail();
        } catch (UtilitiesException e) {
//            e.printStackTrace();
        }
    }

    public void testSubstringWithNull() {
        try {
            StringUtilities.substring(null, 0, TRUNC_MAX);
            fail();
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }
    }

    public void testSubstringInvalid() {
        try {
            StringUtilities.substring("foo", 0, TRUNC_MAX);
            fail();
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }
    }

    public void testSubstring() {
        assertEquals("foo", StringUtilities.substring("foobar", 0,
                NO_OF_PAD_CHARS));
        assertEquals("bar ", StringUtilities.substring("foobar ",
                NO_OF_PAD_CHARS, SUBSTR_ENDCHAR));
    }

    public void testSubstringWithTrim() {
        assertEquals("foo", StringUtilities.substringWithTrim("foobar", 0,
                NO_OF_PAD_CHARS));
        assertEquals("bar", StringUtilities.substringWithTrim("foobar ",
                NO_OF_PAD_CHARS, SUBSTR_ENDCHAR));
    }

    public void testStripDelimiters() {
        assertEquals("1234", StringUtilities.stripDelimiters("1-2-3-4", "-"));
        assertEquals("1-2-3-4", StringUtilities.stripDelimiters("1-2-3-4", ""));
        assertEquals("", StringUtilities.stripDelimiters("", "-"));
    }

    public void testFormatSsn() {
        assertEquals("", StringUtilities.formatSsn("", "xxx-xxx-xxx", "-"));
        assertEquals("", StringUtilities.formatSsn("", "", "-"));
        assertEquals("", StringUtilities.formatSsn("", "xxx-xxx-xxx", ""));
        assertEquals("", StringUtilities.formatSsn("", "", ""));

        assertEquals("12-34", StringUtilities.formatSsn("1234", "xx-xx", "-"));
        assertEquals("1234", StringUtilities.formatSsn("1234", "", "-"));
        assertEquals("1234", StringUtilities.formatSsn("1234", "", ""));
        assertEquals("1234", StringUtilities.formatSsn("1234", "xx-xx", ""));

        assertEquals("123-45-6789", StringUtilities.formatSsn("123456789",
                "xxx-xx-xxxx", "-"));
        assertEquals("123-45-6789", StringUtilities.formatSsn("123-45-6789",
                "xxx-xx-xxxx", "-"));
        assertEquals("123-45-6789", StringUtilities.formatSsn("123456-789",
                "xxx-xx-xxxx", "-"));
        assertEquals("123-45-6789", StringUtilities.formatSsn("123456-789----",
                "xxx-xx-xxxx", "-"));
        assertEquals("123-45-6789", StringUtilities.formatSsn(
                "--1-23-4--56-789-", "xxx-xx-xxxx", "-"));

        assertEquals("123-4", StringUtilities.formatSsn("1234", "xxx-xx-xxxx",
                "-"));
        assertEquals("123-4", StringUtilities.formatSsn("1-2-3-4-",
                "xxx-xx-xxxx", "-"));
        assertEquals("123-4", StringUtilities.formatSsn("12345667", "xxx-x",
                "-"));
        assertEquals("123-4", StringUtilities.formatSsn("1234--",
                "xxx-xx-xxxx", "-"));
        assertEquals("123-45", StringUtilities.formatSsn("123---45--",
                "xxx-xx-xxxx", "-"));
        assertEquals("123", StringUtilities.formatSsn("123-", "xxx-xx-xxxx",
                "-"));
        assertEquals("123", StringUtilities
                .formatSsn("123", "xxx-xx-xxxx", "-"));

        assertEquals("", StringUtilities.formatSsn("", "xxx#xxx#xxx", "#"));
        assertEquals("", StringUtilities.formatSsn("", "", "#"));
        assertEquals("", StringUtilities.formatSsn("", "xxx#xxx#xxx", ""));
        assertEquals("", StringUtilities.formatSsn("", "", ""));

        assertEquals("12#34", StringUtilities.formatSsn("1234", "xx#xx", "#"));
        assertEquals("1234", StringUtilities.formatSsn("1234", "", "#"));
        assertEquals("1234", StringUtilities.formatSsn("1234", "", ""));
        assertEquals("1234", StringUtilities.formatSsn("1234", "xx#xx", ""));

        assertEquals("123#45#6789", StringUtilities.formatSsn("123456789",
                "xxx#xx#xxxx", "#"));
        assertEquals("123#45#6789", StringUtilities.formatSsn("123456#789",
                "xxx#xx#xxxx", "#"));
        assertEquals("123#45#6789", StringUtilities.formatSsn("123456#789####",
                "xxx#xx#xxxx", "#"));
        assertEquals("123#45#6789", StringUtilities.formatSsn(
                "##1#23#4##56#789#", "xxx#xx#xxxx", "#"));

        assertEquals("123#4", StringUtilities.formatSsn("1234", "xxx#xx#xxxx",
                "#"));
        assertEquals("123#4", StringUtilities.formatSsn("1#2#3#4#",
                "xxx#xx#xxxx", "#"));
        assertEquals("123#4", StringUtilities.formatSsn("12345667", "xxx#x",
                "#"));
    }

    public void testCompareTo() {
        assertTrue(StringUtilities.compare("a", "A") > 0);
        assertTrue(StringUtilities.compare("A", "a") < 0);
        assertTrue(StringUtilities.compare("a", "a") == 0);
        assertTrue(StringUtilities.compare("a", "b") < 0);
        assertTrue(StringUtilities.compare("b", "a") > 0);
    }

    public void testCompareToIgnoreCase() {
        assertTrue(StringUtilities.compareIgnoreCase("a", "A") == 0);
        assertTrue(StringUtilities.compareIgnoreCase("A", "a") == 0);
        assertTrue(StringUtilities.compareIgnoreCase("a", "a") == 0);
        assertTrue(StringUtilities.compareIgnoreCase("a", "b") < 0);
        assertTrue(StringUtilities.compareIgnoreCase("b", "a") > 0);
    }

    public void testToString() {
        assertNull(StringUtilities.toString(null));
        assertEquals("3", StringUtilities
                .toString(new Integer(NO_OF_PAD_CHARS)));
    }

    public void testHashCode() {
        assertEquals(0, StringUtilities.hashCode(null));
        assertEquals(0, StringUtilities.hashCode(""));
        assertEquals(CONST_CODE2, StringUtilities.hashCode("foo"));
        assertEquals(CONST_CODE1, StringUtilities.hashCode("FOO"));
    }

    public void testHashCodeIgnoreCase() {
        assertEquals(0, StringUtilities.hashCodeIgnoreCase(null));
        assertEquals(0, StringUtilities.hashCodeIgnoreCase(""));
        assertEquals(CONST_CODE1, StringUtilities.hashCodeIgnoreCase("foo"));
        assertEquals(CONST_CODE1, StringUtilities.hashCodeIgnoreCase("FOO"));
    }

    public void testExtractTokens() {
        String separator = ",";
        String s = ",,test0,test1,test2,,";

        List<String> tokens = StringUtilities.extractTokens("", separator);
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokens(s, "");
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokens("", "");
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokens(",,,,", separator);
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokens("test", separator);
        assertEquals(1, tokens.size());
        assertEquals("test", tokens.get(0));

        tokens = StringUtilities.extractTokens(s, separator);
        assertEquals(NO_OF_PAD_CHARS, tokens.size());
        for (int i = 0; i < NO_OF_PAD_CHARS; i++) {
            assertEquals("test" + i, tokens.get(i));
        }
    }

    public void testExtractTokensWithSeparators() {
        String s = "test0,test1,test2,,";
        String separator = ",";

        List<String> tokens = StringUtilities.extractTokensWithSeparators("", separator);
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokensWithSeparators(s, "");
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokensWithSeparators("", "");
        assertEquals(0, tokens.size());

        tokens = StringUtilities.extractTokens("test", separator);
        assertEquals(1, tokens.size());
        assertEquals("test", tokens.get(0));

        tokens = StringUtilities.extractTokensWithSeparators(s, separator);
        assertEquals(SUBSTR_ENDCHAR, tokens.size());
        assertEquals("test0", tokens.get(0));
        assertEquals(",", tokens.get(1));
        assertEquals("test1", tokens.get(2));
        assertEquals(",", tokens.get(NO_OF_PAD_CHARS));
        assertEquals("test2", tokens.get(COLUMN_SIZE1));
        assertEquals(",", tokens.get(TRUNC_MAX));
        assertEquals(",", tokens.get(ELLIPS_WORD_COUNT1));
    }

    public void testParseString() {
        String[] ret = StringUtilities.split("imnet-ascii", "-");
        assertEquals("imnet", ret[0]);
        assertEquals("ascii", ret[1]);
        ret = StringUtilities.split("imnetascii", "-");
        assertEquals(1, ret.length);
        assertEquals("imnetascii", ret[0]);
    }

    public void testToSafeLowerCaseLettersOrDigits() {
        assertEquals("foobarbaz99", StringUtilities
                .toSafeLowerCaseLettersOrDigits(" Foo - BAR: baz99  "));
        assertEquals("", StringUtilities.toSafeLowerCaseLettersOrDigits(""));
        assertEquals("", StringUtilities.toSafeLowerCaseLettersOrDigits("  "));
        assertEquals("", StringUtilities.toSafeLowerCaseLettersOrDigits(null));
    }

    public void testIndexOfThatTakesACharacter() {
        assertEquals(-1, StringUtilities.indexOf("foo-bar", 'c'));
        assertEquals(-1, StringUtilities.indexOf(null, 'c'));
        assertEquals(NO_OF_PAD_CHARS, StringUtilities.indexOf("foo-bar", '-'));
    }

    public void testContainsIgnoreCaseWithTrim() {
        assertTrue(StringUtilities.containsIgnoreCaseWithTrim(" foo bar baz",
                "        BAR    "));
        assertTrue(StringUtilities.containsIgnoreCaseWithTrim("1234", "3"));

        assertFalse(StringUtilities.containsIgnoreCaseWithTrim("1234", "7"));
        assertFalse(StringUtilities.containsIgnoreCaseWithTrim("foo", "x"));
        assertFalse(StringUtilities.containsIgnoreCaseWithTrim(" fo o ", " "));
        assertFalse(StringUtilities.containsIgnoreCaseWithTrim("", "x"));

        assertFalse(StringUtilities.containsIgnoreCaseWithTrim(null, null));
        assertFalse(StringUtilities.containsIgnoreCaseWithTrim(null, ""));
        assertFalse(StringUtilities.containsIgnoreCaseWithTrim("", null));
        assertFalse(StringUtilities.containsIgnoreCaseWithTrim(" ", "  "));
    }

    public void testStripTrailingWhitespace() {
        assertEquals("foo", StringUtilities.rtrim("foo  "));
        assertEquals(" foo", StringUtilities.rtrim(" foo "));
        assertEquals("", StringUtilities.rtrim("  "));
        assertEquals("\t\r\nfoo", StringUtilities.rtrim("\t\r\nfoo\t\r\n"));
        assertEquals(null, StringUtilities.rtrim(null));

        String foo = "foo";
        assertSame(foo, StringUtilities.rtrim(foo));

        String spacefoo = " foo";
        assertSame(spacefoo, StringUtilities.rtrim(spacefoo));

        String empty = "";
        assertSame(empty, StringUtilities.rtrim(empty));
    }

    public void testIsValidLength() {
        assertEquals(false, StringUtilities.isValidLength(null, THREE));
        assertEquals(false, StringUtilities.isValidLength(new String(), THREE));
        String testString = "ABC";
        assertEquals(true, StringUtilities.isValidLength(testString, THREE));
        testString = "ABCD";
        assertEquals(false, StringUtilities.isValidLength(testString, THREE));
    }

    public void testGetCSV() {

        String input = "Test,\"Value";
        assertEquals(input.length() + THREE, StringUtilities.getCSV(input).length());

        input = "Test\nValue";
        assertEquals(input.length() + THREE, StringUtilities.getCSV(input).length());

        input = "Test,Value";
        assertEquals(input.length() + TWO, StringUtilities.getCSV(input).length());

        assertEquals(ZERO, StringUtilities.getCSV(null).length());
        System.out.println(StringUtilities.getCSV(null));

        input = "Test Value";
        assertEquals(input.length(), StringUtilities.getCSV(input).length());
    }
}
