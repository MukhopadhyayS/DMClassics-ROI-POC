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

package com.mckesson.eig.wsfw.test.axis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis.utils.DOM2Writer;
import org.apache.tools.ant.util.DOMElementWriter;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Provides Methods for processing an XML.
 *
 */
public class XMLProcessor {

    /**
     * Initialises <code>DocumentBuilder</code> to null.
     */
    private DocumentBuilder _builder = null;

    /**
     * Initialises <code>Document</code> to null.
     */
    private Document _document = null;



    /**
     * Parses the rawXML.
     *
     * @param rawXML
     *            Raw data.
     * @throws ParserConfigurationException
     *             if the parsing fails.
     * @throws IOException
     *             if an I/O error occurs.
     * @throws SAXException
     *             if not able to parse.
     */
    public XMLProcessor(String rawXML) throws ParserConfigurationException,
            IOException, SAXException {

        this(new ByteArrayInputStream(rawXML.getBytes()));
    }

    public XMLProcessor(InputStream in) throws ParserConfigurationException,
    IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        _builder = factory.newDocumentBuilder();
        _document = _builder.parse(in);
    }

    /**
     * Serialize this document into the StringWriter as XML.
     *
     * @return current buffer value as String.
     */
    public String formatXML() {
        StringWriter sw = new StringWriter();
        DOM2Writer.serializeAsXML(_document, sw, false, true);
        return sw.toString();
    }

    /**
     * Returns a formatted text without tags.
     *
     * @return formatted text .
     */
    public String formatXMLForBrowser() {
        StringWriter sw = new StringWriter();
        DOMElementWriter dw = new DOMElementWriter();
        try {
            String xmlheader = new String(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\">\n");
            dw.write(_document.getDocumentElement(), sw, 2, " ");
            return escapeXMLTag(xmlheader + sw.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns formatted text without tags.
     *
     * @param xmltext
     *            Text to be formatted.
     * @return formatted text.
     */
    public String escapeXMLTag(String xmltext) {
        final StringBuffer result = new StringBuffer();
        final StringCharacterIterator iterator = new StringCharacterIterator(
                xmltext);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '&') {
                result.append("&amp;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '<') {
                result.append("&lt;");
            } else {
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * Returns the value from the map.
     *
     * @param query
     *            Query for selecting the value.
     * @return a Map
     * @throws JaxenException
     *             If a <code>NamespaceContext</code> used by this XPath has
     *             been explicitly installed.
     *
     */
    public Map getValuesAsMap(String query) throws JaxenException {
        XPath xpath = createXPath(query);
        List results = xpath.selectNodes(_document);
        Map ritorno = new HashMap();
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            Object element = iter.next();
            if (element instanceof org.w3c.dom.Node) {
                org.w3c.dom.Node node = (org.w3c.dom.Node) element;
                ritorno.put(node.getNodeName(), nodeContent(node));
            }
        }
        return ritorno;
    }

    /**
     * It selects all the nodes that are selectable by this expression and adds
     * it to a list.
     *
     * @param query
     *            Query for selection.
     * @return list
     * @throws JaxenException
     *             If a <code>NamespaceContext</code> used by this XPath has
     *             been explicitly installed.
     */
    public List getValuesAsList(String query) throws JaxenException {
        XPath xpath = createXPath(query);
        List results = xpath.selectNodes(_document);
        List ritorno = new LinkedList();
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            Object element = iter.next();
            if (element instanceof org.w3c.dom.Node) {
                ritorno.add(nodeContent((org.w3c.dom.Node) element));
            } else {
                ritorno.add(element);
            }
        }
        return ritorno;
    }

    /**
     * Returns all the attributes from the list.
     *
     * @param query
     *            Query for selecting a value from Map.
     * @param attribute
     *            Attribute that needs to be picked up.
     * @return list of attributes.
     * @throws JaxenException
     *             If a <code>NamespaceContext</code> used by this XPath has
     *             been explicitly installed.
     */
    public String getAttribute(String query, String attribute)
            throws JaxenException {
        String result = null;
        XPath xpath = createXPath(query);
        List results = xpath.selectNodes(_document);
        if (results.size() == 0) {
            result = null;
        } else {
            Node node = (Node) results.get(0);
            Node attributeNode = node.getAttributes().getNamedItem(attribute);
            result = attributeNode.getNodeValue();
        }
        return result;
    }

    /**
     * Returns the value for the corresponding query.
     *
     * @param query
     *            for processing.
     * @return the value for the query.
     * @throws JaxenException
     *             If a <code>NamespaceContext</code> used by this XPath has
     *             been explicitly installed.
     */
    public String getValue(String query) throws JaxenException {

        String result = null;
        List list = getValuesAsList(query);
        if (list.size() == 0) {
            result = null;
        } else if (list.size() == 1) {
            result = (String) list.get(0);
        } else {
            result = list.toString();
        }
        return result;
    }

    /**
     * Returns the node content.
     *
     * @param nodo
     *            Holds the instance of <code>Node</code>.
     * @return node value.
     */
    private String nodeContent(org.w3c.dom.Node nodo) {
        String result = "";
        NodeList childes = nodo.getChildNodes();
        for (int i = 0; i < childes.getLength(); i++) {
            org.w3c.dom.Node n = childes.item(i);
            if (n.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                result = n.getNodeValue();
                break;
            }
        }
        return result;
        // return nodo.getNodeValue();
    }

    /**
     * Set a <code>NamespaceContext</code> for use with this XPath expression.
     *
     * <p>
     * A <code>NamespaceContext</code> is responsible for translating
     * namespace prefixes within the expression into namespace URIs.
     * </p>
     *
     * @return XPath.
     * @param query
     *            for initializing Xpath.
     *
     * @throws JaxenException
     *             If a <code>NamespaceContext</code> used by this XPath has
     *             been explicitly installed.
     */
    private XPath createXPath(String query) throws JaxenException {
        // Uses DOM to XPath mapping
        XPath xpath = new DOMXPath(query);
        // Define a namespaces used in response
        SimpleNamespaceContext nsContext = new SimpleNamespaceContext();
        // TODO hard code the namespace. need figure out how to get from
        // SOAP header
        nsContext.addNamespace("eig", "urn:eig.mckesson.com");
        nsContext.addNamespace("soap",
                "http://schemas.xmlsoap.org/soap/envelope/");
        nsContext.addNamespace("soapenv",
                "http://schemas.xmlsoap.org/soap/envelope/");
        nsContext.addNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/");
        xpath.setNamespaceContext(nsContext);
        return xpath;
    }
}
