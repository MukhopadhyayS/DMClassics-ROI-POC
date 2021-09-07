/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.audit;

/**
 * This class initialises all constants.
 */
import java.util.ResourceBundle;

/**
 * class represents constants.
 *
 */
public final class ResourceData {

    /**
     * Defines mockejb prperties.
     */
    private static final String MOCKEJB_PROPERTIES = "com/mckesson/eig/audit/mockejb";

    private static final ResourceBundle BUNDLE 
        = ResourceBundle.getBundle(MOCKEJB_PROPERTIES);

    private ResourceData() {
        
    }

    public static String getResourceConfig() {
        return BUNDLE.getString("RESOURCE_CONFIG");
    }

    public static String getLoggingConfig() {
        return BUNDLE.getString("LOGGING_CONFIG");
    }

    public static String getJndiQspResourcer() {
        return BUNDLE.getString("JNDI_QspResourcer");
    }

    public static String getJndiQspWorkManager() {
        return BUNDLE.getString("JNDI_QspWorkManager");
    }

    public static String getJndiQspDatasource() {
        return BUNDLE.getString("JNDI_QspDatasource");
    }

    public static String getDatabaseVendor() {
        return BUNDLE.getString("DATABASE_VENDOR");
    }

    public static String getOraDatabase() {
        return BUNDLE.getString("ORA_DATABASE");
    }

    public static String getOraDatabaseServer() {
        return BUNDLE.getString("ORA_DATABASE_SERVER");
    }

    public static String getOraDatabasePort() {
        return BUNDLE.getString("ORA_DATABASE_PORT");
    }

    public static String getOraDatabaseUser() {
        return BUNDLE.getString("ORA_DATABASE_USER");
    }

    public static String getOraDatabasePassword() {
        return BUNDLE.getString("ORA_DATABASE_PASSWD");
    }

    public static String getOraDatabaseUrl() {
        return BUNDLE.getString("ORA_DATABASE_URL");
    }

    public static String getDatabase() {
        return BUNDLE.getString("DATABASE");
    }

    public static String getDatabaseServer() {
        return BUNDLE.getString("DATABASE_SERVER");
    }

    public static String getDatabaseUser() {
        return BUNDLE.getString("DATABASE_USER");
    }

    public static String getDatabasePassword() {
        return BUNDLE.getString("DATABASE_PASSWORD");
    }
    
    public static String getRoiDatabase() {
        return BUNDLE.getString("ROI_DATABASE_DATABASE");
    }
    
    public static String getRoiDatabaseServer() {
        return BUNDLE.getString("ROI_DATABASE_SERVER");
    }
    
    public static String getRoiDatabaseUser() {
        return BUNDLE.getString("ROI_DATABASE_USER");
    }
    
    public static String getRoiDatabasePassword() {
        return BUNDLE.getString("ROI_DATABASE_PASSWD");
    }
   
    public static String getRoiDatasource() {
        return BUNDLE.getString("JNDI_ROI_DATA_SOURCE");
    }
}
