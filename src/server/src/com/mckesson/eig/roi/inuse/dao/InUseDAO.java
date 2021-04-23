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

package com.mckesson.eig.roi.inuse.dao;

import java.util.List;

import com.mckesson.eig.roi.inuse.model.InUseRecord;

public interface InUseDAO {

    /**
     * This method creates a new InUseRecord.
     *
     * @param inUseRecord InUseRecord details to be created
     * @return Unique Id of the InUseRecord
     */
    InUseRecord createRecord(InUseRecord inUseRecord);

    /**
     * This method fetches all InUseRecord's for a particular object type
     *
     * @param objectType type of records to retrieve
     * @return List of all InUseRecord's
     */
    List<InUseRecord> retrieveAllByType(String objectType);

    /**
     * This method updates the InUseRecord details
     *
     * @param inUseRecord Details of the InUseRecord to be updated
     * @return Updated InUseRecord
     */
    InUseRecord updateRecord(InUseRecord inUseRecord);

    /**
     * This method fetches the InUseRecord details by objectID and objectType
     *
     * @param objectType the type of object in use
     * @param objectID the foreign key to the object to which the record is referring
     * @return record if found or <code>null</code>
     */
    InUseRecord retrieveByObjectTypeAndID(String objectType, String objectID);

    /**
     * This method deletes the selected InUseRecord
     *
     * @param inUseRecord InUseRecord to be deleted
     */
    void deleteRecord(InUseRecord inUseRecord);

    /**
     * retrieves records where modifiedDate + expiresMinutes < now
     *
     * @param gracePeriodMinutes time to add to expired minutes to calculate if records are expired
     * @return list of records that are expired
     */
    List<InUseRecord> retrieveExpiredRecords(int gracePeriodMinutes);

    /**
     * retrieves all records
     *
     * @return return list of records
     */
    List<InUseRecord> retrieveAll();

    /**
     * @param inUseRecords records to not delete
     */
    void deleteRecords(List<InUseRecord> inUseRecords);

    /**
     * retrieves the object ids based on object type and object ids
     * @param objType
     * @param objIds
     * @param appId
     * @param userId
     * @return List of InUseRecord
     */
    List<InUseRecord> retrieveInUseRecordsByIds(String objType,
                                                String objIds,
                                                String appId,
                                                String userId);

    /**
     * This method retrieves Inuse record base on application id
     * @param appID
     * @param userID
     * @return InUse record
     */
    InUseRecord retrieveRecordByAppIdAndUserID(String appId, String userID);

    void deleteRecordsOnInit(List<InUseRecord> records);
}
