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
package com.mckesson.eig.workflow.process.datavault;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.datavault.DVLogger;
import com.mckesson.eig.workflow.datavault.DVUtil;
import com.mckesson.eig.workflow.process.datavault.model.ProcessAssignDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessAttributeDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessOwnerDVInfo;
import com.mckesson.eig.workflow.process.datavault.model.ProcessVersionDVInfo;

/**
 * @author OFS
 *
 * @date Apr 3, 2009
 * @since HECM 1.0.3; Apr 3, 2009
 */
public final class ProcessDVHelper {

    private ProcessDVHelper() {
    }

    //CONSTANTS
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static final int SHEET_NUMBER_ZERO     = 0;
    public static final int SHEET_NUMBER_ONE      = 1;
    public static final int SHEET_NUMBER_TWO      = 2;
    public static final int SHEET_NUMBER_THREE    = 3;

    public static final int COLUMN_LENGTH_TWO     = 2;
    public static final int COLUMN_LENGTH_FOUR    = 4;
    public static final int COLUMN_LENGTH_FIVE    = 5;
    public static final int COLUMN_LENGTH_FIFTEEN = 15;
    public static final int COLUMN_LENGTH_SIXTEEN = 16;

    public static final int COLUMN_NUMBER_0  = 0;
    public static final int COLUMN_NUMBER_1  = 1;
    public static final int COLUMN_NUMBER_2  = 2;
    public static final int COLUMN_NUMBER_3  = 3;
    public static final int COLUMN_NUMBER_4  = 4;
    public static final int COLUMN_NUMBER_5  = 5;
    public static final int COLUMN_NUMBER_6  = 6;
    public static final int COLUMN_NUMBER_7  = 7;
    public static final int COLUMN_NUMBER_8  = 8;
    public static final int COLUMN_NUMBER_9  = 9;
    public static final int COLUMN_NUMBER_10 = 10;
    public static final int COLUMN_NUMBER_11 = 11;
    public static final int COLUMN_NUMBER_12 = 12;
    public static final int COLUMN_NUMBER_13 = 13;
    public static final int COLUMN_NUMBER_14 = 14;
    public static final int COLUMN_NUMBER_15 = 15;

    public static final int DOMAIN_ENTITY_TYPE = 1;
    public static final int GROUP_ENTITY_TYPE  = 2;
    public static final int USER_ENTITY_TYPE   = 3;

    public static final String ERROR_SHEET_NUMBER              	  		= "0";
    public static final String ERROR_PROCESS_NAME_MANDATORY    	  		= "1";
    public static final String ERROR_PROCESS_VERSION_MANDATORY 	  		= "2";
    public static final String ERROR_EXPIRE_DATE_MANDATORY     	  		= "3";
    public static final String ERROR_EXPIRE_DATE_INVALID       	  		= "4";
    public static final String ERROR_ATTRIBUTE_NAME_MANDATORY  	  		= "5";
    public static final String ERROR_ATTRIBUTE_VALUE_MANDATORY 	  		= "6";
    public static final String ERROR_OWNER_ACTOR_MANDATORY     	  		= "7";
    public static final String ERROR_INVALID_ACTOR_AND_ENTITY_TYPE 		= "8";
    public static final String ERROR_INVALID_ASSIGN_ACTOR_ENTITY_TYPE 	= "9";
    public static final String ERROR_FILE_READ                          = "10";
    public static final String ERROR_ACTOR_NAME	     	  				= "11";
    public static final String ERROR_OWNER_ACTOR_NAME	     	  		= "12";
    public static final String ERROR_PROCESS_NOT_EXIST	     	  		= "13";

    public static final String MSG_SHEET_NUMBER
                         = " ProcessDataVault does not support for invalid sheet number ";
    public static final String MSG_PROCESS_NAME_MANDATORY
                         = " PROCESS_NAME is mandatory field ";
    public static final String MSG_PROCESS_VERSION_MANDATORY
                         = " Process VERSION_ID is mandatory field ";
    public static final String MSG_EXPIRE_DATE_MANDATORY
                         = " Process Expire Date is mandatory field ";
    public static final String MSG_EXPIRE_DATE_INVALID
                         = " Process Expire Date must be greater than todays date ";
    public static final String MSG_ATTRIBUTE_NAME_MANDATORY
                         = " Attribute Name is mandatory field ";
    public static final String MSG_ATTRIBUTE_VALUE_MANDATORY
                         = " Attribute Value is mandatory field ";
    public static final String MSG_OWNER_ACTOR_MANDATORY
                         = " Owner Actor Id is mandatory field ";
    public static final String MSG_INVALID_ACTOR_AND_ENTITY_TYPE
                         = " Assign Actor Id is not of given entitytype in ProcessAssign ";
    public static final String MSG_INVALID_ASSIGN_ACTOR_ENTITY_TYPE
                         = " Process could not be assigned to Domain ";
    public static final String MSG_FILE_READ 
                         = " Error in reading the file content ";
    public static final String MSG_ACTOR_NAME 
                         = " Actor/Group does not exist ";
    public static final String MSG_OWNER_ACTOR_NAME 
                         = " Owner(Domain) does not exist ";
    public static final String MSG_PROCESS_NOT_EXIST 
    					 = " Process does not exist ";

