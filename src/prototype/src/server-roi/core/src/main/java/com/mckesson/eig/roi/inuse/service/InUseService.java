/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.roi.inuse.service;

import java.util.List;

import com.mckesson.eig.roi.inuse.model.InUseRecord;
import com.mckesson.eig.roi.inuse.model.InUseRecordList;

/**
 * Service for posting and querying "in-use" status of records
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public interface InUseService {

    /**
     * Create's a new "in-use" record. If the record identified by <code>objectType</code> and
     * <code>objectID</code> already exists, an exception will be thrown
     *
     * @param objectType type of object which is in-use
     * @param objectID unique id of the object for the type
     * @param applicationID application specific data
     * @param userID userID of the user who has the object in use
     * @param expiresMinutes amount of time to keep the record in use.
     * @return new record
     */
    InUseRecord createInUseRecord(
            String objectType,
            String objectID,
            String applicationID,
            String userID,
            int expiresMinutes);

    /**
     * Updates the last modified by time of the record. If a record of objectType and objectID
     * does not exist or if the record exists but the calling user and the applicationID fields
     * do not match, an exception will be thrown
     *
     * @param objectType type of object which is in-use
     * @param objectID unique id of the object for the type
     * @param applicationID application specific data
     * @param userID userID of the user who created the record
     * @return modified record
     */
    InUseRecord touchInUseRecord(
            String objectType,
            String objectID,
            String applicationID,
            String userID);

    /**
     * Releases the record. If a record of objectType and objectID does not exist or if the
     * record exists but the calling user and the applicationID fields do not match, an exception
     * will be thrown
     *
     * @param objectType type of object which is in-use
     * @param objectID unique id of the object for the type
     * @param applicationID application specific data
     * @param userID userID who created the record
     */
    void releaseInUseRecord(
            String objectType,
            String objectID,
            String applicationID,
            String userID);


    /**
     * Retrieves a single record
     *
     * @param objectType type of object which is in-use
     * @param objectID unique id of the object for the type
     * @return null if not found
     */
    InUseRecord retrieveInUseRecord(String objectType, String objectID);

    /**
     * Retrieve all records of a given objectType
     *
     * @param objectType type of objects is in-use
     * @return not null
     */
    List<InUseRecord> retrieveInUseRecordsByType(String objectType);

    /**
     * Retrieve all records
     *
     * @return not null
     */
    List<InUseRecord> retrieveAllInUseRecords();

    /**
     * Deletes all expired records
     *
     * @return not null
     */
    List<InUseRecord> clearExpiredRecords();

    /**
     * @return
     */
    int getGracePeriodMinutes();

    /**
     * @param gracePeriodMinutes
     */
    void setGracePeriodMinutes(int gracePeriodMinutes);

    /**
     * This method retrieves InUse object ids based on the input object ids
     * @param objectType Object type
     * @param idsCSV Object ids as CSV
     * @param appId
     * @param userId
     * @return InUseRecordList
     */
    InUseRecordList retrieveInUseRecordsByIDs(String objectType,
                                                String idsCSV,
                                                String appId,
                                                String userId);
}
