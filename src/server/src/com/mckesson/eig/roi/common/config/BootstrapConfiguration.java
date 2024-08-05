package com.mckesson.eig.roi.common.config;

import com.mckesson.eig.roi.dao.DBDataSource;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BootstrapConfiguration class is responsible for reading a config file off of
 * the local file system that contains configuration data for the application
 * server. The config file will be minimal and only contain a very small list of
 * data.
 * <ol>
 * <li>Log File Locations - not tenant specific</li>
 * <li>Core Server (Load Balancer) URLs - used only by the integration server.
 * Separate setting for each tenant.</li>
 * </ol>
 * 
 * @author e6l5nl7
 * 
 */
public final class BootstrapConfiguration {

    private static BootstrapConfiguration _instance = null;
    private static PropertiesConfiguration _propsConfig;


    private BootstrapConfiguration() {
    }

    /**
     * Static method which creates an instance for the first time.
     * 
     * @return SpringUtilities
     */
    public static BootstrapConfiguration getInstance() {
        if (_instance == null) {
            _instance = new BootstrapConfiguration();
        }
        return _instance;
    }

    public void setPropertiesConfiguration(PropertiesConfiguration propertiesConfig) {
        _propsConfig = propertiesConfig;
    }

    public String getTenantId() {
        return _propsConfig.getString("default.tenant");
    }

    public String getLogDirectory() {
        return _propsConfig.getString("log.directory");
    }
    
    public Object get(String key) {
        return _propsConfig.getProperty(key);
    }

    public String getProperty(String key) {
        return _propsConfig.getString(key);
    }

    public void registerListener(ConfigurationListener listener) {
        _propsConfig.addConfigurationListener(listener);
    }

    public String getDbPassword() {
        return _propsConfig.getString("db.password");

    }
    public void setDbPassword(String password) {
        _propsConfig.setProperty("db.password", "ENC_" + password);
    }

    public boolean savePropsConfig() {
        try {
            _propsConfig.save();
        } catch (ConfigurationException e) {
            return false;
        }
        return true;
    }

}
