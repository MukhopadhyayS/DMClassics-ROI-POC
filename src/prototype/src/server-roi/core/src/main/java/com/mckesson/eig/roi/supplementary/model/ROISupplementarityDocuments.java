package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;

public class ROISupplementarityDocuments {
    private List<ROISupplementarityDocument> _documents;

    public ROISupplementarityDocuments() {
        super();
    }
    
    public ROISupplementarityDocuments(List<ROISupplementarityDocument> documents) {
        super();
        setDocuments(documents);
    }
    
    public List<ROISupplementarityDocument> getDocuments() {
        return _documents;
    }

    public void setDocuments(List<ROISupplementarityDocument> documents) {
        _documents = documents;
    }

    public void add(ROISupplementarityDocument document) {
        if(_documents == null) {
            _documents = new ArrayList<ROISupplementarityDocument>();
        }
        _documents.add(document);
    }
}
