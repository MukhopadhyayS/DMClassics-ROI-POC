package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocTypeAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocTypeAudit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codeSetName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nonDocumentTypeChangeAudit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocTypeAudit", propOrder = {
    "docType",
    "docName",
    "fromValue",
    "toValue",
    "codeSetName",
    "nonDocumentTypeChangeAudit"
})
public class DocTypeAudit implements Serializable {
    
    @XmlElement(required = true)
    private String docType;
    @XmlElement(required = true)
    private String docName;
    @XmlElement(required = true)
    private String fromValue;
    @XmlElement(required = true)
    private String toValue;
    @XmlElement(required = true)
    private String codeSetName;
    @XmlElement(required = true)
    private String nonDocumentTypeChangeAudit;

    public String getNonDocumentTypeChangeAudit() {
     return nonDocumentTypeChangeAudit;
    }

    public void setNonDocumentTypeChangeAudit(String nonDocumentTypeChangeAudit) {
     this.nonDocumentTypeChangeAudit = nonDocumentTypeChangeAudit;
    }
    
    public String getCodeSetName() {
        return codeSetName;
    }

    public void setCodeSetName(String codeSetName) {
        this.codeSetName = codeSetName;
    }
    
    public String getDocType() {
        return docType;
    }
    
    public void setDocType(String docType) {
        this.docType = docType;
    }
    
    public String getDocName() {
        return docName;
    }
    
    public void setDocName(String docName) {
        this.docName = docName;
    }
    
    public String getFromValue() {
        return fromValue;
    }
    
    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }
    
    public String getToValue() {
        return toValue;
    }
    
    public void setToValue(String toValue) {
        this.toValue = toValue;
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