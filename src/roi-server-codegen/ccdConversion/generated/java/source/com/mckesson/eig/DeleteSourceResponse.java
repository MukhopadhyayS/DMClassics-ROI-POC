
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
 *         &lt;element name="ccdDeleteRes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "ccdDeleteRes"
})
@XmlRootElement(name = "deleteSourceResponse")
public class DeleteSourceResponse {

    protected boolean ccdDeleteRes;

    /**
     * Gets the value of the ccdDeleteRes property.
     * 
     */
    public boolean isCcdDeleteRes() {
        return ccdDeleteRes;
    }

    /**
     * Sets the value of the ccdDeleteRes property.
     * 
     */
    public void setCcdDeleteRes(boolean value) {
        this.ccdDeleteRes = value;
    }

}
