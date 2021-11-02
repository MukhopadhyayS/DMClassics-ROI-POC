
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
 *         &lt;element name="retrieveParameters" type="{urn:eig.mckesson.com}RetrieveCCDDtoList"/>
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
    "retrieveParameters"
})
@XmlRootElement(name = "retrieveCCD")
public class RetrieveCCD {

    @XmlElement(required = true)
    protected RetrieveCCDDtoList retrieveParameters;

    /**
     * Gets the value of the retrieveParameters property.
     * 
     * @return
     *     possible object is
     *     {@link RetrieveCCDDtoList }
     *     
     */
    public RetrieveCCDDtoList getRetrieveParameters() {
        return retrieveParameters;
    }

    /**
     * Sets the value of the retrieveParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetrieveCCDDtoList }
     *     
     */
    public void setRetrieveParameters(RetrieveCCDDtoList value) {
        this.retrieveParameters = value;
    }

}
