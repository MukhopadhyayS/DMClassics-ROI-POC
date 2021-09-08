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

package com.mckesson.eig.utility.jndi;

import java.util.Collection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author Kenneth Partlow
 */
public final class JndiUtilities {

    private static final OCLogger LOG = new OCLogger(JndiUtilities.class);
    private static Context _context = JndiUtilities.createInitialContext();

    private JndiUtilities() {
    }

    public static void createSubcontext(Context context, String name) {
        try {
            context.createSubcontext(name);
        } catch (NameAlreadyBoundException e) {
            throw new JndiNameAlreadyExistsException("name already exists", e);
        } catch (NamingException e) {
            throw new JndiException("Could not create subcontext", e);
        }
    }

    public static Context createInitialContext() {
        try {
            return new InitialContext();
        } catch (NamingException e) {
            throw new JndiException("Could not create context");
        }
    }

    public static Context createInitialContext(Properties properties) {
        try {
            return new InitialContext(properties);
        } catch (NamingException e) {
            throw new JndiException("Could not create context");
        }
    }

    /**
     * This is called safeRebind becuase if the sub-path doesn't exist where
     * you're trying to rebind the object then the sub-path gets created.
     * @param properties - properties to create the initial contxext with
     * @param path - path to bind the object to
     * @param o - object to bind
     */
    public static void safeRebind(Properties props, String path, Object o) {
        safeRebind(createInitialContext(props), path, o);
    }

    /**
     * This is called safeRebind becuase if the sub-path doesn't exist where
     * you're trying to rebind the object then the sub-path gets created.
     * @param path - path where we're trying to bind the object
     * @param o - object to bind
     */
    public static void safeRebind(String path, Object o) {
        safeRebind(_context, path, o);
    }

    /**
     * This is called safeRebind becuase if the sub-path doesn't exist where
     * you're trying to rebind the object then the sub-path gets created.
     * @param path - path where we're trying to bind the object
     * @param o - object to bind
     */
    public static void safeRebind(Context context, String path, Object o) {
        createPath(context, getSubpath(path));
        rebind(context, path, o);
    }

    private static String getSubpath(String path) {
        int index = path.lastIndexOf("/");
        if (index == -1) {
            return null;
        }
        return path.substring(0, index);
    }

    /**
     * This method is usually used during rebind of an object where
     * you want the path to be created no matter what and you don't
     * want to see an exception if the path already exists.
     * @param context
     * @param path
     */
    private static void createPath(Context context, String path) {
        if (path == null) {
            return;
        }
        Collection<String> list = CollectionUtilities.buildStringList(path, "/");
        StringBuilder builder = new StringBuilder();
        for (String component : list) {
            builder.append(component);
            try {
                createSubcontext(context, builder.toString());
            } catch (JndiNameAlreadyExistsException e) {
                //  catch and hide NameAlreadyExistsException
                LOG.info("JNDI Name In use.", e);
            }
            builder.append("/");
        }

    }

    public static void rebind(Context context, String name, Object o) {
        try {
            context.rebind(name, o);
        } catch (NamingException e) {
            throw new JndiException("Could not rebind", e);
        }
    }

    public static Object lookup(Context context, String name) {
        try {
            return context.lookup(name);
        } catch (NamingException e) {
            LOG.info("Could not find object in JNDI for name:  " + name);
            LOG.info(e.getLocalizedMessage(), e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * This look will take care of creating the initial context since
     * you didn't pass one in!
     * @param name - name to use to search for the object
     * @return The object that was bound to JNDI
     */
    public static Object lookup(String name) {
        return lookup(_context, name);
    }

    public static Context getContext() {
        return _context;
    }
}
