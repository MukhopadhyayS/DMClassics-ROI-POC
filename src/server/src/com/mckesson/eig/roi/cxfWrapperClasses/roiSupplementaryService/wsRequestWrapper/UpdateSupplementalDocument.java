
package com.mckesson.eig.roi.cxfWrapperClasses.roiSupplementaryService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument;


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
 *         &lt;element name="supplementalDocument" type="{urn:eig.mckesson.com}SupplementalDocument"/>
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
    "supplementalDocument"
})
@XmlRootElement(name = "updateSupplementalDocument")
public class UpdateSupplementalDocument {

    @XmlElement(required = true)
    protected ROISupplementalDocument supplementalDocument;

    /**
     * Gets the value of the supplementalDocument property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalDocument }
     *     
     */
    public ROISupplementalDocument getSupplementalDocument() {
        return supplementalDocument;
    }

    /**
     * Sets the value of the supplementalDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalDocument }
     *     
     */
    public void setSupplementalDocument(ROISupplementalDocument value) {
        this.supplementalDocument = value;
    }

}
