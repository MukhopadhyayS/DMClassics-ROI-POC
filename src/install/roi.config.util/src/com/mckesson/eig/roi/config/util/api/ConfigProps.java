/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.config.util.api;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author OFS
 * @date   Oct 13, 2009
 * @since  HPF 16.0 [ROI]; Sep 13, 2013
 */
public class ConfigProps {

    private static final Logger LOG = Logger.getLogger(ConfigProps.class);

    private static final String DEFAULT_JBOSS_HOME = "C:\\ROI\\jboss";
    private static final String DEFAULT_JBOSS_SERVER_RUN_BAT_PATH = "\\bin\\run.conf.bat";
    private static final String DEFAULT_OUTPUT_JBOSS_SERVER_RUN_BAT_PATH = "\\bin\\run.conf.bat";

    private static final String DEFAULT_JBOSS_SERVER_SERVER_XML =
        "\\server\\default\\deploy\\jbossweb.sar\\server.xml";

    private static final String DEFAULT_DB_CONNECTION_CONFIG_PATH =
        "\\server\\default\\deploy\\ROI-ds.xml";

    private static final String DEFAULT_HPFW_SMB_CONFIG_FILE_PATH =
    		"\\server\\default\\deploy\\portal.war\\WEB-INF\\classes\\config\\services\\smb.config";

    private static final String DEFAULT_HPFW_DB_CONNECTION_CONFIG_PATH =
    		"\\server\\default\\deploy\\portal.war\\WEB-INF\\classes\\config\\hibernate\\hibernate.properties";

    private static final String DEFAULT_CLICK_ONCE_PATH =
        "\\server\\default\\deploy\\ROOT.war\\ROIClient\\Application Files";

    private static final String DEFAULT_CLICK_ONCE_UPDATE_SETUP_PATH =
        "\\server\\default\\deploy\\ROOT.war\\ROIClient";

    private static final String DEFAULT_CLICK_ONCE_CONFIG_FILE =
        "\\McK.EIG.ROI.Client.App.exe.config";

    private static final String DEFAULT_CLICK_ONCE_CONFIG_VERSION =
        "\\McK.EIG.ROI.Client.App_1_0_0_0";

    private static final String DEFAULT_FAX_SERVICE_HOME = "C:\\ROI\\FaxService";

    private static final String DEFAULT_FAX_SERVICE_CONFIG_PATH =
        "\\McK.EIG.Common.Output.Fax.exe.config";

    private static final String DEFAULT_OUTPUT_AUTHENTICATION_CONFIG_PATH =
        "\\server\\default\\var\\config\\com\\mckesson\\eig\\output\\output-spring-ext.eig.xml";

    private static final String DEFAULT_ROI_AUTHENTICATION_WSDL_URL =
        "\\server\\default\\var\\config\\com\\mckesson\\eig\\roi\\spring.xml";

    private static final String DEFAULT_OUTPUT_SPRING_FILE =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\output\\output-spring.xml";

    private static final String DEFAULT_INUSE_AUTHENTICATION_WSDL_URL =
        "\\server\\default\\var\\config\\com\\mckesson\\eig\\inuse\\spring.xml";

    private static final String  DEFAULT_OUTPUT_JBOSS_HOME = "C:\\ROI\\output.jboss";

    private static final String  DEFAULT_OUTPUT_DEST_TYPES_FILE =
        "\\server\\default\\var\\config\\com\\mckesson\\eig\\output\\endpoint-types.xml";

    private static final String  DEFAULT_OUTPUT_DEST_DEFS_FILE =
        "\\server\\default\\var\\config\\com\\mckesson\\eig\\output\\endpoint-defs.xml";

    private static final String DEFAULT_OUTPUT_DB_CONNECTION_CONFIG_PATH =
        "\\server\\default\\deploy\\eig-db-ds.xml";

    private static final String DEFAULT_ACTIVEMQ_CONFIG_PATH =
        "\\server\\default\\var\\config\\broker-config.xml";

    private static final String DEFAULT_ROI_LOG_PATH =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\roi\\logging.xml";

    private static final String DEFAULT_HPW_LOG_PATH =
    		"\\server\\default\\deploy\\portal.war\\WEB-INF\\classes\\config\\logging\\log.config";

    private static final String DEFAULT_INUSE_LOG_PATH =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\inuse\\logging.xml";

