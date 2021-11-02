
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorInvoices complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorInvoices">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoiceIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorInvoices", propOrder = {
    "invoiceIds",
    "requestorId",
    "requestorName",
    "requestorType"
})
public class RequestorInvoices {

    @XmlElement(type = Long.class)
    protected List<Long> invoiceIds;
    protected long requestorId;
    protected String requestorName;
    @XmlElement(required = true)
    protected String requestorType;

    /**
     * Gets the value of the invoiceIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getInvoiceIds() {
        if (invoiceIds == null) {
            invoiceIds = new ArrayList<Long>();
        }
        return this.invoiceIds;
    }

    /**
     * Gets the value of the requestorId property.
     * 
     */
    public long getRequestorId() {
        return requestorId;
    }

    /**
     * Sets the value of the requestorId property.
     * 
     */
    public void setRequestorId(long value) {
        this.requestorId = value;
    }

    /**
     * Gets the value of the requestorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * Sets the value of the requestorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorName(String value) {
        this.requestorName = value;
    }

    /**
     * Gets the value of the requestorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorType(String value) {
        this.requestorType = value;
    }

}
