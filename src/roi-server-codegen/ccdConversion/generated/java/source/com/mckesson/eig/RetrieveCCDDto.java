
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrieveCCDDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveCCDDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retrieveCCDKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retrieveCCDValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveCCDDto", propOrder = {
    "retrieveCCDKey",
    "retrieveCCDValue"
})
public class RetrieveCCDDto {

    @XmlElement(required = true)
    protected String retrieveCCDKey;
    @XmlElement(required = true)
    protected String retrieveCCDValue;

    /**
     * Gets the value of the retrieveCCDKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveCCDKey() {
        return retrieveCCDKey;
    }

    /**
     * Sets the value of the retrieveCCDKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveCCDKey(String value) {
        this.retrieveCCDKey = value;
    }

    /**
     * Gets the value of the retrieveCCDValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveCCDValue() {
        return retrieveCCDValue;
    }

    /**
     * Sets the value of the retrieveCCDValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveCCDValue(String value) {
        this.retrieveCCDValue = value;
    }

}
