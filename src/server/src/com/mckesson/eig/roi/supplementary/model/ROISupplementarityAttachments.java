package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;

public class ROISupplementarityAttachments {
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
