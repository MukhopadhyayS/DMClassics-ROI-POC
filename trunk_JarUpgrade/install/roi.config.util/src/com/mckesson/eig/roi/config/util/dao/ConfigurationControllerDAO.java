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

package com.mckesson.eig.roi.config.util.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import org.apache.log4j.Logger;
import org.castor.mapping.BindingType;
import org.castor.mapping.MappingUnmarshaller;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.ClassDescriptorResolverFactory;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLClassDescriptorResolver;

import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.model.EndPointTypeList;
import com.mckesson.eig.roi.config.util.model.EndPointDefList;
import com.mckesson.eig.utility.io.FileLoader;


/**
 *
 * @author OFS
 * @date   Mar 9, 2009
 * @since  HPF 13.1 [ROI]; Mar 9, 2009
 */
public class ConfigurationControllerDAO {

    private static final Logger LOG = Logger.getLogger(ConfigurationControllerDAO.class);

	private XMLClassDescriptorResolver _resolver;
	private File _endpointDefDataFile;
	private File _endpointTypeDataFile;
	private static ConfigurationControllerDAO _instance = null;

	public ConfigurationControllerDAO() { }

	/**
	 *
	 * @param castorMapping
	 * @param endpointTypeFile
	 * @param endpointFile
	 */
	private ConfigurationControllerDAO(String castorMapping,
	                                   String endpointTypeFile,
	                                   String endpointDefFile) {

	    _endpointDefDataFile = resolveToFile(endpointDefFile);
		_endpointTypeDataFile = resolveToFile(endpointTypeFile);

		URL url = resolveToURL(castorMapping, "castorMapping");
		_resolver = (XMLClassDescriptorResolver) ClassDescriptorResolverFactory
				.createClassDescriptorResolver(BindingType.XML);

		Mapping mapping = new Mapping(getClass().getClassLoader());

		try {

			mapping.loadMapping(url);

			MappingUnmarshaller mappingUnmarshaller = new MappingUnmarshaller();
			_resolver.setMappingLoader(mappingUnmarshaller.getMappingLoader(
					mapping, BindingType.XML));

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public static ConfigurationControllerDAO getInstance(String castorMapping,
	                                                     String endpointTypeFile,
	                                                     String endpointDefFile) {

	    if (_instance ==  null) {

	        _instance = new ConfigurationControllerDAO(castorMapping,
	                                                   endpointTypeFile,
	                                                   endpointDefFile);
        }
	    return _instance;
	}

	private static URL resolveToURL(String path, String variableName) {
		if (path == null) {
			throw new IllegalArgumentException(variableName + " argument is null");
		}


		URL url = FileLoader.getResourceAsUrl(path);
		if (url == null) {
			throw new IllegalArgumentException(
					variableName + " argument " + path + " is not valid");
		}

		return url;
	}

	private static File resolveToFile(String path) {
		if (path == null) {
			throw new IllegalArgumentException("path is null");
		}


		File file = FileLoader.getResourceAsFile(path);
		if (file == null) {
			file = new File(path);
			if ((file.exists() && !file.canRead())
					|| !file.getParentFile().canWrite()) {
				throw new IllegalArgumentException(path + " is not valid");
			}
		}

		return file;
	}

	@SuppressWarnings("unchecked")
	private Object readFile(File f) {

		Unmarshaller um = new Unmarshaller();
		um.setResolver(_resolver);

		FileReader reader = null;
		try {

		    if (f.exists()) {

		        reader = new FileReader(f);
		        return um.unmarshal(reader);
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.read.file"), ex.getCause());

		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException iox) {
				LOG.debug("error closing: " + f, iox);
			}
		}
		return null;
	}

	private void writeFile(File f, EndPointTypeList data) {

		Writer writer = null;

		try {

			writer = new FileWriter(f, false);
			Marshaller m = new Marshaller(writer);
			m.setResolver(_resolver);
			m.marshal(data);

		} catch (Exception ex) {
		    ex.printStackTrace();
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.write.file"), ex.getCause());

		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
				LOG.debug("error closing: " + f, ex);
			}
		}
	}

	private void writeFile(File f, EndPointDefList data) {

	    Writer writer = null;

        try {

            writer = new FileWriter(f, false);
            Marshaller m = new Marshaller(writer);
            m.setResolver(_resolver);
            m.marshal(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConfigUtilException(ConfigUtilMessages
                                          .getMessage("unable.write.file"), ex.getCause());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception ex) {
                LOG.debug("error closing: " + f, ex);
            }
        }
    }

	public EndPointTypeList readAllEndpointTypeDefs() {
		return readEndpointTypeDefData();
	}

    private EndPointTypeList readEndpointTypeDefData() {
        return (EndPointTypeList) readFile(_endpointTypeDataFile);
    }

    public void writeEndpointTypeData(EndPointTypeList data) {
        writeFile(_endpointTypeDataFile, data);
    }

    public EndPointDefList readAllEndpointDefs() {
        return readEndpointDefData();
    }

    private EndPointDefList readEndpointDefData() {
        return (EndPointDefList) readFile(_endpointDefDataFile);
    }

    public void writeEndpointDefData(EndPointDefList data) {
        writeFile(_endpointDefDataFile, data);
    }

}
