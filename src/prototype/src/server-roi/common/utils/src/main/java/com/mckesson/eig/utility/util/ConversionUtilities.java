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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ConversionUtilities {

    private static final int BYTE_NUM = 256;

    protected static final Integer ZERO = new Integer(0);

    protected static final Integer ONE = new Integer(1);

    protected static final Character UPPER_Y = new Character('Y');

    protected static final Character UPPER_N = new Character('N');

    protected static final Character LOWER_Y = new Character('y');

    protected static final Character LOWER_N = new Character('n');

    protected static final Character UPPER_T = new Character('T');

    protected static final Character UPPER_F = new Character('F');

    protected static final Character LOWER_T = new Character('t');

    protected static final Character LOWER_F = new Character('f');

    protected static final Character CHAR_ZERO = new Character('0');

    protected static final Character CHAR_ONE = new Character('1');

    private static final Map<Character, Boolean> BOOLEANCHARACTERMAP;

    static {
        Map<Character, Boolean> charactersToBooleans = new HashMap<Character, Boolean>();

        charactersToBooleans.put(UPPER_Y, Boolean.TRUE);
        charactersToBooleans.put(UPPER_T, Boolean.TRUE);
        charactersToBooleans.put(LOWER_Y, Boolean.TRUE);
        charactersToBooleans.put(LOWER_T, Boolean.TRUE);
        charactersToBooleans.put(CHAR_ONE, Boolean.TRUE);

        charactersToBooleans.put(UPPER_N, Boolean.FALSE);
        charactersToBooleans.put(UPPER_F, Boolean.FALSE);
        charactersToBooleans.put(LOWER_N, Boolean.FALSE);
        charactersToBooleans.put(LOWER_F, Boolean.FALSE);
        charactersToBooleans.put(CHAR_ZERO, Boolean.FALSE);

        BOOLEANCHARACTERMAP = Collections.unmodifiableMap(charactersToBooleans);
    }

    /**
     *
     */
    private static final Map<String, Boolean> BOOLEANSTRINGMAP;

    static {
        Map<String, Boolean> stringsToBooleans = new HashMap<String, Boolean>();

        stringsToBooleans.put("true", Boolean.TRUE);
        stringsToBooleans.put("yes", Boolean.TRUE);
        stringsToBooleans.put("on", Boolean.TRUE);
        stringsToBooleans.put("enabled", Boolean.TRUE);
        stringsToBooleans.put("enable", Boolean.TRUE);
        stringsToBooleans.put("t", Boolean.TRUE);
        stringsToBooleans.put("y", Boolean.TRUE);
        stringsToBooleans.put("1", Boolean.TRUE);

        stringsToBooleans.put("false", Boolean.FALSE);
        stringsToBooleans.put("no", Boolean.FALSE);
        stringsToBooleans.put("off", Boolean.FALSE);
        stringsToBooleans.put("disabled", Boolean.FALSE);
        stringsToBooleans.put("disable", Boolean.FALSE);
        stringsToBooleans.put("f", Boolean.FALSE);
        stringsToBooleans.put("n", Boolean.FALSE);
        stringsToBooleans.put("0", Boolean.FALSE);

        BOOLEANSTRINGMAP = Collections.unmodifiableMap(stringsToBooleans);
    }

    private ConversionUtilities() {
    }

    public static Boolean toBoolean(char c) {
        return toBoolean(new Character(c));
    }

    /**
     * Converts a <code>char</code> to a java.util.Boolean. If the
     * <code>char</code> cannot be converted we return the default value.
     *
     * @param c
     *            character to convert
     * @param defaultValue
     *            value to return if c cannot be converted
     * @return Boolean
     */
    public static Boolean toBoolean(char c, Boolean defaultValue) {
        return toBoolean(new Character(c), defaultValue);
    }

    public static Boolean toBoolean(Character c) {
        return (c == null) ? null : lookupBoolean(c);
    }

    private static Boolean lookupBoolean(Character c) {
        return BOOLEANCHARACTERMAP.get(c);
    }

    public static Boolean toBoolean(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Converts a java.lang.Character to a java.lang.Boolean
     *
     * @param c
     *            Character to convert
     * @param defaultValue
     *            Value to return if c cannot be converted
     * @return Boolean
     */
    public static Boolean toBoolean(Character c, Boolean defaultValue) {
        Boolean result = toBoolean(c);
        return (result == null) ? defaultValue : result;
    }

    /**
     * Converts a java.lang.Character to a <code>boolean</code>. If the value
     * is null or cannot be converted then we return the defaultValue.
     *
     * @param c
     *            The character to convert to a boolean
     * @param defaultValue
     *            Value to return on error or null
     * @return boolean
     */
    public static boolean toBooleanValue(Character c, boolean defaultValue) {
        Boolean result = toBoolean(c);
        return (result == null) ? defaultValue : result.booleanValue();
    }

    public static boolean toBooleanValue(Object object, boolean defaultValue) {
        // This is a mess. Perhaps register some form of Converter instances
        // in a hash map with source -> destination keys. I think BeanUtils
        // does something like this.
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        } else if (object instanceof String) {
            return toBooleanValue((String) object, defaultValue);
        } else if (object instanceof Character) {
            return toBooleanValue((Character) object, defaultValue);
        } else if (object instanceof Number) {
            return toBooleanValue((Number) object, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * Converts a java.lang.Character to a <code>boolean</code> value. If the
     * value is null or cannot be converted then we return <code>false</code>
     *
     * @param c
     *            The character to convert to a boolean
     * @return boolean
     */
    public static boolean toBooleanValue(Character c) {
        return toBooleanValue(c, false);
    }

    /**
     * Converts a java.lang.Boolean to a <code>boolean</code> value. If the
     * value is null or cannot be converted then we return <code>false</code>
     *
     * @param b
     *            The Boolean to convert to a boolean
     * @return boolean
     */
    public static boolean toBooleanValue(Boolean b) {
        return toBooleanValue(b, false);
    }

    /**
     * Converts a java.lang.Boolean to a <code>boolean</code> value. If the
     * value is null or cannot be converted then we return defaultValue.
     *
     * @param b
     *            The Boolean to convert to a boolean
     * @param defaultValue
     *            Value to return on error or null
     * @return boolean
     */
    public static boolean toBooleanValue(Boolean b, boolean defaultValue) {
        return (b == null) ? defaultValue : b.booleanValue();
    }

    /**
     * Converts a <code>long</code> to a <code>boolean</code>.
     *
     * @param l
     *            The long to convert
     * @return false if l==0, else true.
     */
    public static boolean toBooleanValue(long l) {
        return (l == 0) ? false : true;
    }

    /**
     * Converts a java.lang.Number to a <code>boolean</code>. If number is
     * null, we return defaultValue, else we .
     *
     * @param Number
     *            to convert
     * @return defaultValue if i == null, false only if Number is equivalent to
     *         0.
     */
    public static boolean toBooleanValue(Number i, boolean defaultValue) {
        return (i == null) ? defaultValue : toBooleanValue(i.longValue());
    }

    /**
     * Same as calling toBooleanValue(i, false);
     */
    public static boolean toBooleanValue(Number i) {
        return toBooleanValue(i, false);
    }

    /**
     * Converts a <code>long</code> to a java.lang.Boolean.
     *
     * @param l
     *            The long to convert
     * @return Boolean.FALSE if l==0, else Boolean.TRUE.
     */
    public static Boolean toBoolean(long l) {
        return (l == 0) ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * Converts a java.lang.Number to a java.lang.Boolean
     *
     * @param n
     *            the number to convert
     * @param theDefault
     *            the value to return on null
     * @return Boolean.FALSE if i.longValue == 0, else Boolean.TRUE
     */
    public static Boolean toBoolean(Number n, Boolean theDefault) {
        return (n == null) ? theDefault : toBoolean(n.longValue());
    }

    public static Boolean toBoolean(Number n) {
        return toBoolean(n, null);
    }

    /**
     * Converts a <code>boolean</code> to a java.lang.Integer returns 1 for
     * true and 0 for false
     */

    public static Integer toInteger(boolean b) {
        return b ? ONE : ZERO;
    }

    /**
     * Same as calling toInteger(b, null);
     *
     * @param b
     * @return Integer
     */
    public static Integer toInteger(Boolean b) {
        return toInteger(b, null);
    }

    /**
     * Converts a boolean to an integer
     *
     * @param b
     *            Boolean to convert
     * @param defaultValue
     *            value to return if boolean is null
     * @return 1 for true and 0 for false
     */
    public static Integer toInteger(Boolean b, Integer defaultValue) {
        return (b == null) ? defaultValue : toInteger(b.booleanValue());
    }

    /**
     * Same as calling toInteger(s, null);
     *
     * @param s
     *            String to convert
     * @return Integer.valueOf(s)
     */
    public static Integer toInteger(String s) {
        return toInteger(s, null);
    }

    /**
     * Converts a java.lang.String into an java.lang.Integer. Returns
     * defaultValue if n is null or unparseable
     *
     * @param s
     *            String to convert
     * @param defaultValue
     *            if n is null, this value gets returned
     * @return Integer.valueOf(s)
     */
    public static Integer toInteger(String s, Integer defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toIntValue(String s, int defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toIntValue(String s) {
        return toIntValue(s, 0);
    }

    /**
     * Same as calling toInt(n, 0)
     *
     * @param i
     *            The number to convert
     * @return see toInt(Number n, int defaultValue)
     * @see ConversionUtilities.toInt(java.lang.Number, int);
     */
    public static int toInt(Number n) {
        return toInt(n, 0);
    }

    /**
     * Converts a java.lang.Number into an int. Returns defaultValue if n is
     * null
     *
     * @param n
     *            Number to convert
     * @param defaultValue
     *            if n is null, this value gets returned
     * @return n.intValue()
     */
    public static int toInt(Number n, int defaultValue) {
        return (n == null) ? defaultValue : n.intValue();
    }

    public static int toInt(Boolean b, int defaultValue) {
        return (b == null) ? defaultValue : (b.booleanValue()) ? 1 : 0;
    }

    public static int toInt(Boolean b) {
        return toInt(b, 0);
    }

    /**
     * Same as calling toShort(n, 0)
     *
     * @param n
     *            Number to convert
     * @see toShort(Number n, short defaultValue)
     *
     */
    public static short toShort(Number n) {
        return toShort(n, (short) 0);
    }

    /**
     * Converts a java.lang.Number into a short. Returns defaultValue if n is
     * null
     *
     * @param n
     *            Number to convert
     * @param defaultValue
     *            if n is null, this value gets returned
     * @return n.shortValue()
     */
    public static short toShort(Number n, short defaultValue) {
        return (n == null) ? defaultValue : n.shortValue();
    }

    /**
     * Converts a java.lang.Character into a char. Returns defaultValue if n is
     * null
     *
     * @param c
     *            Character to convert
     * @param defaultValue
     *            if n is null, this value gets returned
     * @return c.charValue()
     */
    public static char toChar(Character c, char defaultValue) {
        return (c == null) ? defaultValue : c.charValue();
    }

    public static char toCharUpperYN(boolean value) {
        return toCharacterUpperYN(value).charValue();
    }

    public static Character toCharacterUpperTF(Boolean b) {
        return (b == null) ? null : toCharacterUpperTF(b.booleanValue());
    }

    public static Character toCharacterLowerTF(Boolean b) {
        return (b == null) ? null : toCharacterLowerTF(b.booleanValue());
    }

    public static Character toCharacterUpperYN(Boolean b) {
        return (b == null) ? null : toCharacterUpperYN(b.booleanValue());
    }

    public static Character toCharacterLowerYN(Boolean b) {
        return (b == null) ? null : toCharacterLowerYN(b.booleanValue());
    }

    public static Character toCharacterUpperTF(boolean b) {
        return b ? UPPER_T : UPPER_F;
    }

    public static Character toCharacterLowerTF(boolean b) {
        return b ? LOWER_T : LOWER_F;
    }

    public static Character toCharacterUpperYN(boolean b) {
        return b ? UPPER_Y : UPPER_N;
    }

    public static Character toCharacterLowerYN(boolean b) {
        return b ? LOWER_Y : LOWER_N;
    }

    /**
     * Same as calling toLong(s, null)
     *
     * @return Long
     */
    public static Long toLong(String s) {
        return toLong(s, null);
    }

    /**
     * Converts a java.lang.String to a java.lang.Long
     *
     * @param s
     *            string to convert
     * @param defaultValue
     *            value to return on error
     * @return Long
     */
    public static Long toLong(String s, Long defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Long.valueOf(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long toLongValue(String s, long defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Double toDouble(String s) {
        return toDouble(s, null);
    }

    public static Double toDouble(String s, Double defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Double.valueOf(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double toDoubleValue(String s, double defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Float toFloat(String s) {
        return toFloat(s, null);
    }

    public static Float toFloat(String s, Float defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Float.valueOf(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float toFloatValue(String s, float defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(s.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toPositiveInt(byte b) {
        return b < 0 ? b + BYTE_NUM : b;
    }

    public static String toHexByte(byte b) {
        return Integer.toHexString(toPositiveInt(b));
    }

    public static String toZeroPaddedHexByte(byte b) {
        String hex = toHexByte(b);
        return hex.length() == 1 ? '0' + hex : hex;
    }

    public static String toYesNoFlag(boolean b) {
        return b ? "Y" : "N";
    }

    public static String toYesNoFlag(Boolean b) {
        if (b == null) {
            return null;
        }
        return toYesNoFlag(b.booleanValue());
    }

    public static String toString(boolean value) {
        return value ? "true" : "false";
    }

    /**
     * Attempts to parse <code>value</code> as a boolean. Returns
     * <code>defaultValue</code> if the parsing fails.
     * <p>
     * Note that it is safe to pass a null value to this method, in that case
     * you will get back <code>defaultValue</code>.
     */
    public static boolean toBooleanValue(String value, boolean defaultValue) {
        Boolean result = toBoolean(value);
        return (result == null) ? defaultValue : result.booleanValue();
    }

    public static Boolean toBoolean(String value, Boolean defaultValue) {
        Boolean result = toBoolean(value);
        return (value == null) ? defaultValue : result;
    }

    public static Boolean toBoolean(String value) {
        return (value == null) ? null : lookupBoolean(value.trim()
                .toLowerCase());
    }

    private static Boolean lookupBoolean(String key) {
        return BOOLEANSTRINGMAP.get(key);
    }
}
