
package com.mckesson.eig.roi.cxfWrapperClasses.roiSupplementaryService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocuments;


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
 *         &lt;element name="supplementarityDocumentsResult" type="{urn:eig.mckesson.com}SupplementarityDocumentResult"/>
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
    "supplementarityDocumentsResult"
})
@XmlRootElement(name = "getSupplementarityDocumentsResponse")
public class GetSupplementarityDocumentsResponse {

    @XmlElement(required = true)
    protected ROISupplementarityDocuments supplementarityDocumentsResult;

    /**
     * Gets the value of the supplementarityDocumentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementarityDocumentResult }
     *     
     */
    public ROISupplementarityDocuments getSupplementarityDocumentsResult() {
        return supplementarityDocumentsResult;
    }

    /**
     * Sets the value of the supplementarityDocumentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementarityDocumentResult }
     *     
     */
    public void setSupplementarityDocumentsResult(ROISupplementarityDocuments value) {
        this.supplementarityDocumentsResult = value;
    }

}
