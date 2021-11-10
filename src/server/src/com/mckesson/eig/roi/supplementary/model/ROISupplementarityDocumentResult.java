package com.mckesson.eig.roi.supplementary.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityDocumentsResult", propOrder = {
        "documents"
    })
public class ROISupplementarityDocumentResult {

    @XmlElement(name="supplementarityDocument")
    private List<ROISupplementarityDocument> documents;

    public ROISupplementarityDocumentResult() {
        super();
    }

    public ROISupplementarityDocumentResult(
            List<ROISupplementarityDocument> documents) {
        super();
        this.documents = documents;
    }

    public List<ROISupplementarityDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ROISupplementarityDocument> documents) {
        this.documents = documents;
    }

}
