package com.mckesson.eig.audit.dao.hpf;

import java.util.Date;

import com.mckesson.eig.audit.model.AuditEvent;

public class AuditFactory {
	
	public static final String E_P_R_S_FACILITY = "E_P_R_S";
	
	public AuditTrail createAuditTrail(AuditEvent auditEvent) {
		AuditTrail trail = new AuditTrail();
		trail.setActionCode(auditEvent.getActionCode());
		Date createDate = auditEvent.getEventStart();
		if (createDate == null) {
			createDate = new Date(System.currentTimeMillis());
		}
		trail.setCreateDateTime(createDate);
		trail.setUserInstanceId(auditEvent.getUserId());
		trail.setMrn(auditEvent.getMrn());
		trail.setEcnounter(auditEvent.getEncounter());
		String facility = auditEvent.getFacility();
		if (facility == null) {
			facility = E_P_R_S_FACILITY;
		}
		trail.setFacility(facility);
		trail.setComment(auditEvent.getComment());
		
		return trail;
	}

}
