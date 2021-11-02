
package com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;


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
 *         &lt;element name="adjustmentInfo" type="{urn:eig.mckesson.com}AdjustmentInfo"/>
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
    "adjustmentInfo"
})
@XmlRootElement(name = "saveAdjustmentInfo")
public class SaveAdjustmentInfo {

    @XmlElement(required = true)
    protected AdjustmentInfo adjustmentInfo;

    /**
     * Gets the value of the adjustmentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AdjustmentInfo }
     *     
     */
    public AdjustmentInfo getAdjustmentInfo() {
        return adjustmentInfo;
    }

    /**
     * Sets the value of the adjustmentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdjustmentInfo }
     *     
     */
    public void setAdjustmentInfo(AdjustmentInfo value) {
        this.adjustmentInfo = value;
    }

}
