package com.mckesson.eig.roi.ccd.provider.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CcdDocuments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CcdDocuments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ccdDocument" type="{urn:eig.mckesson.com}CcdDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcdDocuments", propOrder = {
    "_ccdDocuments"
})
public class CcdDocumentList {
    
    @XmlElement(name="ccdDocument")
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
