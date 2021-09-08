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

package com.mckesson.eig.utility.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.xpath.XPathConstants;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mckesson.eig.utility.io.IOUtilities;

/**
 * @author sahuly
 * @date   Mar 31, 2009
 * @since  HECM 2.0; Mar 31, 2009
 */
public class TestXMLUtilities extends TestCase {

	private static final String PATH          = "etc\\com\\mckesson\\eig\\utility\\components\\";
	private static final String FILE_NAME     = PATH + "test.component-info.xml";
	private static final String XML_STRING;

	static {
		StringBuilder stringBuilder = new StringBuilder(1024);
		InputStream tstFileInputStream = null;
		try  {
			tstFileInputStream = TestXMLUtilities.class.getResourceAsStream(TestXMLUtilities.class.getSimpleName() + ".xml");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(tstFileInputStream));

			String line = null;
			while (null != (line = bufferedReader.readLine()))
			{
				stringBuilder.append(line).append('\n');
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtilities.close(tstFileInputStream);
		}

		XML_STRING = stringBuilder.toString();
	}

	/**
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp()
	throws Exception {

	    super.setUp();
	    System.setProperty("application.home", System.getenv("JBOSS_HOME") + "\\server\\default");
	}

	public void testParseString()
	throws Exception {

	    Document doc = XMLUtilities.parse(XML_STRING, true);
	    assertNotNull(doc);
	    doc = XMLUtilities.parse(XML_STRING, false);
	    assertNotNull(doc);
	}

	public void testParseFile()
	throws Exception {

	    Document doc = XMLUtilities.parse(new File(FILE_NAME), true);
	    assertNotNull(doc);
	    doc = XMLUtilities.parse(new File(FILE_NAME), false);
	    assertNotNull(doc);
	}

	public void testParseStream()
	throws Exception {

	    Document doc = XMLUtilities.parse(new FileInputStream(new File(FILE_NAME)), true);
	    assertNotNull(doc);
	    doc = XMLUtilities.parse(new FileInputStream(new File(FILE_NAME)), false);
	    assertNotNull(doc);
	}

	public void testEvaluateXPathAsString()
	throws Exception {
		Document doc = XMLUtilities.parse(XML_STRING, true);
		{
			String value = XMLUtilities.evaluateXPathAsString(doc, "//application-id[1]");
			assertEquals(value.trim(), "HECM");
		}
		{
			String value = XMLUtilities.evaluateXPathAsString(doc, "//application-id[text() = 'HECM']");
			assertEquals(value.trim(), "HECM");
		}
		{
			String value = XMLUtilities.evaluateXPathAsString(doc, "/component/application-id");
			assertEquals(value.trim(), "HECM");
		}
		{
			String value = XMLUtilities.evaluateXPathAsString(doc, "/component/application-id/@innerAttribute");
			assertEquals(value.trim(), "innerAttribute1");
		}
	}

	public void testEvaluateXPathAsNode()
	throws Exception {
		Document doc = XMLUtilities.parse(XML_STRING, true);
		{
			Node node = XMLUtilities.evaluateXPathAsNode(doc, "//application-id[1]");
			assertNotNull(node);
			assertEquals(node.getTextContent().trim(), "HECM");
		}
		{
			Node node = XMLUtilities.evaluateXPathAsNode(doc, "//application-id[text() = 'HECM']");
			assertNotNull(node);
			assertEquals(node.getTextContent().trim(), "HECM");
		}
		{
			Node node = XMLUtilities.evaluateXPathAsNode(doc, "/component/application-id");
			assertNotNull(node);
			assertEquals(node.getTextContent().trim(), "HECM");
		}
		{
			Node node = XMLUtilities.evaluateXPathAsNode(doc, "/component/application-id/@innerAttribute");
			assertNotNull(node);
			assertEquals(node.getTextContent().trim(), "innerAttribute1");
		}
	}

	public void testEvaluateXPathAsNodeList()
	throws Exception {
		Document doc = XMLUtilities.parse(XML_STRING, true);
		{
			NodeList nodeList = XMLUtilities.evaluateXPathAsNodeList(doc, "//application-id[1]");
			assertNotNull(nodeList);
			assertEquals(nodeList.getLength(), 1);
		}
		{
			NodeList nodeList = XMLUtilities.evaluateXPathAsNodeList(doc, "//application-id[text() = 'HECM']");
			assertNotNull(nodeList);
			assertEquals(nodeList.getLength(), 1);
		}
		{
			NodeList nodeList = XMLUtilities.evaluateXPathAsNodeList(doc, "/component/application-id");
			assertNotNull(nodeList);
			assertEquals(nodeList.getLength(), 2);
		}
		{
			NodeList nodeList = XMLUtilities.evaluateXPathAsNodeList(doc, "/component/application-id/@innerAttribute");
			assertNotNull(nodeList);
			assertEquals(nodeList.getLength(), 2);
		}
	}

	public void testEvaluateXPath()
	throws Exception {
		Document doc = XMLUtilities.parse(XML_STRING, true);
		{
			Boolean exists = (Boolean) XMLUtilities.evaluateXPath(doc, "//application-id[1]/@innerAttribute", XPathConstants.BOOLEAN);
			assertNotNull(exists);
			assertTrue(exists.booleanValue());
		}
		{
			Boolean exists = (Boolean) XMLUtilities.evaluateXPath(doc, "//application-id[2]/@innerAttribute", XPathConstants.BOOLEAN);
			assertNotNull(exists);
			assertTrue(exists.booleanValue());
		}
		{
			Boolean exists = (Boolean) XMLUtilities.evaluateXPath(doc, "//application-id[2]/@dummy", XPathConstants.BOOLEAN);
			assertNotNull(exists);
			assertFalse(exists.booleanValue());
		}
		{
			Number count = (Number) XMLUtilities.evaluateXPath(doc, "count(//application-id)", XPathConstants.NUMBER);
			assertNotNull(count);
			assertEquals(count.intValue(), 2);
		}
		{
			Number value = (Number) XMLUtilities.evaluateXPath(doc, "/component//application-id[@innerAttribute='innerAttribute1']/@intVal", XPathConstants.NUMBER);
			assertNotNull(value);
			assertEquals(value.intValue(), 8675309);
		}
		{
			Number value = (Number) XMLUtilities.evaluateXPath(doc, "/component//application-id[@innerAttribute='innerAttribute2']/@intVal", XPathConstants.NUMBER);
			assertNotNull(value);
			assertEquals(value.intValue(), 1900);
		}
	}

	public void testXMLUtilitiesConstructor() {

	    try {

	        ReflectionUtilities.callPrivateConstructor(XMLUtilities.class);
	        assertTrue(true);
	    } catch (Exception e) {
	        assertTrue(false);
	    }
	}
}
