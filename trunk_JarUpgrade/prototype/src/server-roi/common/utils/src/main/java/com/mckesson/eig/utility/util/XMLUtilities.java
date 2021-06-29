/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mckesson.eig.utility.io.IOUtilities;

/**
 * @author sahuly
 * @date   Mar 31, 2009
 * @since  HECM 2.0; Mar 31, 2009
 */
public final class XMLUtilities {


    private static final String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String FEATURE1 = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURE2 = "http://xml.org/sax/features/external-parameter-entities";
    private static final String FEATURE3 = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    private XMLUtilities() {
    }

    private static DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
    	DocumentBuilderFactory spf = DocumentBuilderFactory.newInstance();
    	spf.setFeature(FEATURE, true);
    	spf.setFeature(FEATURE1, false);
    	spf.setFeature(FEATURE2, false);
    	spf.setFeature(FEATURE3, false);
    	return spf;
    }

    /**
     * Parse the content of the given  string as an XML document and return a
     * new DOM {@link Document} object.
     *
     * @param xmlElement
     *        string value in xml format
     *
     * @param canConsiderNameSpace
     *        whether to return the document with name space or not.
     *
     * @return document
     *         return a new DOM {@link Document} object.
     *
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static Document parse(String xmlElement, boolean canConsiderNameSpace)
        throws SAXException, IOException, ParserConfigurationException {

    	DocumentBuilderFactory factory = getDocumentBuilderFactory();
    	factory.setNamespaceAware(canConsiderNameSpace);

        Reader reader = null;
        try {
        	reader = new StringReader(xmlElement);
        	return factory.newDocumentBuilder().parse(new InputSource(reader));
        } finally {
        	IOUtilities.close(reader);
        }
    }

    /**
     * Parse the content of the given file as an XML document and return a
     * new DOM {@link Document} object.
     *
     * @param xmlFile
     *        file having values in xml format
     *
     * @param canConsiderNameSpace
     *        whether to return the document with name space or not.
     *
     * @return document
     *         return a new DOM {@link Document} object.
     *
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static Document parse(File xmlFile, boolean canConsiderNameSpace)
    throws SAXException, IOException, ParserConfigurationException {

    	DocumentBuilderFactory factory = getDocumentBuilderFactory();
    	factory.setNamespaceAware(canConsiderNameSpace);
        return factory.newDocumentBuilder().parse(xmlFile);
    }

    /**
     * Parse the content of the given  string as an XML document and return a
     * new DOM {@link Document} object.
     *
     * @param inputStream
     *        inputStream
     *
     * @param canConsiderNameSpace
     *        whether to return the document with name space or not.
     *
     * @return document
     *         return a new DOM {@link Document} object.
     *
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
   public static Document parse(InputStream inputStream, boolean canConsiderNameSpace)
   throws SAXException, IOException, ParserConfigurationException {

		DocumentBuilderFactory factory = getDocumentBuilderFactory();
		factory.setNamespaceAware(canConsiderNameSpace);
		return factory.newDocumentBuilder().parse(inputStream);
   }

    /**
     * Helper method used to convert <code>Node</code> to XMLString
     *
     * @param node Node Instance
     *
     *@return XML String of the node
     */
    public static String convertNodeToString(Node n) {

        String name = n.getNodeName();
        short type  = n.getNodeType();

        if (Node.CDATA_SECTION_NODE == type) {
            return "<![CDATA[" + n.getNodeValue() + "]]&gt;";
        }
        if (name.startsWith("#")) {
            return StringUtilities.EMPTYSTRING;
        }

        StringBuilder sb = new StringBuilder();
        sb.append('<').append(name);

        NamedNodeMap attrs = n.getAttributes();
        if (attrs != null) {

            Node attr = null;
            for (int i = 0, count = attrs.getLength(); i < count; i++) {

                attr = attrs.item(i);
                sb.append(' ')
                  .append(attr.getNodeName())
                  .append("=\"")
                  .append(attr.getNodeValue())
                  .append("\"");
            }
        }

        processChildNodes(n, sb);

        return sb.toString();
    }

	/**
	 * @param document
	 * @param expression
	 * @return String value for the first node matching the expression
	 * @throws XPathExpressionException
	 */
	public static String evaluateXPathAsString(Document document, String expression) throws XPathExpressionException {
		String retValue = (String) evaluateXPath(document, expression, XPathConstants.STRING);
        return retValue;
	}

	/**
	 * @param document
	 * @param expression
	 * @return the first node matching the expression
	 * @throws XPathExpressionException
	 */
	public static Node evaluateXPathAsNode(Document document, String expression) throws XPathExpressionException {
        Node retValue = (Node) evaluateXPath(document, expression, XPathConstants.NODE);
        return retValue;
	}

	/**
	 * @param document
	 * @param expression
	 * @return the list if nodes matching the expression
	 * @throws XPathExpressionException
	 */
	public static NodeList evaluateXPathAsNodeList(Document document, String expression) throws XPathExpressionException {
        NodeList retValue = (NodeList) evaluateXPath(document, expression, XPathConstants.NODESET);
        return retValue;
	}

	/**
	 * @param document
	 * @param expression
	 * @param returnType
	 * @return the desired type requested by the <code>returnType</code> parameter.
	 * @throws XPathExpressionException
	 */
	public static Object evaluateXPath(Document document, String expression, QName returnType) throws XPathExpressionException
	{
		XPath xpath = createXPath();
	    Object result = xpath.evaluate(expression, document, returnType);
		return result;
	}

	/**
	 * @return XPath
	 */
	private static XPath createXPath()
	{
		XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
		return xpath;
	}

	private static void processChildNodes(Node n, StringBuilder sb) {

        String textContent = null;
        NodeList children  = n.getChildNodes();

        if (children.getLength() == 0) {

            textContent = getValue(n);
            if ((textContent != null) && !StringUtilities.EMPTYSTRING.equals(textContent)) {
                sb.append(textContent).append("</").append(n.getNodeName()).append('>');
            } else {
                sb.append("/>");
            }

        } else {

            sb.append('>');
            boolean hasValidChildren = false;
            String childToString = null;
            for (int i = 0, count = children.getLength(); i < count; i++) {

                childToString = convertNodeToString(children.item(i));
                if (!StringUtilities.EMPTYSTRING.equals(childToString)) {

                    sb.append(childToString);
                    hasValidChildren = true;
                }
            }

            textContent = getValue(n);
            if (!hasValidChildren && (textContent != null)) {
                sb.append(textContent);
            }

            sb.append("</").append(n.getNodeName()).append('>');
        }
    }

   /**
    * Returns the data of an element as String or null if either the the element
    * does not contain a Text node or the node is empty.
    *
    * @param e
    *            DOM element
    * @return Element text node data as String
    */
   public static String getValue(Node e) {
       if (e != null) {
           Text node = getFirstNode((Element) e);
           if (node != null) {
               return node.getData();
           }
       }
       return null;
   }

   /**
    * Returns the first text node of an element.
    *
    * @param e
    *            the element to get the node from
    * @return the first text node or <code>null</code> if node is null or is
    *         not a text node
    */
   private static Text getFirstNode(Element e) {
       Node node = e.getFirstChild();
       return ((node != null) && (node instanceof Text)) ? (Text) node : null;
   }
}
