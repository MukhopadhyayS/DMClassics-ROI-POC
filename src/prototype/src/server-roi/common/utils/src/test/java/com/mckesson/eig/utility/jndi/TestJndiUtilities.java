/*
 * Created on Oct 28, 2004
 *
 * Copyright  2004 McKesson Corporation and/or one of its 
 * subsidiaries. All Rights Reserved.
 * 
 * Use of this material is governed by a license agreement. 
 * This material contains confidential, proprietary and trade 
 * secret information of McKesson Information Solutions and is 
 * protected under United States and international copyright and 
 * other intellectual property laws. Use, disclosure, 
 * reproduction, modification, distribution, or storage in a 
 * retrieval system in any form or by any means is prohibited 
 * without the prior express written permission of McKesson 
 * Information Solutions.
 */
package com.mckesson.eig.utility.jndi;

import java.util.Properties;

import javax.naming.Context;

import junit.framework.Test;

import org.mockejb.jndi.MockContextFactory;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 * @author Kenneth Partlow
 *
 */
public class TestJndiUtilities extends UnitTest {

    public TestJndiUtilities(String arg0) {
        super(arg0);
    }

	public static Test suite() {
		return new CoverageSuite(TestJndiUtilities.class, JndiUtilities.class);
	}
    
    protected void setUp() throws Exception {
        MockContextFactory.setAsInitial();
    }
    
    protected void tearDown() throws Exception {
        MockContextFactory.revertSetAsInitial();
    }
    
    public void testCreateInitialContext() {
        assertNotNull(JndiUtilities.createInitialContext());
    }

    public void testCreateInitialContextWithProperties() {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
                "org.mockejb.jndi.MockContextFactory"); 
        assertNotNull(JndiUtilities.createInitialContext(props));
    }

    public void testSafeRebindThatTakesProperties() {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
                "org.mockejb.jndi.MockContextFactory");
        Context context = JndiUtilities.createInitialContext(props);
        JndiUtilities.safeRebind(context, "com/anyone", new Integer(THREE));
        assertEquals(new Integer(THREE), JndiUtilities.lookup(context, "com/anyone"));
    }

    public void testSafeRebindWithSmallPath() throws Exception {
        JndiUtilities.safeRebind("froople", new Integer(THREE));
        assertEquals(new Integer(THREE), JndiUtilities.lookup("froople"));
    }
    
    public void testRebindThrowsException() throws Exception {
        Context context = JndiUtilities.createInitialContext();
        try {
            JndiUtilities.rebind(context, "", new Integer(THREE));
            fail();
        } catch (JndiException e) {
            assertTrue(true);
        }
    }
    
    public void testSafeRebind() {
        JndiUtilities.safeRebind("eig/configuration/help.txt", new Integer(THREE));
        assertEquals(new Integer(THREE), JndiUtilities.lookup("eig/configuration/help.txt"));
        JndiUtilities.safeRebind("eig/help.txt", new Integer(SEVEN));
        assertEquals(new Integer(THREE), JndiUtilities.lookup("eig/configuration/help.txt"));
        assertEquals(new Integer(SEVEN), JndiUtilities.lookup("eig/help.txt"));
        JndiUtilities.safeRebind("eig/configuration/help.txt", new Integer(NINE));
        assertEquals(new Integer(NINE), JndiUtilities.lookup("eig/configuration/help.txt"));
    }
    
    public void testCreateSubcontextWithNameThatExists() {
        JndiUtilities.safeRebind("eig/configuration/help.txt", new Integer(THREE));
        try {
            JndiUtilities.createSubcontext(JndiUtilities.getContext(), "eig/configuration");
            fail();
        } catch (JndiNameAlreadyExistsException e) {
            assertTrue(true);
        }
    }

    public void testCreateSubcontext() {
        try {
            Context context = JndiUtilities.createInitialContext();
            JndiUtilities.createSubcontext(context, "");
            fail();
        } catch (JndiException e) {
            assertTrue(true);
        }
    }
    
    public void testCreateInitialContextExceptionCase() {
        try {
            System.getProperties().setProperty(
                   Context.INITIAL_CONTEXT_FACTORY, 
            	"this.file.does.not.exist"); 
            JndiUtilities.createInitialContext();
            fail();
        } catch (JndiException e) {
            assertTrue(true);
        }
    }

    public void testCreateInitialContextThatTakesAPropertyExceptionCase() {
        try {
            Properties props = new Properties();
            props.setProperty(
                Context.INITIAL_CONTEXT_FACTORY, 
            	"this.file.does.not.exist"); 
            JndiUtilities.createInitialContext(props);
            fail();
        } catch (JndiException e) {
            assertTrue(true);
        }
    }

    public void testLookupWithContext() throws Exception {
        JndiUtilities.safeRebind("froople", new Integer(SEVEN));
        assertEquals(new Integer(SEVEN), JndiUtilities.lookup("froople"));

        assertNull(JndiUtilities.lookup("frahlein"));
    }
}
