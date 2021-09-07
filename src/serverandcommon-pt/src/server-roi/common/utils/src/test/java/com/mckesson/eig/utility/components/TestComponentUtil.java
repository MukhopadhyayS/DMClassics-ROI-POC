/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import junit.framework.TestCase;

import com.mckesson.eig.utility.components.model.ApplicationInfo;
import com.mckesson.eig.utility.components.model.ComponentInfo;
import com.mckesson.eig.utility.components.model.ComponentList;
import com.mckesson.eig.utility.components.model.LogConfig;
import com.mckesson.eig.utility.components.model.UpdateHistory;
import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Sahul Hameed Y
 * @date   Apr 12, 2008
 * @since  HECM 1.0; Apr 12, 2008
 */
public class TestComponentUtil extends TestCase {

    private static final String PATH          = "etc\\com\\mckesson\\eig\\utility\\components\\";
    private static final String FILE_NAME     = PATH + "test.component-info.xml";
    private static final String COMPONENT_ID  = "ComponentID";
    /**
     *
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        System.setProperty("application.home", System.getenv("JBOSS_HOME") + "\\server\\default");
    }

    public void testMarshallObject() 
    throws Exception {

        ComponentInfo componentInfo = new ComponentInfo();

        componentInfo.setComponentID(COMPONENT_ID);
        componentInfo.setConfigurationInfo("configurationInfo");
        componentInfo.setComponentDisplayName("ComponentDisplayName");

        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setApplicationPath("applicationPath");
        applicationInfo.setComponentName("componentName");
        componentInfo.setApplicationInfo(applicationInfo);
        
        LogConfig logConfig = new LogConfig();
        logConfig.setAppenders(new String[] {"app1", "app2"});
        logConfig.setCategories(new String[] {"cat1", "cat2"});
        logConfig.setLogFilePath("logPath");
        componentInfo.setLogConfig(logConfig);

        List<UpdateHistory> updateHistories = new ArrayList<UpdateHistory>();
        UpdateHistory updateHistory = new UpdateHistory(DateUtilities.formatISO8601(new Date()), 
                                                        "version", 
                                                        "comments");

        UpdateHistory updateHistory1 = new UpdateHistory(DateUtilities.formatISO8601(new Date()), 
                                                         "version", 
                                                         "comments");
        updateHistories.add(updateHistory);
        updateHistories.add(updateHistory1);
        componentInfo.setUpdatedHistories(updateHistories);

        StringWriter writer = new StringWriter();
        try {

            ComponentUtil.marshallObject(componentInfo, writer);
            Reader reader = new StringReader(writer.toString());

            ComponentInfo component = ComponentUtil.unMarshallObject(reader);

            assertEquals(componentInfo.getComponentID(), component.getComponentID());
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            ComponentUtil.marshallObject(componentInfo, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    public void testUnMarshallObject() 
    throws Exception {

        File[] files = new File(PATH).listFiles();
        ComponentList componentList = ComponentUtil.unMarshallObject(files);
        assertEquals(componentList.getComponents().size(), 1);

        Reader reader = null;
        try {
            reader = new FileReader(FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }

        ComponentInfo componentInfo = ComponentUtil.unMarshallObject(reader);
        assertEquals(componentInfo.getComponentID(), COMPONENT_ID);
    }
    
    public void testMarshallingWithNullObject() {

        try {
            ComponentUtil.marshallObject(null, new StringWriter());
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testUnMarshallingWithNullReader() {

        try {
            ComponentUtil.unMarshallObject(new FileReader(FILE_NAME));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testUnMarshallingWithNullFiles() {

        try {
            ComponentUtil.unMarshallObject(new File(FILE_NAME).listFiles());
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    public void testGetVersionWithInvalidJar()
    throws Exception {

        String version      = ComponentUtil.getVersion(PATH);
        assertEquals(version, StringUtilities.EMPTYSTRING);
    }
    
    public void testGetVersionWithJar()
    throws Exception {

        FileWriter fileWriter = new FileWriter(new File(PATH + "MANIFEST.MF"));
        fileWriter.write("Manifest-Version: 1.0\nImplementation-Version: 1.0.0.354\n"); 
        fileWriter.close();
        
        InputStream inputStream = new FileInputStream(PATH + "MANIFEST.MF");
        Manifest manifest = new Manifest(inputStream);
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(
                                                              new File(PATH + "temp.jar")), 
                                                              manifest);
        inputStream.close();
        
        new File(PATH + "MANIFEST.MF").delete();
        File file = new File(PATH + "component.mapping.xml");
        
        JarEntry jarEntry = new JarEntry("component.mapping.xml");
        jarOutputStream.putNextEntry(jarEntry);
        
        FileInputStream fileContent = new FileInputStream(file);
        
        byte[] b = new byte[fileContent.available()];
        fileContent.read(b);
        
        fileContent.close();
        jarOutputStream.write(b);
        
        jarOutputStream.closeEntry();
        jarOutputStream.close();
        
        String version      = ComponentUtil.getVersion(PATH + "temp.jar");
        assertEquals(version, "1.0.0.354");
        
        new File(PATH + "temp.jar").delete();
    }
    
    public void testComponentUtilConstructor() {
        
        try {

            ReflectionUtilities.callPrivateConstructor(ComponentUtil.class);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    public void testGetHome() {
        
        try {

            Object componentUtil = ReflectionUtilities.callPrivateConstructor(ComponentUtil.class);
            ReflectionUtilities.callPrivateMethod(ComponentUtil.class, 
                                                  componentUtil, 
                                                  "getHome", 
                                                  new Class[] {String.class}, 
                                                  new Object[] {"USERPROFILE"});
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
