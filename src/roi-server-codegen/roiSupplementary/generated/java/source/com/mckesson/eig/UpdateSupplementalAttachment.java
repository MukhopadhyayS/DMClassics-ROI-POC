
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
 *         &lt;element name="supplementalAttachment" type="{urn:eig.mckesson.com}SupplementalAttachment"/>
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
    "supplementalAttachment"
})
@XmlRootElement(name = "updateSupplementalAttachment")
public class UpdateSupplementalAttachment {

    @XmlElement(required = true)
    protected SupplementalAttachment supplementalAttachment;

    /**
     * Gets the value of the supplementalAttachment property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalAttachment }
     *     
     */
    public SupplementalAttachment getSupplementalAttachment() {
        return supplementalAttachment;
    }

    /**
     * Sets the value of the supplementalAttachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalAttachment }
     *     
     */
    public void setSupplementalAttachment(SupplementalAttachment value) {
        this.supplementalAttachment = value;
    }

}
