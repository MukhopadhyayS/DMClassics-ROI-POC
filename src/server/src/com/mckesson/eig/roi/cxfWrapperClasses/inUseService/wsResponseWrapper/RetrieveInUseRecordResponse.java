
package com.mckesson.eig.roi.cxfWrapperClasses.inUseService.wsResponseWrapper;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.inuse.model.InUseRecord;


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
 *         &lt;element name="inUseRecord" type="{urn:eig.mckesson.com}InUseRecord" minOccurs="0"/>
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
    "inUseRecord"
})
@XmlRootElement(name = "retrieveInUseRecordResponse")
public class RetrieveInUseRecordResponse {

    @XmlElement(name = "inUseRecord")
    protected InUseRecord inUseRecord;

    /**
     * Gets the value of the inUseRecord property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link InUseRecord }{@code >}
     *     
     */
    public InUseRecord getInUseRecord() {
        return inUseRecord;
    }

    /**
     * Sets the value of the inUseRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link InUseRecord }{@code >}
     *     
     */
    public void setInUseRecord(InUseRecord value) {
        this.inUseRecord = value;
    }

}
