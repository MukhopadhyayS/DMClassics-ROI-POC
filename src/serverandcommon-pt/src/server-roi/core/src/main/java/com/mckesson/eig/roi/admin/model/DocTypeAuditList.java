package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.List;

public class DocTypeAuditList implements Serializable {
    
   private List<DocTypeAudit> _docTypeAudit;
  
   public List<DocTypeAudit> getDocTypeAudit() {
    return _docTypeAudit;
   }

   public void setDocTypeAudit(List<DocTypeAudit> docTypeAudit) {
    this._docTypeAudit = docTypeAudit;
   }
   
}
