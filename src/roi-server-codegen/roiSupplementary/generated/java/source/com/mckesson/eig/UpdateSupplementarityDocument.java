
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="supplementarityDocument" type="{urn:eig.mckesson.com}SupplementarityDocument"/>
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
    "supplementarityDocument"
})
@XmlRootElement(name = "updateSupplementarityDocument")
public class UpdateSupplementarityDocument {

    @XmlElement(required = true)
    protected SupplementarityDocument supplementarityDocument;

    /**
     * Gets the value of the supplementarityDocument property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementarityDocument }
     *     
     */
    public SupplementarityDocument getSupplementarityDocument() {
        return supplementarityDocument;
    }

    /**
     * Sets the value of the supplementarityDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementarityDocument }
     *     
     */
    public void setSupplementarityDocument(SupplementarityDocument value) {
        this.supplementarityDocument = value;
    }

}
