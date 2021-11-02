
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.RequestStatusMap;


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
 *         &lt;element name="requestStatusMap" type="{urn:eig.mckesson.com}RequestStatusMap"/>
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
    "requestStatusMap"
})
@XmlRootElement(name = "retrieveAllRequestStatusResponse")
public class RetrieveAllRequestStatusResponse {

    @XmlElement(required = true)
    protected RequestStatusMap requestStatusMap;

    /**
     * Gets the value of the requestStatusMap property.
     * 
     * @return
     *     possible object is
     *     {@link RequestStatusMap }
     *     
     */
    public RequestStatusMap getRequestStatusMap() {
        return requestStatusMap;
    }

    /**
     * Sets the value of the requestStatusMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestStatusMap }
     *     
     */
    public void setRequestStatusMap(RequestStatusMap value) {
        this.requestStatusMap = value;
    }

}
