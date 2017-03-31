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
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * Provides method for getting an equivalent <code>URI</code>.
 * 
 */
public class JDK15PlusURLToFileConverter implements URLToFileConverter {
    static {
        // Force the possible NoSuchMethodError as soon as this class loads.
        try {
            convertToFile(new URL("file:/"));
        } catch (MalformedURLException e) {
            System.err.println("Exception occured in JDK15PlusURLToFileConverter" 
                    + " static block:" + e.getMessage());
        }
    }
    /**
     * constructor for returning an equivalent <code>URL</code>
     * for this url.
     */
    public JDK15PlusURLToFileConverter() {
        super();
    }
    /**
     * Returns a <code> java.net.URI</code> equivalent to this URL.
     * 
     * @param url
     *            source.
     * @return <code>URI</code> equivalent to this <code>URL</code>.
     */
    public File toFile(URL url) {
        return convertToFile(url);
    }
    /** 
     * Checks whether <code>url</code> is <code>null</code> and returns a
     * <code> java.net.URI</code> equivalent to this URL.
     * 
     * @param url
     *            source.
     * @return <code>URI</code> equivalent to this <code>URL</code>.
     */
    private static File convertToFile(URL url) {
        if (url == null) {
            return null;
        }
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            System.err.println("Exception occured in JDK15PlusURLToFileConverter" 
                    + " convertToFile():" + e.getMessage());            
            // Returning null rather than propagating the exception so as to
            // stick with FileLoader's semantics.
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("Exception occured in JDK15PlusURLToFileConverter" 
                    + " convertToFile():" + e.getMessage());            
            // Returning null rather than propagating the exception so as to
            // stick with FileLoader's semantics.
            return null;
        }
    }
}
