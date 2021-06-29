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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public final class StringUtilities {

    private static final int LENGTH_STRING = 64;

    private static final int PARSE_INT = 36;

    private static final int MATH_NUM = 10;

    private static final String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String FEATURE1 = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURE2 = "http://xml.org/sax/features/external-parameter-entities";
    private static final String FEATURE3 = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    private static interface SafeStringComparator {

        int compare(String x, String y);
    }

    private static final SafeStringComparator COMPARE_IGNORE_CASE =
               new SafeStringComparator() {

        public int compare(String x, String y) {
            return x.compareToIgnoreCase(y);
        }
    };

    private static final SafeStringComparator COMPARE_WITH_TRIM =
                    new SafeStringComparator() {

        public int compare(String x, String y) {
            return x.trim().compareTo(y.trim());
        }
    };

    private static final SafeStringComparator COMPARE_IGNORE_CASE_WITH_TRIM =
                    new SafeStringComparator() {

        public int compare(String x, String y) {
            return x.trim().compareToIgnoreCase(y.trim());
        }
    };

    private static final Map<String, Boolean> BOOLEANTABLE;
    private static final String ELLIPSIS = "... ";

    static {
        Map<String, Boolean> map = new HashMap<String, Boolean>();

        map.put("true", Boolean.TRUE);
        map.put("yes", Boolean.TRUE);
        map.put("on", Boolean.TRUE);
        map.put("enabled", Boolean.TRUE);
        map.put("enable", Boolean.TRUE);
        map.put("t", Boolean.TRUE);
        map.put("y", Boolean.TRUE);
        map.put("1", Boolean.TRUE);

        map.put("false", Boolean.FALSE);
        map.put("no", Boolean.FALSE);
        map.put("off", Boolean.FALSE);
        map.put("disabled", Boolean.FALSE);
        map.put("disable", Boolean.FALSE);
        map.put("f", Boolean.FALSE);
        map.put("n", Boolean.FALSE);
        map.put("0", Boolean.FALSE);

        BOOLEANTABLE = Collections.unmodifiableMap(map);
    }

    public static final String EMPTYSTRING = "";

    private StringUtilities() {
    }

    private static boolean equals(String x, String y, SafeStringComparator c) {

        if (isEmpty(x) & isEmpty(y)) {
            return true;
        }

        if (x == null || y == null) {
            return x == y;
        }
        return c.compare(x, y) == 0;
    }

    public static boolean equals(String x, String y) {
        return ObjectUtilities.equals(x, y);
    }

    public static boolean equalsIgnoreCase(String x, String y) {
        return equals(x, y, COMPARE_IGNORE_CASE);
    }

    public static boolean equalsWithTrim(String x, String y) {
        return equals(x, y, COMPARE_WITH_TRIM);
    }

    public static boolean equalsIgnoreCaseWithTrim(String x, String y) {
        return equals(x, y, COMPARE_IGNORE_CASE_WITH_TRIM);
    }

    public static int compareWithoutCaseAndThenWithCase(String x, String y) {
        int value = compareIgnoreCase(x, y);
        return (value == 0) ? compare(x, y) : value;
    }

    public static boolean isEmpty(String s) {
        return (s == null) || s.trim().length() == 0;
    }

    public static boolean hasContent(String s) {
        return !isEmpty(s);
    }

    public static void verifyHasContent(String s) {
        if (isEmpty(s)) {
            throw new IllegalArgumentException("null or empty string");
        }
    }

    public static String safe(String s) {
        return (s == null) ? "" : s;
    }

    public static String toString(Object x) {
        return (x == null) ? null : x.toString();
    }

    public static String safeToString(Object x) {
        return (x == null) ? "" : x.toString();
    }

    public static String safeTrim(String s) {
        return safe(s).trim();
    }

    public static String trim(String s) {
        return (s == null) ? null : s.trim();
    }

    public static String truncate(String s, int max) {
        if (s == null) {
            return s;
        }
        if (s.length() > max) {
            return s.substring(0, max);
        }
        return s;
    }

    public static boolean endsWith(String source, String subString) {
        return source.length() == source.lastIndexOf(subString)
                + subString.length();
    }

    public static boolean endsWithIgnoreCase(String source, String subString) {
        return endsWith(source.toLowerCase(), subString.toLowerCase());
    }

    public static boolean existsIgnoreCase(String source, String subString) {
        return exists(source.toLowerCase(), subString.toLowerCase());
    }

    public static boolean exists(String source, String subString) {
        return source.indexOf(subString) >= 0;
    }

    /**
     * Ensures a string is a certain size by inserting the specified character
     * repeatedly. If the supplied string's length is greater than <code>length
     * </code>,
     * a clone of the string is returned (no truncation or prepending is
     * performed).
     *
     * @param string
     *            string to pad
     * @param index
     *            where to start padding the characters
     * @param pad
     *            character to pad with
     * @param howMany
     *            how many padded characters to add
     * @return the padded string
     */
    public static String padCharacters(String string, int index, String pad,
            int howMany) {
        if (howMany <= 0 || string == null) {
            return string;
        }

        return padCharacters(new StringBuffer(string), index, pad, howMany);
    }

    /**
     * Ensures a string is a certain size by inserting the specified character
     * repeatedly. If the supplied string's length is greater than <code>length
     * </code>,
     * a clone of the string is returned (no truncation or prepending is
     * performed).
     *
     * @param string
     *            string to pad, cannot be null
     * @param index
     *            where to pad the characters
     * @param pad
     *            character to pad with
     * @param howMany
     *            how many padded characters to add
     * @return the padded string
     */
    public static String padCharacters(StringBuffer string, int index,
            String pad, int howMany) {

        if (index >= string.length()) {
            index = string.length();
        }

        while (howMany-- > 0) {
            string.insert(index, pad);
        }

        return string.toString();
    }

    /**
     * Ensures a string is a certain size by prepending the specified character
     * repeatedly. If the supplied string's length is greater than <code>length
     * </code>,
     * a clone of the string is returned (no truncation or prepending is
     * performed).
     *
     * @param length
     *            desired size of the string
     * @param padding
     *            character to pad with
     * @param value
     *            string to pad
     * @return the padded string
     */
    public static String padLeft(String target, char padding, int columnSize) {
        return padCharacters(target, 0, String.valueOf(padding), columnSize
                - target.length());
    }

    public static String padLeft(String target,
                 String padding, int columnSize) {
        return padCharacters(target, 0, padding, columnSize - target.length());
    }

    /**
     * Ensures a string is a certain size by appending the specified character
     * repeatedly. If the supplied string's length is greater than <code>length
     * </code>,
     * a clone of the string is returned (no truncation or prepending is
     * performed).
     *
     * @param length
     *            desired size of the string
     * @param padding
     *            character to pad with
     * @param value
     *            string to pad
     * @return the padded string
     */
    public static String padRight(String target, char padding, int columnSize) {
        return padCharacters(target, target.length(), String.valueOf(padding),
                columnSize - target.length());
    }

    /**
     * Takes an enumeration of <code>String</code> and returns the length of
     * the largest string</code>
     *
     * @param e
     *            Enumeration of Strings
     * @return int The length of the largest or 0 if there
     * were no strings<code>String</code>
     */
    public static int getMaxStringLength(Enumeration<String> e) {
        int max = 0;

        while (e.hasMoreElements()) {
            String name = e.nextElement();

            if (name.length() > max) {
                max = name.length();
            }
        }

        return max;
    }

    /**
     * Replaces all the occurrences of one string within another with a new
     * string. If the search string is null or empty or the replacement string
     * is null, the ordinal string is returned. If the replacement string is
     * empty, all occurrences of the search string are removed with no
     * replacement.
     *
     * @param source
     *            original string to perform replacements within
     * @param search
     *            string to search for
     * @param replace
     *            the string to replace <code>search</code> with
     * @return the string with all instances of <code>search</code> replaced
     */
    public static String replace(String source, String search, String replace) {

        if (source == null || source.length() == 0 || search == null
                || search.length() == 0 || replace == null) {
            return source;
        }

        StringBuilder sb = new StringBuilder(source.length());

        int lastIndex = 0;
        int newIndex = 0;

        while (newIndex >= 0) {

            newIndex = source.indexOf(search, lastIndex);

            if (newIndex > -1) {
                sb.append(source.substring(lastIndex, newIndex));
                sb.append(replace);

                lastIndex = newIndex + search.length();
            }
        }

        sb.append(source.substring(lastIndex));

        return sb.toString();
    }

    // Considering performance, did not combine this with replace method above.

    public static String replaceFirst(String source, String search,
            String replace) {
        if (source == null || source.length() == 0 || search == null
                || search.length() == 0 || replace == null) {
            return source;
        }

        int index = 0;
        StringBuilder sb = new StringBuilder(source.length());
        index = source.indexOf(search, 0);
        if (index > -1) {
            sb.append(source.substring(0, index));
            sb.append(replace);
            sb.append(source.substring(index + search.length()));
            return sb.toString();
        }
        return source;
    }

    /**
     * Returns the length of the string. If the string is null then it returns
     * 0.
     */

    public static int length(String s) {
        return safe(s).length();
    }

    /**
     * Returns the length of the trimmed string. If the length is null then it
     * returns 0.
     */
    public static int trimLength(String s) {
        return safeTrim(s).length();
    }

    /**
     * Extracts a string starting at a particular pattern. If the string is not
     * found we return null.
     *
     * Given:
     *
     * string - "c:\\classes\\com\\mckesson\\Utilities.class pattern - "\\com\\"
     *
     * The function will return:
     *
     * "\\com\\mckesson\\Utilities.class"
     *
     */

    public static String extractStringStartingWith(
                      String string, String pattern) {
        int index = safeIndexOf(string, pattern);
        if (index == -1) {
            return null;
        }
        return string.substring(index);
    }

    /**
     * Performs an indexOf on the string. If string comes in as null we return
     * -1, which means the pattern was not found.
     *
     * @param string
     *            The string to search in.
     * @param pattern
     *            The pattern to search for.
     *
     * @return -1 if the pattern is not found or if the string passed in is
     *         null. Otherwise it returns the what #String.indexOf(String)
     *         returns.
     *
     * @see String.indexOf(String)
     */

    public static int safeIndexOf(String string, String pattern) {
        return (string == null) ? -1 : string.indexOf(pattern);
    }

    /**
     *
     * @param x
     *            Object to be tested
     * @return <code>true</code> if the specified object is a String or a
     *         <code>null</code> value.
     */
    public static boolean isString(Object x) {
        return (x == null) || String.class.isInstance(x);
    }

    /**
     *
     * @param x
     *            Object to be tested
     * @return <code>true</code> if the specified object is a String and has
     *         content.
     */

    public static boolean isSolidString(Object x) {
        return String.class.isInstance(x) && hasContent((String) x);
    }

    /**
     * Performs an Integer.parseInt on the incoming String. If the parse
     * succeeds we return true, otherwise we return false.
     *
     * @param string
     *            The string to parse.
     *
     * @return boolean The boolean value of whether the string can be parsed.
     *
     * @see Integer.parseInt(String)
     */
    public static boolean isNumeric(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Strips the end of a string of a particular pattern. An exmaple would be
     * stripping a string of an extension.
     *
     * @param string -
     *            Given the string "com\\mckesson\\Utilities.class"
     *
     * @param pattern -
     *            and the pattern ".class"
     *
     * @return String we'll return "com\\mckesson\\Utilities"
     *
     */
    public static String stripFromEnd(String string, String pattern) {
        int index = string.lastIndexOf(pattern);

        if (index == -1) {
            return null;
        }
        return string.substring(0, index);
    }

    public static String decapitalize(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        char[] asChars = s.toCharArray();
        asChars[0] = Character.toLowerCase(asChars[0]);
        return new String(asChars);
    }

    public static String capitalize(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        char[] asChars = s.toCharArray();
        asChars[0] = Character.toUpperCase(asChars[0]);
        return new String(asChars);
    }

    public static String plural(String string) {
        if (string.endsWith("s")) {
            return string;
        }
        return string + 's';
    }

    public static String getEllipsisWords(String string, int wordCount) {
        if (string == null) {
            return null;
        } else if (wordCount <= 0) {
            return ELLIPSIS;
        } else {
            return firstEllipsisWords(string.trim(), wordCount);
        }
    }

    private static String firstEllipsisWords(String string, int wordCount) {
        int count = 0, index = 0;
        do {
            index = string.indexOf(" ", index + 1);
            if (index > 0) {
                count = count + 1;
            }
        } while ((index > 0) && (count < wordCount));

        if (index <= 0) {
            return string;
        }

        return string.substring(0, index) + ELLIPSIS;
    }

    public static int parseModifiedBase36(String str) {
        int num = -1;

        try {
            if (str.length() == 1) {
                num = Integer.parseInt(str, PARSE_INT);
            } else {
                String tempStr = str.substring(0, 1);
                num = Integer.parseInt(tempStr, PARSE_INT);
                if (num < MATH_NUM) {
                    throw new UtilitiesException("Not base 36 number");
                }

                num = (num - MATH_NUM)
                        * (int) Math.pow(PARSE_INT, str.length() - 1);

                tempStr = str.substring(1, str.length());
                num += Integer.parseInt(tempStr, PARSE_INT);

                num += (int) Math.pow(MATH_NUM, str.length());
            }

        } catch (Exception e) {
            throw new UtilitiesException("Not base 36 number");
        }

        return num;
    }

    public static boolean isModifiedBase36(String string) {
        try {
            Integer.parseInt(string);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static String substring(String s, int start, int end) {
        return safe(s).substring(start, end);
    }

    public static String substringWithTrim(String s, int start, int end) {
        return substring(s, start, end).trim();
    }

    public static String stripDelimiters(String value, String delimiter) {
        if (StringUtilities.hasContent(value)
                && StringUtilities.hasContent(delimiter)) {

            if (value.indexOf(delimiter) != -1) {
                StringBuilder buff = new StringBuilder();
                StringTokenizer st = new StringTokenizer(value, delimiter);
                while (st.hasMoreTokens()) {
                    buff.append(st.nextToken());
                }
                return buff.toString();
            }
        }
        return value;
    }

    public static String formatSsn(String value, String ssnFormat,
            String delimiter) {
        if (StringUtilities.hasContent(value)
                && StringUtilities.hasContent(ssnFormat)
                && StringUtilities.hasContent(delimiter)) {

            String valueWithNoDelimiters = StringUtilities.stripDelimiters(
                    value, delimiter);
            StringBuilder formattedSSN = new StringBuilder();
            int returnValueIndex = 0;

            for (int i = 0; i < ssnFormat.length(); i++) {
                if (returnValueIndex < valueWithNoDelimiters.length()) {
                    if (ssnFormat.substring(i, i + 1).equals(delimiter)) {
                        formattedSSN.append(delimiter);
                    } else {
                        formattedSSN.append(valueWithNoDelimiters.substring(
                                returnValueIndex, returnValueIndex + 1));
                        returnValueIndex++;
                    }
                }
            }
            return formattedSSN.toString();
        }

        return value;
    }

    public static int compare(String x, String y) {
        return ObjectUtilities.compare(x, y);
    }

    public static int compareIgnoreCase(String s1, String s2) {
        return safe(s1).compareToIgnoreCase(safe(s2));
    }

    public static int hashCode(String s) {
        return ObjectUtilities.hashCode(s);
    }

    public static int hashCodeIgnoreCase(String s) {
        return (s == null) ? 0 : s.toUpperCase().hashCode();
    }

    public static List<String> extractTokens(String s, String separator,
            boolean withSeparators) {
        List<String> list = new ArrayList<String>();
        if (hasContent(s) && hasContent(separator)) {
            StringTokenizer tokenizer = new StringTokenizer(s, separator,
                    withSeparators);
            while (tokenizer.hasMoreTokens()) {
                list.add(tokenizer.nextToken());
            }
        }
        return list;
    }

    public static List<String> extractTokens(String s, String separators,
            String includeSeparators) {
        List<String> list = new ArrayList<String>();
        if (hasContent(s) && hasContent(separators)) {
            StringTokenizer tokenizer =
                        new StringTokenizer(s, separators, true);
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (separators.indexOf(token) == -1) {
                    list.add(token);
                    continue;
                }
                if (includeSeparators.indexOf(token) > -1) {
                    list.add(token);
                    continue;
                }
            }
        }
        return list;
    }

    public static List<String> extractTokens(String s, String separator) {
        return extractTokens(s, separator, false);
    }

    public static List<String> extractTokensWithSeparators(String s, String separator) {
        return extractTokens(s, separator, true);
    }

    public static String[] split(String value, String delimiter) {
        List<String> list = extractTokens(value, delimiter, false);

        return list.toArray(new String[list.size()]);

    }

    public static String[] splitWithSeparators(String value, String separator, String include) {
        List<String> list = extractTokens(value, separator, include);
        return list.toArray(new String[list.size()]);
    }

    public static String toSafeLowerCaseLettersOrDigits(String s) {
        if (s == null) {
            return "";
        }
        final int length = s.length();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                buffer.append(Character.toLowerCase(c));
            }
        }
        return buffer.toString();
    }

    public static int indexOf(String s, char ch) {
        return (s == null) ? -1 : s.indexOf(ch);
    }

    public static int indexOfIgnoreCaseWithTrim(String source, String token) {
        if (source == null || token == null) {
            return -1;
        }
        String s = source.trim();
        String t = token.trim();
        if (s.length() < 1 || t.length() < 1) {
            return -1;
        }
        return s.toLowerCase().indexOf(t.toLowerCase());
    }

    public static boolean containsIgnoreCaseWithTrim(
                 String source, String token) {
        return indexOfIgnoreCaseWithTrim(source, token) >= 0;
    }

    public static String concatNonBlank(String[] strings, String separator) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        StringBuilder buffer = new StringBuilder(strings.length * LENGTH_STRING);
        for (String string : strings) {
            if (hasContent(string)) {
                buffer.append(string);
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

    public static String stripTrailingWhitespace(final String s) {
        if (s == null) {
            return null;
        }
        int end = s.length();
        // java.lang.String.trim() uses the same "definition" of whitespace
        while ((end > 0) && (s.charAt(end - 1) <= ' ')) {
            end--;
        }
        return (end < s.length()) ? s.substring(0, end) : s;
    }

    public static String rtrim(String s) {
        return stripTrailingWhitespace(s);
    }

    /**
     * Attempts to parse <code>value</code> as a boolean.  Returns
     * <code>defaultValue</code> if the parsing fails.
     * <p>
     * Note that it is safe to pass a null value to this method, in that
     * case you will get back <code>defaultValue</code>.
     */
    public static boolean toBooleanValue(String value, boolean defaultValue) {
        Boolean result = toBoolean(value);
        if (result != null) {
            return result.booleanValue();
        }
        return defaultValue;
    }

    public static Boolean toBoolean(String value) {
        if (value == null) {
            return null;
        }
        String key = value.trim().toLowerCase();
        return BOOLEANTABLE.get(key);
    }

    public static boolean isValidLength(String value, int maxLen) {
        if (isEmpty(value) || (value.length() > maxLen)) {
            return false;
        }
        return true;
    }

    /**
     * This method encloses the passed string within quotes and returns it.
     */
    public static String getCSV(String s) {

        if (s == null) {
            return new String();
        }
        s = s.trim();

        StringBuilder csv = new StringBuilder();
        if (s.indexOf('\"') >= 0) {

            csv.append('\"');
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\"') {
                    csv.append("\"\"");
                } else {
                    csv.append(c);
                }
            }
            csv.append('\"');

        } else if (s.indexOf('\n') >= 0) {

            csv.append('\"');
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\n') {
                    csv.append("\\n");
                } else {
                    csv.append(c);
                }
            }
            csv.append('\"');

        } else if (s.indexOf(",") >= 0) {

            csv.append('\"');
            csv.append(s);
            csv.append('\"');

        } else {

            csv.append(s);
        }
        return csv.toString();
    }

    public static String domToString(Document document)
    throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);
        return sw.toString();
    }

    public static Document stringToDom(String xmlSource)
    throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(FEATURE, true);
        factory.setFeature(FEATURE1, false);
        factory.setFeature(FEATURE2, false);
        factory.setFeature(FEATURE3, false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlSource
            .getBytes("UTF-8")));
    }

	public static String sanitizeHttpHeaderString(Object x) {
		// Null check
		if (x == null) {
			return "";
		} 
		
		// Remove CR and LF to prevent CWE-113: Improper Neutralization of CRLF Sequences in HTTP Headers ('HTTP Response Splitting')
		String dirtyString = x.toString();
		dirtyString = dirtyString.replaceAll("\\n", "");
		dirtyString = dirtyString.replaceAll("\\r", "");
		
		// Place future sanitizatoin code here.
		
		// All sanitization completed
		String sanitizedString = dirtyString;
			
        return sanitizedString;
    }
	
}
