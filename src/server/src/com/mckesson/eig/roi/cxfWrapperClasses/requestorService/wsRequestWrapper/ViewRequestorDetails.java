
package com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="retrieverType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="letterType" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "invoiceId",
    "requestId",
    "docType",
    "retrieverType",
    "letterType"
})
@XmlRootElement(name = "viewRequestorDetails")
public class ViewRequestorDetails {

    protected long invoiceId;
    protected long requestId;
    @XmlElement(required = true)
    protected String docType;
    @XmlElement(required = true)
    protected String retrieverType;
    @XmlElement(required = true)
    protected String letterType;

    /**
     * Gets the value of the invoiceId property.
     * 
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the value of the invoiceId property.
     * 
     */
    public void setInvoiceId(long value) {
        this.invoiceId = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     */
    public void setRequestId(long value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the retrieverType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieverType() {
        return retrieverType;
    }

    /**
     * Sets the value of the retrieverType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieverType(String value) {
        this.retrieverType = value;
    }

    /**
     * Gets the value of the letterType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterType() {
        return letterType;
    }

    /**
     * Sets the value of the letterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterType(String value) {
        this.letterType = value;
    }

}
