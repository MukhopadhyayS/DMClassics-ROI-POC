package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;

public class ROISupplementalPatients {
    
    private boolean _maxCountExceeded;
    private List<ROISupplementalPatient> _patients;

    public ROISupplementalPatients() {
        super();
    }
    
    public boolean getMaxCountExceeded() { 
        return _maxCountExceeded; 
    }
    
    public void setMaxCountExceeded(boolean maxCountExceeded) {
        _maxCountExceeded = maxCountExceeded;
    }

    public ROISupplementalPatients(List<ROISupplementalPatient> patients) {
        super();
        setPatients(patients);
    }
    
    public List<ROISupplementalPatient> getPatients() {
        return _patients == null ? new ArrayList<ROISupplementalPatient>() : _patients;
    }

    public void setPatients(List<ROISupplementalPatient> patients) {
        _patients = patients;
    }

    public void add(ROISupplementalPatient patient) {
        if(_patients == null) {
            _patients = new ArrayList<ROISupplementalPatient>();
        }
        _patients.add(patient);
    }
}
