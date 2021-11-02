
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MUDocTypeDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MUDocTypeDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="muDocId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="muDocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MUDocTypeDto", propOrder = {
    "muDocId",
    "muDocName"
})
public class MUDocTypeDto {

    protected long muDocId;
    @XmlElement(required = true)
    protected String muDocName;

    /**
     * Gets the value of the muDocId property.
     * 
     */
    public long getMuDocId() {
        return muDocId;
    }

    /**
     * Sets the value of the muDocId property.
     * 
     */
    public void setMuDocId(long value) {
        this.muDocId = value;
    }

    /**
     * Gets the value of the muDocName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuDocName() {
        return muDocName;
    }

    /**
     * Sets the value of the muDocName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuDocName(String value) {
        this.muDocName = value;
    }

}