    // CR# - 381,475
    /*private static final String DEFAULT_ROI_AUDIT_LOG_PATH =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\audit\\logging\\audit.logging.xml";*/

    private static final String DEFAULT_ROI_ALERT_LOG_PATH =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\alert\\logging.xml";

    private static final String DEFAULT_OUTPUT_AUDIT_LOG_PATH =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\audit\\logging\\audit.logging.xml";

    private static final String DEFAULT_OUTPUT_LOG_PATH =
    	"\\server\\default\\var\\config\\com\\mckesson\\eig\\output\\output-logging.xml";

    public static final String DEFAULT_ROI_JNDI_NAME = "java:/ROI_DS";
    public static final String DEFAULT_OUTPUT_JNDI_NAME = "java:/Eig-Output-DS";

    private static final String DEFAULT_ROI_SERVER_INSTALLED = "true";
    private static final String DEFAULT_ROI_CLIENT_INSTALLER_INSTALLED = "true";
    private static final String DEFAULT_FAX_SERVER_INSTALLED = "true";
    private static final String DEFAULT_OUTPUT_SERVER_INSTALLED = "true";

    public static final String JBOSS_HOME;
    public static final String JBOSS_SERVER_RUN_BAT_PATH;
    public static final String JBOSS_SERVER_SERVER_XML;
    public static final String DB_CONNECTION_CONFIG_PATH;
    public static final String HPFW_SMB_CONFIG_FILE_PATH;
    public static final String HPFW_DB_CONNECTION_CONFIG_PATH;
    public static final String CLICK_ONCE_PATH;
    public static final String CLICK_ONCE_UPDATE_SETUP_PATH;
    public static final String CLICK_ONCE_CONFIG_FILE;
    public static final String CLICK_ONCE_CONFIG_VERSION;

    public static final String JBOSS_SERVER_RUN_BAT_FILE;
    public static final String JBOSS_SERVER_SERVER_XML_FILE;
    public static final String DB_CONNECTION_CONFIG_FILE;
    public static final String HPFW_SMB_CONFIG_FILE;
    public static final String HPFW_DB_CONNECTION_CONFIG_FILE;
    public static final String CLIENT_CONFIG_FILE;

    public static final boolean DEV_ENVIRONMENT;

    public static final String FAX_SERVICE_HOME;
    public static final String FAX_SERVICE_CONFIG_PATH;
    public static final String FAX_SERVICE_CONFIG_FILE;
    public static final String ROI_AUTHENTICATION_WSDL_URL;
    public static final String INUSE_AUTHENTICATION_WSDL_URL;
    public static final String ROI_AUTHENTICATION_WSDL_URL_FILE;
    public static final String INUSE_AUTHENTICATION_WSDL_URL_FILE;
    public static final String OUTPUT_AUTHENTICATION_CONFIG_PATH;
    public static final String OUTPUT_AUTHENTICATION_CONFIG_FILE;
    public static final String OUTPUT_JBOSS_HOME;
    public static final String OUTPUT_DEST_TYPES_FILE;
    public static final String OUTPUT_DEST_DEFS_FILE;
    public static final String OUTPUT_SPRING_FILE;

    public static final String OUTPUT_JBOSS_SERVER_RUN_BAT_PATH;
    public static final String OUTPUT_JBOSS_SERVER_RUN_BAT_FILE;
    public static final String OUTPUT_JBOSS_SERVER_SERVER_XML_FILE;

    public static final String CONFIG_UTIL_CASTOR =
        "com\\mckesson\\eig\\roi\\config\\util\\config-util-castor.xml";

    public static final String PORT_NO = "PORT_NO";
    public static final String PROTOCOL = "PROTOCOL";
    public static final String MIN_MEMORY = "MIN_MEMORY";
    public static final String MAX_MEMORY = "MAX_MEMORY";
    public static final String PERM_SIZE = "PERM_SIZE";
    public static final String IP_ADDRESS = "IP_ADDRESS";
    public static final String USER_ID = "USER_ID";
    public static final String DB_NAME = "DB_NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String SMB_PASSWORD = "SMB_PASSWORD";
    public static final String JNDI_NAME = "JNDI_NAME";
    
