
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="feeTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "feeTypeId"
})
@XmlRootElement(name = "retrieveFeeType")
public class RetrieveFeeType {

    protected long feeTypeId;

    /**
     * Gets the value of the feeTypeId property.
     * 
     */
    public long getFeeTypeId() {
        return feeTypeId;
    }

    /**
     * Sets the value of the feeTypeId property.
     * 
     */
    public void setFeeTypeId(long value) {
        this.feeTypeId = value;
    }

}
