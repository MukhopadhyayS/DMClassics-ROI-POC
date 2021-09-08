package com.mckesson.eig.audit.dao.hpf;

import java.util.Date;

public class AuditTrail {
	private String _actionCode;
	private Long _userInstanceId;
	private Date _createDateTime;
	private String _comment;
	private String _mrn;
	private String _ecnounter;
	private String _facility;
	
	public String getActionCode() {
		return _actionCode;
	}
	
	public void setActionCode(String actionCode) {
		_actionCode = actionCode;
	}
	
	public String getComment() {
		return _comment;
	}
	
	public void setComment(String comment) {
		_comment = comment;
	}
	
	public Date getCreateDateTime() {
		return _createDateTime;
	}
	
	public void setCreateDateTime(Date createDateTime) {
		_createDateTime = createDateTime;
	}
	
	public String getEcnounter() {
		return _ecnounter;
	}
	
	public void setEcnounter(String ecnounter) {
		_ecnounter = ecnounter;
	}
	
	public String getFacility() {
		return _facility;
	}
	
	public void setFacility(String facility) {
		_facility = facility;
	}
	
	public String getMrn() {
		return _mrn;
	}
	
	public void setMrn(String mrn) {
		_mrn = mrn;
	}
	
	public Long getUserInstanceId() {
		return _userInstanceId;
	}
	
	public void setUserInstanceId(Long userInstanceId) {
		_userInstanceId = userInstanceId;
	}
	

}
