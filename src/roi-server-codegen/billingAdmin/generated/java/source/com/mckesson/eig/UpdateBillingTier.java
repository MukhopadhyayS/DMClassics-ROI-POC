
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
 *         &lt;element name="bt" type="{urn:eig.mckesson.com}BillingTier"/>
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
    "bt"
})
@XmlRootElement(name = "updateBillingTier")
public class UpdateBillingTier {

    @XmlElement(required = true)
    protected BillingTier bt;

    /**
     * Gets the value of the bt property.
     * 
     * @return
     *     possible object is
     *     {@link BillingTier }
     *     
     */
    public BillingTier getBt() {
        return bt;
    }

    /**
     * Sets the value of the bt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingTier }
     *     
     */
    public void setBt(BillingTier value) {
        this.bt = value;
    }

}
