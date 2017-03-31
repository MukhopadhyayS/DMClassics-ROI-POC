/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.utility.log;

import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import com.mckesson.eig.utility.values.DateTime;

@XmlRootElement(name = "log-event")
public class LogEvent {

    private static JAXBContext _jaxbContext;

    public LogEvent() {

        try {
            _iPAddress = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace(System.err);
        }
        _timestamp = new DateTime().now().format("yyyy-MM-dd HH:mm:ss.SSS z");
    }

    private String _appID;
    private String _iPAddress;
    private String _timestamp;
    private String _authUser;
    private String _code;
    private String _message;
    private String _details;

    public String getAppID() {
        return _appID;
    }
    public void setAppID(String appID) {
        _appID = appID;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public String getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(String timestamp) {
        _timestamp = timestamp;
    }

    public String getAuthUser() {
        return _authUser;
    }

    public void setAuthUser(String user) {
        _authUser = user;
    }

    public String getCode() {
        return _code;
    }

    public void setCode(String code) {
        _code = code;
    }

    public String getDetails() {
        return _details;
    }

    public void setDetails(String details) {
        _details = details;
    }

    public String getIPAddress() {
        return _iPAddress;
    }

    public void setIPAddress(String address) {
        _iPAddress = address;
    }

    public static JAXBContext getJAXBContext()
    throws JAXBException {

        if (_jaxbContext != null) {
            return _jaxbContext;
        }
        _jaxbContext = JAXBContext.newInstance(LogEvent.class);
        return _jaxbContext;
    }

    @Override
    public String toString() {

        StringWriter stringWriter = new StringWriter();
        try {
            getJAXBContext().createMarshaller().marshal(this, stringWriter);
        } catch (JAXBException e) {
            e.printStackTrace(System.err);
        }
        return stringWriter.toString();
    }

    /**
     * This method constructs the data format
     * @return
     */
    public String toLog() {

        return  "\nSource Information: \n"
                + "Application ID       = " + getAppID() + "\n"
                + "IP Address           = " + getIPAddress() + "\n"
                + "Timestamp            = " + getTimestamp() + "\n"
                + "Authenticated User   = " + getAuthUser() + "\n"
                + "\nError Information: \n"
                + "Code                 = " + getCode() + "\n"
                + "Message              = " + getMessage() + "\n"
                + "Details              = " + getDetails();
    }
}
