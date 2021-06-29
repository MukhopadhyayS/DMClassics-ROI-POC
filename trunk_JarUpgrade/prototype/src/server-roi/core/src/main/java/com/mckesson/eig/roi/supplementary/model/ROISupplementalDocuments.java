package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;

public class ROISupplementalDocuments {
    private List<ROISupplementalDocument> _documents;

    public ROISupplementalDocuments() {
        super();
    }
    
    public ROISupplementalDocuments(List<ROISupplementalDocument> documents) {
        super();
        setDocuments(documents);
    }
    
    public List<ROISupplementalDocument> getDocuments() {
        return _documents;
    }

    public void setDocuments(List<ROISupplementalDocument> documents) {
        _documents = documents;
    }

    public void add(ROISupplementalDocument document) {
        if(_documents == null) {
            _documents = new ArrayList<ROISupplementalDocument>();
        }
        _documents.add(document);
    }
}
