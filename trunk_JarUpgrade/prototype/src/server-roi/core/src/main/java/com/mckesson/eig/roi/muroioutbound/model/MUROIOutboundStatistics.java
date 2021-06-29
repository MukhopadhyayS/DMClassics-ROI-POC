package com.mckesson.eig.roi.muroioutbound.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.utility.util.BooleanUtilities;

/**
 * Domain Class to hold the values for ROI_OUTBOUND_STATISTICS table
 * 
 */
public class MUROIOutboundStatistics implements Serializable {

    private static final long serialVersionUID = 1L;
    private int _id;
    private int _reqID;
    private String _reqStatus;
    private Date _reqDate;
    private Date _availDate;
    private Date _fulfilledDate;
    private Date _cancelledDate;
    private double _ta;
    private double _totalDays;
    private int _weekEndDays;
    private String _mrn;
    private String _encounter;
    private String _facility;
    private String _hpfMuDocumentType;
    private int _userName;
    private String _externalSource;
    private String _reportSname;
    private String _requestFor;
    private String _documentType;
    private String _patSeq;
    private int _availBy;
    private String _cpiSeq;
    private Date _dischargeDate;
    private String _patientName;  
    private String _patientFirstName;
    private String _patientLastName;
    private Date _patientDOB;
    private String _patientSex;
    private String _outbound;
    private boolean _selectedForRelease;
    private String _type;

    public int getAvailBy() {
	return _availBy;
    }

    public void setAvailBy(int by) {
	_availBy = by;
    }

    public Date getAvailDate() {
	return _availDate;
    }

    public void setAvailDate(Date date) {
	_availDate = date;
    }

    public String getCpiSeq() {
	return _cpiSeq;
    }

    public void setCpiSeq(String seq) {
	_cpiSeq = seq;
    }

    public Date getDischargeDate() {
	return _dischargeDate;
    }

    public void setDischargeDate(Date date) {
	_dischargeDate = date;
    }

    public String getDocumentType() {
	return _documentType;
    }

    public void setDocumentType(String type) {
	_documentType = type;
    }

    public String getEncounter() {
	return _encounter;
    }

    public void setEncounter(String _encounter) {
	this._encounter = _encounter;
    }

    public String getExternalSource() {
	return _externalSource;
    }

    public void setExternalSource(String source) {
	_externalSource = source;
    }

    public String getFacility() {
	return _facility;
    }

    public void setFacility(String _facility) {
	this._facility = _facility;
    }

    public Date getFulfilledDate() {
	return _fulfilledDate;
    }

    public void setFulfilledDate(Date date) {
	_fulfilledDate = date;
    }

    public String getHpfMuDocumentType() {
	return _hpfMuDocumentType;
    }

    public void setHpfMuDocumentType(String muDocumentType) {
	_hpfMuDocumentType = muDocumentType;
    }

    public String getMrn() {
	return _mrn;
    }

    public void setMrn(String _mrn) {
	this._mrn = _mrn;
    }

    public Date getPatientDOB() {
	return _patientDOB;
    }

    public void setPatientDOB(Date _patientdob) {
	_patientDOB = _patientdob;
    }

    public String getPatientFirstName() {
	return _patientFirstName;
    }

    public void setPatientFirstName(String firstName) {
	_patientFirstName = firstName;
    }

    public String getPatientSex() {
	return _patientSex;
    }

    public void setPatientSex(String sex) {
	_patientSex = sex;
    }

    public String getPatientLastName() {
	return _patientLastName;
    }

    public void setPatientLastName(String lastName) {
	_patientLastName = lastName;
    }

    public String getPatSeq() {
	return _patSeq;
    }

    public void setPatSeq(String seq) {
	_patSeq = seq;
    }

    public String getReportSname() {
	return _reportSname;
    }

    public void setReportSname(String sname) {
	_reportSname = sname;
    }

    public Date getReqDate() {
	return _reqDate;
    }

    public void setReqDate(Date date) {
	_reqDate = date;
    }

    public int getId() {
	return _id;
    }

    public void setId(int id) {
	_id = id;
    }

    public String getReqStatus() {
	return _reqStatus;
    }

    public void setReqStatus(String status) {
	_reqStatus = status;
    }

    public String getRequestFor() {
	return _requestFor;
    }

    public void setRequestFor(String for1) {
	_requestFor = for1;
    }

    public int getUserName() {
	return _userName;
    }

    public void setUserName(int name) {
	_userName = name;
    }

    public double getTa() {
	return _ta;
    }

    public void setTa(double _ta) {
	this._ta = _ta;
    }

    public double getTotalDays() {
	return _totalDays;
    }

    public void setTotalDays(double days) {
	_totalDays = days;
    }

    public int getWeekEndDays() {
	return _weekEndDays;
    }

    public void setWeekEndDays(int endDays) {
	_weekEndDays = endDays;
    }

    public int getReqID() {
	return _reqID;
    }

    public void setReqID(int reqid) {
	_reqID = reqid;
    }

    public Date getCancelledDate() {
	return _cancelledDate;
    }

    public void setCancelledDate(Date date) {
	_cancelledDate = date;
    }

    public String getOutbound() {
	return _outbound;
    }

    public void setOutbound(String outbound) {
	_outbound = outbound;
    }

    public boolean isOutbounded() {
	return BooleanUtilities.valueOf(_outbound, false);
    }
    
    public String getPatientName() {
        return _patientName;
    }

    public void setPatientName(String patientName) {
        this._patientName = patientName;
    }
    
    public boolean isSelectedForRelease() {
        return _selectedForRelease;
    }

    public void setSelectedForRelease(boolean selectedForRelease) {
        this._selectedForRelease = selectedForRelease;
    }
    
    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }
    
}
