/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.config.util.controller;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mckesson.eig.roi.config.util.api.AutoConfigurator;
import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;
import com.mckesson.eig.roi.config.util.dao.ConfigurationControllerDAO;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * @author OFS
 * @date   Apr 7, 2008
 * @since  HPF 13.1 [ROI]; Sep 9, 2008
 */
public abstract class ConfigurationControllerImpl
implements ConfigurationController {

    private static final Logger LOG = Logger.getLogger(ConfigurationControllerImpl.class);

    private String _servicesPath =
        "//configuration/applicationSettings/McK.EIG.ROI.Client.Properties.Settings"
            + "/setting/value";

    private String _portXPath = "//Server/Service/Connector/@port";
    private String _connectorXPath = "//Server/Service/Connector";
    // CR #368,304
    private String _sslConnectorXPath = "//Server/Service/Connector[@SSLEnabled]";
    
    private static String newLine = System.getProperty("line.separator");

    private String ATTR_PORT = "port";
    private String ATTR_PROTOCOL = "protocol";
    private String ATTR_SSL_ENABLED = "SSLEnabled";
    private String ATTR_HTTP = "HTTP/1.1";
    private String HTTP = "http";
    private String HTTPS = "https";
    private String PROTOCOL_AJP = "AJP/1.3";
    private String REDIRECT_PORT = "redirectPort";

    private final static String CONFIG_FILE = "server.xml";

    /**
     *
     * @see com.mckesson.eig.roi.config.util.controller.ConfigurationController
     * #updateClickOnceFiles()
     */
    @Override
	public void updateClickOnceFiles() {

        final String logSM = "updateClickOnceFiles()";
        LOG.debug(logSM + ">>Start:");

        try {

        	 Document document = getXMLDocument(ConfigProps.JBOSS_SERVER_SERVER_XML_FILE);
             String jBPortNo = getJBossPortValue(document, _portXPath);

             String batFilePath = ConfigProps.JBOSS_HOME
                                     + ConfigProps.CLICK_ONCE_UPDATE_SETUP_PATH;

             String protocol=HTTP;
             if(checkSSLEnabled()){
            	 protocol = HTTPS;
             }

           //Writes an UpdateSetUp.bat file when isUpdate value is true.
             // Increments the client version.Whenever client configuration has been modified.
             File file = new File(batFilePath, "UpdateSetUp.bat");
             String isUpdated = AutoConfigurator.getProps().get("isUpdated").toString();
             LOG.debug("isUpdated:" + isUpdated);
             if (Boolean.valueOf(isUpdated)) {
	             writeUpdateSetUp(file);
             }

             String args[] = new String[] {"cmd", "/c", "start", "UpdateSetUp.bat",
            		 					ConfigProps.JBOSS_HOME, jBPortNo,protocol};


             Runtime.getRuntime().exec(args, null , new File(batFilePath));

             LOG.debug(logSM + "<<End :");

        } catch (Throwable e) {

            LOG.debug(e);
            e.printStackTrace();
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.run.bat.file"),
                                          e.getCause());
        }
    }

    @SuppressWarnings("unchecked") //not supported by 3PartyAPI
	public String getJBossPortValue (Document doc, String xPath){

         List<DefaultElement> nodes = doc.selectNodes(_connectorXPath);
         List<DefaultAttribute> attributes = null;
         DefaultAttribute port = null;
         boolean isHttp = false;

		for (DefaultElement node : nodes) {

			attributes = node.attributes();
			for (DefaultAttribute attr : attributes) {

				if (ATTR_PORT.equals(attr.getName())) {
					port = attr;

				// modified to get only the http port/ssl port number
				// to exclude the AJP port
				} else if (ATTR_PROTOCOL.equals(attr.getName())
							&& ATTR_HTTP.equals(attr.getValue())) {

					isHttp = true;
				}
			}

			if (isHttp && nodes.size() < 3) {
				break;
			}
		}
         return port.getValue();
    }

    /**
     * This method is to get the attribute value from the corresponding file
     * @param doc Document
     * @param xPath Attribute location
     * @return
     */
    public String getAttributeValue(Document doc, String xPath) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<DefaultElement> nodes = doc.selectNodes(xPath);
        List<DefaultAttribute> attributes = null;
        DefaultAttribute port = null;
        boolean isHttp = false;

        for (DefaultElement node : nodes ) {

        	attributes = node.attributes();
	        for (DefaultAttribute attr : attributes) {

	        	if (ATTR_PORT.equals(attr.getName())) {
	        		port = attr;
	        	}

	        	if (ATTR_PROTOCOL.equals(attr.getName()) && ATTR_HTTP.equals(attr.getValue())) {
	        		isHttp = true;
	        	}
	        }
	        if (isHttp) { break; }
        }
        return port.getValue();
    }

    /**
     * This method is to get the attribute value from the corresponding file
     * @param doc Document
     * @param xPath Attribute location
     * @return
     */
	public String getConditionedAttributeValue(Document doc, String xPath) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<DefaultElement> nodes = doc.selectNodes(_connectorXPath);
        List<DefaultAttribute> atts = null;
        DefaultAttribute port = null;
        String val = "";
        boolean isHttp;
        boolean sslconnector;
        // CR #368,304 -  if the both sslconnector and normal http connectors are enabled,
        // only the normal http port number is returned.
        // In case of only sslConnector is enabled, ssl port number is returned.
        for (DefaultElement node : nodes ) {

        	isHttp = false;
        	sslconnector = false;
        	atts = node.attributes();
	        for (DefaultAttribute attr : atts) {

	        	if (ATTR_PORT.equals(attr.getName())) {
	        		port = attr;
	        		continue;
	        	}

	        	if (ATTR_SSL_ENABLED.equals(attr.getName())) {
	        		sslconnector = true;
	        	}

	        	if (ATTR_PROTOCOL.equals(attr.getName()) && ATTR_HTTP.equals(attr.getValue())) {
	        		isHttp = true;
	        	}
	        }
	        if (isHttp && !sslconnector) {
	        	val = port.getValue();
	        	return val;
	        }
        }

        return getAttributeValue(doc, _sslConnectorXPath);
    }

    /**
     * This method is to set the attribute value into the corresponding file
     * @param doc Document
     * @param xPath Attribute location
     * @param value Attribute value to be stored
     */
    public void setAttributeValue(Document doc, String xPath, String value) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<DefaultElement> nodes = doc.selectNodes(_connectorXPath);
        List<DefaultAttribute> attributes = null;
        DefaultAttribute port = null;
        boolean isHttp = false;
        boolean isAJP = false;

        boolean connectorAndRedirectPortSame = false;

        for (DefaultElement node : nodes) {

        	isHttp = false;
        	isAJP = false;
        	connectorAndRedirectPortSame = false;

            attributes = node.attributes();
            for (DefaultAttribute attr : attributes) {

                 if (ATTR_PORT.equals(attr.getName())) {
                     port = attr;
                 }

                 // CR #368,304 - The port should be assigned only to connector with protocol HTTP/1.1
                 // previously it assign to any connector which contains port attribute.
                 if (ATTR_PROTOCOL.equals(attr.getName()) && ATTR_HTTP.equals(attr.getValue())) {
                	 isHttp = true;
                 }

                 if (ATTR_PROTOCOL.equals(attr.getName())  && PROTOCOL_AJP.equals(attr.getValue())) {
                	 isAJP = true;
                 }

                 if (isHttp && REDIRECT_PORT.equals(attr.getName())  && value.equals(attr.getValue())) {
                	 connectorAndRedirectPortSame = true;
                 }
            }
			// No of Connector Nodes will be 2 if SSL is disabled, it will be 3 if SSL is enabled.
            //Port value in the connector node will be updated if SSL is disabled,
            //Port value in the SSL connector node will be updated if SSL is enabled.
            // AJB port will not be updated for both the cases.
			if(!connectorAndRedirectPortSame && !isAJP)
				port.setText(value);
        }
    }

    /**
     * This method is to set the attribute value from the corresponding file
     * @param element Element
     * @param attrName Attribute Name
     * @param attrValue Attribute Value
     * @return
     */
	public void setAttributeValue(Element element, String attrName, String attrValue) {

		List<Attribute> attributes = null;
		if (null != element) {

				attributes = element.attributes();
		        for (Attribute attr : attributes) {

		        	if (attrName.equals(attr.getName())) {
		        		attr.setValue(attrValue);
		        	}
		        }
		 }
	}


    /**
     * This method is to set the attribute value from the corresponding file
     * @param element Element
     * @param attrName Attribute Name
     * @param attrValue Attribute Value
     * @return
     */
	public String getAttributeValue(Element element, String attrName) {

		List<Attribute> attributes = null;
		if (null != element) {

			attributes = element.attributes();
			for (Attribute attr : attributes) {

				if (attrName.equals(attr.getName())) {
					return attr.getValue();
				}
			}
		}
		return null;
	}

	/**
     * This method is to get the attribute value from the corresponding file
     * @param doc Document
     * @param xPath Attribute location
     * @return
     */

	public void setConditionedAttributeValue(Document doc, String xPath, String portValue) {
		setAttributeValue(doc, xPath, portValue);
	}

    /**
     * This method is to get the element value from the corresponding file
     * @param doc Document
     * @param xPath Element location
     * @return Element value
     */
    public String getElementValue(Document doc, String xPath) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = doc.selectNodes(xPath);
        Element element = elements.get(0);
        return element.getText();
    }

    /**
     * This method is to set the element value into the corresponding file
     * @param doc Document
     * @param xPath Element location
     * @param value Element value to be stored
     */
    public void setElementValue(Document doc, String xPath, String value) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = doc.selectNodes(xPath);
        Element element = elements.get(0);
        element.setText(value);
    }

    /**
     * This method is to set the element value into the corresponding file
     * @param doc Document
     * @param xPath Element location
     * @param value Element value to be stored
     */
    @SuppressWarnings("deprecation")
	public void setSMBElementValue(Document doc, String xPath, String value) {

    	@SuppressWarnings("unchecked") //not supported by 3PartyAPI
    	List<Element> elements = doc.selectNodes(xPath);
    	Element element = elements.get(0);
    	element.addAttribute("value", value);
    }

    /**
     * This method is to set the element value into the corresponding file
     * @param doc Document
     * @param xPath Element location
     * @param value Element value to be stored
     */
    @SuppressWarnings("deprecation")
    public void setEncryptionElementValue(Document doc, String xPath, String value) {

    	@SuppressWarnings("unchecked") //not supported by 3PartyAPI
    	List<Element> elements = doc.selectNodes(xPath);
    	Element element = elements.get(0);
    	element.addAttribute("class", value);
    }

    /**
     * This method is to get the app config value from the corresponding file
     * @param doc Document
     * @param xPath location
     * @return app config value
     */
    public String getAppConfigValue(Document doc, String xPath) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = doc.selectNodes(xPath);
        return elements.get(0).attribute("value").getText();
    }

    /**
     * This method is to updateAppSettingsValue
     * @param xPath location
     * @param doc Document
     * @param value value
     */
    public void updateAppSettingsValue(String xPath, Document doc, String value) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = doc.selectNodes(xPath);
        if (CollectionUtilities.isEmpty(elements)) {
        	LOG.error("Unable to find element for the XPath:" + xPath);
        	return;
        }

        elements.get(0).attribute("value").setText(value);
    }

    @SuppressWarnings("unchecked")
    public String getOutputConfigValue(Document doc) {

        String outputAuthenticatorDetails = null;
        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
                && "HPFAuthenticationServiceLoader".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];

                    //get the attribute of constructor-arg of a child
                    Attribute att = de.attribute("value");
                    outputAuthenticatorDetails = att.getText();
                }
            }
        }
        return outputAuthenticatorDetails;
    }

    @SuppressWarnings("unchecked")
    public String getROIConfigValue(Document doc) {

        String roiAuthenticatorDetails = null;
        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
                && "AuthenticationStrategy".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];
                    Attribute attr = de.attribute("name");
                    if ((attr != null) && "authenticationUrl".equalsIgnoreCase(attr.getValue())) {

                        List subchild = de.elements();
                        Object[] chi = subchild.toArray();

                            for (int l = 0; l < chi.length; l++) {
                                DefaultElement d = (DefaultElement) chi[l];
                                roiAuthenticatorDetails = d.getText();
                            }
                    }
                }
            }
        }
        return roiAuthenticatorDetails;
    }

    @SuppressWarnings("unchecked")
    public String getInUseConfigValue(Document doc) {

        String roiAuthenticatorDetails = null;
        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
                && "AuthenticationStrategy".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];
                    Attribute attr = de.attribute("name");
                    if ((attr != null) && "authenticationUrl".equalsIgnoreCase(attr.getValue())) {

                        List subchild = de.elements();
                        Object[] chi = subchild.toArray();

                            for (int l = 0; l < chi.length; l++) {
                                DefaultElement d = (DefaultElement) chi[l];
                                roiAuthenticatorDetails = d.getText();
                            }
                    }
                }
            }
        }
        return roiAuthenticatorDetails;
    }


    @SuppressWarnings("unchecked")
    public void updateHPFWAuthConfigValue(Document doc, Map<String, String> config) {

        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
            && "HPFAuthenticationServiceLoader".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];

                    //get the attribute of constructor-arg of a child
                    Attribute att = de.attribute("value");
                    att.setText(getUpdatedConnectionUrl(att.getText(), config));
                }
            }