    public static final String DEFAULT_ZERO    = "0";
    public static final String DEFAULT_VERSION = "1";
    public static final String DEFAULT_NO      = "N";
    public static final String DEFAULT_YES     = "Y";
    public static final String DEFAULT_STATUS  = "NEW";

    public static final String SHEET_NAME_ZERO  = "In Sheet 01_PROCESS_VERSION ";
    public static final String SHEET_NAME_ONE   = "In Sheet 02_PROCESS_ATTRIBUTES ";
    public static final String SHEET_NAME_TWO   = "In Sheet 03_PROCESS_ASSIGN ";
    public static final String SHEET_NAME_THREE = "In Sheet 04_PROCESS_OWNER ";

    public static final Map<String, String> ERROR_CODE_DESC_MAP = new HashMap<String, String>();

    static {

        ERROR_CODE_DESC_MAP.put(ERROR_SHEET_NUMBER, MSG_SHEET_NUMBER);
        ERROR_CODE_DESC_MAP.put(ERROR_PROCESS_NAME_MANDATORY, MSG_PROCESS_NAME_MANDATORY);
        ERROR_CODE_DESC_MAP.put(ERROR_PROCESS_VERSION_MANDATORY, MSG_PROCESS_VERSION_MANDATORY);
        ERROR_CODE_DESC_MAP.put(ERROR_EXPIRE_DATE_MANDATORY, MSG_EXPIRE_DATE_MANDATORY);
        ERROR_CODE_DESC_MAP.put(ERROR_EXPIRE_DATE_INVALID, MSG_EXPIRE_DATE_INVALID);
        ERROR_CODE_DESC_MAP.put(ERROR_ATTRIBUTE_NAME_MANDATORY, MSG_ATTRIBUTE_NAME_MANDATORY);
        ERROR_CODE_DESC_MAP.put(ERROR_ATTRIBUTE_VALUE_MANDATORY, MSG_ATTRIBUTE_VALUE_MANDATORY);
        ERROR_CODE_DESC_MAP.put(ERROR_OWNER_ACTOR_MANDATORY, MSG_OWNER_ACTOR_MANDATORY);
        ERROR_CODE_DESC_MAP.put(ERROR_INVALID_ACTOR_AND_ENTITY_TYPE,
                                                      MSG_INVALID_ACTOR_AND_ENTITY_TYPE);
        ERROR_CODE_DESC_MAP.put(ERROR_INVALID_ASSIGN_ACTOR_ENTITY_TYPE,
                                                      MSG_INVALID_ASSIGN_ACTOR_ENTITY_TYPE);
        ERROR_CODE_DESC_MAP.put(ERROR_ACTOR_NAME, MSG_ACTOR_NAME);
        ERROR_CODE_DESC_MAP.put(ERROR_OWNER_ACTOR_NAME, MSG_OWNER_ACTOR_NAME);
        ERROR_CODE_DESC_MAP.put(ERROR_PROCESS_NOT_EXIST, MSG_PROCESS_NOT_EXIST);
    }

    /**
     *
     * @param row
     * @param entityType
     */
    public static boolean isValidAssignActor(String entityType) {

       if (DVUtil.isValidNumber(entityType)
           && (Double.valueOf(entityType) == GROUP_ENTITY_TYPE 
                   || Double.valueOf(entityType) == USER_ENTITY_TYPE)) {
           return true;
       }
       return false;
    }

