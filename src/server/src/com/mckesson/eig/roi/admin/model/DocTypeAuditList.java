package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.List;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocTypeAuditList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocTypeAuditList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docTypeAudit" type="{urn:eig.mckesson.com}DocTypeAudit" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocTypeAuditList", propOrder = {
    "docTypeAudit"
})
public class DocTypeAuditList implements Serializable {
    
   @XmlElement(nillable = true)
   private List<DocTypeAudit> docTypeAudit;
  
   public List<DocTypeAudit> getDocTypeAudit() {
    return docTypeAudit;
   }

   public void setDocTypeAudit(List<DocTypeAudit> docTypeAudit) {
    this.docTypeAudit = docTypeAudit;
   }
   
}
