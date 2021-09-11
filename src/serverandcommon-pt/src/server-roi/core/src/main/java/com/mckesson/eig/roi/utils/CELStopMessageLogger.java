/**
 * 
 */
package com.mckesson.eig.roi.utils;
import com.mckesson.eig.roi.base.model.*;
import com.mckesson.dm.core.common.logging.OCLogger;
/**
 * @ Implements ROI JBoss Shutdown Logging
 *
 */

public class CELStopMessageLogger implements ShutdownHook {

	private static final OCLogger LOG = new OCLogger(CELStopMessageLogger.class);
	
	@Override
	public void shutdown() {
		LOG.error(System.getenv("COMPUTERNAME")+ ": ROI server stopped.");
	}
}
