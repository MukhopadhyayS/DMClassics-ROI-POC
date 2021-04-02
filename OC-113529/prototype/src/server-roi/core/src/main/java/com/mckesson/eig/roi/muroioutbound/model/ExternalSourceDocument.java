package com.mckesson.eig.roi.muroioutbound.model;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.RetrieveParameter;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.utility.util.BooleanUtilities;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.ConversionUtilities;

public class ExternalSourceDocument {
    private int _id;
    private String _reqStatus = ROIConstants.NEW_STATUS;
    private String _attId;
    private String _outbound;
    private String _referenceId;
    private int _reqID;
    private String _mrn;
    private String _encounter;
    private String _facility;
    private String _extFacility;
    private String _reqType;
    private String _receivedDate;
    private String _fulfillDate;
    private String _retry;
    private String _outputType;
    
    private List<CcdDocument> _ccdDocuments;

    public ExternalSourceDocument() {
	_ccdDocuments = new ArrayList<CcdDocument>();
    }

    public ExternalSourceDocument(Map<RetrieveParameter, String> data) {
	super();
	if (data != null) {
	    for (RetrieveParameter p : RetrieveParameter.values()) {
		String value = data.get(p);
		if (value != null) {
		    if (p.equals(RetrieveParameter.MRN)) {
			setMrn(value);
		    } else if (p.equals(RetrieveParameter.ENCOUNTER)) {
			setEncounter(value);
		    } else if (p.equals(RetrieveParameter.FACILITY)) {
			setFacility(value);
		    } else if (p.equals(RetrieveParameter.REQID)) {
			setReqID(Integer.valueOf(value));
			setReferenceId(getReferenceId(value));
		    } else if (p.equals(RetrieveParameter.REQTYPE)) {
			setReqType(value);
		    } else if (p.equals(RetrieveParameter.RECEIPTDATE)) {
			setReceivedDate(value);
		    } else if (p.equals(RetrieveParameter.ASSIGNING_AUTHORITY)) {
			setExtFacility(value);
		    }
		}
	    }
	}
    }

    protected ExternalSourceDocument(ExternalSourceDocument original) {
	_ccdDocuments = new ArrayList<CcdDocument>();
	_reqID = original._reqID;
	_reqStatus = original._reqStatus;
	_mrn = original._mrn;
	_encounter = original._encounter;
	_facility = original._facility;
	_referenceId = original._referenceId;
	_reqType = original._reqType;
	_extFacility = original._extFacility;
	_receivedDate = original._receivedDate;
    }

    public String getAttId() {
	if (_attId != null) {
	    return _attId;
	}
	if (!CollectionUtilities.isEmpty(getCcdDocuments())) {
	    return getCcdDocuments().get(0).getFileName();
	}
	return null;
    }

    public List<String> getAttIds() {
	List<String> result = new ArrayList<String>();
	List<CcdDocument> documents = getCcdDocuments();
	if (!CollectionUtilities.isEmpty(documents)) {
	    for (CcdDocument c : documents) {
		result.add(c.getFileName());
	    }
	}
	return result;
    }

    public void setAttId(String id) {
	_attId = id;
    }

    public String getEncounter() {
	return _encounter;
    }

    public void setEncounter(String _encounter) {
	this._encounter = _encounter;
    }

    public String getFacility() {
	return _facility;
    }

    public void setFacility(String _facility) {
	this._facility = _facility;
    }

    public int getId() {
	return _id;
    }

    public void setId(int _id) {
	this._id = _id;
    }

    public String getMrn() {
	return _mrn;
    }

    public void setMrn(String _mrn) {
	this._mrn = _mrn;
    }

    public String getOutbound() {
	return _outbound;
    }

    public void setOutbound(String _outbound) {
	this._outbound = _outbound;
    }

    public void setOutbound(boolean outbound) {
	this._outbound = ConversionUtilities.toYesNoFlag(outbound);
    }

    public int getReqID() {
	return _reqID;
    }

    public void setReqID(int _reqid) {
	_reqID = _reqid;
    }

    public String getReqStatus() {
	return _reqStatus;
    }

    public void setReqStatus(String status) {
	_reqStatus = status;
    }

    public String getReferenceId() {
	return _referenceId;
    }

    public void setReferenceId(String id) {
	_referenceId = id;
    }

    public String getReqType() {
	return _reqType;
    }

    public void setReqType(String type) {
	_reqType = type;
    }

    public boolean isOutbounded() {
	return BooleanUtilities.valueOf(_outbound, false);
    }

    /**
     * @return the extFacility
     */
    public String getExtFacility() {
	return _extFacility;
    }

    /**
     * @param extFacility
     *            the extFacility to set
     */
    public void setExtFacility(String extFacility) {
	_extFacility = extFacility;
    }

    /**
     * @return the receivedDate
     */
    public String getReceivedDate() {
	return _receivedDate;
    }

    /**
     * @param receivedDate
     *            the receivedDate to set
     */
    public void setReceivedDate(String receivedDate) {
	_receivedDate = receivedDate;
    }

    /**
     * @return the ccdDocument
     */
    public List<CcdDocument> getCcdDocuments() {
	return _ccdDocuments;
    }

    /**
     * @param ccdDocument
     *            the ccdDocument to set
     */
    public void setCcdDocuments(List<CcdDocument> ccdDocuments) {
	_ccdDocuments = ccdDocuments;
    }

    public void addCcdDocument(CcdDocument ccdDocument) {
	if (_ccdDocuments == null) {
	    _ccdDocuments = new ArrayList<CcdDocument>();
	}
	_ccdDocuments.add(ccdDocument);
    }

    public OutputStream getOutputStream() {
	if ((_ccdDocuments != null) && (_ccdDocuments.get(0) != null)) {
	    return _ccdDocuments.get(0).getOutputStream();
	}
	return null;
    }

    public static ExternalSourceDocument copy(ExternalSourceDocument orig) {
	return new ExternalSourceDocument(orig);
    }

    private String getReferenceId(String refId) {
	Date t = new Date();
	SimpleDateFormat s = new SimpleDateFormat(
		ROIConstants.ROI_REFERENCE_DATETIME_FORMAT);
	return refId + "-" + s.format(t);
    }

    /**
     * @return the fulfillDate
     */
    public String getFulfillDate() {
        return _fulfillDate;
    }

    /**
     * @param fulfillDate the fulfillDate to set
     */
    public void setFulfillDate(String fulfillDate) {
        _fulfillDate = fulfillDate;
    }

    /**
     * @return the retry
     */
    public String getRetry() {
        return _retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(String retry) {
        _retry = retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(boolean retry) {
	_retry = ConversionUtilities.toYesNoFlag(retry);
    }

    /**
     * @return the outputType
     */
    public String getOutputType() {
        return _outputType;
    }

    /**
     * @param outputType the outputType to set
     */
    public void setOutputType(String outputType) {
        _outputType = outputType;
    }

}
