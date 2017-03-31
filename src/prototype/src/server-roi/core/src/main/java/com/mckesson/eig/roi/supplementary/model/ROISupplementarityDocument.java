package com.mckesson.eig.roi.supplementary.model;

import com.mckesson.eig.roi.request.model.FreeFormFacility;

public class ROISupplementarityDocument
extends ROIDocumentCommon {
    
    private static final long serialVersionUID = 1L;
    
    private String _facility;
    private String _mrn;
    private FreeFormFacility _freeformFacility;

    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }
    
    public FreeFormFacility getFreeformFacility() { return _freeformFacility; }
    public void setFreeformFacility(FreeFormFacility freeformFacility) {
        _freeformFacility = freeformFacility;
    }

}
