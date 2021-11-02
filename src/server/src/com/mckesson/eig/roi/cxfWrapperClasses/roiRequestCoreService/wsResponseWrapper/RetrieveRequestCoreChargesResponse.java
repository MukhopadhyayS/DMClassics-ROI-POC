
package com.mckesson.eig.roi.cxfWrapperClasses.roiRequestCoreService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.request.model.RequestCoreCharges;


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
 *         &lt;element name="requestCoreChargesReq" type="{urn:eig.mckesson.com}RequestCoreCharges"/>
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
    "requestCoreChargesReq"
})
@XmlRootElement(name = "retrieveRequestCoreChargesResponse")
public class RetrieveRequestCoreChargesResponse {

    @XmlElement(required = true)
    protected RequestCoreCharges requestCoreChargesReq;

    /**
     * Gets the value of the requestCoreChargesReq property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCoreCharges }
     *     
     */
    public RequestCoreCharges getRequestCoreChargesReq() {
        return requestCoreChargesReq;
    }

    /**
     * Sets the value of the requestCoreChargesReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCoreCharges }
     *     
     */
    public void setRequestCoreChargesReq(RequestCoreCharges value) {
        this.requestCoreChargesReq = value;
    }

}
