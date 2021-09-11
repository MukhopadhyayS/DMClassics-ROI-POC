/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.audit.dao.hpf;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mckesson.eig.iws.orm.hibernate.HibernateDaoSupport;

public class AuditTrailHibernateDao extends HibernateDaoSupport {

	private DataSource _dataSource;
	private JdbcTemplate _jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
		_jdbcTemplate = new JdbcTemplate(_dataSource);
	}

	@Override
    public Class<?> getObjectType() {
		return com.mckesson.eig.audit.dao.hpf.AuditTrail.class;
	}

    public void save(AuditTrail object) {
    	insertTable(object);
    }

	private void insertTable(AuditTrail trail) {
		String sql = "insert into Audit..Audit_Trail(userinstanceid, action, remark, "
                    + "occurred, mrn, encounter, facility) values (?,?,?,?,?,?,?)";
        Object [] args  = new Object [] { trail.getUserInstanceId(),
                                          trail.getActionCode(),
                                          trail.getComment(),
                                          trail.getCreateDateTime(),
                                          trail.getMrn(),
                                          trail.getEcnounter(),
                                          trail.getFacility()};
		int [] types = new int [] { Types.INTEGER,
                                    Types.VARCHAR,
                                    Types.VARCHAR,
                                    Types.TIMESTAMP,
                                    Types.VARCHAR,
                                    Types.VARCHAR,
                                    Types.VARCHAR};
		_jdbcTemplate.update(sql, args, types);
	}

	/**
	 * This method retrieves the action, track and facility values from the table action_table
	 * and convert the values into to key(action + facility) value(track) pair.
	 * @return Map<String, String>
	 */
    public Map<String, String> getAuditTrackValues() {

	    String sql = "SELECT ACTION, TRACK, FACILITY FROM Audit..action_table";
	    SqlRowSet rs = _jdbcTemplate.queryForRowSet(sql);

	    HashMap<String, String> actionMap = new HashMap<String, String>();

	    while (rs.next()) {

	        String key =  rs.getString("ACTION").trim()
	                     + "~^~"
	                     + rs.getString("FACILITY").trim();
	        String value = rs.getString("TRACK");
	        actionMap.put(key, value);
	    }

	    return actionMap;
	}
}
