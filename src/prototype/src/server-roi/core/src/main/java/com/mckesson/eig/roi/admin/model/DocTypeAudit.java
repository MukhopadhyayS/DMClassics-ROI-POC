package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;

public class DocTypeAudit implements Serializable {
    
    private String _docType;
    private String _docName;
    private String _fromValue;
    private String _toValue;
    private String _codeSetName;
    private String _nonDocumentTypeChangeAudit;

    public String getNonDocumentTypeChangeAudit() {
     return _nonDocumentTypeChangeAudit;
    }

    public void setNonDocumentTypeChangeAudit(String nonDocumentTypeChangeAudit) {
     this._nonDocumentTypeChangeAudit = nonDocumentTypeChangeAudit;
    }
    
    public String getCodeSetName() {
        return _codeSetName;
    }

    public void setCodeSetName(String codeSetName) {
        this._codeSetName = codeSetName;
    }
    
    public String getDocType() {
        return _docType;
    }
    
    public void setDocType(String docType) {
        this._docType = docType;
    }
    
    public String getDocName() {
        return _docName;
    }
    
    public void setDocName(String docName) {
        this._docName = docName;
    }
    
    public String getFromValue() {
        return _fromValue;
    }
    
    public void setFromValue(String fromValue) {
        this._fromValue = fromValue;
    }
    
    public String getToValue() {
        return _toValue;
    }
    
    public void setToValue(String toValue) {
        this._toValue = toValue;
    }   
    
    public String createAuditComment(DocTypeAudit docTypeAudit) {
        if ("MU".equalsIgnoreCase(docTypeAudit.getDocType())) {
            return new StringBuffer().append("For code set [" + docTypeAudit.getCodeSetName() + "] " + "ROI MU Document Type modified to " + docTypeAudit.getToValue() + 
                    " from " + docTypeAudit.getFromValue() + " for " + docTypeAudit.getDocName()).toString();
        } else if("Disclosure".equalsIgnoreCase(docTypeAudit.getDocType())){
            return new StringBuffer().append("ROI Disclosure Document Type modified." + "For code set [" + docTypeAudit.getCodeSetName() + "] " + "Disclosure Request DocType was changed to "  + docTypeAudit.getToValue() + 
                    " from " + docTypeAudit.getFromValue()).toString();
        } else {
            return new StringBuffer().append("ROI Authorize Document Type modified." + "For code set [" + docTypeAudit.getCodeSetName() + "] " + "Authorization Request DocType was changed to "  + docTypeAudit.getToValue() + 
                    " from " + docTypeAudit.getFromValue()).toString();
        }
            
    }

}