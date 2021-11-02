
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
 *         &lt;element name="inUseRecords" type="{urn:eig.mckesson.com}InUseRecordList"/>
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
    "inUseRecords"
})
@XmlRootElement(name = "retrieveInUseRecordsByIDsResponse")
public class RetrieveInUseRecordsByIDsResponse {

    @XmlElement(required = true)
    protected InUseRecordList inUseRecords;

    /**
     * Gets the value of the inUseRecords property.
     * 
     * @return
     *     possible object is
     *     {@link InUseRecordList }
     *     
     */
    public InUseRecordList getInUseRecords() {
        return inUseRecords;
    }

    /**
     * Sets the value of the inUseRecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link InUseRecordList }
     *     
     */
    public void setInUseRecords(InUseRecordList value) {
        this.inUseRecords = value;
    }

}
