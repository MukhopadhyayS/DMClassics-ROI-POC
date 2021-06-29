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

package com.mckesson.eig.config.service;


/**
 * @author OFS
 * @date   Apr 19, 2009
 * @since  HECM 2.0; Apr 19, 2009
 */
public class ApplicationSettingsData {

    public String getGlobalSettings() {
        
        return "<?xml version=\"1.0\"?>"
        + "<application-settings name=\"GLOBAL\" xmlns=\"http://eig.mckesson.com/xsd/2009/01\">"
        + "<item type=\"email-server-setting\">"
        + "<property name=\"host-address\" value=\"ims1.mckesson.com\" />"
        + "<property name=\"port-number\" value=\"25\" />"
        + "</item>"
        + "<item type=\"email-address-setting\">"
        + "<property name=\"sender-name\" value=\"Admin\" />"
        + "<property name=\"sender-email\" value=\"admin@mckesson.com\" />"
        + "<property name=\"reply-to-name\" value=\"Admin\" />"
        + "<property name=\"reply-to-email\" value=\"admin@mckesson.com\" />"
        + "</item>"
        + "</application-settings>";
    }
    
    public String getApplicationSettings() {
        
        return "<?xml version=\"1.0\"?>"
        + "<application-settings name=\"HECM\" xmlns=\"http://eig.mckesson.com/xsd/2009/01\">"
        + "<item type=\"email-server-setting\">"
        + "<property name=\"host-address\" value=\"ims1.mckesson.com\" />"
        + "<property name=\"port-number\" value=\"25\" />"
        + "</item>"
        + "<item type=\"email-address-setting\">"
        + "<property name=\"sender-name\" value=\"Admin\" />"
        + "<property name=\"sender-email\" value=\"admin@mckesson.com\" />"
        + "<property name=\"reply-to-name\" value=\"Admin\" />"
        + "<property name=\"reply-to-email\" value=\"admin@mckesson.com\" />"
        + "</item>"
        + "</application-settings>";
    }
    
}
