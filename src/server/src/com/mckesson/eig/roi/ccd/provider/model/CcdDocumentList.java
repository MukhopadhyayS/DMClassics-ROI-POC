package com.mckesson.eig.roi.ccd.provider.model;

import java.util.List;

public class CcdDocumentList {
    private List<CcdDocument> _ccdDocuments;

    public CcdDocumentList() {
    }

    public CcdDocumentList(List<CcdDocument> ccdDocuments) {
        setCcdDocuments(ccdDocuments);
    }

    public List<CcdDocument> getCcdDocuments() {
        return _ccdDocuments;
    }

    public void setCcdDocuments(List<CcdDocument> ccdDocuments) {
        _ccdDocuments = ccdDocuments;
    }
}
