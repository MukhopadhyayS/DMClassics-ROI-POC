package com.mckesson.eig.roi.supplementary.model;

import java.io.File;
import java.io.IOException;

import com.mckesson.eig.roi.admin.dao.LetterTemplateDAOImpl;
import com.mckesson.eig.roi.admin.service.ROIAttachmentService.FailedReasonCode;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.dm.core.common.logging.OCLogger;

public class ROISupplementarityAttachment extends ROIAttachmentCommon {
    private static final OCLogger LOG = new OCLogger(LetterTemplateDAOImpl.class);
    
    private String _facility;
    private String _mrn;

    public String getMrn() {
        return _mrn;
    }
    
    public void setMrn(String mrn) {
        _mrn = mrn;
    }

    public String getFacility() {
        return _facility;
    }

    public void setFacility(String facility) {
        _facility = facility;
    }

    public FailedReasonCode delete() {
        
        FailedReasonCode deleteLog = FailedReasonCode.DELETION_SUCCESSFUL;
        File file = null;
        try {
		//DE7315 External Control of File Name or Path
            file = AccessFileLoader.getFile(getVolume(),  (getPath() + getUuid()));
        } catch (IOException e) {
                 LOG.error("Exception in delete() "+e.getLocalizedMessage());
        }
        if(file != null) {
            if (!file.exists()) {
                deleteLog = FailedReasonCode.FILE_MISSING;
            } else if (!file.canWrite()) {
                deleteLog = FailedReasonCode.FILE_DELETE_ACCESS_DENIED;
            } else {        
                boolean d = file.delete();
                if (!d) {
                    deleteLog = FailedReasonCode.DELETION_UNSUCCESSFUL;
                } else {
                    if (file.exists()) {
                        deleteLog = FailedReasonCode.FILE_EXISTS_AFTER_DELETION;
                    }
                }
            }
        }
        return deleteLog;
    }
}
