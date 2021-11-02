
package com.mckesson.eig.roi.cxfWrapperClasses.overdueInvoiceCoreService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.OverDueDocInfoList;


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
 *         &lt;element name="overDueDocInfoList" type="{urn:eig.mckesson.com}OverDueDocInfoList"/>
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
    "overDueDocInfoList"
})
@XmlRootElement(name = "regenerateInvoiceCoreAndLetterResponse")
public class RegenerateInvoiceCoreAndLetterResponse {

    @XmlElement(required = true)
    protected OverDueDocInfoList overDueDocInfoList;

    /**
     * Gets the value of the overDueDocInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link OverDueDocInfoList }
     *     
     */
    public OverDueDocInfoList getOverDueDocInfoList() {
        return overDueDocInfoList;
    }

    /**
     * Sets the value of the overDueDocInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OverDueDocInfoList }
     *     
     */
    public void setOverDueDocInfoList(OverDueDocInfoList value) {
        this.overDueDocInfoList = value;
    }

}
