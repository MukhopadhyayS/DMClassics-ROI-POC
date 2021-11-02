
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.ReasonsList;


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
 *         &lt;element name="reasons" type="{urn:eig.mckesson.com}ReasonsList"/>
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
    "reasons"
})
@XmlRootElement(name = "retrieveAllReasonsByTypeResponse")
public class RetrieveAllReasonsByTypeResponse {

    @XmlElement(required = true)
    protected ReasonsList reasons;

    /**
     * Gets the value of the reasons property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonsList }
     *     
     */
    public ReasonsList getReasons() {
        return reasons;
    }

    /**
     * Sets the value of the reasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonsList }
     *     
     */
    public void setReasons(ReasonsList value) {
        this.reasons = value;
    }

}
