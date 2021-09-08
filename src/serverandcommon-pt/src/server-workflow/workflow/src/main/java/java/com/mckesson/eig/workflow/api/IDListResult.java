/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.api;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Sep 12, 2007
 * @since  HECM 1.0
 *
 * Data structure to provide the result of any operation on one/more
 * entities and out of which some succeed and some fail.
 * This wrapper holds all the processed, failed entities along with their ids
 * and also the error codes for the failed entities.
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlType(name = "IDListResult", namespace = EIGConstants.TYPE_NS_V1)
public class IDListResult
extends BasicWorkflowDO {

    /**
     * Holds all the processed entities.
     */
    private List<Worklist> _processed;

    /**
     * Holds all the processed IDs.
     */
    private List<Long> _processedIDs;

    /**
     * Holds all the failed entities.
     */
    private List<Worklist> _failed;

    /**
     * Holds all the failed IDs.
     */
    private List<Long> _failedIDs;

    /**
     * Holds all the error codes.
     */
    private List<String> _errorCodes;

    /**
     * This constructor instantiates this wrapper with empty lists.
     */
    public IDListResult() {

        _processed    = Collections.EMPTY_LIST;
        _processedIDs = Collections.EMPTY_LIST;
        _failed       = Collections.EMPTY_LIST;
        _failedIDs    = Collections.EMPTY_LIST;
        _errorCodes   = Collections.EMPTY_LIST;
    }

    /**
     * This method is used to retrieve the processed entities.
     * @return processed entities
     */
    public List getProcessed() {
        return _processed;
    }

    /**
     * This method is used to set the processed entities.
     * @param processed entities
     */
    public void setProcessed(List processed) {
        _processed = processed;
    }

    /**
     * This method is used to retrieve the processed ids.
     * @return processedIDs
     */
    public List getProcessedIDs() {
        return _processedIDs;
    }

    /**
     * This method is used to set the processed ids.
     * @param processedIDs
     */
    @XmlElement(name = "processedIDs", type = Long.class)
    public void setProcessedIDs(List processedIDs) {
        _processedIDs = processedIDs;
    }

    /**
     * This method is used to retrieve the failed entities.
     * @return failed entities
     */
    public List getFailed() {
        return _failed;
    }

    /**
     * This method is used to set the failed entities.
     * @param failed entities
     */
    public void setFailed(List failed) {
        _failed = failed;
    }

    /**
     * This method is used to retrieve the failed ids.
     * @return failedIDs
     */
    public List getFailedIDs() {
        return _failedIDs;
    }

    /**
     * This method is used to set the failed ids.
     * @param failedIDs
     */
    @XmlElement(name = "unprocessedIDs", type = Long.class)
    public void setFailedIDs(List failedIDs) {
        _failedIDs = failedIDs;
    }

    /**
     * This method is used to retrieve the error codes of the failed ids.
     * @return errorCodes
     */
    public List getErrorCodes() {
        return _errorCodes;
    }

    /**
     * This method is used to set the error codes of the failed ids.
     * @param errorCodes
     */
    @XmlElement(name = "errorCodes", type = String.class)
    public void setErrorCodes(List errorCodes) {
        _errorCodes = errorCodes;
    }
}
