package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementarityAttachments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementarityAttachments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementarityAttachment" type="{urn:eig.mckesson.com}SupplementarityAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityAttachments", propOrder = {
    "_attachments"
})
public class ROISupplementarityAttachments {
    
    @XmlElement(name="supplementarityAttachment")
    private List<ROISupplementarityAttachment> _attachments;

    public ROISupplementarityAttachments() {
        super();
    }
    
    public ROISupplementarityAttachments(List<ROISupplementarityAttachment> attachments) {
        super();
        setAttachments(attachments);
    }
    
    public List<ROISupplementarityAttachment> getAttachments() {
        return _attachments;
    }

    public void setAttachments(List<ROISupplementarityAttachment> attachments) {
        _attachments = attachments;
    }

    public void add(ROISupplementarityAttachment attachment) {
        if(_attachments == null) {
            _attachments = new ArrayList<ROISupplementarityAttachment>();
        }
        _attachments.add(attachment);
    }
}
