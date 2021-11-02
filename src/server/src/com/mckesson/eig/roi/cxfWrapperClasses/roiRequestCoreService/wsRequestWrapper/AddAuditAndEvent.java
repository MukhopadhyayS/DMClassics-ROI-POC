
package com.mckesson.eig.roi.cxfWrapperClasses.roiRequestCoreService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.request.model.AuditAndEventList;


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
 *         &lt;element name="auditAndEventList" type="{urn:eig.mckesson.com}AuditAndEventList"/>
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
    "auditAndEventList"
})
@XmlRootElement(name = "addAuditAndEvent")
public class AddAuditAndEvent {

    @XmlElement(required = true)
    protected AuditAndEventList auditAndEventList;

    /**
     * Gets the value of the auditAndEventList property.
     * 
     * @return
     *     possible object is
     *     {@link AuditAndEventList }
     *     
     */
    public AuditAndEventList getAuditAndEventList() {
        return auditAndEventList;
    }

    /**
     * Sets the value of the auditAndEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuditAndEventList }
     *     
     */
    public void setAuditAndEventList(AuditAndEventList value) {
        this.auditAndEventList = value;
    }

}
