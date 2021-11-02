
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.DocTypeDesignations;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docTypeDesignation" type="{urn:eig.mckesson.com}DocTypeDesignations"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "docTypeDesignation"
})
@XmlRootElement(name = "retrieveDesignationsResponse")
public class RetrieveDesignationsResponse {

    @XmlElement(required = true)
    protected DocTypeDesignations docTypeDesignation;

    /**
     * Gets the value of the docTypeDesignation property.
     * 
     * @return
     *     possible object is
     *     {@link DocTypeDesignations }
     *     
     */
    public DocTypeDesignations getDocTypeDesignation() {
        return docTypeDesignation;
    }

    /**
     * Sets the value of the docTypeDesignation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocTypeDesignations }
     *     
     */
    public void setDocTypeDesignation(DocTypeDesignations value) {
        this.docTypeDesignation = value;
    }

}
