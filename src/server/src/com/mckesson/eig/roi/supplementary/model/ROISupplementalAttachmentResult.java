package com.mckesson.eig.roi.supplementary.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalAttachmentResult", propOrder = {
    "attachments"
})
public class ROISupplementalAttachmentResult {

    @XmlElement(name="supplementalAttachment") 
    private  List<ROISupplementalAttachment> attachments;

    public ROISupplementalAttachmentResult() {
        super();
    }

    public ROISupplementalAttachmentResult(
            List<ROISupplementalAttachment> attachments) {
        super();
        this.attachments = attachments;
    }

    public List<ROISupplementalAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ROISupplementalAttachment> attachments) {
        this.attachments = attachments;
    }
    
    
}
