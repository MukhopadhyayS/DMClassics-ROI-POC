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

package com.mckesson.eig.roi.admin.service;

import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author OFS
 * @date   Feb 24, 2010
 * @since  HPF 15.1 [ROI];
 */
public class TestOutputIntegratiionServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE  =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;

    private final long _validPort = 8080;
    private final long _invalidPort = 80808080;

    @Override
    public void initializeTestData()
    throws Exception {

        setUp();
        _adminService  = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    public void testDisableOutputService() {
        _adminService.enableOutputService(false);
    }

    public void testEnableOutputService() {
        _adminService.enableOutputService(true);
    }

    public void testRetrieveOutputServerProperties() {

        OutputServerProperties properties =  _adminService.retrieveOutputServerProperties();
        assertNotNull(properties);
    }

    /**
     * This test case is to save the output server properties with valid data for create
     */
    public void testSaveOutputServerPropertiesForCreate() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setHostName("192.168.6.151");
        properties.setPort(_validPort);
        properties.setProtocol("http");
        _adminService.saveOutputServerProperties(properties);

        OutputServerProperties opProps =  _adminService.retrieveOutputServerProperties();

        assertEquals(_validPort, opProps.getPort());
        assertEquals("192.168.6.151", opProps.getHostName());
        assertEquals("http", opProps.getProtocol());
    }

    /**
     * This test case is to save the output server properties with invalid port length
     */
    public void testSaveOutputServerPropertiesWithInvalidPortLength() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setHostName("192.168.6.151");
        properties.setPort(_invalidPort);
        properties.setProtocol("http");

        try {
            _adminService.saveOutputServerProperties(properties);
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.OUTPUT_SERVER_PORT_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is to save the output server properties with invalid host name
     */
    public void testSaveOutputServerPropertiesWithInvalidHostName() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setPort(_validPort);
        properties.setHostName("eigDe$@0--193..");
        properties.setProtocol("http");

        try {
            _adminService.saveOutputServerProperties(properties);
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.OUTPUT_SERVER_INVALID_HOSTNAME);
        }
    }

    /**
     * This test case is to save the output server properties with invalid host name length
     */
    public void testSaveOutputServerPropertiesWithInvalidHostNameLength() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setPort(_validPort);
        properties.setHostName(appendString("eigDev100"));
        properties.setProtocol("http");

        try {
            _adminService.saveOutputServerProperties(properties);
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.OUTPUT_SERVER_HOSTNAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is to save the output server properties without host name
     */
    public void testSaveOutputServerPropertiesWithOutHostname() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setPort(_validPort);
        properties.setProtocol("https");

        try {
            _adminService.saveOutputServerProperties(properties);
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.OUTPUT_SERVER_HOSTNAME_EMPTY);
        }
    }

    /**
     * This test case is to save the output server properties with invalid protocol
     */
    public void testSaveOutputServerPropertiesWithInvalidProtocol() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setPort(_validPort);
        properties.setHostName("eigDev100");
        properties.setProtocol("InvalidProtocol");

        try {
            _adminService.saveOutputServerProperties(properties);
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.OUTPUT_SERVER_INVALID_PROTOCOL);
           assertError(e, ROIClientErrorCodes.OUTPUT_SERVER_PROTOCOL_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is to save the output server properties with valid data for merge
     */
    public void testSaveOutputServerPropertiesForMerge() {

        OutputServerProperties properties  = new OutputServerProperties();
        properties.setEnabled(true);
        properties.setHostName("192.168.6.150");
        properties.setPort(_validPort);
        properties.setProtocol("https");
        _adminService.saveOutputServerProperties(properties);

        OutputServerProperties opProps =  _adminService.retrieveOutputServerProperties();

        assertEquals(_validPort, opProps.getPort());
        assertEquals("192.168.6.150", opProps.getHostName());
        assertEquals("https", opProps.getProtocol());
    }


    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
