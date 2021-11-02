
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
 *         &lt;element name="ccdDocuments" type="{urn:eig.mckesson.com}CcdDocuments"/>
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
    "ccdDocuments"
})
@XmlRootElement(name = "retrieveCCDResponse")
public class RetrieveCCDResponse {

    @XmlElement(required = true)
    protected CcdDocuments ccdDocuments;

    /**
     * Gets the value of the ccdDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link CcdDocuments }
     *     
     */
    public CcdDocuments getCcdDocuments() {
        return ccdDocuments;
    }

    /**
     * Sets the value of the ccdDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link CcdDocuments }
     *     
     */
    public void setCcdDocuments(CcdDocuments value) {
        this.ccdDocuments = value;
    }

}
