package com.mckesson.eig.roi.dao;

import com.mckesson.eig.audit.dao.hpf.AuditTrail;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class AuditTrailHibernateDao extends HibernateDaoSupport {
  private DataSource _dataSource;
  
  private JdbcTemplate _jdbcTemplate;
  
  public void setDataSource(DataSource dataSource) {
    this._dataSource = dataSource;
    this._jdbcTemplate = new JdbcTemplate(this._dataSource);
  }
  
  public Class<?> getObjectType() {
    return AuditTrail.class;
  }
  
  public void save(AuditTrail object) {
    insertTable(object);
  }
  
  private void insertTable(AuditTrail trail) {
    String sql = "insert into Audit..Audit_Trail(userinstanceid, action, remark, occurred, mrn, encounter, facility) values (?,?,?,?,?,?,?)";
    Object[] args = { trail.getUserInstanceId(), trail.getActionCode(), trail.getComment(), trail.getCreateDateTime(), trail.getMrn(), trail.getEcnounter(), trail.getFacility() };
    int[] types = { 4, 12, 12, 93, 12, 12, 12 };
    this._jdbcTemplate.update(sql, args, types);
  }
  
  public Map<String, String> getAuditTrackValues() {
    String sql = "SELECT ACTION, TRACK, FACILITY FROM Audit..action_table";
    SqlRowSet rs = this._jdbcTemplate.queryForRowSet(sql);
    HashMap<String, String> actionMap = new HashMap<String, String>();
    while (rs.next()) {
      String key = rs.getString("ACTION").trim() + "~^~" + rs.getString("FACILITY").trim();
      String value = rs.getString("TRACK");
      actionMap.put(key, value);
    } 
    return actionMap;
  }
}
