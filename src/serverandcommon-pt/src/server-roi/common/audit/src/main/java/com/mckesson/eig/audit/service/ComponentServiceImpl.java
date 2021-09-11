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

package com.mckesson.eig.audit.service;

import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.jws.WebService;

import com.mckesson.eig.utility.components.ComponentUtil;
import com.mckesson.eig.utility.components.model.ComponentInfo;
//import com.mckesson.eig.utility.log.Log;
//import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * 
 * This class defines various methods that are used to fetch the details of the components 
 * that are deployed in the server. 
 * 
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  AuditServer; Apr 7, 2008
 */
@WebService(
name              = "ComponentPortType_v1_0",
portName          = "ComponentServicePort_v1_0",
serviceName       = "ComponentService_v1_1",
targetNamespace   = "http://eig.mckesson.com/wsdl/auditcomponent-v1",
endpointInterface = "com.mckesson.eig.audit.service.ComponentService")
public class ComponentServiceImpl implements ComponentService {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final OCLogger LOG = new OCLogger(ComponentServiceImpl.class);
    
    private static final String COMPONENT_PATH = 
        ComponentUtil.COMPONENT_INFO_PATH + "\\audit.component-info-base.xml"; 
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.ComponentService
     *  #getComponentDetails()
     */
    public String getComponentDetails() {
        
        final String logSourceMethod = "getComponentDetails()";
        LOG.debug(logSourceMethod + ">>Start");
        
        Writer writer = new StringWriter();
        try {

            ComponentInfo componentInfo = 
                ComponentUtil.unMarshallObject(new FileReader(COMPONENT_PATH));
            String warPath = ComponentUtil.APP_HOME + "\\deploy\\audit.war";

            componentInfo.getApplicationInfo().setApplicationPath(warPath);
            componentInfo.getApplicationInfo().setVersion(ComponentUtil.getVersion(warPath));
            ComponentUtil.marshallObject(componentInfo, writer);
        } catch (Exception e) {
            LOG.error(e);
        }
        
        LOG.debug(logSourceMethod + ">>End");
        return writer.toString();
    }
}
