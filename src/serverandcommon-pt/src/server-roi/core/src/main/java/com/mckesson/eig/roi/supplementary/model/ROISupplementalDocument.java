package com.mckesson.eig.roi.supplementary.model;

import com.mckesson.eig.roi.request.model.FreeFormFacility;

public class ROISupplementalDocument 
extends ROIDocumentCommon {
    
    private static final long serialVersionUID = 1L;
    
    private long _patientId;
    private FreeFormFacility _freeformFacility;

    public long getPatientId() { return _patientId; }
    public void setPatientId(long patientId) { _patientId = patientId; }

    public FreeFormFacility getFreeformFacility() { return _freeformFacility; }
    public void setFreeformFacility(FreeFormFacility freeformFacility) {
        _freeformFacility = freeformFacility;
    }
}