    // Added to show the default value in the output text
    public static final String CLIENT_ROI_DEFAULT_PORT_NO = "443";
    public static final String CLIENT_OUTPUT_DEFAULT_PORT_NO = "443";
    public static final String ROI_DEFAULT_MAX_MEMORY = "1024";
    public static final String OUTPUT_DEFAULT_MAX_MEMORY = "5120";    
    public static final String CLIENT_ROI_PORT_NO;
    public static final String CLIENT_OUTPUT_PORT_NO;
    public static final String ROI_MAX_MEMORY;
    public static final String OUTPUT_MAX_MEMORY;    

    public static final String FAX_SERVER_NAME = "FAX_SERVER_NAME";
    public static final String FAX_QUEUE_NAME = "FAX_QUEUE_NAME";
    public static final String FAX_PASSWORD = "FAX_PASSWORD";
    public static final String FAX_SERVER_TYPE = "FAX_SERVER_TYPE";
    public static final String RIGHT_FAX = "RightFax";
    public static final String SMB_USER_ID = "SMB_USER_ID";
    public static final String ZETA_FAX = "ZetaFax";


    public static final String OUTPUT_SERVER = "OUTPUT_SERVER";

    public static final String ROI_JNDI_NAME;
    public static final String OUTPUT_JNDI_NAME;

    public static final String LOOK_AND_FEEL;
    private static final String DEFAULT_LOOK_AND_FEEL =
        "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    public static final int VERTICAL_GAP = 60;

    public static final int RED_VALUE = 236;
    public static final int GREEN_VALUE = 233;
    public static final int BLUE_VALUE = 216;

    public static final int PANEL_WIDTH = 690;
    public static final int PANEL_HEIGTH = 445;

    public static final int TITLE_GREEN_VALUE = 85;
    public static final int TITLE_BLUE_VALUE = 234;
    public static final int TITLE_SIZE = 15;

    public static final int TEXT_SIZE = 14;

    public static final int FIELD_WIDTH = 20;
    public static final int FIELD_HEIGHT = 25;

    public static final int TOP_LEFT_MARGIN = 70;
    public static final int TOP_MARGIN = 3;
    public static final int LEFT_MARGIN = 20;
    public static final int COMMON_MARGIN = 5;

    public static final int BOTTOM_RIGHT_MARGIN = 50;

    public static final int COMBO_BOX_WIDTH = 130;
    public static final int COMBO_BOX_HEIGHT = 25;
    public static final int SAVE_BUTTON_WIDTH = 130;

    public static final int IP_POSITION = 7;
    public static final int HTTPS_IP_POSITION = 8;

    public static final int GRID_BAG_CONSTRAINTS = 7;

    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 30;

    public static final String UPDATE_SETUP_BAT = "\\UpdateSetUp.bat";

    public static final boolean ROI_SERVER_INSTALLED;
    public static final boolean ROI_CLIENT_INSTALLER_INSTALLED;
    public static final boolean FAX_SERVER_INSTALLED;
    public static final boolean OUTPUT_SERVER_INSTALLED;

    public static final String OUTPUT_DB_CONNECTION_CONFIG_PATH;
    public static final String OUTPUT_DB_CONNECTION_CONFIG_FILE;
    public static final String OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE;
    public static final String OUTPUT_HPFW_SMB_CONFIG_FILE;

    public static final String ACTIVEMQ_CONFIG_PATH;
    public static final String ACTIVEMQ_CONFIG_FILE;
    public static final String OUTPUT_ACTIVEMQ_CONFIG_FILE;

    public static final String NUMERIC_REGEX = "\\d*";

    public static final String PORTNUM = "port.no";
    public static final String MINMEMORY = "min.memory";
    public static final String MAXMEMORY = "max.memory";
    public static final String PERMSIZE = "perm.size";
    // CR # 378,236 - Change Default values from MFPData, MPFWEB to HPFData and HPFWEB
    public static final String DEFAULT_DB_SERVER_NAME = "HPFDATA";
    //CR 381,988 fix
    public static final String DEFAULT_SERVER_NAME = "MPFROI";
    public static final String DEFAULT_DATA = "HPFDATA";
    public static final String DEFAULT_MPFW_DB_NAME = "cabinet";
    public static final String DEFAULT_MPFW_DB_USER_NAME = "iws";
    public static final String DEFAULT_MPFW_DB_PASSWORD = "oops";

