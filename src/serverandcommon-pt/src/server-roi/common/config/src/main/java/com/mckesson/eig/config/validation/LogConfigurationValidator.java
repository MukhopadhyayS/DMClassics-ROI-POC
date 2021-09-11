/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.config.validation;

import java.util.ResourceBundle;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.config.constants.ConfigurationConstants;
import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.LongUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Validates source name and logging information before configuration.
 *
 */
public class LogConfigurationValidator {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( LogConfigurationValidator.class);

    /**
     * Initialize the Resource Bundle.
     */
    private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("com/mckesson/eig/config/properties/config");

    /**
     * Validates whether a componentsd is installed or not by retrieving the log
     * configuration file from classpath.
     *
     * @param fileName
     *            relative path of the log configuration file.
     * @return <code>true</code> if the configuration file is present
     *         <code>false</code> otherwise.
     */
    public boolean isComponentInstalled(String fileName) {

        if (StringUtilities.isEmpty(fileName) 
            || ClassLoader.getSystemResourceAsStream(fileName) == null) {
            return false;
        }
        return true;
    }

    /**
     * Validates all the fields in <code>LogConfigurationInfo</code>.
     *
     * @param logConfigurationInfo
     *            logging components to be validated.
     * @return <code>true</code> if valid <code>false</code> otherwise.
     */
    public boolean isValidConfigInfo(LogConfigurationInfo logConfigurationInfo) {
        
       LOG.debug("Validating LogConfigurationInfo Fields");
       
        if (!checkForNull(logConfigurationInfo)) {
            
            boolean result = false;
            String loggingLevel;
            loggingLevel = logConfigurationInfo.getLoggingLevel();
            
            if (loggingLevel.equalsIgnoreCase(BUNDLE.getString("LEVEL_DEBUG"))
                    || loggingLevel.equalsIgnoreCase(BUNDLE.getString("LEVEL_INFO"))
                    || loggingLevel.equalsIgnoreCase(BUNDLE.getString("LEVEL_WARN"))
                    || loggingLevel.equalsIgnoreCase(BUNDLE.getString("LEVEL_ERROR"))) {
                
                if (isValidFileSize(logConfigurationInfo.getMaxLogSize())) {
                    
                    if (LongUtilities.isValidPositiveLong(logConfigurationInfo.getMaxLogFiles())) {
                        result = true;
                    } else {
                        
                        LOG.debug(ConfigurationConstants.INVALID_BACKUP_INDEX);
                        throw new ConfigureLogException(
                                                    ConfigurationConstants.INVALID_BACKUP_INDEX,
                                                    ClientErrorCodes.INVALID_MAX_FILES);
                    }
                }
            } else {
                
                LOG.debug(ConfigurationConstants.INVALID_LOGGING_LEVEL);
                throw new ConfigureLogException(ConfigurationConstants.INVALID_LOGGING_LEVEL,
                                                    ClientErrorCodes.INVALID_LOGGING_LEVEL);
            }
            return result;
        } else {
            
            LOG.debug(ConfigurationConstants.EMPTY_LOG_INFORMATION);
            throw new ConfigureLogException(ConfigurationConstants.EMPTY_LOG_INFORMATION,
                                                ClientErrorCodes.EMPTY_LOG_INFORMATION);
        }
    }

    /**
     * Validates <code>fileSize</code>.Throws
     * <code>LogConfigurationException</code> if its an invalid file size.
     *
     * @param fileSize
     *            filesize to be validated.
     *
     * @return <code>true</code> if its a valid size <code>false</code>
     *         otherwise.
     */
    public boolean isValidFileSize(String fileSize) {
        
        LOG.debug("Validating FileSize in " + this.getClass().getName());
        
        int length = fileSize.length();
        String fileUnit = fileSize.substring((length - 2), length);
        
        if ((fileUnit.equalsIgnoreCase(BUNDLE.getString("FILE_SIZE_KB"))
         || fileUnit.equalsIgnoreCase(BUNDLE.getString("FILE_SIZE_MB"))
         || fileUnit.equalsIgnoreCase(BUNDLE.getString("FILE_SIZE_GB")))
         &&  LongUtilities.isValidPositiveLong(fileSize.substring(0, (length - 2)))) {
            
            return true;
        } else {
            
            LOG.debug(ConfigurationConstants.INVALID_FILE_SIZE);
            throw new ConfigureLogException(ConfigurationConstants.INVALID_FILE_SIZE,
                                                ClientErrorCodes.INVALID_FILE_SIZE);
        }
    }

    /**
     * Checks for empty values in <code>LogConfigurationInfo</code> object.
     *
     * @param logConfigurationInfo
     *            LogConfigurationInfo object
     *
     * @return <code>true</code> if the LogConfigurationInfo object is null
     *         <code>false</code> otherwise.
     */
    private boolean checkForNull(LogConfigurationInfo logConfigurationInfo) {
        
        LOG.debug("Checking for null values in logConfigurationInfo object"); 
                                                
        boolean result = false;
        
        if ((StringUtilities.isEmpty(logConfigurationInfo.getLoggingLevel()))
                || (StringUtilities.isEmpty(logConfigurationInfo.getMaxLogFiles()))
                || (StringUtilities.isEmpty(logConfigurationInfo.getMaxLogSize()))
                || (StringUtilities.isEmpty(logConfigurationInfo.getComponentName()))) {
            
            result = true;
            LOG.debug("Found null field in logConfigurationInfo object");
        }
        return result;
    }
}
