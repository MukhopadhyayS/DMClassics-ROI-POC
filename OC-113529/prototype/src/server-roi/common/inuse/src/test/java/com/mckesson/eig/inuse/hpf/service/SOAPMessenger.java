/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.inuse.hpf.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.message.MimeHeaders;
import org.apache.axis.message.SOAPFault;
import org.apache.axis.soap.MessageFactoryImpl;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mckesson.eig.wsfw.test.axis.SoapRequestBuilder;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;


/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class SOAPMessenger {

    private static Mapping _mapping;
    private ServletUnitClient _client;

    public SOAPMessenger(Mapping mapping, ServletUnitClient client) {
        _mapping = mapping;
        _client = client;
    }

    protected String marshallObject(Object o)
    throws Exception {

        StringWriter writer = new StringWriter();
        Marshaller m = new Marshaller(writer);
        m.setMapping(_mapping);
        m.setMarshalAsDocument(false);
        m.marshal(o);
        return writer.toString();
    }

    public String invoke(Object param,
                            String serviceMethod,
                            String serviceURL,
                            String userName,
                            String password)

    throws Exception {

        String paramString = marshallObject(param);
        return invoke(paramString, serviceMethod, serviceURL, userName, password);
    }

    protected String getRequestMessage(String param,
                                       String serviceMethod,
                                       String userName,
                                       String password)
    throws Exception {

        final String uri = "urn:eig.mckesson.com";

        SoapRequestBuilder builder = new SoapRequestBuilder();
        builder.setOperationData(serviceMethod, uri);
        InputStream in = builder.buildSoapRequestWithSecurityHeader(userName, password);

        /*
         * work around - not able to add param as Node to SOAP message's body element. Throws
         * ClassCastException, since SOAPBodyElement.addChilde(Node) requires a different NodeImpl
         */
        byte[] b = new byte[in.available()];
        in.read(b);
        StringBuffer base = new StringBuffer(new String(b));
        int i = base.indexOf("/></soapenv:Body>");
        String msg = base.replace(i, i + 1, ">\n" + param + "</" + serviceMethod).toString();

        return msg;
    }

    protected String invoke(String param,
                            String serviceMethod,
                            String serviceURL,
                            String userName,
                            String password)
    throws Exception {

        final String mime = "text/xml";

        String req = getRequestMessage(param, serviceMethod, userName, password);

        WebRequest request = new PostMethodWebRequest(serviceURL,
                                                      new ByteArrayInputStream(req.getBytes()),
                                                      mime);

        request.setHeaderField("SOAPAction", "");
        WebResponse webResponse = _client.getResponse(request);
        String res = prepareResponse(webResponse);
        return res;
    }

    protected String prepareResponse(WebResponse webResponse)
    throws Exception {

        InputStream in = new ByteArrayInputStream((webResponse.getText()).getBytes());

        SOAPMessage soapMsg = new MessageFactoryImpl().createMessage(new MimeHeaders(), in);

        Node nResponse = soapMsg.getSOAPBody().getChildNodes().item(0);

        if (nResponse instanceof SOAPFault) { // SOAPException
            return nResponse.toString();
        }

        NodeList nResult = nResponse.getChildNodes();
        if (nResult.getLength() == 0) { // for void return type
            return "";
        }

        return nResponse.getChildNodes().item(0).toString();
    }

    public Object unMarshallObject(String s)
    throws Exception {

        Unmarshaller um = new Unmarshaller();
        um.setMapping(_mapping);
        um.setValidation(true);

        return um.unmarshal(new InputSource(new ByteArrayInputStream(s.getBytes())));
    }

    protected long unMarshallLong(String s) {
        return Long.parseLong(unMarshallString(s));
    }

    protected String unMarshallString(String s) {
        return s.substring(s.indexOf(">") + 1, s.lastIndexOf("</"));
    }

    public ServletUnitClient getClient() { return _client; }
    public void setClient(ServletUnitClient client) { _client = client; }

}