    public static final String SERVER_URL;
    private static final String DEFAULT_SERVER_URL = "127.0.0.1";
    private static String strLine;

    public static final String ROI_LOG_PATH;
    public static final String ROI_HPFW_LOG_PATH;
    public static final String OUTPUT_HPFW_LOG_PATH;
    public static final String ROI_INUSE_LOG_PATH;
    // CR# - 381,475
//    public static final String ROI_AUDIT_LOG_PATH;
    public static final String ROI_ALERT_LOG_PATH;
    public static final String OUTPUT_AUDIT_LOG_PATH;
    public static final String OUTPUT_LOG_PATH;

	public static final String ROI_JNDI = "ROI_JNDI";
	public static final String OUTPUT_JNDI = "OUTPUT_JNDI";
	public static final String OUTPUT_DB_PASSWORD = "OUTPUT_DB_PASSWORD";
	public static final String OUTPUT_DB_USER_ID = "OUTPUT_DB_USER_ID";

	// MPFW Configurations
	public static final String DEFAULT_MPFW_LOGGING_PRODUCTION_PATH =
			"\\server\\default\\deploy\\portal.war\\WEB-INF\\classes\\config\\logging\\log4j-production.properties";

	public static final String DEFAULT_MPFW_LOGGING_STAGING_PATH =
			"\\server\\default\\deploy\\portal.war\\WEB-INF\\classes\\config\\logging\\log4j-staging.properties";

	public static final String DEFAULT_MPFW_WEB_XML_PATH =
			"\\server\\default\\deploy\\portal.war\\WEB-INF\\web.xml";

	public static final String DEFAULT_MPFW_ENCRYPTION_CONFIG_PATH =
			"\\server\\default\\deploy\\portal.war\\WEB-INF\\classes\\config\\services\\encryption.config";

	public static final String DEFAULT_MPFW_ENCRYPTION_CLASS = "com.mckesson.eig.common.password.PasswordMD5";

	public static final String DEFAULT_MPFW_LOG_FILE_LOCATION = "\\server\\default\\log\\MPFWLog\\mpfw.log";

	public static final String MPFW_LOGGING_PRODUCTION_PATH;
	public static final String MPFW_LOGGING_STAGING_PATH;
	public static final String MPFW_WEB_XML_PATH;
	public static final String MPFW_ENCRYPTION_CONFIG_PATH;
	public static final String MPFW_LOG_FILE_LOCATION;
	public static final String MPFW_ENCRYPTION_CLASS;
	public static final String DEFAULT_ROI_MPFW_APPLICATION_NAME = "MPFW_IN_ROI";
	public static final String ROI_MPFW_APPLICATION_NAME;
	public static final String DEFAULT_OUTPUT_MPFW_APPLICATION_NAME = "MPFW_IN_OPSVC";
	public static final String OUTPUT_MPFW_APPLICATION_NAME;
	public static final String DEFAULT_MPFW_LOGGING_GLOBAL_NAME_FORMAT = "{0}:{1}";
	public static final String MPFW_LOGGING_GLOBAL_NAME_FORMAT;
	public static final String SERVER_LOGIN_CONFIG_FILE;
	public static final String DEFAULT_SERVER_LOGIN_CONFIG_FILE = "\\server\\default\\conf\\login-config.xml";
	public static final String SERVER_EIG_OUTPUT_SETTINGS_FILE;
	public static final String DEFAULT_SERVER_EIG_OUTPUT_SETTINGS_FILE = "\\server\\default\\var\\config\\com\\mckesson\\eig\\output\\output-settings.properties";

