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
package com.mckesson.eig.utility.components;

import java.util.jar.Attributes;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author OFS
 *
 * @date Apr 30, 2009
 * @since HECM 1.0.3; Apr 30, 2009
 */
public class DeploymentEntropy {

	/** 
     * Object represents the Log4JWrapper object.
     */
    private static final OCLogger LOG = new OCLogger(DeploymentEntropy.class);

    /**
     * Title key found in the ear or war
     */
    private static final String TITLE = "Implementation-Title"; 

    /**
     * Version key found in the ear or war
     */
    private static final String VERSION = "Implementation-Version";

    /**
     * Vendor key found in the ear or war
     */
    private static final String VENDOR = "Implementation-Vendor";

    /**
     * Found the name of archive
     */
    private String _archiveName;

	/**
	 * @param archivePath
	 */
	public DeploymentEntropy(String archivePath) {
		_archiveName = archivePath;
	}

	/**
	 * Reveal the deployment information such as Title and Version 
	 */
	public void revealDeployment() {

		String deployPath = ComponentUtil.APP_HOME + "/deploy/";
        Attributes attributes = ComponentUtil.getManifestAttributes(deployPath + _archiveName);
		if (attributes == null) {

            LOG.debug("Unable to find the Manifest Information in : " + deployPath + "/" 
                                                                      + _archiveName);
			return;
		}

		StringBuffer sb = new StringBuffer()
						  .append(attributes.getValue(TITLE))
						  .append(" : ")
						  .append(attributes.getValue(VENDOR)) 
						  .append(", Version : ")
						  .append(attributes.getValue(VERSION));
		LOG.debug(sb.toString());
	}
}
