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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.mckesson.eig.utility.util.ReflectionUtilities;

/**
 * Static utility class for locating and loading files. It uses the ClassLoader
 * to locate files as from an absolute paths or a path relative to the
 * classpath. This is a simple utility, but very useful for keeping absolute
 * paths that will change from machine to machine out of your code and out of
 * your configuration files.
 */
public final class FileLoader {
    private static final URLToFileConverter URL_FILECONVERTER = createURLToFileConverter();

    /**
     * Sole constructor.
     *
     */
    private FileLoader() {
    }

    /**
     * Creates a new instance of the specified class. The class is instantiated
     * as if by a <code>new</code> expression with an empty argument list. The
     * class is initialized if it has not already been initialized.
     *
     * @return a newly allocated instance of the specified class.
     *
     */
    private static URLToFileConverter createURLToFileConverter() {
        return (URLToFileConverter) ReflectionUtilities
                .newInstance(new String[]{
                        "com.mckesson.eig.utility.io.JDK15PlusURLToFileConverter",
                        "com.mckesson.eig.utility.io.JDK14MinusURLToFileConverter"});
    }

    /**
     * Finds and returns a <code>URL</code> object for reading the resource,
     * or <code>null</code> if the resource could not be found or the invoker
     * doesn't have adequate privileges to get the resource.
     * <p>
     * A resource is some data (images, audio, text, etc) that can be accessed
     * by class code in a way that is independent of the location of the code.
     * The name of a resource is a '<code>/</code>'-separated path name that
     * identifies the resource.
     * <p>
     *
     * @param url
     *            resource name
     * @return <code>URL</code> object for reading the resource, or
     *         <code>null</code> if the resource could not be found or the
     *         invoker doesn't have adequate privileges to get the resource.
     */
    public static URL getResourceAsUrl(String url) {
        return getResourceAsUrl(getClassLoader(), url);
    }

    /**
     * Finds and returns a <code>URL</code> object for reading the resource,
     * or <code>null</code> if the resource could not be found or the invoker
     * doesn't have adequate privileges to get the resource.
     *
     *
     * @param cl
     *            parent <code>ClassLoader</code> object.
     * @param path
     *            resource name
     * @return <code>URL</code> object for reading the resource, or
     *         <code>null</code> if the resource could not be found or the
     *         invoker doesn't have adequate privileges to get the resource.
     */
    public static URL getResourceAsUrl(ClassLoader cl, String path) {
        if (cl == null) {
            return ClassLoader.getSystemResource(path);
        }
        return cl.getResource(path);
    }

    /**
     * Finds and returns a <code>URL</code> object with the given parent
     * <code>ClassLoader</code> object and resource name .Returns
     * <code>null</code> if the resource could not be found or the invoker
     * doesn't have adequate privileges to get the resource.
     *
     * @param source
     *            object of the parent <code>ClassLoader</code>.
     * @param path
     *            resource name.
     * @return <code>URL</code> object for reading the resource, or
     *         <code>null</code> if the resource could not be found or the
     *         invoker doesn't have adequate privileges to get the resource.
     */
    public static URL getResourceAsUrl(Object source, String path) {
        return getResourceAsUrl(getClass(source), path);
    }

    /**
     * Finds a resource with a given path.
     *
     * @param source
     *            <code>Class</code> instance.
     * @param path
     *            Resource path.
     * @return a <code> java.net.URL</code> object or <tt>null</tt> if no
     *         resource with this name is found.
     */
    public static URL getResourceAsUrl(Class<?> source, String path) {
        if (source == null) {
            return ClassLoader.getSystemResource(path);
        }
        return source.getResource(path);
    }

    /**
     * Returns the runtime class of an object. That <tt>Class</tt> object is
     * the object that is locked by <tt>static synchronized</tt> methods of
     * the represented class.
     *
     * @param object
     *            <code>Class</code> object.
     * @return The <code>java.lang.Class</code> object that represents the
     *         runtime class of the object.<code>null</code> if this object
     *         is <code>null</code>.
     */
    private static Class<?> getClass(Object object) {
        return (object == null) ? null : object.getClass();
    }

    /**
     * Returns an input stream for reading the resource of the specified
     * <code>url</code>.
     *
     * @param url
     *            The resource name
     *
     * @return An input stream for reading the resource, or <code>null</code>
     *         if the resource could not be found
     */
    public static InputStream getResourceAsInputStream(String url) {
        return getResourceAsInputStream(getClassLoader(), url);
    }