    /**
     *
     * @param row
     * @return
     */
    public static ProcessDVInfo getProcessVersion(HSSFRow row) {

        ProcessVersionDVInfo processVersion = new ProcessVersionDVInfo();

        String processName = DVUtil.getCellValue(row, COLUMN_NUMBER_0, null);
        if (StringUtilities.isEmpty(processName)) {

            DVLogger.log(SHEET_NAME_ZERO, ERROR_PROCESS_NAME_MANDATORY,
                            row.getRowNum());
            return null;
        }
        processVersion.setName(processName);

        processVersion.setDescription(DVUtil.getCellValue(row, COLUMN_NUMBER_1, null));

        int version = 1;
        String versionId = DVUtil.getCellValue(row, COLUMN_NUMBER_2, DEFAULT_VERSION);
        if (versionId != null && DVUtil.isValidNumber(versionId)) {
        	version = Double.valueOf(versionId).intValue();
        }
        processVersion.setVersionId(version);

        processVersion.setDefContent(DVUtil.getXMLFileContent(row, COLUMN_NUMBER_3));
        processVersion.setGraphContent(DVUtil.getXMLFileContent(row, COLUMN_NUMBER_4));
      
        String active = DVUtil.getCellValue(row, COLUMN_NUMBER_5, null);
        if (!DEFAULT_YES.equalsIgnoreCase(active)) {
            active = DEFAULT_NO;
        }
        processVersion.setActive(active);

        String createdDate = DVUtil.getCellValue(row, COLUMN_NUMBER_6, null);
        if (StringUtilities.isEmpty(createdDate)
                || !DVUtil.isValidDate(createdDate)) {
            createdDate = DVUtil.formatDate(new Date());
        }
        processVersion.setCreatedDate(DVUtil.parseDate(createdDate));

        String effectiveDate = DVUtil.getCellValue(row, COLUMN_NUMBER_7, null);
        if (StringUtilities.isEmpty(effectiveDate)
                || !DVUtil.isValidDate(effectiveDate)) {
            effectiveDate = DVUtil.formatDate(new Date());
        }
        processVersion.setEffectiveDate(DVUtil.parseDate(effectiveDate));

        return getProcessVersionData(row, processVersion);
    }

    /**
     * @param row HSSFRow
     * @param processVersion ProcessVersion
     *
     * @return Process
     */
    public static ProcessDVInfo getProcessVersionData(HSSFRow row,
                                                      ProcessVersionDVInfo processVersion) {

        String expireDate = DVUtil.getCellValue(row, COLUMN_NUMBER_8, null);
        boolean isToDay = false;
        if (StringUtilities.isEmpty(expireDate)) {

            expireDate = DVUtil.formatDate(new Date());
            isToDay = true;
        }
        if (!DVUtil.isValidDate(expireDate) && !isToDay) {

            DVLogger.log(SHEET_NAME_ZERO, ERROR_EXPIRE_DATE_INVALID, row.getRowNum());
            expireDate = new Date().toString();
        }
        processVersion.setExpireDate(DVUtil.parseDate(expireDate));

        String lockedBy = DVUtil.getCellValue(row, COLUMN_NUMBER_9, DEFAULT_ZERO);
        if (DVUtil.isValidNumber(lockedBy)) {
            processVersion.setLockedById(Integer.parseInt(lockedBy));
        }

        String status = DVUtil.getCellValue(row, COLUMN_NUMBER_10, DEFAULT_ZERO);
        if (status.equalsIgnoreCase(DEFAULT_STATUS)) {
            processVersion.setStatus(status);
        }

        String notification = DVUtil.getCellValue(row, COLUMN_NUMBER_11, DEFAULT_ZERO);
        if (notification.equalsIgnoreCase(DEFAULT_YES)) {
            processVersion.setNotifyException(notification);
        }
        processVersion.setNotifyEmailId(DVUtil.getCellValue(row, COLUMN_NUMBER_12, null));

        String maxInstDuration = DVUtil.getCellValue(row, COLUMN_NUMBER_13, DEFAULT_ZERO);
        if (DVUtil.isValidNumber(maxInstDuration)) {
            processVersion.setMaxInstanceDuration(Integer.parseInt(maxInstDuration));
        }

        String retPeriod = DVUtil.getCellValue(row, COLUMN_NUMBER_14, DEFAULT_ZERO);
        if (DVUtil.isValidNumber(retPeriod)) {
            processVersion.setRetentionPeriod(Integer.parseInt(retPeriod));
        }

        processVersion.setProcessOwner(getProcessOwner(row));

        return processVersion;
    }

