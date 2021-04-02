/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.utility.log.log4j.appender;

/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  Mar 17, 2014
 */
import java.util.Properties;

import org.apache.log4j.net.SMTPAppender;


/**
 * <p>
 * This class acts a appender for email log 4j configuration
 * </p>
 *
 * @see org.apache.log4j.net.SMTPAppender;
 *
 */
public class EmailLoggingAppender 
extends SMTPAppender {

	/**
	 * loads the properties from the given property file
	 * @param filename
	 */
	public void setConnectionPropertiesFile(String filename) {

		try {

			Properties prop = new Properties();
			prop.load(EmailLoggingAppender.class.getClassLoader().getResourceAsStream(filename));
			setFrom(prop.getProperty("email.appender.from.address"));
			setTo(prop.getProperty("email.appender.from.address"));
			setSMTPHost(prop.getProperty("email.appender.SMTPHost"));
			setSubject(prop.getProperty("email.appender.Subject"));

		} catch (Exception e) {
			// Unfortunately if we catch an exception here, we cannot
			// log using an appender.
			// So calling LOG.error(e) will cause an infinite loop
			// Instead show the error and stace trace in System.out
			e.printStackTrace(System.out);
		}
	}
}
