package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementalAttachments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementalAttachments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementalAttachment" type="{urn:eig.mckesson.com}SupplementalAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalAttachments", propOrder = {
    "_attachments"
})
public class ROISupplementalAttachments {
    
    @XmlElement(name="supplementalAttachmentResult")
    private ROISupplementalAttachmentResult _attachments;

    public ROISupplementalAttachments() {
        super();
    }
    
    public ROISupplementalAttachments(List<ROISupplementalAttachment> attachments) {
        super();
        setAttachments(attachments);
    }
    
    public List<ROISupplementalAttachment> getAttachments() {
        return _attachments.getAttachments();
    }

    public void setAttachments(List<ROISupplementalAttachment> attachments) {
        _attachments = new ROISupplementalAttachmentResult(attachments);
    }

    /*
     * public void add(ROISupplementalAttachment attachment) { if(_attachments
     * == null) { _attachments = new ArrayList<ROISupplementalAttachment>(); }
     * _attachments.add(attachment); }
     */
}
