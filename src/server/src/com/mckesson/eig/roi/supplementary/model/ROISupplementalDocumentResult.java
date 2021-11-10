package com.mckesson.eig.roi.supplementary.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalDocumentResult", propOrder = {
    "documents"
})
public class ROISupplementalDocumentResult {

    @XmlElement(name="supplementalDocument")
    private List<ROISupplementalDocument> documents;

    public ROISupplementalDocumentResult() {
        super();
    }

    public ROISupplementalDocumentResult(
            List<ROISupplementalDocument> documents) {
        super();
        this.documents = documents;
    }

    public List<ROISupplementalDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ROISupplementalDocument> documents) {
        this.documents = documents;
    } 
    
}
