
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
 *         &lt;element name="requestorLetterId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "requestorLetterId",
    "docType"
})
@XmlRootElement(name = "viewRequestorLetter")
public class ViewRequestorLetter {

    protected long requestorLetterId;
    @XmlElement(required = true)
    protected String docType;

    /**
     * Gets the value of the requestorLetterId property.
     * 
     */
    public long getRequestorLetterId() {
        return requestorLetterId;
    }

    /**
     * Sets the value of the requestorLetterId property.
     * 
     */
    public void setRequestorLetterId(long value) {
        this.requestorLetterId = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

}
