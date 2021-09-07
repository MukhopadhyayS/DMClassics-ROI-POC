package com.mckesson.eig.roi.webservice.util.rest;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.common.config.BootstrapConfiguration;
import com.mckesson.eig.roi.webservice.util.WebClientPool;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

public class RestWebClientPool 
implements WebClientPool<WebClient> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RestWebClientPool.class);

    private HashMap<String, WebClient> _clientMap;
    private List<?> _providers;
    
    public RestWebClientPool() {
        _clientMap = new HashMap<String, WebClient>();
        BootstrapConfiguration.getInstance().registerListener(this);
    }

    public void setProviders(List<?> providers) {
        _providers = providers;
    }

    @Override
    public WebClient getWebClient(HttpServletRequest request) {
        
        // Must read the CoreServerUrl config in order to force a refresh if mpf.properties changed
        String coreAddress = getCoreServerUrl(request); 
        String tenantId = BootstrapConfiguration.getInstance().getTenantId();
        if (coreAddress == null) {
            
            String message = "Error - Mpf core address is not configured for tenant Id:" + tenantId;
            LOGGER.error(message);
            throw new ROIException(ROIClientErrorCodes.MPF_CORE_ADDRESS_NOT_CONFIGURED, message);
        }
        
        WebClient client = _clientMap.get(tenantId);
        if (client == null) {
            client = WebClient.create(coreAddress, _providers);
            _clientMap.put(tenantId, client);
        }
        return client;
    }

    @Override
    public void configurationChanged(ConfigurationEvent event) {
        
        if ((event.getType() == AbstractFileConfiguration.EVENT_RELOAD)
                && (!event.isBeforeUpdate())) {
            _clientMap.clear();
        }
    }

    /* (non-Javadoc)
     * @see com.mckesson.dm.integration.service.util.WebClientPool#getWebClient(javax.servlet.http.HttpServletRequest, java.lang.Class, java.lang.String)
     */
    @Override
    public <P> P getWebClient(HttpServletRequest request, Class<P> webClientInterface, String serviceAddress) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.mckesson.dm.integration.service.util.WebClientPool#getWebClient(javax.servlet.http.HttpServletRequest, java.lang.Class)
     */
    @Override
    public <P> P getWebClient(HttpServletRequest request, Class<P> webClientInterface) {
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.mckesson.dm.integration.service.util.WebClientPool#removeClientProxyByUserName(java.lang.String)
     */
    public void removeClientProxyByUserName(String userName) {
        //Do Nothing intentionally
    }
    
    public static String getCoreServerUrl(HttpServletRequest request) {
        
        BootstrapConfiguration config = BootstrapConfiguration.getInstance();
        String tenantId = config.getTenantId();
        
        if (StringUtilities.isEmpty(tenantId)) {
            return null;
        }
        
        String coreHostName = config.getProperty(tenantId + ".core.server.hostname");
        String corePort = config.getProperty(tenantId + ".core.server.port");
        String coreContext = config.getProperty(tenantId + ".core.server.context");
        
        StringBuilder absolutePath = new StringBuilder("http");
        
        if (StringUtilities.hasContent(coreHostName)) {
            absolutePath.append("://")
                        .append(coreHostName);
        }

        if (StringUtilities.hasContent(corePort)) {
            absolutePath.append(":")
                        .append(corePort);
        }

        if (StringUtilities.hasContent(coreContext)) {
            absolutePath.append("/")
                        .append(coreContext);
        }
        
        return absolutePath.toString();
    }

}
