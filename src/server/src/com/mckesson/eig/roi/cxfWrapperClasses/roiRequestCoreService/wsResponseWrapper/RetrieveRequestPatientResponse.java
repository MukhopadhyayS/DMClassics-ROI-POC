
package com.mckesson.eig.roi.cxfWrapperClasses.roiRequestCoreService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.request.model.RequestPatientsList;


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
 *         &lt;element name="requestPatientsList" type="{urn:eig.mckesson.com}RequestPatientsList"/>
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
    "requestPatientsList"
})
@XmlRootElement(name = "retrieveRequestPatientResponse")
public class RetrieveRequestPatientResponse {

    @XmlElement(required = true)
    protected RequestPatientsList requestPatientsList;

    /**
     * Gets the value of the requestPatientsList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestPatientsList }
     *     
     */
    public RequestPatientsList getRequestPatientsList() {
        return requestPatientsList;
    }

    /**
     * Sets the value of the requestPatientsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestPatientsList }
     *     
     */
    public void setRequestPatientsList(RequestPatientsList value) {
        this.requestPatientsList = value;
    }

}