//            if ((attribute != null) && "roi.outputplugin".equalsIgnoreCase(attribute.getValue())) {
//
//                List children = element.elements();
//                Object[] child = children.toArray();
//                setROIOutputPluginConfig(config, child);
//            }
        }
    }

    @SuppressWarnings("unchecked")
    public void updateOutputAuthConfigValue(Document doc, Map<String, String> config) {

        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
            && "HPFAuthenticationServiceLoader".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];

                    //get the attribute of constructor-arg of a child
                    Attribute att = de.attribute("value");
                    att.setText(getUpdatedConnectionUrl(att.getText(), config));
                }
            }

            if ((attribute != null) && "roi.outputplugin".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                setROIOutputPluginConfig(config, child);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void updateROIAuthenticationConfigValue(Document doc, Map<String, String> config) {

        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
            && "AuthenticationStrategy".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];
                    Attribute attr = de.attribute("name");

                    if ((attr != null) && "authenticationUrl".equalsIgnoreCase(attr.getValue())) {

                        List subchild = de.elements();
                        Object[] chi = subchild.toArray();

                            for (int l = 0; l < chi.length; l++) {
                                DefaultElement d = (DefaultElement) chi[l];
                                d.setText(getUpdatedConnectionUrl(d.getText(), config));
                            }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void updateInUseAuthenticationConfigValue(Document doc, Map<String, String> config) {

        Element e = doc.getRootElement();
        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

            Element element = i.next();
            Attribute attribute = element.attribute("id");

            if ((attribute != null)
            && "AuthenticationStrategy".equalsIgnoreCase(attribute.getValue())) {

                List children = element.elements();
                Object[] child = children.toArray();
                for (int k = 0; k < child.length; k++) {

                    DefaultElement de = (DefaultElement) child[k];
                    Attribute attr = de.attribute("name");

                    if ((attr != null) && "authenticationUrl".equalsIgnoreCase(attr.getValue())) {

                        List subchild = de.elements();
                        Object[] chi = subchild.toArray();

                            for (int l = 0; l < chi.length; l++) {
                                DefaultElement d = (DefaultElement) chi[l];
                                d.setText(getUpdatedConnectionUrl(d.getText(), config));
                            }
                    }
                }
            }
        }
    }

    private void setROIOutputPluginConfig(Map<String, String> config, Object[] child) {

        // set the ipaddress/servername for the roi.outputplugin
        DefaultElement ipAddressEle = (DefaultElement) child[1];
        Attribute ipAddressAtt = ipAddressEle.attribute("value");
        ipAddressAtt.setText(config.get(ConfigProps.IP_ADDRESS));

        // set the port number for the roi.outputplugin
        DefaultElement portNoEle = (DefaultElement) child[2];
        Attribute portNoAtt = portNoEle.attribute("value");
        portNoAtt.setText(config.get(ConfigProps.PORT_NO));
    }

    /**
     * This method is to get the XML Document
     * @param fileName
     * @return Document
     * @throws DocumentException
     */
    public Document getXMLDocument(String fileName)
    throws DocumentException {

        File xmlFile = new File(fileName);

        SAXReader reader = new SAXReader(false);

        // CR# 378,148 - Fix
        // if the Parsing XML contains the DOCTYPE element
        // validation of those elements are ignored, as per our custom Entity Handler.
        reader.setEntityResolver(new EntityResolver() {

			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException,
					IOException {

				LOG.debug("Ignored:" + publicId + ", " + systemId);
				return new InputSource(new StringReader(""));
			}
		});
        Document read = reader.read(xmlFile);
		return read;
    }

    /**
     * This method is to update the XML Document
     * @param doc Document
     * @param fileName
     * @throws IOException
     */
    public void updateXMLDocument(Document doc, String fileName)
    throws IOException {

    	// added to maintain the existing encoding format
    	String encoding = doc.getXMLEncoding();
    	if (null == encoding) {
    		encoding = "UTF-8";
    	}

    	OutputFormat outputFormat = new OutputFormat();
    	outputFormat.setEncoding(encoding);

        XMLWriter output = new XMLWriter(new FileWriter(new File(fileName)),
        								 outputFormat);
        output.write(doc);
        output.close();
    }

    protected long writeMPFWFileConfigFile(String url, String key, String fileName) {

		LOG.debug("Entered writeMPFWFileConfigFile(file:" + fileName + ")");
		String line;
    	long pointer = 0;
    	RandomAccessFile writer = null;

    	File file = new File(fileName);

		try {

			writer = new RandomAccessFile(file, "rw");

			while ((line = writer.readLine()) != null) {
				 line = line.trim();

				 if (line.toLowerCase().contains(key)) {

					 LOG.debug("read line: " + line);
					 line.substring(25);
					 writer.seek(pointer);
					 writer.writeBytes(url);
					 break;
				 }
				 pointer = writer.getFilePointer();
			 }
			return pointer;

		} catch (Exception e) {
			 LOG.debug(e);
	           e.printStackTrace();
	           throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.write.bat.file"),
	                                                                       e.getCause());
		} finally {
			close(writer);
			LOG.debug("Exit writeUpdateSetUp(file:" + file + ")");
		}
	}

    /**
     * This method is to update the XML Document
     * @param doc Document
     * @param fileName
     * @throws IOException
     */
    public void updateHPFWFile(Document doc, String fileName)
    		throws IOException {

    	XMLWriter output = new XMLWriter(new FileWriter(new File(fileName)),
    			OutputFormat.createPrettyPrint());
    	output.write(doc);
    	output.close();
    }

    /**
     * This method is to element value from the corresponding file
     * @param doc Document
     * @param xPath
     * @param matchVal matching string
     * @return element value
     */
    public String getMatchedElementValue(Document doc, String xPath, String matchVal) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = doc.selectNodes(xPath);
        for (Element element : elements) {
            if (element.getText().contains(matchVal)) {
                return element.getText();
            }
        }
        return null;
    }

    /**
     * This method is to get the jboss memory details
     * @param memoryString
     * @param memoryType
     * @return jboss memory details
     */
    public String getJbossMemoryDetails(String memoryString, String memoryType) {

        String[] values         = memoryString.split(memoryType, 2);
        String[] memoryDetails  = values[1].split("m", 2);
        return memoryDetails[0];
    }

    /**
     * This method is to update the jboss memory details
     * @param memoryString
     * @param matchexp
     * @param memoryTye
     * @param updatedValue
     * @return updated jboss memory details
     */
    public String updateJbossMemoryDetails(String memoryString,
                                            String matchexp,
                                            String memoryTye,
                                            String updatedValue) {

        Pattern pattern = Pattern.compile(matchexp);
        Matcher matcher = pattern.matcher(memoryString);
        String updatedMemory = memoryTye + updatedValue + "m";
        memoryString = matcher.replaceAll(updatedMemory);
        return memoryString;
    }

    /**
     * This method is to update the service details
     * @param isHpf true/false
     * @param configParams
     * @param document Document
     */
    public void updateServiceDetails(String service,
                                     Map<String, String> configParams,
                                     Document document) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = document.selectNodes(_servicesPath);

        for (Element element : elements) {
            if (element.getText().contains(service)) {
            	element.setText(getUpdatedClientConnectionUrl(element.getText(), configParams));
            }
        }
    }

    public void updateServletUrlDetails(Document document, Map<String, String> config, String url) {

        //Updating File upload, File download, Report servlet url
        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Element> elements = document.selectNodes(url);
        Element urlElement = elements.get(0);

        String existingUrl = urlElement.attribute("value").getText();
        urlElement.attribute("value").setText(getUpdatedClientConnectionUrl(existingUrl, config));

    }

    private String getUpdatedConnectionUrl(String url, Map<String, String> configParams) {

        String[] values         = url.split("//");
        String[] serviceDetails = values[1].split("/", 2);
        String[] ipAndPort      = serviceDetails[0].split(":");

        String updatedUrl       = values[0] + "//"
        							+ configParams.get(ConfigProps.IP_ADDRESS) + ":"
        							+ configParams.get(ConfigProps.PORT_NO) + "/"
        							+ serviceDetails[1];

        return updatedUrl;
    }

    private String getUpdatedClientConnectionUrl(String url, Map<String, String> configParams) {

    	String[] values         = url.split("//");
    	String[] serviceDetails = values[1].split("/", 2);

    	String updatedUrl       = configParams.get(ConfigProps.PROTOCOL) + "://"
    								+ configParams.get(ConfigProps.IP_ADDRESS) + ":"
    								+ configParams.get(ConfigProps.PORT_NO) + "/"
    								+ serviceDetails[1];

    	return updatedUrl;
    }

    public void updateContentUrlDetails(Document document, Map<String, String> config, String url) {

    	//Updating File upload, File download, Report servlet url
    	@SuppressWarnings("unchecked") //not supported by 3PartyAPI
    	List<Element> elements = document.selectNodes(url);
    	Element urlElement = elements.get(0);

    	String existingUrl = urlElement.attribute("value").getText();
    	urlElement.attribute("value").setText(getUpdatedContentConnectionUrl(existingUrl, config));

    }

    private String getUpdatedContentConnectionUrl(String url, Map<String, String> configParams) {

    	String[] values         = url.split("//");
    	String[] serviceDetails = values[1].split("/", 2);
    	String[] ipAndPort      = serviceDetails[0].split(":");
    	String updatedUrl       = configParams.get(ConfigProps.PROTOCOL) + "://"
    									+ configParams.get(ConfigProps.IP_ADDRESS) + ":"
    									+ configParams.get(ConfigProps.PORT_NO);

    	return updatedUrl;
    }

    public Map <String, String> getIpAddressAndPortDetails(String configDetails) {

        Map<String, String> configParams = new HashMap<String, String>();
        String[] values         = configDetails.split("//");
        String[] serviceDetails = values[1].split("/", 2);
        String[] ipAndPort      = serviceDetails[0].split(":");
        String serverName = setServerName(ipAndPort[0]);
        configParams.put(ConfigProps.IP_ADDRESS, serverName);
        configParams.put(ConfigProps.PORT_NO, ipAndPort[1]);

        return configParams;
    }

    public ConfigurationControllerDAO getXMLEndpointDAO() {

        return ConfigurationControllerDAO.getInstance(ConfigProps.CONFIG_UTIL_CASTOR,
                                                      ConfigProps.OUTPUT_DEST_TYPES_FILE,
                                                      ConfigProps.OUTPUT_DEST_DEFS_FILE);

    }

    public String configDetails(Map<String, String> configParams) {

        return new StringBuffer().append("Port No : ")
                                 .append(configParams.get(ConfigProps.PORT_NO))
                                 .append(", Ip Address : ")
                                 .append(configParams.get(ConfigProps.IP_ADDRESS))
                                 .toString();
    }

    public String setDBServerName(String dbServerName) {

        if (ConfigProps.SERVER_URL.equalsIgnoreCase(dbServerName)) {
            return ConfigProps.DEFAULT_DB_SERVER_NAME;
        }

        return dbServerName;
    }

    public String setServerName(String serverName) {

        if (ConfigProps.SERVER_URL.equalsIgnoreCase(serverName)) {
            return ConfigProps.DEFAULT_SERVER_NAME;
        }

        return serverName;
    }

	@Override
	public Object loadConfigParams() {

    	// Check if the autoconfiguration is exists, if so
    	// it will take the configuration parameters from auto.config.xml
    	if (AutoConfigurator.isConfigExists()) {

    		Map<String, String> configParams = AutoConfigurator.getConfigParams(getTabSource());
    		if (CollectionUtilities.hasContent(configParams)) {
    			return configParams;
    		}
    	} else {
    		AutoConfigurator.addProperty("isUpdated", "false");
    	}

    	// load the config params from extended sub class implementation.
    	return load();
    }

	@Override
	public void saveConfigParams(Object object) {

		// Save the config params to configuration files.
		save(object);

		// if the iproperty is updated not exists in the auto configurator, initialized to false
		if (!AutoConfigurator.getProps().containsKey("isUpdated")) {
			AutoConfigurator.addProperty("isUpdated", "false");
		}
		// validates the new value with the old value
		Map<String, String> newProps = (Map<String, String>) object;
		Map<String, String> oldProps = AutoConfigurator.getConfigParams(getTabSource());

		Set<Entry<String,String>> entrySet = newProps.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {

			Entry<String, String> next = iterator.next();
			String key = next.getKey();
			String value = next.getValue();

			if (!oldProps.containsKey(key)
					|| !value.equalsIgnoreCase(oldProps.get(key))) {

				// adds the property to the AutoConfigurator
				AutoConfigurator.addProperty("isUpdated", "true");
				break;
			}
		}

    	// set properties for Export properties
		AutoConfigurator.addProperties(newProps, getTabSource());
    }

    /**
     * This method is used to save all the parameters
     * @param object
     */
    protected abstract void save(Object object);

    /**
     * This method is used to load the config params.
     * @return
     */
    protected abstract Object load();

    /**
     * This method is used to identify which TabSource to process
     * @return
     */
    protected abstract TabSource getTabSource();

    public String getFilePath(String configFile)
    throws IOException {

    	File f = new File(configFile);
    	return f.toURI().getPath();

    }

    /*************************** Code Added for SSL ********************************/

    private boolean checkSSLEnabled(){

    	String configPath = getConfigPath();
    	boolean sslEnabled = false;

  		 LOG.debug("Server.xml path ------>"+configPath + CONFIG_FILE);
    	 if (new File(configPath + CONFIG_FILE).exists()) {
             BufferedReader file = null;

             String line;
             try {
                 file = new BufferedReader(new FileReader(configPath + CONFIG_FILE));
                 while ((line = file.readLine()) != null) {
                     if (line.indexOf("MCKESSON SSL ENABLED CONFIG START")>0) {
                    	 sslEnabled = true;
                     } else if (line.indexOf("MCKESSON SSL DISABLED CONFIG START")>0) {
                    	 sslEnabled = false;
                     }
                 }
             } catch(Exception ex) {
                 LOG.error(ex.getMessage());
             } finally {
                 if (file != null) {
                     try {
                         file.close();
                     } catch(Exception ex2) {
                    	 LOG.error(ex2.getMessage());
                     }
                 }
             }
         } else {
        	 LOG.error("Not a valid JBoss home folder.");
         }

    	 return sslEnabled;
    }

    /**
     * Method getConfigPath() returns the config path
     * @return String
     */
    private String getConfigPath()
    {
    	String tokens[] = getAppPath().split("roi.config.util");
    	String configPath = tokens[0];

        configPath += "jboss" + File.separator
        		+"server" + File.separator
                + "default" + File.separator
                + "deploy" + File.separator
                + "jbossweb.sar" + File.separator;
        LOG.debug("Using Config Path: " + configPath);
        return configPath;
    }

    /**
     * Method getAppPath fetches the application path
     * @return String
     */
    private String getAppPath() {
        URL url = this.getClass().getResource(this.getClass().getSimpleName() + ".class");
        String appPath;
        if(url==null) {
            File currentDir = new File("");
            appPath = currentDir.getAbsolutePath();
            LOG.debug("Can not determine app path. Using " + appPath);
        } else {
            appPath = url.getPath();
        }
        appPath = cleanUrlPath(appPath);
        if (!appPath.endsWith(File.separator)) appPath += File.separator;
        LOG.debug("Using application path: " + appPath);
        return appPath;
    }

    /**
     * Method cleanUrlPath
     * @param urlPath
     * @return String
     */
    private String cleanUrlPath(String urlPath) {
        String path = urlPath;
        try {
            path = URLDecoder.decode(urlPath, "UTF-8");
            if(path.startsWith("/"))path=path.substring(1);
            if(path.startsWith("file:/"))path=path.substring(6);
            String className = this.getClass().getSimpleName() + ".class";
            if(path.endsWith(className))path=path.substring(0, path.length()-className.length());
        } catch(Exception ex) {
            LOG.error(ex.getMessage());
        }
        return path;
    }

	/**
	 * This method used to updated the UpdateSetUp.bat file version
	 * @param writer
	 * @param pointer
	 * @return
	 * @throws IOException
	 */
	private long writeUpdateSetUp(File file) {

		LOG.debug("Entered writeUpdateSetUp(file:" + file + ")");
		String line;
		String version;
		String buildver;
    	long pointer = 0;
    	RandomAccessFile writer = null;

		try {

			writer = new RandomAccessFile(file, "rw");

			while ((line = writer.readLine()) != null) {
				 line = line.trim();

				 if (!line.toLowerCase().startsWith("rem")
						 && line.toLowerCase().contains("set clientversion=")) {

					 LOG.debug("read line: " + line);
					 version = line.substring(18);
					 buildver = version.substring(version.lastIndexOf(".") + 1);
					 int res = Integer.parseInt(buildver) + 1;
					 LOG.debug("updated version to : " + res);
					 writer.seek(pointer);

					 String s = line.substring(0,18) +
							    version.substring(0, version.lastIndexOf(".") + 1) + res;
					 writer.writeBytes(s);
					 break;
				 }
				 pointer = writer.getFilePointer();
			 }
			return pointer;

		} catch (Exception e) {
			 LOG.debug(e);
	           e.printStackTrace();
	           throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.write.bat.file"),
	                                                                       e.getCause());
		} finally {
			close(writer);
			LOG.debug("Exit writeUpdateSetUp(file:" + file + ")");
		}
	}

	/**
	 * This method is to close the writer
	 * @param writer Closeable
	 */
    protected void close(Closeable writer) {

    	if (null == writer) {
    		return;
    	}

		try {
			writer.close();
		} catch (IOException e) {
			LOG.debug(e);
            e.printStackTrace();
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.close.file"),
                                          e.getCause());
        }
    }

    public void writeMPFWFileConfigFile(Properties p, String hpfwDbConnectionConfigFile) {

    	File f = new File(hpfwDbConnectionConfigFile);
    	Set<Entry<Object,Object>> entrySet = p.entrySet();
    	Iterator<Entry<Object, Object>> iterator = entrySet.iterator();

    	FileOutputStream fileStream = null;

    	try {

			fileStream = new FileOutputStream(f);
			while (iterator.hasNext()) {

				Entry<Object, Object> next = iterator.next();
				String key = (String) next.getKey();
				String value = (String) next.getValue();
				String lineData = key + "=" + value + "\n";
				fileStream.write(lineData.getBytes());
			}
		} catch (Exception e) {
			LOG.debug(e);
			throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.mpfw.db.prop"),
                    e.getCause());
		} finally {
			close(fileStream);
		}


	}
    
    /**
	 * Save the data into propeties file
	 * @param file
	 * @param key
	 * @param value
	 */
	public void saveProperties(File file, String key, String value) {
		
		if (!file.exists()) {
			return;
		}

		FileReader reader = null;
		FileWriter writer = null;
		BufferedReader bufReader = null;
		
		try {

			reader = new FileReader(file);
			bufReader = new BufferedReader(reader);

			String lineData;
			StringBuffer updatedData = new StringBuffer();
			while ((lineData = bufReader.readLine()) != null) {

				String trimmedData = lineData.trim();
				
				if (trimmedData.startsWith("#")) {
					updatedData.append(trimmedData + newLine);
					continue;
				}

				if (trimmedData.startsWith(key + "=")) {
					updatedData.append(key + "=" + value);					
				} else {
					updatedData.append(trimmedData);
				}
				
				updatedData.append(newLine);
			}

			writer = new FileWriter(file);
			writer.write(updatedData.toString());

		} catch (Exception ex) {
			LOG.error("Exception occurred while updating File:"
					+ ConfigProps.SERVER_EIG_OUTPUT_SETTINGS_FILE, ex);
			throw new ConfigUtilException(
					ConfigUtilMessages.getMessage("unable.update.outputsettings.properties"), 
					ex);
		} finally {

			close(bufReader);
			close(reader);
			close(writer);
		}
	}

}
