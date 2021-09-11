/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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

import java.io.IOException;
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mckesson.eig.config.constants.ConfigurationEC;
import com.mckesson.eig.config.model.ApplicationSetting;
import com.mckesson.eig.config.service.ApplicationSettingServiceImpl;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.utility.util.XMLUtilities;
import com.mckesson.eig.utility.util.net.MailValidationUtilities;

/**
 * Validator class for ApplicationSetting related services
 *
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 */
public final class ApplicationSettingValidator extends BaseValidator {

    private ApplicationSettingValidator() {
    }

    private static final String PROPERTY_PATH         = "application-settings/item/property";
    private static final String EMAIL_SERVER_SETTING  = "email-server-setting";
    private static final String APPLN_SETTING_SCHEMA  =
                                        "/com/mckesson/eig/config/xsd/applicationsetting.xsd";
    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ApplicationSettingValidator.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final XPath XPATH = XPathFactory.newInstance().newXPath();

    private static final int MAX_EMAIL_LENGTH = 320;

    /**
     *  Method to test whether the applnsetting passed is valid to application setting schema xsd.
     *
     *  @param settingXML
     *
     *  @return
     */
    private static BaseValidator isValidToSchema(String settingXML,
                                                 BaseValidator baseValidator)
    throws Exception {

        try {

            Document doc = XMLUtilities.parse(settingXML, true);

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile     = new StreamSource(
                    ApplicationSettingValidator.class.getResourceAsStream(APPLN_SETTING_SCHEMA));
            javax.xml.validation.Schema schema = factory.newSchema(schemaFile);
            Validator validator   = schema.newValidator();
            validator.validate(new DOMSource(doc));
        } catch (SAXException e) {

            baseValidator.addError(ConfigurationEC.EC_INVALID_XML_DOCUMENT,
                                   ConfigurationEC.MSG_INVALID_XML_DOCUMENT);
        }
        return baseValidator;
    }

    /**
     * Method to validate whether the passed in globalConfig is valid
     * @param globalConfig
     * @return
     */
    public static void validateGlobalSetting(ApplicationSetting globalSetting)
    throws Exception {

        if (DO_DEBUG) {
            LOG.debug("Validating ApplicationSetting Fields");
        }

        BaseValidator validator = new ApplicationSettingValidator();

        if (globalSetting == null) {

            validator.addError(ConfigurationEC.EC_NULL_GLOBAL_SETTING,
                               ConfigurationEC.MSG_NULL_GLOBAL_SETTING);
        } else {

            validator = isValidToSchema(globalSetting.getSettings(), validator);
            HashMap<String, String> settings =
                ApplicationSettingServiceImpl.getItemValues(globalSetting.getSettings(),
                                                            EMAIL_SERVER_SETTING, PROPERTY_PATH);

            if (StringUtilities.isEmpty(settings.get("host-address"))) {

                validator.addError(ConfigurationEC.EC_NULL_HOSTNAME,
                                   ConfigurationEC.MSG_NULL_HOSTNAME);
            }

            if (!MailValidationUtilities.isValidMailServerPort(
                                        Integer.parseInt(settings.get("port-number")))) {

                validator.addError(ConfigurationEC.EC_INVALID_PORT_NUMBER,
                                   ConfigurationEC.MSG_INVALID_PORT_NUMBER);
            }
        }

        if (validator.errorsExist()) {
            throw validator.createException();
        }
    }

    /**
     * Method to validate whether the passed in ApplicationSetting is valid
     * @param applnSetting
     * @return
     */
    public static void validateApplicationSetting(ApplicationSetting applnSetting)
    throws Exception {

        if (DO_DEBUG) {
            LOG.debug("Validating ApplicationSetting Fields");
        }

        BaseValidator validator = new BaseValidator();
        if (applnSetting == null) {

            validator.addError(ConfigurationEC.EC_NULL_APPLICATION_SETTING,
                              ConfigurationEC.MSG_NULL_APPLICATION_SETTING);
            throw validator.createException();
        }

        if (StringUtilities.isEmpty(applnSetting.getAppName())) {

            validator.addError(ConfigurationEC.EC_NULL_APPLICATION_NAME ,
                               ConfigurationEC.MSG_NULL_APPLICATION_NAME);
        }

        if (applnSetting.getSettings() == null) {

            validator.addError(ConfigurationEC.EC_NULL_APPLICATION_SETTING_DATA,
                               ConfigurationEC.MSG_NULL_APPLICATION_SETTING_DATA);
        } else if (StringUtilities.isEmpty(applnSetting.getSettings())) {

            validator.addError(ConfigurationEC.EC_INVALID_APPLICATION_SETTING_DATA,
                               ConfigurationEC.MSG_INVALID_APPLICATION_SETTING_DATA);
        } else {

            validator = isValidToSchema(applnSetting.getSettings(), validator);
            validateSettings(validator, applnSetting.getSettings());
        }


        if (validator.errorsExist()) {
            throw validator.createException();
        }
    }

    /**
     * Helper method used to validate the application settings which is in XML Format
     * 
     * @param validator
     * @param settings
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws XPathExpressionException
     */
    private static void validateSettings(BaseValidator validator, String settings)
    throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {

        Document doc = XMLUtilities.parse(settings, false);

        String senderEmail = 
          XPATH.evaluate("/application-settings/item/property[@name='sender-email']/@value", doc);

        String replyToEmail = 
          XPATH.evaluate("/application-settings/item/property[@name='reply-to-email']/@value", doc);

        if (senderEmail.length() > MAX_EMAIL_LENGTH || replyToEmail.length() > MAX_EMAIL_LENGTH) {
            validator.addError(ConfigurationEC.EC_EMAIL_EXCEEDS_MAX_LENGTH, 
                               ConfigurationEC.MSG_EMAIL_EXCEEDS_MAX_LENGTH);
        }
    }
}
