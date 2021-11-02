
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.DeliveryMethod;


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
 *         &lt;element name="deliveryMethodDetails" type="{urn:eig.mckesson.com}DeliveryMethod"/>
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
    "deliveryMethodDetails"
})
@XmlRootElement(name = "updateDeliveryMethodResponse")
public class UpdateDeliveryMethodResponse {

    @XmlElement(required = true)
    protected DeliveryMethod deliveryMethodDetails;

    /**
     * Gets the value of the deliveryMethodDetails property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryMethod }
     *     
     */
    public DeliveryMethod getDeliveryMethodDetails() {
        return deliveryMethodDetails;
    }

    /**
     * Sets the value of the deliveryMethodDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryMethod }
     *     
     */
    public void setDeliveryMethodDetails(DeliveryMethod value) {
        this.deliveryMethodDetails = value;
    }

}
