
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementalDocumentResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementalDocumentResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementalDocumentResult" type="{urn:eig.mckesson.com}SupplementalDocuments"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalDocumentResult", propOrder = {
    "supplementalDocumentResult"
})
public class SupplementalDocumentResult {

    @XmlElement(required = true)
    protected SupplementalDocuments supplementalDocumentResult;

    /**
     * Gets the value of the supplementalDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalDocuments }
     *     
     */
    public SupplementalDocuments getSupplementalDocumentResult() {
        return supplementalDocumentResult;
    }

    /**
     * Sets the value of the supplementalDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalDocuments }
     *     
     */
    public void setSupplementalDocumentResult(SupplementalDocuments value) {
        this.supplementalDocumentResult = value;
    }

}
