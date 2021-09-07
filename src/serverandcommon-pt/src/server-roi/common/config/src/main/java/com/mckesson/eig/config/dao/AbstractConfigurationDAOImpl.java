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

import java.sql.Date;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 *
 * It holds all the common functionalities which have to be used in the
 * dao layer of the ConfigServer components. All implementation
 * classes of dao layer of ConfigServer components extend this
 * abstract class.
 */
public class AbstractConfigurationDAOImpl extends HibernateDaoSupport {

    private static long _diff;
    private static boolean _diffSet;

    private void setDateDiff() {

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Date> list = (List<Date>) getHibernateTemplate().findByNamedQuery("getDBDate");
        _diff = list.get(0).getTime() - System.currentTimeMillis();
    }

    /**
     *
     * @see com.mckesson.eig.roi.base.dao.ROIDAO#getDate()
     */
    public Timestamp getDate() {

        if (!_diffSet) {
            setDateDiff();
            _diffSet = true;
        }

        return new Timestamp(System.currentTimeMillis() + _diff);
    }
}
