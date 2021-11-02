package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementalDocuments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementalDocuments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementalDocument" type="{urn:eig.mckesson.com}SupplementalDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalDocuments", propOrder = {
    "_documents"
})
public class ROISupplementalDocuments {
    
    @XmlElement(name="supplementalDocument")
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
