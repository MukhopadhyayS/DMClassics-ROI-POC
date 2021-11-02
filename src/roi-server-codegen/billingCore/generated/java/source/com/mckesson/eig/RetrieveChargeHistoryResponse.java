
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
 *         &lt;element name="chargeHistoryList" type="{urn:eig.mckesson.com}ChargeHistoryList"/>
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
    "chargeHistoryList"
})
@XmlRootElement(name = "retrieveChargeHistoryResponse")
public class RetrieveChargeHistoryResponse {

    @XmlElement(required = true)
    protected ChargeHistoryList chargeHistoryList;

    /**
     * Gets the value of the chargeHistoryList property.
     * 
     * @return
     *     possible object is
     *     {@link ChargeHistoryList }
     *     
     */
    public ChargeHistoryList getChargeHistoryList() {
        return chargeHistoryList;
    }

    /**
     * Sets the value of the chargeHistoryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeHistoryList }
     *     
     */
    public void setChargeHistoryList(ChargeHistoryList value) {
        this.chargeHistoryList = value;
    }

}
