
package com.mckesson.eig.roi.cxfWrapperClasses.roiSupplementaryService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocuments;


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
 *         &lt;element name="supplementalDocumentsResult" type="{urn:eig.mckesson.com}SupplementalDocumentResult"/>
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
    "supplementalDocumentsResult"
})
@XmlRootElement(name = "getSupplementalDocumentsResponse")
public class GetSupplementalDocumentsResponse {

    @XmlElement(required = true)
    protected ROISupplementalDocuments supplementalDocumentsResult;

    /**
     * Gets the value of the supplementalDocumentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalDocumentResult }
     *     
     */
    public ROISupplementalDocuments getSupplementalDocumentsResult() {
        return supplementalDocumentsResult;
    }

    /**
     * Sets the value of the supplementalDocumentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalDocumentResult }
     *     
     */
    public void setSupplementalDocumentsResult(ROISupplementalDocuments value) {
        this.supplementalDocumentsResult = value;
    }

}