    /**
     * Returns an input stream for reading the resource of the specified
     * <code>url</code>.
     *
     * @param url
     *            The resource name
     * @param cl
     *            instance of <code>ClassLoader</code>.
     * @return An input stream for reading the resource, or <code>null</code>
     *         if the resource could not be found
     */
    public static InputStream getResourceAsInputStream(ClassLoader cl, String url) {
        if (cl == null) {
            return ClassLoader.getSystemResourceAsStream(url);
        }
        return cl.getResourceAsStream(url);
    }

    /**
     * Creates a new <code>FileReader</code> for the given resource name.
     *
     * @param fileName
     *            resource file name
     * @return <code>Reader</code> of the specified resource.
     */
    public static Reader getResourceAsReader(String fileName) {
        try {
            return getReader(getResourceAsFile(fileName));
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Creates a new <code>FileReader</code>, given the <code>File</code>
     * to read from.
     *
     * @param file
     *            file to be read.
     * @return opens the specific file for reading <code>null</code> if not
     *         found.
     */
    public static Reader getReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Returns a file which has a specified resource.
     *
     * @param cl
     *            instance of <code>ClassLoader</code>.
     * @param file
     *            resource file name.
     * @return File having the specified resource <code>URL</code>.
     */
    public static File getResourceAsFile(ClassLoader cl, String file) {
        return toFile(getResourceAsUrl(cl, file));
    }

    /**
     * Returns a file which has a a specified resource.
     *
     * @param file
     *            resource file name.
     * @return file having the specified resource <code>URL</code>
     */
    public static File getResourceAsFile(String file) {
        return getResourceAsFile(getClassLoader(), file);
    }

    /**
     * Returns a file which has a specified resource.
     *
     * @param source
     *            parent <code>ClassLoader</code> object.
     * @param file
     *            resource file name.
     * @return file having the specified resource <code>URL</code>
     */
    public static File getResourceAsFile(Object source, String file) {
        return toFile(getResourceAsUrl(source, file));
    }

    /**
     * Returns a file which has a specified resource.
     *
     * @param source
     *            instance of <code>Class</code>.
     * @param file
     *            resource file name.
     * @return file having the specified resource <code>URL</code>
     */
    public static File getResourceAsFile(Class<?> source, String file) {
        return toFile(getResourceAsUrl(source, file));
    }

    /**
     * Gets a resource bundle using the specified base name, the default locale,
     * and the caller's class loader.
     *
     * @param source
     *            instance of parent class.
     * @param name
     *            file name.
     * @return a resource bundle for the obtained base name and the default
     *         locale.
     */
    public static ResourceBundle getResourceBundle(Object source, String name) {
        return ResourceBundle.getBundle(buildResourceBundleName(source, name));
    }

    /**
     * Gets a resource bundle using the specified base name, the default locale,
     * and the caller's class loader.
     *
     * @param source
     *            parent <code>class</code> object.
     * @param name
     *            file name
     * @return a resource bundle for the obtained base name and the default
     *         locale.
     */
    public static ResourceBundle getResourceBundle(Class<?> source, String name) {
        return ResourceBundle.getBundle(buildResourceBundleName(source, name));
    }

    /**
     * Returns the absolute path of the <code>java</code> file.
     *
     * @param source
     *            parent <code>class</code> object.
     * @param name
     *            file name.
     * @return absolute path of the specified file.
     */
    private static String buildResourceBundleName(Class<?> source, String name) {
        return source.getPackage().getName() + '.' + name;
    }

    /**
     * Returns the absolute path of the <code>java</code> file.
     *
     * @param source
     *            parent <code>class</code> object.
     * @param name
     *            file name.
     * @return absolute path of the specified file.
     */
    private static String buildResourceBundleName(Object source, String name) {
        return buildResourceBundleName(source.getClass(), name);
    }

    /**
     * Creates and draws an ImageIcon from the specified file. The image will be
     * preloaded by using MediaTracker to monitor the loading state of the
     * image. The specified String can be a file name or a file path.
     *
     * @param file
     *            source
     * @return draws an ImageIcon from the specified file
     */
    public static Icon getResourceAsIcon(String file) {
        return loadLocalIcon(getResourceAsUrl(file));
    }

    /**
     * Returns the class loader for the class.
     *
     * @return class loader for the class.
     */
    public static ClassLoader getClassLoader() {
        return FileLoader.class.getClassLoader();
    }

    /**
     * Creates and draws an ImageIcon from the specified file. The image will be
     * preloaded by using MediaTracker to monitor the loading state of the
     * image. The specified String can be a file name or a file path.
     *
     * @param url
     *            source
     * @return draws an ImageIcon from the specified file
     */
    public static Icon loadLocalIcon(URL url) {
        if (url == null) {
            return null;
        }
        return new ImageIcon(url.getFile());
    }

    public static File toFile(URL url) {
        return URL_FILECONVERTER.toFile(url);
    }
}
