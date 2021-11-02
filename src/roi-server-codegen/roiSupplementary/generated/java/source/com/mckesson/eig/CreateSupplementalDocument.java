
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
@XmlRootElement(name = "createSupplementalDocument")
public class CreateSupplementalDocument {

    @XmlElement(required = true)
    protected SupplementalDocument supplementalDocument;

    /**
     * Gets the value of the supplementalDocument property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalDocument }
     *     
     */
    public SupplementalDocument getSupplementalDocument() {
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
    public void setSupplementalDocument(SupplementalDocument value) {
        this.supplementalDocument = value;
    }

}
