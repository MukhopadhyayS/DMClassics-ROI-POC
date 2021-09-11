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

package com.mckesson.eig.workflow.util;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.jms.MessagePoster;

public final class QueueUtil {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( QueueUtil.class);

    private QueueUtil() {

    }

    /**
     * Writes message string to a queue.
     *
     * Queue's message poster must be defined in spring configurations.
     *
     * @param msg
     * @param queueName
     * @throws Exception
     */
    public static void writeMessageToQueue(String msg, String queueProducerName)
            throws Exception {

        MessagePoster messagePoster = (MessagePoster) SpringUtilities
                .getInstance().getBeanFactory().getBean(queueProducerName);
        String soapMessage = SOAPWrapper.buildSoapEnvelope(msg);
        LOG.debug("Put message using queue producer: " + queueProducerName + " = " + soapMessage);
        messagePoster.sendTextMessage(soapMessage);
    }
}
