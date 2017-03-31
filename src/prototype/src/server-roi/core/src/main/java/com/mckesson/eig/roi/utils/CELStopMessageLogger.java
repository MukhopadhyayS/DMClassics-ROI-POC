/**
 * 
 */
package com.mckesson.eig.roi.utils;
import com.mckesson.eig.roi.base.model.*;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * @ Implements ROI JBoss Shutdown Logging
 *
 */

public class CELStopMessageLogger implements ShutdownHook {

	private static final Log LOG = LogFactory.getLogger(CELStopMessageLogger.class);
	
	@Override
	public void shutdown() {
		LOG.error(System.getenv("COMPUTERNAME")+ ": ROI server stopped.");
	}
}
