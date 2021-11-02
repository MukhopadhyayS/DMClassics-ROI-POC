
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="ccdSourceDtoRes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "ccdSourceDtoRes"
})
@XmlRootElement(name = "updateSourceResponse")
public class UpdateSourceResponse {

    protected boolean ccdSourceDtoRes;

    /**
     * Gets the value of the ccdSourceDtoRes property.
     * 
     */
    public boolean isCcdSourceDtoRes() {
        return ccdSourceDtoRes;
    }

    /**
     * Sets the value of the ccdSourceDtoRes property.
     * 
     */
    public void setCcdSourceDtoRes(boolean value) {
        this.ccdSourceDtoRes = value;
    }

}
