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

import com.mckesson.eig.roi.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.roi.inuse.base.service.BaseInUseValidator;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class InUseServiceValidator extends BaseInUseValidator {


    public boolean validateFields(String objectType, String objectId, String appId, String userId) {

        validateFields(objectType, objectId);
        validateAppId(appId);
        validateUserId(userId);
        return hasNoErrors();
    }

    public boolean validateFields(String objectType,
                                  String objectId,
                                  String appId,
                                  String userId,
                                  int expMin) {

        validateFields(objectType, objectId, expMin);
        validateAppId(appId);
        validateUserId(userId);
        return hasNoErrors();
    }

    public boolean validateFields(String objectType, String objectId, int expMin) {

        validateFields(objectType, objectId);

        if (expMin <= 0) {
            addError(InUseClientErrorCodes.EXPIRES_MINUTES_SHOULD_NOT_BE_LESS_THAN_ZERO);
        }

        return hasNoErrors();
    }

    public boolean validateFields(String objectType, String objectId) {

        validateFields(objectType);

        if (StringUtilities.isEmpty(objectId)) {
            addError(InUseClientErrorCodes.OBJECT_ID_SHOULD_NOT_BE_EMPTY);
        }

        return hasNoErrors();
    }

    public boolean validateFields(String objectType) {

        if (StringUtilities.isEmpty(objectType)) {
            addError(InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY);
        }
        return hasNoErrors();
    }

    public boolean validateAppId(String appId) {

        if (StringUtilities.isEmpty(appId)) {
            addError(InUseClientErrorCodes.INVALID_APPLICATION_ID);
        }
        return hasNoErrors();
    }

    public boolean validateUserId(String userId) {

        if (StringUtilities.isEmpty(userId)) {
            addError(InUseClientErrorCodes.USER_ID_SHOULD_NOT_BE_EMPTY);
        }
        return hasNoErrors();
    }

}
