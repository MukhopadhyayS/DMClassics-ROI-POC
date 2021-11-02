/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.admin.model;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;
import com.mckesson.eig.wsfw.EIGConstants;



/**
 * <p>Java class for OutputServerProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputServerProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hostName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="protocol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputServerProperties", propOrder = {
    "enabled",
    "hostName",
    "port",
    "protocol"
})
/*@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "output-server")
@XmlType(name = "OutputServerProperties", namespace = EIGConstants.TYPE_NS_V1)*/
public class OutputServerProperties {

    private boolean enabled;
    @XmlElement(required = true)
    private String hostName;
    private long port;
    @XmlElement(required = true)
    private String protocol; // http or https

   

    private static JAXBContext _jaxbContext;

    public static JAXBContext getJAXBContext()
    throws JAXBException {

        if (_jaxbContext != null) {
            return _jaxbContext;
        }
        _jaxbContext = JAXBContext.newInstance(OutputServerProperties.class);
        return _jaxbContext;
    }

    public long getPort() {
        return port;
    }

    //@XmlElement(name = "port")
    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.OUTPUT_SERVER_PORT_EMPTY,
            pattern = ROIConstants.PORT,
            misMatchErrCode = ROIClientErrorCodes.OUTPUT_SERVER_INVALID_PORT,
            maxLength = ROIConstants.OUTPUT_SERVER_PORT_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.OUTPUT_SERVER_PORT_LENGTH_EXCEEDS_LIMIT)
    public void setPort(long port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    //@XmlElement(name = "hostName")
    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.OUTPUT_SERVER_HOSTNAME_EMPTY,
            pattern = ROIConstants.HOST_NAME,
            misMatchErrCode = ROIClientErrorCodes.OUTPUT_SERVER_INVALID_HOSTNAME,
            maxLength = ROIConstants.OUTPUT_SERVER_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.OUTPUT_SERVER_HOSTNAME_LENGTH_EXCEEDS_LIMIT)
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProtocol() {
        return protocol;
    }

    //@XmlElement(name = "protocol")
    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.OUTPUT_SERVER_PROTOCOL_EMPTY,
            pattern = ROIConstants.PROTOCOL,
            misMatchErrCode = ROIClientErrorCodes.OUTPUT_SERVER_INVALID_PROTOCOL,
            maxLength = ROIConstants.OUTPUT_SERVER_PROTOCOL_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.OUTPUT_SERVER_PROTOCOL_LENGTH_EXCEEDS_LIMIT)
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean getEnabled() {
        return enabled;
    }

    //@XmlAttribute(name = "enabled")
    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

    /**
     * Helper method used to convert this instance of <code>OutputServerProporties</code> to an xml
     * string
     *
     * @return XML String
     * @throws JAXBException  if error occurd during the conversion
     */
    public String toXMLString() throws JAXBException {

        javax.xml.bind.Marshaller m = getJAXBContext().createMarshaller();
        StringWriter writer = new StringWriter();
        m.marshal(this, writer);
        return writer.toString();
    }

    /**
     * Helper method used to prepare the instance of <code>OutputServerProporties</code> from an xml
     * string
     *
     * @param xmlString XMLString of output server properties
     * @return instance of <code>OutputServerProporties</code>
     * @throws JAXBException  if error occurd during the conversion
     */
    public static OutputServerProperties valueOf(String xmlString) throws JAXBException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        Source xmlSource = null;
        Unmarshaller um = null;
        try {
            spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            org.xml.sax.InputSource source = new  org.xml.sax.InputSource (xmlString);
            xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), source);
            um = getJAXBContext().createUnmarshaller();
        }  catch (Exception e) {
    
            throw new JAXBException(e);
        }
        return (OutputServerProperties) um.unmarshal(xmlSource);
    }
}
