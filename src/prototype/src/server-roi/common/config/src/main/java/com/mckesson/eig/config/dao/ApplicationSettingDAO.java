/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.config.dao;

import com.mckesson.eig.config.model.ApplicationSetting;
import com.mckesson.eig.config.model.ApplicationSettingList;


/**
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 *
 * DAO interface for Application Setting Service
 *
 */

public interface ApplicationSettingDAO extends ConfigurationDAO {

    ApplicationSettingList getApplicationSettings();
    Boolean updateApplicationSetting(ApplicationSetting applnSetting);
    ApplicationSetting getApplicationSetting(long appId);
}
