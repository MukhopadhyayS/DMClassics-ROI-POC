
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.DeliveryMethodList;


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
 *         &lt;element name="deliveryMethods" type="{urn:eig.mckesson.com}DeliveryMethodList"/>
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
    "deliveryMethods"
})
@XmlRootElement(name = "retrieveAllDeliveryMethodsResponse")
public class RetrieveAllDeliveryMethodsResponse {

    @XmlElement(required = true)
    protected DeliveryMethodList deliveryMethods;

    /**
     * Gets the value of the deliveryMethods property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryMethodList }
     *     
     */
    public DeliveryMethodList getDeliveryMethods() {
        return deliveryMethods;
    }

    /**
     * Sets the value of the deliveryMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryMethodList }
     *     
     */
    public void setDeliveryMethods(DeliveryMethodList value) {
        this.deliveryMethods = value;
    }

}
