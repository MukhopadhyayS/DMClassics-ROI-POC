
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorLetterHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorLetterHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorLetterId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="resendDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="templateUsed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorLetterHistory", propOrder = {
    "requestorLetterId",
    "createdDate",
    "resendDate",
    "outputMethod",
    "createdBy",
    "requestTemplateId",
    "templateUsed",
    "requestPassword",
    "queuePassword"
})
public class RequestorLetterHistory {

    protected long requestorLetterId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(required = true)
    protected String resendDate;
    @XmlElement(required = true)
    protected String outputMethod;
    @XmlElement(required = true)
    protected String createdBy;
    protected long requestTemplateId;
    @XmlElement(required = true)
    protected String templateUsed;
    @XmlElement(required = true)
    protected String requestPassword;
    @XmlElement(required = true)
    protected String queuePassword;

    /**
     * Gets the value of the requestorLetterId property.
     * 
     */
    public long getRequestorLetterId() {
        return requestorLetterId;
    }

    /**
     * Sets the value of the requestorLetterId property.
     * 
     */
    public void setRequestorLetterId(long value) {
        this.requestorLetterId = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the resendDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResendDate() {
        return resendDate;
    }

    /**
     * Sets the value of the resendDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResendDate(String value) {
        this.resendDate = value;
    }

    /**
     * Gets the value of the outputMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputMethod() {
        return outputMethod;
    }

    /**
     * Sets the value of the outputMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputMethod(String value) {
        this.outputMethod = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the requestTemplateId property.
     * 
     */
    public long getRequestTemplateId() {
        return requestTemplateId;
    }

    /**
     * Sets the value of the requestTemplateId property.
     * 
     */
    public void setRequestTemplateId(long value) {
        this.requestTemplateId = value;
    }

    /**
     * Gets the value of the templateUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateUsed() {
        return templateUsed;
    }

    /**
     * Sets the value of the templateUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateUsed(String value) {
        this.templateUsed = value;
    }

    /**
     * Gets the value of the requestPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestPassword() {
        return requestPassword;
    }

    /**
     * Sets the value of the requestPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestPassword(String value) {
        this.requestPassword = value;
    }

    /**
     * Gets the value of the queuePassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueuePassword() {
        return queuePassword;
    }

    /**
     * Sets the value of the queuePassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueuePassword(String value) {
        this.queuePassword = value;
    }

}
