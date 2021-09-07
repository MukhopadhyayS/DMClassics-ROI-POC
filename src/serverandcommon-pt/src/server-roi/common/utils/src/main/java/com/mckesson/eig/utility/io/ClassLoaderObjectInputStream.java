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

package com.mckesson.eig.utility.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Note: The class is used to resolve class loading problem in HFN 10.0 which
 * uses JBoss 3.2.3, during de-serialization JBoss uses Tomcat's class loader
 * and can not find our classes, e.g. DocumentSpecification. This class shoud be
 * obsolete with higher version of JBoss.

 * Provides methods which creates an ObjectInputStream that reads from the
 * specified InputStream.It also provides methods for Loading the local class
 * equivalent of the specified stream class description.
 *
 */
public class ClassLoaderObjectInputStream extends ObjectInputStream {

    /**
     * Creates an ObjectInputStream that reads from the specified InputStream. A
     * serialization stream header is read from the stream and verified. This
     * constructor will block until the corresponding ObjectOutputStream has
     * written and flushed the header.
     *
     * @param in
     *            input stream to read from.
     * @throws IOException
     *             any of the usual input/output exceptions.
     */

    public ClassLoaderObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    /**
     * Provide a way for reimplementing ObjectInputStream.
     *
     * @throws IOException
     *             any of the usual input/output exceptions.
     */
    public ClassLoaderObjectInputStream() throws IOException {
    }

    /**
     * Loads the local class equivalent of the specified stream class
     * description.This method allows classes to be fetched from an alternate
     * source.
     *
     * @param desc
     *            an instance of class <code>ObjectStreamClass</code>
     * @return a <code>Class</code> object corresponding to <code>desc</code>
     * @throws IOException
     *             any of the usual input/output exceptions.
     * @throws ClassNotFoundException
     *             if class of a serialized object cannot be found.
     */
    @Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
            ClassNotFoundException {
        String name = desc.getName();
        try {
            return Class.forName(name, false, getClass().getClassLoader());
        } catch (Exception e) {
            return super.resolveClass(desc);
        }
    }
}
