package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementarityDocuments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementarityDocuments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementarityDocument" type="{urn:eig.mckesson.com}SupplementarityDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityDocuments", propOrder = {
    "_documents"
})
public class ROISupplementarityDocuments {
    
    @XmlElement(name="supplementarityDocumentResult")
    private ROISupplementarityDocumentResult _documents;

    public ROISupplementarityDocuments() {
        super();
    }
    
    public ROISupplementarityDocuments(List<ROISupplementarityDocument> documents) {
        super();
        setDocuments(documents);
    }
    
    public List<ROISupplementarityDocument> getDocuments() {
        return _documents.getDocuments();
    }

    public void setDocuments(List<ROISupplementarityDocument> documents) {
        _documents = new ROISupplementarityDocumentResult(documents);
    }

    /*
     * public void add(ROISupplementarityDocument document) { if(_documents ==
     * null) { _documents = new ArrayList<ROISupplementarityDocument>(); }
     * _documents.add(document); }
     */
}