    /**
     * @param row HSSFRow
     *
     * @return Process
     */
    public static ProcessOwnerDVInfo getProcessOwner(HSSFRow row) {

        ProcessOwnerDVInfo processOwner = new ProcessOwnerDVInfo();

        String processName = DVUtil.getCellValue(row, COLUMN_NUMBER_0, null);
        if (StringUtilities.isEmpty(processName)) {

            DVLogger.log(SHEET_NAME_ZERO, ERROR_PROCESS_NAME_MANDATORY, row.getRowNum());
            return null;
        }
        processOwner.setName(processName);

        String ownerActorName = DVUtil.getCellValue(row, COLUMN_NUMBER_15, null);
        if (StringUtilities.isEmpty(ownerActorName)) {

            DVLogger.log(SHEET_NAME_ZERO, ERROR_OWNER_ACTOR_MANDATORY, row.getRowNum());
            return null;
        }
        processOwner.setOwnerName(ownerActorName);

        return processOwner;
    }

    /**
     * @param row HSSFRow
     *
     * @return Process
     */
    public static ProcessDVInfo getProcessAssign(HSSFRow row) {

        ProcessAssignDVInfo processsAssign = new ProcessAssignDVInfo();

        String processName = DVUtil.getCellValue(row, COLUMN_NUMBER_0, null);
        if (StringUtilities.isEmpty(processName)) {

            DVLogger.log(SHEET_NAME_TWO, ERROR_PROCESS_NAME_MANDATORY, row.getRowNum());
            return null;
        }
        processsAssign.setName(processName);

        String actorName = DVUtil.getCellValue(row, COLUMN_NUMBER_1, null);
        String entityType = DVUtil.getCellValue(row, COLUMN_NUMBER_2, null);

        if (StringUtilities.isEmpty(actorName)) {

            DVLogger.log(SHEET_NAME_TWO, ERROR_OWNER_ACTOR_MANDATORY, row.getRowNum());
            return null;
        }
        if (!isValidAssignActor(entityType)) {

            DVLogger.log(SHEET_NAME_TWO, ERROR_INVALID_ASSIGN_ACTOR_ENTITY_TYPE, row.getRowNum());
            return null;
        }

        processsAssign.setActorName(actorName);
        processsAssign.setPermission(DVUtil.getCellValue(row, COLUMN_NUMBER_3, null));
        processsAssign.setEntityType(Double.valueOf(entityType).intValue());
        return processsAssign;
    }

    /**
     * @param row HSSFRow
     *
     * @return Process
     */
    public static ProcessDVInfo getProcessAttribute(HSSFRow row) {

        ProcessAttributeDVInfo processAttribute = new ProcessAttributeDVInfo();

        String processName = DVUtil.getCellValue(row, COLUMN_NUMBER_0, null);
        if (StringUtilities.isEmpty(processName)) {

            DVLogger.log(SHEET_NAME_ONE, ERROR_PROCESS_NAME_MANDATORY, row.getRowNum());
            return null;
        }
        processAttribute.setName(processName);

        int version = 1;
        String versionId = DVUtil.getCellValue(row, COLUMN_NUMBER_1, DEFAULT_VERSION);
        if (versionId != null && DVUtil.isValidNumber(versionId)) {
        	version = Double.valueOf(versionId).intValue();
        }
        processAttribute.setVersionId(version);

        String attributeName = DVUtil.getCellValue(row, COLUMN_NUMBER_2, null);
        if (StringUtilities.isEmpty(attributeName)) {

            DVLogger.log(SHEET_NAME_ONE, ERROR_ATTRIBUTE_NAME_MANDATORY, row.getRowNum());
            return null;
        }
        processAttribute.setAttributeName(attributeName);

        String attributeValue = DVUtil.getCellValue(row, COLUMN_NUMBER_3, null);
        if (StringUtilities.isEmpty(attributeValue)) {

            DVLogger.log(SHEET_NAME_ONE, ERROR_ATTRIBUTE_VALUE_MANDATORY, row.getRowNum());
            return null;
        }
        processAttribute.setAttributeValue(attributeValue);
        processAttribute.setAttributeType(
                DVUtil.getCellValue(row, COLUMN_NUMBER_4, null));

        return processAttribute;
    }

    /**
     *
     * @param row HSSFRow
     * 
     * @param sheetNumber int
     * 
     * @return ProcessDVInfo
     */
    public static ProcessDVInfo getProcessDetail(HSSFRow row, int sheetNumber) {

        switch (sheetNumber) {

            case SHEET_NUMBER_ZERO  : return getProcessVersion(row);
            case SHEET_NUMBER_ONE   : return getProcessAttribute(row);
            case SHEET_NUMBER_TWO   : return getProcessAssign(row);

            default : return null;
        }
    }
}
