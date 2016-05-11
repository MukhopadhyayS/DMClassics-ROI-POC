package com.mckesson.eig.roi.ccd.provider.model;

import java.io.Serializable;

public class AssigningAuthorityModel implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String _facCode;
    private String _mrn;
    private String _gpi;
    private String _hostSendingSystem;
    private String _hostFacilityCode;
    
    public String getFacCode() {
        return _facCode;
    }
    public void setFacCode(String code) {
        _facCode = code;
    }
    public String getGpi() {
        return _gpi;
    }
    public void setGpi(String _gpi) {
        this._gpi = _gpi;
    }
    public String getHostFacilityCode() {
        return _hostFacilityCode;
    }
    public void setHostFacilityCode(String facilityCode) {
        _hostFacilityCode = facilityCode;
    }
    public String getHostSendingSystem() {
        return _hostSendingSystem;
    }
    public void setHostSendingSystem(String sendingSystem) {
        _hostSendingSystem = sendingSystem;
    }
    public String getMrn() {
        return _mrn;
    }
    public void setMrn(String _mrn) {
        this._mrn = _mrn;
    }
    
    

}
