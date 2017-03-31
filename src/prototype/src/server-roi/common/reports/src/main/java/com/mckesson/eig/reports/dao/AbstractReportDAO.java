/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.reports.dao;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 13, 2007
 * @since  HECM 1.0
 *
 * This class holds all the common methods used in the DAO layer of the report component. All the
 * implementation classes in the report component extend this abstract class.
 */
public abstract class AbstractReportDAO
extends org.springframework.orm.hibernate4.support.HibernateDaoSupport
implements com.mckesson.eig.reports.dao.ReportDAO {

    /**
     * This method is used to execute the query
     *
     * @param Query - Input query object.
     * @return  ScrollableResults - output data;
     * @see com.mckesson.eig.reports.dao.ReportDAO#processQuery(org.hibernate.Query)
     */
    public ScrollableResults processQuery(Query query) {
        return query.scroll();
    }
}
