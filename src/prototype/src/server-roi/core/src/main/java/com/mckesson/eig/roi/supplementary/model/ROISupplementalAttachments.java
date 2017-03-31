package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;

public class ROISupplementalAttachments {
    private List<ROISupplementalAttachment> _attachments;

    public ROISupplementalAttachments() {
        super();
    }
    
    public ROISupplementalAttachments(List<ROISupplementalAttachment> attachments) {
        super();
        setAttachments(attachments);
    }
    
    public List<ROISupplementalAttachment> getAttachments() {
        return _attachments;
    }

    public void setAttachments(List<ROISupplementalAttachment> attachments) {
        _attachments = attachments;
    }

    public void add(ROISupplementalAttachment attachment) {
        if(_attachments == null) {
            _attachments = new ArrayList<ROISupplementalAttachment>();
        }
        _attachments.add(attachment);
    }
}
