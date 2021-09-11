/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.workflow.worklist.service;

import java.io.FileReader;
import java.io.StringWriter;

import javax.jws.WebService;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.components.ComponentUtil;
import com.mckesson.eig.utility.components.model.ComponentInfo;


/**
 * This class defines various methods that are used to fetch the details of the 
 * components that are deployed in the server.
 *  
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  HECM 1.0; Apr 7, 2008
 */
@WebService(
        name              = "ComponentPortType_v1_0",
        portName          = "ComponentServicePort_v1_0",
        serviceName       = "ComponentService_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/workflowcomponent-v1",
        endpointInterface = "com.mckesson.eig.workflow.worklist.service.ComponentService")
public class ComponentServiceImpl implements ComponentService {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ComponentServiceImpl.class);
    
    private static final String COMPONENT_PATH = 
        ComponentUtil.COMPONENT_INFO_PATH + "\\workflow.component-info-base.xml";
    
    /**
     * @see ComponentService
     *  #getComponentDetails()
     */
    public String getComponentDetails() {

        final String logSourceMethod = "getComponentDetails()";
        LOG.debug(logSourceMethod + ">>Start");
        
        StringWriter writer = new StringWriter();
        
        try {

            String path = ComponentUtil.APP_HOME + "\\deploy\\eig.workflow.war";
            
            ComponentInfo componentInfo = 
                ComponentUtil.unMarshallObject(new FileReader(COMPONENT_PATH));
            
            componentInfo.getApplicationInfo().setApplicationPath(path);
            componentInfo.getApplicationInfo().setVersion(ComponentUtil.getVersion(path));
            
            ComponentUtil.marshallObject(componentInfo, writer);
        } catch (Exception e) {
            LOG.error(e);
        }

        LOG.debug(logSourceMethod + ">>End");
        return writer.toString();
    }
}
