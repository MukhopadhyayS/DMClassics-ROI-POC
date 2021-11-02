
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
 *         &lt;element name="ccdSourceConfigDtoRes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "ccdSourceConfigDtoRes"
})
@XmlRootElement(name = "updateSourceConfigResponse")
public class UpdateSourceConfigResponse {

    protected boolean ccdSourceConfigDtoRes;

    /**
     * Gets the value of the ccdSourceConfigDtoRes property.
     * 
     */
    public boolean isCcdSourceConfigDtoRes() {
        return ccdSourceConfigDtoRes;
    }

    /**
     * Sets the value of the ccdSourceConfigDtoRes property.
     * 
     */
    public void setCcdSourceConfigDtoRes(boolean value) {
        this.ccdSourceConfigDtoRes = value;
    }

}
