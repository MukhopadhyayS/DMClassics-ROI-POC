package com.mckesson.eig.roi.webservice.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.event.ConfigurationListener;

/**
 * The Interface WebClientPool.
 *
 * @param <T> the generic type
 */
public interface WebClientPool<T> extends ConfigurationListener {
    
    /**
     * Gets the web client.
     *
     * @param request the request
     * @return the web client
     */
    T getWebClient(HttpServletRequest request);
    
    /**
     * Gets the web client.
     *
     * @param <P> the generic type
     * @param request the request
     * @param webClientInterface the web client interface
     * @return the web client
     */
    <P> P getWebClient(HttpServletRequest request, Class<P> webClientInterface);
    
    /**
     * Gets the web client.
     *
     * @param <P> the generic type
     * @param request the request
     * @param webClientInterface the web client interface
     * @param serviceAddress the service address
     * @return the web client
     */
    <P> P getWebClient(HttpServletRequest request, Class<P> webClientInterface, String serviceAddress);
    
    /**
     * Removes the users client proxy from the pool.
     *
     * @param userName the userName of the proxy to remove
     */
    void removeClientProxyByUserName(String userName);
}
