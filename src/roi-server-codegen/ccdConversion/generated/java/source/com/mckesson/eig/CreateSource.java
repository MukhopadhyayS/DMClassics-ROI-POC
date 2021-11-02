
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
 *         &lt;element name="ccdSourceDto" type="{urn:eig.mckesson.com}CcdSourceDto"/>
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
    "ccdSourceDto"
})
@XmlRootElement(name = "createSource")
public class CreateSource {

    @XmlElement(required = true)
    protected CcdSourceDto ccdSourceDto;

    /**
     * Gets the value of the ccdSourceDto property.
     * 
     * @return
     *     possible object is
     *     {@link CcdSourceDto }
     *     
     */
    public CcdSourceDto getCcdSourceDto() {
        return ccdSourceDto;
    }

    /**
     * Sets the value of the ccdSourceDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link CcdSourceDto }
     *     
     */
    public void setCcdSourceDto(CcdSourceDto value) {
        this.ccdSourceDto = value;
    }

}
