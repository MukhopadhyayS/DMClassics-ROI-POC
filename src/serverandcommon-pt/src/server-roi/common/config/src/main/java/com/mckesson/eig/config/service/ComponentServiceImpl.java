package com.mckesson.eig.config.service;

import java.io.File;
import java.io.FileReader;

import javax.jws.WebService;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.components.ComponentUtil;
import com.mckesson.eig.utility.components.model.ComponentInfo;
import com.mckesson.eig.utility.components.model.ComponentList;
import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;

/**
 * This class defines various methods that are used to fetch the details of the 
 * components that are deployed in the server.
 *  
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  HECM 1.0; Apr 7, 2008
 */
@WebService(
        name              = "ComponentePortType_v1_0",
        portName          = "component_v1_0",
        serviceName       = "ComponentService_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/configservercomponents-v1",
        endpointInterface = "com.mckesson.eig.config.service.ComponentService")
public class ComponentServiceImpl implements ComponentService {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ComponentServiceImpl.class);
    
    private static final String HECM_COMPONENT_INFO_PATH = 
        ComponentUtil.CONFIG_HOME + "\\com\\mckesson\\hecm\\components";
    
    /**
     * 
     * @see ComponentService#getAllComponents()
     */
    public ComponentList getAllComponents() {
        
        final String logSourceMethod = "getAllComponents()";
        LOG.debug(logSourceMethod + ">>Start");
        
        ComponentList componentList = null;
        try {

            File[] files = new File(ComponentUtil.COMPONENT_INFO_PATH).listFiles();
            componentList = ComponentUtil.unMarshallObject(files);
            
            files = new File(HECM_COMPONENT_INFO_PATH).listFiles();
            componentList.getComponents().addAll(
                    ComponentUtil.unMarshallObject(files).getComponents());
            
            LOG.debug(new StringBuffer().append(logSourceMethod)
                                        .append("Available Components:")
                                        .append(componentList.getComponents().size())
                                        .append(">>End").toString());
        } catch (Exception e) {
            LOG.debug(e.getMessage ());
        }
        return componentList;
    }
    
    /**
     * 
     * @see ComponentService#getComponent(String)
     */
    public ComponentInfo getComponent(String componentID) {
        
        final String logSourceMethod = "getAllComponents()";
        LOG.debug(logSourceMethod + ">>Start");
        
        validateComponentID(componentID);
        ComponentInfo componentInfo = null;
        try {
            
            componentInfo = ComponentUtil.unMarshallObject(
                                        new FileReader(getComponentPath(componentID)));
        } catch (Exception e) {
            LOG.debug(e.getMessage ());
        }
        LOG.debug(logSourceMethod + ">>End");
        return componentInfo;
    }
    
    /**
     * set the component id and their corresponding path in a map.
     *  
     * @return components 
     *         component map holding the id as key and their path as value.
     */
    private String getComponentPath(String componentID) {
        
        String componentPath     = setPath(ComponentUtil.COMPONENT_INFO_PATH, componentID);
        String hecmComponentPath = setPath(HECM_COMPONENT_INFO_PATH, componentID);
        
        if (new File(componentPath).exists()) {
            return componentPath;
        } else if (new File(hecmComponentPath).exists()) {
            return hecmComponentPath;
        }
        
        throw new ApplicationException("Invalid ComponentID", 
                                        ClientErrorCodes.INVALID_COMPONENT_ID);
    }
    
    /**
     * set the component ID and append it with corresponding path
     * 
     * @param path
     *        location of the component where it is located
     *          
     * @param componentID
     *        unique ID of the component
     *          
     * @return componentPath
     *         file name with location. 
     */
    private String setPath(String path, String componentID) {
        
        return new StringBuffer().append(path)
                                 .append("\\")
                                 .append(componentID)
                                 .append(".component-info.xml").toString();
    }
    
    /**
     * This method verifies if the passed componentID are not null and valid.
     * 
     * @param componentID
     *        component ID needs to be verify  
     */
    private void validateComponentID(String componentID) {
        
        if (componentID == null) {
            throw new ApplicationException ("ComponentID null", ClientErrorCodes.NULL_COMPONENT_ID);
        }
    }
}
