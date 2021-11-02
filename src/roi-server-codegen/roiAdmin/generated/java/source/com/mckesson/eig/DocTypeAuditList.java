
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
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
public class DocTypeAuditList {

    @XmlElement(nillable = true)
    protected List<DocTypeAudit> docTypeAudit;

    /**
     * Gets the value of the docTypeAudit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the docTypeAudit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocTypeAudit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocTypeAudit }
     * 
     * 
     */
    public List<DocTypeAudit> getDocTypeAudit() {
        if (docTypeAudit == null) {
            docTypeAudit = new ArrayList<DocTypeAudit>();
        }
        return this.docTypeAudit;
    }

}
