/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.conversion.processor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * @author bhanu
 *
 */
public abstract class UnitOfWork implements Runnable {

	private static final Logger logger = LogManager.getLogger(UnitOfWork.class);
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		UnitOfWork.logger.debug("Entering");
		this.execute();
		UnitOfWork.logger.debug("Exiting");
	}
	
	public abstract void execute();

}
