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

package com.mckesson.eig.config.constants;

/**
 * This class holds the constants corresponding to List View Log and
 * Log Configuration modules.
 */
public class ConfigurationConstants {

    /**
     * Member Variable.
     */
    public static final String OFFSET = "OFFSET";
    /**
     * Member Variable.
     */
    public static final String COMPONENT_SEQ = "componentSeq";
    /**
     * Member Variable.
     */
    public static final String FILE_NAME = "fileName";
    /**
     * Member Variable.
     */
    public static final String BLOCK_SIZE = "BLOCKSIZE";
    
    /**
     * Audit entry <code>Comment</code> that needs to be persisted in the
     * database to configure log files.
     */
    public static final String AUDIT_COMMENT = "Logging information configured sucessfully";

    /**
     * Variable holding Log4j attribute appender.
     */
    public static final String APPENDER = "appender";

    /**
     * Variable holding Log4j attribute category.
     */
    public static final String CATEGORY = "category";

    /**
     * Variable holding Log4j attribute MaxFileSize.
     */
    public static final String MAX_FILE_SIZE = "MaxFileSize";

    /**
     * Variable holding Log4j attribute LogFileName.
     */
    public static final String LOG_FILE_NAME = "File";

    /**
     * Variable holding Log4j attribute MaxBackupIndex.
     */
    public static final String MAX_BCKUP = "MaxBackupIndex";

    /**
     * Variable holding Log4j attribute priority.
     */
    public static final String PRIORITY = "priority";

    /**
     * Variable holding attribute name.
     */
    public static final String NAME = "name";

    /**
     * Variable holding attribute value.
     */
    public static final String VALUE = "value";
    
    /**
     * Variable holding security exception message.
     */
    public static final String SECURITY_EXCEPTION = 
        "User do not have enough security to access the file";
    /**
     * Variable holding invalid file name message.
     */
    public static final String INVALID_FILE_NAME = "Invalid log file name";
    /**
     * Variable holding invalid file name message.
     */
    public static final String INVALID_OFFSET = "Invalid offset";
    /**
     * Variable holding invalid file name message.
     */
    public static final String INVALID_BLOCK_SIZE = "Invalid Block Size";
    /**
     * Variable holding invalid file name message.
     */
    public static final String INVALID_FILE_SIZE = "Not an valid file size numeral";
    /**
     * Variable holding invalid component sequence message.
     */
    public static final String INVALID_COMPONENT_SEQ = "Invalid component sequence.";
    /**
     * Variable holding invalid file type message.
     */
    public static final String INVALID_FILE_TYPE = "Invalid file type.";
    /**
     * Variable holding no components found exception message.
     */
    public static final String NIL_COMPONENT_INSTALLED = "No components found";
    /**
     * Variable holding exception while retrieving server component log list message.
     */
    public static final String ERROR_RETRIEVING_COMPONENTS = "Exception occured while "
        + "retrieving the Server Component Log List";
    /**
     * Variable holding exception while retrieving log file path message.
     */
    public static final String ERROR_RETRIEVING_PATH = "Invalid log file path";
    /**
     * Variable holding exception message while processing the request.
     */
    public static final String ERROR_PROCESSING_REQUEST = "Error occured while processing request ";
    /**
     * Variable holding List View Log Exception message.
     */
    public static final String LIST_VIEW_LOG_EXCEPTION = "ListViewLog Exception";
    
    /**
     * Variable holding save log configuration exception message  
     */
    public static final String SAVE_CONFIGURATION_EXCEPTION =
        "Unable to save the configuration of logger file. Check the log file for more details.";
    
    /**
     * Variable holding LogConfiguration Exception message for any mismatch
     * between spring config and log4j files.
     */
    public static final String LOG_MESSAGE = 
        "Mismatch between the appender and category list in spring config file and Log4j xml ";
    
    /**
     * Variable holding LogConfiguration Exception message for any mismatch
     * between spring config and log4j files.
     */
    public static final String RETRIEVE_EXCEPTION =  
        "Unable to retrieve Log4J contents in the configuration file";
    
    /**
     * Variable holding LogConfiguration Exception message for any APPENDER mismatch
     * between spring config and log4j files.
     */
    public static final String INVALID_APPENDER_LOGGING = "Invalid appender logging";
    
    /**
     * Variable holding LogConfiguration Exception message for any CATEGORY mismatch
     * between spring config and log4j files.
     */
    public static final String INVALID_CATEGORY_LOGGING = "Invalid category logging";
    
    /**
     * Variable holding LogConfiguration Exception message for any mismatch
     * between spring config and log4j files.
     */
    public static final String UNABLE_RETRIEVE_INFO_EXCEPTION = 
        "Unable to retrieve component information from server.Check log file for more details";

    /**
     * Variable holding Exception Message for invalid Log4J elements
     */
    public static final String LOG4J_MESSAGE =
        "Log4J Element not defined properly in the configuration file.";
    
    /**
     * Variable holding Exception Message for empty log information elements
     */
    public static final String EMPTY_LOG_INFORMATION = 
        "Log Information cannot be empty.All fields are mandatory.";

    /**
     * Variable holding Exception Message for invalid logging level elements
     */
    public static final String INVALID_LOGGING_LEVEL = "Invalid logging level specified";
    
    /**
     * Variable holding Exception Message for invalid Log4J elements
     */
    public static final String INVALID_BACKUP_INDEX = "Invalid BackUp Index";
    
    /**
     * Variable holding Exception Message for invalid file path
     */
    public static final String INVALID_FILE_PATH = 
        "Unable to find the file for the given LogFilePath";
    
    /**
     * Variable holding Exception Message for no components installed in this machine
     */
    public static final String NIL_COMPONENETS_INSTALLED =
        "could not find any component installed in this machine";
                                
    /**
     * Variable holding Exception Message for invalid component name.
     */
    public static final String INVALID_COMPONENT_NAME = "Not an valid component name";
}


