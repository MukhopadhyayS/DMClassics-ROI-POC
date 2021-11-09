package com.mckesson.eig.roi.supplementary.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityAttachmentResult", propOrder = {
    "attachments"
})
public class ROISupplementarityAttachmentResult {
    @XmlElement(name="supplementarityAttachment")
    private List<ROISupplementarityAttachment> attachments;
    
    public ROISupplementarityAttachmentResult() {
        super();
    }

    public ROISupplementarityAttachmentResult(
            List<ROISupplementarityAttachment> attachments) {
        super();
        this.attachments = attachments;
    }

    public List<ROISupplementarityAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ROISupplementarityAttachment> attachments) {
        this.attachments = attachments;
    }


    
}
