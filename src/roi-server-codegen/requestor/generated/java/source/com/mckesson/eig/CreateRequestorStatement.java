
package com.mckesson.eig;

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
 *         &lt;element name="RequestorStatementCriteria" type="{urn:eig.mckesson.com}RequestorStatementCriteria"/>
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
    "requestorStatementCriteria"
})
@XmlRootElement(name = "createRequestorStatement")
public class CreateRequestorStatement {

    @XmlElement(name = "RequestorStatementCriteria", required = true)
    protected RequestorStatementCriteria requestorStatementCriteria;

    /**
     * Gets the value of the requestorStatementCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorStatementCriteria }
     *     
     */
    public RequestorStatementCriteria getRequestorStatementCriteria() {
        return requestorStatementCriteria;
    }

    /**
     * Sets the value of the requestorStatementCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorStatementCriteria }
     *     
     */
    public void setRequestorStatementCriteria(RequestorStatementCriteria value) {
        this.requestorStatementCriteria = value;
    }

}
