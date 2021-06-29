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
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.mckesson.eig.utility.components.model.ComponentInfo;
import com.mckesson.eig.utility.components.model.ComponentList;
import com.mckesson.eig.utility.io.IOUtilities;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * This class contains the utility methods in order to access and update the components.
 *
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  Utils; Apr 7, 2008
 */
public final class ComponentUtil {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ComponentUtil.class);

    /**
     * specifies the separator
     */
    public static final String SEPARATOR = ",";

    /**
     * Version key found in the ear or war
     */
    private static final String VERSION = "Implementation-Version";

    /**
     * server config home
     */
    public static final String CONFIG_HOME = getHome("CONFIG_HOME");

    /**
     * application home
     */
    public static final String APP_HOME = getHome("application.home");

    /**
     * component info path
     */
    public static final String COMPONENT_INFO_PATH =
                               CONFIG_HOME + "\\com\\mckesson\\eig\\components";

    private static JAXBContext _jaxbContext;

    private static final String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String FEATURE1 = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURE2 = "http://xml.org/sax/features/external-parameter-entities";
    private static final String FEATURE3 = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    private ComponentUtil() {
    }

    public static JAXBContext getJAXBContext()
    throws JAXBException {

        if (_jaxbContext != null) {
            return _jaxbContext;
        }
        _jaxbContext = JAXBContext.newInstance(ComponentInfo.class);
        return _jaxbContext;
    }

    /**
     * Marshals the given Object as String using the mapping provided
     *
     * @param componentInfo
     *        Object need to be marshalling.
     *
     * @return componentInfoXML
     *         XML string for the corresponding object.
     */
    public static void marshallObject(ComponentInfo componentInfo, Writer writer)
    throws JAXBException {

        Marshaller m = getJAXBContext().createMarshaller();
        m.marshal(componentInfo, writer);
    }

    private static SAXParserFactory getSAXParserFactory() 
    		throws SAXNotSupportedException, SAXNotRecognizedException, ParserConfigurationException {
    	SAXParserFactory spf = SAXParserFactory.newInstance();
    	spf.setNamespaceAware(true);
    	spf.setFeature(FEATURE, true);
    	spf.setFeature(FEATURE1, false);
    	spf.setFeature(FEATURE2, false);
    	spf.setFeature(FEATURE3, false);
    	return spf;
    }
    /**
     * Unmarshals Objects of this Unmarshaller's Class type. The Class must specify the proper
     * access methods (setters/getters) in order for instances of the Class to be properly
     * unmarshalled.
     *
     * @param fileName
     *        read the XML from the corresponding file.
     *
     * @return componentInfo
     *         component information.
     */
    public static ComponentInfo unMarshallObject(Reader reader) throws JAXBException {
        ComponentInfo returnValue = null;
        try {
            SAXParserFactory spf = getSAXParserFactory();
	        spf.setValidating(false);
	        org.xml.sax.InputSource source = new  org.xml.sax.InputSource (reader);
	        Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), source);
	        Unmarshaller um = getJAXBContext().createUnmarshaller();
	        returnValue = (ComponentInfo) um.unmarshal(xmlSource);
        } catch (SAXNotRecognizedException e) {
        	LOG.error("SAXNotRecognizedException", e);
			throw new JAXBException(e);
		} catch (SAXNotSupportedException e) {
			LOG.error("SAXNotSupportedException", e);
			throw new JAXBException(e);	
		} catch ( ParserConfigurationException ex) {
			LOG.error("ParserConfigurationException", ex);
			throw new JAXBException(ex);
		}  catch (Exception e) {
			LOG.error("SaXException", e);
			throw new JAXBException(e);
		} 
        return returnValue;
    }


    /**
     * Unmarshalls objects from the list of files.
     *
     * @param files
     *        list of files need to unmarshalling.
     *
     * @return componentList
     *         list of component info.
     */
    public static ComponentList unMarshallObject(File[] files)
    throws Exception {

        List<ComponentInfo> list = new ArrayList<ComponentInfo>();
        ComponentInfo componentInfo = null;
        for (File file : files) {
            if (!file.getName().endsWith(".component-info.xml")) {
                continue;
            }
            Reader reader = IOUtilities.createBufferedReader(file);
             try {
                componentInfo = unMarshallObject(reader);
                list.add(componentInfo);
            } finally {
            	IOUtilities.close(reader);
            }
        }

        return new ComponentList(list);
    }

    /**
     * Getting the version of an archive file from the provided archive location.
     *
     * @param archiveLocation
     *        need to get the version from this archive location.
     *
     * @return version
     *         version of the archive file.
     */
    public static String getVersion(String archiveLocation) {

        Attributes attributes = getManifestAttributes(archiveLocation);
        return (attributes == null) ? StringUtilities.EMPTYSTRING : attributes.getValue(VERSION);
    }

    /**
     * Getting the Attributes of an archive file from the provided archive location.
     *
     * @param archiveLocation
     *        need to get the manifest attribute from this archive location.
     *
     * @return attributes
     *         attributes of the archive manifest file.
     */
    public static Attributes getManifestAttributes(String archiveLocation) {

        Attributes attributes = null;
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(archiveLocation);
            attributes = jarFile.getManifest().getMainAttributes();
        } catch (IOException ie) {
            LOG.debug(ie);
        } finally {
        	if (null != jarFile) {
        		try {
        			jarFile.close();
        		} catch (Exception e) {
        			LOG.debug(e);
        		}
        	}
        }
        return attributes;
    }

    /**
     * Get home from environment variable or from system properties and parse accordingly.
     *
     * @param homeName
     *        key for to get the home value
     *
     * @return value
     *         corresponding home
     */
    private static String getHome(String homeName) {

        String home = StringUtilities.EMPTYSTRING;
        try {

            home = System.getenv(homeName) != null
                   ? new File(System.getenv(homeName)).getCanonicalPath()
                   : System.getProperty(homeName) != null
                   ? new File(System.getProperty(homeName)).getCanonicalPath()
                   : StringUtilities.EMPTYSTRING;
        } catch (IOException e) {
            LOG.debug(e);
        }
        return home;
    }
}
