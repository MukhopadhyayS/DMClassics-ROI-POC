package com.mckesson.eig.roi.supplementary.model;

import com.mckesson.eig.roi.request.model.FreeFormFacility;

public class ROISupplementalAttachment 
extends ROIAttachmentCommon {
    
    private static final long serialVersionUID = 1L;
    
    private long _patientId;
    private long _freeFormFacilityId;
    private FreeFormFacility _freeformFacility;

    public long getPatientId() { return _patientId; }
    public void setPatientId(long patientId) { _patientId = patientId; }

    public long getFreeFormFacilityId() { return _freeFormFacilityId; }
    public void setFreeFormFacilityId(long freeFormFacilityId) {
        _freeFormFacilityId = freeFormFacilityId;
    }
    
    public FreeFormFacility getFreeformFacility() { return _freeformFacility; }
    public void setFreeformFacility(FreeFormFacility freeformFacility) {
        _freeformFacility = freeformFacility;
    }
}
