
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdjustmentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdjustmentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reasonsList" type="{urn:eig.mckesson.com}ReasonsList"/>
 *         &lt;element name="requestorAdjustment" type="{urn:eig.mckesson.com}RequestorAdjustment"/>
 *         &lt;element name="requestorInvoicesList" type="{urn:eig.mckesson.com}RequestorInvoicesList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdjustmentInfo", propOrder = {
    "reasonsList",
    "requestorAdjustment",
    "requestorInvoicesList"
})
public class AdjustmentInfo {

    @XmlElement(required = true)
    protected ReasonsList reasonsList;
    @XmlElement(required = true)
    protected RequestorAdjustment requestorAdjustment;
    @XmlElement(required = true)
    protected RequestorInvoicesList requestorInvoicesList;

    /**
     * Gets the value of the reasonsList property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonsList }
     *     
     */
    public ReasonsList getReasonsList() {
        return reasonsList;
    }

    /**
     * Sets the value of the reasonsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonsList }
     *     
     */
    public void setReasonsList(ReasonsList value) {
        this.reasonsList = value;
    }

    /**
     * Gets the value of the requestorAdjustment property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorAdjustment }
     *     
     */
    public RequestorAdjustment getRequestorAdjustment() {
        return requestorAdjustment;
    }

    /**
     * Sets the value of the requestorAdjustment property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorAdjustment }
     *     
     */
    public void setRequestorAdjustment(RequestorAdjustment value) {
        this.requestorAdjustment = value;
    }

    /**
     * Gets the value of the requestorInvoicesList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorInvoicesList }
     *     
     */
    public RequestorInvoicesList getRequestorInvoicesList() {
        return requestorInvoicesList;
    }

    /**
     * Sets the value of the requestorInvoicesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorInvoicesList }
     *     
     */
    public void setRequestorInvoicesList(RequestorInvoicesList value) {
        this.requestorInvoicesList = value;
    }

}
