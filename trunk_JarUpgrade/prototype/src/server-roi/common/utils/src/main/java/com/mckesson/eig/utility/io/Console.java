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

import java.io.PrintStream;

import com.mckesson.eig.utility.util.ConversionUtilities;

/**
 * Wraps a PrintStream (generally either System.out or System.err) to make it
 * easy to turn on/off console specific output in one place.
 * <p>
 * One of the primary uses of this class is to have a message sink for when the
 * log may not be available (such as in the logging classes initialization).
 */
public final class Console {

    /**
     * String whose system property is required.
     */
    protected static final String ENABLED_PROPERTYNAME = "eig.console";

    /**
     * <code>PrintStream</code> to use for output.
     */
    private static PrintStream _printSTREAM = System.out;

    /**
     * No argument constructor.
     */
    private Console() {
    }

    /**
     * Returns <code>true</code> if the property for
     * <code>ENABLED_PROPERTYNAME</code> is enabled.
     * 
     * @return <code>true</code> if the property is enabled.<code>false</code>
     *         otherwise.
     */
    public static boolean isEnabled() {
        return ConversionUtilities.toBooleanValue(System
                .getProperty(ENABLED_PROPERTYNAME), true);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     * 
     * @param t
     *            which is to be printed.
     */
    public static synchronized void printStackTrace(Throwable t) {
        if ((isEnabled()) && (t != null)) {
            t.printStackTrace(_printSTREAM);
            _printSTREAM.println();
            _printSTREAM.flush();
        }
    }

    /**
     * Flush the stream if the property is enabled.
     */
    public static synchronized void flush() {
        if (isEnabled()) {
            _printSTREAM.flush();
        }
    }

    /**
     * Print a boolean value.
     * 
     * @param b
     *            <code>boolean</code> to be printed.
     */
    public static synchronized void print(boolean b) {
        if (isEnabled()) {
            _printSTREAM.print(b);
        }
    }

    /**
     * Print a character.
     * 
     * @param c
     *            character to be printed.
     */
    public static synchronized void print(char c) {
        if (isEnabled()) {
            _printSTREAM.print(c);
        }
    }

    /**
     * Print an array of characters.
     * 
     * @param s
     *            array to be printed.
     */
    public static synchronized void print(char[] s) {
        if (isEnabled()) {
            _printSTREAM.print(s);
        }
    }

    /**
     * Print a double-precision floating-point number.
     * 
     * @param d
     *            <code>double</code> to be printed.
     */
    public static synchronized void print(double d) {
        if (isEnabled()) {
            _printSTREAM.print(d);
        }
    }

    /**
     * Print a floating-point number.
     * 
     * @param f
     *            <code>float</code> to be printed.
     */
    public static synchronized void print(float f) {
        if (isEnabled()) {
            _printSTREAM.print(f);
        }
    }

    /**
     * Print an integer.
     * 
     * @param i
     *            integer to be printed.
     */
    public static synchronized void print(int i) {
        if (isEnabled()) {
            _printSTREAM.print(i);
        }
    }

    /**
     * Print a long.
     * 
     * @param l
     *            <code>long</code> to be printed.
     */
    public static synchronized void print(long l) {
        if (isEnabled()) {
            _printSTREAM.print(l);
        }
    }

    /**
     * Print a object.
     * 
     * @param obj
     *            Object to be printed.
     */
    public static synchronized void print(Object obj) {
        if (isEnabled()) {
            _printSTREAM.print(obj);
        }
    }

    /**
     * Print a String.
     * 
     * @param s
     *            String to be printed.
     */
    public static synchronized void print(String s) {
        if (isEnabled()) {
            _printSTREAM.print(s);
        }
    }

    /**
     * Terminate the current line by writing the line separator string.
     */
    public static synchronized void println() {
        if (isEnabled()) {
            _printSTREAM.println();
        }
    }

    /**
     * Print a boolean and then terminate the line.
     * 
     * @param x
     *            <code>boolean</code> to be printed.
     */
    public static synchronized void println(boolean x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>character</code> and then terminate the line.
     * 
     * @param x
     *            <code>character</code> to be printed.
     */
    public static synchronized void println(char x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print an array of characters and then terminate the line.
     * 
     * @param x
     *            <code>character</code> array to be printed
     */
    public static synchronized void println(char[] x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>double</code> and then terminate the line.
     * 
     * @param x
     *            double to be printed.
     */
    public static synchronized void println(double x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>float</code> and then terminate the line.
     * 
     * @param x
     *            float to be printed.
     */
    public static synchronized void println(float x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>Integer</code> and then terminate the line.
     * 
     * @param x
     *            <code>Integer</code> to be printed.
     */
    public static synchronized void println(int x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>long</code> and then terminate the line.
     * 
     * @param x
     *            <code>long</code> to be printed.
     */
    public static synchronized void println(long x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>Object</code> and then terminate the line.
     * 
     * @param x
     *            <code>Object</code> to be printed.
     */
    public static synchronized void println(Object x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Print a <code>String</code> and then terminate the line.
     * 
     * @param x
     *            <code>String</code> to be printed.
     */
    public static synchronized void println(String x) {
        if (isEnabled()) {
            _printSTREAM.println(x);
        }
    }

    /**
     * Write <code>len</code> bytes from the specified byte array starting at
     * offset <code>off</code> to this stream.
     * 
     * @param buf
     *            A byte array
     * @param off
     *            Offset from which to start taking bytes
     * @param len
     *            Number of bytes to write
     */
    public static synchronized void write(byte[] buf, int off, int len) {
        if (isEnabled()) {
            _printSTREAM.write(buf, off, len);
        }
    }

    /**
     * Write the specified byte to this stream. If the byte is a newline and
     * automatic flushing is enabled then the <code>flush</code> method will
     * be invoked.
     * 
     * @param b
     *            byte to be written.
     */
    public static synchronized void write(int b) {
        if (isEnabled()) {
            _printSTREAM.write(b);
        }
    }

    /**
     * Allows you to set the stream of where this Console writes its output.
     * 
     * @param stream
     *            instance of <code>PrintStream</code>.
     */
    public static synchronized void setStream(PrintStream stream) {
        if (stream == null) {
            throw new NullPointerException();
        }
        _printSTREAM = stream;
    }
}