    static {

        try {

            URL resource = ConfigProps.class
                           		.getResource("/config.util.properties");

            FileInputStream fis = new FileInputStream(resource.toURI().getPath());
            DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			HashMap<String, String> _maps = new HashMap<String, String>();
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {

				if (strLine.contains("=") && !(strLine.charAt(0)== '#')) {
					_maps.put(strLine.substring(0, strLine.indexOf("=")), strLine.substring(strLine.indexOf("=")));
				}
			}

			JBOSS_HOME = checkNull(_maps.get("jboss.home"), DEFAULT_JBOSS_HOME);

			JBOSS_SERVER_RUN_BAT_PATH = checkNull(_maps.get("jboss.server.run.bat.path"), DEFAULT_JBOSS_SERVER_RUN_BAT_PATH);

			JBOSS_SERVER_SERVER_XML  =  checkNull(_maps.get("jboss.server.server.xml.path"),DEFAULT_JBOSS_SERVER_SERVER_XML);

            DB_CONNECTION_CONFIG_PATH = checkNull(_maps.get("db.connection.config.path"), DEFAULT_DB_CONNECTION_CONFIG_PATH);

            HPFW_SMB_CONFIG_FILE_PATH = checkNull(_maps.get("smb.connection.config.path"), DEFAULT_HPFW_SMB_CONFIG_FILE_PATH);

            HPFW_DB_CONNECTION_CONFIG_PATH = checkNull(_maps.get("hpfw.connection.config.path"), DEFAULT_HPFW_DB_CONNECTION_CONFIG_PATH);

            CLICK_ONCE_PATH = checkNull(_maps.get("click.once.path"), DEFAULT_CLICK_ONCE_PATH);

            CLICK_ONCE_UPDATE_SETUP_PATH = checkNull(_maps.get("click.once.update.setup.path"), DEFAULT_CLICK_ONCE_UPDATE_SETUP_PATH);

            CLICK_ONCE_CONFIG_FILE = checkNull(_maps.get("client.config.file"), DEFAULT_CLICK_ONCE_CONFIG_FILE);

            CLICK_ONCE_CONFIG_VERSION = checkNull(_maps.get("client.config.version"), DEFAULT_CLICK_ONCE_CONFIG_VERSION);

            JBOSS_SERVER_RUN_BAT_FILE = JBOSS_HOME + JBOSS_SERVER_RUN_BAT_PATH;

            JBOSS_SERVER_SERVER_XML_FILE = JBOSS_HOME + JBOSS_SERVER_SERVER_XML;

            OUTPUT_JBOSS_HOME = checkNull(_maps.get("output.jboss.home"), DEFAULT_OUTPUT_JBOSS_HOME);

            OUTPUT_JBOSS_SERVER_RUN_BAT_PATH = checkNull(_maps.get("output.jboss.server.run.bat.path"), DEFAULT_OUTPUT_JBOSS_SERVER_RUN_BAT_PATH);

            OUTPUT_JBOSS_SERVER_RUN_BAT_FILE = OUTPUT_JBOSS_HOME + OUTPUT_JBOSS_SERVER_RUN_BAT_PATH;

            OUTPUT_JBOSS_SERVER_SERVER_XML_FILE = OUTPUT_JBOSS_HOME + JBOSS_SERVER_SERVER_XML;

            OUTPUT_SPRING_FILE = OUTPUT_JBOSS_HOME + checkNull(_maps.get("output.spring.config.path"), DEFAULT_OUTPUT_SPRING_FILE);

            DB_CONNECTION_CONFIG_FILE = JBOSS_HOME + DB_CONNECTION_CONFIG_PATH;

            HPFW_SMB_CONFIG_FILE = JBOSS_HOME + HPFW_SMB_CONFIG_FILE_PATH;

            HPFW_DB_CONNECTION_CONFIG_FILE = JBOSS_HOME + DEFAULT_HPFW_DB_CONNECTION_CONFIG_PATH;

            LOOK_AND_FEEL = checkNull(_maps.get("look.and.feel"), DEFAULT_LOOK_AND_FEEL);


            DEV_ENVIRONMENT = Boolean.parseBoolean(checkNull(_maps.get("dev.environment"), "false"));

            FAX_SERVICE_HOME = checkNull(_maps.get("fax.service.home"), DEFAULT_FAX_SERVICE_HOME);

            FAX_SERVICE_CONFIG_PATH = checkNull(_maps.get("fax.service.config.path"), DEFAULT_FAX_SERVICE_CONFIG_PATH);

            FAX_SERVICE_CONFIG_FILE = FAX_SERVICE_HOME + FAX_SERVICE_CONFIG_PATH;

            OUTPUT_AUTHENTICATION_CONFIG_PATH =
            	checkNull(_maps.get("output.authuntication.config.path"), DEFAULT_OUTPUT_AUTHENTICATION_CONFIG_PATH);

            ROI_AUTHENTICATION_WSDL_URL = checkNull(_maps.get("roi.authentication.wsdl.url"), DEFAULT_ROI_AUTHENTICATION_WSDL_URL);

            ROI_AUTHENTICATION_WSDL_URL_FILE = JBOSS_HOME
                                            + ROI_AUTHENTICATION_WSDL_URL;

            INUSE_AUTHENTICATION_WSDL_URL =
                checkNull(_maps.get("inuse.authentication.wsdl.url"), DEFAULT_INUSE_AUTHENTICATION_WSDL_URL);

            INUSE_AUTHENTICATION_WSDL_URL_FILE = JBOSS_HOME
                                            + INUSE_AUTHENTICATION_WSDL_URL;

            OUTPUT_AUTHENTICATION_CONFIG_FILE = OUTPUT_JBOSS_HOME
                                                + OUTPUT_AUTHENTICATION_CONFIG_PATH;

            OUTPUT_DEST_TYPES_FILE =
                OUTPUT_JBOSS_HOME + checkNull(_maps.get("output.end.point.type.path"), DEFAULT_OUTPUT_DEST_TYPES_FILE);

            OUTPUT_DEST_DEFS_FILE =
                OUTPUT_JBOSS_HOME + checkNull(_maps.get("output.end.point.defs.path"), DEFAULT_OUTPUT_DEST_DEFS_FILE);

            ROI_SERVER_INSTALLED = Boolean.parseBoolean(checkNull(_maps.get("roi.server.installed"), DEFAULT_ROI_SERVER_INSTALLED));

            ROI_CLIENT_INSTALLER_INSTALLED = Boolean.parseBoolean(checkNull(_maps.get("roi.client.installer.installed"), DEFAULT_ROI_CLIENT_INSTALLER_INSTALLED));

            FAX_SERVER_INSTALLED = Boolean.parseBoolean(checkNull(_maps.get("fax.server.installed"), DEFAULT_FAX_SERVER_INSTALLED));

            OUTPUT_SERVER_INSTALLED = Boolean.parseBoolean(checkNull(_maps.get("output.server.installed"), DEFAULT_OUTPUT_SERVER_INSTALLED));

            OUTPUT_DB_CONNECTION_CONFIG_PATH = checkNull(_maps.get("output.db.connection.config.path"), DEFAULT_OUTPUT_DB_CONNECTION_CONFIG_PATH);

            OUTPUT_DB_CONNECTION_CONFIG_FILE = OUTPUT_JBOSS_HOME + OUTPUT_DB_CONNECTION_CONFIG_PATH;

            OUTPUT_HPFW_DB_CONNECTION_CONFIG_FILE = OUTPUT_JBOSS_HOME + DEFAULT_HPFW_DB_CONNECTION_CONFIG_PATH;

            OUTPUT_HPFW_SMB_CONFIG_FILE = OUTPUT_JBOSS_HOME + DEFAULT_HPFW_SMB_CONFIG_FILE_PATH;

            ACTIVEMQ_CONFIG_PATH = checkNull(_maps.get("activeMQ.config.path"), DEFAULT_ACTIVEMQ_CONFIG_PATH);

            ACTIVEMQ_CONFIG_FILE = JBOSS_HOME + ACTIVEMQ_CONFIG_PATH;

            OUTPUT_ACTIVEMQ_CONFIG_FILE = OUTPUT_JBOSS_HOME + ACTIVEMQ_CONFIG_PATH;

            SERVER_URL = checkNull(_maps.get("server.url"), DEFAULT_SERVER_URL);

            ROI_LOG_PATH = JBOSS_HOME + checkNull(_maps.get("roi.log.path"), DEFAULT_ROI_LOG_PATH);

            ROI_HPFW_LOG_PATH = JBOSS_HOME + checkNull(_maps.get("hpfw.logging.config.path"), DEFAULT_HPW_LOG_PATH);

            OUTPUT_HPFW_LOG_PATH = OUTPUT_JBOSS_HOME + checkNull(_maps.get("hpfw.logging.config.path"), DEFAULT_HPW_LOG_PATH);

            ROI_INUSE_LOG_PATH = JBOSS_HOME + checkNull(_maps.get("roi.inuse.log.path"), DEFAULT_INUSE_LOG_PATH);

            // CR# - 381,475
//            ROI_AUDIT_LOG_PATH = JBOSS_HOME + checkNull(_maps.get("roi.audit.log.path"), DEFAULT_ROI_AUDIT_LOG_PATH);

            ROI_ALERT_LOG_PATH = JBOSS_HOME + checkNull(_maps.get("roi.alert.log.path"), DEFAULT_ROI_ALERT_LOG_PATH);

            OUTPUT_AUDIT_LOG_PATH = OUTPUT_JBOSS_HOME + checkNull(_maps.get("output.audit.log.path"), DEFAULT_OUTPUT_AUDIT_LOG_PATH);

            OUTPUT_LOG_PATH = OUTPUT_JBOSS_HOME + checkNull(_maps.get("output.log.path"), DEFAULT_OUTPUT_LOG_PATH);

            ROI_JNDI_NAME = checkNull(_maps.get("roi.jndi.name"), DEFAULT_ROI_JNDI_NAME);

            OUTPUT_JNDI_NAME = checkNull(_maps.get("output.jndi.name"), DEFAULT_OUTPUT_JNDI_NAME);

            MPFW_LOGGING_PRODUCTION_PATH = checkNull(_maps.get("mpfw.logging.production.path"), DEFAULT_MPFW_LOGGING_PRODUCTION_PATH);

            MPFW_LOGGING_STAGING_PATH = checkNull(_maps.get("mpfw.logging.staging.path"), DEFAULT_MPFW_LOGGING_STAGING_PATH);

            MPFW_WEB_XML_PATH = checkNull(_maps.get("mpfw.web.xml.path"), DEFAULT_MPFW_WEB_XML_PATH);

            MPFW_ENCRYPTION_CONFIG_PATH = checkNull(_maps.get("mpfw.encryption.config.path"), DEFAULT_MPFW_ENCRYPTION_CONFIG_PATH);

            MPFW_LOG_FILE_LOCATION = checkNull(_maps.get("mpfw.log.file.location"), DEFAULT_MPFW_LOG_FILE_LOCATION);

            MPFW_ENCRYPTION_CLASS = checkNull(_maps.get("mpfw.encryption.class"), DEFAULT_MPFW_ENCRYPTION_CLASS);

            ROI_MPFW_APPLICATION_NAME = checkNull(_maps.get("roi.mpfw.application.name"), DEFAULT_ROI_MPFW_APPLICATION_NAME);

            OUTPUT_MPFW_APPLICATION_NAME = checkNull(_maps.get("output.mpfw.application.name"), DEFAULT_OUTPUT_MPFW_APPLICATION_NAME);

            MPFW_LOGGING_GLOBAL_NAME_FORMAT = checkNull(_maps.get("logging.global.name.format"), DEFAULT_MPFW_LOGGING_GLOBAL_NAME_FORMAT);

            SERVER_LOGIN_CONFIG_FILE = checkNull(_maps.get("server.login.conf.file"), DEFAULT_SERVER_LOGIN_CONFIG_FILE);

            SERVER_EIG_OUTPUT_SETTINGS_FILE = checkNull(_maps.get("server.eig.outputsettings.file"), DEFAULT_SERVER_EIG_OUTPUT_SETTINGS_FILE);
            
            ROI_MAX_MEMORY = checkNull(_maps.get("roi.max.memory"), ROI_DEFAULT_MAX_MEMORY);
            OUTPUT_MAX_MEMORY = checkNull(_maps.get("output.max.memory"), OUTPUT_DEFAULT_MAX_MEMORY);
            CLIENT_ROI_PORT_NO = checkNull(_maps.get("client.roi.default.port"), CLIENT_ROI_DEFAULT_PORT_NO);
            CLIENT_OUTPUT_PORT_NO = checkNull(_maps.get("client.output.default.port"), CLIENT_OUTPUT_DEFAULT_PORT_NO);

            if (!DEV_ENVIRONMENT) {

                CLIENT_CONFIG_FILE = JBOSS_HOME
                                     + CLICK_ONCE_PATH
                                     + CLICK_ONCE_CONFIG_VERSION
                                     + CLICK_ONCE_CONFIG_FILE;
            } else {
                CLIENT_CONFIG_FILE = CLICK_ONCE_CONFIG_VERSION + CLICK_ONCE_CONFIG_FILE;
            }


        } catch (Exception e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.load.config.util.properties"));
        }
    }

	private static String checkNull(String property, String defaultValue) {

		if (null == property) {
			return defaultValue;
		} else {
			return property.substring(1);
		}
	}
 }
