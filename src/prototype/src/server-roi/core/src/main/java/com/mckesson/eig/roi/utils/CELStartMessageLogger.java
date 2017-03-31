/**
 * 
 */
package com.mckesson.eig.roi.utils;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public class CELStartMessageLogger {

	private static final Log LOG = LogFactory.getLogger(CELStartMessageLogger.class);

	public void init() {

		new Thread() {

			public void run() {

				do {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						LOG.debug(e);
					}
				} while (!logMessage());
			}

		}.start();
	}
	protected boolean logMessage() {
		LOG.error(System.getenv("COMPUTERNAME")+": ROI server started.");
		return true;
	}
}
