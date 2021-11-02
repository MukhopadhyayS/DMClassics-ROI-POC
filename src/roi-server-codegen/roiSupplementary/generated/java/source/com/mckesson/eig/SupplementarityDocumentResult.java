
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementarityDocumentResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementarityDocumentResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementarityDocumentResult" type="{urn:eig.mckesson.com}SupplementarityDocuments"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityDocumentResult", propOrder = {
    "supplementarityDocumentResult"
})
public class SupplementarityDocumentResult {

    @XmlElement(required = true)
    protected SupplementarityDocuments supplementarityDocumentResult;

    /**
     * Gets the value of the supplementarityDocumentResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementarityDocuments }
     *     
     */
    public SupplementarityDocuments getSupplementarityDocumentResult() {
        return supplementarityDocumentResult;
    }

    /**
     * Sets the value of the supplementarityDocumentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementarityDocuments }
     *     
     */
    public void setSupplementarityDocumentResult(SupplementarityDocuments value) {
        this.supplementarityDocumentResult = value;
    }

}
