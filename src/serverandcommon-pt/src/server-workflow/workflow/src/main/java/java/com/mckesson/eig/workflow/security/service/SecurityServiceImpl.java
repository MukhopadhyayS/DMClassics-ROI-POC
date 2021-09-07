/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.security.service;

import javax.jws.WebService;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.security.api.SecurityProperties;
import com.mckesson.eig.workflow.security.PluginSecurity;
import com.mckesson.eig.workflow.security.SecurityService;
import com.mckesson.eig.wsfw.session.WsSession;
import java.util.HashMap;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 18-March-2009 8:51:50 AM
 */
@WebService(
        name = "SecurityPortType_v1_0",
        portName = "security_v1_0",
        serviceName = "SecurityService_v1_0",
        targetNamespace = "http://eig.mckesson.com/wsdl/security-v1",
        endpointInterface = "com.mckesson.eig.workflow.security.SecurityService")
public class SecurityServiceImpl implements SecurityService {
    /**
     * Log object.
     */
    private static final Log LOG = LogFactory
            .getLogger(SecurityServiceImpl.class);

    private static final String WORKFLOW_DESIGNER_MAP = "WorkflowDesignerMap";
    private static final String PLUGIN_SECURITY_BEAN = "PluginSecurityBean";

    public SecurityServiceImpl() {
        super();
    }
    /**
     * @see com.mckesson.eig.workflow.security.SecurityService #logon()
     *     
     * #SecurityProperties logon(String, String)
 
     */
    public SecurityProperties logon(String application, String environment) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(">>>>>>>>>>>>>  IN WEBSERVICE logon()");
        }
        
        HashMap map = (HashMap) (WsSession.getSessionData(WsSession.SECURITY_HEADER_MAP));
        SecurityProperties securityProperties = new SecurityProperties();

        if (map != null) {
            String userName = (String) (map.get("wsse:Username"));
            String password = (String) (map.get("wsse:Password"));
           
             try {
                BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
                String beanId = application + "." + environment;
                PluginSecurity securityBean = (PluginSecurity) beanFactory.getBean(beanId);
                if (securityBean != null) {
                    HashMap designerMap = getDesignerMap();
                    designerMap.put(PLUGIN_SECURITY_BEAN, securityBean);
                    securityProperties = securityBean.logon(userName, password);
                } else {
                    LOG.debug("Error instantiating PluginSecurity bean.");
                    WorkflowException we = 
                        new WorkflowException("Error instantiating PluginSecurity bean.",
                            WorkflowEC.OTHER_SERVER_ERROR);
                    throw we;
                }
            } catch (Exception ex) {
                LOG.error("SecurityServiceImpl:logon request processing"
                        + " exception: " + ex.getMessage());
                WorkflowException we = new WorkflowException(ex, 
                        WorkflowEC.OTHER_SERVER_ERROR);
                throw we;
            }
        }
        LOG.debug("RIGHTS: " + securityProperties.getSecurityRights().size());
        return securityProperties;
    }

    /**
     * @see com.mckesson.eig.workflow.security.SecurityService
     *      #logoff()
     */
    public void logoff() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("logoff()");
        }
        try {
            HashMap designerMap = (HashMap) (WsSession.getSessionData(WORKFLOW_DESIGNER_MAP));
            if (designerMap != null) {
                PluginSecurity securityBean = 
                    (PluginSecurity) designerMap.remove(PLUGIN_SECURITY_BEAN);
                if (securityBean != null) {
                    securityBean.logoff();
                }
            }
        } catch (Exception ex) {
            LOG.debug(ex.getMessage());
            WorkflowException we = new WorkflowException(ex, 
                    WorkflowEC.OTHER_SERVER_ERROR);
            throw we;
        }
    }
    
    private HashMap getDesignerMap() {
        HashMap designerMap = (HashMap) (WsSession.getSessionData(WORKFLOW_DESIGNER_MAP));
        if (designerMap == null) {
            designerMap = new HashMap();
            WsSession.setSessionData(WORKFLOW_DESIGNER_MAP, designerMap);
        }
        return designerMap;
    }
}
