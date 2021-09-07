/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.wsfw.axis;

import java.net.URL;

import org.apache.axis.utils.Messages;
import org.castor.mapping.BindingType;
import org.castor.mapping.MappingUnmarshaller;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.ClassDescriptorResolverFactory;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLClassDescriptorResolver;

import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * Provides methods for loading the Mapping XML and loggging all the exceptions.
 * 
 */
public class CastorContext {

    /**
     * Gets the Logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(CastorContext.class);

    /**
     * Holds the instance of Mapping.
     */
    private final Mapping _mapping;

    /**
     * Holds the instance of ClassDescriptorResolver.
     */
    private XMLClassDescriptorResolver _cldResolver;
    
    /**
     * Constructs a new mapping.
     */
    public CastorContext() {
        _mapping = new Mapping(getClass().getClassLoader());
    }

    /**
     * Loads the mapping from the specified URL with type defaults to
     * 'CastorXmlMapping'.
     * 
     * @param mappingFile
     *            resourceName.
     */
    public void setMappingFile(String mappingFile) {
        try {
            URL url = FileLoader.getResourceAsUrl(mappingFile);
            _mapping.loadMapping(url);
            prepareClassDescriptorResolver();
        } catch (Exception e) {
            throw new CastorContextException(e);
        }
    }

    /**
     * prepare the ClassDescriptorResolver to use during marshalling and unmarshalling
     */
    private void prepareClassDescriptorResolver() 
    throws MappingException {

        _cldResolver = (XMLClassDescriptorResolver)
            ClassDescriptorResolverFactory.createClassDescriptorResolver(BindingType.XML);

        MappingUnmarshaller mappingUnmarshaller = new MappingUnmarshaller();
        _cldResolver.setMappingLoader(mappingUnmarshaller.getMappingLoader(_mapping,
                                                                           BindingType.XML));
    }

	    /**
     * Returns this <code>ClassDescriptorResolver</code>.
     */
    public XMLClassDescriptorResolver getClassDescriptorResolver() {
        return _cldResolver;
    }
	
    /**
     * Returns this <code>Mapping</code>.
     * 
     * @return Mapping.
     */
    public Mapping getMapping() {
        return _mapping;
    }

    /**
     * Logs the error message and returns a formatted
     * message.(MarshalErrorMessage and a localized description).
     * 
     * @param e
     *            MarshalException Details.
     * @return a formatted message.
     */
    public String report(MarshalException e) {
        LOG.error(getMarshalErrorMessage(), e);
        return getMarshalErrorMessage() + " " + e.getLocalizedMessage();
    }

    /**
     * Logs the Error Message and returns formatted Message(ValidationException)
     * along with the location of its occurance and localized description of
     * this throwable.
     * 
     * @param e
     *            ValidationException Details.
     * @return Error Message.
     */
    public String report(ValidationException e) {
        LOG.error(getValidationErrorMessage(), e);
        return getValidationErrorMessage() + " " + e.getLocation() + ": "
                + e.getLocalizedMessage();
    }

    /**
     * Logs the Error Message and returns a formatted message.
     * 
     * @param e
     *            Mapping Exception Detail.
     * @return formatted mesage.
     */
    public String report(MappingException e) {
        LOG.error(getMappingErrorMessage(), e);
        return getMappingErrorMessage() + " " + e.getLocalizedMessage();
    }

    /**
     * Logs the Error Message.
     * 
     * @param e
     *            Exception Detail.
     * @return <code>null</code>.
     */
    public String report(Exception e) {
        LOG.error(getGenericErrorMessage(), e);
        return null;
    }

    /**
     * Returns the formatted message from resource.properties (from the package
     * of this <code>exception00</code> object.
     * 
     * @return formatted message.
     */
    private String getGenericErrorMessage() {
        return Messages.getMessage("exception00");
    }

    /**
     * Returns the formatted message from resource.properties (from the package
     * of this <code>castorMarshalException00</code> object.
     * 
     * @return formatted message.
     */
    private String getMarshalErrorMessage() {
        return Messages.getMessage("castorMarshalException00");
    }

    /**
     * Returns the formatted message from resource.properties (from the package
     * of this <code>castorValidationException00</code> object.
     * 
     * @return formatted message.
     */
    private String getValidationErrorMessage() {
        return Messages.getMessage("castorValidationException00");
    }

    /**
     * Returns a Error Message for this Castor Mapping.
     * 
     * @return Error Message.
     */
    private String getMappingErrorMessage() {
        return "Error in castor mapping:";
    }
}
