
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
 *         &lt;element name="DestInfoList" type="{urn:eig.mckesson.com}DestInfoList"/>
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
    "destInfoList"
})
@XmlRootElement(name = "getDestinationsResponse")
public class GetDestinationsResponse {

    @XmlElement(name = "DestInfoList", required = true)
    protected DestInfoList destInfoList;

    /**
     * Gets the value of the destInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link DestInfoList }
     *     
     */
    public DestInfoList getDestInfoList() {
        return destInfoList;
    }

    /**
     * Sets the value of the destInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestInfoList }
     *     
     */
    public void setDestInfoList(DestInfoList value) {
        this.destInfoList = value;
    }

}
